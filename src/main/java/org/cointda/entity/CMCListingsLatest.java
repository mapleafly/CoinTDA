package org.cointda.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 最新价格全表
 */
@Data
@TableName("cmc_listings_latest")
public class CMCListingsLatest implements Serializable {
    //CoinMarketCap定义的id
    @TableId
    private Integer id;
    //coin名称
    private String name;
    //简称(符号)
    private String symbol;
    //标称  The web URL friendly shorthand version of this cryptocurrency name
    private String slug;
    //The cryptocurrency's CoinMarketCap rank by market cap.
    private Integer cmcRank;
    //首次加入CoinMarketCap的时间
    private String dateAdded;
    //平台id，如果是基于某个平台的token，比如基于eth的很多coin和token
    private Integer platformId;
    //在平台中的创建地址,如果platform_id 为null，这里也是null
    private String tokenAddress;
    //全部市场的相关交易对
    private Integer numMarketPairs;
    //最终的数量
    private String maxSupply;
    //当前正在流通的硬币的大概数量
    private String circulatingSupply;
    //当前大约存在的硬币总数（减去已被可验证燃烧的所有硬币）
    private String totalSupply;
    //整个市场的最新平均交易价格
    private String price;
    //24小时交易量
    @TableField("volume_24h")
    private String volume_24h;
    @TableField("volume_change_24h")
    private String volume_change_24h;
    //每种货币1小时的交易价格百分比变化
    @TableField("percent_change_1h")
    private String percent_change_1h;
    //每种货币的24小时交易价格百分比变化
    @TableField("percent_change_24h")
    private String percent_change_24h;
    //每种货币7天交易价格的百分比变化
    @TableField("percent_change_7d")
    private String percent_change_7d;
    //CoinMarketCap计算的市值
    private String marketCap;
    //最后更新时间
    private String lastUpdated;
}
