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
package org.mapleaf.cointda;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javafx.scene.image.Image;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.modules.baseData.BaseData;
import org.mapleaf.cointda.modules.export.ExportData;
import org.mapleaf.cointda.modules.patable.PATableModule;
import org.mapleaf.cointda.modules.piechart.TypePieChartModule;
import org.mapleaf.cointda.modules.selectcoin.SelectCoinModule;
import org.mapleaf.cointda.modules.trade.TradeDataEditModule;
import org.mapleaf.cointda.pool.InitTable;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final Logger logger = LogManager.getLogger(App.class.getName());

    private Workbench workbench;

    @Override
    public void start(Stage primaryStage) {

        Scene myScene = new Scene(initWorkbench());

        primaryStage.setTitle("CoinTDA");
        primaryStage.getIcons().add(new Image(
                App.class.getResource("images/cointda.jpg").toString()));
        primaryStage.setScene(myScene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.show();
        primaryStage.centerOnScreen();

        initNightMode();
        // open calendar module by default
        //workbench.openModule(calendarModule);
        //初始化数据库
        InitTable.createTable();
    }

    private Workbench initWorkbench() {
        //导出交易数据
        ToolbarItem showExportDataButton = new ToolbarItem("导出交易数据",
                new MaterialDesignIconView(MaterialDesignIcon.EXPORT));
        showExportDataButton.setOnClick(event -> workbench.showConfirmationDialog(
                "导出交易数据",
                "你确定要导出交易数据吗？",
                buttonType -> {
                    if (buttonType == ButtonType.YES) {
                        ExportData exportData = new ExportData(workbench);
                        exportData.handleExportData();
                    }
                }));

        //更新基础数据
        ToolbarItem showBaseDataButton = new ToolbarItem("更新基础数据",
                new MaterialDesignIconView(MaterialDesignIcon.UPDATE));
        showBaseDataButton.setOnClick(event -> workbench.showConfirmationDialog(
                "更新基础数据",
                "你确定要更新基础数据吗？",
                buttonType -> {
                    if (buttonType == ButtonType.YES) {
                        BaseData baseData = new BaseData();
                        baseData.handleUpdate();
                    }
                }));

        // Navigation Drawer
        MenuItem item3 = new MenuItem("首选项", new MaterialDesignIconView(MaterialDesignIcon.SETTINGS));
        item3.setOnAction(event -> workbench.hideNavigationDrawer());

        workbench = Workbench.builder(
                new TradeDataEditModule(),
                new PATableModule(),
                new TypePieChartModule(),
                new SelectCoinModule()
        )
                .modulesPerPage(6)
                .toolbarRight(
                        showExportDataButton,
                        showBaseDataButton
                )
                .navigationDrawerItems(item3)
                .build();

        return workbench;
    }

    private void initNightMode() {
        // initially set stylesheet
        //setNightMode(preferences.isNightMode());

        // change stylesheet depending on whether nightmode is on or not
        //preferences.nightModeProperty().addListener(
        //        (observable, oldValue, newValue) -> setNightMode(newValue)
        //);
        setNightMode(false);

    }

    private void setNightMode(boolean on) {
        String customTheme = App.class.getResource("themes/customTheme.css")
                .toExternalForm();
        String darkTheme = App.class.getResource("themes/darkTheme.css")
                .toExternalForm();
        ObservableList<String> stylesheets = workbench.getStylesheets();
        if (on) {
            stylesheets.remove(customTheme);
            stylesheets.add(darkTheme);
        } else {
            stylesheets.remove(darkTheme);
            stylesheets.add(customTheme);
        }
    }

    public Workbench getWorkbench() {
        return workbench;
    }

    public static void main(String[] args) {
        launch();
    }

}
