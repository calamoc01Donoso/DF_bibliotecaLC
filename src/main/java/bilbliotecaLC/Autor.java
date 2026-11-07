package bilbliotecaLC;

import java.util.Date;

/**
 * @author  Celia Alamo Calle y Leonarda Ivette Martinez García
 *@version 1.0.0
 *
 * */
public class Autor {

    //Atributos

    String nombre;
    Date fechaPublicacion;

    /**
     * Constructor libro
     * @param nombre  devuelve el valor del nombre del autor
     * @param fechapublicacion devuelve el valor de la fecha
     **/
    public Autor( int id, String nombre, Date fechapublicacion){
      this.nombre = nombre;
      this.fechaPublicacion = fechapublicacion;
    }

    public Autor(){};

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", fechaPublicacion=" + fechaPublicacion +
                '}';
    }
}
