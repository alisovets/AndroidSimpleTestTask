package alisovets.example.simpletesttask.dialog;


import alisovets.example.simpletesttask.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

/**
 * The class to simplify the creation of dialogs
 * @author Alexander Lisovets, 2014
 *
 */
public class DialogCreator {


	/**
	 * Creates a dialog with the "OK" button and the title and message determined of the their resource Ids      
	 * @param context
	 * @param titleResourceId - the resource id of the title string
	 * @param messageResourceId - the resource id of the message string
	 */
	public static void messageDialog(final Context context, final int titleResourceId, final int messageResourceId) {
		Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				if (!((Activity) context).isFinishing()) {
					new AlertDialog.Builder(context).setTitle(titleResourceId).setMessage(messageResourceId)
							.setPositiveButton(R.string.ok_btn_title, null).setOnCancelListener(null).show();
				}

			}
		});

	}
	
	
	
	/**
	 * Creates a dialog without buttons and with the title and message which is determined of the their resource Ids       
	 * @param activity
	 * @param titleResourceId - the resource id of the title string
	 * @param messageResourceId - the resource id of the message string
	 */
	public static void messageDialogNoButton(final Activity activity, final int titleResourceId, final int messageResourceId) {
		Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (! activity.isFinishing()) {
					new AlertDialog.Builder(activity).setTitle(titleResourceId).setMessage(messageResourceId).show();
				}
			}
		});

	}
	
	/**
	 * Creates a dialog with view that determines by specified resource Id       
	 * @param activity
	 * @param viewResourceId - the resource id of the view
	 */
	public static void viewDialog(final Activity activity, final int viewResourceId) {		
		LayoutInflater inflater = LayoutInflater.from(activity);
		final View dialogView = inflater.inflate(viewResourceId, null, false);
		
		Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (!activity.isFinishing()) {
					new AlertDialog.Builder(activity).setView(dialogView).show();
					
				}
			}
		});

	}

	
	
	/**
	 * Creates a dialog to ask a question the user and process user's positive response
	 * @param activity
	 * @param titleResource
	 * @param questionResource
	 * @param yesOnClickListener
	 */
	public static void questionYesCancelDialog(final Activity activity, final int titleResource, final int questionResource,
			final OnClickListener yesOnClickListener) {
		Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				if (activity.isFinishing()) {
					return;
				}
				new AlertDialog.Builder(activity).setTitle(titleResource).setMessage(questionResource)
						.setPositiveButton(R.string.yes_btn_title, yesOnClickListener).setNegativeButton(R.string.cancel_btn_title, null)
						.setOnCancelListener(null).show();
			}
		});
	}
	
	
	/**
	 * 
	 * Creates dialog to report that the network is not available 
	 * The dialog has two buttons: 
	 * "Setting" to open network setting;
	 * "Cancel" to close dialog.
	 * @param context
	 * @param messageResourceId
	 */
	public static void noNetworkChangeSettingDialog(final Context context) {
		Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				if (((Activity) context).isFinishing()) {
					return;
				}
				new AlertDialog.Builder(context).setTitle(R.string.no_network_connection).setMessage(R.string.you_need_check_settings)
						.setPositiveButton(R.string.settings_btn_title, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int whichButton) {
								Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
								context.startActivity(new Intent(intent));

							}
						}).setNegativeButton(R.string.cancel_btn_title, null).show();
			}
		});

	}
	

}
