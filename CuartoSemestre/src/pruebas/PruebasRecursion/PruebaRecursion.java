package pruebas.PruebasRecursion;

import entradasalida.SalidaPorDefecto;
import recursion.metodosRecursivos;

public class PruebaRecursion {
    public static void main(String[] args){
        metodosRecursivos recursion = new metodosRecursivos();
        SalidaPorDefecto.consola("Prueba multiplicacion entera de 5, 6 veces: " + recursion.multiplicacionEntera(5,6,5)+"\n");
        SalidaPorDefecto.consola("Prueba multiplicacion entera de 10, 10 veces: " + recursion.multiplicacionEntera(10,10,10)+"\n");
        SalidaPorDefecto.consola("Prueba numero primo 17: " + recursion.esPrimo(17,2)+"\n");
        SalidaPorDefecto.consola("Prueba numero primo 18: " + recursion.esPrimo(18,2)+"\n");
        SalidaPorDefecto.consola("Prueba numero binario 100110: "+ recursion.esBinario(100110) + "\n");
        SalidaPorDefecto.consola("Prueba numero binario 100132: "+ recursion.esBinario(100132) + "\n");
        SalidaPorDefecto.consola("Prueba convertir a hexadecimal 65029: " + recursion.aHexadecimal(65029,"")+"\n");
        SalidaPorDefecto.consola("Prueba convertir a hexadecimal 1000: " + recursion.aHexadecimal(1000,"")+"\n");
        SalidaPorDefecto.consola("Prueba maximo comun divisor de 412 y 184: " + recursion.maximoComunDivisor(412,184)+"\n");
        SalidaPorDefecto.consola("Prueba maximo comun divisor de 144 y 80: " + recursion.maximoComunDivisor(144,80)+"\n");
        SalidaPorDefecto.consola("Prueba convertir a binario 150: " + recursion.aBinario(150,"")+"\n");
        SalidaPorDefecto.consola("Prueba convertir a binario 678: " + recursion.aBinario(678,"")+"\n");
    }
}
