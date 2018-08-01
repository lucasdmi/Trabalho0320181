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
import java.util.List;

import io.realm.Realm;
import pooa20181.iff.edu.br.trabalho0320181.R;
import pooa20181.iff.edu.br.trabalho0320181.model.Oficina;
import pooa20181.iff.edu.br.trabalho0320181.util.PermissionUtils;

public class OficinaDetalhe extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    EditText edtNome, edtRua, edtMunicipio, edtLongitude, edtLatitude, edtBairro;
    Button btnBuscar, btnAdicionar, btnAlterar, btnExcluir;

    int id;
    Oficina oficina;
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
        setContentView(R.layout.activity_oficina_detalhe);

        edtNome = findViewById(R.id.edtNome);
        edtRua = findViewById(R.id.edtRua);
        edtBairro = findViewById(R.id.edtBairro);
        edtMunicipio = findViewById(R.id.edtMunicipio);
        edtLongitude = findViewById(R.id.edtLongitude);
        edtLatitude = findViewById(R.id.edtLatitude);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnAdicionar = findViewById(R.id.badicionar);
        btnAlterar = findViewById(R.id.balterar);
        btnExcluir = findViewById(R.id.bexcluir);

        Intent intent = getIntent();
        id = (int) intent.getSerializableExtra("id");
        realm = Realm.getDefaultInstance();
        
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar();
            }
        });
        
        callConnection();
        PermissionUtils.validate(this,0, permissoes);
        googleApiClient.connect();
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
            Toast.makeText(this,"Preencha o campo Rua, por favor.", Toast.LENGTH_LONG);
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
            Log.i("Log","Municipio: " + endereco.getSubAdminArea() );
            Log.i("Log","Bairro: " + endereco.getSubLocality() );
            Log.i("Log","Longitude: " + endereco.getLongitude() );
            Log.i("Log","Latitude: " + endereco.getLatitude() );
            latitude = endereco.getLatitude();
            longitude = endereco.getLongitude();
            edtLongitude.setText( Double.toString(longitude));
            edtLatitude.setText((Double.toString(latitude)));

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
