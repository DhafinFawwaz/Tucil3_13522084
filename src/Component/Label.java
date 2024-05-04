package Component;

import javax.swing.JLabel;

public class Label extends JLabel {
    public Label(String text) {
        super(text);
        setForeground(Colors.slate100);
        setFont(Fonts.OutfitBold.deriveFont(14));
    }
}
