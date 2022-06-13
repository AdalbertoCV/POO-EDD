package herramientas.texto;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.PilaEstatica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Menus {
    private PilaEstatica pila;
    private int numLineas;
    private String nomArchivo;
    private BufferedReader bufferedReader;
    private String path;


    public Menus() throws IOException {// El constructor recibe el nombre de el archivo a trabajar y lo lee...
        contarLineas("src\\herramientas\\texto\\TextosMenus\\MenuPrincipal.txt");
        pila = new PilaEstatica(numLineas);
        leerArchivo("src\\herramientas\\texto\\TextosMenus\\MenuPrincipal.txt");
        path = "src\\herramientas\\texto\\TextosMenus\\";
    }

    /**
     * lee el archivo enviado al constructor y obtiene la cantidad de lineas
     * @param archivo es el archivo a leer
     * @return regresa true si se pudo leer y false si no
     */
    protected boolean leerArchivo(String archivo) {
        try {
            bufferedReader = new BufferedReader(new FileReader(archivo));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                SalidaPorDefecto.consola( linea + "\n");
                linea = bufferedReader.readLine();
            }
            vaciarPila();
            llenarPila(numLineas, archivo);
            bufferedReader.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * cuenta las lieneas para inicializar la pila
     * @param archivo nombre del archivo
     * @throws IOException
     */
    protected void contarLineas(String archivo) throws IOException {
        nomArchivo = archivo;
        SalidaPorDefecto.consola("Leyendo archivo Seleccionado: \n");
        bufferedReader = new BufferedReader(new FileReader(archivo));
        numLineas = 0; //Contamos la cantidad de lineas del archivo.
        String linea = bufferedReader.readLine();
        while (linea != null) {
            numLineas++;
            linea = bufferedReader.readLine();
        }
    }

    /**
     * llena la pila con las lineas del archivo del menu
     * @param lineas
     * @return
     */
    protected boolean llenarPila(int lineas, String archivo){
        try{
            pila = new PilaEstatica(lineas);
            bufferedReader = new BufferedReader(new FileReader(archivo));
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
     * abre un archivo segun la seleccion al mostrar el menu
     * @param opcion es la opcion seleccionada
     * @return regresa true si se logro abrir el nuevo menu y false si no
     */
    public boolean abrir(int opcion){
        if (opcion == 4){
            leerArchivo(nomArchivo);
            return true;
        }
        opcion--;
        if (opcion>=0 && opcion<pila.numeroElementos()){
            int numElems = pila.numeroElementos();
            for (int pos =0; pos< numElems; pos++ ){
                String elementoPila = (String) pila.pila.getDatos()[pos];
                String nombreArchivo = elementoPila.substring(4,elementoPila.length());
                if (pos == opcion){
                    pila.pila.eliminar(pos);
                    return leerArchivo(path + nombreArchivo+".txt");
                }
            }
            return false;
        }else{
            return false;
        }
    }

    /**
     * Vacia la pila cuando se requiere
     */
    public void vaciarPila(){
        if (!pila.vacio()){
            int numElems = pila.numeroElementos();
            for (int pos =0; pos<numElems; pos++){
                pila.quitar();
            }
        }
    }

}
