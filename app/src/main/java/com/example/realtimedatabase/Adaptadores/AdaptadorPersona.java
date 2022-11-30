package com.example.realtimedatabase.Adaptadores;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.realtimedatabase.Adaptadores.Persona;
import com.example.realtimedatabase.R;

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
        cedula.setText(getItem(position).getCedula());

        return(item);
    }
}
