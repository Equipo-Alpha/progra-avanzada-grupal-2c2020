public class Gnikgers extends Raza {
    
    public Gnikgers() {
        this.salud = 168;
        this.arma = "Magia";
        this.valorAtaque = 118;
        this.posicionX = 0;
        this.rangoAtaqueMinimo = 15;
        this.rangoAtaqueMaximo = 20;
    }
    
    @Override
    public double atacar(Raza enemy) {
        int probability = (int) (Math.random()*100);
 
        if(!this.puedeAtacar(enemy) || probability < 75) {
            return 0;
        }
        enemy.recibirAtaque(this);
        return this.valorAtaque;
    }
    
    private boolean puedeAtacar(Raza enemy) {
        double distance = enemy.getX() - this.getX();
        if(distance >= this.rangoAtaqueMinimo && distance <= rangoAtaqueMaximo) {
            return true;    
        }
        return false;
    }
 
    @Override
    public int getX() {
        return this.posicionX;
    }
 

    @Override
    public void recibirAtaque(Raza o) {
        this.salud -= o.atacar(this);    
    }
 
    @Override
    public void descansar() {
        return;
    }
 
    @Override
    public double getSalud() {
        return this.salud;
    }
}