package poo2.prac09.main;

import junit.framework.TestCase;
import poo2.progs.basedatos.DaoConcursosREST;
import poo2.progs.entidades.Institucion;
import poo2.progs.entidades.Municipio;
import poo2.progs.interfaces.DAOConcursos;
import poo2.progs.utils.HttpUtils;
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

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestServiciosREST extends TestCase {
    private static IDatabaseTester databaseTester;
    private final static String driverName="com.mysql.cj.jdbc.Driver";
    private static IDatabaseConnection conndbunit;
    private static int calif_inst;
    private static int cont_agregar, cont_act, cont_elim;
    private final static int CALIF_OBTENER=8;
    private final static int CALIF_AGREGAR=24;
    private final static int CALIF_UPDATE=24;
    private final static int CALIF_DELETE=24;
    private final static int CALIF_MUNICIPIO=20;
    private final static int MAX_CALIF_INST=100;
    private final static double PORCENTAJE_INST=100.0;
    private static DAOConcursos dao;

    @BeforeAll
    public static void inicializa() throws Exception {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        ResourceBundle res=ResourceBundle.getBundle("datosmysql", Locale.ROOT);
        String matricula = res.getString("matricula");
        String nombreCompleto = res.getString("nombre");

        String usuario=res.getString("usuario");
        String clave= res.getString("clave");
        String basedatos=res.getString("basedatos");

        String tipo_prueba=System.getProperty("tipo_prueba","local");
        if (tipo_prueba==null || tipo_prueba.isEmpty()) {
            tipo_prueba="local";
        }

        String ubicacionmysl=res.getString("ubicacionmysql"+"_"+tipo_prueba);
        String puertomysql=res.getString("puertomysql"+"_"+tipo_prueba);

        String ubicacionGF = res.getString("ubicacionglassfish" + "_" + tipo_prueba);
        String puertoGF = res.getString("puertoglassfish" + "_" + tipo_prueba);

        String urlbaseGF = String.format("http://%s:%s/RESTConcursos-%s",
                ubicacionGF, puertoGF, matricula);
        String url=String.format("jdbc:mysql://%s:%s/%s",
                ubicacionmysl,puertomysql,basedatos);
        //System.out.println("URL MySQL:"+url);

        String pagina= HttpUtils.httpGet(urlbaseGF, null);
        int posInicioTitulo=pagina.indexOf("<title>")+7;
        int posFinTitulo=pagina.indexOf("</title>");
        int posInicioH1=pagina.indexOf("<h1>")+4;
        int posFinH1=pagina.indexOf("</h1>");
        int posInicioH2=pagina.indexOf("<h2>")+4;
        int posFinH2=pagina.indexOf("</h2>");
        String textoTitulo=pagina.substring(posInicioTitulo, posFinTitulo).toUpperCase();
        String textoH1=pagina.substring(posInicioH1, posFinH1).toUpperCase();
        String textoH2=pagina.substring(posInicioH2, posFinH2).toUpperCase();
        String tituloEsperado=String.format("Servicios REST de %s", nombreCompleto).toUpperCase();
        String h2Esperado=String.format("Matricula %s", matricula).toUpperCase();

        Assertions.assertEquals(tituloEsperado,textoTitulo);
        Assertions.assertEquals(tituloEsperado,textoH1);
        Assertions.assertEquals(h2Esperado,textoH2);

        dao=new DaoConcursosREST(ubicacionGF,Integer.parseInt(puertoGF), matricula);

        databaseTester=new JdbcDatabaseTester(driverName,url,
                usuario,clave);
        databaseTester.setOperationListener(new ConfigAccesoBaseDatos.CustomConfigurationOperationListener());
        conndbunit=databaseTester.getConnection();
        DatabaseConfig config=conndbunit.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS,true);
        IDataSet dataSet=new FlatXmlDataSetBuilder().build(new FileInputStream("concursosv3.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.REFRESH);
        databaseTester.onTearDown();
        calif_inst+=cont_agregar==3?CALIF_AGREGAR:0;
        calif_inst+=cont_act==3?CALIF_UPDATE:0;
        calif_inst+=cont_elim==3?CALIF_DELETE:0;
        if (calif_inst>0) {
            System.out.printf("Puntos para Pruebas Practica 9: %.2f/%.2f\n", calif_inst * PORCENTAJE_INST / MAX_CALIF_INST, PORCENTAJE_INST);
        }
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
            Assertions.assertEquals(expected.getValue(numrow, "codpostal_institucion"), actual.getCodpostalInstitucion() );
            Assertions.assertEquals(expected.getValue(numrow, "telefono_institucion"),actual.getTelefonoInstitucion());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex,"No deberia generar excepcion comparar las instituciones");
        }
    }

    @Test
    @Order(1)
    public void testInstObtenTodas() throws Exception {
        List<Institucion> actual = dao.obtenInstituciones();

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaInst(actual.get(i), expectedTable,i);
        }
        calif_inst += CALIF_OBTENER;
    }

    @Test
    @Order(2)
    public void testInstAgregarValida() throws Exception {
        long id=5;
        String nom="Universidad Tecnologica Estado de Zacatecas";
        String nomcorto="UTZAC";
        String url="http://www.utzac.edu.mx";
        String calle="Carretera Zacatecas, Cd Cuauhtemoc km. 5";
        String colonia="Ejido Cieneguitas";
        long idmun=32017;
        long ident=32;
        String codpostal="98601";
        String tel="4929276180";
        Institucion inst= new Institucion(id,nom,nomcorto,url,
                calle,idmun,ident);
        inst.setColoniaInstitucion(colonia);
        inst.setCodpostalInstitucion(codpostal);
        inst.setTelefonoInstitucion(tel);
        boolean resultado;
        try {
            resultado = dao.agregaInstitucion(inst);
        }
        catch (Exception ex) {
            resultado=false;
            //ex.printStackTrace();
            Assertions.assertNull(ex,"No deberia generarse excepcion al llamar a agregaInstitucion");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion WHERE id_institucion>4");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");

        Assertion.assertEquals(expectedTable,actualTable);
        cont_agregar++;
    }

    @Test
    @Order(3)
    public void testInstAgregarDuplicada()  {
        long id=5;
        String nom="Universidad Tecnologica Estado de Zacatecas";
        String nomcorto="UTZAC";
        String url="http://www.utzac.edu.mx";
        String calle="Carretera Zacatecas, Cd Cuauhtemoc km. 5";
        String colonia="Ejido Cieneguitas";
        long idmun=32017;
        long ident=32;
        String codpostal="98601";
        String tel="4929276180";

        Institucion inst= new Institucion(id,nom,nomcorto,url,
                calle,idmun,ident);
        inst.setColoniaInstitucion(colonia);
        inst.setCodpostalInstitucion(codpostal);
        inst.setTelefonoInstitucion(tel);

        boolean resultado;
        try {
            resultado=dao.agregaInstitucion(inst);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generarse excepcion al llamar a agregaInstitucion");
        }

        cont_agregar++;
    }

    private void validaNullo_Vacio(String valor, long id, boolean nuevo) {
        String nom="Universidad Tecnologica Estado de Zacatecas";
        String nomcorto="UTZAC";
        String url="http://www.utzac.edu.mx";
        String calle="Carretera Zacatecas, Cd Cuauhtemoc km. 5";
        String colonia="Ejido Cieneguitas";
        long idmun=32017;
        long ident=32;
        String codpostal="98601";
        String tel="4929276180";
        boolean resultado;
        String valprev;

        Institucion inst= new Institucion(id,valor,nomcorto,url,
                calle,idmun,ident);
        inst.setColoniaInstitucion(colonia);
        inst.setCodpostalInstitucion(codpostal);
        inst.setTelefonoInstitucion(tel);
        resultado=nuevo?dao.agregaInstitucion(inst):dao.actualizaInstitucion(inst);
        Assertions.assertFalse(resultado);

        inst.setNombreInstitucion(nom);
        valprev=inst.getNombreCortoInstitucion();
        inst.setNombreCortoInstitucion(valor);
        resultado=nuevo?dao.agregaInstitucion(inst):dao.actualizaInstitucion(inst);
        Assertions.assertFalse(resultado);

        inst.setNombreCortoInstitucion(valprev);
        valprev=inst.getUrlInstitucion();
        inst.setUrlInstitucion(valor);
        resultado=nuevo?dao.agregaInstitucion(inst):dao.actualizaInstitucion(inst);
        Assertions.assertFalse(resultado);

        inst.setUrlInstitucion(valprev);
        valprev=inst.getCalleNumInstitucion();
        inst.setCalleNumInstitucion(valor);
        resultado=nuevo?dao.agregaInstitucion(inst):dao.actualizaInstitucion(inst);
        Assertions.assertFalse(resultado);

        inst.setCalleNumInstitucion(valprev);
        valprev=inst.getColoniaInstitucion();
        inst.setColoniaInstitucion(valor);
        resultado=nuevo?dao.agregaInstitucion(inst):dao.actualizaInstitucion(inst);
        Assertions.assertTrue(resultado);

        Connection conn;
        Statement stmt=null;
        String sql=String.format("DELETE FROM institucion WHERE id_institucion=%d",id);
        try {
            if (nuevo) {
                conn = conndbunit.getConnection();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            Assertions.assertNull(exsql, "Problema al eliminar la institucion que se agrego en la prueba");
        }

        inst.setColoniaInstitucion(valprev);
        valprev=inst.getCodpostalInstitucion();
        inst.setCodpostalInstitucion(valor);
        resultado=nuevo?dao.agregaInstitucion(inst):dao.actualizaInstitucion(inst);
        Assertions.assertTrue(resultado);

        try {
            if (nuevo) {
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            exsql.printStackTrace();
            Assertions.assertNull(exsql, "Problema al eliminar la institucion que se agrego en la prueba");
        }

        inst.setCodpostalInstitucion(valprev);
        inst.setTelefonoInstitucion(valor);
        resultado=nuevo?dao.agregaInstitucion(inst):dao.actualizaInstitucion(inst);
        Assertions.assertTrue(resultado);

        try {
            if (nuevo) {
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            exsql.printStackTrace();
            Assertions.assertNull(exsql, "Problema al eliminar la institucion que se agrego en la prueba");
        }
    }

    @Test
    @Order(4)
    public void testInstAgregarInvalida() {
        long id=6;
        String[] nom={"Universidad","Universidad Con un Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] nomcorto={"UEZAM","UniversidadTecnologicadelEstadodeZacatecas"};
        String url1="Este URL es extremadamente largo y por tanto no deberia de pasar ";
        url1 += url1; url1 += url1; url1 += url1;
        String[] url ={"http://es.com",url1};
        String calle1="Carretera Zacatecas, Cd Cuauhtemoc km. 5";
        calle1 += calle1;
        calle1 += calle1;
        String[] calle={"Calle Tolosa 25",calle1};
        String colonia1="Ejido Cieneguitas";
        colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1;
        String[] colonia={"Centro",colonia1};
        long[] idmun={32056,32100};
        long[] ident={32,35};
        String[] codpostal={"98000","198601"};
        String[] tel={"4921234567","49292762180"};

        boolean resultado;
        Institucion inst= new Institucion(id,nom[1],nomcorto[0],url[0],
                calle[0],idmun[0],ident[0]);
        inst.setColoniaInstitucion(colonia[0]);
        inst.setCodpostalInstitucion(codpostal[0]);
        inst.setTelefonoInstitucion(tel[0]);

        try {
            validaNullo_Vacio("",100,true);

            resultado=dao.agregaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setNombreInstitucion(nom[0]);
            inst.setNombreCortoInstitucion(nomcorto[1]);
            resultado=dao.agregaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setNombreCortoInstitucion(nomcorto[0]);
            inst.setCalleNumInstitucion(calle[1]);
            resultado=dao.agregaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setCalleNumInstitucion(calle[0]);
            inst.setColoniaInstitucion(colonia[1]);
            resultado=dao.agregaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setColoniaInstitucion(colonia[0]);
            inst.setIdMunicipioInstitucion(idmun[1]);
            resultado=dao.agregaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setIdMunicipioInstitucion(idmun[0]);
            inst.setIdEntidadInstitucion(ident[1]);
            resultado=dao.agregaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setIdEntidadInstitucion(ident[0]);
            inst.setCodpostalInstitucion(codpostal[1]);
            resultado=dao.agregaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setCodpostalInstitucion(codpostal[0]);
            inst.setTelefonoInstitucion(tel[1]);
            resultado=dao.agregaInstitucion(inst);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado=true;
        }
        Assertions.assertFalse(resultado);
        cont_agregar++;
    }

    @Test
    @Order(5)
    public void testInstActualizarValida() throws Exception {
        long id=5;
        String nom="Universidad Tecnologica de Aguascalientes";
        String nomcorto="UTAGS";
        String url="http://www.utags.edu.mx";
        String calle="Blvd. Juan Pablo II 1302";
        String colonia="Canteras de San Agustin";
        long idmun=1001;
        long ident=1;
        String codpostal="20200";
        String tel="4499105000";
        Institucion inst= new Institucion(id,nom,nomcorto,url,
                calle,idmun,ident);
        inst.setColoniaInstitucion(colonia);
        inst.setCodpostalInstitucion(codpostal);
        inst.setTelefonoInstitucion(tel);

        boolean resultado=false;
        try {
            resultado=dao.actualizaInstitucion(inst);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaInstitucion");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion WHERE id_institucion>4");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("instcompleto_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");
        Assertion.assertEquals(expectedTable,actualTable);
        cont_act++;
    }

    @Test
    @Order(6)
    public void testInstActualizarInexistente()  {
        long id=16;
        String nom="Universidad Tecnologica de Aguascalientes";
        String nomcorto="UTAGS";
        String url="http://www.utags.edu.mx";
        String calle="Blvd. Juan Pablo II 1302";
        String colonia="Canteras de San Agustin";
        long idmun=1001;
        long ident=1;
        String codpostal="20200";
        String tel="4499105000";
        Institucion inst= new Institucion(id,nom,nomcorto,url,
                calle,idmun,ident);
        inst.setColoniaInstitucion(colonia);
        inst.setCodpostalInstitucion(codpostal);
        inst.setTelefonoInstitucion(tel);

        boolean resultado=true;
        try {
            resultado=dao.actualizaInstitucion(inst);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaInstitucion");
        }
        Assertions.assertFalse(resultado);
        cont_act++;
    }

    @Test
    @Order(7)
    public void testInstActualizarInvalida()  {
        long id=5;
        String[] nom={"Universidad","Universidad Con un Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] nomcorto={"UEZAM","UniversidadTecnologicadelEstadodeZacatecas"};
        String url1="Este URL es extremadamente largo y por tanto no deberia de pasar ";
        url1 += url1; url1 += url1; url1 += url1;
        String[] url ={"http://es.com",url1};
        String calle1="Carretera Zacatecas, Cd Cuauhtemoc km. 5";
        calle1 += calle1;
        calle1 += calle1;
        String[] calle={"Calle Tolosa 25",calle1};
        String colonia1="Ejido Cieneguitas";
        colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1;
        String[] colonia={"Centro",colonia1};
        long[] idmun ={32056,32100};
        long[] ident={32,35};
        String[] codpostal={"98000","198601"};
        String[] tel={"4921234567","49292762180"};

        boolean resultado;

        Institucion inst= new Institucion(id,nom[1],nomcorto[0],url[0],
                calle[0],idmun[0],ident[0]);
        inst.setColoniaInstitucion(colonia[0]);
        inst.setCodpostalInstitucion(codpostal[0]);
        inst.setTelefonoInstitucion(tel[0]);


        try {
            validaNullo_Vacio("",5,false);


            resultado=dao.actualizaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setNombreInstitucion(nom[0]);
            inst.setNombreCortoInstitucion(nomcorto[1]);
            resultado=dao.actualizaInstitucion(inst);
            Assertions.assertFalse(resultado);


            inst.setNombreCortoInstitucion(nomcorto[0]);
            inst.setCalleNumInstitucion(calle[1]);
            resultado=dao.actualizaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setCalleNumInstitucion(calle[0]);
            inst.setColoniaInstitucion(colonia[1]);
            resultado=dao.actualizaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setColoniaInstitucion(colonia[0]);
            inst.setIdMunicipioInstitucion(idmun[1]);
            resultado=dao.actualizaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setIdMunicipioInstitucion(idmun[0]);
            inst.setIdEntidadInstitucion(ident[1]);
            resultado=dao.actualizaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setIdEntidadInstitucion(ident[0]);
            inst.setCodpostalInstitucion(codpostal[1]);
            resultado=dao.actualizaInstitucion(inst);
            Assertions.assertFalse(resultado);

            inst.setCodpostalInstitucion(codpostal[0]);
            inst.setTelefonoInstitucion(tel[1]);
            resultado=dao.actualizaInstitucion(inst);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generarse excepcion al llamar al metodo actualizaInstitucion");
            resultado=true;
        }
        Assertions.assertFalse(resultado);
        cont_act++;
    }

    @Test
    @Order(8)
    public void testInstEliminar() throws Exception {
        long id=5;
        try {
            boolean resultado=dao.eliminaInstitucion(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generarse excepcion al llamar al metodo eliminaInstitucion");
        }

        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");
        Assertion.assertEquals(expectedTable,actualTable);
        cont_elim++;
    }


    @Test
    @Order(9)
    public void testInstEliminarInexistente() throws Exception {
        long id=16L;
        try {
            boolean resultado=dao.eliminaInstitucion(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generarse excepcion al llamar al metodo eliminaInstitucion");
        }
        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");

        Assertion.assertEquals(expectedTable,actualTable);
        cont_elim++;
    }

    @Test
    @Order(10)
    public void testInstEliminarInvalida() throws Exception {
        long id=1L;
        try {
            boolean resultado=dao.eliminaInstitucion(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion al llamar al metodo eliminaInstitucion");
        }

        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");

        Assertion.assertEquals(expectedTable,actualTable);
        cont_elim++;
    }


    /// MUNICIPIO

    private void comparaMunicipio(Municipio actual, ITable expected, int numrow) {
        try {
            Assertions.assertEquals(expected.getValue(numrow, "id_municipio").toString(),String.valueOf(actual.getIdMunicipio()));
            Assertions.assertEquals(expected.getValue(numrow, "nombre_municipio"),actual.getNombreMunicipio());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar los municipios");
        }
    }

    @Test
    @Order(11)
    public void testMunicipioObten() throws Exception {
        List<Municipio> actual;
        long id=32;
        try {
            actual=dao.obtenMunicipios(id);
        }
        catch (Exception ex) {
            actual=new ArrayList<>();
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenMunicipios");
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
        catch (Exception ex) {
            actual=new ArrayList<>();
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenMunicipios");
        }
        expectedDataSet=new FlatXmlDataSetBuilder().build(new File("municipio1.xml"));
        expectedTable=expectedDataSet.getTable("municipio");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaMunicipio(actual.get(i), expectedTable,i);
        }
        calif_inst += CALIF_MUNICIPIO;
    }

}
