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
package org.mapleaf.cointda.modules.trade;

import com.dlsc.workbenchfx.Workbench;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.dao.TradeDataDao;
import org.mapleaf.cointda.bean.property.TradeDataFXC;
import org.mapleaf.cointda.util.DateHelper;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class TradeDataEditViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(TradeDataEditViewController.class.getName());

    @FXML
    private TableView<TradeDataFXC> dataTable;
    @FXML
    private TableColumn<TradeDataFXC, Integer> idCol;
    @FXML
    private TableColumn<TradeDataFXC, Integer> coinIdCol;
    @FXML
    private TableColumn<TradeDataFXC, String> symbolCol;
    @FXML
    private TableColumn<TradeDataFXC, String> salebuyCol;
    @FXML
    private TableColumn<TradeDataFXC, String> priceCol;
    @FXML
    private TableColumn<TradeDataFXC, String> numCol;
    @FXML
    private TableColumn<TradeDataFXC, String> totalCol;
    @FXML
    private TableColumn<TradeDataFXC, String> dateCol;

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
    private final ObservableList<TradeDataFXC> tradeDataList;
    private final List<String> coinList;
    private Workbench workbench;

    public TradeDataEditViewController() {

        tradeDataList = FXCollections.observableArrayList();

        coinList = TradeDataDao.queryAllSymbol();
        if (coinList != null && !coinList.isEmpty()) {
            List<TradeDataFXC> list = TradeDataDao.queryAllFXC(coinList.get(0));
            tradeDataList.addAll(list);
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
        dataTable.setItems(tradeDataList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        coinIdCol.setCellValueFactory(new PropertyValueFactory<>("coinId"));
        symbolCol.setCellValueFactory(cellData -> cellData.getValue().coinSymbolProperty());
        salebuyCol.setCellValueFactory(cellData -> cellData.getValue().saleOrBuyProperty());
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        numCol.setCellValueFactory(new PropertyValueFactory<>("num"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        typeChoiceBox.setItems(FXCollections.observableArrayList(coinList));
        typeChoiceBox.setTooltip(new Tooltip("选择交易品种"));
        typeChoiceBox.getSelectionModel().selectFirst();

        salebuyChoiceBox.setItems(FXCollections.observableArrayList("买", "卖"));
        salebuyChoiceBox.setTooltip(new Tooltip("选择交易种类"));

        dateDatePicker.setConverter(DateHelper.CONVERTER);
        //dateDatePicker.setPromptText(pattern.toLowerCase());
        dateDatePicker.setTooltip(new Tooltip("选择交易时间"));

        showTradeDataDetails(null);

        dataTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTradeDataDetails(newValue));

        typeChoiceBox.getSelectionModel().selectedIndexProperty()
                .addListener((ObservableValue<? extends Number> observable,
                        Number oldValue, Number newValue) -> {
                    if (newValue.intValue() >= 0) {
                        String selectedCoin = this.coinList.get(newValue.intValue());
                        tradeDataList.clear();
                        tradeDataList.addAll(TradeDataDao.queryAllFXC(selectedCoin));
                    }
                });

        priceTextField.textProperty()
                .addListener((ObservableValue<? extends String> observable,
                        String oldValue, String newValue) -> {
                    if (!newValue.matches("\\d*(\\.\\d*)?")) {
                        priceTextField.setText(oldValue);
                    }
                });
        numTextField.textProperty()
                .addListener((ObservableValue<? extends String> observable,
                        String oldValue, String newValue) -> {
                    if (!newValue.matches("\\d*(\\.\\d*)?")) {
                        numTextField.setText(oldValue);
                    }
                });
        totalTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    totalTextField.setText(oldValue);
                }
            }
        });

    }

    @FXML
    private void handleAddData(ActionEvent event) {
        if (isInputValid()) {
            TradeDataBean bean = new TradeDataBean();
            bean.setCoin_id(TradeDataDao.queryCoinBySymbol(typeChoiceBox.getValue()).getId());
            bean.setCoin_symbol(typeChoiceBox.getValue());
            bean.setSale_or_buy(salebuyChoiceBox.getValue());
            bean.setPrice(new BigDecimal(priceTextField.getText()));
            bean.setNum(new BigDecimal(numTextField.getText()));
            bean.setTotal_price(new BigDecimal(totalTextField.getText()));
            bean.setTrade_date(DateHelper.toString(this.dateDatePicker.getValue()));

            Integer id = TradeDataDao.insert(bean);
            if (id != -1) {
                bean.setId(id);
                tradeDataList.add(0, beanToFXC(bean));
                priceTextField.setText("");
                numTextField.setText("");
                totalTextField.setText("");
            }
        }

    }

    @FXML
    private void handleEdtitData(ActionEvent event) {
        if (isInputValid()) {
            int selectedIndex = dataTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                TradeDataFXC fxc = dataTable.getItems().get(selectedIndex);
                TradeDataBean bean = fxcToBean(fxc);
                bean.setCoin_symbol(typeChoiceBox.getValue());
                bean.setSale_or_buy(salebuyChoiceBox.getValue());
                bean.setPrice(new BigDecimal(priceTextField.getText()));
                bean.setNum(new BigDecimal(numTextField.getText()));
                bean.setTotal_price(new BigDecimal(totalTextField.getText()));
                bean.setTrade_date(DateHelper.toString(this.dateDatePicker.getValue()));
                int n = TradeDataDao.update(bean);
                if (n == 1) {
                    fxc = beanToFXC(bean);
                    for (int i = 0; i < tradeDataList.size(); i++) {
                        if (tradeDataList.get(i).getId().equals(fxc.getId())) {
                            tradeDataList.remove(i);
                            tradeDataList.add(i, fxc);
                            dataTable.getSelectionModel().select(i);
                            break;
                        }
                    }
                } else {
                    workbench.showErrorDialog(
                            "错误",
                            "数据库更新错误！",
                            "选中数据没有被数据库更新!",
                            buttonType -> {
                            }
                    );
                }
            } else {
                // Nothing selected.
                workbench.showErrorDialog(
                        "提示",
                        "没有选中数据",
                        "请从表格中选择一行数据!",
                        buttonType -> {
                        }
                );
            }
        }
    }

    @FXML
    private void handleDelData(ActionEvent event) {
        int selectedIndex = dataTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            TradeDataFXC fxc = dataTable.getItems().get(selectedIndex);
            if (TradeDataDao.delete(fxcToBean(fxc)) == 1) {
                dataTable.getItems().remove(selectedIndex);
            } else {
                workbench.showErrorDialog(
                        "错误",
                        "数据库删除错误",
                        "选中数据没有被从数据库删除!",
                        buttonType -> {
                        }
                );
            }
        } else {
            // Nothing selected.
            workbench.showErrorDialog(
                    "提示",
                    "没有选中数据",
                    "请从表格中选择一行数据!",
                    buttonType -> {
                    }
            );
        }
    }

    @FXML
    private void handlePriceTextFieldKeyReleased(KeyEvent event) {
        if (numTextField.getText().trim().equals("")
                || priceTextField.getText().trim().equals("")) {
            return;
        }
        BigDecimal num = new BigDecimal(numTextField.getText());
        BigDecimal price = new BigDecimal(priceTextField.getText());
        totalTextField.setText(num.multiply(price).setScale(8, RoundingMode.HALF_UP).toString());
    }

    @FXML
    private void handleNumTextFieldKeyReleased(KeyEvent event) {
        if (numTextField.getText().trim().equals("")
                || priceTextField.getText().trim().equals("")) {
            return;
        }
        BigDecimal num = new BigDecimal(numTextField.getText());
        BigDecimal price = new BigDecimal(priceTextField.getText());
        totalTextField.setText(num.multiply(price).setScale(8, RoundingMode.HALF_UP).toString());
    }

    private TradeDataFXC beanToFXC(TradeDataBean bean) {
        TradeDataFXC fxc = new TradeDataFXC();
        fxc.setId(bean.getId());
        fxc.setCoinId(bean.getCoin_id());
        fxc.setCoinSymbol(bean.getCoin_symbol());
        fxc.setSaleOrBuy(bean.getSale_or_buy());
        fxc.setPrice(bean.getPrice().toString());
        fxc.setNum(bean.getNum().toString());
        fxc.setTotalPrice(bean.getTotal_price().toString());
        fxc.setDate(bean.getTrade_date());
        return fxc;
    }

    private TradeDataBean fxcToBean(TradeDataFXC fxc) {
        TradeDataBean bean = new TradeDataBean();
        bean.setId(fxc.getId());
        bean.setCoin_id(fxc.getCoinId());
        bean.setCoin_symbol(fxc.getCoinSymbol());
        bean.setSale_or_buy(fxc.getSaleOrBuy());
        bean.setPrice(new BigDecimal(fxc.getPrice()));
        bean.setNum(new BigDecimal(fxc.getNum()));
        bean.setTotal_price(new BigDecimal(fxc.getTotalPrice()));
        bean.setTrade_date(fxc.getDate());

        return bean;
    }

    /**
     *
     * @param coinType
     */
    private void showTradeDataDetails(TradeDataFXC tradeData) {
        if (tradeData != null) {
            typeChoiceBox.setValue(tradeData.getCoinSymbol());
            salebuyChoiceBox.setValue(tradeData.getSaleOrBuy());
            priceTextField.setText(tradeData.getPrice());
            numTextField.setText(tradeData.getNum());
            totalTextField.setText(tradeData.getTotalPrice());
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
            workbench.showErrorDialog(
                    "提示",
                    "无效的字段",
                    errorMessage,
                    buttonType -> {
                    }
            );

            return false;
        }
    }

    /**
     * @param workbench the workbench to set
     */
    public void setWorkbench(Workbench workbench) {
        this.workbench = workbench;
    }
}
