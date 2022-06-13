package estructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstatica;
import enumerados.*;


public class Matriz2 {
    protected int renglones;
    protected int columnas;
    protected Object[][] datos;
    protected int ultimoRenglon;
    protected int ultimaColumna;

    public Matriz2(int renglones, int columnas) {
        this.renglones = renglones;
        this.columnas = columnas;
        datos = new Object[renglones][columnas];
        this.ultimaColumna = -1;
        this.ultimoRenglon = -1;
    }

    public Matriz2(int renglones, int columnas, Object valor) {
        this.renglones = renglones;
        this.columnas = columnas;
        datos = new Object[renglones][columnas];
        this.ultimaColumna = -1;
        this.ultimoRenglon = -1;
        rellenar(valor);
    }

    /**
     * Rellena la matriz
     *
     * @param valor es el valor a rellenar
     */
    public void rellenar(Object valor) {
        for (int fila = 0; fila < renglones; fila++) { //recorre fila por fila
            //podemos asumir que en nuestro escenario hay solo un renglón
            for (int columna = 0; columna < columnas; columna++) { //recorremos una por una las columnas de un solo renglòn
                datos[fila][columna] = valor;
            }
        }
    }

    /**
     * Obtiene el valor de algun lugar en especifico
     *
     * @param fila indica la ubicacion en fila del valor
     * @param col  indica en que columna buscar
     * @return retorna un objeto que representa el valor encontrado, o null en caso contrario
     */
    public Object obtener(int fila, int col) {
        if (enRango(fila, renglones) == true && enRango(col, columnas) == true) {
            return datos[fila][col];
        } else { //índices fuera de rango
            return null;
        }
    }

    /**
     * Cambia el valor especificado
     *
     * @param fila  especifica el renglon en el que se encuentra
     * @param col   especifica la columna en que se encuentra
     * @param valor especifica el valor nuevo
     * @return retorna <b>true </b> en caso posible, <b>false</b> en caso contrario.
     */
    public boolean cambiar(int fila, int col, Object valor) {
        if (enRango(fila, renglones) == true && enRango(col, columnas) == true) {
            datos[fila][col] = valor;
            return true;
        } else { //índices fuera de rango
            return false;
        }
    }

    /**
     * Revisa el rango
     *
     * @param indice          el indice a considerar
     * @param limiteDimension la dimension a considerar
     * @return
     */
    private boolean enRango(int indice, int limiteDimension) {
        return indice >= 0 && indice < limiteDimension;
    }

    /**
     * Retorna el valor de la cantidad de renglones
     *
     * @return retorna un entero que representa la cantidad de renglones
     */
    public int obtenerRenglones() {
        return renglones;
    }

    /**
     * Retorna el valor de la cantidad de columnas
     *
     * @return retorna un entero que representa la cantidad de columnas
     */
    public int obtenerColumnas() {
        return columnas;
    }

    /**
     * Imprime por columans la matriz
     */
    public void imprimirXColumnas() {
        for (int col = 0; col < columnas; col++) {  //una columna
            for (int fila = 0; fila < renglones; fila++) { //extrae renglón por renglón
                SalidaPorDefecto.consola(datos[fila][col] + " ");
            }
            //Cuando termina cada renglon, hacer salto de línea
            SalidaPorDefecto.consola("\n");
        }
    }

    /**
     * Imprime por renglones la matriz
     */
    public void imprimirXRenglones() {
        for (int fila = 0; fila < renglones; fila++) {   //reng x reng
            for (int col = 0; col < columnas; col++) { //extrae col x col
                SalidaPorDefecto.consola(datos[fila][col] + " ");
            }
            //Cuando termina cada columna, hacer salto de línea
            SalidaPorDefecto.consola("\n");
        }
    }

