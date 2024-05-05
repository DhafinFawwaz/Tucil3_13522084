package Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SquaredLabel extends JPanel {
    int gap = 20;
    int width = 32;
    int height = 35;

    int cornerRadius = 20;
    Color backgroundColor = Colors.slate900;

    ArrayList<Label> labelList = new ArrayList<Label>();
    public SquaredLabel(String text) {
        setBackground(Colors.slate950);
        setText(text);
    }

    public void setText(String text){
        String[] textArray = text.split("");
        for(String s : textArray){
            Label newLabel = createLabel(s.toUpperCase());
            add(newLabel);
            labelList.add(newLabel);
        }
    }

    public void setNumberLabel(int number){
        Label newLabel = new Label(String.valueOf(number));
        newLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        add(newLabel, 0);
    }

    public void setGreenMask(String destination) {
        for(int i = 0; i < destination.length(); i++){
            if(destination.toLowerCase().charAt(i) == labelList.get(i).getText().toLowerCase().charAt(0))
                labelList.get(i).setBackground(Colors.green500);
        }
    }

    Label createLabel(String s){
        Label label = new Label(s){
            // round corner
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = getWidth();
                int height = getHeight();
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);
                g2d.setColor(getBackground());
                g2d.fill(roundedRectangle);

                // Paint the text
                g2d.setColor(getForeground());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (width - fm.stringWidth(getText())) / 2;
                int y = (height - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };
        label.setBackground(backgroundColor);
        label.setMinimumSize(new Dimension(width, height));
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }
}
