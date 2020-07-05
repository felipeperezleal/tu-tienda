package poo.u.tutienda.ui.profile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

import poo.u.tutienda.MainActivity;
import poo.u.tutienda.R;
import poo.u.tutienda.data.Negocio;

import static android.content.ContentValues.TAG;
import static java.lang.Double.parseDouble;
import static poo.u.tutienda.MainActivity.getNegocio;

public class ProfileFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mMapView;
    private LatLng storePlace;
    static String LatStr, LonStr;
    static boolean change = false;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView StoreName = (TextView) view.findViewById(R.id.txtStoreName);
        TextView TypeStore = (TextView) view.findViewById(R.id.txtTypeStore);
        TextView Telephone = (TextView) view.findViewById(R.id.txtTelephone);
        Button SetLocation = (Button) view.findViewById(R.id.btnSetLocation);
        Button SetCurrentLocation = (Button) view.findViewById(R.id.btnSetCurrentLocation);
        final EditText Lat = (EditText) view.findViewById(R.id.txtLat);
        final EditText Lon = (EditText) view.findViewById(R.id.txtLon);

        final Negocio negocio = getNegocio();

        StoreName.setText("Nombre: "+ negocio.getNombre());
        TypeStore.setText("Tipo de tienda: "+ negocio.getTipo());
        Telephone.setText("Teléfono: "+ negocio.getTelefono());

        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(this);

        SetLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                change = true;
                LatStr = Lat.getText().toString();
                LonStr = Lon.getText().toString();
                if ((MainActivity.validateInput(Lat , LatStr)) && (MainActivity.validateInput(Lon, LonStr))){
                    try{
                        storePlace = new LatLng(parseDouble(LatStr), parseDouble(LonStr));
                        negocio.setStorePlace(storePlace);
                        map.clear();
                        map.addMarker(new MarkerOptions().position(storePlace).title(negocio.getNombre()).snippet(negocio.getTipo() +
                                "\n" + negocio.getTelefono()));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(storePlace ,15));

                    } catch (Exception ex){

                    }

                }
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        SetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getLocation(negocio);
                }else{
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });

        return view;
    }

    public static String getLat(){
        return LatStr;

    }

    public static String getLon(){
        return LonStr;
    }
    public static boolean cambioLocalizacion(){
        return change;
    }

    private void getLocation(final Negocio negocio){
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null){
                    try{
                        Geocoder geocoder = new Geocoder(getContext(),
                                Locale.getDefault());

                        List<Address> adresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                    );

                        try{
                            change = true;
                            LatStr = String.valueOf(adresses.get(0).getLatitude());
                            LonStr = String.valueOf(adresses.get(0).getLongitude());

                            storePlace = new LatLng(adresses.get(0).getLatitude(), adresses.get(0).getLongitude());
                            negocio.setStorePlace(storePlace);
                            map.clear();
                            map.addMarker(new MarkerOptions().position(storePlace).title(negocio.getNombre()).snippet(negocio.getTipo() +
                                    "\n" + negocio.getTelefono()));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(storePlace ,15));

                        } catch (Exception ex){
                            ex.printStackTrace();
                        }

                } catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is showing on the screen");
        MapsInitializer.initialize(getContext());
        map = googleMap;

        try{
            //Coordenadas y enfocando la tienda
            map.addMarker(new MarkerOptions().position(getNegocio().getStorePlace()).title(getNegocio().getNombre()).snippet(getNegocio().getTipo() +
                    "\n" + getNegocio().getTelefono()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(getNegocio().getStorePlace() ,15));
        }catch (Exception e){
            LatLng bogota = new LatLng(4.6097100, -74.0817500); //Coordenadas Bogotá
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 11));  //Enfocando Bogotá
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
