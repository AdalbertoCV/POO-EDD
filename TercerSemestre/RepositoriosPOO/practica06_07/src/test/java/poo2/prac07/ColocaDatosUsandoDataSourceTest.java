package poo2.prac07;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dbunit.*;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.*;
import poo2.prac06_07.MainPractica06_07;

import java.io.File;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ColocaDatosUsandoDataSourceTest  extends TestCase  {
    private static IDatabaseTester databaseTester;
    private final static String driverName="com.mysql.cj.jdbc.Driver";
    private final static String url="jdbc:mysql://localhost/controlconcursos_ad2021";
    private final static String usuario = "IngSW";
    private final static String clave = "UAZsw2021";
    private final static String ubicacion="localhost";
    private final static String nombreBD="controlconcursos_ad2021";
    private static IDatabaseConnection conndbunit;
    private static String nomConcurso;
    private static String nomSede;
    private static int calificacion;
    private final static int MAX_CALIF=100;
    private final static int CALIF_CONSTRUCTOR=5;
    private final static  int CALIF_LLENA_TABLA_SEDE=50;
    private final static  int CALIF_LLENA_TABLA_CONCURSO=40;

    @BeforeAll
    public static void inicializa() throws Exception {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        String matricula = MainPractica06_07.matricula;
        databaseTester=new JdbcDatabaseTester(driverName,url,
                usuario,clave);
        databaseTester.setOperationListener(new ColocaDatosUsandoDataSourceTest.CustomConfigurationOperationListener());
        conndbunit=databaseTester.getConnection();
        DatabaseConfig config=conndbunit.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS,true);
        QueryDataSet dataSet = new QueryDataSet(conndbunit);
        nomConcurso="concurso_"+matricula;
        nomSede="sede_"+matricula;
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onSetup();
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.REFRESH);
        databaseTester.onTearDown();
        System.out.printf("Calificacion Test de ColocaDatosUsandoDataSource (Practica 07): %d/%d\n",calificacion,MAX_CALIF);
    }

    @Test
    @Order(1)
    public void testConstructorConDatosValidos()  throws AssertionFailedError {
        ColocaDatosUsandoDataSource creador=null;
        try {
            creador=new ColocaDatosUsandoDataSource(ubicacion,usuario,clave,nombreBD);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        Assertions.assertNotNull(creador,"Se deberia haber creado el objeto de clase ColocaDatosUsandoDataSource");
        Assertions.assertTrue(creador.isConnActive(),"La conexion del objeto ColocaDatosUsandoDataSource deberia estar activa");
        calificacion += CALIF_CONSTRUCTOR;
    }

    @Test
    @Order(2)
    public void testConstructorConDatosInvalidos()  throws AssertionFailedError {
        ColocaDatosUsandoDataSource creador=null;
        try {
            creador=new ColocaDatosUsandoDataSource(ubicacion,usuario,clave+clave,nombreBD);
        }
        catch (Exception ex) {
            //ex.printStackTrace();
        }
        Assertions.assertNull(creador,"No se deberia haber creado el objeto de clase ColocaDatosUsandoDataSource");
        calificacion += CALIF_CONSTRUCTOR;
    }

    @Test
    @Order(6)
    public void testTablaSede()  throws Exception {
        ColocaDatosUsandoDataSource creador=new ColocaDatosUsandoDataSource(ubicacion,usuario,clave,nombreBD);
        creador.llenaTablaSede(false);
        ITable actualTable=conndbunit.createQueryTable("sede",
                String.format("SELECT email_director_sede, id_institucion_sede, id_sede, nombre_sede FROM %s",nomSede));

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datoscontrolconcursos.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_LLENA_TABLA_SEDE;
    }

    @Test
    @Order(7)
    public void testTablaConcurso()  throws Exception {
        ColocaDatosUsandoDataSource creador=new ColocaDatosUsandoDataSource(ubicacion,usuario,clave,nombreBD);
        creador.llenaTablaConcurso(false);
        ITable actualTable=conndbunit.createQueryTable("concurso",
                String.format("SELECT * FROM %s",nomConcurso));

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datoscontrolconcursos.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_LLENA_TABLA_CONCURSO;
    }


    public static class CustomConfigurationOperationListener extends DefaultOperationListener implements IOperationListener {
        @Override
        public void connectionRetrieved(IDatabaseConnection iDatabaseConnection) {
            super.connectionRetrieved(iDatabaseConnection);
            iDatabaseConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
        }
    }
}
