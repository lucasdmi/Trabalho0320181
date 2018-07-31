package pooa20181.iff.edu.br.trabalho0320181.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import io.realm.Realm;
import pooa20181.iff.edu.br.trabalho0320181.R;
import pooa20181.iff.edu.br.trabalho0320181.adapter.ClickRecyclerViewListener;
import pooa20181.iff.edu.br.trabalho0320181.adapter.OficinaAdapter;
import pooa20181.iff.edu.br.trabalho0320181.model.Oficina;

public class ListaOficina extends AppCompatActivity implements ClickRecyclerViewListener{

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_oficina);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    protected void onResume()
    {
        super.onResume();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvOficinas);
        recyclerView.setAdapter(new OficinaAdapter(getOficinas(), this, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    public List<Oficina> getOficinas()
    {
        return (List) realm.where(Oficina.class).findAll();
    }
    @Override
    public void onClick(Object object) {
       Oficina oficina = (Oficina) object;
    }

    public void finish(){
        super.finish();
        realm.close();
    }
}
