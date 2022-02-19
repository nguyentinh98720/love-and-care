package com.tinhnv.setting;

import org.springframework.stereotype.Component;

@Component
public class MailConfig {
    public final String HostSend = "smtp.gmail.com";
    public final int SSL_PORT = 465;
    public final int TSL_PORT = 587;
    
    public final String email = "";
    public final String password = "";
}
