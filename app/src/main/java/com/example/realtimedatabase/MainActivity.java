package com.example.realtimedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realtimedatabase.Adaptadores.AdaptadorPersona;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.realtimedatabase.Adaptadores.Persona;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private ListView lstPersonas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstPersonas = (ListView)findViewById(R.id.lsPersonas);

        View header = getLayoutInflater().inflate(R.layout.persona, null);
        //lstPersonas.addHeaderView(header);

        cargar_datos();
    }

    private void cargar_datos(){
        try {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("/Uber/Personas");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    try {
                        ArrayList<Persona> personas = new ArrayList<Persona>();
                        String value = dataSnapshot.getValue().toString();
                        JSONObject json_data = new JSONObject(value.toString());
                        System.out.println(json_data);
                        Iterator<?> json_object = json_data.keys();
                        while(json_object.hasNext() ){
                            String key = (String)json_object.next();
                            Iterator<?>datos_persona = json_data.getJSONObject(key).keys();
                            Persona unaPersona = new Persona();
                            while(datos_persona.hasNext()){
                                String key2 = (String)datos_persona.next();
                                String valor = (json_data.getJSONObject(key).get(key2)).toString();
                                switch (key2){
                                    case "nombres":
                                        unaPersona.setNombres(valor);
                                        break;
                                    case "apellidos":
                                        unaPersona.setApellidos(valor);
                                        break;
                                    case "cedula":
                                        unaPersona.setCedula(valor);
                                        break;
                                }
                            }
                            personas.add(unaPersona);
                        }
                        AdaptadorPersona adaptadorPersona = new AdaptadorPersona(getApplicationContext(), personas);
                        lstPersonas.setAdapter(adaptadorPersona);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void guardar(View view){
        try {
            TextView txtnombres = (TextView) findViewById(R.id.txtNombres);
            TextView txtapellidos = (TextView) findViewById(R.id.txtApellidos);
            TextView txtcedula = (TextView) findViewById(R.id.txtCedula);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            JSONObject json = new JSONObject();
            if (txtnombres.getText().toString().length() > 0 & txtapellidos.getText().toString().length() > 0 & txtcedula.getText().toString().length() > 0){
                json.put("nombres", txtnombres.getText().toString());
                json.put("apellidos", txtapellidos.getText().toString());
                json.put("cedula", txtcedula.getText().toString());
                DatabaseReference myRef = database.getReference("/Uber/Personas/Persona"+ txtcedula.getText().toString());
                myRef.setValue(json.toString());
                txtnombres.setText("");
                txtapellidos.setText("");
                txtcedula.setText("");
                Toast.makeText(this, "Guardado!!", Toast.LENGTH_SHORT).show();
                cargar_datos();
            }else{
                Toast.makeText(this, "Todos los campos son obligatorios!!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}