package com.zhengcheng.common.http;


import com.zhengcheng.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.nutz.json.Json;
import org.nutz.lang.Strings;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.*;


/**
 * HttpWrapper
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.common.http
 * @Description :
 * @date :    2019/2/28 21:00
 */
@Slf4j
public class HttpWrapper {
    private HttpClient httpClient;
    private String url;
    private HttpWrapper.HttpMethod method;
    private Charset charset;
    private int timeout;
    private int retryTimes;
    private int currentRetryNo;
    private Map<String, String> headers;
    private Map<String, Object> params;
    private String postData;
    private InputStream inputStream;
    private HttpHost proxy;

    private HttpWrapper() {
        this.method = HttpWrapper.HttpMethod.GET;
        this.charset = StandardCharsets.UTF_8;
        this.timeout = 30000;
        this.retryTimes = 1;
        this.currentRetryNo = 0;
        this.headers = new HashMap();
        this.params = new HashMap();
        this.httpClient = this.initHttpClient();
    }

    public static HttpWrapper create() {
        return new HttpWrapper();
    }

    public void setProxy(HttpHost proxy) {
        this.proxy = proxy;
    }

    public HttpWrapper setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public HttpWrapper setUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpWrapper setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public HttpWrapper setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    public HttpWrapper setMethod(HttpWrapper.HttpMethod httpMethod) {
        this.method = httpMethod;
        return this;
    }

    public HttpWrapper setMethod(String method) {
        this.method = HttpWrapper.HttpMethod.getMethod(method);
        return this;
    }

