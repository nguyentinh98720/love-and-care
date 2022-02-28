package com.tinhnv.dao.impl;

import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tinhnv.context.ConnectDatabase;
import com.tinhnv.dao.AccountDao;
import com.tinhnv.model.account.AccountForManage;
import com.tinhnv.model.account.accountMapper.AccountManageMapper;
import com.tinhnv.model.account.accountMapper.AccountMapperNowImg;
import com.tinhnv.model.others.ImgSource;
import com.tinhnv.model.others.rowMapper.ImgSourceMapper;


@Repository
public class AccountDaoImpl implements AccountDao {
	
	private ConnectDatabase connect = new ConnectDatabase();
	private JdbcTemplate jdbcTemplate = connect.getConnection();

/*
 * Chỉnh sửa ngày 1/2/2022 - Bỏ phần hình ảnh và định dạng ảnh do phần frontend không yêu cầu ====> ROWMAPPER
 * AccountManageMapper ==> AccountMapperNoImg
 * IMAGE and IMGTYPE column
 * x3 method
 */	
	@Override
	public List<AccountForManage> getListAccountForManage() {
		String sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN";
		
		List<AccountForManage> list = jdbcTemplate.query(sql, new AccountMapperNowImg());
		return list;
	}

	@Override
	public List<AccountForManage> getListAccountForManageByFilter(String role, String status) {
		String sql = "";
		if(role.equalsIgnoreCase("all")) {
			if(status.equalsIgnoreCase("all")) {
				return getListAccountForManage();
			} else if(status.equalsIgnoreCase("active")) {
				sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE TRANGTHAI = TRUE;";
			} else if(status.equalsIgnoreCase("lock")) {
				sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE TRANGTHAI = FALSE;";
			}
		} else if(role.equalsIgnoreCase("user")) {
			if(status.equalsIgnoreCase("all")) {
				sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE ROLE = 'ROLE_USER';";
			} else if(status.equalsIgnoreCase("active")) {
				sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE ROLE = 'ROLE_USER' AND TRANGTHAI = TRUE;";
			} else if(status.equalsIgnoreCase("lock")) {
				sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE ROLE = 'ROLE_USER' AND TRANGTHAI = FALSE;";
			}
		} else if(role.equalsIgnoreCase("admin")) {
			if(status.equalsIgnoreCase("all")) {
				sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE ROLE = 'ROLE_ADMIN';";
			} else if(status.equalsIgnoreCase("active")) {
				sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE ROLE = 'ROLE_ADMIN' AND TRANGTHAI = TRUE;";
			} else if(status.equalsIgnoreCase("lock")) {
				sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE ROLE = 'ROLE_ADMIN' AND TRANGTHAI = FALSE;";
			}
		}
		
		List<AccountForManage> list = jdbcTemplate.query(sql, new AccountMapperNowImg());
		return list;
	}

	@Override
	public List<AccountForManage> getListAccountForManageBySearch(String condition, String search) {
		String sql = "";
		if(condition.equalsIgnoreCase("name")) {
			sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE TEN LIKE N'%" + search + "%' OR HO LIKE N'%" + search + "%'";
		} else if(condition.equalsIgnoreCase("phone")) {
			sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE SODT LIKE N'%" + search + "%'";
		} else if(condition.equalsIgnoreCase("address")) {
			sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, TRANGTHAI FROM TAIKHOAN WHERE DIACHI LIKE N'%" + search + "%'";
		}
		
		List<AccountForManage> list = jdbcTemplate.query(sql, new AccountMapperNowImg());
		return list;
	}

	@Override
	public boolean deleteAnAccount(int id) {
		String sql = "DELETE FROM TAIKHOAN WHERE MATK = " + id;
		
		int result = jdbcTemplate.update(sql);
		if(result > 0) return true;
		return false;
	}

	@Override
	public boolean changeStatusAccount(int id, boolean status) {
		String sql = "";
		if(status) {
			sql = "UPDATE TAIKHOAN SET TRANGTHAI = TRUE WHERE MATK = " + id;
		} else {
			sql = "UPDATE TAIKHOAN SET TRANGTHAI = FALSE WHERE MATK = " + id;
		}
		int result = jdbcTemplate.update(sql);
		if(result > 0) return true;
		return false;
	}

	@Override
	public AccountForManage getAccountForEdit(int id) {
		String sql = "SELECT MATK, EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, IMAGE, IMGTYPE, TRANGTHAI FROM TAIKHOAN WHERE MATK = " + id;
		
		AccountForManage account = jdbcTemplate.queryForObject(sql, new AccountManageMapper());
		return account;
	}

	@Override
	public int updateAccountInformation(AccountForManage account) {
		int result = 0;
		int accountId = account.getId();
		String firstNameUpdate = "UPDATE TAIKHOAN SET TEN = ? WHERE MATK = " + accountId;
		String lastNameUpdate = "UPDATE TAIKHOAN SET HO = ? WHERE MATK = " + accountId;
		String phoneNumberUpdate = "UPDATE TAIKHOAN SET SODT = ? WHERE MATK = " + accountId;
		String addressUpdate = "UPDATE TAIKHOAN SET DIACHI = ? WHERE MATK = " + accountId;
		String statusUpdate = "UPDATE TAIKHOAN SET TRANGTHAI = ? WHERE MATK = " + accountId;
		String roleUpdate = "UPDATE TAIKHOAN SET ROLE = ? WHERE MATK = " + accountId;
		String avatarUpdate = "UPDATE TAIKHOAN SET IMAGE = ? WHERE MATK = " + accountId;
		String typeImgUpdate = "UPDATE TAIKHOAN SET IMGTYPE = ? WHERE MATK = " + accountId;
		
		result += jdbcTemplate.update(firstNameUpdate, account.getFirstName());
		result += jdbcTemplate.update(lastNameUpdate, account.getLastName());
		result += jdbcTemplate.update(phoneNumberUpdate, account.getPhoneNumber());
		result += jdbcTemplate.update(addressUpdate, account.getAddress());
		result += jdbcTemplate.update(statusUpdate, account.isStatus());
		result += jdbcTemplate.update(roleUpdate, account.getRole());
		if(account.getAvatar() != null) {
			result += jdbcTemplate.update(avatarUpdate, account.getAvatar().getArr());
			result += jdbcTemplate.update(typeImgUpdate, account.getAvatar().getType());
		}
		
		return result;
	}

