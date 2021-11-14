package com.example.ttstest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Papago {
    public String getTranslation(String arg, String sourceLang) {

        String clientId = "jyxzd3yELfsaLC6j_j9y";// "UISim5ZQQpIa3_LpFOHS";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "MDfiDsfVSp";//애플리케이션 클라이언트 시크릿값";

        //Scanner sc = new Scanner(System.in);
        //String before= sc.nextLine();

        try {
            String source =  URLEncoder.encode(sourceLang, "UTF-8");
            String text = URLEncoder.encode(arg, "UTF-8");

            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source="+source+"&target=ko&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            String s = response.toString();
            s = s.split("\"")[27];
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }


}
