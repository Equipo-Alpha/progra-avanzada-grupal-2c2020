package equipoalpha.loveletter.partida;

public final class JugadorNoValido extends JugadorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 101L;

	JugadorNoValido() {
		super();
	}

	@Override
	public String getMessage() {
		return "El jugador seleccionado no puede ser elejido";
	}
}