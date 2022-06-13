package Examen2.Pruebas;

import Examen2.DistribucionNormal.DistribucionNormal;
import entradasalida.SalidaPorDefecto;

import java.io.IOException;

public class PruebaDistribucionNormal {
    public static void main (String[] args) throws IOException {
        DistribucionNormal dn = new DistribucionNormal("src\\Examen2\\DistribucionNormal\\X.txt");
        SalidaPorDefecto.consola("Promedio X: " + dn.getPromedioX() + "\n");
        SalidaPorDefecto.consola("Desviacion X: " + dn.getDesviacionX() + "\n");
        SalidaPorDefecto.consola("Promedio Z: " + dn.getPromedioZ() + "\n");
        SalidaPorDefecto.consola("Desviacion Z: " + dn.getDesviacionZ() + "\n");
    }
}
