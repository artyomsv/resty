package com.asv.resty.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Common application panel implementation class.
 */
public class AppPanel extends JPanel {

    /**
     * No pointBorder.
     */
    public static final int BORDER_NONE = 0;

    /**
     * Etched pointBorder.
     */
    public static final int BORDER_ETCHED = 1;

    /**
     * Rounded pointBorder.
     */
    public static final int BORDER_ROUNDED = 2;

    /**
     * Panel background color.
     */
    private Color background;

    /**
     * Border style.
     */
    private int borderStyle = BORDER_NONE;

    /**
     * Stroke size. it is recommended to set it to 1 for better view
     */
    private int strokeSize = 1;

    /**
     * Color of shadow.
     */
    private Color shadowColor;

    /**
     * Sets if it drops shadow
     */
    private boolean shady;

    /**
     * Sets if it has an High Quality view
     */
    private boolean highQuality = true;

    /**
     * Double values for Horizontal and Vertical radius of corner arcs
     */
    private Dimension arcs;

    /**
     * Distance between shadow border and opaque panel border
     */
    private int shadowGap;

    /**
     * The offset of shadow.
     */
    private int shadowOffset;

    /**
     * The transparency value of shadow. ( 0 - 255)
     */
    private int shadowAlpha;

    /**
     * Constructor.
     *
     * @param layout Layout manager
     */
    public AppPanel(final LayoutManager layout) {
        super(layout, true);
        initDefaultParams();
        setVisible(true);

    }

    /**
     * Method initialize default panel parameters.
     */
    private void initDefaultParams() {
        this.borderStyle = BORDER_NONE;
        this.background = Color.LIGHT_GRAY;
        this.shadowColor = Color.BLACK;
        this.arcs = new Dimension(10, 10);
        this.shadowAlpha = 150;
        this.shadowOffset = 4;
        this.shadowGap = 5;
        this.highQuality = true;
        setOpaque(false);
    }

    /**
     * Change background color of panel.
     *
     * @param backgroundColor Background color
     */
    public final void setBackgroundColor(final Color backgroundColor) {
        this.background = backgroundColor;
    }

    @Override
    public final void paintComponent(final Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;
        Color shadowColorA = new Color(shadowColor.getRed(),
                shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        //Sets antialiasing if HQ.
        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        //Draws shadow borders if any.
        if (shady) {
            graphics.setColor(shadowColorA);
            switch (borderStyle) {
                case BORDER_ROUNDED:
                    graphics.fillRoundRect(
                            shadowOffset,// X position
                            shadowOffset,// Y position
                            width - strokeSize - shadowOffset, // width
                            height - strokeSize - shadowOffset, // height
                            arcs.width, arcs.height);// arc Dimension
                    break;
                default:
                    graphics.fillRect(
                            shadowOffset,// X position
                            shadowOffset,// Y position
                            width - strokeSize - shadowOffset, // width
                            height - strokeSize - shadowOffset); // height
                    break;
            }
        } else {
            shadowGap = 1;
        }

        //Draws the rounded opaque panel with borders.
        graphics.setColor(background);
        switch (borderStyle) {
            case BORDER_NONE:
                graphics.fillRect(0, 0, width, height);
                break;
            case BORDER_ETCHED:
                graphics.fillRect(0, 0, width - shadowGap,
                        height - shadowGap);
                graphics.setColor(getForeground());
                graphics.setStroke(new BasicStroke(strokeSize));
                graphics.drawRect(0, 0, width - shadowGap,
                        height - shadowGap);
                break;
            case BORDER_ROUNDED:
                graphics.fillRoundRect(0, 0, width - shadowGap,
                        height - shadowGap, arcs.width, arcs.height);
                graphics.setColor(getForeground());
                graphics.setStroke(new BasicStroke(strokeSize));
                graphics.drawRoundRect(0, 0, width - shadowGap,
                        height - shadowGap, arcs.width, arcs.height);
                break;
        }

        //Sets strokes to default, is better.
        graphics.setStroke(new BasicStroke());
    }

    /**
     * Setter for shady effect.
     *
     * @param shady true/false.
     */
    public final void setShady(final boolean shady) {
        this.shady = shady;
    }

    /**
     * Sets operations defined point pointBorder style.
     *
     * @param borderStyle Point Border style (None, etched, rounded)
     */
    public final void setBorderStyle(final int borderStyle) {
        this.borderStyle = borderStyle;
    }

    /**
     * Setter for panel arc field.
     *
     * @param arc Panel arc value
     */
    public final void setArcs(final int arc) {
        this.arcs = new Dimension(arc, arc);
    }
}
