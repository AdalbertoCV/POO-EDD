package poo2.prac03;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PruebaCalculadora {
    private Pattern patronExpr;
    private CalculadoraSimple calc;
    public static void main(String[] args) {
        PruebaCalculadora pc = new PruebaCalculadora();
        try {
            // Se crea el patron para determinar si una linea dada de entrada
            // tiene el formato esperado
            pc.patronExpr = Pattern.compile("^([+\\-]?\\d+(\\.\\d+)?)\\s*([+\\-/*^])\\s*([+\\-]?\\d+(\\.\\d+)?)$");
            pc.calc = new CalculadoraSimple();
            pc.procesaEntrada();
        }
        catch (PatternSyntaxException pex) {
            // Si entra aqui la expresion regular esta mal escrita
            System.err.println(pex.getMessage());
        }
    }

    /**
     * Este metodo lee lineas del teclado que representan expresiones de 2 operandos,
     * donde los operadores validos son + - * / y ^ (representando este ultimo la exponenciacion)
     * las expresiones deben tener dos operandos y un operador enmedio
     * los operandos se asumen son números double con un signo opcional.
     * Para el caso de la exponenciacion del segundo operando que representa al exponente se toma
     * solo la parte entera.
     * Una vez introducida una linea se valida si tiene el formato esperado, usando la clase Pattern
     * y Matcher, si cumple con el formato esperado,
     * se imprime el resultado de evaluar la expresion contenida en la linea, de otra
     * manera imprime el texto Invalida
     * NOTA: Para indicar que ya no se deseen introducir mas lineas no teclee nada y presione Enter
     */
    private void procesaEntrada() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("El programa espera expresiones de 2 operandos (+,-,*,/,^), linea vacia para terminar:");
            String linea=in.readLine().trim();
            while (!linea.isEmpty()) {
                Matcher m = patronExpr.matcher(linea);
                if (m.find()) {
                    /* Al método group de la clase Matcher se le puede pasar
                     un entero N como argumento lo cual indica que se desea obtener
                     lo que concuerda con el parentesis N de la E.R. usada
                     para crear al Pattern
                     */
                    // El primer parentesis agrupa el primer operando
                    String op1Str = m.group(1);
                    // El tercer parentesis agrupa el operador
                    String op=m.group(3);
                    // El cuarto parentesis agrupa el segundo operando
                    String op2Str = m.group(4);
                    double result = 0;
                    try {
                        double num1 = Double.parseDouble(op1Str);
                        double num2 = Double.parseDouble(op2Str);
                        switch (op.charAt(0)) {
                            case '+' : result = calc.suma(num1,num2);
                                break;
                            case '-' : result = calc.resta(num1,num2);
                                break;
                            case '*' : result = calc.mult(num1,num2);
                                break;
                            case '/' : result = calc.divide(num1,num2);
                                break;
                            case '^' : result = calc.eleva(num1,(int)num2);
                                break;
                        }
                        System.out.println(result);
                    }
                    catch (NumberFormatException nex) {
                        // Si entra aqui no se dieron valores numericos como operandos
                        System.out.println("Invalida");
                    }
                    catch (ArithmeticException eaex) {
                        // Si entra aqui hubo un error aritmetico al hacer la operacion
                        // aritmetica, especificamente, se hizo una division entre cero
                        System.out.println(eaex.getMessage());
                    }
                }
                else {
                    // Si entra aqui no se introdujo una linea con el formato esperado
                    System.out.println("Invalida");
                }
                linea=in.readLine().trim();
            }
            in.close();
            System.out.println("Gracias por usar esta calculadora.");
        }
        catch (IOException eio) {

        }
    }

}
