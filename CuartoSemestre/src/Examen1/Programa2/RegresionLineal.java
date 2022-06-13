
package Examen1.Programa2;

import estructuraslineales.ListaEstatica;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Clase regresion Lineal
 * Esta clase sirve para predecir el valor de una casa segun el numero de habitaciones
 * @author Adalberto Cerrillo Vazquez
 * @version 1.0
 */
public class RegresionLineal {
    /**
     * valoresX es la lista estatica donde se almacenaran los valores de x del archivo X.txt
     */
    protected ListaEstatica valoresX;
    /**
     * valoresY es la lista estatica donde se almacenaran los valores de y del archivo Y.txt
     */
    protected ListaEstatica valoresY;
    /**
     * numLineas es la cantidad de lineas (o elementos) de ambos archivos
     */
    protected int numLineas;
    /**
     * es la constante de correlacion de la regresion lineal
     */
    protected  double correlacion;
    /**
     * Es la desviacion estandar del vector x
     */
    protected double desviacionx;
    /**
     * Es la desviacion estandar del vector y
     */
    protected double desviaciony;
    /**
     * Es la media del vector x
     */
    protected double promediox;
    /**
     * Es la media del vector y
     */
    protected double promedioy;
    /**
     * Es el valor de p de la ecuacion final
     */
    protected double p;
    /**
     * Es el valor de b de la ecuacion final
     */
    protected double b;
    /**
     * Es el valor de la suma de los productos (x-promediox) * (y - promedioy)
     */
    protected double sumaProductos;

    public RegresionLineal(String nomArchivoX, String nomArchivoY){
        numLineas = contarLineas(nomArchivoX);
        valoresX = new ListaEstatica(numLineas);
        valoresY = new ListaEstatica(numLineas);
        leerArchivo(nomArchivoX, valoresX);
        leerArchivo(nomArchivoY, valoresY);
        obtenerPromedioX();
        obtenerPromedioY();
        obtenerDesviacionX();
        obtenerDesviacionY();
        obtenerSumaProductos();
        obtenerCorrelacion();
        inicializarValoresPYB();
    }


    /**
     * lee el archivo seleccionado por lineas y guarda sus datos en las lista de valores x y Y
     * @param archivo es el archivo a leer
     */
    protected void leerArchivo(String archivo, ListaEstatica lista) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                lista.agregar(linea);
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
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
     * Obtiene el promedio de los valores de x
     * @return regresa el promedio
     */
    public double obtenerPromedioX(){
        promediox =0.0;
        for (int pos=0; pos< valoresX.numeroElementos(); pos++){
            promediox = promediox + Double.parseDouble((String) valoresX.obtener(pos));
        }
        promediox = promediox / valoresX.numeroElementos();
        return promediox;
    }

    /**
     * Obtiene el promedio de los valores de y
     * @return regresa el promedio
     */
    public double obtenerPromedioY(){
        promedioy =0.0;
        for (int pos=0; pos< valoresY.numeroElementos(); pos++){
            promedioy = promedioy + Double.parseDouble((String) valoresY.obtener(pos));
        }
        promedioy = promedioy / valoresY.numeroElementos();
        return promedioy;
    }

    /**
     * Obtenemos la desviacion estandar de x
     * @return regresamos la desviacion
     */
    public double obtenerDesviacionX(){
        desviacionx =0;
        for (int pos =0; pos< valoresX.numeroElementos(); pos++){
            desviacionx = desviacionx + Math.pow(Double.parseDouble((String) valoresX.obtener(pos))- promediox,2);
        }
        desviacionx = Math.sqrt(desviacionx/valoresX.numeroElementos());
        return desviacionx;
    }

    /**
     * Obtenemos la desviacion estandar de y
     * @return regresamos la desviacion
     */
    public double obtenerDesviacionY(){
        desviaciony =0;
        for (int pos =0; pos< valoresY.numeroElementos(); pos++){
            desviaciony = desviaciony + Math.pow(Double.parseDouble((String) valoresY.obtener(pos))- promedioy,2);
        }
        desviaciony = Math.sqrt(desviaciony/valoresY.numeroElementos());
        return desviaciony;
    }

    /**
     * Calcula el costo de un valor x introducido por el usuario
     * @param valorX es el valor indicado
     * @return regresa el costo calculado
     */
    public double calcularCosto(double valorX){
        double costo= (b + (p*valorX));
        return costo;
    }

    /**
     * Obtenemos la ecuacion final
     * @return regresa una cadena con la ecuacion
     */
    public String obtenerEcuacion(){
        String ecuacion = "y= "+ b+ "+"+p+"x";
        return ecuacion;
    }

    /**
     * Obtiene la suma de los productos de x-promediox y y-promedioy
     * @return regresa el valor de la suma
     */
    public double obtenerSumaProductos(){
        sumaProductos =0;
        for (int pos=0; pos< valoresX.numeroElementos(); pos++){
            sumaProductos = sumaProductos + ((Double.parseDouble((String) valoresX.obtener(pos)) - promediox) * (Double.parseDouble((String) valoresY.obtener(pos))- promedioy));
        }
        return sumaProductos;
    }

    /**
     * obtenemos la correlacion
     * @return regresa el valor de la correlacion
     */
    public double obtenerCorrelacion(){
        correlacion = sumaProductos/ (valoresX.numeroElementos() * desviacionx * desviaciony);
        return correlacion;
    }

    /**
     * Inicializa los valores de p y b
     */
    public void inicializarValoresPYB(){
        p = correlacion * (desviaciony/ desviacionx);
        b = promedioy - (p * promediox);
    }

}
