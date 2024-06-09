package com.profapp;

import com.profapp.model.Alumno;
import com.profapp.util.Tools;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateEditController  implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField NIATextField;
    @FXML
    private DatePicker fechaNacimientoTextField;

    @FXML
    private TextField localidadTextField;

    @FXML
    private TextField domicilioTextField;

    @FXML
    private TextField quienArreplegaTextField;

    @FXML
    private ImageView fotoImageView;
    @FXML
    private ImageView fotoImageViewTutor;
    @FXML
    private ImageView fotoImageViewObservacion;

    @FXML
    private TextField nombreFamiliar1TextField;

    @FXML
    private TextField telefonoFamiliar1TextField;

    @FXML
    private TextField correoFamiliar1TextField;

    @FXML
    private TextField nombreFamiliar2TextField;

    @FXML
    private TextField telefonoFamiliar2TextField;

    @FXML
    private TextField correoFamiliar2TextField;

    @FXML
    private TextField numeroHermanosTextField;

    @FXML
    private TextField situacionFamiliarTextField;

    @FXML
    private TextArea observacionesTextField;

    @FXML
    private TextArea enfermedadesAlergiasTextField;

    // BUTTONS
    @FXML
    Button selectImage;

    private boolean update;
    Alumno alumnoCRUD;

    private ArrayList<Alumno> arrayList = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(CreateEditController.class);

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void Add(ActionEvent event) throws IOException {
        try{
            if(update){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                root = loader.load();

                AlumnoController alumnoController = loader.getController();

                updateAlumnoFromFields();

                alumnoController.AddAlumno(alumnoCRUD, update);

            }else{
                Alumno newAlumno = gatherAlumnoData();

                arrayList.add(newAlumno);

                System.out.println(newAlumno);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                root = loader.load();

                AlumnoController alumnoController = loader.getController();

                alumnoController.AddAlumno(newAlumno, update);

            }
        }catch (Exception e){
            logger.error("Error: ", e);
            Tools.showErrorPopup("Error", "Problema al crear alumno: " + e.getMessage());
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Create_Edit_Alumnos.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("ProfApp");
            stage.setScene(scene);
            stage.show();
        }

        try{
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            logger.error("Error: ", e);
            Tools.showErrorPopup("Error", "Problema al crear alumno: " + e.getMessage());
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Create_Edit_Alumnos.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("ProfApp");
            stage.setScene(scene);
            stage.show();
        }

    }

    public void onActionCancelButton(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            root = loader.load();

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            logger.error("No se ha podido cambiar de escena: ", e);
        }

    }

    public void LoadCurrent(Alumno alumno, boolean is_update){
        alumnoCRUD = alumno;
        setAlumno(alumnoCRUD);
        update = is_update;
        setImage(false);
    }

    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                fotoImageView.setImage(image);
                Tools.setImageCircle(fotoImageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Alumno gatherAlumnoData() {
        Alumno newAlumno = null;
        try{
            // Retrieve data from TextFields
            int NIA = Integer.parseInt(NIATextField.getText());
            String nombre = nameTextField.getText();
            String apellidos = lastNameTextField.getText();
            String fechaNacimiento = fechaNacimientoTextField.getValue().toString();
            String localidad = localidadTextField.getText();
            String domicilio = domicilioTextField.getText();
            String quienArreplega = quienArreplegaTextField.getText();
            String nombreFamiliar1 = nombreFamiliar1TextField.getText();
            String telefonoFamiliar1 = telefonoFamiliar1TextField.getText();
            String correoFamiliar1 = correoFamiliar1TextField.getText();
            String nombreFamiliar2 = nombreFamiliar2TextField.getText();
            String telefonoFamiliar2 = telefonoFamiliar2TextField.getText();
            String correoFamiliar2 = correoFamiliar2TextField.getText();
            int numeroHermanos = Integer.parseInt(numeroHermanosTextField.getText());
            String situacionFamiliar = situacionFamiliarTextField.getText();
            String observaciones = observacionesTextField.getText();
            String enfermedadesAlergias = enfermedadesAlergiasTextField.getText();

            // Convert image to byte array
            byte[] fotoBytes = null;
            if (fotoImageView.getImage() != null) {
                fotoBytes = convertImageToByteArray(fotoImageView.getImage());
            }else{
                URL imageUrl = getClass().getResource("/com/profapp/images/blank_user.png");
                if (imageUrl != null) {
                    Image image = new Image(imageUrl.toString());
                    fotoBytes = convertImageToByteArray(image);
                }else{
                    fotoImageView.setImage(null);
                    logger.error("No se puede cargar Blank Image");
                }
            }


            // Create a new Alumno object with the gathered data
            newAlumno = new Alumno(
                    NIA,
                    nombre,
                    apellidos,
                    fechaNacimiento,
                    localidad,
                    domicilio,
                    quienArreplega,
                    fotoBytes,
                    nombreFamiliar1,
                    telefonoFamiliar1,
                    correoFamiliar1,
                    nombreFamiliar2,
                    telefonoFamiliar2,
                    correoFamiliar2,
                    numeroHermanos,
                    situacionFamiliar,
                    observaciones,
                    enfermedadesAlergias
            );
        }catch (Exception e){
            try{
                Tools.showErrorPopup("Error", "Problema al crear alumno: " + e.getMessage());
            }catch (Exception err){
                logger.error("No se ha podido abrir pop-up", err);
            }
        }
        return newAlumno;
    }

    private void setAlumno(Alumno alumno) {
        this.alumnoCRUD = alumno;

        if (alumnoCRUD != null) {
            NIATextField.setText(String.valueOf(alumnoCRUD.getNIA()));
            nameTextField.setText(alumnoCRUD.getNombre());
            lastNameTextField.setText(alumnoCRUD.getApellido());
            fechaNacimientoTextField.setValue(LocalDate.parse(alumnoCRUD.getFechaNacimiento()));
            localidadTextField.setText(alumnoCRUD.getLocalidad());
            domicilioTextField.setText(alumnoCRUD.getDomicilio());
            quienArreplegaTextField.setText(alumnoCRUD.getQuienArreplega());

            setImage(false);

            nombreFamiliar1TextField.setText(alumnoCRUD.getNombreFamiliar1());
            telefonoFamiliar1TextField.setText(alumnoCRUD.getTelefonoFamiliar1());
            correoFamiliar1TextField.setText(alumnoCRUD.getCorreoFamiliar1());
            nombreFamiliar2TextField.setText(alumnoCRUD.getNombreFamiliar2());
            telefonoFamiliar2TextField.setText(alumnoCRUD.getTelefonoFamiliar2());
            correoFamiliar2TextField.setText(alumnoCRUD.getCorreoFamiliar2());
            numeroHermanosTextField.setText(String.valueOf(alumnoCRUD.getNumeroHermanos()));
            situacionFamiliarTextField.setText(alumnoCRUD.getSituacionFamiliar());
            observacionesTextField.setText(alumnoCRUD.getObservaciones());
            enfermedadesAlergiasTextField.setText(alumnoCRUD.getEnfermedadesAlergias());
        }
    }

    private void updateAlumnoFromFields() {
        if (alumnoCRUD != null) {
            alumnoCRUD.setNIA(Integer.parseInt(NIATextField.getText()));
            alumnoCRUD.setNombre(nameTextField.getText());
            alumnoCRUD.setApellido(lastNameTextField.getText());

            // Parse Date to String
            LocalDate fechaNacimiento = LocalDate.parse(alumnoCRUD.getFechaNacimiento());
            fechaNacimientoTextField.setValue(fechaNacimiento);

            alumnoCRUD.setFechaNacimiento(String.valueOf(fechaNacimiento));
            alumnoCRUD.setLocalidad(localidadTextField.getText());
            alumnoCRUD.setDomicilio(domicilioTextField.getText());
            alumnoCRUD.setQuienArreplega(quienArreplegaTextField.getText());

            // Update Image in a function
            setImage(true);

            alumnoCRUD.setNombreFamiliar1(nombreFamiliar1TextField.getText());
            alumnoCRUD.setTelefonoFamiliar1(telefonoFamiliar1TextField.getText());
            alumnoCRUD.setCorreoFamiliar1(correoFamiliar1TextField.getText());
            alumnoCRUD.setNombreFamiliar2(nombreFamiliar2TextField.getText());
            alumnoCRUD.setTelefonoFamiliar2(telefonoFamiliar2TextField.getText());
            alumnoCRUD.setCorreoFamiliar2(correoFamiliar2TextField.getText());
            alumnoCRUD.setNumeroHermanos(Integer.parseInt(numeroHermanosTextField.getText()));
            alumnoCRUD.setSituacionFamiliar(situacionFamiliarTextField.getText());
            alumnoCRUD.setObservaciones(observacionesTextField.getText());
            alumnoCRUD.setEnfermedadesAlergias(enfermedadesAlergiasTextField.getText());
        }
    }

    private void setImage(boolean update){
        if (alumnoCRUD.getFoto() != null) {
            Image image = new Image(new ByteArrayInputStream(alumnoCRUD.getFoto()));
            if(update){
                alumnoCRUD.setFoto(convertImageToByteArray(fotoImageView.getImage()));
            }else{
                fotoImageView.setImage(image);
                fotoImageViewTutor.setImage(image);
                fotoImageViewObservacion.setImage(image);
                Tools.setImageCircle(fotoImageView);
                Tools.setImageCircle(fotoImageViewTutor);
                Tools.setImageCircle(fotoImageViewObservacion);
            }
        } else {
            URL imageUrl = getClass().getResource("/com/profapp/images/blank_user.png");
            if (imageUrl != null) {
                Image image = new Image(imageUrl.toString());
                fotoImageView.setImage(image);
                fotoImageViewTutor.setImage(image);
                fotoImageViewObservacion.setImage(image);
                Tools.setImageCircle(fotoImageView);
                Tools.setImageCircle(fotoImageViewTutor);
                Tools.setImageCircle(fotoImageViewObservacion);
            }
        }
    }

    private byte[] convertImageToByteArray(Image image) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tools.setImageCircle(fotoImageView);
    }
}
