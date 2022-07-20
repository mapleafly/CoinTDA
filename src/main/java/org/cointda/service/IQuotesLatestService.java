package org.cointda.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.cointda.dto.quote.QuotesLatestDto;
import org.cointda.entity.QuotesLatest;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface IQuotesLatestService extends IService<QuotesLatest> {

    List<QuotesLatestDto> getJson(String key, String values, String convert, String aux);

    int insert(QuotesLatest entity);
    int update(QuotesLatest entity);

    int deleteByID(Serializable id);
    int deleteById(QuotesLatest entity);
    QuotesLatest selectById(Serializable id);
    List<QuotesLatest> selectBatchIds(Collection<? extends Serializable> idList);
    List<QuotesLatest> selectList(@Param("ew") Wrapper<QuotesLatest> queryWrapper);
}
