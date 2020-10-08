import static org.junit.Assert.*;
 
import org.junit.Test;
 
public class GnikgersTest {
 
    public static double TOL = 0.001;
    
    @Test
    public void probarCrearlo() {
        
        Gnikgers g = new Gnikgers();
        assertEquals(168,g.salud, TOL);
        assertEquals(118,g.valorAtaque, TOL);
        assertEquals("Magia",g.arma);
        assertEquals(0,g.getX(), TOL);
 
    }
    
    @Test
    public void probarAtaque() {
        
        Gnikgers g1 = new Gnikgers();
        Gnikgers g2 = new Gnikgers();
        
        g1.atacar(g2);
        
        assertEquals(50,g2.getSalud(),TOL);
    }
    
    @Test
    public void probarRecibirAtaque() {
        
        Gnikgers g1 = new Gnikgers();
        Gnikgers g2 = new Gnikgers();
        
        g1.recibirAtaque(g2);
        assertEquals(50,g2.getSalud(),TOL);
    }
    
    @Test
    public void probarDistanciaAbsurdo() {
        
        Gnikgers g1 = new Gnikgers();
        g1.posicionX = 500;
        Gnikgers g2 = new Gnikgers();
        g2.posicionX = 0;
        
        g1.atacar(g2);
        assertEquals(168,g2.getSalud(),TOL);
    }
 
}