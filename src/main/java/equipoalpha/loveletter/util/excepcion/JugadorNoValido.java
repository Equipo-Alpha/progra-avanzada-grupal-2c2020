package equipoalpha.loveletter.util.excepcion;

public final class JugadorNoValido extends JugadorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 101L;

	public JugadorNoValido() {
		super();
	}

	@Override
	public String getMessage() {
		return "El jugador seleccionado no puede ser elejido";
	}
}