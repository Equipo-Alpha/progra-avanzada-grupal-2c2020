package loom.vikings.sinstatepattern;

enum Estado {
	
	NORMAL, CALMEDDOWN, CHOLERIC, BERSERKER;
	
}

public class Viking {

	public final static int DEFAULT_CAUSED_DAMAGE = 10;

	private Estado state = Estado.NORMAL;
	private int health = 100;

	final int receivedDamageInAttack = 5;

	public Viking(int health) {
		this.health = health;
		this.state = Estado.NORMAL;
	}

	public int attack() {
		if (this.state == Estado.CALMEDDOWN)
			this.state = Estado.NORMAL;

		switch (state) {
		case NORMAL:
			return DEFAULT_CAUSED_DAMAGE;
		case CHOLERIC:
			return DEFAULT_CAUSED_DAMAGE * 2;
		case BERSERKER:
			return DEFAULT_CAUSED_DAMAGE * 3;
		default:
			return DEFAULT_CAUSED_DAMAGE;
		}
	}

	public Estado getStatus() {
		return this.state;
	}

	public void receiveAttack() {
		switch (this.state) {
		case CALMEDDOWN:
			break;
		case BERSERKER:
			this.health -= DEFAULT_CAUSED_DAMAGE / 2;
			break;
		case CHOLERIC:
			this.health -= DEFAULT_CAUSED_DAMAGE * 2;
			this.state = Estado.BERSERKER;
			break;
		case NORMAL:
			this.health -= DEFAULT_CAUSED_DAMAGE;
			this.state = Estado.CHOLERIC;
			break;
		}
	}

	public int getHealth() {
		return this.health;
	}

	public void meditate() {
		if (this.state != Estado.NORMAL && this.state != Estado.CALMEDDOWN) {
			this.state = Estado.NORMAL;
		} else {
			this.state = Estado.CALMEDDOWN;
		}
	}

	public void receiveAttack(Viking v) {
		if (this.state == Estado.CALMEDDOWN)
			return;

		switch (v.state) {
		case BERSERKER:
			this.health -= DEFAULT_CAUSED_DAMAGE / 2;
			changeState();
			break;
		case CHOLERIC:
			this.health -= DEFAULT_CAUSED_DAMAGE * 2;
			changeState();
			this.state = Estado.BERSERKER;
			break;
		case NORMAL:
			this.health -= DEFAULT_CAUSED_DAMAGE;
			changeState();
			this.state = Estado.CHOLERIC;
			break;
		default:
			break;
		}
	}

	private void changeState() {
		switch (this.state) {
		case CHOLERIC:
			this.state = Estado.BERSERKER;
			break;
		case NORMAL:
			this.state = Estado.CHOLERIC;
			break;
		default:
			break;
		}
	}

	public void attack(Viking v) {
		if (this.state == Estado.CALMEDDOWN)
			this.state = Estado.NORMAL;
		v.receiveAttack(this);
	}
}
