package konid.soxzz5.fitfood.fitfood_session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Soxzer on 16/12/2016.
 */

public class SessionManager {
    public void setPreferences(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("FitFood", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();

    }

    public  String getPreferences(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("FitFood",	Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }

}
