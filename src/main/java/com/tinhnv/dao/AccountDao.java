package com.tinhnv.dao;

import java.util.List;

import com.tinhnv.model.account.AccountForManage;
import com.tinhnv.model.others.ImgSource;

public interface AccountDao {
	
	List<AccountForManage> getListAccountForManage();
	List<AccountForManage> getListAccountForManageByFilter(String role, String status);
	List<AccountForManage> getListAccountForManageBySearch(String condition, String search);
	
	boolean deleteAnAccount(int id);
	boolean changeStatusAccount(int id, boolean status);
	
	AccountForManage getAccountForEdit(int id);
	int updateAccountInformation(AccountForManage account);
	int updateAccountInformationFromUserProfilePage(AccountForManage account);
	boolean deleteAccountAvatar(int id);
	
	boolean isNewEmail(String email);
	boolean createNewAccount(AccountForManage account);

	boolean updatePassword(int userId, String newPassword, String newSaltPass);
	int getAccountId(String email);
	String getAccountRole(int userId);
	boolean isVerified(int userId);
	void setAccountVerify(int userId, boolean isVerified);
	
	int getRoleAccountCount(String role);
	int getStatusAccountCount(boolean status);
	
	ImgSource getAccountAvatar(int accountId);
}
