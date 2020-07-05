package poo.u.tutienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import poo.u.tutienda.data.Negocio;
import poo.u.tutienda.data.Perfil;

import static poo.u.tutienda.MainActivity.getProfile;
import static poo.u.tutienda.MainActivity.getList;

public class CrearNegocio extends AppCompatActivity {

    private static Negocio negocio;
    Button empezar, cancelar;
    EditText nombre, tipo, telefono;
    TextView NameStore, TypeStore, Telephone;
    private String carpeta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_negocio);

        nombre = (EditText) findViewById(R.id.txtUsername);
        tipo = (EditText) findViewById(R.id.txtDireccion);
        telefono = (EditText) findViewById(R.id.txtTelefono);

        NameStore = (TextView) findViewById(R.id.txtStoreName);
        TypeStore = (TextView) findViewById(R.id.txtTypeStore);
        Telephone = (TextView) findViewById(R.id.txtTelephone);

        empezar = (Button) findViewById(R.id.btnEmpezar);
        empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nombre.getText().toString();
                String type = tipo.getText().toString();
                String tel = telefono.getText().toString();

                if (MainActivity.validateInput(nombre, name)){
                    if (MainActivity.validateInput(tipo, type)){
                        if (MainActivity.validateInput(telefono, tel)){

                            Perfil profile = getProfile();

                            negocio = new Negocio(profile.getUsername(), profile.getPassword(), name, type, tel);

                            saveFile(negocio);

                            finish();
                        }
                    }
                }
            }
        });

        cancelar = (Button) findViewById(R.id.btnCancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void saveFile (Negocio negocio){
        String filename = "data.txt";
        String jumpln = "\n";
        File file = new File(this.getFilesDir(), filename);
        FileOutputStream outputStream = null;

        try {
            outputStream = openFileOutput(filename, this.MODE_APPEND);

            outputStream.write(negocio.getUsername().getBytes());
            outputStream.write(jumpln.getBytes());
            outputStream.write(negocio.getPassword().getBytes());
            outputStream.write(jumpln.getBytes());
            outputStream.write(negocio.getNombre().getBytes());
            outputStream.write(jumpln.getBytes());
            outputStream.write(negocio.getTipo().getBytes());
            outputStream.write(jumpln.getBytes());
            outputStream.write(negocio.getTelefono().getBytes());
            outputStream.write(jumpln.getBytes());

            Log.d("Guardado", "Fichero guardado en: "+ getFilesDir() + "/" + filename);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
