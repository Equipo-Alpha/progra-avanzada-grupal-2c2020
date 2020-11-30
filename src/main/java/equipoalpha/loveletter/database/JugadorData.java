package equipoalpha.loveletter.database;

import javax.persistence.*;

@Entity
@Table( name = "Jugadores" )
@NamedQuery(name = "BuscarPorNombre", query = "SELECT u FROM JugadorData u WHERE u.nombre=:jugadorNombre")
public class JugadorData {
    @Id
    @GeneratedValue(generator="increment", strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, name = "Nombre")
    private String nombre;
    @Column(name = "Contraseña")
    private String password;
    @Column(name = "Victorias")
    private int victorias;
    @Column(name = "Derrotas")
    private int derrotas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }
}
