package pruebas.PruebasEstructurasLineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ColaDinamica;

public class PruebaColaDinamica {
    public static void main(String args[]){
        SalidaPorDefecto.consola("---Creando Cola Dinamica---\n");
        ColaDinamica cola = new ColaDinamica();
        SalidaPorDefecto.consola("Prueba agregando elemento a cola (Cliente 1): "+ cola.poner("Cliente 1")+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento a cola (Cliente 2): "+ cola.poner("Cliente 2")+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento a cola (Cliente 3): "+ cola.poner("Cliente 3")+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento a cola (Cliente 4): "+ cola.poner("Cliente 4")+"\n");
        cola.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba atendiendo al siguiente: "+ cola.quitar() + "\n");
        cola.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba atendiendo al siguiente: "+ cola.quitar() + "\n");
        cola.imprimir();
        SalidaPorDefecto.consola("\n");
    }
}
