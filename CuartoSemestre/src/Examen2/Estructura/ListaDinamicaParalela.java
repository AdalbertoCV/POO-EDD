package Examen2.Estructura;

import estructuraslineales.ListaDinamica;

public class ListaDinamicaParalela {
    protected ListaDinamica listaSuperior;
    protected ListaDinamica listaInferior;
    protected int numeroElementos;

    public ListaDinamicaParalela(){
        listaInferior = new ListaDinamica();
        listaSuperior = new ListaDinamica();
        numeroElementos =0;
    }

    /**
     * Indica si la lista esta vacia en un momento dado
     * @return regresa true si esta vacia y false si no lo esta
     */
    public boolean vacia(){
        if (numeroElementos==0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Agrega un valor al inicio de la lista
     * @param valor es el valor a agregar
     * @return regresa 0 si se pudo agregar y false si no se pudo
     */
    public int agregarInicio(Object valor){
        if (vacia()){
            if (listaSuperior.agregar(valor)==0){
                numeroElementos++;
                return 0;
            }else{
                return -1;
            }
        }else{
            if (numeroElementos==1){
                if (listaInferior.agregar(listaSuperior.eliminarInicio())==0 && listaSuperior.agregar(valor)==0){
                    numeroElementos++;
                    return 0;
                }else{
                    return -1;
                }
            }else{
                ListaDinamica temporalSup = new ListaDinamica();
                while (!listaSuperior.vacia()){
                    temporalSup.agregar(listaSuperior.eliminarInicio());
                }
                ListaDinamica temporalInf = new ListaDinamica();
                temporalInf.agregar(valor);
                while (!listaInferior.vacia()){
                    temporalInf.agregar(listaInferior.eliminarInicio());
                }
                if (listaSuperior.agregarLista(temporalInf) && listaInferior.agregarLista(temporalSup)){
                    numeroElementos++;
                    return 0;
                }else{
                    return -1;
                }
            }
        }
    }

    /**
     * Inserta un valor al final de la lista
     * @param valor es el valor a instertar
     * @return regresa un 0 si se agrego y -1 si no se pudo
     */
    public int agregarFinal(Object valor){
        if (vacia()){
            if (listaSuperior.agregar(valor)==0){
                numeroElementos++;
                return 0;
            }else{
                return -1;
            }
        }else{
            if (numeroElementos%2==0){
                if (listaSuperior.agregar(valor)==0){
                    numeroElementos++;
                    return 0;
                }else{
                    return -1;
                }
            }else{
                if (listaInferior.agregar(valor)==0){
                    numeroElementos++;
                    return 0;
                }else{
                    return -1;
                }
            }
        }
    }

    public void Imprimir(){
        listaSuperior.imprimir();
        listaInferior.imprimir();
    }

    /**
     * Elimina el primer elmento de la lista
     * @return regresa el elemento si se elimino o null si no
     */
    public Object eliminarInicio(){
        if (!vacia()){
            if (numeroElementos ==1){
                numeroElementos--;
                return listaSuperior.eliminarInicio();
            }else{
                if (numeroElementos<4){
                    if (numeroElementos==3){
                        Object eliminado = listaSuperior.eliminarInicio();
                        listaSuperior.agregarInicio(listaInferior.eliminarInicio());
                        listaInferior.agregarInicio(listaSuperior.eliminar());
                        numeroElementos--;
                        return eliminado;
                    }else{
                        numeroElementos--;
                        return listaInferior.eliminarInicio();
                    }
                }else{
                    Object eliminado = listaSuperior.eliminarInicio();
                    ListaDinamica temporalSup =new ListaDinamica();
                    while(!listaSuperior.vacia()){
                        temporalSup.agregar(listaSuperior.eliminarInicio());
                    }
                    ListaDinamica temporalInf = new ListaDinamica();
                    while(!listaInferior.vacia()){
                        temporalInf.agregar(listaInferior.eliminarInicio());
                    }
                    listaSuperior.agregarLista(temporalInf);
                    listaInferior.agregarLista(temporalSup);
                    return  eliminado;
                }
            }
        }else{
            return null;
        }
    }

    /**
     * Elimina el ultimo elemento de la lista
     * @return regresa el objeto si se elimino y null si no se pudo
     */
    public Object eliminarFinal(){
        if (!vacia()){
            if (numeroElementos==1){
                numeroElementos--;
                return listaSuperior.eliminarInicio();
            }else{
                if (numeroElementos%2==0){
                    numeroElementos--;
                    return listaInferior.eliminar();
                }else{
                    numeroElementos--;
                    return listaSuperior.eliminar();
                }
            }
        }else{
            return null;
        }
    }

    /**
     * Busca un objeto dentro de la lista
     * @param valor es el valor a buscar
     * @return retorna el valor si lo encontro y null si no
     */
    public Object buscar(Object valor){
        Object busqueda1 = listaSuperior.buscar(valor);
        if (busqueda1!= null){
            return busqueda1;
        }else{
            Object busqueda2 = listaInferior.buscar(valor);
            return busqueda2;
        }
    }

    /**
     * Elimina un valor indicado como parametro
     * @param valor es el valor a eliminar
     * @return regresa el valor si lo elimino y null si no
     */
    public Object eliminar(Object valor){
        if (!vacia()){
            Object eliminado = listaSuperior.eliminar(valor);
            if (eliminado!= null){
                numeroElementos--;
                return eliminado;
            }else{
                numeroElementos--;
                return listaInferior.eliminar(valor);
            }
        }else{
            return null;
        }
    }

}
