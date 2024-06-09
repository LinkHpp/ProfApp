package com.profapp;

import com.profapp.DAO.AlumnoDAO;
import com.profapp.DAO.TutoriaDAO;
import com.profapp.model.Tutoria;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TutoriaController {

    private static final Logger logger = LoggerFactory.getLogger(TutoriaController.class);

    @FXML
    private TableColumn<Tutoria, String> DateColumn;
    @FXML
    private TableColumn<Tutoria, String> NameColumn;

    @FXML
    private Label fechaTutoriaLabel;
    @FXML
    private Label nombreAlumnoTutoriaLabel;
    @FXML
    private Label notasTutoriaLabel;

    @FXML
    TableView<Tutoria> tutoriaTable;

    private ObservableList<Tutoria> data;

    private Tutoria currentTutoria;


    @FXML
    private void initialize(){
        data = TutoriaDAO.getAllTutorias();
        DateColumn.setCellValueFactory(cellData -> cellData.getValue().fechaNacimientoProperty());
        NameColumn.setCellValueFactory(cellData -> cellData.getValue().alumnoProperty());

        tutoriaTable.setItems(data);

        //clearInfo();


        tutoriaTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tutoria>()  {

            @Override
            public void changed(ObservableValue<? extends Tutoria> observable, Tutoria oldValue, Tutoria newValue) {
                // Directly use the newValue without referencing oldValue
                currentTutoria = newValue; // Update the currentAlumno to the newly selected one

                if (currentTutoria != null) { // Ensure the new selection is not null
                    // Update UI elements based on the new selection
                    fechaTutoriaLabel.setText(String.valueOf(currentTutoria.getFecha()));
                    nombreAlumnoTutoriaLabel.setText(currentTutoria.getNomCompAlumno());
                    notasTutoriaLabel.setText(currentTutoria.getNotas());
                }
            }

        });
    }

    @FXML
    protected void onEditButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTutoria.fxml"));
        Parent root = loader.load();

        TutoriaCreateController controller = loader.getController();

        controller.LoadCurrentTutoria(currentTutoria, true);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    protected void addTutoria(Tutoria tutoria, boolean update){
        if (update) {
            // Edit the existing Alumno
            TutoriaDAO.editTutoria(tutoria);
            data.clear();
            tutoriaTable.refresh();
            data.addAll(TutoriaDAO.getAllTutorias());
            tutoriaTable.refresh();

        } else {
            TutoriaDAO.createTutoria(tutoria);
            data.clear();
            tutoriaTable.refresh();
            data.addAll(TutoriaDAO.getAllTutorias());
            tutoriaTable.refresh();
        }
    }

    @FXML
    protected void onAddButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTutoria.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onDeleteButtonClick() {
        TutoriaDAO.deleteTutoria(currentTutoria);
        data.remove(currentTutoria);
        tutoriaTable.refresh();
        tutoriaTable.getSelectionModel().clearSelection(1);
        clearInfo();
    }

    private void clearInfo(){
        fechaTutoriaLabel.setText("");
        nombreAlumnoTutoriaLabel.setText("");
        notasTutoriaLabel.setText("");
    }
}
