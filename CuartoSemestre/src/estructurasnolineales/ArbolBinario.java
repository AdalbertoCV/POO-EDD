package estructurasnolineales;

import entradasalida.EntradaPorDefecto;
import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaDinamica;
import estructuraslineales.ListaDinamicaClave;
import estructuraslineales.auxiliares.Nodo;
import estructuraslineales.auxiliares.NodoClave;
import estructuraslineales.auxiliares.NodoDoble;
import herramientas.matematicas.ExpresionesAritmeticas;

public class ArbolBinario {
    protected NodoDoble raiz;
    protected ListaDinamicaClave operandos;
    protected ListaDinamicaClave operandosSustituidos;
    protected ListaDinamica operadores;
    protected int numClave;

    public ArbolBinario(){
        raiz=null;
        operadores = new ListaDinamica();
        operandos = new ListaDinamicaClave();
        operandosSustituidos = new ListaDinamicaClave();
        numClave = 1;
    }

    public boolean generarArbol(){
        SalidaPorDefecto.consola("Indica la raíz: ");
        String contenidoNodo= EntradaPorDefecto.consolaCadenas();
        NodoDoble nuevoNodo=new NodoDoble(contenidoNodo);
        if(nuevoNodo!=null){ //hay espacio de memoria
            raiz=nuevoNodo;
            generarArbol(raiz);
            return true;
        }else{ //no hay espacio de memoria
            return false;
        }
    }

    private void generarArbol(NodoDoble subRaiz){ //Por cada Subraiz
        //Agregar hijo izquierdo
        SalidaPorDefecto.consola("¿El nodo "+ subRaiz.getContenido()+ " tiene hijo izquierdo ? ");
        String respuestaPreguntaHijoIzq=EntradaPorDefecto.consolaCadenas();
        if(respuestaPreguntaHijoIzq.equalsIgnoreCase("s")){ //si quiuere agregar un hijo izquierdo
            SalidaPorDefecto.consola("Indica el dato del hijo izquierdo: ");
            String contenidoNodoIzquierdo=EntradaPorDefecto.consolaCadenas();
            NodoDoble nuevoNodoIzquierdo=new NodoDoble(contenidoNodoIzquierdo);
            if(nuevoNodoIzquierdo!=null){ //si hay espacio
                subRaiz.setNodoIzq(nuevoNodoIzquierdo); //ligamos la subraiz a este hijo nuevo
                //llamada recursiva para ver si a este nodo izquierdo le corresponden tener hijo también
                generarArbol(subRaiz.getNodoIzq());
            }
        }//si no quiere tener hijo izquierdo, no hacemos nada, es decir, caso base

        //Agregar hijo derecho
        SalidaPorDefecto.consola("¿El nodo "+ subRaiz.getContenido()+ " tiene hijo derecho ? ");
        String respuestaPreguntaHijoDer=EntradaPorDefecto.consolaCadenas();
        if(respuestaPreguntaHijoDer.equalsIgnoreCase("s")){ //si quiuere agregar un hijo derecho
            SalidaPorDefecto.consola("Indica el dato del hijo derecho: ");
            String contenidoNodoDerecho=EntradaPorDefecto.consolaCadenas();
            NodoDoble nuevoNodoDerecho=new NodoDoble(contenidoNodoDerecho);
            if(nuevoNodoDerecho!=null){ //si hay espacio
                subRaiz.setNodoDer(nuevoNodoDerecho); //ligamos la subraiz a este hijo nuevo
                //llamada recursiva para ver si a este nodo derecho le corresponden tener hijo también
                generarArbol(subRaiz.getNodoDer());
            }
        }//si no quiere tener hijo derecho, no hacemos nada, es decir, caso base
    }

    /**
     * Metodo publico para extraer en listas los operandos y los operadores
     */
    public void extraerOperandosYOperadores(){
        extraerOperandosYOperadores(raiz);
    }

    /**
     * Metodo privado y recursivo para recorrer el arbol y extraer los operandos y operadores
     * @param subraiz es la raiz del arbol que se ira moviendo en cada iteracion
     */
    private void extraerOperandosYOperadores(NodoDoble subraiz){
        if (subraiz!=null){
            extraerOperandosYOperadores(subraiz.getNodoIzq());
            String contenido = (String) subraiz.getContenido();
            if (contenido.length()>1){
                operandos.agregar(numClave,contenido);
                numClave++;
            }else{
                char token = contenido.charAt(0);
                if (ExpresionesAritmeticas.esOperando(token)){
                    operandos.agregar(numClave,contenido);
                    numClave++;
                }else{
                    operadores.agregar(token);
                }
            }
            extraerOperandosYOperadores(subraiz.getNodoDer());
        }
    }

    /**
     * metodo para imprimir los operadores obtenidos
     */
    public void imprimirOperadores(){
        operadores.imprimir();
    }

    /**
     * metodo para imprimir los operandos obtenidos
     */
    public void imprimirOperandos(){
        operandos.imprimir();
    }

