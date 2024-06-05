package com.profapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label welcomeText1;


    @FXML
    protected void onHelloButtonClick() {
        System.out.println("TestingButton");
        welcomeText.setText("Hola");
        welcomeText1.setText("Test");
    }

}