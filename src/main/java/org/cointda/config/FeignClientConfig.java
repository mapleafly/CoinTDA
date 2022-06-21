package org.cointda.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
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

@Slf4j
@Configuration
public class FeignClientConfig {
    @Configuration
    static class OkHttpClientConfiguration {
        @Value("${proxy.host}")
        private String proxyHost;
        @Value("${proxy.port}")
        private Integer proxyPort;
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
}