    /**
     * Preguntramos uno a uno si se desea cambiar el valor de los operandos
     */
    public void cambiarOperandos(){
        NodoClave iterador = operandos.verPrimerNodo();
        while (iterador!=null){
            Object contenido = iterador.getContenido();
            SalidaPorDefecto.consola("Desea cambiar el valor del operando "+ contenido+"? \n");
            String respuesta = EntradaPorDefecto.consolaCadenas();
            if (respuesta.equals("s")){
                SalidaPorDefecto.consola("Indique el nuevo valor de "+ contenido+": \n");
                String contenidoNuevo = EntradaPorDefecto.consolaCadenas();
                operandosSustituidos.agregar(iterador.getClave(),contenidoNuevo);
            }
            iterador =  iterador.getNodoDer();
        }
        ListaDinamicaClave temporal = operandos;
        operandos = operandosSustituidos;
        operandosSustituidos = temporal;
    }

    /**
     * Es el metodo publico para generar un nuevo arbol a partir de los operados sustituidos
     */
    public void generarNuevoArbol(){
        NodoClave iter = operandos.verPrimerNodo();
        NodoClave iterViejos = operandosSustituidos.verPrimerNodo();
        while(iter!= null){
            generarNuevoArbol(raiz,iter.getContenido(), iterViejos.getContenido());
            iter = iter.getNodoDer();
            iterViejos = iterViejos.getNodoDer();
        }
    }

    /**
     * metodo privado y recursivo para generar un arbol nuevo a partir de la sustitucion de los operandos
     * @param subraiz es el nodo actual
     * @param operandoNuevo es el operando a colocar
     * @param operandoViejo es el operando a sustituir
     */
    private void generarNuevoArbol(NodoDoble subraiz, Object operandoNuevo, Object operandoViejo){
        if (subraiz!=null){
            generarNuevoArbol(subraiz.getNodoIzq(), operandoNuevo, operandoViejo);
            if (String.valueOf(operandoViejo).equals(subraiz.getContenido())){
                subraiz.setContenido(operandoNuevo);
            }
            generarNuevoArbol(subraiz.getNodoDer(), operandoNuevo, operandoViejo);
        }
    }

    /**
     * Es el metodo para calcular la altura del arbol
     * @return regresa la altura
     */
    public int altura(){
        int altura = 0;
        int alturaIzq = alturaIzquierda(raiz, altura);
        altura =0;
        int alturaDer = alturaDerecha(raiz,altura);
        if (alturaDer> alturaIzq){
            return alturaDer;
        }else{
            return alturaIzq;
        }
    }

    /**
     * Calcula recursivamente la altura por la parte izquierda del arbol
     * @param subraiz es el nodo actual
     * @param altura es la variable de altura
     * @return regresa la altura
     */
    private int alturaIzquierda(NodoDoble subraiz, int altura){
        if (subraiz!=null){
            return alturaIzquierda(subraiz.getNodoIzq(), altura+1);
        }else{
            return altura;
        }
    }

    /**
     * Calcula recursivamente la altura por la parte derecha del arbol
     * @param subraiz es el nodo actual
     * @param altura es la variable de altura
     * @return regresa la altura
     */
    private int alturaDerecha(NodoDoble subraiz, int altura){
        if (subraiz!=null){
            return alturaDerecha(subraiz.getNodoDer(), altura+1);
        }else{
            return altura;
        }
    }

    /**
     * Metodo para saber en que nivel se encuentra un nodo
     * @param nodo es el nodo a buscar
     * @return regresa el nivel del nodo
     */
    public int alturaNodo(String nodo){
        int altura=0;
        int alturaIzq= alturaNodoIzq(raiz,altura,nodo);
        altura =0;
        int alturaDer = alturaNodoDer(raiz,altura,nodo);
        if (alturaDer < alturaIzq){
            return alturaDer;
        }else{
            return alturaIzq;
        }
    }

    /**
     * Es el metodo para buscar el nodo por la parte izquierda
     * @param subraiz es el nodo actual
     * @param altura es la variable que determina en que nivel es encontrado el nodo
     * @param nodo es el nodo a buscar
     * @return regresa el nivel del nodo o 0 si no esta
     */
    private int alturaNodoIzq(NodoDoble subraiz, int altura, String nodo){
        if (subraiz!= null && !String.valueOf(subraiz.getContenido()).equals(nodo)){
            return alturaNodoIzq(subraiz.getNodoIzq(), altura+1,nodo);
        }else{
            if (subraiz== raiz){
                return altura+1;
            }else{
                if (subraiz!=null){
                    if (String.valueOf(subraiz.getContenido()).equals(nodo)){
                        return altura;
                    }else{
                        return altura();
                    }
                }else{
                    return altura();
                }
            }
        }
    }

