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
package org.mapleaf.cointda.modules.piechart;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/** @author lif */
public class TypePieChartModule extends WorkbenchModule {

  private static final Logger logger = LogManager.getLogger(TypePieChartModule.class.getName());

  public TypePieChartModule() {
    super("数据图例", MaterialDesignIcon.CHART_PIE);
  }

  @Override
  public Node activate() {
    AnchorPane view = null;
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(TypePieChartModule.class.getResource("TypePieChartView.fxml"));
      view = loader.load();
    } catch (IOException e) {
      logger.error(e.toString());
    }
    return view;
  }
}
