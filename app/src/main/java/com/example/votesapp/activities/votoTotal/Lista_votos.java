package com.example.votesapp.activities.votoTotal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.votesapp.R;

public class Lista_votos extends Fragment {
    private int idSala;
    ListView listVoto;
    ArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_voto,container,false);

        listVoto = view.findViewById(R.id.listVoto);

        idSala= getArguments().getInt("param_id");
        adapter = new VotoAdapter(getContext(),idSala);
        listVoto.setAdapter(adapter);


        return view;
    }
}
