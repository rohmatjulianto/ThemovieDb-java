package com.subdico.moviecatalogue4.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.subdico.moviecatalogue4.MainActivity;
import com.subdico.moviecatalogue4.db.FavHelper;
import com.subdico.moviecatalogue4.ui.Favorites.pager_favorites.SeriesFavoritesFragment;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.subdico.moviecatalogue4.db.DbContract.AUTHORITY;
import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.CONTENT_URI;
import static com.subdico.moviecatalogue4.db.DbContract.TABLE_NAME;


public class FavProvider extends ContentProvider {
    private static final int favSaved = 1;
    private static final int favSavedAll = 2;

    private static final UriMatcher sUrimatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavHelper favHelper;

    static {
        sUrimatcher.addURI(AUTHORITY,TABLE_NAME, favSaved);
        sUrimatcher.addURI(AUTHORITY,TABLE_NAME + "/all", favSavedAll);
    }
    @Override
    public boolean onCreate() {
        favHelper = FavHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        favHelper.open();
        Cursor cursor;
        switch (sUrimatcher.match(uri)){
            case favSaved:
                cursor = favHelper.getFavProvider(s);
            break;
            case favSavedAll:
                cursor = favHelper.getFavProviderAll();
                break;
            default:
                cursor = null;
                break;

        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        favHelper.open();
        long added;
        switch (sUrimatcher.match(uri)){
            case favSaved:
                added = favHelper.insertFavProvider(contentValues);
                break;
            default:
                added = 0;
                break;

        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        favHelper.open();
        int deleted;
        switch (sUrimatcher.match(uri)){
            case favSaved:
                deleted = favHelper.deleteFavProvider(s);
                break;
            default:
                deleted = 0;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
