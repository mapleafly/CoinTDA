package org.mapleaf.cointda.modules.note;

import com.dlsc.workbenchfx.Workbench;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.mapleaf.cointda.modules.note.richtextfx.RichTextView;

/**
 * @program: CoinTDA
 * @classname: NoteView
 * @description: 记事本View
 * @author: mapleaf
 * @date 2020/7/21 16:24
 */
public class NoteView extends AnchorPane {

  public NoteView(Workbench workbench){
    TabPane tabPane = new TabPane();

    Tab strategyTab = new Tab();
    strategyTab.setText("策略");
    strategyTab.closableProperty().set(false);
    strategyTab.setContent(new RichTextView(workbench, "strategy"));

    Tab noteTab = new Tab("备忘");
    noteTab.closableProperty().set(false);
    noteTab.setContent(new RichTextView(workbench, "note"));

    tabPane.getTabs().addAll(strategyTab,noteTab);

    getChildren().addAll(tabPane);
    setTopAnchor(tabPane, 0.0);
    setLeftAnchor(tabPane, 0.0);
    setRightAnchor(tabPane, 0.0);
    setBottomAnchor(tabPane, 0.0);

  }
}
