package com.gayo.maru.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeetCountViewModel extends ViewModel {

    private final MutableLiveData<Integer> meetCount = new MutableLiveData<Integer>();
    public  void meetCount(Integer integer){
        meetCount.setValue(integer);
    }

    public LiveData<Integer> getMeetCount(){
        return meetCount;
    }
}
