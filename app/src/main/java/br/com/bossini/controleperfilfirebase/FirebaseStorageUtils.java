package br.com.bossini.controleperfilfirebase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by rodrigo on 9/26/17.
 */

public class FirebaseStorageUtils {
    private static StorageReference storageRootReference = FirebaseStorage.getInstance().getReference();

    private static FirebaseStorageUtils instance;
    public static FirebaseStorageUtils getInstance (){
        return instance == null ? instance = new FirebaseStorageUtils() : instance;
    }

    public void askUpdate (final Observer observer){
        try{
            final File fileFoto = File.createTempFile("img", "foto.png");
            StorageReference fotoReference = storageRootReference.child("img/foto.png");
            fotoReference.getFile(Uri.fromFile(fileFoto)).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Usuario.getInstance().setFoto(BitmapFactory.decodeFile(fileFoto.getPath()));
                    observer.notificar();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void save (Bitmap foto){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte [] bytes = bos.toByteArray();
        StorageReference ref = storageRootReference.child("img/foto.png");
        ref.putBytes(bytes);    }

    private FirebaseStorageUtils (){}


}
