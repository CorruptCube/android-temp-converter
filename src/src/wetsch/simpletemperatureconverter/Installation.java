package wetsch.simpletemperatureconverter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Installation {
	private static SharedPreferences sharedPref = null;//The shared preferences object.

    /**
	 * This method checks to see if the app version matches the current app version .
	 * The about app dialog will be displayed if the version does not match.
	 * @param context The activity from which this method is called.
	 */

    public static void checkVersion(Context context){
		sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		//Checking if the key that holds the app version exists in shared preferences.
		if(!sharedPref.contains("version_number")){
			Editor e = sharedPref.edit();
			e.putString("version_number", context.getString(R.string.version_name));
			e.commit();
			context.startActivity(new Intent(context, AboutAppMessage.class));
			
		/*If the key that holds the app version exists, checking if the app version stored in the key 
		* matches.
		*/
		}else if(!sharedPref.getString("version_number", "").equals(context.getString(R.string.version_name))){
			Editor e = sharedPref.edit();
			e.putString("version_number", context.getString(R.string.version_name));
			e.commit();
			context.startActivity(new Intent(context, AboutAppMessage.class));
		}
	}
}