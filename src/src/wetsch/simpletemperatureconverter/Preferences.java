package wetsch.simpletemperatureconverter;

import wetsch.simpletemperatureconverter.R;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity implements OnPreferenceChangeListener {
	private ListPreference roundingOption;
	private Preference versionNumber;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		roundingOption = (ListPreference) getPreferenceManager().findPreference("rounding_option");
		versionNumber = (Preference) getPreferenceManager().findPreference("version_number");
		versionNumber.setSummary(versionNumber.getSharedPreferences().getString("version_number", "0"));
		roundingOption.setOnPreferenceChangeListener(this);
		roundingOption.setSummary("Rounding set to "+roundingOption.getValue());
	}
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		roundingOption.setSummary("Value changed");

		return true;
	}

}
