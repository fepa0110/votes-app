package com.example.votesapp.activities.opciones_votacion;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.votesapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter extends PagerAdapter {

    private List<Modelo_OpVt> models;
    private LayoutInflater layoutInflater;
    private Context context;
    private TextView title, desc;
    private int salaId;

    public Adapter(List<Modelo_OpVt> models, Context context, int salaId) {
        this.models = models;
        this.context = context;
        this.salaId = salaId;
        Toast.makeText(context, "OpAdapter: id -> "+salaId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);

        //imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        //imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CargaDatosOP.class);
                intent.putExtra("param_id", models.get(position).getId());
                intent.putExtra("param_sala_id", salaId);
                intent.putExtra("param_titulo", models.get(position).getTitle());
                intent.putExtra("param_descripcion", models.get(position).getDesc());
                intent.putExtra("param_editable", true);
                context.startActivity(intent);
                // finish();
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
