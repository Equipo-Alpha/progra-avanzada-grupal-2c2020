package loom.vikings.statepattern;

public interface VikingState {

	void receiveAttack(Viking v);
	
	int attack(Viking v);
	
	void meditate(Viking v);
	
    String getStatus();
}
