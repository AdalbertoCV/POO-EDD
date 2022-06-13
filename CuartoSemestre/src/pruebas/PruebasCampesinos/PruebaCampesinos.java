package pruebas;

import entradasalida.EntradaPorDefecto;
import entradasalida.SalidaPorDefecto;
import registros.Campesinos.Campesino;
import registros.Campesinos.MenuCampesinos;

import java.io.IOException;

public class PruebaCampesinos {
    public static void main (String args[]) throws IOException {
        SalidaPorDefecto.consola("...Prueba para clases de campesinos..."+ "\n");
        Campesino campesino1 = new Campesino("Juan Diego", "Garcia");
        Campesino campesino2 = new Campesino("Teodoro", "Campos");
        MenuCampesinos menuCampesinos = new MenuCampesinos();
        SalidaPorDefecto.consola("Prueba llenando la informacion de el campesino 1: "+ menuCampesinos.llenarDesdeArchivo(campesino1, "campesino1")+ "\n");
        SalidaPorDefecto.consola("Prueba llenando la informacion de el campesino 2: "+ menuCampesinos.llenarDesdeArchivo(campesino2, "campesino2")+ "\n");

        int opcion = -1;
        while (opcion!=0){
            menuCampesinos.ImprimirOpciones();
            opcion = EntradaPorDefecto.consolaInt();

            if (opcion == 1){
                menuCampesinos.ImprimirPromedioAnual(menuCampesinos.seleccionarCampesino(campesino1, campesino2));
            }
            if (opcion == 2){
                menuCampesinos.ImprimirMesesMenores(menuCampesinos.seleccionarCampesino(campesino1, campesino2));
            }
            if (opcion == 3){
                menuCampesinos.ImprimirMesesDeMayorProduccion(menuCampesinos.seleccionarCampesino(campesino1, campesino2));
            }
            if (opcion == 4){
                menuCampesinos.ImprimirToneladasUltimoMes(menuCampesinos.seleccionarCampesino(campesino1, campesino2));
            }
            if (opcion == 5){
                menuCampesinos.ImprimirPrimerosTrimestres(menuCampesinos.seleccionarCampesino(campesino1, campesino2));
            }
            if (opcion == 6){
                menuCampesinos.ImprimirComparacionProductividad(campesino1,campesino2);
            }
            if (opcion==7){
                menuCampesinos.ImprimirMejorMesCampesino(menuCampesinos.seleccionarCampesino(campesino1, campesino2));
            }
            if (opcion ==8){
                menuCampesinos.ImprimirMejorEpoca(menuCampesinos.seleccionarCampesino(campesino1, campesino2));
            }

            if (opcion<1 || opcion>8){
                if (opcion==0){
                    SalidaPorDefecto.consola("Hasta Pronto :)");
                }else{
                    menuCampesinos.MensajeOpcionInvalida();
                }
            }
        }
    }
}
