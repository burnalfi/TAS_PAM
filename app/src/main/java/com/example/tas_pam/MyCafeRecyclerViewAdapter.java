package com.example.tas_pam;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tas_pam.CafeFragment.OnListFragmentInteractionListener;
import com.example.tas_pam.dummy.DummyContent;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.example.tas_pam.dummy.DummyContent.Cafe} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCafeRecyclerViewAdapter extends RecyclerView.Adapter<MyCafeRecyclerViewAdapter.ViewHolder> {

  private final List<DummyContent.Cafe> mCafes;
  private final OnListFragmentInteractionListener mListener;

  public MyCafeRecyclerViewAdapter(List<DummyContent.Cafe> items, OnListFragmentInteractionListener listener) {
    mCafes = items;
    mListener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_cafe, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.mItem = mCafes.get(position);
    holder.mIdView.setText(mCafes.get(position).id);
    holder.mContentView.setText(mCafes.get(position).description);

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (null != mListener) {
          // Notify the active callbacks interface (the activity, if the
          // fragment is attached to one) that an item has been selected.
          mListener.onListFragmentInteraction(holder.mItem);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return mCafes.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mIdView;
    public final TextView mContentView;
    public DummyContent.Cafe mItem;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      mIdView = (TextView) view.findViewById(R.id.item_number);
      mContentView = (TextView) view.findViewById(R.id.content);
    }

    @Override
    public String toString() {
      return super.toString() + " '" + mContentView.getText() + "'";
    }
  }
}
