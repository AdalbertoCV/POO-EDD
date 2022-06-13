package registros.ControlPintores;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Actividad {
    private final String nomActividad;
    private String Pintura;
    private String estiloPintura;
    private String nombreEvento;
    private String lugarEvento;
    private Date fechaEvento;
    private Integer aforoEvento;
    private String lugarFirma;
    private Date fechaFirma;
    private String lugarViaje;
    SimpleDateFormat formato;

    /**
     * Constructor para actividad Pintar
     * @param nompintura nombre de pintura que se esta pintando
     * @param tipoPintura tipo (estilo) de la pintura que se esta pintando
     */
    public Actividad(String nompintura, String tipoPintura){
        nomActividad = "Pintar";
        Pintura = nompintura;
        estiloPintura = tipoPintura;
    }

    /**
     * Constructor pata la actividad Exponer
     * @param nombreExpo nombre de la exposicion
     * @param lugarExpo lugar de la exposicion
     * @param fechaExpo fecha de la exposicion
     * @param aforoExpo cantidad de personas en la exposicion
     */
    public Actividad(String nombreExpo, String lugarExpo, Date fechaExpo, int aforoExpo){
        nomActividad = "Exponer";
        nombreEvento = nombreExpo;
        lugarEvento = lugarExpo;
        fechaEvento = fechaExpo;
        aforoEvento = aforoExpo;
        formato = new SimpleDateFormat("dd/MM/yyyy");
    }

    /**
     * Constructor para la actividad Firmar Autografos
     * @param lugarAutografo lugar de la firma
     * @param fechaAutografo fecha de la firma
     */
    public Actividad(String lugarAutografo, Date fechaAutografo){
        nomActividad = "Firmar Autografos";
        lugarFirma = lugarAutografo;
        fechaFirma = fechaAutografo;
        formato = new SimpleDateFormat("dd/MM/yyyy");
    }

    /**
     * Constructor para la actividad Viajar
     * @param lugar lugar a donde se viaja
     */
    public Actividad(String lugar){
        nomActividad = "Viajar";
        lugarViaje = lugar;
    }

    /**
     * Constructor para la actividad descansar
     */
    public Actividad(){
        nomActividad = "Descansar";
    }

    /**
     * obtiene todos los datos de la actividad
     * @return regresa la cadena con la informacion de la actividad
     */
    public String obtenerDatos(){
        String cadenaRetorno ="Actividad: " + nomActividad;
        if (Pintura!=null){
            cadenaRetorno = cadenaRetorno +" -"+ " Pintura: " + Pintura;
        }
        if (estiloPintura!=null){
            cadenaRetorno = cadenaRetorno +" -"+ " Estilo Pintura: " + estiloPintura;
        }
        if (nombreEvento!=null){
            cadenaRetorno = cadenaRetorno +" -"+ " Nombre Evento: " + nombreEvento;
        }
        if (lugarEvento!= null){
            cadenaRetorno = cadenaRetorno +" -"+ " Lugar Evento: " + lugarEvento;
        }
        if (fechaEvento!= null){
            cadenaRetorno = cadenaRetorno +" -"+ " Fecha Evento: " + formato.format(fechaEvento);
        }
        if (aforoEvento!=null){
            cadenaRetorno = cadenaRetorno +" -"+ " Aforo Evento: " + aforoEvento + " Personas";
        }
        if (lugarFirma!=null){
            cadenaRetorno = cadenaRetorno +" -"+ " Lugar Firma: " + lugarFirma;
        }
        if (fechaFirma!=null){
            cadenaRetorno = cadenaRetorno +" -"+ " Fecha Firma: " + formato.format(fechaFirma);
        }
        if(lugarViaje!=null){
            cadenaRetorno = cadenaRetorno +" -"+ " Lugar Viaje: " + lugarViaje;
        }
        return cadenaRetorno;
    }

    @Override
    public String toString(){
        return nomActividad;
    }

    public String getNomActividad() {
        return nomActividad;
    }

    public String getPintura() {
        return Pintura;
    }

    public String getEstiloPintura() {
        return estiloPintura;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public Integer getAforoEvento() {
        return aforoEvento;
    }

    public String getLugarFirma() {
        return lugarFirma;
    }

    public Date getFechaFirma() {
        return fechaFirma;
    }

    public String getLugarViaje() {
        return lugarViaje;
    }
}