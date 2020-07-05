package poo.u.tutienda.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import poo.u.tutienda.R;
import poo.u.tutienda.data.Negocio;

import static poo.u.tutienda.MainActivity.listNegocios;

public class SearchFragment extends Fragment {

    EditText buscador;
    SearchAdapter adaptador;
    RecyclerView rv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        buscador = view.findViewById(R.id.searchView);
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new GridLayoutManager(this.getContext(), 1));

        adaptador = new SearchAdapter(this.getContext(), listNegocios);
        rv.setAdapter(adaptador);

        return view;
    }

    public void filter(String txt) {
        ArrayList<Negocio> filterList = new ArrayList<>();
        for(Negocio negocio : listNegocios) {
            if(negocio.getNombre().toLowerCase().contains(txt.toLowerCase())){
                filterList.add(negocio);
            } else if (negocio.getTelefono().toLowerCase().contains(txt.toLowerCase())){
                filterList.add(negocio);
            } else if (negocio.getTipo().toLowerCase().contains(txt.toLowerCase())){
                filterList.add(negocio);
            }
        }

        adaptador.filter(filterList);
    }
}
