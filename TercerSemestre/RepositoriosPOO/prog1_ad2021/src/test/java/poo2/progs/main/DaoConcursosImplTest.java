package poo2.progs.main;

import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.*;
import poo2.ConfigAccesoBaseDatos;
import poo2.progs.basedatos.DaoConcursosImpl;
import poo2.progs.entidades.*;
import poo2.progs.interfaces.DaoException;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DaoConcursosImplTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static IDatabaseConnection conndbunit;
    private static int calif_inst;
    private static int calif_persona;
    private static int calif_datos_estudiante;
    private static int calif_municipio;
    private static int calif_entidad;
    private static int calif_sede;
    private static int calif_equipo;
    private static int calif_concurso;
    private static int calif_sede_concurso;
    private static int calif_equipos_sede_concurso;
    private static int calif_emails;
    private static int calif_sedesdisp;
    private static int calif_sedesasig;
    private static int calif_equiposdisp;
    private static int calif_equiposreg;


    private final static int CALIF_OBTENER=5;
    private final static int CALIF_AGREGAR=5;
    private final static int CALIF_UPDATE=5;
    private final static int CALIF_DELETE=5;
    private final static int MAX_CALIF_INST=35;
    private final static int MAX_CALIF_ENTIDAD=5;
    private final static int MAX_CALIF_MUNICIPIO=5;
    private final static int MAX_CALIF_PERSONA=35;
    private final static int MAX_CALIF_DATOSESTUDIANTE=40;
    private final static int MAX_CALIF_SEDE=35;
    private final static int MAX_CALIF_CONCURSO=35;
    private final static int MAX_CALIF_EQUIPO=35;
    private final static int MAX_CALIF_SEDECONCURSO=20;
    private final static int MAX_CALIF_EQUIPOSEDECONCURSO=20;
    private final static double PORCENTAJE_INST=0.25;
    private final static double PORCENTAJE_ENTIDAD=0.25;
    private final static double PORCENTAJE_PERSONA=0.5;
    private final static double PORCENTAJE_DATOSESTUDIANTE=0.5;
    private final static double PORCENTAJE_SEDE=0.5;
    private final static double PORCENTAJE_CONCURSO=0.5;
    private final static double PORCENTAJE_EQUIPO=0.5;
    private final static double PORCENTAJE_SEDECONCURSO=0.5;
    private final static double PORCENTAJE_EQUIPOSEDECONCURSO=0.5;
    private final static double PORCENTAJE_MUNICIPIO=2.0;
    private final static double PORCENTAJE_EMAILS=2.0;
    private final static double PORCENTAJE_SEDESDISP=5.0;
    private final static double PORCENTAJE_SEDESASIG=5.0;
    private final static double PORCENTAJE_EQUIPOSDISP=7.0;
    private final static double PORCENTAJE_EQUIPOSREG=5.0;
    private final static double MAX_CALIF_METODOS_ESP=5;

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
        if (calif_entidad>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (Entidad): %.2f/%.2f\n", calif_entidad * PORCENTAJE_INST / MAX_CALIF_ENTIDAD, PORCENTAJE_ENTIDAD);
            calif_total+=calif_entidad * PORCENTAJE_INST / MAX_CALIF_ENTIDAD;
        }
        if (calif_persona>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (Persona): %.2f/%.2f\n", calif_persona * PORCENTAJE_PERSONA / MAX_CALIF_PERSONA, PORCENTAJE_PERSONA);
            calif_total+=calif_persona * PORCENTAJE_PERSONA / MAX_CALIF_PERSONA;
        }
        if (calif_datos_estudiante>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (DatosEstudiante): %.2f/%.2f\n", calif_datos_estudiante * PORCENTAJE_DATOSESTUDIANTE / MAX_CALIF_DATOSESTUDIANTE, PORCENTAJE_DATOSESTUDIANTE);
            calif_total+=calif_datos_estudiante * PORCENTAJE_DATOSESTUDIANTE / MAX_CALIF_DATOSESTUDIANTE;
        }
        if (calif_sede>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (Sede): %.2f/%.2f\n", calif_sede * PORCENTAJE_SEDE / MAX_CALIF_SEDE, PORCENTAJE_SEDE);
            calif_total+=calif_sede * PORCENTAJE_SEDE / MAX_CALIF_SEDE;
        }
        if (calif_concurso>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (Concurso): %.2f/%.2f\n", calif_concurso * PORCENTAJE_CONCURSO / MAX_CALIF_CONCURSO, PORCENTAJE_CONCURSO);
            calif_total+=calif_concurso * PORCENTAJE_CONCURSO / MAX_CALIF_CONCURSO;
        }
        if (calif_equipo>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (Equipo): %.2f/%.2f\n", calif_equipo * PORCENTAJE_EQUIPO / MAX_CALIF_EQUIPO, PORCENTAJE_EQUIPO);
            calif_total+=calif_equipo * PORCENTAJE_EQUIPO / MAX_CALIF_EQUIPO;
        }
        if (calif_sede_concurso>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (SedeConcurso): %.2f/%.2f\n", calif_sede_concurso * PORCENTAJE_SEDECONCURSO / MAX_CALIF_SEDECONCURSO, PORCENTAJE_SEDECONCURSO);
            calif_total+=calif_sede_concurso * PORCENTAJE_SEDECONCURSO / MAX_CALIF_SEDECONCURSO;
        }
        if (calif_equipos_sede_concurso>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (EquiposSedeConcurso): %.2f/%.2f\n", calif_equipos_sede_concurso * PORCENTAJE_EQUIPOSEDECONCURSO / MAX_CALIF_EQUIPOSEDECONCURSO, PORCENTAJE_EQUIPOSEDECONCURSO);
            calif_total+=calif_equipos_sede_concurso * PORCENTAJE_EQUIPOSEDECONCURSO / MAX_CALIF_EQUIPOSEDECONCURSO;
        }
        if (calif_municipio>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (obtenMunicipios): %.2f/%.2f\n", calif_municipio * PORCENTAJE_MUNICIPIO / MAX_CALIF_MUNICIPIO, PORCENTAJE_MUNICIPIO);
            calif_total+=calif_municipio * PORCENTAJE_MUNICIPIO / MAX_CALIF_MUNICIPIO;
        }
        if (calif_emails>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (obtenCorreosDeInstitucion): %.2f/%.2f\n", calif_emails * PORCENTAJE_EMAILS / MAX_CALIF_METODOS_ESP, PORCENTAJE_EMAILS);
            calif_total+=calif_emails * PORCENTAJE_EMAILS / MAX_CALIF_METODOS_ESP;
        }
        if (calif_sedesdisp>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (obtenSedesDisponibles): %.2f/%.2f\n", calif_sedesdisp * PORCENTAJE_SEDESDISP / MAX_CALIF_METODOS_ESP, PORCENTAJE_SEDESDISP);
            calif_total+=calif_sedesdisp * PORCENTAJE_SEDESDISP / MAX_CALIF_METODOS_ESP;
        }
        if (calif_sedesasig>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (obtenSedesAsignadas): %.2f/%.2f\n", calif_sedesasig * PORCENTAJE_SEDESASIG / MAX_CALIF_METODOS_ESP, PORCENTAJE_SEDESASIG);
            calif_total+=calif_sedesasig * PORCENTAJE_SEDESASIG / MAX_CALIF_METODOS_ESP;
        }
        if (calif_equiposdisp>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (obtenEquiposDisponibles): %.2f/%.2f\n", calif_equiposdisp * PORCENTAJE_EQUIPOSDISP / MAX_CALIF_METODOS_ESP, PORCENTAJE_EQUIPOSDISP);
            calif_total+=calif_equiposdisp * PORCENTAJE_EQUIPOSDISP / MAX_CALIF_METODOS_ESP;
        }
        if (calif_equiposreg>0) {
            System.out.printf("Puntos para Pruebas DAOConcursoImpl (obtenEquiposRegistrados): %.2f/%.2f\n", calif_equiposreg * PORCENTAJE_EQUIPOSREG / MAX_CALIF_METODOS_ESP, PORCENTAJE_EQUIPOSREG);
            calif_total+=calif_equiposreg * PORCENTAJE_EQUIPOSREG / MAX_CALIF_METODOS_ESP;
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

    ///   ENTIDAD

    private void comparaEntidad(Entidad actual, ITable expected, int numrow) {
        try {
            Assertions.assertEquals(expected.getValue(numrow, "id_entidad").toString(),String.valueOf(actual.getIdEntidad()));
            Assertions.assertEquals(expected.getValue(numrow, "nombre_entidad"),actual.getNombreEntidad());
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar las entidades");
        }
    }

    @Test
    @Order(11)
    public void testEntidadObten() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<Entidad> actual;
        try {
            actual=dao.obtenEntidades();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenEntidades de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("entidad.xml"));
        ITable expectedTable=expectedDataSet.getTable("entidad");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaEntidad(actual.get(i), expectedTable,i);
        }
        calif_entidad += CALIF_OBTENER;
    }

    // PERSONA

    private void comparaPersona(Persona actual, ITable expected, int numrow) {
        try {
            Assertions.assertEquals(expected.getValue(numrow, "email_persona"),actual.getEmailPersona());
            Assertions.assertEquals(expected.getValue(numrow, "nombre_persona"),actual.getNombrePersona());
            Assertions.assertEquals(expected.getValue(numrow, "apellidos_persona"),actual.getApellidosPersona());
            Assertions.assertEquals(expected.getValue(numrow, "sexo_persona"),actual.getSexoPersona());
            Assertions.assertEquals(expected.getValue(numrow, "calle_num_persona"),actual.getCalleNumPersona());
            Assertions.assertEquals(expected.getValue(numrow, "colonia_persona"),actual.getColoniaPersona());
            Assertions.assertEquals(expected.getValue(numrow, "id_municipio_persona").toString(),String.valueOf(actual.getIdMunicipioPersona()));
            Assertions.assertEquals(expected.getValue(numrow, "id_entidad_persona").toString(),String.valueOf(actual.getIdEntidadPersona()));
            Assertions.assertEquals(expected.getValue(numrow, "codpostal_persona"),actual.getCodpostalPersona());
            Assertions.assertEquals(expected.getValue(numrow, "telefono_persona"),actual.getTelefonoPersona());
            Assertions.assertEquals(expected.getValue(numrow, "fecha_nac_persona"),actual.getFechaNacPersona().toString());
            Assertions.assertEquals(expected.getValue(numrow, "id_institucion_persona").toString(),String.valueOf(actual.getIdInstitucionPersona()));
            Assertions.assertEquals(expected.getValue(numrow, "tipo_persona"),actual.getTipoPersona());
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar las personas");
        }
    }

    @Test
    @Order(21)
    public void testPersonaObten() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<Persona> actual;
        try {
            actual=dao.obtenPersonas();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenPersonas de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaPersona(actual.get(i), expectedTable,i);
        }
        calif_persona += CALIF_OBTENER;
    }

    @Test
    @Order(22)
    public void testPersonaAgregar() throws Exception {
        String email="misalas@itszn.edu.mx";
        String nom="Miguel";
        String apellidos="Salas Guzman";
        String sexo="M";
        String calle="Herreros 25";
        String colonia="Centro";
        long idmun=32042L;
        long ident=32L;
        String codpostal="99104";
        String tel="4331234567";
        Date fecha=Date.valueOf("2001-10-13");
        long idinst=3L;
        String tipo="Estudiante";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.agregaPersona(per);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaPersona de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM persona");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("personacompleto_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_persona += CALIF_AGREGAR;
    }

    @Test
    @Order(23)
    public void testPersonaAgregarDuplicada() throws Exception {
        String email="misalas@itszn.edu.mx";
        String nom="Miguel";
        String apellidos="Salas Guzman";
        String sexo="M";
        String calle="Herreros 25";
        String colonia="Centro";
        long idmun=32042L;
        long ident=32L;
        String codpostal="99104";
        String tel="4331234567";
        Date fecha=Date.valueOf("2001-10-13");
        long idinst=3L;
        String tipo="Estudiante";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=dao.agregaPersona(per);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaPersona de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_persona += CALIF_AGREGAR;
    }


    @Test
    @Order(24)
    public void testPersonaActualizar() throws Exception {
        String email="misalas@itszn.edu.mx";
        String nom="Maria";
        String apellidos="Guzman Salas";
        String sexo="F";
        String calle="Juarez 215";
        String colonia="Las Flores";
        long idmun=1001L;
        long ident=1L;
        String codpostal="91010";
        String tel="4911234567";
        Date fecha=Date.valueOf("2001-10-14");
        long idinst=1L;
        String tipo="Profesor";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=dao.actualizaPersona(per);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaPersona de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("persona",
                "SELECT * FROM persona WHERE email_persona='misalas@itszn.edu.mx'");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("persona_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_persona += CALIF_UPDATE;
    }

    @Test
    @Order(25)
    public void testPersonaActualizarInexistente() throws Exception {
        String email="xmisalas@itszn.edu.mx";
        String nom="Maria";
        String apellidos="Guzman Salas";
        String sexo="F";
        String calle="Juarez 215";
        String colonia="Las Flores";
        long idmun=1001L;
        long ident=1L;
        String codpostal="91010";
        String tel="4911234567";
        Date fecha=Date.valueOf("2001-10-14");
        long idinst=1L;
        String tipo="Profesor";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=dao.actualizaPersona(per);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaPersona de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_persona += CALIF_UPDATE;
    }

    @Test
    @Order(26)
    public void testPersonaEliminar() throws Exception {
        String id="misalas@itszn.edu.mx";
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaPersona(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaPersona de DaoConcursosImpl");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createQueryTable("persona",
                "SELECT * FROM persona");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_persona += CALIF_DELETE;
    }


    @Test
    @Order(27)
    public void testPersonaEliminaInexistente() throws Exception {
        String id="xmisalas@itszn.edu.mx";
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaPersona(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaPersona de DaoConcursosImpl");
        }
        calif_persona += CALIF_DELETE;
    }

    // DATOSESTUDIANTE

    private void comparaDatosEstudiante(DatosEstudiante actual, ITable expected) {
        try {
            Assertions.assertEquals(expected.getValue(0, "email_estudiante"),actual.getEmailEstudiante());
            Assertions.assertEquals(expected.getValue(0, "carrera_estudiante"),actual.getCarreraEstudiante());
            Assertions.assertEquals(expected.getValue(0, "fecha_inicio_carrera"),actual.getFechaInicioCarrera().toString());
            Assertions.assertEquals(expected.getValue(0, "fecha_esperada_terminacion"),actual.getFechaEsperadaTerminacion().toString());
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar los datosestudiante");
        }
    }

    @Test
    @Order(31)
    public void testDatosEstudianteObten() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        String emailABuscar="charly@uaz.edu.mx";
        Optional<DatosEstudiante> actual = Optional.empty();
        try {
            actual=dao.obtenDatosEstudiante(emailABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenDatosEstudiante de DaoConcursosImpl");
        }
        Assertions.assertTrue(actual.isPresent());

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosestudiante_get.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");

        comparaDatosEstudiante(actual.get(),expectedTable);
        calif_datos_estudiante += CALIF_OBTENER;
    }

    @Test
    @Order(32)
    public void testDatosEstudianteObtenInexistente() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        String emailABuscar="charlydaniel@uaz.edu.mx";
        Optional<DatosEstudiante> actual = Optional.empty();
        try {
            actual=dao.obtenDatosEstudiante(emailABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenDatosEstudiante de DaoConcursosImpl");
        }
        Assertions.assertFalse(actual.isPresent());
        calif_datos_estudiante += CALIF_OBTENER;
    }

    @Test
    @Order(33)
    public void testDatosEstudianteAgregar() throws Exception {
        String email="newstudent@outlook.com";
        String carrera="Ing. en Informatica";
        Date fechaInicio=Date.valueOf("2019-08-12");
        Date fechaFin=Date.valueOf("2023-07-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.agregaDatosEstudiante(est);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaDatosEstudiante de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable=conndbunit.createQueryTable("datos_estudiante",
                "SELECT * FROM datos_estudiante WHERE email_estudiante='newstudent@outlook.com'");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosestudiantecomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_datos_estudiante += CALIF_AGREGAR;
    }

    @Test
    @Order(34)
    public void testDatosEstudianteAgregarDuplicada() throws Exception {
        String email="newstudent@outlook.com";
        String carrera="Ing. en Informatica";
        Date fechaInicio=Date.valueOf("2019-08-12");
        Date fechaFin=Date.valueOf("2023-07-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=dao.agregaDatosEstudiante(est);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaDatosEstudiante de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_datos_estudiante += CALIF_AGREGAR;
    }


    @Test
    @Order(35)
    public void testDatosEstudianteActualizar() throws Exception {
        String email="newstudent@outlook.com";
        String carrera="Ing. en Informatica Admva";
        Date fechaInicio=Date.valueOf("2019-01-28");
        Date fechaFin=Date.valueOf("2023-02-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=dao.actualizaDatosEstudiante(est);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaDatosEstudiante de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("datos_estudiante",
                "SELECT * FROM datos_estudiante WHERE email_estudiante='newstudent@outlook.com'");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosestudiantecomp_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_datos_estudiante += CALIF_UPDATE;
    }

    @Test
    @Order(36)
    public void testDatosEstudianteActualizarInexistente() throws Exception {
        String email="ulgaja@itszn.edu.mx";
        String carrera="Ing. en Informatica Admva";
        Date fechaInicio=Date.valueOf("2019-01-28");
        Date fechaFin=Date.valueOf("2023-02-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=dao.actualizaDatosEstudiante(est);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaDatosEstudiante de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_datos_estudiante += CALIF_UPDATE;
    }

    @Test
    @Order(37)
    public void testDatosEstudianteEliminar() throws Exception {
        String id="newstudent@outlook.com";
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaDatosEstudiante(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaDatosEstudiante de DaoConcursosImpl");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createQueryTable("datos_estudiante",
                "SELECT * FROM datos_estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_datos_estudiante += CALIF_DELETE;
    }


    @Test
    @Order(38)
    public void testDatosEstudianteEliminaInexistente() throws Exception {
        String id="xmisalas@itszn.edu.mx";
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaDatosEstudiante(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaDatosEstudiante de DaoConcursosImpl");
        }
        calif_datos_estudiante += CALIF_DELETE;
    }

    /// SEDE

    private void comparaSede(Sede actual, ITable expected, int numrow) {
        try {
            Assertions.assertEquals(expected.getValue(numrow, "id_sede").toString(),String.valueOf(actual.getIdSede()));
            Assertions.assertEquals(expected.getValue(numrow, "nombre_sede"),actual.getNombreSede());
            Assertions.assertEquals(expected.getValue(numrow, "id_institucion_sede").toString(),String.valueOf(actual.getIdInstitucionSede()));
            Assertions.assertEquals(expected.getValue(numrow, "email_director_sede"),actual.getEmailDirectorSede());
            Assertions.assertEquals(expected.getValue(numrow, "url_sede"),actual.getUrlSede());
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar las sedes");
        }
    }

    @Test
    @Order(41)
    public void testSedeObten() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<Sede> actual;
        try {
            actual=dao.obtenSedes();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenSedes de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaSede(actual.get(i), expectedTable,i);
        }
        calif_sede += CALIF_OBTENER;
    }

    @Test
    @Order(42)
    public void testSedeAgregar() throws Exception {
        long id=5;
        String nom="UPIIZ Club 2";
        long idInst=2;
        String email="alexac@upiiz.ipn.mx";
        String url="http://www.upiiz.edu.mx/icpc2";
        Sede sede = new Sede(id,nom,idInst,email);
        sede.setUrlSede(url);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.agregaSede(sede);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaSede de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable=conndbunit.createQueryTable("sede",
                "SELECT * FROM sede");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sedecomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_sede += CALIF_AGREGAR;
    }

    @Test
    @Order(43)
    public void testSedeAgregarDuplicada() throws Exception {
        long id=5;
        String nom="UPIIZ Club 2";
        long idInst=2;
        String email="alexac@upiiz.ipn.mx";
        String url="http://www.upiiz.edu.mx/icpc2";
        Sede sede = new Sede(id,nom,idInst,email);
        sede.setUrlSede(url);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=dao.agregaSede(sede);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaSede de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_sede += CALIF_AGREGAR;
    }


    @Test
    @Order(44)
    public void testSedeActualizar() throws Exception {
        long id=5;
        String nom="ICPC UPIIZ 2";
        long idInst=2;
        String email="alexac@upiiz.ipn.mx";
        String url="http://www.upiiz.ipn.edu.mx/icpc2";
        Sede sede = new Sede(id,nom,idInst,email);
        sede.setUrlSede(url);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=dao.actualizaSede(sede);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaSede de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("sede",
                "SELECT * FROM sede WHERE id_sede>4");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sedecomp_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_sede += CALIF_UPDATE;
    }

    @Test
    @Order(45)
    public void testSedeActualizarInexistente() throws Exception {
        long id=7;
        String nom="ICPC UPIIZ 2";
        long idInst=2;
        String email="alexac@upiiz.ipn.mx";
        String url="http://www.upiiz.ipn.edu.mx/icpc2";
        Sede sede = new Sede(id,nom,idInst,email);
        sede.setUrlSede(url);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=dao.actualizaSede(sede);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaSede de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_sede += CALIF_UPDATE;
    }

    @Test
    @Order(46)
    public void testSedeEliminar() throws Exception {
        long id=5;
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaSede(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaSede de DaoConcursosImpl");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createQueryTable("sede",
                "SELECT * FROM sede");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_sede += CALIF_DELETE;
    }


    @Test
    @Order(47)
    public void testSedeEliminarInexistente() throws Exception {
        long id=16L;
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaSede(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaSede de DaoConcursosImpl");
        }
        calif_sede += CALIF_DELETE;
    }

    /// CONCURSO

    private void comparaConcurso(Concurso actual, ITable expected, int numrow) {
        try {
            Assertions.assertEquals(expected.getValue(numrow, "id_concurso").toString(),String.valueOf(actual.getIdConcurso()));
            Assertions.assertEquals(expected.getValue(numrow, "nombre_concurso"),actual.getNombreConcurso());
            Assertions.assertEquals(expected.getValue(numrow, "fecha_concurso"),actual.getFechaConcurso().toString());
            Assertions.assertEquals(expected.getValue(numrow, "fecha_inicio_registro"),actual.getFechaInicioRegistro().toString());
            Assertions.assertEquals(expected.getValue(numrow, "fecha_fin_registro"),actual.getFechaFinRegistro().toString());
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar los concursos");
        }
    }

    @Test
    @Order(51)
    public void testConcursoObten() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<Concurso> actual;
        try {
            actual=dao.obtenConcursos();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenConcursos de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaConcurso(actual.get(i), expectedTable,i);
        }
        calif_concurso += CALIF_OBTENER;
    }

    @Test
    @Order(52)
    public void testConcursoAgregar() throws Exception {
        long id=4;
        String nom="Coding Cup TecNM";
        Date fecha=Date.valueOf("2020-11-06");
        Date fechaIni=Date.valueOf("2020-10-15");
        Date fechaFin=Date.valueOf("2020-11-03");
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.agregaConcurso(conc);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaConcurso de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable=conndbunit.createQueryTable("concurso",
                "SELECT * FROM concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursocomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_concurso += CALIF_AGREGAR;
    }

    @Test
    @Order(53)
    public void testConcursoAgregarDuplicada() throws Exception {
        long id=4;
        String nom="Coding Cup TecNM";
        Date fecha=Date.valueOf("2020-11-06");
        Date fechaIni=Date.valueOf("2020-10-15");
        Date fechaFin=Date.valueOf("2020-11-03");
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=dao.agregaConcurso(conc);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaConcurso de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_concurso += CALIF_AGREGAR;
    }


    @Test
    @Order(54)
    public void testConcursoActualizar() throws Exception {
        long id=4;
        String nom="Coding Cup TecNM 2020";
        Date fecha=Date.valueOf("2020-11-04");
        Date fechaIni=Date.valueOf("2020-10-13");
        Date fechaFin=Date.valueOf("2020-11-01");
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=dao.actualizaConcurso(conc);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaConcurso de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("concurso",
                "SELECT * FROM concurso WHERE id_concurso>3");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursocomp_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_concurso += CALIF_UPDATE;
    }

    @Test
    @Order(55)
    public void testConcursoActualizarInexistente() throws Exception {
        long id=5;
        String nom="Coding Cup TecNM 2020";
        Date fecha=Date.valueOf("2020-11-04");
        Date fechaIni=Date.valueOf("2020-10-13");
        Date fechaFin=Date.valueOf("2020-11-01");
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=dao.actualizaConcurso(conc);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaConcurso de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_concurso += CALIF_UPDATE;
    }

    @Test
    @Order(56)
    public void testConcursoEliminar() throws Exception {
        long id=4;
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaConcurso(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaConcurso de DaoConcursosImpl");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createQueryTable("concurso",
                "SELECT * FROM concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_concurso += CALIF_DELETE;
    }


    @Test
    @Order(57)
    public void testConcursoEliminarInexistente() throws Exception {
        long id=16L;
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaConcurso(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaConcurso de DaoConcursosImpl");
        }
        calif_concurso += CALIF_DELETE;
    }

    /// EQUIPO

    private void comparaEquipo(Equipo actual, ITable expected, int numrow) {
        try {
            Assertions.assertEquals(expected.getValue(numrow, "id_equipo").toString(),String.valueOf(actual.getIdEquipo()));
            Assertions.assertEquals(expected.getValue(numrow, "nombre_equipo"),actual.getNombreEquipo());
            Assertions.assertEquals(expected.getValue(numrow, "email_coach"),actual.getEmailCoach());
            Assertions.assertEquals(expected.getValue(numrow, "email_concursante1"),actual.getEmailConcursante1());
            Assertions.assertEquals(expected.getValue(numrow, "email_concursante2"),actual.getEmailConcursante2());
            Assertions.assertEquals(expected.getValue(numrow, "email_concursante3"),actual.getEmailConcursante3());
            if (expected.getTableMetaData().getColumns().length>7)
                Assertions.assertEquals(expected.getValue(numrow, "email_concursante_reserva"),actual.getEmailConcursanteReserva());
            Assertions.assertEquals(expected.getValue(numrow, "id_institucion_equipo").toString(),String.valueOf(actual.getIdInstitucionEquipo()));
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar los equipos");
        }
    }

    @Test
    @Order(61)
    public void testEquipoObten() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<Equipo> actual;
        try {
            actual=dao.obtenEquipos();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenEquipos de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaEquipo(actual.get(i), expectedTable,i);
        }
        calif_equipo += CALIF_OBTENER;
    }

    @Test
    @Order(62)
    public void testEquipoAgregar() throws Exception {
        long id=9;
        String nom="Code Avengers";
        String emailC="rsolis@uaz.edu.mx";
        String email1="hjesus@hotmail.com";
        String email2="charly@uaz.edu.mx";
        String email3="joyce@hotmail.com";
        String emailR="lalo@uaz.edu.mx";
        long idInst=1;

        Equipo eq = new Equipo(id,nom,emailC,email1,email2,email3,idInst);
        eq.setEmailConcursanteReserva(emailR);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.agregaEquipo(eq);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaEquipo de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable=conndbunit.createQueryTable("equipo",
                "SELECT * FROM equipo WHERE id_equipo>8");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipocomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_equipo += CALIF_AGREGAR;
    }

    @Test
    @Order(63)
    public void testEquipoAgregarDuplicada() throws Exception {
        long id=9;
        String nom="Code Avengers";
        String emailC="rsolis@uaz.edu.mx";
        String email1="hjesus@hotmail.com";
        String email2="charly@uaz.edu.mx";
        String email3="joyce@hotmail.com";
        String emailR="lalo@uaz.edu.mx";
        long idInst=1;

        Equipo eq = new Equipo(id,nom,emailC,email1,email2,email3,idInst);
        eq.setEmailConcursanteReserva(emailR);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=dao.agregaEquipo(eq);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaEquipo de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_equipo += CALIF_AGREGAR;
    }


    @Test
    @Order(64)
    public void testEquipoActualizar() throws Exception {
        long id=9;
        String nom="Code Avengers Reloaded";
        String emailC="rsolis@uaz.edu.mx";
        String emailR="hjesus@hotmail.com";
        String email1="charly@uaz.edu.mx";
        String email2="joyce@hotmail.com";
        String email3="lalo@uaz.edu.mx";
        long idInst=1;
        Equipo eq = new Equipo(id,nom,emailC,email1,email2,email3,idInst);
        eq.setEmailConcursanteReserva(emailR);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=dao.actualizaEquipo(eq);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaEquipo de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("equipo",
                "SELECT * FROM equipo WHERE id_equipo>8");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipocomp_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_equipo += CALIF_UPDATE;
    }

    @Test
    @Order(65)
    public void testEquipoActualizarInexistente() throws Exception {
        long id=91;
        String nom="Code Avengers Reloaded";
        String emailC="rsolis@uaz.edu.mx";
        String emailR="hjesus@hotmail.com";
        String email1="charly@uaz.edu.mx";
        String email2="joyce@hotmail.com";
        String email3="lalo@uaz.edu.mx";
        long idInst=1;
        Equipo eq = new Equipo(id,nom,emailC,email1,email2,email3,idInst);
        eq.setEmailConcursanteReserva(emailR);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=dao.actualizaEquipo(eq);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaEquipo de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_equipo += CALIF_UPDATE;
    }

    @Test
    @Order(66)
    public void testEquipoEliminar() throws Exception {
        long id=9;
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaEquipo(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaEquipo de DaoConcursosImpl");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createQueryTable("equipo",
                "SELECT * FROM equipo WHERE id_equipo>8");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipocomp_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_equipo += CALIF_DELETE;
    }


    @Test
    @Order(67)
    public void testEquipoEliminarInexistente() throws Exception {
        long id=16L;
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaEquipo(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaEquipo de DaoConcursosImpl");
        }
        calif_equipo += CALIF_DELETE;
    }

    /// SEDECONCURSO

    @Test
    @Order(71)
    public void testXSedeConcursoAgregar() throws Exception {
        long id=4;
        long idsede=3;
        long idconcurso=3;
        SedeConcurso sc = new SedeConcurso(id, idsede,idconcurso);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.agregaSedeConcurso(sc);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaSedeConcurso de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable=conndbunit.createQueryTable("sede_concurso",
                "SELECT id_sede,id_concurso FROM sede_concurso ORDER BY id_sede_concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sede_concursocomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede_concurso");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_sede_concurso += CALIF_AGREGAR;
    }

    @Test
    @Order(72)
    public void testXSedeConcursoAgregarDuplicada() throws Exception {
        long id=4;
        long idsede=3;
        long idconcurso=3;
        SedeConcurso sc = new SedeConcurso(id, idsede,idconcurso);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=dao.agregaSedeConcurso(sc);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaSedeConcurso de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_sede_concurso += CALIF_AGREGAR;
    }


    @Test
    @Order(73)
    public void testXSedeConcursoEliminar() throws Exception {
        long id=4;
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaSedeConcurso(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaSedeConcurso de DaoConcursosImpl");
        }

        ITable actualTable=conndbunit.createQueryTable("sede_concurso",
                "SELECT * FROM sede_concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede_concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_sede_concurso += CALIF_DELETE;
    }


    @Test
    @Order(74)
    public void testXSedeConcursoEliminarInexistente() throws Exception {
        long id=16L;
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaSedeConcurso(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaSedeConcurso de DaoConcursosImpl");
        }
        calif_sede_concurso += CALIF_DELETE;
    }

    /// EQUIPOSSEDECONCURSO

    @Test
    @Order(81)
    public void testXEquipoSedeConcursoAgregar() throws Exception {
        long id=6;
        long idequipo=1;
        long idsedeconcurso=2;

        EquiposSedeConcurso sc = new EquiposSedeConcurso(id, idequipo,idsedeconcurso);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.registrarEquipoSedeConcurso(sc);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo registrarEquipoSedeConcurso de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable=conndbunit.createQueryTable("equipos_sede_concurso",
                "SELECT id_equipo,id_sede_concurso FROM equipos_sede_concurso ORDER BY id_equipo_sede_concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipos_sede_concursocomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipos_sede_concurso");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_equipos_sede_concurso += CALIF_AGREGAR;
    }

    @Test
    @Order(82)
    public void testXEquipoSedeConcursoAgregarDuplicada() throws Exception {
        long id=6;
        long idequipo=1;
        long idsedeconcurso=2;

        EquiposSedeConcurso sc = new EquiposSedeConcurso(id, idequipo,idsedeconcurso);

        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=dao.registrarEquipoSedeConcurso(sc);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo registrarEquipoSedeConcurso de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_equipos_sede_concurso += CALIF_AGREGAR;
    }


    @Test
    @Order(83)
    public void testXEquipoSedeConcursoEliminar() throws Exception {
        long id=6;
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.cancelarEquipoSedeConcurso(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo cancelarEquipoSedeConcurso de DaoConcursosImpl");
        }

        ITable actualTable=conndbunit.createQueryTable("equipos_sede_concurso",
                "SELECT * FROM equipos_sede_concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipos_sede_concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_equipos_sede_concurso += CALIF_DELETE;
    }


    @Test
    @Order(84)
    public void testXEquipoSedeConcursoEliminarInexistente() throws Exception {
        long id=16L;
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.cancelarEquipoSedeConcurso(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo cancelarEquipoSedeConcurso de DaoConcursosImpl");
        }
        calif_equipos_sede_concurso += CALIF_DELETE;
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
    @Order(91)
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

    private void comparaEmails(List<String> actual, ITable expected) {
        try {
            Assertions.assertEquals(expected.getRowCount(),actual.size());
            for (int i = 0; i < actual.size(); i++) {
                Assertions.assertEquals(expected.getValue(i, "email_persona"),actual.get(i));
            }
        }
        catch (DataSetException exds) {
            //exds.printStackTrace();
            Assertions.assertNull(exds, "No deberia generar excepcion comparar los emails");
        }
    }

    @Test
    @Order(92)
    public void testEmailsObten() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<String> actual1=null,actual2=null,actual3=null;

        try {
            actual1=dao.obtenCorreosDeInstitucion(1,"Estudiante");
            actual2=dao.obtenCorreosDeInstitucion(1,"Profesor");
            actual3=dao.obtenCorreosDeInstitucion(3,"Estudiante");

        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenCorreosDeInstitucion de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosmetodosespeciales.xml"));
        ITable expectedTable1=expectedDataSet.getTable("correos1_e");
        ITable expectedTable2=expectedDataSet.getTable("correos1_p");
        ITable expectedTable3=expectedDataSet.getTable("correos3_e");

        comparaEmails(actual1,expectedTable1);
        comparaEmails(actual2,expectedTable2);
        comparaEmails(actual3,expectedTable3);
        calif_emails += CALIF_OBTENER;
    }

    private void comparaTablaSede(List<Sede> actual, ITable expectedTable) {
        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaSede(actual.get(i), expectedTable,i);
        }
    }


    @Test
    @Order(93)
    public void testYSedesDisponibles() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<Sede> actual1=null,actual2=null,actual3=null;

        try {
            actual1=dao.obtenSedesDisponibles(1);
            actual2=dao.obtenSedesDisponibles(2);
            actual3=dao.obtenSedesDisponibles(3);

        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenSedesDisponibles de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosmetodosespeciales.xml"));
        ITable expectedTable1=expectedDataSet.getTable("sededisp_1");
        ITable expectedTable2=expectedDataSet.getTable("sededisp_2");
        ITable expectedTable3=expectedDataSet.getTable("sededisp_3");

        comparaTablaSede(actual1,expectedTable1);
        comparaTablaSede(actual2,expectedTable2);
        comparaTablaSede(actual3,expectedTable3);
        calif_sedesdisp += CALIF_OBTENER;
    }

    private void comparaSedeConcursoExtendida(List<SedeConcursoExtendida> actual, ITable expected) {
        try {
            Assertions.assertEquals(actual.size(), expected.getRowCount());
            for (int i = 0; i < actual.size(); i++) {
                Assertions.assertEquals(expected.getValue(i, "id_sede_concurso").toString(),String.valueOf(actual.get(i).getIdSedeConcurso()));
                Assertions.assertEquals(expected.getValue(i, "id_sede").toString(),String.valueOf(actual.get(i).getIdSede()));
                Assertions.assertEquals(expected.getValue(i, "id_concurso").toString(),String.valueOf(actual.get(i).getIdConcurso()));
                Assertions.assertEquals(expected.getValue(i, "nombre_sede"),actual.get(i).getNombreSede());
                Assertions.assertEquals(expected.getValue(i, "nombre_concurso"),actual.get(i).getNombreConcurso());
            }
        }
        catch (DataSetException exds) {
            //exds.printStackTrace();
            Assertions.assertNull(exds, "No deberia generar excepcion comparar las SedesConcursoExtendidas");
        }
    }

    @Test
    @Order(94)
    public void testYSedesAsignadas() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<SedeConcursoExtendida> actual1=null,actual2=null,actual3=null;

        try {
            actual1=dao.obtenSedesAsignadas(1);
            actual2=dao.obtenSedesAsignadas(2);
            actual3=dao.obtenSedesAsignadas(3);

        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenSedesAsignadas de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosmetodosespeciales.xml"));
        ITable expectedTable1=expectedDataSet.getTable("sedeasign_1");
        ITable expectedTable2=expectedDataSet.getTable("sedeasign_2");
        ITable expectedTable3=expectedDataSet.getTable("sedeasign_3");

        comparaSedeConcursoExtendida(actual1,expectedTable1);
        comparaSedeConcursoExtendida(actual2,expectedTable2);
        comparaSedeConcursoExtendida(actual3,expectedTable3);
        calif_sedesasig += CALIF_OBTENER;
    }

    private void comparaTablaEquipo(List<Equipo> actual, ITable expectedTable) {
        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaEquipo(actual.get(i), expectedTable,i);
        }
    }


    @Test
    @Order(95)
    public void testYEquiposDisponibles() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<Equipo> actual1=null,actual2=null,actual3=null,actual4=null,actual5=null;

        try {
            actual1=dao.obtenEquiposDisponibles(1,1);
            actual2=dao.obtenEquiposDisponibles(2,1);
            actual3=dao.obtenEquiposDisponibles(2,2);
            actual4=dao.obtenEquiposDisponibles(1,2);
            actual5=dao.obtenEquiposDisponibles(2,3);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenEquiposDisponibles de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosmetodosespeciales.xml"));
        ITable expectedTable1=expectedDataSet.getTable("equipodisp1_1");
        ITable expectedTable2=expectedDataSet.getTable("equipodisp2_1");
        ITable expectedTable3=expectedDataSet.getTable("equipodisp2_2");
        ITable expectedTable4=expectedDataSet.getTable("equipodisp1_2");
        ITable expectedTable5=expectedDataSet.getTable("equipodisp2_3");

        comparaTablaEquipo(actual1,expectedTable1);
        comparaTablaEquipo(actual2,expectedTable2);
        comparaTablaEquipo(actual3,expectedTable3);
        comparaTablaEquipo(actual4,expectedTable4);
        comparaTablaEquipo(actual5,expectedTable5);
        calif_equiposdisp += CALIF_OBTENER;
    }

    private void comparaEquiposSedeConcursoExtendida(List<EquiposSedeConcursoExtendida> actual, ITable expected) {
        try {
            Assertions.assertEquals(expected.getRowCount(),actual.size());
            for (int i = 0; i < actual.size(); i++) {
                Assertions.assertEquals(expected.getValue(i, "id_equipo_sede_concurso").toString(),String.valueOf(actual.get(i).getIdEquipoSedeConcurso()));
                Assertions.assertEquals(expected.getValue(i, "id_equipo").toString(),String.valueOf(actual.get(i).getIdEquipo()));
                Assertions.assertEquals(expected.getValue(i, "id_sede_concurso").toString(),String.valueOf(actual.get(i).getIdSedeConcurso()));
                Assertions.assertEquals(expected.getValue(i, "nombre_equipo"),actual.get(i).getNombreEquipo());
                Assertions.assertEquals(expected.getValue(i, "nombre_sede"),actual.get(i).getNombreSede());
                Assertions.assertEquals(expected.getValue(i, "nombre_concurso"),actual.get(i).getNombreConcurso());
            }
        }
        catch (DataSetException exds) {
            //exds.printStackTrace();
            Assertions.assertNull(exds, "No deberia generar excepcion comparar las EquiposSedesConcursoExtendidas");
        }
    }

    @Test
    @Order(96)
    public void testYEquiposRegistrados() throws Exception {
        DaoConcursosImpl dao= new DaoConcursosImpl(conndbunit.getConnection());
        List<EquiposSedeConcursoExtendida> actual1=null,actual2=null,actual3=null,actual4=null;

        try {
            actual1=dao.obtenEquiposRegistrados(1,1);
            actual2=dao.obtenEquiposRegistrados(1,2);
            actual3=dao.obtenEquiposRegistrados(1,3);
            actual4=dao.obtenEquiposRegistrados(3,1);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenSedesAsignadas de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosmetodosespeciales.xml"));
        ITable expectedTable1=expectedDataSet.getTable("equiporeg1_1");
        ITable expectedTable2=expectedDataSet.getTable("equiporeg1_2");
        ITable expectedTable3=expectedDataSet.getTable("equiporeg1_3");
        ITable expectedTable4=expectedDataSet.getTable("equiporeg3_1");

        comparaEquiposSedeConcursoExtendida(actual1,expectedTable1);
        comparaEquiposSedeConcursoExtendida(actual2,expectedTable2);
        comparaEquiposSedeConcursoExtendida(actual3,expectedTable3);
        comparaEquiposSedeConcursoExtendida(actual4,expectedTable4);
        calif_equiposreg += CALIF_OBTENER;
    }

}