module com.profapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;
    requires org.slf4j;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires javafx.swing;

    opens com.profapp to javafx.fxml;
    exports com.profapp;
    exports com.profapp.model;
    opens com.profapp.model to javafx.fxml, org.hibernate.orm.core;
}
