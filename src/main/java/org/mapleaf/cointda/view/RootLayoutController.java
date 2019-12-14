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
package org.mapleaf.cointda.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.crypto.CoinListingCollector;
import org.mapleaf.cointda.dao.CoinListingDao;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class RootLayoutController implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger(RootLayoutController.class.getName());

    private BorderPane pane;

    @FXML
    private ContextMenu settingMenu;
    @FXML
    private MenuItem typeItem;
    @FXML
    private MenuItem priceItem;
    @FXML
    private Button settingButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleEditData(ActionEvent event) {
        showDataEditView();
    }

    @FXML
    private void handlePieChart(ActionEvent event) {
        showPieChartView();
    }

    @FXML
    private void handlePATable(ActionEvent event) {
        showPATableView();
    }

    @FXML
    private void handleSetting(ActionEvent event) {
        settingMenu.show(settingButton, Side.RIGHT, 0, 0);
    }

    @FXML
    private void handleCloseApp(ActionEvent event) {
        //Platform.exit();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSettingType(ActionEvent event) {
        showTypeSettingView();
    }

    @FXML
    private void handleSettingPrice(ActionEvent event) {
        showSettingPriceView();
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        if (update()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息");
            alert.setHeaderText("更新基础数据成功");
            alert.setContentText("更新基础数据成功！");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("更新基础数据失败");
            alert.setContentText("请首先确认网络状况和网站key！");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param pane
     */
    public void setPane(BorderPane pane) {
        this.pane = pane;
    }

    /**
     * 更新基础数据 从coinmarketcap.com获取coin id 价格等数据
     *
     * @param event
     */
    private boolean update() {
        boolean ok = false;
//        CoinIDMapCollector coin = new CoinIDMapCollector();
//        if (CoinMarketCapDao.truncate()) {
//            if (CoinMarketCapDao.batchInsert(coin.getCoinMarketCapIds()).length > 0) {
//                ok = true;
//            }
//        }
        CoinListingCollector listing = new CoinListingCollector();
        if (CoinListingDao.truncate()) {
            if (CoinListingDao.batchInsert(listing.getCoinMarketListing()).length > 0) {
                ok = true;
            }
        }
        return ok;
    }

    /**
     * 显示数据录入视图
     */
    public void showDataEditView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootLayoutController.class.getResource("TradeDataEditView.fxml"));
            AnchorPane dataEditView = (AnchorPane) loader.load();
            pane.setCenter(dataEditView);
            //转移焦点到center
            dataEditView.requestFocus();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    /**
     * 显示饼图视图
     */
    public void showPieChartView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootLayoutController.class.getResource("TypePieChartView.fxml"));
            AnchorPane dataEditView = (AnchorPane) loader.load();
            pane.setCenter(dataEditView);
            //转移焦点到center
            dataEditView.requestFocus();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    /**
     * 显示平均价格视图
     */
    public void showPATableView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootLayoutController.class.getResource("PATableView.fxml"));
            AnchorPane dataEditView = (AnchorPane) loader.load();
            pane.setCenter(dataEditView);
            //转移焦点到center
            dataEditView.requestFocus();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    /**
     * 显示品种类型设置视图
     */
    public void showTypeSettingView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootLayoutController.class.getResource("TypeSettingView.fxml"));
            AnchorPane dataEditView = (AnchorPane) loader.load();
            pane.setCenter(dataEditView);
            //转移焦点到center
            dataEditView.requestFocus();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    /**
     * 显示品种价格设置视图
     */
    public void showSettingPriceView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootLayoutController.class.getResource("SettingPriceView.fxml"));
            AnchorPane dataEditView = (AnchorPane) loader.load();
            pane.setCenter(dataEditView);
            //转移焦点到center
            dataEditView.requestFocus();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

}