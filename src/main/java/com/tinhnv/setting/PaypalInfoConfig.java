package com.tinhnv.setting;

import org.springframework.stereotype.Component;

@Component
public class PaypalInfoConfig {

	public final String CLIENT_ID = "";
	public final String CLIENT_SECRET = "";
	public final String MODE = "";
	
	public final String canceUrl = "https://love-and-care.herokuapp.com/greeting/home";
	public final String resultUrl = "https://love-and-care.herokuapp.com/quyen-gop/quyen-gop-thanh-cong-qua-paypal";
	
	public enum PaypalPaymentMethod {
		credit_card, paypal
	}
	
	public enum PaypalPaymentIntent {
		sale, authorize, order
	}
	
//	public APIContext getApiContext() throws PayPalRESTException {
//		Map<String, String> modeConfig = new HashMap<>();
//		modeConfig.put("mode", mode);
//		OAuthTokenCredential token = new OAuthTokenCredential(clientId, clientSecret, modeConfig);
//		APIContext apiContext = new APIContext(token.getAccessToken());
//		apiContext.setConfigurationMap(modeConfig);
//		return apiContext;
//	}
}
