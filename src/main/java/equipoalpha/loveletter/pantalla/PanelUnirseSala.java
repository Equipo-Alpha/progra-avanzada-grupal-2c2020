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
    private final JButton unirse;
    private final JButton volver;
    private final JButton actualizar;
    protected JsonArray salas;
    private JTable tablaSalas;
    private JScrollPane scrollSalas;
    private String salaSeleccionada = "";

    public PanelUnirseSala(JsonArray salas) {
        this.loveletter = LoveLetter.getInstance();
        this.salas = salas;
        this.unirse = new JButton("Unirse");
        this.volver = new JButton("volver");
        this.actualizar = new JButton("Actualizar lista");
        Font buttonFont = new Font("Monotype Corsiva", Font.BOLD, 26);
        this.unirse.setFont(buttonFont);
        this.unirse.setForeground(Color.WHITE);
        this.unirse.setBackground(Color.BLACK);
        this.volver.setFont(buttonFont);
        this.volver.setForeground(Color.WHITE);
        this.volver.setBackground(Color.BLACK);
        this.actualizar.setFont(buttonFont);
        this.actualizar.setForeground(Color.WHITE);
        this.actualizar.setBackground(Color.BLACK);

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

        g2.drawImage(Imagenes.fondo3, null, 0, 0);
        g2.drawImage(Imagenes.salas, null, 280, 20);

        this.scrollSalas.setBounds(300, 150, 410, 320);
        this.unirse.setBounds(360, 500, 150, 50);
        this.volver.setBounds(520, 500, 150, 50);
        this.actualizar.setBounds(400, 570, 240, 50);
    }

    @Override
    public void render() {
        this.repaint();
    }

    public void actualizarSalas(JsonArray array) {
        String[] columns = new String[]{"Nombre de la Sala", "Jugadores", "Disponibilidad"};
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
        this.tablaSalas.getTableHeader().setBackground(Color.BLACK);
        this.tablaSalas.setForeground(Color.WHITE);
        this.tablaSalas.getTableHeader().setForeground(Color.WHITE);
        this.tablaSalas.setFont(new Font("Monotype Corsiva", Font.PLAIN, 20));
        this.tablaSalas.getTableHeader().setFont(new Font("Monotype Corsiva", Font.PLAIN, 20));
        if (scrollSalas != null)
            this.remove(scrollSalas);
        this.scrollSalas = new JScrollPane(this.tablaSalas);
        this.scrollSalas.getViewport().setBackground(Color.BLACK);
        this.scrollSalas.getViewport().setForeground(Color.WHITE);
        this.scrollSalas.getViewport().setFont(new Font("Monotype Corsiva", Font.PLAIN, 20));
        add(scrollSalas);
        this.repaint();
    }
}
