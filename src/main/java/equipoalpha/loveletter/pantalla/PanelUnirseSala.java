package equipoalpha.loveletter.pantalla;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.util.Drawable;
import equipoalpha.loveletter.util.JsonUtils;

import javax.swing.*;
import java.awt.*;

public class PanelUnirseSala extends JPanel implements Drawable {
    private static final long serialVersionUID = 8L;
    private final LoveLetter loveletter;
    private boolean moviendoCentro = false;
    private final JButton unirse;
    private final JButton volver;
    private final JButton actualizar;
    private JTable tablaSalas;
    private JScrollPane scrollSalas;
    protected JsonArray salas;
    private String salaSeleccionada = "";
    private int centroX;


    public PanelUnirseSala(JsonArray salas) {
        this.loveletter = LoveLetter.getInstance();
        this.salas = salas;
        this.unirse = new JButton("Unirse");
        this.volver = new JButton("volver");
        this.actualizar = new JButton("Actualizar lista");
        Font buttonFont = new Font("Arial", Font.BOLD, 26);
        this.unirse.setFont(buttonFont);
        this.volver.setFont(buttonFont);
        this.actualizar.setFont(buttonFont);

        actualizarSalas(salas);

        this.unirse.addActionListener(actionEvent -> {
            int row = this.tablaSalas.getSelectedRow();
            if (row >= 0)
                salaSeleccionada = (String) this.tablaSalas.getModel().getValueAt(row, 0);
            else
                salaSeleccionada = "";
            System.out.println(salaSeleccionada);
            loveletter.getCliente().getJugadorCliente().unirseASala(salaSeleccionada);
        });
        this.volver.addActionListener(actionEvent -> {
            loveletter.getVentana().onLogin();
        });
        this.actualizar.addActionListener(actionEvent -> {
            loveletter.getCliente().getJugadorCliente().buscarSalas();
        });
        add(volver);
        add(unirse);
        add(actualizar);

        registrar();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(Imagenes.background, null, 0, 0);

        if (moviendoCentro) {
            moverCentro(g2);
            return;
        }

        Color color = new Color(0, 0, 0, 185);
        g2.setColor(color);
        g2.fillRect((loveletter.WIDTH / 2) - 200, 0, 400, loveletter.HEIGHT);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("Salas", 450, 100);
        g2.setFont(new Font("Arial", Font.PLAIN, 18));

        this.scrollSalas.setBounds(330, 150, 360, 300);
        this.unirse.setBounds(360, 500, 150, 50);
        this.volver.setBounds(520 , 500, 150, 50);
        this.actualizar.setBounds(400, 570, 240, 50);
    }

    @Override
    public void render() {
        this.repaint();
    }

    private void moverCentro(Graphics2D g2) {
        Color color = new Color(0, 0, 0, 185);
        g2.setColor(color);
        centroX -= 5;
        if (centroX > 10)
            g2.fillRect(centroX, 0, 400, loveletter.HEIGHT);
        else {
            moviendoCentro = false;
            centroX = (loveletter.WIDTH / 2) - 200;
            LoveLetter.getInstance().getVentana().onCrearSala();
        }
    }

    public void setMoviendoCentro(boolean moviendoCentro) {
        this.moviendoCentro = moviendoCentro;
    }

    public void actualizarSalas(JsonArray array) {
        String[] columns = new String[]{"Nombre de la Sala", "Cantidad de Jugadores", "Disponibilidad"};
        this.salas = array;
        String[][] data = new String[array.size()][3];
        int index = 0;
        for (JsonElement sala : array) {
            data[index][0] = JsonUtils.getString(sala.getAsJsonObject(), "nombre");
            data[index][1] = JsonUtils.getString(sala.getAsJsonObject(), "jugadores");
            if (JsonUtils.getBoolean(sala.getAsJsonObject(), "inicio"))
                data[index][2] = "Empezada";
            else {
                if (JsonUtils.getInt(sala.getAsJsonObject(), "jugadores") < 4)
                    data[index][2] = "Con Lugar";
                else
                    data[index][2] = "LLena";
            }
            index++;
        }

        this.tablaSalas = new JTable(data, columns) {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };
        this.tablaSalas.setShowGrid(false);
        this.tablaSalas.setSelectionMode(0);
        this.tablaSalas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.tablaSalas.setRowHeight(16);
        this.tablaSalas.setBackground(Color.BLACK);
        this.tablaSalas.setForeground(Color.WHITE);
        this.tablaSalas.setSize(360, 300);
        if (scrollSalas != null)
            this.remove(scrollSalas);
        this.scrollSalas = new JScrollPane(this.tablaSalas);
        add(scrollSalas);
        this.repaint();
    }
}