	@Override
	public int updateAccountInformationFromUserProfilePage(AccountForManage account) {
		int result = 0;
		int accountId = account.getId();
		String firstNameUpdate = "UPDATE TAIKHOAN SET TEN = ? WHERE MATK = " + accountId;
		String lastNameUpdate = "UPDATE TAIKHOAN SET HO = ? WHERE MATK = " + accountId;
		String phoneNumberUpdate = "UPDATE TAIKHOAN SET SODT = ? WHERE MATK = " + accountId;
		String addressUpdate = "UPDATE TAIKHOAN SET DIACHI = ? WHERE MATK = " + accountId;
		String avatarUpdate = "UPDATE TAIKHOAN SET IMAGE = ? WHERE MATK = " + accountId;
		String typeImgUpdate = "UPDATE TAIKHOAN SET IMGTYPE = ? WHERE MATK = " + accountId;
		
		result += jdbcTemplate.update(firstNameUpdate, account.getFirstName());
		result += jdbcTemplate.update(lastNameUpdate, account.getLastName());
		result += jdbcTemplate.update(phoneNumberUpdate, account.getPhoneNumber());
		result += jdbcTemplate.update(addressUpdate, account.getAddress());
		if(account.getAvatar() != null) {
			result += jdbcTemplate.update(avatarUpdate, account.getAvatar().getArr());
			result += jdbcTemplate.update(typeImgUpdate, account.getAvatar().getType());
		}
		
		return result;
	}

	@Override
	public boolean deleteAccountAvatar(int id) {
		String sql1 = "UPDATE TAIKHOAN SET IMAGE = NULL WHERE MATK = ?";
		String sql2 = "UPDATE TAIKHOAN SET IMGTYPE = NULL WHERE MATK = ?";
		
		int result = jdbcTemplate.update(sql1, id);
		result += jdbcTemplate.update(sql2, id);
		if(result > 0) return true;
		return false;
	}

	@Override
	public boolean isNewEmail(String email) {
		String sql = "SELECT COUNT(1) FROM TAIKHOAN WHERE EMAIL = ?";
		
		int count = jdbcTemplate.queryForObject(sql, Integer.class, email.toLowerCase());
		if(count == 0) return true;
		return false;
	}

	@Override
	public boolean createNewAccount(AccountForManage account) {
		String sql = "INSERT INTO TAIKHOAN (EMAIL, MATKHAU, KHOAMK, TEN, HO, SODT, DIACHI, ROLE, IMAGE, IMGTYPE, TRANGTHAI) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		
		int result = jdbcTemplate.update(sql, account.getEmail(), account.getPassword(), account.getSaltPass(), account.getFirstName(),
				account.getLastName(), account.getPhoneNumber(), account.getAddress(), account.getRole(), account.getAvatar().getArr(),
				account.getAvatar().getType(), account.isStatus());
		if(result > 0) return true;
		return false;
	}

	@Override
	public boolean updatePassword(int userId, String newPassword, String newSaltPass) {
		String passUpdate = "UPDATE TAIKHOAN SET MATKHAU = ? WHERE MATK = " + userId;
		String saltPassUpdate = "UPDATE TAIKHOAN SET KHOAMK = ? WHERE MATK = " + userId;
		
		int result = 0;
		result += jdbcTemplate.update(passUpdate, newPassword);
		result += jdbcTemplate.update(saltPassUpdate, newSaltPass);
		if(result == 2) return true;
		return false;
	}

	@Override
	public String getAccountRole(int userId) {
		String sql = "SELECT ROLE FROM TAIKHOAN WHERE MATK = ?";
		
		return jdbcTemplate.queryForObject(sql, String.class, userId);
	}

	@Override
	public boolean isVerified(int userId) {
		String sql = "SELECT XACTHUC FROM TAIKHOAN WHERE MATK=?";
		
		return jdbcTemplate.queryForObject(sql, Boolean.class, userId);
	}

	@Override
	public int getAccountId(String email) throws IncorrectResultSizeDataAccessException {
		String sql = "SELECT MATK FROM TAIKHOAN WHERE EMAIL=?";
		
		return jdbcTemplate.queryForObject(sql, Integer.class, email);
	}

	@Override
	public void setAccountVerify(int userId, boolean isVerified) {
		String sql = "UPDATE TAIKHOAN SET XACTHUC = ? WHERE MATK = ?";
		jdbcTemplate.update(sql, isVerified, userId);
	}

	@Override
	public int getRoleAccountCount(String role) {
		String sql  = "SELECT COUNT(1) FROM TAIKHOAN WHERE ROLE = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, role);
	}

	@Override
	public int getStatusAccountCount(boolean status) {
		String sql = "SELECT COUNT(1) FROM TAIKHOAN WHERE TRANGTHAI = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, status);
	}

	@Override
	public ImgSource getAccountAvatar(int accountId) {
		String sql = "SELECT IMAGE, IMGTYPE FROM TAIKHOAN WHERE MATK = ?";
		
		return jdbcTemplate.queryForObject(sql, new ImgSourceMapper(), accountId);
	}
	
}
