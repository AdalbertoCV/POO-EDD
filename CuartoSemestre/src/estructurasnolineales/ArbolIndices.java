package estructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ColaDinamica;
import estructuraslineales.ListaDinamica;
import estructurasnolineales.auxiliares.NodoBusquedaArbol;
import java.io.*;
import java.util.Objects;

public class ArbolIndices {
    protected ArbolBinarioBusqueda arbol;
    protected int numColumnas;
    protected String pathArchivo;
    protected int posValor;

    public ArbolIndices(String archivo, int posicionValor) throws IOException {
        arbol = new ArbolBinarioBusqueda();
        pathArchivo = archivo;
        posValor = posicionValor;
        leerArchivoPK(pathArchivo);
    }

    /**
     * Lee el archivo registro por registro
     * @param nombre es el nombre del archivo a leer
     * @throws IOException si no se encuentra el archivo se lanza una excepcion
     */
    public void leerArchivoPK(String nombre) throws IOException {
        RandomAccessFile archivo = null;
        try {
            archivo = new RandomAccessFile(nombre, "r");
            //archivo.readLine();
            String cad= archivo.readLine();
            while(true) {
                NodoBusquedaArbol nodoNuevo = new NodoBusquedaArbol();
                nodoNuevo.setDireccion(archivo.getFilePointer());
                cad =  archivo.readLine();
                if (cad==null)
                    break;
                String[] valores = cad.split("\t");
                nodoNuevo.setIndice(valores[posValor]);
                numColumnas = valores.length;
                NodoBusquedaArbol busqueda = (NodoBusquedaArbol) arbol.buscar(nodoNuevo.getIndice());
                if (busqueda!=null){
                    busqueda.setDireccion(nodoNuevo.getDireccion());
                }else{
                    arbol.agregar(nodoNuevo);
                }
            }
        } catch (FileNotFoundException fe) {
        }
        archivo.close();
    }


    /**
     * Elimina del arbol y del archivo un registro indicado por su indice
     * @param indice es el indice del elemento a eliminar
     * @return regresa el elemento eliminado o null si no se encontro
     * @throws IOException si no se encuentra el archivo se lanza una excepcion
     */
    public Object eliminarIndice(String indice) throws IOException {
        NodoBusquedaArbol busqueda = (NodoBusquedaArbol) arbol.buscar(indice);
        ColaDinamica cola = new ColaDinamica();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathArchivo));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                String[] valores = linea.split("\t");
                if (busqueda!=null){
                    if (Objects.equals(valores[posValor], indice)){
                        SalidaPorDefecto.consola("Eliminando indice "+ indice+"\n");
                        arbol.eliminar(busqueda);
                    }else{
                        cola.poner(linea);
                    }
                }
                linea = bufferedReader.readLine();
            }
            if (busqueda!=null){
                reescribirArchivo(cola);
            }
            bufferedReader.close();
        } catch (Exception e) {
        }
        return busqueda;
    }

    /**
     * Agrega un nuevo registro con nuevos datos
     * @param datos son los datos a colocar en el registro
     * @return regresa true si se pudo agregar y false si no
     */
    public boolean agregarDatos (String datos){
        String[] nuevosDatos = datos.split("\t");
        if (nuevosDatos.length == numColumnas){
            NodoBusquedaArbol busqueda = (NodoBusquedaArbol) arbol.buscar(nuevosDatos[posValor]);
            if (busqueda==null || posValor!=0){
                ColaDinamica cola = new ColaDinamica();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(pathArchivo));
                    String linea = bufferedReader.readLine();
                    while (linea != null) {
                        cola.poner(linea);
                        linea = bufferedReader.readLine();
                    }
                    cola.poner(datos);
                    reescribirArchivo(cola);
                    bufferedReader.close();
                    //leerArchivoPK(pathArchivo);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Reescribe los registros del archivo exceptuando los eliminados y agregando los nuevos
     * @param cola recibe una cola con los registros a escribir
     * @throws IOException si no se encuentra el archivo se lanza una excepcion
     */
    public void reescribirArchivo(ColaDinamica cola) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathArchivo));
        while (!cola.vacio()){
            bufferedWriter.write(cola.quitar()+"\n");
        }
        bufferedWriter.close();
    }

    /**
     * Busca un valor segun su indice
     * @param indice es el indice del elemento a buscar
     * @throws IOException si no se encuentra el archivo se lanza una excepcion
     */
    public void buscarIndice(String indice) throws IOException {
        NodoBusquedaArbol busqueda = (NodoBusquedaArbol) arbol.buscar(indice);
        if (busqueda!=null){
            RandomAccessFile archivo = null;
            try {
                archivo = new RandomAccessFile(pathArchivo, "r");
                ListaDinamica lista = busqueda.obtenerDirecciones();
                for (int pos =0; pos< lista.numeroElementos(); pos++){
                    archivo.seek((Long) lista.obtener(pos));
                    SalidaPorDefecto.consola(archivo.readLine()+"\n");
                }
            }
            catch (FileNotFoundException fe) {
            }
            archivo.close();
        }else{
            SalidaPorDefecto.consola("no hay elementos que coincidan con la busqueda. \n");
        }
    }
}
