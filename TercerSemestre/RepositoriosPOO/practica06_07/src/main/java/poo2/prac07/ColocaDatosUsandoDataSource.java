package poo2.prac07;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.*;
import java.util.*;
import java.io.*;


//TODO Coloca tu nombre en vez de las NNNNNNNN y tu matricula en vez de las XXXXXXXX
/**
 * @author  Adalberto Cerrillo Vazquez
 * @author  34152734
 */

public class ColocaDatosUsandoDataSource {
    private final String nomSede = "sede_34152734";
    private final String nomConcurso = "concurso_34152734";
    private Connection conn;


    public ColocaDatosUsandoDataSource(String direccionServidor, String usuario,
                                       String clave, String nomBD)
            throws SQLException {
        MysqlDataSource fuente = new MysqlDataSource();
        fuente.setServerName(direccionServidor);
        fuente.setUser(usuario);
        fuente.setPassword(clave);
        fuente.setDatabaseName(nomBD);
        conn = fuente.getConnection();
    }


    public void llenaTablaSede(boolean debug) throws Exception {
        if (debug){
            System.out.println("\nColocando datos en tabla "+ nomSede+":");
        }
        Statement sentencia = conn.createStatement();
        sentencia.executeUpdate("DELETE FROM "+nomSede);
        sentencia.executeUpdate("ALTER TABLE  "+nomSede+ " AUTO_INCREMENT=1");
        Scanner lector = new Scanner(new File("datosSede.txt"));
        lector.useLocale(Locale.US);
        lector.useDelimiter("[,\n\r]+");
        sentencia = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultado = sentencia.executeQuery("SELECT * FROM " + nomSede);
        String valor;
        while (lector.hasNext()){
            resultado.moveToInsertRow();
            valor = lector.next();
            if (debug){
                System.out.print("Agregando la sede "+valor);
            }
            resultado.updateString("nombre_sede", valor);
            resultado.updateInt(3, lector.nextInt());
            valor = lector.next();
            resultado.updateString(4,valor);
            resultado.insertRow();
            resultado.last();
            int idSede = resultado.getInt("id_sede");
            if (debug){
                System.out.println(", el id_sede que le toca fue : " + idSede);
            }
        }
        lector.close();
        sentencia.close();

    } // fin del metodo llenaTablaSede()

    public void llenaTablaConcurso(boolean debug) throws Exception {
        if (debug){
            System.out.println("\nColocando datos en la tabla "+ nomConcurso+":");
        }
        Statement sentencia = conn.createStatement();
        sentencia.executeUpdate("DELETE FROM "+nomConcurso);
        sentencia.executeUpdate("ALTER TABLE "+nomConcurso+" AUTO_INCREMENT = 1");
        String insertaDatos = "INSERT INTO  "+nomConcurso +
                "(nombre_concurso, fecha_concurso, fecha_inicio_registro," +
                " fecha_fin_registro) VALUES(";
        if (debug){
            System.out.print("Agregando 'Gran Premio ICPC 2021'");
        }
        String sql = insertaDatos+"'Gran Premio ICPC 2021', '2021-10-30',"+
                "'2021-09-27', '2021-10-27')";
        sentencia.executeUpdate(sql);
        int valorLlaveAutoIncremento = -1;
        ResultSet rs = sentencia.executeQuery("SELECT LAST_INSERT_ID()");
        if (rs.next()){
            valorLlaveAutoIncremento = rs.getInt(1);
        }
        if (debug){
            System.out.println(", el id_concurso que le toco fue : "+ valorLlaveAutoIncremento);
        }
        sentencia.close();
    } // fin del metodo llenaTablaConcurso()


    public void close() throws Exception {
        if (isConnActive()){
            conn.close();
        }
    }

    public boolean isConnActive() {
        return conn!=null;
    }
}
