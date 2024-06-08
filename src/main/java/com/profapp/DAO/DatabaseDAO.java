package com.profapp.DAO;

import com.profapp.HelloApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        String file_url = "";

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
            url = file_url + fileName;

            initConfig(file_url);
        } else if (Objects.equals(os, "Linux")) {
            String homeDir = System.getenv("HOME");
            file_url = homeDir + "/.config/profapp";
            logger.debug(file_url);
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
            initConfig(file_url);
        }


        try {
            logger.info(url);
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                logger.info("The driver name is {}", meta.getDriverName());
                logger.info("A new database has been created");
                initConfig(file_url);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }


    public static void initConfig(String url){
        if(Objects.equals(getOperatingSystem(), "Linux")){
            String templatePath = "src/main/resources/hibernate-templateLinux.xml";
            String templateContent = "";
            try {
                templateContent = new String(Files.readAllBytes(Paths.get(templatePath)));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Replace placeholder with actual database URL
            String databaseUrl = "jdbc:sqlite:" + url;
            String configFileContent = templateContent.replace("@USERNAME@", System.getProperty("user.name"));

            // Write modified XML to a new file
            String configFileOutputPath = "src/main/resources/hibernate.cfg.xml";
            try {
                Files.write(Paths.get(configFileOutputPath), configFileContent.getBytes());
                System.out.println("Dynamic Hibernate configuration file generated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String templatePath = "src/main/resources/hibernate-templateWindows.xml";
            String templateContent = "";
            try {
                templateContent = new String(Files.readAllBytes(Paths.get(templatePath)));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Replace placeholder with actual database URL
            String databaseUrl = "jdbc:sqlite:" + url;
            String configFileContent = templateContent.replace("@USERNAME@", System.getProperty("user.name"));

            // Write modified XML to a new file
            String configFileOutputPath = "src/main/resources/hibernate.cfg.xml";
            try {
                Files.write(Paths.get(configFileOutputPath), configFileContent.getBytes());
                System.out.println("Dynamic Hibernate configuration file generated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
