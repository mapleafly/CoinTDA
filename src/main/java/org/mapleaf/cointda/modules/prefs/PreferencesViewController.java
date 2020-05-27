/*
 * Copyright 2020 lif.
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
package org.mapleaf.cointda.modules.prefs;

import com.dlsc.workbenchfx.Workbench;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import org.mapleaf.cointda.enums.CoinIDMapEnum;
import org.mapleaf.cointda.enums.BooleanEnum;
import org.mapleaf.cointda.enums.ThemeEnum;
import org.mapleaf.cointda.modules.baseData.CoinInfo;
import org.mapleaf.cointda.pool.InitTable;
import org.mapleaf.cointda.themes.InterfaceTheme;
import org.mapleaf.cointda.util.PrefsHelper;

/**
 * FXML Controller class
 *
 * @author lif
 */
public class PreferencesViewController implements Initializable {

    private Workbench workbench;
    @FXML
    private RadioButton lightRadio;
    @FXML
    private ToggleGroup modeGroup;
    @FXML
    private RadioButton nightRadio;
    @FXML
    private CheckBox autoPriceCheck;
    @FXML
    private CheckBox autoCoinInfoCheck;
    @FXML
    private CheckBox notSmallCheck;
    @FXML
    private Spinner<Integer> numSpinner;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lightRadio.setUserData(ThemeEnum.LIGHT);
        nightRadio.setUserData(ThemeEnum.NIGHT);
        String themeValue = PrefsHelper.getPreferencesValue(PrefsHelper.THEME, ThemeEnum.LIGHT.toString());
        ThemeEnum themeEnum = ThemeEnum.valueOf(themeValue);
        switch (themeEnum) {
            case LIGHT:
                lightRadio.setSelected(true);
                lightRadio.requestFocus();
                break;
            case NIGHT:
                nightRadio.setSelected(true);
                nightRadio.requestFocus();
                break;
            default:
                lightRadio.setSelected(true);
                lightRadio.requestFocus();
                break;
        }

        String apValue = PrefsHelper.getPreferencesValue(PrefsHelper.UPDATEPRICE, BooleanEnum.NO.toString());
        BooleanEnum apEnum = BooleanEnum.valueOf(apValue);
        if (apEnum.equals(BooleanEnum.YES)) {
            autoPriceCheck.setSelected(true);
        } else {
            autoPriceCheck.setSelected(false);
        }

        String acValue = PrefsHelper.getPreferencesValue(PrefsHelper.COINIDMAP, BooleanEnum.NO.toString());
        BooleanEnum acEnum = BooleanEnum.valueOf(acValue);
        if (acEnum.equals(BooleanEnum.YES)) {
            autoCoinInfoCheck.setSelected(true);
        } else {
            autoCoinInfoCheck.setSelected(false);
        }

        SpinnerValueFactory<Integer> spinner =new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 1000, 100, 50);
        numSpinner.setValueFactory(spinner);

        String notSmallCoinValue = PrefsHelper.getPreferencesValue(PrefsHelper.NOTSMALLCOIN, BooleanEnum.NO.toString());
        BooleanEnum notSmallCoinEnum = BooleanEnum.valueOf(notSmallCoinValue);
        if (notSmallCoinEnum.equals(BooleanEnum.YES)) {
            notSmallCheck.setSelected(true);
            numSpinner.setDisable(false);
            String notSmallCoinNumValue = PrefsHelper.getPreferencesValue(PrefsHelper.NOTSMALLCOINNUM, "100");
            numSpinner.getValueFactory().setValue(Integer.valueOf(notSmallCoinNumValue));
        } else {
            notSmallCheck.setSelected(false);
            numSpinner.setDisable(true);
        }

    }

    public PreferencesViewController() {
    }

    /**
     * @param workbench the workbench to set
     */
    public void setWorkbench(Workbench workbench) {
        this.workbench = workbench;
    }

    @FXML
    private void handleSave(ActionEvent event) {
        //theme
        String ra = modeGroup.getSelectedToggle().getUserData().toString();
        PrefsHelper.updatePreferencesValue(PrefsHelper.THEME, ra);

        //db
        if (autoPriceCheck.isSelected()) {
            PrefsHelper.updatePreferencesValue(PrefsHelper.UPDATEPRICE, BooleanEnum.YES.toString());
        } else {
            PrefsHelper.updatePreferencesValue(PrefsHelper.UPDATEPRICE, BooleanEnum.NO.toString());
        }
        if (autoCoinInfoCheck.isSelected()) {
            PrefsHelper.updatePreferencesValue(PrefsHelper.COINIDMAP, BooleanEnum.YES.toString());
        } else {
            PrefsHelper.updatePreferencesValue(PrefsHelper.COINIDMAP, BooleanEnum.NO.toString());
        }
        //品种比例图设置
        if (notSmallCheck.isSelected()) {
            PrefsHelper.updatePreferencesValue(PrefsHelper.NOTSMALLCOIN, BooleanEnum.YES.toString());
            PrefsHelper.updatePreferencesValue(PrefsHelper.NOTSMALLCOINNUM, numSpinner.getValue().toString());
        } else {
            PrefsHelper.updatePreferencesValue(PrefsHelper.NOTSMALLCOIN, BooleanEnum.NO.toString());
        }
        
        //刷新保存
        PrefsHelper.flushPreferences();
        
        //theme即时生效
        InterfaceTheme theme = new InterfaceTheme(workbench);
        theme.initNightMode();

    }

    @FXML
    private void handleInitDB(ActionEvent event) {
        InitTable.dropTable();
        InitTable.createTable();
        CoinInfo info = new CoinInfo(workbench);
        info.updateCoinIDMap();
        workbench.showInformationDialog(
                "消息",
                "初始化数据库成功！",
                buttonType -> {
                }
        );
    }

    @FXML
    private void handleNotSmallCheckOnAction(ActionEvent event) {
        if(notSmallCheck.isSelected()){
            numSpinner.setDisable(false);
        }else{
            numSpinner.setDisable(true);
        }
    }

}
