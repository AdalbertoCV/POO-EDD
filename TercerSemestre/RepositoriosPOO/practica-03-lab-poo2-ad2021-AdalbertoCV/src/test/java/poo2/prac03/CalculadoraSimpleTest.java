package poo2.prac03;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraSimpleTest {
    private final CalculadoraSimple calc = new CalculadoraSimple();
    private final static int NUMTESTS=100;
    private final static int CALIF_SUMA=18;
    private final static int CALIF_RESTA=18;
    private final static int CALIF_MULT=18;
    private final static int CALIF_DIVIDE=23;
    private final static int CALIF_ELEVA=23;
    private final static int MAXPOT = 20;
    private final static int MAXBASE= 30;
    private final static int MAXCALIF=100;
    private final Random rand = new Random();
    private final double[] firstOpnds=new double[NUMTESTS];
    private final double[] secondOpnds =new double[NUMTESTS];
    private static int calificacion;

    @BeforeAll
    static void beforeAll() {
        calificacion=0;
    }

    @BeforeEach
    void setUp() {
        for (int i = 0; i < NUMTESTS; i++) {
            int residuo=i%4;
            firstOpnds[i]= rand.nextDouble()* rand.nextInt(10000);
            secondOpnds[i]= rand.nextDouble() * rand.nextInt(10000);
            if (residuo==0) {
                if (rand.nextBoolean()) {
                    firstOpnds[i]=-firstOpnds[i];
                }
                else {
                    secondOpnds[i]=-secondOpnds[i];
                }
            }
            else if (residuo==1) {
                firstOpnds[i]=-firstOpnds[i];
                secondOpnds[i]=-secondOpnds[i];
            }
            else if (residuo==2) {
                if (rand.nextBoolean()) {
                    firstOpnds[i]=0;
                }
                else{
                    secondOpnds[i]=0;
                }
            }
        }

    }

    @AfterAll
    static void afterAll() {
        System.out.printf("Calificacion:%d/%d\n",calificacion,MAXCALIF);
    }

    @Test
    void pruebaSuma() {
        for (int i = 0; i < NUMTESTS; i++) {
            System.out.printf("Prim:%f, Sec: %f\n",firstOpnds[i],secondOpnds[i]);
            double num1 = firstOpnds[i];
            double num2 = secondOpnds[i];
            assertEquals(num1+num2,calc.suma(num1,num2));
        }
        calificacion+=CALIF_SUMA;
    }

    @Test
    void pruebaResta() {
        for (int i = 0; i < NUMTESTS; i++) {
            System.out.printf("Prim:%f, Sec: %f\n",firstOpnds[i],secondOpnds[i]);
            double num1 = firstOpnds[i];
            double num2 = secondOpnds[i];
            assertEquals(num1-num2,calc.resta(num1,num2));
        }
        calificacion+=CALIF_RESTA;
    }

    @Test
    void pruebaMult() {
        for (int i = 0; i < NUMTESTS; i++) {
            System.out.printf("Prim:%f, Sec: %f\n",firstOpnds[i],secondOpnds[i]);
            double num1 = firstOpnds[i];
            double num2 = secondOpnds[i];
            assertEquals(num1*num2,calc.mult(num1,num2));
        }
        calificacion+=CALIF_MULT;
    }

    @Test
    void pruebaDivide() {
        for (int i = 0; i < NUMTESTS; i++) {
            System.out.printf("Prim:%f, Sec: %f\n",firstOpnds[i],secondOpnds[i]);
            double num1 = firstOpnds[i];
            double num2 = secondOpnds[i];
            if (num2==0) {
                assertThrows(ArithmeticException.class, () -> calc.divide(num1,num2)
                );
            }
            else {
                assertEquals(num1/num2,calc.divide(num1,num2));
            }

        }
        calificacion+=CALIF_DIVIDE;
    }

    @Test
    void pruebaEleva() {
        for (int i = 0; i < NUMTESTS; i++) {
            System.out.printf("Prim:%f, Sec: %f\n",firstOpnds[i],secondOpnds[i]);
            double num1=firstOpnds[i];
            if (num1>MAXBASE) {
                num1 = num1 - (int)(num1/MAXBASE)*MAXBASE;
            }
            else if (num1<-MAXBASE) {
                num1 = -num1;
                num1 = num1 - (int)(num1/MAXBASE)*MAXBASE;
                num1 = -num1;
            }
            int num2 = (int)secondOpnds[i]%MAXPOT;
            double expected = Math.pow(num1,num2);
            double delta = Math.abs(expected/1000000000000000.0);
            System.out.printf("*Prim:%f, Sec: %d\n",num1,num2);
            System.out.printf("Exp:%f, Rea: %f\n",expected,calc.eleva(num1,num2));
            assertEquals(expected,calc.eleva(num1,num2),delta);
        }
        calificacion+=CALIF_ELEVA;
    }
}