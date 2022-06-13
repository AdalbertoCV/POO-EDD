package Examen2.DistribucionNormal;

import estructuraslineales.ListaDinamica;
import estructuraslineales.auxiliares.Nodo;

import java.io.*;

public class DistribucionNormal {
    private ListaDinamica valoresX;
    private ListaDinamica valoresFx;
    private ListaDinamica valoresZ;
    private ListaDinamica valoresFz;
    private double promedioX;
    private double desviacionX;
    private double promedioZ;
    private double desviacionZ;

    public DistribucionNormal(String archivo) throws IOException {
        valoresX = new ListaDinamica();
        leerArchivo(archivo);
        promedioX = 0.0;
        promedioX = calcularPromedio(promedioX,valoresX,valoresX.getPrimerNodo());
        desviacionX = 0.0;
        desviacionX = calcularDesviacion(desviacionX,valoresX,valoresX.getPrimerNodo(),promedioX);
        valoresFx = new ListaDinamica();
        obtenerF(valoresFx, valoresX.getPrimerNodo(),desviacionX,promedioX);
        crearArchivoF("src\\Examen2\\DistribucionNormal\\Fx.txt", valoresFx);
        valoresZ = new ListaDinamica();
        obtenerZ(valoresZ, valoresX.getPrimerNodo());
        crearArchivoF("src\\Examen2\\DistribucionNormal\\Z.txt", valoresZ);
        promedioZ = 0.0;
        promedioZ = calcularPromedio(promedioZ,valoresZ,valoresZ.getPrimerNodo());
        desviacionZ = 0.0;
        desviacionZ = calcularDesviacion(desviacionZ,valoresZ,valoresZ.getPrimerNodo(),promedioZ);
        valoresFz = new ListaDinamica();
        obtenerF(valoresFz,valoresZ.getPrimerNodo(), desviacionZ, promedioZ);
        crearArchivoF("src\\Examen2\\DistribucionNormal\\Fz.txt", valoresFz);
    }

    /**
     * Lee las lineas del archivo en forma recursiva
     * @param lista es la lista donde se van guardando los datos
     * @param linea es la linea actual
     * @param lector es el lector del archivo
     * @return regresa la lista cuando ya se guardaron todos los datos
     * @throws IOException genera una excepcion si no se puede leer alguna linea
     */
    public ListaDinamica leerLineas(ListaDinamica lista,String linea,BufferedReader lector) throws IOException {
        if (linea== null){
            return lista;
        }else{
            lista.agregar(linea);
            return leerLineas(lista, lector.readLine(), lector);
        }
    }

    /**
     * Lee el archivo invicando a un metodo recursivo
     * @param archivo es el archivo a leer
     * @throws IOException lanza una excepcion si no se puede leer el archivo
     */
    public void leerArchivo(String archivo) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
        leerLineas(valoresX,bufferedReader.readLine(),bufferedReader);
    }

    /**
     * Calcula el promedio de x de forma recuriva
     * @param promedio es la variable para almacenar el promedio
     * @param lista son los valores de x o z
     * @param iter es el nodo iterador
     * @return regresa el promedio
     */
    public double calcularPromedio(double promedio, ListaDinamica lista, Nodo iter){
        if (iter == null){
            return promedio/lista.numeroElementos();
        }else{
            promedio = promedio + Double.parseDouble((String) iter.getContenido());
            return calcularPromedio(promedio,lista,iter.getNodoDer());
        }
    }

    /**
     * Calcula la desviacion tipica de forma recursiva
     * @param desviacion es la variable donde se almacenda la desviacion
     * @param lista son los valores de x o z
     * @param iter es el nodo iterador
     * @param promedio es el promedio de los valores x o y
     * @return regresa el valor de la desviacion
     */
    public double calcularDesviacion(double desviacion, ListaDinamica lista, Nodo iter,double promedio){
        if (iter == null){
            return Math.sqrt(desviacion/lista.numeroElementos());
        }else{
            desviacion = desviacion + Math.pow(Double.parseDouble((String) iter.getContenido())-promedio,2);
            return calcularDesviacion(desviacion,lista,iter.getNodoDer(),promedio);
        }
    }

    /**
     * Obtiene los valores de f(x) 0 f(z)
     * @param lista es la lista donde se guardaran los datos
     * @param iter es el nodo iterador
     * @param desviacion es la desviacion
     * @param promedio es el promedio
     * @return regresa la lista con los valores de f
     */
    public ListaDinamica obtenerF(ListaDinamica lista, Nodo iter, double desviacion, double promedio){
        if (iter == null){
            return lista;
        }else{
            lista.agregar((1/(desviacion*(Math.sqrt(2*3.1416)))) * Math.exp((-1/(2* Math.pow(desviacion,2)))* Math.pow((Double.parseDouble((String)iter.getContenido())-promedio),2)));
            return obtenerF(lista, iter.getNodoDer(), desviacion, promedio);
        }
    }

    /**
     * Obtiene los valores de z
     * @param lista es la lista donde se guardaran los valores
     * @param iter es el nodo iterador
     * @return regresa la lista cuando se terminaron de guardar los datos
     */
    public ListaDinamica obtenerZ(ListaDinamica lista, Nodo  iter){
        if (iter == null){
            return lista;
        }else{
            lista.agregar(String.valueOf((Double.parseDouble((String) iter.getContenido()) - promedioX)/ desviacionX));
            return obtenerZ(lista, iter.getNodoDer());
        }
    }

    public void crearArchivoF(String nombre, ListaDinamica lista) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(nombre));
        Nodo iter = lista.getPrimerNodo();
        while (iter != null){
            bufferedWriter.write(String.valueOf(iter.getContenido()) + "\n");
            iter = iter.getNodoDer();
        }
        bufferedWriter.close();
    }

    public double getPromedioX() {
        return promedioX;
    }


    public double getDesviacionX() {
        return desviacionX;
    }


    public double getPromedioZ() {
        return promedioZ;
    }


    public double getDesviacionZ() {
        return desviacionZ;
    }
}
