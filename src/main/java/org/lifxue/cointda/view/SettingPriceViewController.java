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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.bean.CurUsedCoinBean;
import org.lifxue.cointda.dao.CoinTypeDao;
import org.lifxue.cointda.dao.CurUsedCoinDao;
import org.lifxue.cointda.models.CoinType;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class SettingPriceViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(SettingPriceViewController.class.getName());

    @FXML
    private TableView<CoinType> priceTable;

    @FXML
    private TableColumn<CoinType, String> idCol;
    @FXML
    private TableColumn<CoinType, Boolean> selectCol;
    @FXML
    private TableColumn<CoinType, String> nameCol;
    @FXML
    private TableColumn<CoinType, String> symbolCol;
    @FXML
    private TableColumn<CoinType, String> rankCol;
    @FXML
    private TableColumn<CoinType, String> priceCol;
    @FXML
    private TableColumn<CoinType, String> dateCol;

    private final ObservableList<CoinType> coinTypeData;

    public List<CoinType> updateData;

    public SettingPriceViewController() {
        this.coinTypeData = FXCollections.observableArrayList();

        List<CoinType> list = CoinTypeDao.queryAll();
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

        List<CoinType> list = CoinTypeDao.queryCurAll();
        for (CoinType coin : list) {
            for (int i = 0; i < coinTypeData.size(); i++) {
                if (coin.getId().equals(coinTypeData.get(i).getId())) {
                    coinTypeData.get(i).setSelect(true);
                    break;
                }
            }
        }

//        priceCol.setCellFactory(cellData -> {
//            TextField priceTextField = new TextField();
//            TableCell<CoinType, String> cell = new TableCell<CoinType, String>() {
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
//            TableCell<CoinType, String> cell = new TableCell<CoinType, String>() {
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
        List<CurUsedCoinBean> list = new ArrayList<>();
        for (int i = 0; i < priceTable.getItems().size(); i++) {
            if (priceTable.getItems().get(i).getSelect()) {
                CurUsedCoinBean bean = new CurUsedCoinBean();
                bean.setId(Integer.valueOf(priceTable.getItems().get(i).getId()));
                bean.setSymbol(priceTable.getItems().get(i).getSymbol());
                bean.setCmc_rank(Integer.valueOf(priceTable.getItems().get(i).getRank()));
                bean.setLastUpdated(priceTable.getItems().get(i).getDate());
                list.add(bean);
            }
        }
        if (CurUsedCoinDao.truncate()) {
            if (CurUsedCoinDao.batchInsert(list).length == list.size()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("完成");
                alert.setHeaderText("完成保存操作");
                alert.setContentText("已经保存选中数据。");

                alert.showAndWait();
            }
        }
    }

}
