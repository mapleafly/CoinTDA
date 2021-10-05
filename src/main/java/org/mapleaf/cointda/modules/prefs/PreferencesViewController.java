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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.mapleaf.cointda.enums.BooleanEnum;
import org.mapleaf.cointda.enums.ThemeEnum;
import org.mapleaf.cointda.modules.baseData.CoinInfo;
import org.mapleaf.cointda.pool.InitTable;
import org.mapleaf.cointda.themes.InterfaceTheme;
import org.mapleaf.cointda.util.PrefsHelper;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author lif
 */
public class PreferencesViewController implements Initializable {

  private final int PRICE_MIN = 50;
  private final int PRICE_MAX = 5000;

  private Workbench workbench;
  @FXML private RadioButton lightRadio;
  @FXML private ToggleGroup modeGroup;
  @FXML private RadioButton nightRadio;
  @FXML private CheckBox autoPriceCheck;
  @FXML private CheckBox autoCoinInfoCheck;
  @FXML private CheckBox notSmallCheck;
  @FXML private Spinner<Integer> numSpinner;
  @FXML
  private TextField portTextField;
  @FXML
  private TextField hostTextField;
  @FXML
  private CheckBox proxyCheck;

  /**
   * @Description: Initializes the controller class.
   *
   * @param url 1
   * @param rb 2
   * @return: void
   * @author: mapleaf
   * @date: 2020/6/23 18:32
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    //初始化界面主题
    lightRadio.setUserData(ThemeEnum.LIGHT);
    nightRadio.setUserData(ThemeEnum.NIGHT);
    String themeValue =
        PrefsHelper.getPreferencesValue(PrefsHelper.THEME, ThemeEnum.LIGHT.toString());
    ThemeEnum themeEnum = ThemeEnum.valueOf(themeValue);
    switch (themeEnum) {
      case NIGHT:
        nightRadio.setSelected(true);
        nightRadio.requestFocus();
        break;
      case LIGHT:
      default:
        lightRadio.setSelected(true);
        lightRadio.requestFocus();
        break;
    }
    //初始化自动更新设置
    String apValue =
        PrefsHelper.getPreferencesValue(PrefsHelper.UPDATEPRICE, BooleanEnum.NO.toString());
    BooleanEnum apEnum = BooleanEnum.valueOf(apValue);
    autoPriceCheck.setSelected(apEnum.equals(BooleanEnum.YES));

    String acValue =
        PrefsHelper.getPreferencesValue(PrefsHelper.COINIDMAP, BooleanEnum.NO.toString());
    BooleanEnum acEnum = BooleanEnum.valueOf(acValue);
    autoCoinInfoCheck.setSelected(acEnum.equals(BooleanEnum.YES));

    //初始化小额品种设置
    SpinnerValueFactory<Integer> spinner =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(PRICE_MIN, PRICE_MAX, 100, 100);
    numSpinner.setValueFactory(spinner);

    String notSmallCoinValue =
        PrefsHelper.getPreferencesValue(PrefsHelper.NOTSMALLCOIN, BooleanEnum.NO.toString());
    BooleanEnum notSmallCoinEnum = BooleanEnum.valueOf(notSmallCoinValue);
    if (notSmallCoinEnum.equals(BooleanEnum.YES)) {
      notSmallCheck.setSelected(true);
      numSpinner.setDisable(false);
      String notSmallCoinNumValue =
          PrefsHelper.getPreferencesValue(PrefsHelper.NOTSMALLCOINNUM, "100");
      numSpinner.getValueFactory().setValue(Integer.valueOf(notSmallCoinNumValue));
    } else {
      notSmallCheck.setSelected(false);
      numSpinner.setDisable(true);
    }
    //初始化代理设置
    String proxyValue = PrefsHelper.getPreferencesValue(PrefsHelper.PROXY, BooleanEnum.NO.toString());
    BooleanEnum proxyEnum = BooleanEnum.valueOf(proxyValue);
    if(proxyEnum.equals(BooleanEnum.YES)){
      proxyCheck.setSelected(true);
      hostTextField.setDisable(false);
      hostTextField.setText(PrefsHelper.getPreferencesValue(PrefsHelper.HOST, "127.0.0.1"));
      portTextField.setDisable(false);
      portTextField.setText(PrefsHelper.getPreferencesValue(PrefsHelper.PORT, "62010"));
    }else{
      proxyCheck.setSelected(false);
      hostTextField.setDisable(true);
      hostTextField.setText(PrefsHelper.getPreferencesValue(PrefsHelper.HOST, "127.0.0.1"));
      portTextField.setDisable(true);
      portTextField.setText(PrefsHelper.getPreferencesValue(PrefsHelper.PORT, "62010"));
    }
  }

  /** @param workbench the workbench to set */
  public void setWorkbench(Workbench workbench) {
    this.workbench = workbench;
  }

  @FXML
  private void handleSave(ActionEvent event) {
    // theme
    String ra = modeGroup.getSelectedToggle().getUserData().toString();
    PrefsHelper.updatePreferencesValue(PrefsHelper.THEME, ra);

    // db
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
    // 品种比例图设置
    if (notSmallCheck.isSelected()) {
      PrefsHelper.updatePreferencesValue(PrefsHelper.NOTSMALLCOIN, BooleanEnum.YES.toString());
      PrefsHelper.updatePreferencesValue(
          PrefsHelper.NOTSMALLCOINNUM, numSpinner.getValue().toString());
    } else {
      PrefsHelper.updatePreferencesValue(PrefsHelper.NOTSMALLCOIN, BooleanEnum.NO.toString());
    }
    //代理设置
    if(proxyCheck.isSelected()){
      PrefsHelper.updatePreferencesValue(PrefsHelper.PROXY, BooleanEnum.YES.toString());
      PrefsHelper.updatePreferencesValue(PrefsHelper.HOST, hostTextField.getText());
      PrefsHelper.updatePreferencesValue(PrefsHelper.PORT, portTextField.getText());
    }else{
      PrefsHelper.updatePreferencesValue(PrefsHelper.PROXY, BooleanEnum.NO.toString());
    }

    // 刷新保存
    PrefsHelper.flushPreferences();

    // theme即时生效
    InterfaceTheme theme = new InterfaceTheme(workbench);
    theme.initNightMode();

    workbench.showInformationDialog("消息", "设置信息保存成功！", buttonType -> {});
  }

  @FXML
  private void handleInitDB(ActionEvent event) {
    InitTable.dropTable();
    InitTable.createTable();
    CoinInfo info = new CoinInfo(workbench);
    if(info.updateCoinIDMap()) {
      workbench.showInformationDialog("消息", "初始化数据库成功！", buttonType -> {
      });
    }else{
      workbench.showInformationDialog("消息", "初始化数据库失败！", buttonType -> {
      });
    }
  }

  @FXML
  private void handleNotSmallCheckOnAction(ActionEvent event) {
    numSpinner.setDisable(!notSmallCheck.isSelected());
  }

  @FXML
  public void proxyCheckOnAction(ActionEvent actionEvent) {
    hostTextField.setDisable(!proxyCheck.isSelected());
    portTextField.setDisable(!proxyCheck.isSelected());
  }
}
