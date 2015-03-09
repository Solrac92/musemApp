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
public class ArtJobLoader extends AsyncTaskLoader<List<String>> {

    int art_num;
    public ArtJobLoader(Context context, int id) {

        super(context);
        art_num = id;
    }

    @Override
    public List<String> loadInBackground() {

        final ArrayList<String> list = new ArrayList<String>();

        MuseumDbHelper db = new MuseumDbHelper(getContext());
        Cursor c = db.findArtJob(art_num);

            Log.v("MUSEUM DB", "ENCONTRADO");
            //c.moveToNext();

                list.add(c.getString(0));
                list.add(c.getString(1));
                list.add(c.getString(2));
                list.add(c.getString(3));
                list.add(c.getString(4));

        return list;
    }

}
