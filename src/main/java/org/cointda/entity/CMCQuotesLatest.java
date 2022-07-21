package org.cointda.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 最新价格表
 */
@Data
@TableName("cmc_quotes_latest")
public class CMCQuotesLatest implements Serializable {
    //CoinMarketCap定义的id
    @TableId
    private Integer id;
    //coin名称
    private String name;
    //简称(符号)
    private String symbol;
    //标称  The web URL friendly shorthand version of this cryptocurrency name
    private String slug;
    //最后更新时间
    private String lastUpdated;

    //aux
    //coin在市场中的交易对数量
    private Integer numMarketPairs;
    //首次加入CoinMarketCap的时间
    private String dateAdded;
    //最终的数量
    private String maxSupply;
    //当前正在流通的硬币的大概数量
    private String circulatingSupply;
    //当前大约存在的硬币总数（减去已被可验证燃烧的所有硬币）
    private String totalSupply;
    //是否激活，1-yes  0=no
    private Integer isActive;
    //平台id，如果是基于某个平台的token，比如基于eth的很多coin和token
    private Integer platformId;
    //在平台中的创建地址,如果platform_id 为null，这里也是null
    private String tokenAddress;
    //The cryptocurrency's CoinMarketCap rank by market cap.
    private Integer cmcRank;

    //quote
    //整个市场的最新平均交易价格
    private String price;
    //24小时交易量
    @TableField("volume_24h")
    private String volume24h;
    @TableField("volume_change_24h")
    private String volumeChange24h;
    //每种货币1小时的交易价格百分比变化
    @TableField("percent_change_1h")
    private String percentChange1h;
    //每种货币的24小时交易价格百分比变化
    @TableField("percent_change_24h")
    private String percentChange24h;
    //每种货币7天交易价格的百分比变化
    @TableField("percent_change_7d")
    private String percentChange7d;
    @TableField("percent_change_30d")
    private String percentChange30d;
    @TableField("percent_change_60d")
    private String percentChange60d;
    @TableField("percent_change_90d")
    private String percentChange90d;
    //CoinMarketCap计算的市值
    private String marketCap;
    //市值占总市值百分比
    private String marketCapDominance;

}
