package com.tinhnv.service.impl;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Date;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.paypal.api.payments.Transaction;
import com.tinhnv.dao.DonationDao;
import com.tinhnv.dao.EventDao;
import com.tinhnv.model.account.SessionUser;
import com.tinhnv.model.donate.Donate;
import com.tinhnv.model.donate.DonateForPaypal;
import com.tinhnv.model.donate.TinyInfoDonation;
import com.tinhnv.model.others.CurrencyExchangeRate;
import com.tinhnv.model.others.CurrencyRateResponse;
import com.tinhnv.model.others.DonateStatistics;
import com.tinhnv.model.others.Rate;
import com.tinhnv.service.DonationService;
import com.tinhnv.setting.APIKey;

@Service
public class DonationServiceImpl implements DonationService {
	
	@Autowired
	DonationDao donationAction;
	
	@Autowired
	EventDao eventAction;

	@Override
	public TinyInfoDonation getTinyInfoDonationWithHighestContributed() {
		return donationAction.getTinyInfoDonationHasHighestContributed();
	}

	@Override
	public List<Donate> lastestDonateForDashboard(int topSize) {
		return donationAction.lastestDonationForDashboard(topSize);
	}

	@Override
	public boolean createADonateAction(HttpServletRequest request) {
		if(request.getParameter("eventId") == null) return false;
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		BigDecimal money = new BigDecimal(request.getParameter("money"));
		String message = request.getParameter("message");
		boolean secret = false;
		if(request.getParameter("secret") != null && request.getParameter("secret").equalsIgnoreCase("on")) {
			secret = true;
		}
		HttpSession session = request.getSession(true);
		if(session.getAttribute("user") == null) return false;
		SessionUser user = (SessionUser) session.getAttribute("user");
		Donate donate = new Donate(eventId, user.getId(), money, new Date(System.currentTimeMillis()), message, secret);
		
		return donationAction.createADonateAction(donate);
	}

	@Override
	public String getDonationTitle(int eventId) {
		return donationAction.getDonationTitle(eventId);
	}

	@Override
	public DonateStatistics getDonateStatistics(int accountId) {
		DonateStatistics statistics = new DonateStatistics();
		statistics.setNumDonateAction(donationAction.getNumDonatePerUser(accountId));
		statistics.setNumEventJoin(donationAction.getNumEventJoinedPerUser(accountId));
		statistics.setTotalMoneySpent(donationAction.getTotalMoneyPerUser(accountId));
		return statistics;
	}

	@Override
	public List<Donate> getDonateHistory(int from, int to, int accountId) {
		return donationAction.getDonateHistory(from, to, accountId);
	}

	@Override
	public int getTotalDonateBy(int eventId, boolean isSearching, String searchValue) {
		if(eventId == 0) {
			if(isSearching) {
				return donationAction.getNumDonateBySearchAccountName(searchValue);
			} else {
				return donationAction.getTotalNumDonateAction();
			}
		} else {
			if(isSearching) {
				return donationAction.getNumDonatePerEventAndSearchAccountName(eventId, searchValue);
			} else {
				return eventAction.getNumberDonateActionPerEvent(eventId);
			}
		}
	}

	@Override
	public List<Donate> getListDonateForTimeline(HttpServletRequest request) {
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		int from = Integer.parseInt(request.getParameter("from"));
		int to = Integer.parseInt(request.getParameter("to"));
		boolean isSearching = Boolean.valueOf(request.getParameter("isSearching"));
		String searchValue = request.getParameter("searchValue");
		
		if(eventId == 0) {
			if(isSearching) {
				return donationAction.getDonateTimelineBySearchAccountName(from, to, searchValue);
			} else {
				return donationAction.getDonateTimeline(from, to);
			}
		} else {
			if(isSearching) {
				return donationAction.getDonateTimelinePerEventBySearchAccountName(from, to, eventId, searchValue);
			} else {
				return donationAction.getDonateTimeLinePerEvent(from, to, eventId);
			}
		}
	}

	@Override
	public void setEchangeRate(HttpServletRequest request) {
		String rate = request.getParameter("rate");
		String time = request.getParameter("time");
		request.getServletContext().setAttribute("rate", rate);
		request.getServletContext().setAttribute("lastTimeUpdate", time);
	}

	@Override
	public Map<String, String> getExchageRate(HttpServletRequest request) {
		String rate = (String) request.getServletContext().getAttribute("rate");
		String rateTime = (String) request.getServletContext().getAttribute("lastTimeUpdate");
		Map<String, String> map = new HashMap<>();
		map.put("rate", rate);
		map.put("lastTimeUpdate", rateTime);
		return map;
	}

