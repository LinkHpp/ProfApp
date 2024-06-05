module com.profapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;

    opens com.profapp to javafx.fxml;
    exports com.profapp;
}
