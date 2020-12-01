package equipoalpha.loveletter.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.List;

public class BaseDeDatos {
    public static Logger log = LogManager.getLogger("LoveLeter BD");
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private final EntityManager em;

    public BaseDeDatos() {
        this.em = Persistence.createEntityManagerFactory("equipo-alpha").createEntityManager();
    }

    public void cerrar() {
        em.close();
    }

    public JugadorData getJugadorPorId(int id) {
        JugadorData jugador;
        jugador = em.find(JugadorData.class, id);
        return jugador;
    }

    public JugadorData getJugadorPorNombre(String nombre) {
        JugadorData jugador = null;
        em.getTransaction().begin();
        Query query = em.createNamedQuery("BuscarPorNombre");
        query.setParameter("jugadorNombre", nombre);
        List<JugadorData> jugadores = query.getResultList();
        if (!jugadores.isEmpty()) {
            jugador = jugadores.get(0);
            log.info("Id del usuario: " + jugador.getId());
        }
        em.getTransaction().commit();
        return jugador;
    }

    public void guardarDatosJugador(JugadorData data) {
        em.getTransaction().begin();
        em.merge(data);
        em.getTransaction().commit();
    }

    public void guardarNuevoJugador(JugadorData data) {
        em.getTransaction().begin();
        em.persist(data);
        em.getTransaction().commit();
    }
}
