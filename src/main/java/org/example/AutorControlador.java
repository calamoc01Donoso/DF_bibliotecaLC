package org.example;
import java.io.IOException;
import javafx.fxml.FXML;

// este es el controlador

//Prueba numero 1001

public class AutorControlador {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("infoLibro");
    }
}
