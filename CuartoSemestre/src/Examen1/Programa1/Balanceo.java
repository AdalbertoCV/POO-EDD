package Examen1.Programa1;

import entradasalida.EntradaPorDefecto;
import entradasalida.SalidaPorDefecto;
import estructuraslineales.ColaEstatica;
import estructuraslineales.ListaEstatica;
import estructuraslineales.PilaEstatica;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Clase Balanceo
 * Esta clase sirve para evaluar que los balanceos de parentesis, llaves o corchetes en los archivos o cadenas por teclado sean coherentes
 * @author Adalberto Cerrillo Vazquez
 * @version 1.0
 */
public class Balanceo {
    /**
     * Es la pila donde se guardaran las lineas del archivo
     */
    protected PilaEstatica pilaLineas;
    /**
     * Es la cola donde se almacenaran los errores
     */
    protected ColaEstatica errores;
    /**
     * Es la pila donde se guardaran los caracteres de la expresion ingresada por teclado
     */
    protected PilaEstatica pilaExpresion;
    /**
     * Es la cantidad de lineas del archivo
     */
    protected int numLineas;

    public Balanceo(String nomArchivo){
        numLineas = contarLineas(nomArchivo);
        pilaLineas = new PilaEstatica(numLineas);
        ListaEstatica lineas = leerArchivo(nomArchivo);
        for (int pos= lineas.numeroElementos()-1; pos>=0; pos--){
            pilaLineas.poner(lineas.obtener(pos));
        }
        errores = new ColaEstatica(numLineas);
    }

    public Balanceo(){
        SalidaPorDefecto.consola("Ingrese la expresion por teclado: \n");
        String expresion = EntradaPorDefecto.consolaCadenas();
        pilaExpresion = new PilaEstatica(expresion.length());
        for (int pos=expresion.length()-1; pos>=0; pos--){
            pilaExpresion.poner(expresion.charAt(pos));
        }
    }

