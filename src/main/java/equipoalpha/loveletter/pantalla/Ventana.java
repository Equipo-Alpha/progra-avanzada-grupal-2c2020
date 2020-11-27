package equipoalpha.loveletter.pantalla;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.common.ComandoTipo;
import equipoalpha.loveletter.util.Drawable;
import equipoalpha.loveletter.util.JsonUtils;

import javax.swing.*;
import java.awt.*;

public class Ventana {
    private final JFrame ventana;
    private VentanaChat ventanaChat;
    private Drawable panelActual;
    private Timer timer;

    public Ventana() {
        this.ventana = new JFrame("Love Letter");
        this.ventana.setSize(1024, 768);
        this.ventana.setPreferredSize(new Dimension(1024, 768));
        PanelElegirNombre panelElegirNombre = new PanelElegirNombre();
        this.ventana.add(panelElegirNombre);
        this.panelActual = panelElegirNombre;
        this.ventana.pack();
        this.ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ventana.setLocationRelativeTo(null);
        this.ventana.setResizable(false);
        this.ventana.setVisible(true);
        panelElegirNombre.setVisible(true);
        this.ventana.setFocusable(true);
        this.ventana.requestFocusInWindow();
        this.timer = new Timer(16, LoveLetter.getInstance());
        timer.start();
    }

    public void onLogin() {
        PanelMenuPrincipal panelMenuPrincipal = new PanelMenuPrincipal();
        this.ventana.add(panelMenuPrincipal);
        this.ventana.pack();
        ((JPanel) this.panelActual).setVisible(false);
        LoveLetter.handler.removeDrawableObject(panelActual);
        this.panelActual = panelMenuPrincipal;
        panelMenuPrincipal.setVisible(true);
    }

    public void onBuscarSalas(JsonArray array) {
        PanelUnirseSala panelUnirseSala = new PanelUnirseSala(array);
        this.ventana.add(panelUnirseSala);
        this.ventana.pack();
        ((JPanel) this.panelActual).setVisible(false);
        LoveLetter.handler.removeDrawableObject(panelActual);
        this.panelActual = panelUnirseSala;
        panelUnirseSala.setVisible(true);
    }

    public void onActualizarSalas(JsonArray array) {
        if (panelActual instanceof PanelUnirseSala) {
            ((PanelUnirseSala) panelActual).actualizarSalas(array);
        } else onBuscarSalas(array);
    }

    public void onSalaInvalida() {
        JOptionPane.showConfirmDialog(this.ventana, // o this.panelActual
                "La sala seleccionada es invalida o ya no esta disponible." +
                        ".\nPor favor, seleccione otra.",
                "Sala invalida",
                JOptionPane.DEFAULT_OPTION);
    }

    public void onCrearSala() {
        PanelSala panelSala = new PanelSala();
        this.ventana.add(panelSala);
        this.ventana.pack();
        ((JPanel) this.panelActual).setVisible(false);
        LoveLetter.handler.removeDrawableObject(this.panelActual);
        this.panelActual = panelSala;
        panelSala.setVisible(true);
        this.ventanaChat = new VentanaChat();
    }

    public void onSalirSala() {
        Container container = ventana.getContentPane();
        for (Component component : container.getComponents()) {
            if (component instanceof PanelMenuPrincipal) {
                ((JPanel) this.panelActual).setVisible(false);
                LoveLetter.handler.removeDrawableObject(this.panelActual);
                component.setVisible(true);
                this.panelActual = (Drawable) component;
                LoveLetter.handler.addDrawableObject((Drawable) component);
                this.ventanaChat.ocultar();
                this.ventanaChat = null;
                return;
            }
        }
    }

    public void onConfirmarInicio() {
        int seleccion = JOptionPane.showConfirmDialog(this.ventana, // o this.panelActual
                "El creador selecciono para empezar la partida" +
                        ".\n¿Estas listo?",
                "Partida empezando",
                JOptionPane.YES_NO_OPTION);
        if (seleccion == JOptionPane.YES_OPTION) {
            LoveLetter.getInstance().getCliente().getJugadorCliente().confirmarInicio();
        } else {
            LoveLetter.getInstance().getCliente().getJugadorCliente().cancelarInicio();
        }
    }

