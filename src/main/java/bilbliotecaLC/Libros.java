package bilbliotecaLC;
/**
* @author  Celia Alamo Calle y Leonarda Ivette Martinez García
 *@version 1.0.0
*
* */
public class Libros extends Autor {

    //Atributos
    int id;
    String titulo;
    double precio;
     Autor autor = new Autor();
    /**
     * Constructor libro
     * @param precio  devulve el valor de precio
     * @param titulo devuelve el valor de titulo de libro
     **/
    public Libros(int id, String titulo, double precio){
        this.precio = precio;
        this.titulo = titulo;
        this.id = id;

    }
    public Libros(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", precio=" + precio +
                '}';
    }
}
