package bibliotecaLC;
/**
 * Clase que representa un libro de la Biblioteca
 *
* @author  Celia Alamo Calle y Leonarda Ivette Martinez García
 *@version 1.0.0
*
* */
public class Libros {

    //Atributos
    int id;
    String titulo;
    String genero;
    String isbn;
    String fechaPublicacion;
    String descrip;
    String portada;
    Autor autor;
    double precio;

    /**
     * Constructor vacío de la clase Libros.
     * Permite crear un objeto Libros sin inicializar sus atributos
     */
    public Libros() {
    }

    /**
     * Constructor libro
     * @param id es el identificador del libro
     * @param genero es el género del libro
     * @param isbn es el ISBN del libro
     * @param fechaPublicacion es la fecha de publicación del libro
     * @param descrip es la descripción del libro
     * @param portada es la ruta de la imagen de portada del libro
     * @param autor es el autor del libro
     * @param precio  devuelve el valor de precio
     * @param titulo devuelve el valor de titulo de libro
     **/
    public Libros(int id, String titulo, String genero, String isbn, String fechaPublicacion, String descrip, String portada, Autor autor, double precio) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.descrip = descrip;
        this.portada = portada;
        this.autor = autor;
        this.precio = precio;

    }

    /**
     * Obtiene el identificador del libro
     * @return identificador del libro
     */

    public int getId() {
        return id;
    }

    /**
     * Modificará el identificador del libro
     * @param id nuevo identificador del libro
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el título del libro
     * @return título del libro
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Modificará el título del libro
     * @param titulo nuevo título del libro
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el género del libro
     * @return género del libro
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Modifica el género del libro
     * @param genero nuevo género del libro
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Obtiene el ISBN del libro
     * @return ISBN del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Modifica el ISBN del libro
     * @param isbn nuevo ISBN del libro
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene la fecha de publicación del libro
     * @return fecha de publicación del libro
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Modifica la fecha de publicación del libro
     * @param fechaPublicacion nueva fecha de publicación del libro
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Obtiene la descripción del libro
     * @return descripción del libro
     */
    public String getDescrip() {
        return descrip;
    }

    /**
     * Modifica la descripción del libro
     * @param descrip nueva descripción del libro
     */
    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    /**
     * Obtiene la ruta de la portada del libro
     * @return ruta de la portada del libro
     */
    public String getPortada() {
        return portada;
    }

    /**
     * Modificará la ruta de la portada del libro
     * @param portada nueva ruta de la portada del libro
     */
    public void setPortada(String portada) {
        this.portada = portada;
    }

    /**
     * Obtiene el autor del libro
     * @return autor del libro
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * Modifica el autor del libro
     * @param autor nuevo autor del libro
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    /**
     * Obtiene el precio del libro
     * @return precio del libro
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Modificará el precio del libro
     * @param precio nuevo precio del libro
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Este método devuelve la información del libro en formato texto
     * @return representación en texto del libro
     */
    @Override
    public String toString() {
        return "Libros{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", isbn='" + isbn + '\'' +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                ", descrip='" + descrip + '\'' +
                ", portada='" + portada + '\'' +
                ", autor=" + autor +
                ", precio=" + precio +
                '}';
    }
}
