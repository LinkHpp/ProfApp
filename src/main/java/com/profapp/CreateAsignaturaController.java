package com.profapp;

import com.profapp.DAO.AsignaturaDAO;
import com.profapp.model.Asignatura;
import com.profapp.util.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CreateAsignaturaController {
    @FXML
    TextField textField;
    @FXML
    Button cancelButton;
    @FXML
    Button saveButton;

    Asignatura asignaturaCRUD;
    boolean update = false;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static final Logger logger = LoggerFactory.getLogger(CreateEditController.class);

    public void onSaveButtonPress(ActionEvent event) throws IOException {
        try {
            if (update) {
                asignaturaCRUD.setNombre(textField.getText());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Asignaturas.fxml"));
                root = loader.load();

                AsignaturasController asignaturaController = loader.getController();
                asignaturaController.addAsignatura(asignaturaCRUD, update); // Method to handle the update in AsignaturaController

            } else {
                Asignatura newAsignatura = gatherAsignaturaData();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Asignaturas.fxml"));
                root = loader.load();

                AsignaturasController asignaturaController = loader.getController();
                asignaturaController.addAsignatura(newAsignatura, update); // Method to handle adding new Asignatura in AsignaturaController
            }
        } catch (Exception e) {
            logger.error("Error: ", e);
            Tools.showErrorPopup("Error", "Problema al crear asignatura: " + e.getMessage());
            // Reload the current scene if an error occurs
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Asignaturas.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("ProfApp");
            stage.setScene(scene);
            stage.show();
        }

        // Switch to the new scene
        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            logger.error("Error: ", e);
            Tools.showErrorPopup("Error", "Problema al cambiar de vista: " + e.getMessage());
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("CreateTutoria.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("ProfApp");
            stage.setScene(scene);
            stage.show();
        }
    }

    private Asignatura gatherAsignaturaData() {
        String nameAsignatura = textField.getText();
        return new Asignatura(nameAsignatura);
    }

    public void LoadCurrentAsignatura(Asignatura asignatura, boolean is_update){
        asignaturaCRUD = asignatura;
        textField.setText(asignaturaCRUD.getNombre());
        update = is_update;
    }

    private void updateAsignaturaFromFields() {
    }

    public void onCancelButtonPress(){

        Scene scene = cancelButton.getScene();

        Stage stage = (Stage) scene.getWindow();

        stage.close();
    }
}
