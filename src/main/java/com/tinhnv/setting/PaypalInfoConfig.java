package com.tinhnv.setting;

import org.springframework.stereotype.Component;

@Component
public class PaypalInfoConfig {

	public final String CLIENT_ID = "AVrD0QPmCQXPGn4QH5fTf7bAPNOAWWu1asKI0dRrmllCFxL7BMAvsu5OLgck4xYhcrfKjzmtAcHqvZZ4";
	public final String CLIENT_SECRET = "EKdNGykCgeWH-2MV2ru3lxOjtFSg8ul7-RMqq1-QPI206kX9ACsJBsxupX17UKkGq_BfNt3yUG94qUl2";
	public final String MODE = "sandbox";
	
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
