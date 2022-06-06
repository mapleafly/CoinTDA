package org.mapleaf.cointda.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.mapleaf.cointda.enums.BooleanEnum;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class HttpHelper {
    private CloseableHttpClient buildHttps() {
        CloseableHttpClient httpClient;
        BooleanEnum proxyEnum = BooleanEnum.valueOf(PrefsHelper.getPreferencesValue(PrefsHelper.PROXY, BooleanEnum.NO.toString()));
        if (proxyEnum.equals(BooleanEnum.YES)) {
            String strHost = PrefsHelper.getPreferencesValue(PrefsHelper.HOST, "127.0.0.1");
            String strPort = PrefsHelper.getPreferencesValue(PrefsHelper.PORT, "62010");
            // 代理
            HttpHost pr = new HttpHost(strHost, Integer.parseInt(strPort), "http");
            RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(pr).build();
            httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        } else {
            // 非代理
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }

    public String makeAPICall(String uri, String httpHeaders, String customHeader, String apiKey, List<NameValuePair> parameters) throws URISyntaxException, IOException {
        String response_content;

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = new HttpHelper().buildHttps();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, httpHeaders);
        request.addHeader(customHeader, apiKey);

        try (CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }

        return response_content;
    }
}
