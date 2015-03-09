package com.example.android.fragments.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by cgcalatrava on 06/03/2015.
 */
public class MuseumProvider extends ContentProvider {

    private MuseumDbHelper mDbHelper;
    private static final String AUTHORITY = "com.example.android.fragments.provider.MuseumProvider";
    private static final String TABLE = MuseumContract.MuseumInfo.TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int ARTJOBS = 1;
    public static final int ARTJOB_ID = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, TABLE, ARTJOBS);
        sURIMatcher.addURI(AUTHORITY, TABLE + "/#", ARTJOB_ID);
    }



    @Override
    public boolean onCreate() {
        mDbHelper = new MuseumDbHelper(getContext());
        return true;
    }

    @Override //DONE
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MuseumContract.MuseumInfo.TABLE_NAME);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case ARTJOB_ID:
                queryBuilder.appendWhere(MuseumContract.MuseumInfo.COLUMN_NUMBER_ART + "="
                        + uri.getLastPathSegment());
                break;
            case ARTJOBS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(mDbHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override //DONE
    public Uri insert(Uri uri, ContentValues values) {

        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase sqlDB = mDbHelper.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case ARTJOBS:
                id = sqlDB.insert(MuseumContract.MuseumInfo.TABLE_NAME,
                        null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(TABLE + "/" + id);

    }

    @Override //DONE
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDbHelper.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case ARTJOBS:
                rowsDeleted = sqlDB.delete(MuseumContract.MuseumInfo.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case ARTJOB_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(MuseumContract.MuseumInfo.TABLE_NAME,
                            MuseumContract.MuseumInfo.COLUMN_NUMBER_ART + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(MuseumContract.MuseumInfo.TABLE_NAME,
                            MuseumContract.MuseumInfo.COLUMN_NUMBER_ART + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override //DONE
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDbHelper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case ARTJOBS:
                rowsUpdated = sqlDB.update(MuseumContract.MuseumInfo.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case ARTJOB_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated =
                            sqlDB.update(MuseumContract.MuseumInfo.TABLE_NAME,
                                    values,
                                    MuseumContract.MuseumInfo.COLUMN_NUMBER_ART + "=" + id,
                                    null);
                } else {
                    rowsUpdated =
                            sqlDB.update(MuseumContract.MuseumInfo.TABLE_NAME,
                                    values,
                                    MuseumContract.MuseumInfo.COLUMN_NUMBER_ART + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;

    }
}
