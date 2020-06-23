/*
 * Copyright 2019 xuelf.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mapleaf.cointda.modules.piechart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinQuotesLatestBean;
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.dao.TypePieChartDao;
import org.mapleaf.cointda.enums.BooleanEnum;
import org.mapleaf.cointda.util.PrefsHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class TypePieChartViewController implements Initializable {

  private static final Logger logger =
      LogManager.getLogger(TypePieChartViewController.class.getName());

  @FXML private PieChart pieChart;
  @FXML private Label totalPrice;

  /** */
  public TypePieChartViewController() {}

  /**
   * @Description: Initializes the controller class.
   *
   * @param url 1
   * @param rb 2
   * @return: void
   * @author: mapleaf
   * @date: 2020/6/23 18:38
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    pieChartData.addAll(getData());
    pieChart.setData(pieChartData);
    double n = 0;
    for (PieChart.Data data : pieChart.getData()) {
      n += data.getPieValue();
    }
    final double total = n;
    totalPrice.setText("当前总价值约:" + Math.round(total));
    pieChart
        .getData()
        .forEach(
            data -> {
              // 建立货币格式化引用
              NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
              // 建立百分比格式化引用
              NumberFormat percent = NumberFormat.getPercentInstance();
              // 百分比小数点最多3位
              percent.setMaximumFractionDigits(3);
              Tooltip toolTip =
                  new Tooltip(
                      data.getName()
                          + "总价:"
                          + currency.format(data.getPieValue())
                          + "； 占比:"
                          + percent.format(data.getPieValue() / total));
              toolTip.setFont(new Font("Arial", 20));
              Tooltip.install(data.getNode(), toolTip);
            });
  }

  /**
   * @Description: 生成饼图数据
   *
   * @return: java.util.List<javafx.scene.chart.PieChart.Data>
   * @author: mapleaf
   * @date: 2020/6/23 18:39
   */
  private List<PieChart.Data> getData() {
    List<PieChart.Data> list = new ArrayList<>();
    List<TradeDataBean> tdList = TypePieChartDao.queryAllTradeData();
    List<CoinQuotesLatestBean> typeList = TypePieChartDao.queryByTradeData();
    BigDecimal usdtNum = new BigDecimal("0");
    // 计算USDT之外的coin现价
    double otherAllPrice = 0;
    for (CoinQuotesLatestBean coinType : typeList) {
      Integer id = coinType.getId();
      String symbol = coinType.getSymbol();
      BigDecimal price;
      if (coinType.getPrice() == null || coinType.getPrice().isBlank()) {
        price = new BigDecimal("0");
      } else {
        price = new BigDecimal(coinType.getPrice());
      }

      BigDecimal buyNum = new BigDecimal("0");
      BigDecimal saleNum = new BigDecimal("0");
      for (TradeDataBean bean : tdList) {
        if (bean.getBase_id().intValue() == id.intValue()) {
          if (coinType.getSymbol().equals("USDT")) {
            if (bean.getSale_or_buy().equals("买")) {
              usdtNum = usdtNum.subtract(new BigDecimal(bean.getQuote_num()));
            } else if (bean.getSale_or_buy().equals("卖")) {
              usdtNum = usdtNum.add(new BigDecimal(bean.getQuote_num()));
            }
          } else {
            if (bean.getSale_or_buy().equals("买")) {
              buyNum = buyNum.add(new BigDecimal(bean.getBase_num()));
              usdtNum = usdtNum.subtract(new BigDecimal(bean.getQuote_num()));
            } else if (bean.getSale_or_buy().equals("卖")) {
              saleNum = saleNum.add(new BigDecimal(bean.getBase_num()));
              usdtNum = usdtNum.add(new BigDecimal(bean.getQuote_num()));
            }
          }
        }
      }
      double allPrice =
          buyNum.subtract(saleNum).multiply(price).setScale(12, RoundingMode.HALF_UP).doubleValue();
      // 忽略总价小的coin
      String notSmallCoinValue =
          PrefsHelper.getPreferencesValue(PrefsHelper.NOTSMALLCOIN, BooleanEnum.NO.toString());
      BooleanEnum notSmallCoinEnum = BooleanEnum.valueOf(notSmallCoinValue);
      if (notSmallCoinEnum.equals(BooleanEnum.YES)) {
        String notSmallCoinNumValue =
            PrefsHelper.getPreferencesValue(PrefsHelper.NOTSMALLCOINNUM, "100");
        if (allPrice > Integer.parseInt(notSmallCoinNumValue)) {
          list.add(new PieChart.Data(symbol, allPrice));
        } else {
          otherAllPrice += allPrice;
        }
      } else {
        list.add(new PieChart.Data(symbol, allPrice));
      }
    }
    // 低于设定价格的种类合计到其他里面
    list.add(new PieChart.Data("其他", otherAllPrice));
    // 计算USDT数量
    list.add(new PieChart.Data("USDT", usdtNum.doubleValue()));
    return list;
  }
}
