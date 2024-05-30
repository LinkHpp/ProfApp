module com.profapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.profapp to javafx.fxml;
    exports com.profapp;
}
