package org.example;

import javafx.fxml.FXML;
import java.io.IOException;
import bibliotecaLC.Autor;
import bibliotecaLC.Libros;
import bibliotecaLC.BibliotecaModelo;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import java.util.Optional;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class BibliotecaControlador {

    /**
     * Creación del modelo dentro del controlador
     */

    private static BibliotecaModelo modelo = new BibliotecaModelo();

    // Libro seleccionado en la tabla
    private static Libros libroSeleccionado;

    // Ruta de la portada seleccionada
    private String rutaPortada = "";

    //Campos de Nuevo Libro en anadirLibro.fxml
    @FXML
    private TextField autor;

    @FXML
    private TextField titulo;

    @FXML
    private TextField genero;

    @FXML
    private TextField isbn;

    @FXML
    private TextField fsalida;

    @FXML
    private TextField descripcion;

    // Campo de búsqueda de inicio en pantallaInicio.fxml
    @FXML
    private TextField buscarInicio;

    // Campo de búsqueda de listar en listar.fxml
    @FXML
    private TextField buscarId;

    // Campo de búsqueda de modificar en modificar.fxml
    @FXML
    private TextField buscarIdModificar;

    // Campo de búsqueda de infoLibro.fxml
    @FXML
    private TextField inpubuscar;

    // Mensaje de confirmación de modificar.fxml
    @FXML
    private Label mensajeModificar;

    // Mensaje de confirmación de eliminar.fxml
    @FXML
    private Label mensajeEliminar;

    // Campos de información del libro en infoLibro.fxml
    @FXML
    private Label title;

    @FXML
    private Label autorInfo;

    @FXML
    private Label isbnInfo;

    @FXML
    private TextArea descripInfo;

    @FXML
    private Label fechaInfo;

    @FXML
    private ImageView portadaInfo;

    // Portada del libro seleccionado en listar.fxml
    @FXML
    private ImageView portadaLista;

    // Tabla de libros en listar.fxml
    @FXML
    private TableView<Libros> listatab;

    @FXML
    private TableColumn<Libros, Integer> colId;

    @FXML
    private TableColumn<Libros, String> colTitulo;

    @FXML
    private TableColumn<Libros, String> colDescripcion;

    @FXML
    private TableColumn<Libros, String> colAutor;

    /**
     * Inicializa la tabla de libros cuando se carga listar.fxml.
     */
    @FXML
    private void initialize() {

        // Comprueba que está en la pantalla listar.fxml
        if (listatab != null) {

            // Relaciona cada columna con un atributo de Libros
            colId.setCellValueFactory(
                    new PropertyValueFactory<>("id")
            );

            colTitulo.setCellValueFactory(
                    new PropertyValueFactory<>("titulo")
            );

            colDescripcion.setCellValueFactory(
                    new PropertyValueFactory<>("descrip")
            );

            colAutor.setCellValueFactory(celda ->
                    new javafx.beans.property.SimpleStringProperty(
                            celda.getValue().getAutor().getNombre()
                    )
            );

            // Carga los libros del modelo en la tabla
            listatab.setItems(
                    FXCollections.observableArrayList(modelo.getLibro())
            );

            // Muestra la portada al seleccionar un libro de la tabla
            listatab.getSelectionModel()
                    .selectedItemProperty()
                    .addListener((observable, libroAnterior, libroActual) -> {

                        // Evita errores si esta vista no contiene portadaLista
                        if (portadaLista == null) {
                            return;
                        }

                        if (libroActual != null
                                && libroActual.getPortada() != null
                                && !libroActual.getPortada().isEmpty()) {

                            File archivoPortada =
                                    new File(libroActual.getPortada());

                            if (archivoPortada.exists()) {

                                Image imagen = new Image(
                                        archivoPortada.toURI().toString()
                                );

                                portadaLista.setImage(imagen);

                            } else {
                                // La ruta existe en el libro, pero el archivo no existe
                                portadaLista.setImage(null);
                            }
                        } else {

                            // El libro no tiene portada
                            portadaLista.setImage(null);
                        }
                    });

            // Abre la información del libro al hacer doble clic
            listatab.setOnMouseClicked(event -> {

                if (event.getClickCount() == 2) {

                    // Obtiene el libro seleccionado
                    libroSeleccionado =
                            listatab.getSelectionModel().getSelectedItem();

                    if (libroSeleccionado != null) {

                        try {
                            App.setRoot("infoLibro");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        // Comprueba si estamos en modificar.fxml
        if (mensajeModificar != null && libroSeleccionado != null) {

            mensajeModificar.setText(
                    "¿Quieres modificar el título del libro \"" +
                            libroSeleccionado.getTitulo() + "\"?"
            );
        }

        // Comprueba si está en eliminar.fxml
        if (mensajeEliminar != null && libroSeleccionado != null) {

            mensajeEliminar.setText(
                    "¿Quieres eliminar el libro \"" +
                            libroSeleccionado.getTitulo() + "\"?"
            );
        }
        // Comprueba si estamos en infoLibro.fxml
        if (title != null && libroSeleccionado != null) {

            mostrarLibroEnInfo();
        }
    }

    /**
     * Busca libros desde la pantalla de inicio por ID, título,
     * género, ISBN o autor.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void buscarLibroInicio() throws IOException {

        // Obtiene el texto escrito en el buscador de inicio
        String texto = buscarInicio.getText().trim();

        // Comprueba que el usuario haya escrito algo
        if (texto.isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Búsqueda vacía");
            alerta.setHeaderText(null);
            alerta.setContentText("Introduce algún dato para buscar un libro.");
            alerta.showAndWait();

            return;
        }

        // Busca las coincidencias en el modelo
        java.util.ArrayList<Libros> encontrados =
                modelo.buscarLibros(texto);

        // Si no encuentra ningún libro
        if (encontrados.isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Libro no encontrado");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "No se ha encontrado ningún libro que coincida con: " + texto
            );
            alerta.showAndWait();

        } else if (encontrados.size() == 1) {

            // Guarda el libro encontrado
            libroSeleccionado = encontrados.get(0);

            // Abre directamente su información
            App.setRoot("infoLibro");

        } else {

            // Si existen varias coincidencias, permite elegir un libro
            ChoiceDialog<Libros> dialogo = new ChoiceDialog<>(
                    encontrados.get(0),
                    encontrados
            );

            dialogo.setTitle("Resultados de búsqueda");
            dialogo.setHeaderText("Se han encontrado varios libros.");
            dialogo.setContentText("Selecciona un libro:");

            Optional<Libros> resultado = dialogo.showAndWait();

            if (resultado.isPresent()) {

                libroSeleccionado = resultado.get();

                // Abre la información del libro elegido
                App.setRoot("infoLibro");
            }
        }
    }

    /**
     * Busca libros desde listar.fxml por ID, título,
     * género, ISBN o autor.
     */
    @FXML
    private void buscarLibro() {

        // Obtiene el texto escrito en el buscador
        String texto = buscarId.getText().trim();

        // Si el buscador está vacío, vuelve a mostrar todos los libros
        if (texto.isEmpty()) {

            listatab.setItems(
                    FXCollections.observableArrayList(modelo.getLibro())
            );

            return;
        }

        // Busca todas las coincidencias
        java.util.ArrayList<Libros> encontrados =
                modelo.buscarLibros(texto);

        if (encontrados.isEmpty()) {

            // Si no encuentra nada, muestra un aviso
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Libro no encontrado");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "No se ha encontrado ningún libro que coincida con: " + texto
            );
            alerta.showAndWait();

        } else {

            // Muestra en la tabla todos los libros encontrados
            listatab.setItems(
                    FXCollections.observableArrayList(encontrados)
            );
        }
    }

    /**
     * Busca libros utilizando el texto introducido en infoLibro.fxml.
     * Permite buscar por ID, título, género, ISBN o autor.
     */
    @FXML
    private void buscarLibroInfo() {

        // Obtiene el texto escrito en el buscador
        String texto = inpubuscar.getText().trim();

        // Comprueba que el usuario haya escrito algo
        if (texto.isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Búsqueda vacía");
            alerta.setHeaderText(null);
            alerta.setContentText("Introduce algún dato para buscar un libro.");
            alerta.showAndWait();

            return;
        }

        // Busca las coincidencias en el modelo
        java.util.ArrayList<Libros> encontrados =
                modelo.buscarLibros(texto);

        // Si no existen coincidencias
        if (encontrados.isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Libro no encontrado");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "No se ha encontrado ningún libro que coincida con: " + texto
            );
            alerta.showAndWait();

            // Si solamente existe una coincidencia
        } else if (encontrados.size() == 1) {

            libroSeleccionado = encontrados.get(0);

            mostrarLibroEnInfo();

            // Si existen varias coincidencias
        } else {

            // Si existen varias coincidencias, permite elegir un libro
            ChoiceDialog<Libros> dialogo = new ChoiceDialog<>(
                    encontrados.get(0),
                    encontrados
            );

            dialogo.setTitle("Resultados de búsqueda");
            dialogo.setHeaderText(
                    "Se han encontrado varios libros."
            );
            dialogo.setContentText("Selecciona un libro:");

            // Muestra el selector y espera la elección
            Optional<Libros> resultado = dialogo.showAndWait();

            if (resultado.isPresent()) {

                libroSeleccionado = resultado.get();

                mostrarLibroEnInfo();
            }
        }
    }

    /**
     * Muestra en infoLibro.fxml los datos del libro seleccionado.
     */
    private void mostrarLibroEnInfo() {

        if (libroSeleccionado != null) {

            title.setText(libroSeleccionado.getTitulo());
            autorInfo.setText(
                    libroSeleccionado.getAutor().getNombre()
            );

            isbnInfo.setText(
                    libroSeleccionado.getIsbn()
            );

            descripInfo.setText(
                    libroSeleccionado.getDescrip()
            );

            fechaInfo.setText(
                    libroSeleccionado.getFechaPublicacion()
            );

            // Muestra la información del libro aunque no muestre la portada del libro
            if (portadaInfo != null
                    && libroSeleccionado.getPortada() != null
                    && !libroSeleccionado.getPortada().isEmpty()) {

                File archivoPortada =
                        new File(libroSeleccionado.getPortada());

                if (archivoPortada.exists()) {

                    Image imagen = new Image(
                            archivoPortada.toURI().toString()
                    );

                    portadaInfo.setImage(imagen);
                }
            }
        }
    }

    /**
     * Busca un libro por su ID para poder modificarlo
     */
    @FXML
    private void buscarLibroModificar() {

        // Obtiene el ID escrito en modificar.fxml
        int id = Integer.parseInt(buscarIdModificar.getText());

        // Busca el libro en el modelo
        Libros libroEncontrado = modelo.buscarLibroPorId(id);

        if (libroEncontrado != null) {

            // Guarda el libro encontrado como libro seleccionado
            libroSeleccionado = libroEncontrado;

            // Cambia el mensaje mostrando el título del libro encontrado
            mensajeModificar.setText(
                    "¿Quieres modificar el título del libro \"" +
                            libroEncontrado.getTitulo() + "\"?"
            );

            System.out.println("Libro a modificar: " + libroEncontrado);

        } else {

            // Muestra un aviso si el libro no existe
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Libro no encontrado");
            alerta.setHeaderText(null);
            alerta.setContentText("No existe ningún libro con el ID: " + id);
            alerta.showAndWait();
        }
    }

    /**
     * Solicita un nuevo título y modifica el libro seleccionado.
     */
    @FXML
    private void confirmarModificacion() {

        // Comprueba que existe un libro seleccionado
        if (libroSeleccionado == null) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Libro no seleccionado");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "Primero debes seleccionar o buscar un libro."
            );
            alerta.showAndWait();

            return;
        }

        // Crea una ventana para introducir el nuevo título
        TextInputDialog dialogo = new TextInputDialog(
                libroSeleccionado.getTitulo()
        );

        dialogo.setTitle("Modificar libro");
        dialogo.setHeaderText(
                "Modificar el título de \"" +
                        libroSeleccionado.getTitulo() + "\""
        );
        dialogo.setContentText("Nuevo título:");

        // Espera a que el usuario confirme o cancele
        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {

            String nuevoTitulo = resultado.get().trim();

            if (!nuevoTitulo.isEmpty()) {

                // Cambia el título del libro seleccionado
                libroSeleccionado.setTitulo(nuevoTitulo);

                // Actualiza el libro dentro del modelo
                boolean modificado = modelo.modificar(libroSeleccionado);

                if (modificado) {

                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Libro modificado");
                    alerta.setHeaderText(null);
                    alerta.setContentText(
                            "El título se ha modificado correctamente."
                    );
                    alerta.showAndWait();

                    // Actualiza el mensaje de la pantalla
                    mensajeModificar.setText(
                            "¿Quieres modificar el título del libro \"" +
                                    libroSeleccionado.getTitulo() + "\"?"
                    );

                } else {

                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText(null);
                    alerta.setContentText(
                            "No se ha podido modificar el libro."
                    );
                    alerta.showAndWait();
                }

            } else {

                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Título vacío");
                alerta.setHeaderText(null);
                alerta.setContentText(
                        "El título del libro no puede estar vacío."
                );
                alerta.showAndWait();
            }
        }
    }

    /**
     * Cancela la modificación del libro y regresa a la lista.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void cancelarModificacion() throws IOException {

        libroSeleccionado = null;
        App.setRoot("listar");
    }

    /**
     * Permite seleccionar una imagen y copiarla en la carpeta
     * portadas del proyecto.
     */
    @FXML
    private void seleccionarPortada() {

        FileChooser selector = new FileChooser();
        selector.setTitle("Seleccionar portada");

        selector.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Imágenes",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )
        );

        File archivoSeleccionado = selector.showOpenDialog(null);

        if (archivoSeleccionado != null) {

            try {
                // Carpeta portadas situada en la raíz del proyecto
                Path carpetaPortadas = Path.of("portadas");

                // Crea la carpeta si todavía no existe
                Files.createDirectories(carpetaPortadas);

                // Obtiene el nombre original del archivo
                String nombreArchivo = archivoSeleccionado.getName();

                // Evita sobrescribir portadas con el mismo nombre
                String nombreUnico =
                        System.currentTimeMillis() + "_" + nombreArchivo;

                // Ruta de destino dentro del proyecto
                Path destino = carpetaPortadas.resolve(nombreUnico);

                // Copia la imagen seleccionada a la carpeta portadas
                Files.copy(
                        archivoSeleccionado.toPath(),
                        destino,
                        StandardCopyOption.REPLACE_EXISTING
                );

                // Guarda una ruta relativa y compatible entre sistemas
                rutaPortada = destino.toString();

                System.out.println(
                        "Portada guardada en: " + rutaPortada
                );

            } catch (IOException e) {

                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error al guardar la portada");
                alerta.setHeaderText(null);
                alerta.setContentText(
                        "No se ha podido copiar la imagen seleccionada."
                );
                alerta.showAndWait();

                e.printStackTrace();
            }
        }
    }

    /**
     * Creación de método agregarLibro()
     */
    @FXML
    private void agregarLibro() {

        // Comprueba que los campos obligatorios estén rellenos
        if (autor.getText().trim().isEmpty()
                || titulo.getText().trim().isEmpty()
                || genero.getText().trim().isEmpty()
                || isbn.getText().trim().isEmpty()
                || fsalida.getText().trim().isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos incompletos");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "Debes completar Autor, Título, Género, ISBN y Fecha de salida."
            );
            alerta.showAndWait();

            return;
        }

        // Comprueba que el ISBN tenga 10 o 13 dígitos numéricos
        String isbnTexto = isbn.getText().trim();

        if (!isbnTexto.matches("\\d{10}|\\d{13}")) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("ISBN incorrecto");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "El ISBN debe contener exactamente 10 o 13 números."
            );
            alerta.showAndWait();

            return;
        }

        // Comprueba que la fecha tenga formato dd/mm/yyyy y sea válida
        String fechaTexto = fsalida.getText().trim();

        DateTimeFormatter formatoFecha = DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        try {

            LocalDate.parse(fechaTexto, formatoFecha);

        } catch (DateTimeParseException e) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Fecha incorrecta");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "La fecha debe tener el formato dd/MM/yyyy y ser una fecha válida."
            );
            alerta.showAndWait();

            return;
        }

        // Obtiene el autor existente o crea uno nuevo si no existe
        Autor nuevoAutor = modelo.obtenerOCrearAutor(
                autor.getText()
        );

        // Mostrar por pantalla solo para comprobar que si se están reutilizando los ID de autores
        System.out.println(
                "Autor utilizado -> ID: " + nuevoAutor.getId()
                        + " | Nombre: " + nuevoAutor.getNombre()
        );
        // Fin de prueba

        //Creación del nuevo libro
        Libros nuevoLibro = new Libros(
                modelo.obtenerSiguienteId(),
                titulo.getText(),
                genero.getText(),
                isbn.getText(),
                fsalida.getText(),
                descripcion.getText(),
                rutaPortada,
                nuevoAutor,
                0.0
        );

        //Añadir el libro al modelo
        modelo.agregarLibro(nuevoLibro);

        // Informa al usuario de forma visual de que el libro se ha añadido correctamente
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Libro añadido");
        alerta.setHeaderText(null);
        alerta.setContentText("El libro \"" + nuevoLibro.getTitulo() + "\" se ha añadido correctamente.");
        alerta.showAndWait();

        //Comprobaciones
        System.out.println("Libro agregado" + nuevoLibro);
        System.out.println("Libros en la biblioteca: " + modelo.getLibro());
    }

    /**
     * Cambiar a la pantalla para añadir un nuevo libro
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void irAAnadir() throws IOException {
        App.setRoot("anadirLibro");
    }

    /**
     * Abre la pantalla con la lista de libros.
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void irAListar() throws IOException {
        App.setRoot("listar");
    }

    /**
     * Obtiene el libro seleccionado en la tabla y abre la pantalla de modificación.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void irAModificar() throws IOException {

        // Obtiene el libro seleccionado en la tabla
        libroSeleccionado = listatab.getSelectionModel().getSelectedItem();

        if (libroSeleccionado != null) {

            // Abre la pantalla de modificar
            App.setRoot("modificar");

        } else {

            // Avisa si no se ha seleccionado ningún libro
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Libro no seleccionado");
            alerta.setHeaderText(null);
            alerta.setContentText("Selecciona primero un libro de la lista.");
            alerta.showAndWait();
        }
    }

    /**
     * Abre la pantalla de modificación desde la información
     * del libro seleccionado.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void modificarDesdeInfo() throws IOException {

        if (libroSeleccionado != null) {

        App.setRoot("modificar");

        } else {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Libro no seleccionado");
            alerta.setHeaderText(null);
            alerta.setContentText("No hay ningún libro seleccionado.");
            alerta.showAndWait();
        }
    }

    /**
     * Obtiene el libro seleccionado en la tabla y abre la pantalla de eliminación
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void irAEliminar() throws IOException {
        //Obtiene el libro seleccionado en la tabla
        libroSeleccionado = listatab.getSelectionModel().getSelectedItem();

        if (libroSeleccionado != null) {
            // Abre la pantalla de eliminar
            App.setRoot("eliminar");

        } else {
            // Avisa si no se ha selccionado ningún libro
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Libro no seleccionado");
            alerta.setHeaderText(null);
            alerta.setContentText("Selecciona primero un libro de la lista.");
            alerta.showAndWait();
        }
    }

    /**
     * Abre la pantalla de eliminación desde la información
     * del libro seleccionado.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void eliminarDesdeInfo() throws IOException {

        if (libroSeleccionado != null) {

            // Abre la pantalla de eliminación
            App.setRoot("eliminar");

        } else {

            // Avisa si no existe ningún libro seleccionado
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Libro no seleccionado");
            alerta.setHeaderText(null);
            alerta.setContentText("No hay ningún libro seleccionado.");
            alerta.showAndWait();
        }
    }

    /**
     * Confirma la eliminación del libro seleccionado
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void confirmarEliminacion() throws IOException {

        if (libroSeleccionado != null) {

            // Guarda el título antes de eliminar el libro
            String tituloEliminado = libroSeleccionado.getTitulo();

            // Elimina el libro del modelo mediante su ID
            boolean eliminado = modelo.eliminarLibroPorId(
                    libroSeleccionado.getId()
            );

            if (eliminado) {

                //Informa al usuario de que el libro se ha eliminado
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Libro eliminado");
                alerta.setHeaderText(null);
                alerta.setContentText(
                        "El libro \"" + tituloEliminado +
                                "\" se ha eliminado correctamente."
                );
                alerta.showAndWait();

                // Ya no hay ningún libro seleccionado
                libroSeleccionado = null;

                // Regresa a la lista de libros
                App.setRoot("listar");

            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText(
                        "No se ha podido eliminar el libro."
                );
                alerta.showAndWait();
            }
        }
    }

    /**
     * Cancela la eliminación del libro y regresa a la lista de lista.fxml sin
     * eliminar el libro seleccionado.
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void cancelarEliminacion() throws IOException {

    // Cancela la selección del libro
    libroSeleccionado = null;

    // Regresa a la lista sin eliminar ninguún libro
    App.setRoot("listar");
    }

    /**
     * Regresa desde la lista de libros a la pantalla principal.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void regresarAInicio() throws IOException {
        App.setRoot("pantallaInicio");
    }

    /**
     * Regresa a la pantalla donde se muestra la lista de libros
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void regresarALista() throws IOException {
        App.setRoot("listar");
    }

    /**
     * Obtiene el libro seleccionado en la tabla y abre
     * la pantalla con la información del libro.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void irAInfoLibro() throws IOException {

        // Obtiene el libro seleccionado en la tabla
        libroSeleccionado = listatab.getSelectionModel().getSelectedItem();

        if (libroSeleccionado != null) {

            // Abre la pantalla con la información del libro
            App.setRoot("infoLibro");

        } else {

            // Avisa si no se ha seleccionado ningún libro
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Libro no seleccionado");
            alerta.setHeaderText(null);
            alerta.setContentText("Selecciona primero un libro de la lista.");
            alerta.showAndWait();
        }
    }
}
