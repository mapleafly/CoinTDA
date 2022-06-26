package org.cointda.service;

import org.cointda.dto.quote.QuotesLatestDto;

import java.util.List;

public interface IQuotesLatestService {

    public List<QuotesLatestDto> getJson(String key, String values, String convert, String aux);

}
