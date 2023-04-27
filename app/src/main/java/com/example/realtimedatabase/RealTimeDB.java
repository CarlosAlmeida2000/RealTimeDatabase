package com.example.realtimedatabase;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class RealTimeDB {

    public static  boolean post(String ruta, Map<String, Object> map){
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(ruta);
            String id = myRef.push().getKey(); // Obtén un ID único para el nuevo objeto
            map.put("id", id);
            myRef.child(id).setValue(map).isSuccessful();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean put(String ruta, Map<String, Object> map){
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(ruta);
            myRef.setValue(map).isComplete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean delete(String ruta){
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(ruta);
            myRef.removeValue().isSuccessful();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
