package poo2.progs.main;

public class TotalAcumulado  {
    private static Double total=null;

    public static Double getTotal() {
        return total!=null?total:0;
    }

    public static void acumula(double puntos) {
        if (total==null) {
            total=new Double(0.0);
        }
        total+=puntos;
    }

}
