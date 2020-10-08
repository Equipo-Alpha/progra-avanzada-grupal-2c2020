import static org.junit.Assert.*;

import org.junit.Test;

public class NortaichainTest {

	@Test

	public void noPuedeAtacarFueraDeRango() {

		RazaTest t = new RazaTest(25);

		Nortaichain a = new Nortaichain(3);

		assertEquals(0, a.atacar(t), 0.0);

	}

	@Test

	public void seCuraUnPorcentajeAlAtacar() {

		RazaTest t = new RazaTest(0);

		Nortaichain a = new Nortaichain(3);

		a.salud = 50;

		System.out.println(a.atacar(t));

		assertEquals(51, a.salud, 0.0);

	}

}