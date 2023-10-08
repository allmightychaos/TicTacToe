import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TicTacToe {
    private final JFrame frame;
    private final JButton[][] boardButtons = new JButton[3][3];
    private final ImageIcon CROSS_ICON = new ImageIcon("data/cross.png");
    private final ImageIcon CIRCLE_ICON = new ImageIcon("data/circle.png");
    private final ImageIcon EMPTY_ICON = new ImageIcon("data/empty.png");

    private JComboBox<String> player1Choice;
    private JComboBox<String> player2Choice;

    private JButton startButton;

    private boolean isCrossTurn;
    private boolean isGameInProgress;

    public TicTacToe() {
        frame = Hauptfenster();

        isCrossTurn = new Random().nextBoolean();
        isGameInProgress = false;

        JPanel boardPanel = Spielbrett();
        JPanel auswahlPanel = AuswahlPanel();

        boardPanel.setBounds(20, 20, 450, 450);
        auswahlPanel.setBounds(20, 470, 450, 100);

        frame.add(boardPanel);
        frame.add(auswahlPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }

    private JFrame Hauptfenster() {
        JFrame frame = new JFrame("TicTacToe-Spiel");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(490, 570);
        frame.setLayout(null); // Kein Layoutmanager

        return frame;
    }

    private JPanel Spielbrett() {
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j] = SpielfeldButton();
                boardPanel.add(boardButtons[i][j]);
            }
        }
        return boardPanel;
    }

    private JButton SpielfeldButton() {
        JButton button = new JButton();

        button.setIcon(EMPTY_ICON);
        button.addActionListener(e -> SpielfeldKlick((JButton) e.getSource()));

        button.setPreferredSize(new Dimension(150, 150));
        button.setSize(new Dimension(150, 150));

        return button;
    }

    private JPanel AuswahlPanel() {
        JPanel auswahlPanel = new JPanel(new FlowLayout());

        player1Choice = new JComboBox<>(new String[]{"Mensch", "Zufällig", "MiniMax"});
        player2Choice = new JComboBox<>(new String[]{"Mensch", "Zufällig", "MiniMax"});

        startButton = new JButton("Spiel starten...");
        startButton.addActionListener(e -> StartButtonKlick());

        auswahlPanel.add(new JLabel("Spieler 1:"));
        auswahlPanel.add(player1Choice);

        auswahlPanel.add(new JLabel("Spieler 2:"));
        auswahlPanel.add(player2Choice);

        auswahlPanel.add(startButton);

        return auswahlPanel;
    }

    private void SpielfeldKlick(JButton clickedButton) {
        if (isGameInProgress) {
            if (clickedButton.getIcon().equals(EMPTY_ICON)) {
                clickedButton.setIcon(isCrossTurn ? CROSS_ICON : CIRCLE_ICON);
                isCrossTurn = !isCrossTurn;
                pruefeSpielStatus();
            }

            // Zufälliger Zug, falls 'Zufällig' als Spieler gewählt wurde

            if (isCrossTurn && "Zufällig".equals(player1Choice.getSelectedItem())) {
                zufaelligerZug(CROSS_ICON);
            } // Zufällig als Spieler 1

            if (!isCrossTurn && "Zufällig".equals(player2Choice.getSelectedItem())) {
                zufaelligerZug(CIRCLE_ICON);
            } // Zufällig als Spieler 2
        } else {
            JOptionPane.showMessageDialog(frame, "Spiel ist noch nicht gestartet!");
        }
    }

    private void StartButtonKlick() {
        if (isGameInProgress) {
            startButton.setText("Spiel starten...");
            isGameInProgress = false;
            setzeSpielZurueck();
        } else {
            startButton.setText("Spiel beenden...");
            isGameInProgress = true;
            JOptionPane.showMessageDialog(frame, (isCrossTurn ? "Kreuz" : "Kreis") + " beginnt!");
        }
    }

    private void zufaelligerZug(ImageIcon icon) {
        Random rand = new Random();
        int x, y;

        do { // Zufallszahl generieren (zwischen 1 und 9)
            int zufall = rand.nextInt(9) + 1;
            x = (zufall - 1) / 3;
            y = (zufall - 1) % 3;
        } while (!boardButtons[x][y].getIcon().equals(EMPTY_ICON));

        boardButtons[x][y].setIcon(icon);
        isCrossTurn = !isCrossTurn;
        pruefeSpielStatus();
    }

    private void pruefeSpielStatus() {
        if (hatGewonnen(CROSS_ICON)) {
            beendeSpiel("Kreuz gewinnt!");
        } else if (hatGewonnen(CIRCLE_ICON)) {
            beendeSpiel("Kreis gewinnt!");
        } else if (istBrettVoll()) {
            beendeSpiel("Unentschieden!");
        }
    }

    private boolean hatGewonnen(ImageIcon icon) {
        for (int i = 0; i < 3; i++) {
            if (pruefeReiheSpalte(i, 0, i, 1, i, 2, icon) || pruefeReiheSpalte(0, i, 1, i, 2, i, icon)) {
                return true;
            }
        }
        return pruefeDiagonalen(icon);
    }

    private boolean pruefeReiheSpalte(int x1, int y1, int x2, int y2, int x3, int y3, ImageIcon icon) {
        return icon.equals(boardButtons[x1][y1].getIcon()) &&
                icon.equals(boardButtons[x2][y2].getIcon()) &&
                icon.equals(boardButtons[x3][y3].getIcon());
    }

    private boolean pruefeDiagonalen(ImageIcon icon) {
        return (icon.equals(boardButtons[0][0].getIcon()) &&
                icon.equals(boardButtons[1][1].getIcon()) &&
                icon.equals(boardButtons[2][2].getIcon())) ||
                (icon.equals(boardButtons[0][2].getIcon()) &&
                        icon.equals(boardButtons[1][1].getIcon()) &&
                        icon.equals(boardButtons[2][0].getIcon()));
    }

    private boolean istBrettVoll() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardButtons[i][j].getIcon().equals(EMPTY_ICON)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void beendeSpiel(String nachricht) {
        JOptionPane.showMessageDialog(frame, nachricht);
        isGameInProgress = false;
        startButton.setText("Spiel starten...");
        setzeSpielZurueck();
    }

    private void setzeSpielZurueck() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j].setIcon(EMPTY_ICON);
            }
        }

        isCrossTurn = new Random().nextBoolean();
    }
}
