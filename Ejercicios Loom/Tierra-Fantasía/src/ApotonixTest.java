import static org.junit.Assert.*;

import org.junit.Test;

public class ApotonixTest {

	@Test
	public void debeAtacarCon26PuntosLaPrimeraVez() {
		RazaTest t = new RazaTest(0);
		Apotonix a = new Apotonix(3);
		assertEquals(26, a.atacar(t), 0.0);
	}
	
	@Test
	public void noPuedeAtacarFueraDeRango() {
		RazaTest t = new RazaTest(25);
		Apotonix a = new Apotonix(3);
		
		assertEquals(0, a.atacar(t), 0.0);
	}
	
	@Test
	public void aumentaPuntosDeAtaqueCadaVez() {
		RazaTest t = new RazaTest(0);
		Apotonix a = new Apotonix(3);
		assertEquals(26, a.atacar(t), 0.0);
		assertEquals(28, a.atacar(t), 0.0);
	}
	
	@Test
	public void seCuraUnPorcentajeAlSerAtacado() {
		RazaTest t = new RazaTest(0);
		Apotonix a = new Apotonix(3);
		
		System.out.println(t.atacar(a));
		
		// 98 - 10 = 88 * 1.25 
		
		assertEquals(110, a.getSalud(), 0.0);
	}

}
