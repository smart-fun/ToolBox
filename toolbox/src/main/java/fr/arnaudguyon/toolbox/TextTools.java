package fr.arnaudguyon.toolbox;

import android.support.annotation.NonNull;

import java.text.Normalizer;

/**
 * Created by aguyon on 03.02.17.
 */

public class TextTools {

    public static String replaceAccents(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static boolean matchSearchPattern(@NonNull String searchPattern, @NonNull String text) {
        if (searchPattern.equals(text)) {
            return true;
        }
        if (searchPattern.isEmpty()) {
            return true;
        }
        if (text.isEmpty()) {
            return false;
        }
        char c = searchPattern.charAt(0);
        for(int i=0; i<text.length(); ++i) {
            if (c == text.charAt(i)) {
                String newPattern = searchPattern.substring(1);
                String newText = text.substring(i+1);
                return matchSearchPattern(newPattern, newText);
            }
        }
        return false;
    }

}
