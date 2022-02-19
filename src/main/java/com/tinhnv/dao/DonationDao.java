package com.tinhnv.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tinhnv.model.donate.Donate;
import com.tinhnv.model.donate.TinyInfoDonation;

public interface DonationDao {
	
	int getNumDonatePerUser(int userId);
	BigDecimal getTotalMoneyPerUser(int userId);
	int getNumEventJoinedPerUser(int userId);
	
	TinyInfoDonation getTinyInfoDonationHasHighestContributed();
	
	List<Donate> lastestDonationForDashboard(int topSize);
	
	boolean createADonateAction(Donate donate);
	String getDonationTitle(int eventId);
	
	List<Donate> getDonateHistory(int from, int to, int accountId);
	List<Donate> getDonateTimeline(int from, int to);
	List<Donate> getDonateTimelineBySearchAccountName(int from, int to, String searchValue);
	List<Donate> getDonateTimeLinePerEvent(int from, int to, int eventId);
	List<Donate> getDonateTimelinePerEventBySearchAccountName(int from, int to, int eventId, String searchValue);
	
	int getTotalNumDonateAction();
	int getNumDonateBySearchAccountName(String searchValue);
	int getNumDonatePerEventAndSearchAccountName(int eventId, String searchValue);
}
