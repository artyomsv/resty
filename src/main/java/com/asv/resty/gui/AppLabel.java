package com.asv.resty.gui;

import com.asv.resty.utils.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Common application label implementation class.
 *
 * @author Artjoms Stukans <artjoms.stukans@point.lv>
 *         Date: 12.11.7
 */
public class AppLabel extends JLabel {

    /**
     * Label background.
     */
    private Color background = Constants.COLOR_MAIN;

    /**
     * Label foreground.
     */
    private Color foreground = Color.BLACK;

    /**
     * Constructor.
     *
     * @param text Label text
     */
    public AppLabel(final String text) {
        super(text);
        this.setBackground(background);
        this.setForeground(foreground);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVisible(true);
    }

    /**
     * Set label size.
     *
     * @param width  Label width
     * @param height Label height
     */
    public final void setLabelSize(final int width,
                                   final int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
    }

    /**
     * Set label background.
     *
     * @param background Background color
     */
    public final void setLabelBackground(final Color background) {
        setBackground(background);
        this.background = background;
    }

    /**
     * Set label foreground.
     *
     * @param foreground Background color
     */
    public final void setLabelForeground(final Color foreground) {
        setForeground(foreground);
        this.foreground = foreground;
    }

    /**
     * Adds image to label.
     *
     * @param path   Path to button icon
     * @param width  Image width
     * @param height Image height
     */
    public final void addImage(final String path,
                               final int width,
                               final int height) {
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource(path));
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        this.setIcon(imageIcon);
        this.setBounds(0, 0, width, height);
    }

    public final void addImage(final Image image) {
        Image newImage = image.getScaledInstance(262, 350, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(newImage);
        this.setIcon(imageIcon);
        this.setBounds(0, 0, 262, 350);
    }
}
