import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class TicTacToe {
    private final JFrame frame;

    private final JComboBox<String> player1Choice;  // Dropdown-Menüs zur Auswahl der Spieler.
    private final JComboBox<String> player2Choice;  // Dropdown-Menüs zur Auswahl der Spieler.

    private final JButton[][] boardButtons = new JButton[3][3];     // 3x3 Matrix (Schaltflächen)
    private final JButton startButton;                              // Button zum Starten des Spiels

    private JButton player1DifficultyButton, player2DifficultyButton;
    private int player1Difficulty = 1, player2Difficulty = 1;

    private boolean isCrossTurn = true;     // Ob Kreuz oder Kreis dran ist
    private boolean gameStarted = false;    // Ob das Spiel gestartet wurde oder nicht

    private final ImageIcon CROSS_ICON = new ImageIcon("data/cross.png");
    private final ImageIcon CIRCLE_ICON = new ImageIcon("data/circle.png");
    private final ImageIcon EMPTY_ICON = new ImageIcon("data/empty.png");

    public TicTacToe() { // Konstruktor
        // Fenster erstellen
        frame = new JFrame("TicTacToe");
        frame.setIconImage(new ImageIcon("/mnt/data/icon.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        // Spielfeld erstellen
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j] = new JButton(); // Schaltfläche erstellen
                boardButtons[i][j].setIcon(EMPTY_ICON);

                boardButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!gameStarted) return; // Wenn das Spiel noch nicht gestartet wurde, dann nichts machen

                        // Wenn die Schaltfläche leer ist, dann das Icon setzen und den Zug wechseln
                        JButton clickedButton = (JButton) e.getSource();
                        if (clickedButton.getIcon().equals(EMPTY_ICON)) {
                            if (isCrossTurn) {
                                clickedButton.setIcon(CROSS_ICON);
                            } else {
                                clickedButton.setIcon(CIRCLE_ICON);
                            }
                            isCrossTurn = !isCrossTurn;
                            checkGameStatus();
                        }

                        if (gameStarted && ("PC".equals(player1Choice.getSelectedItem()) || "PC".equals(player2Choice.getSelectedItem()))) {
                            playPCTurn();
                        }
                    }
                });
                boardPanel.add(boardButtons[i][j]);
            }
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JPanel choicePanel = new JPanel(new GridBagLayout());
        player1Choice = new JComboBox<>(new String[]{"Mensch", "PC"});
        player2Choice = new JComboBox<>(new String[]{"Mensch", "PC"});

        player1Choice.addActionListener(e -> toggleDifficultyButton(player1Choice, player1DifficultyButton));
        player2Choice.addActionListener(e -> toggleDifficultyButton(player2Choice, player2DifficultyButton));

        player1DifficultyButton = new JButton("Schwierigkeit: Mittel");
        player1DifficultyButton.addActionListener(e -> {
            player1Difficulty = showDifficultyChooser();
            player1DifficultyButton.setText("Schwierigkeit: " + getDifficultyName(player1Difficulty));
        });
        player2DifficultyButton = new JButton("Schwierigkeit: Mittel");
        player2DifficultyButton.addActionListener(e -> {
            player2Difficulty = showDifficultyChooser();
            player2DifficultyButton.setText("Schwierigkeit: " + getDifficultyName(player2Difficulty));
        });

        choicePanel.add(player1DifficultyButton);
        choicePanel.add(player2DifficultyButton);

        player1DifficultyButton.setVisible(false);
        player2DifficultyButton.setVisible(false);

        // Start-Button erstellen
        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(150, 30));  // Set a preferred size so it won't resize

        JPanel startButtonPanel = new JPanel();  // This will use FlowLayout by default
        startButtonPanel.add(startButton);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        choicePanel.add(startButtonPanel, constraints);  // Add the new panel instead of the button directly


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStarted = true;
                resetGame();
                choicePanel.remove(startButton);
                frame.validate();
                frame.repaint();
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        choicePanel.add(new JLabel("Spieler 1:"), constraints);

        constraints.gridx = 1;
        choicePanel.add(player1Choice, constraints);

        constraints.gridx = 2;
        choicePanel.add(player1DifficultyButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        choicePanel.add(new JLabel("Spieler 2:"), constraints);

        constraints.gridx = 1;
        choicePanel.add(player2Choice, constraints);

        constraints.gridx = 2;
        choicePanel.add(player2DifficultyButton, constraints);

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(choicePanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void checkGameStatus() {
        if (hasContestantWon(CROSS_ICON)) {
            JOptionPane.showMessageDialog(frame, "Kreuz gewinnt!");
            resetGame();
        } else if (hasContestantWon(CIRCLE_ICON)) {
            JOptionPane.showMessageDialog(frame, "Kreis gewinnt!");
            resetGame();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(frame, "Unentschieden!");
            resetGame();
        }
    }

    private boolean hasContestantWon(ImageIcon icon) {
        for (int i = 0; i < 3; i++) {
            if (boardButtons[i][0].getIcon() == icon && boardButtons[i][1].getIcon() == icon && boardButtons[i][2].getIcon() == icon) {
                return true;
            }
            if (boardButtons[0][i].getIcon() == icon && boardButtons[1][i].getIcon() == icon && boardButtons[2][i].getIcon() == icon) {
                return true;
            }
        }
        if (icon.equals(boardButtons[0][0].getIcon()) && icon.equals(boardButtons[1][1].getIcon()) && icon.equals(boardButtons[2][2].getIcon())) {
            return true;
        }
        return icon.equals(boardButtons[0][2].getIcon()) && icon.equals(boardButtons[1][1].getIcon()) && icon.equals(boardButtons[2][0].getIcon());
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardButtons[i][j].getIcon().equals(EMPTY_ICON)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j].setIcon(EMPTY_ICON);
            }
        }
        isCrossTurn = new Random().nextBoolean();
        String startingPlayer = isCrossTurn ? "Kreuz" : "Kreis";
        JOptionPane.showMessageDialog(frame, startingPlayer + " beginnt!");

        if (("PC".equals(player1Choice.getSelectedItem()) && isCrossTurn) ||
                ("PC".equals(player2Choice.getSelectedItem()) && !isCrossTurn)) {
            playPCTurn();
        }
    }

    private int showDifficultyChooser() {
        String[] options = {"Leicht", "Mittel", "Schwer"};
        return JOptionPane.showOptionDialog(frame, "Schwierigkeitsgrad auswählen", "Schwierigkeitsgrad",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
    }

    private String getDifficultyName(int difficulty) {
        return switch (difficulty) {
            case 0 -> "Leicht";
            case 2 -> "Schwer";
            default -> "Mittel";
        };
    }

    private void toggleDifficultyButton(JComboBox<String> playerChoice, JButton difficultyButton) {
        // Wenn "PC" ausgewählt ist, dann den Schwierigkeit-Button anzeigen
        difficultyButton.setVisible("PC".equals(playerChoice.getSelectedItem()));
    }

    private void playPCTurn() {
        Point bestMove;

        if ("PC".equals(player1Choice.getSelectedItem()) && isCrossTurn) {
            bestMove = getBestMove(CROSS_ICON, player1Difficulty);
        } else if ("PC".equals(player2Choice.getSelectedItem()) && !isCrossTurn) {
            bestMove = getBestMove(CIRCLE_ICON, player2Difficulty);
        } else {
            return;
        }

        if (bestMove != null) {
            boardButtons[bestMove.x][bestMove.y].setIcon(isCrossTurn ? CROSS_ICON : CIRCLE_ICON);
            isCrossTurn = !isCrossTurn;
            checkGameStatus();
        }
    }

    private Point getBestMove(ImageIcon icon, int difficulty) {
        return switch (difficulty) {
            case 0 -> // Leicht
                    getRandomMove();
            case 1 -> // Mittel
                    getMediumMove(icon);
            case 2 -> // Schwer
                    getHardMove(icon);
            default -> getRandomMove();
        };
    }

    private Point getRandomMove() {
        List<Point> availableMoves = getAvailableMoves();
        if (availableMoves.isEmpty()) return null;
        return availableMoves.get(new Random().nextInt(availableMoves.size()));
    }

    private Point getMediumMove(ImageIcon icon) {
        Point winningMove = getWinningMove(icon);
        if (winningMove != null) {
            return winningMove;
        }
        return getRandomMove();
    }

    private Point getHardMove(ImageIcon icon) {
        int bestVal = -1000;
        Point bestMove = new Point(-1, -1);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardButtons[i][j].getIcon().equals(EMPTY_ICON)) {
                    boardButtons[i][j].setIcon(icon);
                    int moveVal = minimax(0, false);
                    boardButtons[i][j].setIcon(EMPTY_ICON);

                    if (moveVal > bestVal) {
                        bestMove.x = i;
                        bestMove.y = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    private Point getWinningMove(ImageIcon icon) {
        for (Point move : getAvailableMoves()) {
            boardButtons[move.x][move.y].setIcon(icon);
            if (hasContestantWon(icon)) {
                boardButtons[move.x][move.y].setIcon(EMPTY_ICON);
                return move;
            }
            boardButtons[move.x][move.y].setIcon(EMPTY_ICON);
        }
        return null;
    }

    private List<Point> getAvailableMoves() {
        List<Point> availableMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardButtons[i][j].getIcon().equals(EMPTY_ICON)) {
                    availableMoves.add(new Point(i, j));
                }
            }
        }
        return availableMoves;
    }

    private int evaluateBoard() {
        if (hasContestantWon(CROSS_ICON)) {
            return Objects.equals(player1Choice.getSelectedItem(), "PC") ? +10 : -10;
        }
        if (hasContestantWon(CIRCLE_ICON)) {
            return Objects.equals(player2Choice.getSelectedItem(), "PC") ? +10 : -10;
        }
        return 0;
    }

    private int minimax(int depth, boolean isMax) {
        int boardVal = evaluateBoard();

        if (boardVal == 10 || boardVal == -10) {
            return boardVal;
        }

        if (isBoardFull()) {
            return 0;
        }

        int best;
        if (isMax) {
            best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (EMPTY_ICON.equals(boardButtons[i][j].getIcon())) {
                        boardButtons[i][j].setIcon("PC".equals(player2Choice.getSelectedItem()) ? CIRCLE_ICON : CROSS_ICON);
                        best = Math.max(best, minimax(depth + 1, false));
                        boardButtons[i][j].setIcon(EMPTY_ICON);
                    }
                }
            }
        } else {
            best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (EMPTY_ICON.equals(boardButtons[i][j].getIcon())) {
                        boardButtons[i][j].setIcon("PC".equals(player1Choice.getSelectedItem()) ? CROSS_ICON : CIRCLE_ICON);
                        best = Math.min(best, minimax(depth + 1, true));
                        boardButtons[i][j].setIcon(EMPTY_ICON);
                    }
                }
            }
        }
        return best;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
