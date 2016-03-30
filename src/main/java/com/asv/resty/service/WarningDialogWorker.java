package com.asv.resty.service;

import com.asv.resty.gui.AppWarning;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.asv.resty.gui.MainFrame.TITLE;
import static com.asv.resty.service.Pref.WARNING_DIALOG_TIME;

public class WarningDialogWorker implements Runnable {


    private final Notifier notifier;
    private final Notifier warningEndNotifier;

    public WarningDialogWorker(Notifier warningStartNotifier, Notifier warningEndNotifier) {
        this.notifier = warningStartNotifier;
        this.warningEndNotifier = warningEndNotifier;
    }

    @Override
    public void run() {

        final String message = "Let`s rest for a while!";
        final String buttonOk = "OK";

        SwingUtilities.invokeLater(new Runnable() {

            private Timer warningDialogDisposingTimer;
            private Timer warningDialogCountDownTimer;

            @Override
            public void run() {
                notifier.execute();

                if (warningDialogDisposingTimer != null) {
                    warningDialogDisposingTimer.stop();
                }

                if (warningDialogCountDownTimer != null) {
                    warningDialogCountDownTimer.stop();
                }


                final AppWarning warning = new AppWarning(AppWarning.INFO, message, TITLE, buttonOk);

                warningDialogCountdownTimerInitiation(warning);
                warningDisposeTimerInitiation(warning);

                warning.performWarning();
            }

            private void warningDialogCountdownTimerInitiation(final AppWarning warning) {
                warningDialogCountDownTimer = new Timer((int) TimeUnit.SECONDS.toMillis(1), new ActionListener() {

                    private long countdownTime = TimeUnit.SECONDS.toMillis(WARNING_DIALOG_TIME - 1);

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        warning.setTitle(Pref.dateFormat.format(new Date(countdownTime)));
                        if (countdownTime > 0) {
                            countdownTime -= 1000;
                        }
                    }
                });
                warningDialogCountDownTimer.start();
            }

            private void warningDisposeTimerInitiation(final AppWarning warning) {

                warningDialogDisposingTimer = new Timer((int) TimeUnit.SECONDS.toMillis(WARNING_DIALOG_TIME), new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        warningDialogDisposingTimer.stop();
                        warningDialogCountDownTimer.stop();
                        warning.dispose();

                        warningEndNotifier.execute();
                    }
                });

                warningDialogDisposingTimer.start();
            }
        });

    }
}