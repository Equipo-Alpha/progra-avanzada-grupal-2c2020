package unlam.taller.vikingos;

public interface VikingState {

	void receiveAttack(Viking v);
	
	int attack(Viking v);
	
	void meditate(Viking v);
	
    void printStatus();
}
