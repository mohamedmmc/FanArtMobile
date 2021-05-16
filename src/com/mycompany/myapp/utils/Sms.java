/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.utils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

/**
 *
 * @author splin
 */
public class Sms {
    public static final String ACCOUNT_SID = "ACc7c244aa75cb3f02e899d9d08bedeed7";
    public static final String AUTH_TOKEN = "955bfc6065c326e5f028a94f58fd0f07";
    
    public void otpsend(String num){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator(
                "VAfe30c9adac4272a8cdd00745287f8f13",
                "+216"+num,
                "sms")
            .create();
         System.out.println(verification.getStatus());
    }
     public Boolean otpverify(String num, String mdp){
         if (mdp.length()!=6){
             return false;
         }
         else{
             VerificationCheck verificationCheck = VerificationCheck.creator(
                "VAfe30c9adac4272a8cdd00745287f8f13",
                mdp)
            .setTo("+216"+num).create();
        return verificationCheck.getStatus().equals("approved");
         }
         
         
     }
     public void sendsms(String num,String sms){
         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+216"+num),
                new com.twilio.type.PhoneNumber("+12052863364"),
                sms)
            .create();

        System.out.println(message.getSid());
     }
    
}
