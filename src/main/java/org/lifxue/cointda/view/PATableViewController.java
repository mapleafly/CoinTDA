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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.dao.CoinTypeDao;
import org.lifxue.cointda.dao.PATableDao;
import org.lifxue.cointda.dao.TradeDataDao;
import org.lifxue.cointda.models.TradeData;
import org.lifxue.cointda.util.DateHelper;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class PATableViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(PATableViewController.class.getName());

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private Label coinTypeLabel;
    @FXML
    private Label paLabel;
    @FXML
    private Label numTotalLabel;
    @FXML
    private Label nowPriceTotalLabel;
    @FXML
    private TableView<TradeData> tradeDataTable;
    @FXML
    private TableColumn<TradeData, Integer> idCol;
    @FXML
    private TableColumn<TradeData, String> coinTypeCol;
    @FXML
    private TableColumn<TradeData, String> buyOrSaleCol;
    @FXML
    private TableColumn<TradeData, Double> priceCol;
    @FXML
    private TableColumn<TradeData, Double> numCol;
    @FXML
    private TableColumn<TradeData, Double> totalCol;
    @FXML
    private TableColumn<TradeData, String> dateCol;

    /**
     * The data as an observable list of TradeData.
     */
    private final ObservableList<TradeData> tradeDataList;
    private final List<String> coinTypeShortName;

    public PATableViewController() {
        tradeDataList = FXCollections.observableArrayList();
        PATableDao paDao = new PATableDao();
        List<TradeData> list = paDao.QueryAll();
        tradeDataList.addAll(list);

        CoinTypeDao ctDao = new CoinTypeDao();
        coinTypeShortName = ctDao.QueryAllShortName();
        if (coinTypeShortName == null || coinTypeShortName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("缺少数据");
            alert.setContentText("请首先去设置界面设置coin类型！");
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tradeDataTable.setItems(tradeDataList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        coinTypeCol.setCellValueFactory(cellData -> cellData.getValue().coinTypeProperty());
        buyOrSaleCol.setCellValueFactory(cellData -> cellData.getValue().saleOrBuyProperty());
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        numCol.setCellValueFactory(new PropertyValueFactory<>("num"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        typeChoiceBox.setItems(FXCollections.observableArrayList(coinTypeShortName));
        typeChoiceBox.setTooltip(new Tooltip("选择交易品种"));

        startDatePicker.setConverter(DateHelper.CONVERTER);
        startDatePicker.setTooltip(new Tooltip("选择初始时间"));
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setConverter(DateHelper.CONVERTER);
        endDatePicker.setTooltip(new Tooltip("选择结束时间"));
        endDatePicker.setValue(LocalDate.now());
    }

    @FXML
    private void handleSearchOnAction(ActionEvent event) {
        if (isInputValid()) {
            String coinType = this.typeChoiceBox.getValue();
            String startDate = DateHelper.toString(this.startDatePicker.getValue());
            String endDate = DateHelper.toString(this.endDatePicker.getValue());

            PATableDao dao = new PATableDao();
            List<TradeData> list = dao.QueryByTypeAndDate(coinType, startDate, endDate);
            tradeDataList.clear();
            tradeDataList.addAll(list);

            Map<String, Double> mapTotal = getPAData(coinType, list);
            this.coinTypeLabel.setText(coinType);
            this.nowPriceTotalLabel.setText(Double.toString(mapTotal.get("nowPriceTotal")));
            this.paLabel.setText(Double.toString(mapTotal.get("paPrice")));
            this.numTotalLabel.setText(Double.toString(mapTotal.get("numTotal")));
        }
    }

    private Map<String, Double> getPAData(String strCoinType, List<TradeData> tradeDataList) {
        Map map = new HashMap<String, String>();
        double paPrice = 0, numTotal = 0;
        double buy = 0, sale = 0;

        for (TradeData td : tradeDataList) {
            if (td.getSaleOrBuy().equals("买")) {
                numTotal += td.getNum();
                buy += td.getTotalPrice();
            } else {
                numTotal -= td.getNum();
                sale += td.getTotalPrice();
            }
        }
        CoinTypeDao ctDao = new CoinTypeDao();
        double curPrice = ctDao.QueryOne(strCoinType).getPrice();
        paPrice = (buy - sale) / numTotal;
        map.put("numTotal", numTotal);
        map.put("nowPriceTotal", curPrice * numTotal);
        map.put("paPrice", paPrice);
        return map;
    }

    /**
     * Validates the user inpu.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (typeChoiceBox.getValue() == null || typeChoiceBox.getValue().length() == 0) {
            errorMessage += "无效的类别!\n";
        }
        if (!DateHelper.validDate(DateHelper.toString(startDatePicker.getValue()))
                || startDatePicker.getValue() == null) {
            errorMessage += "无效的时间!\n";
        }
        if (!DateHelper.validDate(DateHelper.toString(endDatePicker.getValue()))
                || endDatePicker.getValue() == null) {
            errorMessage += "无效的时间!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("无效的字段");
            alert.setHeaderText("请修改无效的字段");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
