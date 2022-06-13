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
import poo2.progs.basedatos.DaoEntidad;
import poo2.progs.entidades.Entidad;
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
public class DaoEntidadTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static List<Entidad> datosEsperados;
    private static IDatabaseConnection conndbunit;
    private static int calificacion;
    private final static double CALIF_OBTENER_EXISTENTE=5;
    private final static double CALIF_OBTENER_INEXISTENTE=1;
    private final static int CALIF_OBTENER_ALL=5;
    private final static int CALIF_AGREGAR=1;
    private final static int CALIF_UPDATE=1;
    private final static int CALIF_DELETE=1;
    private final static int MAX_CALIF=14;
    private final static double PORCENTAJE_DAOENTIDAD=3.0;

    private static void iniciaDatosLista() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream("datosent.tsv"), StandardCharsets.UTF_8));
        String linea=in.readLine();
        datosEsperados=new ArrayList<>();
        while (linea!=null) {
            String[] p=linea.split("\t");
            Entidad ent=new Entidad(Long.parseLong(p[0]), p[1]);
            datosEsperados.add(ent);
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
        databaseTester.setSetUpOperation(DatabaseOperation.NONE);
        databaseTester.onSetup();
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.NONE);
        databaseTester.onTearDown();
        System.out.printf("Puntos para Pruebas DAOEntidad: %.2f/%.2f\n",calificacion*PORCENTAJE_DAOENTIDAD/MAX_CALIF,PORCENTAJE_DAOENTIDAD);
        TotalAcumulado.acumula(calificacion* PORCENTAJE_DAOENTIDAD /MAX_CALIF);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }


    private void comparaEntidad(Entidad expected,
                                Entidad actual) {
        Assertions.assertEquals(expected.getIdEntidad(),
                actual.getIdEntidad());
        Assertions.assertEquals(expected.getNombreEntidad(),
                actual.getNombreEntidad());
    }

    @Test
    @Order(1)
    public void testObtenEntidadExistente() throws Exception {
        DaoEntidad daoEntidad=new DaoEntidad(conndbunit.getConnection());
        Long idABuscar=1L;
        Optional<Entidad> actual = Optional.empty();
        try {
            actual = daoEntidad.get(idABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoEntidad");
        }
        Assertions.assertTrue(actual.isPresent());
        comparaEntidad(datosEsperados.get(0),actual.get());
        calificacion += CALIF_OBTENER_EXISTENTE;
    }

    @Test
    @Order(2)
    public void testObtenEntidadInexistente() throws Exception {
        DaoEntidad daoEntidad=new DaoEntidad(conndbunit.getConnection());
        Long idABuscar=100L;

        Optional<Entidad> actual = Optional.empty();
        try {
            actual = daoEntidad.get(idABuscar);
            Assertions.assertFalse(actual.isPresent());
            actual = daoEntidad.get("Nuevecita");
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoEntidad");
        }

        Assertions.assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER_INEXISTENTE;
    }

    @Test
    @Order(3)
    public void testObtenEntidadAll() throws Exception {
        DaoEntidad daoEntidad=new DaoEntidad(conndbunit.getConnection());
        List<Entidad> actual;
        try {
            actual=daoEntidad.getAll();
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo getAll de DaoEntidad");
            actual=new ArrayList<>();
        }
        Assertions.assertEquals(datosEsperados.size(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaEntidad(datosEsperados.get(i), actual.get(i));
        }
        calificacion += CALIF_OBTENER_ALL;
    }

    @Test
    @Order(4)
    public void testAgregarEntidad() throws Exception {
        long id=5L;
        String nom="SuperZacatecas";
        Entidad inst= new Entidad(id,nom);

        DaoEntidad daoEntidad=new DaoEntidad(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoEntidad.save(inst);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoEntidad");
            resultado=true;
        }
        Assertions.assertFalse(resultado);


        ITable actualTable=conndbunit.createQueryTable("entidad",
                "SELECT * FROM entidad");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("entidad.xml"));
        ITable expectedTable=expectedDataSet.getTable("entidad");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }


    @Test
    @Order(5)
    public void testUpdateEntidad() throws Exception {
        long id=5L;
        String nom="SuperZacatecas";
        Entidad inst= new Entidad(id,nom);

        DaoEntidad daoEntidad=new DaoEntidad(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoEntidad.update(inst);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoEntidad");
            resultado=true;
        }
        Assertions.assertFalse(resultado);

        ITable actualTable=conndbunit.createQueryTable("entidad",
                "SELECT * FROM entidad");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("entidad.xml"));
        ITable expectedTable=expectedDataSet.getTable("entidad");


        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }


    @Test
    @Order(6)
    public void testDeleteEntidad() throws Exception {
        long id=16L;
        DaoEntidad daoEntidad=new DaoEntidad(conndbunit.getConnection());
        try {
            boolean resultado=daoEntidad.delete(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoEntidad");
        }
        ITable actualTable=conndbunit.createQueryTable("entidad",
                "SELECT * FROM entidad");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("entidad.xml"));
        ITable expectedTable=expectedDataSet.getTable("entidad");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }
}
