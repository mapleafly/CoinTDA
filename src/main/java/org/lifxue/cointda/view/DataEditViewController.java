/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lifxue.cointda.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class DataEditViewController implements Initializable {

        private static final Logger LOGGER = LogManager.getLogger(DataEditViewController.class.getName());

    @FXML
    private TableView<?> dataTable;
    @FXML
    private TableColumn<?, ?> typeColumn;
    @FXML
    private TableColumn<?, ?> salebuyColumn;
    @FXML
    private TableColumn<?, ?> priceColumn;
    @FXML
    private TableColumn<?, ?> numColumn;
    @FXML
    private TableColumn<?, ?> totalColumn;
    @FXML
    private TableColumn<?, ?> dateColumn;
    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private ChoiceBox<?> typeChoiceBox;
    @FXML
    private ChoiceBox<?> salebuyChoiceBox;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField numTextField;
    @FXML
    private TextField totalTextField;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        priceTextField = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };
        numTextField = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };
    }

    @FXML
    private void handleAddData(ActionEvent event) {
    }

    @FXML
    private void handleEdtitData(ActionEvent event) {
    }

    @FXML
    private void handleDelData(ActionEvent event) {
    }

    @FXML
    private void onNumTextFieldKeyReleased(KeyEvent event) {

        totalTextField.setText(numTextField.getText());
    }

}
