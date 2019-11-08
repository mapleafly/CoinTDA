module org.lifxue.cointda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.graphics;
    
    requires org.apache.logging.log4j;

    opens org.lifxue.cointda to javafx.fxml;
    opens org.lifxue.cointda.view to javafx.fxml;
    exports org.lifxue.cointda;
    exports org.lifxue.cointda.view;
}