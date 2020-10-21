package com.example.votesapp.activities.opciones_votacion;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.votesapp.R;
import com.example.votesapp.services.SalaService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import com.example.votesapp.services.OPVotacionService;

public class OpcionesVotacion extends Fragment {

    private AppBarConfiguration mAppBarConfiguration;
    ViewPager viewPager;
    Adapter adapter;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    List<Modelo_OpVt> items;
    OPVotacionService service;
    private int salaId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_opciones_votacion,container,false);

        this.salaId = getArguments().getInt("param_id");

        //Obtener instancia de la actividad
        viewPager = view.findViewById(R.id.viewPager);
        service = new OPVotacionService(getContext(),viewPager, this.salaId);

        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (viewPager.getAdapter().getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }
                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Boton btnAñadir
        Button btnAgregar = (Button) view.findViewById(R.id.btnAñadir);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewPager.getContext(), CargaDatosOP.class);
                intent.putExtra("param_titulo", "");
                intent.putExtra("param_descripcion", "");
                intent.putExtra("param_editable", false);
                intent.putExtra("param_sala_id", salaId);
                startActivity(intent);
            }
        });

            return view;
        }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.setAdapter(null);
        service = new OPVotacionService(getContext(),viewPager,this.salaId);
    }




}