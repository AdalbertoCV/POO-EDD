package poo2.prac08.main;
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
import poo2.progs.basedatos.DaoMunicipio;
import poo2.progs.entidades.Municipio;
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
public class DaoMunicipioTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static IDatabaseConnection conndbunit;
    private static List<Municipio> datosEsperados;
    private static double calificacion;
    public static IDataSet expectedDataSet;

    private final static double CALIF_OBTENER_EXISTENTE=5;
    private final static double CALIF_OBTENER_INEXISTENTE=1;

    private final static double CALIF_OBTENER_ALL=5;
    private final static double CALIF_AGREGAR=1;
    private final static double CALIF_UPDATE=1;
    private final static double CALIF_DELETE=1;
    private final static double MAX_CALIF=14;

    @BeforeAll
    public static void inicializa() throws Exception {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        databaseTester=new JdbcDatabaseTester(ConfigAccesoBaseDatos.driverName,
                ConfigAccesoBaseDatos.url,
                ConfigAccesoBaseDatos.usuario,
                ConfigAccesoBaseDatos.clave);
        databaseTester.setOperationListener(new ConfigAccesoBaseDatos.CustomConfigurationOperationListener());
        conndbunit=databaseTester.getConnection();
        DatabaseConfig config=conndbunit.getConfig();
        /*config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS,true);
        IDataSet dataSet=new FlatXmlDataSetBuilder().build(new FileInputStream("municipio.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.REFRESH);
        databaseTester.onSetup();*/
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS,true);
        expectedDataSet=new FlatXmlDataSetBuilder().build(
                new FileInputStream("municipio.xml"));
        databaseTester.setDataSet(expectedDataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.REFRESH);
        databaseTester.onSetup();
        ITable expectedTable=expectedDataSet.getTable("municipio");
        datosEsperados=new ArrayList<>();
        int numRegistros=expectedTable.getRowCount();
        for (int i=0; i<numRegistros;i++) {
            long id = Long.parseLong(expectedTable.getValue(i,"id_municipio").toString());
            String nom = (String) expectedTable.getValue(i,"nombre_municipio");
            datosEsperados.add(new Municipio(id,nom));
        }
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.REFRESH);
        databaseTester.onTearDown();
        System.out.printf("Puntos para Pruebas DAOMunicipio: %.2f/%.2f\n",calificacion, MAX_CALIF);
        TotalAcumulado.acumula(calificacion);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }


    private void comparaMunicipio(Municipio expected,
                                  Municipio actual) {
        Assertions.assertEquals(expected.getIdMunicipio(),
                actual.getIdMunicipio());
        Assertions.assertEquals(expected.getNombreMunicipio(),
                actual.getNombreMunicipio());
    }

    @Test
    @Order(1)
    public void testObtenMunicipioExistente() throws Exception {
        DaoMunicipio daoMunicipio=new DaoMunicipio(conndbunit.getConnection());
        Long idABuscar=1001L;
        Optional<Municipio> actual = Optional.empty();
        try {
            actual = daoMunicipio.get(idABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoMunicipio");
        }
        Assertions.assertTrue(actual.isPresent());
        comparaMunicipio(datosEsperados.get(0),actual.get());
        calificacion += CALIF_OBTENER_EXISTENTE;
    }

    @Test
    @Order(2)
    public void testObtenMunicipioInexistente() throws Exception {
        DaoMunicipio daoMunicipio=new DaoMunicipio(conndbunit.getConnection());
        Long idABuscar=32100L;

        Optional<Municipio> actual = Optional.empty();
        try {
            actual = daoMunicipio.get(idABuscar);
            Assertions.assertFalse(actual.isPresent());
            actual = daoMunicipio.get("Nuevecita");
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoMunicipio");
        }

        Assertions.assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER_INEXISTENTE;
    }

    @Test
    @Order(3)
    public void testObtenMunicipioAll() throws Exception {
        DaoMunicipio daoMunicipio=new DaoMunicipio(conndbunit.getConnection());
        List<Municipio> actual;
        try {
            actual=daoMunicipio.getAll();
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo getAll de DaoMunicipio");
            actual=new ArrayList<>();
        }
        // Se asume que localmente la tabla municipio esta completa (2457 municipios)
        // pero en la de Github solo se insertaran 128
        Assertions.assertTrue(actual.size()==2457 || actual.size()==128);
        int numMunsAgs=11;
        // Se asume que en los datos que se usan para inicializar (datosescolar.xml)
        // solo estan los 11 municipios de Aguascalientes y los 58 de Zacatecas
        int posZac=actual.indexOf(new Municipio(32001))-numMunsAgs;
        int offset=0;
        for (int i=0; i<datosEsperados.size(); i++) {
            comparaMunicipio(datosEsperados.get(i), actual.get(i+offset));
            if (i>=10) {
                offset=posZac;
            }
        }
        calificacion += CALIF_OBTENER_ALL;
    }

    @Test
    @Order(4)
    public void testAgregarMunicipio() throws Exception {
        long id=32100L;
        String nom="SuperZacatecas";
        Municipio inst= new Municipio(id,nom);

        DaoMunicipio daoMunicipio=new DaoMunicipio(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoMunicipio.save(inst);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoMunicipio");
            resultado=true;
        }
        Assertions.assertFalse(resultado);


        ITable actualTable=conndbunit.createQueryTable("municipio",
                "SELECT * FROM municipio WHERE id_municipio<2000 OR id_municipio>32000");

        //IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosescolar.xml"));
        ITable expectedTable=expectedDataSet.getTable("municipio");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }


    @Test
    @Order(5)
    public void testUpdateMunicipio() throws Exception {
        long id=32001L;
        String nom="SuperZacatecas";
        Municipio inst= new Municipio(id,nom);

        DaoMunicipio daoMunicipio=new DaoMunicipio(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoMunicipio.update(inst);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoMunicipio");
            resultado=true;
        }
        Assertions.assertFalse(resultado);

        ITable actualTable=conndbunit.createQueryTable("municipio",
                "SELECT * FROM municipio WHERE id_municipio<2000 OR id_municipio>32000");

        //IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosescolar.xml"));
        ITable expectedTable=expectedDataSet.getTable("municipio");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }


    @Test
    @Order(6)
    public void testDeleteMunicipio() throws Exception {
        Long id=32001L;
        DaoMunicipio daoMunicipio=new DaoMunicipio(conndbunit.getConnection());
        try {
            boolean resultado=daoMunicipio.delete(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoMunicipio");
        }
        ITable actualTable=conndbunit.createQueryTable("municipio",
                "SELECT * FROM municipio WHERE id_municipio<2000 OR id_municipio>32000");

        //IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosescolar.xml"));
        ITable expectedTable=expectedDataSet.getTable("municipio");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }

}
