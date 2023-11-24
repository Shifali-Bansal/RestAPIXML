package com.newgen.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.newgen.srvr.xml.XMLParser;

public class RESTAPIXML {
	String respp = "";

	public String getCurrentISOTimeInUTF8() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
		String timeNow = df.format(new Date());
		return timeNow;
	}

	public String restOutPutXml(String uri, String requestBody) {

		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
					// TODO Auto-generated method stub

				}
			} };
			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			HostnameVerifier allHostValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}

			};
			HttpsURLConnection.setDefaultHostnameVerifier(allHostValid);
			URL url = new URL(uri);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setConnectTimeout(300000);
			conn.setReadTimeout(300000);
			OutputStream os = conn.getOutputStream();
			os.write(requestBody.getBytes());
			os.flush();
			System.out.println("conn.getResponseCode" + conn.getResponseCode());
			System.out.println("conn.getResponseMessage" + conn.getResponseMessage());

			if (conn.getResponseCode() != 200) {

				
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			StringBuilder OutputXML = new StringBuilder();
			String output2 = "";

			System.out.println("Response ");

			while ((output = br.readLine()) != null) {
				OutputXML.append(output);
			}
		

			br.close();
			conn.disconnect();
			return OutputXML.toString();
		} catch (MalformedURLException e) {

			System.out.println(Arrays.toString(e.getStackTrace()));

		} catch (IOException e) {

			System.out.println(Arrays.toString(e.getStackTrace()));

		} catch (Exception e) {

			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		return "";
	}
	
	
	public void getIGOutXml(String actId) {

		
		System.out.println("callingapi");
		

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		
		LocalDateTime now = LocalDateTime.now();

		String dateTime = dtf.format(now).substring(0, 4) + dtf.format(now).substring(5, 7)
				+ dtf.format(now).substring(8, 10) + dtf.format(now).substring(11, 13)
				+ dtf.format(now).substring(14, 16) + dtf.format(now).substring(17);

		
		String vr = dtf.format(now).substring(14, 16) + dtf.format(now).substring(17, 19);
		String requestId = "daccea31-fcca-480b-" + vr + "-" + dtf.format(now).substring(0, 3) + "fbc"
				+ dtf.format(now).substring(5, 7) + dtf.format(now).substring(8, 10) + "a0";

		
		System.out.println("request id" + requestId);
		String request = "";
	String a="",b="";
		

		request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<FIXML xsi:schemaLocation=\"http://www.finacle.com/fixml executeFinacleScript.xsd\" xmlns=\"http://www.finacle.com/fixml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSc\r\n"
				+ "hema-instance\">\r\n" + "<Header>\r\n" + "<RequestHeader>\r\n" + "<MessageKey>\r\n" + "<RequestUUID>"
				+ requestId + "</RequestUUID>\r\n" + "<ServiceRequestId>executeFinacleScript</ServiceRequestId>\r\n"
				+ "<ServiceRequestVersion>10.2</ServiceRequestVersion>\r\n" + "<ChannelId>AOU</ChannelId>\r\n"
				+ "<LanguageId></LanguageId>\r\n" + "</MessageKey>\r\n" + "<RequestMessageInfo>\r\n"
				+ "<BankId>01</BankId>\r\n" + "<TimeZone></TimeZone>\r\n" + "<EntityId></EntityId>\r\n"
				+ "<EntityType></EntityType>\r\n" + "<ArmCorrelationId></ArmCorrelationId>\r\n" + "<MessageDateTime>"
				+ getCurrentISOTimeInUTF8() + "</MessageDateTime>\r\n" + "</RequestMessageInfo>\r\n" + "<Security>\r\n"
				+ "<Token>\r\n" + "<PasswordToken>\r\n" + "<UserId></UserId>\r\n" + "<Password></Password>\r\n"
				+ "</PasswordToken>\r\n" + "</Token>\r\n" + "<FICertToken></FICertToken>\r\n"
				+ "<RealUserLoginSessionId></RealUserLoginSessionId>\r\n" + "<RealUser></RealUser>\r\n"
				+ "<RealUserPwd></RealUserPwd>\r\n" + "<SSOTransferToken></SSOTransferToken>\r\n" + "</Security>\r\n"
				+ "</RequestHeader>\r\n" + "</Header>\r\n" + "<Body>\r\n" + "<executeFinacleScriptRequest>\r\n"
				+ "<executeFinacleScriptInputVO>\r\n" + "<requestId>FI_Acct_Schm_Change_v1.scr</requestId>\r\n"
				+ "<executeFinacleScript_CustomData>\r\n" + "<ChannelId>AOU</ChannelId>\r\n" + "<AcctId>" + actId
				+ "</AcctId>\r\n" + "<TargetSchmCode>" + a + "</TargetSchmCode>\r\n" + "<TargetGLSubCode>"
				+ b + "</TargetGLSubCode>\r\n" + "</executeFinacleScript_CustomData>\r\n"
				+ "</executeFinacleScriptInputVO>\r\n" + "</executeFinacleScriptRequest>\r\n" + "</Body>\r\n"
				+ "</FIXML>";

		System.out.println("request::::" + request);
		String RequestURL ="url";
		
		String Response = "";
		
		/*Response = "<FIXML xsi:schemaLocation=\"http://www.finacle.com/fixml executeFinacleScript.xsd\" xmlns=\"http://www.\r\n"
				+ "finacle.com/fixml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n"
				+ "<Header>\r\n" + "<ResponseHeader>\r\n" + "<RequestMessageKey>\r\n"
				+ "<RequestUUID>daccea31-fcca-480b-8476-398fbc4875a0</RequestUUID>\r\n"
				+ "<ServiceRequestId>executeFinacleScript</ServiceRequestId>\r\n"
				+ "<ServiceRequestVersion>10.2</ServiceRequestVersion>\r\n" + "<ChannelId>AOU</ChannelId>\r\n"
				+ "</RequestMessageKey>\r\n" + "<ResponseMessageInfo>\r\n" + "<BankId>01</BankId>\r\n"
				+ "<TimeZone>GMT+05:30</TimeZone>\r\n"
				+ "<MessageDateTime>2023-04-13T11:41:55.954</MessageDateTime>\r\n" + "</ResponseMessageInfo>\r\n"
				+ "<UBUSTransaction>\r\n" + "<Id/>\r\n" + "<Status/>\r\n" + "</UBUSTransaction>\r\n"
				+ "<HostTransaction>\r\n" + "<Id/>\r\n" + "<Status>SUCCESS</Status>\r\n" + "</HostTransaction>\r\n"
				+ "<HostParentTransaction>\r\n" + "<Id/>\r\n" + "<Status/>\r\n" + "</HostParentTransaction>\r\n"
				+ "<CustomInfo/>\r\n" + "</ResponseHeader>\r\n" + "</Header>\r\n" + "<Body>\r\n"
				+ "<executeFinacleScriptResponse>\r\n" + "<ExecuteFinacleScriptOutputVO>\r\n"
				+ "</ExecuteFinacleScriptOutputVO>\r\n" + "<executeFinacleScript_CustomData/>\r\n"
				+ "</executeFinacleScriptResponse>\r\n" + "</Body>\r\n" + "</FIXML>";*/
	
			Response = restOutPutXml(RequestURL, request);
		

		System.out.println("Response from the fullKYC API:" + Response);

		XMLParser Resp1 = new XMLParser(Response);
		Resp1 = new XMLParser(Resp1.getValueOf("Header"));

		XMLParser Resp = new XMLParser(Response);
		Resp = new XMLParser(Resp.getValueOf("Body"));

		XMLParser getStatus = new XMLParser(Resp1.getValueOf("HostTransaction"));
		String status = "";
		if (!getStatus.getValueOf("Status").toString().equals("SUCCESS")) {

			System.out.println("FAILED IN CALLING API");
			
		} else {

			System.out.println("Sucess in Calling API");

			

		}
	}
}