	@Override
	public DonateForPaypal createAPaypalDonateAction(HttpServletRequest request) {
		CurrencyExchangeRate rateCurrency = new CurrencyExchangeRate();
		Object rateObject = request.getServletContext().getAttribute("rate");
		Object rateTimeObject = request.getServletContext().getAttribute("lastTimeUpdate");
		if(rateObject != null && rateTimeObject != null) {
			rateCurrency.setRate((String) rateObject);
			rateCurrency.setLastTimeUpdate((String) rateTimeObject);
		} else {
			return null;
		}
		if(request.getParameter("eventId") == null) return null;
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		BigDecimal money = new BigDecimal(request.getParameter("money"));
		String message = request.getParameter("message");
		boolean secret = false;
		if(request.getParameter("secret") != null && request.getParameter("secret").equalsIgnoreCase("on")) {
			secret = true;
		}
		HttpSession session = request.getSession(true);
		if(session.getAttribute("user") == null) return null;
		SessionUser user = (SessionUser) session.getAttribute("user");
		int accountId = user.getId();
		DonateForPaypal donate = new DonateForPaypal(eventId, accountId, money, message, secret, rateCurrency);
		return donate;
	}

	@Override
	public int paypalDonateSuccess(Transaction transaction) {
		DonateForPaypal donateTrans = new Gson().fromJson(transaction.getCustom(), DonateForPaypal.class);
		CurrencyExchangeRate rateObject = donateTrans.getRate();
		
		int eventId = donateTrans.getEventId();
		int accountId = donateTrans.getAccountId();
		BigDecimal money = donateTrans.getMoney();
		String message = "(" + money.toString() + " USD) " + donateTrans.getMessage();
		boolean secret = donateTrans.isSecret();
		money = money.multiply(new BigDecimal(rateObject.getRate()));
		Donate donate = new Donate(eventId, accountId, money, new Date(System.currentTimeMillis()), message, secret);
		
		if(donationAction.createADonateAction(donate)) {
			return eventId;
		}
		return 0;
	}

	@Override
	public CurrencyExchangeRate getCurrencyExchangeRatesByServer(HttpServletRequest req)
			throws URISyntaxException, IOException, InterruptedException {
		String filePath = req.getServletContext().getRealPath("/myFolder");
		Gson gson = new Gson();
		File currencyInformationFile = new File(filePath,"currency.txt");
		
		if(!currencyInformationFile.getParentFile().exists()) {
			if(!currencyInformationFile.getParentFile().mkdir()) {
				return null;
			}
		}
		if(!currencyInformationFile.exists()) {
			if(!currencyInformationFile.createNewFile()) {
				return null;
			}
		}
		
		APIKey api = new APIKey();
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI(api.fullUrlExchangeRates))
				  .GET()
				  .build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
		CurrencyRateResponse currencyResponse = gson.fromJson(response.body(), CurrencyRateResponse.class);
		Rate rate = currencyResponse.getRates();
		CurrencyExchangeRate result = new CurrencyExchangeRate();
		if(rate != null) {
			result.setLastTimeUpdate(DateFormat.getDateInstance().format(currencyResponse.getDate()));
			result.setRate(rate.getVND().divide(rate.getUSD(), 0, RoundingMode.HALF_UP).toString());
			FileWriter fileWriter = new FileWriter(currencyInformationFile);
			BufferedWriter buffWriter = new BufferedWriter(fileWriter);
			PrintWriter printer = new PrintWriter(buffWriter);
			printer.print(response.body());
			printer.close();
			buffWriter.close();
			fileWriter.close();
		} else {
			FileReader fileReader = new FileReader(currencyInformationFile);
			BufferedReader buffReader = new BufferedReader(fileReader);
			String data = buffReader.readLine();
			buffReader.close();
			fileReader.close();
			if(data == null) {
				return null;
			} else {
				CurrencyRateResponse currencyFile = gson.fromJson(data, CurrencyRateResponse.class);
				Rate rateFile = currencyFile.getRates();
				if(rateFile == null) {
					return null;
				} else {
					result.setLastTimeUpdate(DateFormat.getDateInstance().format(currencyFile.getDate()));
					result.setRate(String.valueOf(rateFile.getVND().divide(rateFile.getUSD(), 0, RoundingMode.HALF_UP).toString()));
				}
			}
		}
		return result;
	}
	
}
