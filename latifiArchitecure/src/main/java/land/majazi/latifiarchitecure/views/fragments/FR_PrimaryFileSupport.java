package land.majazi.latifiarchitecure.views.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import java.io.File;

import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.manager.FileManager;
import land.majazi.latifiarchitecure.views.activity.RecordVideo;
import land.majazi.latifiarchitecure.views.customs.buttons.MLButton;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class FR_PrimaryFileSupport extends FR_Primary {

    private int REQUEST_CHOOSE_PICTURE = 7127;
    private int REQUEST_TAKE_VIDEO = 7126;
    private Dialog chooseImageDialog;
    private Dialog cropImageDialog;
    private Uri uriFromCamera;
    private static String applicationName;
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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        FileManager fileManager = new FileManager();
        uriFromCamera = fileManager.getOutputMediaFileUri(getActivity(), applicationName, MEDIA_TYPE_IMAGE);
        fileManager = null;
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
        applicationName = appName;
        FileManager fileManager = new FileManager();
        File fileFromCamera = fileManager.getOutputMediaFile(MEDIA_TYPE_VIDEO, applicationName);
        uriFromCamera = fileManager.getUriFromFile(getActivity(), fileFromCamera);
        fileManager = null;
        Intent intent = new Intent(getContext(), RecordVideo.class);
        intent.putExtra("INTENT_NAME_VIDEO_PATH", fileFromCamera.getPath());
        intent.putExtra("INTENT_NAME_VIDEO_TEXT", message);
        startActivityForResult(intent, REQUEST_TAKE_VIDEO);


/*        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFromCamera);
        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        cameraIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
        cameraIntent.putExtra(Intent.EXTRA_TEXT, "Hi,This is Test");
        startActivityForResult(cameraIntent, REQUEST_TAKE_VIDEO);*/


//        uriFromCamera = fileController.getOutputMediaFileUri(getActivity(), appName, MEDIA_TYPE_VIDEO);

/*        SandriosCamera.outFile = fileFromCamera;
        SandriosCamera
                .with()
                .setShowPicker(true)
                //.setShowPickerType(CameraConfiguration.VIDEO)
                .setVideoFileSize(20)
                .setMediaAction(CameraConfiguration.MEDIA_ACTION_BOTH)
                .enableImageCropping(true)
                .launchCamera(FR_Primary.this, REQUEST_TAKE_VIDEO);*/

/*        Intent intent = new Intent(getContext(), FFmpegRecordActivity.class);
        intent.putExtra(FFmpegRecordActivity.INTENT_NAME_VIDEO_PATH, fileFromCamera.getPath());
        startActivityForResult(intent, REQUEST_TAKE_VIDEO);*/

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
                    showDialogCropImage(uriFromCamera);
                else
                    cropImage(uriFromCamera);
            } else if (requestCode == REQUEST_TAKE_VIDEO) {
                cropImage(uriFromCamera);
            }
        }
    }
    //______________________________________________________________________________________________ onActivityResult


    //______________________________________________________________________________________________ showDialogCropImage
    public void showDialogCropImage(Uri uri) {

        if (chooseImageDialog != null) {
            chooseImageDialog.dismiss();
            chooseImageDialog = null;
        }

        if (cropImageDialog != null) {
            cropImageDialog.dismiss();
            cropImageDialog = null;
        }

        cropImageDialog = createDialog(R.layout.dialog_crop_image, true);
        MLButton ml_buttonCrop = cropImageDialog.findViewById(R.id.ml_buttonCrop);
        ml_buttonCrop.setTitle("تایید");
        CropImageView mCropView = cropImageDialog.findViewById(R.id.cropImageView);
        mCropView.load(uri).execute(getLoadCallback());

        ImageView imageViewRotateLeft = cropImageDialog.findViewById(R.id.imageViewRotateLeft);
        ImageView imageViewRotateRight = cropImageDialog.findViewById(R.id.imageViewRotateRight);

        imageViewRotateLeft.setOnClickListener(v -> mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D));
        imageViewRotateRight.setOnClickListener(v -> mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D));

        ml_buttonCrop.setOnClickListener(v -> {
            ml_buttonCrop.setEnabled(false);
            ml_buttonCrop.setTitle("درحال آماده سازی...");
//            ml_buttonCrop.startLoading();
            ml_buttonCrop.setAlpha(0.3f);
            mCropView.crop(uri)
                    .execute(new CropCallback() {
                        @Override
                        public void onSuccess(Bitmap cropped) {
                            FileManager fileManager = new FileManager();
                            mCropView.save(cropped)
                                    .execute(fileManager.createSaveUri(getContext(), applicationName), mSaveCallback);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                    });
        });

        cropImageDialog.show();
    }
    //______________________________________________________________________________________________ showDialogCropImage


    //______________________________________________________________________________________________ saveBitmap
    public void saveBitmap(Bitmap bitmap, String appName) {
        FileManager fileManager = new FileManager();
        CropImageView mCropView = new CropImageView(getContext());
        mCropView.save(bitmap)
                .execute(fileManager.createSaveUri(getContext(), appName), mSaveCallback);
    }
    //______________________________________________________________________________________________ saveBitmap


    //______________________________________________________________________________________________ getLoadCallback
    public LoadCallback getLoadCallback() {

        LoadCallback mLoadCallback = new LoadCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };

        return mLoadCallback;
    }
    //______________________________________________________________________________________________ getLoadCallback


    //______________________________________________________________________________________________ mSaveCallback
    private SaveCallback mSaveCallback = new SaveCallback() {
        @Override
        public void onSuccess(Uri outputUri) {
            cropImage(outputUri);
            if (cropImageDialog != null) {
                cropImageDialog.dismiss();
                cropImageDialog = null;
            }
        }

        @Override
        public void onError(Throwable e) {
            if (cropImageDialog != null) {
                cropImageDialog.dismiss();
                cropImageDialog = null;
            }
        }
    };
    //______________________________________________________________________________________________ mSaveCallback


}
