package estructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstatica;
import estructuraslineales.ListaEstaticaNumerica;
import enumerados.TipoLogaritmo;

public class Matriz2Numerica extends Matriz2 {

    public Matriz2Numerica(int renglones, int columnas) {
        super(renglones, columnas,0.0);
    }

    public Matriz2Numerica(int renglones, int columnas, Object valor) {
        super(renglones, columnas, valor);
    }

    /**
     * Rellena la matriz
     * Comprueba si el valor es una instancia de Number
     *
     * @param valor es el valor a rellenar
     */
    @Override
    public void rellenar(Object valor) {
        if (valor instanceof Number) {
            super.rellenar(valor);
        }

    }

    /**
     * Cambia el valor especificado
     * Comprueba que valor sea una instancia de Number
     *
     * @param fila  especifica el renglon en el que se encuentra
     * @param col   especifica la columna en que se encuentra
     * @param valor especifica el valor nuevo
     * @return retorna <b>true </b> en caso posible, <b>false</b> en caso contrario.
     */
    @Override
    public boolean cambiar(int fila, int col, Object valor) {
        if (valor instanceof Number) {
            return super.cambiar(fila, col, valor);
        } else {
            return false;
        }

    }

    /**
     * Revisa so dos matrices son iguales
     * Comprueba que la matriz sea Matriz2Numerica
     *
     * @param matriz2 es la matriz con la que se va a comparar
     * @return retorna <b>true</b> en caso de ser cierto, <b>false</b> en caso contrario.
     */
    @Override
    public boolean esIgual(Matriz2 matriz2) {
        if (matriz2 instanceof Matriz2Numerica) {
            return super.esIgual(matriz2);
        } else {
            return false;
        }
    }

    /**
     * Crea un vector columa si la matriz esta vacia
     * Comprueba que valor sea una instancia de Number
     *
     * @param filas la cantidad de filas a crear
     * @param valor el valor a insertar
     * @return retorna <b>true</b> en caso posible, <b>false</b> en caso contrario.
     */
    @Override
    public boolean vectorColumna(int filas, Object valor) {
        if (valor instanceof Number) {
            return super.vectorColumna(filas, valor);
        } else {
            return false;
        }

    }

    /**
     * Crea un vector renglon si la matriz esta vacia
     * Comprueba que valor sea una instancia de Number
     *
     * @param column representa la cantidad de columnas a crear
     * @param valor  representa el valor a llenar
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario.
     */
    @Override
    public boolean vectorRenglon(int column, Object valor) {
        if (valor instanceof Number) {
            return super.vectorRenglon(column, valor);
        } else {
            return false;
        }
    }

    /**
     * Redefine las dimensiones de una matriz a otra
     * Comprueba que matriz2 sea una instancia de Matriz2Numerica
     *
     * @param matriz2 es la matriz a considerar
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario.
     */
    @Override
    public boolean redefinir(Matriz2 matriz2) {
        if (matriz2 instanceof Matriz2Numerica) {
            return super.redefinir(matriz2);
        } else {
            return false;
        }

    }

    /**
     * Agrega un renglon
     * Comprueba que arreglo sea instancia de ListaEstaticaNumerica
     *
     * @param arreglo contiene el renglon a agregar
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario.
     */
    @Override
    public boolean agregarRenglon(ListaEstatica arreglo) {
        if (arreglo instanceof ListaEstaticaNumerica) {
            return super.agregarRenglon(arreglo);
        } else {
            return false;
        }
    }

    /**
     * Agrega una columna a la matriz
     * Comprueba que arreglo sea instancia de ListaEstaticaNumerica
     *
     * @param arreglo contiene la columna a agregar
     * @return retorna <b>true</b> si fue posible, <b>false</b> de lo contrario
     */
    @Override
    public boolean agregarColumna(ListaEstatica arreglo) {
        if (arreglo instanceof ListaEstaticaNumerica) {
            return super.agregarColumna(arreglo);
        } else {
            return false;
        }

    }

    /**
     * Convierte la matriz a Matriz3
     * Comprueba que matrices contenga numeros.
     *
     * @param matrices contiene la profunidad
     * @return retorna una Matriz3
     */
    @Override
    public Matriz3 aMatriz3(ListaEstatica matrices) {
        int cantidad = matrices.numeroElementos();
        int contador = 0;
        int numeros = 0;
        while (contador <= cantidad) {
            if (matrices.obtener(contador) instanceof Matriz2Numerica) {
                numeros++;
            }
            contador++;
        }
        if (numeros == cantidad) {
            return super.aMatriz3(matrices);
        } else {
            return null;
        }
    }

