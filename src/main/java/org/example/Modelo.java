package org.example;

import bilbliotecaLC.*;

import java.util.ArrayList;

public class Modelo {
    public static Biblioteca biblioteca = new Biblioteca();
    public static  void main(String [] args){

        Libros nuevolibro = new Libros(1,"El capitan 1",25.00);
        crearLibro(nuevolibro);
        mostrarLibro();

        eliminarLibro(1, biblioteca);
        Libros nuevolibros = new Libros(2,"El capitan 2",25.00);
        crearLibro(nuevolibros);
        mostrarLibro();

    }

    private static void modificarLibro() {


    }

    private static void eliminarLibro(int id, Biblioteca biblioteca) {
        boolean eliminado = biblioteca.eliminarLibroPorId(id);

        if (eliminado) {
            System.out.println("Libro con ID " + id + " eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún libro con el ID: " + id);
        }
    }

    private static void mostrarLibro() {
        System.out.println(biblioteca);
    }

    private static void crearLibro(Libros libros) {



    biblioteca.agregarLibro(libros);


    }


}
