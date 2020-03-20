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
import org.mapleaf.cointda.crypto.CoinListingCollector;
import org.mapleaf.cointda.dao.CoinListingDao;

/**
 *
 * @author lif
 */
public class BaseData {

    private final Workbench workbench;

    public BaseData(Workbench workbench) {
        this.workbench = workbench;
    }

    /**
     * 更新基础数据 从coinmarketcap.com获取coin id 价格等数据
     *
     * @param event
     */
    private boolean update() {
        boolean ok = false;
        CoinListingCollector listing = new CoinListingCollector();
        if (CoinListingDao.truncate()) {
            if (CoinListingDao.batchInsert(listing.getCoinMarketListing()).length > 0) {
                ok = true;
            }
        }
        return ok;
    }

    public void handleUpdate() {
        if (update()) {
            workbench.showInformationDialog(
                    "消息",
                    "更新基础数据成功！",
                    buttonType -> {
                    }
            );
        } else {
            workbench.showErrorDialog(
                    "错误",
                    "更新基础数据失败！",
                    "请首先确认网络状况和网站key！",
                    buttonType -> {
                    }
            );
        }
    }
}
