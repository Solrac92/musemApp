package com.example.android.fragments.Loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.fragments.data.MuseumDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgcalatrava on 08/03/2015.
 */
public class ArtCiclopediaLoader extends AsyncTaskLoader<List<String>> {

    public ArtCiclopediaLoader(Context context) {
        super(context);
    }

    @Override
    public List<String> loadInBackground() {

        final ArrayList<String> list = new ArrayList<String>();

        MuseumDbHelper db = new MuseumDbHelper(getContext());
        Cursor c = db.getArtJobs();

        if (c.getCount() > 0) {
            Log.v("ENCONTRADOS", c.getCount() + " ELEMENTOS");
            while (c.moveToNext()) {

                list.add(c.getString(0) + ": " + c.getString(1) + " (" + c.getString(2) + ") ");

            }
        }

        else { list.add("Catch some Art Jobs and come back =)"); }

        return list;
    }

}
