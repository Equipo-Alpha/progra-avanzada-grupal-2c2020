public class Qivephan extends Raza {
int damage = 49;
int heal = 132;
public static int[] rango = { 17, 35 };
public static int posX;
/* ----------- */
int turnos = 0;
public boolean multiAtack, stoneState = false;

public Qivephan(int x) {
super.salud = 132;
super.valorAtaque = 49;
super.posicionX = x;
this.posX = x;
this.multiAtack = false;
}


public void atacar(Qivephan obj) {
if (puedoAtacar(obj)) {
obj.recibirAtak(multiAtack ? damage * 3 : damage);
multiAtack = !multiAtack;
}
checkTurns();
}


public void recibirAtak(int damage) {
checkTurns();
this.heal -= stoneState ? damage * 0.25 : damage;
}


public void recibirAtaque(Qivephan atacante) {
checkTurns();
this.heal -= stoneState ? atacante.damage * 0.25 : atacante.damage;
}


public void descansar() {
checkTurns();
this.turnos = 0;
this.heal = 132;
this.stoneState = true;
}


private boolean puedoAtacar(Raza o) {
return (o.posicionX > (this.rango[0] + this.posicionX) && o.posicionX < (this.rango[1] + this.posicionX)) && !this.stoneState ? true : false;
}


private void checkTurns() {
if (this.stoneState) {
if (this.turnos == 2) {
this.turnos = 0;
this.stoneState = !this.stoneState;
} else {
this.turnos++;
}
}
}


@Override
public double atacar(Raza o) {
checkTurns();
if (this.puedoAtacar(o)) {
((Qivephan) o).recibirAtak(multiAtack ? damage * 3 : damage);
// o.recibirAtaque(this);
multiAtack = !multiAtack;
return 0;
} else {
return -1;
}

}


@Override
public void recibirAtaque(Raza atacante) {
checkTurns();
this.heal -= stoneState ? atacante.valorAtaque * 0.25 : atacante.valorAtaque;
}


@Override
public int getX() {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public double getSalud() {
	// TODO Auto-generated method stub
	return 0;
}
}