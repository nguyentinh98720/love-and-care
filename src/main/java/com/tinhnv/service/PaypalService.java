package com.tinhnv.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.tinhnv.model.donate.DonateForPaypal;

@Service
public class PaypalService {

	@Autowired
	APIContext apiContext;
	
	public Payment createPayment(String currency, String method, String intent,
			String canceUrl, String successUrl, DonateForPaypal donate, String eventTitle) throws PayPalRESTException {
		String json = new Gson().toJson(donate);
		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setTotal(donate.getMoney().toString());
		Transaction transaction = new Transaction();
		transaction.setDescription(eventTitle);
		transaction.setAmount(amount);
		transaction.setNoteToPayee(donate.getMessage());
		transaction.setCustom(json);
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		Payer payer = new Payer();
		payer.setPaymentMethod(method);
		Payment payment = new Payment();
		payment.setIntent(intent);
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(canceUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		apiContext.setMaskRequestId(true);
		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecution);
	}
}
