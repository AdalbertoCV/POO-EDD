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
import poo2.progs.basedatos.DaoEquipo;
import poo2.progs.entidades.Equipo;
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
public class DaoEquipoTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static List<Equipo> datosEsperados;
    private static IDatabaseConnection conndbunit;
    private static int calificacion;
    private final static int CALIF_OBTENER=5;
    private final static int CALIF_AGREGAR=10;
    private final static int CALIF_UPDATE=10;
    private final static int CALIF_DELETE=5;
    private final static int MAX_CALIF=90;
    private final static double PORCENTAJE_DAOEQUIPO = 12.0;

    private static void iniciaDatosLista() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream("datosequipo.tsv"), StandardCharsets.UTF_8));
        String linea=in.readLine();
        datosEsperados=new ArrayList<>();
        while (linea!=null) {
            String[] p=linea.split("\t");
            Equipo eq=new Equipo(Long.parseLong(p[0]),p[1],p[2],p[3],p[4],p[5],
                    Long.parseLong(p[7]));
            eq.setEmailConcursanteReserva(p[6]);
            datosEsperados.add(eq);
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
        System.out.printf("Puntos para Pruebas DAOEquipo: %.2f/%.2f\n",calificacion* PORCENTAJE_DAOEQUIPO /MAX_CALIF, PORCENTAJE_DAOEQUIPO);
        TotalAcumulado.acumula(calificacion* PORCENTAJE_DAOEQUIPO /MAX_CALIF);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }


    private void comparaEquipo(Equipo expected,
                               Equipo actual) {
        Assertions.assertEquals(expected.getIdEquipo(),
                actual.getIdEquipo());
        Assertions.assertEquals(expected.getNombreEquipo(),
                actual.getNombreEquipo());
        Assertions.assertEquals(expected.getEmailCoach(),
                actual.getEmailCoach());
        Assertions.assertEquals(expected.getEmailConcursante1(),
                actual.getEmailConcursante1());
        Assertions.assertEquals(expected.getEmailConcursante2(),
                actual.getEmailConcursante2());
        Assertions.assertEquals(expected.getEmailConcursante3(),
                actual.getEmailConcursante3());
        Assertions.assertEquals(expected.getEmailConcursanteReserva(),
                actual.getEmailConcursanteReserva());
        Assertions.assertEquals(expected.getIdInstitucionEquipo(),
                actual.getIdInstitucionEquipo());
    }

    @Test
    @Order(2)
    public void testObtenEquipoExistente() throws Exception {
        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        long idABuscar=1;
        Optional<Equipo> actual = Optional.empty();
        try {
            actual = daoEquipo.get(idABuscar);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoEquipo");
        }
        Assertions.assertTrue(actual.isPresent());
        comparaEquipo(datosEsperados.get(0),actual.get());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(3)
    public void testObtenEquipoInexistente() throws Exception {
        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        long idABuscar=10;
        Optional<Equipo> actual = Optional.empty();
        try {
            actual = daoEquipo.get(idABuscar);
            Assertions.assertFalse(actual.isPresent());
            actual = daoEquipo.get("Inexistente");
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo get de DaoEquipo");
        }

        Assertions.assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(4)
    public void testObtenEquipoAll() throws Exception {
        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        List<Equipo> actual;
        try {
            actual=daoEquipo.getAll();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo getAll de DaoEquipo");
        }
        Assertions.assertEquals(datosEsperados.size(),actual.size());
        for (int i=0; i<actual.size(); i++) {
            comparaEquipo(datosEsperados.get(i), actual.get(i));
        }
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(5)
    public void testAgregarEquipoValida() throws Exception {
        long id=3;
        String nom="Code Avengers";
        String emailC="sodelv@uaz.edu.mx";
        String email1="hjesus@hotmail.com";
        String email2="charly@uaz.edu.mx";
        String email3="joyce@hotmail.com";
        String emailR="lalo@uaz.edu.mx";
        long idInst=1;

        Equipo eq = new Equipo(id,nom,emailC,email1,email2,email3,idInst);
        eq.setEmailConcursanteReserva(emailR);

        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoEquipo.save(eq);
        }
        catch (DaoException ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoEquipo");
        }
        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("equipo",
                "SELECT * FROM equipo");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipo_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(6)
    public void testAgregarEquipoDuplicada() throws Exception {
        long id=3;
        String nom="Code Avengers";
        String emailC="sodelv@uaz.edu.mx";
        String email1="hjesus@hotmail.com";
        String email2="charly@uaz.edu.mx";
        String email3="joyce@hotmail.com";
        String emailR="lalo@uaz.edu.mx";
        long idInst=1;

        Equipo eq = new Equipo(id,nom,emailC,email1,email2,email3,idInst);
        eq.setEmailConcursanteReserva(emailR);

        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = daoEquipo.save(eq);
        }
        catch (Exception ex) {
            resultado=false;
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoEquipo");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    private void validaNullo_Vacio(DaoEquipo daoEquipo, String valor, long ideq, boolean nuevo) {
        String nom="Code Avengers";
        String emailC="sodelv@uaz.edu.mx";
        String email1="hjesus@hotmail.com";
        String email2="charly@uaz.edu.mx";
        String email3="joyce@hotmail.com";
        String emailR="lalo@uaz.edu.mx";
        long idInst=1;

        Equipo eq = new Equipo(ideq,valor,emailC,email1,email2,email3,idInst);
        eq.setEmailConcursanteReserva(emailR);
        boolean resultado;

        resultado=nuevo?daoEquipo.save(eq):daoEquipo.update(eq);
        Assertions.assertFalse(resultado);

        eq.setNombreEquipo(nom);
        eq.setEmailCoach(valor);
        resultado=nuevo?daoEquipo.save(eq):daoEquipo.update(eq);
        Assertions.assertFalse(resultado);

        eq.setEmailCoach(emailC);
        eq.setEmailConcursante1(valor);
        resultado=nuevo?daoEquipo.save(eq):daoEquipo.update(eq);
        Assertions.assertFalse(resultado);

        eq.setEmailConcursante1(email1);
        eq.setEmailConcursante2(valor);
        resultado=nuevo?daoEquipo.save(eq):daoEquipo.update(eq);
        Assertions.assertFalse(resultado);

        eq.setEmailConcursante2(email2);
        eq.setEmailConcursante3(valor);
        resultado=nuevo?daoEquipo.save(eq):daoEquipo.update(eq);
        Assertions.assertFalse(resultado);

        eq.setEmailConcursante3(email3);
        eq.setEmailConcursanteReserva(valor);
        resultado=nuevo?daoEquipo.save(eq):daoEquipo.update(eq);
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
            Assertions.assertNull(exsql, "Problema al eliminar el equipo que se agrego en la prueba");
        }

    }

    @Test
    @Order(7)
    public void testAgregarEquipoInvalida() throws Exception {
        String[] nombre={"Equipo Poderoso","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] email={"xxtat@yho.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String[] emailExistentesInst1={"rsolis@uaz.edu.mx","charly@uaz.edu.mx","lalo@uaz.edu.mx","hjesus@hotmail.com", "joyce@hotmail.com"};
        String[] emailExistentesInst3={"ontiveros@itszo.edu.mx","dianamtz@yahoo.com.mx"};
        long ideq=10L;
        long idinst=1;
        Equipo equipo = new Equipo(ideq,nombre[1],email[0],email[0],email[0],email[0],idinst);
        equipo.setEmailConcursanteReserva(email[0]);

        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        boolean resultado;
        try {
            validaNullo_Vacio(daoEquipo,null,30,true);
            validaNullo_Vacio(daoEquipo,"",30, true);

            resultado=daoEquipo.save(equipo);
            Assertions.assertFalse(resultado);

            equipo.setNombreEquipo(nombre[0]);
            equipo.setEmailCoach(email[1]);
            resultado=daoEquipo.save(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(email[0]);
            equipo.setEmailConcursante1(email[1]);
            resultado=daoEquipo.save(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante1(email[0]);
            equipo.setEmailConcursante2(email[1]);
            resultado=daoEquipo.save(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante2(email[0]);
            equipo.setEmailConcursante3(email[1]);
            resultado=daoEquipo.save(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante3(email[0]);
            equipo.setEmailConcursanteReserva(email[1]);
            resultado=daoEquipo.save(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[1]);
            equipo.setEmailConcursante1(emailExistentesInst1[0]);
            equipo.setEmailConcursante2(emailExistentesInst1[2]);
            equipo.setEmailConcursante3(emailExistentesInst1[3]);
            equipo.setEmailConcursanteReserva(emailExistentesInst1[4]);
            resultado=daoEquipo.save(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[0]);
            equipo.setEmailConcursante1(emailExistentesInst1[2]);
            resultado=daoEquipo.save(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst3[0]);
            equipo.setEmailConcursante1(emailExistentesInst1[1]);
            resultado=daoEquipo.save(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[0]);
            equipo.setEmailConcursante1(emailExistentesInst3[1]);
            resultado=daoEquipo.save(equipo);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println("ERROR");
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo save de DaoEquipo");
        }

        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(8)
    public void testUpdateEquipoValida() throws Exception {
        long id=3;
        String nom="Code Avengers Reloaded";
        String emailC="rsolis@uaz.edu.mx";
        String emailR="hjesus@hotmail.com";
        String email1="charly@uaz.edu.mx";
        String email2="joyce@hotmail.com";
        String email3="lalo@uaz.edu.mx";
        long idInst=1;

        Equipo eq = new Equipo(id,nom,emailC,email1,email2,email3,idInst);
        eq.setEmailConcursanteReserva(emailR);

        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado=daoEquipo.update(eq);
        }
        catch (DaoException ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoEquipo");
        }

        Assertions.assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("equipo",
                "SELECT * FROM equipo WHERE id_equipo=3");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipo_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(9)
    public void testUpdateEquipoInexistente() throws Exception {
        long id=30L;
        String nom="Code Avengers Reloaded";
        String emailC="rsolis@uaz.edu.mx";
        String emailR="hjesus@hotmail.com";
        String email1="charly@uaz.edu.mx";
        String email2="joyce@hotmail.com";
        String email3="lalo@uaz.edu.mx";
        long idInst=1;

        Equipo eq = new Equipo(id,nom,emailC,email1,email2,email3,idInst);
        eq.setEmailConcursanteReserva(emailR);

        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado=daoEquipo.update(eq);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoEquipo");
        }
        Assertions.assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(10)
    public void testUpdateEquipoInvalida() throws Exception {
        String[] nombre={"Equipo Poderoso","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] email={"xxtat@yho.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        String[] emailExistentesInst1={"rsolis@uaz.edu.mx","charly@uaz.edu.mx","lalo@uaz.edu.mx","hjesus@hotmail.com", "joyce@hotmail.com"};
        String[] emailExistentesInst3={"ontiveros@itszo.edu.mx","dianamtz@yahoo.com.mx"};
        long ideq=3L;
        long idinst=1;
        Equipo equipo = new Equipo(ideq,nombre[1],email[0],email[0],email[0],email[0],idinst);
        equipo.setEmailConcursanteReserva(email[0]);

        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        boolean resultado;

        try {
            validaNullo_Vacio(daoEquipo,null,3,false);
            validaNullo_Vacio(daoEquipo,"",3, false);

            resultado=daoEquipo.update(equipo);
            Assertions.assertFalse(resultado);

            equipo.setNombreEquipo(nombre[0]);
            equipo.setEmailCoach(email[1]);
            resultado=daoEquipo.update(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(email[0]);
            equipo.setEmailConcursante1(email[1]);
            resultado=daoEquipo.update(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante1(email[0]);
            equipo.setEmailConcursante2(email[1]);
            resultado=daoEquipo.update(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante2(email[0]);
            equipo.setEmailConcursante3(email[1]);
            resultado=daoEquipo.update(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailConcursante3(email[0]);
            equipo.setEmailConcursanteReserva(email[1]);
            resultado=daoEquipo.update(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[1]);
            equipo.setEmailConcursante1(emailExistentesInst1[0]);
            equipo.setEmailConcursante2(emailExistentesInst1[2]);
            equipo.setEmailConcursante3(emailExistentesInst1[3]);
            equipo.setEmailConcursanteReserva(emailExistentesInst1[4]);
            resultado=daoEquipo.update(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[0]);
            equipo.setEmailConcursante1(emailExistentesInst1[2]);
            resultado=daoEquipo.update(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst3[0]);
            equipo.setEmailConcursante1(emailExistentesInst1[1]);
            resultado=daoEquipo.update(equipo);
            Assertions.assertFalse(resultado);

            equipo.setEmailCoach(emailExistentesInst1[0]);
            equipo.setEmailConcursante1(emailExistentesInst3[1]);
            resultado=daoEquipo.update(equipo);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo update de DaoEquipo");
            System.err.println(ex.getMessage());
        }
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(11)
    public void testDeleteEquipoInexistente() throws Exception {
        long id=350L;
        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        try {
            boolean resultado=daoEquipo.delete(id);
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoEquipo");
        }
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(12)
    public void testDeleteEquipoExistente() throws Exception {
        long id=3L;
        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        try {
            boolean resultado=daoEquipo.delete(id);
            Assertions.assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoEquipo");
        }

        ITable actualTable=conndbunit.createQueryTable("equipo",
                "SELECT * FROM equipo");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("equipo_del.xml"));
        ITable expectedTable=expectedDataSet.getTable("equipo");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(13)
    public void testDeleteEquipoInvalida() throws Exception {
        long id=2;
        DaoEquipo daoEquipo=new DaoEquipo(conndbunit.getConnection());
        try {
            boolean resultado=daoEquipo.delete(id);
            Assertions.assertFalse(resultado);
            resultado=daoEquipo.delete(null);
            Assertions.assertFalse(resultado);
            resultado=daoEquipo.delete("Inexistente");
            Assertions.assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            Assertions.assertNull(ex, "No deberia generar excepcion el metodo delete de DaoEquipo");
        }
        calificacion += CALIF_DELETE;
    }


}
