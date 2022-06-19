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
package org.cointda.modules.selectcoin;

import com.dlsc.workbenchfx.Workbench;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.cointda.bean.property.CoinTypeFXC;
import org.cointda.dao.CoinTypeDao;
import org.cointda.modules.baseData.CoinInfo;

import java.net.URL;
import java.util.*;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class SelectCoinViewController implements Initializable {
    private final ObservableList<CoinTypeFXC> coinTypeData;
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
    private TableColumn<CoinTypeFXC, String> dateCol;
    @FXML
    private TextField searchField;
    private Workbench workbench;

    public SelectCoinViewController() {
        this.coinTypeData = FXCollections.observableArrayList();
        List<CoinTypeFXC> list = CoinTypeDao.queryAll();
        coinTypeData.addAll(list);
    }

    /**
     * @param url 1
     * @param rb  2
     * @Description: Initializes the controller class.
     * @return: void
     * @author: mapleaf
     * @date: 2020/6/23 18:49
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
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    }

    @FXML
    private void handleSave(ActionEvent event) {
        List<Integer> dbCurId = CoinTypeDao.queryCurID();
        Map<Integer, Integer> tableSelectedMap = new HashMap<>();
        List<Integer> items = new ArrayList<>();
        for (int i = 0; i < priceTable.getItems().size(); i++) {
            items.add(Integer.valueOf(priceTable.getItems().get(i).getId()));
            if (priceTable.getItems().get(i).getSelect()) {
                tableSelectedMap.put(Integer.valueOf(priceTable.getItems().get(i).getId()), 1);
            }
        }
        // 交集-dbCurId留下items中也存在的项
        dbCurId.retainAll(items);

        dbCurId.forEach(
            (id) -> {
                if (tableSelectedMap.containsKey(id)) {
                    tableSelectedMap.remove(id);
                } else {
                    tableSelectedMap.put(id, 0);
                }
            });
        if (CoinTypeDao.batchUpdate(tableSelectedMap) == tableSelectedMap.size()) {
            // 自动更新选择coin的当前价格
            CoinInfo info = new CoinInfo(workbench);
            info.updateCurPrice();

            workbench.showInformationDialog("信息", "完成可用品种保存操作！", buttonType -> {
            });
        }
    }

    @FXML
    private void handleSearchFieldKeyReleased(KeyEvent event) {
        coinTypeData.clear();
        coinTypeData.addAll(CoinTypeDao.queryBySymbol(searchField.getText().trim()));
        List<CoinTypeFXC> list = CoinTypeDao.queryCurFXC();
        for (CoinTypeFXC coin : list) {
            for (CoinTypeFXC coinTypeDatum : coinTypeData) {
                if (coin.getId().equals(coinTypeDatum.getId())) {
                    coinTypeDatum.setSelect(true);
                    break;
                }
            }
        }
    }

    /**
     * @param workbench 1
     * @Description: set workbench
     * @return: void
     * @author: mapleaf
     * @date: 2020/6/23 18:51
     */
    public void setWorkbench(Workbench workbench) {
        this.workbench = workbench;
    }
}
