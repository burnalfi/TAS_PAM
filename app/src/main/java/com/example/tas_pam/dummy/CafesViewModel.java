package com.example.tas_pam.dummy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tas_pam.dummy.DummyContent;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CafesViewModel extends ViewModel {
  private static CafesViewModel cafesViewModel;
  private MutableLiveData<List<DummyContent.Cafe>> cafes;
  private static List<DummyContent.Cafe> cafesFromPlaceApi;
  public CafesViewModel() {
    cafes = new MutableLiveData<>();
    cafes.setValue(null);
  }
  public static synchronized CafesViewModel getInstance() {
    if (cafesViewModel==null) {
      cafesViewModel= new CafesViewModel();
    }
    return cafesViewModel;
  }

  public static List<DummyContent.Cafe> getCafesFromPlaceApi() {
    return cafesFromPlaceApi;
  }

  public static void setCafesFromPlaceApi(List<DummyContent.Cafe> cafesFromPlaceApi) {
    CafesViewModel.cafesFromPlaceApi = cafesFromPlaceApi;
  }

  public void setCafes(List<DummyContent.Cafe> data) {
    cafes.setValue(data);
  }

  public LiveData<List<DummyContent.Cafe>> getCafes() {
    return cafes;
  }

  public boolean addCafe(DummyContent.Cafe x) {
    List<DummyContent.Cafe> exisitingCafes= cafes.getValue();
    if (exisitingCafes.add(x)) {
      cafes.setValue(exisitingCafes);
      return true;
    } else {
      System.err.println("err populating live data!!!! ");
      return false;
    }
  }

  public void addCafes(List<DummyContent.Cafe> cx) {
    List<DummyContent.Cafe> exisitingCafes= cafes.getValue();
    exisitingCafes.addAll(cx);
    cafes.setValue(exisitingCafes);
  }
}