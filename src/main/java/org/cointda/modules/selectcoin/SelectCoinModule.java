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
package org.cointda.modules.selectcoin;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author lif
 */
@Slf4j
public class SelectCoinModule extends WorkbenchModule {
    public SelectCoinModule() {
        super("可用币种管理", MaterialDesignIcon.SELECT);
    }

    @Override
    public Node activate() {
        AnchorPane view = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SelectCoinModule.class.getResource("SelectCoinView.fxml"));
            view = loader.load();

            SelectCoinViewController controller = loader.getController();
            controller.setWorkbench(getWorkbench());

        } catch (IOException e) {
            log.error(e.toString());
        }
        return view;
    }
}
