package com.profapp;

import com.profapp.DAO.AlumnoDAO;
import com.profapp.DAO.TutoriaDAO;
import com.profapp.model.Alumno;
import com.profapp.model.Tutoria;
import com.profapp.util.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TutoriaCreateController implements Initializable {
    @FXML
    DatePicker datePicker;
    @FXML
    TextArea textArea;
    @FXML
    ChoiceBox<Alumno> alumnoChoiceBox;

    Tutoria tutoriaCRUD;
    Boolean update = false;

    private Stage stage;
    private Scene scene;
    private Parent root;



    private static final Logger logger = LoggerFactory.getLogger(TutoriaCreateController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAlumnosIntoChoiceBox();
    }

    private void loadAlumnosIntoChoiceBox() {
        List<Alumno> alumnos = AlumnoDAO.RetriveAll(); // Fetching all Alumnos from the database
        ObservableList<Alumno> alumnoList = FXCollections.observableArrayList(alumnos);
        alumnoChoiceBox.setItems(alumnoList);

        // Set a custom string converter to display the full name of the Alumno
        alumnoChoiceBox.setConverter(new StringConverter<Alumno>() {
            @Override
            public String toString(Alumno alumno) {
                return alumno.getNombre() + " " + alumno.getApellido();
            }

            @Override
            public Alumno fromString(String string) {
                // This method is not used for our current needs
                return null;
            }
        });

        // Optionally, select the first Alumno by default if the list is not empty
        if (!alumnoList.isEmpty()) {
            alumnoChoiceBox.setValue(alumnoList.get(0));
        }
    }


    @FXML
    private void Add(ActionEvent event) throws IOException {
        try {
            if (update) {
                // If we are updating an existing Tutoria
                updateTutoriaFromFields();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Tutoria.fxml"));
                root = loader.load();

                TutoriaController tutoriaController = loader.getController();
                tutoriaController.addTutoria(tutoriaCRUD, update); // Method to handle the update in TutoriaController

            } else {
                // If we are creating a new Tutoria
                Tutoria newTutoria = gatherTutoriaData();

                TutoriaDAO.createTutoria(newTutoria); // Add the new Tutoria to the database

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Tutoria.fxml"));
                root = loader.load();

                TutoriaController tutoriaController = loader.getController();
                tutoriaController.addTutoria(newTutoria, update); // Method to handle adding new Tutoria in TutoriaController
            }
        } catch (Exception e) {
            logger.error("Error: ", e);
            Tools.showErrorPopup("Error", "Problema al crear tutoria: " + e.getMessage());
            // Reload the current scene if an error occurs
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("CreateTutoria.fxml"));
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

    // Method to gather data from the form and create a new Tutoria
    private Tutoria gatherTutoriaData() {
        String notas = textArea.getText();
        LocalDate date = datePicker.getValue();
        Alumno selectedAlumno = alumnoChoiceBox.getValue();

        return new Tutoria(date, notas, selectedAlumno);
    }

    // Method to update the current Tutoria's fields from the form inputs
    private void updateTutoriaFromFields() {
        if (tutoriaCRUD != null) {
            tutoriaCRUD.setFecha(datePicker.getValue());
            tutoriaCRUD.setNotas(textArea.getText());
            tutoriaCRUD.setAlumno(alumnoChoiceBox.getValue());
        }
    }

    public void LoadCurrentTutoria(Tutoria tutoria, boolean is_update){
        tutoriaCRUD = tutoria;
        setTutoria(tutoriaCRUD);
        update = is_update;
    }

    public void setTutoria(Tutoria tutoria) {
        this.tutoriaCRUD = tutoria; // Update the currentTutoria reference

        if (tutoria != null) { // Ensure the new tutoria is not null
            // Update the DatePicker and TextArea with the tutoria details
            datePicker.setValue(tutoria.getFecha());
            textArea.setText(tutoria.getNotas());
            alumnoChoiceBox.setValue(tutoria.getAlumno());
        } else {
            // Clear the fields if tutoria is null
            datePicker.setValue(null);
            textArea.clear();
            alumnoChoiceBox.setValue(null);
        }
    }


}
