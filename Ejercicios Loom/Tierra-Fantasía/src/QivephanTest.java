import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QivephanTest {

	@Test
	public void test() {
	Raza pj1 = new Qivephan(0);
	Raza pj2 = new Qivephan(30);
	
	/* turno 1 */
	pj1.atacar(pj2);
	pj2.descansar();
	/* turno 2 */
	pj1.atacar(pj2);
	pj2.atacar(pj1);
	assertEquals(95.0, pj2.salud, 0.1);
	}


}