    /**
     * lee el archivo seleccionado por lineas
     * @param archivo es el archivo a leer
     * @return regresa una lista estatica con las lineas del archivo
     */
    protected ListaEstatica leerArchivo(String archivo) {
        try {
            ListaEstatica listaLineas = new ListaEstatica(numLineas);
            SalidaPorDefecto.consola("Leyendo archivo Seleccionado: \n");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                SalidaPorDefecto.consola(linea + "\n");
                listaLineas.agregar(linea);
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
            return listaLineas;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Cuenta las lineas del archivo de texto seleccionado para inicializar variables
     * @param archivo es el nombre del archivo
     * @return regresa la cantidad de lineas
     */
    protected int contarLineas(String archivo){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
            int numLineas = 0;
            String linea = bufferedReader.readLine();
            while (linea != null) {
                numLineas++;;
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
            return numLineas;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Evalua que la expresion este correcatamente balanceada
     * @return regresa una cadena indicando si es correcto, y en caso de no serlo, indica el error o los errores..
     */
    public String balanceoExpresion(){
        char parentesisApertura = '(';
        char parentesisCierre = ')';
        char llaveApertura = '{';
        char llaveCierre = '}';
        char corcheteApetura = '[';
        char corcheteCierre = ']';
        int numParentesisCierre =0;
        int numParentesisApertura =0;
        int numLlavesApertura =0;
        int numLLavesCierre =0;
        int numCorcheteApertura=0;
        int numCorcheteCierre =0;
        int numCorrectos=0;
        String errores = "";
        int numElemsPila = pilaExpresion.numeroElementos();
        for (int pos=0; pos< numElemsPila; pos++){
            char cadenaActual = (char) pilaExpresion.quitar();
            if (Character.compare(cadenaActual, parentesisApertura)==0){
                numParentesisApertura++;
            }else{
                if (Character.compare(cadenaActual, parentesisCierre)==0){
                    numParentesisCierre++;
                }else{
                    if (Character.compare(cadenaActual, llaveApertura)==0){
                        numLlavesApertura++;
                    }else{
                        if (Character.compare(cadenaActual, llaveCierre)==0){
                            numLLavesCierre++;
                        }else{
                            if (Character.compare(cadenaActual, corcheteApetura)==0){
                                numCorcheteApertura++;
                            }else{
                                if (Character.compare(cadenaActual, corcheteCierre)==0){
                                    numCorcheteCierre++;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (numParentesisApertura == numParentesisCierre){
            numCorrectos++;
        }else{
            if (numParentesisApertura> numParentesisCierre){
                errores = errores + "Falto Cerrar parentesis ";
            }else{
                errores = errores + "Falto abrir parentesis ";
            }
        }

        if (numLlavesApertura == numLLavesCierre){
            numCorrectos++;
        }else{
            if (numLlavesApertura> numLLavesCierre){
                errores = errores + "Falto Cerrar llaves ";
            }else{
                errores = errores + "Falto abrir llaves ";
            }
        }

        if (numCorcheteApertura == numCorcheteCierre){
            numCorrectos++;
        }else{
            if (numCorcheteApertura> numCorcheteCierre){
                errores = errores + "Falto Cerrar Corchetes ";
            }else{
                errores = errores + "Falto abrir Corchetes ";
            }
        }

        if (numCorrectos ==3){
            return "Balanceo Correcto";
        }else{
            return "Blanceo Incorrecto " + errores;
        }
    }

    /**
     * Evalua el balanceo del archivo seleccionado e imprime ya sea correcto o sus errores..
     */
    public void balanceoArchivo(){
        char parentesisApertura = '(';
        char parentesisCierre = ')';
        char llaveApertura = '{';
        char llaveCierre = '}';
        char corcheteApetura = '[';
        char corcheteCierre = ']';
        int numParentesisCierre =0;
        int numParentesisApertura =0;
        int numLlavesApertura =0;
        int numLLavesCierre =0;
        int numCorcheteApertura=0;
        int numCorcheteCierre =0;
        int numCorrectos=0;
        int lineaErrorParentesis =0;
        int lineaErrorLlaves =0;
        int lineaErrorCorchetes =0;
        int NumElemsPila = pilaLineas.numeroElementos();
        for (int pos=0; pos< NumElemsPila; pos++){
            String lineaActual = (String) pilaLineas.quitar();
            for (int indiceLinea =0; indiceLinea< lineaActual.length(); indiceLinea++){
                char caracterActual = lineaActual.charAt(indiceLinea);
                if (Character.compare(caracterActual, parentesisApertura)==0){
                    numParentesisApertura++;
                    lineaErrorParentesis = pos+1;
                }else{
                    if (Character.compare(caracterActual, parentesisCierre)==0){
                        numParentesisCierre++;
                        lineaErrorParentesis = pos+1;
                    }else{
                        if (Character.compare(caracterActual, llaveApertura)==0){
                            numLlavesApertura++;
                            lineaErrorLlaves = pos+1;
                        }else{
                            if (Character.compare(caracterActual, llaveCierre)==0){
                                numLLavesCierre++;
                                lineaErrorLlaves = pos+1;
                            }else{
                                if (Character.compare(caracterActual, corcheteApetura)==0){
                                    numCorcheteApertura++;
                                    lineaErrorCorchetes = pos+1;
                                }else{
                                    if (Character.compare(caracterActual, corcheteCierre)==0){
                                        numCorcheteCierre++;
                                        lineaErrorCorchetes = pos+1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (numParentesisApertura == numParentesisCierre){
            numCorrectos++;
        }else{
            if (numParentesisApertura> numParentesisCierre){
                errores.poner("Error linea: "+ lineaErrorParentesis +" Falto cerrar parentesis");
            }else{
                errores.poner("Error linea: "+ lineaErrorParentesis +" Falto abrir parentesis");
            }
        }

        if (numLlavesApertura == numLLavesCierre){
            numCorrectos++;
        }else{
            if (numLlavesApertura> numLLavesCierre){
                errores.poner("Error linea: "+ lineaErrorLlaves +" Falto cerrar llaves");
            }else{
                errores.poner("Error linea: "+ lineaErrorLlaves +" Falto abrir llaves");
            }
        }

        if (numCorcheteApertura == numCorcheteCierre){
            numCorrectos++;
        }else{
            if (numCorcheteApertura> numCorcheteCierre){
                errores.poner("Error linea: "+ lineaErrorCorchetes +" Falto cerrar corchetes");
            }else{
                errores.poner("Error linea: "+ lineaErrorCorchetes +" Falto abrir corchetes");
            }
        }

        if (numCorrectos ==3){
            SalidaPorDefecto.consola("Balanceo de archivo correcto");
        }else{
            errores.imprimir();
        }
    }
}
