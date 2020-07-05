package poo.u.tutienda.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import poo.u.tutienda.R;
import poo.u.tutienda.data.Negocio;

import static poo.u.tutienda.MainActivity.getNegocio;
import static poo.u.tutienda.MainActivity.listNegocios;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    MapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapView);

        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;

        LatLng bogota = new LatLng(4.6097100, -74.0817500); //Coordenadas Bogotá
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 11));  //Enfocando Bogotá

        for (Negocio negocio : listNegocios){
            try{
                map.addMarker(new MarkerOptions().position(negocio.getStorePlace()).title(negocio.getNombre()).snippet(negocio.getTipo() +
                        "\n" + negocio.getTelefono()));
            }catch (Exception ex){

            }

        }
    }
}