    public HttpWrapper addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public HttpWrapper addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public HttpWrapper addParams(Map<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    public HttpWrapper addParam(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    public HttpWrapper initParam() {
        this.params = new HashMap();
        return this;
    }

    public HttpWrapper setPostData(String data) {
        this.postData = data;
        return this;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public HttpResponse execute() {
        this.currentRetryNo = 0;
        HttpResponse response = null;

        while (true) {
            if (this.currentRetryNo > 0) {
                log.info("Retry {}: {} {}", new Object[]{this.currentRetryNo, this.method.value, this.url});
            }

            try {
                HttpUriRequest request = this.getHttpRequest();
                response = this.httpClient.execute(request);
                if (response != null) {
                    log.info("{} {} {}", new Object[]{this.method.value, this.url, response.getStatusLine().getStatusCode()});
                }
                break;
            } catch (Exception var3) {
                log.error("catch exception request : " + this.url + ", retryNo:" + this.currentRetryNo, var3);
                if (this.currentRetryNo++ >= this.retryTimes) {
                    break;
                }
            }
        }

        return response;
    }

    public String executeStr(String charset) {
        HttpResponse response = this.execute();
        String content = null;

        try {
            if (response != null) {
                content = EntityUtils.toString(response.getEntity(), charset);
            }
        } catch (IOException | ParseException var5) {
            log.error(var5.getMessage(), var5);
        }

        return content;
    }

    public String executeStr() {
        return this.executeStr(StandardCharsets.UTF_8.name());
    }

    public Result executeResult() {
        String resp = this.executeStr();
        return (Result) Json.fromJson(Result.class, resp);
    }

    public <T> Result<T> executeResult(Class<T> clazz) {
        String resp = this.executeStr();
        return (Result) Json.fromJson(Result.class, resp);
    }

    public byte[] executeByte() {
        HttpResponse resp = this.execute();
        byte[] data = null;

        try {
            data = EntityUtils.toByteArray(resp.getEntity());
        } catch (IOException | ParseException var4) {
            log.error(var4.getMessage(), var4);
        }

        return data;
    }

    private RequestConfig requestConfig() {
        return RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(this.timeout).setProxy(this.proxy).build();
    }

    private HttpUriRequest getHttpRequest() throws UnsupportedEncodingException {
        String requestURL = this.url;
        Object request;
        if (HttpWrapper.HttpMethod.GET == this.method) {
            if (this.params != null && this.params.size() != 0) {
                requestURL = requestURL + "?" + getQueryParameter(this.params);
            }

            request = new HttpGet(requestURL);
        } else {
            StringEntity bodyParams;
            if (HttpWrapper.HttpMethod.POST == this.method) {
                request = new HttpPost(this.url);
                bodyParams = null;
                if (Strings.isNotBlank(this.postData)) {
                    bodyParams = new StringEntity(this.postData, this.charset);
                } else {
                    bodyParams = new StringEntity(Json.toJson(this.params), this.charset);
                }

                bodyParams.setContentType("application/json");
                ((HttpPost) request).setEntity(bodyParams);
            } else if (HttpWrapper.HttpMethod.POST_TEXT == this.method) {
                request = new HttpPost(this.url);
                bodyParams = this.postForNormal();
                ((HttpPost) request).setEntity(bodyParams);
            } else {
                Iterator var4;
                String key;
                if (HttpWrapper.HttpMethod.POST_FORM == this.method) {
                    request = new HttpPost(this.url);
                    List<NameValuePair> nameValuePairList = new ArrayList();
                    var4 = this.params.keySet().iterator();

                    while (var4.hasNext()) {
                        key = (String) var4.next();
                        nameValuePairList.add(new BasicNameValuePair(key, Strings.sNull(this.params.get(key))));
                    }

                    ((HttpPost) request).setEntity(new UrlEncodedFormEntity(nameValuePairList, "utf8"));
                } else {
                    if (HttpWrapper.HttpMethod.MULTI_FORM != this.method) {
                        throw new RuntimeException("没有指定http请求类型");
                    }

                    request = new HttpPost(this.url);
                    MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
                    var4 = this.params.keySet().iterator();

                    while (var4.hasNext()) {
                        key = (String) var4.next();
                        Object value = this.params.get(key);
                        if (value instanceof InputStream) {
                            InputStream is = (InputStream) value;
                            String fileName = Strings.sNull(this.params.get("__filename__" + key));
                            fileName = Strings.isBlank(fileName) ? String.valueOf(System.currentTimeMillis()) : fileName;
                            entityBuilder.addPart(key, new InputStreamBody(is, fileName));
                        } else if (value instanceof File) {
                            File file = (File) value;
                            entityBuilder.addPart(key, new FileBody(file));
                        } else {
                            entityBuilder.addTextBody(key, Strings.sBlank(this.params.get(key)));
                        }
                    }

                    HttpEntity httpEntity = entityBuilder.build();
                    ((HttpPost) request).setEntity(httpEntity);
                }
            }
        }

        ((HttpRequestBase) request).setConfig(this.requestConfig());
        Iterator var13 = this.headers.keySet().iterator();

        while (var13.hasNext()) {
            String key = (String) var13.next();
            ((HttpUriRequest) request).addHeader(key, (String) this.headers.get(key));
        }

        return (HttpUriRequest) request;
    }

    private static String getQueryParameter(Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator var2 = params.keySet().iterator();

        while (var2.hasNext()) {
            String key = (String) var2.next();
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }

            String value = Strings.sBlank(params.get(key));
            stringBuilder.append(key).append("=").append(value);
        }

        return stringBuilder.toString();
    }

    private StringEntity postForNormal() throws UnsupportedEncodingException {
        List<NameValuePair> nameValuePairList = new ArrayList();
        StringBuilder args = new StringBuilder();
        Set<String> keySet = this.params.keySet();
        Iterator var4 = keySet.iterator();

        while (var4.hasNext()) {
            String key = (String) var4.next();
            args.append(key).append("=").append(this.params.get(key)).append("&");
            nameValuePairList.add(new BasicNameValuePair(key, Strings.sNull(this.params.get(key))));
        }

        log.info("request params:" + args.toString());
        return new UrlEncodedFormEntity(nameValuePairList, this.charset);
    }

    private CloseableHttpClient initHttpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        SSLContext sslContext;
        try {
            sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore) null, (i, j) -> {
                return true;
            }).build();
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }

        httpClientBuilder.setSSLContext(sslContext);
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext, hostnameVerifier))
                .build();
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connMgr.setMaxTotal(200);
        connMgr.setDefaultMaxPerRoute(100);
        httpClientBuilder.setConnectionManager(connMgr);
        return httpClientBuilder.build();
    }

    public static enum HttpMethod {
        POST("post"),
        GET("get"),
        POST_TEXT("post_text"),
        POST_FORM("post_form"),
        MULTI_FORM("multi_form");

        private String value;

        private HttpMethod(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static HttpWrapper.HttpMethod getMethod(String value) {
            if (POST.getValue().equalsIgnoreCase(value)) {
                return POST;
            } else if (GET.getValue().equalsIgnoreCase(value)) {
                return GET;
            } else if (POST_TEXT.getValue().equalsIgnoreCase(value)) {
                return POST_TEXT;
            } else if (POST_FORM.getValue().equalsIgnoreCase(value)) {
                return POST_FORM;
            } else if (MULTI_FORM.getValue().equalsIgnoreCase(value)) {
                return MULTI_FORM;
            } else {
                throw new RuntimeException("error input method value is wrong!");
            }
        }
    }
}

