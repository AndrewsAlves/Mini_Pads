package com.music.chillin.launchpad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class MainLaunchPad extends AppCompatActivity {

    public CardView r11, r12, r13, r14, r15, r16, r21, r22, r23, r24, r25, r26, r31, r32, r33, r34, r35, r36, r41, r42, r43, r44, r45, r46, r51, r52, r53, r54, r55, r56, r61, r62, r63, r64, r65, r66;
    public ImageView ar11, ar12, ar13, ar14, ar15, ar16, ar21, ar22, ar23, ar24, ar25, ar26, ar31, ar32, ar33, ar34, ar35, ar36, ar41, ar42, ar43, ar44, ar45, ar46, ar51, ar52, ar53, ar54, ar55, ar56, ar61, ar62, ar63, ar64, ar65, ar66;
    public int red, blue, yellow, green, orange, purple, skyblue, default_white,visualizer;
    public GifImageView gif;
    public ImageView Looplogo1,Looplogo2,Looplogo3,Looplogo4,Looplogo5,Looplogo6,howtoplay;
    public ImageButton back;
    public SoundPool spmain;
    String Songname;
    public String song_path;
    public String pt[] = new String[37];
    public int LEDspeed[] = new int[37];
    public int colorid[] = new int[37];
    public int sustain[] = new int [37];
    public int LEDtype[] = new int[37];
    public int SP[] = new int[37];
    private int[] LEDcolor = new int[37];
    public String pathID[] = new String[37];
    public int  stop[] = new int[37];
    public int j[] = new int[223];
    private ProgressDialog mProgressDialog;
    private TextView title,kitname_name;
    private int i;
    boolean isPlaying;
    Animation zoomin,zoomout;
    private KProgressHUD hud;
    private String tutorial;


    /** ANIMATION VARIABLES **/

    public int MainAnim[][][] = new int[pt.length][200][pt.length];
    public int MainAnim_LED_size[][] = new int[pt.length][200];
    public int MainAnim_LED_patternSize[] = new int[pt.length];
    private ImageView setSkin;
    private File rootpath_skin;
    private InterstitialAd interstitialAd;
    private int samples_lenth;
    private boolean adPause = false;
    private AdView adView_banner_launchpad;
    private boolean multicol = true;
    private int padColor[][] = new int[11][37];
    private int[] padColorAssign = new int[37];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        int skin = bundle.getInt("skin");
        Songname = bundle.getString("songname");
        tutorial = bundle.getString("tutorial");

        if (skin == 1) {
            setContentView(R.layout.skin_1);
            default_white = ContextCompat.getColor(this,R.color.white);
        } else if (skin == 2) {
            setContentView(R.layout.skin_2);
            default_white = ContextCompat.getColor(this,R.color.white);
        } else if (skin == 3) {
            rootpath_skin = new File(getFilesDir().getAbsolutePath(), "/MiniPads/skins/skin3.png");
            setContentView(R.layout.skin_3);
            setSkin = (ImageView) findViewById(R.id.set_skin);
            Bitmap myBitmap = BitmapFactory.decodeFile(rootpath_skin.getAbsolutePath());
            setSkin.setImageBitmap(myBitmap);
            default_white = ContextCompat.getColor(this,R.color.black);
        } else if (skin == 4) {
            rootpath_skin = new File(getFilesDir().getAbsolutePath(), "/MiniPads/skins/skin4.png");
            setContentView(R.layout.skin_4);
            setSkin = (ImageView) findViewById(R.id.set_skin);
            Bitmap myBitmap = BitmapFactory.decodeFile(rootpath_skin.getAbsolutePath());
            setSkin.setImageBitmap(myBitmap);
            default_white = ContextCompat.getColor(this,R.color.white);
        } else if (skin == 5) {
            rootpath_skin = new File(getFilesDir().getAbsolutePath(), "/MiniPads/skins/skin5.png");
            setContentView(R.layout.skin_1);
            setSkin = (ImageView) findViewById(R.id.set_skin);
            Bitmap myBitmap = BitmapFactory.decodeFile(rootpath_skin.getAbsolutePath());
            setSkin.setImageBitmap(myBitmap);
            default_white = ContextCompat.getColor(this,R.color.white);
        } else if (skin == 6) {
            rootpath_skin = new File(getFilesDir().getAbsolutePath(), "/MiniPads/skins/skin6.png");
            setContentView(R.layout.skin_1);
            setSkin = (ImageView) findViewById(R.id.set_skin);
            Bitmap myBitmap = BitmapFactory.decodeFile(rootpath_skin.getAbsolutePath());
            setSkin.setImageBitmap(myBitmap);
            default_white = ContextCompat.getColor(this,R.color.white);
        }
        adPause = false;
        back = (ImageButton)findViewById(R.id.back_pad);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.intertiatial_between));

        r11 = (CardView) findViewById(R.id.r1_1);
        r12 = (CardView) findViewById(R.id.r1_2);
        r13 = (CardView) findViewById(R.id.r1_3);
        r14 = (CardView) findViewById(R.id.r1_4);
        r15 = (CardView) findViewById(R.id.r1_5);
        r16 = (CardView) findViewById(R.id.r1_6);
        r21 = (CardView) findViewById(R.id.r2_1);
        r22 = (CardView) findViewById(R.id.r2_2);
        r23 = (CardView) findViewById(R.id.r2_3);
        r24 = (CardView) findViewById(R.id.r2_4);
        r25 = (CardView) findViewById(R.id.r2_5);
        r26 = (CardView) findViewById(R.id.r2_6);
        r31 = (CardView) findViewById(R.id.r3_1);
        r32 = (CardView) findViewById(R.id.r3_2);
        r33 = (CardView) findViewById(R.id.r3_3);
        r34 = (CardView) findViewById(R.id.r3_4);
        r35 = (CardView) findViewById(R.id.r3_5);
        r36 = (CardView) findViewById(R.id.r3_6);
        r41 = (CardView) findViewById(R.id.r4_1);
        r42 = (CardView) findViewById(R.id.r4_2);
        r43 = (CardView) findViewById(R.id.r4_3);
        r44 = (CardView) findViewById(R.id.r4_4);
        r45 = (CardView) findViewById(R.id.r4_5);
        r46 = (CardView) findViewById(R.id.r4_6);
        r51 = (CardView) findViewById(R.id.r5_1);
        r52 = (CardView) findViewById(R.id.r5_2);
        r53 = (CardView) findViewById(R.id.r5_3);
        r54 = (CardView) findViewById(R.id.r5_4);
        r55 = (CardView) findViewById(R.id.r5_5);
        r56 = (CardView) findViewById(R.id.r5_6);
        r61 = (CardView) findViewById(R.id.r6_1);
        r62 = (CardView) findViewById(R.id.r6_2);
        r63 = (CardView) findViewById(R.id.r6_3);
        r64 = (CardView) findViewById(R.id.r6_4);
        r65 = (CardView) findViewById(R.id.r6_5);
        r66 = (CardView) findViewById(R.id.r6_6);

        ar11 = (ImageView) findViewById(R.id.ar1_1);
        ar12 = (ImageView) findViewById(R.id.ar1_2);
        ar13 = (ImageView) findViewById(R.id.ar1_3);
        ar14 = (ImageView) findViewById(R.id.ar1_4);
        ar15 = (ImageView) findViewById(R.id.ar1_5);
        ar16 = (ImageView) findViewById(R.id.ar1_6);
        ar21 = (ImageView) findViewById(R.id.ar2_1);
        ar22 = (ImageView) findViewById(R.id.ar2_2);
        ar23 = (ImageView) findViewById(R.id.ar2_3);
        ar24 = (ImageView) findViewById(R.id.ar2_4);
        ar25 = (ImageView) findViewById(R.id.ar2_5);
        ar26 = (ImageView) findViewById(R.id.ar2_6);
        ar31 = (ImageView) findViewById(R.id.ar3_1);
        ar32 = (ImageView) findViewById(R.id.ar3_2);
        ar33 = (ImageView) findViewById(R.id.ar3_3);
        ar34 = (ImageView) findViewById(R.id.ar3_4);
        ar35 = (ImageView) findViewById(R.id.ar3_5);
        ar36 = (ImageView) findViewById(R.id.ar3_6);
        ar41 = (ImageView) findViewById(R.id.ar4_1);
        ar42 = (ImageView) findViewById(R.id.ar4_2);
        ar43 = (ImageView) findViewById(R.id.ar4_3);
        ar44 = (ImageView) findViewById(R.id.ar4_4);
        ar45 = (ImageView) findViewById(R.id.ar4_5);
        ar46 = (ImageView) findViewById(R.id.ar4_6);
        ar51 = (ImageView) findViewById(R.id.ar5_1);
        ar52 = (ImageView) findViewById(R.id.ar5_2);
        ar53 = (ImageView) findViewById(R.id.ar5_3);
        ar54 = (ImageView) findViewById(R.id.ar5_4);
        ar55 = (ImageView) findViewById(R.id.ar5_5);
        ar56 = (ImageView) findViewById(R.id.ar5_6);
        ar61 = (ImageView) findViewById(R.id.ar6_1);
        ar62 = (ImageView) findViewById(R.id.ar6_2);
        ar63 = (ImageView) findViewById(R.id.ar6_3);
        ar64 = (ImageView) findViewById(R.id.ar6_4);
        ar65 = (ImageView) findViewById(R.id.ar6_5);
        ar66 = (ImageView) findViewById(R.id.ar6_6);
        howtoplay = (ImageView) findViewById(R.id.howtoplay);
        Looplogo1 = (ImageView)findViewById(R.id.loop_logo_r1_1) ;
        Looplogo2 = (ImageView)findViewById(R.id.loop_logo_r1_2) ;
        Looplogo3 = (ImageView)findViewById(R.id.loop_logo_r1_3) ;
        Looplogo4 = (ImageView)findViewById(R.id.loop_logo_r1_4) ;
        Looplogo5 = (ImageView)findViewById(R.id.loop_logo_r1_5) ;
        Looplogo6 = (ImageView)findViewById(R.id.loop_logo_r1_6) ;
        kitname_name = (TextView) findViewById(R.id.Kitname_name);



        kitname_name.setTypeface(getFont("Roboto-Thin.ttf"));
        kitname_name.setText(Songname);


        red = ContextCompat.getColor(this,R.color.red);
        yellow = ContextCompat.getColor(this,R.color.yellow);
        orange =ContextCompat.getColor(this,R.color.orange);
        purple = ContextCompat.getColor(this,R.color.purple);
        skyblue = ContextCompat.getColor(this,R.color.skyblue);
        blue = ContextCompat.getColor(this,R.color.blue);
        green = ContextCompat.getColor(this,R.color.green);
        gif = (GifImageView) findViewById(R.id.gif_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            spmain = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .setMaxStreams(12)
                    .build();
        } else {
            spmain = new SoundPool(12, 3,0);
        }

        zoomin = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        zoomout = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out);

        song_path =  getFilesDir().getAbsolutePath()+ "/MiniPads/"+Songname+"/"+"pad";
        configInitialPack();
        setPadColors();
        loadAudio loadSoundPool = new loadAudio();
        loadSoundPool.execute();
        //show_rating();

            howtoplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            if(tutorial.equals("CS")){
                                Toast.makeText(MainLaunchPad.this,"Tutorial Coming Soon",Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(tutorial)));
                            }
                }
            });

        r11.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 1 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                   // animateButtonsZoomOut(v);
                    if(getInt(num) != 0) {
                        r11.setSelected(!r11.isSelected());
                        if (r11.isSelected()) {
                            setLoopStop(2, 3, 4, 5, 6, r12, r13, r14, r15, r16, r11);
                            stop[num] = spmain.play(SP[num], 1.0f, 1.0f, 0, -1, 1.0f);
                            animatePad(r11);

                        } else {
                            spmain.stop(stop[num]);
                            stopAnimationPad(r11);

                        }
                        if (LEDtype[num] == 1) {
                            animPad1(num, LEDspeed[num]);
                        } else if (LEDtype[num] == 2) {
                            animPad2(num, LEDspeed[num]);
                        }else if(LEDtype[num] == 3) {
                            animPad3(num, LEDspeed[num]);
                        }
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                  //  animateButtonsZoomIn(v);
                    return true;
                }
                return false;
            }
        });
        r12.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 2 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //animateButtonsZoomOut(v);
                    if(getInt(num) != 0) {
                        r12.setSelected(!r12.isSelected());
                        if (r12.isSelected()) {
                            setLoopStop(1, 3, 4, 5, 6, r11, r13, r14, r15, r16, r12);

                            stop[num] = spmain.play(SP[2], 1.0f, 1.0f, 0, -1, 1.0f);
                            animatePad(r12);
                        } else {
                            spmain.stop(stop[num]);
                            stopAnimationPad(r12);
                        }
                        if (LEDtype[num] == 1) {
                            animPad1(num, LEDspeed[num]);
                        } else if (LEDtype[num] == 2) {
                            animPad2(num, LEDspeed[num]);
                        }else if(LEDtype[num] == 3) {
                            animPad3(num, LEDspeed[num]);
                        }
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //animateButtonsZoomIn(v);
                    return true;
                }



                return false;
            }
        });
        r13.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 3 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //animateButtonsZoomOut(v);
                    if(getInt(num) != 0) {
                        r13.setSelected(!r13.isSelected());
                        if (r13.isSelected()) {
                            setLoopStop(1, 2, 4, 5, 6, r11, r12, r14, r15, r16, r13);
                            stop[num] = spmain.play(SP[num], 1.0f, 1.0f, 0, -1, 1.0f);
                            animatePad(r13);
                        } else {
                            spmain.stop(stop[num]);
                            stopAnimationPad(r13);
                        }
                        if (LEDtype[num] == 1) {
                            animPad1(num, LEDspeed[num]);
                        } else if (LEDtype[num] == 2) {
                            animPad2(num, LEDspeed[num]);
                        }else if(LEDtype[num] == 3) {
                            animPad3(num, LEDspeed[num]);
                        }
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //animateButtonsZoomIn(v);
                    return true;
                }



                return false;
            }
        });
        r14.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 4 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //animateButtonsZoomOut(v);
                    if(getInt(num) != 0) {
                        r14.setSelected(!r14.isSelected());
                        if (r14.isSelected()) {
                            setLoopStop(1, 2, 3, 5, 6, r11, r12, r13, r15, r16, r14);

                            stop[num] = spmain.play(SP[num], 1.0f, 1.0f, 0, -1, 1.0f);
                            animatePad(r14);
                        } else {
                            spmain.stop(stop[num]);
                            stopAnimationPad(r14);
                        }
                        if (LEDtype[num] == 1) {
                            animPad1(num, LEDspeed[num]);
                        } else if (LEDtype[num] == 2) {
                            animPad2(num, LEDspeed[num]);
                        }else if(LEDtype[num] == 3) {
                            animPad3(num, LEDspeed[num]);
                        }
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //animateButtonsZoomIn(v);
                    return true;
                }



                return false;
            }
        });
        r15.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 5 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //animateButtonsZoomOut(v);
                    if(getInt(num) != 0) {
                        r15.setSelected(!r15.isSelected());
                        if (r15.isSelected()) {
                            setLoopStop(1, 2, 3, 4, 6, r11, r12, r13, r14, r16, r15);

                            stop[num] = spmain.play(SP[num], 1.0f, 1.0f, 0, -1, 1.0f);
                            animatePad(r15);
                        } else {
                            spmain.stop(stop[num]);
                            stopAnimationPad(r15);
                        }
                        if (LEDtype[num] == 1) {
                            animPad1(num, LEDspeed[num]);
                        } else if (LEDtype[num] == 2) {
                            animPad2(num, LEDspeed[num]);
                        }else if(LEDtype[num] == 3) {
                            animPad3(num, LEDspeed[num]);
                        }
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //animateButtonsZoomIn(v);
                    return true;
                }



                return false;
            }
        });
        r16.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 6 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //animateButtonsZoomOut(v);
                    if(getInt(num) != 0) {
                        r16.setSelected(!r16.isSelected());
                        if (r16.isSelected()) {
                            setLoopStop(1, 2, 3, 4, 5, r11, r12, r13, r14, r15, r16);
                            stop[num] = spmain.play(SP[6], 1.0f, 1.0f, 0, -1, 1.0f);
                            animatePad(r16);
                        } else {
                            spmain.stop(stop[num]);
                            stopAnimationPad(r16);
                        }
                        if (LEDtype[num] == 1) {
                            animPad1(num, LEDspeed[num]);
                        } else if (LEDtype[num] == 2) {
                            animPad2(num, LEDspeed[num]);
                        }else if(LEDtype[num] == 3) {
                            animPad3(num, LEDspeed[num]);
                        }
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //animateButtonsZoomIn(v);
                    return true;
                }

                return false;
            }
        });


        r21.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 7 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                       playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r22.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 8 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r23.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 9 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r24.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 10 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }
                return false;
            }
        });
        r25.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 11 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r26.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 12 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r31.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 13 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r32.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 14 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r33.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 15 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;

                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r34.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 16 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r35.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 17 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r36.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 18 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r41.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 19 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r42.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 20 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r43.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 21 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r44.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 22 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r45.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 23 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r46.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 24 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r51.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 25 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r52.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 26 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r53.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 27 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r54.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 28 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r55.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 29 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }


                return false;
            }
        });
        r56.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 30 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r61.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 31 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                        if(isPlaying){
                        spmain.stop(stop[num]);
                            playtile(num);
                        isPlaying = true;

                    }else if(!isPlaying){
                            playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r62.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 32 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }

                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r63.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 33 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r64.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 34 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r65.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 35 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }



                return false;
            }
        });
        r66.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 36 ;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }else if(LEDtype[num] == 3) {
                        animPad3(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }

                return false;
            }
        });


        /*View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int num = 1;
                switch(v.getId()){

                    case R.id.r1_1:
                        num = 1;
                        break;
                    case R.id.r1_2:
                        num = 2;
                        break;
                    case R.id.r1_3:
                        num = 3;
                        break;
                    case R.id.r1_4:
                        num = 4;
                        break;
                    case R.id.r1_5:
                        num = 5;
                        break;
                    case R.id.r1_6:
                        num = 6;
                        break;
                    case R.id.r2_1:
                        num = 7;
                        break;
                    case R.id.r2_2:
                        num = 8;
                        break;
                    case R.id.r2_3:
                        num = 9;
                        break;
                    case R.id.r2_4:
                        num = 10;
                        break;
                    case R.id.r2_5:
                        num = 11;
                        break;
                    case R.id.r2_6:
                        num = 12;
                        break;
                    case R.id.r3_1:
                        num = 13;
                        break;
                    case R.id.r3_2:
                        num = 14;
                        break;
                    case R.id.r3_3:
                        num = 15;
                        break;
                    case R.id.r3_4:
                        num = 16;
                        break;
                    case R.id.r3_5:
                        num = 17;
                        break;
                    case R.id.r3_6:
                        num = 18;
                        break;
                    case R.id.r4_1:
                        num = 19;
                        break;
                    case R.id.r4_2:
                        num = 20;
                        break;
                    case R.id.r4_3:
                        num = 21;
                        break;
                    case R.id.r4_4:
                        num = 22;
                        break;
                    case R.id.r4_5:
                        num = 23;
                        break;
                    case R.id.r4_6:
                        num = 24;
                        break;
                    case R.id.r5_1:
                        num = 25;
                        break;
                    case R.id.r5_2:
                        num = 26;
                        break;
                    case R.id.r5_3:
                        num = 27;
                        break;
                    case R.id.r5_4:
                        num = 28;
                        break;
                    case R.id.r5_5:
                        num = 29;
                        break;
                    case R.id.r5_6:
                        num = 30;
                        break;
                    case R.id.r6_1:
                        num = 31;
                        break;
                    case R.id.r6_2:
                        num = 32;
                        break;
                    case R.id.r6_3:
                        num = 33;
                        break;
                    case R.id.r6_4:
                        num = 34;
                        break;
                    case R.id.r6_5:
                        num = 35;
                        break;
                    case R.id.r6_6:
                        num = 36;
                        break;
                }

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    animateButtonsZoomOut(v);
                    if(isPlaying){
                        spmain.stop(stop[num]);
                        playtile(num);
                        isPlaying = true;
                    }else if(!isPlaying){
                        playtile(num);
                        isPlaying = true;
                    }
                    if(LEDtype[num] == 1){
                        animPad1(num,LEDspeed[num]);
                    }else if(LEDtype[num] == 2) {
                        animPad2(num, LEDspeed[num]);
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    animateButtonsZoomIn(v);
                    if(sustain[num] == 1)spmain.stop(stop[num]);
                    return true;
                }
                return false;
            }
        };


        r21.setOnTouchListener(touchListener);
        r22.setOnTouchListener(touchListener);
        r23.setOnTouchListener(touchListener);
        r24.setOnTouchListener(touchListener);
        r25.setOnTouchListener(touchListener);
        r26.setOnTouchListener(touchListener);

        r31.setOnTouchListener(touchListener);
        r32.setOnTouchListener(touchListener);
        r33.setOnTouchListener(touchListener);
        r34.setOnTouchListener(touchListener);
        r35.setOnTouchListener(touchListener);
        r36.setOnTouchListener(touchListener);

        r41.setOnTouchListener(touchListener);
        r42.setOnTouchListener(touchListener);
        r43.setOnTouchListener(touchListener);
        r44.setOnTouchListener(touchListener);
        r45.setOnTouchListener(touchListener);
        r46.setOnTouchListener(touchListener);

        r51.setOnTouchListener(touchListener);
        r52.setOnTouchListener(touchListener);
        r53.setOnTouchListener(touchListener);
        r54.setOnTouchListener(touchListener);
        r55.setOnTouchListener(touchListener);
        r56.setOnTouchListener(touchListener);

        r61.setOnTouchListener(touchListener);
        r62.setOnTouchListener(touchListener);
        r63.setOnTouchListener(touchListener);
        r64.setOnTouchListener(touchListener);
        r65.setOnTouchListener(touchListener);
        r66.setOnTouchListener(touchListener);*/


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spmain.release();
                adPause = true;
                finish();
            }
        });


    }

    private void show_rating() {

        Runnable showRatingRunnable = new Runnable() {
            @Override
            public void run() {
                AppRater.app_launched(MainLaunchPad.this);
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(showRatingRunnable,7000);

    }

    public void setPadColors(){

        initPadColors(1,ar11,colorid[1]);
        initPadColors(2,ar12,colorid[2]);
        initPadColors(3,ar13,colorid[3]);
        initPadColors(4,ar14,colorid[4]);
        initPadColors(5,ar15,colorid[5]);
        initPadColors(6,ar16,colorid[6]);
        initPadColors(7,ar21,colorid[7]);
        initPadColors(8,ar22,colorid[8]);
        initPadColors(9,ar23,colorid[9]);
        initPadColors(10,ar24,colorid[10]);
        initPadColors(11,ar25,colorid[11]);
        initPadColors(12,ar26,colorid[12]);
        initPadColors(13,ar31,colorid[13]);
        initPadColors(14,ar32,colorid[14]);
        initPadColors(15,ar33,colorid[15]);
        initPadColors(16,ar34,colorid[16]);
        initPadColors(17,ar35,colorid[17]);
        initPadColors(18,ar36,colorid[18]);
        initPadColors(19,ar41,colorid[19]);
        initPadColors(20,ar42,colorid[20]);
        initPadColors(21,ar43,colorid[21]);
        initPadColors(22,ar44,colorid[22]);
        initPadColors(23,ar45,colorid[23]);
        initPadColors(24,ar46,colorid[24]);
        initPadColors(25,ar51,colorid[25]);
        initPadColors(26,ar52,colorid[26]);
        initPadColors(27,ar53,colorid[27]);
        initPadColors(28,ar54,colorid[28]);
        initPadColors(29,ar55,colorid[29]);
        initPadColors(30,ar56,colorid[30]);
        initPadColors(31,ar61,colorid[31]);
        initPadColors(32,ar62,colorid[32]);
        initPadColors(33,ar63,colorid[33]);
        initPadColors(34,ar64,colorid[34]);
        initPadColors(35,ar65,colorid[35]);
        initPadColors(36,ar66,colorid[36]);
        setLoopingLogo(1,Looplogo1);
        setLoopingLogo(2,Looplogo2);
        setLoopingLogo(3,Looplogo3);
        setLoopingLogo(4,Looplogo4);
        setLoopingLogo(5,Looplogo5);
        setLoopingLogo(6,Looplogo6);

       // for(i = 1;i<pt.length;i++){
        //  initPadColors(i,Cardviews[i],colorid[i]);
      //}

    }
        public void setLoopingLogo(int i , ImageView logo){
            if(getInt(i) != 0){
                logo.setVisibility(View.VISIBLE);
            }else{
                logo.setVisibility(View.INVISIBLE);
            }
        }


    public void initPadColors(int i,ImageView audioRecog,int color){

        try{
            if(getInt(i) != 0){
                if(color == 1){
                    audioRecog.setImageResource(R.drawable.audio_indicator_red_00000);
                }else if(color == 2){
                    audioRecog.setImageResource(R.drawable.audio_indicator_yellow_00000);
                }else if(color == 3){
                    audioRecog.setImageResource(R.drawable.audio_indicator_green_00000);
                }else if(color == 4){
                    audioRecog.setImageResource(R.drawable.audio_indicator_blue_00000);
                }else if(color == 5){
                    audioRecog.setImageResource(R.drawable.audio_indicator_skyblue_00000);
                }else if(color == 6){
                    audioRecog.setImageResource(R.drawable.audio_indicator_purple_00000);;
                }else if(color == 7){
                    audioRecog.setImageResource(R.drawable.audio_indicator_orange_00000_00000);;
                }

            }else if(getInt(i)==0){
                audioRecog.setVisibility(View.INVISIBLE);
            }
        }catch (NumberFormatException number){
            Toast.makeText(this,"Somethime Went wrong",Toast.LENGTH_SHORT).show();
        }




    }

    public int getInt(int k){
        int parsed = 1;
        try{
            parsed =  Integer.parseInt(pt[k]);
        }catch (NumberFormatException number){
            Toast.makeText(this,"Somethime Went wrong",Toast.LENGTH_SHORT).show();
        }

        return parsed;
    }

    public void animateButtonsZoomIn(View v){
        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in));
    }
    public void animateButtonsZoomOut(View v){
        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out));
    }


    public void stopAnimationPad(CardView view) {
        view.setCardBackgroundColor(default_white);
        view.clearAnimation();
    }
    public void animatePad(CardView view) {
        Animation anim = new AlphaAnimation(1.0f, 0.5f);
        anim.setDuration(300);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(-1);
        anim.setRepeatMode(2);
        view.startAnimation(anim);
    }

    public void playtile(int play) {


        if(getInt(play) != 0){
               stop[play] = spmain.play(SP[play],1.0f,1.0f,0,0,1f);
            }

        }



    public void visuals(int raw_vis, GifImageView gifa) {
        final GifDrawable gifFromResource;
        try {
            gifFromResource = new GifDrawable(getResources(), raw_vis);
            gifa.setImageDrawable(gifFromResource);
            gifFromResource.setLoopCount(1);
            gifFromResource.start();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void stopPlayingPads(){
        spmain.stop(stop[1]);
        stopAnimationPad(r11);
        spmain.stop(stop[2]);
        stopAnimationPad(r12);
        spmain.stop(stop[3]);
        stopAnimationPad(r13);
        spmain.stop(stop[4]);
        stopAnimationPad(r14);
        spmain.stop(stop[5]);
        stopAnimationPad(r15);
        spmain.stop(stop[6]);
        stopAnimationPad(r16);
    }



    @Override
    public void onBackPressed() {
        spmain.release();
        adPause = true;

        finish();


    }

    public void setLoopStop(int one, int two, int three, int four, int five, CardView cardView1, CardView cardView2, CardView cardView3, CardView cardView4, CardView cardView5, CardView cardView6){
        spmain.stop(stop[one]);
        spmain.stop(stop[two]);
        spmain.stop(stop[three]);
        spmain.stop(stop[four]);
        spmain.stop(stop[five]);
        cardView1.setSelected(false);
        cardView2.setSelected(false);
        cardView3.setSelected(false);
        cardView4.setSelected(false);
        cardView5.setSelected(false);
        stopAnimationPad(cardView1);
        stopAnimationPad(cardView2);
        stopAnimationPad(cardView3);
        stopAnimationPad(cardView4);
        stopAnimationPad(cardView5);

    }



    public class loadAudio extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

            spmain.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    publishProgress(sampleId);
                   if(sampleId >= samples_lenth){
                       stop_progress();
                   }

                }
            });
            for (i = 1; i < pt.length; i++) {
                //int i2 = -1;
                //String str = "";
                // int group = -1;
                if(Integer.parseInt(pt[i]) != 0){
                    SP[i] = spmain.load(pathID[i],1);


                }
                Log.e("assigned path = ", pt[i]);
                // str = colors.getString("pad" + i);
                //group = groups.getInt("pad" + (i + 1));

            }


                // str = colors.getString("pad" + i);
                //group = groups.getInt("pad" + (i + 1));


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("Progress","Started");
             hud = KProgressHUD.create(MainLaunchPad.this)
                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                    .setLabel("Please wait")
                    .setMaxProgress(samples_lenth)
                     .setCancellable(false)
                    .show();
            request_ad();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            hud.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }

    public void stop_progress(){
        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
            //request_ad_startup();

            visuals(R.raw.wire,gif);


    }

    public void UImulticolor(final int cardview, final Integer color, Integer timer,int h){

        int n = 1;

        n = padColorAssign[h];

        switch (cardview) {
            case 1:
                LED_set(r11, timer, padColor[n][1]);
                break;
            case 2:
                LED_set(r12, timer, padColor[n][2]);
                break;
            case 3:
                LED_set(r13, timer, padColor[n][3]);
                break;
            case 4:
                LED_set(r14, timer, padColor[n][4]);
                break;
            case 5:
                LED_set(r15, timer, padColor[n][5]);
                break;
            case 6:
                LED_set(r16, timer, padColor[n][6]);
                break;
            case 7:
                LED_set(r21, timer, padColor[n][7]);
                break;
            case 8:
                LED_set(r22, timer, padColor[n][8]);
                break;
            case 9:
                LED_set(r23, timer, padColor[n][9]);
                break;
            case 10:
                LED_set(r24, timer, padColor[n][10]);
                break;
            case 11:
                LED_set(r25, timer, padColor[n][11]);
                break;
            case 12:
                LED_set(r26, timer, padColor[n][12]);
                break;
            case 13:
                LED_set(r31, timer, padColor[n][13]);
                break;
            case 14:
                LED_set(r32, timer, padColor[n][14]);
                break;
            case 15:
                LED_set(r33, timer, padColor[n][15]);
                break;
            case 16:
                LED_set(r34, timer, padColor[n][16]);
                break;
            case 17:
                LED_set(r35, timer, padColor[n][17]);
                break;
            case 18:
                LED_set(r36, timer, padColor[n][18]);
                break;
            case 19:
                LED_set(r41, timer, padColor[n][19]);
                break;
            case 20:
                LED_set(r42, timer, padColor[n][20]);
                break;
            case 21:
                LED_set(r43, timer, padColor[n][21]);
                break;
            case 22:
                LED_set(r44, timer, padColor[n][22]);
                break;
            case 23:
                LED_set(r45, timer, padColor[n][23]);
                break;
            case 24:
                LED_set(r46, timer, padColor[n][24]);
                break;
            case 25:
                LED_set(r51, timer, padColor[n][25]);
                break;
            case 26:
                LED_set(r52, timer, padColor[n][26]);
                break;
            case 27:
                LED_set(r53, timer, padColor[n][27]);
                break;
            case 28:
                LED_set(r54, timer, padColor[n][28]);
                break;
            case 29:
                LED_set(r55, timer, padColor[n][29]);
                break;
            case 30:
                LED_set(r56, timer, padColor[n][30]);
                break;
            case 31:
                LED_set(r61, timer, padColor[n][31]);
                break;
            case 32:
                LED_set(r62, timer, padColor[n][32]);
                break;
            case 33:
                LED_set(r63, timer, padColor[n][33]);
                break;
            case 34:
                LED_set(r64, timer, padColor[n][34]);
                break;
            case 35:
                LED_set(r65, timer, padColor[n][35]);
                break;
            case 36:
                LED_set(r66, timer, padColor[n][36]);
                break;
        }
    }


    public void UI(final int cardview, final Integer color, Integer timer) {
        int n = 1;

        n = 2;

        switch (cardview) {
            case 1:
                LED_set(r11, timer, color);
                break;
            case 2:
                LED_set(r12, timer, color);
                break;
            case 3:
                LED_set(r13, timer, color);
                break;
            case 4:
                LED_set(r14, timer, color);
                break;
            case 5:
                LED_set(r15, timer, color);
                break;
            case 6:
                LED_set(r16, timer, color);
                break;
            case 7:
                LED_set(r21, timer, color);
                break;
            case 8:
                LED_set(r22, timer, color);
                break;
            case 9:
                LED_set(r23, timer, color);
                break;
            case 10:
                LED_set(r24, timer, color);
                break;
            case 11:
                LED_set(r25, timer, color);
                break;
            case 12:
                LED_set(r26, timer, color);
                break;
            case 13:
                LED_set(r31, timer, color);
                break;
            case 14:
                LED_set(r32, timer, color);
                break;
            case 15:
                LED_set(r33, timer, color);
                break;
            case 16:
                LED_set(r34, timer, color);
                break;
            case 17:
                LED_set(r35, timer, color);
                break;
            case 18:
                LED_set(r36, timer, color);
                break;
            case 19:
                LED_set(r41, timer, color);
                break;
            case 20:
                LED_set(r42, timer, color);
                break;
            case 21:
                LED_set(r43, timer, color);
                break;
            case 22:
                LED_set(r44, timer, color);
                break;
            case 23:
                LED_set(r45, timer, color);
                break;
            case 24:
                LED_set(r46, timer, color);
                break;
            case 25:
                LED_set(r51, timer, color);
                break;
            case 26:
                LED_set(r52, timer, color);
                break;
            case 27:
                LED_set(r53, timer, color);
                break;
            case 28:
                LED_set(r54, timer, color);
                break;
            case 29:
                LED_set(r55, timer, color);
                break;
            case 30:
                LED_set(r56, timer, color);
                break;
            case 31:
                LED_set(r61, timer, color);
                break;
            case 32:
                LED_set(r62, timer, color);
                break;
            case 33:
                LED_set(r63, timer, color);
                break;
            case 34:
                LED_set(r64, timer, color);
                break;
            case 35:
                LED_set(r65, timer, color);
                break;
            case 36:
                LED_set(r66, timer, color);
                break;
        }
    }
    public void UImulticolor_type2(final int cardview, final Integer color, Integer timer,int h){

        int n = 1;

        n = padColorAssign[h];

        switch (cardview) {
            case 1:
                LED_set(r11, timer, padColor[n][1]);
                break;
            case 2:
                LED_set(r12, timer, padColor[n][2]);
                break;
            case 3:
                LED_set(r13, timer, padColor[n][3]);
                break;
            case 4:
                LED_set(r14, timer, padColor[n][4]);
                break;
            case 5:
                LED_set(r15, timer, padColor[n][5]);
                break;
            case 6:
                LED_set(r16, timer, padColor[n][6]);
                break;
            case 7:
                LED_set(r21, timer, padColor[n][7]);
                break;
            case 8:
                LED_set(r22, timer, padColor[n][8]);
                break;
            case 9:
                LED_set(r23, timer, padColor[n][9]);
                break;
            case 10:
                LED_set(r24, timer, padColor[n][10]);
                break;
            case 11:
                LED_set(r25, timer, padColor[n][11]);
                break;
            case 12:
                LED_set(r26, timer, padColor[n][12]);
                break;
            case 13:
                LED_set(r31, timer, padColor[n][13]);
                break;
            case 14:
                LED_set(r32, timer, padColor[n][14]);
                break;
            case 15:
                LED_set(r33, timer, padColor[n][15]);
                break;
            case 16:
                LED_set(r34, timer, padColor[n][16]);
                break;
            case 17:
                LED_set(r35, timer, padColor[n][17]);
                break;
            case 18:
                LED_set(r36, timer, padColor[n][18]);
                break;
            case 19:
                LED_set(r41, timer, padColor[n][19]);
                break;
            case 20:
                LED_set(r42, timer, padColor[n][20]);
                break;
            case 21:
                LED_set(r43, timer, padColor[n][21]);
                break;
            case 22:
                LED_set(r44, timer, padColor[n][22]);
                break;
            case 23:
                LED_set(r45, timer, padColor[n][23]);
                break;
            case 24:
                LED_set(r46, timer, padColor[n][24]);
                break;
            case 25:
                LED_set(r51, timer, padColor[n][25]);
                break;
            case 26:
                LED_set(r52, timer, padColor[n][26]);
                break;
            case 27:
                LED_set(r53, timer, padColor[n][27]);
                break;
            case 28:
                LED_set(r54, timer, padColor[n][28]);
                break;
            case 29:
                LED_set(r55, timer, padColor[n][29]);
                break;
            case 30:
                LED_set(r56, timer, padColor[n][30]);
                break;
            case 31:
                LED_set(r61, timer, padColor[n][31]);
                break;
            case 32:
                LED_set(r62, timer, padColor[n][32]);
                break;
            case 33:
                LED_set(r63, timer, padColor[n][33]);
                break;
            case 34:
                LED_set(r64, timer, padColor[n][34]);
                break;
            case 35:
                LED_set(r65, timer, padColor[n][35]);
                break;
            case 36:
                LED_set(r66, timer, padColor[n][36]);
                break;
            case 37:
                LED_set(r11, timer, default_white);
                break;
            case 38:
                LED_set(r12, timer, default_white);
                break;
            case 39:
                LED_set(r13, timer, default_white);
                break;
            case 40:
                LED_set(r14, timer, default_white);
                break;
            case 41:
                LED_set(r15, timer, default_white);
                break;
            case 42:
                LED_set(r16, timer, default_white);
                break;
            case 43:
                LED_set(r21, timer, default_white);
                break;
            case 44:
                LED_set(r22, timer, default_white);
                break;
            case 45:
                LED_set(r23, timer, default_white);
                break;
            case 46:
                LED_set(r24, timer, default_white);
                break;
            case 47:
                LED_set(r25, timer, default_white);
                break;
            case 48:
                LED_set(r26, timer, default_white);
                break;
            case 49:
                LED_set(r31, timer, default_white);
                break;
            case 50:
                LED_set(r32, timer, default_white);
                break;
            case 51:
                LED_set(r33, timer, default_white);
                break;
            case 52:
                LED_set(r34, timer, default_white);
                break;
            case 53:
                LED_set(r35, timer, default_white);
                break;
            case 54:
                LED_set(r36, timer, default_white);
                break;
            case 55:
                LED_set(r41, timer, default_white);
                break;
            case 56:
                LED_set(r42, timer, default_white);
                break;
            case 57:
                LED_set(r43, timer, default_white);
                break;
            case 58:
                LED_set(r44, timer, default_white);
                break;
            case 59:
                LED_set(r45, timer, default_white);
                break;
            case 60:
                LED_set(r46, timer, default_white);
                break;
            case 61:
                LED_set(r51, timer, default_white);
                break;
            case 62:
                LED_set(r52, timer, default_white);
                break;
            case 63:
                LED_set(r53, timer, default_white);
                break;
            case 64:
                LED_set(r54, timer, default_white);
                break;
            case 65:
                LED_set(r55, timer, default_white);
                break;
            case 66:
                LED_set(r56, timer, default_white);
                break;
            case 67:
                LED_set(r61, timer, default_white);
                break;
            case 68:
                LED_set(r62, timer, default_white);
                break;
            case 69:
                LED_set(r63, timer, default_white);
                break;
            case 70:
                LED_set(r64, timer, default_white);
                break;
            case 71:
                LED_set(r65, timer, default_white);
                break;
            case 72:
                LED_set(r66, timer, default_white);
                break;
        }
    }






    public void LED_set(final CardView im, int timer, final int color){
        Runnable clickRunnable = new Runnable() {
            @Override
            public void run() {

                /*if(multicol){
                    im.setCardBackgroundColor(color);
                }else{
                    im.setCardBackgroundColor(getResources().getColor(color));
                }
                */
                im.setCardBackgroundColor(color);

            }
        };
        im.postDelayed(clickRunnable, timer);
    }

    public void animPad1(int h,int speed){
        int interval = speed;
        int timer = 0;
        int color =red;
        switch (LEDcolor[h]){

            case 1:
                color = red;
                break;
            case 2:
                color = yellow;
                break;
            case 3:
                color = green;
                break;
            case 4:
                color = skyblue;
                break;
            case 5:
                color = blue;
                break;
            case 6:
                color = purple;
                break;
            case 7:
                color = orange;
                break;
        }
            for (int B = 0; B < MainAnim_LED_patternSize[h]; B++) {




                    for (int C = 0; C < MainAnim_LED_size[h][B]; C++) {
                        if(multicol){
                            UImulticolor(MainAnim[h][B][C], color, timer,h);
                        }else{
                            UI(MainAnim[h][B][C], color, timer);
                        }
                        Log.d("Animated pads ", String.valueOf(MainAnim[h][B][C]));

                    }
                    timer += interval;
                if(B == MainAnim_LED_patternSize[h] - 1){
                    for (int D = 0; D < MainAnim_LED_patternSize[h]; D++) {
                        for (int C = 0; C < MainAnim_LED_size[h][D]; C++) {
                            UI(MainAnim[h][D][C], default_white, timer);
                            Log.d("Animated pads whites ", String.valueOf(MainAnim[h][D][C]));

                        }
                        timer += interval;
                    }
                }


        }
        }

    public void animPad2(int h,int speed) {
        int interval = speed;
        int timer = 0;
        int color = red;



        switch (LEDcolor[h]){

            case 1:
                color = red;
                break;
            case 2:
                color = yellow;
                break;
            case 3:
                color = green;
                break;
            case 4:
                color = skyblue;
                break;
            case 5:
                color = blue;
                break;
            case 6:
                color = purple;
                break;
            case 7:
                color = orange;
                break;
        }
        for (int B = 0; B < MainAnim_LED_patternSize[h]; B++) {


            for (int C = 0; C < MainAnim_LED_size[h][B]; C++) {

                if(multicol){
                    UImulticolor(MainAnim[h][B][C], color, timer,h);
                }else{
                    UI(MainAnim[h][B][C], color, timer);
                }
                if (C == MainAnim_LED_size[h][B] - 1) {

                    Log.d("Animated pads ", String.valueOf(MainAnim[h][B][C]));
                }
            }
                timer += interval;
                for (int D = 0; D < MainAnim_LED_size[h][B]; D++) {
                    UI(MainAnim[h][B][D], default_white, timer);
                        Log.d("Animated pads) whites", String.valueOf(MainAnim[h][B][D]));

            }

        }

    }
    public void animPad3(int h,int speed) {
        int interval = speed;
        int timer = 0;
        int color = R.color.red;



        switch (LEDcolor[h]){

            case 1:
                color = R.color.red;
                break;
            case 2:
                color = R.color.yellow;
                break;
            case 3:
                color = R.color.green;
                break;
            case 4:
                color = R.color.skyblue;
                break;
            case 5:
                color = R.color.blue;
                break;
            case 6:
                color = R.color.purple;
                break;
            case 7:
                color = R.color.orange;
                break;
        }
        for (int B = 0; B < MainAnim_LED_patternSize[h]; B++) {


            for (int C = 0; C < MainAnim_LED_size[h][B]; C++) {

                if(multicol){
                    UImulticolor_type2(MainAnim[h][B][C], color, timer,h);
                }else{
                    UI(MainAnim[h][B][C], color, timer);
                }

                if (C == MainAnim_LED_size[h][B] - 1) {

                    Log.d("Animated pads ", String.valueOf(MainAnim[h][B][C]));
                }

            }
            timer += interval;

        }

    }

    public void configInitialPack() {
        JSONObject json = null;
        try {
            json = new JSONObject(readTxtInfoFile());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json != null) {
            initSongWithInfo(json);
        }
    }

    public String readTxtInfoFile() {
        try {
            InputStream is = new FileInputStream(getFilesDir().getAbsolutePath()+"/MiniPads/"+Songname+"/info.txt");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void initSongWithInfo(JSONObject info) {
        if (info.isNull("audiopath")) {
            Log.d("ERROR", "Json cannot find the JSON 1");
            return;
        }
        try {

            JSONObject audiopath = info.getJSONObject("audiopath");
            JSONObject colorpattern = info.getJSONObject("colorpattern");
            JSONObject ledanim = info.getJSONObject("ledanim");
            JSONObject ledtype = info.getJSONObject("ledtype");
            JSONObject ledspeed = info.getJSONObject("ledspeed");
            JSONObject audiosustain = info.getJSONObject("sustain");

            samples_lenth = audiopath.length();

            for (int i = 1; i < pt.length; i++) {

                try {

                    pt[i] = audiopath.getString("pad" + i);
                    colorid[i] = colorpattern.getInt("pad" + i);
                    sustain[i] = audiosustain.getInt("pad" + i);

                    Log.e("assigned path = ",pt[i]);
                    pathID[i] = song_path+pt[i]+".ogg";
                    //passingINT[i] = Integer.parseInt(pt[i]);
                    Log.d("assignes to ", String.valueOf(j) );

                } catch (JSONException e) {
                    pt[i] = "0";
                    //j[i] = tutorID[1];
                    e.printStackTrace();
                }catch (ArrayIndexOutOfBoundsException e){

                }

            }
            try{
                JSONObject ledcolor = info.getJSONObject("ledcolor");
                for (int i = 1; i < pt.length; i++) {
                    try{
                        LEDcolor[i] = ledcolor.getInt("pad" + i);
                        Log.e("LED COLOR= ", String.valueOf(LEDcolor[i]));
                    }catch (JSONException e2){
                        LEDcolor[i] = 0;
                    }

                }
            }catch (JSONException e2){
                multicol = true;
                Log.e("multicol", "true");
            }

                for (int a = 1; a < pt.length; a++) {
                    try {

                        LEDspeed[a] = ledspeed.getInt("pad" + a);
                        LEDtype[a] = ledtype.getInt("pad" + a);

                        for (int b = 0; b < ledanim.getJSONArray("pad" + a).length(); b++) {
                            MainAnim_LED_patternSize[a] = ledanim.getJSONArray("pad" + a).length();
                            try {
                                for (int c = 0; c < ledanim.getJSONArray("pad" + a).getJSONArray(b).length(); c++) {
                                    try {
                                        MainAnim_LED_size[a][b] = ledanim.getJSONArray("pad" + a).getJSONArray(b).length();
                                        MainAnim[a][b][c] =  ledanim.getJSONArray("pad" + a).getJSONArray(b).getInt(c);
                                        Log.e("LED = ", String.valueOf(MainAnim[a][b][c]));
                                    } catch (JSONException e) {
                                        MainAnim[a][b][c] = 0;
                                        e.printStackTrace();
                                    } catch (ArrayIndexOutOfBoundsException e) {

                                    }
                                }

                            } catch (JSONException e) {
                                //j[i] = tutorID[1];
                                e.printStackTrace();
                            } catch (ArrayIndexOutOfBoundsException e) {

                            }
                        }
                    } catch (JSONException e) {
                        MainAnim[a][0][0] = 0;
                        //j[i] = tutorID[1];
                        e.printStackTrace();
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }

            try {
                JSONObject multicolor = info.getJSONObject("multicolor");

                for(int a = 1; a <= 10 ; a++){
                    for(int b = 1; b <= 36 ; b++) {
                        try{
                            padColor[a][b] = multicolor.getJSONObject("padcolor" + a).getInt("color" + b);
                            Log.e("LEDCOLOR = ", String.valueOf(padColor[a][b]));
                        }catch (JSONException k2){
                            padColor[a][b]=0;
                        }
                    }

                }
            }catch (JSONException j2){
                multicol = false;
            }
            try {

                JSONObject multicolorassign = info.getJSONObject("multicolorassign");
                for(int a = 1; a < pt.length ; a++){

                    try{
                        padColorAssign[a] = multicolorassign.getInt("pad" + a);

                    }catch (JSONException k2){
                        padColorAssign[a] = 0;

                    }

                }
            }catch (JSONException j2){
                multicol = false;
            }


        } catch (JSONException e2) {
            e2.printStackTrace();
            Log.d("TXT", "Json cannot find the JSON 2");
        }
    }

    public void request_ad(){
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("E4CB5D6F83689B45B7F53BE5B6C4E6DA").build());
    }

    private void request_ad_startup() {

        adPause = true;
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adPause = true;
                interstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                visuals(R.raw.wire,gif);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!adPause) {
           showIntertitial();
        }
        adPause = false;

    }
    @Override
    protected void onPause() {
        stopPlayingPads();

        //if(!adPause) stopTimer();

        super.onPause();

    }
    public Typeface getFont(String font) {

        Typeface myFont = Typeface.createFromAsset(this.getAssets(), "fonts/" + font);

        return myFont;
    }

    public void showIntertitial(){

        request_ad();
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adPause = true;
                interstitialAd.show();
            }

        });

    }



}




