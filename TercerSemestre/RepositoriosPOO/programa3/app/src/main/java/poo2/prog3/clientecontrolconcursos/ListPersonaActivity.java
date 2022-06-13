package poo2.prog3.clientecontrolconcursos;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import poo2.prog3.clientecontrolconcursos.entidades.Persona;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ListPersonaActivity extends AppCompatActivity {
    private List<Persona> mDatos;
    private RecyclerView recyclerView;
    private DaoConcursosImpl mDao;
    private View mLayout;
    private ProgressBar progressBar;
    private PersonaRecyclerViewAdapter adapterPer;


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
        setContentView(R.layout.activity_list_persona);
        mDao = new DaoConcursosImpl(getString(R.string.url_base));
        mLayout=findViewById(R.id.contenedor_lista_personas);

        CollapsingToolbarLayout collLayout=findViewById(R.id.toolbar_layout);
        collLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        int margen =collLayout.getExpandedTitleMarginStart();
        collLayout.setExpandedTitleMarginStart(margen+getResources().getDimensionPixelSize(R.dimen.text_margin));
        collLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabNueva = findViewById(R.id.fab_nueva_persona);
        ManejadorNuevaPersona manNueva = new ManejadorNuevaPersona();
        fabNueva.setOnClickListener(manNueva);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        progressBar=findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.persona_list);
        if (savedInstanceState==null) {
            llenaRecyclerView();
        }
        else {
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            navigateUpTo(new Intent(this, MainActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ManejadorNuevaPersona implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ListPersonaActivity.this, DetallePersonaActivity.class);
            startActivityForResult(intent,SOLICITUD_NUEVA);

        }
    }
    private void llenaRecyclerView() {
        Thread hilo = new Thread(new hiloQueObtienePersonas());
        hilo.start();
    }

    public static class PersonaRecyclerViewAdapter
            extends RecyclerView.Adapter<PersonaRecyclerViewAdapter.ViewHolder> {
        private final ListPersonaActivity mParentActivity;
        private final List<Persona> mValues;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona item = (Persona) view.getTag();
                Intent intent = new Intent(mParentActivity, DetallePersonaActivity.class);
                intent.putExtra("persona", item);
                mParentActivity.startActivityForResult(intent,SOLICITUD_EXISTENTE);
            }
        };
        PersonaRecyclerViewAdapter(ListPersonaActivity parent,
                                       List<Persona> items) {
            mValues = items;
            mParentActivity = parent;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.persona_list_content, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mContentView.setText(mValues.get(position).getApellidosPersona());
            holder.mContentView.setTag(mValues.get(position));
            holder.mContentView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mContentView = view.findViewById(R.id.content);
            }
        }

    }
    private void muestraMensaje(int idMensaje) {
        final Snackbar snack=Snackbar.make(mLayout,idMensaje,Snackbar.LENGTH_LONG).
                setAction(R.string.texto_cerrar, v -> {
                });
        snack.show();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            Bundle datos = data.getExtras();
            Persona per = (Persona) datos.getSerializable("persona");
            if (requestCode == SOLICITUD_NUEVA) {
                mDatos.add(per);
                adapterPer.notifyItemInserted(mDatos.size()-1);
            }
            else {
                int operacion = datos.getInt("operacion");
                int pos = mDatos.indexOf(per);
                if (operacion == OPERACION_ELIMINACION) {
                    mDatos.remove(pos);
                    adapterPer.notifyItemRemoved(pos);
                }
                else {
                    mDatos.set(pos,per);
                    adapterPer.notifyItemChanged(pos);
                }
            }
            muestraMensaje(R.string.mensaje_exito_operacion);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("datos", (ArrayList<Persona>) mDatos);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDatos= (ArrayList<Persona>) savedInstanceState.getSerializable("datos");
        if (mDatos != null) {
            adapterPer = new PersonaRecyclerViewAdapter(this,mDatos);
            recyclerView.setAdapter(adapterPer);
        } else {
            llenaRecyclerView();
        }
    }
    private class hiloQueObtienePersonas implements Runnable{
        public void run(){
            List<Persona> datos = new ArrayList<>();
            int status = STATUS_OK;
            try{
                datos = mDao.obtenPersonas();
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
            runOnUiThread(new hiloQueMuestraPersonas(datos,status));
        }
    }

    private class hiloQueMuestraPersonas implements Runnable{
        private List<Persona> datos;
        private int statusHiloAnterior;

        public hiloQueMuestraPersonas(List<Persona> list, int status){
            datos = list;
            statusHiloAnterior = status;
            mDatos= list;
        }
        public void run(){
           // progressBar.setVisibility(View.GONE);
            switch (statusHiloAnterior){
                case STATUS_OK:
                    if (datos == null || datos.size()==0){
                        muestraMensaje(R.string.mensaje_error_no_hay_registros);
                    }
                    else{
                        adapterPer = new PersonaRecyclerViewAdapter(ListPersonaActivity.this, mDatos);
                        recyclerView.setAdapter(adapterPer);
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