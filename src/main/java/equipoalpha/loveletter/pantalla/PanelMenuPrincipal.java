package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

import static java.lang.System.exit;

public class PanelMenuPrincipal extends JPanel implements Drawable {
    private static final long serialVersionUID = 3L;
    private final Ventana parent;
    private final LoveLetter loveletter;
    private final JPanel equipo;
    private final JPanel panelConfiguracion;
    private final JLabel labelNombre;
    private final JLabel labelNombreInvalido;
    private final JTextField textFieldNombre;
    private final JLabel labelIcono;
    private final JComboBox<String> comboBoxIcono;
    private final JLabel labelTema;
    private final JComboBox<String> comboBoxTema;
    private final JButton botonAceptar;
    private final JButton buttonUnirseSala;
    private final JButton buttonCrearSala;
    private final JButton buttonConfigurar;
    private final JButton buttonTutorial;
    private final JButton buttonSalir;
    private final JButton buttonEquipo;
    private final JLabel ramiro;
    private final JLabel kevin;
    private final JLabel santiG;
    private final JLabel nico;
    private final JLabel ignacio;
    private final JLabel santiV;
    private final JLabel mati;
    private boolean isExpanding, isMaxSize, isAchicandose, isMinSize;
    private float sizeX = 0, sizeY = 0;

