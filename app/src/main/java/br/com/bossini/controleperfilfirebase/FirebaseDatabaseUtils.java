package br.com.bossini.controleperfilfirebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 9/26/17.
 */

public class FirebaseDatabaseUtils implements  Observable {

    private static FirebaseDatabaseUtils instance;
    public static FirebaseDatabaseUtils getInstance (){
        return instance == null ? instance = new FirebaseDatabaseUtils() : instance;
    }
    private List <Observer> observers = new ArrayList<>();
    private static DatabaseReference usuarioReference;
    private static FirebaseDatabase database;

    static{
        database = FirebaseDatabase.getInstance();
        usuarioReference = database.getReference("usuario");
    }

    private FirebaseDatabaseUtils (){
        usuarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuarioAtualizado = dataSnapshot.getValue(Usuario.class);
                Usuario.getInstance().setNome(usuarioAtualizado.getNome());
                Usuario.getInstance().setFone(usuarioAtualizado.getFone());
                Usuario.getInstance().setEmail(usuarioAtualizado.getEmail());
                notifyObservers();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.notificar();
    }


    public void save (Usuario usuario){
        usuarioReference.setValue(usuario);
    }
}
