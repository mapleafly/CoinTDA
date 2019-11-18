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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.dao.CoinTypeDao;
import org.lifxue.cointda.models.CoinType;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class TypeSettingViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(RootLayoutController.class.getName());

    @FXML
    private TableView<CoinType> typeTable;
    @FXML
    private TableColumn<CoinType, String> shortNameColumn;
    @FXML
    private TableColumn<CoinType, String> fullNameColumn;
    @FXML
    private TableColumn<CoinType, String> cnNameColumn;

    @FXML
    private TextField shortNameTextField;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField cnNameTextField;

    /**
     * The data as an observable list of CoinType.
     */
    private final ObservableList<CoinType> coinTypeData;

    public TypeSettingViewController() {
        this.coinTypeData = FXCollections.observableArrayList();

        CoinTypeDao dao = new CoinTypeDao();
        List<CoinType> list = dao.QueryAll();
        coinTypeData.addAll(list);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeTable.setItems(coinTypeData);
        showCoinTypeDetails(null);

        shortNameColumn.setCellValueFactory(cellData -> cellData.getValue().shortNameProperty());
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        cnNameColumn.setCellValueFactory(cellData -> cellData.getValue().cnNameProperty());

        typeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCoinTypeDetails(newValue));
    }

    /**
     *
     * @param coinType
     */
    private void showCoinTypeDetails(CoinType coinType) {
        if (coinType != null) {
            shortNameTextField.setText(coinType.getShortName());
            fullNameTextField.setText(coinType.getFullName());
            cnNameTextField.setText(coinType.getCnName());
        } else {
            shortNameTextField.setText("");
            fullNameTextField.setText("");
            cnNameTextField.setText("");
        }
    }

    @FXML
    private void handleAddType(ActionEvent event) {
        if (isInputValid()) {
            String shortName = this.shortNameTextField.getText();
            String fullName = this.fullNameTextField.getText();
            String cnName = this.cnNameTextField.getText();

            int len = coinTypeData.size();
            boolean re = false;
            for (int i = 0; i < len; i++) {
                CoinType ct = coinTypeData.get(i);
                if (ct.getShortName().equals(shortName)) {
                    re = true;
                    break;
                }
            }
            if (re) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText("数据重复提示");
                alert.setContentText("英文简称不能和已有数据重复！");
                alert.showAndWait();
            } else {
                saveData(shortName, fullName, cnName);
            }
        }
    }

    /**
     *
     * @param shortName
     * @param fullName
     * @param cnName
     */
    private void saveData(String shortName, String fullName, String cnName) {
        CoinType coinType = new CoinType(shortName, fullName, cnName);
        CoinTypeDao dao = new CoinTypeDao();
        if (dao.insert(coinType)) {
            coinTypeData.add(coinType);
            shortNameTextField.setText("");
            fullNameTextField.setText("");
            cnNameTextField.setText("");
        }
    }

    @FXML
    private void handleDelType(ActionEvent event) {
        int selectedIndex = typeTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            CoinType ct = typeTable.getItems().get(selectedIndex);
            CoinTypeDao dao = new CoinTypeDao();
            if (dao.delete(ct)) {
                typeTable.getItems().remove(selectedIndex);
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

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (shortNameTextField.getText() == null || shortNameTextField.getText().length() == 0) {
            errorMessage += "无效的英文简称!\n";
        }
        if (fullNameTextField.getText() == null || fullNameTextField.getText().length() == 0) {
            errorMessage += "无效的英文全称!\n";
        }
        if (cnNameTextField.getText() == null || cnNameTextField.getText().length() == 0) {
            errorMessage += "无效的中文名称!\n";
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
