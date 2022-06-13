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
import poo2.progs.basedatos.DaoEquiposSedeConcurso;
import poo2.progs.entidades.EquiposSedeConcurso;
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
public class DaoEquiposSedeConcursoTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static List<EquiposSedeConcurso> datosEsperados;
    private static IDatabaseConnection conndbunit;
    private static int calificacion;
    private final static int CALIF_OBTENER=5;
    private final static int CALIF_AGREGAR=10;
    private final static int CALIF_UPDATE=10;
    private final static int CALIF_DELETE=5;
    private final static int MAX_CALIF=90;
    private final static double PORCENTAJE_DAOEQUIPOSSEDECONCURSO = 4.0;

    private static void iniciaDatosLista() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream("datoseqsedeconc.tsv"), StandardCharsets.UTF_8));
        String linea=in.readLine();
        datosEsperados=new ArrayList<>();
        while (linea!=null) {
            String[] p=linea.split("\t");
            EquiposSedeConcurso sc=new EquiposSedeConcurso(Long.parseLong(p[0]),
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
        System.out.printf("Puntos para Pruebas DAOEquiposSedeConcurso: %.2f/%.2f\n",calificacion* PORCENTAJE_DAOEQUIPOSSEDECONCURSO /MAX_CALIF, PORCENTAJE_DAOEQUIPOSSEDECONCURSO);
        TotalAcumulado.acumula(calificacion* PORCENTAJE_DAOEQUIPOSSEDECONCURSO /MAX_CALIF);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }


    private void comparaEquiposSedeConcurso(EquiposSedeConcurso expected,
                                            EquiposSedeConcurso actual) {
        // llave primaria autoincrementable
        /*assertEquals(expected.getIdEquiposSedeConcurso(),
                actual.getIdEquiposSedeConcurso()); */
        Assertions.assertEquals(expected.getIdEquipo(),
                actual.getIdEquipo());
        Assertions.assertEquals(expected.getIdSedeConcurso(),
                actual.getIdSedeConcurso());
    }

    @Test
    @Order(2)
    public void testObtenEquiposSedeConcursoExistente() throws Exception {
        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        long idABuscar=1;
        Optional<EquiposSedeConcurso> actual = Optional.empty();
        try {
            actual = daoEquiposSedeConcurso.get(idABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoEquiposSedeConcurso");
        }
        Assertions.assertTrue(actual.isPresent());
        comparaEquiposSedeConcurso(datosEsperados.get(0),actual.get());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(3)
    public void testObtenEquiposSedeConcursoInexistente() throws Exception {
        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        long idABuscar=10;
        Optional<EquiposSedeConcurso> actual = Optional.empty();
        try {
            actual = daoEquiposSedeConcurso.get(idABuscar);
            Assertions.assertFalse(actual.isPresent());
            actual = daoEquiposSedeConcurso.get("Inexistente");
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoEquiposSedeConcurso");
        }

        Assertions.assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(4)
    public void testObtenEquiposSedeConcursoAll() throws Exception {
        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        List<EquiposSedeConcurso> actual;
        try {
            actual=daoEquiposSedeConcurso.getAll();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo getAll de DaoEquiposSedeConcurso");
        }
        Assertions.assertEquals(datosEsperados.size(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaEquiposSedeConcurso(datosEsperados.get(i), actual.get(i));
        }
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(5)
    public void testAgregarEquiposSedeConcursoValida() throws Exception {
        long id=2;
        long idequipo=1;
        long idsedeconcurso=2;

        EquiposSedeConcurso sc = new EquiposSedeConcurso(id, idequipo,idsedeconcurso);

        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoEquiposSedeConcurso.save(sc);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoEquiposSedeConcurso");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("equipos_sede_concurso",
                "SELECT id_equipo,id_sede_concurso FROM equipos_sede_concurso ORDER BY id_equipo_sede_concurso");



        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipos_sede_concurso_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipos_sede_concurso");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(6)
    public void testAgregarEquiposSedeConcursoDuplicada() throws Exception {
        long id=2;
        long idequipo=2;
        long idsedeconcurso=2;

        EquiposSedeConcurso sc = new EquiposSedeConcurso(id, idequipo,idsedeconcurso);

        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoEquiposSedeConcurso.save(sc);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoEquiposSedeConcurso");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }


    @Test
    @Order(7)
    public void testAgregarEquiposSedeConcursoInvalida() throws Exception {
        long id=2;
        long idequipovalido=1;
        long idequipoinvalido=5000;
        long idsedeconcursoval=1;
        long idsedeconcursoinval=1000;


        EquiposSedeConcurso sc= new EquiposSedeConcurso(id,idequipoinvalido,idsedeconcursoval);
        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=daoEquiposSedeConcurso.save(sc);
            Assertions.assertFalse(resultado);

            sc.setIdEquipo(idequipovalido);
            sc.setIdSedeConcurso(idsedeconcursoinval);
            resultado=daoEquiposSedeConcurso.save(sc);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println("ERROR");
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoEquiposSedeConcurso");
        }

        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(8)
    public void testUpdateEquiposSedeConcursoValida() throws Exception {
        long id=2;
        long idequipo=2;
        long idsedeconcurso=1;
        EquiposSedeConcurso sc = new EquiposSedeConcurso(id, idequipo,idsedeconcurso);

        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=daoEquiposSedeConcurso.update(sc);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoEquiposSedeConcurso");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("equipos_sede_concurso",
                "SELECT id_equipo,id_sede_concurso FROM equipos_sede_concurso WHERE id_equipo_sede_concurso>1");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipos_sede_concurso_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipos_sede_concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(9)
    public void testUpdateEquiposSedeConcursoInexistente() throws Exception {
        long id=305;
        long idequipo=2;
        long idsedeconcurso=1;
        EquiposSedeConcurso sc = new EquiposSedeConcurso(id, idequipo,idsedeconcurso);

        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=daoEquiposSedeConcurso.update(sc);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoEquiposSedeConcurso");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(10)
    public void testUpdateEquiposSedeConcursoInvalida() throws Exception {
        long id=2;
        long idequipovalido=1;
        long idequipoinvalido=5000;
        long idsedeconcursoval=1;
        long idsedeconcursoinval=1000;


        EquiposSedeConcurso sc= new EquiposSedeConcurso(id,idequipoinvalido,idsedeconcursoval);
        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=daoEquiposSedeConcurso.update(sc);
            Assertions.assertFalse(resultado);

            sc.setIdEquipo(idequipovalido);
            sc.setIdSedeConcurso(idsedeconcursoinval);
            resultado=daoEquiposSedeConcurso.update(sc);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println("ERROR");
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoEquiposSedeConcurso");
        }
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(11)
    public void testDeleteEquiposSedeConcursoInexistente() throws Exception {
        long id=350L;
        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        try {
            boolean resultado=daoEquiposSedeConcurso.delete(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoEquiposSedeConcurso");
        }
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(12)
    public void testDeleteEquiposSedeConcursoExistente() throws Exception {
        long id=2;
        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        try {
            boolean resultado=daoEquiposSedeConcurso.delete(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoEquiposSedeConcurso");
        }

        ITable actualTable=conndbunit.createQueryTable("equipos_sede_concurso",
                "SELECT id_equipo,id_sede_concurso FROM equipos_sede_concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipos_sede_concurso_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipos_sede_concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(13)
    public void testDeleteEquiposSedeConcursoInvalida() throws Exception {
        DaoEquiposSedeConcurso daoEquiposSedeConcurso=new DaoEquiposSedeConcurso(conndbunit.getConnection());
        try {
            boolean resultado=daoEquiposSedeConcurso.delete(null);
            Assertions.assertFalse(resultado);
            resultado=daoEquiposSedeConcurso.delete("Inexistente");
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoEquiposSedeConcurso");
        }
        calificacion += CALIF_DELETE;
    }
}
