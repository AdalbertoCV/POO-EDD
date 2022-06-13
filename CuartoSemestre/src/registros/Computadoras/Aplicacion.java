package registros.Computadoras;

/**
 * Clase Aplicacion - Contiene toda la informacion de las aplicaciones instaladas en una computadora
 * @author  Adalberto Cerrillo Vazquez - 4B
 * @version  1.0
 */
public class Aplicacion {
    protected String nombre;
    protected String logo;
    protected String Autores;
    protected String Version;
    protected double RamMinima;

    public Aplicacion(String nom, String log, String aut, String ver, double ram){
        this.nombre = nom;
        this.logo = log;
        this.Autores = aut;
        this.Version = ver;
        this.RamMinima = ram;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAutores() {
        return Autores;
    }

    public void setAutores(String autores) {
        Autores = autores;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public double getRamMinima() {
        return RamMinima;
    }

    public void setRamMinima(double ramMinima) {
        RamMinima = ramMinima;
    }

    @Override
    public String toString() {
        return "Nombre:" +nombre + " -Autores:" + Autores + " -Version:" + Version;
    }
}
