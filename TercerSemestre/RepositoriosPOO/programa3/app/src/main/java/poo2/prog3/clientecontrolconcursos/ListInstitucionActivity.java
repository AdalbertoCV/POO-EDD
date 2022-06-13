package poo2.prog3.clientecontrolconcursos;

import android.content.Intent;
import android.view.*;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonParseException;
import poo2.prog3.clientecontrolconcursos.clientes.DaoConcursosImpl;
import poo2.prog3.clientecontrolconcursos.entidades.Institucion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ListInstitucionActivity extends AppCompatActivity {
    private List<Institucion> mDatos;
    private RecyclerView recyclerView;
    private DaoConcursosImpl mDao;
    private View mLayout;
    private ProgressBar progressBar;
    private InstitucionRecyclerViewAdapter adapterInst;


    public static final int STATUS_OK=0;
    public static final int STATUS_URL_INVALIDO = 1;
    public static final int STATUS_ERROR_IO = 2;
    public static final int STATUS_JSON_INVALIDO = 3;

    public static final int SOLICITUD_NUEVA = 100;
    public static final int SOLICITUD_EXISTENTE = 101;

    public static final int OPERACION_ELIMINACION = 200;
    public static final int OPERACION_ACTUALIZACION = 201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_institucion);
        mDao = new DaoConcursosImpl(getString(R.string.url_base));
        mLayout=findViewById(R.id.contenedor_lista_inst);

        // Cambia el tamano de la letra en el titulo de la Action Bar
        CollapsingToolbarLayout collLayout=findViewById(R.id.toolbar_layout);
        collLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        int margen =collLayout.getExpandedTitleMarginStart();
        collLayout.setExpandedTitleMarginStart(margen+getResources().getDimensionPixelSize(R.dimen.text_margin));
        collLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabNueva = findViewById(R.id.fab_nueva_inst);
        ManejadorNuevaInst manNueva = new ManejadorNuevaInst();
        fabNueva.setOnClickListener(manNueva);

        // Muestra el boton de regreso en la  action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        progressBar=findViewById(R.id.progressBar);

        // Se obtiene una referencia al RecyclerView
        recyclerView = findViewById(R.id.institucion_list);
        // Y se obtiene la informacion a colocar ahi
        // (si es que no se se esta reiniciando)
        if (savedInstanceState==null) {
            llenaRecyclerView();
        }
        else {
            progressBar.setVisibility(View.GONE);
        }

    }

    /**
     * Metodo que se ejecuta al seleccionar una opcion de la Action Bar
     * @param item  MenuItem seleccionado
     * @return  true si es posible manejar la opcion seleccionada, false de lo contrario
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            // Este ID representa el boton de regreso o home. Para esta activity,
            // se muestra el boton de regreso. Para mas detalles, consulte
            // el patron de Navigation en el Android Design:
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Clase que se encarga de manejar el evento de darle click
     * al Floating Action Button para agregar una nueva institucion
     */
    private class ManejadorNuevaInst implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ListInstitucionActivity.this, DetalleInstitucionActivity.class);
            startActivityForResult(intent,SOLICITUD_NUEVA);

        }
    }
    /**
     * Metodo para colocar la informacion en un RecyclerView
     */
    private void llenaRecyclerView() {
        Thread hilo = new Thread(new hiloQueObtieneInstituciones());
        hilo.start();
    }

    /**
     * Clase para crear el adaptador con los datos a mostrarse en el RecyclerView de Instituciones
     */
    public static class InstitucionRecyclerViewAdapter
            extends RecyclerView.Adapter<InstitucionRecyclerViewAdapter.ViewHolder> {
        // Almacenamos una referencia a la Activity especifica en la que esta el RecyclerView
        private final ListInstitucionActivity mParentActivity;
        // Guardamos una referencia a la lista de valores a mostrar en el RecyclerView
        private final List<Institucion> mValues;

        // Creamos el manejador para el evento de seleccionar un elemento del RecyclerView
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Institucion item = (Institucion) view.getTag();
                    Intent intent = new Intent(mParentActivity, DetalleInstitucionActivity.class);
                    intent.putExtra("institucion", item);
                    mParentActivity.startActivityForResult(intent,SOLICITUD_EXISTENTE);
            }
        };

        /**
         * Constructor de la clase
         * @param parent  Referencia a la Activity que contiene el RecyclerView
         * @param items  Lista con los datos a mostrar en el RecyclerView
         */
        InstitucionRecyclerViewAdapter(ListInstitucionActivity parent,
                                       List<Institucion> items) {
            mValues = items;
            mParentActivity = parent;
        }

        /**
         * Este metodo se llama al momento de tener que desplegar un elemento del RecyclerView
         * @param parent  ViewGroup que contiene al elemento a mostrar
         * @param viewType  Tipo de View
         * @return  Objeto ViewHolder que contiene el objeto
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.institucion_list_content, parent, false);
            return new ViewHolder(view);
        }

        /**
         * Metodo que rellena los datos que se deben mostrar en la opcion que se debe mostrar
         * @param holder  ViewHolder que contiene el layout donde se colocaran los datos
         * @param position  Indice del elemento a mostrar
         */
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            // Para el caso de Institucion, el layout contiene campos para el
            // id y el nombre corto
            holder.mIdView.setText(Long.toString(mValues.get(position).getIdInstitucion()));
            holder.mContentView.setText(mValues.get(position).getNombreCortoInstitucion());

            // Le asignamos como tag a este View el objeto Institucion correspondiente
            holder.itemView.setTag(mValues.get(position));
            // Le asignamos su manejador de eventos
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        /**
         * Clase interna que representa al ViewHolder de la Institucion a mostrar
         */
        static class ViewHolder extends RecyclerView.ViewHolder {
            // Los atributos dependen de lo que contenga el layout a usar
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.content);
            }
        }

    }

    /**
     * Metodo que muestra un mensaje temporal en la pantalla cuyo texto esta en
     * algun archivo XML de res/values
     *
     * @param idMensaje   ID del string a mostrar
     */
    private void muestraMensaje(int idMensaje) {
        final Snackbar snack=Snackbar.make(mLayout,idMensaje,Snackbar.LENGTH_LONG).
                setAction(R.string.texto_cerrar, v -> {
                    // No necesitamos hacer nada, solo que se cierre
                });
        snack.show();
    }

    /**
     * Metodo que se ejecuta al terminar la activity llamada con
     * startActivityForResult
     *
     * @param requestCode    Codigo enviado al usar startActivityForResult
     * @param resultCode
     *            Codigo que especifica el resultado (RESULT_OK o
     *            RESULT_CANCELLED)
     * @param data
     *            Intent que contiene los datos enviados por la Activity que
     *            termino
     */
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            Bundle datos = data.getExtras();
            Institucion inst = (Institucion) datos.getSerializable("institucion");
            if (requestCode == SOLICITUD_NUEVA) {
                mDatos.add(inst);
                adapterInst.notifyItemInserted(mDatos.size()-1);
            }
            else {
                int operacion = datos.getInt("operacion");
                int pos = mDatos.indexOf(inst);
                if (operacion == OPERACION_ELIMINACION) {
                    mDatos.remove(pos);
                    adapterInst.notifyItemRemoved(pos);
                }
                else {
                    mDatos.set(pos,inst);
                    adapterInst.notifyItemChanged(pos);
                }
            }
            muestraMensaje(R.string.mensaje_exito_operacion);
        }
    }


    /**
     * Metodo que se ejecuta al indicar a la Activity que debe ser destruida
     *
     * @param outState Bundle donde deben colocarse todos los datos a mantener
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("datos", (ArrayList<Institucion>) mDatos);
    }

    /**
     * Metodo que se ejecuta al reiniciar la activity que tuvo que ser destruida
     *
     * @param savedInstanceState contiene los datos almacenados antes de destruir la activity
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDatos= (ArrayList<Institucion>) savedInstanceState.getSerializable("datos");
        if (mDatos != null) {
            adapterInst = new InstitucionRecyclerViewAdapter(this,mDatos);
            recyclerView.setAdapter(adapterInst);
        } else {
            llenaRecyclerView();
        }
    }

    // HACER CLASES PARA REPRESENTAR HILOS. EL PRIMERO DEBE
    // COMUNICARSE CON EL SERVICIO REST PARA OBTENER INFORMACION
    // EL SEGUNDO DEBE MOSTRAR EL RESULTADO EN LA GUI
    private class hiloQueObtieneInstituciones implements Runnable{
        public void run(){
            List<Institucion> datos = new ArrayList<>();
            int status = STATUS_OK;
            try{
                datos = mDao.obtenInstituciones();
            }
            catch(MalformedURLException eurl){
                status = STATUS_URL_INVALIDO;
            }
            catch(IOException eio){
                status = STATUS_ERROR_IO;
            }
            catch(JsonParseException ejson){
                status = STATUS_JSON_INVALIDO;
            }
            runOnUiThread(new hiloQueMuestraInstituciones(datos,status));
        }
    }

    private class hiloQueMuestraInstituciones implements Runnable{
        private List<Institucion> datos;
        private int statusHiloAnterior;

        public hiloQueMuestraInstituciones(List<Institucion> list, int status){
            datos = list;
            statusHiloAnterior = status;
            mDatos= list;
        }
        public void run(){
            progressBar.setVisibility(View.GONE);
            switch (statusHiloAnterior){
                case STATUS_OK:
                    if (datos == null || datos.size()==0){
                        muestraMensaje(R.string.mensaje_error_no_hay_registros);
                    }
                    else{
                        adapterInst = new InstitucionRecyclerViewAdapter(ListInstitucionActivity.this, mDatos);
                        recyclerView.setAdapter(adapterInst);
                    }
                    break;
                case STATUS_URL_INVALIDO:
                    muestraMensaje(R.string.mensaje_error_url_invalido);
                    break;
                case STATUS_ERROR_IO:
                    muestraMensaje(R.string.mensaje_error_io);
                    break;
                case STATUS_JSON_INVALIDO:
                    muestraMensaje(R.string.mensaje_error_json);
                    break;
            }
        }
    }

}