package bibliotecaLC;

/**
 * Clase que representa un autor de la Biblioteca.
 *
 * @author  Celia Alamo Calle y Leonarda Ivette Martinez García
 *@version 1.0.0
 *
 * */
public class Autor {

    //Atributos
    int id;
    String nombre;

    /**
     * Constructor de la clase Autor
     * @param id devuelve el identificador del autor
     * @param nombre  devuelve el valor del nombre del autor
     **/
    public Autor(int id, String nombre){
        this.id = id;
      this.nombre = nombre;
    }

    /**
     * Constructor vacío de la clase Autor.
     * Permite crear un objeto Autor sin inicializar sus atributos
     */
    public Autor(){
    }

    /**
     * Obtiene el identificador del autor
     * @return identificador del autor
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica el identificador del autor
     * @param id nuevo identificador del autor
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del autor
     * @return nombre del autor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del autor
     * @param nombre nuevo nombre del autor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Este método devuelve la información del autor en formato texto.
     * @return representación en texto del autor
     */
    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
