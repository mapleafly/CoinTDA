package org.cointda.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 交易信息表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("trade_info")
//@KeySequence
public class TradeInfo implements Serializable {

    //自增id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //base coin id
    private Integer baseId;
    // base coin 简称
    private String baseSymbol;
    //quote coin id
    private Integer quoteId;
    // quote coin 简称
    private String quoteSymbol;
    //买或者卖
    private String saleOrBuy;
    //买入或卖出价格
    private String price;
    //基准货币买入或卖出数量
    private String baseNum;
    //计价货币数量
    private String quoteNum;
    //交易时间
    private String tradeDate;
}
