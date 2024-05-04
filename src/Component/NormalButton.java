package Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class NormalButton extends JButton {
    Color defaultColor = Colors.slate900;
    Color pressedColor = Colors.indigo400;
    Color hoverColor = Colors.slate800;
    Color outlineColor = Colors.indigo400;
    Color textColor = Colors.slate100;
    int cornerRadius = 20;
    int outlineRadius = 20;
    int outlineThickness = 2;
    int py = 10;
    int px = 20;

    boolean isHovered = false;

    public NormalButton(String text) {
        super(text);
        addKeyListener(null);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                isHovered = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
                isHovered = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
                isHovered = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(defaultColor);
                isHovered = false;
            }
        });

        // Set initial button properties
        setForeground(textColor);
        setBackground(defaultColor);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        
        setBorder(BorderFactory.createCompoundBorder(
               BorderFactory.createLineBorder(Color.BLACK, 0),
               BorderFactory.createEmptyBorder(py, px, py, px)
        ));
        setFont(Fonts.OutfitBold);
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    public void setPressedColor(Color backgroundColor) {
        this.pressedColor = backgroundColor;
        setBackground(backgroundColor);
        repaint();
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        repaint();
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        setForeground(textColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Create rounded rectangle with specified corner radius
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);

        // Paint the background
        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);

        // Paint the text
        g2d.setColor(getForeground());
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(getText())) / 2;
        int y = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(getText(), x, y);

        if (isHovered) {
            g2d.setColor(outlineColor);
            g2d.setStroke(new BasicStroke(outlineThickness));
            g2d.drawRoundRect(outlineThickness / 2, outlineThickness / 2, getWidth() - outlineThickness, getHeight() - outlineThickness, outlineRadius, outlineRadius);
        }

        g2d.dispose();
    }
}
