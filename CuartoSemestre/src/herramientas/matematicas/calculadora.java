package herramientas.matematicas;
import estructuraslineales.PilaEstatica;

public class calculadora extends ExpresionesAritmeticas{

    protected PilaEstatica variablesPostfija;
    protected PilaEstatica variablesPrefija;

    public calculadora(){
        variablesPostfija = new PilaEstatica(20);
        variablesPrefija = new PilaEstatica(20);
    }
    /**
     * Transforma una cadena infija a postfija
     * @param infija es la cadena a transformar
     * @return regresa la cadena convertida a postfija
     */
    @Override
    public String infijaAPostfija(String infija){
        vaciarPilaPostfija();
        PilaEstatica pila = new PilaEstatica(infija.length());
        String postfija ="";
        int indiceToken=0;
        while(indiceToken<infija.length()){ //Tokenizando la expresion
            char token = infija.charAt(indiceToken);
            if (esOperando(token)){
                if (Character.isDigit(token)){
                    postfija = postfija + token;
                    indiceToken++;
                }else{
                   int indiceVariable =indiceToken;
                   String variableFinal = "";
                   int numElemsVariable = 0;
                   int corte =0;
                   while (indiceVariable<infija.length() && corte<1){
                       char siguiente = infija.charAt(indiceVariable);
                       indiceVariable++;
                       if (String.valueOf(siguiente).equals("$") ||String.valueOf(siguiente).equals("_") || Character.isDigit(siguiente) || String.valueOf(siguiente).equals(" ") || Character.isLetter(siguiente)){
                           numElemsVariable++;
                           if (String.valueOf(siguiente).equals(" ") || indiceToken == infija.length()-1 || indiceVariable>= infija.length()){
                               if (indiceToken==infija.length()-1 ){
                                   variableFinal = variableFinal+token;
                               }else{
                                   if (indiceVariable>= infija.length()){
                                       variableFinal = variableFinal+siguiente;
                                   }
                               }
                               if (variableFinal!="" && variableFinal!=" "){
                                   variablesPostfija.poner(variableFinal);
                               }
                               corte++;
                               postfija = postfija + variableFinal; // Concatenamos en la EPOS
                           }else{
                               variableFinal = variableFinal + siguiente;
                           }
                       }else{
                           return null; // Variable no valida
                       }
                   }
                   indiceToken = indiceToken + numElemsVariable;
                }
            }else{ //si no es operando, entonces es operador
                if (!pila.vacio()){ //si no esta vacia
                    while (((evaluarPrioridad(token) <= evaluarPrioridad(new String(""+pila.verUltimo()).charAt(0))) && evaluarPrioridad(token)!=0)){ // sacamos los de mayor o igual prioridad
                        postfija = postfija + pila.quitar(); //concatenamos en la epos
                    }
                    if (evaluarPrioridad(token) == 0){
                        if (String.valueOf(token).equals("(")){ //si es parentesis de apertura
                            pila.poner(token);
                        }else if (String.valueOf(token).equals(")")){
                            int contelems =0;
                            while (contelems < pila.numeroElementos()){
                                String ultimo =  ""+ pila.verUltimo();
                                if (!ultimo.equals("(") && !pila.vacio()){
                                    postfija = postfija + pila.quitar();
                                }
                                contelems++;
                            }
                            pila.quitar(); //quitamos parentesis de apertura
                        }
                    }else{
                        pila.poner(token); // agregamos token a la pila
                    }
                }else{
                    pila.poner(token); //si esta vacia simplemente agregamos el token a la pila
                }
                indiceToken++;
            }
        }
        //Ya se procesaron todos los tokens
        int numElemsPila = pila.numeroElementos();
        if (!pila.vacio()){
            for (int cont =0; cont< numElemsPila; cont++){
                postfija = postfija + pila.quitar();
            }
        }
        return postfija;
    }

