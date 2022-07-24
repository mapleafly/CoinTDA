package org.cointda.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.cointda.dto.CMCMapDto;
import org.cointda.entity.CMCMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface ICMCMapService extends IService<CMCMap> {

    List<CMCMapDto> getJson(String listing_status, Integer start, Integer limit, String sort, String aux);
    List<CMCMapDto> getJson(Integer start, Integer limit, String sort, String aux);
    List<CMCMapDto> getJson(Integer start, Integer limit, String sort);
    List<CMCMapDto> getJson(Integer limit, String sort);
    List<CMCMapDto> getJson(Integer limit);

    int insert(CMCMap entity);
    int update(CMCMap entity);

    int deleteByID(Serializable id);
    int deleteById(CMCMap entity);
    CMCMap selectById(Serializable id);
    List<CMCMap> selectBatchIds(Collection<? extends Serializable> idList);
    List<CMCMap> selectList(@Param("ew") Wrapper<CMCMap> queryWrapper);
}
