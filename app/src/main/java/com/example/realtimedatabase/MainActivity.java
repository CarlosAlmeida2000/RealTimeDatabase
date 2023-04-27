package com.example.realtimedatabase;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realtimedatabase.Adaptadores.AdaptadorPersona;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.realtimedatabase.Adaptadores.Persona;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView lstPersonas;
    private ArrayList<Persona> personas;
    public static TextView txtNombres;
    private static TextView txtApellidos;
    private static TextView txtCedula;

    private static String accionActual = "registrar";
    private static String personaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombres = (TextView) findViewById(R.id.txtNombres);
        txtApellidos = (TextView) findViewById(R.id.txtApellidos);
        txtCedula = (TextView) findViewById(R.id.txtCedula);

        lstPersonas = (ListView)findViewById(R.id.lsPersonas);

        cargar_datos();
    }

    public static void seleccionar(Persona persona){
        txtNombres.setText(persona.getNombres());
        txtApellidos.setText(persona.getApellidos());
        txtCedula.setText(persona.getCedula());
        accionActual = "editar";
        personaId = persona.getId();
    }

    private void cargar_datos(){
        try {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("/Uber/Personas");

            //Query query = myRef.orderByChild("nombres").equalTo(q);
            //Query query = myRef.orderByChild("nombres").startAt(q).endAt(q + "\uf8ff");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    personas = new ArrayList<Persona>();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        // Accede a los datos de cada objeto encontrado en la consulta.
                        Persona unaPersona = new Persona();

                        unaPersona.setId(childSnapshot.getKey());
                        unaPersona.setNombres(childSnapshot.child("nombres").getValue(String.class));
                        unaPersona.setApellidos(childSnapshot.child("apellidos").getValue(String.class));
                        unaPersona.setCedula(childSnapshot.child("cedula").getValue(String.class));

                        personas.add(unaPersona);
                    }

                    AdaptadorPersona adaptadorPersona = new AdaptadorPersona(getApplicationContext(), personas);
                    lstPersonas.setAdapter(adaptadorPersona);
                    lstPersonas.refreshDrawableState();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Maneja los errores que puedan ocurrir
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar(View view){

        if (txtNombres.getText().toString().length() > 0 & txtApellidos.getText().toString().length() > 0 & txtCedula.getText().toString().length() > 0){

            Map<String, Object> map = new HashMap<>();
            map.put("nombres", txtNombres.getText().toString().toUpperCase());
            map.put("apellidos", txtApellidos.getText().toString().toUpperCase());
            map.put("cedula", txtCedula.getText().toString());

            if(accionActual.equals("registrar")){
                if(RealTimeDB.post("/Uber/Personas", map)){
                    txtNombres.setText("");
                    txtApellidos.setText("");
                    txtCedula.setText("");
                    Toast.makeText(this, "Datos registrados!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Ups! sucedió un problema vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(RealTimeDB.put("/Uber/Personas/" + personaId, map)){
                    txtNombres.setText("");
                    txtApellidos.setText("");
                    txtCedula.setText("");
                    accionActual = "registrar";
                    Toast.makeText(this, "Datos modificados!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Ups! sucedió un problema vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(this, "Todos los campos son obligatorios!!", Toast.LENGTH_SHORT).show();
        }
    }
}