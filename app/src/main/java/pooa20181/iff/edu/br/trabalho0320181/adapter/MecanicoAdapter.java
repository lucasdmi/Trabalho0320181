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
import pooa20181.iff.edu.br.trabalho0320181.model.Mecanico;


public class MecanicoAdapter extends RecyclerView.Adapter{

    private List<Mecanico> mecanicos;
    private Context context;
    private static ClickRecyclerViewListener clickRecyclerViewListener;

    public MecanicoAdapter(List<Mecanico> mecanicos, Context context, ClickRecyclerViewListener clickRecyclerViewListener){
        this.mecanicos = mecanicos;
        this.context = context;
        this.clickRecyclerViewListener = clickRecyclerViewListener;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_mecanico_cv, parent, false);

        MecanicoViewHolder mecanicoViewHolder = new MecanicoViewHolder(view);

        return mecanicoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        MecanicoViewHolder mecanicoHolder = (MecanicoViewHolder) viewHolder;

        Mecanico mecanico = this.mecanicos.get(position);

        mecanicoHolder.nome.setText(mecanico.getNome());
        mecanicoHolder.funcao.setText(mecanico.getFuncao());


    }

    @Override
    public int getItemCount() {
        return mecanicos.size();
    }


    public class MecanicoViewHolder extends RecyclerView.ViewHolder{

        private final TextView nome;
        private final TextView funcao;


        public MecanicoViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.tvNome);
            funcao = itemView.findViewById(R.id.tvFuncao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickRecyclerViewListener.onClick(mecanicos.get(getLayoutPosition()));
                }
            });
        }

    }

}
