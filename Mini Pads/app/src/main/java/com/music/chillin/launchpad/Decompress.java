package com.music.chillin.launchpad;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Andrews
 */
public class Decompress extends AsyncTask<Void, Integer, Integer> {

    private String _zipFile;
    private String _location;
    private int per = 0;
    private KProgressHUD bar;
    byte b[] = new byte[1024];
    int n;
    Class context;

    public Decompress(String zipFile, String location, KProgressHUD _bar) {
        _zipFile = zipFile;
        _location = location;
        bar = _bar;
        _dirChecker("");
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {
            ZipFile zip = new ZipFile(_zipFile);
            bar.setMaxProgress(zip.size());
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            BufferedInputStream in = new BufferedInputStream(zin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {

                Log.v("Decompress", "Unzipping " + ze.getName());
                if (ze.isDirectory()) {
                    _dirChecker(ze.getName());
                } else {
                    // Here I am doing the update of my progress bar

                    per++;
                    publishProgress(per);

                    FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                    BufferedOutputStream out = new BufferedOutputStream(fout);
                    while ((n = in.read(b,0,1024)) >= 0) {
                        out.write(b,0,n);
                    }

                    zin.closeEntry();
                    out.close();
                    fout.close();
                }
            }
            zin.close();
        } catch (Exception e) {
            Log.e("Decompress", "unzip", e);
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... progress) {
        bar.setProgress(per); //Since it's an inner class, Bar should be able to be called directly
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (bar != null && bar.isShowing()) {
            bar.dismiss();
        }


    }

    private void _dirChecker(String dir) {
        File f = new File(_location + dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }else if(f.isDirectory()){

        }
    }

}
