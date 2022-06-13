package poo2.progs.main;
import junit.framework.TestCase;
import poo2.progs.basedatos.DaoConcursosREST;

import poo2.progs.entidades.*;
import poo2.progs.interfaces.DAOConcursos;
import poo2.progs.utils.HttpUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dbunit.*;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.*;
import poo2.ConfigAccesoBaseDatos;


import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.*;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestServiciosREST extends TestCase {
    private static IDatabaseTester databaseTester;
    private final static String driverName="com.mysql.cj.jdbc.Driver";
    private static IDatabaseConnection conndbunit;
    private final static double PORCENTAJE_INST=4.0;
    private final static double PORCENTAJE_ENTIDAD=3.0;
    private final static double PORCENTAJE_PERSONA=18.0;
    private final static double PORCENTAJE_DATOSESTUDIANTE=11.00;
    private final static double PORCENTAJE_SEDE=15.00;
    private final static double PORCENTAJE_CONCURSO=13.0;
    private final static double PORCENTAJE_EQUIPO=19.0;
    private final static double PORCENTAJE_SEDECONCURSO=6.0;
    private final static double PORCENTAJE_EQUIPOSEDECONCURSO=6.0;
    private final static double PORCENTAJE_MUNICIPIO=1.0;
    private final static double PORCENTAJE_EMAILS=4.0;
    private final static double PORCENTAJE_SEDESDISP=2.5;
    private final static double PORCENTAJE_SEDESASIG=2.5;
    private final static double PORCENTAJE_EQUIPOSDISP=2.5;
    private final static double PORCENTAJE_EQUIPOSREG=2.5;

    private static final int[] calif_inst ={0,0,0,0};
    private static final int[] calif_persona ={0,0,0,0};
    private static final int[] calif_datos_estudiante ={0,0,0,0};
    private static double calif_municipio;
    private static double calif_entidad;
    private static final int[] calif_sede ={0,0,0,0};
    private static final int[] calif_equipo ={0,0,0,0};
    private static final int[] calif_concurso ={0,0,0,0};
    private static final int[] calif_sede_concurso ={0,0,0,0};
    private static final int[] calif_equipos_sede_concurso ={0,0,0,0};
    private static double calif_emails;
    private static double calif_sedesdisp;
    private static double calif_sedesasig;
    private static double calif_equiposdisp;
    private static double calif_equiposreg;

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
        System.out.println("Tipo_prueba: '"+tipo_prueba+"'");
        String ubicacionmysl=res.getString("ubicacionmysql"+"_"+tipo_prueba);
        String puertomysql=res.getString("puertomysql"+"_"+tipo_prueba);

        String ubicacionGF = res.getString("ubicacionglassfish" + "_" + tipo_prueba);
        String puertoGF = res.getString("puertoglassfish" + "_" + tipo_prueba);

        String urlbaseGF = String.format("http://%s:%s/RESTConcursos-%s",
                ubicacionGF, puertoGF, matricula);
        String url=String.format("jdbc:mysql://%s:%s/%s", //?useLegacyDatetimeCode=false&serverTimezone=America/Chicago",
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

        //TimeZone timeZone = TimeZone.getDefault();
        //System.out.println("Zona:"+timeZone.toString());
        TimeZone horaMexico=TimeZone.getTimeZone("America/Mexico_City");
        TimeZone.setDefault(horaMexico);
        //timeZone = TimeZone.getDefault();
        //System.out.println("Zona Nueva:"+timeZone.toString());
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

    private static double puntaje(int[] arreglo, int[] numpruebas, double[] puntos) {
        double suma=0.0;
        for (int i=0; i<arreglo.length;i++) {
            if (numpruebas[i]!=0 && numpruebas[i]==arreglo[i]) {
                suma += puntos[i];
            }
        }
        return suma;
    }
    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.REFRESH);
        databaseTester.onTearDown();
        double[] puntajes_porop={1.0, 1.0, 1.0, 1.0};
        int[] numpruebas={1,3,3,3};
        double puntuacion=puntaje(calif_inst,numpruebas,puntajes_porop);
        double calif_total = puntuacion;
        System.out.printf("Puntos para Pruebas ServiciosREST (Institucion): %.2f/%.2f\n",puntuacion,PORCENTAJE_INST);
        calif_total +=calif_entidad;
        System.out.printf("Puntos para Pruebas ServiciosREST (Entidad): %.2f/%.2f\n", calif_entidad, PORCENTAJE_ENTIDAD);
        calif_total +=calif_municipio;
        System.out.printf("Puntos para Pruebas ServiciosREST (obtenMunicipios): %.2f/%.2f\n", calif_municipio, PORCENTAJE_MUNICIPIO);
        puntajes_porop[0]=3.0; puntajes_porop[1]=5.0;
        puntajes_porop[2]=5.0; puntajes_porop[3]=5.0;
        puntuacion=puntaje(calif_persona,numpruebas,puntajes_porop);
        calif_total += puntuacion;
        System.out.printf("Puntos para Pruebas ServiciosREST (Persona): %.2f/%.2f\n", puntuacion, PORCENTAJE_PERSONA);
        puntajes_porop[0]=3.0; puntajes_porop[1]=3.0;
        puntajes_porop[2]=3.0; puntajes_porop[3]=2.0;
        numpruebas[0]=2; numpruebas[1]=3;
        numpruebas[2]=3; numpruebas[3]=2;
        puntuacion=puntaje(calif_datos_estudiante,numpruebas,puntajes_porop);
        calif_total += puntuacion;
        System.out.printf("Puntos para Pruebas ServiciosREST (DatosEstudiante): %.2f/%.2f\n", puntuacion, PORCENTAJE_DATOSESTUDIANTE);
        numpruebas[0]=1; numpruebas[1]=3;
        numpruebas[2]=3; numpruebas[3]=3;
        puntajes_porop[0]=3.0; puntajes_porop[1]=4.0;
        puntajes_porop[2]=4.0; puntajes_porop[3]=4.0;
        puntuacion=puntaje(calif_sede,numpruebas,puntajes_porop);
        calif_total += puntuacion;
        System.out.printf("Puntos para Pruebas ServiciosREST (Sede): %.2f/%.2f\n", puntuacion, PORCENTAJE_SEDE);

        puntajes_porop[0]=3.0; puntajes_porop[1]=4.0;
        puntajes_porop[2]=4.0; puntajes_porop[3]=2.0;
        puntuacion=puntaje(calif_concurso,numpruebas,puntajes_porop);
        calif_total += puntuacion;
        System.out.printf("Puntos para Pruebas ServiciosREST (Concurso): %.2f/%.2f\n", puntuacion, PORCENTAJE_CONCURSO);

        puntajes_porop[0]=3.0; puntajes_porop[1]=6.0;
        puntajes_porop[2]=6.0; puntajes_porop[3]=4.0;
        puntuacion=puntaje(calif_equipo,numpruebas,puntajes_porop);
        calif_total += puntuacion;
        System.out.printf("Puntos para Pruebas ServiciosREST (Equipo): %.2f/%.2f\n", puntuacion, PORCENTAJE_EQUIPO);

        numpruebas[0]=0; numpruebas[1]=3;
        numpruebas[2]=0; numpruebas[3]=3;
        puntajes_porop[0]=0.0; puntajes_porop[1]=3.0;
        puntajes_porop[2]=0.0; puntajes_porop[3]=3.0;
        puntuacion=puntaje(calif_sede_concurso,numpruebas,puntajes_porop);
        calif_total += puntuacion;
        System.out.printf("Puntos para Pruebas ServiciosREST (SedeConcurso): %.2f/%.2f\n", puntuacion, PORCENTAJE_SEDECONCURSO);


        numpruebas[0]=0; numpruebas[1]=3;
        numpruebas[2]=0; numpruebas[3]=2;
        puntajes_porop[0]=0.0; puntajes_porop[1]=3.0;
        puntajes_porop[2]=0.0; puntajes_porop[3]=3.0;
        puntuacion=puntaje(calif_equipos_sede_concurso,numpruebas,puntajes_porop);
        calif_total += puntuacion;
        System.out.printf("Puntos para Pruebas ServiciosREST (EquiposSedeConcurso): %.2f/%.2f\n", puntuacion, PORCENTAJE_EQUIPOSEDECONCURSO);

        calif_total +=calif_emails;
        System.out.printf("Puntos para Pruebas ServiciosREST (obtenCorreosDeInstitucion): %.2f/%.2f\n", calif_emails, PORCENTAJE_EMAILS);

        calif_total +=calif_sedesdisp;
        System.out.printf("Puntos para Pruebas ServiciosREST (obtenSedesDisponibles): %.2f/%.2f\n", calif_sedesdisp, PORCENTAJE_SEDESDISP);

        calif_total +=calif_sedesasig;
        System.out.printf("Puntos para Pruebas ServiciosREST (obtenSedesAsignadas): %.2f/%.2f\n", calif_sedesasig, PORCENTAJE_SEDESASIG);

        calif_total +=calif_equiposdisp;
        System.out.printf("Puntos para Pruebas ServiciosREST (obtenEquiposDisponibles): %.2f/%.2f\n", calif_equiposdisp, PORCENTAJE_EQUIPOSDISP);

        calif_total +=calif_equiposreg;
        System.out.printf("Puntos para Pruebas ServiciosREST (obtenEquiposRegistrados): %.2f/%.2f\n", calif_equiposreg, PORCENTAJE_EQUIPOSREG);

        System.out.printf("Calificacion Total: %.2f/110.00\n",calif_total);
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
        calif_inst[0]++;
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
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generarse excepcion al llamar a agregaInstitucion");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion WHERE id_institucion>4");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_inst[1]++;
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

        calif_inst[1]++;
    }

    private void validaNullo_VacioInst(String valor, long id, boolean nuevo) {
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
            exsql.printStackTrace();
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
    public void testInstAgregarInvalida()  {
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
            //resultado=dao.agregaInstitucion(null);
            //assertFalse(resultado);

            //validaNullo_VacioInst(null,100,true);
            validaNullo_VacioInst("",100,true);

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
        calif_inst[1]++;
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
        calif_inst[2]++;
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
        calif_inst[2]++;
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

            //validaNullo_VacioInst(null,5,false);
            validaNullo_VacioInst("",5,false);


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
            resultado=true;
            Assertions.assertNull(ex, "No deberia generarse excepcion al llamar al metodo actualizaInstitucion");
        }
        Assertions.assertFalse(resultado);
        calif_inst[2]++;
    }

    @Test
    @Order(8)
    public void testInstEliminarValida() throws Exception {
        long id=5;
        try {
            boolean resultado=dao.eliminaInstitucion(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generarse excepcion al llamar al metodo eliminaInstitucion");
        }

        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_inst[3]++;
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
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generarse excepcion al llamar al metodo eliminaInstitucion");
        }
        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_inst[3]++;
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
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion al llamar al metodo eliminaInstitucion");
        }

        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_inst[3]++;
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
        calif_municipio += PORCENTAJE_MUNICIPIO;
    }

    ///   ENTIDAD

    private void comparaEntidad(Entidad actual, ITable expected, int numrow) {
        try {
            Assertions.assertEquals(expected.getValue(numrow, "id_entidad").toString(),String.valueOf(actual.getIdEntidad()));
            Assertions.assertEquals(expected.getValue(numrow, "nombre_entidad"),actual.getNombreEntidad());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar las entidades");
        }
    }

    @Test
    @Order(12)
    public void testEntidadObten() throws Exception {
        List<Entidad> actual;
        try {
            actual=dao.obtenEntidades();
        }
        catch (Exception ex) {
            actual=new ArrayList<>();
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenEntidades");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("entidad.xml"));
        ITable expectedTable=expectedDataSet.getTable("entidad");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaEntidad(actual.get(i), expectedTable,i);
        }
        calif_entidad += PORCENTAJE_ENTIDAD;
    }

    // PERSONA

    private void comparaPersona(Persona actual, ITable expected, int numrow) {
        try {

            Assertions.assertEquals(expected.getValue(numrow, "email_persona"), actual.getEmailPersona());
            Assertions.assertEquals(expected.getValue(numrow, "nombre_persona"),actual.getNombrePersona());
            Assertions.assertEquals(expected.getValue(numrow, "apellidos_persona"),actual.getApellidosPersona());
            Assertions.assertEquals(expected.getValue(numrow, "sexo_persona"),actual.getSexoPersona());
            Assertions.assertEquals(expected.getValue(numrow, "calle_num_persona"), actual.getCalleNumPersona());
            Assertions.assertEquals(expected.getValue(numrow, "colonia_persona"),actual.getColoniaPersona());
            Assertions.assertEquals(expected.getValue(numrow, "id_municipio_persona").toString(),String.valueOf(actual.getIdMunicipioPersona()));
            Assertions.assertEquals(expected.getValue(numrow, "id_entidad_persona").toString(),String.valueOf(actual.getIdEntidadPersona()));
            Assertions.assertEquals(expected.getValue(numrow, "codpostal_persona"),actual.getCodpostalPersona());
            Assertions.assertEquals(expected.getValue(numrow, "telefono_persona"),actual.getTelefonoPersona());
            java.sql.Date f1;
            f1=new java.sql.Date(actual.getFechaNacPersona().getTime());
            Assertions.assertEquals(expected.getValue(numrow, "fecha_nac_persona"),f1.toString());
            Assertions.assertEquals(expected.getValue(numrow, "id_institucion_persona").toString(),String.valueOf(actual.getIdInstitucionPersona()));
            Assertions.assertEquals(expected.getValue(numrow, "tipo_persona"),actual.getTipoPersona());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar las personas");
        }
    }

    @Test
    @Order(13)
    public void testPersonaObten() throws Exception {

        List<Persona> actual;
        try {
            actual=dao.obtenPersonas();
        }
        catch (Exception ex) {
            actual=new ArrayList<>();
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenPersonas de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaPersona(actual.get(i), expectedTable,i);
        }
        calif_persona[0]++;
    }

    @Test
    @Order(14)
    public void testPersonaAgregarValida() throws Exception {
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
        Date fecha=new Date(java.sql.Date.valueOf("2001-10-13").getTime());
        long idinst=3L;
        String tipo="Estudiante";

        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        boolean resultado;
        try {
            resultado = dao.agregaPersona(per);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaPersona de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("persona",
                "SELECT * FROM persona");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("personacompleto_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");

        Assertion.assertEquals(expectedTable,actualTable);

        calif_persona[1]++;
    }

    @Test
    @Order(15)
    public void testPersonaAgregarDuplicada() {
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
        Date fecha=new Date(java.sql.Date.valueOf("2001-10-13").getTime());
        long idinst=3L;
        String tipo="Estudiante";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        boolean resultado;
        try {
            resultado=dao.agregaPersona(per);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaPersona de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_persona[1]++;
    }

    private void validaNullo_VacioPersona(String valor, String id, boolean nuevo) {
        String nom="Miguel";
        String apellidos="Salas Guzman";
        String sexo="M";
        String calle="Herreros 25";
        String colonia="Centro";
        long idmun=32042L;
        long ident=32L;
        String codpostal="99104";
        String tel="4331234567";
        Date fecha=new Date(java.sql.Date.valueOf("2001-10-13").getTime());
        long idinst=3L;
        String tipo="Estudiante";
        boolean resultado;
        String valprev;

        Persona per= new Persona(valor,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        if (nuevo) {
            resultado = nuevo ? dao.agregaPersona(per) : dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);
        }

        per.setEmailPersona(id);
        valprev=per.getNombrePersona();
        per.setNombrePersona(valor);
        resultado=nuevo?dao.agregaPersona(per):dao.actualizaPersona(per);
        Assertions.assertFalse(resultado);

        per.setNombrePersona(valprev);
        valprev=per.getApellidosPersona();
        per.setApellidosPersona(valor);
        resultado=nuevo?dao.agregaPersona(per):dao.actualizaPersona(per);
        Assertions.assertFalse(resultado);

        per.setApellidosPersona(valprev);
        valprev=per.getSexoPersona();
        per.setSexoPersona(valor);
        resultado=nuevo?dao.agregaPersona(per):dao.actualizaPersona(per);
        Assertions.assertFalse(resultado);

        per.setSexoPersona(valprev);
        valprev=per.getCalleNumPersona();
        per.setCalleNumPersona(valor);
        resultado=nuevo?dao.agregaPersona(per):dao.actualizaPersona(per);
        Assertions.assertFalse(resultado);

        per.setCalleNumPersona(valprev);
        valprev=per.getTelefonoPersona();
        per.setTelefonoPersona(valor);
        resultado=nuevo?dao.agregaPersona(per):dao.actualizaPersona(per);
        Assertions.assertFalse(resultado);

        per.setTelefonoPersona(valprev);
        if (valor==null) {
            Date fechaprev=per.getFechaNacPersona();
            per.setFechaNacPersona(null);
            resultado=nuevo?dao.agregaPersona(per):dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);
            per.setFechaNacPersona(fechaprev);
        }

        valprev=per.getTipoPersona();
        per.setTipoPersona(valor);
        resultado=nuevo?dao.agregaPersona(per):dao.actualizaPersona(per);
        Assertions.assertFalse(resultado);

        per.setTipoPersona(valprev);
        valprev=per.getColoniaPersona();
        per.setColoniaPersona(valor);
        resultado=nuevo?dao.agregaPersona(per):dao.actualizaPersona(per);
        Assertions.assertTrue(resultado);

        Connection conn;
        Statement stmt=null;
        String sql=String.format("DELETE FROM persona WHERE email_persona='%s'", id);
        try {
            if (nuevo) {
                conn = conndbunit.getConnection();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            exsql.printStackTrace();
            Assertions.assertNull(exsql, "Problema al eliminar la persona que se agrego en la prueba");
        }


        per.setColoniaPersona(valprev);
        per.setCodpostalPersona(valor);
        resultado=nuevo?dao.agregaPersona(per):dao.actualizaPersona(per);
        Assertions.assertTrue(resultado);
        try {
            if (nuevo) {
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            exsql.printStackTrace();
            Assertions.assertNull(exsql, "Problema al eliminar la persona que se agrego en la prueba");
        }

    }

    @Test
    @Order(16)
    public void testPersonaAgregarInvalida()  {
        String[] email={"xxtat@yho.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String[] nom={"Mario","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] apellido={"Ramires","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] sexo ={"F","N"};
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

        java.sql.Date[] fechaSQL={java.sql.Date.valueOf("2000-10-10"),java.sql.Date.valueOf("2000-10-10")};
        Date[] fecha = {new Date(fechaSQL[0].getTime()), new Date(fechaSQL[1].getTime())};
        long[] idinst={1,350};
        String[] tipo={"Profesor","Teacher"};
        Persona per= new Persona(email[1],nom[0],apellido[0],sexo[0],calle[0],idmun[0],ident[0],tel[0],fecha[0],idinst[0],tipo[0]);
        per.setColoniaPersona(colonia[0]);
        per.setCodpostalPersona(codpostal[0]);

        boolean resultado;
        try {
            //validaNullo_Vacio(null,"xxuigg@demonio.com",true);
            validaNullo_VacioPersona("","xxuigg@demonio.com",true);

            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setEmailPersona(email[0]);
            per.setNombrePersona(nom[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setNombrePersona(nom[0]);
            per.setApellidosPersona(apellido[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setApellidosPersona(apellido[0]);
            per.setSexoPersona(sexo[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setSexoPersona(sexo[0]);
            per.setCalleNumPersona(calle[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setCalleNumPersona(calle[0]);
            per.setColoniaPersona(colonia[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setColoniaPersona(colonia[0]);
            per.setIdMunicipioPersona(idmun[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setIdMunicipioPersona(idmun[0]);
            per.setIdEntidadPersona(ident[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setIdEntidadPersona(ident[0]);
            per.setCodpostalPersona(codpostal[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setCodpostalPersona(codpostal[0]);
            per.setTelefonoPersona(tel[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setTelefonoPersona(tel[0]);
            per.setIdInstitucionPersona(idinst[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);

            per.setIdInstitucionPersona(idinst[0]);
            per.setTipoPersona(tipo[1]);
            resultado=dao.agregaPersona(per);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado=true;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoPersona");
        }
        Assertions.assertFalse(resultado);
        calif_persona[1]++;
    }


    @Test
    @Order(17)
    public void testPersonaActualizarValida() throws Exception {
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
        Date fecha=new Date(java.sql.Date.valueOf("2001-10-14").getTime());
        long idinst=1L;
        String tipo="Profesor";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        boolean resultado=false;
        try {
            resultado=dao.actualizaPersona(per);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaPersona de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("persona",
                "SELECT * FROM persona WHERE email_persona='misalas@itszn.edu.mx'");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("persona_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_persona[2]++;
    }

    @Test
    @Order(18)
    public void testPersonaActualizarInexistente()  {
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
        Date fecha=new Date(java.sql.Date.valueOf("2001-10-14").getTime());
        long idinst=1L;
        String tipo="Profesor";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);


        boolean resultado=true;
        try {
            resultado=dao.actualizaPersona(per);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaPersona de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_persona[2]++;
    }

    @Test
    @Order(19)
    public void testPersonaActualizarInvalida() {
        String[] email={"xxtat@yho.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String[] nom={"Mario","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] apellido={"Ramires","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] sexo={"F","N"};
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
        java.sql.Date[] fechaSQL={java.sql.Date.valueOf("2000-10-10"), java.sql.Date.valueOf("2000-10-10")};
        Date[] fecha = {new Date(fechaSQL[0].getTime()), new Date(fechaSQL[1].getTime())};
        long[] idinst={1,350};
        String[] tipo={"Profesor","Teacher"};
        Persona per= new Persona(email[1],nom[0],apellido[0],sexo[0],calle[0],idmun[0],ident[0],tel[0],fecha[0],idinst[0],tipo[0]);
        per.setColoniaPersona(colonia[0]);
        per.setCodpostalPersona(codpostal[0]);

        boolean resultado;
        try {
            //validaNullo_Vacio(null,"misalas@itszn.edu.mx",false);
            validaNullo_VacioPersona("","misalas@itszn.edu.mx",false);


            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setEmailPersona(email[0]);
            per.setNombrePersona(nom[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setNombrePersona(nom[0]);
            per.setApellidosPersona(apellido[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setApellidosPersona(apellido[0]);
            per.setSexoPersona(sexo[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setSexoPersona(sexo[0]);
            per.setCalleNumPersona(calle[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setCalleNumPersona(calle[0]);
            per.setColoniaPersona(colonia[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setColoniaPersona(colonia[0]);
            per.setIdMunicipioPersona(idmun[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setIdMunicipioPersona(idmun[0]);
            per.setIdEntidadPersona(ident[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setIdEntidadPersona(ident[0]);
            per.setCodpostalPersona(codpostal[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setCodpostalPersona(codpostal[0]);
            per.setTelefonoPersona(tel[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setTelefonoPersona(tel[0]);
            per.setIdInstitucionPersona(idinst[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);

            per.setIdInstitucionPersona(idinst[0]);
            per.setTipoPersona(tipo[1]);
            resultado=dao.actualizaPersona(per);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado=true;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoPersona");
        }

        Assertions.assertFalse(resultado);
        calif_persona[2]++;
    }

    @Test
    @Order(20)
    public void testPersonaEliminarValida() throws Exception {
        String id="misalas@itszn.edu.mx";

        try {
            boolean resultado=dao.eliminaPersona(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaPersona de DaoConcursosImpl");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createQueryTable("persona",
                "SELECT * FROM persona");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_persona[3]++;
    }


    @Test
    @Order(21)
    public void testPersonaEliminarInexistente()  {
        String id="xmisalas@itszn.edu.mx";

        try {
            boolean resultado=dao.eliminaPersona(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaPersona de DaoConcursosImpl");
        }
        calif_persona[3]++;
    }

    @Test
    @Order(22)
    public void testPersonaEliminarInvalida() {
        String id="charly@uaz.edu.mx";
        try {
            boolean resultado=dao.eliminaPersona(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoPersona");
        }
        calif_persona[3]++;
    }


    // DATOSESTUDIANTE

    private void comparaDatosEstudiante(DatosEstudiante actual, ITable expected) {
        try {
            Assertions.assertEquals(expected.getValue(0, "email_estudiante"),actual.getEmailEstudiante());
            Assertions.assertEquals(expected.getValue(0, "carrera_estudiante"),actual.getCarreraEstudiante());
            java.sql.Date f1;
            f1=new java.sql.Date(actual.getFechaInicioCarrera().getTime());

            Assertions.assertEquals(expected.getValue(0, "fecha_inicio_carrera"),f1.toString());
            f1=new java.sql.Date(actual.getFechaEsperadaTerminacion().getTime());
            Assertions.assertEquals(expected.getValue(0, "fecha_esperada_terminacion"),f1.toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar los datosestudiante");
        }
    }

    @Test
    @Order(23)
    public void testDatosEstudianteObten() throws Exception {
        String emailABuscar="charly@uaz.edu.mx";
        Optional<DatosEstudiante> actual = Optional.empty();
        try {
            actual=dao.obtenDatosEstudiante(emailABuscar);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenDatosEstudiante de DaoConcursosImpl");
        }
        Assertions.assertTrue(actual.isPresent());

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosestudiante_get.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");

        comparaDatosEstudiante(actual.get(),expectedTable);
        calif_datos_estudiante[0]++;
    }

    @Test
    @Order(24)
    public void testDatosEstudianteObtenInexistente()  {
        String emailABuscar="charlydaniel@uaz.edu.mx";
        Optional<DatosEstudiante> actual = Optional.empty();
        try {
            actual=dao.obtenDatosEstudiante(emailABuscar);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenDatosEstudiante de DaoConcursosImpl");
        }
        Assertions.assertFalse(actual.isPresent());
        calif_datos_estudiante[0]++;
    }

    @Test
    @Order(25)
    public void testDatosEstudianteAgregarValida() throws Exception {
        String email="newstudent@outlook.com";
        String carrera="Ing. en Informatica";
        //Date fechaInicio=new Date(java.sql.Date.valueOf("2019-08-12").getTime());
        //Date fechaFin=new Date(java.sql.Date.valueOf("2023-07-01").getTime());
        java.sql.Date fechaInicio=java.sql.Date.valueOf("2019-08-12");
        java.sql.Date fechaFin=java.sql.Date.valueOf("2023-07-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);

        boolean resultado;
        try {
            //dao.agregaPersona(per);
            resultado = dao.agregaDatosEstudiante(est);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaDatosEstudiante de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable=conndbunit.createQueryTable("datos_estudiante",
                "SELECT * FROM datos_estudiante WHERE email_estudiante='newstudent@outlook.com'");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosestudiantecomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_datos_estudiante[1]++;
    }


    private void validaNullo_VacioDatEst(String valor, String id, boolean nuevo) {
        String carrera="Ing. en Informatica";
        //Date fechaInicio=new Date(java.sql.Date.valueOf("2019-08-12").getTime());
        //Date fechaFin=new Date(java.sql.Date.valueOf("2023-07-01").getTime());
        java.sql.Date fechaInicio=java.sql.Date.valueOf("2019-08-12");
        java.sql.Date fechaFin=java.sql.Date.valueOf("2023-07-01");
        boolean resultado;
        String valprev;

        DatosEstudiante datest= new DatosEstudiante(id,carrera,fechaInicio,fechaFin);

        valprev=datest.getCarreraEstudiante();
        datest.setCarreraEstudiante(valor);
        resultado=nuevo?dao.agregaDatosEstudiante(datest):dao.actualizaDatosEstudiante(datest);
        Assertions.assertFalse(resultado);

        if (valor==null) {
            datest.setCarreraEstudiante(valprev);
            datest.setFechaInicioCarrera(null);
            resultado=nuevo?dao.agregaDatosEstudiante(datest):dao.actualizaDatosEstudiante(datest);
            Assertions.assertFalse(resultado);

            datest.setFechaInicioCarrera(fechaInicio);
            datest.setFechaEsperadaTerminacion(null);
            resultado=nuevo?dao.agregaDatosEstudiante(datest):dao.actualizaDatosEstudiante(datest);
            Assertions.assertFalse(resultado);
        }
    }

    @Test
    @Order(26)
    public void testDatosEstudianteAgregarInvalida()  {
        String[] email={"saucedokevin544@gmail.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String[] carrera={"Informatica","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        java.sql.Date[] fecha1={java.sql.Date.valueOf("2018-10-10"), java.sql.Date.valueOf("2018-10-10")};
        java.sql.Date[] fecha2={java.sql.Date.valueOf("2024-10-10"), java.sql.Date.valueOf("2020-10-10")};
        DatosEstudiante est= new DatosEstudiante(
                email[1],carrera[0],fecha1[0],fecha2[0]);

        boolean resultado;
        try {
            validaNullo_VacioDatEst("","saucedokevin544@gmail.com",true);

            resultado=dao.agregaDatosEstudiante(est);
            Assertions.assertFalse(resultado);

            est.setEmailEstudiante(email[0]);
            est.setCarreraEstudiante(carrera[1]);
            resultado=dao.agregaDatosEstudiante(est);
            Assertions.assertFalse(resultado);

            est.setCarreraEstudiante(carrera[0]);
            est.setFechaEsperadaTerminacion(fecha2[1]);
            resultado=dao.agregaDatosEstudiante(est);
            Assertions.assertFalse(resultado);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoDatosEstudiante");
            resultado=true;
        }
        Assertions.assertFalse(resultado);
        calif_datos_estudiante[1]++;
    }

    @Test
    @Order(27)
    public void testDatosEstudianteAgregarDuplicada()  {
        String email="newstudent@outlook.com";
        String carrera="Ing. en Informatica";
        //Date fechaInicio=new Date(java.sql.Date.valueOf("2019-08-12").getTime());
        //Date fechaFin=new Date(java.sql.Date.valueOf("2023-07-01").getTime());
        java.sql.Date fechaInicio=java.sql.Date.valueOf("2019-08-12");
        java.sql.Date fechaFin=java.sql.Date.valueOf("2023-07-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);

        boolean resultado;
        try {
            resultado=dao.agregaDatosEstudiante(est);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaDatosEstudiante de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_datos_estudiante[1]++;
    }


    @Test
    @Order(28)
    public void testDatosEstudianteActualizarValida() throws Exception {
        String email="newstudent@outlook.com";
        String carrera="Ing. en Informatica Admva";
        //Date fechaInicio=new Date(java.sql.Date.valueOf("2019-01-28").getTime());
        //Date fechaFin=new Date(java.sql.Date.valueOf("2023-02-01").getTime());
        java.sql.Date fechaInicio=java.sql.Date.valueOf("2019-01-28");
        java.sql.Date fechaFin=java.sql.Date.valueOf("2023-02-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);

        boolean resultado=false;
        try {
            resultado=dao.actualizaDatosEstudiante(est);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaDatosEstudiante de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("datos_estudiante",
                "SELECT * FROM datos_estudiante WHERE email_estudiante='newstudent@outlook.com'");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosestudiantecomp_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_datos_estudiante[2]++;
    }

    @Test
    @Order(29)
    public void testDatosEstudianteActualizarInexistente()  {
        String email="ulgaja@itszn.edu.mx";
        String carrera="Ing. en Informatica Admva";
        //Date fechaInicio=new Date(java.sql.Date.valueOf("2019-01-28").getTime());
        //Date fechaFin=new Date(java.sql.Date.valueOf("2023-02-01").getTime());
        java.sql.Date fechaInicio=java.sql.Date.valueOf("2019-01-28");
        java.sql.Date fechaFin=java.sql.Date.valueOf("2023-02-01");
        DatosEstudiante est = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);
        boolean resultado=true;
        try {
            resultado=dao.actualizaDatosEstudiante(est);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaDatosEstudiante de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_datos_estudiante[2]++;
    }

    @Test
    @Order(30)
    public void testDatosEstudianteActualizarInvalida() {
        String[] email={"saucedokevin544@gmail.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String[] carrera={"Informatica","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        java.sql.Date[] fecha1={java.sql.Date.valueOf("2018-10-10"), java.sql.Date.valueOf("2018-10-10")};
        java.sql.Date[] fecha2={java.sql.Date.valueOf("2024-10-10"), java.sql.Date.valueOf("2020-10-10")};
        DatosEstudiante est= new DatosEstudiante(
                email[1],carrera[0],fecha1[0],fecha2[0]);

        boolean resultado;
        try {
            //validaNullo_VacioDatEst(null,"lauratapia@outlook.com",false);
            validaNullo_VacioDatEst("","lauratapia@outlook.com",false);

            est.setEmailEstudiante(email[0]);
            est.setCarreraEstudiante(carrera[1]);
            resultado=dao.actualizaDatosEstudiante(est);
            Assertions.assertFalse(resultado);

            est.setCarreraEstudiante(carrera[0]);
            est.setFechaEsperadaTerminacion(fecha2[1]);
            resultado=dao.actualizaDatosEstudiante(est);
            Assertions.assertFalse(resultado);

        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoDatosEstudiante");
            System.err.println(ex.getMessage());
            resultado=true;
        }

        Assertions.assertFalse(resultado);
        calif_datos_estudiante[2]++;
    }

    @Test
    @Order(31)
    public void testDatosEstudianteEliminarValida() throws Exception {
        String id="newstudent@outlook.com";
        try {
            boolean resultado=dao.eliminaDatosEstudiante(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaDatosEstudiante de DaoConcursosImpl");
        }

        ITable actualTable=conndbunit.createQueryTable("datos_estudiante",
                "SELECT * FROM datos_estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("datos_estudiante");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_datos_estudiante[3]++;
    }


    @Test
    @Order(32)
    public void testDatosEstudianteEliminaInexistente() {
        String id="xmisalas@itszn.edu.mx";
        try {
            boolean resultado=dao.eliminaDatosEstudiante(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaDatosEstudiante de DaoConcursosImpl");
        }
        calif_datos_estudiante[3]++;
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
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar las sedes");
        }
    }

    @Test
    @Order(33)
    public void testSedeObten() throws Exception {
        List<Sede> actual;
        try {
            actual=dao.obtenSedes();
        }
        catch (Exception ex) {
            actual=new ArrayList<>();
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenSedes de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaSede(actual.get(i), expectedTable,i);
        }
        calif_sede[0]++;
    }

    @Test
    @Order(34)
    public void testSedeAgregarValida() throws Exception {
        int id=5;
        String nom="UPIIZ Club 2";
        long idInst=2;
        String email="alexac@upiiz.ipn.mx";
        String url="http://www.upiiz.edu.mx/icpc2";
        Sede sede = new Sede(id,nom,idInst,email);
        sede.setUrlSede(url);

        boolean resultado;
        try {
            resultado = dao.agregaSede(sede);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaSede de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("sede",
                "SELECT * FROM sede");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sedecomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_sede[1]++;
    }

    @Test
    @Order(35)
    public void testSedeAgregarDuplicada()  {
        int id=5;
        String nom="UPIIZ Club 2";
        long idInst=2;
        String email="alexac@upiiz.ipn.mx";
        String url="http://www.upiiz.edu.mx/icpc2";
        Sede sede = new Sede(id,nom,idInst,email);
        sede.setUrlSede(url);

        boolean resultado;
        try {
            resultado=dao.agregaSede(sede);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaSede de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_sede[1]++;
    }


    private void validaNullo_VacioSede(String valor, int idsede, boolean nuevo) {
        String nom="SEDE POTENCIA";
        String email="rsolis@uaz.edu.mx";
        long idinst=1;
        String url="http://inexistente.com";
        boolean resultado;

        Sede sede= new Sede(idsede,valor,idinst,email);
        sede.setUrlSede(url);

        resultado=nuevo?dao.agregaSede(sede):dao.actualizaSede(sede);
        Assertions.assertFalse(resultado);

        sede.setNombreSede(nom);
        sede.setEmailDirectorSede(valor);
        resultado=nuevo?dao.agregaSede(sede):dao.actualizaSede(sede);
        Assertions.assertFalse(resultado);

        sede.setEmailDirectorSede(email);
        sede.setUrlSede(valor);
        resultado=nuevo?dao.agregaSede(sede):dao.actualizaSede(sede);
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
            exsql.printStackTrace();
            Assertions.assertNull(exsql, "Problema al eliminar la sede que se agrego en la prueba");
        }


    }

    @Test
    @Order(36)
    public void testSedeAgregarInvalida() {
        String[] nombre={"Sede Poderosa","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String largo="ESTEVALORDEURLDEBERIADESERTOTALMENTEINVALIDOPUESESTALARGUISIMO";
        largo += largo; largo += largo; largo += largo; largo += largo; largo += largo; largo += largo;
        String[] url = {"http://www.uaz.edu.mx", largo};
        String[] email={"rsolis@uaz.edu.mx","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String emailEst="charly@uaz.edu.mx";
        long[] idInst={3,1};
        int idsede=10;
        Sede sede = new Sede(idsede,nombre[1],idInst[0],email[0]);
        sede.setUrlSede(url[0]);

        boolean resultado;
        try {
            //validaNullo_Vacio(null,30,true);
            validaNullo_VacioSede("",30, true);

            resultado=dao.agregaSede(sede);
            Assertions.assertFalse(resultado);

            sede.setNombreSede(nombre[0]);
            resultado=dao.agregaSede(sede);
            Assertions.assertFalse(resultado);

            sede.setEmailDirectorSede(email[1]);
            resultado=dao.agregaSede(sede);
            Assertions.assertFalse(resultado);

            sede.setEmailDirectorSede(emailEst);
            sede.setIdInstitucionSede(idInst[1]);
            resultado=dao.agregaSede(sede);
            Assertions.assertFalse(resultado);

            sede.setUrlSede(url[1]);
            resultado=dao.agregaSede(sede);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado=true;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoSede");
        }
        Assertions.assertFalse(resultado);
        calif_sede[1]++;
    }

    @Test
    @Order(37)
    public void testSedeActualizarValida() throws Exception {
        int id=5;
        String nom="ICPC UPIIZ 2";
        long idInst=2;
        String email="alexac@upiiz.ipn.mx";
        String url="http://www.upiiz.ipn.edu.mx/icpc2";
        Sede sede = new Sede(id,nom,idInst,email);
        sede.setUrlSede(url);

        boolean resultado=false;
        try {
            resultado=dao.actualizaSede(sede);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaSede de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("sede",
                "SELECT * FROM sede WHERE id_sede>4");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sedecomp_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_sede[2]++;
    }

    @Test
    @Order(38)
    public void testSedeActualizarInexistente()  {
        int id=7;
        String nom="ICPC UPIIZ 2";
        long idInst=2;
        String email="alexac@upiiz.ipn.mx";
        String url="http://www.upiiz.ipn.edu.mx/icpc2";
        Sede sede = new Sede(id,nom,idInst,email);
        sede.setUrlSede(url);

        boolean resultado=true;
        try {
            resultado=dao.actualizaSede(sede);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaSede de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_sede[2]++;
    }

    @Test
    @Order(39)
    public void testSedeActualizarInvalida() {
        String[] nombre={"Sede Poderosa","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String largo="ESTEVALORDEURLDEBERIADESERTOTALMENTEINVALIDOPUESESTALARGUISIMO";
        largo += largo; largo += largo; largo += largo; largo += largo; largo += largo; largo += largo;
        String[] url = {"http://www.uaz.edu.mx", largo};
        String[] email={"rsolis@uaz.edu.mx","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String emailEst="charly@uaz.edu.mx";
        long[] idInst={3,1};
        int idsede=5;
        Sede sede = new Sede(idsede,nombre[1],idInst[0],email[0]);
        sede.setUrlSede(url[0]);

        boolean resultado;
        try {
            //validaNullo_VacioSede(null,3,false);
            validaNullo_VacioSede("",5, false);

            resultado=dao.actualizaSede(sede);
            Assertions.assertFalse(resultado);

            sede.setNombreSede(nombre[0]);
            resultado=dao.actualizaSede(sede);
            Assertions.assertFalse(resultado);

            sede.setEmailDirectorSede(email[1]);
            resultado=dao.actualizaSede(sede);
            Assertions.assertFalse(resultado);

            sede.setEmailDirectorSede(emailEst);
            sede.setIdInstitucionSede(idInst[1]);
            resultado=dao.actualizaSede(sede);
            Assertions.assertFalse(resultado);

            sede.setUrlSede(url[1]);
            resultado=dao.actualizaSede(sede);
            Assertions.assertFalse(resultado);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado=true;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoSede");
        }

        Assertions.assertFalse(resultado);
        calif_sede[2]++;
    }

    @Test
    @Order(40)
    public void testSedeEliminarValida() throws Exception {
        long id=5;
        try {
            boolean resultado=dao.eliminaSede(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaSede de DaoConcursosImpl");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createQueryTable("sede",
                "SELECT * FROM sede");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_sede[3]++;
    }


    @Test
    @Order(41)
    public void testSedeEliminarInexistente()  {
        long id=16L;
        try {
            boolean resultado=dao.eliminaSede(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaSede de DaoConcursosImpl");
        }
        calif_sede[3]++;
    }

    @Test
    @Order(42)
    public void testSedeEliminarSedeInvalida()  {
        long id=1L;
        try {
            boolean resultado=dao.eliminaSede(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoSede");
        }
        calif_sede[3]++;
    }


    /// CONCURSO

    private void comparaConcurso(Concurso actual, ITable expected, int numrow) {
        try {
            Assertions.assertEquals(expected.getValue(numrow, "id_concurso").toString(),String.valueOf(actual.getIdConcurso()));
            Assertions.assertEquals(expected.getValue(numrow, "nombre_concurso"),actual.getNombreConcurso());
            java.sql.Date f1;
            f1=new java.sql.Date(actual.getFechaConcurso().getTime());
            Assertions.assertEquals(expected.getValue(numrow, "fecha_concurso"),f1.toString());

            f1=new java.sql.Date(actual.getFechaInicioRegistro().getTime());
            Assertions.assertEquals(expected.getValue(numrow, "fecha_inicio_registro"),f1.toString());

            f1=new java.sql.Date(actual.getFechaFinRegistro().getTime());
            Assertions.assertEquals(expected.getValue(numrow, "fecha_fin_registro"),f1.toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar los concursos");
        }
    }

    @Test
    @Order(43)
    public void testConcursoObten() throws Exception {
        List<Concurso> actual;
        try {
            actual=dao.obtenConcursos();
        }
        catch (Exception ex) {
            actual=new ArrayList<>();
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenConcursos de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaConcurso(actual.get(i), expectedTable,i);
        }
        calif_concurso[0]++;
    }

    @Test
    @Order(44)
    public void testConcursoAgregarValida() throws Exception {
        long id=4;
        String nom="Coding Cup TecNM";
        Date fecha=new Date(java.sql.Date.valueOf("2020-11-06").getTime());
        Date fechaIni=new Date(java.sql.Date.valueOf("2020-10-15").getTime());
        Date fechaFin=new Date(java.sql.Date.valueOf("2020-11-03").getTime());
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        boolean resultado;
        try {
            resultado = dao.agregaConcurso(conc);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaConcurso de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("concurso",
                "SELECT * FROM concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursocomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_concurso[1]++;
    }

    @Test
    @Order(45)
    public void testConcursoAgregarDuplicada()  {
        long id=4;
        String nom="Coding Cup TecNM";
        Date fecha=new Date(java.sql.Date.valueOf("2020-11-06").getTime());
        Date fechaIni=new Date(java.sql.Date.valueOf("2020-10-15").getTime());
        Date fechaFin=new Date(java.sql.Date.valueOf("2020-11-03").getTime());
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        boolean resultado;
        try {
            resultado=dao.agregaConcurso(conc);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaConcurso de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_concurso[1]++;
    }

    private void validaNullo_VacioConcurso(String valor, long idconc, boolean nuevo) {
        String nom="Coding Cup TecNM";
        Date fecha=new Date(java.sql.Date.valueOf("2020-11-06").getTime());
        Date fechaIni=new Date(java.sql.Date.valueOf("2020-10-15").getTime());
        Date fechaFin=new Date(java.sql.Date.valueOf("2020-11-03").getTime());
        Concurso conc = new Concurso(idconc,valor,fecha,fechaIni,fechaFin);
        boolean resultado;

        resultado=nuevo?dao.agregaConcurso(conc): dao.actualizaConcurso(conc);
        Assertions.assertFalse(resultado);

        conc.setNombreConcurso(nom);
        if (valor==null) {
            conc.setFechaConcurso(null);
            resultado=nuevo?dao.agregaConcurso(conc): dao.actualizaConcurso(conc);
            Assertions.assertFalse(resultado);

            conc.setFechaConcurso(fecha);
            conc.setFechaInicioRegistro(null);
            resultado=nuevo?dao.agregaConcurso(conc): dao.actualizaConcurso(conc);
            Assertions.assertFalse(resultado);

            conc.setFechaInicioRegistro(fechaIni);
            conc.setFechaFinRegistro(null);
            resultado=nuevo?dao.agregaConcurso(conc): dao.actualizaConcurso(conc);
            Assertions.assertFalse(resultado);
        }
    }

    @Test
    @Order(46)
    public void testConcursoAgregarInvalida()  {
        String[] nombre={"Concurso Poderoso","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};

        Date[] fecha = {new Date(java.sql.Date.valueOf("2020-11-05").getTime()),
                new Date(java.sql.Date.valueOf("2020-10-31").getTime())};
        Date fechaIniVal=new Date(java.sql.Date.valueOf("2020-10-15").getTime());
        Date fechaIniInval=new Date(java.sql.Date.valueOf("2020-10-31").getTime());
        Date fechaFinVal=new Date(java.sql.Date.valueOf("2020-10-31").getTime());
        Date fechaFinInval=new Date(java.sql.Date.valueOf("2020-10-15").getTime());

        long idconc=10L;
        Concurso conc = new Concurso(idconc,nombre[1],fecha[0],fechaIniVal,fechaFinVal);

        boolean resultado;
        try {
            //validaNullo_VacioConcurso(null,30,true);
            validaNullo_VacioConcurso("",30, true);

            resultado=dao.agregaConcurso(conc);
            Assertions.assertFalse(resultado);

            conc.setNombreConcurso(nombre[0]);
            conc.setFechaFinRegistro(fechaFinInval);
            resultado=dao.agregaConcurso(conc);
            Assertions.assertFalse(resultado);

            conc.setFechaFinRegistro(fechaFinVal);
            conc.setFechaInicioRegistro(fechaIniInval);
            resultado=dao.agregaConcurso(conc);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            resultado=true;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoConcurso");
        }
        Assertions.assertFalse(resultado);
        calif_concurso[1]++;
    }

    @Test
    @Order(47)
    public void testConcursoActualizarValida() throws Exception {
        long id=4;
        String nom="Coding Cup TecNM 2020";
        Date fecha=new Date(java.sql.Date.valueOf("2020-11-04").getTime());
        Date fechaIni=new Date(java.sql.Date.valueOf("2020-10-13").getTime());
        Date fechaFin=new Date(java.sql.Date.valueOf("2020-11-01").getTime());

        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        boolean resultado=false;
        try {
            resultado=dao.actualizaConcurso(conc);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaConcurso de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("concurso",
                "SELECT * FROM concurso WHERE id_concurso>3");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursocomp_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_concurso[2]++;
    }

    @Test
    @Order(48)
    public void testConcursoActualizarInexistente()  {
        long id=5;
        String nom="Coding Cup TecNM 2020";
        Date fecha=new Date(java.sql.Date.valueOf("2020-11-04").getTime());
        Date fechaIni=new Date(java.sql.Date.valueOf("2020-10-13").getTime());
        Date fechaFin=new Date(java.sql.Date.valueOf("2020-11-01").getTime());
        Concurso conc = new Concurso(id,nom,fecha,fechaIni,fechaFin);

        boolean resultado=true;
        try {
            resultado=dao.actualizaConcurso(conc);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaConcurso de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_concurso[2]++;
    }

    @Test
    @Order(49)
    public void testConcursoActualizarInvalida()  {
        String[] nombre={"Concurso Poderoso","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        Date[] fecha = {new Date(java.sql.Date.valueOf("2020-11-05").getTime()),
                new Date(java.sql.Date.valueOf("2020-10-31").getTime())};
        Date[] fechaIni = {new Date(java.sql.Date.valueOf("2020-10-15").getTime()),
                new Date(java.sql.Date.valueOf("2020-10-31").getTime())};
        Date[] fechaFin = {new Date(java.sql.Date.valueOf("2020-10-31").getTime()),
                new Date(java.sql.Date.valueOf("2020-10-15").getTime())};

        long idconc=10L;
        Concurso conc = new Concurso(idconc,nombre[1],fecha[0],fechaIni[0],fechaFin[0]);

        boolean resultado;

        try {
            //validaNullo_VacioConcurso(null,3,false);
            validaNullo_VacioConcurso("",3, false);

            resultado=dao.actualizaConcurso(conc);
            Assertions.assertFalse(resultado);

            conc.setNombreConcurso(nombre[0]);
            conc.setFechaFinRegistro(fechaFin[1]);
            resultado=dao.actualizaConcurso(conc);
            Assertions.assertFalse(resultado);

            conc.setFechaFinRegistro(fechaFin[0]);
            conc.setFechaFinRegistro(fechaIni[1]);
            resultado=dao.actualizaConcurso(conc);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado=true;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoConcurso");
        }

        Assertions.assertFalse(resultado);
        calif_concurso[2]++;
    }

    @Test
    @Order(50)
    public void testConcursoEliminarValida() throws Exception {
        long id=4;
        try {
            boolean resultado=dao.eliminaConcurso(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaConcurso de DaoConcursosImpl");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createQueryTable("concurso",
                "SELECT * FROM concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_concurso[3]++;
    }


    @Test
    @Order(51)
    public void testConcursoEliminarInexistente()  {
        long id=16L;
        try {
            boolean resultado=dao.eliminaConcurso(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaConcurso de DaoConcursosImpl");
        }
        calif_concurso[3]++;
    }

    @Test
    @Order(52)
    public void testConcursoEliminarInvalida()  {
        long id=1L;
        try {
            boolean resultado=dao.eliminaConcurso(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoConcurso");
        }
        calif_concurso[3]++;
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
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion comparar los equipos");
        }
    }

    @Test
    @Order(53)
    public void testEquipoObten() throws Exception {
        List<Equipo> actual;
        try {
            actual=dao.obtenEquipos();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenEquipos de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");

        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaEquipo(actual.get(i), expectedTable,i);
        }
        calif_equipo[0]++;
    }

    @Test
    @Order(54)
    public void testEquipoAgregarValida() throws Exception {
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

        boolean resultado;
        try {
            resultado = dao.agregaEquipo(eq);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaEquipo de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable=conndbunit.createQueryTable("equipo",
                "SELECT * FROM equipo WHERE id_equipo>8");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipocomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_equipo[1]++;
    }

    @Test
    @Order(55)
    public void testEquipoAgregarDuplicada()  {
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

        boolean resultado;
        try {
            resultado=dao.agregaEquipo(eq);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaEquipo de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_equipo[1]++;
    }

    private void validaNullo_VacioEquipo( String valor, long ideq, boolean nuevo) {
        String nom="Code Avengers";
        String emailC="rsolis@uaz.edu.mx";
        String email1="hjesus@hotmail.com";
        String email2="charly@uaz.edu.mx";
        String email3="joyce@hotmail.com";
        String emailR="lalo@uaz.edu.mx";
        long idInst=1;

        Equipo eq = new Equipo(ideq,valor,emailC,email1,email2,email3,idInst);
        eq.setEmailConcursanteReserva(emailR);
        boolean resultado;

        resultado=nuevo?dao.agregaEquipo(eq):dao.actualizaEquipo(eq);
        Assertions.assertFalse(resultado);

        eq.setNombreEquipo(nom);
        eq.setEmailCoach(valor);
        resultado=nuevo?dao.agregaEquipo(eq):dao.actualizaEquipo(eq);
        Assertions.assertFalse(resultado);

        eq.setEmailCoach(emailC);
        eq.setEmailConcursante1(valor);
        resultado=nuevo?dao.agregaEquipo(eq):dao.actualizaEquipo(eq);
        Assertions.assertFalse(resultado);

        eq.setEmailConcursante1(email1);
        eq.setEmailConcursante2(valor);
        resultado=nuevo?dao.agregaEquipo(eq):dao.actualizaEquipo(eq);
        Assertions.assertFalse(resultado);

        eq.setEmailConcursante2(email2);
        eq.setEmailConcursante3(valor);
        resultado=nuevo?dao.agregaEquipo(eq):dao.actualizaEquipo(eq);
        Assertions.assertFalse(resultado);

        eq.setEmailConcursante3(email3);
        eq.setEmailConcursanteReserva(valor);
        resultado=nuevo?dao.agregaEquipo(eq):dao.actualizaEquipo(eq);
        Assertions.assertTrue(resultado);

        Connection conn;
        Statement stmt;
        String sql=String.format("DELETE FROM equipo WHERE id_equipo=%d",ideq);
        try {
            if (nuevo) {
                conn = conndbunit.getConnection();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            exsql.printStackTrace();
            Assertions.assertNull(exsql, "Problema al eliminar el equipo que se agrego en la prueba");
        }

    }

    @Test
    @Order(56)
    public void testEquipoAgregarInvalida()  {
        String[] nombre={"Equipo Poderoso","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] email={"xxtat@yho.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String[] emailExistentesInst1={"rsolis@uaz.edu.mx","charly@uaz.edu.mx","lalo@uaz.edu.mx","hjesus@hotmail.com", "joyce@hotmail.com"};
        String[] emailExistentesInst3={"ontiveros@itszo.edu.mx","dianamtz@yahoo.com.mx"};
        long ideq=10L;
        long idinst=1;
        Equipo equipo = new Equipo(ideq,nombre[1],email[0],email[0],email[0],email[0],idinst);
        equipo.setEmailConcursanteReserva(email[0]);

        boolean resultado;
        try {
            //validaNullo_VacioEquipo(null,30,true);
            validaNullo_VacioEquipo("",30, true);

            resultado=dao.agregaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setNombreEquipo(nombre[0]);
            equipo.setEmailCoach(email[1]);
            resultado=dao.agregaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(email[0]);
            equipo.setEmailConcursante1(email[1]);
            resultado=dao.agregaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante1(email[0]);
            equipo.setEmailConcursante2(email[1]);
            resultado=dao.agregaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante2(email[0]);
            equipo.setEmailConcursante3(email[1]);
            resultado=dao.agregaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante3(email[0]);
            equipo.setEmailConcursanteReserva(email[1]);
            resultado=dao.agregaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[1]);
            equipo.setEmailConcursante1(emailExistentesInst1[0]);
            equipo.setEmailConcursante2(emailExistentesInst1[2]);
            equipo.setEmailConcursante3(emailExistentesInst1[3]);
            equipo.setEmailConcursanteReserva(emailExistentesInst1[4]);
            resultado=dao.agregaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[0]);
            equipo.setEmailConcursante1(emailExistentesInst1[2]);
            resultado=dao.agregaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst3[0]);
            equipo.setEmailConcursante1(emailExistentesInst1[1]);
            resultado=dao.agregaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[0]);
            equipo.setEmailConcursante1(emailExistentesInst3[1]);
            resultado=dao.agregaEquipo(equipo);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoEquipo");
        }

        calif_equipo[1]++;
    }

    @Test
    @Order(57)
    public void testEquipoActualizarValida() throws Exception {
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

        boolean resultado=false;
        try {
            resultado=dao.actualizaEquipo(eq);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaEquipo de DaoConcursosImpl");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("equipo",
                "SELECT * FROM equipo WHERE id_equipo>8");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipocomp_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_equipo[2]++;
    }

    @Test
    @Order(58)
    public void testEquipoActualizarInexistente()  {
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

        boolean resultado=true;
        try {
            resultado=dao.actualizaEquipo(eq);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo actualizaEquipo de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_equipo[2]++;
    }

    @Test
    @Order(59)
    public void testEquipoActualizarInvalida()  {
        String[] nombre={"Equipo Poderoso","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] email={"xxtat@yho.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String[] emailExistentesInst1={"rsolis@uaz.edu.mx","charly@uaz.edu.mx","lalo@uaz.edu.mx","hjesus@hotmail.com", "joyce@hotmail.com"};
        String[] emailExistentesInst3={"ontiveros@itszo.edu.mx","dianamtz@yahoo.com.mx"};
        long ideq=9L;
        long idinst=1;
        Equipo equipo = new Equipo(ideq,nombre[1],email[0],email[0],email[0],email[0],idinst);
        equipo.setEmailConcursanteReserva(email[0]);

        boolean resultado;

        try {
            //validaNullo_VacioEquipo(null,3,false);
            validaNullo_VacioEquipo("",9, false);

            resultado=dao.actualizaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setNombreEquipo(nombre[0]);
            equipo.setEmailCoach(email[1]);
            resultado=dao.actualizaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(email[0]);
            equipo.setEmailConcursante1(email[1]);
            resultado=dao.actualizaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante1(email[0]);
            equipo.setEmailConcursante2(email[1]);
            resultado=dao.actualizaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante2(email[0]);
            equipo.setEmailConcursante3(email[1]);
            resultado=dao.actualizaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante3(email[0]);
            equipo.setEmailConcursanteReserva(email[1]);
            resultado=dao.actualizaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[1]);
            equipo.setEmailConcursante1(emailExistentesInst1[0]);
            equipo.setEmailConcursante2(emailExistentesInst1[2]);
            equipo.setEmailConcursante3(emailExistentesInst1[3]);
            equipo.setEmailConcursanteReserva(emailExistentesInst1[4]);
            resultado=dao.actualizaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[0]);
            equipo.setEmailConcursante1(emailExistentesInst1[2]);
            resultado=dao.actualizaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst3[0]);
            equipo.setEmailConcursante1(emailExistentesInst1[1]);
            resultado=dao.actualizaEquipo(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[0]);
            equipo.setEmailConcursante1(emailExistentesInst3[1]);
            resultado=dao.actualizaEquipo(equipo);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoEquipo");
        }
        calif_equipo[2]++;
    }

    @Test
    @Order(60)
    public void testEquipoEliminarValida() throws Exception {
        long id=9;
        try {
            boolean resultado=dao.eliminaEquipo(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaEquipo de DaoConcursosImpl");
        }

        ITable actualTable=conndbunit.createQueryTable("equipo",
                "SELECT * FROM equipo WHERE id_equipo>8");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipocomp_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_equipo[3]++;
    }


    @Test
    @Order(61)
    public void testEquipoEliminarInexistente()  {
        long id=16L;
        try {
            boolean resultado=dao.eliminaEquipo(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaEquipo de DaoConcursosImpl");
        }
        calif_equipo[3]++;
    }

    @Test
    @Order(62)
    public void testEquipoEliminarInvalida()  {
        long id=2;
        try {
            boolean resultado=dao.eliminaEquipo(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoEquipo");
        }
        calif_equipo[3]++;
    }


    /// SEDECONCURSO

    @Test
    @Order(63)
    public void testXSedeConcursoAgregarValida() throws Exception {
        long id=4;
        long idsede=3;
        long idconcurso=3;
        SedeConcurso sc = new SedeConcurso(id, idsede,idconcurso);

        boolean resultado;
        try {
            resultado = dao.agregaSedeConcurso(sc);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaSedeConcurso de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("sede_concurso",
                "SELECT id_sede,id_concurso FROM sede_concurso ORDER BY id_sede_concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("sede_concursocomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede_concurso");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_sede_concurso[1]++;
    }

    @Test
    @Order(64)
    public void testXSedeConcursoAgregarDuplicada()  {
        long id=4;
        long idsede=3;
        long idconcurso=3;
        SedeConcurso sc = new SedeConcurso(id, idsede,idconcurso);

        boolean resultado;
        try {
            resultado=dao.agregaSedeConcurso(sc);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo agregaSedeConcurso de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_sede_concurso[1]++;
    }

    @Test
    @Order(65)
    public void testXSedeConcursoAgregarInvalida()  {
        long id=100;
        long idsedeValida=2;
        long idconcursoValido=2;
        long idsedeInvalida=100;
        long idconcursoInvalido=100;

        SedeConcurso sc= new SedeConcurso(id,idsedeInvalida,idconcursoValido);
        boolean resultado;
        try {
            resultado=dao.agregaSedeConcurso(sc);
            Assertions.assertFalse(resultado);

            sc.setIdSede(idsedeValida);
            sc.setIdConcurso(idconcursoInvalido);
            resultado=dao.agregaSedeConcurso(sc);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoSedeConcurso");
        }

        calif_sede_concurso[1]++;
    }

    @Test
    @Order(66)
    public void testXSedeConcursoEliminarValida() throws Exception {
        long id=4;
        try {
            boolean resultado=dao.eliminaSedeConcurso(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaSedeConcurso de DaoConcursosImpl");
        }

        ITable actualTable=conndbunit.createQueryTable("sede_concurso",
                "SELECT * FROM sede_concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("sede_concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_sede_concurso[3]++;
    }


    @Test
    @Order(67)
    public void testXSedeConcursoEliminarInexistente()  {
        long id=16L;
        try {
            boolean resultado=dao.eliminaSedeConcurso(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo eliminaSedeConcurso de DaoConcursosImpl");
        }
        calif_sede_concurso[3]++;
    }

    @Test
    @Order(69)
    public void testXSedeConcursoEliminarInvalida()  {
        long id=3L;
        try {
            boolean resultado=dao.eliminaSedeConcurso(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoSedeConcurso");
        }
        calif_sede_concurso[3]++;
    }

    /// EQUIPOSSEDECONCURSO

    @Test
    @Order(68)
    public void testXEquipoSedeConcursoAgregarValida() throws Exception {
        long id=6;
        long idequipo=1;
        long idsedeconcurso=2;

        EquiposSedeConcurso sc = new EquiposSedeConcurso(id, idequipo,idsedeconcurso);

        boolean resultado;
        try {
            resultado = dao.registrarEquipoSedeConcurso(sc);
        }
        catch (Exception ex) {
            resultado=false;
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo registrarEquipoSedeConcurso de DaoConcursosImpl");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("equipos_sede_concurso",
                "SELECT id_equipo,id_sede_concurso FROM equipos_sede_concurso ORDER BY id_equipo_sede_concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipos_sede_concursocomp_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipos_sede_concurso");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_equipos_sede_concurso[1]++;
    }

    @Test
    @Order(69)
    public void testXEquipoSedeConcursoAgregarDuplicada() {
        long id=6;
        long idequipo=1;
        long idsedeconcurso=2;

        EquiposSedeConcurso sc = new EquiposSedeConcurso(id, idequipo,idsedeconcurso);

        boolean resultado;
        try {
            resultado=dao.registrarEquipoSedeConcurso(sc);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo registrarEquipoSedeConcurso de DaoConcursosImpl");
        }
        Assertions.assertFalse(resultado);
        calif_equipos_sede_concurso[1]++;
    }

    @Test
    @Order(70)
    public void testXEquipoSedeConcursoAgregarInvalida()  {
        long id=2;
        long idequipovalido=1;
        long idequipoinvalido=5000;
        long idsedeconcursoval=1;
        long idsedeconcursoinval=1000;

        EquiposSedeConcurso sc= new EquiposSedeConcurso(id,idequipoinvalido,idsedeconcursoval);

        boolean resultado;
        try {
            resultado=dao.registrarEquipoSedeConcurso(sc);
            Assertions.assertFalse(resultado);

            sc.setIdEquipo(idequipovalido);
            sc.setIdSedeConcurso(idsedeconcursoinval);
            resultado=dao.registrarEquipoSedeConcurso(sc);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoEquiposSedeConcurso");
        }

        calif_equipos_sede_concurso[1]++;
    }

    @Test
    @Order(71)
    public void testXEquipoSedeConcursoEliminarValida() throws Exception {
        long id=6;
        try {
            boolean resultado=dao.cancelarEquipoSedeConcurso(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo cancelarEquipoSedeConcurso de DaoConcursosImpl");
        }

        ITable actualTable=conndbunit.createQueryTable("equipos_sede_concurso",
                "SELECT * FROM equipos_sede_concurso");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("concursosv3.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipos_sede_concurso");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_equipos_sede_concurso[3]++;
    }


    @Test
    @Order(72)
    public void testXEquipoSedeConcursoEliminarInexistente()  {
        long id=16L;
        try {
            boolean resultado=dao.cancelarEquipoSedeConcurso(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo cancelarEquipoSedeConcurso de DaoConcursosImpl");
        }
        calif_equipos_sede_concurso[3]++;
    }

    private void comparaEmails(List<String> actual, ITable expected) {
        try {
            Assertions.assertEquals(expected.getRowCount(),actual.size());
            for (int i = 0; i < actual.size(); i++) {
                Assertions.assertEquals(expected.getValue(i, "email_persona"),actual.get(i));
            }
        }
        catch (DataSetException exds) {
            exds.printStackTrace();
            Assertions.assertNull(exds, "No deberia generar excepcion comparar los emails");
        }
    }

    @Test
    @Order(73)
    public void testEmailsObten() throws Exception {
        List<String> actual1=null,actual2=null,actual3=null;

        try {
            actual1=dao.obtenCorreosDeInstitucion(1,"Estudiante");
            actual2=dao.obtenCorreosDeInstitucion(1,"Profesor");
            actual3=dao.obtenCorreosDeInstitucion(3,"Estudiante");

        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenCorreosDeInstitucion de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosmetodosespeciales.xml"));
        ITable expectedTable1=expectedDataSet.getTable("correos1_e");
        ITable expectedTable2=expectedDataSet.getTable("correos1_p");
        ITable expectedTable3=expectedDataSet.getTable("correos3_e");

        comparaEmails(actual1,expectedTable1);
        comparaEmails(actual2,expectedTable2);
        comparaEmails(actual3,expectedTable3);
        calif_emails += PORCENTAJE_EMAILS;
    }

    private void comparaTablaSede(List<Sede> actual, ITable expectedTable) {
        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaSede(actual.get(i), expectedTable,i);
        }
    }


    @Test
    @Order(74)
    public void testYSedesDisponibles() throws Exception {
        List<Sede> actual1=null,actual2=null,actual3=null;

        try {
            actual1=dao.obtenSedesDisponibles(1);
            actual2=dao.obtenSedesDisponibles(2);
            actual3=dao.obtenSedesDisponibles(3);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenSedesDisponibles de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosmetodosespeciales.xml"));
        ITable expectedTable1=expectedDataSet.getTable("sededisp_1");
        ITable expectedTable2=expectedDataSet.getTable("sededisp_2");
        ITable expectedTable3=expectedDataSet.getTable("sededisp_3");

        comparaTablaSede(actual1,expectedTable1);
        comparaTablaSede(actual2,expectedTable2);
        comparaTablaSede(actual3,expectedTable3);
        calif_sedesdisp += PORCENTAJE_SEDESDISP;
    }

    private void comparaSedeConcursoExtendida(List<SedeConcursoExtendida> actual, ITable expected) {
        try {
            Assertions.assertEquals(expected.getRowCount(),actual.size());
            for (int i = 0; i < actual.size(); i++) {
                Assertions.assertEquals(expected.getValue(i, "id_sede_concurso").toString(),String.valueOf(actual.get(i).getIdSedeConcurso()));
                Assertions.assertEquals(expected.getValue(i, "id_sede").toString(),String.valueOf(actual.get(i).getIdSede()));
                Assertions.assertEquals(expected.getValue(i, "id_concurso").toString(),String.valueOf(actual.get(i).getIdConcurso()));
                Assertions.assertEquals(expected.getValue(i, "nombre_sede"),actual.get(i).getNombreSede());
                Assertions.assertEquals(expected.getValue(i, "nombre_concurso"),actual.get(i).getNombreConcurso());
            }
        }
        catch (DataSetException exds) {
            exds.printStackTrace();
            Assertions.assertNull(exds, "No deberia generar excepcion comparar las SedesConcursoExtendidas");
        }
    }

    @Test
    @Order(75)
    public void testYSedesAsignadas() throws Exception {
        List<SedeConcursoExtendida> actual1=null,actual2=null,actual3=null;

        try {
            actual1=dao.obtenSedesAsignadas(1);
            actual2=dao.obtenSedesAsignadas(2);
            actual3=dao.obtenSedesAsignadas(3);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo obtenSedesAsignadas de DaoConcursosImpl");
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosmetodosespeciales.xml"));
        ITable expectedTable1=expectedDataSet.getTable("sedeasign_1");
        ITable expectedTable2=expectedDataSet.getTable("sedeasign_2");
        ITable expectedTable3=expectedDataSet.getTable("sedeasign_3");

        comparaSedeConcursoExtendida(actual1,expectedTable1);
        comparaSedeConcursoExtendida(actual2,expectedTable2);
        comparaSedeConcursoExtendida(actual3,expectedTable3);
        calif_sedesasig += PORCENTAJE_SEDESASIG;
    }

    private void comparaTablaEquipo(List<Equipo> actual, ITable expectedTable) {
        Assertions.assertEquals(expectedTable.getRowCount(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaEquipo(actual.get(i), expectedTable,i);
        }
    }


    @Test
    @Order(76)
    public void testYEquiposDisponibles() throws Exception {
        List<Equipo> actual1=null,actual2=null,actual3=null,actual4=null,actual5=null;

        try {
            actual1=dao.obtenEquiposDisponibles(1,1);
            actual2=dao.obtenEquiposDisponibles(2,1);
            actual3=dao.obtenEquiposDisponibles(2,2);
            actual4=dao.obtenEquiposDisponibles(1,2);
            actual5=dao.obtenEquiposDisponibles(2,3);
        }
        catch (Exception ex) {
            ex.printStackTrace();
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
        calif_equiposdisp += PORCENTAJE_EQUIPOSDISP;
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
            exds.printStackTrace();
            Assertions.assertNull(exds, "No deberia generar excepcion comparar las EquiposSedesConcursoExtendidas");
        }
    }

    @Test
    @Order(77)
    public void testYEquiposRegistrados() throws Exception {
        List<EquiposSedeConcursoExtendida> actual1=null,actual2=null,actual3=null,actual4=null;

        try {
            actual1=dao.obtenEquiposRegistrados(1,1);
            actual2=dao.obtenEquiposRegistrados(1,2);
            actual3=dao.obtenEquiposRegistrados(1,3);
            actual4=dao.obtenEquiposRegistrados(3,1);
        }
        catch (Exception ex) {
            ex.printStackTrace();
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
        calif_equiposreg += PORCENTAJE_EQUIPOSREG;
    }
}

