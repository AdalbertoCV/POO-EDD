package herramientas.texto;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.PilaEstatica;

import java.io.*;
import java.util.Random;

public class Encriptar {
    protected String nomArchivo;
    protected PilaEstatica pila;
    protected PilaEstatica pilaEncriptada;
    protected int numLineas;
    protected BufferedReader bufferedReader;

    public Encriptar(String archivo){// El constructor recibe el nombre de el archivo a trabajar y lo lee...
        if (leerArchivo(archivo)){
             llenarPila(numLineas);// llenamos nuestra pila
             pilaEncriptada = new PilaEstatica(numLineas);
        }
    }

    /**
     * lee el archivo enviado al constructor y obtiene la cantidad de lineas
     * @param archivo es el archivo a leer
     * @return regresa true si se pudo leer y false si no
     */
    protected boolean leerArchivo(String archivo) {
        try {
            nomArchivo = archivo;
            SalidaPorDefecto.consola("Leyendo archivo Seleccionado: \n");
            bufferedReader = new BufferedReader(new FileReader(nomArchivo));
            numLineas = 0; //Contamos la cantidad de lineas del archivo.
            String linea = bufferedReader.readLine();
            while (linea != null) {
                numLineas++;
                SalidaPorDefecto.consola("Linea "+numLineas+" = "+ linea + "\n");
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * llena la pila con las lineas del archivo para trabajarlas
     * @param lineas son las lineas a agregar a la pila
     * @return regresa true si se pudo llenar la pila y false si no
     **/
    protected boolean llenarPila(int lineas){
        try{
            pila = new PilaEstatica(lineas);
            bufferedReader = new BufferedReader(new FileReader(nomArchivo));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                pila.poner(linea);
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
            return true;
        } catch (IOException e){
            return false;
        }
    }

    /**
     * encripta todas las lineas del archivo guardadas en la pila
     */
    public void encriptarArchivo(String nombreNuevo) throws IOException {
        int numElems= pila.numeroElementos();
        for (int pos=0; pos<numElems;pos++){
            String lineaEncriptada = encriptarLinea((String) pila.quitar());
            pilaEncriptada.poner(lineaEncriptada);
        }
        escribirArchivo(nombreNuevo);
    }

    /**
     * Escribe las lineas encriptadas en un nuevo archivo cuyo nombre se especifica como parametro
     * @param nombreNuevo es el nombre del nuevo archivo
     * @throws IOException
     */
    protected void escribirArchivo(String nombreNuevo) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(nombreNuevo));
        int numElemsPila = pilaEncriptada.numeroElementos();
        for (int pos =0; pos< numElemsPila; pos++){
            bufferedWriter.write((String) pilaEncriptada.quitar() + "\n");
        }
        bufferedWriter.close();
    }

    /**
     * Encripta una linea enviada como parametro
     * @param linea es la linea a encriptar
     * @return regresa la linea encriptada
     */
    private String encriptarLinea(String linea){
        String[] separarCadena = linea.split(" "); //Separamos la cadena por espacios
        String lineaInversa = "";
        for (int pos =0; pos<separarCadena.length; pos++){
            lineaInversa = lineaInversa + InvertirPalabra(separarCadena[pos])+ " "; // le damos formato a las palabras
        }
        linea = lineaInversa;
        char simbolo = 'A'; // obtenemos el simbolo de la agrupacion
        int numAgrupaciones  = 3; // numero aleatorio de agrupaciones respetando el largo de la linea
        String lineaNueva = "";
        int inicioAgrupaciones = 1;
        int anchoAnterior=0;
        int EspacioLinea = linea.length()-inicioAgrupaciones;
        int indice =0;
        while (indice<numAgrupaciones && inicioAgrupaciones<linea.length() ){
            EspacioLinea = EspacioLinea - anchoAnterior;
            int inicioAgrupacion = inicioAgrupaciones;
            int anchoAgrupacion = (int) (Math.random() * ((EspacioLinea )-1))+1;
            int finAgrupacion = inicioAgrupacion + anchoAgrupacion;
            anchoAnterior = anchoAgrupacion;
            inicioAgrupaciones = finAgrupacion;
            if (indice ==0) {
                if (inicioAgrupacion ==0){
                    lineaNueva = lineaNueva+ String.valueOf(simbolo);
                    String subCadena = linea.substring(0,finAgrupacion);
                    lineaNueva = lineaNueva + subCadena + String.valueOf(simbolo);
                }else{
                    for (int posletra = 0; posletra < inicioAgrupacion; posletra++) {
                        char letra = linea.charAt(posletra);
                        lineaNueva = lineaNueva + String.valueOf(letra);
                    }
                    String subCadena = linea.substring(inicioAgrupacion, finAgrupacion);
                    lineaNueva = lineaNueva + String.valueOf(simbolo) + subCadena + String.valueOf(simbolo);
                }
            }
            if (indice== numAgrupaciones-1) {
                String subCadena = linea.substring(inicioAgrupacion, finAgrupacion);
                lineaNueva = lineaNueva + String.valueOf(simbolo) + subCadena + String.valueOf(simbolo);
                if (finAgrupacion < linea.length()) {
                    for (int posletra = finAgrupacion + 1; posletra < linea.length(); posletra++) {
                        char letra = linea.charAt(posletra);
                        lineaNueva = lineaNueva + String.valueOf(letra);
                    }

                }
            }
            if (indice!=0 && indice!= numAgrupaciones-1){
                String subCadena = linea.substring(inicioAgrupacion, finAgrupacion);
                lineaNueva = lineaNueva + String.valueOf(simbolo) + subCadena + String.valueOf(simbolo);
            }
            indice++;
        }
        return lineaNueva;
    }

    /**
     * Invierte la cadena segun el formato requerido para encriptar
     * @param palabra es la palabra a invertir
     * @return regresa la palabra invertida con el nuevo formato
     */
    protected String InvertirPalabra(String palabra){
        char primeraLetra = palabra.charAt(0); // sacamos la primera letra ya que es la unica que conserva su orden
        palabra = palabra.replace(String.valueOf(primeraLetra),"");
        char[] arregloCaracteres = palabra.toCharArray();
        int inicioCadena = 0;
        int finCadena = arregloCaracteres.length-1;
        char memoria;
        while(finCadena>inicioCadena){
            memoria = arregloCaracteres[inicioCadena];
            arregloCaracteres[inicioCadena] = arregloCaracteres[finCadena];
            arregloCaracteres[finCadena] = memoria;
            finCadena--;
            inicioCadena++;
        }
        String cadenaInversa = new String(arregloCaracteres);
        palabra = String.valueOf(primeraLetra) + cadenaInversa;
        return palabra;
    }

}
