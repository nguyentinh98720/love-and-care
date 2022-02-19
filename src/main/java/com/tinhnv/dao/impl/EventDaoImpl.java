package com.tinhnv.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tinhnv.context.ConnectDatabase;
import com.tinhnv.dao.EventDao;
import com.tinhnv.model.donate.Donate;
import com.tinhnv.model.donate.donateMapper.DonateMapper;
import com.tinhnv.model.event.EventForEdit;
import com.tinhnv.model.event.EventForManage;
import com.tinhnv.model.event.EventHomePage;
import com.tinhnv.model.event.eventMapper.EventEditMapper;
import com.tinhnv.model.event.eventMapper.EventManageMapper;
import com.tinhnv.model.others.ImgEvent;
import com.tinhnv.model.others.ResponseList;
import com.tinhnv.model.others.VaTiImage;
import com.tinhnv.model.others.rowMapper.ImgEventMapper;
import com.tinhnv.model.others.rowMapper.VaTiImageForEventMapper;


@Repository
public class EventDaoImpl implements EventDao {

	private JdbcTemplate jdbcTemplate = new ConnectDatabase().getConnection();
	
	@Override
	public List<EventForManage> getListEventManage() {
		String sql = "SELECT MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH;";
		
		List<EventForManage> list = jdbcTemplate.query(sql, new EventManageMapper());
		return list;
	}

	@Override
	public List<EventForManage> getListEventManage(String sort, String filter) {
		StringBuilder sql = new StringBuilder();
		if(filter.equalsIgnoreCase("all")) {
			sql.append("SELECT MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH");
		} else if(filter.equalsIgnoreCase("running")) {
			sql.append("SELECT MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH WHERE TRANGTHAICT=TRUE AND (NGAYKETTHUC IS NULL OR NGAYKETTHUC>CURRENT_DATE)");
		} else if(filter.equalsIgnoreCase("stopping")) {
			sql.append("SELECT MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH WHERE TRANGTHAICT=FALSE AND (NGAYKETTHUC IS NULL OR NGAYKETTHUC>CURRENT_DATE)");
		} else if(filter.equalsIgnoreCase("ended")) {
			sql.append("SELECT MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH WHERE NGAYKETTHUC<CURRENT_DATE");
		} else if(filter.equalsIgnoreCase("unlimit")) {
			sql.append("SELECT MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH WHERE NGAYKETTHUC IS NULL");
		}
		sql.append(" ");
		if(sort.equalsIgnoreCase("lastest")) {
			sql.append("ORDER BY NGAYBATDAU DESC;");
		} else {
			sql.append("ORDER BY NGAYBATDAU ASC;");
		}
		
		List<EventForManage> list = jdbcTemplate.query(sql.toString(), new EventManageMapper());
		return list;
	}
	
	@Override
	public ResponseList getListEventHomePage(int from, int to, boolean only_active) {
		String countSql = "SELECT COUNT(1) FROM VIEWCHUONGTRINHTRANGCHINHALL";
		String sql = "SELECT * FROM VIEWCHUONGTRINHTRANGCHINHALL WHERE ROW >= ? AND ROW < ?";
		if(only_active) {
			sql = "SELECT * FROM VIEWCHUONGTRINHTRANGCHINH WHERE ROW >= ? AND ROW < ?";
			countSql = "SELECT COUNT(1) FROM VIEWCHUONGTRINHTRANGCHINH";
		}
		List<EventHomePage> resultList = new ArrayList<>();
		List<EventForManage> list = jdbcTemplate.query(sql, new EventManageMapper(), from, to);
		for(EventForManage eventbase : list) {
			EventHomePage event = new EventHomePage(eventbase);
			int id = event.getId();
			event.setImage(getImageBannerForEvent(id));
			event.setNumDonates(getNumberDonateActionPerEvent(id));
			resultList.add(event);
		}
		
		int totalToGet = jdbcTemplate.queryForObject(countSql, Integer.class);
		
		return new ResponseList(totalToGet, resultList);
	}

