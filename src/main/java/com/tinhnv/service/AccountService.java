package com.tinhnv.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.tinhnv.model.account.AccountForIntroduce;
import com.tinhnv.model.account.AccountForManage;
import com.tinhnv.model.account.SessionUser;
import com.tinhnv.model.others.AccountStatistics;
import com.tinhnv.model.others.ImgSource;

public interface AccountService {
	
	List<AccountForManage> getListForManageByFilter(String role, String status);
	List<AccountForManage> getListForManageBySearch(String condition, String search);
	
	boolean deleteAccount(int id);
	boolean changeStatus(int id, boolean status);
	
	AccountForManage getAccountForEdit(int id);
	boolean updateAccountInformation(HttpServletRequest request, MultipartFile image) throws IOException;
	boolean updateAccountInformationUserAction(HttpServletRequest request, MultipartFile image) throws IOException;
	boolean deleteAccountAvatar(int id);
	
	boolean isNewEmail(String email);
	boolean createNewAccount(HttpServletRequest request, MultipartFile image) throws IOException;
	
	boolean resetAccountPassword(int userId, String userEmail);
	boolean resetAccountPassword(String userEmail);
	String changeAccountPassword(HttpServletRequest request) throws IllegalAccessException;
	
	boolean createAccountFromRegister(HttpServletRequest request);
	boolean createAccountFromRegister_NotSendEmail(HttpServletRequest request);
	SessionUser checkAndGetAccount(HttpServletRequest request);
	SessionUser checkAndGetAccountForGoogleLogin(HttpServletRequest request);
	
	AccountForIntroduce getIntroduceAccount(int accountId);
	
	AccountStatistics getAccountStatistics();
	ImgSource getAccountAvatar(int AccountId);
}
