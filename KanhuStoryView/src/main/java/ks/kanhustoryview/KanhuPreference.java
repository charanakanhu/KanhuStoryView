package ks.kanhustoryview;

import android.content.Context;
import android.content.SharedPreferences;

public class KanhuPreference {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private static final String PREF_NAME = "kanhu_perf";
    private static final int PREF_MODE_PRIVATE = 1;

    public KanhuPreference(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }
    public void clearStoryPreferences() {
        editor.clear();
        editor.apply();
    }

    public void setStoryVisited(String uri){
        editor.putBoolean(uri,true);
        editor.apply();
    }

    public boolean isStoryVisited(String uri){
        return preferences.getBoolean(uri,false);
    }
}

