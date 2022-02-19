package com.tinhnv.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tinhnv.context.ConnectDatabase;
import com.tinhnv.dao.DonationDao;
import com.tinhnv.model.donate.Donate;
import com.tinhnv.model.donate.TinyInfoDonation;
import com.tinhnv.model.donate.donateMapper.DonateHistoryMapper;
import com.tinhnv.model.donate.donateMapper.DonateMapperForDashboard;
import com.tinhnv.model.donate.donateMapper.DonateTimelineMapper;
import com.tinhnv.model.donate.donateMapper.TinyInfoDonationMapper;

@Repository
public class DonationDaoImpl implements DonationDao {

	private JdbcTemplate jdbcTemplate = new ConnectDatabase().getConnection();
	
	@Override
	public int getNumDonatePerUser(int userId) {
		String sql = "SELECT COUNT(1) AS COUNT FROM DONGGOP WHERE MATK = ?";
		
		return jdbcTemplate.queryForObject(sql, Integer.class, userId);
	}

	@Override
	public BigDecimal getTotalMoneyPerUser(int userId) {
		String sql = "SELECT SUM(SOTIEN) AS SUM FROM DONGGOP WHERE MATK = ?";
		
		return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
	}

	@Override
	public int getNumEventJoinedPerUser(int userId) {
		String sql = "SELECT COUNT(DISTINCT MACT) AS COUNT FROM DONGGOP WHERE MATK = ?";
		
		return jdbcTemplate.queryForObject(sql, Integer.class, userId);
	}

	@Override
	public TinyInfoDonation getTinyInfoDonationHasHighestContributed() {
		String sql = "SELECT MATK, SOTIEN FROM DONGGOP ORDER BY SOTIEN DESC LIMIT 1";
		TinyInfoDonation dona = null;
		try {
			dona = jdbcTemplate.queryForObject(sql, new TinyInfoDonationMapper());
		} catch (EmptyResultDataAccessException e) {
			dona = new TinyInfoDonation(0, new BigDecimal("0"));
		}
		return dona;
	}

	@Override
	public List<Donate> lastestDonationForDashboard(int topSize) {
		String sql = "SELECT CONCAT(T.TEN,' ',T.HO) AS TENTK, C.TIEUDECT AS TIEUDECT, D.NGAY AS NGAY, "
				+ "D.TINNHAN AS TINNHAN, D.SOTIEN AS SOTIEN, D.ANDANH AS ANDANH, C.MACT AS MACT "
				+ "FROM DONGGOP AS D, TAIKHOAN AS T, CHUONGTRINH AS C WHERE D.MATK = T.MATK AND D.MACT = C.MACT "
				+ "ORDER BY D.MAHD DESC, D.NGAY DESC LIMIT " + topSize;
		return jdbcTemplate.query(sql, new DonateMapperForDashboard());
	}

	@Override
	public boolean createADonateAction(Donate donate) {
		int eventId = donate.getEventId();
		int accountId = donate.getAccountId();
		BigDecimal money = donate.getMoney();
		Date date = donate.getDate();
		String message = donate.getMessage();
		boolean secret = donate.isSecret();
		
		String sql = "INSERT INTO DONGGOP (MACT, MATK, SOTIEN, NGAY, TINNHAN, ANDANH) VALUES (?,?,?,?,?,?)";
		int result = jdbcTemplate.update(sql, eventId, accountId, money, date, message, secret);
		if(result > 0) return true;
		return false;
	}

	@Override
	public String getDonationTitle(int eventId) {
		String sql = "SELECT TIEUDECT FROM CHUONGTRINH WHERE MACT = ?";
		
		return jdbcTemplate.queryForObject(sql, String.class, eventId);
	}

	@Override
	public List<Donate> getDonateHistory(int from, int to, int accountId) {
		String sql = "select mahd, mact, tieudect, sotien, ngay, tinnhan, andanh from (\r\n"
				+ "	select d.mahd as mahd, c.mact as mact, c.tieudect as tieudect, d.sotien as sotien,\r\n"
				+ "	d.ngay as ngay, d.tinnhan as tinnhan, d.andanh as andanh,\r\n"
				+ "	row_number() over(order by d.mahd desc) as row\r\n"
				+ "	from donggop as d, chuongtrinh as c\r\n"
				+ "	where d.mact = c.mact and d.matk = " + accountId + "\r\n"
				+ "	) as result where result.row >= " + from + " and result.row < " + to + ";\r\n";
		
		return jdbcTemplate.query(sql, new DonateHistoryMapper());
	}