    public PanelMenuPrincipal() {
        loveletter = LoveLetter.getInstance();
        parent = loveletter.getVentana();
        equipo = new JPanel();
        equipo.setBackground(new Color(0, 0, 0, 200));
        add(equipo);
        equipo.setVisible(false);

        Font fEquipo = new Font("Monotype Corsiva", Font.PLAIN, 22);
        ramiro = new JLabel("Ramiro Valle");
        ramiro.setFont(fEquipo);
        ramiro.setForeground(Color.WHITE);
        equipo.add(ramiro);
        kevin = new JLabel("Kevin Niedfeld");
        kevin.setFont(fEquipo);
        kevin.setForeground(Color.WHITE);
        equipo.add(kevin);
        santiG = new JLabel("Santiago Giasone");
        santiG.setFont(fEquipo);
        santiG.setForeground(Color.WHITE);
        equipo.add(santiG);
        nico = new JLabel("Nicolas Muryn");
        nico.setFont(fEquipo);
        nico.setForeground(Color.WHITE);
        equipo.add(nico);
        ignacio = new JLabel("Ignacio Arias");
        ignacio.setFont(fEquipo);
        ignacio.setForeground(Color.WHITE);
        equipo.add(ignacio);
        santiV = new JLabel("Santiago Vasquez");
        santiV.setFont(fEquipo);
        santiV.setForeground(Color.WHITE);
        equipo.add(santiV);
        mati = new JLabel("Matias Ramirez");
        mati.setFont(fEquipo);
        mati.setForeground(Color.WHITE);
        equipo.add(mati);

        panelConfiguracion = new JPanel();
        panelConfiguracion.setVisible(false);
        panelConfiguracion.setBackground(Color.BLACK);
        panelConfiguracion.setBorder(new BorderUIResource.LineBorderUIResource(Color.WHITE));
        add(panelConfiguracion);

        Font labelsConfiguracion = new Font("Monotype Corsiva", Font.BOLD, 20);
        labelNombre = new JLabel("Nombre: ");
        labelNombre.setFont(labelsConfiguracion);
        labelNombre.setForeground(Color.WHITE);
        panelConfiguracion.add(labelNombre);
        labelIcono = new JLabel("Icono: ");
        labelIcono.setFont(labelsConfiguracion);
        labelIcono.setForeground(Color.WHITE);
        panelConfiguracion.add(labelIcono);
        labelTema = new JLabel("Tema: ");
        labelTema.setFont(labelsConfiguracion);
        labelTema.setForeground(Color.WHITE);
        panelConfiguracion.add(labelTema);
        labelNombreInvalido = new JLabel("Nombre invalido");
        labelNombreInvalido.setFont(labelsConfiguracion);
        labelNombreInvalido.setForeground(Color.WHITE);
        labelNombreInvalido.setVisible(false);
        panelConfiguracion.add(labelNombreInvalido);
        textFieldNombre = new JTextField("Test");
        textFieldNombre.setFont(labelsConfiguracion);
        panelConfiguracion.add(textFieldNombre);
        botonAceptar = new JButton("Aceptar");
        botonAceptar.setFont(labelsConfiguracion);
        panelConfiguracion.add(botonAceptar);
        comboBoxIcono = new JComboBox<>();
        comboBoxIcono.addItem("Principe");
        comboBoxIcono.addItem("Princesa");
        comboBoxIcono.setFont(labelsConfiguracion);
        panelConfiguracion.add(comboBoxIcono);
        comboBoxTema = new JComboBox<>();
        comboBoxTema.addItem("Normal");
        comboBoxTema.addItem("StarWars");
        comboBoxTema.setFont(labelsConfiguracion);
        panelConfiguracion.add(comboBoxTema);

        isExpanding = isMaxSize = isAchicandose = false;
        isMinSize = true;

        buttonUnirseSala = new JButton("Buscar salas");
        buttonCrearSala = new JButton("Crear sala");
        buttonConfigurar = new JButton("Configuracion");
        buttonTutorial = new JButton("Tutorial");
        buttonSalir = new JButton("Salir");
        buttonEquipo = new JButton("Miembros");
        Font buttonFont = new Font("Monotype Corsiva", Font.BOLD, 26);
        buttonCrearSala.setFont(buttonFont);
        buttonCrearSala.setBackground(Color.BLACK);
        buttonCrearSala.setForeground(Color.WHITE);
        buttonConfigurar.setFont(buttonFont);
        buttonConfigurar.setBackground(Color.BLACK);
        buttonConfigurar.setForeground(Color.WHITE);
        buttonTutorial.setFont(buttonFont);
        buttonTutorial.setBackground(Color.BLACK);
        buttonTutorial.setForeground(Color.WHITE);
        buttonSalir.setFont(buttonFont);
        buttonSalir.setBackground(Color.BLACK);
        buttonSalir.setForeground(Color.WHITE);
        buttonUnirseSala.setFont(buttonFont);
        buttonUnirseSala.setBackground(Color.BLACK);
        buttonUnirseSala.setForeground(Color.WHITE);
        buttonFont = new Font("Monotype Corsiva", Font.PLAIN, 16);
        buttonEquipo.setFont(buttonFont);
        buttonEquipo.setBackground(Color.BLACK);
        buttonEquipo.setForeground(Color.WHITE);
        add(buttonUnirseSala);
        add(buttonCrearSala);
        add(buttonConfigurar);
        add(buttonTutorial);
        add(buttonSalir);
        add(buttonEquipo);

        buttonUnirseSala.addActionListener(actionEvent -> loveletter.getCliente().getJugadorCliente().buscarSalas());

        buttonConfigurar.addActionListener(actionEvent -> {
            panelConfiguracion.setVisible(true);
            panelConfiguracion.requestFocus();
        });

        botonAceptar.addActionListener(actionEvent -> {
            if (textFieldNombre.getText().isEmpty()) {
                labelNombreInvalido.setVisible(true);
            } else {
                labelNombreInvalido.setVisible(false);
                this.cambiarConfiguracion(textFieldNombre.getText(),
                        comboBoxIcono.getItemAt(comboBoxIcono.getSelectedIndex()),
                        comboBoxTema.getItemAt(comboBoxTema.getSelectedIndex()));
                panelConfiguracion.setVisible(false);
            }
        });

        buttonEquipo.addActionListener(actionEvent -> {
            if (isMinSize) {
                isMinSize = false;
                isAchicandose = false;
                isExpanding = true;
            } else {
                isMaxSize = false;
                isExpanding = false;
                isAchicandose = true;
            }
            equipo.setVisible(true);
        });

        buttonCrearSala.addActionListener(actionEvent -> loveletter.getCliente().getJugadorCliente().crearSala(""));

        buttonTutorial.addActionListener(ActionEvent -> this.parent.onShowTutorial());

        buttonSalir.addActionListener(actionEvent -> exit(0));

        registrar();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(Imagenes.fondoPrincesa, null, 0, 0);
        g2.drawImage(Imagenes.logo, null, 240, 40);

        if (panelConfiguracion.isVisible()) {
            g2.setColor(new Color(0, 0, 0, 165));
            g2.fillRect(0, 0, loveletter.WIDTH, loveletter.HEIGHT);
            buttonCrearSala.setEnabled(false);
            buttonUnirseSala.setEnabled(false);
            buttonSalir.setEnabled(false);
            buttonConfigurar.setEnabled(false);
        } else {
            buttonCrearSala.setEnabled(true);
            buttonUnirseSala.setEnabled(true);
            buttonSalir.setEnabled(true);
            buttonConfigurar.setEnabled(true);
        }

        buttonUnirseSala.setBounds(250, 260, 200, 64);
        buttonCrearSala.setBounds(454, 260, 200, 64);
        buttonTutorial.setBounds(250, 335, 200, 64);
        buttonConfigurar.setBounds(454, 335, 200, 64);
        buttonSalir.setBounds(250, 410, 404, 64);
        buttonEquipo.setBounds(900, 10, 100, 32);

        if (isExpanding) expandirEquipo();
        if (isAchicandose) achicarEquipo();
        if (isMaxSize) equipo.setBounds(800, 42, 200, 250);

        if (sizeX > 120) {
            ramiro.setVisible(true);
            kevin.setVisible(true);
            santiG.setVisible(true);
            nico.setVisible(true);
            ignacio.setVisible(true);
            santiV.setVisible(true);
            mati.setVisible(true);
            ramiro.setBounds(30, 20, 200, 20);
            kevin.setBounds(30, 50, 200, 20);
            santiG.setBounds(30, 80, 200, 20);
            nico.setBounds(30, 110, 200, 20);
            ignacio.setBounds(30, 140, 200, 20);
            santiV.setBounds(30, 170, 200, 20);
            mati.setBounds(30, 200, 200, 20);
        } else {
            ramiro.setVisible(false);
            kevin.setVisible(false);
            santiG.setVisible(false);
            nico.setVisible(false);
            ignacio.setVisible(false);
            santiV.setVisible(false);
            mati.setVisible(false);
        }

        labelNombre.setBounds(50, 50, 150, 50);
        textFieldNombre.setBounds(190, 50, 150, 50);
        labelIcono.setBounds(50, 120, 150, 50);
        comboBoxIcono.setBounds(190, 120, 150, 50);
        labelTema.setBounds(50, 190, 150, 50);
        comboBoxTema.setBounds(190, 190, 150, 50);
        botonAceptar.setBounds(125, 280, 150, 50);
        labelNombreInvalido.setBounds(125, 300, 150, 50);
        panelConfiguracion.setBounds(312, 150, 400, 400);
    }

