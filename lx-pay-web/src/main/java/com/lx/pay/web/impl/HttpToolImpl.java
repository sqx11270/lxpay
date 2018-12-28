package com.lx.pay.web.impl;

import com.lx.pay.web.service.HttpTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Service
public class HttpToolImpl implements HttpTool {
    private final static Logger logger = LoggerFactory.getLogger(HttpToolImpl.class);
    /**
     * 发送post请求获取返回头文件获这cookie
     * @param urlstr  请求地址
     * @param data  请求参数
     * @return
     * @throws IOException
     */
    public String httpPost(String urlstr, String data) {
        String resp = "";
        try{
            URL url = new URL(urlstr);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "text/plain");
            urlConnection.setRequestProperty("User-Agent", "curl/7.12.1");
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
            out.write(data);
            out.flush();
            out.close();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }

            in.close();

            resp = sb.toString();
        }catch(IOException e){
            logger.error("post error. url:{}, e:{}", urlstr, e);
        }

        return resp;
    }
}
