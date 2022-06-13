package estructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstatica;

public class Matriz3 {
    protected int renglones;
    protected int columnas;
    protected int profundidad;
    protected Object datos[][][];

    public Matriz3(int renglones, int columnas, int profundidad){
        this.renglones=renglones;
        this.columnas=columnas;
        this.profundidad=profundidad;
        datos=new Object[renglones][columnas][profundidad];
    }

    public Matriz3(int renglones, int columnas, int profundidad, Object valor){
        this.renglones=renglones;
        this.columnas=columnas;
        this.profundidad=profundidad;
        datos=new Object[renglones][columnas][profundidad];
        rellenar(valor);
    }

    public void rellenar(Object valor){
        for(int fila=0; fila<renglones; fila++){ //recorre fila por fila
            //podemos asumir que en nuestro escenario hay solo un renglón
            for(int columna=0;columna<columnas; columna++){ //recorremos una por una las columnas de un solo renglòn
                //Asumir que hay un solo renglón y una sola columna
                for(int prof=0;prof<profundidad;prof++){ //recorrer una por una las celdas de profundidad
                    datos[fila][columna][prof]=valor;
                }
            }
        }
    }

    public Object obtener(int fila, int col, int prof){
        if(enRango(fila,renglones)==true && enRango(col,columnas)==true && enRango(prof, profundidad)==true ){
            return datos[fila][col][prof];
        }else{ //índices fuera de rango
            return null;
        }
    }

    public boolean cambiar(int fila, int col, int prof, Object valor){
        if(enRango(fila,renglones)==true && enRango(col,columnas)==true && enRango(prof, profundidad)==true ){
            datos[fila][col][prof]=valor;
            return true;
        }else{ //índices fuera de rango
            return false;
        }
    }

    private boolean enRango(int indice, int limiteDimension){
        if(indice>=0 && indice<limiteDimension){
            return true;
        }else{
            return false;
        }
    }

    public int obtenerRenglones(){
        return renglones;
    }

    public int obtenerColumnas(){
        return columnas;
    }

    public int obtenerProfundidad(){
        return profundidad;
    }

    public void imprimirXColumnas(){
        //Extraer las rebanadas (columnas)
        for(int col=0;col<columnas;col++){  //una por una las rebanadas de columnas
            //ya se tienen tablas 2d o matrices
            for(int fila=0;fila<renglones;fila++){ //extrae renglón por renglón
                //ya tenemos el procesamiento de los renglones de una tabla 2d
                for(int prof=0;prof<profundidad;prof++){ //extraemos las columnas de la matriz 2d, (profundidad)
                    SalidaPorDefecto.consola(datos[fila][col][prof]+" ");
                }
                //Cuando termina todas las columnas, hacemos un salto de línea
                SalidaPorDefecto.consola("\n");
            }
            //Cuando termina todos los renglones de una rebanada, hacer salto de línea
            SalidaPorDefecto.consola("\n");
        }
    }

    //Agregar un método que permita pasar una matriz tridimensional a una matriz bidimensional (pueden resultar varias)
    public ListaEstatica aMatriz2XColumnas(){
        ListaEstatica matrices2=new ListaEstatica(columnas); // el número de columnas (de lamatriz 3d) es el número de matrices bidimensionales

        //Primero recorremos todas las rebanadas que serán matrices bidimensionales
        for(int rebanada=0;rebanada<columnas;rebanada++){
            //Cada vez que yo obtenga una nueva rebanada, será una nueva matriz2
            Matriz2 matriz2Rebanada=new Matriz2(renglones,profundidad); //renglones 3d y columnas 3d
            //hay que ir metiendo los valoresdentro de esta matriz 2d nueva
            //estos valores se obtienen de la matriz 3d
            for(int fila=0;fila<renglones;fila++){
                //por cada fila recorremos cada columna(la profundidad del 3d)
                for(int profCol=0;profCol<profundidad;profCol++){
                    //extraer cada celda y colocarla en la matriz 2d
                    matriz2Rebanada.cambiar(fila,profCol,datos[fila][rebanada][profCol]);
                }
            }
            //cuando termino de agregar todos los valores a mi matriz2, jalado de la matriz3
            //guardamos la matriz2 en mi arreglo de matrices2
            matrices2.agregar(matriz2Rebanada);
        }
        //cuando ya agregamos todas las rebanadas, entonces retornamos el arreglo de matrices2
        return matrices2;
    }

}
