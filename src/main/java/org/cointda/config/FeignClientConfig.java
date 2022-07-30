package org.cointda.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.cointda.enums.BooleanEnum;
import org.cointda.util.PrefsHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.httpclient.DefaultOkHttpClientFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 使用代理连接网络
 */
@Slf4j
@Configuration
public class FeignClientConfig {
    //@Value("${proxy.host}")
    BooleanEnum proxyEnum = BooleanEnum.valueOf(PrefsHelper.getPreferencesValue(PrefsHelper.PROXY, BooleanEnum.NO.toString()));
    private String proxyHost = PrefsHelper.getPreferencesValue(PrefsHelper.HOST, "127.0.0.1");
    //@Value("${proxy.port}")
    private Integer proxyPort = Integer.valueOf(PrefsHelper.getPreferencesValue(PrefsHelper.PORT, "53214"));
    @Value("#{'${proxy.domains}'.split(',')}")
    private Set<String> domainList;

    @Bean
    public OkHttpClientFactory okHttpClientFactory(OkHttpClient.Builder builder) {
        return new ProxyOkHttpClientFactory(builder);
    }

    public class ProxyOkHttpClientFactory extends DefaultOkHttpClientFactory {
        public ProxyOkHttpClientFactory(OkHttpClient.Builder builder) {
            super(builder);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            List<Proxy> proxyList = new ArrayList<>(1);
            proxyList.add(proxy);
            builder.proxySelector(new ProxySelector() {
                @Override
                public List<Proxy> select(URI uri) {
                    if(proxyEnum.equals(BooleanEnum.NO)){
                        return Collections.singletonList(Proxy.NO_PROXY);
                    }
                    if (uri == null || !domainList.contains(uri.getHost())) {
                        //if (uri == null) {
                        return Collections.singletonList(Proxy.NO_PROXY);
                    }
                    return proxyList;
                }

                @Override
                public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                }
            });
        }
    }
}
