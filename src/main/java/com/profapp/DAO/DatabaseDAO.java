package com.profapp.DAO;

import com.profapp.HelloApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DatabaseDAO {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseDAO.class);

    public static void createNewDatabase(String fileName) {

        String os = getOperatingSystem();
        String username = System.getProperty("user.name");
        String url = "";
        String file_url ="C:/Users/" + username + "/AppData/Roaming/ProfApp/";

        System.out.println(os);

        if(Objects.equals(os, "Windows 10")){
            file_url ="C:/Users/" + username + "/AppData/Roaming/ProfApp/";
            if(Files.exists(Path.of(file_url))){
                logger.info("Existe carpeta");
            }else{
                logger.info("No existe carpeta");
                /////////////////////////////////
                logger.info("Creando carpeta");
                File folder = new File(file_url);
                if(folder.mkdirs()){
                    logger.info("Carpeta creada");
                }
                logger.info("URL: {}", file_url);
            }
            url = "jdbc:sqlite:"+ file_url + fileName;
        } else if (Objects.equals(os, "Linux")) {
            file_url = "$HOME/.config/profapp";
            if(Files.exists(Path.of(file_url))){
                logger.info("Existe carpeta");
            }else{
                logger.info("No existe carpeta");
                /////////////////////////////////
                logger.info("Creando carpeta");
                File folder = new File(file_url);
                if(folder.mkdirs()){
                    logger.info("Carpeta creada");
                }
                logger.info("URL: {}", file_url);
            }
            System.out.println("User: " + username.toLowerCase());
            url = "jdbc:sqlite:$HOME/.config/" + fileName;
        }

        try {
            logger.info(url);
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
}
