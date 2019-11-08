package org.lifxue.cointda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;
import org.lifxue.cointda.view.RootLayoutController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final Logger LOGGER = LogManager.getLogger(App.class.getName());

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CoinTDA");

        initRootLayout();
        //showDataEditView();
        
        //监听到窗口关闭
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            LOGGER.info("监听到窗口关闭");
            primaryStage.close();
        });
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setPane(rootLayout);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }

}
