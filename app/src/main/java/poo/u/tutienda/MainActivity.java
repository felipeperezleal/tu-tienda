package poo.u.tutienda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import poo.u.tutienda.data.Negocio;
import poo.u.tutienda.data.Perfil;

import static java.lang.Double.parseDouble;

public class MainActivity extends AppCompatActivity {

    private static Perfil perfil;
    Button login, register;
    EditText username, password;
    public static ArrayList<Negocio> listNegocios = new ArrayList();
    public static Negocio mainNegocio;
    private HashMap<String, LatLng> placesDict = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = (EditText) findViewById(R.id.txtUsername);
                password = (EditText) findViewById(R.id.txtPassword);

                String usn = username.getText().toString();
                String pass = password.getText().toString();

                if (validateInput(username, usn) && validateInput(password, pass)){

                    getList().clear();

                    readFile();

                    readPlaces();

                    for (Negocio negocio: listNegocios){
                        String currentNegocio = negocio.getNombre();
                        try{
                            negocio.setStorePlace(placesDict.get(currentNegocio));
                        } catch (Exception ex){

                        }

                    }

                    for (Negocio negocio: listNegocios){        //Setting la tienda del usuario actual y creando el menú

                        if ((negocio.getUsername().equals(usn)) && (negocio.getPassword().equals(pass))){
                            mainNegocio = negocio;

                            Intent menu = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(menu);
                            finish();
                        }
                    }
                }
            }
        });

        register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = (EditText) findViewById(R.id.txtUsername);
                password = (EditText) findViewById(R.id.txtPassword);

                readFile();

                String usn = username.getText().toString();
                String pass = password.getText().toString();

                if (validateInput(username, usn) && validateInput(password, pass)){
                    perfil = new Perfil(usn, pass);
                    Intent crearNegocio = new Intent(MainActivity.this, CrearNegocio.class);
                    startActivity(crearNegocio);
                }
            }
        });

    }

    public static Perfil getProfile(){
        return perfil;
    }

    public static Negocio getNegocio(){
        return mainNegocio;
    }

    public static ArrayList getList(){
        return listNegocios;
    }

    public void addToList(Negocio negocio){
        listNegocios.add(negocio);
    }

    public void readFile() {
        FileInputStream inputStream = null;
        try {
            inputStream = this.openFileInput("data.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            while(true) {
                String usn = bufferedReader.readLine();
                if (usn.equals(null)){
                    break;
                }
                String pass = bufferedReader.readLine();
                String name = bufferedReader.readLine();
                String type = bufferedReader.readLine();
                String tel = bufferedReader.readLine();

                Negocio negocio = new Negocio(usn, pass, name, type, tel);
                addToList(negocio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try{
                    inputStream.close();
                }catch (Exception e){

                }
            }
        }
    }

    public void readPlaces() {
        FileInputStream inputStream = null;
        try {
            inputStream = this.openFileInput("places.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            while (true){
                String place = bufferedReader.readLine();
                if (place.equals(null)){
                    break;
                }
                String lat = bufferedReader.readLine();
                String lon = bufferedReader.readLine();

                LatLng storePlace = new LatLng(parseDouble(lat), parseDouble(lon));

                placesDict.put(place, storePlace);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try{
                    inputStream.close();
                }catch (Exception e){

                }
            }
        }
    }

    public static boolean validateInput(EditText et, String str){
        if (TextUtils.isEmpty(str)){
            et.setError("Rellena el campo");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de Tu Tienda?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent (Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
