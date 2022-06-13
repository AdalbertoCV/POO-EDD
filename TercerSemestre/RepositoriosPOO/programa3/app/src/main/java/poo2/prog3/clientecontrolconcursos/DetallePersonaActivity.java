package poo2.prog3.clientecontrolconcursos;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
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
import poo2.prog3.clientecontrolconcursos.entidades.Persona;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DetallePersonaActivity extends AppCompatActivity {
    private FloatingActionButton mFabElimina;
    private FloatingActionButton mFabModifica;
    private FloatingActionButton mFabGuarda;
    private DaoConcursosImpl mDao;
    private Persona mItem;
    private boolean mEsNueva;
    private boolean mCamposActivos;
    private AlertDialog mDialogoConfirmacionEliminar;
    private AlertDialog mDialogoConfirmacionGuardar;
    private View mLayout;
    private EditText mEditemail;
    private EditText mEditNombre;
    private EditText mEditapellidos;
    private EditText mEditsexo;
    private EditText mEditCalle;
    private EditText mEditColonia;
    private EditText mEditIDMun;
    private EditText mEditIDEnt;
    private EditText mEditCodpostal;
    private EditText mEditTelefono;
    private EditText mEditfecha;
    private EditText mEditIDInstitucion;
    private EditText mEditTipo;
    private ProgressBar progressBar;
    private ArrayList<EditText> mEdits;

    private final static int MAXLENGTH_EMAIL = 40;
    private final static int MAXLENGTH_NOM=30;
    private final static int MAXLENGTH_APELLIDOS=30;
    private final static int MAXLENGTH_SEXO=1;
    private final static int MAXLENGTH_CALLE=50;
    private final static int MAXLENGTH_COLONIA=50;
    private final static int MAXLENGTH_CODPOSTAL=5;
    private final static int MAXLENGTH_TELEFONO=10;
    private final static int MAXLENGTH_TIPO = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_persona);
        mDao = new DaoConcursosImpl(getString(R.string.url_base));

        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        mEdits=new ArrayList<>();
        mLayout = findViewById(R.id.container_persona_detalle);

        CollapsingToolbarLayout collLayout=findViewById(R.id.toolbar_layout);
        collLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        int margen =collLayout.getExpandedTitleMarginStart();
        collLayout.setExpandedTitleMarginStart(margen+getResources().getDimensionPixelSize(R.dimen.text_margin));
        collLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mFabElimina = findViewById(R.id.fab_elimina_persona);
        mFabElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogoConfirmacionEliminar.show();
            }
        });

        mFabModifica = findViewById(R.id.fab_modifica_persona);
        mFabModifica.setOnClickListener(view -> {
            habilitaCampos(!mCamposActivos);
            mEditemail.setEnabled(false);

            if (mCamposActivos) {
                mFabModifica.setImageResource(R.drawable.mi_ic_lock);
                mFabGuarda.setVisibility(View.VISIBLE);
            }
            else {
                mFabModifica.setImageResource(R.drawable.mi_ic_lock_open);
                mFabGuarda.setVisibility(View.GONE);
            }
        });
        mFabGuarda = findViewById(R.id.fab_guarda_persona);
        mFabGuarda.setOnClickListener(view -> {
            try {
                guardaInformacion();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        mEditemail = findViewById(R.id.edit_email_persona); mEdits.add(mEditemail);
        mEditNombre = findViewById(R.id.edit_nombre_persona); mEdits.add(mEditNombre);
        mEditapellidos = findViewById(R.id.edit_apellidos_persona); mEdits.add(mEditapellidos);
        mEditsexo = findViewById(R.id.edit_sexo_persona); mEdits.add(mEditsexo);
        mEditCalle = findViewById(R.id.edit_calle_num_persona); mEdits.add(mEditCalle);
        mEditColonia = findViewById(R.id.edit_colonia_persona); mEdits.add(mEditColonia);
        mEditIDMun = findViewById(R.id.edit_id_municipio_persona); mEdits.add(mEditIDMun);
        mEditIDEnt = findViewById(R.id.edit_id_entidad_persona); mEdits.add(mEditIDEnt);
        mEditCodpostal = findViewById(R.id.edit_codpostal_persona); mEdits.add(mEditCodpostal);
        mEditTelefono = findViewById(R.id.edit_telefono_persona); mEdits.add(mEditTelefono);
        mEditfecha = findViewById(R.id.edit_fecha_nac_persona); mEdits.add(mEditfecha);
        mEditIDInstitucion = findViewById(R.id.edit_id_institucion_persona); mEdits.add(mEditIDInstitucion);
        mEditTipo = findViewById(R.id.edit_tipo_persona); mEdits.add(mEditTipo);
        obtenDetalles();
        creaDialogoConfirmacionEliminacion();
        creaDialogoConfirmacionGuardar();
    }

    private void obtenDetalles() {
        Intent intent=getIntent();
        mItem = (Persona) intent.getSerializableExtra("persona");

        if (mItem==null) {
            mFabElimina.setVisibility(View.GONE);
            mFabModifica.setVisibility(View.GONE);
            mEsNueva=true;
        }
        else {
            mFabGuarda.setVisibility(View.GONE);
            habilitaCampos(false);
            mEditemail.setText((mItem.getEmailPersona()));
            mEditNombre.setText(mItem.getNombrePersona());
            mEditapellidos.setText(mItem.getApellidosPersona());
            mEditsexo.setText(mItem.getSexoPersona());
            mEditCalle.setText(mItem.getCalleNumPersona());
            if (mItem.getColoniaPersona()!=null) {
                mEditColonia.setText(mItem.getColoniaPersona());
            }
            mEditIDMun.setText(Long.toString(mItem.getIdMunicipioPersona()));
            mEditIDEnt.setText(Long.toString(mItem.getIdEntidadPersona()));
            if (mItem.getCodpostalPersona()!=null) {
                mEditCodpostal.setText(mItem.getCodpostalPersona());
            }
            mEditTelefono.setText(mItem.getTelefonoPersona());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaComoCadena = sdf.format(mItem.getFechaNacPersona());
            mEditfecha.setText(fechaComoCadena);
            mEditIDInstitucion.setText(Long.toString(mItem.getIdInstitucionPersona()));
            mEditTipo.setText(mItem.getTipoPersona());

            mEsNueva=false;
        }
    }

    private void habilitaCampos(boolean flag) {
        for (EditText et :mEdits) {
            et.setEnabled(flag);
        }
        mCamposActivos=flag;
    }
    private void guardaInformacion() throws ParseException {
        String email=mEditemail.getText().toString();
        String nombre=mEditNombre.getText().toString();
        String apellidos=mEditapellidos.getText().toString();
        String sexo=mEditsexo.getText().toString();
        String calle=mEditCalle.getText().toString();
        String colonia=mEditColonia.getText().toString();
        String idMun=mEditIDMun.getText().toString();
        String idEnt=mEditIDEnt.getText().toString();
        String codpostal=mEditCodpostal.getText().toString();
        String telefono=mEditTelefono.getText().toString();
        String fechaNac = mEditfecha.getText().toString();
        String idInst = mEditIDInstitucion.getText().toString();
        String tipo = mEditTipo.getText().toString();
        if (email.isEmpty() || email.length()>MAXLENGTH_EMAIL || nombre.isEmpty() ||
                nombre.length()>MAXLENGTH_NOM ||
                apellidos.isEmpty() || apellidos.length()>MAXLENGTH_APELLIDOS ||
                sexo.isEmpty() || sexo.length()>MAXLENGTH_SEXO ||
                calle.isEmpty() || calle.length()>MAXLENGTH_CALLE ||
                idMun.isEmpty() || idEnt.isEmpty() || (!sexo.equals("F") && !sexo.equals("M")) ||
                (!colonia.isEmpty() && colonia.length()>MAXLENGTH_COLONIA) ||
                (!codpostal.isEmpty() && codpostal.length()!=MAXLENGTH_CODPOSTAL) ||
                telefono.isEmpty() || telefono.length()!=MAXLENGTH_TELEFONO||
                tipo.isEmpty() || tipo.length() > MAXLENGTH_TIPO || (!tipo.equals("Estudiante") && !tipo.equals("Profesor"))) {
            muestraMensaje(R.string.mensaje_error_campos);
            return;
        }
        if (mItem==null) {
            mItem=new Persona();
        }
        mItem.setEmailPersona((email));
        mItem.setNombrePersona(nombre);
        mItem.setApellidosPersona(apellidos);
        mItem.setSexoPersona(sexo);
        mItem.setCalleNumPersona(calle);
        mItem.setIdMunicipioPersona(Long.parseLong(idMun));
        mItem.setIdEntidadPersona(Long.parseLong(idEnt));
        if (!colonia.isEmpty()) {
            mItem.setColoniaPersona(colonia);
        }
        if (!codpostal.isEmpty()) {
            mItem.setCodpostalPersona(codpostal);
        }
        mItem.setTelefonoPersona(telefono);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = formato.parse(fechaNac);
        mItem.setFechaNacPersona(fecha);
        mItem.setIdInstitucionPersona(Long.parseLong(idInst));
        mItem.setTipoPersona(tipo);


        mDialogoConfirmacionGuardar.show();
    }
    private void regresaDatos(int codigo) {
        Intent datos = new Intent();
        Bundle info = new Bundle();
        info.putInt("operacion", codigo);
        info.putSerializable("persona", mItem);
        datos.putExtras(info);
        setResult(RESULT_OK, datos);
        finish();
    }

    private void creaDialogoConfirmacionEliminacion() {
        AlertDialog.Builder constructor = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        constructor.setTitle(R.string.titulo_confirmacion_eliminar);
        constructor.setMessage(R.string.mensaje_confirmacion_eliminar);

        constructor.setPositiveButton(R.string.titulo_opcion_si, new ManejadorConfirmacion(ListPersonaActivity.OPERACION_ELIMINACION));
        constructor.setNegativeButton(R.string.titulo_opcion_no, null);
        mDialogoConfirmacionEliminar = constructor.create();
    }

    private void creaDialogoConfirmacionGuardar() {
        AlertDialog.Builder constructor = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        constructor.setTitle(R.string.titulo_confirmacion_guardar);
        constructor.setMessage(R.string.mensaje_confirmacion_guardar);

        constructor.setPositiveButton(R.string.titulo_opcion_si, new ManejadorConfirmacion(ListPersonaActivity.OPERACION_ACTUALIZACION));
        constructor.setNegativeButton(R.string.titulo_opcion_no, null);
        mDialogoConfirmacionGuardar = constructor.create();
    }

    private void muestraMensaje(int idMensaje) {
        final Snackbar snack=Snackbar.make(mLayout,idMensaje,Snackbar.LENGTH_LONG).
                setAction(R.string.texto_cerrar, v -> {
                });
        snack.show();
    }

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
            int status = ListPersonaActivity.STATUS_OK;
            progressBar.setVisibility(View.GONE);
            try{
                switch(tipoOperacion){
                    case ListPersonaActivity.OPERACION_ELIMINACION:
                        String id = mItem.getEmailPersona();
                        resultado = mDao.eliminaPersona(id);
                        break;
                    case ListPersonaActivity.OPERACION_ACTUALIZACION:
                        if(mEsNueva){
                            resultado = mDao.agregaPersona(mItem);
                        }
                        else{
                            resultado = mDao.actualizaPersona(mItem);
                        }
                        break;
                }
            }
            catch(MalformedURLException eurl){
                status = ListPersonaActivity.STATUS_URL_INVALIDO;
            }
            catch(IOException eio){
                status = ListPersonaActivity.STATUS_ERROR_IO;
                eio.printStackTrace();
            }
            runOnUiThread(new DetallePersonaActivity.hiloQueMuestraRes(resultado, tipoOperacion, status));
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
                case ListPersonaActivity.STATUS_OK:
                    if (resultado){
                        regresaDatos(tipoOperacion);
                    }
                    else{
                        muestraMensaje(R.string.mensaje_error_operacion);
                    }
                    break;
                case ListPersonaActivity.STATUS_URL_INVALIDO:
                    muestraMensaje(R.string.mensaje_error_url_invalido);
                    break;
                case ListPersonaActivity.STATUS_ERROR_IO:
                    muestraMensaje(R.string.mensaje_error_io);
                    break;
            }
        }
    }
}