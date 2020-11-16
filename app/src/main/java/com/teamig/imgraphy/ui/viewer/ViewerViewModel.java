package com.teamig.imgraphy.ui.viewer;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.util.ByteBufferUtil;
import com.teamig.imgraphy.R;
import com.teamig.imgraphy.service.Imgraphy;
import com.teamig.imgraphy.service.ImgraphyType;
import com.teamig.imgraphy.tool.TagParser;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class ViewerViewModel extends ViewModel {

    MutableLiveData<String> userID;
    MutableLiveData<String> keyword;
    MutableLiveData<ImgraphyType.Graphy> graphy;

    private final Imgraphy imgraphy;

    public ViewerViewModel() {
        userID = new MutableLiveData<>();
        keyword = new MutableLiveData<>();
        graphy = new MutableLiveData<>();

        imgraphy = new Imgraphy();
    }

    public LiveData<Integer> parseLicense(int license) {
        MutableLiveData<Integer> parsedLicense = new MutableLiveData<>();
        int resId = R.string.license_non;

        switch (license) {
            case 1:
                resId = R.string.license_att;
                break;
            case 2:
                resId = R.string.license_ncc;
                break;
            case 3:
                resId = R.string.license_ndw;
                break;
            case 4:
                resId = R.string.license_sha;
                break;
        }
        parsedLicense.setValue(resId);

        return parsedLicense;
    }

    public LiveData<List<String>> parseTag(String tags) {
        MutableLiveData<List<String>> parsedTag = new MutableLiveData<>();
        parsedTag.setValue(TagParser.parse(tags));

        return parsedTag;
    }

    public LiveData<ImgraphyType.Result> deprecateGraphy(boolean confirm, String uuid) {

        return imgraphy.deprecateGraphy(confirm, uuid);
    }
    
    public LiveData<Uri> copyCacheToFile(File storageDir, File resource) {
        MutableLiveData<Uri> imageUri = new MutableLiveData<>();

        File imagePath = null;
        try {
            imagePath = File.createTempFile("graphy_" + graphy.getValue().uuid, "." + graphy.getValue().ext,
                    storageDir);

            FileInputStream fileInputStream = new FileInputStream(resource);
            ByteBuffer byteBuffer = ByteBufferUtil.fromStream(fileInputStream);

            ByteBufferUtil.toFile(byteBuffer, imagePath);

            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageUri.setValue(Uri.parse(imagePath.getAbsolutePath()));

        return imageUri;
    }
}