package com.profapp;

import com.profapp.model.Tutoria;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalDate;

public class TutoriaController {
    @FXML
    DatePicker datePicker;

    @FXML
    TextArea textArea;

    public void AddTutoria(){
        String notas = textArea.getText();
        LocalDate date = datePicker.getValue();

        new Tutoria(date, notas);

        System.out.println(notas + " " +date);
    }
}
