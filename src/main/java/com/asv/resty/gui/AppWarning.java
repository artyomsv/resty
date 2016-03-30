package com.asv.resty.gui;

import com.asv.resty.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * @author Artjoms Stukans <artjoms.stukans@point.lv>
 *         Date: 12.11.7
 */
public class AppWarning extends JDialog implements ActionListener {

    /**
     * Question icon image path.
     */
    public static final String QUESTION = "image/warning/metal_question.png";
    /**
     * Info icon image path.
     */
    public static final String INFO = "image/warning/metal_info.png";
    /**
     * Warning icon image path.
     */
    public static final String WARNING = "image/warning/metal_warning.png";
    /**
     * Error icon image path.
     */
    public static final String ERROR = "image/warning/metal_error.png";
    /**
     * Warning message text alignment to center.
     */
    public static final String ALIGN_CENTER = "center";
    /**
     * Warning message text alignment to left.
     */
    public static final String ALIGN_LEFT = "left";
    /**
     * Warning message text alignment to right.
     */
    public static final String ALIGN_RIGHT = "right";
    /**
     * panel margin.
     */
    private static final int MARGIN = 20;
    /**
     * Message label margin.
     */
    private static final int LABEL_MARGIN = 5;
    /**
     * Buttons width.
     */
    private static final int BUTTON_WIDTH = 100;
    /**
     * Buttons height.
     */
    private static final int BUTTON_HEIGHT = 30;
    /**
     * Icon size.
     */
    private static final int ICON_SIZE = 32;
    /**
     * Button pressed result variable.
     */
    private int answer = 0;
    /**
     * Array of buttons showed on warning dialog.
     */
    private AppButton[] buttons;
    /**
     * Content pane panel.
     */
    private AppPanel panel;

    /**
     * Image and warning message north panel.
     */
    private AppPanel panelNorth;

    /**
     * Choose button south panel.
     */
    private AppPanel panelSouth;

    /**
     * Image label.
     */
    private AppLabel image;

    /**
     * Message label.
     */
    private AppLabel message;

    /**
     * Path to icon.
     */
    private String iconPath;

    /**
     * Warning message.
     */
    private String warningMessage;

    /**
     * Button names.
     */
    private String[] buttonNames;

    /**
     * Background color.
     */
    private Color background = Constants.COLOR_MAIN;

    /**
     * Constructor.
     *
     * @param iconPath       Icon
     * @param warningMessage Warning message
     * @param dialogTitle    Warning dialog title
     * @param buttonNames    Buttons names for warning message
     */
    public AppWarning(final String iconPath,
                      final String warningMessage,
                      final String dialogTitle,
                      final String... buttonNames) {
        this.iconPath = iconPath;
        this.warningMessage = warningMessage;
        this.buttonNames = buttonNames;
        initPanels();
        imageInit();
        messageInit();
        buttonsInit();
        constructUI();
        URL dialogIcon = getClass().getClassLoader().getResource(Constants.IMG_ICON);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(dialogIcon));
        this.setTitle(dialogTitle);
        this.setModal(true);
        this.setResizable(false);
    }

    /**
     * Shows warning message.
     */
    public final void performWarning() {
        pack();
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
    }

    /**
     * Construct warning dialog UI.
     */
    private void constructUI() {
        this.getContentPane().add(panel);

        panel.add(panelNorth,
                new GridBagConstraints(
                        1, 0, 1, 1, 1, 1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(MARGIN, MARGIN, MARGIN / 2, MARGIN), 0, 0));

        panel.add(panelSouth,
                new GridBagConstraints(
                        1, 1, 1, 1, 1, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, LABEL_MARGIN, 0), 0, 0));

        panel.add(image,
                new GridBagConstraints(
                        0, 0, 1, 1, 1, 1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(MARGIN / 2, MARGIN, 0, 0), 0, 0));

        addMessageLabelToDialog();

        for (JButton button : buttons) {
            panelSouth.add(button);
        }
    }

    /**
     * Method adds array of message labels to dialog.
     */
    private void addMessageLabelToDialog() {

        panelNorth.add(message,
                new GridBagConstraints(
                        1, 0, 1, 1, 1, 1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0, 0, LABEL_MARGIN, 0), 0, 0));
    }

    /**
     * Initialize icon label.
     */
    private void imageInit() {
        image = new AppLabel(null);
        image.addImage(iconPath, ICON_SIZE, ICON_SIZE);
    }

    /**
     * Initialize warning message label.
     */
    private void messageInit() {
        message = new AppLabel(parseWarningMessage(warningMessage, ALIGN_CENTER));
        message.setFont(new Font("Display", Font.BOLD, 15));
    }

    /**
     * Initialize dialog panels.
     */
    private void initPanels() {
        panel = new AppPanel(new GridBagLayout());
        panel.setBackgroundColor(background);
        panel.setBorderStyle(AppPanel.BORDER_NONE);

        panelNorth = new AppPanel(new GridBagLayout());
        panelNorth.setBackgroundColor(background);
        panelNorth.setBorderStyle(AppPanel.BORDER_NONE);

        panelSouth = new AppPanel(new FlowLayout(FlowLayout.CENTER));
        panelSouth.setBackgroundColor(background);
        panelSouth.setBorderStyle(AppPanel.BORDER_NONE);
    }

    /**
     * Buttons initialization according to argument buttonNames size and values.
     */
    private void buttonsInit() {
        buttons = new AppButton[buttonNames.length];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new AppButton(buttonNames[i]);
            buttons[i].setButtonSize(BUTTON_WIDTH, BUTTON_HEIGHT);
            buttons[i].setFont(new Font("Display", Font.BOLD, 15));
            buttons[i].setButtonColors(Constants.BUTTON_COLOR, Constants.BUTTON_MOUSE_OVER_COLOR);
            buttons[i].setMnemonic(buttonNames[i].charAt(0));
            buttons[i].setDefaultMouseListener();
            buttons[i].addActionListener(this);
        }
    }

    /**
     * Set warning message background color.
     *
     * @param background Background color.
     */
    public final void setWarningBackground(final Color background) {
        this.background = background;
    }

    /**
     * Getter to receive index of button was pressed during warning dialog.
     *
     * @return Button number
     */
    public final int getAnswer() {
        return this.answer;
    }


    @Override
    public final void actionPerformed(final ActionEvent e) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == e.getSource()) {
                this.answer = i;
                dispose();
            }
        }
    }

    /**
     * Getter for Point Label instance
     *
     * @return Point Label instance
     */
    public final AppLabel getLabelMessage() {
        return message;
    }

    /**
     * Setter for Point Label array
     *
     * @param label AppLabel
     * @param index index
     */
    public final void setMessage(final AppLabel label,
                                 final int index) {
        this.message = label;
    }

    /**
     * Method parse incoming warning message in to
     * HTML aligned format.
     *
     * @param message Warning message.
     * @param align   Align value.
     * @return Formatted string.
     */
    private String parseWarningMessage(final String message,
                                       final String align) {
        StringBuilder builder = new StringBuilder(message.replaceAll("\\\n", "<BR>"));
        builder.insert(0, "<html><div align=\"" + align + "\">");
        builder.insert(builder.length(), "</div></html>");
        return builder.toString();
    }

    /**
     * Method to set warning text alignment.
     *
     * @param alignment Text alignment value.
     */
    public void setWarningAlignment(final String alignment) {
        message.setText(parseWarningMessage(warningMessage, alignment));
    }
}
