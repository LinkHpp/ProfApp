package com.profapp;

import atlantafx.base.theme.NordDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloApplication extends Application {

    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());

        createNewDatabase("test.db");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void createNewDatabase(String fileName) {

        String os = getOperatingSystem();

        System.out.println(os);

        String url = "jdbc:sqlite:C:/Program Files/ProfApp/" + fileName;

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                logger.info("The driver name is {}", meta.getDriverName());
                logger.info("A new database has been created");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    public static void main(String[] args) {
        launch();
    }
}