package com.example.tas_pam.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tas_pam.R;

public class HomeFragment extends Fragment {

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    final TextView textView = root.findViewById(R.id.text_home);
    textView.setText(R.string.home_fragment_text);
    return root;
  }
}
