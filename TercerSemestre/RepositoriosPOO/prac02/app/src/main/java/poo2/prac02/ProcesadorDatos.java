package poo2.prac02;

import java.util.*;
import java.io.*;
import java.text.*;
public class ProcesadorDatos {

	/** Este metodo recibe una lista de strings que representan
	 *	que pudieran contener cualquier texto, un String que 
	 *	representa un codigo de idioma y un String que representa
	 *	un codigo de pais. El metodo debera verificar cuales
	 *	lineas contienen solo un valor numerico (Double) y deberá regresar
	 *	una lista con strings que representan los valores numericos
	 *	encontrados ordenados del menor al mayor y en el formato de
	 *	moneda especificado por el codigo de idioma y pais recibidos
	 *  @param lista  List de objetos String con las lineas de entrada
	 *  @param codIdioma String que representa el codigo de idioma
	 *  @param codPais String que representa el codigo de pais (posiblemente vacio o null)
	 @  return Lista de Strings que contienen la lista de valores numericos encontrados
	           ordenados del menor al mayor y en el formato de moneda especificado
	           por los argumentos codIdioma y codPais
	*/
	public List<String> procesaListaNumeros(List<String> lineas,String codIdioma, String codPais) {
		List<String> listaRetorno = new ArrayList<>();
		List<Double> listaDoubles = new ArrayList<>();
		Double valActual;
		String cadActual;
		String[] arregloLinea;
		String lineaActual;
		for (int x=0; x<lineas.size();x++){
			lineaActual = lineas.get(x);
			arregloLinea = lineaActual.split(" ");
			if (arregloLinea.length ==1){
			    for (int y=0; y<arregloLinea.length ;y++){
				    try{
			            valActual = Double.parseDouble(arregloLinea[y]);
			            listaDoubles.add(valActual);
                    }
                    catch (NumberFormatException e){
			        }
			    }
			}
			
				
		}
		Collections.sort(listaDoubles, Collections.reverseOrder());
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale(codIdioma,codPais));
		for(int i=0; i<listaDoubles.size();i++){
			cadActual = nf.format(listaDoubles.get(i));
			listaRetorno.add(cadActual);
		}
		return listaRetorno;
	}
}