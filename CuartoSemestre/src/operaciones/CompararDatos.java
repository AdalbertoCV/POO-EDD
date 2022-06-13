package operaciones;

public class CompararDatos {
    public static boolean objetoMayor(Object objeto1, Object objeto2){
        if( objeto1.toString().compareTo(objeto2.toString())>0){
            return true;
        }else{
                return false;
        }

    }
    public static boolean objetoIgual(Object objeto1, Object objeto2){
        if( objeto1.toString().compareTo(objeto2.toString())==0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean objetoMenor(Object objeto1, Object objeto2){
        if( objeto1.toString().compareTo(objeto2.toString())<0){
            return true;
        }else{
            return false;
        }
    }
}
