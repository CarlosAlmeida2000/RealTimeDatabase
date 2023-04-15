package com.example.realtimedatabase;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class RealTimeDB {

    public static  boolean post(String ruta, Map<String, Object> map){
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(ruta);
            myRef.push().setValue(map);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static  boolean put(String ruta, Map<String, Object> map){
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(ruta);
            myRef.setValue(map);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean delete(String ruta){
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(ruta);
            myRef.removeValue();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
