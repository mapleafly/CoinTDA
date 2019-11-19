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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.dao.TypePieChartDao;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class TypePieChartViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(TypePieChartViewController.class.getName());

    @FXML
    private PieChart pieChart;
    @FXML
    private Label caption;

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

        caption.setTextFill(Color.DARKORANGE);
        caption.setFont(new Font("Arial", 30));
        
        double n = 0;
        for (PieChart.Data data : pieChart.getData()) {
            n += data.getPieValue();
        }
        final double total = n;
        
        pieChart.getData().forEach((PieChart.Data data) -> {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                DecimalFormat decimalFormat = new DecimalFormat(".00");
                String p = decimalFormat.format(data.getPieValue() / total * 100);
                caption.setText(p + "%");
                
            });
        });

    }

    private List<PieChart.Data> getData() {
        List list = new ArrayList<PieChart.Data>();
        TypePieChartDao dao = new TypePieChartDao();
        List<Map<String, Double>> buyList = dao.QueryBuyTotal();
        List<Map<String, Double>> saleList = dao.QuerySaleTotal();
        buyList.forEach((buy) -> {
            Iterator<String> buyIter = buy.keySet().iterator();
            if (buyIter.hasNext()) {
                String buyKey = (String) buyIter.next();
                double saleTotal = 0;
                for (Map sale : saleList) {
                    if (sale.get(buyKey) == null) {
                        continue;
                    }
                    saleTotal = (double) sale.get(buyKey);
                }
                double total = (double) buy.get(buyKey) - saleTotal;
                if (total > 0) {
                    list.add(new PieChart.Data(buyKey, total));
                }
            }
        });

        return list;
    }

}
