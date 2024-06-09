package tableviewexample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Asistencia {
    private final SimpleStringProperty nombre;
    private final SimpleBooleanProperty asistio;

    public Asistencia(String nombre, boolean asistio) {
        this.nombre = new SimpleStringProperty(nombre);
        this.asistio = new SimpleBooleanProperty(asistio);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public boolean isAsistio() {
        return asistio.get();
    }

    public void setAsistio(boolean asistio) {
        this.asistio.set(asistio);
    }

    public SimpleBooleanProperty asistioProperty() {
        return asistio;
    }
}
