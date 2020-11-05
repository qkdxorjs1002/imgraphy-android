package com.teamig.imgraphy.ui.viewer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.teamig.imgraphy.tool.TagParser;

import java.util.List;

public class ViewerViewModel extends ViewModel {

    public ViewerViewModel() {

    }

    public LiveData<List<String>> parseTag(String tags) {
        MutableLiveData<List<String>> parsedTag = new MutableLiveData<>();
        parsedTag.setValue(TagParser.parse(tags));

        return parsedTag;
    }
}