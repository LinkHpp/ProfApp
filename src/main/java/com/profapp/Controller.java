package com.profapp;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

public class Controller {
    @FXML
    private TableView<Asistencia> tableView;

    @FXML
    private TableColumn<Asistencia, Boolean> asistenciaColumn;

    public void initialize() {
        asistenciaColumn.setCellValueFactory(cellData -> cellData.getValue().asistioProperty());
        asistenciaColumn.setCellFactory(CheckBoxTableCell.forTableColumn(asistenciaColumn));

        // Add sample data
        tableView.getItems().addAll(
                new Asistencia("Student 1", false),
                new Asistencia("Student 2", true),
                new Asistencia("Student 3", false)
        );
    }
}




