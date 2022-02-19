package com.tinhnv.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.paypal.api.payments.Transaction;
import com.tinhnv.model.donate.Donate;
import com.tinhnv.model.donate.DonateForPaypal;
import com.tinhnv.model.donate.TinyInfoDonation;
import com.tinhnv.model.others.CurrencyExchangeRate;
import com.tinhnv.model.others.DonateStatistics;

public interface DonationService {

	TinyInfoDonation getTinyInfoDonationWithHighestContributed();
	
	List<Donate> lastestDonateForDashboard(int topSize);
	
	boolean createADonateAction(HttpServletRequest request);
	
	String getDonationTitle(int eventId);
	
	DonateStatistics getDonateStatistics(int accountId);
	
	List<Donate> getDonateHistory(int from, int to, int accountId);
	
	int getTotalDonateBy(int eventId, boolean isSearching, String searchValue);
	
	List<Donate> getListDonateForTimeline(HttpServletRequest request);
	
	void setEchangeRate(HttpServletRequest request);
	Map<String, String> getExchageRate(HttpServletRequest request);
	
	DonateForPaypal createAPaypalDonateAction(HttpServletRequest request);
	
	int paypalDonateSuccess(Transaction transaction);
	
	CurrencyExchangeRate getCurrencyExchangeRatesByServer(HttpServletRequest request) throws URISyntaxException, IOException, InterruptedException;

}
