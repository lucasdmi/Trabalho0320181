package pooa20181.iff.edu.br.trabalho0320181.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pooa20181.iff.edu.br.trabalho0320181.R;
import pooa20181.iff.edu.br.trabalho0320181.model.Oficina;

public class OficinaAdapter extends RecyclerView.Adapter{

    private List<Oficina> oficinas;
    private Context context;
    private static ClickRecyclerViewListener clickRecyclerViewListener;

    public OficinaAdapter(List<Oficina> oficinas, Context context, ClickRecyclerViewListener clickRecyclerViewListener){
        this.oficinas = oficinas;
        this.context = context;
        this.clickRecyclerViewListener = clickRecyclerViewListener;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_oficina_cv, parent, false);

        OficinaViewHolder oficinaViewHolder = new OficinaViewHolder(view);

        return oficinaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        OficinaViewHolder oficinaHolder = (OficinaViewHolder) viewHolder;

        Oficina oficina = this.oficinas.get(position);

        oficinaHolder.nome.setText(oficina.getNome());
        oficinaHolder.rua.setText(oficina.getRua());


    }

    @Override
    public int getItemCount() {
        return oficinas.size();
    }


    public class OficinaViewHolder extends RecyclerView.ViewHolder{

        private final TextView nome;
        private final TextView rua;


        public OficinaViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.etNome);
            rua = itemView.findViewById(R.id.etRua);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickRecyclerViewListener.onClick(oficinas.get(getLayoutPosition()));
                }
            });
        }

    }

}
