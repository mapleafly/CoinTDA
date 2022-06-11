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
package org.cointda.modules.prefs;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author lif
 */
public class PreferencesViewModule extends WorkbenchModule {

    private static final Logger logger = LogManager.getLogger(PreferencesViewModule.class.getName());

    public PreferencesViewModule() {
        super("首选项", MaterialDesignIcon.SETTINGS);
    }

    @Override
    public Node activate() {
        AnchorPane view = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PreferencesViewModule.class.getResource("PreferencesView.fxml"));
            view = loader.load();

            PreferencesViewController controller = loader.getController();
            controller.setWorkbench(getWorkbench());

        } catch (IOException e) {
            logger.error(e.toString());
        }
        return view;
    }
}
