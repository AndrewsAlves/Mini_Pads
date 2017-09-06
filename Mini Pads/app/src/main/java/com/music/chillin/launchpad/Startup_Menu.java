package com.music.chillin.launchpad;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
//import com.music.chillin.launchpad.Startup_MenuPermissionsDispatcher;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class Startup_Menu extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    public TextView indian_toggle,international_toggle;
    public EditText search_text;
    public FloatingActionButton facebook,instagram,youtube,rate,share,bugreport;
    LinearLayout searchbar;
    DatabaseReference kitnames_inter = FirebaseDatabase.getInstance().getReferenceFromUrl("https://launchpad-93445.firebaseio.com/songs");
    DatabaseReference kitnames_indian = FirebaseDatabase.getInstance().getReferenceFromUrl("https://launchpad-93445.firebaseio.com/indian_songs");
    public ArrayList<String> temperarySongname_inter = new ArrayList<>();
    public ArrayList<String> temperaryKitname_inter = new ArrayList<>();
    public ArrayList<String> temperaryTutorialLink_inter = new ArrayList<>();
    public ArrayList<String> mSongngame_inter = new ArrayList<>();
    public ArrayList<String> mKitname_inter = new ArrayList<>();
    public ArrayList<String> mTutoriallink_inter = new ArrayList<>();

    public ArrayList<String> temperarySongname_indian = new ArrayList<>();
    public ArrayList<String> temperaryKitname_indian = new ArrayList<>();
    public ArrayList<String> temperaryTutorialLink_indian = new ArrayList<>();
    public ArrayList<String> mSongngame_indian = new ArrayList<>();
    public ArrayList<String> mKitname_indian = new ArrayList<>();
    public ArrayList<String> mTutoriallink_indian = new ArrayList<>();

    public ArrayList<String> mSongngame = new ArrayList<>();
    public ArrayList<String> mKitname = new ArrayList<>();
    public ArrayList<String> mTutoriallink = new ArrayList<>();


    public ListView listViewKits;
    CustomListAdapter songngmae_adapter;


    public static String FILE_KIT_INTER = "kit_file_inter";
    public static String FILE_SONGNAME_INTER = "songname_file_inter";
    public static String FILE_TUTORIAL_INTER = "tutoriallinks_inter";
    public static String KEY_KIT_INTER = "kit_key_inter";
    public static String KEY_SONGNAME_INTER = "songname_key_inter";
    public static String KEY_TUTORIAL_INTER = "tutorial_inter";

    public static String FILE_KIT_INDIAN = "kit_file_indian";
    public static String FILE_SONGNAME_INDIAN = "songname_file_indian";
    public static String FILE_TUTORIAL_INDIAN = "tutoriallinks_indian";
    public static String KEY_KIT_INDIAN = "kit_key_indian";
    public static String KEY_SONGNAME_INDIAN = "songname_key_indian";
    public static String KEY_TUTORIAL_INDIAN = "tutorial_indian";

    File rootPath;
    FragmentTransaction fragmentTransaction;
    Select_skin select_skin;
    private ConnectivityReceiver mReceiver;
    private KProgressHUD hud;
    boolean isFragmented = false;

    boolean international = true;
    private File rootPath_skins;
    private InterstitialAd interstitialAd_launchpad;
    public boolean showIntertitial = false;
    private AdView adView_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup__menu);


        MobileAds.initialize(this,"ca-app-pub-4870071250957997~5574912812");
        showIntertitial = false;
        interstitialAd_launchpad = new InterstitialAd(this);
        interstitialAd_launchpad.setAdUnitId(getString(R.string.intertiatial_between));

        adView_banner = (AdView)findViewById(R.id.adView_startup);
        adView_banner.loadAd(new AdRequest.Builder().build());
        international = true;
        listViewKits = (ListView) findViewById(R.id.songs_recycler);
        search_text = (EditText)findViewById(R.id.search_text_view);
        international_toggle = (TextView)findViewById(R.id.international_toggle);
        indian_toggle = (TextView)findViewById(R.id.indian_toggle);
        searchbar = (LinearLayout)findViewById(R.id.searchbar);
        facebook = (FloatingActionButton)findViewById(R.id.facebook) ;
        instagram = (FloatingActionButton)findViewById(R.id.instagram) ;
        youtube = (FloatingActionButton)findViewById(R.id.youtube) ;
        share = (FloatingActionButton)findViewById(R.id.share) ;
        rate = (FloatingActionButton)findViewById(R.id.rate) ;
        bugreport = (FloatingActionButton)findViewById(R.id.bug_report) ;




        international_toggle.setTypeface(getFont("Roboto-Bold.ttf"));
        indian_toggle.setTypeface(getFont("Roboto-Bold.ttf"));
        international_toggle.setTextColor(ContextCompat.getColor(Startup_Menu.this,R.color.red));
        songngmae_adapter = new CustomListAdapter(this, mKitname, mSongngame, mTutoriallink);
        listViewKits.setAdapter(songngmae_adapter);
       /* if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){


            Startup_MenuPermissionsDispatcher.onGrantedWithCheck(this);
            //fileReadGranted(1);
           // fileReadDenied(1);
           // fileAccessDenied(1);

        } else{

            doStuff();

        }*/


        rootPath = new File(getFilesDir().getAbsolutePath(),"/MiniPads");
        Log.e("File not available", rootPath.getAbsolutePath());
        rootPath_skins = new File(rootPath.getAbsolutePath(),"/skins");
        //initialrootPath = new File(rootPath.getPath(),);
        if (!rootPath.exists()) {
            rootPath.mkdir();
        }
        if(!rootPath_skins.exists()){
            rootPath_skins.mkdir();
        }
        if(!new File(rootPath.getAbsolutePath(),"Rover").exists()){
            try{

                CopyRAWtoSDCard();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        getDownloadedSongs(mSongngame_inter,FILE_SONGNAME_INTER,KEY_SONGNAME_INTER,1);
        getDownloadedSongs(mKitname_inter,FILE_KIT_INTER,KEY_KIT_INTER,2);
        getDownloadedSongs(mTutoriallink_inter,FILE_TUTORIAL_INTER,KEY_TUTORIAL_INTER,3);

        getDownloadedSongs(mSongngame_indian,FILE_SONGNAME_INDIAN,KEY_SONGNAME_INDIAN,4);
        getDownloadedSongs(mKitname_indian,FILE_KIT_INDIAN,KEY_KIT_INDIAN,5);
        getDownloadedSongs(mTutoriallink_indian,FILE_TUTORIAL_INDIAN,KEY_TUTORIAL_INDIAN,6);

        mSongngame.clear();
        mKitname.clear();
        mTutoriallink.clear();
        mSongngame.addAll(mSongngame_inter);
        mKitname.addAll(mKitname_inter);
        mTutoriallink.addAll(mTutoriallink_inter);
        songngmae_adapter.notifyDataSetChanged();

        indian_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indian_toggle.setTextColor(ContextCompat.getColor(Startup_Menu.this,R.color.red));
                international_toggle.setTextColor(ContextCompat.getColor(Startup_Menu.this,R.color.default_text_color));
                mSongngame.clear();
                mKitname.clear();
                mTutoriallink.clear();
                mSongngame.addAll(mSongngame_indian);
                mKitname.addAll(mKitname_indian);
                mTutoriallink.addAll(mTutoriallink_indian);
                songngmae_adapter.notifyDataSetChanged();
                international = false;

            }
        });
        international_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indian_toggle.setTextColor(ContextCompat.getColor(Startup_Menu.this,R.color.default_text_color));
                international_toggle.setTextColor(ContextCompat.getColor(Startup_Menu.this,R.color.red));
                mSongngame.clear();
                mKitname.clear();
                mTutoriallink.clear();
                mSongngame.addAll(mSongngame_inter);
                mKitname.addAll(mKitname_inter);
                mTutoriallink.addAll(mTutoriallink_inter);
                songngmae_adapter.notifyDataSetChanged();
                international = true;

            }
        });

        search_text.setTypeface(getFont("Roboto-Thin.ttf"));
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(international){
                    if (s != null && mSongngame_inter != null) {
                        mSongngame.clear();
                        mKitname.clear();
                        mTutoriallink.clear();
                        int length = mSongngame_inter.size();
                        int i = 0;
                        while (i < length) {
                            String item = mSongngame_inter.get(i);

                            if (item.toLowerCase().startsWith(s.toString().toLowerCase())) {

                                mSongngame.add(item);
                                mKitname.add(mKitname_inter.get(i));
                                mTutoriallink.add(mTutoriallink_inter.get(i));
                                Log.e("Matched Songs", item);
                                Log.e("Matched Artist", item.split(" - ")[1]);
                            }else if(item.contains("-")){
                                if(item.split(" - ")[1].toLowerCase().startsWith(s.toString().toLowerCase())){

                                    mSongngame.add(item);
                                    mKitname.add(mKitname_inter.get(i));
                                    mTutoriallink.add(mTutoriallink_inter.get(i));
                                    Log.e("Matched Songs", item);
                                    Log.e("Matched Artist", item.split(" - ")[1]);

                                }
                            }

                            i++;
                        }
                        songngmae_adapter.notifyDataSetChanged();
                    }
                }else if(!international){
                    if (s != null && mSongngame_indian != null) {
                        mSongngame.clear();
                        mKitname.clear();
                        mTutoriallink.clear();
                        int length = mSongngame_indian.size();
                        int i = 0;
                        while (i < length) {
                            String item = mSongngame_indian.get(i);

                            if (item.toLowerCase().startsWith(s.toString().toLowerCase())) {

                                mSongngame.add(item);
                                mKitname.add(mKitname_indian.get(i));
                                mTutoriallink.add(mTutoriallink_indian.get(i));
                                Log.e("Matched Songs", item);

                            }else if(item.contains("-")){
                                if(item.split(" - ")[1].toLowerCase().startsWith(s.toString().toLowerCase())){

                                    mSongngame.add(item);
                                    mKitname.add(mKitname_indian.get(i));
                                    mTutoriallink.add(mTutoriallink_indian.get(i));
                                    Log.e("Matched Songs", item);
                                    Log.e("Matched Artist", item.split(" - ")[1]);

                                }
                            }

                            i++;
                        }
                        songngmae_adapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listViewKits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File rootPath1 = new File(rootPath.getAbsolutePath(), mKitname.get(position));
                if(!rootPath1.exists()){
                    if(isNetworkAvailable()){

                            downloadTask(mKitname.get(position), mSongngame.get(position), mTutoriallink.get(position));

                    }else {
                        Toast.makeText(Startup_Menu.this,"check your connection!",Toast.LENGTH_SHORT).show();
                    }

                }else{

                    select_skin = new Select_skin();
                    fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("songname", mKitname.get(position));
                    args.putString("tutorial", mTutoriallink.get(position));
                    args.putString("songname_title", mSongngame.get(position));
                    select_skin.setArguments(args);
                    fragmentTransaction.replace(R.id.select_skin_frame, select_skin);
                    fragmentTransaction.commit();
                    // title.setText("Select A Skin");
                    isFragmented = true;

                }
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://facewebmodal/f?href=" +"https://www.facebook.com/Andyapps2/")));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/Andyapps2/")));
                }

            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/andy_apps/?hl=en")));

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"http://play.google.com/store/apps/details?id=" +getPackageName());
                startActivity(Intent.createChooser(intent,"Share Mini Pads Via"));
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/channel/UCA6ZaYTr7uCrH5f_lz3sHGA")));
            }
        });
        bugreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                final PackageManager pm = getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") ||
                            info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"andrewsalvisbusiness@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT," Mini Pads : Crash or Bug report");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        if(isFragmented) {
            fragmentTransaction.remove(select_skin);
            fragmentTransaction.commit();
            //title.setText("Mini Pads");
            isFragmented = false;
        }else if(!isFragmented){
            new AlertDialog.Builder(this)
                    .setTitle("Mini Pads")
                    .setMessage("Are you sure you want to close Mini Pads ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }

    }
    public void saveDownloadedSongs(ArrayList<String> saveLists, String FILE_KIT, String KEY_KIT) {
        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        for (int i = 0; i < saveLists.size(); i++) {
            if (i + 1 != saveLists.size()) {
                sb.append(gson.toJson(saveLists.get(i))).append("|");
                Log.e("saving Songnames : ", saveLists.get(i));
            } else {
                sb.append(gson.toJson(saveLists.get(i)));
                Log.e("saving Songnames : ", saveLists.get(i));
            }
        }
        SharedPreferences.Editor prefEditor = this.getBaseContext().getSharedPreferences(FILE_KIT, 0).edit();
        prefEditor.putString(KEY_KIT, sb.toString());
        prefEditor.apply();

    }

    public void getDownloadedSongs(ArrayList<String> saveLists, String FILE_KIT, String KEY_KIT,int type) {
        saveLists.removeAll(saveLists);
        String storedCollection = this.getBaseContext().getSharedPreferences(FILE_KIT, 0).getString(KEY_KIT, null);
        if (storedCollection != null) {
            String[] arrStrPacks = storedCollection.split("\\|");
            Gson gson = new Gson();
            for (String json : arrStrPacks) {
                String obj = (String) gson.fromJson(json, String.class);
                saveLists.add(obj);
                Log.d("Getting SONGNAMES : ",obj);
            }

        }else {
            if(type == 1) saveLists.add("Closer - Chainsmokers");
            if(type == 2) saveLists.add("Rover");
            if(type == 3) saveLists.add("CS");
            if(type == 4) saveLists.add("Nashe Si Chadh Gayi");
            if(type == 5) saveLists.add("After Party");
            if(type == 6) saveLists.add("CS");

        }
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    private void CopyRAWtoSDCard() throws IOException {
        InputStream in = getResources().openRawResource(R.raw.initialpack);
        FileOutputStream out = new FileOutputStream(rootPath.getAbsolutePath()+"/Rover.zip");
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
        KProgressHUD hud = KProgressHUD.create(Startup_Menu.this)
                .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                .setLabel("Extracting")
                .setCancellable(false)
                .setMaxProgress(100)
                .show();
        Decompress unzip = new Decompress(rootPath.getAbsolutePath()+"/Rover.zip",rootPath.getAbsolutePath()+"/Rover/",hud);
        unzip.execute();

    }

    public void downloadTask(final String child_url, final String songname, final String tutorial_link) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://launchpad-93445.appspot.com/");
        final StorageReference islandRef = storageRef.child(child_url+".zip");
        hud = KProgressHUD.create(Startup_Menu.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setLabel("Downloading Pack")
                .setMaxProgress(100)
                .setCancellable(false)
                .show();
        final File localFile = new File(rootPath, child_url+".zip");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                final String allFilePath = rootPath.getAbsolutePath()+"/";
                final String zipFile = allFilePath + child_url +".zip" ;
                final String unzipLocation = allFilePath + child_url +"/" ;
                hud.setLabel("Extracting Audio Chunks");
                Decompress d = new Decompress(zipFile, unzipLocation,hud);
                d.execute();
                Toast.makeText(Startup_Menu.this, "Kit Ready", Toast.LENGTH_LONG).show();
                songngmae_adapter.notifyDataSetChanged();
                openSkin(child_url,songname,tutorial_link);
                //  updateDb(timestamp,localFile.toString(),position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                hud.dismiss();
                Toast.makeText(Startup_Menu.this, "Kit not downloaded", Toast.LENGTH_LONG).show();
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


    public void getDataFromFirebase() {

        if (isNetworkAvailable()) {

            temperarySongname_indian.clear();
            temperaryKitname_indian.clear();
            temperaryTutorialLink_indian.clear();
            kitnames_indian.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String songname = dataSnapshot.child("song").getValue(String.class);
                    String kitname = dataSnapshot.child("kit").getValue(String.class);
                    String tutoriallink = dataSnapshot.child("tutorial").getValue(String.class);
                    Log.e("song name  ", songname);
                    Log.e("kitname  ", kitname);
                    Log.e("tutorial link  ", tutoriallink);
                    if(!temperaryKitname_indian.contains(kitname)) {
                        temperarySongname_indian.add(songname);
                        temperaryKitname_indian.add(kitname);
                        temperaryTutorialLink_indian.add(tutoriallink);
                    }
                    mSongngame_indian.clear();
                    mKitname_indian.clear();
                    mTutoriallink_indian.clear();
                    mSongngame_indian.addAll(temperarySongname_indian);
                    mKitname_indian.addAll(temperaryKitname_indian);
                    mTutoriallink_indian.addAll(temperaryTutorialLink_indian);

                    if(!international){
                        mSongngame.clear();
                        mKitname.clear();
                        mTutoriallink.clear();
                        mSongngame.addAll(mSongngame_indian);
                        mKitname.addAll(mKitname_indian);
                        mTutoriallink.addAll(mTutoriallink_indian);
                        songngmae_adapter.notifyDataSetChanged();
                    }
                    saveDownloadedSongs(mSongngame_indian,FILE_SONGNAME_INDIAN,KEY_SONGNAME_INDIAN);
                    saveDownloadedSongs(mKitname_indian,FILE_KIT_INDIAN,KEY_KIT_INDIAN);
                    saveDownloadedSongs(mTutoriallink_indian,FILE_TUTORIAL_INDIAN,KEY_TUTORIAL_INDIAN);


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    //Log.e("Changed Variable: ",s);
                    String songname = dataSnapshot.child("song").getValue(String.class);
                    String kitname = dataSnapshot.child("kit").getValue(String.class);
                    String tutoriallink = dataSnapshot.child("tutorial").getValue(String.class);

                    if (s == null) {
                        temperarySongname_indian.set(0, songname);
                        temperaryKitname_indian.set(0, kitname);
                        temperaryTutorialLink_indian.set(0, tutoriallink);

                        mSongngame_indian.set(0, songname);
                        mKitname_indian.set(0, kitname);
                        mTutoriallink_indian.set(0, tutoriallink);
                        if(!international){
                            songngmae_adapter.notifyDataSetChanged();
                        }
                        saveDownloadedSongs(mSongngame_indian,FILE_SONGNAME_INDIAN,KEY_SONGNAME_INDIAN);
                        saveDownloadedSongs(mKitname_indian,FILE_KIT_INDIAN,KEY_KIT_INDIAN);
                        saveDownloadedSongs(mTutoriallink_indian,FILE_TUTORIAL_INDIAN,KEY_TUTORIAL_INDIAN);

                    } else {
                        temperarySongname_indian.set(Integer.parseInt(s), songname);
                        temperaryKitname_indian.set(Integer.parseInt(s), kitname);
                        temperaryTutorialLink_indian.set(Integer.parseInt(s), tutoriallink);

                        mSongngame_indian.set(Integer.parseInt(s), songname);
                        mKitname_indian.set(Integer.parseInt(s), kitname);
                        mTutoriallink_indian.set(Integer.parseInt(s), tutoriallink);
                        if(!international){
                            mSongngame.clear();
                            mKitname.clear();
                            mTutoriallink.clear();
                            mSongngame.addAll(mSongngame_indian);
                            mKitname.addAll(mKitname_indian);
                            mTutoriallink.addAll(mTutoriallink_indian);
                            songngmae_adapter.notifyDataSetChanged();
                        }
                        saveDownloadedSongs(mSongngame_indian,FILE_SONGNAME_INDIAN,KEY_SONGNAME_INDIAN);
                        saveDownloadedSongs(mKitname_indian,FILE_KIT_INDIAN,KEY_KIT_INDIAN);
                        saveDownloadedSongs(mTutoriallink_indian,FILE_TUTORIAL_INDIAN,KEY_TUTORIAL_INDIAN);

                    }


                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    String songname = dataSnapshot.child("song").getValue(String.class);
                    String kitname = dataSnapshot.child("kit").getValue(String.class);
                    String tutoriallink = dataSnapshot.child("tutorial").getValue(String.class);
                    if (temperarySongname_indian.contains(songname)) {
                        temperarySongname_indian.remove(songname);
                        temperaryKitname_indian.remove(kitname);
                        temperaryTutorialLink_indian.remove(tutoriallink);
                    }
                    if (mSongngame_indian.contains(songname)) {
                        mSongngame_indian.remove(songname);
                        mKitname_indian.remove(kitname);
                        mTutoriallink_indian.remove(tutoriallink);
                        saveDownloadedSongs(mSongngame_indian,FILE_SONGNAME_INDIAN,KEY_SONGNAME_INDIAN);
                        saveDownloadedSongs(mKitname_indian,FILE_KIT_INDIAN,KEY_KIT_INDIAN);
                        saveDownloadedSongs(mTutoriallink_indian,FILE_TUTORIAL_INDIAN,KEY_TUTORIAL_INDIAN);
                        if(!international){
                            clearKitList();
                            mSongngame.addAll(mSongngame_indian);
                            mKitname.addAll(mKitname_indian);
                            mTutoriallink.addAll(mTutoriallink_indian);
                            songngmae_adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





            temperarySongname_inter.clear();
            temperaryKitname_inter.clear();
            temperaryTutorialLink_inter.clear();
            kitnames_inter.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String songname = dataSnapshot.child("song").getValue(String.class);
                    String kitname = dataSnapshot.child("kit").getValue(String.class);
                    String tutoriallink = dataSnapshot.child("tutorial").getValue(String.class);

                    if(!temperaryKitname_inter.contains(kitname)) {
                        temperarySongname_inter.add(songname);
                        temperaryKitname_inter.add(kitname);
                        temperaryTutorialLink_inter.add(tutoriallink);
                    }
                    mSongngame_inter.clear();
                    mKitname_inter.clear();
                    mTutoriallink_inter.clear();
                    mSongngame_inter.addAll(temperarySongname_inter);
                    mKitname_inter.addAll(temperaryKitname_inter);
                    mTutoriallink_inter.addAll(temperaryTutorialLink_inter);
                    if(international){
                        clearKitList();
                        add_All_Kits_international();
                        songngmae_adapter.notifyDataSetChanged();
                    }
                    saveDownloadedSongs(mSongngame_inter,FILE_SONGNAME_INTER,KEY_SONGNAME_INTER);
                    saveDownloadedSongs(mKitname_inter,FILE_KIT_INTER,KEY_KIT_INTER);
                    saveDownloadedSongs(mTutoriallink_inter,FILE_TUTORIAL_INTER,KEY_TUTORIAL_INTER);


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    //Log.e("Changed Variable: ",s);
                    String songname = dataSnapshot.child("song").getValue(String.class);
                    String kitname = dataSnapshot.child("kit").getValue(String.class);
                    String tutoriallink = dataSnapshot.child("tutorial").getValue(String.class);


                    if (s == null) {
                        temperarySongname_inter.set(0, songname);
                        temperaryKitname_inter.set(0, kitname);
                        temperaryTutorialLink_inter.set(0, tutoriallink);

                        mSongngame_inter.set(0, songname);
                        mKitname_inter.set(0, kitname);
                        mTutoriallink_inter.set(0, tutoriallink);
                        if(international){
                            songngmae_adapter.notifyDataSetChanged();
                        }
                        saveDownloadedSongs(mSongngame_inter,FILE_SONGNAME_INTER,KEY_SONGNAME_INTER);
                        saveDownloadedSongs(mKitname_inter,FILE_KIT_INTER,KEY_KIT_INTER);
                        saveDownloadedSongs(mTutoriallink_inter,FILE_TUTORIAL_INTER,KEY_TUTORIAL_INTER);

                    } else {
                        temperarySongname_inter.set(Integer.parseInt(s), songname);
                        temperaryKitname_inter.set(Integer.parseInt(s), kitname);
                        temperaryTutorialLink_inter.set(Integer.parseInt(s), tutoriallink);

                        mSongngame_inter.set(Integer.parseInt(s), songname);
                        mKitname_inter.set(Integer.parseInt(s), kitname);
                        mTutoriallink_inter.set(Integer.parseInt(s), tutoriallink);
                        if(international){
                            clearKitList();
                            add_All_Kits_international();
                            songngmae_adapter.notifyDataSetChanged();
                        }
                        saveDownloadedSongs(mSongngame_inter,FILE_SONGNAME_INTER,KEY_SONGNAME_INTER);
                        saveDownloadedSongs(mKitname_inter,FILE_KIT_INTER,KEY_KIT_INTER);
                        saveDownloadedSongs(mTutoriallink_inter,FILE_TUTORIAL_INTER,KEY_TUTORIAL_INTER);

                    }


                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    String songname = dataSnapshot.child("song").getValue(String.class);
                    String kitname = dataSnapshot.child("kit").getValue(String.class);
                    String tutoriallink = dataSnapshot.child("tutorial").getValue(String.class);
                    if (temperarySongname_inter.contains(songname)) {
                        temperarySongname_inter.remove(songname);
                        temperaryKitname_inter.remove(kitname);
                        temperaryTutorialLink_inter.remove(tutoriallink);
                    }
                    if (mSongngame_inter.contains(songname)) {
                        mSongngame_inter.remove(songname);
                        mKitname_inter.remove(kitname);
                        mTutoriallink_inter.remove(tutoriallink);
                        saveDownloadedSongs(mSongngame_inter,FILE_SONGNAME_INTER,KEY_SONGNAME_INTER);
                        saveDownloadedSongs(mKitname_inter,FILE_KIT_INTER,KEY_KIT_INTER);
                        saveDownloadedSongs(mTutoriallink_inter,FILE_TUTORIAL_INTER,KEY_TUTORIAL_INTER);
                        if(international){
                          clearKitList();
                           add_All_Kits_international();
                            songngmae_adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }


public void doStuff(){



}
    public void add_All_Kits_international(){

        mSongngame.addAll(mSongngame_inter);
        mKitname.addAll(mKitname_inter);
        mTutoriallink.addAll(mTutoriallink_inter);

    }
    public void clearKitList(){
        mSongngame.clear();
        mKitname.clear();
        mTutoriallink.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();


        this.mReceiver = new ConnectivityReceiver();
        registerReceiver(
                this.mReceiver,
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));
        onNetworkConnectionChanged(ConnectivityReceiver.isConnected());


       /* if (showIntertitial) {
            interstitialAd_launchpad.loadAd(new AdRequest.Builder().addTestDevice("E4CB5D6F83689B45B7F53BE5B6C4E6DA").build());
            Log.e("State", "Resumed for Intertitials");
            interstitialAd_launchpad.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    showIntertitial = false;
                    interstitialAd_launchpad.show();
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                    showIntertitial = false;
                }
            });
        }
        showIntertitial = true;*/

    }


    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mReceiver);

    }



    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

       if(isConnected){
            Toast.makeText(this,"Connected",Toast.LENGTH_SHORT).show();
            getDataFromFirebase();
        }else{
           Toast.makeText(this,"you must be online for new songs!",Toast.LENGTH_SHORT).show();
       }

    }








        public void request_ad(){

            interstitialAd_launchpad.loadAd(new AdRequest.Builder().addTestDevice("E4CB5D6F83689B45B7F53BE5B6C4E6DA").build());

    }

    private void request_ad_startup() {

        interstitialAd_launchpad.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                showIntertitial = true;
                interstitialAd_launchpad.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                    //request_ad();
            }
        });


    }


    public Typeface getFont(String font) {

        Typeface myFont = Typeface.createFromAsset(this.getAssets(), "fonts/" + font);

        return myFont;
    }

    public void openSkin(String kitname, String songname,String tutorial_link){
        select_skin = new Select_skin();
        fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("songname", kitname);
        args.putString("tutorial",tutorial_link);
        args.putString("songname_title", songname);
        select_skin.setArguments(args);
        fragmentTransaction.replace(R.id.select_skin_frame, select_skin);
        fragmentTransaction.commitAllowingStateLoss();
        // title.setText("Select A Skin");
        isFragmented = true;
    }
}





