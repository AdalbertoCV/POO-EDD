package herramientas.texto;

import java.io.IOException;

public class Desencriptar extends Encriptar{
    public Desencriptar(String archivo) {
        super(archivo);
    }

    /**
     * Desencripta la linea enviada como parametro
     * @param linea es la linea a desencriptar
     * @return regresa la linea desencriptada
     */
    public String DesencriptarLinea(String linea){
        String cadenaNueva = linea.replaceAll("Æ","");
        String[] palabras = cadenaNueva.split(" ");
        String lineaDesencriptada = "";
        for (int pos=0; pos< palabras.length; pos++){
            lineaDesencriptada = lineaDesencriptada + InvertirPalabra(palabras[pos]) + " ";
        }
        return lineaDesencriptada;
    }

    /**
     * Desencripta linea a linea el archivo
     * @param nombreNuevo es nombre del nuevo archivo desencriptado
     * @throws IOException
     */
    public void DesencriptarArchivo(String nombreNuevo) throws IOException {
        int numElems= pila.numeroElementos();
        for (int pos=0; pos<numElems;pos++){
            String lineaEncriptada = DesencriptarLinea((String) pila.quitar());
            pilaEncriptada.poner(lineaEncriptada);
        }
        escribirArchivo(nombreNuevo);
    }





}
