package com.nieyue.ipa;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;
@Component
public class IPABusiness {
	    //购买凭证验证地址  
	    private static final String certificateUrl = "https://buy.itunes.apple.com/verifyReceipt";  
	      
	    //测试的购买凭证验证地址   
	    private static final String certificateUrlTest = "https://sandbox.itunes.apple.com/verifyReceipt";  
	      
	    /** 
	     * 重写X509TrustManager 
	     */  
	    private static TrustManager myX509TrustManager = new X509TrustManager() {  
	          
	        @Override  
	        public X509Certificate[] getAcceptedIssuers() {  
	            return null;  
	        }  
	          
	        @Override  
	        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
	              
	        }  
	          
	        @Override  
	        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
	              
	        }  
	    };  
	      
	    /** 
	     * 接收iOS端发过来的购买凭证 
	     * @param userId  
	     * @param receipt 
	     * @param chooseEnv 
	     */  
	    public String setIapCertificate(String userId, String receipt, boolean chooseEnv){  
	        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(receipt)){  
	            return null;  
	        }  
	        String url = null;  
	        url = chooseEnv == true? certificateUrl:certificateUrlTest;  
	        final String certificateCode = receipt;  
	        if(StringUtils.isNotEmpty(certificateCode)){  
	            return sendHttpsCoon(url, certificateCode);  
	        }else{  
	            return null;  
	        }  
	    }  
	      
	    /** 
	     * 发送请求 
	     * @param url 
	     * @param strings 
	     * @return 
	     */  
	    private String sendHttpsCoon(String url, String code){  
	        if(url.isEmpty()){  
	            return null;  
	        }  
	        try {  
	            //设置SSLContext  
	            SSLContext ssl = SSLContext.getInstance("SSL");  
	            ssl.init(null, new TrustManager[]{myX509TrustManager}, null);  
	              
	            //打开连接  
	            HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();  
	            //设置套接工厂  
	            conn.setSSLSocketFactory(ssl.getSocketFactory());  
	            //加入数据  
	            conn.setRequestMethod("POST");  
	            conn.setDoOutput(true);  
	            conn.setRequestProperty("Content-type","application/json");  
	              
	            JSONObject obj = new JSONObject();  
	            obj.put("receipt-data", code);  
	              
	            BufferedOutputStream buffOutStr = new BufferedOutputStream(conn.getOutputStream());  
	            buffOutStr.write(obj.toString().getBytes());  
	            buffOutStr.flush();  
	            buffOutStr.close();  
	              
	            //获取输入流  
	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
	              
	            String line = null;  
	            StringBuffer sb = new StringBuffer();  
	            while((line = reader.readLine())!= null){  
	                sb.append(line);  
	            }  
	            return sb.toString();  
	          
	        } catch (Exception e) {  
	            return null;  
	        }  
	    }  
}
