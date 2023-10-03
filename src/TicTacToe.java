import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToe {

    private JFrame frame;
    private JButton[][] boardButtons = new JButton[3][3];
    private JComboBox<String> player1Choice, player2Choice;
    private JButton startButton;
    private boolean isCrossTurn = true;
    private final ImageIcon CROSS_ICON = new ImageIcon("data/cross.png");
    private final ImageIcon CIRCLE_ICON = new ImageIcon("data/circle.png");
    private final ImageIcon EMPTY_ICON = new ImageIcon("data/empty.png");
    private boolean gameStarted = false;

    public TicTacToe() {
        frame = new JFrame("Tic Tac Toe");
        frame.setIconImage(new ImageIcon("/mnt/data/ifnmho27.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].setIcon(EMPTY_ICON);
                boardButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!gameStarted) return;

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
                    }
                });
                boardPanel.add(boardButtons[i][j]);
            }
        }

        JPanel choicePanel = new JPanel();
        player1Choice = new JComboBox<>(new String[]{"Mensch", "PC"});
        player2Choice = new JComboBox<>(new String[]{"Mensch", "PC"});

        // PC-Option deaktivieren
        player1Choice.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if ("PC".equals(value)) {
                    c.setEnabled(false);
                }
                return c;
            }
        });

        player2Choice.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if ("PC".equals(value)) {
                    c.setEnabled(false);
                }
                return c;
            }
        });

        startButton = new JButton("Start");
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

        choicePanel.add(new JLabel("Spieler 1:"));
        choicePanel.add(player1Choice);
        choicePanel.add(new JLabel("Spieler 2:"));
        choicePanel.add(player2Choice);
        choicePanel.add(startButton);

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
        if (boardButtons[0][0].getIcon() == icon && boardButtons[1][1].getIcon() == icon && boardButtons[2][2].getIcon() == icon) {
            return true;
        }
        return boardButtons[0][2].getIcon() == icon && boardButtons[1][1].getIcon() == icon && boardButtons[2][0].getIcon() == icon;
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
