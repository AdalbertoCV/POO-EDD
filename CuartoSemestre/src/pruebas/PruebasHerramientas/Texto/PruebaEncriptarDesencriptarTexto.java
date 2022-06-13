package pruebas.PruebasHerramientas.Texto;
import herramientas.texto.Desencriptar;
import herramientas.texto.Encriptar;
import java.io.IOException;

public class PruebaEncriptarDesencriptarTexto {
    public static void main(String args[]) throws IOException {
        String archivo = "src\\herramientas\\texto\\archivo.txt";
        Encriptar encriptador = new Encriptar(archivo);
        encriptador.encriptarArchivo("src\\herramientas\\texto\\encriptado.txt");

        String archivoDesencriptar = "src\\herramientas\\texto\\encriptado.txt";
        Desencriptar desencriptador = new Desencriptar(archivoDesencriptar);
        desencriptador.DesencriptarArchivo("src\\herramientas\\texto\\desencriptado.txt");
    }
}
