package com.example.realtimedatabase.Adaptadores;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realtimedatabase.MainActivity;
import com.example.realtimedatabase.R;
import com.example.realtimedatabase.RealTimeDB;

import java.util.ArrayList;

public class AdaptadorPersona extends ArrayAdapter<Persona> {
    public AdaptadorPersona(Context context, ArrayList<Persona> datos) {
        super(context, R.layout.persona, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.persona, null);

        TextView nombres_apellidos = (TextView)item.findViewById(R.id.lblNombresApellidos);
        nombres_apellidos.setText(getItem(position).getNombres() + " " + getItem(position).getApellidos());

        TextView cedula = (TextView)item.findViewById(R.id.lblCedula);
        cedula.setText("Identi: " + getItem(position).getCedula());

        Button btnEditar = (Button) item.findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.seleccionar(getItem(position));
            }
        });

        Button btnEliminar = (Button) item.findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RealTimeDB.delete("/Uber/Personas/" + getItem(position).getId())){
                    Toast.makeText(getContext(), "Persona eliminada!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Ups! sucedió un problema vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return(item);
    }
}
