import javax.swing.*;
import java.util.Random;

public class Algorithm {

    private final JButton[][] boardButtons;
    private final ImageIcon CROSS_ICON;
    private final ImageIcon CIRCLE_ICON;
    private final ImageIcon EMPTY_ICON;

    public Algorithm(JButton[][] boardButtons, ImageIcon CROSS_ICON, ImageIcon CIRCLE_ICON, ImageIcon EMPTY_ICON) {
        this.boardButtons = boardButtons;
        this.CROSS_ICON = CROSS_ICON;
        this.CIRCLE_ICON = CIRCLE_ICON;
        this.EMPTY_ICON = EMPTY_ICON;
    }

    public void zufaelligerZug(boolean isCrossTurn) {
        ImageIcon icon = isCrossTurn ? CROSS_ICON : CIRCLE_ICON;
        Random rand = new Random();
        int x, y;

        do {
            int zufall = rand.nextInt(9) + 1;
            x = (zufall - 1) / 3;
            y = (zufall - 1) % 3;
        } while (!boardButtons[x][y].getIcon().equals(EMPTY_ICON));

        boardButtons[x][y].setIcon(icon);
    }

    // TODO: Methode für den Minimax-Algorithmus hinzufügen
}
