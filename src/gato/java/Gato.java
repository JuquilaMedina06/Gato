import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gato extends JFrame {

    private JButton[][] buttons = new JButton[3][3];
    private boolean turnoX = true; // true para X, false para O
    private JLabel statusLabel;

    public Gato() {
        setTitle("Juego de Gato");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear un panel para el tablero
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        // Crear un label para el estado del juego
        statusLabel = new JLabel("Turno de X", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(statusLabel, BorderLayout.NORTH);

        // Inicializar los botones del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(buttons[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("")) {
                if (turnoX) {
                    buttons[row][col].setText("X");
                    statusLabel.setText("Turno de O");
                } else {
                    buttons[row][col].setText("O");
                    statusLabel.setText("Turno de X");
                }
                turnoX = !turnoX;
                verificarGanador();
            }
        }
    }

    private void verificarGanador() {
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().equals("")) {
                if (buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][1].getText().equals(buttons[i][2].getText())) {
                    mostrarGanador(buttons[i][0].getText());
                    return;
                }
            }
        }

        // Verificar columnas
        for (int j = 0; j < 3; j++) {
            if (!buttons[0][j].getText().equals("")) {
                if (buttons[0][j].getText().equals(buttons[1][j].getText()) && buttons[1][j].getText().equals(buttons[2][j].getText())) {
                    mostrarGanador(buttons[0][j].getText());
                    return;
                }
            }
        }

        // Verificar diagonales
        if (!buttons[0][0].getText().equals("")) {
            if (buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText())) {
                mostrarGanador(buttons[0][0].getText());
                return;
            }
        }

        if (!buttons[0][2].getText().equals("")) {
            if (buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][0].getText())) {
                mostrarGanador(buttons[0][2].getText());
                return;
            }
        }

        // Verificar empate
        boolean empate = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    empate = false;
                    break;
                }
            }
        }

        if (empate) {
            JOptionPane.showMessageDialog(this, "¡Empate!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
            reiniciarJuego();
        }
    }

    private void mostrarGanador(String ganador) {
        JOptionPane.showMessageDialog(this, "¡El jugador " + ganador + " ha ganado!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        reiniciarJuego();
    }

    private void reiniciarJuego() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        turnoX = true;
        statusLabel.setText("Turno de X");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gato().setVisible(true);
            }
        });
    }
}