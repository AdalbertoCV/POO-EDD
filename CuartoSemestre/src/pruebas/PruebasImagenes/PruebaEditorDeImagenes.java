package pruebas.PruebasImagenes;

import registros.Imagenes.editorImagen;

import java.awt.*;
import java.io.IOException;

public class PruebaEditorDeImagenes {
    public static void main(String args[]) throws IOException {
        String pathImagen = "src\\registros\\Imagenes\\mario.jpg";
        editorImagen editor = new editorImagen(pathImagen);
        editor.escalaDeGrises();
        editor.editarBrillo(50);
        //editor.editarBrillo(-50);
        //editor.invertirVertical();
        editor.invertirHorizontal();
        //editor.rotar();
        //editor.rotar();
        //editor.rotar();
        //editor.redimensionar(500,500);
        //editor.redimensionar(.5);
        //editor.redimensionar(2);
        editor.colocarMarco(15, Color.CYAN);
    }
}