    /**
     * Agrega columnas de otra matriz
     * Comprueba que matriz2 sea instancia de Matriz2Numerica
     *
     * @param matriz2 es la matriz de donde se sacaran las columnas
     * @return retorna <b>true </b> en caso posible, <b>false</b> de lo contrario
     */
    @Override
    public boolean agregarMatrizXColumna(Matriz2 matriz2) {
        if (matriz2 instanceof Matriz2Numerica) {
            return super.agregarMatrizXColumna(matriz2);
        } else {
            return false;
        }
    }

    /**
     * Agrega renglones de otra matriz
     * Comprueba que matriz2 sea instancia de Matriz2Numerica
     *
     * @param matriz2 es la matriz de donde se sacaran los renglones
     * @return retorna <b>true </b> en caso posible, <b>false</b> de lo contrario
     */
    @Override
    public boolean agregarMatrizXRenglon(Matriz2 matriz2) {
        if (matriz2 instanceof Matriz2Numerica) {
            return super.agregarMatrizXRenglon(matriz2);
        } else {
            return false;
        }

    }


    /**
     * Obtiene los renglones de una matriz
     * Comprueba que matriz2 sea instancia de Matriz2Numerica
     *
     * @param matriz2 es la matriz a considerar
     * @return retorna una listaEstatica con los renglones
     */
    public ListaEstatica obtenerMatrizRenglones(Matriz2 matriz2) {
        if (matriz2 instanceof Matriz2Numerica) {
            return super.obtenerMatrizRenglones(matriz2);
        } else {
            return null;
        }
    }

    /**
     * Obtiene las columnas de una matriz
     * Comprueba que matriz2 sea instancia de Matriz2Numerica
     *
     * @param matriz2 la matriz a considerar
     * @return retorna una ListaEstatica con las columnas de la matriz
     */
    public ListaEstatica obtenerMatrizColumnas(Matriz2 matriz2) {
        if (matriz2 instanceof Matriz2Numerica) {
            return super.obtenerMatrizColumnas(matriz2);
        } else {
            return null;
        }

    }