	@Override
	public int getTotalNumDonateAction() {
		String sql = "SELECT COUNT(1) FROM DONGGOP";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public int getNumDonateBySearchAccountName(String searchValue) {
		searchValue = "%" + searchValue + "%";
		String sql = "SELECT COUNT(1) FROM DONGGOP AS D JOIN TAIKHOAN AS T ON D.MATK = T.MATK WHERE CONCAT(T.TEN,' ', T.HO) LIKE ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, searchValue);
	}

	@Override
	public int getNumDonatePerEventAndSearchAccountName(int eventId, String searchValue) {
		searchValue = "%" + searchValue + "%";
		String sql = "SELECT COUNT(1) FROM DONGGOP AS D JOIN TAIKHOAN AS T ON D.MATK = T.MATK WHERE D.MACT = ? AND CONCAT(T.TEN,' ', T.HO) LIKE ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, eventId, searchValue);
	}

	@Override
	public List<Donate> getDonateTimeline(int from, int to) {
		String sql = "SELECT MAHD, MACT, HOTEN, SOTIEN, NGAY, TINNHAN, ANDANH FROM (\r\n"
				+ " SELECT D.MAHD AS MAHD,D.MACT AS MACT, CONCAT(T.TEN,' ',T.HO) AS HOTEN, D.SOTIEN AS SOTIEN,\r\n"
				+ "	D.NGAY AS NGAY, D.TINNHAN AS TINNHAN, D.ANDANH AS ANDANH,\r\n"
				+ "	ROW_NUMBER() OVER (ORDER BY D.MAHD DESC) AS ROW\r\n"
				+ "	FROM DONGGOP AS D, TAIKHOAN AS T\r\n"
				+ "	WHERE D.MATK = T.MATK ) AS RESULT\r\n"
				+ "	WHERE RESULT.ROW >= " + from + " AND RESULT.ROW < " + to;
		return jdbcTemplate.query(sql, new DonateTimelineMapper());
	}

	@Override
	public List<Donate> getDonateTimelineBySearchAccountName(int from, int to, String searchValue) {
		String sql = "SELECT MAHD, MACT, HOTEN, SOTIEN, NGAY, TINNHAN, ANDANH FROM (\r\n"
				+ " SELECT D.MAHD AS MAHD,D.MACT AS MACT, CONCAT(T.TEN,' ',T.HO) AS HOTEN, D.SOTIEN AS SOTIEN,\r\n"
				+ "	D.NGAY AS NGAY, D.TINNHAN AS TINNHAN, D.ANDANH AS ANDANH,\r\n"
				+ "	ROW_NUMBER() OVER (ORDER BY D.MAHD DESC) AS ROW\r\n"
				+ "	FROM DONGGOP AS D, TAIKHOAN AS T\r\n"
				+ "	WHERE D.MATK = T.MATK AND CONCAT(T.TEN,' ',T.HO) LIKE ? ) AS RESULT\r\n"
				+ "	WHERE RESULT.ROW >= " + from + " AND RESULT.ROW < " + to;
		searchValue = "%" + searchValue + "%";
		return jdbcTemplate.query(sql, new DonateTimelineMapper(), searchValue);
	}

	@Override
	public List<Donate> getDonateTimeLinePerEvent(int from, int to, int eventId) {
		String sql = "SELECT MAHD, MACT, HOTEN, SOTIEN, NGAY, TINNHAN, ANDANH FROM (\r\n"
				+ " SELECT D.MAHD AS MAHD,D.MACT AS MACT, CONCAT(T.TEN,' ',T.HO) AS HOTEN, D.SOTIEN AS SOTIEN,\r\n"
				+ "	D.NGAY AS NGAY, D.TINNHAN AS TINNHAN, D.ANDANH AS ANDANH,\r\n"
				+ "	ROW_NUMBER() OVER (ORDER BY D.MAHD DESC) AS ROW\r\n"
				+ "	FROM DONGGOP AS D JOIN TAIKHOAN AS T\r\n"
				+ "	ON D.MATK = T.MATK WHERE D.MACT = " + eventId + " ) AS RESULT\r\n"
				+ "	WHERE RESULT.ROW >= " + from + " AND RESULT.ROW < " + to;
		return jdbcTemplate.query(sql, new DonateTimelineMapper());
	}

	@Override
	public List<Donate> getDonateTimelinePerEventBySearchAccountName(int from, int to, int eventId,
			String searchValue) {
		String sql = "SELECT MAHD, MACT, HOTEN, SOTIEN, NGAY, TINNHAN, ANDANH FROM (\r\n"
				+ " SELECT D.MAHD AS MAHD, D.MACT AS MACT, CONCAT(T.TEN,' ',T.HO) AS HOTEN, D.SOTIEN AS SOTIEN,\r\n"
				+ "	D.NGAY AS NGAY, D.TINNHAN AS TINNHAN, D.ANDANH AS ANDANH,\r\n"
				+ "	ROW_NUMBER() OVER (ORDER BY D.MAHD DESC) AS ROW\r\n"
				+ "	FROM DONGGOP AS D, TAIKHOAN AS T\r\n"
				+ "	WHERE D.MATK = T.MATK AND D.MACT = " + eventId + " AND CONCAT(T.TEN,' ',T.HO) LIKE ? ) AS RESULT\r\n"
				+ "	WHERE RESULT.ROW >= " + from + " AND RESULT.ROW < " + to;
		searchValue = "%" + searchValue + "%";
		return jdbcTemplate.query(sql, new DonateTimelineMapper(), searchValue);
	}
}