    /**
     * Es el metodo para buscar el nodo por la parte derecha
     * @param subraiz es el nodo actual
     * @param altura es la variable que determina en que nivel es encontrado el nodo
     * @param nodo es el nodo a buscar
     * @return regresa el nivel del nodo o 0 si no esta
     */
    private int alturaNodoDer(NodoDoble subraiz, int altura, String nodo){
        if (subraiz!= null && !String.valueOf(subraiz.getContenido()).equals(nodo)){
            altura++;
            return alturaNodoDer(subraiz.getNodoDer(), altura+1,nodo);
        }else{
            if (subraiz== raiz){
                return altura+1;
            }else{
                if (subraiz!=null){
                    if (String.valueOf(subraiz.getContenido()).equals(nodo)){
                        return altura;
                    }else{
                        return altura();
                    }
                }else{
                    return altura();
                }
            }
        }
    }

    /**
     * Es el metodo para imprimir cuantos nodos hay en cada nivel del arbol
     */
    public void cantidadPorNivel(){
        int nivelActual=altura();
        while(nivelActual!=0){
            SalidaPorDefecto.consola("Hay "+contarNodos(nivelActual,raiz)+" nodos en el nivel "+nivelActual+"\n");
            nivelActual--;
        }
    }

    /**
     * es el metodo privado y recursivo para contar cuantos nodos tiene el actual nivel
     * @param nivel es el nivel actual a evaluar
     * @param subRaiz es el nodo actual
     * @return regresa la cantidad de nodos del nivel actual
     */
    public int contarNodos(int nivel,NodoDoble subRaiz){
        if (subRaiz== null) {
            return 0;
        }
        if (nivel == 1) {
            return 1;
        } else {
            return contarNodos(nivel-1,subRaiz.getNodoDer())+ contarNodos(nivel-1,subRaiz.getNodoIzq());
        }
    }

    /**
     * Es el metodo para imprimir el tipo del nodo enviado como parametro
     * @param nodo es el nodo a evaluar
     */
    public void tipoNodo(String nodo){
       tipoNodo(raiz,nodo);
    }

    /**
     * Es el metodo privado recursivo para saber el tipo de un nodo
     * @param subRaiz es el nodo actual
     * @param nodo es el nodo a evaluar
     */
    public void tipoNodo(NodoDoble subRaiz, String nodo){
        if (subRaiz!=null){
            tipoNodo(subRaiz.getNodoIzq(), nodo);
            if (subRaiz == raiz){
                if (String.valueOf(subRaiz.getContenido()).equals(nodo)){
                    SalidaPorDefecto.consola("El nodo es de tipo raiz. No tiene padre \n");
                }
            }else{
                if (String.valueOf(subRaiz.getContenido()).equals(nodo)){
                    if (subRaiz.getNodoIzq()!= null || subRaiz.getNodoDer()!=null){
                        SalidaPorDefecto.consola("El nodo es de tipo interior. \n");
                    }else{
                        SalidaPorDefecto.consola("El nodo es de tipo hoja. \n");
                    }
                }
            }
            tipoNodo(subRaiz.getNodoDer(), nodo);
        }
    }

    /**
     * Metodo para determinar si un nodo dado tiene hermano.
     * @param nodo es el nodo qa evaluar
     */
    public void tieneHermano(String nodo){
        int altura =0;
        int alturaNodoIzq = alturaNodoDer(raiz, altura, nodo);
        altura =0;
        int alturaNodoDer = alturaNodoIzq(raiz,altura,nodo);
        if (alturaNodoDer>1){
            altura =0;
            if (alturaIzquierda(raiz,altura)<= alturaNodoDer){
                SalidaPorDefecto.consola("Si tiene hermano.\n");
            }else{
                SalidaPorDefecto.consola("No tiene hermano.\n");
            }
        }else{
            if(alturaNodoIzq>1){
                altura =0;
                if (alturaDerecha(raiz,altura)<= alturaNodoIzq){
                    SalidaPorDefecto.consola("Si tiene hermano.\n");
                }else{
                    SalidaPorDefecto.consola("No tiene hermano.\n");
                }
            }
        }
    }

    public void inorden(){
        inorden(raiz);
    }

    private void inorden(NodoDoble subRaiz){
        //IRD
        if(subRaiz!=null) {
            inorden(subRaiz.getNodoIzq());
            SalidaPorDefecto.consola(subRaiz.getContenido() + " ");
            inorden(subRaiz.getNodoDer());
        } //en el else el caso base, donde n ose hace nada
    }

    public void preorden(){
        preorden(raiz);
    }

    private void preorden(NodoDoble subRaiz){
        //RID
        if(subRaiz!=null) {
            SalidaPorDefecto.consola(subRaiz.getContenido() + " ");
            preorden(subRaiz.getNodoIzq());
            preorden(subRaiz.getNodoDer());
        } //en el else el caso base, donde n ose hace nada
    }

    public void postorden(){
        postorden(raiz);
    }

    private void postorden(NodoDoble subRaiz){
        //IDR
        if(subRaiz!=null) {
            postorden(subRaiz.getNodoIzq());
            postorden(subRaiz.getNodoDer());
            SalidaPorDefecto.consola(subRaiz.getContenido() + " ");
        } //en el else el caso base, donde n ose hace nada
    }


}
