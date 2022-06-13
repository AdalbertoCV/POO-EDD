package poo2.prac06;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

//TODO Coloca tu nombre en vez de las NNNNNNNN y tu matricula en vez de las XXXXXXXX
/**
 * @author  Adalberto Cerrillo Vazquez
 * @author  34152734
 */
public class CreaTablasConDriverManager {
    private final String nomInstitucion = "institucion_34152734";
    private final String nomSede = "sede_34152734";
    private final String nomConcurso = "concurso_34152734";
    private final String[] nomtablas = {nomInstitucion, nomSede, nomConcurso};
    private Connection conn;


    public CreaTablasConDriverManager(String direccionServidor, String usuario,
                                      String clave, String nomBD)
            throws SQLException {
        String url = String.format("jdbc:mysql://%s/%s", direccionServidor, nomBD);
        conn = DriverManager.getConnection(url,usuario,clave);

    }


    public CreaTablasConDriverManager(Connection conn)
            throws Exception {
        if (conn==null){
            throw new Exception("Conector recibido es nulo");
        }
        this.conn = conn;

    }


    public void creaTablas(boolean debug) throws Exception {
        Statement sentencia;
        String prefijo = "DROP TABLE IF EXISTS ";
        String stringEliminacionInstitucion = prefijo + nomInstitucion;
        String stringEliminacionSede = prefijo + nomSede;
        String stringEliminacionConcurso = prefijo + nomConcurso;

        if (debug){
            System.out.println("Iniciando creacion de tablas ...");
        }
        sentencia = conn.createStatement();
        sentencia.executeUpdate(stringEliminacionInstitucion);
        sentencia.executeUpdate(stringEliminacionSede);
        sentencia.executeUpdate(stringEliminacionConcurso);
        Scanner lector = new Scanner(new File("tablas_ad2021.txt"));
        int i= 0;
        while (lector.hasNext()){
            String sql = lector.nextLine();
            if (debug){
                System.out.println("Creando tabla "+ nomtablas[i]+"..");
            }
            sentencia.executeUpdate(String.format(sql, nomtablas[i]));
            i++;
        }
        sentencia.close();
    } // fin del metodo creaTablas()


    public void llenaTablaInstitucion(boolean debug) throws Exception {
        if (debug){
            System.out.println("\nColocando datos en tabla " + nomInstitucion+":");
        }
        Statement sentencia = conn.createStatement();
        sentencia.executeUpdate("DELETE FROM "+ nomInstitucion);
        PreparedStatement stmtInsertInst = conn.prepareStatement(
                "INSERT INTO "+nomInstitucion+"(nombre_institucion, "+
                    "nombre_corto_institucion, "+
                    "url_institucion, calle_num_institucion, "+
                    "id_entidad_institucion, "+
                    "id_municipio_institucion) "+
                    "VALUES(?,?,?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS);
        BufferedReader lector = new BufferedReader(new InputStreamReader(new FileInputStream("datosInstitucion.txt")));
        String linea = lector.readLine();
        while (linea!=null){
            String[] elems = linea.split(",");
            if (debug){
                System.out.printf("Agregando a la %s, ",elems[1]);
            }
            stmtInsertInst.setString(1,elems[0]);
            stmtInsertInst.setString(2,elems[1]);
            stmtInsertInst.setString(3,elems[2]);
            stmtInsertInst.setString(4,elems[3]);
            stmtInsertInst.setInt(5, Integer.parseInt(elems[4]));
            stmtInsertInst.setInt(6, Integer.parseInt(elems[5]));
            stmtInsertInst.executeUpdate();
            int valorLLaveAutoIncremento = -1;
            ResultSet rs = stmtInsertInst.getGeneratedKeys();
            if(rs.next()){
                valorLLaveAutoIncremento = rs.getInt(1);
            }
            if (debug){
                System.out.println("el id_institucion que le toco fue : " + valorLLaveAutoIncremento);
            }
            linea = lector.readLine();
        }
        lector.close();
        stmtInsertInst.close();
    } // fin del metodo llenaTablaInstitucion()


    public void close() throws Exception {
        if (isConnActive()){
            conn.close();
        }
    }

    public boolean isConnActive() {
        return conn!=null;
    }
}

