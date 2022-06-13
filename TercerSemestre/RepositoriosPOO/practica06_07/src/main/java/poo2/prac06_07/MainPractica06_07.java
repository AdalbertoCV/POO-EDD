package poo2.prac06_07;

import poo2.prac06.CreaTablasConDriverManager;
import poo2.prac07.ColocaDatosUsandoDataSource;

public class MainPractica06_07 {
    // TODO Al siguiente String ponle como valor tu matricula (en vez de xxxx)
    public static final String matricula="34152734";

    public static final String usuario="IngSW";
    public static final String clave="UAZsw2021";
    public static final String nomdb="controlconcursos_ad2021";
    public static final String ubicacionMySQL="localhost";
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            CreaTablasConDriverManager creadorPrac06 =
                    new CreaTablasConDriverManager(
                            ubicacionMySQL, usuario,
                            clave, nomdb);
            creadorPrac06.creaTablas(true);
            creadorPrac06.llenaTablaInstitucion(true);
            creadorPrac06.close();
        }
        catch (ClassNotFoundException ec) {
            System.err.print("No se encontro el driver de MySQL: ");
            System.err.println(ec.getMessage());
            System.exit(0);
        }
        catch(Exception ex) {
            System.err.println("Error al llamar los metodos de CreaTablasConDriverManager: "
                    + ex.getMessage());
            ex.printStackTrace();
            System.exit(0);
        }

        try {
            ColocaDatosUsandoDataSource creadorPrac07 =
                    new ColocaDatosUsandoDataSource(
                            ubicacionMySQL, usuario,
                            clave, nomdb);
            creadorPrac07.llenaTablaSede(true);
            creadorPrac07.llenaTablaConcurso(true);
            creadorPrac07.close();
        }
        catch(Exception ex) {
            System.err.println("Error al llamar los metodos de ColocaDatosUsandoDataSource: "
                    + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
