package com.teamig.imgraphy.ui.upload;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.teamig.imgraphy.service.Imgraphy;
import com.teamig.imgraphy.service.ImgraphyType;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UploadViewModel extends ViewModel {

    private final Imgraphy imgraphy;

    public UploadViewModel() {
        imgraphy = new Imgraphy();
    }

    public LiveData<ImgraphyType.Result> uploadFile(ImgraphyType.Options.Upload option) {

        return imgraphy.uploadGraphy(option);
    }

    public LiveData<byte[]> getByte(InputStream inputStream) {
        MutableLiveData<byte[]> byteData = new MutableLiveData<>();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            while(inputStream.available() != 0) {
                bos.write(inputStream.read());
            }
            byteData.setValue(bos.toByteArray());

            inputStream.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteData;
    }
}