package com.profapp;

import com.profapp.DAO.AlumnoDAO;
import com.profapp.model.Alumno;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import com.profapp.util.Tools;

public class AlumnoController {
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label NIALabel;
    @FXML
    private Label fechaNacimientoLabel;

    @FXML
    private Label localidadLabel;

    @FXML
    private Label domicilioLabel;

    @FXML
    private Label quienArreplegaLabel;

    @FXML
    private Label nombreFamiliar1Label;

    @FXML
    private Label telefonoFamiliar1Label;

    @FXML
    private Label correoFamiliar1Label;

    @FXML
    private Label nombreFamiliar2Label;

    @FXML
    private Label telefonoFamiliar2Label;

    @FXML
    private Label correoFamiliar2Label;

    @FXML
    private Label numeroHermanosLabel;
    @FXML
    private Label situacionFamiliarLabel;

    @FXML
    private Label observacionesLabel;

    @FXML
    private Label enfermedadesAlergiasLabel;

    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageViewTutor;
    @FXML
    private ImageView imageViewObservacion;

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

    private static final Logger logger = LoggerFactory.getLogger(AlumnoDAO.class);

    @FXML
    private void initialize(){
        data = AlumnoDAO.getAllAlumnos();
        NIAColumn.setCellValueFactory(cellData -> cellData.getValue().NIAProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());

        alumnoTable.setItems(data);

        clearInfo();


        alumnoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Alumno>()  {

            @Override
            public void changed(ObservableValue<? extends Alumno> observable, Alumno oldValue, Alumno newValue) {
                // Directly use the newValue without referencing oldValue
                currentAlumno = newValue; // Update the currentAlumno to the newly selected one

                if (currentAlumno != null) { // Ensure the new selection is not null
                    // Update UI elements based on the new selection
                    NIALabel.setText(String.valueOf(currentAlumno.getNIA()));
                    firstNameLabel.setText(currentAlumno.getNombre());
                    lastNameLabel.setText(currentAlumno.getApellido());
                    fechaNacimientoLabel.setText(currentAlumno.getFechaNacimiento());
                    localidadLabel.setText(currentAlumno.getLocalidad());
                    domicilioLabel.setText(currentAlumno.getDomicilio());
                    quienArreplegaLabel.setText(currentAlumno.getQuienArreplega());
                    nombreFamiliar1Label.setText(currentAlumno.getNombreFamiliar1());
                    telefonoFamiliar1Label.setText(currentAlumno.getTelefonoFamiliar1());
                    correoFamiliar1Label.setText(currentAlumno.getCorreoFamiliar1());
                    nombreFamiliar2Label.setText(currentAlumno.getNombreFamiliar2());
                    telefonoFamiliar2Label.setText(currentAlumno.getTelefonoFamiliar2());
                    correoFamiliar2Label.setText(currentAlumno.getCorreoFamiliar2());
                    numeroHermanosLabel.setText(String.valueOf(currentAlumno.getNumeroHermanos()));
                    situacionFamiliarLabel.setText(currentAlumno.getSituacionFamiliar());
                    observacionesLabel.setText(currentAlumno.getObservaciones());
                    enfermedadesAlergiasLabel.setText(currentAlumno.getEnfermedadesAlergias());

                    // Assuming you have a getFoto method in your Alumno class
                    byte[] fotoBytes = currentAlumno.getFoto();
                    if (fotoBytes != null) {
                        // Create an Image from the byte array
                        Image image = new Image(new ByteArrayInputStream(fotoBytes));
                        // Set the Image to your ImageView
                        imageView.setImage(image);
                        imageViewTutor.setImage(image);
                        imageViewObservacion.setImage(image);
                        Tools.setImageCircle(imageView);
                        Tools.setImageCircle(imageViewTutor);
                        Tools.setImageCircle(imageViewObservacion);
                    } else {
                        // Handle case where there is no image data
                        imageView.setImage(null);
                    }
                }
            }

        });
    }

    public void clearInfo(){
        NIALabel.setText("");
        firstNameLabel.setText("");
        lastNameLabel.setText("");
        fechaNacimientoLabel.setText("");
        localidadLabel.setText("");
        domicilioLabel.setText("");
        quienArreplegaLabel.setText("");
        nombreFamiliar1Label.setText("");
        telefonoFamiliar1Label.setText("");
        correoFamiliar1Label.setText("");
        nombreFamiliar2Label.setText("");
        telefonoFamiliar2Label.setText("");
        correoFamiliar2Label.setText("");
        numeroHermanosLabel.setText("");
        situacionFamiliarLabel.setText("");
        observacionesLabel.setText("");
        enfermedadesAlergiasLabel.setText("");
        try {
            // Attempt to load the default image
            URL imageUrl = getClass().getResource("/com/profapp/images/blank_user.png");
            if (imageUrl != null) {
                Image image = new Image(imageUrl.toString());
                imageView.setImage(image);
                imageViewTutor.setImage(image);
                imageViewObservacion.setImage(image);
                Tools.setImageCircle(imageView);
                Tools.setImageCircle(imageViewTutor);
                Tools.setImageCircle(imageViewObservacion);
            } else {
                // Handle the case where the image resource is not found
                logger.error("Default image not found. Setting imageView to null.");
                imageView.setImage(null); // or you can set a different fallback image if desired
            }
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            logger.error("Error loading default image: ", e);
            imageView.setImage(null); // or you can set a different fallback image if desired
        }
    }


    @FXML
    protected void AddAlumno(Alumno alumno, boolean update) {
        if (update) {
            // Edit the existing Alumno
            AlumnoDAO.editAlumno(alumno);
            data.clear();
            alumnoTable.refresh();
            data.addAll(AlumnoDAO.getAllAlumnos());
            alumnoTable.refresh();

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
        alumnoTable.getSelectionModel().clearSelection(1);
        clearInfo();
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