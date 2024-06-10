package com.profapp;

import com.profapp.DAO.AlumnoDAO;
import com.profapp.DAO.CalificarDAO;
import com.profapp.model.Alumno;
import com.profapp.model.Asignatura;
import com.profapp.model.Calificar;
import com.profapp.util.EstadoCalificacion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalificarPage {
    @FXML
    private ChoiceBox<EstadoCalificacion> choiceBox;
    @FXML
    private TableColumn<Alumno, Integer> NIAColumn;
    @FXML
    private TableColumn<Alumno, String> firstNameColumn;
    @FXML
    private TableColumn<Alumno, String> lastNameColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label asignaturaLabel;

    private ObservableList<Alumno> data;

    @FXML
    private TableView<Alumno> alumnoTable;

    Alumno currentAlumno;
    Asignatura currentAsignatura;
    Calificar currentCalificar;

    private static final Logger logger = LoggerFactory.getLogger(AlumnoController.class);

    @FXML
    public void initialize() {
        data = AlumnoDAO.getAllAlumnos();
        NIAColumn.setCellValueFactory(cellData -> cellData.getValue().NIAProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());

        alumnoTable.setItems(data);

        // Obtén los valores del enum EstadoCalificacion
        EstadoCalificacion[] estados = EstadoCalificacion.values();

        // Convierte el array en una lista observable
        ObservableList<EstadoCalificacion> opciones = FXCollections.observableArrayList(estados);

        // Configura el ChoiceBox con las opciones
        choiceBox.setItems(opciones);

        // Opcional: Establece un valor predeterminado si es necesario
        choiceBox.setValue(EstadoCalificacion.NO_ALCANZADO);

        alumnoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Alumno>()  {

            @Override
            public void changed(ObservableValue<? extends Alumno> observable, Alumno oldValue, Alumno newValue) {
                // Directly use the newValue without referencing oldValue
                currentAlumno = newValue; // Update the currentAlumno to the newly selected one

                if (currentAlumno != null) { // Ensure the new selection is not null
                    // Update UI elements based on the new selection
                    firstNameLabel.setText(currentAlumno.getNombre());
                    asignaturaLabel.setText(currentAsignatura.getNombre());
                    // Obtener la calificación actual del alumno (si existe)
                    currentCalificar = CalificarDAO.getCalificacion(currentAlumno, currentAsignatura);

                    if (currentCalificar != null) {
                        // Si existe una calificación para el alumno y la asignatura actual,
                        // establece el valor del ChoiceBox en el estado de la calificación
                        choiceBox.setValue(currentCalificar.getEstado());
                    } else {
                        // Si no existe una calificación, establece un valor predeterminado en el ChoiceBox
                        choiceBox.setValue(EstadoCalificacion.NO_ALCANZADO);
                    }
                }
            }

        });
    }
    public void loadAsignatura(Asignatura asignatura){
        currentAsignatura = asignatura;
    }

    public void saveCalificacion() {
        // Obtén la calificación actual del alumno para la asignatura seleccionada
        Calificar calificacion = CalificarDAO.getCalificacion(currentAlumno, currentAsignatura);

        // Si no existe una calificación, crea una nueva
        if (calificacion == null) {
            calificacion = new Calificar(currentAlumno, currentAsignatura, choiceBox.getValue());
            CalificarDAO.createCalificar(calificacion);
        } else {
            // Si ya existe una calificación, actualiza el estado
            calificacion.setEstado(choiceBox.getValue());
            CalificarDAO.updateCalificar(calificacion);
        }
    }
}
