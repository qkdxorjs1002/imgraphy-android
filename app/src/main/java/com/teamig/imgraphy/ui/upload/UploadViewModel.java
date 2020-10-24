package com.teamig.imgraphy.ui.upload;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.teamig.imgraphy.service.Imgraphy;
import com.teamig.imgraphy.service.ImgraphyType;

import java.io.File;

public class UploadViewModel extends ViewModel {

    private final Imgraphy imgraphy;

    public UploadViewModel() {
        imgraphy = new Imgraphy();
    }

    public LiveData<ImgraphyType.Result> uploadFile(ImgraphyType.Options.Upload option) {

        return imgraphy.uploadGraphy(option);
    }
}