    /**
     * Transforma la expresion infija a prefija
     * @param infija es la expresion a trnasformar
     * @return regresa la expresion transformada
     */
    @Override
    public String infijaPrefija(String infija) {
        vaciarPilaPrefija();
        PilaEstatica pila = new PilaEstatica(infija.length());
        String infijaInversa = invertirCadena(infija); // invertimos la cadena para evaluarla
        String prefija ="";
        int indiceToken=0;
        while(indiceToken<infijaInversa.length()){ //Tokenizando la expresion
            char token = infijaInversa.charAt(indiceToken);
            if (esOperando(token)){
                if (Character.isDigit(token)){
                    int indiceNumero = indiceToken+1;
                    int numeroFinal = token;
                    int numNumeros =0;
                    int corte = 0;
                    while (indiceNumero<infijaInversa.length() && corte<1){
                        char siguiente = infijaInversa.charAt(indiceNumero);
                        indiceNumero++;
                        if (Character.isDigit(siguiente)){ //Sigue siendo una constante
                            numeroFinal = numeroFinal + siguiente;
                            numNumeros++;
                        }else{
                            corte++; // variable corte sirve para cortar el ciclo while
                            if (esOperando(siguiente)){
                                return null; //Es una variable invalida
                            }
                        }
                    }
                    indiceToken = indiceToken + numNumeros; //Se aumenta el indice
                    prefija = prefija + numeroFinal; //Concatenamos a la epre
                }else{
                    int indiceVariable =indiceToken;
                    String variableFinal = "";
                    int numElemsVariable = 0;
                    int corte =0;
                    while (indiceVariable<infijaInversa.length() && corte<1){
                        char siguiente = infijaInversa.charAt(indiceVariable);
                        indiceVariable++;
                        if (String.valueOf(siguiente).equals("$") ||String.valueOf(siguiente).equals("_") || Character.isDigit(siguiente) || String.valueOf(siguiente).equals(" ") || Character.isLetter(siguiente)){
                            numElemsVariable++;
                            if (String.valueOf(siguiente).equals(" ") || indiceToken == infijaInversa.length()-1 || indiceVariable>= infijaInversa.length()){
                                if (indiceToken==infijaInversa.length()-1 ){
                                    variableFinal = variableFinal+token;
                                }else{
                                    if (indiceVariable>= infijaInversa.length()){
                                        variableFinal = variableFinal+siguiente;
                                    }
                                }
                                if (variableFinal!="" && variableFinal!=" "){
                                    variablesPrefija.poner(variableFinal);
                                }
                                corte++;
                                prefija = prefija + variableFinal; // Concatenamos en la epre
                            }else{
                                variableFinal = variableFinal + siguiente;
                            }
                        }else{
                            return null; // Variable no valida
                        }
                    }
                    indiceToken = indiceToken + numElemsVariable;
                }
            }else{ //si no es operando, entonces es operador
                if (!pila.vacio()){ //si no esta vacia
                    while (((evaluarPrioridad(token) < evaluarPrioridad(new String(""+pila.verUltimo()).charAt(0))) && evaluarPrioridad(token)!=0)){ // sacamos los de mayor prioridad
                        prefija = prefija + pila.quitar(); //concatenamos en la epre
                    }
                    if (evaluarPrioridad(token) == 0){
                        if (String.valueOf(token).equals(")")){ //si es parentesis de apertura
                            pila.poner(token);
                        }else if (String.valueOf(token).equals("(")){
                            int contelems =0;
                            while (contelems < pila.numeroElementos()){
                                String ultimo =  ""+ pila.verUltimo();
                                if (!ultimo.equals(")") && !pila.vacio()){
                                    prefija = prefija + pila.quitar();
                                }
                                contelems++;
                            }
                            pila.quitar(); //quitamos parentesis de apertura
                        }
                    }else{
                        pila.poner(token); // agregamos token a la pila
                    }
                }else{
                    pila.poner(token); //si esta vacia simplemente agregamos el token a la pila
                }
                indiceToken++;
            }
        }
        //Ya se procesaron todos los tokens
        int numElemsPila = pila.numeroElementos();
        if (!pila.vacio()){
            for (int cont =0; cont< numElemsPila; cont++){
                prefija = prefija + pila.quitar();
            }
        }
        return invertirCadena(prefija); // regresamos la expresion prefija invertida para mostrarla en el orden correcto
    }

