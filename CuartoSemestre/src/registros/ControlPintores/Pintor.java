package registros.ControlPintores;
import java.util.Date;

public class Pintor {
    private String nombre;
    private String apellido;
    private Integer edad;
    private Date fechaNacimiento;
    private String Domicilio;
    private String RFC;
    private String NivelEducativo;

    public Pintor(String nom, String ap, Integer ed, Date fechaNac, String dom, String rfc, String nivel){
        nombre = nom;
        apellido = ap;
        edad = ed;
        fechaNacimiento = fechaNac;
        Domicilio = dom;
        RFC = rfc;
        NivelEducativo = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDomicilio() {
        return Domicilio;
    }

    public String getRFC() {
        return RFC;
    }

    public String getNivelEducativo() {
        return NivelEducativo;
    }

    @Override
    public String toString(){
        return RFC;
    }
}
