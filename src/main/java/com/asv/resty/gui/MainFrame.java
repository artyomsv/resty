package com.asv.resty.gui;

import com.asv.resty.service.Notifier;
import com.asv.resty.service.Pref;
import com.asv.resty.service.WarningDialogWorker;
import com.asv.resty.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Class description:
 *
 * @author Artjoms Stukans <artjoms.stukans@point.lv>
 *         Date: 12.11.7
 */
public class MainFrame extends JFrame {

    public static final String TITLE = "Resty";

    private final AppButton buttonStart;
    private final AppButton buttonStop;
    private final AppButton buttonExit;
    private final JPanel panelButtons;
    private final JSlider slider;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> future;
    private Timer countdownTimer;
    private TrayIcon trayIcon;

    public MainFrame() {
        super("Rest timer");
        setSize(new Dimension(380, 150));
        setLocationRelativeTo(null);
        setResizable(false);

        URL dialogIcon = getClass().getClassLoader().getResource(Constants.IMG_ICON);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(dialogIcon));

        addWindowListener(new WindowListener());

        buttonStart = getButton("Start");
        buttonStart.addActionListener(new StartListener());

        buttonStop = getButton("Stop");
        buttonStop.addActionListener(new StopListener());
        buttonStop.setEnabled(false);

        buttonExit = getButton("Exit");
        buttonExit.addActionListener(new ExitListener());

        slider = new JSlider(JSlider.HORIZONTAL, 0, 90, Pref.getTimer());
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0), true);
        panelButtons.add(buttonStart);
        panelButtons.add(buttonStop);
        panelButtons.add(buttonExit);

        this.getContentPane().setLayout(new GridBagLayout());
        this.getContentPane().add(slider, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 20, 0, 20), 0, 0));
        this.getContentPane().add(panelButtons, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));

        setVisible(true);
    }

    private AppButton getButton(final String caption) {
        AppButton button = new AppButton(caption);
        button.setButtonSize(100, 30);
        button.setDefaultMouseListener();
        button.setButtonColors(Constants.BUTTON_COLOR, Constants.BUTTON_MOUSE_OVER_COLOR);
        return button;
    }

    private void startResty() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }

        countdownTimer = new Timer((int) TimeUnit.SECONDS.toMillis(1), new TimerAction(TimeUnit.SECONDS.toMillis(slider.getValue()) - 1000));
        countdownTimer.start();

        future = executorService.schedule(
                new WarningDialogWorker(
                        new Notifier() {
                            @Override
                            public void execute() {
                                if (countdownTimer != null) {
                                    countdownTimer.stop();
                                    setTitle(TITLE);
                                }
                            }
                        },
                        new Notifier() {
                            @Override
                            public void execute() {
                                startResty();
                            }
                        }
                ),
                slider.getValue(),
                TimeUnit.SECONDS);
    }

    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            System.exit(0);
        }
    }

    private class StartListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            Pref.setTimer(slider.getValue());
            buttonStop.setEnabled(true);
            buttonStart.setEnabled(false);
            buttonExit.setEnabled(true);
            startResty();
        }
    }

    private class TimerAction implements ActionListener {

        private long time;

        private TimerAction(long time) {
            this.time = time;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            String value = Pref.dateFormat.format(new Date(time));
            MainFrame.this.setTitle(value);
            if (trayIcon != null) {
                trayIcon.setToolTip(value);
            }
            if (time > 0) {
                time -= 1000;
            }
        }
    }

    private class StopListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            future.cancel(true);
            countdownTimer.stop();
            MainFrame.this.setTitle(TITLE);
            buttonStop.setEnabled(false);
            buttonStart.setEnabled(true);
            buttonExit.setEnabled(true);
        }
    }

    private class WindowListener extends WindowAdapter {

        @Override
        public void windowClosing(final WindowEvent e) {
            System.exit(0);
        }

        @Override
        public void windowActivated(final WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {

            if (SystemTray.isSupported()) {

                MainFrame.this.setVisible(false);
                SystemTray tray = SystemTray.getSystemTray();
                URL dialogIcon = getClass().getClassLoader().getResource(Constants.IMG_ICON);
                Image image = Toolkit.getDefaultToolkit().getImage(dialogIcon).getScaledInstance(16, 16, Image.SCALE_SMOOTH);

                ActionListener listener = new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        MainFrame.this.setVisible(true);
                        MainFrame.this.setState(Frame.NORMAL);
                    }
                };

                PopupMenu popup = new PopupMenu();
                MenuItem defaultItem = new MenuItem("Restore application");
                defaultItem.addActionListener(listener);
                popup.add(defaultItem);

                trayIcon = new TrayIcon(image, "Tray demo", popup);
                trayIcon.setImage(image);

                trayIcon.addActionListener(listener);

                try {
                    tray.add(trayIcon);
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
            } else {
                System.out.println("Else");
            }
        }
    }


}