    /**
     * Cambia la ubicacion de renglones y columnas
     */
    public void transpuesta() {
        int Col = renglones;
        int Ren = columnas;
        Object[][] nuevoDatos = new Object[Ren][Col];
        int contador = 0;
        while (contador < renglones) {
            int contador2 = 0;
            while (contador2 < columnas) {
                nuevoDatos[contador2][contador] = datos[contador][contador2];
                contador2++;
            }
            contador++;
        }
        columnas = Col;
        renglones = Ren;
        datos = nuevoDatos;
    }

    /**
     * Clona la matriz y regresa la copia
     *
     * @return retorna la copia en tipo Matriz2
     */
    public Matriz2 clonar() {
        Matriz2 copia = new Matriz2(renglones, columnas);
        int contador = 0;
        while (contador < renglones) {
            int contador2 = 0;
            ListaEstatica temporal = new ListaEstatica(columnas);
            while (contador2 < columnas) {
                temporal.agregar(datos[contador][contador2]);
                contador2++;
            }
            copia.agregarRenglon(temporal);
            contador++;
        }
        return copia;
    }

    /**
     * Revisa so dos matrices son iguales
     *
     * @param matriz2 es la matriz con la que se va a comparar
     * @return retorna <b>true</b> en caso de ser cierto, <b>false</b> en caso contrario.
     */
    public boolean esIgual(Matriz2 matriz2) {
        int col = matriz2.obtenerColumnas();
        int ren = matriz2.obtenerRenglones();
        if (columnas == col && renglones == ren) {
            int contador = 0;
            while (contador < renglones) {
                int contador2 = 0;
                while (contador2 < columnas) {
                    if (matriz2.obtener(contador, contador2) == datos[contador][contador2]) {
                        contador2++;
                    } else {
                        return false;
                    }
                }
                contador++;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Crea un vector columa si la matriz esta vacia
     *
     * @param filas la cantidad de filas a crear
     * @param valor el valor a insertar
     * @return retorna <b>true</b> en caso posible, <b>false</b> en caso contrario.
     */
    public boolean vectorColumna(int filas, Object valor) {
        if (vacia()) {
            if (filas <= renglones) {
                int contador = 0;
                while (contador < filas) {
                    datos[contador][0] = valor;
                    contador++;
                }
                ultimoRenglon = filas - 1;
                ultimaColumna = 0;
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * Crea un vector renglon si la matriz esta vacia
     *
     * @param column representa la cantidad de columnas a crear
     * @param valor  representa el valor a llenar
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario.
     */
    public boolean vectorRenglon(int column, Object valor) {
        if (vacia()) {
            if (column <= columnas) {
                int contador = 0;
                while (contador < column) {
                    datos[0][contador] = valor;
                    contador++;
                }
                ultimaColumna = column - 1;
                ultimoRenglon = 0;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Redefine las dimensiones de una matriz a otra
     *
     * @param matriz2 es la matriz a considerar
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario.
     */
    public boolean redefinir(Matriz2 matriz2) {
        if (matriz2 instanceof Matriz2 && !matriz2.vacia()) {
            int col = matriz2.obtenerColumnas();
            int reng = matriz2.obtenerRenglones();
            ultimaColumna = matriz2.obtenerUltimaColumna();
            ultimoRenglon = matriz2.obtenerUltimoRenglon();
            columnas = col;
            renglones = reng;
            Object[][] nuevo = new Object[reng][col];
            int contador = 0;
            while (contador < reng) {
                int contador2 = 0;
                while (contador2 < col) {
                    nuevo[contador][contador2] = matriz2.obtener(contador, contador2);
                    contador2++;
                }
                contador++;
            }
            datos = nuevo;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Agrega un renglon
     *
     * @param arreglo contiene el renglon a agregar
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario.
     */
    public boolean agregarRenglon(ListaEstatica arreglo) {
        if (arreglo instanceof ListaEstatica) {
            if (arreglo.numeroElementos()  == columnas) {
                if (ultimoRenglon < renglones - 1) {
                    ultimoRenglon++;
                    int contador = 0;
                    while (contador < columnas) {
                        datos[ultimoRenglon][contador] = arreglo.obtener(contador);
                        contador++;
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Agrega una columna a la matriz
     *
     * @param arreglo contiene la columna a agregar
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario
     */
    public boolean agregarColumna(ListaEstatica arreglo) {
        if (arreglo instanceof ListaEstatica) {
            if (arreglo.numeroElementos() + 1 == renglones) {
                if (ultimaColumna < columnas - 1) {
                    ultimaColumna++;
                    int contador = 0;
                    while (contador < renglones) {
                        datos[contador][ultimaColumna] = arreglo.obtener(contador);
                        contador++;
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Convierte la matriz a Matriz3
     *
     * @param matrices contiene la profunidad
     * @return retorna una Matriz3
     */
    public Matriz3 aMatriz3(ListaEstatica matrices) {
        int profundidad = matrices.numeroElementos() + 1;
        for (int pos = 0; pos < profundidad; pos++) {
            this.agregarMatrizXColumna((Matriz2) matrices.obtener(pos));
        }
        Matriz3 nuevaMatriz = new Matriz3(renglones, columnas, profundidad);
        for (int fila = 0; fila < renglones; fila++) {
            for (int col = 0; col < columnas; col++) {
                for (int prof = 0; prof < profundidad; prof++) {
                    nuevaMatriz.datos[fila][col][prof] = datos[fila][col];
                }

            }
        }
        return nuevaMatriz;
    }

    /**
     * Agrega columnas de otra matriz
     *
     * @param matriz2 es la matriz de donde se sacaran las columnas
     * @return retorna <b>true </b> en caso posible, <b>false</b> de lo contrario
     */
    public boolean agregarMatrizXColumna(Matriz2 matriz2) {
        if (!matriz2.vacia()) {
            ListaEstatica column = obtenerMatrizColumnas(matriz2);
            ListaEstatica columnO = obtenerMatrizColumnas(this);
            Matriz2 temporal = new Matriz2(renglones, (column.numeroElementos() + 1) + columnas);
            int contador = 0;
            while (contador <= columnO.numeroElementos()) {
                ListaEstatica tempora = (ListaEstatica) columnO.obtener(contador);
                temporal.agregarColumna(tempora);
                contador++;

            }
            contador = 0;
            while (contador <= column.numeroElementos()) {
                ListaEstatica tempora = (ListaEstatica) column.obtener(contador);
                temporal.agregarColumna(tempora);
                contador++;
            }
            redefinir(temporal);
            return true;

        } else {
            return false;
        }


    }

    /**
     * Agrega renglones de otra matriz
     *
     * @param matriz2 es la matriz de donde se sacaran los renglones
     * @return retorna <b>true </b> en caso posible, <b>false</b> de lo contrario
     */
    public boolean agregarMatrizXRenglon(Matriz2 matriz2) {
        if (!matriz2.vacia()) {
            ListaEstatica column = obtenerMatrizRenglones(matriz2);
            ListaEstatica columnO = obtenerMatrizRenglones(this);
            Matriz2 temporal = new Matriz2((column.numeroElementos() + 1) + renglones, +columnas);
            int contador = 0;
            while (contador <= columnO.numeroElementos()) {
                ListaEstatica tempora = (ListaEstatica) columnO.obtener(contador);
                temporal.agregarRenglon(tempora);
                contador++;

            }
            contador = 0;
            while (contador <= column.numeroElementos()) {
                ListaEstatica tempora = (ListaEstatica) column.obtener(contador);
                temporal.agregarRenglon(tempora);
                contador++;
            }
            redefinir(temporal);
            return true;

        } else {
            return false;
        }
    }

    /**
     * Transforma la matriz actual a vectorColumna y la retorna
     *
     * @return retorna una Matriz2 transformada
     */
    public Matriz2 aVectorColumna() {
        if (!vacia()) {
            int nuevolimite = columnas * renglones;
            Matriz2 regresar = new Matriz2(nuevolimite, 1);
            ListaEstatica nuevo = new ListaEstatica(nuevolimite);
            int contador = 0;
            int contador2 = 0;
            while (contador < columnas && contador2 < nuevolimite) {
                int contador3 = 0;
                while (contador3 < renglones) {
                    nuevo.agregar(datos[contador3][contador]);
                    contador3++;
                    contador2++;
                }
                contador++;
            }
            regresar.agregarColumna(nuevo);
            return regresar;
        } else {
            return null;
        }
    }

    /**
     * Transforma la matriz actual a vectorRenglon y la retorna
     *
     * @return retorna una Matriz2 transformada
     */
    public Matriz2 aVectorRenglon() {
        if (!vacia()) {
            int nuevolimite = columnas * renglones;
            Matriz2 regresar = new Matriz2(1, nuevolimite);
            ListaEstatica nuevo = new ListaEstatica(nuevolimite);
            int contador = 0;
            int contador2 = 0;
            while (contador < renglones && contador2 < nuevolimite) {
                int contador3 = 0;
                while (contador3 < columnas) {
                    nuevo.agregar(datos[contador][contador3]);
                    contador3++;
                    contador2++;
                }
                contador++;
            }
            regresar.agregarRenglon(nuevo);
            return regresar;
        } else {
            return null;
        }
    }

    /**
     * Elimina una columna
     *
     * @param tipoColumna Especifica el tipo de columna
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario.
     */
    public boolean eliminarColumna(TipoColumna tipoColumna) {
        if (!vacia()) {
            if (tipoColumna == TipoColumna.IZQUIERDA) {
                ListaEstatica lista = obtenerMatrizColumnas(this);
                columnas--;
                Matriz2 nueva = new Matriz2(renglones, 0);
                int contador = 0;
                while (contador < lista.numeroElementos()) {
                    nueva.agregarColumna((ListaEstatica) lista.obtener(contador));
                    contador++;
                }
                this.redefinir(nueva);
                return true;
            } else {
                if (tipoColumna == TipoColumna.DERECHA) {
                    ListaEstatica lista = obtenerMatrizColumnas(this);
                    columnas--;
                    Matriz2 nueva = new Matriz2(renglones, 0);
                    int contador = 0;
                    while (contador < lista.numeroElementos() - 1) {
                        nueva.agregarColumna((ListaEstatica) lista.obtener(contador));
                        contador++;
                    }
                    this.redefinir(nueva);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * Elimina un renglon
     *
     * @param tipoRenglon Especifica el tipo de renglon a eliminar
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario.
     */
    public boolean eliminarRenglon(TipoRenglon tipoRenglon) {
        if (vacia()) {
            if (tipoRenglon == TipoRenglon.SUPERIOR) {
                ListaEstatica lista = obtenerMatrizRenglones(this);
                renglones--;
                Matriz2 nueva = new Matriz2(0, columnas);
                int contador = 1;
                while (contador < lista.numeroElementos()) {
                    nueva.agregarRenglon((ListaEstatica) lista.obtener(contador));
                    contador++;
                }
                this.redefinir(nueva);
                return true;
            } else {
                if (tipoRenglon == TipoRenglon.INFERIOR) {
                    ListaEstatica lista = obtenerMatrizRenglones(this);
                    renglones--;
                    Matriz2 nueva = new Matriz2(0, columnas);
                    int contador = 0;
                    while (contador < lista.numeroElementos() - 1) {
                        nueva.agregarRenglon((ListaEstatica) lista.obtener(contador));
                        contador++;

                    }
                    this.redefinir(nueva);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * Elimina un renglon
     *
     * @param renglon es el renglon a eliminar
     * @return retorna <b>true </b> si fue posible, <b>false</b> de lo contrario
     */
    public boolean eliminarRenglon(int renglon) {
        if (!vacia()) {
            ListaEstatica lista = obtenerMatrizRenglones(this);
            renglones--;
            Matriz2 nueva = new Matriz2(0, columnas);
            int contador = 0;
            while (contador < lista.numeroElementos()) {
                if (contador != renglon) {
                    nueva.agregarRenglon((ListaEstatica) lista.obtener(contador));
                }
                contador++;

            }
            this.redefinir(nueva);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Elimina una columna
     *
     * @param columna es la posicion de la columna a eliminar
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario
     */
    public boolean eliminarColumna(int columna) {
        if (!vacia()) {
            ListaEstatica lista = obtenerMatrizColumnas(this);
            columnas--;
            Matriz2 nueva = new Matriz2(renglones, 0);
            int contador = 0;
            while (contador < lista.numeroElementos()) {
                if (contador != columna) {
                    nueva.agregarColumna((ListaEstatica) lista.obtener(contador));
                }
                contador++;
            }
            this.redefinir(nueva);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtiene los renglones de una matriz
     *
     * @param matriz2 es la matriz a considerar
     * @return retorna una listaEstatica con los renglones
     */
    public ListaEstatica obtenerMatrizRenglones(Matriz2 matriz2) {
        ListaEstatica renglonesMatriz = new ListaEstatica(matriz2.obtenerRenglones());
        int contador = 0;
        while (contador < matriz2.obtenerRenglones()) {
            ListaEstatica renglonActual = new ListaEstatica(matriz2.obtenerColumnas());
            int contador2 = 0;
            while (contador2 < matriz2.obtenerColumnas()) {
                renglonActual.agregar(matriz2.datos[contador][contador2]);
                contador2++;
            }
            contador++;
            renglonesMatriz.agregar(renglonActual);
        }
        return renglonesMatriz;
    }

    /**
     * Obtiene las columnas de una matriz
     *
     * @param matriz2 la matriz a considerar
     * @return retorna una ListaEstatica con las columnas de la matriz
     */
    public ListaEstatica obtenerMatrizColumnas(Matriz2 matriz2) {
        ListaEstatica columnasMatriz = new ListaEstatica((matriz2.obtenerColumnas()));
        int contador = 0;
        while (contador < matriz2.obtenerColumnas()) {
            ListaEstatica columnaActual = new ListaEstatica(matriz2.obtenerRenglones());
            columnasMatriz.agregar(columnaActual);
            int contador2 = 0;
            while (contador2 < matriz2.obtenerRenglones()) {
                columnaActual.agregar(matriz2.datos[contador2][contador]);
                contador2++;
            }
            contador++;
        }
        return columnasMatriz;
    }

    /**
     * Checa si la matriz esta vacia
     *
     * @return retorna <b>true</b> en caso de estarlo, <b>false</b> en caso contrario
     */
    public boolean vacia() {
        int contador = 0;
        while (contador < renglones) {
            int contador2 = 0;
            while (contador2 < columnas) {
                if (datos[contador][contador2] == null) {
                    contador2++;
                } else {
                    return false;
                }
            }
            contador++;
        }
        return true;
    }

    /**
     * Obtiene el ultimo renglon
     *
     * @return retorna un entero
     */
    public int obtenerUltimoRenglon() {
        return ultimoRenglon;
    }

    /**
     * Retorna la ultima columna
     *
     * @return retorna un entero
     */
    public int obtenerUltimaColumna() {
        return ultimaColumna;
    }

    /**
     * Realiza operaciones de matriz
     *
     * @param elemento el elemento considerar
     */
    public void matrizDiagonal(Object elemento) {
        if (renglones == columnas) {
            for (int diag = 0; diag < renglones; diag++) {
                datos[diag][diag] = elemento;
            }
        } else { //hacer lo que corresponda,

        }
    }

}
