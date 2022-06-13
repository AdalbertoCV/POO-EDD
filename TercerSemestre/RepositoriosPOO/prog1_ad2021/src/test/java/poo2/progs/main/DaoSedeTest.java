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
import poo2.progs.basedatos.DaoSede;
import poo2.progs.entidades.Sede;
import poo2.progs.interfaces.DaoException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DaoSedeTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static List<Sede> datosEsperados;
    private static IDatabaseConnection conndbunit;
    private static int calificacion;
    private final static int CALIF_OBTENER=5;
    private final static int CALIF_AGREGAR=10;
    private final static int CALIF_UPDATE=10;
    private final static int CALIF_DELETE=5;
    private final static int MAX_CALIF=90;
    private final static double PORCENTAJE_DAOSEDE = 9.0;

    private static void iniciaDatosLista() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream("datossede.tsv"), StandardCharsets.UTF_8));
        String linea=in.readLine();
        datosEsperados=new ArrayList<>();
        while (linea!=null) {
            String[] p=linea.split("\t");
            Sede sede=new Sede(Long.parseLong(p[0]),p[1],Long.parseLong(p[2]),p[3]);
            sede.setUrlSede(p[4]);
            datosEsperados.add(sede);
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
        System.out.printf("Puntos para Pruebas DAOSede: %.2f/%.2f\n",calificacion* PORCENTAJE_DAOSEDE /MAX_CALIF, PORCENTAJE_DAOSEDE);
        TotalAcumulado.acumula(calificacion* PORCENTAJE_DAOSEDE /MAX_CALIF);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }


    private void comparaSede(Sede expected,
                             Sede actual) {
        Assertions.assertEquals(expected.getIdSede(),
                actual.getIdSede());
        Assertions.assertEquals(expected.getNombreSede(),
                actual.getNombreSede());
        Assertions.assertEquals(expected.getIdInstitucionSede(),
                actual.getIdInstitucionSede());
        Assertions.assertEquals(expected.getEmailDirectorSede(),
                actual.getEmailDirectorSede());
        Assertions.assertEquals(expected.getUrlSede(),
                actual.getUrlSede());
    }

    @Test
    @Order(2)
    public void testObtenSedeExistente() throws Exception {
        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        long idABuscar=1;
        Optional<Sede> actual = Optional.empty();
        try {
            actual = daoSede.get(idABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoSede");
        }
        Assertions.assertTrue(actual.isPresent());
        comparaSede(datosEsperados.get(0),actual.get());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(3)
    public void testObtenSedeInexistente() throws Exception {
        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        long idABuscar=10;
        Optional<Sede> actual = Optional.empty();
        try {
            actual = daoSede.get(idABuscar);
            Assertions.assertFalse(actual.isPresent());
            actual = daoSede.get("Inexistente");
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoSede");
        }

        Assertions.assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(4)
    public void testObtenSedeAll() throws Exception {
        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        List<Sede> actual;
        try {
            actual=daoSede.getAll();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo getAll de DaoSede");
        }

        Assertions.assertEquals(datosEsperados.size(), actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaSede(datosEsperados.get(i), actual.get(i));
        }
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(5)
    public void testAgregarSedeValida() throws Exception {
        long id=3;
        String nom="UPIIZ Club";
        long idInst=2;
        String email="rodmtz@upiiz.edu.mx";
        String url="http://www.upiiz.edu.mx/icpc";
        Sede est = new Sede(id,nom,idInst,email);
        est.setUrlSede(url);

        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoSede.save(est);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoSede");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("sede",
                "SELECT * FROM sede");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sede_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(6)
    public void testAgregarSedeDuplicada() throws Exception {
        long id=3;
        String nom="UPIIZ Club";
        long idInst=2;
        String email="rodmtz@upiiz.edu.mx";
        String url="http://www.upiiz.edu.mx/icpc";
        Sede est = new Sede(id,nom,idInst,email);
        est.setUrlSede(url);

        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoSede.save(est);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoSede");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    private void validaNullo_Vacio(DaoSede daoSede, String valor, long idsede, boolean nuevo) {
        String nom="SEDE POTENCIA";
        String email="rsolis@uaz.edu.mx";
        long idinst=1;
        String url="http://inexistente.com";
        boolean resultado;

        Sede sede= new Sede(idsede,valor,idinst,email);
        sede.setUrlSede(url);

        resultado=nuevo?daoSede.save(sede):daoSede.update(sede);
        Assertions.assertFalse(resultado);

        sede.setNombreSede(nom);
        sede.setEmailDirectorSede(valor);
        resultado=nuevo?daoSede.save(sede):daoSede.update(sede);
        Assertions.assertFalse(resultado);

        sede.setEmailDirectorSede(email);
        sede.setUrlSede(valor);
        resultado=nuevo?daoSede.save(sede):daoSede.update(sede);
        Assertions.assertTrue(resultado);

        Connection conn;
        Statement stmt;
        String sql=String.format("DELETE FROM sede WHERE id_sede=%d",idsede);
        try {
            if (nuevo) {
                conn = conndbunit.getConnection();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            Assertions.assertNull(exsql, "Problema al eliminar la sede que se agrego en la prueba");
        }


    }

    @Test
    @Order(7)
    public void testAgregarSedeInvalida() throws Exception {
        String[] nombre={"Sede Poderosa","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String largo="ESTEVALORDEURLDEBERIADESERTOTALMENTEINVALIDOPUESESTALARGUISIMO";
        largo += largo; largo += largo; largo += largo; largo += largo; largo += largo; largo += largo;
        String[] url = {"http://www.uaz.edu.mx", largo};
        String[] email={"rsolis@uaz.edu.mx","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String emailEst="charly@uaz.edu.mx";
        long[] idInst={3,1};
        long idsede=10L;
        Sede sede = new Sede(idsede,nombre[1],idInst[0],email[0]);
        sede.setUrlSede(url[0]);

        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        boolean resultado;
        try {
            validaNullo_Vacio(daoSede,null,30,true);
            validaNullo_Vacio(daoSede,"",30, true);

            resultado=daoSede.save(sede);
            Assertions.assertFalse(resultado);

            sede.setNombreSede(nombre[0]);
            resultado=daoSede.save(sede);
            Assertions.assertFalse(resultado);

            sede.setEmailDirectorSede(email[1]);
            resultado=daoSede.save(sede);
            Assertions.assertFalse(resultado);

            sede.setEmailDirectorSede(emailEst);
            sede.setIdInstitucionSede(idInst[1]);
            resultado=daoSede.save(sede);
            Assertions.assertFalse(resultado);

            sede.setUrlSede(url[1]);
            resultado=daoSede.save(sede);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println("ERROR");
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoSede");

            resultado=true;
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(8)
    public void testUpdateSedeValida() throws Exception {
        long id=3;
        String nom="ICPC UPIIZ";
        long idInst=2;
        String email="alexacu@upiiz.edu.mx";
        String url="http://www.upiiz.ipn.edu.mx/icpc";
        Sede est = new Sede(id,nom,idInst,email);
        est.setUrlSede(url);

        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=daoSede.update(est);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoSede");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("sede",
                "SELECT * FROM sede WHERE id_sede=3");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sede_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(9)
    public void testUpdateSedeInexistente() throws Exception {
        long id=300L;
        String nom="ICPC UPIIZ";
        long idInst=2;
        String email="alexacu@upiiz.edu.mx";
        String url="http://www.upiiz.ipn.edu.mx/icpc";
        Sede est = new Sede(id,nom,idInst,email);
        est.setUrlSede(url);

        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=daoSede.update(est);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoSede");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(10)
    public void testUpdateSedeInvalida() throws Exception {
        String[] nombre={"Sede Poderosa","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String largo="ESTEVALORDEURLDEBERIADESERTOTALMENTEINVALIDOPUESESTALARGUISIMO";
        largo += largo; largo += largo; largo += largo; largo += largo; largo += largo; largo += largo;
        String[] url = {"http://www.uaz.edu.mx", largo};
        String[] email={"rsolis@uaz.edu.mx","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String emailEst="charly@uaz.edu.mx";
        long[] idInst={3,1};
        long idsede=3L;
        Sede sede = new Sede(idsede,nombre[1],idInst[0],email[0]);
        sede.setUrlSede(url[0]);

        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        boolean resultado;
        //resultado = true;
        try {
            validaNullo_Vacio(daoSede,null,3,false);
            validaNullo_Vacio(daoSede,"",3, false);


            resultado=daoSede.update(sede);
            Assertions.assertFalse(resultado);

            sede.setNombreSede(nombre[0]);
            resultado=daoSede.update(sede);
            Assertions.assertFalse(resultado);

            sede.setEmailDirectorSede(email[1]);
            resultado=daoSede.update(sede);
            Assertions.assertFalse(resultado);

            sede.setEmailDirectorSede(emailEst);
            sede.setIdInstitucionSede(idInst[1]);
            resultado=daoSede.update(sede);
            Assertions.assertFalse(resultado);

            sede.setUrlSede(url[1]);
            resultado=daoSede.update(sede);
            Assertions.assertFalse(resultado);

        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoSede");
            System.err.println(ex.getMessage());
            resultado=true;
        }

        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(11)
    public void testDeleteSedeInexistente() throws Exception {
        long id=350L;
        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        try {
            boolean resultado=daoSede.delete(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoSede");
        }
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(12)
    public void testDeleteSedeExistente() throws Exception {
        long id=3L;
        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        try {
            boolean resultado=daoSede.delete(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoSede");
        }

        ITable actualTable=conndbunit.createQueryTable("sede",
                "SELECT * FROM sede");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sede_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(13)
    public void testDeleteSedeInvalida() throws Exception {
        Long id=1L;
        DaoSede daoSede=new DaoSede(conndbunit.getConnection());
        try {
            boolean resultado=daoSede.delete(id);
            Assertions.assertFalse(resultado);
            resultado=daoSede.delete(null);
            Assertions.assertFalse(resultado);
            resultado=daoSede.delete("Inexistente");
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoSede");
        }
        calificacion += CALIF_DELETE;
    }

}
