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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.bean.property.TradeDataFXC;
import org.mapleaf.cointda.dao.CoinTypeDao;
import org.mapleaf.cointda.dao.TradeDataDao;
import org.mapleaf.cointda.util.DateHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class TradeDataEditViewController implements Initializable {

  private static final Logger logger =
      LogManager.getLogger(TradeDataEditViewController.class.getName());
  /** The data as an observable list of TradeData. */
  private final ObservableList<TradeDataFXC> tradeDataList;

  private final List<String> coinList;
  @FXML private TableView<TradeDataFXC> dataTable;
  @FXML private TableColumn<TradeDataFXC, Integer> idCol;
  @FXML private TableColumn<TradeDataFXC, Integer> coinIdCol;
  @FXML private TableColumn<TradeDataFXC, String> symbolPairsCol;
  @FXML private TableColumn<TradeDataFXC, String> salebuyCol;
  @FXML private TableColumn<TradeDataFXC, String> priceCol;
  @FXML private TableColumn<TradeDataFXC, String> baseNumCol;
  @FXML private TableColumn<TradeDataFXC, String> quoteNumCol;
  @FXML private TableColumn<TradeDataFXC, String> dateCol;
  @FXML private ChoiceBox<String> baseChoiceBox;
  @FXML private ChoiceBox<String> quoteChoiceBox;
  @FXML private ChoiceBox<String> salebuyChoiceBox;
  @FXML private DatePicker dateDatePicker;
  @FXML private TextField priceTextField;
  @FXML private TextField numTextField;
  @FXML private TextField totalTextField;
  private Workbench workbench;

  public TradeDataEditViewController() {

    tradeDataList = FXCollections.observableArrayList();

    coinList = CoinTypeDao.queryCurSymbol();
    if (coinList != null && !coinList.isEmpty()) {
      List<TradeDataFXC> list = TradeDataDao.queryAllFXC(coinList.get(0));
      tradeDataList.addAll(list);
    }
  }

  /**
   * @Description: Initializes the controller class.
   *
   * @param url 1
   * @param rb 2
   * @return: void
   * @author: mapleaf
   * @date: 2020/6/23 16:52
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    dataTable.setItems(tradeDataList);

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    coinIdCol.setCellValueFactory(new PropertyValueFactory<>("coinId"));
    symbolPairsCol.setCellValueFactory(cellData -> cellData.getValue().symbolPairsProperty());
    salebuyCol.setCellValueFactory(cellData -> cellData.getValue().saleOrBuyProperty());
    priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    baseNumCol.setCellValueFactory(new PropertyValueFactory<>("baseNum"));
    quoteNumCol.setCellValueFactory(new PropertyValueFactory<>("quoteNum"));
    dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

    baseChoiceBox.setItems(FXCollections.observableArrayList(coinList));
    baseChoiceBox.setTooltip(new Tooltip("选择基准货币"));
    baseChoiceBox.getSelectionModel().selectFirst();

    quoteChoiceBox.setItems(FXCollections.observableArrayList("USDT"));
    quoteChoiceBox.setTooltip(new Tooltip("选择计价货币"));
    quoteChoiceBox.getSelectionModel().selectFirst();

    salebuyChoiceBox.setItems(FXCollections.observableArrayList("买", "卖"));
    salebuyChoiceBox.setTooltip(new Tooltip("选择交易类型"));

    dateDatePicker.setConverter(DateHelper.CONVERTER);
    // dateDatePicker.setPromptText(pattern.toLowerCase());
    dateDatePicker.setTooltip(new Tooltip("选择交易时间"));

    showTradeDataDetails(null);

    dataTable
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> showTradeDataDetails(newValue));

    baseChoiceBox
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
              if (newValue.intValue() >= 0) {
                String selectedCoin = this.coinList.get(newValue.intValue());
                tradeDataList.clear();
                tradeDataList.addAll(TradeDataDao.queryAllFXC(selectedCoin));
              }
            });

    priceTextField
        .textProperty()
        .addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
              if (!newValue.matches("\\d*(\\.\\d*)?")) {
                priceTextField.setText(oldValue);
              }
            });
    numTextField
        .textProperty()
        .addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
              if (!newValue.matches("\\d*(\\.\\d*)?")) {
                numTextField.setText(oldValue);
              }
            });
    totalTextField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue.matches("\\d*(\\.\\d*)?")) {
                totalTextField.setText(oldValue);
              }
            });
  }

  @FXML
  private void handleAddData(ActionEvent event) {
    if (isInputValid()) {
      TradeDataBean bean = new TradeDataBean();
      setTradeDataBean(bean);
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

  private  void setTradeDataBean(TradeDataBean bean) {
    bean.setBase_id(TradeDataDao.queryCoinBySymbol(baseChoiceBox.getValue()).getId());
    bean.setBase_symbol(baseChoiceBox.getValue());
    bean.setQuote_id(TradeDataDao.queryCoinBySymbol(quoteChoiceBox.getValue()).getId());
    bean.setQuote_symbol(quoteChoiceBox.getValue());
    bean.setSale_or_buy(salebuyChoiceBox.getValue());
    bean.setPrice(priceTextField.getText());
    bean.setBase_num(numTextField.getText());
    bean.setQuote_num(totalTextField.getText());
    bean.setTrade_date(DateHelper.toString(this.dateDatePicker.getValue()));

  }

  @FXML
  private void handleEdtitData(ActionEvent event) {
    if (isInputValid()) {
      int selectedIndex = dataTable.getSelectionModel().getSelectedIndex();
      if (selectedIndex >= 0) {
        TradeDataFXC fxc = dataTable.getItems().get(selectedIndex);
        TradeDataBean bean = TradeDataDao.queryBean(fxc.getId());
        setTradeDataBean(bean);
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
          workbench.showErrorDialog("错误", "数据库更新错误！", "选中数据没有被数据库更新!", buttonType -> {});
        }
      } else {
        // Nothing selected.
        workbench.showErrorDialog("提示", "没有选中数据", "请从表格中选择一行数据!", buttonType -> {});
      }
    }
  }

  @FXML
  private void handleDelData(ActionEvent event) {
    int selectedIndex = dataTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
      TradeDataFXC fxc = dataTable.getItems().get(selectedIndex);
      if (TradeDataDao.delete(fxc.getId()) == 1) {
        // 整理表格
        dataTable.getItems().remove(selectedIndex);
      } else {
        workbench.showErrorDialog("错误", "数据库删除错误", "选中数据没有被从数据库删除!", buttonType -> {});
      }
    } else {
      // Nothing selected.
      workbench.showErrorDialog("提示", "没有选中数据", "请从表格中选择一行数据!", buttonType -> {});
    }
  }

  @FXML
  private void handlePriceTextFieldKeyReleased(KeyEvent event) {
    if (numTextField.getText().trim().equals("") || priceTextField.getText().trim().equals("")) {
      return;
    }
    BigDecimal num = new BigDecimal(numTextField.getText());
    BigDecimal price = new BigDecimal(priceTextField.getText());
    totalTextField.setText(num.multiply(price).setScale(12, RoundingMode.HALF_UP).toString());
  }

  @FXML
  private void handleNumTextFieldKeyReleased(KeyEvent event) {
    if (numTextField.getText().trim().equals("") || priceTextField.getText().trim().equals("")) {
      return;
    }
    BigDecimal num = new BigDecimal(numTextField.getText());
    BigDecimal price = new BigDecimal(priceTextField.getText());
    totalTextField.setText(num.multiply(price).setScale(12, RoundingMode.HALF_UP).toString());
  }

  private TradeDataFXC beanToFXC(TradeDataBean bean) {
    TradeDataFXC fxc = new TradeDataFXC();
    fxc.setId(bean.getId());
    fxc.setCoinId(bean.getBase_id());
    fxc.setSymbolPairs(bean.getBase_symbol() + "/" + bean.getQuote_symbol());
    fxc.setSaleOrBuy(bean.getSale_or_buy());
    fxc.setPrice(bean.getPrice());
    fxc.setBaseNum(bean.getBase_num());
    fxc.setQuoteNum(bean.getQuote_num());
    fxc.setDate(bean.getTrade_date());
    return fxc;
  }

  /**
   * @Description:
   *
   * @param tradeData 1
   * @return: void
   * @author: mapleaf
   * @date: 2020/6/23 16:54
   */
  private void showTradeDataDetails(TradeDataFXC tradeData) {
    if (tradeData != null) {
      String symbolPairs = tradeData.getSymbolPairs();
      int split = symbolPairs.indexOf('/');
      if (split < 1) {
        logger.error("交易对是空值或者格式错误");
        return;
      }
      String base = symbolPairs.substring(0, split);
      String quote = symbolPairs.substring(split + 1);
      baseChoiceBox.setValue(base);
      quoteChoiceBox.setValue(quote);
      salebuyChoiceBox.setValue(tradeData.getSaleOrBuy());
      priceTextField.setText(tradeData.getPrice());
      numTextField.setText(tradeData.getBaseNum());
      totalTextField.setText(tradeData.getQuoteNum());
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
   * @Description:
   *
   * @return: boolean
   * @author: mapleaf
   * @date: 2020/6/23 16:56
   */
  private boolean isInputValid() {
    String errorMessage = "";

    if (baseChoiceBox.getValue() == null || baseChoiceBox.getValue().length() == 0) {
      errorMessage += "无效的类别!\n";
    }
    if (quoteChoiceBox.getValue() == null || quoteChoiceBox.getValue().length() == 0) {
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
      workbench.showErrorDialog("提示", "无效的字段", errorMessage, buttonType -> {});

      return false;
    }
  }

  /**
   * @Description:
   *
   * @param workbench 1
   * @return: void
   * @author: mapleaf
   * @date: 2020/6/23 16:55
   */
  public void setWorkbench(Workbench workbench) {
    this.workbench = workbench;
  }
}
