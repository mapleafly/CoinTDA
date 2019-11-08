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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author xuelf
 */
public class TypeSettingViewController implements Initializable {

    @FXML
    private TextField shortNameTextField;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField cnNameTextField;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAddType(ActionEvent event) {
    }

    @FXML
    private void handleEditType(ActionEvent event) {
    }

    @FXML
    private void handleDelType(ActionEvent event) {
    }
    
}
