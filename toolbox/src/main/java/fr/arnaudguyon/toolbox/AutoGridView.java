package fr.arnaudguyon.toolbox;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created by aguyon on 13.02.17.
 */

public class AutoGridView extends LinearLayout {

    private int mNumColumns = 3;
    private BaseAdapter mAdapter;
    private DataSetObserver mObserver;
    private boolean mMatchParent = false;

    public AutoGridView(Context context) {
        super(context);
    }

    public AutoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if ((mAdapter != null) && (mObserver == null)) {
            mObserver = new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    onDataChanged();
                }
            };
            mAdapter.registerDataSetObserver(mObserver);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if ((mAdapter != null) && (mObserver != null)) {
            mAdapter.unregisterDataSetObserver(mObserver);
            mObserver = null;
        }
    }

    public void setAdapter(BaseAdapter adapter) {
        if (adapter == mAdapter) {
            return;
        }
        if (mAdapter != null) {
            if (mObserver != null) {
                mAdapter.unregisterDataSetObserver(mObserver);
                mObserver = null;
            }
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mObserver = new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    onDataChanged();
                }
            };
            mAdapter.registerDataSetObserver(mObserver);
        }
        refresh();
    }

    public void setNumColumns(int numColumns) {
        if (numColumns != mNumColumns) {
            mNumColumns = numColumns;
            refresh();
        }
    }

    public void setMatchParent(boolean matchParent) {
        if (matchParent != mMatchParent) {
            mMatchParent = matchParent;
            refresh();
        }
    }

    private void refresh() {

        setOrientation(VERTICAL);
        removeAllViews();

        if ((mAdapter == null) || (mNumColumns <= 0)) {
            return;
        }

        LinearLayout horizontal = null;
        for (int position = 0; position < mAdapter.getCount(); ++position) {

            View view = mAdapter.getView(position, null, this);

            if (mMatchParent) {
                LinearLayout.LayoutParams current = (LayoutParams) view.getLayoutParams();
                LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, current.height);
                params.weight = 1f / mNumColumns;
                view.setLayoutParams(params);
            }

            if ((position % mNumColumns) == 0) {
                horizontal = new LinearLayout(getContext(), null); //LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                horizontal.setOrientation(HORIZONTAL);
                addView(horizontal);
            }
            horizontal.addView(view);
        }

        if (mMatchParent && (horizontal != null)) {
            while (horizontal.getChildCount() < mNumColumns) {
                View view = new View(getContext());
                LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                params.weight = 1f / mNumColumns;
                view.setLayoutParams(params);
                horizontal.addView(view);
            }
        }

        invalidate();
    }

    private void onDataChanged() {
        refresh();
    }
}
