package poo2.prac10.m34152734.primerappandroid;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText editNumero;
    private TextView tvResultado;
    public static final int solicitud_calculo = 100;
    private EditText editTelefono;
    public static final int SOLICITUD_PERMISOS = 200;
    private Button btnHacerLlamada;
    private EditText editTextoMensaje;
    private EditText editUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editNumero = findViewById(R.id.editNumero);
        tvResultado = findViewById(R.id.tv_resultado);
        editTelefono = findViewById(R.id.edit_telefono);
        btnHacerLlamada = findViewById(R.id.btn_llamar);
        editTextoMensaje = findViewById(R.id.editTextoMensaje);
        editUrl = findViewById(R.id.editurl);
    }

    public void factorial(View origen) {
        String contenido = editNumero.getText().toString();
        if (contenido.length() > 0) {
            Integer n = Integer.parseInt(contenido);
            int prod = 1;
            for (int i = 1; i <= n; i++) {
                prod = prod * i;
            }
            String formato = getString(R.string.str_resultado);
            String formatoResultante = String.format(formato, contenido, prod);
            tvResultado.setText(formatoResultante);
        }
    }

    public void llamadaNormalActivity(View origen) {
        Intent intent = new Intent(this, OperacionActivity.class);
        startActivity(intent);
    }

    public void llamadaNormalActivityPasandoDatos(View origen) {
        String contenido = editNumero.getText().toString();
        if (contenido.length() > 0) {
            Intent intent = new Intent(this, OperacionActivity.class);
            intent.putExtra("num", contenido);
            startActivity(intent);
        }
    }

    public void llamadaConResultados(View Origen) {
        String contenido = editNumero.getText().toString();
        if (contenido.length() > 0) {
            Intent intent = new Intent(this, OperacionActivity.class);
            intent.putExtra("num", contenido);
            startActivityForResult(intent, solicitud_calculo);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == solicitud_calculo) {
            if (resultCode == RESULT_OK) {
                double resultado = data.getDoubleExtra("res", 0.0);
                String texto = data.getStringExtra("opcion");
                String txtRes = String.format(getString(R.string.str_res_gen), texto, editNumero.getText().toString(), resultado);
                tvResultado.setText(txtRes);
            }
        }
    }

    public void abreAppLlamada(View origen) {
        String numTel = editTelefono.getText().toString();
        if (numTel.length() > 0) {
            Intent intTel = new Intent(Intent.ACTION_DIAL);
            intTel.setData(Uri.parse("tel:" + numTel));
            try {
                startActivity(intTel);
            } catch (ActivityNotFoundException ex) {
            }
        }
    }

    /**
     * Este metodo verifica si se tienen los permisos necesarios, y si no se tiene
     * se deben solicitar
     */
    private void checaPermisos() {
        Log.d("MiApp", "Entrando a checar permisos");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // NO ME HAN OTORGADO EL PERMISO
            Log.d("MiApp", "No me han otorgado permiso");

// Deberia mostrar una explicacion de porque necesito el permiso?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                // Hay que mostrar una explicacion mas detallada de las
                // causas del permiso y esperar a que el usuario decida
                // ESTA EXPLICACION DEBE SER ASINCRONA
                Log.d("MiApp", "Se da una mayor explicacion del permiso");
            } else {
                // Apenas vamos a pedir el permiso por primera vez
                Log.d("MiApp", "Pedimos el permiso");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        SOLICITUD_PERMISOS);
            }
        } else {
            // Si tengo los permisos que necesito
            Log.d("MiApp", "Si tengo el permiso necesario");
            btnHacerLlamada.setEnabled(true);
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == SOLICITUD_PERMISOS) {
            if (grantResults.length>0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

// EL usuario decidio darme los permisos
                Log.d("MiApp", "El usuario me otorga permisos");
                btnHacerLlamada.setEnabled(true);
            }
            else {
                Log.d("MiApp","El usuario NO me dio los permisos");
            }
        }
    }

    public void enviarSMS(View Origen){
        String numTel = editTelefono.getText().toString();
        if (numTel.length()>0){
            String mensaje = editTextoMensaje.getText().toString();
            Intent intSMS = new Intent(Intent.ACTION_SENDTO);
            intSMS.setData(Uri.parse("smsto:"+numTel));
            intSMS.putExtra("sms_body", mensaje);
            try{
                startActivity(intSMS);
            }
            catch (ActivityNotFoundException ex){
            }
        }
    }

    public void irAURL(View Origen){
        String contenido = editUrl.getText().toString();
        if (contenido.length()>0){
            Intent intenturl = new Intent(Intent.ACTION_VIEW);
            intenturl.setData(Uri.parse(contenido));
            try{
                startActivity(intenturl);
            }
            catch(ActivityNotFoundException ex){
            }
        }
    }

    public void agregaEvento(View origen){
        Intent intentEvento = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar inicio = Calendar.getInstance();
        inicio.set(2021,10,11,14,05);
        Calendar fin = Calendar.getInstance();
        fin.set(2021, 10,11,14,30);
        intentEvento.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,inicio.getTimeInMillis());
        intentEvento.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fin.getTimeInMillis());
        intentEvento.putExtra(CalendarContract.Events.TITLE, "E3 2021");
        intentEvento.putExtra(CalendarContract.Events.EVENT_LOCATION, "L.A California");
        try{
            startActivity(intentEvento);
        }
        catch(ActivityNotFoundException ex){
        }
    }
    public void obtenMapa (View origen){
        Uri ubicacion = Uri.parse("geo:22°45'10.1, 102°28'57.6");
        Intent intentmap = new Intent(Intent.ACTION_VIEW, ubicacion);
        try{
            startActivity(intentmap);
        }
        catch(ActivityNotFoundException ex){
        }
    }
}