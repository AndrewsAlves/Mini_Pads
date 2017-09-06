package com.music.chillin.launchpad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.music.chillin.launchpad.R;

import java.io.File;
import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String>  {
    private  Activity context;
    public  ArrayList<String> itemname;
    public  ArrayList<String> Songname;
    public  ArrayList<String> tutorial;
    Typeface Robotothin;
    Typeface Robotoblack;
    Typeface Robotobold;

    public int colors[] = new int[5];

    public CustomListAdapter(Activity context, ArrayList<String> packsnames, ArrayList<String> Songname, ArrayList<String> tutorial_link) {
        super(context, R.layout.song_recycler_layout, packsnames);
        this.context = context;
        this.itemname = packsnames;
        this.Songname = Songname;
        this.tutorial = tutorial_link;
         Robotothin = getFont("Roboto-Thin.ttf");
         Robotoblack = getFont("Roboto-Black.ttf");
         Robotobold = getFont("Roboto-Bold.ttf");

        colors[0] = ContextCompat.getColor(context,R.color.blue);
        colors[1] = ContextCompat.getColor(context,R.color.purple);
        colors[2] = ContextCompat.getColor(context,R.color.pink);
        colors[3] = ContextCompat.getColor(context,R.color.red);
        colors[4] = ContextCompat.getColor(context,R.color.orange);

    }

    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.song_recycler_layout, parent, false);
        }

        RelativeLayout song_relative_layout = (RelativeLayout) view.findViewById(R.id.relative_layout_songs);
       // YoYo.with(Techniques.FadeIn).duration(700).playOn(song_relative_layout);
        TextView txtTitle = (TextView) view.findViewById(R.id.kitname);
        TextView txtfirstLetter = (TextView) view.findViewById(R.id.kitfistLetter);
        TextView txtSongname = (TextView) view.findViewById(R.id.songname);
        ImageView youtubeTutorial = (ImageView) view.findViewById(R.id.youtubeTutorial);
        txtTitle.setTypeface(Robotothin);
        txtSongname.setTypeface(Robotoblack);
        txtfirstLetter.setTypeface(Robotoblack);
        if (position < 5) {
            txtTitle.setTextColor(colors[position]);
            txtfirstLetter.setTextColor(colors[position]);
        } else if (position < 10) {
            txtTitle.setTextColor(colors[position - 5]);
            txtfirstLetter.setTextColor(colors[position - 5]);
        } else if (position < 15) {
            txtTitle.setTextColor( colors[position - 10]);
            txtfirstLetter.setTextColor(colors[position - 10]);
        } else if (position < 20) {
            txtTitle.setTextColor(colors[position - 15]);
            txtfirstLetter.setTextColor(colors[position - 15]);
        } else if (position < 25) {
            txtTitle.setTextColor( colors[position - 20]);
            txtfirstLetter.setTextColor( colors[position - 20]);
        } else if (position < 30) {
            txtTitle.setTextColor(colors[position - 25]);
            txtfirstLetter.setTextColor( colors[position - 25]);
        } else if (position < 35) {
            txtTitle.setTextColor( colors[position - 30]);
            txtfirstLetter.setTextColor(colors[position - 30]);
        } else if (position < 40) {
            txtTitle.setTextColor(colors[position - 35]);
            txtfirstLetter.setTextColor(colors[position - 35]);
        } else if (position < 45) {
            txtTitle.setTextColor(colors[position - 40]);
            txtfirstLetter.setTextColor(colors[position - 40]);
        } else if (position < 50) {
            txtTitle.setTextColor(colors[position - 45]);
            txtfirstLetter.setTextColor( colors[position - 45]);
        }


        //txtTitle.setTypeface(Typer.set(context).getFont(Font.ROBOTO_THIN));
        //txtSongname.setTypeface(Typer.set(context).getFont(Font.ROBOTO_BLACK));
        //txtfirstLetter.setTypeface(Typer.set(context).getFont(Font.ROBOTO_BLACK));
        ImageView ivDownloaded = (ImageView) view.findViewById(R.id.iistodownload);
        File rootPath = new File(context.getFilesDir().getAbsolutePath(),"/MiniPads/" + itemname.get(position));
        if (!rootPath.exists()) {
            ivDownloaded.setVisibility(View.VISIBLE);
        } else {
            ivDownloaded.setVisibility(View.INVISIBLE);
        }
        txtTitle.setText(itemname.get(position).toUpperCase());
        txtfirstLetter.setText(itemname.get(position).substring(0, 1));
        txtSongname.setText(Songname.get(position));
        youtubeTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tutorial.get(position).equals("CS")) {
                    Toast.makeText(context, "Tutorial Coming Soon", Toast.LENGTH_SHORT).show();
                } else {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(tutorial.get(position))));
                }
            }
        });

        return view;
    }

    /*@NonNull
    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults1 = new FilterResults();
            FilterResults filterResults2 = new FilterResults();
            FilterResults filterResults3 = new FilterResults();
            temp_tutorial.clear();
            temp_Songname.clear();
            temp_itemname.clear();
            ArrayList<String> tempList = new ArrayList<String>();
        //constraint is the result from text you want to filter against.
        //objects is your data set you will filter from
            if (constraint != null && Songname != null) {
            int length = Songname.size();
            int i = 0;
            while (i < length) {
                String item = Songname.get(i);

                if (item.toLowerCase().startsWith(constraint.toString())) {

                    temp_Songname.add(item);
                    temp_itemname.add(itemname.get(i));
                    temp_tutorial.add(tutorial.get(i));
                    Log.e("Matched Songs", item);


                }

                i++;
            }
            //following two lines is very important
            //as publish result can only take FilterResults objects
            filterResults1.values = tempList;

            filterResults1.count = tempList.size();
        }
            return filterResults1;
    }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            Songname.clear();
            tutorial.clear();
            itemname.clear();
            Songname.addAll(temp_Songname);
            tutorial.addAll(temp_tutorial);
            itemname.addAll(temp_itemname);
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    };*/
    public Typeface getFont(String font) {

         Typeface myFont = Typeface.createFromAsset(context.getAssets(), "fonts/" + font);

        return myFont;
    }
}

