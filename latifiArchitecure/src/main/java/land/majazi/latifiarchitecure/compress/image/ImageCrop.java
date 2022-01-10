package land.majazi.latifiarchitecure.compress.image;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.manager.DialogManager;
import land.majazi.latifiarchitecure.manager.FileManager;
import land.majazi.latifiarchitecure.views.customs.buttons.MLButton;
import land.majazi.latifiarchitecure.views.fragments.FragmentAction;

public class ImageCrop {


    private final Context context;
    private Dialog cropImageDialog;
    private final String applicationName;
    private final FragmentAction fragmentAction;

    //______________________________________________________________________________________________ ImageCrop
    public ImageCrop(Context context, String applicationName, FragmentAction fragmentAction) {
        this.context = context;
        this.applicationName = applicationName;
        this.fragmentAction = fragmentAction;
    }
    //______________________________________________________________________________________________ ImageCrop


    //______________________________________________________________________________________________ showDialogCropImage
    public void showDialogCropImage(Uri uri) {


        if (cropImageDialog != null) {
            cropImageDialog.dismiss();
            cropImageDialog = null;
        }

        cropImageDialog = new DialogManager().createDialog(context, R.layout.dialog_crop_image, true);
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
                                    .execute(fileManager.createSaveUri(context, applicationName), mSaveCallback);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                    });
        });

        cropImageDialog.show();
    }
    //______________________________________________________________________________________________ showDialogCropImage


    //______________________________________________________________________________________________ getLoadCallback
    public LoadCallback getLoadCallback() {

        return new LoadCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };
    }
    //______________________________________________________________________________________________ getLoadCallback


    //______________________________________________________________________________________________ mSaveCallback
    private final SaveCallback mSaveCallback = new SaveCallback() {
        @Override
        public void onSuccess(Uri outputUri) {
            fragmentAction.cropImage(outputUri);
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



    //______________________________________________________________________________________________ saveBitmap
    public void saveBitmap(Bitmap bitmap, String appName) {
        FileManager fileManager = new FileManager();
        CropImageView mCropView = new CropImageView(context);
        mCropView.save(bitmap).execute(fileManager.createSaveUri(context, appName), mSaveCallback);
    }
    //______________________________________________________________________________________________ saveBitmap


}
