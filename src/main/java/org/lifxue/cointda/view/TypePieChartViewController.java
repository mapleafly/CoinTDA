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
package org.lifxue.cointda.view;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.lifxue.cointda.dao.CoinTypeDao;
import org.lifxue.cointda.dao.TypePieChartDao;
import org.lifxue.cointda.models.CoinType;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class TypePieChartViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(TypePieChartViewController.class.getName());

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
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.addAll(getData());
        pieChart.setData(pieChartData);

        double n = 0;
        for (PieChart.Data data : pieChart.getData()) {
            n += data.getPieValue();
        }
        final double total = n;

        pieChart.getData().forEach(data -> {
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            String p = decimalFormat.format(data.getPieValue() / total * 100);
            String t = decimalFormat.format(data.getPieValue());
            Tooltip toolTip = new Tooltip(data.getName() + "总价:" + t + "； 占比:" + p + "%");
            toolTip.setFont(new Font("Arial", 20));
            Tooltip.install(data.getNode(), toolTip);
        });

    }

    /**
     * 生成饼图数据
     * @return 
     */
    private List<PieChart.Data> getData() {
        List<PieChart.Data> list = new ArrayList<PieChart.Data>();
        TypePieChartDao dao = new TypePieChartDao();
        CoinTypeDao ctDao = new CoinTypeDao();
        List<Map<String, Double>> buyList = dao.QueryBuyNum();
        List<Map<String, Double>> saleList = dao.QuerySaleNum();
        buyList.forEach((buy) -> {
            Iterator<String> buyIter = buy.keySet().iterator();
            if (buyIter.hasNext()) {
                String buyKey = (String) buyIter.next();
                double saleNum = 0;
                for (Map<String, Double> sale : saleList) {
                    if (sale.get(buyKey) == null) {
                        continue;
                    }
                    saleNum = (double) sale.get(buyKey);
                }
                double num = (double) buy.get(buyKey) - saleNum;
                if (num > 0) {
                    List<CoinType> ctList = ctDao.QueryAll();
                    double price = 0;
                    for (CoinType ct : ctList) {
                        if (ct.getShortName().equals(buyKey)) {
                            price = ct.getPrice();
                        }
                    }
                    list.add(new PieChart.Data(buyKey, num * price));
                }
            }
        });

        return list;
    }

}
