package com.tinhnv.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tinhnv.dao.AccountDao;
import com.tinhnv.dao.DonationDao;
import com.tinhnv.model.account.AccountForIntroduce;
import com.tinhnv.model.account.AccountForManage;
import com.tinhnv.model.account.SessionUser;
import com.tinhnv.model.others.AccountStatistics;
import com.tinhnv.model.others.ImgSource;
import com.tinhnv.model.others.VaTiImage;
import com.tinhnv.response.ConnectEmail;
import com.tinhnv.service.AccountService;
import com.tinhnv.setting.AccountConfig;
import com.tinhnv.validator.AccountValidator;
import com.tinhnv.validator.HashPassword;
import com.tinhnv.validator.PasswordGenerator;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountDao accountAction;
	
	@Autowired
	DonationDao donationAction;

	@Autowired
	HashPassword hashPassword;
	
	@Autowired
	PasswordGenerator passwordGenerator;
	
	@Autowired
	ConnectEmail sendPass;
	
	@Autowired
	AccountConfig accountConfig;
	
	@Autowired
	AccountValidator accountValidator;
	
	@Override
	public List<AccountForManage> getListForManageByFilter(String role, String status) {
		return accountAction.getListAccountForManageByFilter(role, status);
	}

	@Override
	public List<AccountForManage> getListForManageBySearch(String condition, String search) {
		return accountAction.getListAccountForManageBySearch(condition, search);
	}

	@Override
	public boolean deleteAccount(int id) {
		//dont accept to delete admin account
		if(accountAction.getAccountRole(id).equalsIgnoreCase(accountConfig.ADMIN)) {
			return false;
		}
		return accountAction.deleteAnAccount(id);
	}

	@Override
	public boolean changeStatus(int id, boolean status) {
		return accountAction.changeStatusAccount(id, status);
	}

	@Override
	public AccountForManage getAccountForEdit(int id) {
		return accountAction.getAccountForEdit(id);
	}

	@Override
	public boolean updateAccountInformation(HttpServletRequest request, MultipartFile image) throws IOException {
		
		String accountId = request.getParameter("accountId");
		int id = Integer.parseInt(accountId);
		String roleBase = accountAction.getAccountRole(id);
		String role = request.getParameter("role");

		//dont accept to set admin to user
		if(roleBase.equalsIgnoreCase(accountConfig.ADMIN) && !roleBase.equals(role)) {
			return false;
		}
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		boolean status = Boolean.valueOf(request.getParameter("status"));
		
		VaTiImage avatar = null;
		if(image.getContentType().split("/")[0].equals("image")) {
			String typeImg =  image.getContentType();
			byte[] arr = image.getInputStream().readAllBytes();
			avatar = new VaTiImage(arr, typeImg);
		}
		AccountForManage account = new AccountForManage(id, firstName, lastName, phoneNumber,
				address, role, avatar, status);
		int numaffected =  accountAction.updateAccountInformation(account);
		if(numaffected > 0) return true;
		return false;
	}

	@Override
	public boolean updateAccountInformationUserAction(HttpServletRequest request, MultipartFile image)
			throws IOException {
		HttpSession session = request.getSession(true);
		if(session.getAttribute("user") == null) return false;
		
		SessionUser user = (SessionUser) session.getAttribute("user");
		int id = user.getId();
		String role = user.getRole();
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		
		VaTiImage avatar = null;
		if(image.getContentType().split("/")[0].equals("image")) {
			String typeImg =  image.getContentType();
			byte[] arr = image.getInputStream().readAllBytes();
			avatar = new VaTiImage(arr, typeImg);
		}
		AccountForManage account = new AccountForManage(id, firstName, lastName, phoneNumber,
				address, role, avatar, true);
		int numaffected =  accountAction.updateAccountInformation(account);
		if(numaffected > 0) return true;
		return false;
	}

	@Override
	public boolean deleteAccountAvatar(int id) {
		return accountAction.deleteAccountAvatar(id);
	}

	@Override
	public boolean isNewEmail(String email) {
		return accountAction.isNewEmail(email);
	}

	@Override
	public boolean createNewAccount(HttpServletRequest request, MultipartFile image) throws IOException {
		String email = request.getParameter("email");
		
		if(!(accountValidator.checkEmail(email))) return false;
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		boolean status = Boolean.valueOf(request.getParameter("status"));
		String role = request.getParameter("role");

		VaTiImage avatar = new VaTiImage();
		if(image.getContentType().split("/")[0].equals("image")) {
			String typeImg = image.getContentType();
			byte[] arr = image.getInputStream().readAllBytes();
			avatar = new VaTiImage(arr, typeImg);
		}

		String buildedPassword = passwordGenerator.getStrongPassword();
		hashPassword.setPassword(buildedPassword);
		hashPassword.hashing();
		String password = "";
		String saltPass = "";
		try {
			password = hashPassword.getHashEncoded();
			saltPass = hashPassword.getSaltEncoded();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if(password.equals("")) return false;
		
		if(sendPass.sendPassword(email, buildedPassword)) {
			AccountForManage account = new AccountForManage(email, password, firstName, lastName, phoneNumber,
					address, role, avatar, status, saltPass);
			return accountAction.createNewAccount(account);
		};
		
		return false;
	}
	
	@Override
	public boolean resetAccountPassword(int userId, String userEmail) {
		String buildPass = passwordGenerator.getStrongPassword();
		hashPassword.setPassword(buildPass);
		hashPassword.hashing();
		String password = "";
		String saltPass = "";
		try {
			password = hashPassword.getHashEncoded();
			saltPass = hashPassword.getSaltEncoded();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
		if(password.equals("")) return false;
		
		if(sendPass.sendPassword(userEmail, buildPass)) {
			accountAction.setAccountVerify(userId, false);
			return accountAction.updatePassword(userId, password, saltPass);
		}
		return false;
	}

	@Override
	public boolean resetAccountPassword(String userEmail) {
		int userId = 0;
		try {
			userId = accountAction.getAccountId(userEmail);
		} catch(EmptyResultDataAccessException e) {
			return false;
		}
		
		return resetAccountPassword(userId, userEmail);
	}

	@Override
	public boolean createAccountFromRegister(HttpServletRequest request) {
		String email = request.getParameter("email");
		
		if(!(accountValidator.checkEmail(email))) return false;
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		boolean status = true;
		String role = accountConfig.USER;
		
		String buildedPassword = passwordGenerator.getStrongPassword();
		hashPassword.setPassword(buildedPassword);
		hashPassword.hashing();
		String password = "";
		String saltPass = "";
		try {
			password = hashPassword.getHashEncoded();
			saltPass = hashPassword.getSaltEncoded();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if(password.equals("")) return false;
		
		if(sendPass.sendPassword(email, buildedPassword)) {
			AccountForManage account = new AccountForManage(email, password, firstName, lastName, phoneNumber,
					address, role, new VaTiImage(), status, saltPass);
			return accountAction.createNewAccount(account);
		};
		
		return false;
	}
	
	@Override
	public boolean createAccountFromRegister_NotSendEmail(HttpServletRequest request) {
		String email = request.getParameter("email");
		
		if(!(accountValidator.checkEmail(email))) return false;
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		boolean status = true;
		String role = accountConfig.USER;
		
		String buildedPassword = passwordGenerator.getStrongPassword();
		hashPassword.setPassword(buildedPassword);
		hashPassword.hashing();
		String password = "";
		String saltPass = "";
		try {
			password = hashPassword.getHashEncoded();
			saltPass = hashPassword.getSaltEncoded();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if(password.equals("")) return false;
		
		AccountForManage account = new AccountForManage(email, password, firstName, lastName, null,
				null, role, new VaTiImage(), status, saltPass);
		return accountAction.createNewAccount(account);
	}
	
	@Override
	public SessionUser checkAndGetAccount(HttpServletRequest request) {
		SessionUser user = new SessionUser();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		accountValidator = new AccountValidator(email, password);
		
		if(email == null) {
			user.setId(-2);
			return user;
		}
		
		int id = 0;
		try {
			id = accountAction.getAccountId(email);
			if(id == 0) return user;
		} catch (EmptyResultDataAccessException e) {
			user.setId(0);
			return user;
		}
		
		AccountForManage account = accountAction.getAccountForEdit(id);
		
		if(!(accountValidator.checkAccount(account.getPassword(), account.getSaltPass()))) {
			user.setId(-1);
			return user;
		}
		
		user.setVerified(accountAction.isVerified(id));
		user.setActive(account.isStatus());
		user.setId(account.getId());
		user.setFirstName(account.getFirstName());
		user.setLastName(account.getLastName());
		user.setRole(account.getRole());
		VaTiImage avatar = new VaTiImage(account.getAvatar().getArr(), account.getAvatar().getType());
		user.setAvatar(avatar);
		return user;
	}

	@Override
	public SessionUser checkAndGetAccountForGoogleLogin(HttpServletRequest request) {
		SessionUser user = new SessionUser();
		String email = request.getParameter("email");

		int id = 0;
		try {
			id = accountAction.getAccountId(email);
			if(id == 0) return user;
		} catch (EmptyResultDataAccessException e) {
			user.setId(0);
			return user;
		}
		
		AccountForManage account = accountAction.getAccountForEdit(id);
		
		user.setVerified(accountAction.isVerified(id));
		
		user.setActive(account.isStatus());
		user.setId(account.getId());
		user.setFirstName(account.getFirstName());
		user.setLastName(account.getLastName());
		user.setRole(account.getRole());
		VaTiImage avatar = new VaTiImage(account.getAvatar().getArr(), account.getAvatar().getType());
		user.setAvatar(avatar);
		return user;
	}

	@Override
	public String changeAccountPassword(HttpServletRequest request) throws IllegalAccessException {
		String id = request.getParameter("userId");
		int userId = Integer.parseInt(id);
		String oldPass = request.getParameter("oldPass");
		String newPass = request.getParameter("newPass");
		String repeatPass = request.getParameter("repeatPass");
		if(!newPass.equals(repeatPass)) return "Mật khẩu nhập lại không trùng khớp";
		
		HashPassword hashPass = new HashPassword(oldPass);
		
		AccountForManage account = accountAction.getAccountForEdit(userId);
		if(!(hashPass.verify(account.getPassword(), account.getSaltPass()))) {
			return "Mật khẩu cũ không đúng";
		}
		
		hashPass.setPassword(newPass);
		String newHashPass = "";
		String newSaltPass = "";
		
		if(hashPass.hashing()) {
			newHashPass = hashPass.getHashEncoded();
			newSaltPass = hashPass.getSaltEncoded();
		} else {
			return "Lỗi băm mật khẩu";
		}
		
		if(!accountAction.updatePassword(userId, newHashPass, newSaltPass)) {
			return "Lỗi cập nhật cơ sở dữ liệu";
		}
		accountAction.setAccountVerify(userId, true);
		return null;
	}

	@Override
	public AccountForIntroduce getIntroduceAccount(int accountId) {
		AccountForManage account = accountAction.getAccountForEdit(accountId);
		int numEventJoined = donationAction.getNumEventJoinedPerUser(accountId);
		int numDonateActive = donationAction.getNumDonatePerUser(accountId);
		BigDecimal totalMoneySpent = donationAction.getTotalMoneyPerUser(accountId);

		return account.toIntroduce(numEventJoined, numDonateActive, totalMoneySpent);
	}

	@Override
	public AccountStatistics getAccountStatistics() {
		AccountStatistics statistics = new AccountStatistics();
		statistics.setAdminCount(accountAction.getRoleAccountCount(accountConfig.ADMIN));;
		statistics.setUserCount(accountAction.getRoleAccountCount(accountConfig.USER));
		statistics.setLockedCount(accountAction.getStatusAccountCount(false));
		statistics.setTotal();
		return statistics;
	}

	@Override
	public ImgSource getAccountAvatar(int AccountId) {

		return accountAction.getAccountAvatar(AccountId);
	}
}
