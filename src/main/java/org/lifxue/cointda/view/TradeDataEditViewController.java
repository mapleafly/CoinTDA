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
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.dao.CoinTypeDao;
import org.lifxue.cointda.dao.TradeDataDao;
import org.lifxue.cointda.models.TradeData;
import org.lifxue.cointda.util.DateHelper;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class TradeDataEditViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(TradeDataEditViewController.class.getName());

    @FXML
    private TableView<TradeData> dataTable;
    @FXML
    private TableColumn<TradeData, Integer> idColumn;
    @FXML
    private TableColumn<TradeData, String> typeColumn;
    @FXML
    private TableColumn<TradeData, String> salebuyColumn;
    @FXML
    private TableColumn<TradeData, Double> priceColumn;
    @FXML
    private TableColumn<TradeData, Double> numColumn;
    @FXML
    private TableColumn<TradeData, Double> totalColumn;
    @FXML
    private TableColumn<TradeData, String> dateColumn;
    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private ChoiceBox<String> salebuyChoiceBox;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField numTextField;
    @FXML
    private TextField totalTextField;

    /**
     * The data as an observable list of TradeData.
     */
    private final ObservableList<TradeData> tradeDataList;
    private final List<String> coinTypeShortName;

    public TradeDataEditViewController() {

        tradeDataList = FXCollections.observableArrayList();
        TradeDataDao tdDao = new TradeDataDao();
        List<TradeData> list = tdDao.QueryAll();
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
    public void initialize(URL url, ResourceBundle rb ) {
        dataTable.setItems(tradeDataList);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().coinTypeProperty());
        salebuyColumn.setCellValueFactory(cellData -> cellData.getValue().saleOrBuyProperty());
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        numColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        typeChoiceBox.setItems(FXCollections.observableArrayList(coinTypeShortName));
        typeChoiceBox.setTooltip(new Tooltip("选择交易品种"));

        salebuyChoiceBox.setItems(FXCollections.observableArrayList("买", "卖"));
        salebuyChoiceBox.setTooltip(new Tooltip("选择交易种类"));

        dateDatePicker.setConverter(DateHelper.CONVERTER);
        //dateDatePicker.setPromptText(pattern.toLowerCase());
        dateDatePicker.setTooltip(new Tooltip("选择交易时间"));

        showTradeDataDetails(null);

        dataTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTradeDataDetails(newValue));

        priceTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                priceTextField.setText(oldValue);
            }
        });
        numTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                numTextField.setText(oldValue);
            }
        });
        totalTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    totalTextField.setText(oldValue);
                }
            }
        });

    }

    @FXML
    private void handleAddData(ActionEvent event) {
        if (isInputValid()) {
            String coinType = this.typeChoiceBox.getValue();
            String sob = this.salebuyChoiceBox.getValue();
            double price = Double.parseDouble(this.priceTextField.getText());
            double num = Double.parseDouble(this.numTextField.getText());
            double total = Double.parseDouble(this.totalTextField.getText());
            String date = DateHelper.toString(this.dateDatePicker.getValue());
            TradeData td = new TradeData(coinType, sob, price, num, total, date);
            TradeDataDao dao = new TradeDataDao();
            int key = dao.insert(td);
            if (key != -1) {
                td.setId(key);
                tradeDataList.add(td);
                priceTextField.setText("");
                numTextField.setText("");
                totalTextField.setText("");
            }
        }

    }

    @FXML
    private void handleEdtitData(ActionEvent event) {
        if (isInputValid()) {
            String coinType = this.typeChoiceBox.getValue();
            String sob = this.salebuyChoiceBox.getValue();
            double price = Double.parseDouble(this.priceTextField.getText());
            double num = Double.parseDouble(this.numTextField.getText());
            double total = Double.parseDouble(this.totalTextField.getText());
            String date = DateHelper.toString(this.dateDatePicker.getValue());
            TradeData td = new TradeData(coinType, sob, price, num, total, date);
            int selectedIndex = dataTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                td.setId(dataTable.getItems().get(selectedIndex).getId());
                TradeDataDao dao = new TradeDataDao();
                if (dao.update(td)) {
                    for (int i = 0; i < tradeDataList.size(); i++) {
                        if (tradeDataList.get(i).getId() == td.getId()) {
                            tradeDataList.remove(i);
                            tradeDataList.add(i, td);
                            dataTable.getSelectionModel().select(i);
                            break;
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误提示");
                    alert.setHeaderText("数据库更新错误");
                    alert.setContentText("选中数据没有被数据库更新!");
                    alert.showAndWait();
                }
            } else {
                // Nothing selected.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText("没有选中数据!");
                alert.setContentText("请从表格中选择一行数据。");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleDelData(ActionEvent event) {
        int selectedIndex = dataTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            TradeData td = dataTable.getItems().get(selectedIndex);
            TradeDataDao dao = new TradeDataDao();
            if (dao.delete(td)) {
                dataTable.getItems().remove(selectedIndex);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误提示");
                alert.setHeaderText("数据库删除错误");
                alert.setContentText("选中数据没有被从数据库删除!");
                alert.showAndWait();
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("没有选中数据!");
            alert.setContentText("请从表格中选择一行数据。");
            alert.showAndWait();
        }
    }

    @FXML
    private void handlePriceTextFieldKeyReleased(KeyEvent event) {
        if (numTextField.getText().trim().equals("")
                || priceTextField.getText().trim().equals("")) {
            return;
        }
        double num = Double.parseDouble(numTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        totalTextField.setText(Double.toString(price * num));
    }

    @FXML
    private void handleNumTextFieldKeyReleased(KeyEvent event) {
        if (numTextField.getText().trim().equals("")
                || priceTextField.getText().trim().equals("")) {
            return;
        }
        double num = Double.parseDouble(numTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        totalTextField.setText(Double.toString(price * num));
    }

    /**
     *
     * @param coinType
     */
    private void showTradeDataDetails(TradeData tradeData) {
        if (tradeData != null) {
            typeChoiceBox.setValue(tradeData.getCoinType());
            salebuyChoiceBox.setValue(tradeData.getSaleOrBuy());
            priceTextField.setText(Double.toString(tradeData.getPrice()));
            numTextField.setText(Double.toString(tradeData.getNum()));
            totalTextField.setText(Double.toString(tradeData.getTotalPrice()));
            dateDatePicker.setValue(DateHelper.fromString(tradeData.getDate()));
        } else {
            salebuyChoiceBox.setValue("买");
            priceTextField.setText("");
            numTextField.setText("");
            totalTextField.setText("");
            dateDatePicker.setValue(LocalDate.now());
        }
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
        if (salebuyChoiceBox.getValue() == null || salebuyChoiceBox.getValue().length() == 0) {
            errorMessage += "无效的买/卖!\n";
        }
        if (!DateHelper.validDate(DateHelper.toString(dateDatePicker.getValue()))
                || dateDatePicker.getValue() == null) {
            errorMessage += "无效的时间!\n";
        }

        if (priceTextField.getText() == null || priceTextField.getText().length() == 0) {
            errorMessage += "无效的单价!\n";
        } else {
            // try to parse the price into an double.
            try {
                Double.parseDouble(priceTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "无效的单价(必须是整数或小数)!\n";
            }
        }

        if (numTextField.getText() == null || numTextField.getText().length() == 0) {
            errorMessage += "无效的数量!\n";
        } else {
            // try to parse the num into an double.
            try {
                Double.parseDouble(numTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "无效的数量(必须是整数或小数)!\n";
            }
        }

        if (totalTextField.getText() == null || totalTextField.getText().length() == 0) {
            errorMessage += "无效的总价!\n";
        } else {
            // try to parse the total into an double.
            try {
                Double.parseDouble(totalTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "无效的总价(必须是整数或小数)!\n";
            }
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
