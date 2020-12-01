package equipoalpha.loveletter.pantalla;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import equipoalpha.loveletter.client.JugadorCliente;
import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.util.Drawable;

public class VentanaTutorial extends JPanel implements Drawable {

	private static final long serialVersionUID = 95894L;
	
	//Needed to interact with the game
	private final LoveLetter loveletter;
    // Needed to render the components
	private static JButton btnBack;
	private static JTextArea txtTutorial;
	private final JPanel panelTutorial;
	
	public VentanaTutorial() {
		// TODO Auto-generated constructor stub
		this.panelTutorial = new JPanel();
		this.loveletter = LoveLetter.getInstance();
        LoveLetter.getInstance().getCliente().getJugadorCliente();
        
        VentanaTutorial.txtTutorial = new JTextArea(
        	"El objetivo de cada jugador, es entregar una carta de amor a la princesa con ayuda de sus colaboradores." + 
    		"\r\n" + 
    		"* El juego utiliza un mazo de cartas especificado en su propia sección.\n" + 
    		"\r\n" + 
    		"* Una partida debe contar como mínimo con 2 jugadores (y soportar hasta 4).\n" + 
    		"\r\n" + 
    		"* Una partida debe contar con múltiples rondas, que finalizará cuando no haya más cartas en el mazo, o cuando\nquede un solo jugador en juego.\n" + 
    		"\r\n" + 
    		"* Una partida finaliza cuando un jugador haya conseguido alcanzar una cantidad de símbolos de afecto\nestablecidas al principio de la partida.\n" + 
    		"\r\n" + 
    		"* Al inicio de la ronda, se elimina del juego una carta al azar (su valor será secreto), y se reparte una carta a cada\njugador. El resto de las cartas pertenece al mazo.\n" + 
    		"\r\n" + 
    		"* Durante el turno de cada jugador, este roba una carta del mazo a su mano, y luego juega/descarta una de las\ncartas de su mano.\n" + 
    		"\r\n" + 
    		"* Al jugarse/descartarse una carta, activa su efecto (explicadas en la sección del mazo de cartas). Una vez\nactivado termina el turno de ese jugador, y comienza el del siguiente.\n" + 
    		"\r\n" + 
    		"* Cuando se llegue a la condición de fin de ronda, el jugador que tiene en su mano la carta con valor numérico\nmás alto (fuerza), gana la ronda (Si hay empate, gana el jugador que haya jugado/descartado más cartas en la ronda).\n" + 
    		"\r\n" + 
    		"* El jugador que gana una ronda, gana un símbolo de afecto");
        VentanaTutorial.btnBack = new JButton("Volver al menú");
        
        Font labelsConfiguracion = new Font("Arial", Font.BOLD, 13);
        Color colorBg = new Color(255, 255, 255, 0);        
        Consumer<JTextArea> consumer = label -> {
            label.setOpaque(true);
            label.setBackground(colorBg);
            label.setForeground(Color.WHITE);
            label.setFont(labelsConfiguracion);
            label.setEditable(false);
        };
        
        consumer.accept(VentanaTutorial.txtTutorial);
        
        btnBack.addActionListener(actionEvent -> {
        	LoveLetter.getInstance().getVentana().onSalirTutorial();
        });
        
        panelTutorial.add(txtTutorial);
        panelTutorial.add(btnBack);
        add(txtTutorial);
        add(btnBack);
        
        registrar();
	}
	

	@Override
	public void render() {
		// TODO Auto-generated method stub
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(Imagenes.background, null, 0, 0);
        Color color = new Color(0, 0, 0, 185);
        g2.setColor(color);
        g2.fillRect(loveletter.WIDTH - 875, 0, 750, loveletter.HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("Reglas del juego", 375, 100);
        g2.setFont(new Font("Arial", Font.PLAIN, 22));
        g2.drawString("", 375, 140);
        
        txtTutorial.setBounds(loveletter.WIDTH - 865, 150, 730, loveletter.HEIGHT - 250);
        
        btnBack.setBounds(300, loveletter.HEIGHT - 120, 450, 70);
        btnBack.setVisible(true);
	}
	
}
