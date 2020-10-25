package com.teamig.imgraphy.ui.upload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamig.imgraphy.R;
import com.teamig.imgraphy.service.ImgraphyType;

import java.io.FileNotFoundException;
import java.io.InputStream;

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

        uploadFormUserID.setText("user-test");

        uploadFormTag.setOnKeyListener((v, keyCode, event) -> {
            uploadOption.tag = uploadFormTag.getText().toString();

            return false;
        });

        uploadFormLicense.setOnCheckedChangeListener((group, checkedId) -> {
            int license = 1;

            switch (checkedId) {
                case R.id.License2 :
                    license = 2;
                    break;

                case R.id.License3 :
                    license = 3;
                    break;

                case R.id.License4 :
                    license = 4;
                    break;
            }

            uploadOption.license = license;
        });

        uploadFormButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                    .setType("image/*")
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivityForResult(Intent.createChooser(intent,"Select Image"), 1002);
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1002 && resultCode == Activity.RESULT_OK && data != null) {
            InputStream inputStream = null;

            String type = root.getContext().getContentResolver().getType(data.getData());
            String ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(type);

            try {
                inputStream = root.getContext().getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            viewModel.getByte(inputStream).observe(getViewLifecycleOwner(), byteData -> {
                RequestBody requestFile = RequestBody.create(MediaType.parse(type), byteData);
                uploadOption.uploadfile = MultipartBody.Part.createFormData(
                        ImgraphyType.Options.Upload.UPLOAD_FILE,
                        String.format("%s.%s", uploadOption.uploader, ext),
                        requestFile
                );

                viewModel.uploadFile(uploadOption).observe(getViewLifecycleOwner(), result -> {
                    Toast.makeText(this.getContext(), result.code + ": " + result.log, Toast.LENGTH_LONG).show();
                });
            });
        }
    }
}