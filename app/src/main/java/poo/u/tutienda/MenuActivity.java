package poo.u.tutienda;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.File;
import java.io.FileOutputStream;

import poo.u.tutienda.data.Negocio;
import poo.u.tutienda.ui.profile.ProfileFragment;

import static poo.u.tutienda.MainActivity.getList;
import static poo.u.tutienda.MainActivity.getNegocio;
import static poo.u.tutienda.MainActivity.mainNegocio;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activity);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_profile, R.id.navigation_search, R.id.navigation_map)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea guardar los cambios y cerrar su sesión?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (ProfileFragment.cambioLocalizacion()){
                                savePlaces();
                            }
                            getList().clear();
                            Intent main = new Intent(MenuActivity.this, MainActivity.class);
                            startActivity(main);
                            finish();
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

    public void savePlaces (){
        String filename = "places.txt";
        String jumpln = "\n";
        File file = new File(this.getFilesDir(), filename);
        FileOutputStream outputStream = null;

        try {
            outputStream = openFileOutput(filename, this.MODE_APPEND);

            outputStream.write(getNegocio().getNombre().getBytes());
            outputStream.write(jumpln.getBytes());
            outputStream.write(ProfileFragment.getLat().getBytes());
            outputStream.write(jumpln.getBytes());
            outputStream.write(ProfileFragment.getLon().getBytes());
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
