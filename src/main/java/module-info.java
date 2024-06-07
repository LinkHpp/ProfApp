module com.profapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;
    requires org.slf4j;

    opens com.profapp to javafx.fxml;
    exports com.profapp;
}
