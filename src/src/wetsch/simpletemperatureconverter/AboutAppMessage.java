package wetsch.simpletemperatureconverter;
/**
 * This activity displays the about application dialog to the user.
 * This dialog contains the application name, version name and short
 * description about the application.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AboutAppMessage extends Activity {
	private AssetManager assets;
	private TextView message;
	private StringBuilder messageString;
	private InputStream messageData;
	private final String tag = getClass().getName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutapp);
		assets = getAssets();
		message = (TextView) findViewById(R.id.AboutAppMessage);
		messageString = new StringBuilder();
		buildMessage();
	}
	//Building the message text to be loaded to dialog text view.
	private void buildMessage(){
		String line = null;
		messageString.append("Application Version:\t"+getAppVersion()+"\n\n");
		try {
			messageData = assets.open("about_app.txt");
			BufferedReader data = new BufferedReader(new InputStreamReader(messageData));
			while ((line = data.readLine()) != null){
				if(line.equals("\\n"))
					messageString.append("\n");
				else if(line.equals("\\n\\n"))
					messageString.append("\n\n");
				else if(line.equals("\\u2022"))
					messageString.append("\n\u2022 " + data.readLine());
				else
					messageString.append(line);
			}
			message.setText(messageString.toString());

		} catch (IOException e) {
			Log.e(tag, "Could not load File about_app.txt");
			e.printStackTrace();
		}
	}
	
	//Get application version name.
	private String getAppVersion(){
		String versionName = null;
			try {
				versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			} catch (NameNotFoundException e) {
				Log.e(tag, "Could not get application version name from package " + getPackageName());
				e.printStackTrace();
			}
			return versionName;
	}
}
