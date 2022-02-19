package com.tinhnv.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import com.tinhnv.model.donate.Donate;
import com.tinhnv.model.donate.DonateForPaypal;
import com.tinhnv.model.donate.TinyInfoDonation;
import com.tinhnv.model.event.EventFullInfo;
import com.tinhnv.model.others.CurrencyExchangeRate;
import com.tinhnv.service.DonationService;
import com.tinhnv.service.EventService;
import com.tinhnv.service.PaypalService;
import com.tinhnv.setting.APIKey;
import com.tinhnv.setting.PaypalInfoConfig;

@Controller
@RequestMapping("/quyen-gop")
public class DonationController {

	@Autowired
	DonationService donation;
	@Autowired
	EventService event;
	@Autowired
	PaypalService paypal;
	@Autowired
	PaypalInfoConfig paypalInfo;
	
	private Gson gson = new Gson();
	
	@RequestMapping(value={"/dong-gop-lon-nhat"}, method=RequestMethod.POST)
	public void maxDonationContributed(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		TinyInfoDonation result = donation.getTinyInfoDonationWithHighestContributed();
		String json = gson.toJson(result);
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/danh-sach-dong-gop-moi-nhat"}, method=RequestMethod.POST)
	public void lastestDonate(@RequestParam(name="topSize") int size,HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		List<Donate> result = donation.lastestDonateForDashboard(size);
		String json = gson.toJson(result);
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/noi-dung-chuong-trinh"}, method=RequestMethod.GET)
	public ModelAndView userDetailEvent(@RequestParam(name="eventId") int eventId, HttpServletRequest request) {
		ModelAndView view  = new ModelAndView("userDetailEvent");
		EventFullInfo result = event.getDonation(eventId);
		view.addObject("donation", result);
		return view;
	}
	@RequestMapping(value={"/buoc-1"}, method=RequestMethod.POST)
	public String userDonateAction1(HttpServletRequest request) {
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		request.setAttribute("eventId", eventId);
		request.setAttribute("eventTitle", donation.getDonationTitle(eventId));
		return "donate";
	}
	
	@RequestMapping("/quyen-gop-thanh-cong")
	public String resultDonatePage() {
		return "afterDonate";
	}
	
	@RequestMapping("/redirect-after-donate")
	public RedirectView redirectAfterDonate(@RequestParam("eventId") int eventId) {
		RedirectView view = new RedirectView("https://love-and-care.herokuapp.com/quyen-gop/quyen-gop-thanh-cong");
		view.addStaticAttribute("eventId", eventId);
		view.addStaticAttribute("eventTitle", donation.getDonationTitle(eventId));
		return view;
	}
	
	@RequestMapping(value={"/buoc-2"}, method=RequestMethod.POST)
	public String userDonateAction2(HttpServletRequest request) {
		String donateAction = request.getParameter("donateAction");
		if(donateAction.equalsIgnoreCase("test")) {
			if(donation.createADonateAction(request)) {
				return "redirect:/quyen-gop/redirect-after-donate?eventId=" + request.getParameter("eventId");
			} else {
				request.setAttribute("message", "Xảy ra lỗi khi kết nối cơ sở dũ liệu");
				return "error";
			}
		} else if(donateAction.equalsIgnoreCase("paypal")) {
			DonateForPaypal donate = donation.createAPaypalDonateAction(request);
			String title = event.getEventTitle(donate.getEventId());
			try {
				Payment payment = paypal.createPayment("USD", "paypal", "sale", paypalInfo.canceUrl, paypalInfo.resultUrl, donate, title);
				for(Links link : payment.getLinks()) {
					if(link.getRel().equals("approval_url")) {
						return "redirect:" + link.getHref();
					}
				}
			} catch (PayPalRESTException e) {
				e.printStackTrace();
				request.setAttribute("message", "Lỗi kết nối với paypal");
			}
			return "error";
		} else {
			request.setAttribute("message", "Không rõ tác vụ (phương thúc) quyên góp được yêu cầu");
			return "error";
		}
	}
	
	@RequestMapping(value={"/ti-gia-tien-te"})
	public void currencyExchangeRate(HttpServletRequest request, @RequestParam("action") String action,
			HttpServletResponse response) throws IOException {
		if(action.equalsIgnoreCase("set")) {
			donation.setEchangeRate(request);
		}
		if(action.equalsIgnoreCase("get")) {
			Map<String, String> result = donation.getExchageRate(request);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toJson(result));
		}
	}
	
	@RequestMapping(value={"/lay-khoa-api-ti-gia-tien-te"}, method=RequestMethod.GET)
	public void getKeyCurrencyAPIExchangeRate(HttpServletResponse response) throws IOException {
		APIKey key = new APIKey();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(gson.toJson(key.exchangeRateKey));
	}
	
	@RequestMapping(value={"/quyen-gop-thanh-cong-qua-paypal"}, method=RequestMethod.GET)
	public String paypalDonateSuccess(HttpServletRequest request) {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		Payment payment;
		try {
			payment = paypal.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")) {
				List<Transaction> transactions = payment.getTransactions();
				Transaction transaction = transactions.get(0);
				int eventId = donation.paypalDonateSuccess(transaction);
				if(eventId != 0) {
					return "redirect:/quyen-gop/redirect-after-donate?eventId=" + eventId;
				} else {
					request.setAttribute("message", "Quá trình xử lý transaction gặp lỗi");
					return "error";
				}
			}
		} catch (PayPalRESTException e) {
			request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		return "error";
	}

	@RequestMapping(value={"/con-cach-nay-thoi"}, method=RequestMethod.GET)
	public void getCurrencyRateFinalAction(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException, IOException, InterruptedException {
		CurrencyExchangeRate result = donation.getCurrencyExchangeRatesByServer(request);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(gson.toJson(result));
	}
}
