package herramientas.matematicas;

import estructuraslineales.PilaEstatica;

public class ExpresionesAritmeticas {

    public String infijaAPostfija(String infija) {
        return null;
    }

    public String infijaPrefija(String infija) {
        return "";
    }

    public static Double evaluarPostfija(String postfija) {
        PilaEstatica pila = new PilaEstatica(postfija.length());

        //1) Tokenizar la expresión postfija.
        //  a b c d - * e f ^ /  +
        //  0 1 2 3 4 5 6 7 8 9 10, índiceToken
        for (int indiceToken = 0; indiceToken < postfija.length(); indiceToken++) {
            //2) Analizar de uno por uno los tokens.
            char token = postfija.charAt(indiceToken);
            if (esOperando(token) == true) {
                //2.1) Si el token es Operando, se mete en la pila.
                if (pila.poner("" + token) == false) { //no hay espacio en la pila
                    return null;
                }
            } else { //es operador
                //2.2) Si el token es Operador, se sacan de la pila dos operandos
                //(el primer operando que se saque equivale a OP2).
                // Se aplica la operación del token y el resultado se mete en la
                //pila.
                String operando2 = (String) pila.quitar();
                String operando1 = (String) pila.quitar();

                if (operando1 == null || operando2 == null) { //no había elementos que sacar
                    return null;
                } else { //seguimos con el proceso
                    Double operacionParcial = aplicarOperacionA(token, Double.parseDouble(operando1),
                            Double.parseDouble(operando2));
                    if (operacionParcial == null) { //el operador es inválido
                        return null;
                    } else { //seguimos
                        if (pila.poner("" + operacionParcial) == false) { //no hab{ia espacio
                            return null;
                        }
                    }
                }
            }
        } //for
        //3) El resultado de la operación queda en la pila.
        String resultadoEvaluacion = (String) pila.quitar();

        if (resultadoEvaluacion == null) { //hubo error
            return null;
        } else {
            return Double.parseDouble(resultadoEvaluacion);
        }
    }

    public static Double aplicarOperacionA(char operador, double operando1, double operando2) {
        if (operador == '+') {
            return operando1 + operando2;
        } else if (operador == '-') {
            return operando1 - operando2;
        } else if (operador == '*') {
            return operando1 * operando2;
        } else if (operador == '/') {
            if (operando2 == 0) {
                return null;
            } else {
                return operando1 / operando2;
            }
        } else if (operador == '%') {
            return operando1 % operando2;
        } else if (operador == '^') {
            return Math.pow(operando1, operando2);
        } else { //no es un operador del catálogo
            return null;
        }
    }

    public static boolean esOperando(char token) {
        if (token == '+' || token == '-' || token == '*' || token == '/' || token == '^' || token == '(' || token == ')' || token == '%' || token == ' ') {
            return false; //no es operando
        } else { //como no es operador y otro s{imbolo, es operando
            return true;
        }
    }

    public Double evaluarPrefija(String prefija) {
        PilaEstatica pila = new PilaEstatica(prefija.length());
        for (int indiceToken = 0; indiceToken < prefija.length(); indiceToken++) {
            char token = prefija.charAt(indiceToken);
            if (esOperando(token) == true) {
                if (pila.poner("" + token) == false) {
                    return null;
                }
            } else {
                String operando1 = (String) pila.quitar();
                String operando2 = (String) pila.quitar();

                if (operando1 == null || operando2 == null) {
                    return null;
                } else { //seguimos con el proceso
                    Double operacionParcial = aplicarOperacionA(token, Double.parseDouble(operando1),
                            Double.parseDouble(operando2));
                    if (operacionParcial == null) {
                        return null;
                    } else {
                        if (pila.poner("" + operacionParcial) == false) {
                            return null;
                        }
                    }
                }
            }
        }
        String resultadoEvaluacion = (String) pila.quitar();

        if (resultadoEvaluacion == null) {
            return null;
        } else {
            return Double.parseDouble(resultadoEvaluacion);
        }
    }

    public static Integer evaluarPrioridad(char operador){
        if (String.valueOf(operador).equals("+") || String.valueOf(operador).equals("-")){
            return 1;
        }else if (String.valueOf(operador).equals("*") || String.valueOf(operador).equals("/")){
            return 2;
        }else if (String.valueOf(operador).equals("^")){
            return 3;
        }else{
            return 0;
        }
    }
}
