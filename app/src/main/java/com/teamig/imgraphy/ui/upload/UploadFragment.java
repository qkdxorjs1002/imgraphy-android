package com.teamig.imgraphy.ui.upload;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.teamig.imgraphy.MediaUriProvider;
import com.teamig.imgraphy.R;
import com.teamig.imgraphy.service.ImgraphyType;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadFragment extends Fragment {

    private UploadViewModel viewModel;
    private View root;

    private EditText uploadFormTag;
    private RadioGroup uploadFormLicense;
    private TextView uploadFormUserID;
    private Button uploadFormButton;

    private ImgraphyType.Options.Upload uploadOption;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(UploadViewModel.class);
        root = inflater.inflate(R.layout.fragment_upload, container, false);

        uploadFormTag = (EditText) root.findViewById(R.id.UploadFormTag);
        uploadFormLicense = (RadioGroup) root.findViewById(R.id.UploadFormLicense);
        uploadFormUserID = (TextView) root.findViewById(R.id.UploadFormUserID);
        uploadFormButton = (Button) root.findViewById(R.id.UploadFormButton);

        uploadOption = new ImgraphyType.Options.Upload(null, 1, "user-test", null);

        uploadFormUserID.setText(uploadOption.uploader);

        uploadFormTag.setOnKeyListener((v, keyCode, event) -> {
            uploadOption.tag = uploadFormTag.getText().toString();

            return false;
        });

        uploadFormLicense.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.License2 :
                    uploadOption.license = 2;
                    break;

                case R.id.License3 :
                    uploadOption.license = 3;
                    break;

                case R.id.License4 :
                    uploadOption.license = 4;
                    break;

                default :
                    uploadOption.license = 1;
            }
        });

        uploadFormButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivityForResult(Intent.createChooser(intent,"Select Image"), 1002);
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}