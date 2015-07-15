package utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SmsUtils {

    public static final String[] prefix = new String[]{"65", "065"};

    public static String sendMsg(String mobileNo, String sMsg) throws UnsupportedEncodingException {
        String result = "";
        HttpURLConnection conn = null;
        try {
            if (!StringUtils.startsWithAny(mobileNo, prefix)) {
                //mobileNo = StringUtils.substring(mobileNo, mobileNo.length() - 8);
                mobileNo = "65" + mobileNo;
            }
            String sURL = "http://gateway.onewaysms.sg:10002/api.aspx?apiusername=APIPKMESZCF7I&apipassword=APIPKMESZCF7IPKMES&mobileno="
                    + mobileNo + "&senderid=WeeboPos&languagetype=1&message=" + URLEncoder.encode(sMsg, "UTF-8");
            URL url = new URL(sURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(false);
            conn.setRequestMethod("GET");
            conn.connect();
            int iResponseCode = conn.getResponseCode();
            if (iResponseCode == 200) {
                BufferedReader oIn = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String sInputLine = "";
                StringBuilder sResult = new StringBuilder();
                while ((sInputLine = oIn.readLine()) != null) {
                    sResult.append(sInputLine);
                }
                if (Long.parseLong(sResult.toString()) > 0) {
                    result = "success";
                } else {
                    result = "fail";
                }
            } else {
                result = "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }
}