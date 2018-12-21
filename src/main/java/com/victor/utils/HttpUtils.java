package com.victor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.victor.model.KeyValueModel;

public class HttpUtils {

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        // Validate connections after 1 sec of inactivity
        connMgr.setValidateAfterInactivity(1000);
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);

        requestConfig = configBuilder.build();
    }
    
	/** 
     * 发送GET请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendGet(String url, Map<String, String> parameters) {  
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        StringBuffer sb = new StringBuffer();// 存储参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            String full_url = url + "?" + params;  
            System.out.println(full_url);  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(full_url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 建立实际的连接  
            httpConn.connect();  
            // 响应头部获取  
            Map<String, List<String>> headers = httpConn.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : headers.keySet()) {  
                System.out.println(key + "\t：\t" + headers.get(key));  
            }  
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    } 
    
    
    
    
    
    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }   
    
    
    /**
     * 发送 GET 请求（HTTP），不带输入数据
     *
     * @param url
     * @return
     */
    public static JSONObject doGet(String url) {
        return doGet(url, new HashMap<String, Object>());
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @return
     */
    public static JSONObject doGet(String url, Map<String, Object> params) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0) {
                param.append("?");
            } else {
                param.append("&");
                param.append(key).append("=").append(params.get(key));
                i++;
            }
        }
        apiUrl += param;
        String result = null;
        HttpClient httpClient = null;
        if (apiUrl.startsWith("https")) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(result);
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param apiUrl
     * @return
     */
    public static JSONObject doPost(String apiUrl) {
        return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 发送 POST 请求，K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static JSONObject doPost(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = null;
        if (apiUrl.startsWith("https")) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println( JSON.parseObject(httpStr));
        return JSON.parseObject(httpStr);
    }

    /**
     * 发送 POST 请求，JSON形式
     *
     * @param apiUrl
     * @param json   json对象
     * @return
     */
    public static JSONObject doPost(String apiUrl, Object json) {
        CloseableHttpClient httpClient = null;
        if (apiUrl.startsWith("https")) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("传入参数为："+json.toString());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return JSON.parseObject(httpStr);
    }

    /**
     * 发送 POST 请求，K-V形式
     *
     * @param apiUrl     API接口URL
     * @param params     参数map
     * @param headParams 参数headmap
     * @return
     */
    public static JSONObject doPost(String apiUrl, Map<String, Object> params, Map<String, Object> headParams) {
        CloseableHttpClient httpClient = null;
        if (apiUrl.startsWith("https")) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            Iterator headerIterator = headParams.entrySet().iterator();          //循环增加header
            while (headerIterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) headerIterator.next();
                httpPost.addHeader(elem.getKey(), elem.getValue());
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return JSON.parseObject(httpStr);
    }

    /**
     * 发送 POST 请求，JSON形式
     *
     * @param apiUrl
     * @param json       json对象
     * @param headParams 参数headmap
     * @return
     */
    public static Map<String, Object> doPost(String apiUrl, Object json, Map<String, Object> headParams) {
        Map<String, Object> responseMap = new HashMap<>();
        CloseableHttpClient httpClient = null;
        if (apiUrl.startsWith("https")) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            Iterator headerIterator = headParams.entrySet().iterator();          //循环增加header
            while (headerIterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) headerIterator.next();
                httpPost.addHeader(elem.getKey(), elem.getValue());
            }
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        responseMap.put("response", httpStr.toString());
        responseMap.put("status", response.getStatusLine().getStatusCode());
        return responseMap;
    }

    /**
     * 发送 POST 请求，form-data格式
     *
     * @param apiUrl
     * @param params
     * @param headParams
     * @return
     */
    public static Map<String, Object> doPost(String apiUrl, List<KeyValueModel> params, Map<String, Object> headParams) throws Exception {
        Map<String, Object> responseMap = new HashMap<>();
        String responseMessage = "";
        StringBuffer response = new StringBuffer();
        HttpURLConnection httpConnection = null;
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        try {
            URL urlPost = new URL(apiUrl);
            httpConnection = (HttpURLConnection) urlPost.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setRequestMethod("POST");
            // 不使用缓存
            httpConnection.setUseCaches(false);
            // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
            httpConnection.setInstanceFollowRedirects(true);
            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
            // 意思是正文是urlencoded编码过的form参数
            httpConnection.setRequestProperty("Connection", "Keep-Alive");
            httpConnection.setRequestProperty("Charset", "UTF-8");
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置请求头信息
            Iterator headerIterator = headParams.entrySet().iterator();          //循环增加header
            while (headerIterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) headerIterator.next();
                httpConnection.setRequestProperty(elem.getKey(), elem.getValue());
            }
            httpConnection.connect();
            out = new OutputStreamWriter(httpConnection.getOutputStream(), "UTF-8");
            // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
            // 构建请求参数
            StringBuffer sb = new StringBuffer();
            if (params.size() > 0 && params != null) {//請求只有一个
                for (KeyValueModel p : params) {
                    sb.append(p.getKey());
                    sb.append("=");
                    sb.append(p.getValue());
                    sb.append("&");
                }
                sb.substring(0, sb.length() - 1);
            }
            //写入参数,DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
            out.write(sb.toString());
            System.out.println("send_url:" + apiUrl);
            System.out.println("send_data:" + sb.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != httpConnection) {
                    httpConnection.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
        while ((responseMessage = reader.readLine()) != null) {
            response.append(responseMessage);
            response.append("\n");
        }
        if (!"failure".equals(response.toString())) {
            System.out.println("success");
        } else {
            System.out.println("failue");
        }
        // 将该url的配置信息缓存起来
        System.out.println(response.toString());
        System.out.println(httpConnection.getResponseCode());
        responseMap.put("response", response);
        responseMap.put("status", httpConnection.getResponseCode());
        return responseMap;
    }

    /**
     * 发送 HTTP 请求，XML格式
     * @param apiUrl
     * @param params 此处为XML格式参数
     * @param method POST/DELETE/PUT
     * @return
     */
    public static Map<String, Object> doHttp(String apiUrl, String params, String method) throws Exception {
        Map<String, Object> responseMap = new HashMap<>();
        String responseMessage = "";
        StringBuffer response = new StringBuffer();
        HttpURLConnection httpConnection = null;
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        try {
            URL urlPost = new URL(apiUrl);
            httpConnection = (HttpURLConnection) urlPost.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setRequestMethod(method);
            // 不使用缓存
            httpConnection.setUseCaches(false);
            // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestProperty("Connection", "Keep-Alive");
            httpConnection.setRequestProperty("Charset", "UTF-8");
            httpConnection.setRequestProperty("Content-Type", "application/xml");
            httpConnection.connect();
            out = new OutputStreamWriter(httpConnection.getOutputStream(), "UTF-8");
            // params不为空，传输参数
            if (StringUtils.isNotBlank(params)){
                out.write(params);
            }
            logger.info("send_url:" + apiUrl + "send_data:" + params);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != httpConnection) {
                    httpConnection.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
        while ((responseMessage = reader.readLine()) != null) {
            response.append(responseMessage);
            response.append("\n");
        }
        /*if (!"failure".equals(response.toString())) {
            System.out.println("success");
        } else {
            System.out.println("failue");
        }*/
        logger.info("response:" + response==null?"":response.toString() + "status:" + httpConnection.getResponseCode());
        responseMap.put("response", response==null?"":response.toString());
        responseMap.put("status", httpConnection.getResponseCode());
        return responseMap;
    }


    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

//    private static String xml = " <instance>\n" +
//            "            <!--实例ID -->\n" +
//            "            <instanceId>192.168.5.37:5010</instanceId>\n" +
//            "            <!--实例HOST -->\n" +
//            "            <hostName>192.168.5.37</hostName>\n" +
//            "            <!--全局应用名 -->\n" +
//            "            <app>DAAS-TEST-API-LT</app>\n" +
//            "            <!--IP地址 -->\n" +
//            "            <ipAddr>192.168.5.37</ipAddr>\n" +
//            "            <!--实例状态 -->\n" +
//            "            <status>UP</status>\n" +
//            "            <!--实例的覆盖状态 -->\n" +
//            "            <overriddenstatus>UNKNOWN</overriddenstatus>\n" +
//            "            <!--端口 -->\n" +
//            "            <port enabled=\"true\">5010</port>\n" +
//            "            <securePort enabled=\"false\">443</securePort>\n" +
//            "            <countryId>1</countryId>\n" +
//            "            <!--使用的数据中心 -->\n" +
//            "            <dataCenterInfo class=\"com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo\">\n" +
//            "                <name>MyOwn</name>\n" +
//            "            </dataCenterInfo>\n" +
//            "            <!--监控端口 -->\n" +
//            "            <metadata>\n" +
//            "                <gated-launch>false</gated-launch>\n" +
//            "            </metadata>\n" +
//            "            <!--主页 -->\n" +
//            "            <homePageUrl>http://192.168.5.37:5010/</homePageUrl>\n" +
//            "            <!--信息查询接口 -->\n" +
//            "            <statusPageUrl>http://192.168.5.37:5010/info</statusPageUrl>\n" +
//            "            <!--健康检查接口 -->\n" +
//            "            <healthCheckUrl>http://192.168.5.37:5010/health</healthCheckUrl>\n" +
//            "            <!--VIP -->\n" +
//            "            <vipAddress>DAAS-TEST-API-LT</vipAddress>\n" +
//            "            <secureVipAddress>DAAS-TEST-API-LT</secureVipAddress>\n" +
//            "            <isCoordinatingDiscoveryServer>false</isCoordinatingDiscoveryServer>\n" +
//            "            <!--最后修改时间 -->\n" +
//            "            <lastDirtyTimestamp>1528277019770</lastDirtyTimestamp>\n" +
//            "   </instance>";
//
//
//    /**
//     * test
//     *
//     * @param args
//     */
    public static void main(String args[]) {
        Object json = "{\"RequestId\":\"1112\",\"ServiceName\":\"S0140001A\",\"SourceSystem\":\"EASIPASS\",\"TargetSystem\":\"EIR\"}";
        String url = "http://192.168.129.172:8889/eureka/apps/DAAS-TEST-API-LT";
        Map<String, Object> headParams = new HashMap<>();
        headParams.put("sourceSystem", "EASIPASS");
        headParams.put("requestId", "21345558");
        headParams.put("serviceName", "S0140001A");
        headParams.put("targetSystem", "AUTH");
        headParams.put("Authorization", "Basic dHJ1c3RlZC1FQVNJUEFTUzo2N3NmYXcyLTJqaGZqaGEyNzY4My1mYXk3MjIxZWVrbA==");
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", "EASIPASS");
        params.put("Password", "q1234567");
        List<KeyValueModel> p = new ArrayList<KeyValueModel>();
        p.add(new KeyValueModel("grant_type", "password"));
        p.add(new KeyValueModel("username", "EASIPASS"));
        p.add(new KeyValueModel("password", "q1234567"));
        Map<String, Object> responseMap = new HashMap<>();
        String method = "";
        String upUrl = "http://192.168.129.172:8889/eureka/apps/DAAS-TEST-API-LT";
        //下线url
        String downUrl = "http://192.168.129.172:8889/eureka/apps/DAAS-TEST-API-LT/192.168.5.37:6010";
        //心跳续约url
        String heatbeatUrl = "http://192.168.129.172:8889/eureka/apps/DAAS-TEST-API-LT/192.168.5.37:6010";
        String requestParams = "";
//        requestParams = xml;
        try {
            //注册
            //responseMap = test(downUrl, requestParams, "POST");
            //下线
            //responseMap = test(downUrl, "", "DELETE");
            //心跳续约
            responseMap = doHttp(heatbeatUrl, "", "PUT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response result:" + responseMap.get("response").toString());
    }

}
