/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lifxue.cointda.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class RootLayoutController implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger(RootLayoutController.class.getName());

    private BorderPane pane;

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
        showTypeSettingView();
    }

    @FXML
    private void handleCloseApp(ActionEvent event) {
        //Platform.exit();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    /**
     *
     * @param pane
     */
    public void setPane(BorderPane pane) {
        this.pane = pane;
    }

    /**
     * 显示数据录入视图
     */
    public void showDataEditView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootLayoutController.class.getResource("DataEditView.fxml"));
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
}
