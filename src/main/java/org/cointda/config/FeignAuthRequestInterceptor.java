package org.cointda.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.cointda.util.PrefsHelper;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class FeignAuthRequestInterceptor  implements RequestInterceptor {

    @Value("${coin-market-cap.customHeader}")
    private String customHeader;
    @Value("${coin-market-cap.httpHeader}")
    private String httpHeader;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //requestTemplate.header("Accept", httpHeader);
        requestTemplate.header(customHeader, PrefsHelper.getPreferencesValue(PrefsHelper.CMC_API_KEY, ""));
    }

}