    /**
     * Resuelve la expresion con los valores indicados como parametro
     * @param prefija es la expresion a resolver
     * @param variables son los valores para los cuales se va a resolver
     * @return regresa el resultado de la expresion
     */
    public Double resolverPrefija(String prefija, PilaEstatica variables){
        String prefijaConValores = sustituirVariablesPrefija(prefija,variables);
        return evaluarPrefija(prefijaConValores);
    }

    /**
     * Evalua la expresion prefija
     * @param prefija es la expresion a evaluar
     * @return regresa el resultado de la expresion
     */
    @Override
    public Double evaluarPrefija(String prefija){
        String prefijaInvertida = invertirCadena(prefija);
        return super.evaluarPrefija(prefijaInvertida);
    }
    /**
     *  Se sustituyen las valriables de la expresion postfija con los valores correspondientes
     * @param postfija es la expresion a sustituir sus variables
     * @param valores es la pila de valores a sustituir
     * @return regresa la expresion con los valores sustituidos
     */
    public String  sustituirVariablesPostfija(String postfija, PilaEstatica valores){
        int numElemsPilaVariables = valores.numeroElementos();
        String nuevaCadena = postfija;
        for (int pos =0; pos<numElemsPilaVariables; pos++){
            nuevaCadena = nuevaCadena.replaceAll(""+variablesPostfija.quitar(),""+valores.quitar() );
        }
        return nuevaCadena;
    }

    /**
     * Se sustituyen las variables de la expresion prefina con los valores correspondientes
     * @param prefija es la expresion a sustituir variables
     * @param valores son los valores a colocar
     * @return regresa la expresion con sus sutituciones
     */
    public String sustituirVariablesPrefija(String prefija, PilaEstatica valores){
        int numElemsPilaVariables = valores.numeroElementos();
        String nuevaCadena = prefija;
        for (int pos =0; pos<numElemsPilaVariables; pos++){
            nuevaCadena = nuevaCadena.replaceAll(""+variablesPrefija.quitar(),""+valores.quitar() );
        }
        return nuevaCadena;
    }

    /**
     * resuelve la expresion postfija
     * @param postfija es la expresion a resolver
     * @param valores son los valores para los cuales resolver la ecuacion (variables)
     * @return regresa el resultado de la expresion
     */
    public Double resolverPostfija(String postfija, PilaEstatica valores){
        String postfijaConValores = sustituirVariablesPostfija(postfija,valores);
        return evaluarPostfija(postfijaConValores);
    }

    /**
     * Invierte el sentido de la cadena para su evaluacion
     * @param cadena es la cadena a invertir
     * @return regresa la cadena invertida
     */
    public String invertirCadena(String cadena){
        char[] arregloCaracteres = cadena.toCharArray();
        int inicioCadena = 0;
        int finCadena = arregloCaracteres.length-1;
        char memoria;
        while(finCadena>inicioCadena){
            memoria = arregloCaracteres[inicioCadena];
            arregloCaracteres[inicioCadena] = arregloCaracteres[finCadena];
            arregloCaracteres[finCadena] = memoria;
            finCadena--;
            inicioCadena++;
        }
        String cadenaRetorno = new String(arregloCaracteres);
        return cadenaRetorno;
    }

    public void vaciarPilaPostfija(){
        int numElem = variablesPostfija.numeroElementos();
        for (int pos=0; pos<numElem; pos++){
            variablesPostfija.quitar();
        }
    }


    public void vaciarPilaPrefija(){
        int numElem = variablesPrefija.numeroElementos();
        for (int pos=0; pos<numElem; pos++){
            variablesPrefija.quitar();
        }
    }
}
