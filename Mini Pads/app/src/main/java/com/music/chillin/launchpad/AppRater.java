package com.music.chillin.launchpad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codemybrainsout.ratingdialog.RatingDialog;

/**
 * Created by Andrews on 8/7/2017.
 */

public class AppRater {
    private final static String APP_TITLE = "App Name";// App Name
    private final static String APP_PNAME = "com.example.name";// Package Name

    private final static int DAYS_UNTIL_PROMPT = 1;//Min number of days
    private static int LAUNCHES_UNTIL_PROMPT;//Min number of launches
    private static SharedPreferences prefs;

    public static void app_launched(Context mContext) {
         prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();
        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);


        LAUNCHES_UNTIL_PROMPT = prefs.getInt("times_opened",0);

        if(launch_count == 2){
            editor.putInt("times_opened",LAUNCHES_UNTIL_PROMPT + 5);
            showRateDialog(mContext, editor);
        }
        // Get date of first launch
        // Wait at least n days before opening
        if (launch_count == LAUNCHES_UNTIL_PROMPT) {
                showRateDialog(mContext, editor);
            editor.putInt("times_opened",LAUNCHES_UNTIL_PROMPT + 5);
        }


        editor.commit();
    }

    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {


        final RatingDialog ratingDialog = new RatingDialog.Builder(mContext)
                .title("Why Waiting Bruh !"+"\nPlease take a moment to rate MiniPads ! Really Motivates Me")
                .titleTextColor(R.color.black)
                .positiveButtonText("Not Now")
                .negativeButtonText("Dont show me Again")
                .icon(mContext.getResources().getDrawable(R.drawable.minipads_icaon_00000))
                .positiveButtonTextColor(R.color.grey_500)
                .negativeButtonTextColor(R.color.black)
                .ratingBarColor(R.color.red)
                .playstoreUrl("http://play.google.com/store/apps/details?id=" +mContext.getPackageName())
                .onThresholdCleared(new RatingDialog.Builder.RatingThresholdClearedListener() {
                    @Override
                    public void onThresholdCleared(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {

                        if(rating > 4.0f){
                            try {
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("market://details?id=" +mContext.getPackageName())));
                            } catch (android.content.ActivityNotFoundException e) {
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://play.google.com/store/apps/details?id=" +mContext.getPackageName())));
                            }
                            if (editor != null) {
                                editor.putBoolean("dontshowagain",true);
                            }
                        }else{
                            Toast.makeText(mContext,"Thanks for your Review",Toast.LENGTH_SHORT).show();
                            if (editor != null) {
                                editor.putBoolean("dontshowagain",true);
                            }
                            ratingDialog.dismiss();

                        }


                    }
                }).onThresholdFailed(new RatingDialog.Builder.RatingThresholdFailedListener() {
                    @Override
                    public void onThresholdFailed(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                        if(!thresholdCleared){

                        }
                        ratingDialog.dismiss();
                    }
                })
                .build();
        ratingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {


            }
        });
        ratingDialog.setCanceledOnTouchOutside(false);
        ratingDialog.show();

    }
}