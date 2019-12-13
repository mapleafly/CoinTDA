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
package org.lifxue.cointda.dao;

import java.util.List;
import org.lifxue.cointda.bean.CurUsedCoinBean;
import org.lifxue.cointda.pool.DBHelper;

/**
 *
 * @author xuelf
 */
public class CurUsedCoinDao {

    public static int[] batchInsert(List<CurUsedCoinBean> list) {
        String sql = "insert into tab_curuse_coin"
                + "(id,symbol,cmc_rank,lastUpdated)"
                + " values (?,?,?,?)";

        Object[][] params = new Object[list.size()][];
        //组织params
        for (int i = 0; i < list.size(); i++) {
            CurUsedCoinBean bean = list.get(i);
            Object[] param = new Object[4];
            param[0] = bean.getId();
            param[1] = bean.getSymbol();
            param[2] = bean.getCmc_rank();
            param[3] = bean.getLastUpdated();

            params[i] = param;
        }

        return DBHelper.batch(sql, params);
    }

    /**
     * 删除全部数据
     *
     * @return
     */
    public static boolean truncate() {
        String sql = "TRUNCATE TABLE tab_curuse_coin";
        return DBHelper.update(sql) == 0;
    }

   
}
