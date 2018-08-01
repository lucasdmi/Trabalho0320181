package pooa20181.iff.edu.br.trabalho0320181.activity;

import android.content.Intent;
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
import pooa20181.iff.edu.br.trabalho0320181.adapter.MecanicoAdapter;
import pooa20181.iff.edu.br.trabalho0320181.model.Mecanico;

public class ListaMecanico extends AppCompatActivity implements ClickRecyclerViewListener {

    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mecanico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaMecanico.this, MecanicoDetalhe.class);
                intent.putExtra("id", 0);
                startActivity(intent);
            }
        });
    }

    @Override


    protected void onResume()
    {
        super.onResume();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvMecanicos);
        recyclerView.setAdapter(new MecanicoAdapter(getMecanicos(), this, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public List<Mecanico> getMecanicos()
    {
        return (List) realm.where(Mecanico.class).findAll();
    }
    @Override
    public void onClick(Object object) {
        Mecanico mecanico = (Mecanico) object;
        Intent intent = new Intent(ListaMecanico.this, MecanicoDetalhe.class);
        intent.putExtra("id", mecanico.getId());
        startActivity(intent);
    }

    public void finish(){
        super.finish();
        realm.close();
    }
}
