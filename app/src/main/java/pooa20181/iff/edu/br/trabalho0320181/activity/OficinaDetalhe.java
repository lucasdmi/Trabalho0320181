package pooa20181.iff.edu.br.trabalho0320181.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import pooa20181.iff.edu.br.trabalho0320181.R;

public class OficinaDetalhe extends AppCompatActivity {

    EditText edtNome, edtRua, edtMunicipio, edtLongitude, edtLatitude;
    Button btnBuscar, btnAdicionar, btnAlterar, btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oficina_detalhe);
    }
}
