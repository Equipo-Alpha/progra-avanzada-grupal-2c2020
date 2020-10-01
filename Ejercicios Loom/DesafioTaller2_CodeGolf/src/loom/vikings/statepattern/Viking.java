package loom.vikings.statepattern;

public class Viking {
	
	public final static int DEFAULT_CAUSED_DAMAGE = 10;
	
	private VikingState state = new NormalState();
	
	private int health = 100; // Set some default values
	//Each time the viking is attack, you have to substract the following value from health, depending on the state
	final int receivedDamageInAttack = 5; 
	
	public Viking(int initialHealth) {
		this.health = initialHealth;
	}

	public void setState(VikingState state) {
		this.state = state;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int attack() {
		return state.attack(this);
	}
	
	public void receiveAttack() {
		this.state.receiveAttack(this);
	}
	
	public void meditate() {
		this.state.meditate(this);
	}

	public String getStatus() {
		return state.getStatus();
	}

}
