package com.example.android.fragments.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.fragments.Fragments.ArtListFragment;
import com.example.android.fragments.Loaders.ArtJobLoader;
import com.example.android.fragments.R;

import java.util.List;


public class ArtJobView extends ActionBarActivity implements LoaderManager.LoaderCallbacks<List<String>>{

    private static final int THE_LOADER = 0x02;
    int art_num;
    String art_name;
    String aut_name;
    String date;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportLoaderManager().initLoader(THE_LOADER, null, this).forceLoad();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



        return super.onOptionsItemSelected(item);
    }

    public void surf(View v) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.google.es/#q=" + art_name + " " + aut_name));

        startActivity(intent);

    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        Intent intent = getIntent();
        art_num = Integer.valueOf(intent.getStringExtra(ArtListFragment.EXTRA_MESSAGE));
        ArtJobLoader loader = new ArtJobLoader(this, art_num);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {

        aut_name = data.get(1);
        art_name = data.get(2);
        date = data.get(3);
        desc = data.get(4);

        TextView newtext = (TextView) findViewById(R.id.detail_art_num);

        newtext.setText(String.valueOf(getString(R.string.art_num) + " " + String.valueOf(art_num)));

        newtext = (TextView) findViewById(R.id.detail_aut_name);

        newtext.setText(getString(R.string.aut_name) +  " " + aut_name);

        newtext = (TextView) findViewById(R.id.detail_art_name);

        newtext.setText(getString(R.string.art_name) +   " " +art_name);

        newtext = (TextView) findViewById(R.id.detail_date);

        newtext.setText(getString(R.string.date) +  " " +date);

        newtext = (TextView) findViewById(R.id.detail_desc);

        newtext.setText(getString(R.string.desc) +  " " + desc);

        newtext = (TextView) findViewById(R.id.detail_photo);

        newtext.setText(getString(R.string.surf));

    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }
}
