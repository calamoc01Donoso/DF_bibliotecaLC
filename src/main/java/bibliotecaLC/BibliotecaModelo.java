package bibliotecaLC;

import java.util.ArrayList;

public class BibliotecaModelo {

    ArrayList <Libros> libro = new ArrayList<>();

    ArrayList<Autor> autores = new ArrayList<>();

    public BibliotecaModelo(){

        this.libro = new ArrayList<>();
        this.autores = new ArrayList<>();
    }


    public ArrayList<Libros> getLibro() {
        return libro;
    }

    public void setLibro(ArrayList<Libros> libro) {
        this.libro = libro;
    }

// metodos   de agregar eliminar y modificar
    public void agregarLibro(Libros libro) {
        this.libro.add(libro);
    }

    /**
     * Obtiene el siguiente identificador disponible para un libro.
     * @return siguiente identificador disponible
     */
    public int obtenerSiguienteId() {

        int idMayor = 0;

        // Busca el ID más alto entre los libros existentes
        for (Libros libro : this.libro) {

            if (libro.getId() > idMayor) {
                idMayor = libro.getId();
            }
        }

        // El siguiente ID será uno más que el ID mayor
        return idMayor + 1;
    }

    /**
     * Busca un autor por su nombre. Si ya existe, devuelve el autor existente. Si no existe, crea uno nuevo.
     * @param nombre nombre del autor
     * @return autor existente o nuevo autor creado
     */
    public Autor obtenerOCrearAutor(String nombre) {

        String nombreBuscado = nombre.trim();

        // Busca si el autor ya existe
        for (Autor autor : autores) {

            if (autor.getNombre().equalsIgnoreCase(nombreBuscado)) {
                return autor;
            }
        }

        // Si no existe, calcula el siguiente ID de autor
        int nuevoId = obtenerSiguienteIdAutor();

        // Crea el nuevo autor
        Autor nuevoAutor = new Autor(nuevoId, nombreBuscado);

        // Lo guarda en la lista de autores
        autores.add(nuevoAutor);

        return nuevoAutor;
    }

    /**
     * Obtiene el siguiente identificador disponible para un autor.
     *
     * @return siguiente identificador disponible
     */
    public int obtenerSiguienteIdAutor() {

        int idMayor = 0;

        // Busca el ID más alto entre los autores existentes
        for (Autor autor : autores) {

            if (autor.getId() > idMayor) {
                idMayor = autor.getId();
            }
        }
        return idMayor + 1;
    }

    /**
     * Busca un libro en la biblioteca mediante su identificador
     * @param id identificador del libro que se desea buscar
     * @return el libro encontrado o null si no existe
     */
    public Libros buscarLibroPorId(int id) {
        //Recorrer la lista de libros
        for (Libros libro : this.libro) {
            //Comprobación de si el ID coincide
            if (libro.getId() == id) {
                return libro;
            }
        }
            // Si no se encuentra ningún libro con ese id
            return null;
        }

    /**
     * Busca libros que coincidan con el texto introducido.
     * La búsqueda se realiza por ID, título, género, ISBN o autor.
     *
     * @param texto texto que se desea buscar
     * @return lista de libros que coinciden con la búsqueda
     */
    public ArrayList<Libros> buscarLibros(String texto) {

        // Lista donde se guardarán los libros encontrados
        ArrayList<Libros> encontrados = new ArrayList<>();

        // Convierte el texto a minúsculas para facilitar la búsqueda
        String busqueda = texto.toLowerCase().trim();

        // Recorre todos los libros de la biblioteca
        for (Libros libro : this.libro) {

            // Comprueba si la búsqueda coincide con algún dato del libro
            if (String.valueOf(libro.getId()).contains(busqueda)
                    || libro.getTitulo().toLowerCase().contains(busqueda)
                    || libro.getGenero().toLowerCase().contains(busqueda)
                    || libro.getIsbn().toLowerCase().contains(busqueda)
                    || libro.getAutor().getNombre().toLowerCase().contains(busqueda)) {

                encontrados.add(libro);
            }
        }

        return encontrados;
    }

    public boolean eliminarLibroPorId(int id) {
        for (int i = 0; i < this.libro.size(); i++) {
            if (this.libro.get(i).getId() == id) {
                this.libro.remove(i);
                return true; // Libro encontrado y eliminado
            }
        }
        return false; // No se encontró ningún libro con ese ID
    }

    public boolean modificar( Libros libro){
       // recorremos
        for(int i=0 ; i<this.libro.size(); i++){
            if(this.libro.get(i).getId() == libro.getId()){
                this.libro.set(i, libro);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Biblioteca{" +
                "libro=" + libro +
                '}';
    }
}