    @Override
    public void render() {
        this.repaint();
    }

    private void expandirEquipo() {
        if (sizeX < 200 && sizeY < 250) {
            sizeX += 4;
            sizeY += 5;
        } else {
            sizeX = 200;
            sizeY = 250;
            isExpanding = false;
            isMaxSize = true;
            return;
        }
        equipo.setBounds(1000 - (int) sizeX, 42, (int) sizeX, (int) sizeY);
    }

    private void achicarEquipo() {
        isMinSize = true;
        if (sizeX > 4 && sizeY > 5) {
            sizeX -= 0.66;
            sizeY -= 0.83;
            equipo.setBounds(1000 - (int) sizeX, 42, (int) sizeX, (int) sizeY);
        } else {
            sizeX = 0;
            sizeY = 0;
            isAchicandose = false;
            equipo.setVisible(false);
        }
    }

    private void cambiarConfiguracion(String nombre, String icono, String tema) {
        loveletter.getCliente().getJugadorCliente().nombre = nombre;
        if (icono.equals("Principe"))
            loveletter.getCliente().getJugadorCliente().icono = Imagenes.iconoPrincipe;
        else
            loveletter.getCliente().getJugadorCliente().icono = Imagenes.iconoPrincesa;

        if (tema.equalsIgnoreCase("normal"))
            Imagenes.elegirNormales();
        else
            Imagenes.elegirStarWars();
    }
}
