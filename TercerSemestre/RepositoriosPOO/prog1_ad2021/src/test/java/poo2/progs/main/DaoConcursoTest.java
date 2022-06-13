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
import poo2.progs.basedatos.DaoConcurso;
import poo2.progs.entidades.Concurso;
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
public class DaoConcursoTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static List<Concurso> datosEsperados;
    private static IDatabaseConnection conndbunit;
    private static int calificacion;
    private final static int CALIF_OBTENER=5;
    private final static int CALIF_AGREGAR=10;
    private final static int CALIF_UPDATE=10;
    private final static int CALIF_DELETE=5;
    private final static int MAX_CALIF=90;
    private final static double PORCENTAJE_DAOCONCURSO = 7.0;

    private static void iniciaDatosLista() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream("datosconcurso.tsv"), StandardCharsets.UTF_8));
        String linea=in.readLine();
        datosEsperados=new ArrayList<>();
        while (linea!=null) {
            String[] p=linea.split("\t");
            Concurso concurso=new Concurso(Long.parseLong(p[0]), p[1],
                    Date.valueOf(p[2]),Date.valueOf(p[3]),Date.valueOf(p[4]));
            datosEsperados.add(concurso);
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
        databaseTester.setOperationListener(new ConfigAccesoBaseDatos.CustomConfigurationOperationListener());
        conndbunit=databaseTester.getConnection();
        DatabaseConfig config=conndbunit.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS,true);
        IDataSet dataSet=new FlatXmlDataSetBuilder().build(new FileInputStream("concursosv2.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onTearDown();
        System.out.printf("Puntos para Pruebas DAOConcurso: %.2f/%.2f\n",calificacion* PORCENTAJE_DAOCONCURSO /MAX_CALIF, PORCENTAJE_DAOCONCURSO);
        TotalAcumulado.acumula(calificacion* PORCENTAJE_DAOCONCURSO /MAX_CALIF);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }


    private void comparaConcurso(Concurso expected,
                                 Concurso actual) {
        Assertions.assertEquals(expected.getIdConcurso(),
                actual.getIdConcurso());
        Assertions.assertEquals(expected.getNombreConcurso(),
                actual.getNombreConcurso());
        Assertions.assertEquals(expected.getFechaConcurso(),
                actual.getFechaConcurso());
        Assertions.assertEquals(expected.getFechaInicioRegistro(),
                actual.getFechaInicioRegistro());
        Assertions.assertEquals(expected.getFechaFinRegistro(),
                actual.getFechaFinRegistro());
    }

    @Test
    @Order(2)
    public void testObtenConcursoExistente() throws Exception {
        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        long idABuscar=1;
        Optional<Concurso> actual = Optional.empty();
        try {
            actual = daoConcurso.get(idABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoConcurso");
        }
        Assertions.assertTrue(actual.isPresent());
        comparaConcurso(datosEsperados.get(0),actual.get());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(3)
    public void testObtenConcursoInexistente() throws Exception {
        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        long idABuscar=10;
        Optional<Concurso> actual = Optional.empty();
        try {
            actual = daoConcurso.get(idABuscar);
            Assertions.assertFalse(actual.isPresent());
            actual = daoConcurso.get("Inexistente");
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoConcurso");
        }

        Assertions.assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(4)
    public void testObtenConcursoAll() throws Exception {
        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        List<Concurso> actual;
        try {
            actual=daoConcurso.getAll();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo getAll de DaoConcurso");
        }
        Assertions.assertEquals(datosEsperados.size(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaConcurso(datosEsperados.get(i), actual.get(i));
        }
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(5)
    public void testAgregarConcursoValida() throws Exception {
        long id=3;
        String nom="Coding Cup TecNM";
        Date fecha=Date.valueOf("2020-11-06");
        Date fechaIni=Date.valueOf("2020-10-15");
        Date fechaFin=Date.valueOf("2020-11-03");
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoConcurso.save(conc);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoConcurso");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("concurso",
                "SELECT * FROM concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concurso_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(6)
    public void testAgregarConcursoDuplicada() throws Exception {
        long id=3;
        String nom="Coding Cup TecNM";
        Date fecha=Date.valueOf("2020-11-06");
        Date fechaIni=Date.valueOf("2020-10-15");
        Date fechaFin=Date.valueOf("2020-11-03");
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoConcurso.save(conc);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoConcurso");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    private void validaNullo_Vacio(DaoConcurso daoConcurso, String valor, long idconc, boolean nuevo) {
        String nom="Coding Cup TecNM";
        Date fecha=Date.valueOf("2020-11-06");
        Date fechaIni=Date.valueOf("2020-10-15");
        Date fechaFin=Date.valueOf("2020-11-03");
        Concurso conc = new Concurso(idconc,valor,fecha,fechaIni,fechaFin);
        boolean resultado;

        resultado=nuevo?daoConcurso.save(conc):daoConcurso.update(conc);
        Assertions.assertFalse(resultado);

        conc.setNombreConcurso(nom);
        if (valor==null) {
            conc.setFechaConcurso(null);
            resultado=nuevo?daoConcurso.save(conc):daoConcurso.update(conc);
            Assertions.assertFalse(resultado);

            conc.setFechaConcurso(fecha);
            conc.setFechaInicioRegistro(null);
            resultado=nuevo?daoConcurso.save(conc):daoConcurso.update(conc);
            Assertions.assertFalse(resultado);

            conc.setFechaInicioRegistro(fechaIni);
            conc.setFechaFinRegistro(null);
            resultado=nuevo?daoConcurso.save(conc):daoConcurso.update(conc);
            Assertions.assertFalse(resultado);
        }
    }

    @Test
    @Order(7)
    public void testAgregarConcursoInvalida() throws Exception {
        String[] nombre={"Concurso Poderoso","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        Date[] fecha = {Date.valueOf("2020-11-05"), Date.valueOf("2020-10-31")};
        Date fechaIniVal= Date.valueOf("2020-10-15");
        Date fechaIniInval = Date.valueOf("2020-10-31");
        Date fechaFinVal = Date.valueOf("2020-10-31");
        Date fechaFinInval=Date.valueOf("2020-10-15");
        long idconc=10L;
        Concurso conc = new Concurso(idconc,nombre[1],fecha[0],fechaIniVal,fechaFinVal);

        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            validaNullo_Vacio(daoConcurso,null,30,true);
            validaNullo_Vacio(daoConcurso,"",30, true);

            resultado=daoConcurso.save(conc);
            Assertions.assertFalse(resultado);

            conc.setNombreConcurso(nombre[0]);
            conc.setFechaFinRegistro(fechaFinInval);
            resultado=daoConcurso.save(conc);
            Assertions.assertFalse(resultado);

            conc.setFechaFinRegistro(fechaFinVal);
            conc.setFechaInicioRegistro(fechaIniInval);
            resultado=daoConcurso.save(conc);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println("ERROR");
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoConcurso");

            resultado=true;
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(8)
    public void testUpdateConcursoValida() throws Exception {
        long id=3;
        String nom="Coding Cup TecNM 2020";
        Date fecha=Date.valueOf("2020-11-04");
        Date fechaIni=Date.valueOf("2020-10-13");
        Date fechaFin=Date.valueOf("2020-11-01");
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=daoConcurso.update(conc);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoConcurso");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("concurso",
                "SELECT * FROM concurso WHERE id_concurso=3");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concurso_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(9)
    public void testUpdateConcursoInexistente() throws Exception {
        long id=30;
        String nom="Coding Cup TecNM 2020";
        Date fecha=Date.valueOf("2020-11-04");
        Date fechaIni=Date.valueOf("2020-10-13");
        Date fechaFin=Date.valueOf("2020-11-01");
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=daoConcurso.update(conc);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoConcurso");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(10)
    public void testUpdateConcursoInvalida() throws Exception {
        String[] nombre={"Concurso Poderoso","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        Date[] fecha = {Date.valueOf("2020-11-05"), Date.valueOf("2020-10-31")};
        Date[] fechaIni = {Date.valueOf("2020-10-15"), Date.valueOf("2020-10-31")};
        Date[] fechaFin = {Date.valueOf("2020-10-31"), Date.valueOf("2020-10-15")};
        long idconc=10L;
        Concurso conc = new Concurso(idconc,nombre[1],fecha[0],fechaIni[0],fechaFin[0]);

        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        boolean resultado;

        try {
            validaNullo_Vacio(daoConcurso,null,3,false);
            validaNullo_Vacio(daoConcurso,"",3, false);

            resultado=daoConcurso.update(conc);
            Assertions.assertFalse(resultado);

            conc.setNombreConcurso(nombre[0]);
            conc.setFechaFinRegistro(fechaFin[1]);
            resultado=daoConcurso.update(conc);
            Assertions.assertFalse(resultado);

            conc.setFechaFinRegistro(fechaFin[0]);
            conc.setFechaFinRegistro(fechaIni[1]);
            resultado=daoConcurso.update(conc);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoConcurso");
            System.err.println(ex.getMessage());
            resultado=true;
        }

        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(11)
    public void testDeleteConcursoInexistente() throws Exception {
        long id=350L;
        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        try {
            boolean resultado=daoConcurso.delete(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoConcurso");
        }
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(12)
    public void testDeleteConcursoExistente() throws Exception {
        long id=3L;
        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        try {
            boolean resultado=daoConcurso.delete(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoConcurso");
        }

        ITable actualTable=conndbunit.createQueryTable("concurso",
                "SELECT * FROM concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concurso_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(13)
    public void testDeleteConcursoInvalida() throws Exception {
        Long id=1L;
        DaoConcurso daoConcurso=new DaoConcurso(conndbunit.getConnection());
        try {
            boolean resultado=daoConcurso.delete(id);
            Assertions.assertFalse(resultado);
            resultado=daoConcurso.delete(null);
            Assertions.assertFalse(resultado);
            resultado=daoConcurso.delete("Inexistente");
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoConcurso");
        }
        calificacion += CALIF_DELETE;
    }

}
