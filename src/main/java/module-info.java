module org.lifxue.cointda {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;
    requires java.sql;
    requires org.apache.derby.engine;
    requires org.apache.derby.locale_zh_CN;
    requires org.apache.derby.commons;
    
    requires org.apache.logging.log4j;

    opens org.lifxue.cointda to javafx.fxml;
    opens org.lifxue.cointda.view to javafx.fxml;
    exports org.lifxue.cointda;
    exports org.lifxue.cointda.view;
}