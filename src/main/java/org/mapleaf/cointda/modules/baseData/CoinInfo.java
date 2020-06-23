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
package org.mapleaf.cointda.modules.baseData;

import com.dlsc.workbenchfx.Workbench;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapleaf.cointda.bean.CoinMarketCapIdBean;
import org.mapleaf.cointda.bean.CoinQuotesLatestBean;
import org.mapleaf.cointda.crypto.CoinIDMapCollector;
import org.mapleaf.cointda.crypto.CoinQuotesLatestCollector;
import org.mapleaf.cointda.dao.CoinMarketCapDao;
import org.mapleaf.cointda.dao.CoinQuotesLatestDao;
import org.mapleaf.cointda.dao.CoinTypeDao;
import org.mapleaf.cointda.util.DateHelper;
import org.mapleaf.cointda.util.PrefsHelper;

import java.time.LocalDate;
import java.util.List;

/** @author lif */
public class CoinInfo {

  private static final Logger logger = LogManager.getLogger(CoinInfo.class.getName());

  private final Workbench workbench;

  public CoinInfo(Workbench workbench) {
    this.workbench = workbench;
  }

  /**
   * @Description: 更新当前价格数据 从coinmarketcap.com获取coin id 价格等数据
   *
   * @return: boolean
   * @author: mapleaf
   * @date: 2020/6/23 16:48
   */
  public boolean updateCurPrice() {
    boolean ok = false;

    List<Integer> idList = CoinTypeDao.queryCurID();
    if (idList == null || idList.isEmpty()) {
      return false;
    }
    String k = "id";
    StringBuilder v = new StringBuilder();
    for (Integer id : idList) {
      if (v.toString().isBlank()) {
        v.append(id.toString());
      } else {
        v.append(",").append(id.toString());
      }
    }
    CoinQuotesLatestCollector quotesLatest = new CoinQuotesLatestCollector();
    List<CoinQuotesLatestBean> list = quotesLatest.getQuotesLatest(k, v.toString());
    if (list == null || list.isEmpty()) {
      return false;
    }

    if (CoinQuotesLatestDao.truncate()) {
      if (CoinQuotesLatestDao.batchInsert(list).length > 0) {
        ok = true;
      }
    }
    return ok;
  }

  public void handleUpdateCurPrice() {
    if (updateCurPrice()) {
      workbench.showInformationDialog("消息", "更新当前价格数据成功！", buttonType -> {});
    } else {
      workbench.showErrorDialog(
          "错误", "更新当前价格数据失败！", "请确认网络状况和网站key！/n 请确认是否选择了可用Coin。", buttonType -> {});
    }
  }

  /**
   * @Description: 更新MarketCap货币信息
   *
   * @return: boolean
   * @author: mapleaf
   * @date: 2020/6/23 16:49
   */
  public boolean updateCoinIDMap() {
    boolean ok;
    CoinIDMapCollector c = new CoinIDMapCollector();
    List<CoinMarketCapIdBean> list = c.getCoinMarketCapIds();
    if (list == null || list.isEmpty()) {
      return false;
    }
    List<Integer> dbCurId = CoinTypeDao.queryCurID();
    dbCurId.add(825);
    CoinMarketCapDao.truncate();
    CoinMarketCapDao.batchInsert(list);
    ok = CoinTypeDao.batchUpdate(dbCurId) == dbCurId.size();
    return ok;
  }

  /** 更新MarketCap货币信息 */
  public void handleUpdateCoinIDMap() {
    if (updateCoinIDMap()) {
      // 复写更新时间
      PrefsHelper.updatePreferencesValue(
          PrefsHelper.COINIDMAP_DATE, DateHelper.toString(LocalDate.now()));
      PrefsHelper.flushPreferences();

      workbench.showInformationDialog("消息", "更新货币数据成功！", buttonType -> {});
    } else {
      workbench.showErrorDialog("错误", "更新货币数据失败！", "请首先确认网络状况和网站key！", buttonType -> {});
    }
  }
}
