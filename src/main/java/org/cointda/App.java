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
package org.cointda;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.cointda.enums.BooleanEnum;
import org.cointda.modules.baseData.CoinInfo;
import org.cointda.modules.cash.CashViewModule;
import org.cointda.modules.export.ExportTradeData;
import org.cointda.modules.imports.ImportTradeData;
import org.cointda.modules.note.NoteModule;
import org.cointda.modules.patable.PATableModule;
import org.cointda.modules.piechart.TypePieChartModule;
import org.cointda.modules.prefs.PreferencesViewModule;
import org.cointda.modules.selectcoin.SelectCoinModule;
import org.cointda.modules.trade.TradeDataEditModule;
import org.cointda.pool.InitTable;
import org.cointda.themes.InterfaceTheme;
import org.cointda.util.DateHelper;
import org.cointda.util.PrefsHelper;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * JavaFX App
 */
public class App extends Application {

    private Workbench workbench;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        Scene myScene = new Scene(initWorkbench());

        primaryStage.setTitle("CoinTDA");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResource("images/lifng.jpg")).toString()));
        primaryStage.setScene(myScene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.show();
        primaryStage.centerOnScreen();

        // 设置主题
        InterfaceTheme theme = new InterfaceTheme(workbench);
        theme.initNightMode();
        // 异步
        CompletableFuture.runAsync(this::initData);
    }


    /**
     * @Description: 更新数据，包括：1.初始化数据库表；2。获取远程数据，更新到数据库
     * @return: void
     * @author: mapleaf
     * @date: 2020/7/24 10:40
     */
    private void initData() {
        // 初始化数据库表
        InitTable.createTable();

        // 插入CoinIDMap数据
        BooleanEnum coinIDMapEnum =
            BooleanEnum.valueOf(
                PrefsHelper.getPreferencesValue(PrefsHelper.COINIDMAP, BooleanEnum.YES.toString()));
        if (coinIDMapEnum.equals(BooleanEnum.YES)) {
            String oldDate = PrefsHelper.getPreferencesValue(PrefsHelper.COINIDMAP_DATE, "2009-10-10");
            // 计算天数
            Long numDay =
                DateHelper.differentDays(
                    LocalDate.now(), Objects.requireNonNull(DateHelper.fromString(oldDate)));
            if (numDay >= 30) {
                CoinInfo info = new CoinInfo(workbench);
                info.handleUpdateCoinIDMap();
            }
        }
        // 更新价格
        BooleanEnum apEnum =
            BooleanEnum.valueOf(
                PrefsHelper.getPreferencesValue(PrefsHelper.UPDATEPRICE, BooleanEnum.NO.toString()));
        if (apEnum.equals(BooleanEnum.YES)) {
            CoinInfo info = new CoinInfo(workbench);
            info.handleUpdateCurPrice();
        }
    }

    private Workbench initWorkbench() {
        // layoutParts
        // 导入CSV菜单项
        MenuItem importItem =
            new MenuItem("导入CSV", new MaterialDesignIconView(MaterialDesignIcon.IMPORT));
        // 导出CSV菜单项
        MenuItem exportItem =
            new MenuItem("导出CSV", new MaterialDesignIconView(MaterialDesignIcon.EXPORT));
        // 更新现价菜单项
        MenuItem updatePriceItem =
            new MenuItem("更新现价", new MaterialDesignIconView(MaterialDesignIcon.BANK));
        // 更新MarketCap货币信息菜单项
        MenuItem coinMapItem =
            new MenuItem("更新货币数据", new MaterialDesignIconView(MaterialDesignIcon.DOWNLOAD));

        // 文件菜单
        ToolbarItem fileItem =
            new ToolbarItem(
                "文件", new MaterialDesignIconView(MaterialDesignIcon.FILE_WORD), importItem, exportItem);
        // update菜单
        ToolbarItem updateItem =
            new ToolbarItem(
                "更新",
                new MaterialDesignIconView(MaterialDesignIcon.UPDATE),
                coinMapItem,
                updatePriceItem);

        // setupEventHandlers
        // 导入交易数据
        importItem.setOnAction(
            event -> {
                ImportTradeData importData = new ImportTradeData(workbench);
                importData.handleImportData();
            });
        // 导出交易数据
        exportItem.setOnAction(
            event -> {
                ExportTradeData exportData = new ExportTradeData(workbench);
                exportData.handleExportData();
            });
        // 更新现价
        updatePriceItem.setOnAction(
            event ->
                workbench.showConfirmationDialog(
                    "更新当前价格",
                    "你确定要更新当前价格数据吗？",
                    buttonType -> {
                        if (buttonType == ButtonType.YES) {
                            CoinInfo coinInfo = new CoinInfo(workbench);
                            CompletableFuture.runAsync(coinInfo::handleUpdateCurPrice);
                        }
                    }));

        // 更新货币数据
        coinMapItem.setOnAction(
            event ->
                workbench.showConfirmationDialog(
                    "更新货币数据",
                    "你确定要更新货币数据吗？",
                    buttonType -> {
                        if (buttonType == ButtonType.YES) {
                            CoinInfo coinInfo = new CoinInfo(workbench);
                            CompletableFuture.runAsync(coinInfo::handleUpdateCoinIDMap);
                        }
                    }));

        workbench =
            Workbench.builder(
                    new NoteModule(),
                    new TradeDataEditModule(),
                    new PATableModule(),
                    new TypePieChartModule(),
                    new SelectCoinModule(),
                    new CashViewModule(),
                    new PreferencesViewModule())
                .modulesPerPage(9)
                .toolbarLeft(fileItem, updateItem)
                // .toolbarRight( )
                // .navigationDrawerItems(item3)
                .build();

        return workbench;
    }

    public Workbench getWorkbench() {
        return workbench;
    }
}
