package pruebas.PruebasEstructurasLineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ColaEstaticaDePrioridad;

public class PruebaColaDePrioridad {
    public static void main(String args[]){
        ColaEstaticaDePrioridad cola = new ColaEstaticaDePrioridad(5);
        SalidaPorDefecto.consola("Prueba agregar Proceso1 (Prioridad 10): " + cola.poner("Proceso1", 10) + "\n");
        SalidaPorDefecto.consola("Prueba agregar Proceso2 (Prioridad 1): " + cola.poner("Proceso2", 1) + "\n");
        SalidaPorDefecto.consola("Prueba agregar Proceso3 (Prioridad 11): " + cola.poner("Proceso3", 11) + "\n");
        SalidaPorDefecto.consola("Prueba agregar Proceso4 (Prioridad 5): " + cola.poner("Proceso4", 5) + "\n");
        SalidaPorDefecto.consola("Prueba agregar Proceso5 (Prioridad 7): " + cola.poner("Proceso5", 7) + "\n");

        cola.imprimir();
        SalidaPorDefecto.consola("Quitando el siguiente valor: " + cola.quitar() + "\n");
        cola.imprimir();
        SalidaPorDefecto.consola("Quitando el siguiente valor: " + cola.quitar() + "\n");
        cola.imprimir();
        SalidaPorDefecto.consola("Quitando el siguiente valor: " + cola.quitar() + "\n");
        cola.imprimir();
        SalidaPorDefecto.consola("Quitando el siguiente valor: " + cola.quitar() + "\n");
        cola.imprimir();
        SalidaPorDefecto.consola("Quitando el siguiente valor: " + cola.quitar() + "\n");
        cola.imprimir();

        SalidaPorDefecto.consola("Cuando esta vacia, probamos agregar valores nuevos: \n");
        SalidaPorDefecto.consola("Prueba agregar NuevoProceso1 (Prioridad 11): " + cola.poner("NuevoProceso1", 11) + "\n");
        SalidaPorDefecto.consola("Prueba agregar NuevoProceso2 (Prioridad 8): " + cola.poner("NuevoProceso2", 8) + "\n");
        SalidaPorDefecto.consola("Prueba agregar NuevoProceso3 (Prioridad 20): " + cola.poner("NuevoProceso3", 20) + "\n");
        cola.imprimir();

        SalidaPorDefecto.consola("Quitando el siguiente valor: " + cola.quitar() + "\n");
        cola.imprimir();
        SalidaPorDefecto.consola("Quitando el siguiente valor: " + cola.quitar() + "\n");
        cola.imprimir();
        SalidaPorDefecto.consola("Quitando el siguiente valor: " + cola.quitar() + "\n");
        cola.imprimir();

    }
}
