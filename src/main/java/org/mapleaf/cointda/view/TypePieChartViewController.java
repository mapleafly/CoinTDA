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
package org.mapleaf.cointda.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinMarketCapListingBean;
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.dao.TypePieChartDao;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class TypePieChartViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(
            TypePieChartViewController.class.getName());

    @FXML
    private PieChart pieChart;

    /**
     *
     */
    public TypePieChartViewController() {

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<PieChart.Data> pieChartData = FXCollections
                .observableArrayList();
        pieChartData.addAll(getData());
        pieChart.setData(pieChartData);
        double n = 0;
        for (PieChart.Data data : pieChart.getData()) {
            n += data.getPieValue();
        }
        final double total = n;

        pieChart.getData().forEach(data -> {
            //建立货币格式化引用 
            NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
            //建立百分比格式化引用 
            NumberFormat percent = NumberFormat.getPercentInstance();
            //百分比小数点最多3位 
            percent.setMaximumFractionDigits(3);
            Tooltip toolTip = new Tooltip(data.getName()
                    + "总价:" + currency.format(data.getPieValue())
                    + "； 占比:" + percent.format(data.getPieValue() / total));
            toolTip.setFont(new Font("Arial", 20));
            Tooltip.install(data.getNode(), toolTip);
        });

    }

    /**
     * 生成饼图数据
     *
     * @return
     */
    private List<PieChart.Data> getData() {
        List<PieChart.Data> list = new ArrayList<>();
        List<TradeDataBean> tdList = TypePieChartDao.queryAllTradeData();
        List<CoinMarketCapListingBean> typeList = TypePieChartDao.queryByTradeData();

        typeList.forEach(coinType -> {
            Integer id = coinType.getId();
            String symbol = coinType.getSymbol();
            BigDecimal price = coinType.getPrice();
            BigDecimal buyNum = new BigDecimal("0");
            BigDecimal saleNum = new BigDecimal("0");
            for (TradeDataBean bean : tdList) {
                if (bean.getCoin_id().intValue() == id.intValue()) {
                    if (bean.getSale_or_buy().equals("买")) {
                        buyNum = buyNum.add(bean.getNum());
                    } else if (bean.getSale_or_buy().equals("卖")) {
                        saleNum = saleNum.add(bean.getNum());
                    }
                }
            }
            list.add(new PieChart.Data(symbol, buyNum.subtract(saleNum)
                    .multiply(price).setScale(8, RoundingMode.HALF_UP).doubleValue()));
        });

        return list;
    }

}
