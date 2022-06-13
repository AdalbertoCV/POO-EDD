package poo2.prog3.clientecontrolconcursos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private ImageButton mImageBtnInstitucion;
    private ImageButton mImageBtnPersona;
    private View mLayout;
    public static final int SOLICITUD_PERMISO_ACCESO_A_RED = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.layout_principal);
        TextView mTVAutor = findViewById(R.id.tv_autor);
        mTVAutor.setText(String.format(getString(R.string.etiqueta_autor),
                getString(R.string.nombre_autor)));
        mImageBtnInstitucion = findViewById(R.id.imgbutton_institucion);
        mImageBtnPersona = findViewById(R.id.imagebtn_persona);
        mImageBtnPersona.setEnabled(false);
        mImageBtnInstitucion.setEnabled(false);
        verificaPermisos();
    }

    /**
     * Este metodo verifica si se tienen los permisos necesarios
     * para que la aplicacion funcione correctamente
     */
    private void verificaPermisos() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED) {
            // Permiso ya esta disponible, activa los botones
            mImageBtnPersona.setEnabled(true);
            mImageBtnInstitucion.setEnabled(true);
        } else {
            // No tenemos permiso, hay que solicitarlos.
            solicitaPermisos();
        }
    }

    /**
     * Solicita los permisos requeridos para la aplicacion {@link android.Manifest.permission#INTERNET}.
     * Si se debe desplegar una explicación adicional, se usara un Snackbar
     * para incluir esa informacion adicional.
     */
    private void solicitaPermisos() {
        // El permiso no se ha otorgado y debe ser solicitado.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.INTERNET)) {
            // Proporcionamos una explicacion adicional al usuario si el permiso no fue otorgado
            // para que el usuario entienda porque es necesario este permiso.
            // Muestra un SnackBar con un boton para solicitar el permiso requerido.
            Snackbar.make(mLayout, R.string.permiso_internet_requerido,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.etiqueta_ok, view -> {
                        // Solicitamos el permiso. La respuesta sera verifiada en onRequestPermissionResult()
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.INTERNET},
                                SOLICITUD_PERMISO_ACCESO_A_RED);
                    }).show();

        } else {
            Snackbar.make(mLayout, R.string.internet_no_disponible, Snackbar.LENGTH_LONG).show();
            // Solicitamos el permiso. La respuesta sera verifiada en onRequestPermissionResult()
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, SOLICITUD_PERMISO_ACCESO_A_RED);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SOLICITUD_PERMISO_ACCESO_A_RED) {
            // Verificamos el resultado de la solicitud de permiso.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso ha sido otorgado. Se podra acceder a la informacion.
                Snackbar.make(mLayout, R.string.permiso_internet_otorgado,
                                Snackbar.LENGTH_SHORT)
                        .show();
                mImageBtnPersona.setEnabled(true);
                mImageBtnInstitucion.setEnabled(true);
            } else {
                // La solicitud de permiso fue denegada.
                Snackbar.make(mLayout, R.string.permiso_internet_denegado,
                                Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void ponLista(View origen) {
        int idbtnorigen= origen.getId();
        Intent intent;
        if (idbtnorigen==R.id.imgbutton_institucion) {
            intent=new Intent(this, ListInstitucionActivity.class);
        }
        else {
            intent=new Intent(this, ListPersonaActivity.class);
        }
        startActivity(intent);
    }

}