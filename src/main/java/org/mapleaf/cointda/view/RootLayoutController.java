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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.crypto.CoinListingCollector;
import org.mapleaf.cointda.dao.CoinListingDao;
import org.mapleaf.cointda.dao.TradeDataDao;
import org.mapleaf.cointda.util.CSVHelper;

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

    /**
     * 导出交易数据到csv文件
     *
     * @param event
     */
    @FXML
    private void handleExportData(ActionEvent event) {
        List<TradeDataBean> list = TradeDataDao.queryAll();
        List<String[]> data = new ArrayList<>();
        String[] headers = {"id", "coid_id", "简称", "买卖", "单价", "数量", "总价", "交易时间"};
        if (list == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息");
            alert.setHeaderText("导出数据失败");
            alert.setContentText("没有交易数据！");
            alert.showAndWait();
            return;
        }
        list.stream().map((bean) -> {
            String[] str = new String[8];
            str[0] = bean.getId().toString();
            str[1] = bean.getCoin_id().toString();
            str[2] = bean.getCoin_symbol();
            str[3] = bean.getSale_or_buy();
            str[4] = bean.getPrice().toString();
            str[5] = bean.getNum().toString();
            str[6] = bean.getTotal_price().toString();
            str[7] = bean.getTrade_date();
            return str;
        }).forEachOrdered((str) -> {
            data.add(str);
        });
        FileChooser fileChooser = new FileChooser();
        //文档类型过滤器
        ExtensionFilter extFilter = new ExtensionFilter("txt files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());
        CSVHelper.writeCsv(headers, data, file);
    }

    @FXML
    private void handleCloseApp(ActionEvent event) {
        //Platform.exit();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
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
