package com.profapp;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ErrorController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void onActionOkButton(){
        stage.close();
    }
}
