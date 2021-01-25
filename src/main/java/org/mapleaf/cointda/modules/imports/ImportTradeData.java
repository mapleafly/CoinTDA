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
package org.mapleaf.cointda.modules.imports;

import com.dlsc.workbenchfx.Workbench;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.dao.CoinTypeDao;
import org.mapleaf.cointda.dao.TradeDataDao;
import org.mapleaf.cointda.modules.baseData.CoinInfo;
import org.mapleaf.cointda.modules.export.ExportTradeData;
import org.mapleaf.cointda.util.CSVHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/** @author lif */
public class ImportTradeData {

  private static final Logger logger = LogManager.getLogger(ExportTradeData.class.getName());
  private final Workbench workbench;

  public ImportTradeData(Workbench workbench) {
    this.workbench = workbench;
  }

  public void handleImportData() {
    workbench.showConfirmationDialog(
        "导入数据操作",
        "这个操作会覆盖原有数据！你确定要导入新数据吗？",
        buttonType -> {
          if (buttonType == ButtonType.YES) {
            importData();
          }
        });
  }

  public void importData() {
    FileChooser fileChooser = new FileChooser();
    // 文档类型过滤器
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("txt files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(workbench.getScene().getWindow());
    if (file != null) {
      List<String[]> list = CSVHelper.readCsv(file.toString());
      if (list.isEmpty()) {
        workbench.showErrorDialog("错误", "导入数据失败！", "csv文件为空，或者交易数据格式错误！", buttonType -> {});
      } else {
        // 更新可使用品种数据
        List<Integer> coinid = new ArrayList<>();
        for (String[] str : list) {
          coinid.add(Integer.parseInt(str[1]));
        }
        // 去重
        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(coinid);
        List<Integer> listWithoutDuplicates = new ArrayList<>(hashSet);
        List<Integer> usedid = CoinTypeDao.queryCurID();
        // 差集 (list1 - list2)
        listWithoutDuplicates.removeAll(usedid);
        // 将csv文件中的可用类型和数据库中的可用类型做差集后，将差集更新进数据库中
        if (listWithoutDuplicates.size() > 0) {
          CoinTypeDao.batchUpdate(listWithoutDuplicates);
        }

        // 删除数据库中的原有数据
        TradeDataDao.truncate();
        // 导入到数据库
        int[] is = TradeDataDao.batchInsert(list);
        CoinInfo info = new CoinInfo(workbench);
        // 导入数据后自动更新价格
        info.updateCurPrice();
        int t = 0, f = 0;
        for (int i : is) {
          if (i == 1) {
            t++;
          } else {
            f++;
          }
        }
        workbench.showInformationDialog(
            "完成导入",
            "数据总行数：" + is.length + ";\n" + "正确导入行数：" + t + ";\n " + "错误导入行数：" + f,
            buttonType -> {});
      }
    }
  }
}
