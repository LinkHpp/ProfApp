package com.profapp.util;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Tools {
    public static void setImageCircle(ImageView imageView){
        double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        imageView.setClip(clip);
    }

    public static void showErrorPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
