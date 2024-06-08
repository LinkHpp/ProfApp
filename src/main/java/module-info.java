module com.profapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;
    requires org.slf4j;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    opens com.profapp to javafx.fxml;
    opens com.profapp.model to org.hibernate.orm.core;
    exports com.profapp;
}
