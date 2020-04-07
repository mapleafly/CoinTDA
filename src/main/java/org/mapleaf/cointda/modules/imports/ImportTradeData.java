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
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinMarketCapListingBean;
import org.mapleaf.cointda.bean.CurUsedCoinBean;
import org.mapleaf.cointda.bean.TradeDataBean;
import org.mapleaf.cointda.dao.CoinListingDao;
import org.mapleaf.cointda.dao.CurUsedCoinDao;
import org.mapleaf.cointda.dao.TradeDataDao;
import org.mapleaf.cointda.modules.export.ExportTradeData;
import org.mapleaf.cointda.util.CSVHelper;

/**
 *
 * @author lif
 */
public class ImportTradeData {

    private static final Logger logger = LogManager.getLogger(ExportTradeData.class.getName());
    private final Workbench workbench;

    public ImportTradeData(Workbench workbench) {
        this.workbench = workbench;
    }

    public void handleImportData() {
        FileChooser fileChooser = new FileChooser();
        //文档类型过滤器
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(workbench.getScene().getWindow());
        if (file != null) {
            List<String[]> list = CSVHelper.readCsv(file.toString());
            if (list.isEmpty() || list == null) {
                workbench.showErrorDialog(
                        "错误",
                        "导入数据失败！",
                        "csv文件为空，或者交易数据格式错误！",
                        buttonType -> {
                        }
                );
                return;
            } else {
                //更新可使用库
                List<Integer> coinid = new ArrayList<>();
                for (String[] str : list) {
                    coinid.add(Integer.parseInt(str[1]));
                }
                //去重
                LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(coinid);
                List<Integer> listWithoutDuplicates = new ArrayList<>(hashSet);

                List<CurUsedCoinBean> curUsedList = CurUsedCoinDao.queryCurAll();
                List<Integer> usedid = new ArrayList<>();
                for (CurUsedCoinBean bean : curUsedList) {
                    usedid.add(bean.getId());
                }
                // 差集 (list1 - list2)
                List<Integer> reduce1 = listWithoutDuplicates.stream()
                        .filter(item -> !usedid.contains(item))
                        .collect(Collectors.toList());
                //System.out.println("---差集 reduce1 (list1 - list2)---");
                //reduce1.parallelStream().forEach(System.out::println);
                List<CoinMarketCapListingBean> allCoin = CoinListingDao.queryAll();
                List<CurUsedCoinBean> addUsedList = new ArrayList<>();
                for (CoinMarketCapListingBean bean : allCoin) {
                    for (int id : reduce1) {
                        if (bean.getId() == id) {
                            CurUsedCoinBean b = new CurUsedCoinBean();
                            b.setId(id);
                            b.setSymbol(bean.getSymbol());
                            b.setCmc_rank(bean.getCmc_rank());
                            b.setLastUpdated(bean.getLastUpdated());
                            addUsedList.add(b);
                        }
                    }

                }
                CurUsedCoinDao.batchInsert(addUsedList);

                //导入到数据库
                int[] is = TradeDataDao.batchInsert(list);
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
                        "数据总行数：" + is.length + ";\n"
                        + "正确导入行数：" + t + ";\n "
                        + "错误导入行数：" + f,
                        buttonType -> {
                        }
                );
            }
        }
    }
}
