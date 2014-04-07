package alisovets.example.simpletesttask.net;

import alisovets.example.simpletesttask.dialog.DialogCreator;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectChecker {

	/**
	 * check if settings allow to use a network
	 * @param context
	 * @return true if net available
	 */
	public static boolean checkNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			return false;
		}

		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetworkInfo == null) {
			return false;
		}
		return activeNetworkInfo.isConnectedOrConnecting();
	}

	/**
	 * check if settings allow to use a network and shows dialog to change setting if no networks available 
	 * @param context 
	 * @return true if net available
	 */
	public static boolean checkNetworkAvailableWithDialog(Context context) {
		if(checkNetworkAvailable(context)){
			return true;
		}
		DialogCreator.noNetworkChangeSettingDialog(context);
		return false;
	}
	
}
