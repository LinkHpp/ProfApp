package com.profapp;

import com.profapp.model.Alumno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CreateEditController {

    @FXML
    TextField nameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    Button addButton;

    private ArrayList<Alumno> arrayList = new ArrayList<>();

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Add(ActionEvent event) throws IOException {
        String name = nameTextField.getText();
        String lastName = lastNameTextField.getText();

        Alumno newAlumno = new Alumno(name, lastName);

        arrayList.add(newAlumno);

        System.out.println(newAlumno);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        HelloController helloController = loader.getController();

        helloController.AddAlumno(newAlumno);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void LoadCurrent(Alumno alumno){
        nameTextField.setText(alumno.getName());
        lastNameTextField.setText(alumno.getLastName());
    }

}
