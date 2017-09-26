package br.com.bossini.controleperfilfirebase;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditarUsuarioActivity extends AppCompatActivity {

    private EditText nomeEditText, foneEditText, emailEditText;
    private ImageView fotoImageView;
    private FirebaseDatabaseUtils firebaseDatabaseUtils;
    private FirebaseStorageUtils firebaseStorageUtils;
    private static final int REQUEST_PERMISSION_CAMERA = 1;
    private static final int REQUEST_TIRAR_FOTO = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);
        firebaseDatabaseUtils = FirebaseDatabaseUtils.getInstance();
        firebaseStorageUtils = FirebaseStorageUtils.getInstance();
        nomeEditText = (EditText) findViewById(R.id.nome_edit_text);
        foneEditText = (EditText) findViewById(R.id.fone_edit_text);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        fotoImageView = (ImageView) findViewById(R.id.foto_editar_image_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.confirmar_edicao_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Usuario.getInstance().setNome(nomeEditText.getEditableText().toString());
                    Usuario.getInstance().setFone(foneEditText.getEditableText().toString());
                    Usuario.getInstance().setEmail(emailEditText.getEditableText().toString());
                    firebaseDatabaseUtils.save(Usuario.getInstance());
                }
                catch (Exception e){
                    Toast.makeText(EditarUsuarioActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setValues();
    }

    private void setValues (){
        nomeEditText.setText(Usuario.getInstance().getNome() != null ? Usuario.getInstance().getNome() : "");
        foneEditText.setText(Usuario.getInstance().getFone() != null ? Usuario.getInstance().getFone() : "");
        emailEditText.setText(Usuario.getInstance().getEmail() != null ? Usuario.getInstance().getEmail() : "");
        if (Usuario.getInstance().getFoto() != null)
            fotoImageView.setImageBitmap(Usuario.getInstance().getFoto());
    }

    public void atualizarFoto (View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String []{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
        }
        else{
            tirarFoto();
        }

    }

    private void tirarFoto(){
        Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TIRAR_FOTO);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                tirarFoto();
            }
            else{
                Toast.makeText(this, getString(R.string.explicacao_permissao_foto), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TIRAR_FOTO && resultCode == Activity.RESULT_OK){
            Bitmap foto = (Bitmap)data.getExtras().get("data");
            fotoImageView.setImageBitmap(foto);
            firebaseStorageUtils.save(foto);
            Usuario.getInstance().setFoto(foto);
        }
    }
}
