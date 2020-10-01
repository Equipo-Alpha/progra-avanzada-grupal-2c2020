package loom.vikings.sinstatepattern;

public class Viking {

	public final static int DEFAULT_CAUSED_DAMAGE = 10;

	private String state = "NORMAL";
	private int health = 100;

	final int receivedDamageInAttack = 5;

	public Viking(int health) {
		this.health = health;
		this.state = "NORMAL";
	}

	public int attack() {
		if (this.state == "CALMED DOWN")
			this.state = "NORMAL";

		switch (state) {
		case "NORMAL":
			return DEFAULT_CAUSED_DAMAGE;
		case "CHOLERIC":
			return DEFAULT_CAUSED_DAMAGE * 2;
		case "BERSERKER":
			return DEFAULT_CAUSED_DAMAGE * 3;
		default:
			return DEFAULT_CAUSED_DAMAGE;
		}
	}

	public String getStatus() {
		return this.state;
	}

	public void receiveAttack() {
		switch (this.state) {
		case "CALMED DOWN":
			break;
		case "BERSERKER":
			this.health -= DEFAULT_CAUSED_DAMAGE / 2;
			break;
		case "CHOLERIC":
			this.health -= DEFAULT_CAUSED_DAMAGE * 2;
			this.state = "BERSERKER";
			break;
		case "NORMAL":
			this.health -= DEFAULT_CAUSED_DAMAGE;
			this.state = "CHOLERIC";
			break;
		}
	}

	public int getHealth() {
		return this.health;
	}

	public void meditate() {
		if (this.state != "NORMAL") {
			this.state = "NORMAL";
		} else {
			this.state = "CALMED DOWN";
		}
	}

	public void receiveAttack(Viking v) {
		if (this.state == "CALMED DOWN")
			return;

		switch (v.state) {
		case "BERSERKER":
			this.health -= DEFAULT_CAUSED_DAMAGE / 2;
			changeState();
			break;
		case "CHOLERIC":
			this.health -= DEFAULT_CAUSED_DAMAGE * 2;
			changeState();
			this.state = "BERSERKER";
			break;
		case "NORMAL":
			this.health -= DEFAULT_CAUSED_DAMAGE;
			changeState();
			this.state = "CHOLERIC";
			break;
		}
	}

	private void changeState() {
		switch (this.state) {
		case "CHOLERIC":
			this.state = "BERSERKER";
			break;
		case "NORMAL":
			this.state = "CHOLERIC";
			break;
		}
	}

	public void attack(Viking v) {
		if (this.state == "CALMED DOWN")
			this.state = "NORMAL";
		v.receiveAttack(this);
	}
}
