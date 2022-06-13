package pruebas.PruebasEstructurasLineales;

import Menus.MenuProcesos;

public class PruebaColaPrioridadDesdeMenu {
    public static void main (String args[]){
        MenuProcesos menu = new MenuProcesos(5,5,5);
        while (true){
            menu.EjecutarOpcion(menu.pedirOpcion());
        }
    }
}
