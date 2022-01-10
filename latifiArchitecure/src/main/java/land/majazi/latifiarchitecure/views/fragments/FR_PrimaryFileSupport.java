package land.majazi.latifiarchitecure.views.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.TextView;
import java.io.File;

import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.compress.image.ImageCrop;
import land.majazi.latifiarchitecure.manager.FileManager;
import land.majazi.latifiarchitecure.views.activity.RecordVideo;
import land.majazi.latifiarchitecure.views.customs.buttons.MLButton;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class FR_PrimaryFileSupport extends FR_Primary {

    private final int REQUEST_CHOOSE_PICTURE = 7127;
    private final int REQUEST_TAKE_VIDEO = 7126;
    private Dialog chooseImageDialog;
    private Uri uriFromCamera;
    private String applicationName;
    private boolean crop;


    //______________________________________________________________________________________________ showDialogChooseImage
    public void showDialogChooseImage(String title, String appName, boolean crop) {
        this.crop = crop;
        applicationName = appName;
        if (chooseImageDialog != null) {
            chooseImageDialog.dismiss();
            chooseImageDialog = null;
        }
        uriFromCamera = null;
        chooseImageDialog = createDialog(R.layout.dialog_choose_image, true);
        MLButton ml_buttonGallery = chooseImageDialog.findViewById(R.id.ml_buttonGallery);
        MLButton ml_buttonTakePhoto = chooseImageDialog.findViewById(R.id.ml_buttonTakePhoto);
        TextView textViewDialogTitle = chooseImageDialog.findViewById(R.id.textViewDialogTitle);
        textViewDialogTitle.setText(title);
        ml_buttonGallery.setTitle("گالری");
        ml_buttonTakePhoto.setTitle("دوربین");

        ml_buttonGallery.setOnClickListener(v -> chooseImageFromGallery());
        ml_buttonTakePhoto.setOnClickListener(v -> takePhoto());

        chooseImageDialog.show();
    }
    //______________________________________________________________________________________________ showDialogChooseImage


    //______________________________________________________________________________________________ chooseImageFromGallery
    public void chooseImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "انتخاب تصویر"), REQUEST_CHOOSE_PICTURE);
        if (chooseImageDialog != null) {
            chooseImageDialog.dismiss();
            chooseImageDialog = null;
        }
    }
    //______________________________________________________________________________________________ chooseImageFromGallery


    //______________________________________________________________________________________________ chooseImageFromGallery
    public void takePhoto() {
        if (getActivity() == null)
            return;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        FileManager fileManager = new FileManager();
        uriFromCamera = fileManager.getOutputMediaFileUri(getActivity(), applicationName, MEDIA_TYPE_IMAGE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFromCamera);
        startActivityForResult(cameraIntent, REQUEST_CHOOSE_PICTURE);
        if (chooseImageDialog != null) {
            chooseImageDialog.dismiss();
            chooseImageDialog = null;
        }
    }
    //______________________________________________________________________________________________ chooseImageFromGallery


    //______________________________________________________________________________________________ takeVideo
    public void takeVideo(String message, String appName) {
        if (getActivity() == null)
            return;
        applicationName = appName;
        FileManager fileManager = new FileManager();
        File fileFromCamera = fileManager.getOutputMediaFile(MEDIA_TYPE_VIDEO, applicationName);
        uriFromCamera = fileManager.getUriFromFile(getActivity(), fileFromCamera);
        Intent intent = new Intent(getContext(), RecordVideo.class);
        intent.putExtra("INTENT_NAME_VIDEO_PATH", fileFromCamera.getPath());
        intent.putExtra("INTENT_NAME_VIDEO_TEXT", message);
        startActivityForResult(intent, REQUEST_TAKE_VIDEO);

    }
    //______________________________________________________________________________________________ takeVideo


    //______________________________________________________________________________________________ onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PICTURE) {
                if (uriFromCamera == null)
                    uriFromCamera = data.getData();
                if (crop)
                    new ImageCrop(getContext(), applicationName, FR_PrimaryFileSupport.this).showDialogCropImage(uriFromCamera);
                else
                    cropImage(uriFromCamera);
            } else if (requestCode == REQUEST_TAKE_VIDEO) {
                cropImage(uriFromCamera);
            }
        }
    }
    //______________________________________________________________________________________________ onActivityResult

}
