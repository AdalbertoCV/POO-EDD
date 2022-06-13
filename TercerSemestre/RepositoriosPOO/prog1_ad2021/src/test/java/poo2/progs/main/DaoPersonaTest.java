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
import poo2.progs.basedatos.DaoPersona;
import poo2.progs.entidades.Persona;
import poo2.progs.interfaces.DaoException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DaoPersonaTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static List<Persona> datosEsperados;
    private static IDatabaseConnection conndbunit;
    private static int calificacion;
    private final static int CALIF_OBTENER=5;
    private final static int CALIF_AGREGAR=10;
    private final static int CALIF_UPDATE=10;
    private final static int CALIF_DELETE=5;
    private final static int MAX_CALIF=90;
    private final static double PORCENTAJE_DAOPERSONA = 12.0;

    private static void iniciaDatosLista() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream("datosper.tsv"), StandardCharsets.UTF_8));
        String linea=in.readLine();
        datosEsperados=new ArrayList<>();
        while (linea!=null) {
            String[] p=linea.split("\t");
            Persona per=new Persona(p[0],p[1],p[2],p[3],p[4],Long.parseLong(p[6]),
                    Long.parseLong(p[7]),p[9], java.sql.Date.valueOf(p[10]),
                    Long.parseLong(p[11]),p[12]);
            per.setColoniaPersona(p[5]);
            per.setCodpostalPersona(p[8]);
            datosEsperados.add(per);
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
        IDataSet dataSet=new FlatXmlDataSetBuilder().build(new FileInputStream("concursos.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onTearDown();
        System.out.printf("Puntos para Pruebas DAOPersona: %.2f/%.2f\n",calificacion* PORCENTAJE_DAOPERSONA /MAX_CALIF, PORCENTAJE_DAOPERSONA);
        TotalAcumulado.acumula(calificacion* PORCENTAJE_DAOPERSONA /MAX_CALIF);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }


    private void comparaPersona(Persona expected,
                                Persona actual) {
        Assertions.assertEquals(expected.getEmailPersona(),
                actual.getEmailPersona());
        Assertions.assertEquals(expected.getNombrePersona(),
                actual.getNombrePersona());
        Assertions.assertEquals(expected.getApellidosPersona(),
                actual.getApellidosPersona());
        Assertions.assertEquals(expected.getSexoPersona(),
                actual.getSexoPersona());
        Assertions.assertEquals(expected.getCalleNumPersona(),
                actual.getCalleNumPersona());
        Assertions.assertEquals(expected.getColoniaPersona(),
                actual.getColoniaPersona());
        Assertions.assertEquals(expected.getIdMunicipioPersona(),
                actual.getIdMunicipioPersona());
        Assertions.assertEquals(expected.getIdEntidadPersona(),
                actual.getIdEntidadPersona());
        Assertions.assertEquals(expected.getCodpostalPersona(),
                actual.getCodpostalPersona());
        Assertions.assertEquals(expected.getTelefonoPersona(),
                actual.getTelefonoPersona());
        Assertions.assertEquals(expected.getFechaNacPersona(),
                actual.getFechaNacPersona());
        Assertions.assertEquals(expected.getIdInstitucionPersona(),
                actual.getIdInstitucionPersona());
        Assertions.assertEquals(expected.getTipoPersona(),
                actual.getTipoPersona());

    }

    @Test
    @Order(2)
    public void testObtenPersonaExistente() throws Exception {
        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        String emailABuscar="charly@uaz.edu.mx";
        Optional<Persona> actual = Optional.empty();
        try {
            actual = daoPersona.get(emailABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoPersona");
        }
        Assertions.assertTrue(actual.isPresent());
        comparaPersona(datosEsperados.get(0),actual.get());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(3)
    public void testObtenPersonaInexistente() throws Exception {
        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        String emailABuscar="noexiste@infierno.com";

        Optional<Persona> actual = Optional.empty();
        try {
            actual = daoPersona.get(emailABuscar);
            Assertions.assertFalse(actual.isPresent());
            actual = daoPersona.get(80L);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoPersona");
        }

        Assertions.assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(4)
    public void testObtenPersonaAll() throws Exception {
        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        List<Persona> actual;
        try {
            actual=daoPersona.getAll();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo getAll de DaoPersona");
        }
        Assertions.assertEquals(datosEsperados.size(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaPersona(datosEsperados.get(i), actual.get(i));
        }
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(5)
    public void testAgregarPersonaValida() throws Exception {
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
        Date fecha=java.sql.Date.valueOf("2001-10-13");
        long idinst=3L;
        String tipo="Estudiante";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoPersona.save(per);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de Persona");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("persona",
                "SELECT * FROM persona");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("persona_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(6)
    public void testAgregarPersonaDuplicada() throws Exception {
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
        Date fecha=java.sql.Date.valueOf("2001-10-13");
        long idinst=3L;
        String tipo="Estudiante";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoPersona.save(per);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoPersona");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    private void validaNullo_Vacio(DaoPersona daoPersona, String valor, String id, boolean nuevo) {
        String email=id;
        String nom="Miguel";
        String apellidos="Salas Guzman";
        String sexo="M";
        String calle="Herreros 25";
        String colonia="Centro";
        long idmun=32042L;
        long ident=32L;
        String codpostal="99104";
        String tel="4331234567";
        Date fecha=java.sql.Date.valueOf("2001-10-13");
        long idinst=3L;
        String tipo="Estudiante";
        boolean resultado;
        String valprev;

        Persona per= new Persona(valor,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        resultado=nuevo?daoPersona.save(per):daoPersona.update(per);
        Assertions.assertFalse(resultado);

        per.setEmailPersona(email);
        valprev=per.getNombrePersona();
        per.setNombrePersona(valor);
        resultado=nuevo?daoPersona.save(per):daoPersona.update(per);
        Assertions.assertFalse(resultado);

        per.setNombrePersona(valprev);
        valprev=per.getApellidosPersona();
        per.setApellidosPersona(valor);
        resultado=nuevo?daoPersona.save(per):daoPersona.update(per);
        Assertions.assertFalse(resultado);

        per.setApellidosPersona(valprev);
        valprev=per.getSexoPersona();
        per.setSexoPersona(valor);
        resultado=nuevo?daoPersona.save(per):daoPersona.update(per);
        Assertions.assertFalse(resultado);

        per.setSexoPersona(valprev);
        valprev=per.getCalleNumPersona();
        per.setCalleNumPersona(valor);
        resultado=nuevo?daoPersona.save(per):daoPersona.update(per);
        Assertions.assertFalse(resultado);

        per.setCalleNumPersona(valprev);
        valprev=per.getTelefonoPersona();
        per.setTelefonoPersona(valor);
        resultado=nuevo?daoPersona.save(per):daoPersona.update(per);
        Assertions.assertFalse(resultado);

        per.setTelefonoPersona(valprev);
        if (valor==null) {
            Date fechaprev=per.getFechaNacPersona();
            per.setFechaNacPersona(null);
            resultado=nuevo?daoPersona.save(per):daoPersona.update(per);
            Assertions.assertFalse(resultado);
            per.setFechaNacPersona(fechaprev);
        }

        valprev=per.getTipoPersona();
        per.setTipoPersona(valor);
        resultado=nuevo?daoPersona.save(per):daoPersona.update(per);
        Assertions.assertFalse(resultado);

        per.setTipoPersona(valprev);
        valprev=per.getColoniaPersona();
        per.setColoniaPersona(valor);
        resultado=nuevo?daoPersona.save(per):daoPersona.update(per);
        Assertions.assertTrue(resultado);

        Connection conn;
        Statement stmt=null;
        String sql=String.format("DELETE FROM persona WHERE email_persona='%s'",email);
        try {
            if (nuevo) {
                conn = conndbunit.getConnection();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            Assertions.assertNull(exsql, "Problema al eliminar la persona que se agrego en la prueba");
        }


        per.setColoniaPersona(valprev);
        per.setCodpostalPersona(valor);
        resultado=nuevo?daoPersona.save(per):daoPersona.update(per);
        Assertions.assertTrue(resultado);
        try {
            if (nuevo) {
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            Assertions.assertNull(exsql, "Problema al eliminar la persona que se agrego en la prueba");
        }

    }

    @Test
    @Order(7)
    public void testAgregarPersonaInvalida() throws Exception {
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
        Date[] fecha={java.sql.Date.valueOf("2000-10-10"),java.sql.Date.valueOf("2000-10-10")};
        long[] idinst={1,350};
        String[] tipo={"Profesor","Teacher"};
        Persona per= new Persona(email[1],nom[0],apellido[0],sexo[0],calle[0],idmun[0],ident[0],tel[0],fecha[0],idinst[0],tipo[0]);
        per.setColoniaPersona(colonia[0]);
        per.setCodpostalPersona(codpostal[0]);

        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        boolean resultado;
        //resultado = true;
        try {
            validaNullo_Vacio(daoPersona,null,"xxuigg@demonio.com",true);
            validaNullo_Vacio(daoPersona,"","xxuigg@demonio.com",true);

            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setEmailPersona(email[0]);
            per.setNombrePersona(nom[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setNombrePersona(nom[0]);
            per.setApellidosPersona(apellido[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setApellidosPersona(apellido[0]);
            per.setSexoPersona(sexo[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setSexoPersona(sexo[0]);
            per.setCalleNumPersona(calle[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setCalleNumPersona(calle[0]);
            per.setColoniaPersona(colonia[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setColoniaPersona(colonia[0]);
            per.setIdMunicipioPersona(idmun[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setIdMunicipioPersona(idmun[0]);
            per.setIdEntidadPersona(ident[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setIdEntidadPersona(ident[0]);
            per.setCodpostalPersona(codpostal[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setCodpostalPersona(codpostal[0]);
            per.setTelefonoPersona(tel[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setTelefonoPersona(tel[0]);
            per.setIdInstitucionPersona(idinst[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);

            per.setIdInstitucionPersona(idinst[0]);
            per.setTipoPersona(tipo[1]);
            resultado=daoPersona.save(per);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoPersona");
            System.err.println(ex.getMessage());
            resultado=true;
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(8)
    public void testUpdatePersonaValida() throws Exception {
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
        Date fecha=java.sql.Date.valueOf("2001-10-14");
        long idinst=1L;
        String tipo="Profesor";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=daoPersona.update(per);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de Persona");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("persona",
                "SELECT * FROM persona WHERE email_persona='misalas@itszn.edu.mx'");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("persona_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(9)
    public void testUpdatePersonaInexistente() throws Exception {
        String email="ulgaja@itszn.edu.mx";
        String nom="Maria";
        String apellidos="Guzman Salas";
        String sexo="F";
        String calle="Juarez 215";
        String colonia="Las Flores";
        long idmun=32045L;
        long ident=1001L;
        String codpostal="91010";
        String tel="4911234567";
        Date fecha=java.sql.Date.valueOf("2001-10-14");
        long idinst=1L;
        String tipo="Profesor";
        Persona per= new Persona(email,nom,apellidos,sexo,calle,idmun,ident,tel,fecha,idinst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);

        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=daoPersona.update(per);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoPersona");
        }
        assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(10)
    public void testUpdatePersonaInvalida() throws Exception {
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
        Date[] fecha={java.sql.Date.valueOf("2000-10-10"),java.sql.Date.valueOf("2000-10-10")};
        long[] idinst={1,350};
        String[] tipo={"Profesor","Teacher"};
        Persona per= new Persona(email[1],nom[0],apellido[0],sexo[0],calle[0],idmun[0],ident[0],tel[0],fecha[0],idinst[0],tipo[0]);
        per.setColoniaPersona(colonia[0]);
        per.setCodpostalPersona(codpostal[0]);

        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        boolean resultado;
        //resultado = true;
        try {
            validaNullo_Vacio(daoPersona,null,"misalas@itszn.edu.mx",false);
            validaNullo_Vacio(daoPersona,"","misalas@itszn.edu.mx",false);


            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setEmailPersona(email[0]);
            per.setNombrePersona(nom[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setNombrePersona(nom[0]);
            per.setApellidosPersona(apellido[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setApellidosPersona(apellido[0]);
            per.setSexoPersona(sexo[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setSexoPersona(sexo[0]);
            per.setCalleNumPersona(calle[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setCalleNumPersona(calle[0]);
            per.setColoniaPersona(colonia[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setColoniaPersona(colonia[0]);
            per.setIdMunicipioPersona(idmun[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setIdMunicipioPersona(idmun[0]);
            per.setIdEntidadPersona(ident[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setIdEntidadPersona(ident[0]);
            per.setCodpostalPersona(codpostal[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setCodpostalPersona(codpostal[0]);
            per.setTelefonoPersona(tel[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setTelefonoPersona(tel[0]);
            per.setIdInstitucionPersona(idinst[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);

            per.setIdInstitucionPersona(idinst[0]);
            per.setTipoPersona(tipo[1]);
            resultado=daoPersona.update(per);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoPersona");
            System.err.println(ex.getMessage());
            resultado=true;
        }

        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(11)
    public void testDeletePersonaInexistente() throws Exception {
        String id="SHY$$gq@gaga";
        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        try {
            boolean resultado=daoPersona.delete(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoPersona");
        }
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(12)
    public void testDeletePersonaExistente() throws Exception {
        String id="misalas@itszn.edu.mx";
        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        try {
            boolean resultado=daoPersona.delete(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoPersona");
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createQueryTable("persona",
                "SELECT * FROM persona");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("persona_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("persona");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(13)
    public void testDeletePersonaInvalida() throws Exception {
        String id="charly@uaz.edu.mx";
        DaoPersona daoPersona=new DaoPersona(conndbunit.getConnection());
        try {
            boolean resultado=daoPersona.delete(id);
            Assertions.assertFalse(resultado);
            resultado=daoPersona.delete(80L);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoPersona");
        }
        calificacion += CALIF_DELETE;
    }

}

