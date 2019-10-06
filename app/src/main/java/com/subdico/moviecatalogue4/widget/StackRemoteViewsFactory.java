package com.subdico.moviecatalogue4.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.subdico.moviecatalogue4.R;
import com.subdico.moviecatalogue4.model.ListData;
import com.subdico.moviecatalogue4.model.WidgetItems;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.subdico.moviecatalogue4.db.DbContract.NoteColumns.CONTENT_URI;
import static com.subdico.moviecatalogue4.helper.MappingHelper.mapCursorToArrayList;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final ArrayList<WidgetItems> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private Cursor cursor;


    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mWidgetItems.clear();

        if ( cursor != null){
            cursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();
        // querying ke database
        if (cursor != null){
            cursor.close();
        }
        Uri uriAll = Uri.parse(CONTENT_URI + "/all");
        cursor = mContext.getContentResolver().query(uriAll, null,null , null, null);

        ArrayList<ListData> listData =  mapCursorToArrayList(cursor);
        if(listData.size() > 0){

            for (ListData mLisdata : listData){
                String name = mLisdata.getName();
                Bitmap image = null;
                try {
                    image = Glide.with(mContext)
                            .asBitmap()
                            .load(mLisdata.getPoster_path())
                            .submit()
                            .get();
                }catch (ExecutionException | InterruptedException e){
                    Log.d("XXX", "onDataSetChanged: " + e.getMessage());
                }

                if (image == null)
                    image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_image);

                mWidgetItems.add(new WidgetItems(name, image));
            }
        }

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        if (mWidgetItems.size() > 0){
            rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position).getImgPoster());
        }else{
            rv.setImageViewBitmap(R.id.imageView, null);
        }

        Bundle extras = new Bundle();
        extras.putString(WidgetFav.EXTRA_ITEM, mWidgetItems.get(position).getName());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
