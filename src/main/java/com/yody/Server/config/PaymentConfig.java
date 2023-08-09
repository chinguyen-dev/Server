package com.yody.Server.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Configuration
public class PaymentConfig {
    @Value("${paypal.client.app}")
    private String clientId = "ASJfdMs1OO8f9FPtIjUlBYflzf82-DlUfy-HrfZMXK3jBHCwZoVFjS0jQ3rE12lMbBGFu4qhfmt8TCaW";
    @Value("${paypal.client.secret}")
    private String clientSecret = "EDBr7ZLm4B0pXs7KYctP0Ma8rpmjydKQnZcoyB5N_KOcFzc5Bm8ZDX0qa3Cd1EDoINYx66O_5SmudCdo";
    @Value("${paypal.mode}")
    private String mode = "sandbox";

    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", mode);
        return sdkConfig;
    }

    @Bean
    public OAuthTokenCredential authTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        apiContext.setConfigurationMap(paypalSdkConfig());

        return apiContext;
    }
//    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
////    public static String vnp_ReturnUrl = "http://localhost:4200/thanh-toan";
//    public static String vnp_TmnCode = "GDE4DU1H";
//    public static String vnp_Version = "2.1.0";
//    public static String vnp_Command = "pay";
//    public static String vnp_HashSecret = "SJJYOXKZJODJPCMHIBTKPHXOMSEJLTZG";
//    public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
//
//
//    public static String Sha256(String message) {
//        String digest = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hash = md.digest(message.getBytes("UTF-8"));
//            StringBuilder sb = new StringBuilder(2 * hash.length);
//            for (byte b : hash) {
//                sb.append(String.format("%02x", b & 0xff));
//            }
//            digest = sb.toString();
//        } catch (UnsupportedEncodingException ex) {
//            digest = "";
//        } catch (NoSuchAlgorithmException ex) {
//            digest = "";
//        }
//        return digest;
//    }
//
//    //Util for VNPAY
//    public static String hashAllFields(Map fields) {
//        List fieldNames = new ArrayList(fields.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder sb = new StringBuilder();
//        Iterator itr = fieldNames.iterator();
//        while (itr.hasNext()) {
//            String fieldName = (String) itr.next();
//            String fieldValue = (String) fields.get(fieldName);
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                sb.append(fieldName);
//                sb.append("=");
//                sb.append(fieldValue);
//            }
//            if (itr.hasNext()) {
//                sb.append("&");
//            }
//        }
//        return hmacSHA512(vnp_HashSecret,sb.toString());
//    }
//
//    public static String hmacSHA512(final String key, final String data) {
//        try {
//
//            if (key == null || data == null) {
//                throw new NullPointerException();
//            }
//            final Mac hmac512 = Mac.getInstance("HmacSHA512");
//            byte[] hmacKeyBytes = key.getBytes();
//            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
//            hmac512.init(secretKey);
//            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
//            byte[] result = hmac512.doFinal(dataBytes);
//            StringBuilder sb = new StringBuilder(2 * result.length);
//            for (byte b : result) {
//                sb.append(String.format("%02x", b & 0xff));
//            }
//            return sb.toString();
//
//        } catch (Exception ex) {
//            return "";
//        }
//    }
//
//    public static String getIpAddress(HttpServletRequest request) {
//        String ipAdress;
//        try {
//            ipAdress = request.getHeader("X-FORWARDED-FOR");
//            if (ipAdress == null) {
//                ipAdress = request.getRemoteAddr();
//            }
//        } catch (Exception e) {
//            ipAdress = "Invalid IP:" + e.getMessage();
//        }
//        return ipAdress;
//    }
//
//    public static String getRandomNumber(int len) {
//        Random rnd = new Random();
//        String chars = "0123456789";
//        StringBuilder sb = new StringBuilder(len);
//        for (int i = 0; i < len; i++) {
//            sb.append(chars.charAt(rnd.nextInt(chars.length())));
//        }
//        return sb.toString();
//    }
}