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
import poo2.progs.basedatos.DaoConcursosImpl;
import poo2.progs.entidades.Institucion;
import poo2.progs.entidades.Municipio;
import poo2.progs.interfaces.DaoException;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DaoConcursosImplTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static IDatabaseConnection conndbunit;
    private static int calif_inst;
    private static int calif_municipio;

    private final static int CALIF_OBTENER = 5;
    private final static int CALIF_AGREGAR = 5;
    private final static int CALIF_UPDATE = 5;
    private final static int CALIF_DELETE = 5;
    private final static int MAX_CALIF_INST = 35;
    private final static int MAX_CALIF_MUNICIPIO=5;
    private final static double PORCENTAJE_INST = 10.0;
    private final static double PORCENTAJE_MUNICIPIO=20.0;

    @BeforeAll
    public static void inicializa() throws Exception {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        databaseTester=new JdbcDatabaseTester(ConfigAccesoBaseDatos.driverName,
                ConfigAccesoBaseDatos.url,
                ConfigAccesoBaseDatos.usuario,
                ConfigAccesoBaseDatos.clave);
        databaseTester.setOperationListener(new ConfigAccesoBaseDatos.CustomConfigurationOperationListener());        conndbunit = databaseTester.getConnection();
        DatabaseConfig config = conndbunit.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("concursosv3.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.REFRESH);
        databaseTester.onTearDown();
        double calif_total=0.0;
        if (calif_inst>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (Institucion): %.2f/%.2f\n", calif_inst * PORCENTAJE_INST / MAX_CALIF_INST, PORCENTAJE_INST);
            calif_total+=calif_inst * PORCENTAJE_INST / MAX_CALIF_INST;
        }
        if (calif_municipio>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (obtenMunicipios): %.2f/%.2f\n", calif_municipio * PORCENTAJE_MUNICIPIO / MAX_CALIF_MUNICIPIO, PORCENTAJE_MUNICIPIO);
            calif_total+=calif_municipio * PORCENTAJE_MUNICIPIO / MAX_CALIF_MUNICIPIO;
        }
        System.out.printf("Puntos TOTALES para Pruebas DAOConcursoImpl: %.2f/%.2f\n", calif_total, 30.0);
        TotalAcumulado.acumula(calif_total);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }

    /// INSTITUCION

    private void comparaInst(Institucion actual, ITable expected, int numrow) {
        try {
            Assertions.assertEquals(expected.getValue(numrow, "id_institucion").toString(),String.valueOf(actual.getIdInstitucion()));
            Assertions.assertEquals(expected.getValue(numrow, "nombre_institucion"),actual.getNombreInstitucion());
            Assertions.assertEquals(expected.getValue(numrow, "nombre_corto_institucion"),actual.getNombreCortoInstitucion());
            Assertions.assertEquals(expected.getValue(numrow, "url_institucion"),actual.getUrlInstitucion());
            Assertions.assertEquals(expected.getValue(numrow, "calle_num_institucion"),actual.getCalleNumInstitucion());
            Assertions.assertEquals(expected.getValue(numrow, "colonia_institucion"),actual.getColoniaInstitucion());
            Assertions.assertEquals(expected.getValue(numrow, "id_municipio_institucion").toString(),String.valueOf(actual.getIdMunicipioInstitucion()));
            Assertions.assertEquals(expected.getValue(numrow, "id_entidad_institucion").toString(),String.valueOf(actual.getIdEntidadInstitucion()));
            Assertions.assertEquals(expected.getValue(numrow, "codpostal_institucion"),actual.getCodpostalInstitucion());
            Assertions.assertEquals(expected.getValue(numrow, "telefono_institucion"),actual.getTelefonoInstitucion());
        } catch (Exception ex) {
            //ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar las instituciones");
        }
    }

    @Test
    @Order(1)
    public void testInstObten() throws Exception {
        DaoConcursosImpl dao = new DaoConcursosImpl(conndbunit.getConnection());
        List<Institucion> actual;
        try {
            actual = dao.obtenInstituciones();
        } catch (DaoException ex) {
            actual = new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenInstituciones de DaoConcursosImpl");
        }
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable = expectedDataSet.getTable("institucion");

        Assertions.assertEquals( expectedTable.getRowCount(),actual.size());
        for (int i = 0; i < actual.size(); i++) {
            comparaInst(actual.get(i), expectedTable, i);
        }
        calif_inst += CALIF_OBTENER;
    }

    @Test
    @Order(2)
    public void testInstAgregar() throws Exception {
        long id = 5;
        String nom = "Universidad Tecnologica Estado de Zacatecas";
        String nomcorto = "UTZAC";
        String url = "http://www.utzac.edu.mx";
        String calle = "Carretera Zacatecas, Cd Cuauhtemoc km. 5";
        String colonia = "Ejido Cieneguitas";
        long idmun = 32017;
        long ident = 32;
        String codpostal = "98601";
        String tel = "4929276180";
        Institucion inst = new Institucion(id, nom, nomcorto, url,
                calle, idmun, ident);
        inst.setColoniaInstitucion(colonia);
        inst.setCodpostalInstitucion(codpostal);
        inst.setTelefonoInstitucion(tel);

        DaoConcursosImpl dao = new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.agregaInstitucion(inst);
        } catch (DaoException ex) {
            resultado = false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaInstitucion de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable = conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion WHERE id_institucion>4");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("inst_add.xml"));
        ITable expectedTable = expectedDataSet.getTable("institucion");

        Assertion.assertEquals(expectedTable, actualTable);
        calif_inst += CALIF_AGREGAR;
    }

    @Test
    @Order(3)
    public void testInstAgregarDuplicada() throws Exception {
        long id = 5;
        String nom = "Universidad Tecnologica Estado de Zacatecas";
        String nomcorto = "UTZAC";
        String url = "http://www.utzac.edu.mx";
        String calle = "Carretera Zacatecas, Cd Cuauhtemoc km. 5";
        String colonia = "Ejido Cieneguitas";
        long idmun = 32017;
        long ident = 32;
        String codpostal = "98601";
        String tel = "4929276180";

        Institucion inst = new Institucion(id, nom, nomcorto, url,
                calle, idmun, ident);
        inst.setColoniaInstitucion(colonia);
        inst.setCodpostalInstitucion(codpostal);
        inst.setTelefonoInstitucion(tel);

        DaoConcursosImpl dao = new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.agregaInstitucion(inst);
        } catch (Exception ex) {
            resultado = false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaInstitucion de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_inst += CALIF_AGREGAR;
    }


    @Test
    @Order(4)
    public void testInstActualizar() throws Exception {
        long id = 5;
        String nom = "Universidad Tecnologica de Aguascalientes";
        String nomcorto = "UTAGS";
        String url = "http://www.utags.edu.mx";
        String calle = "Blvd. Juan Pablo II 1302";
        String colonia = "Canteras de San Agustin";
        long idmun = 1001;
        long ident = 1;
        String codpostal = "20200";
        String tel = "4499105000";
        Institucion inst = new Institucion(id, nom, nomcorto, url,
                calle, idmun, ident);
        inst.setColoniaInstitucion(colonia);
        inst.setCodpostalInstitucion(codpostal);
        inst.setTelefonoInstitucion(tel);

        DaoConcursosImpl dao = new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado = false;
        try {
            resultado = dao.actualizaInstitucion(inst);
        } catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaInstitucion de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable = conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion WHERE id_institucion>4");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("instcompleto_upd.xml"));
        ITable expectedTable = expectedDataSet.getTable("institucion");
        Assertion.assertEquals(expectedTable, actualTable);
        calif_inst += CALIF_UPDATE;
    }

    @Test
    @Order(5)
    public void testInstActualizarInexistente() throws Exception {
        long id = 16;
        String nom = "Universidad Tecnologica de Aguascalientes";
        String nomcorto = "UTAGS";
        String url = "http://www.utags.edu.mx";
        String calle = "Blvd. Juan Pablo II 1302";
        String colonia = "Canteras de San Agustin";
        long idmun = 1001;
        long ident = 1;
        String codpostal = "20200";
        String tel = "4499105000";
        Institucion inst = new Institucion(id, nom, nomcorto, url,
                calle, idmun, ident);
        inst.setColoniaInstitucion(colonia);
        inst.setCodpostalInstitucion(codpostal);
        inst.setTelefonoInstitucion(tel);

        DaoConcursosImpl dao = new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado = true;
        try {
            resultado = dao.actualizaInstitucion(inst);
        } catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaInstitucion de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_inst += CALIF_UPDATE;
    }

    @Test
    @Order(6)
    public void testInstEliminar() throws Exception {
        long id = 5;
        DaoConcursosImpl dao = new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado = dao.eliminaInstitucion(id);
            Assertions.assertTrue(resultado);
        } catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaInstitucion de DaoConcursosImpl");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable = conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("inst_del.xml"));
        ITable expectedTable = expectedDataSet.getTable("institucion");
        Assertion.assertEquals(expectedTable, actualTable);
        calif_inst += CALIF_DELETE;
    }


    @Test
    @Order(7)
    public void testInstEliminarInexistente() throws Exception {
        long id = 16L;
        DaoConcursosImpl dao = new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado = dao.eliminaInstitucion(id);
            Assertions.assertFalse(resultado);
        } catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaInstitucion de DaoConcursosImpl");
        }
        calif_inst += CALIF_DELETE;
    }

    /// MUNICIPIO

    private void comparaMunicipio(Municipio actual, ITable expected, int numrow) {
        try {
            Assertions.assertEquals(expected.getValue(numrow, "id_municipio").toString(),String.valueOf(actual.getIdMunicipio()));
            Assertions.assertEquals(expected.getValue(numrow, "nombre_municipio"),actual.getNombreMunicipio());
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar los municipios");
        }
    }

    // METODOS ESPECIALES
    @Test
    @Order(8)
    public void testMunicipioObten() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<Municipio> actual;
        long id=32;
        try {
            actual=dao.obtenMunicipios(id);
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenMunicipios de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("municipio32.xml"));
        ITable expectedTable=expectedDataSet.getTable("municipio");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaMunicipio(actual.get(i), expectedTable,i);
        }

        id=1;
        try {
            actual=dao.obtenMunicipios(id);
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenMunicipios de DaoConcursosImpl");
        }
        expectedDataSet=new FlatXmlDataSetBuilder().build(new File("municipio1.xml"));
        expectedTable=expectedDataSet.getTable("municipio");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaMunicipio(actual.get(i), expectedTable,i);
        }
        calif_municipio += CALIF_OBTENER;
    }
}