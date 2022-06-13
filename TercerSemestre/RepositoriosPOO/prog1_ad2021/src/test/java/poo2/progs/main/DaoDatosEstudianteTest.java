package poo2.progs.main;

import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dbunit.*;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.*;
import poo2.ConfigAccesoBaseDatos;
import poo2.progs.basedatos.DaoDatosEstudiante;
import poo2.progs.entidades.DatosEstudiante;
import poo2.progs.interfaces.DaoException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DaoDatosEstudianteTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static List<DatosEstudiante> datosEsperados;
    private static IDatabaseConnection conndbunit;
    private static int calificacion;
    private final static int CALIF_OBTENER=5;
    private final static int CALIF_AGREGAR=10;
    private final static int CALIF_UPDATE=10;
    private final static int CALIF_DELETE=5;
    private final static int MAX_CALIF=90;
    private final static double PORCENTAJE_DAODATOSEST = 8.0;

    private static void iniciaDatosLista() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream("datosestud.tsv"), StandardCharsets.UTF_8));
        String linea=in.readLine();
        datosEsperados=new ArrayList<>();
        while (linea!=null) {
            String[] p=linea.split("\t");
            DatosEstudiante est=new DatosEstudiante(
                    p[0],p[1],Date.valueOf(p[2]),Date.valueOf(p[3]));
            datosEsperados.add(est);
            linea=in.readLine();
        }
    }


    @BeforeAll
    public static void inicializa() throws Exception {
        iniciaDatosLista();
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        databaseTester=new JdbcDatabaseTester(ConfigAccesoBaseDatos.driverName,
                ConfigAccesoBaseDatos.url,
                ConfigAccesoBaseDatos.usuario,
                ConfigAccesoBaseDatos.clave);
        databaseTester.setOperationListener(new ConfigAccesoBaseDatos.CustomConfigurationOperationListener());        conndbunit=databaseTester.getConnection();
        DatabaseConfig config=conndbunit.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS,true);
        IDataSet dataSet=new FlatXmlDataSetBuilder().build(new FileInputStream("datosestudiante.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onTearDown();
        System.out.printf("Puntos para Pruebas DAODatosEstudiante: %.2f/%.2f\n",calificacion* PORCENTAJE_DAODATOSEST /MAX_CALIF, PORCENTAJE_DAODATOSEST);
        TotalAcumulado.acumula(calificacion* PORCENTAJE_DAODATOSEST /MAX_CALIF);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }


    private void comparaDatosEstudiante(DatosEstudiante expected,
                                        DatosEstudiante actual) {
        Assertions.assertEquals(expected.getEmailEstudiante(),
                actual.getEmailEstudiante());
        Assertions.assertEquals(expected.getCarreraEstudiante(),
                actual.getCarreraEstudiante());
        Assertions.assertEquals(expected.getFechaInicioCarrera(),
                actual.getFechaInicioCarrera());
        Assertions.assertEquals(expected.getFechaEsperadaTerminacion(),
                actual.getFechaEsperadaTerminacion());
    }

    @Test
    @Order(2)
    public void testObtenDatosEstudianteExistente() throws Exception {
        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        String emailABuscar="charly@uaz.edu.mx";
        Optional<DatosEstudiante> actual = Optional.empty();
        try {
            actual = daoDatosEstudiante.get(emailABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoDatosEstudiante");
        }
        Assertions.assertTrue(actual.isPresent());
        comparaDatosEstudiante(datosEsperados.get(0),actual.get());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(3)
    public void testObtenDatosEstudianteInexistente() throws Exception {
        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        String emailABuscar="noexiste@infierno.com";

        Optional<DatosEstudiante> actual = Optional.empty();
        try {
            actual = daoDatosEstudiante.get(emailABuscar);
            Assertions.assertFalse(actual.isPresent());
            actual = daoDatosEstudiante.get(80L);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoDatosEstudiante");
        }

        Assertions.assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(4)
    public void testObtenDatosEstudianteAll() throws Exception {
        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        List<DatosEstudiante> actual;
        try {
            actual=daoDatosEstudiante.getAll();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo getAll de DaoDatosEstudiante");
        }
        Assertions.assertEquals(datosEsperados.size(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaDatosEstudiante(datosEsperados.get(i), actual.get(i));
        }
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(5)
    public void testAgregarDatosEstudianteValida() throws Exception {
        String email="lauratapia@outlook.com";
        String carrera="Ing. en Informatica";
        Date fechaInicio=Date.valueOf("2019-08-12");
        Date fechaFin=Date.valueOf("2023-07-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);

        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoDatosEstudiante.save(est);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoDatosEstudiante");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("datos_estudiante",
                "SELECT * FROM datos_estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosestudiante_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(6)
    public void testAgregarDatosEstudianteDuplicada() throws Exception {
        String email="lauratapia@outlook.com";
        String carrera="Ing. en Informatica";
        Date fechaInicio=Date.valueOf("2019-08-12");
        Date fechaFin=Date.valueOf("2023-07-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);

        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoDatosEstudiante.save(est);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoDatosEstudiante");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    private void validaNullo_Vacio(DaoDatosEstudiante daoDatosEstudiante, String valor, String id, boolean nuevo) {
        String email=id;
        String carrera="Ing. en Informatica";
        Date fechaInicio=Date.valueOf("2019-08-12");
        Date fechaFin=Date.valueOf("2023-07-01");
        boolean resultado;
        String valprev;

        DatosEstudiante per= new DatosEstudiante(valor,carrera,fechaInicio,fechaFin);

        resultado=nuevo?daoDatosEstudiante.save(per):daoDatosEstudiante.update(per);
        Assertions.assertFalse(resultado);

        per.setEmailEstudiante(email);
        valprev=per.getCarreraEstudiante();
        per.setCarreraEstudiante(valor);
        resultado=nuevo?daoDatosEstudiante.save(per):daoDatosEstudiante.update(per);
        Assertions.assertFalse(resultado);

        if (valor==null) {
            per.setCarreraEstudiante(valprev);
            per.setFechaInicioCarrera(null);
            resultado = nuevo ? daoDatosEstudiante.save(per) : daoDatosEstudiante.update(per);
            Assertions.assertFalse(resultado);

            per.setFechaInicioCarrera(fechaInicio);
            per.setFechaEsperadaTerminacion(null);
            resultado=nuevo?daoDatosEstudiante.save(per):daoDatosEstudiante.update(per);
            Assertions.assertFalse(resultado);
        }

    }

    @Test
    @Order(7)
    public void testAgregarDatosEstudianteInvalida() throws Exception {
        String[] email={"xxtat@yho.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String[] carrera={"Informatica","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        Date[] fecha1={Date.valueOf("2018-10-10"),Date.valueOf("2018-10-10")};
        Date[] fecha2={Date.valueOf("2024-10-10"),Date.valueOf("2020-10-10")};
        DatosEstudiante est= new DatosEstudiante(
                email[1],carrera[0],fecha1[0],fecha2[0]);

        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        boolean resultado;
        try {
            validaNullo_Vacio(daoDatosEstudiante,null,"xxuigg@demonio.com",true);
            validaNullo_Vacio(daoDatosEstudiante,"","xxuigg@demonio.com",true);

            resultado=daoDatosEstudiante.save(est);
            Assertions.assertFalse(resultado);

            est.setEmailEstudiante(email[0]);
            est.setCarreraEstudiante(carrera[1]);
            resultado=daoDatosEstudiante.save(est);
            Assertions.assertFalse(resultado);

            est.setCarreraEstudiante(carrera[0]);
            est.setFechaEsperadaTerminacion(fecha2[1]);
            resultado=daoDatosEstudiante.save(est);
            Assertions.assertFalse(resultado);

        }
        catch (Exception ex) {
            System.err.println("ERROR");
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoDatosEstudiante");

            resultado=true;
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(8)
    public void testUpdateDatosEstudianteValida() throws Exception {
        String email="lauratapia@outlook.com";
        String carrera="Ing. en Informatica Admva";
        Date fechaInicio=Date.valueOf("2019-01-28");
        Date fechaFin=Date.valueOf("2023-02-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);

        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=daoDatosEstudiante.update(est);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoDatosEstudiante");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("datos_estudiante",
                "SELECT * FROM datos_estudiante WHERE email_estudiante='lauratapia@outlook.com'");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosestudiante_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(9)
    public void testUpdateDatosEstudianteInexistente() throws Exception {
        String email="ulgaja@itszn.edu.mx";
        String carrera="Ing. en Informatica Admva";
        Date fechaInicio=Date.valueOf("2019-01-28");
        Date fechaFin=Date.valueOf("2023-02-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);

        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=daoDatosEstudiante.update(est);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoDatosEstudiante");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(10)
    public void testUpdateDatosEstudianteInvalida() throws Exception {
        String[] email={"xxtat@yho.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String[] carrera={"Informatica","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        Date[] fecha1={Date.valueOf("2018-10-10"),Date.valueOf("2018-10-10")};
        Date[] fecha2={Date.valueOf("2024-10-10"),Date.valueOf("2020-10-10")};
        DatosEstudiante est= new DatosEstudiante(
                email[1],carrera[0],fecha1[0],fecha2[0]);

        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        boolean resultado;
        //resultado = true;
        try {
            validaNullo_Vacio(daoDatosEstudiante,null,"lauratapia@outlook.com",false);
            validaNullo_Vacio(daoDatosEstudiante,"","lauratapia@outlook.com",false);


            est.setEmailEstudiante(email[0]);
            est.setCarreraEstudiante(carrera[1]);
            resultado=daoDatosEstudiante.update(est);
            Assertions.assertFalse(resultado);

            est.setCarreraEstudiante(carrera[0]);
            est.setFechaEsperadaTerminacion(fecha2[1]);
            resultado=daoDatosEstudiante.update(est);
            Assertions.assertFalse(resultado);

        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoDatosEstudiante");
            System.err.println(ex.getMessage());
            resultado=true;
        }

        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(11)
    public void testDeleteDatosEstudianteInexistente() throws Exception {
        String id="SHY$$gq@gaga";
        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        try {
            boolean resultado=daoDatosEstudiante.delete(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoDatosEstudiante");
        }
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(12)
    public void testDeleteDatosEstudianteExistente() throws Exception {
        String id="lauratapia@outlook.com";
        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        try {
            boolean resultado=daoDatosEstudiante.delete(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoDatosEstudiante");
        }

        ITable actualTable=conndbunit.createQueryTable("datos_estudiante",
                "SELECT * FROM datos_estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosestudiante_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(13)
    public void testDeleteDatosEstudianteInvalida() throws Exception {
        Long id=1L;
        DaoDatosEstudiante daoDatosEstudiante=new DaoDatosEstudiante(conndbunit.getConnection());
        try {
            boolean resultado=daoDatosEstudiante.delete(id);
            Assertions.assertFalse(resultado);
            resultado=daoDatosEstudiante.delete(null);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoDatosEstudiante");
        }
        calificacion += CALIF_DELETE;
    }

}
