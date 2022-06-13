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
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.*;
import poo2.ConfigAccesoBaseDatos;
import poo2.progs.basedatos.DaoInstitucion;
import poo2.progs.entidades.Institucion;
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
public class DaoInstitucionTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static List<Institucion> datosEsperados;
    private static IDatabaseConnection conndbunit;
    private static int calificacion;
    private final static int CALIF_OBTENER=5;
    private final static int CALIF_AGREGAR=10;
    private final static int CALIF_UPDATE=10;
    private final static int CALIF_DELETE=5;
    private final static int MAX_CALIF=95;
    private final static double PORCENTAJE_DAOINST=2.0;

    private static void iniciaDatosLista() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream("datosinst.tsv"), StandardCharsets.UTF_8));
        String linea=in.readLine();
        datosEsperados=new ArrayList<>();
        while (linea!=null) {
            String[] p=linea.split("\t");
            Institucion inst=new Institucion(Long.parseLong(p[0]),
                    p[1],p[2],p[3],p[4],Long.parseLong(p[6]),
                    Long.parseLong(p[7]));
            inst.setColoniaInstitucion(p[5]);
            inst.setCodpostalInstitucion(p[8]);
            inst.setTelefonoInstitucion(p[9]);
            datosEsperados.add(inst);
            linea=in.readLine();
        }
    }


    @BeforeAll
    public static void inicializa() throws Exception {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        iniciaDatosLista();
        databaseTester=new JdbcDatabaseTester(ConfigAccesoBaseDatos.driverName,
                ConfigAccesoBaseDatos.url,
                ConfigAccesoBaseDatos.usuario,
                ConfigAccesoBaseDatos.clave);
        databaseTester.setOperationListener(new ConfigAccesoBaseDatos.CustomConfigurationOperationListener());
        conndbunit=databaseTester.getConnection();
        DatabaseConfig config=conndbunit.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS,true);
        IDataSet dataSet=new FlatXmlDataSetBuilder().build(new FileInputStream("concursos.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.REFRESH);
        databaseTester.onTearDown();
        System.out.printf("Puntos para Pruebas DAOInstitucion: %.2f/%.2f\n",calificacion*PORCENTAJE_DAOINST/MAX_CALIF,PORCENTAJE_DAOINST);
        TotalAcumulado.acumula(calificacion*PORCENTAJE_DAOINST/MAX_CALIF);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }


    private void comparaInst(Institucion expected,
                             Institucion actual) {
        Assertions.assertEquals(expected.getIdInstitucion(),
                actual.getIdInstitucion());
        Assertions.assertEquals(expected.getNombreInstitucion(),
                actual.getNombreInstitucion());
        Assertions.assertEquals(expected.getNombreCortoInstitucion(),
                actual.getNombreCortoInstitucion());
        Assertions.assertEquals(expected.getUrlInstitucion(),
                actual.getUrlInstitucion());
        Assertions.assertEquals(expected.getCalleNumInstitucion(),
                actual.getCalleNumInstitucion());
        Assertions.assertEquals(expected.getColoniaInstitucion(),
                actual.getColoniaInstitucion());
        Assertions.assertEquals(expected.getIdMunicipioInstitucion(),
                actual.getIdMunicipioInstitucion());
        Assertions.assertEquals(expected.getIdEntidadInstitucion(),
                actual.getIdEntidadInstitucion());
        Assertions.assertEquals(expected.getCodpostalInstitucion(),
                actual.getCodpostalInstitucion());
        Assertions.assertEquals(expected.getTelefonoInstitucion(),
                actual.getTelefonoInstitucion());
    }

    @Test
    @Order(2)
    public void testObtenInstExistente() throws Exception {
        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        Long idABuscar=1L;
        Optional<Institucion> actual = Optional.empty();
        try {
            actual = daoInst.get(idABuscar);

        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoInstitucion");
        }
        Assertions.assertTrue(actual.isPresent());
        comparaInst(datosEsperados.get(0),actual.get());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(3)
    public void testObtenInstInexistente() throws Exception {
        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        Long idABuscar=100L;

        Optional<Institucion> actual = Optional.empty();
        try {
            actual = daoInst.get(idABuscar);
            Assertions.assertFalse(actual.isPresent());
            actual = daoInst.get("Nuevecita");
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoInstitucion");
        }

        Assertions.assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(4)
    public void testObtenInstAll() throws Exception {
        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        List<Institucion> actual;
        try {
            actual=daoInst.getAll();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo getAll de DaoInstitucion");
        }
        Assertions.assertEquals(datosEsperados.size(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaInst(datosEsperados.get(i), actual.get(i));
        }
        calificacion += CALIF_OBTENER*2;
    }

    @Test
    @Order(5)
    public void testAgregarInstValida() throws Exception {
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

        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado = daoInst.save(inst);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoInstitucion");
        }
        Assertions.assertTrue(resultado);


        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion WHERE id_institucion>4 AND id_institucion<10");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(6)
    public void testAgregarInstDuplicada() throws Exception {
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

        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado=daoInst.save(inst);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoInstitucion");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    private void validaNullo_Vacio(DaoInstitucion daoInst, String valor, long id, boolean nuevo) {
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
        resultado=nuevo?daoInst.save(inst):daoInst.update(inst);
        Assertions.assertFalse(resultado);

        inst.setNombreInstitucion(nom);
        valprev=inst.getNombreCortoInstitucion();
        inst.setNombreCortoInstitucion(valor);
        resultado=nuevo?daoInst.save(inst):daoInst.update(inst);
        Assertions.assertFalse(resultado);

        inst.setNombreCortoInstitucion(valprev);
        valprev=inst.getUrlInstitucion();
        inst.setUrlInstitucion(valor);
        resultado=nuevo?daoInst.save(inst):daoInst.update(inst);
        Assertions.assertFalse(resultado);

        inst.setUrlInstitucion(valprev);
        valprev=inst.getCalleNumInstitucion();
        inst.setCalleNumInstitucion(valor);
        resultado=nuevo?daoInst.save(inst):daoInst.update(inst);
        Assertions.assertFalse(resultado);

        inst.setCalleNumInstitucion(valprev);
        valprev=inst.getColoniaInstitucion();
        inst.setColoniaInstitucion(valor);
        resultado=nuevo?daoInst.save(inst):daoInst.update(inst);
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
        resultado=nuevo?daoInst.save(inst):daoInst.update(inst);
        Assertions.assertTrue(resultado);

        try {
            if (nuevo) {
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            Assertions.assertNull(exsql, "Problema al eliminar la institucion que se agrego en la prueba");
        }

        inst.setCodpostalInstitucion(valprev);
        inst.setTelefonoInstitucion(valor);
        resultado=nuevo?daoInst.save(inst):daoInst.update(inst);
        Assertions.assertTrue(resultado);

        try {
            if (nuevo) {
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            Assertions.assertNull(exsql, "Problema al eliminar la institucion que se agrego en la prueba");
        }
    }

    @Test
    @Order(7)
    public void testAgregarInstInvalida() throws Exception {
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

        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        boolean resultado;

        Institucion inst= new Institucion(id,nom[1],nomcorto[0],url[0],
                calle[0],idmun[0],ident[0]);
        inst.setColoniaInstitucion(colonia[0]);
        inst.setCodpostalInstitucion(codpostal[0]);
        inst.setTelefonoInstitucion(tel[0]);



        //resultado = true;
        try {

            resultado=daoInst.save(null);
            Assertions.assertFalse(resultado);

            validaNullo_Vacio(daoInst,null,100,true);
            validaNullo_Vacio(daoInst,"",100,true);

            resultado=daoInst.save(inst);
            Assertions.assertFalse(resultado);

            //resultado=true;
            inst.setNombreInstitucion(nom[0]);
            inst.setNombreCortoInstitucion(nomcorto[1]);
            resultado=daoInst.save(inst);
            Assertions.assertFalse(resultado);


            //resultado=true;
            inst.setNombreCortoInstitucion(nomcorto[0]);
            inst.setCalleNumInstitucion(calle[1]);
            resultado=daoInst.save(inst);
            Assertions.assertFalse(resultado);

            //resultado=true;
            inst.setCalleNumInstitucion(calle[0]);
            inst.setColoniaInstitucion(colonia[1]);
            resultado=daoInst.save(inst);
            Assertions.assertFalse(resultado);

            //resultado=true;
            inst.setColoniaInstitucion(colonia[0]);
            inst.setIdMunicipioInstitucion(idmun[1]);
            resultado=daoInst.save(inst);
            Assertions.assertFalse(resultado);

            //resultado=true;
            inst.setIdMunicipioInstitucion(idmun[0]);
            inst.setIdEntidadInstitucion(ident[1]);
            resultado=daoInst.save(inst);
            Assertions.assertFalse(resultado);

            //resultado=true;
            inst.setIdEntidadInstitucion(ident[0]);
            inst.setCodpostalInstitucion(codpostal[1]);
            resultado=daoInst.save(inst);
            Assertions.assertFalse(resultado);

            //resultado=true;
            inst.setCodpostalInstitucion(codpostal[0]);
            inst.setTelefonoInstitucion(tel[1]);
            resultado=daoInst.save(inst);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            ex.printStackTrace();
            resultado=true;
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(8)
    public void testUpdateInstValida() throws Exception {
        long id=10;
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

        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=daoInst.update(inst);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoInstitucion");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion WHERE id_institucion>5 AND id_institucion<=10");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(9)
    public void testUpdateInstInexistente() throws Exception {
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

        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=daoInst.update(inst);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoInstitucion");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(10)
    public void testUpdateInstInvalida() throws Exception {
        long id=0;
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
        long[] ident ={32,35};
        String[] codpostal={"98000","198601"};
        String[] tel={"4921234567","49292762180"};

        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        boolean resultado;

        Institucion inst= new Institucion(id,nom[1],nomcorto[0],url[0],
                calle[0],idmun[0],ident[0]);
        inst.setColoniaInstitucion(colonia[0]);
        inst.setCodpostalInstitucion(codpostal[0]);
        inst.setTelefonoInstitucion(tel[0]);


        try {

            resultado=daoInst.update(null);
            Assertions.assertFalse(resultado);

            validaNullo_Vacio(daoInst,null,10,false);
            validaNullo_Vacio(daoInst,"",10,false);

            resultado=daoInst.update(inst);
            Assertions.assertFalse(resultado);

            inst.setIdInstitucion(5L);
            resultado=daoInst.update(inst);
            Assertions.assertFalse(resultado);

            inst.setNombreInstitucion(nom[0]);
            inst.setNombreCortoInstitucion(nomcorto[1]);
            resultado=daoInst.update(inst);
            Assertions.assertFalse(resultado);


            inst.setNombreCortoInstitucion(nomcorto[0]);
            inst.setCalleNumInstitucion(calle[1]);
            resultado=daoInst.update(inst);
            Assertions.assertFalse(resultado);

            inst.setCalleNumInstitucion(calle[0]);
            inst.setColoniaInstitucion(colonia[1]);
            resultado=daoInst.update(inst);
            Assertions.assertFalse(resultado);

            inst.setColoniaInstitucion(colonia[0]);
            inst.setIdMunicipioInstitucion(idmun[1]);
            resultado=daoInst.update(inst);
            Assertions.assertFalse(resultado);

            inst.setIdMunicipioInstitucion(idmun[0]);
            inst.setIdEntidadInstitucion(ident[1]);
            resultado=daoInst.update(inst);
            Assertions.assertFalse(resultado);

            inst.setIdEntidadInstitucion(ident[0]);
            inst.setCodpostalInstitucion(codpostal[1]);
            resultado=daoInst.update(inst);
            Assertions.assertFalse(resultado);

            inst.setCodpostalInstitucion(codpostal[0]);
            inst.setTelefonoInstitucion(tel[1]);
            resultado=daoInst.update(inst);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoInstitucion");
            resultado=true;
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(11)
    public void testDeleteInstInexistente() throws Exception {
        Long id=16L;
        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        try {
            boolean resultado=daoInst.delete(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoInstitucion");
        }
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(12)
    public void testDeleteInstExistente() throws Exception {
        long id=10L;
        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        try {
            boolean resultado=daoInst.delete(id);
            Assertions.assertTrue(resultado);
            id=5L;
            daoInst.delete(id); // Se borra la 5 agregada en las pruebas
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoInstitucion");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createQueryTable("institucion",
                "SELECT * FROM institucion WHERE id_institucion<=10");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("inst_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("institucion");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(13)
    public void testDeleteInstInvalida() throws Exception {
        Long id=1L;
        DaoInstitucion daoInst=new DaoInstitucion(conndbunit.getConnection());
        try {
            boolean resultado=daoInst.delete(id);
            Assertions.assertFalse(resultado);
            resultado=daoInst.delete("Nuevecita");
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoInstitucion");
        }
        calificacion += CALIF_DELETE;
    }

}


