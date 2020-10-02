package loom.vikings.sinstatepattern;

import static org.junit.Assert.*;

import org.junit.Test;
//import static org.junit.Assert.assertEquals;
public class VikingTest {

	/*
	 * Tests for Normal state
	 */
	@Test
	public void vikingShouldStartInNormalState() {
		Viking v = new Viking(100);

		assertEquals(Estado.NORMAL, v.getStatus());
	}

	@Test
	public void vikingShouldCause10DamagePointsInNormalState() {
		Viking v = new Viking(100);

		assertEquals(10, v.attack());
	}

	@Test
	public void vikingShouldReceive10DamagePointsInNormalState() {
		Viking v = new Viking(100);

		v.receiveAttack();

		assertEquals(90, v.getHealth());
	}

	@Test
	public void vikingShouldTransitionFromNormalToCalmedDown() {
		Viking v = new Viking(100);

		v.meditate();

		assertEquals(Estado.CALMEDDOWN, v.getStatus());
	}

	/*
	 * Tests for Calmed down state
	 */
	@Test
	public void vikingInCalmedDownReceiveDamageAndAttack() {
		Viking v = new Viking(100);
		Viking enem = new Viking(100);

		v.meditate();

		assertEquals(Estado.CALMEDDOWN, v.getStatus());

		enem.attack(v);

		assertEquals(100, v.getHealth());
		
		v.attack();

		assertEquals(Estado.NORMAL, v.getStatus());
	}

	/*
	 * Tests for Choleric state
	 */
	@Test
	public void vikingShouldTransitionToCholericWhenReceiveTheFirstAttack() {
		Viking v = new Viking(100);

		v.receiveAttack();

		assertEquals(Estado.CHOLERIC, v.getStatus());
	}

	@Test
	public void vikingShouldCauseDoubleDamagePointsInCholericState() {
		Viking v = new Viking(100);

		v.receiveAttack();

		assertEquals(20, v.attack());
	}

	@Test
	public void vikingShouldReceiveDoubleDamagePointsInCholericState() {
		Viking v = new Viking(100);

		v.receiveAttack();
		v.receiveAttack();

		assertEquals(70, v.getHealth());
	}

	@Test
	public void vikingShouldTransitionFromCholericToNormalWhenMeditate() {
		Viking v = new Viking(100);

		v.receiveAttack();
		v.meditate();

		assertEquals(Estado.NORMAL, v.getStatus());
	}

	/*
	 * Tests for Berserker state
	 */
	@Test
	public void vikingShouldTransitionToBerserkerWhenReceiveTheSecondAttack() {
		Viking v = new Viking(100);

		v.receiveAttack();
		v.receiveAttack();

		assertEquals(Estado.BERSERKER, v.getStatus());
	}

	@Test
	public void vikingShouldCauseTripleDamagePointsInBerserkerState() {
		Viking v = new Viking(100);

		v.receiveAttack();
		v.receiveAttack();

		assertEquals(30, v.attack());
	}

	@Test
	public void vikingShouldReceiveHalfOfDamangeOnBerserkerState() {
		Viking v = new Viking(100);

		v.receiveAttack();
		v.receiveAttack();
		v.receiveAttack();

		assertEquals(65, v.getHealth());
	}

	@Test
	public void vikingShouldTransitionFromBerserkerToNormalWhenMeditate() {
		Viking v = new Viking(100);

		v.receiveAttack();
		v.receiveAttack();
		v.meditate();

		assertEquals(Estado.NORMAL, v.getStatus());
	}
}