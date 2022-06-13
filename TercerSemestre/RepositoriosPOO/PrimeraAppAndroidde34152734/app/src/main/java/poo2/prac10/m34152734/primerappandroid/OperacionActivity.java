package poo2.prac10.m34152734.primerappandroid;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class OperacionActivity extends AppCompatActivity {
    int numero;
    private RadioGroup rgoperaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacion);
        rgoperaciones = findViewById(R.id.rg_operaciones);
        extraeDatos();
    }

    private void extraeDatos(){
        Intent intente = getIntent();
        String valor = intente.getStringExtra("num");
        numero = Integer.parseInt(valor);
        Log.d("P002", "Recibi el valor " + valor);
    }

    public void calculaResultado(View origen){
        int idseleccionado = rgoperaciones.getCheckedRadioButtonId();
        double res = 0.0;
        if (idseleccionado!=-1){
            switch (idseleccionado){
                case R.id.rb_factorial:
                   int prod = 1;
                   for (int i=1;i<=numero; i++){
                     prod = prod * i;
                   }
                   res = prod;
                   break;

                case R.id.rb_cuadrado:
                    res = numero * numero;
                    break;

                case R.id.rb_cubo:
                    res = numero * numero * numero;
                    break;

                case R.id.rb_raiz:
                    res = Math.sqrt(numero);
                    break;
            }
            RadioButton rbSelec = findViewById(idseleccionado);
            String textoBoton = rbSelec.getText().toString();
            regresaResult(res, textoBoton);
        }


    }

    private void regresaResult(double res, String texto){
        Intent intentretorno = new Intent();
        intentretorno.putExtra("res", res);
        intentretorno.putExtra("opcion", texto);
        setResult(RESULT_OK, intentretorno);
        finish();
    }
}