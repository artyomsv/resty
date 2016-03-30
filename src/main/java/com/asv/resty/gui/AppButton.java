package com.asv.resty.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Common application button implementation class.
 */
public class AppButton extends JButton {

    /**
     * Background button color.
     */
    private Color buttonColor;

    /**
     * Background button standard color.
     */
    private Color buttonStandardColor;

    /**
     * Background button color when mouse enter.
     */
    private Color buttonMouseEnter;

    /**
     * Constructor for plain button.
     *
     * @param caption Button caption
     */
    public AppButton(final String caption) {
        super(caption);
        setFocusPainted(false);
        setBorder(BorderFactory.createEtchedBorder());
        setVisible(true);
    }

    /**
     * Constructor for button with image.
     *
     * @param imagePath   Image path
     * @param imageWidth  Image width
     * @param imageHeight Image height
     */
    public AppButton(final String imagePath,
                     final int imageWidth,
                     final int imageHeight) {
        addButtonIcon(imagePath, imageWidth, imageHeight);
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(buttonColor);
        setFocusPainted(false);
        setVisible(true);
    }

    /**
     * Sets image icon to the button.
     *
     * @param imagePath   path to the icon resource directory
     * @param imageWidth  Image width
     * @param imageHeight Image height
     */
    public final void addButtonIcon(final String imagePath,
                                    final int imageWidth,
                                    final int imageHeight) {
        AppLabel image = new AppLabel(null);
        image.addImage(imagePath, imageWidth, imageHeight);
        this.setLayout(new GridBagLayout());
        this.add(image,
                new GridBagConstraints(
                        0, 0, 1, 1, 1, 1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * Sets button foreground, background colors and font.
     *
     * @param background        Background color
     * @param onMouseBackground Foreground color
     */
    public final void setButtonColors(final Color background,
                                      final Color onMouseBackground) {
        this.buttonColor = background;
        this.buttonStandardColor = background;
        this.buttonMouseEnter = onMouseBackground;
        setBackground(this.buttonColor);
    }

    /**
     * Method to change button background color.
     *
     * @param color Color instance.
     */
    private void changeBackgroundColor(final Color color) {
        this.setBackground(color);
    }

    /**
     * Ser button size.
     *
     * @param width  Button width
     * @param height Button height
     */
    public final void setButtonSize(final int width,
                                    final int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
    }

    /**
     * Adds mouse listener to change background
     * color on mouse enter and exit.
     */
    public final void setDefaultMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                if (isEnabled()) {
                    changeBackgroundColor(buttonMouseEnter);
                }
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                if (isEnabled()) {
                    changeBackgroundColor(buttonStandardColor);
                }
            }
        });
    }
}
