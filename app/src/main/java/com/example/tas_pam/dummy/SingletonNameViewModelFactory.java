//credit https://medium.com/@droidbobakr/using-viewmodel-between-many-activities-d39bfad8a7ed
package com.example.tas_pam.dummy;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SingletonNameViewModelFactory extends ViewModelProvider.NewInstanceFactory {
  private CafesViewModel cafesViewModel;

  private final Map<Class<? extends ViewModel>, ViewModel> mFactory = new HashMap<>();

  public SingletonNameViewModelFactory(CafesViewModel myViewModel) {
    this.cafesViewModel = myViewModel;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(final @NonNull Class<T> modelClass) {
    mFactory.put(modelClass, cafesViewModel);

    if (CafesViewModel.class.isAssignableFrom(modelClass)) {
      CafesViewModel shareVM = null;

      if (mFactory.containsKey(modelClass)) {
        shareVM = (CafesViewModel) mFactory.get(modelClass);
      } else {
        try {
          shareVM = (CafesViewModel) modelClass.getConstructor(Runnable.class).newInstance(new Runnable() {
            @Override
            public void run() {
              mFactory.remove(modelClass);
            }
          });
        } catch (NoSuchMethodException e) {
          throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        } catch (InstantiationException e) {
          throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        } catch (InvocationTargetException e) {
          throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
        mFactory.put(modelClass, shareVM);
      }

      return (T) shareVM;
    }
    return super.create(modelClass);
  }

}