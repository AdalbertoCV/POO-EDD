package poo2.prog3.clientecontrolconcursos;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import poo2.prog3.clientecontrolconcursos.clientes.DaoConcursosImpl;
import poo2.prog3.clientecontrolconcursos.entidades.Institucion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class DetalleInstitucionActivity extends AppCompatActivity {
    private FloatingActionButton mFabElimina;
    private FloatingActionButton mFabModifica;
    private FloatingActionButton mFabGuarda;
    private DaoConcursosImpl mDao;
    private Institucion mItem;
    private boolean mEsNueva;
    private boolean mCamposActivos;
    private AlertDialog mDialogoConfirmacionEliminar;
    private AlertDialog mDialogoConfirmacionGuardar;
    private View mLayout;
    private EditText mEditIDInst;
    private EditText mEditNombreInst;
    private EditText mEditNomCortoInst;
    private EditText mEditURLInst;
    private EditText mEditCalleInst;
    private EditText mEditColoniaInst;
    private EditText mEditIDMunInst;
    private EditText mEditIDEntInst;
    private EditText mEditCodpostalInst;
    private EditText mEditTelefonoInst;
    private ProgressBar progressBar;
    private ArrayList<EditText> mEdits;

    private final static int MAXLENGTH_NOM=100;
    private final static int MAXLENGTH_NOMCORTO=20;
    private final static int MAXLENGTH_URL=200;
    private final static int MAXLENGTH_CALLE=50;
    private final static int MAXLENGTH_COLONIA=50;
    private final static int MAXLENGTH_CODPOSTAL=5;
    private final static int MAXLENGTH_TELEFONO=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_institucion);
        mDao = new DaoConcursosImpl(getString(R.string.url_base));

        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        mEdits=new ArrayList<>();
        mLayout = findViewById(R.id.institucion_detalle_container);

        CollapsingToolbarLayout collLayout=findViewById(R.id.toolbar_layout);
        collLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        int margen =collLayout.getExpandedTitleMarginStart();
        collLayout.setExpandedTitleMarginStart(margen+getResources().getDimensionPixelSize(R.dimen.text_margin));
        collLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Muestra el boton de regreso en la  action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mFabElimina = findViewById(R.id.fab_elimina_inst);
        mFabElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogoConfirmacionEliminar.show();
            }
        });

        mFabModifica = findViewById(R.id.fab_modifica_inst);
        mFabModifica.setOnClickListener(view -> {
            habilitaCampos(!mCamposActivos);
            mEditIDInst.setEnabled(false);

            if (mCamposActivos) {
                mFabModifica.setImageResource(R.drawable.mi_ic_lock);
                mFabGuarda.setVisibility(View.VISIBLE);
            }
            else {
                mFabModifica.setImageResource(R.drawable.mi_ic_lock_open);
                mFabGuarda.setVisibility(View.GONE);
            }
        });
        mFabGuarda = findViewById(R.id.fab_guarda_inst);
        mFabGuarda.setOnClickListener(view -> guardaInformacion());

        mEditIDInst = findViewById(R.id.edit_id_inst); mEdits.add(mEditIDInst);
        mEditNombreInst = findViewById(R.id.edit_nombre_inst); mEdits.add(mEditNombreInst);
        mEditNomCortoInst = findViewById(R.id.edit_nomcorto_inst); mEdits.add(mEditNomCortoInst);
        mEditURLInst = findViewById(R.id.edit_url_inst); mEdits.add(mEditURLInst);
        mEditCalleInst = findViewById(R.id.edit_calle_inst); mEdits.add(mEditCalleInst);
        mEditColoniaInst = findViewById(R.id.edit_colonia_inst); mEdits.add(mEditColoniaInst);
        mEditIDMunInst = findViewById(R.id.edit_idmun_inst); mEdits.add(mEditIDMunInst);
        mEditIDEntInst = findViewById(R.id.edit_ident_inst); mEdits.add(mEditIDEntInst);
        mEditCodpostalInst = findViewById(R.id.edit_codpostal_inst); mEdits.add(mEditCodpostalInst);
        mEditTelefonoInst = findViewById(R.id.edit_tel_inst); mEdits.add(mEditTelefonoInst);
        obtenDetalles();
        creaDialogoConfirmacionEliminacion();
        creaDialogoConfirmacionGuardar();
    }

    /**
     * Este metodo obtiene la informacion que viene en el Intent usado para llamar a esta Activity
     * e inicializa los campos de texto con tal informacion
     */
    private void obtenDetalles() {
        Intent intent=getIntent();
        mItem = (Institucion) intent.getSerializableExtra("institucion");

        if (mItem==null) {
            mFabElimina.setVisibility(View.GONE);
            mFabModifica.setVisibility(View.GONE);
            mEsNueva=true;
        }
        else {
            mFabGuarda.setVisibility(View.GONE);
            habilitaCampos(false);
            mEditIDInst.setText(Long.toString(mItem.getIdInstitucion()));
            mEditNombreInst.setText(mItem.getNombreInstitucion());
            mEditNomCortoInst.setText(mItem.getNombreCortoInstitucion());
            mEditURLInst.setText(mItem.getUrlInstitucion());
            mEditCalleInst.setText(mItem.getCalleNumInstitucion());
            if (mItem.getColoniaInstitucion()!=null) {
                mEditColoniaInst.setText(mItem.getColoniaInstitucion());
            }
            mEditIDMunInst.setText(Long.toString(mItem.getIdMunicipioInstitucion()));
            mEditIDEntInst.setText(Long.toString(mItem.getIdEntidadInstitucion()));
            if (mItem.getCodpostalInstitucion()!=null) {
                mEditCodpostalInst.setText(mItem.getCodpostalInstitucion());
            }
            if (mItem.getTelefonoInstitucion()!=null) {
                mEditTelefonoInst.setText(mItem.getTelefonoInstitucion());
            }
            mEsNueva=false;
        }
    }

    /**
     * Este metodo habilita/deshabilita los campos
     * @param flag true si se desea habilitar, false de lo contrario
     */
    private void habilitaCampos(boolean flag) {
        for (EditText et :mEdits) {
            et.setEnabled(flag);
        }
        mCamposActivos=flag;
    }

    /**
     * Este metodo toma los datos de las cajas de texto, y si son validos
     * solicita al servicio que se actualice o agregue
     */
    private void guardaInformacion() {
        String idInst=mEditIDInst.getText().toString();
        String nomInst=mEditNombreInst.getText().toString();
        String nomCorto=mEditNomCortoInst.getText().toString();
        String url=mEditURLInst.getText().toString();
        String calle=mEditCalleInst.getText().toString();
        String colonia=mEditColoniaInst.getText().toString();
        String idMun=mEditIDMunInst.getText().toString();
        String idEnt=mEditIDEntInst.getText().toString();
        String codpostal=mEditCodpostalInst.getText().toString();
        String telefono=mEditTelefonoInst.getText().toString();
        if (idInst.isEmpty() || nomInst.isEmpty() ||
                nomInst.length()>MAXLENGTH_NOM ||
                nomCorto.isEmpty() || nomCorto.length()>MAXLENGTH_NOMCORTO ||
                url.isEmpty() || url.length()>MAXLENGTH_URL ||
                calle.isEmpty() || calle.length()>MAXLENGTH_CALLE ||
                idMun.isEmpty() || idEnt.isEmpty() ||
                (!colonia.isEmpty() && colonia.length()>MAXLENGTH_COLONIA) ||
                (!codpostal.isEmpty() && codpostal.length()!=MAXLENGTH_CODPOSTAL) ||
                (!telefono.isEmpty() && telefono.length()!=MAXLENGTH_TELEFONO)) {
            muestraMensaje(R.string.mensaje_error_campos);
            return;
        }
        if (mItem==null) {
            mItem=new Institucion();
        }
        mItem.setIdInstitucion(Long.parseLong(idInst));
        mItem.setNombreInstitucion(nomInst);
        mItem.setNombreCortoInstitucion(nomCorto);
        mItem.setUrlInstitucion(url);
        mItem.setCalleNumInstitucion(calle);
        mItem.setIdMunicipioInstitucion(Long.parseLong(idMun));
        mItem.setIdEntidadInstitucion(Long.parseLong(idEnt));
        if (!colonia.isEmpty()) {
            mItem.setColoniaInstitucion(colonia);
        }
        if (!codpostal.isEmpty()) {
            mItem.setCodpostalInstitucion(codpostal);
        }
        if (!telefono.isEmpty()) {
            mItem.setTelefonoInstitucion(telefono);
        }
        mDialogoConfirmacionGuardar.show();
    }


    /**
     * Metodo que crea un Intent para enviar datos de regreso a la activity que llamo a ésta
     * Los datos estaran en un Bundle dentro del intent indicando que operacion se hizo
     * y los datos del usuario (a usarse si la operacion fue crear o actualizar)
     * @param codigo  Codigo que representa la operacion realizada (actualizacion o eliminacion)
     */
    private void regresaDatos(int codigo) {
        Intent datos = new Intent();
        Bundle info = new Bundle();
        info.putInt("operacion", codigo);
        info.putSerializable("institucion", mItem);
        datos.putExtras(info);
        setResult(RESULT_OK, datos);
        finish();
    }

    /**
     * Metodo para crear un cuadro de dialogo de confirmacion de eliminacion
     */
    private void creaDialogoConfirmacionEliminacion() {
        // Se crea un constructor de cuadros de dialogo
        AlertDialog.Builder constructor = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        // Se establece el titulo del cuadro de dialogo
        constructor.setTitle(R.string.titulo_confirmacion_eliminar);
        // Se estable el mensaje del cuadro de dialogo
        constructor.setMessage(R.string.mensaje_confirmacion_eliminar);

        // Se establece el texto del boton de aceptacion asi como el manejador
        constructor.setPositiveButton(R.string.titulo_opcion_si, new ManejadorConfirmacion(ListInstitucionActivity.OPERACION_ELIMINACION));
        // Se establece el texto del boton de cancelacio  asi como el manejador
        constructor.setNegativeButton(R.string.titulo_opcion_no, null);
        // Se crea el cuadro de dialogo
        mDialogoConfirmacionEliminar = constructor.create();
    }

    /**
     * Metodo para crear un cuadro de dialogo de confirmacion de guardar cambios
     */
    private void creaDialogoConfirmacionGuardar() {
        // Se crea un constructor de cuadros de dialogo
        AlertDialog.Builder constructor = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        // Se establece el titulo del cuadro de dialogo
        constructor.setTitle(R.string.titulo_confirmacion_guardar);
        // Se estable el mensaje del cuadro de dialogo
        constructor.setMessage(R.string.mensaje_confirmacion_guardar);

        // Se establece el texto del boton de aceptacion asi como el manejador
        constructor.setPositiveButton(R.string.titulo_opcion_si, new ManejadorConfirmacion(ListInstitucionActivity.OPERACION_ACTUALIZACION));
        // Se establece el texto del boton de cancelacio  asi como el manejador
        constructor.setNegativeButton(R.string.titulo_opcion_no, null);
        // Se crea el cuadro de dialogo
        mDialogoConfirmacionGuardar = constructor.create();
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
     * Manejador de la opcion de SI en el cuadro de confirmacion
     *
     */
    private class ManejadorConfirmacion implements DialogInterface.OnClickListener {
        private final int operacion;

        public ManejadorConfirmacion(int operacion) {
            this.operacion = operacion;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            progressBar.setVisibility(View.VISIBLE);
            Thread hilo = new Thread(new hiloQueActualizaInfo(operacion));
            hilo.start();
        }
    }

    private class hiloQueActualizaInfo implements Runnable{
        private int tipoOperacion;

        public hiloQueActualizaInfo(int tipo){
            tipoOperacion = tipo;
        }
        public void run(){
            boolean resultado = false;
            int status = ListInstitucionActivity.STATUS_OK;
            progressBar.setVisibility(View.GONE);
            try{
                switch(tipoOperacion){
                    case ListInstitucionActivity.OPERACION_ELIMINACION:
                        long id = mItem.getIdInstitucion();
                        resultado = mDao.eliminaInstitucion(id);
                        break;
                    case ListInstitucionActivity.OPERACION_ACTUALIZACION:
                        if(mEsNueva){
                            resultado = mDao.agregaInstitucion(mItem);
                        }
                        else{
                            resultado = mDao.actualizaInstitucion(mItem);
                        }
                        break;
                }
            }
            catch(MalformedURLException eurl){
                status = ListInstitucionActivity.STATUS_URL_INVALIDO;
            }
            catch(IOException eio){
                status = ListInstitucionActivity.STATUS_ERROR_IO;
            }
            runOnUiThread(new hiloQueMuestraRes(resultado, tipoOperacion, status));
        }
    }

    private class hiloQueMuestraRes implements Runnable{
       private boolean resultado;
       private int tipoOperacion;
       private int statusOtroHilo;

       public hiloQueMuestraRes(boolean res, int tipo, int status){
           resultado = res;
           tipoOperacion = tipo;
           statusOtroHilo = status;
       }

       public void run(){
            switch (statusOtroHilo){
                case ListInstitucionActivity.STATUS_OK:
                    if (resultado){
                        regresaDatos(tipoOperacion);
                    }
                    else{
                        muestraMensaje(R.string.mensaje_error_operacion);
                    }
                    break;
                case ListInstitucionActivity.STATUS_URL_INVALIDO:
                    muestraMensaje(R.string.mensaje_error_url_invalido);
                    break;
                case ListInstitucionActivity.STATUS_ERROR_IO:
                    muestraMensaje(R.string.mensaje_error_io);
                    break;
            }
       }
    }
}