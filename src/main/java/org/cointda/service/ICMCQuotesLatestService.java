package org.cointda.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.cointda.dto.CMCQuotesLatestDto;
import org.cointda.entity.CMCQuotesLatest;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface ICMCQuotesLatestService extends IService<CMCQuotesLatest> {

    List<CMCQuotesLatestDto> getJson(String key, String values, String convert, String aux);

    int insert(CMCQuotesLatest entity);
    int update(CMCQuotesLatest entity);

    int deleteByID(Serializable id);
    int deleteById(CMCQuotesLatest entity);
    CMCQuotesLatest selectById(Serializable id);
    List<CMCQuotesLatest> selectBatchIds(Collection<? extends Serializable> idList);
    List<CMCQuotesLatest> selectList(@Param("ew") Wrapper<CMCQuotesLatest> queryWrapper);
}
