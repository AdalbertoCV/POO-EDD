package herramientas.Estadistica;

import enumerados.TipoDatos;
import estructuraslineales.ListaDinamica;
import estructuraslineales.auxiliares.Nodo;

import java.io.BufferedReader;
import java.io.FileReader;

public class CalcularCovarianza {
    private ListaDinamica vectorX;
    private ListaDinamica vectorY;
    private int numeroElementos;
    private double promedioX;
    private double promedioY;
    private double sumaProductos;
    private double sumaRestaMediaXCuadrado;
    private double sumaRestaMediaYCuadrado;
    private double desviacionX;
    private double desviacionY;
    private double covarianza;
    private double r;
    private double p;

    public CalcularCovarianza(String ArchivoX, String ArchivoY, TipoDatos tipo){
        vectorX = leerArchivo(ArchivoX);
        vectorY = leerArchivo(ArchivoY);
        numeroElementos = vectorX.numeroElementos();
        promedioX = obtenerPromedioX();
        promedioY = obtenerPromedioY();
        sumaProductos = obtenerSumaProductos();
        sumaRestaMediaYCuadrado = obtenerRestasMediaYCuadrado();
        sumaRestaMediaXCuadrado = obtenerRestasMediaXCuadrado();
        if (tipo.getValor()==1){
            covarianza = obtenerCovarianza();
            r =  obtenerCoeficienteMuestral();
        }else{
            desviacionX = obtenerDesviacionX();
            desviacionY = obtenerDesviacionY();
            covarianza = obtenerCovarianza();
            p = obtenerCoeficientePoblacional();
        }
    }

    /**
     * Lee el contenido de un archivo y lo guarda en una lista dinamica
     * @param archivo es el nombre del archivo a leer
     * @return regresa una lista dinamica con los datos del archivo
     */
    public ListaDinamica leerArchivo(String archivo){
        try {
            ListaDinamica listaLineas = new ListaDinamica();
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
     * Obtiene el promedio de los valores de x
     * @return regresa el promedio
     */
    public double obtenerPromedioX(){
        if (!vectorX.vacia()){
            double promedio =0.0;
            Nodo iterador = vectorX.getPrimerNodo();
            while (iterador != null){
                promedio = promedio + Double.parseDouble((String) iterador.getContenido());
                iterador = iterador.getNodoDer();
            }
            promedio = promedio / numeroElementos;
            return promedio;
        }else{
            return 0.0;
        }
    }

    /**
     * Obtiene el promedio de los valores de y
     * @return regresa el promedio
     */
    public double obtenerPromedioY(){
        if (!vectorY.vacia()){
            double promedio =0.0;
            Nodo iterador = vectorY.getPrimerNodo();
            while (iterador != null){
                promedio = promedio + Double.parseDouble((String) iterador.getContenido());
                iterador = iterador.getNodoDer();
            }
            promedio = promedio / numeroElementos;
            return promedio;
        }else{
            return 0.0;
        }
    }

    /**
     * Obtiene la suma de los productos (x-promediox) * (y-promedioy)
     * @return regresa la suma
     */
    public double obtenerSumaProductos(){
        if (!vectorX.vacia() && !vectorY.vacia()){
            if (vectorX.numeroElementos() == vectorY.numeroElementos()){
                double suma =0.0;
                Nodo iteradorX = vectorX.getPrimerNodo();
                Nodo iteradorY = vectorY.getPrimerNodo();
                while (iteradorX != null){
                    suma = suma + ((Double.parseDouble((String) iteradorX.getContenido()) - promedioX) * (Double.parseDouble((String) iteradorY.getContenido()) - promedioY));
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
     * Obtenemos la suma de las restas de tipo x-promediox
     * @return regresa la suma
     */
    public double obtenerRestasMediaXCuadrado(){
        if (!vectorX.vacia()){
            double suma = 0.0;
            Nodo iterador = vectorX.getPrimerNodo();
            while (iterador!=null){
                suma = suma+ Math.pow((Double.parseDouble((String) iterador.getContenido())) - promedioX,2);
                iterador= iterador.getNodoDer();
            }
            return suma;
        }else{
            return 0.0;
        }
    }

    /**
     * Obtenemos la suma de las restas de tipo y-promedioy
     * @return regresa la suma
     */
    public double obtenerRestasMediaYCuadrado(){
        if (!vectorY.vacia()){
            double suma = 0.0;
            Nodo iterador = vectorY.getPrimerNodo();
            while (iterador!=null){
                suma = suma+ Math.pow((Double.parseDouble((String) iterador.getContenido())) - promedioY,2);
                iterador= iterador.getNodoDer();
            }
            return suma;
        }else{
            return 0.0;
        }
    }

    /**
     * Obtenemos la desviacion muestral tipica de el vector y
     * @return retorna la desviacion muestral x
     */
    public double obtenerDesviacionX(){
        return Math.sqrt(sumaRestaMediaXCuadrado/numeroElementos);
    }

    /**
     * Obtenemos la desviacion muestral tipica de el vector y
     * @return retorna la desviacion muestral y
     */
    public double obtenerDesviacionY(){
        return Math.sqrt(sumaRestaMediaYCuadrado/numeroElementos);
    }

    /**
     * Obtiene la covarianza
     * @return regresa el valor de la covarianza
     */
    public double obtenerCovarianza(){
        return (sumaProductos)/ (numeroElementos -1 );
    }

    /**
     * Obtenemos el coeficiente de pearson sobre la muestra
     * @return regresa el valor del coeficiente
     */
    public double obtenerCoeficienteMuestral(){
        return sumaProductos/ Math.sqrt(sumaRestaMediaXCuadrado* sumaRestaMediaYCuadrado);
    }

    /**
     * Obtenemos el coeficiente de pearson sobre la poblacion
     * @return regresa el valor del coeficiente
     */
    public double obtenerCoeficientePoblacional(){
        return covarianza/ (desviacionX*desviacionY);
    }

    /**
     * Obtenemos el coeficiente calculado
     * @return regresamos el valor del coeficiente
     */
    public double getCoeficienteMuestral(){
        return r;
    }
    /**
     * Obtenemos el coeficiente calculado
     * @return regresamos el valor del coeficiente
     */
    public double getCoeficientePoblacional(){
        return p;
    }

    /**
     * Obtenemos la interpretacion de la correlacion de la muestra
     * @return regresamos la interpretacion como cadena
     */
    public String getInterpretacionMuestra(){
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
     * Obtenemos la interpretacion de la correlacion de la poblacion
     * @return regresamos la interpretacion como cadena
     */
    public String getInterpretacionPoblacion(){
        if (p == 0) {
            return "Ninguna Correlacion";
        }
        if (p == 1) {
            return "Correlacion Positiva perfecta";
        }
        if (p > 0 && p < 1) {
            return "Correlacion Positiva";
        }
        if (p == -1) {
            return "Correlacion Negativa perfecta";
        }
        if (p > -1 && p < 0) {
            return "Correlacion Negativa";
        }
        return "ocurrio algun error";
    }
}
