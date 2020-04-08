package com.example.tas_pam;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tas_pam.dummy.DummyContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CafeFragment extends Fragment {

  // TODO: Customize parameter argument names
  private static final String ARG_COLUMN_COUNT = "column-count";
  // TODO: Customize parameters
  private int mColumnCount = 1;
  private RecyclerView recyclerView;
  private OnListFragmentInteractionListener mListener;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public CafeFragment() {
  }

  Observer<List<DummyContent.Cafe>> cafeFragmentObserver= new Observer<List<DummyContent.Cafe>>() {
    @Override
    public void onChanged(List<DummyContent.Cafe> cafes) {
      recyclerView.setAdapter(new MyCafeRecyclerViewAdapter(cafes, mListener));
    }
  };
  // TODO: Customize parameter initialization
  @SuppressWarnings("unused")
  public static CafeFragment newInstance(int columnCount) {
    CafeFragment fragment = new CafeFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
    MainActivity ma= (MainActivity) getActivity();
    ma.mainViewModel.getCafes().observe(this, cafeFragmentObserver);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_cafe_list, container, false);
    // Set the adapter
    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      this.recyclerView = (RecyclerView) view;
      if (mColumnCount <= 1) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      } else {
        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
      }
      recyclerView.setAdapter(new MyCafeRecyclerViewAdapter(DummyContent.ITEMS, mListener));
//      recyclerView.setAdapter(new MyCafeRecyclerViewAdapter(DummyContent.ITEMS, mListener)); ori from AS
<<<<<<< HEAD
//      MainActivity ma= (MainActivity) getActivity();
//      recyclerView.setAdapter(new MyCafeRecyclerViewAdapter((List<DummyContent.Cafe>) ma.mainViewModel.getCafes(), mListener));
=======
>>>>>>> 41863978276096a804eb3d99815756484081e40e
    }
    return view;
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    System.err.println("CafeFrag attached");
    Log.e("adi", "CafeFrag onAttach: ");
    if (context instanceof OnListFragmentInteractionListener) {
      mListener = (OnListFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
              + " must implement OnListFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnListFragmentInteractionListener {
    // TODO: Update argument type and name
    void onListFragmentInteraction(DummyContent.Cafe item);
  }


}
