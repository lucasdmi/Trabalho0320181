package pooa20181.iff.edu.br.trabalho0320181.activity;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import pooa20181.iff.edu.br.trabalho0320181.R;
import pooa20181.iff.edu.br.trabalho0320181.model.Mecanico;

public class MecanicoDetalhe extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    EditText edtNome, edtFuncao, edtData, edtRua, edtBairro, edtMunicipio, edtLongitude, edtLatitude;
    Button btBuscar, btAdicionar, btAlterar, btExcluir;

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    int id;
    Mecanico mecanico;
    private Realm realm;


    String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    private Address endereco;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mecanico_detalhe);

        edtNome = (EditText) findViewById(R.id.etNome);
        edtFuncao = (EditText) findViewById(R.id.etFuncao);
        edtData = (EditText) findViewById(R.id.etData);
        edtRua = (EditText) findViewById(R.id.etRua);
        edtBairro = (EditText) findViewById(R.id.etBairro);
        edtMunicipio = (EditText) findViewById(R.id.etMunicipio);
        edtLongitude = (EditText)findViewById(R.id.etLongitude);
        edtLatitude = (EditText) findViewById(R.id.etLatitude);

        btBuscar = findViewById(R.id.btBuscar);
        btAdicionar = findViewById(R.id.btAdicionar);
        btAlterar = findViewById(R.id.btAlterar);
        btExcluir = findViewById(R.id.btExcluir);

        Intent intent = getIntent();
        id = (int) intent.getSerializableExtra("id");
        realm = Realm.getDefaultInstance();

        if(id != 0) {

            btAdicionar.setEnabled(false);
            btAdicionar.setClickable(false);
            btAdicionar.setVisibility(View.INVISIBLE);

            mecanico = realm.where(Mecanico.class).equalTo("id", id).findFirst();

            edtNome.setText(mecanico.getNome());
            edtFuncao.setText(mecanico.getFuncao());
            edtData.setText(formato.format(mecanico.getDataNascimento()));
            edtRua.setText(mecanico.getRua());
            edtBairro.setText(mecanico.getBairro());
            edtMunicipio.setText(mecanico.getMunicipio());
            edtLatitude.setText(mecanico.getLatitude());
            edtLongitude.setText(mecanico.getLongitude());
        }
        else
        {
            btAlterar.setEnabled(false);
            btAlterar.setClickable(false);
            btAlterar.setVisibility(View.INVISIBLE);
            btExcluir.setEnabled(false);
            btExcluir.setClickable(false);
            btExcluir.setVisibility(View.INVISIBLE);
        }
    }


    public void excluir(){
        realm.beginTransaction();
        mecanico.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
        Toast.makeText(this,"Oficina Excluido", Toast.LENGTH_LONG).show();
        this.finish();
    }

    public void salvar()
    {
        int proximoID = 1;
        if(realm.where(Mecanico.class).max("id") != null)
        {
            proximoID = realm.where(Mecanico.class).max("id").intValue() + 1;
        }

        realm.beginTransaction();
        Mecanico mecanico = new Mecanico();
        mecanico.setId(proximoID);

        setarEgravar(mecanico);

        realm.copyToRealm(mecanico);
        realm.commitTransaction();
        realm.close();

        Toast.makeText(this, "Cadastro Efetuado com sucesso", Toast.LENGTH_LONG).show();
        this.finish();
    }

    public void alterar(){

        realm.beginTransaction();
        setarEgravar(mecanico);
        realm.copyFromRealm(mecanico);
        realm.commitTransaction();
        realm.close();

        Toast.makeText(this, "Mecanico alterada", Toast.LENGTH_LONG).show();
        this.finish();
    }

    public void setarEgravar(Mecanico Mecanico)
    {
        mecanico.setNome(edtBairro.getText().toString());
        mecanico.setFuncao(edtFuncao.getText().toString());

        try {
            mecanico.setDataNascimento((Date) formato.parse(edtData.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mecanico.setRua(edtRua.getText().toString());
        mecanico.setBairro(edtBairro.getText().toString());
        mecanico.setMunicipio(edtMunicipio.getText().toString());
        mecanico.setLatitude(edtLatitude.getText().toString());
        mecanico.setLongitude(edtLongitude.getText().toString());
    }


    private synchronized void callConnection() {
        Log.i("LOG", "callConnection()");
        googleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void buscar()
    {
        int i, tam;
        double longitude, latitude;


        if(edtRua.getText() == null)
        {
            Toast.makeText(this,"Preencha o campo Rua, por favor.", Toast.LENGTH_LONG).show();
        }

        String rua = edtRua.getText().toString();
        Log.i("LOG", "Buscando");

        String resultAddres = "";

        try {
            endereco = getEndereco(rua);

            for (i = 0, tam = endereco.getMaxAddressLineIndex(); i < tam; i++)
            {
                resultAddres += endereco.getAddressLine(i);
                resultAddres += i < tam - 1 ? ", " : "";
                Log.i("Log","Result Addres: " + resultAddres );
            }
            Log.i("Log","Result Addres: " + resultAddres );


            edtMunicipio.setText(endereco.getSubAdminArea());
            edtBairro.setText(endereco.getSubLocality());
            latitude = endereco.getLatitude();
            longitude = endereco.getLongitude();

            edtLatitude.setText((Double.toString(latitude)));
            edtLongitude.setText( Double.toString(longitude));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public Address getEndereco(String rua) throws IOException{

        Geocoder geocoder;
        Address endereco = null;
        List<Address> enderecos;
        geocoder = new Geocoder(getApplicationContext());
        enderecos = geocoder.getFromLocationName(rua, 1);

        if(enderecos.size() > 0)
        {
            Log.i("LOG", "Endereços: " +String.valueOf(enderecos.size()));
            Log.i("LOG", "Endereço completo: " + enderecos.get(0));
        }

        endereco = enderecos.get(0);
        return endereco;
    }


    public void onResume() {
        super.onResume();
        if (googleApiClient != null && googleApiClient.isConnected())
            startLocationUpdate();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient != null) {
            stopLocationUpdate();
        }
    }

    private void startLocationUpdate() {
        initLocationRequest();
    }

    private void stopLocationUpdate() {

    }

    private void initLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOG", "UpdateLocationActivity.onConnectionSuspended(" + i + ")");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("LOG", "UpdateLocationActivity.onConnectionFailed(" + connectionResult + ")");
    }
}
