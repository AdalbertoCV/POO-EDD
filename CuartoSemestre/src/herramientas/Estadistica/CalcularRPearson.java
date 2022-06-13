package herramientas.Estadistica;
import estructuraslineales.ListaDinamicaDoble;
import estructuraslineales.auxiliares.NodoDoble;

import java.io.BufferedReader;
import java.io.FileReader;

public class CalcularRPearson {
    private ListaDinamicaDoble vectorX;
    private ListaDinamicaDoble vectorY;
    private int numElementos;
    private double sumaX;
    private double sumaY;
    private double sumaXY;
    private double sumaXCuadrada;
    private double sumaYCuadrada;
    private double r;
    private String interpretacion;

    public CalcularRPearson(String archivoX, String archivoY){
        vectorX = leerArchivo(archivoX);
        vectorY = leerArchivo(archivoY);
        numElementos = vectorX.numeroElementos();
        sumaX = obtenerSumaX();
        sumaY = obtenerSumaY();
        sumaXY = obtenerSumaXY();
        sumaXCuadrada = obtenerSumaXCuadrada();
        sumaYCuadrada = obtenerSumaYCuadrada();
        r = calcularRPearson();
        interpretacion = calcularInterpretacion();
    }


    /**
     * Lee el contenido de un archivo y lo guarda en una lista dinamica
     * @param archivo es el nombre del archivo a leer
     * @return regresa una lista dinamica con los datos del archivo
     */
    public ListaDinamicaDoble leerArchivo(String archivo){
        try {
            ListaDinamicaDoble listaLineas = new ListaDinamicaDoble();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
            String linea = bufferedReader.readLine();
            while (linea != null) {
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
     * obtiene la suma de todos los elementos del vectorX
     * @return regresa la suma
     */
    public double obtenerSumaX(){
        if (!vectorX.vacia()){
            double suma = 0.0;
            NodoDoble iterador = vectorX.getPrimerNodo();
            while (iterador != null){
                suma = suma + Double.parseDouble((String) iterador.getContenido());
                iterador = iterador.getNodoDer();
            }
            return suma;
        }else{
            return 0.0;
        }
    }

    /**
     * obtiene la suma de todos los elementos del vectorY
     * @return regresa la suma
     */
    public double obtenerSumaY(){
        if (!vectorY.vacia()){
            double suma = 0.0;
            NodoDoble iterador = vectorY.getPrimerNodo();
            while (iterador != null){
                suma = suma + Double.parseDouble((String) iterador.getContenido());
                iterador = iterador.getNodoDer();
            }
            return suma;
        }else{
            return 0.0;
        }
    }

    /**
     * Obtiene la suma de todos los elementos del vectorX elevados al cuadrado
     * @return regresa la suma
     */
    public double obtenerSumaXCuadrada(){
        if (!vectorX.vacia()){
            double suma = 0.0;
            NodoDoble iterador = vectorX.getPrimerNodo();
            while (iterador != null){
                suma = suma + Math.pow(Double.parseDouble((String) iterador.getContenido()),2);
                iterador = iterador.getNodoDer();
            }
            return suma;
        }else{
            return 0.0;
        }
    }

    /**
     * Obtiene la suma de todos los elementos del vectorY elevados al cuadrado
     * @return regresa la suma
     */
    public double obtenerSumaYCuadrada(){
        if (!vectorY.vacia()){
            double suma = 0.0;
            NodoDoble iterador = vectorY.getPrimerNodo();
            while (iterador != null){
                suma = suma + Math.pow(Double.parseDouble((String) iterador.getContenido()),2);
                iterador = iterador.getNodoDer();
            }
            return suma;
        }else{
            return 0.0;
        }
    }

    /**
     * Obtiene la suma de las multiplicaciones xy de los vectores
     * @return regresa la suma
     */
    public double obtenerSumaXY(){
        if (!vectorX.vacia() && !vectorY.vacia()){
            if (vectorX.numeroElementos() == vectorY.numeroElementos()){
                double suma = 0.0;
                NodoDoble iteradorX = vectorX.getPrimerNodo();
                NodoDoble iteradorY = vectorY.getPrimerNodo();
                while (iteradorX != null){
                    suma = suma + (Double.parseDouble((String) iteradorX.getContenido()) * Double.parseDouble((String) iteradorY.getContenido()));
                    iteradorX = iteradorX.getNodoDer();
                    iteradorY = iteradorY.getNodoDer();
                }
                return suma;
            }else{
                return 0.0;
            }
        }else{
            return 0.0;
        }
    }

    /**
     * Obtiene el coeficiente r de pearson
     * @return regresa el coeficiente
     */
    public double calcularRPearson(){
       return ((numElementos * sumaXY) - (sumaX* sumaY))/ (Math.sqrt((numElementos * sumaXCuadrada) - Math.pow(sumaX,2)) * Math.sqrt((numElementos * sumaYCuadrada) - Math.pow(sumaY,2)));
    }

    /**
     * Calcula la interpretacion de la relacion
     * @return regresa un string con el mensaje corrrespondiente
     */
    public String calcularInterpretacion() {
        if (r == 0) {
            return "Ninguna Correlacion";
        }
        if (r == 1) {
            return "Correlacion Positiva perfecta";
        }
        if (r > 0 && r < 1) {
            return "Correlacion Positiva";
        }
        if (r == -1) {
            return "Correlacion Negativa perfecta";
        }
        if (r > -1 && r < 0) {
            return "Correlacion Negativa";
        }
        return "ocurrio algun error";
    }

    /**
     * Obtiene el coeficiente calculado
     * @return retorna el coeficiente
     */
    public double obtenerCoeficienteR(){
        return r;
    }

    /**
     * Obtiene la interpretacion calculada
     * @return retorna la interpretacion como String
     */
    public String obtenerInterpretacion(){
        return interpretacion;
    }
}
