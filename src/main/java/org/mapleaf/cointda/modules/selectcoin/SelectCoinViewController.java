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
package org.mapleaf.cointda.modules.selectcoin;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CurUsedCoinBean;
import org.mapleaf.cointda.dao.CoinTypeDao;
import org.mapleaf.cointda.dao.CurUsedCoinDao;
import org.mapleaf.cointda.bean.property.CoinTypeFXC;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class SelectCoinViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(SelectCoinViewController.class.getName());

    @FXML
    private TableView<CoinTypeFXC> priceTable;

    @FXML
    private TableColumn<CoinTypeFXC, String> idCol;
    @FXML
    private TableColumn<CoinTypeFXC, Boolean> selectCol;
    @FXML
    private TableColumn<CoinTypeFXC, String> nameCol;
    @FXML
    private TableColumn<CoinTypeFXC, String> symbolCol;
    @FXML
    private TableColumn<CoinTypeFXC, String> rankCol;
    @FXML
    private TableColumn<CoinTypeFXC, String> priceCol;
    @FXML
    private TableColumn<CoinTypeFXC, String> dateCol;

    @FXML
    private TextField searchField;

    private final ObservableList<CoinTypeFXC> coinTypeData;

    public List<CoinTypeFXC> updateData;

    public SelectCoinViewController() {
        this.coinTypeData = FXCollections.observableArrayList();

        List<CoinTypeFXC> list = CoinTypeDao.queryAll();
        coinTypeData.addAll(list);
        updateData = list;

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        priceTable.setItems(coinTypeData);
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        selectCol.setCellValueFactory(new PropertyValueFactory<>("select"));
        selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));

        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        symbolCol.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
        rankCol.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        List<CoinTypeFXC> list = CoinTypeDao.queryCurAll();
        for (CoinTypeFXC coin : list) {
            for (int i = 0; i < coinTypeData.size(); i++) {
                if (coin.getId().equals(coinTypeData.get(i).getId())) {
                    coinTypeData.get(i).setSelect(true);
                    break;
                }
            }
        }

//        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//            logger.info(searchField.getText().trim());
//            coinTypeData.clear();
//            coinTypeData.addAll(CoinTypeDao.queryBySymbol(searchField.getText().trim()));
//
//        });
//        priceCol.setCellFactory(cellData -> {
//            TextField priceTextField = new TextField();
//            TableCell<CoinTypeFXC, String> cell = new TableCell<CoinTypeFXC, String>() {
//                @Override
//                protected void updateItem(String chuzhi, boolean empty) {
//                    super.updateItem(chuzhi, empty);
//                    if (empty) {
//                        setGraphic(null);
//                    } else {
//                        String price = priceTable.getItems().get(this.getIndex()).getPrice();
//                        if (price == null || price.isEmpty() || price.equals("-1")) {
//                            priceTextField.setText("0");
//                        } else {
//                            priceTextField.setText(price);
//                        }
//                        setGraphic(priceTextField);
//                    }
//                }
//            };
//
//            priceTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//                if (!newValue.matches("\\d*(\\.\\d*)?")) {
//                    priceTextField.setText(oldValue);
//                }
//                int ind = cell.getIndex();//获取正在编辑的单元格所在行序号
//                BigDecimal value;
//                try {
//                    value = new BigDecimal(priceTextField.getText());
//                } catch (NumberFormatException | NullPointerException e) {
//                    value = new BigDecimal("0");
//                }
//                String id = priceTable.getItems().get(ind).getId();
//                for (int i = 0; i < updateData.size(); i++) {
//                    if (updateData.get(i).getId().equals(id)) {
//                        updateData.get(i).setPrice(value.toString());
//                    }
//                }
//
//            });
//            return cell;
//        });
//        dateCol.setCellFactory(cellData -> {
//            DatePicker datePicker = new DatePicker();
//            datePicker.setEditable(true);
//            TableCell<CoinTypeFXC, String> cell = new TableCell<CoinTypeFXC, String>() {
//                @Override
//                protected void updateItem(String chuzhi, boolean empty) {
//                    super.updateItem(chuzhi, empty);
//                    if (empty) {
//                        setGraphic(null);
//                    } else {
//                        datePicker.setConverter(DateHelper.CONVERTER);
//
//                        String date = priceTable.getItems().get(this.getIndex()).getDate();
//                        if (date == null || date.trim().isEmpty()) {
//                            datePicker.setValue(LocalDate.now());
//                        } else {
//                            datePicker.setValue(DateHelper.fromString(date));
//                        }
//                        String id = priceTable.getItems().get(this.getIndex()).getId();
//                        String value = DateHelper.toString(datePicker.getValue());
//                        for (int i = 0; i < updateData.size(); i++) {
//                            if (updateData.get(i).getId().equals(id)) {
//                                updateData.get(i).setDate(value);
//                            }
//                        }
//                        setGraphic(datePicker);
//                    }
//                }
//            };
//
//            datePicker.setOnAction(e -> {
//                int rank = cell.getIndex();//获取正在编辑的单元格所在行序号
//                String value = DateHelper.toString(datePicker.getValue());//获取正在编辑的单元格值
//                String id = priceTable.getItems().get(rank).getId();
//                for (int i = 0; i < updateData.size(); i++) {
//                    if (updateData.get(i).getId().equals(id)) {
//                        updateData.get(i).setDate(value);
//                    }
//                }
//            });
//
//            return cell;
//
//        });
    }

    @FXML
    private void handleSave(ActionEvent event) {
        List<CurUsedCoinBean> dbCurList = CurUsedCoinDao.queryCurAll();
        List<CurUsedCoinBean> batchDelList = new ArrayList<>();
        List<CurUsedCoinBean> batchInsertList = new ArrayList<>();
        for (int i = 0; i < priceTable.getItems().size(); i++) {
            CurUsedCoinBean bean = new CurUsedCoinBean();
            bean.setId(Integer.valueOf(priceTable.getItems().get(i).getId()));
            bean.setSymbol(priceTable.getItems().get(i).getSymbol());
            bean.setCmc_rank(Integer.valueOf(priceTable.getItems().get(i).getRank()));
            bean.setLastUpdated(priceTable.getItems().get(i).getDate());
            if (priceTable.getItems().get(i).getSelect()) {
                boolean is = false;
                for (CurUsedCoinBean dbCur : dbCurList) {
                    if (dbCur.getId().equals(bean.getId())) {
                        is = true;
                        break;
                    }
                }
                if (!is) {
                    batchInsertList.add(bean);
                }
            } else {
                for (CurUsedCoinBean dbCur : dbCurList) {
                    if (dbCur.getId().equals(bean.getId())) {
                        batchDelList.add(bean);
                    }
                }
            }
        }

        if (CurUsedCoinDao.batchDelete(batchDelList).length == batchDelList.size()) {
            if (CurUsedCoinDao.batchInsert(batchInsertList).length == 
                    batchInsertList.size()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("完成");
                alert.setHeaderText("完成保存操作");
                alert.setContentText("已经保存选中数据。");

                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleSearchFieldKeyReleased(KeyEvent event) {
        coinTypeData.clear();
        coinTypeData.addAll(CoinTypeDao.queryBySymbol(searchField.getText().trim()));
        List<CoinTypeFXC> list = CoinTypeDao.queryCurAll();
        for (CoinTypeFXC coin : list) {
            for (int i = 0; i < coinTypeData.size(); i++) {
                if (coin.getId().equals(coinTypeData.get(i).getId())) {
                    coinTypeData.get(i).setSelect(true);
                    break;
                }
            }
        }

    }

}
