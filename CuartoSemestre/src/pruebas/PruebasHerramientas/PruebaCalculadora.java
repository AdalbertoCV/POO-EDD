package pruebas.PruebasHerramientas;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.PilaEstatica;
import herramientas.matematicas.calculadora;

public class PruebaCalculadora {
    public static void main(String args[]){
        calculadora calc = new calculadora();
        String infija = "variable + suma * ( c - d ) / exp ^ potencia";
        String infija2 = "variable + suma * ( c - d ) / exp ^ potencia";
        SalidaPorDefecto.consola("Expresion infija : " + infija + "\n");
        SalidaPorDefecto.consola("La expresion postfija es: \n");
        String resultadoPostfija = calc.infijaAPostfija(infija);
        SalidaPorDefecto.consola(resultadoPostfija+"\n");
        SalidaPorDefecto.consola("La expresion prefija es: \n");
        String resultadoPrefija = calc.infijaPrefija(infija2);
        SalidaPorDefecto.consola(resultadoPrefija+"\n");
        PilaEstatica variables = new PilaEstatica(6);
        int potencia = 2;
        int exp = 2;
        int d = 1;
        int c = 3;
        int suma = 2;
        int variable = 2;
        variables.poner(variable);
        variables.poner(suma);
        variables.poner(c);
        variables.poner(d);
        variables.poner(exp);
        variables.poner(potencia);
        SalidaPorDefecto.consola("Resolviendo para valores: \n");
        SalidaPorDefecto.consola("variable = "+ variable+"\n");
        SalidaPorDefecto.consola("suma = "+suma+"\n");
        SalidaPorDefecto.consola("c = "+c+"\n");
        SalidaPorDefecto.consola("d = "+d+"\n");
        SalidaPorDefecto.consola("exp = "+exp+"\n");
        SalidaPorDefecto.consola("Potencia = "+potencia +"\n");
        //calc.resolverPostfija(resultadoPostfija,variables);
       SalidaPorDefecto.consola("Resultado Postfija = "+ calc.resolverPostfija(resultadoPostfija,variables)+"\n");
       // calc.resolverPrefija(resultadoPrefija,variables2);
       // SalidaPorDefecto.consola("Resultado Prefija = "+ calc.resolverPrefija(resultadoPrefija,variables2)+"\n");

        SalidaPorDefecto.consola("----------------------------------------- \n");

        String infijaInvalida1 = "1suma + 2suma * (x-y)";
        String infijaInvalida2 = "1suma + 2suma * (x-y)";
        SalidaPorDefecto.consola("Prueba infija invalida: \n");
        SalidaPorDefecto.consola("Infija invalida : "+ infijaInvalida1 + "\n");
        String resultadoPOSTInvalido = calc.infijaAPostfija(infijaInvalida1);
        String resultadoPREFInvalido = calc.infijaPrefija(infijaInvalida2);
        SalidaPorDefecto.consola("Resultado conversion a postfija: " + resultadoPOSTInvalido + "\n");
        SalidaPorDefecto.consola("Resultado conversion a prefija: " + resultadoPREFInvalido + "\n");

    }
}
