package com.example.tas_pam.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tas_pam.dummy.DummyContent;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SlideshowViewModel extends ViewModel {

  private MutableLiveData<List<DummyContent.Cafe>> cafes;

  public SlideshowViewModel() {
    cafes = new MutableLiveData<>();
    cafes.setValue(null);
  }

  public void setCafes(List<DummyContent.Cafe> data) {
    cafes.setValue(data);
  }

  public LiveData<List<DummyContent.Cafe>> getCafes() {
    return cafes;
  }

  public void addCafe(DummyContent.Cafe x) {
    List<DummyContent.Cafe> exisitingCafes= cafes.getValue();
    if (exisitingCafes.add(x)) {
      cafes.setValue(exisitingCafes);
    } else {
      System.err.println("err populating live data!!!! ");
    }
  }
}