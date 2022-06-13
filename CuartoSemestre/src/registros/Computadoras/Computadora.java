package registros.Computadoras;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaDinamica;
/**
 * Clase Computadora - Contiene toda la informacion de las computadoras registradas en el sistema
 * @author  Adalberto Cerrillo Vazquez - 4B
 * @version  1.0
 */
public class Computadora {
    protected int numComputadora;
    protected String CentroComputo;
    protected double RAM;
    protected double DiscoDuro;
    protected String procesador;
    protected String marca;
    protected ListaDinamica aplicaciones;
    protected ListaDinamica sesionesUsuarios;

    public Computadora(int numComputadora, String centroComputo, double RAM, double discoDuro, String procesador, String marca, ListaDinamica apps) {
        this.numComputadora = numComputadora;
        this.CentroComputo = centroComputo;
        this.RAM = RAM;
        this.DiscoDuro = discoDuro;
        this.procesador = procesador;
        this.marca = marca;
        this.aplicaciones = apps;
        this.sesionesUsuarios = new ListaDinamica();
    }

    public int getNumComputadora() {
        return numComputadora;
    }

    public void setNumComputadora(int numComputadora) {
        this.numComputadora = numComputadora;
    }

    public String getCentroComputo() {
        return CentroComputo;
    }

    public void setCentroComputo(String centroComputo) {
        CentroComputo = centroComputo;
    }

    public double getRAM() {
        return RAM;
    }

    public void setRAM(double RAM) {
        this.RAM = RAM;
    }

    public double getDiscoDuro() {
        return DiscoDuro;
    }

    public void setDiscoDuro(double discoDuro) {
        DiscoDuro = discoDuro;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Num Computadora:"+numComputadora+ " -CC:" + CentroComputo + " -Marca:" + marca + " -Procesador:" + procesador + " -RAM:" + RAM + " -DD:" + DiscoDuro;
    }

    /**
     * Agregamos una aplicacion a la lista de aplicaciones de la computadora
     * @param app es la aplicacion a agregar
     * @return regresa 0 si se agrego o -1 si no se pudo
     */
    public int agregarAplicacion(Aplicacion app){
        return this.aplicaciones.agregar(app);
    }

    /**
     * Elimina una aplicacion de la lista de aplicaciones de la computadora
     * @param app es la aplicacion a eliminar
     * @return regresa la aplicacion eliminada
     */
    public Object eliminarAplicacion(Aplicacion app){
        return this.aplicaciones.eliminar(app);
    }

    /**
     * Agregamos una nueva sesion a la lista de las sesiones de la computadora
     * @param sesion es la sesion a agregar
     * @return regresa 0 si se pudo agregar la sesion y -1 si no se pudo
     */
    public int agregarSesion(SesionComputadora sesion){
        return this.sesionesUsuarios.agregar(sesion);
    }
    /**
     * Imprime en pantalla todas las caracteristcas de la computadora asi como sus aplicaciones instaladas
     */
    public void ImprimirCaracteristicasYAplicaciones(){
        SalidaPorDefecto.consola("---Mostrando Caracteristicas---\n");
        SalidaPorDefecto.consola("Centro de computo: " + CentroComputo +"\n");
        SalidaPorDefecto.consola("Memoria RAM: " + RAM + "\n");
        SalidaPorDefecto.consola("Disco Duro: " + DiscoDuro + "\n");
        SalidaPorDefecto.consola("Procesador: " + procesador + "\n");
        SalidaPorDefecto.consola("Marca: " + marca + "\n");
        SalidaPorDefecto.consola("---Aplicaciones Instaladas---\n");
        aplicaciones.ImprimirFormatoColumna();
    }

}
