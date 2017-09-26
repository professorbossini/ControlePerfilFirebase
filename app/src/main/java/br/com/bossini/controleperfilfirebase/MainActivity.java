package br.com.bossini.controleperfilfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabaseUtils firebaseDatabaseUtils;
    private FirebaseStorageUtils firebaseStorageUtils;
    private TextView nomeTextView, foneTextView, emailTextView;
    private ImageView fotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabaseUtils = FirebaseDatabaseUtils.getInstance();
        firebaseDatabaseUtils.registerObserver(new Observer() {
            @Override
            public void notificar() {
                setValues();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nomeTextView = (TextView) findViewById(R.id.nome_text_view);
        foneTextView = (TextView) findViewById(R.id.fone_text_view);
        emailTextView = (TextView) findViewById(R.id.email_text_view);
        fotoImageView = (ImageView) findViewById(R.id.foto_image_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.ir_para_edicao_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity (new Intent(MainActivity.this, EditarUsuarioActivity.class));
            }
        });
        firebaseStorageUtils = FirebaseStorageUtils.getInstance();
        firebaseStorageUtils.askUpdate(new Observer() {
            @Override
            public void notificar() {
                fotoImageView.setImageBitmap(Usuario.getInstance().getFoto());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setValues();

    }


    private void setValues(){
        nomeTextView.setText(Usuario.getInstance().getNome() != null ? Usuario.getInstance().getNome() : "");
        foneTextView.setText(Usuario.getInstance().getFone() != null ? Usuario.getInstance().getFone() : "");
        emailTextView.setText(Usuario.getInstance().getEmail() != null ? Usuario.getInstance().getEmail() : "");
        if (Usuario.getInstance().getFoto() != null)
            fotoImageView.setImageBitmap(Usuario.getInstance().getFoto());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
