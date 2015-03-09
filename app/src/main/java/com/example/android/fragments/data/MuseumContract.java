package com.example.android.fragments.data;

import android.provider.BaseColumns;

/**
 * Created by cgcalatrava on 06/03/2015.
 */
public class MuseumContract {

    public MuseumContract() {}

    public static abstract class MuseumInfo implements BaseColumns {

        public static final String TABLE_NAME = "Museum";
        public static final String COLUMN_NUMBER_ART = "number";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_ARTWORK = "artwork";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DESCRIPTION = "description";

        public static final int NUMBER_ART = 0;
        public static final int NAME_AUTHOR = 1;
        public static final int NAME_ARTWORK = 2;
        public static final int DATE = 3;
        public static final int DESCRIPTION = 4;

    }

}
