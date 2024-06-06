package com.profapp;

import com.profapp.model.Alumno;
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
    private ImageView imageView;

    @FXML
    private TableView<Alumno> alumnoTable;

    @FXML
    private TableColumn<Alumno, String> firstNameColumn;
    @FXML
    private TableColumn<Alumno, String> lastNameColumn;

    Alumno currentAlumno;

    private ObservableList<Alumno> data= FXCollections.observableArrayList(
            new Alumno("Link", "Link"),
            new Alumno("Ganondorf", "Dragmire"),
            new Alumno("Zelda", "Hyrule"));

    public ObservableList<Alumno> getData() {
        return data;
    }

    public void setData(ObservableList<Alumno> data) {
        this.data = data;
    }

    @FXML
    private void initialize(){
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        alumnoTable.setItems(data);

        alumnoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Alumno>()  {

            @Override
            public void changed(ObservableValue<? extends Alumno> observable, Alumno oldValue, Alumno newValue) {
                currentAlumno = alumnoTable.getSelectionModel().getSelectedItem();
                firstNameLabel.setText(currentAlumno.getName());
                lastNameLabel.setText(currentAlumno.getLastName());
                imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(currentAlumno.getImagePath()))));
            }
        });
    }

    @FXML
    protected void AddAlumno(Alumno newAlumno) {
        data.add(newAlumno);
        System.out.println(newAlumno);
    }

    @FXML
    protected void onDeleteButtonClick() {
        alumnoTable.getItems().remove(currentAlumno);
    }

    @FXML
    protected void onEditButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Create_Edit_Alumnos.fxml"));
        Parent root = loader.load();

        CreateEditController controller = loader.getController();

        controller.LoadCurrent(currentAlumno);

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