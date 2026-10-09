package bilbliotecaLC;

import java.util.ArrayList;

public class Biblioteca  {

    ArrayList <Libros> libro = new ArrayList<>();

    public Biblioteca(){
        this.libro = new ArrayList<>();
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
