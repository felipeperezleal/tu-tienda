package poo.u.tutienda.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import poo.u.tutienda.R;
import poo.u.tutienda.data.Negocio;


import static poo.u.tutienda.MainActivity.listNegocios;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.NegocioViewHolder> {


    Context context;
    List<Negocio> listaNegocios = listNegocios;

    public SearchAdapter(Context context, List<Negocio> listaNegocios) {
        this.context = context;
        this.listaNegocios = listaNegocios;
    }

    @NonNull
    @Override
    public NegocioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new NegocioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NegocioViewHolder holder, int i) {
        holder.card_title.setText(listaNegocios.get(i).getNombre());
        holder.card_telephone.setText("Teléfono: " + listaNegocios.get(i).getTelefono());
        holder.card_type.setText("Tipo de tienda: " + listaNegocios.get(i).getTipo());
    }

    @Override
    public int getItemCount() {
        return listaNegocios.size();
    }

    public class NegocioViewHolder extends RecyclerView.ViewHolder {

        TextView card_title, card_telephone, card_type;

        public NegocioViewHolder(@NonNull View itemView) {
            super(itemView);
            card_title = itemView.findViewById(R.id.card_title);
            card_telephone = itemView.findViewById(R.id.card_telephone);
            card_type = itemView.findViewById(R.id.card_type);
        }
    }

    public void filter(ArrayList<Negocio> filtroNegocio){
        this.listaNegocios = filtroNegocio;
        notifyDataSetChanged();
    }
}
