package com.profapp;

import com.profapp.DAO.AsignaturaDAO;
import com.profapp.model.Asignatura;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AsignaturasController {
    private static final Logger logger = LoggerFactory.getLogger(TutoriaController.class);

    @FXML
    private TableColumn<Asignatura, Integer> idColumn;
    @FXML
    private TableColumn<Asignatura, String> nameColumn;

    @FXML
    TableView<Asignatura> asignaturaTable;

    private ObservableList<Asignatura> data;

    Asignatura currentAsignatura;

    @FXML
    private void initialize(){
        data = AsignaturaDAO.getAllAsignaturas();
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        asignaturaTable.setItems(data);

        asignaturaTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Asignatura>()  {

            @Override
            public void changed(ObservableValue<? extends Asignatura> observable, Asignatura oldValue, Asignatura newValue) {
                // Directly use the newValue without referencing oldValue
                currentAsignatura = newValue; // Update the currentAlumno to the newly selected one
            }

        });
    }


    @FXML
    protected void onEditButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAsignatura.fxml"));
        Parent root = loader.load();

        CreateAsignaturaController controller = loader.getController();

        controller.LoadCurrentAsignatura(currentAsignatura, true);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void selectAsignatura(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Calificar.fxml"));
        Parent root = loader.load();

        CalificarPage controller = loader.getController();

        controller.loadAsignatura(currentAsignatura);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onAddButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAsignatura.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onDeleteButtonClick() {
        AsignaturaDAO.deleteAsignatura(currentAsignatura);
        data.remove(currentAsignatura);
        asignaturaTable.refresh();
        asignaturaTable.getSelectionModel().clearSelection(1);
    }

    protected void addAsignatura(Asignatura asignatura, boolean update){
        if (update) {
            AsignaturaDAO.editAsignatura(asignatura);
            refreshTable();
        } else {
            AsignaturaDAO.createAsignatura(asignatura);
            refreshTable();
        }
    }

    public void refreshTable() {
        data.clear();
        asignaturaTable.refresh();
        data.addAll(AsignaturaDAO.getAllAsignaturas());
        asignaturaTable.refresh();
    }
}
