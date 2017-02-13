package fr.arnaudguyon;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by aguyon on 13.02.17.
 */

public class AutoGridAdapter extends ArrayAdapter<String> {

    private static @LayoutRes int ITEM_ID = R.layout.grid_item;

    public AutoGridAdapter(Context context) {
        super(context, ITEM_ID);
    }

    @Override
    public int getCount() {
        return 13;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return "Item " + position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        } else {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            return inflater.inflate(ITEM_ID, parent, false);
        }
    }
}
