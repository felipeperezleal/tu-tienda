package poo.u.tutienda.data;

import com.google.android.gms.maps.model.LatLng;

public class Negocio extends Perfil{

    private String nombre;
    private String tipo;
    private String telefono;
    public LatLng storePlace;

    public Negocio(String username, String password) {
        super(username, password);
    }

    public Negocio(String username, String password, String nombre, String tipo, String telefono) {
        super(username, password);
        this.nombre = nombre;
        this.tipo = tipo;
        this.telefono = telefono;
    }

    public Negocio(String nombre, String tipo, String telefono){
        this.nombre = nombre;
        this.tipo = tipo;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LatLng getStorePlace() {
        return storePlace;
    }

    public void setStorePlace(LatLng storePlace) {
        this.storePlace = storePlace;
    }

    @Override
    public String toString() {
        return nombre +
                ". Esta tienda le pertenece al usuario: " + getUsername();
    }
}
