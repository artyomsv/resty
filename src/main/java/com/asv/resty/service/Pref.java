package com.asv.resty.service;

import com.asv.resty.RestTimer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;

/**
 * Class description:
 *
 * @author Artjoms Stukans
 *         artjoms@eptron.eu
 */
public class Pref {

    public static final String TIMER = "User timer value";
    public static final DateFormat dateFormat = new SimpleDateFormat("mm:ss");
    public static final int WARNING_DIALOG_TIME = 6;
    private static final Preferences pref = Preferences.userRoot().node(RestTimer.class.getName());

    public static int getTimer() {
        String timer = pref.get(TIMER, "0");
        if (Pattern.matches("^[0-9]+$", timer)) {
            return Integer.valueOf(timer);
        } else {
            pref.put(TIMER, "0");
            throw new RuntimeException("Wrong output digit format. Saving \"0\" preferences");
        }
    }

    public static void setTimer(final Integer timer) {
        pref.put(TIMER, String.valueOf(timer));
    }

}
