package unlam.taller.vikingos;

public class Viking {
	
	private VikingState state = new NormalState();
	private int health = 100, timesAttacked = 0, attackDamage = 10; // Set some default values
	//Each time the viking is attack, you have to substract the following value from health, depending on the state
	final int receivedDamageInAttack = 5; 
	
	public Viking(Integer initialHealth) throws Exception {
		if(initialHealth < 50) {
			throw new Exception("A Viking must have an initial health greater or equal than 50");
		}
		this.health = initialHealth;
	}
	
	public VikingState getState() {
		return state;
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

	public int getTimesAttacked() {
		return timesAttacked;
	}

	public void setTimesAttacked(int timesAttacked) {
		this.timesAttacked++;
	}

	public int getAttackDamage() {
		return attackDamage;
	}
	
	public int attack() {
		return state.attack(this);
	}

	public void printStatus() {
		state.printStatus();
	}

}
