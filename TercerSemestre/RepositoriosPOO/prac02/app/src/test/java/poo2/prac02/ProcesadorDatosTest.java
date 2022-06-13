package poo2.prac02;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class ProcesadorDatosTest {	
	private final Random rand = new Random();
	private final int MAXVALRAND = 1000;
	private final int RANGO=50;

	private static int calificacion;
    private static List<String> ins, outs;
	private final static int MAXCALIF=100;
	private final static int CALIF_DATOS_MUESTRA=25;
    private final static int CALIF_DATOS_OTROS_DATOS=25;

	@BeforeAll
    static void beforeAll() {
        calificacion=0;
    }

    @AfterAll
    static void afterAll() {
        System.out.printf("Calificacion:%d/%d\n",calificacion,MAXCALIF);
    }

    @Test
    void testDatosMuestra() {
        // Se declara una variable para mantener
        // una lista de Strings
        List<String> lineasEntrada = new ArrayList<>();
        // Se colocan Strings fijos en lineasEntrada
        // para probar el metodo procesaListaNumeros
        lineasEntrada.add("hola");
        lineasEntrada.add("46.2");
        lineasEntrada.add("17015");
        lineasEntrada.add("34.246");
        lineasEntrada.add("nuevo amigo mio");
        lineasEntrada.add("juan gana 2891.23 a la semana");
        lineasEntrada.add("2107.71");
        lineasEntrada.add("un millon");
        lineasEntrada.add("781.23");
        // Se define un codigo de idioma
        String codigoIdioma="es";
        // Se define un codigo de pais
        String codigoPais="MX";
        // Se llama al metodo procesaListaNumeros
        // creando un objeto de clase ProcesadorDatos
        ProcesadorDatos proc = new ProcesadorDatos();
        List<String> lineasResultado = 
          proc.procesaListaNumeros(lineasEntrada,
            codigoIdioma, codigoPais);
        assertEquals(5, lineasResultado.size());
        assertEquals("$17,015.00", lineasResultado.get(0));
        assertEquals("$2,107.71", lineasResultado.get(1));
        assertEquals("$781.23", lineasResultado.get(2));
        assertEquals("$46.20", lineasResultado.get(3));
        assertEquals("$34.25", lineasResultado.get(4));
        // Si pasa las pruebas se otorgan los puntos correspondientes
        calificacion += CALIF_DATOS_MUESTRA;
    }

    @Test
    void testDatosOtros() {
        // Se declara una variable para mantener
        // una lista de Strings
        List<String> lineasEntrada = new ArrayList<>();
        // Se colocan Strings fijos en lineasEntrada
        // para probar el metodo procesaListaNumeros
        lineasEntrada.add("tres mil");
        lineasEntrada.add("92231.3551");
        lineasEntrada.add("663.11");
        lineasEntrada.add("1945");
        lineasEntrada.add("mios son 22000");
        lineasEntrada.add("tengo 20 years");
        lineasEntrada.add("2020");
        lineasEntrada.add("21");
        lineasEntrada.add("1071");
        lineasEntrada.add("7");
        lineasEntrada.add("");
        lineasEntrada.add("inmenso es el mar");
        lineasEntrada.add("viva la UAZ");
        lineasEntrada.add("Software rifa");
        lineasEntrada.add("881.1");
        // Se define un codigo de idioma
        String codigoIdioma="es";
        // Se define un codigo de pais
        String codigoPais="MX";
        // Se llama al metodo procesaListaNumeros
        // creando un objeto de clase ProcesadorDatos
        ProcesadorDatos proc = new ProcesadorDatos();
        List<String> lineasResultado = 
          proc.procesaListaNumeros(lineasEntrada,
            codigoIdioma, codigoPais);
        String[] res = {"$92,231.36","$2,020.00","$1,945.00","$1,071.00","$881.10","$663.11","$21.00","$7.00"};
        assertEquals(res.length, lineasResultado.size());
        for (int i=0; i<res.length; i++) {
            assertEquals(res[i],lineasResultado.get(i));
        }
        // Si pasa las pruebas se otorgan los puntos correspondientes
        calificacion += CALIF_DATOS_OTROS_DATOS;
    }

    @Test
    void testDatosSinNumeros() {
        // Se declara una variable para mantener
        // una lista de Strings
        List<String> lineasEntrada = new ArrayList<>();
        // Se colocan Strings fijos en lineasEntrada
        // para probar el metodo procesaListaNumeros
        lineasEntrada.add("esto");
        lineasEntrada.add("es un ejemplo");
        lineasEntrada.add("con solo");
        lineasEntrada.add("lineas");
        lineasEntrada.add("de texto");
        lineasEntrada.add("sin un");
        lineasEntrada.add("solo dato");
        lineasEntrada.add("por lo cual");
        lineasEntrada.add("no debe");
        lineasEntrada.add("pasar nada");
        // Se define un codigo de idioma
        String codigoIdioma="es";
        // Se define un codigo de pais
        String codigoPais="MX";
        // Se llama al metodo procesaListaNumeros
        // creando un objeto de clase ProcesadorDatos
        ProcesadorDatos proc = new ProcesadorDatos();
        List<String> lineasResultado = 
          proc.procesaListaNumeros(lineasEntrada,
            codigoIdioma, codigoPais);        
        assertEquals(0, lineasResultado.size());
        // Si pasa las pruebas se otorgan los puntos correspondientes
        calificacion += CALIF_DATOS_OTROS_DATOS;
    }

    @Test
    void testDatosSoloNums() {
        // Se declara una variable para mantener
        // una lista de Strings
        List<String> lineasEntrada = new ArrayList<>();
        // Se colocan Strings fijos en lineasEntrada
        // para probar el metodo procesaListaNumeros
        lineasEntrada.add("871");
        lineasEntrada.add("532");
        lineasEntrada.add("10.2");
        lineasEntrada.add("8973.2");
        lineasEntrada.add("114");
        lineasEntrada.add("2001");
        lineasEntrada.add("1976");
        lineasEntrada.add("25");
        lineasEntrada.add("5155.116631");
        lineasEntrada.add("223.21516");
        // Se define un codigo de idioma
        String codigoIdioma="es";
        // Se define un codigo de pais
        String codigoPais="MX";
        // Se llama al metodo procesaListaNumeros
        // creando un objeto de clase ProcesadorDatos
        ProcesadorDatos proc = new ProcesadorDatos();
        List<String> lineasResultado = 
          proc.procesaListaNumeros(lineasEntrada,
            codigoIdioma, codigoPais);
        String[] res = {"$8,973.20","$5,155.12","$2,001.00","$1,976.00","$871.00","$532.00","$223.22","$114.00","$25.00","$10.20"};
        assertEquals(res.length, lineasResultado.size());
        for (int i=0; i<res.length; i++) {
            assertEquals(res[i],lineasResultado.get(i));
        }
        // Si pasa las pruebas se otorgan los puntos correspondientes
        calificacion += CALIF_DATOS_OTROS_DATOS;
    }
}