    /**
     * Multiplica cada elemento de la matriz por el escalar
     *
     * @param escalar es un numero entero
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> de lo contrario.
     */
    public boolean porEscalar(Number escalar) {
        if (!vacia()) {
            Double valor = new Double(escalar.toString());
            int contador = 0;
            while (contador < renglones) {
                int contador2 = 0;
                while (contador2 < columnas) {
                    Double temporal = new Double(datos[contador][contador2].toString());
                    datos[contador][contador2] = temporal * valor;
                    contador2++;
                }
                contador++;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Multiplica cada elemento por los numeros de la ListaEstaticaNumerica
     *
     * @param escalares la lista que contiene los numeros
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean porEscalares(ListaEstaticaNumerica escalares) {
        if (escalares instanceof ListaEstaticaNumerica) {
            if (!escalares.vacia()) {
                if ((columnas * renglones) == (escalares.numeroElementos() + 1)) {
                    int contador = 0;
                    int contador3 = 0;
                    while (contador < renglones) {
                        int contador2 = 0;
                        while (contador2 < columnas) {
                            Double matriz = new Double(datos[contador][contador2].toString());
                            Double escalar = new Double(escalares.obtener(contador3).toString());
                            datos[contador][contador2] = matriz * escalar;
                            contador2++;
                            contador3++;
                        }
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
     * Suma cada elemento de la matriz por el escalar
     *
     * @param escalar es un numero entero
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean sumarEscalar(Number escalar) {
        if (!vacia()) {
            Double valor = new Double(escalar.toString());
            int contador = 0;
            while (contador < renglones) {
                int contador2 = 0;
                while (contador2 < columnas) {
                    Double temporal = new Double(datos[contador][contador2].toString());
                    datos[contador][contador2] = temporal + valor;
                    contador2++;
                }
                contador++;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Suma cada escalar de la lista a un elemento diferente de la matriz
     *
     * @param escalares contiene los numeros escalares
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean sumarEscalares(ListaEstaticaNumerica escalares) {
        if (escalares instanceof ListaEstaticaNumerica) {
            if (!escalares.vacia()) {
                if ((columnas * renglones) == (escalares.numeroElementos() + 1)) {
                    int contador = 0;
                    int contador3 = 0;
                    while (contador < renglones) {
                        int contador2 = 0;
                        while (contador2 < columnas) {
                            Double matriz = new Double(datos[contador][contador2].toString());
                            Double escalar = new Double(escalares.obtener(contador3).toString());
                            datos[contador][contador2] = matriz + escalar;
                            contador2++;
                            contador3++;
                        }
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
     * Suma dos matrices
     *
     * @param matriz2 contiene la segunda matriz
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean sumar(Matriz2Numerica matriz2) {
        if (matriz2 instanceof Matriz2Numerica) {
            if ((columnas == matriz2.obtenerColumnas()) && (renglones == matriz2.obtenerRenglones())) {
                int contador = 0;
                while (contador < renglones) {
                    int contador2 = 0;
                    while (contador2 < columnas) {
                        Double uno = new Double(datos[contador][contador2].toString());
                        Double dos = new Double(matriz2.obtener(contador, contador2).toString());
                        datos[contador][contador2] = uno + dos;
                        contador2++;
                    }
                    contador++;
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Eleva a una potencia cada uno de los elementos de la matriz
     *
     * @param escalar es la potencia a la que vamos a elevar
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean aplicarPotencia(Number escalar) {
        if (!vacia()) {
            Double valor = new Double(escalar.toString());
            int contador = 0;
            while (contador < renglones) {
                int contador2 = 0;
                while (contador2 < columnas) {
                    Double temporal = new Double(datos[contador][contador2].toString());
                    datos[contador][contador2] = Math.pow(temporal, valor);
                    contador2++;
                }
                contador++;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Aplica el logaritmo a cada elemento de la matriz
     *
     * @param tipo Representa el tipo de Logaritmo a aplicar
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean aplicarLogaritmo(TipoLogaritmo tipo) {
        if (!contieneCero()) {
            double base = 0;
            if (tipo == TipoLogaritmo.BASE10) {
                base = 10;
            }
            if (tipo == TipoLogaritmo.BASE2) {
                base = 2;
            }
            int contador = 0;
            while (contador < renglones) {
                int contador2 = 0;
                while (contador2 < columnas) {
                    Double temp = new Double(datos[contador][contador2].toString());
                    datos[contador][contador2] = Math.log(temp) / Math.log(base);
                    contador2++;
                }
                contador++;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Revisa si la matriz contiene ceros
     *
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean contieneCero() {
        if (!vacia()) {
            int contador = 0;
            double cero = 0;
            int contadorcero = 0;
            while (contador < renglones) {
                int contador2 = 0;
                while (contador2 < columnas) {
                    Double temp = new Double(datos[contador][contador2].toString());
                    if (cero == temp) {
                        contadorcero++;
                    }
                    contador2++;
                }
                contador++;
            }
            return contadorcero != 0;
        } else {
            return false;
        }
    }

    /**
     * Crea una matriz diagonal a partir del numero dado
     *
     * @param contenido contiene el numero a utilizar
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean matrizDiagonal(Number contenido) {
        if (contenido instanceof Number) {
            Double valor = new Double(contenido.toString());
            if (columnas == renglones) {
                rellenar(0);
                int contador = 0;
                while (contador < renglones) {
                    datos[contador][contador] = valor;
                    contador++;
                }
                return true;

            } else {
                return false;
            }


        } else {
            return false;
        }
    }

    /**
     * Imprime por renglones la matriz
     */
    @Override
    public void imprimirXRenglones() {
        for (int fila = 0; fila < renglones; fila++) {   //reng x reng
            for (int col = 0; col < columnas; col++) { //extrae col x col
                SalidaPorDefecto.consola(datos[fila][col] + "     ");
            }
            //Cuando termina cada columna, hacer salto de línea
            SalidaPorDefecto.consola("\n");
        }
    }

    /**
     * Revisa si la matriz es Diagonal superior
     *
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean esDiagonalSuperior() {
        if (contieneCero()) {
            if (columnas == renglones) {
                int contador = 1;
                int reng = renglones;
                double cero = 0;
                while (contador < renglones) {
                    int contador2 = 0;
                    while (contador2 <= renglones - reng) {
                        Double temp = new Double(datos[contador][contador2].toString());
                        if (temp != cero) {
                            return false;
                        }
                        contador2++;
                    }
                    contador++;
                    reng--;
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Revisa si la matriz es diagonal inferior
     *
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean esDiagonalInferior() {
        if (contieneCero()) {
            if (columnas == renglones) {
                int contador = 0;
                double cero = 0;
                int contador2 = 1;
                while (contador < renglones) {
                    while (contador2 < renglones) {
                        Double temp = new Double(datos[contador][contador2].toString());
                        if (temp != cero) {
                            return false;
                        }
                        contador2++;
                    }
                    contador++;
                    contador2 = contador + 1;
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Eleva la matriz entera a un exponente
     *
     * @param exponente contiene el valor al cual se va elevar la matriz
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean potencia(int exponente) {
        if (exponente > 0) {
            if (!vacia()) {
                if (columnas == renglones) {
                    int times = 0;
                    Object[][] datosOriginales = datos;
                    while (times < exponente - 1) {
                        Object[][] datosnuevos = new Object[renglones][columnas];
                        int contadorRenglones = 0;
                        while (contadorRenglones < renglones) {
                            int contadorRenglones2 = 0;
                            int contadorColumnas2 = 0;
                            int contadorColumnas = 0;
                            int contadorveces = 0;
                            double temporal = 0;
                            while (contadorveces < columnas) {
                                Double uno = new Double(datos[contadorRenglones][contadorColumnas].toString());
                                Double dos = new Double(datosOriginales[contadorRenglones2][contadorColumnas2].toString());
                                temporal = temporal + (uno * dos);
                                contadorRenglones2++;
                                contadorColumnas++;
                                if (contadorColumnas == columnas) {
                                    datosnuevos[contadorRenglones][contadorColumnas2] = temporal;
                                    temporal = 0;
                                    contadorColumnas = 0;
                                    contadorRenglones2 = 0;
                                    contadorColumnas2++;
                                    contadorveces++;
                                }
                            }
                            contadorRenglones++;
                        }
                        datos = datosnuevos;
                        times++;
                    }
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        } else {
            if (exponente == 0) {
                return matrizDiagonal(1);
            } else {
                return false;
            }
        }
    }

    /**
     * Dobla la matriz a la mitad sumando sus columnas
     *
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean doblarColumnas() {
        if (!vacia()) {
            if (columnas % 2 == 0) {
                Matriz2Numerica nuev = new Matriz2Numerica(renglones, 2);
                ListaEstaticaNumerica nuevo = new ListaEstaticaNumerica(renglones);
                int contador = 0;
                while (contador < renglones) {
                    int contador2 = 0;
                    double suma = 0;
                    while (contador2 < columnas / 2) {
                        Double temp = new Double(datos[contador][contador2].toString());
                        suma = suma + temp;
                        contador2++;
                    }
                    nuevo.agregar(suma);
                    contador++;
                }
                nuev.agregarColumna(nuevo);
                nuevo.vaciar();
                contador = 0;
                while (contador < renglones) {
                    int contador2 = columnas / 2;
                    double suma = 0;
                    while (contador2 < columnas) {
                        Double temp = new Double(datos[contador][contador2].toString());
                        suma = suma + temp;
                        contador2++;
                    }
                    nuevo.agregar(suma);
                    contador++;
                }
                nuev.agregarColumna(nuevo);
                redefinir(nuev);
                return true;
            } else {
                Matriz2Numerica nuev = new Matriz2Numerica(renglones, 3);
                ListaEstaticaNumerica nuevo = new ListaEstaticaNumerica(renglones);
                int contador = 0;
                int col = columnas;
                col--;
                while (contador < renglones) {
                    int contador2 = 0;
                    double suma = 0;
                    while (contador2 < col / 2) {
                        Double temp = new Double(datos[contador][contador2].toString());
                        suma = suma + temp;
                        contador2++;
                    }
                    nuevo.agregar(suma);
                    contador++;
                }
                nuev.agregarColumna(nuevo);
                nuevo.vaciar();
                contador = 0;
                int cont = (col / 2);
                while (contador < renglones) {
                    nuevo.agregar(datos[contador][cont]);
                    contador++;
                }
                nuev.agregarColumna(nuevo);
                nuevo.vaciar();
                contador = 0;
                while (contador < renglones) {
                    int contador2 = (col / 2) + 1;
                    double suma = 0;
                    while (contador2 < columnas) {
                        Double temp = new Double(datos[contador][contador2].toString());
                        suma = suma + temp;
                        contador2++;
                    }
                    nuevo.agregar(suma);
                    contador++;
                }
                nuev.agregarColumna(nuevo);
                redefinir(nuev);
                return true;
            }

        } else {
            return false;
        }
    }

    /**
     * Dobla la matriz a la mitad, sumando sus renglones
     *
     * @return retorna <b>true</b> en caso exitoso, <b>false</b> en caso contrario.
     */
    public boolean doblarRenglones() {
        if (!vacia()) {
            if (renglones % 2 == 0) {
                Matriz2Numerica nuev = new Matriz2Numerica(2, columnas);
                ListaEstaticaNumerica nuevo = new ListaEstaticaNumerica(columnas);
                int contador = 0;
                while (contador < columnas) {
                    int contador2 = 0;
                    double suma = 0;
                    while (contador2 < renglones / 2) {
                        Double temp = new Double(datos[contador2][contador].toString());
                        suma = suma + temp;
                        contador2++;
                    }
                    nuevo.agregar(suma);
                    contador++;
                }
                nuev.agregarRenglon(nuevo);
                nuevo.vaciar();
                contador = 0;
                while (contador < columnas) {
                    int contador2 = renglones / 2;
                    double suma = 0;
                    while (contador2 < renglones) {
                        Double temp = new Double(datos[contador2][contador].toString());
                        suma = suma + temp;
                        contador2++;
                    }
                    nuevo.agregar(suma);
                    contador++;
                }
                nuev.agregarRenglon(nuevo);
                redefinir(nuev);
                return true;


            } else {
                Matriz2Numerica nuev = new Matriz2Numerica(3, columnas);
                ListaEstaticaNumerica nuevo = new ListaEstaticaNumerica(columnas);
                int contador = 0;
                int reng = renglones - 1;
                while (contador < columnas) {
                    int contador2 = 0;
                    double suma = 0;
                    while (contador2 < reng / 2) {
                        Double temp = new Double(datos[contador2][contador].toString());
                        suma = suma + temp;
                        contador2++;
                    }
                    nuevo.agregar(suma);
                    contador++;
                }
                nuev.agregarRenglon(nuevo);
                nuevo.vaciar();
                contador = 0;
                int cont = reng / 2;
                while (contador < columnas) {
                    nuevo.agregar(datos[cont][contador]);
                    contador++;
                }
                nuev.agregarRenglon(nuevo);
                nuevo.vaciar();
                contador = 0;
                while (contador < columnas) {
                    int contador2 = reng / 2 + 1;
                    double suma = 0;
                    while (contador2 < renglones) {
                        Double temp = new Double(datos[contador2][contador].toString());
                        suma = suma + temp;
                        contador2++;
                    }
                    nuevo.agregar(suma);
                    contador++;
                }
                nuev.agregarRenglon(nuevo);
                redefinir(nuev);
                return true;
            }

        } else {
            return false;
        }
    }

    public Object[][] leerArreglo() {
        return datos;
    }

    public Matriz2Numerica clonarNumerica() {
        Matriz2Numerica copia = new Matriz2Numerica(renglones, columnas);
        int contador = 0;
        while (contador < renglones) {
            int contador2 = 0;
            ListaEstaticaNumerica temporal = new ListaEstaticaNumerica(columnas);
            while (contador2 < columnas) {
                temporal.agregar(datos[contador][contador2]);
                contador2++;
            }
            copia.agregarRenglon(temporal);
            contador++;
        }
        return copia;
    }

    public Matriz2Numerica Multiplicacion(Matriz2Numerica matriz2)
            throws Exception
    {

        // Required condition for matrix multiplication
        if (columnas != matriz2.obtenerRenglones()) {
            throw new Exception("Invalid matrix given.");
        }

        // create a result matrix
        int resultMatrix[][] = new int[renglones][matriz2.obtenerColumnas()];
        Matriz2Numerica regresar=new Matriz2Numerica(renglones, matriz2.obtenerColumnas());

        // Core logic for 2 matrices multiplication
        for (int i = 0; i < resultMatrix.length; i++)
        {
            for (int j = 0;
                 j < resultMatrix[i].length;
                 j++)
            {
                for (int k = 0; k < columnas; k++)
                {
                    Double valor=new Double(datos[i][k].toString());
                    Double valor2=new Double(matriz2.obtener(k,j).toString());
                    Double valor3=new Double(regresar.obtener(i,j).toString());
                    if(valor3!=null){
                        regresar.cambiar(i, j, (valor3+(valor * valor2)));

                    }else {
                        regresar.cambiar(i, j, (valor * valor2));
                    }
                }
            }
        }
        return regresar;
    }


}
