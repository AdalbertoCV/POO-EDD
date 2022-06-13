package recursion;

public class metodosRecursivos {

    /**
     * Realiza la multiplicacion entera (suma de a el numero de veces determinado por b)
     * @param a es el numero a multiplicar
     * @param b es la cantidad de veces
     * @param n es el valor constante de a
     * @return regresa el reultado de la multiplicacion
     */
    public int multiplicacionEntera(int a, int b, int n){
        if (a == (n*b)){ // caso base
            return a;
        }else{
            return multiplicacionEntera(a+n,b,n);
        }
    }

    /**
     * Determina si un numero es primo
     * @param n es el numero a evaluar
     * @param divisor sera el divisor a evaluar (siempre es 2)
     * @return regresa true si es primo
     */
    public boolean esPrimo(int n, int divisor){
        if(n/2 < divisor){
            return true;
        } else {
            if(n%divisor==0){
                return false;
            } else {
                return esPrimo(n, divisor+1);
            }
        }
    }

    /**
     * Indica si un numero enviado como parametro es o no binario
     * @param n es el numero a evaluar
     * @return regresa true si es binario y false si no lo es
     */
    public boolean esBinario(long n){
        String cadena = ""+n;
        if (cadena.length() == 1){  // caso base
            if (cadena.charAt(0) != '1' &&  cadena.charAt(0) != '0' ){
                return false;
            }else{
                return true;
            }
        }
        if (cadena.charAt(cadena.length()-1)!= '1' && cadena.charAt(cadena.length()-1)!= '0'){ // caso base
            return false;
        }else{
            cadena = cadena.substring(0,cadena.length()-1);
            return esBinario(Long.parseLong(cadena));
        }
    }

    /**
     * Convierte un numero decimal a hexadecimal
     * @param n es el numero a convertir
     * @param hexa es la cadena en la que se ira guardando la conversion (se envia vacia)
     * @return regresa la cadena convertida
     */
    public String aHexadecimal(long n,String hexa){
        if (n<=15){// caso base
            if (n<=9){
                hexa = hexa +n;
            }else{
                if (n==10){
                    hexa = hexa + "A";
                }
                if (n==11){
                    hexa = hexa + "B";
                }
                if (n==12){
                    hexa = hexa + "C";
                }
                if (n==13){
                    hexa = hexa + "D";
                }
                if (n==14){
                    hexa = hexa + "E";
                }
                if (n==15){
                    hexa = hexa + "F";
                }
            }
            return invertirCadena(hexa, hexa.length()-1);
        }else{
            long residuo =  n % 16;
            if (residuo <=9){
                hexa = hexa + residuo;
            }else{
                if (residuo==10){
                    hexa = hexa + "A";
                }
                if (residuo==11){
                    hexa = hexa + "B";
                }
                if (residuo==12){
                    hexa = hexa + "C";
                }
                if (residuo==13){
                    hexa = hexa + "D";
                }
                if (residuo==14){
                    hexa = hexa + "E";
                }
                if (residuo==15){
                    hexa = hexa + "F";
                }
            }
            return aHexadecimal(Math.round(n/16),hexa);
        }
    }

    /**
     * invierte una cadena de forma recursiva
     * @param cad es la cadena a invertir
     * @param indice es el indice a empezar (la ultima posicion de la cadena)
     * @return regresa la cadena invertida
     */
    private String invertirCadena(String cad, int indice){
        if(indice == 0){
            return cad.charAt(0) + "";
        }
        char letraActual= cad.charAt(indice);
        return letraActual + invertirCadena(cad, indice-1);
    }

    /**
     * obtiene el maximo comun divisor de 2 numeros
     * @param a es el primer numero
     * @param b es el segundo numero
     * @return regresa el maximo comun divisor
     */
    public int maximoComunDivisor(int a, int b){
        if (a==b){
            return a;
        }else{
            if (a>b){
                return maximoComunDivisor(a-b,b);
            }else{
                return maximoComunDivisor(a,b-a);
            }
        }
    }

    /**
     * Convierte un numero decimal a binario
     * @param n es el numero a convertir
     * @param bin es la cadena en donde se va guardando el resultado (debe enviarse vacia)
     * @return regresa el binario del numero
     */
    public String aBinario(int n,String bin) {
        if (n<2){
            bin = bin+n;
            return invertirCadena(bin,bin.length()-1);
        }else{
            if(n%2 == 0){
                bin = bin + 0;
            }else{
                bin = bin + 1;
            }
        }
        return aBinario(n/2, bin);
    }
}
