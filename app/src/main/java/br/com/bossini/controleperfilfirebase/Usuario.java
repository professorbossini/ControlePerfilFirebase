package br.com.bossini.controleperfilfirebase;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by rodrigo on 9/25/17.
 */

public class Usuario implements Serializable {

    private transient static Usuario instance;

    @Exclude
    public static Usuario getInstance (){
        return instance == null ? instance = new Usuario () : instance;
    }

    private String nome, fone, email;
    private transient Bitmap foto;

    @Exclude
    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario (){}

    public Usuario (String nome, String fone, String email){
        this.nome = nome;
        this.fone = fone;
        this.email = email;
    }
}
