package herramientas.generales;

public class Utilerias {

    public static int compararObjetos(Object objeto1, Object objeto2){
        if(objeto1 instanceof Number && objeto2 instanceof Number){ //numéricos
            Double numero1=Double.parseDouble(objeto1.toString());
            Double numero2=Double.parseDouble(objeto2.toString());
            //Number numero1Number=(Number)objeto1;
            //double numero1=numero1Number.doubleValue();

            if(numero1.doubleValue()>numero2.doubleValue()){
                return 1;
            }else if(numero1.doubleValue()<numero2.doubleValue()){
                return -1;
            }else{
                return 0;
            }
        }else{ //no numéricos: Personba, Alumno, Mueble, Casa...
            // regresa valores mayores a cero si el objeto1 es mayor a objeto2
            //si objeto1 es menor que objeto2  regresa valores menores acero
            //si objeto1 es igual a objeto2 regresa 0
            return objeto1.toString().compareToIgnoreCase(objeto2.toString());
        }
    }
}