    public void onPartidaEmpezada() {
        PanelPartida panelPartida = new PanelPartida();
        ventana.add(panelPartida);
        ventana.pack();
        ((JPanel) this.panelActual).setVisible(false);
        LoveLetter.handler.removeDrawableObject(this.panelActual);
        this.panelActual = panelPartida;
        panelPartida.setVisible(true);
    }

    public void onRondaEmpezada() {
        ((PanelPartida) this.panelActual).animandoAIR = true;
    }

    public void onRondaTerminadaMsg(JsonObject msj) {
        String motivo = JsonUtils.getString(msj, "motivo");
        String jugadorGanador = JsonUtils.getString(msj, "ganador");
        String cant = "";
        if (JsonUtils.hasElement(msj, "cantidad")) {
            cant = JsonUtils.getString(msj, "cantidad");
        }
        if (JsonUtils.hasElement(msj, "carta")) {
            Carta carta = new Gson().fromJson(msj.get("carta"), Carta.class);
            JOptionPane.showMessageDialog(this.ventana,
                    "La ronda termino!" +
                            "\nEl ganador es: " + jugadorGanador +
                            "\nMotivo: " + motivo,
                    "Ronda Terminada",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(carta.getImagen()));
        } else {
            JOptionPane.showMessageDialog(this.ventana,
                    "La ronda termino!" +
                            "\nEl ganador es: " + jugadorGanador +
                            "\nMotivo: " + motivo +
                            (cant.equals("") ? "" : "\nCantidad: " + cant),
                    "Ronda Terminada",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        LoveLetter.getInstance().getCliente().send(ComandoTipo.ContinuarFin, new JsonObject());
    }

    public void onPartidaTerminadaMsg() {
        int seleccion = JOptionPane.showConfirmDialog(this.ventana,
                "La partida termino, el ganador es: " +
                        LoveLetter.getInstance().getCliente().getJugadorCliente().getPartidaActual().jugadorEnTurno.nombre +
                        ".\n¿Volver a jugar?",
                "Partida terminada",
                JOptionPane.YES_NO_OPTION);
        if (seleccion == JOptionPane.YES_OPTION) {
            LoveLetter.getInstance().getCliente().send(ComandoTipo.ConfirmarVolverAJugar, new JsonObject());
        } else {
            LoveLetter.getInstance().getCliente().send(ComandoTipo.CancelarVolverJugar, new JsonObject());
        }
    }

    public void onPartidaTerminada() {
        Container container = ventana.getContentPane();
        for (Component component : container.getComponents()) {
            if (component instanceof PanelSala) {
                ((JPanel) this.panelActual).setVisible(false);
                LoveLetter.handler.removeDrawableObject(this.panelActual);
                component.setVisible(true);
                this.panelActual = (Drawable) component;
                LoveLetter.handler.addDrawableObject((Drawable) component);
                return;
            }
        }
    }

    public void onServerDesconectado() {
        PanelElegirNombre panelElegirNombre = new PanelElegirNombre();
        ventana.add(panelElegirNombre);
        ventana.pack();
        ((JPanel) this.panelActual).setVisible(false);
        LoveLetter.handler.removeDrawableObject(this.panelActual);
        this.panelActual = panelElegirNombre;
        panelElegirNombre.setVisible(true);

        JOptionPane.showMessageDialog(this.ventana,
                "Hubo un problema en la conexion.",
                "Error en el server",
                JOptionPane.ERROR_MESSAGE);
    }

    public void onErrorConexion() {
        JOptionPane.showMessageDialog(this.ventana,
                "Hubo un problema al conectarse.\n",
                "Error al conectarse",
                JOptionPane.ERROR_MESSAGE);
    }

    public void cambiarVisibilidadChat() {
        if (this.ventanaChat != null) {
            this.ventanaChat.cambiarVisibilidad();
        }
    }

    public void onNuevoMensajeChat(String mensaje) {
        if (this.ventanaChat != null) {
            this.ventanaChat.agregarMensaje(mensaje);
        }
    }

    public Drawable getPanelActual() {
        return panelActual;
    }
}
