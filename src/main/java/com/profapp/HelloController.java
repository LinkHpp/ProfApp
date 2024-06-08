package com.profapp;

import com.profapp.DAO.AlumnoDAO;
import com.profapp.model.Alumno;
import com.profapp.util.HibernateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label NIALabel;

    @FXML
    private ImageView imageView;

    @FXML
    private TableView<Alumno> alumnoTable;

    @FXML
    private TableColumn<Alumno, Integer> NIAColumn;
    @FXML
    private TableColumn<Alumno, String> firstNameColumn;
    @FXML
    private TableColumn<Alumno, String> lastNameColumn;

    private ObservableList<Alumno> data;

    Alumno currentAlumno;

    public ObservableList<Alumno> getData() {
        return data;
    }

    public void setData(ObservableList<Alumno> data) {
        this.data = data;
    }

    @FXML
    private void initialize(){
        data = AlumnoDAO.getAllAlumnos();
        NIAColumn.setCellValueFactory(cellData -> cellData.getValue().NIAProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        alumnoTable.setItems(data);

        alumnoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Alumno>()  {

            @Override
            public void changed(ObservableValue<? extends Alumno> observable, Alumno oldValue, Alumno newValue) {
                // Directly use the newValue without referencing oldValue
                currentAlumno = newValue; // Update the currentAlumno to the newly selected one

                if (currentAlumno != null) { // Ensure the new selection is not null
                    // Update UI elements based on the new selection
                    NIALabel.setText(String.valueOf(currentAlumno.getNIA()));
                    firstNameLabel.setText(currentAlumno.getName());
                    lastNameLabel.setText(currentAlumno.getLastName());

                    // Load and set the image if the path is not null
                    if (currentAlumno.getImagePath() != null) {
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(currentAlumno.getImagePath()))));
                    } else {
                        imageView.setImage(null); // Clear the image if there is no path
                    }
                } else {
                    // Clear the UI elements if no selection
                    firstNameLabel.setText("");
                    lastNameLabel.setText("");
                    imageView.setImage(null);
                }
            }
        });
    }

    @FXML
    protected void AddAlumno(Alumno alumno, boolean update) {
        if (update) {
            // Edit the existing Alumno
            AlumnoDAO.editAlumno(alumno);
            data.remove(alumno.getNIA()-1);
            data.add(alumno);
            alumnoTable.refresh();

            /*// Update the TableView with the edited Alumno
            int index = data.indexOf(alumno);
            if (index != -1) {
                data.set(index, alumno);
                alumnoTable.refresh()*/
        } else {
            // Create a new Alumno
            AlumnoDAO.createAlumno(alumno);

            // Add the new Alumno to the TableView
            data.add(alumno);
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        AlumnoDAO.deleteAlumno(currentAlumno);
        data.remove(currentAlumno);
        alumnoTable.refresh();
    }

    @FXML
    protected void onEditButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Create_Edit_Alumnos.fxml"));
        Parent root = loader.load();

        CreateEditController controller = loader.getController();

        controller.LoadCurrent(currentAlumno, true);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onAddButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Create_Edit_Alumnos.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onTutoriaButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tutoria.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage(); // Create a new stage for the new scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onAsistenciaButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Asistencia.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage(); // Create a new stage for the new scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}