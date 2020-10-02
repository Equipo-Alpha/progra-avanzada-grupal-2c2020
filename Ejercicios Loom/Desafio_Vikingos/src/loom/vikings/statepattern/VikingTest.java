package loom.vikings.statepattern;

import static org.junit.Assert.*;

import org.junit.Test;

public class VikingTest {

	/*
	 * Tests for Normal state
	 */
	@Test
	public void vikingShouldStartInNormalState() {
		Viking v = new Viking(100);
		
		assertEquals("NORMAL", v.getStatus());
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
		
		assertEquals("CALMED DOWN", v.getStatus());
	}
		
	/*
	 * Tests for Choleric state
	 */	
	@Test
	public void vikingShouldTransitionToCholericWhenReceiveTheFirstAttack() {
		Viking v = new Viking(100);
		
		v.receiveAttack();
		
		assertEquals("CHOLERIC", v.getStatus());
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
		
		v.receiveAttack();// recive damage 10
		v.receiveAttack();// recive damage 20
		
		assertEquals(70, v.getHealth());
	}
	
	@Test
	public void vikingShouldTransitionFromCholericToNormalWhenMeditate() {
		Viking v = new Viking(100);
		
		v.receiveAttack();		
		v.meditate();
		
		assertEquals("NORMAL", v.getStatus());
	}
	
	/*
	 * Tests for Berserker state
	 */		
	@Test
	public void vikingShouldTransitionToBerserkerWhenReceiveTheSecondAttack() {
		Viking v = new Viking(100);
		
		v.receiveAttack();
		v.receiveAttack();
		
		assertEquals("BERSERKER", v.getStatus());
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
		
		v.receiveAttack();// recive damage 10
		v.receiveAttack();// recive damage 20
		v.receiveAttack();// recive damage 5
		
		assertEquals(65, v.getHealth());
	}


	@Test
	public void vikingShouldTransitionFromBerserkerToNormalWhenMeditate() {
		Viking v = new Viking(100);
		
		v.receiveAttack();
		v.receiveAttack();
		v.meditate();
		
		assertEquals("NORMAL", v.getStatus());
	}
}
