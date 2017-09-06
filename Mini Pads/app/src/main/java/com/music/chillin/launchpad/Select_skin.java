package com.music.chillin.launchpad;


import android.animation.Animator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;



/**
 * A simple {@link Fragment} subclass.
 */
public class Select_skin extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {

    public RelativeLayout skin_contaner;
    public ImageView skin_logo, next_skin, previous_skin;
    public TextView skin_name;
    public int firstTime = 0;
    public int skin = 1;
    String songname;
    String tutorial;

    int animationduration = 100;
    String Skinname1 = "Basic", Skinname2 = "Dark Knight", Skinname3 = "Galaxy", Skinname4 = "White", Skinname5 = "Spider Pad", Skinname6 = "Snow Lite";
    ImageView blurimage;
    File rootPath;
    private KProgressHUD hud;
    private ImageView download_skin_icon;
    private TextView skintiel;
    private InterstitialAd interstitialAd_launchpad;
    private TextView skinsongngame;
    private String songname_title;
    private AdView adView_banner;


    public Select_skin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_select_skin, container, false);

        final Startup_Menu showAd = (Startup_Menu)getActivity();
        rootPath = new File(getActivity().getFilesDir().getAbsolutePath(), "/MiniPads/skins/");

        interstitialAd_launchpad = new InterstitialAd(getContext());
        interstitialAd_launchpad.setAdUnitId(getString(R.string.intertiatial_between));

        songname = getArguments().getString("songname");
        tutorial = getArguments().getString("tutorial");
        songname_title = getArguments().getString("songname_title");
        skin_logo = (ImageView) v.findViewById(R.id.skin_logo);
        download_skin_icon = (ImageView) v.findViewById(R.id.download_icon_skin);
        next_skin = (ImageView) v.findViewById(R.id.left_arrow);
        previous_skin = (ImageView) v.findViewById(R.id.right_arrow);
        skin_name = (TextView) v.findViewById(R.id.skin_name);
        skintiel = (TextView) v.findViewById(R.id.select_skintitle);
        skinsongngame = (TextView) v.findViewById(R.id.song_name_select_skin);


        skin_name.setTypeface(getFont("Roboto-Thin.ttf"));
        skintiel.setTypeface(getFont("Roboto-Thin.ttf"));
         skinsongngame.setTypeface(getFont("Roboto-Thin.ttf"));


       // skin_name.setTypeface(Typer.set(getActivity()).getFont(Font.ROBOTO_THIN));
       // skintiel.setTypeface(Typer.set(getActivity()).getFont(Font.ROBOTO_THIN));
       // skinsongngame.setTypeface(Typer.set(getActivity()).getFont(Font.ROBOTO_THIN));
        //Blurry.with(getActivity()).radius(25).sampling(2).onto((ViewGroup) blur);
        adView_banner = (AdView)v.findViewById(R.id.adView_startup_select_skin);
        adView_banner.loadAd(new AdRequest.Builder().build());
        skinsongngame.setText(songname_title);
        // getActivity().onBackPressed();
        skin_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skin == 1) {
                    //showAd.showIntertitial = true;
                    startActivity();
                } else if (skin == 2) {
                    //showAd.showIntertitial = true;
                    startActivity();

                } else if (skin == 3) {
                    File rootPath = new File(getActivity().getFilesDir().getAbsolutePath(), "/MiniPads/skins/skin3.png");
                    if (!rootPath.exists()) {
                        if (ConnectivityReceiver.isConnected()) {
                            downloadTask("skin3");
                        } else {
                            Toast.makeText(getActivity(), "check your connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                       // showAd.showIntertitial = true;
                        startActivity();

                    }


                } else if (skin == 4) {
                    File rootPath = new File(getActivity().getFilesDir().getAbsolutePath(), "/MiniPads/skins/skin4.png");
                    if (!rootPath.exists()) {
                        if (ConnectivityReceiver.isConnected()) {
                            downloadTask("skin4");
                        } else {
                            Toast.makeText(getActivity(), "check your connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //showAd.showIntertitial = true;
                        startActivity();

                    }
                } else if (skin == 5) {
                    File rootPath = new File(getActivity().getFilesDir().getAbsolutePath(), "/MiniPads/skins/skin5.png");
                    if (!rootPath.exists()) {
                        if (ConnectivityReceiver.isConnected()) {
                            downloadTask("skin5");
                        } else {
                            Toast.makeText(getActivity(), "check your connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //showAd.showIntertitial = true;
                        startActivity();
                    }
                } else if (skin == 6) {
                    File rootPath = new File(getActivity().getFilesDir().getAbsolutePath(), "/MiniPads/skins/skin6.png");
                    if (!rootPath.exists()) {
                        if (ConnectivityReceiver.isConnected()) {
                            downloadTask("skin6");
                        } else {
                            Toast.makeText(getActivity(), "check your connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //showAd.showIntertitial = true;
                        startActivity();
                    }
                }

            }
        });

        download_skin_icon.setVisibility(View.INVISIBLE);
        next_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skin == 1) {

                    YoYo.with(Techniques.FadeOutLeft).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_logo.setImageResource(R.drawable.skin2_00000);
                            YoYo.with(Techniques.FadeInRight).duration(animationduration).repeat(1).playOn(skin_logo);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_logo);
                    YoYo.with(Techniques.FadeOut).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_name.setText(Skinname2);
                            YoYo.with(Techniques.FadeIn).duration(animationduration).repeat(1).playOn(skin_name);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_name);

///////////////////////////////////////////////////////////////////////////
                    skin = 2;

                } else if (skin == 2) {
                    YoYo.with(Techniques.FadeOutLeft).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            YoYo.with(Techniques.FadeOutLeft).duration(animationduration).repeat(1).playOn(download_skin_icon);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_logo.setImageResource(R.drawable.skin3_00000);
                            if(!isSkinAvalable("skin3")){
                                download_skin_icon.setVisibility(View.VISIBLE);
                            }else {
                                download_skin_icon.setVisibility(View.INVISIBLE);
                            }
                            YoYo.with(Techniques.FadeInRight).duration(animationduration).repeat(1).playOn(download_skin_icon);
                            YoYo.with(Techniques.FadeInRight).duration(animationduration).repeat(1).playOn(skin_logo);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_logo);

                    YoYo.with(Techniques.FadeOut).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_name.setText(Skinname3);
                            YoYo.with(Techniques.FadeIn).duration(animationduration).repeat(1).playOn(skin_name);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_name);

                    skin = 3;

                } else if (skin == 3) {
                    YoYo.with(Techniques.FadeOutLeft).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            YoYo.with(Techniques.FadeOutLeft).duration(animationduration).repeat(1).playOn(download_skin_icon);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_logo.setImageResource(R.drawable.skin4_00000_00000);
                            if(!isSkinAvalable("skin4")){
                                download_skin_icon.setVisibility(View.VISIBLE);
                            }else {
                                download_skin_icon.setVisibility(View.INVISIBLE);
                            }
                            YoYo.with(Techniques.FadeInRight).duration(animationduration).repeat(1).playOn(download_skin_icon);
                            YoYo.with(Techniques.FadeInRight).duration(animationduration).repeat(1).playOn(skin_logo);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_logo);

                    YoYo.with(Techniques.FadeOut).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_name.setText(Skinname4);

                            YoYo.with(Techniques.FadeIn).duration(animationduration).repeat(1).playOn(skin_name);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_name);

                    skin = 4;

                } else if (skin == 4) {
                    YoYo.with(Techniques.FadeOutLeft).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            YoYo.with(Techniques.FadeOutLeft).duration(animationduration).repeat(1).playOn(download_skin_icon);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_logo.setImageResource(R.drawable.skin5_00000);
                            if(!isSkinAvalable("skin5")){
                                download_skin_icon.setVisibility(View.VISIBLE);
                            }else {
                                download_skin_icon.setVisibility(View.INVISIBLE);
                            }
                            YoYo.with(Techniques.FadeInRight).duration(animationduration).repeat(1).playOn(download_skin_icon);
                            YoYo.with(Techniques.FadeInRight).duration(animationduration).repeat(1).playOn(skin_logo);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_logo);

                    YoYo.with(Techniques.FadeOut).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_name.setText(Skinname5);
                            YoYo.with(Techniques.FadeIn).duration(animationduration).repeat(1).playOn(skin_name);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_name);

                    skin = 5;

                } else if (skin == 5) {
                    YoYo.with(Techniques.FadeOutLeft).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            YoYo.with(Techniques.FadeOutLeft).duration(animationduration).repeat(1).playOn(download_skin_icon);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_logo.setImageResource(R.drawable.skin6_00000);
                            if(!isSkinAvalable("skin6")){
                                download_skin_icon.setVisibility(View.VISIBLE);
                            }else {
                                download_skin_icon.setVisibility(View.INVISIBLE);
                            }
                            YoYo.with(Techniques.FadeInRight).duration(animationduration).repeat(1).playOn(download_skin_icon);
                            YoYo.with(Techniques.FadeInRight).duration(animationduration).repeat(1).playOn(skin_logo);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_logo);

                    YoYo.with(Techniques.FadeOut).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_name.setText(Skinname6);

                            YoYo.with(Techniques.FadeIn).duration(animationduration).repeat(1).playOn(skin_name);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_name);

                    skin = 6;

                }

            }
        });


        previous_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (skin == 6) {
                    YoYo.with(Techniques.FadeOutRight).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            YoYo.with(Techniques.FadeOutRight).duration(animationduration).repeat(1).playOn(download_skin_icon);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_logo.setImageResource(R.drawable.skin5_00000);
                            if(!isSkinAvalable("skin5")){
                                download_skin_icon.setVisibility(View.VISIBLE);
                            }else {
                                download_skin_icon.setVisibility(View.INVISIBLE);
                            }
                            YoYo.with(Techniques.FadeInLeft).duration(animationduration).repeat(1).playOn(download_skin_icon);
                            YoYo.with(Techniques.FadeInLeft).duration(animationduration).repeat(1).playOn(skin_logo);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_logo);


                    YoYo.with(Techniques.FadeOut).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_name.setText(Skinname5);
                            YoYo.with(Techniques.FadeIn).duration(animationduration).repeat(1).playOn(skin_name);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_name);

                    skin = 5;

                    /////////////////////////////////////////////////////////////////////////////////////////////


                } else if (skin == 5) {
                    YoYo.with(Techniques.FadeOutRight).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            YoYo.with(Techniques.FadeOutRight).duration(animationduration).repeat(1).playOn(download_skin_icon);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_logo.setImageResource(R.drawable.skin4_00000_00000);
                            if(!isSkinAvalable("skin4")){
                                download_skin_icon.setVisibility(View.VISIBLE);
                            }else {
                                download_skin_icon.setVisibility(View.INVISIBLE);
                            }
                            YoYo.with(Techniques.FadeInLeft).duration(animationduration).repeat(1).playOn(download_skin_icon);
                            YoYo.with(Techniques.FadeInLeft).duration(animationduration).repeat(1).playOn(skin_logo);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_logo);


                    YoYo.with(Techniques.FadeOut).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_name.setText(Skinname4);
                            YoYo.with(Techniques.FadeIn).duration(animationduration).repeat(1).playOn(skin_name);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_name);

                    skin = 4;

                    /////////////////////////////////////////////////////////////////////////////////////////////


                } else if (skin == 4) {
                    YoYo.with(Techniques.FadeOutRight).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            YoYo.with(Techniques.FadeOutRight).duration(animationduration).repeat(1).playOn(download_skin_icon);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_logo.setImageResource(R.drawable.skin3_00000);
                            if(!isSkinAvalable("skin3")){
                                download_skin_icon.setVisibility(View.VISIBLE);
                            }else {
                                download_skin_icon.setVisibility(View.INVISIBLE);
                            }
                            YoYo.with(Techniques.FadeInLeft).duration(animationduration).repeat(1).playOn(download_skin_icon);
                            YoYo.with(Techniques.FadeInLeft).duration(animationduration).repeat(1).playOn(skin_logo);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_logo);


                    YoYo.with(Techniques.FadeOut).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_name.setText(Skinname3);
                            YoYo.with(Techniques.FadeIn).duration(animationduration).repeat(1).playOn(skin_name);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_name);

                    skin = 3;

                    /////////////////////////////////////////////////////////////////////////////////////////////


                } else if (skin == 3) {
                    YoYo.with(Techniques.FadeOutRight).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            YoYo.with(Techniques.FadeOutRight).duration(animationduration).repeat(1).playOn(download_skin_icon);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_logo.setImageResource(R.drawable.skin2_00000);
                            download_skin_icon.setVisibility(View.INVISIBLE);
                            YoYo.with(Techniques.FadeInLeft).duration(animationduration).repeat(1).playOn(skin_logo);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_logo);


                    YoYo.with(Techniques.FadeOut).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_name.setText(Skinname2);
                            download_skin_icon.setVisibility(View.INVISIBLE);
                            YoYo.with(Techniques.FadeIn).duration(animationduration).repeat(1).playOn(skin_name);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_name);

                    skin = 2;

                    /////////////////////////////////////////////////////////////////////////////////////////////


                } else if (skin == 2) {
                    YoYo.with(Techniques.FadeOutRight).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            YoYo.with(Techniques.FadeOutRight).duration(animationduration).repeat(1).playOn(download_skin_icon);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_logo.setImageResource(R.drawable.skin1_00000);
                            download_skin_icon.setVisibility(View.INVISIBLE);
                            YoYo.with(Techniques.FadeInLeft).duration(animationduration).repeat(1).playOn(skin_logo);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_logo);

                    YoYo.with(Techniques.FadeOut).duration(animationduration).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            skin_name.setText(Skinname1);
                            download_skin_icon.setVisibility(View.INVISIBLE);
                            YoYo.with(Techniques.FadeIn).duration(animationduration).repeat(1).playOn(skin_name);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).repeat(1).playOn(skin_name);
                    skin = 1;

                }

            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    public void downloadTask(String child_url) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://launchpad-93445.appspot.com/");
        final StorageReference islandRef = storageRef.child(child_url + ".png");
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setLabel("Getting Skin")
                .setMaxProgress(100)
                .setCancellable(false)
                .show();
        final File localFile = new File(rootPath, child_url + ".png");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getActivity(), "Skin Downloaded", Toast.LENGTH_LONG).show();
                if(download_skin_icon.getVisibility() == View.VISIBLE)download_skin_icon.setVisibility(View.INVISIBLE);
                startActivity();
                //  updateDb(timestamp,localFile.toString(),position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("download_error",exception.getMessage());
                if(hud.isShowing()){
                    hud.dismiss();
                }

                Toast.makeText(getActivity(), "Skin not Downloaded", Toast.LENGTH_LONG).show();
            }
        })
                .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        hud.setMaxProgress(100);
                        @SuppressWarnings("VisibleForTests") Long longi = taskSnapshot.getBytesTransferred();
                        @SuppressWarnings("VisibleForTests") Long longj = taskSnapshot.getTotalByteCount();
                        double progress = 100 * longi / longj;
                        hud.setProgress((int) progress);
                    }
                });
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {


    }

    public boolean isSkinAvalable(String skin) {
        File rootPath = new File(getActivity().getFilesDir().getAbsolutePath(), "/MiniPads/skins/"+skin+".png");
        if (!rootPath.exists()) {
            return false;
            } else {
               return true;
            }

    }



    public void request_ad(){

        interstitialAd_launchpad.loadAd(new AdRequest.Builder().addTestDevice("E4CB5D6F83689B45B7F53BE5B6C4E6DA").build());

    }

    public Typeface getFont(String font) {

        Typeface myFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + font);

        return myFont;
    }


    public void startActivity(){

        Intent intent = new Intent(getActivity(), MainLaunchPad.class);
        intent.putExtra("skin", skin);
        intent.putExtra("songname", songname);
        intent.putExtra("tutorial", tutorial);
        startActivity(intent);

    }
}