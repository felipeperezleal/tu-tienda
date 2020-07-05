package poo.u.tutienda.data;

public class Perfil {

    private String username;
    private String password;

    public Perfil() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Perfil(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
