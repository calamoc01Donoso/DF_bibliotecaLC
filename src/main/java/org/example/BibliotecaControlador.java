package org.example;

import javafx.fxml.FXML;

import java.io.IOException;

// este es el controlador

//Prueba numero 1001

public class BibliotecaControlador {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("infoLibro");
    }
}
