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
import poo2.progs.basedatos.DaoSedeConcurso;
import poo2.progs.entidades.SedeConcurso;
import poo2.progs.interfaces.DaoException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DaoSedeConcursoTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static List<SedeConcurso> datosEsperados;
    private static IDatabaseConnection conndbunit;
    private static int calificacion;
    private final static int CALIF_OBTENER=5;
    private final static int CALIF_AGREGAR=10;
    private final static int CALIF_UPDATE=10;
    private final static int CALIF_DELETE=5;
    private final static int MAX_CALIF=90;
    private final static double PORCENTAJE_DAOSEDECONCURSO = 4.0;

    private static void iniciaDatosLista() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream("datossedeconc.tsv"), StandardCharsets.UTF_8));
        String linea=in.readLine();
        datosEsperados=new ArrayList<>();
        while (linea!=null) {
            String[] p=linea.split("\t");
            SedeConcurso sc=new SedeConcurso(Long.parseLong(p[0]),
                    Long.parseLong(p[1]),Long.parseLong(p[2]));
            datosEsperados.add(sc);
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
        System.out.printf("Puntos para Pruebas DAOSedeConcurso: %.2f/%.2f\n",calificacion* PORCENTAJE_DAOSEDECONCURSO /MAX_CALIF, PORCENTAJE_DAOSEDECONCURSO);
        TotalAcumulado.acumula(calificacion* PORCENTAJE_DAOSEDECONCURSO /MAX_CALIF);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }


    private void comparaSedeConcurso(SedeConcurso expected,
                                     SedeConcurso actual) {
        // llave primaria autoincrementable
        /*assertEquals(expected.getIdSedeConcurso(),
                actual.getIdSedeConcurso()); */
        Assertions.assertEquals(expected.getIdSede(),
                actual.getIdSede());
        Assertions.assertEquals(expected.getIdConcurso(),
                actual.getIdConcurso());
    }

    @Test
    @Order(2)
    public void testObtenSedeConcursoExistente() throws Exception {
        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        long idABuscar=1;
        Optional<SedeConcurso> actual = Optional.empty();
        try {
            actual = daoSedeConcurso.get(idABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoSedeConcurso");
        }
        Assertions.assertTrue(actual.isPresent());
        comparaSedeConcurso(datosEsperados.get(0),actual.get());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(3)
    public void testObtenSedeConcursoInexistente() throws Exception {
        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        long idABuscar=10;
        Optional<SedeConcurso> actual = Optional.empty();
        try {
            actual = daoSedeConcurso.get(idABuscar);
            Assertions.assertFalse(actual.isPresent());
            actual = daoSedeConcurso.get("Inexistente");
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoSedeConcurso");
        }

        Assertions.assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(4)
    public void testObtenSedeConcursoAll() throws Exception {
        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        List<SedeConcurso> actual;
        try {
            actual=daoSedeConcurso.getAll();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo getAll de DaoSedeConcurso");
        }
        Assertions.assertEquals(datosEsperados.size(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaSedeConcurso(datosEsperados.get(i), actual.get(i));
        }
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(5)
    public void testAgregarSedeConcursoValida() throws Exception {
        long id=3;
        long idsede=2;
        long idconcurso=2;

        SedeConcurso sc = new SedeConcurso(id, idsede,idconcurso);

        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoSedeConcurso.save(sc);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoSedeConcurso");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("sede_concurso",
                "SELECT id_sede,id_concurso FROM sede_concurso ORDER BY id_sede_concurso");



        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sede_concurso_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede_concurso");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(6)
    public void testAgregarSedeConcursoDuplicada() throws Exception {
        long id=3;
        long idsede=2;
        long idconcurso=2;

        SedeConcurso sc = new SedeConcurso(id, idsede,idconcurso);

        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoSedeConcurso.save(sc);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoSedeConcurso");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }


    @Test
    @Order(7)
    public void testAgregarSedeConcursoInvalida() throws Exception {
        long id=100;
        long idsedeValida=2;
        long idconcursoValido=2;
        long idsedeInvalida=100;
        long idconcursoInvalido=100;

        SedeConcurso sc= new SedeConcurso(id,idsedeInvalida,idconcursoValido);
        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=daoSedeConcurso.save(sc);
            Assertions.assertFalse(resultado);

            sc.setIdSede(idsedeValida);
            sc.setIdConcurso(idconcursoInvalido);
            resultado=daoSedeConcurso.save(sc);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println("ERROR");
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoSedeConcurso");
        }

        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(8)
    public void testUpdateSedeConcursoValida() throws Exception {
        long id=3;
        long idsede=1;
        long idconcurso=1;
        SedeConcurso sc = new SedeConcurso(id, idsede,idconcurso);

        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=daoSedeConcurso.update(sc);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoSedeConcurso");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("sede_concurso",
                "SELECT id_sede,id_concurso FROM sede_concurso WHERE id_sede_concurso>2");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sede_concurso_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede_concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(9)
    public void testUpdateSedeConcursoInexistente() throws Exception {
        long id=305L;
        long idsede=1;
        long idconcurso=1;
        SedeConcurso sc = new SedeConcurso(id, idsede,idconcurso);

        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=daoSedeConcurso.update(sc);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoSedeConcurso");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(10)
    public void testUpdateSedeConcursoInvalida() throws Exception {
        long id=1000;
        long idsedeValida=2;
        long idconcursoValido=2;
        long idsedeInvalida=100;
        long idconcursoInvalido=100;

        SedeConcurso sc= new SedeConcurso(id,idsedeInvalida,idconcursoValido);

        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=daoSedeConcurso.update(sc);
            Assertions.assertFalse(resultado);

            sc.setIdSede(idsedeValida);
            sc.setIdConcurso(idconcursoInvalido);
            resultado=daoSedeConcurso.update(sc);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println("ERROR");
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoSedeConcurso");
        }
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(11)
    public void testDeleteSedeConcursoInexistente() throws Exception {
        long id=350L;
        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        try {
            boolean resultado=daoSedeConcurso.delete(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoSedeConcurso");
        }
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(12)
    public void testDeleteSedeConcursoExistente() throws Exception {
        long id=3L;
        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        try {
            boolean resultado=daoSedeConcurso.delete(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoSedeConcurso");
        }

        ITable actualTable=conndbunit.createQueryTable("sede_concurso",
                "SELECT id_sede,id_concurso FROM sede_concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sede_concurso_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede_concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(13)
    public void testDeleteSedeConcursoInvalida() throws Exception {
        DaoSedeConcurso daoSedeConcurso=new DaoSedeConcurso(conndbunit.getConnection());
        try {
            boolean resultado=daoSedeConcurso.delete(null);
            Assertions.assertFalse(resultado);
            resultado=daoSedeConcurso.delete("Inexistente");
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoSedeConcurso");
        }
        calificacion += CALIF_DELETE;
    }
}