	@Override
	public ResponseList getListEventSearchPage(int from, int to, boolean only_active, String searchValue) {
		String countSql = "SELECT COUNT(1) FROM CHUONGTRINH WHERE TIEUDECT LIKE ?";
		String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY MACT DESC) AS ROW, MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC,"
				+ " MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH WHERE TIEUDECT LIKE ?) AS MYTABLE WHERE ROW >= ? AND ROW < ?";
		if(only_active) {
			sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY MACT DESC) AS ROW, MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC,"
					+ " MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH WHERE TRANGTHAICT = TRUE AND TIEUDECT LIKE ?) AS MYTABLE WHERE ROW >= ? AND ROW < ?";
			countSql = "SELECT COUNT(1) FROM CHUONGTRINH WHERE TIEUDECT LIKE ? AND TRANGTHAICT = TRUE";
		}
		searchValue = "%" + searchValue + "%";
		List<EventHomePage> resultList = new ArrayList<>();
		List<EventForManage> list = jdbcTemplate.query(sql, new EventManageMapper(), searchValue, from, to);
		for(EventForManage eventbase : list) {
			EventHomePage event = new EventHomePage(eventbase);
			int id = event.getId();
			event.setImage(getImageBannerForEvent(id));
			event.setNumDonates(getNumberDonateActionPerEvent(id));
			resultList.add(event);
		}
		
		int totalToGet = jdbcTemplate.queryForObject(countSql, Integer.class, searchValue);
		
		return new ResponseList(totalToGet, resultList);
	}

	@Override
	public VaTiImage getImageBannerForEvent(int eventId) {
		String sql = "SELECT HINHANH, TYPE FROM HINHANHCT WHERE MACT = ? ORDER BY MAANH ASC LIMIT 1";
		VaTiImage image = null;
		try {
			image = jdbcTemplate.queryForObject(sql, new VaTiImageForEventMapper(), eventId);
		} catch (EmptyResultDataAccessException e) {
			image = new VaTiImage();
		}
		
		return image;
	}

	@Override
	public int getNumberDonateActionPerEvent(int eventId) {
		String sql = "SELECT COUNT(1) AS MYCOUNT FROM DONGGOP WHERE MACT = ?";
		
		return jdbcTemplate.queryForObject(sql, Integer.class, eventId);
	}

	@Override
	public List<EventForManage> getListEventManage(String search) {
		String sql = "SELECT MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH WHERE TIEUDECT LIKE N'%" + search + "%';";
		
		List<EventForManage> list = jdbcTemplate.query(sql, new EventManageMapper());
		return list;
	}

	@Override
	public int setEventStatus(int id, boolean status) {
		String sql = "";
		if(status) {
			sql = "UPDATE CHUONGTRINH SET TRANGTHAICT = TRUE WHERE MACT = ?";
		} else {
			sql = "UPDATE CHUONGTRINH SET TRANGTHAICT = FALSE WHERE MACT = ?";
		}
		
		int result = jdbcTemplate.update(sql, id);
		return result;
	}

	@Override
	public int deleteEvent(int id) {
		String sql = "DELETE FROM CHUONGTRINH WHERE MACT = ?;";
		
		int result = jdbcTemplate.update(sql, id);
		return result;
	}

	@Override
	public EventForEdit getDetailEvent(int id) {
		String sql = "SELECT * FROM CHUONGTRINH WHERE MACT = " + id;
		
		EventForEdit event = jdbcTemplate.queryForObject(sql, new EventEditMapper());
		
		return event;
	}

	@Override
	public List<ImgEvent> getImageEvent(int eventId) {
		String sql = "SELECT * FROM HINHANHCT WHERE MACT = ?";
		
		List<ImgEvent> list = jdbcTemplate.query(sql, new ImgEventMapper(), eventId);
		
		return list;
	}

	@Override
	public ImgEvent getImageEventById(int imageId) {
		return jdbcTemplate.queryForObject("SELECT * FROM HINHANHCT WHERE MAANH = ?",
				new ImgEventMapper(), imageId);
	}

	@Override
	public int deleteImageEvent(int id) {
		String sql = "DELETE FROM HINHANHCT WHERE MAANH = ?";
		
		int result = jdbcTemplate.update(sql, id);
		
		return result;
	}

	@Override
	public int updateDetailEvent(EventForEdit event) {
		int eventId = event.getId();
		String titleUpdate = "UPDATE CHUONGTRINH SET TIEUDECT = ? WHERE MACT=" + eventId;
		String startDateUpdate = "UPDATE CHUONGTRINH SET NGAYBATDAU = ? WHERE MACT=" + eventId;
		String endDateUpdate = "UPDATE CHUONGTRINH SET NGAYKETTHUC = ? WHERE MACT=" + eventId;
		
		String purposeUpdate = "UPDATE CHUONGTRINH SET MUCTIEU = ? WHERE MACT=" + eventId;
		String statusUpdate = "UPDATE CHUONGTRINH SET TRANGTHAICT = ? WHERE MACT=" + eventId;
		String detailUpdate = "UPDATE CHUONGTRINH SET NOIDUNGCT = ? WHERE MACT=" + eventId;
		int result = 0;
		result += jdbcTemplate.update(titleUpdate, event.getTitle().trim());
		result += jdbcTemplate.update(startDateUpdate, event.getStartDate());
		result += jdbcTemplate.update(endDateUpdate, event.getEndDate());
		result += jdbcTemplate.update(purposeUpdate, event.getPurpose());
		result += jdbcTemplate.update(statusUpdate, event.getStatus());
		result += jdbcTemplate.update(detailUpdate, event.getDetail());
		
		if(result == 6) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public int insertDetailEvent(EventForEdit event) {
		String sql = "INSERT INTO CHUONGTRINH (TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, TRANGTHAICT, NOIDUNGCT) VALUES (?,?,?,?,?,?) RETURNING MACT;";
		
		return jdbcTemplate.update(sql, event.getTitle().trim(), event.getStartDate(), event.getEndDate(), event.getPurpose(), 1, event.getDetail().trim());
	}

	@Override
	public int createAndGetIdANullEvent() {
		return jdbcTemplate.queryForObject("INSERT INTO CHUONGTRINH (TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, TRANGTHAICT, NOIDUNGCT)"
				+ " VALUES ('',CURRENT_DATE,null,0,false,'') RETURNING MACT;", Integer.class);
	}

	@Override
	public int saveAndGetIdImageEvent(ImgEvent img) {
		return jdbcTemplate.queryForObject("INSERT INTO HINHANHCT (MACT, HINHANH, TYPE) VALUES (?,?,?) RETURNING MAANH;",
				Integer.class, img.getEventId(), img.getArr(), img.getType());
	}

	@Override
	public int saveImageEvent(List<ImgEvent> imgs) {
		int result = 0;
		String sql = "INSERT INTO HINHANHCT(MACT, HINHANH, TYPE) VALUES (?,?,?);";
		for(ImgEvent image : imgs) {
			int eventId = image.getId();
			byte[] arr = image.getArray();
			String type = image.getType().trim();
			
			if(type.substring(0, 5).equals("image")) {
				result += jdbcTemplate.update(sql, eventId, arr, type);
			}
		}
		return result;
	}

	@Override
	public int deleteAllImageEvent(int eventId) {
		String sql = "DELETE FROM HINHANHCT WHERE MACT = ?";
		
		int result = jdbcTemplate.update(sql, eventId);
		
		return result;
	}

	@Override
	public long getDonationCount(int id) {
		String sql = "SELECT COUNT(1) FROM DONGGOP WHERE MACT=" + id;
		
		long result = jdbcTemplate.queryForObject(sql, Long.class);
		return result;
	}

	@Override
	public List<Donate> getTopDonate(int eventId) {
		String sql = "SELECT CONCAT(T.TEN,' ',T.HO) AS TENTK,"
				+ " D.MAHD AS MAHD, C.TIEUDECT AS TIEUDECT,"
				+ " D.SOTIEN AS SOTIEN, D.NGAY AS NGAY,"
				+ " D.TINNHAN AS TINNHAN, D.ANDANH AS ANDANH"
				+ " FROM DONGGOP AS D, TAIKHOAN AS T,"
				+ " CHUONGTRINH AS C WHERE D.MATK = T.MATK"
				+ " AND D.MACT = C.MACT AND C.MACT=" + eventId
				+ " ORDER BY D.MAHD DESC LIMIT 10";
		
		List<Donate> list = jdbcTemplate.query(sql, new DonateMapper());
		
		return list;
	}

	@Override
	public List<Donate> getTopRichDonate(int eventId) {
		String sql = "SELECT CONCAT(T.TEN,' ',T.HO) AS TENTK,"
				+ " D.MAHD AS MAHD, C.TIEUDECT AS TIEUDECT,"
				+ " D.SOTIEN AS SOTIEN, D.NGAY AS NGAY,"
				+ " D.TINNHAN AS TINNHAN, D.ANDANH AS ANDANH"
				+ " FROM DONGGOP AS D, TAIKHOAN AS T,"
				+ " CHUONGTRINH AS C WHERE D.MATK = T.MATK"
				+ " AND D.MACT = C.MACT AND C.MACT=" + eventId
				+ " ORDER BY D.SOTIEN DESC LIMIT 10";
		List<Donate> list = jdbcTemplate.query(sql, new DonateMapper());
		
		return list;
	}

	@Override
	public int runningEvent() {
		String sql = "SELECT COUNT(1) AS MYCOUNT FROM CHUONGTRINH WHERE TRANGTHAICT = TRUE AND (NGAYKETTHUC >= CURRENT_DATE OR NGAYKETTHUC IS NULL);";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public int stoppingEvent() {
		String sql = "SELECT COUNT(1) AS MYCOUNT FROM CHUONGTRINH WHERE TRANGTHAICT = FALSE AND (NGAYKETTHUC >= CURRENT_DATE OR NGAYKETTHUC IS NULL);";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public int endingEvent() {
		String sql = "SELECT COUNT(1) AS MYCOUNT FROM CHUONGTRINH WHERE NGAYKETTHUC < CURRENT_DATE;";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public int noTimeEndEvent() {
		String sql = "SELECT COUNT(1) AS MYCOUNT FROM CHUONGTRINH WHERE NGAYKETTHUC IS NULL";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public List<BigDecimal> getDataForLineChart(int eventId, int length) {
		String sql = "SELECT getMoney(?,?)";
		return jdbcTemplate.queryForList(sql, BigDecimal.class, length, eventId);
	}

	@Override
	public EventForManage eventHasHighestPurpose() {
		String sql = "SELECT MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH ORDER BY MUCTIEU DESC LIMIT 1";
		EventForManage event = null;
		try {
			event = jdbcTemplate.queryForObject(sql, new EventManageMapper());
		} catch (EmptyResultDataAccessException e) {
			event = new EventForManage(0, "Empty", null, null, null, null, false);
		}
		return event;
	}

	@Override
	public EventForManage eventHasShortestTimeDeployment() {
		String sql = "SELECT MACT, TIEUDECT, NGAYBATDAU, NGAYKETTHUC, MUCTIEU, UNGHO, TRANGTHAICT FROM CHUONGTRINH WHERE NGAYKETTHUC IS NOT NULL ORDER BY AGE(NGAYKETTHUC,NGAYBATDAU) ASC LIMIT 1";
		EventForManage event = null;
		try {
			event = jdbcTemplate.queryForObject(sql, new EventManageMapper());
		} catch (EmptyResultDataAccessException e) {
			event = new EventForManage(0, "Empty", null, null, null, null, false);
		}
		return event;
	}

	@Override
	public String eventTile(int eventId) {
		String sql = "SELECT TIEUDECT FROM CHUONGTRINH WHERE MACT = ?";
		return jdbcTemplate.queryForObject(sql, String.class, eventId);
	}

}
