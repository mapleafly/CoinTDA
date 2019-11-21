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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.dao.CoinTypeDao;
import org.lifxue.cointda.models.CoinType;
import org.lifxue.cointda.util.DateHelper;

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
    private TableColumn<CoinType, String> typeColumn;
    @FXML
    private TableColumn<CoinType, Double> priceColumn;
    @FXML
    private TableColumn<CoinType, String> dateColumn;

    private final ObservableList<CoinType> coinTypeData;

    public List<CoinType> updateData;

    public SettingPriceViewController() {
        this.coinTypeData = FXCollections.observableArrayList();

        CoinTypeDao dao = new CoinTypeDao();
        List<CoinType> list = dao.QueryAll();
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
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().shortNameProperty());
        priceColumn.setCellFactory(cellData -> {
            TextField priceTextField = new TextField();
            TableCell<CoinType, Double> cell = new TableCell<CoinType, Double>() {
                @Override
                protected void updateItem(Double chuzhi, boolean empty) {
                    super.updateItem(chuzhi, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        double price = priceTable.getItems().get(this.getIndex()).getPrice();
                        if (price == -1) {
                            priceTextField.setText("0");
                        } else {
                            priceTextField.setText(Double.toString(price));
                        }
                        setGraphic(priceTextField);
                    }
                }
            };

            priceTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    priceTextField.setText(oldValue);
                }
                int rank = cell.getIndex();//获取正在编辑的单元格所在行序号
                double value = 0;
                try {
                    value = Double.parseDouble(priceTextField.getText());
                } catch (NumberFormatException | NullPointerException e) {
                    value = 0;
                }
                String shortName = priceTable.getItems().get(rank).getShortName();
                for (int i = 0; i < updateData.size(); i++) {
                    if (updateData.get(i).getShortName().equals(shortName)) {
                        updateData.get(i).setPrice(value);
                    }
                }

            });
            return cell;
        });

        dateColumn.setCellFactory(cellData -> {
            DatePicker datePicker = new DatePicker();
            datePicker.setEditable(true);
            TableCell<CoinType, String> cell = new TableCell<CoinType, String>() {
                @Override
                protected void updateItem(String chuzhi, boolean empty) {
                    super.updateItem(chuzhi, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        datePicker.setConverter(DateHelper.CONVERTER);

                        String date = priceTable.getItems().get(this.getIndex()).getDate();
                        if (date == null || date.trim().isEmpty()) {
                            datePicker.setValue(LocalDate.now());
                        } else {
                            datePicker.setValue(DateHelper.fromString(date));
                        }
                        String shortName = priceTable.getItems().get(this.getIndex()).getShortName();
                        String value = DateHelper.toString(datePicker.getValue());
                        for (int i = 0; i < updateData.size(); i++) {
                            if (updateData.get(i).getShortName().equals(shortName)) {
                                updateData.get(i).setDate(value);
                            }
                        }
                        setGraphic(datePicker);
                    }
                }
            };

            datePicker.setOnAction(e -> {
                int rank = cell.getIndex();//获取正在编辑的单元格所在行序号
                String value = DateHelper.toString(datePicker.getValue());//获取正在编辑的单元格值
                String shortName = priceTable.getItems().get(rank).getShortName();
                for (int i = 0; i < updateData.size(); i++) {
                    if (updateData.get(i).getShortName().equals(shortName)) {
                        updateData.get(i).setDate(value);
                    }
                }
            });

            return cell;

        });
    }

    @FXML
    private void handleSave(ActionEvent event) {
        CoinTypeDao dao = new CoinTypeDao();
        for (CoinType ct : updateData) {
            String shortName = ct.getShortName();
            double price = ct.getPrice();
            String date = ct.getDate();
            dao.update(shortName, price, date);
        }

    }

    @FXML
    private void handleAUtoUpdate(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("信息");
        alert.setHeaderText("近期实现");
        alert.setContentText("敬请期待。");
        alert.showAndWait();

    }

}
