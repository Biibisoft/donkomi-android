package com.pongo.biibisoft.donkomi_android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * In case you forget to brind in some libraries for this helper, links are down below
 * https://github.com/ArthurHub/Android-Image-Cropper
 */
public class ImageUploadHelper {
  private Context context;

  public ImageUploadHelper(Context context) {
    this.context = context;
  }

  public String getFileExtension(Uri uri) {
    ContentResolver cr = context.getContentResolver();
    MimeTypeMap mime = MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(cr.getType(uri));
  }

  public void uploadImageToFirebase(StorageReference firebaseStorageRef, Uri imageUri, String tinyDesc, String uniqueString, byte[] imageInBytes, final CollectUploadedImageURI imageCallback) {
//    String ext = getFileExtension(imageUri);
    String filename = uniqueString + tinyDesc + System.currentTimeMillis()  ;
    final StorageReference fileReference = firebaseStorageRef.child(filename);
    fileReference.putBytes(imageInBytes)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
              @Override
              public void onSuccess(Uri uri) {
                imageCallback.getURI(uri);
              }
            });
          }
        }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  public static Bitmap changeBytesToBitmap(byte[] compressedImage) {
    Bitmap bitmap = BitmapFactory.decodeByteArray(compressedImage, 0, compressedImage.length);
    return bitmap;
  }

  public void openFileChooserWithCropper(Activity activity, int aspectRationX, int aspectRatioY) {
    CropImage.activity()
        .setGuidelines(CropImageView.Guidelines.ON)
        .setAspectRatio(aspectRationX, aspectRatioY)
        .start(activity);
  }

  public void collectCroppedImage(int requestCode, int resultCode, @Nullable Intent data, CroppingImageCallback croppingResult) {
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == RESULT_OK) {
        Uri resultUri = result.getUri();
        croppingResult.getCroppedImage(result.getUri());
      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        Exception error = result.getError();
        croppingResult.getCroppingError(error);
      }
    }

  }

  public void compressImageToBytes(Uri uri, final CompressedImageToBytesCallback callback) {
    new BackgroundCompressorToBytes(context.getContentResolver(), new CompressedImageToBytesCallback() {
      @Override
      public void getCompressedImage(byte[] compressedImage) {
        callback.getCompressedImage(compressedImage);
      }
    }).execute(uri);
  }

  public void compressImage(Uri uri, final CompressedImageCallback callback) {
    new BackgroundCompressor(context.getContentResolver(), new CompressedImageCallback() {
      @Override
      public void getCompressedImage(Bitmap compressedBitmap) {
        callback.getCompressedImage(compressedBitmap);
      }
    }).execute(uri);
  }


  public void openFileChooser(FileChooserCallback fileChooserCallback) {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    fileChooserCallback.getBackChooserIntent(intent);
  }

  public interface CollectUploadedImageURI {
    void getURI(Uri uri);
  }

  public interface CroppingImageCallback {
    void getCroppedImage(Uri uri);

    void getCroppingError(Exception e);
  }

  public interface CompressedImageCallback {
    void getCompressedImage(Bitmap compressedBitmap);
  }

  public interface CompressedImageToBytesCallback {
    void getCompressedImage(byte[] compressedImage);
  }

  public interface FileChooserCallback {
    /**
     * returns the image file picker intent
     * Just call "startActivityForResult" with this intent and be on your way
     */
    void getBackChooserIntent(Intent intent);
  }

//  ============================================================================================

  public class BackgroundCompressor extends AsyncTask<Uri, Integer, Bitmap> {
    ContentResolver resolver;
    Bitmap bitmap;
    CompressedImageCallback imageCallback;

    public BackgroundCompressor(ContentResolver resolver, CompressedImageCallback imageCallback) {
      this.resolver = resolver;
      this.imageCallback = imageCallback;
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap, Integer quality) {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
      return stream.toByteArray();
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Uri... uris) {
      try {
        bitmap = MediaStore.Images.Media.getBitmap(resolver, uris[0]);
      } catch (Exception e) {
        Log.w("errorOnResizing", e.getMessage());
      }
      byte[] bytesArr = getBytesFromBitmap(bitmap, 60);
      Bitmap bitmap = BitmapFactory.decodeByteArray(bytesArr, 0, bytesArr.length);
      return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
      super.onPostExecute(bitmap);
      imageCallback.getCompressedImage(bitmap);
    }
  }


  // ==================================== ASYNC COMPRESSION TO BYTES ====================================
  public class BackgroundCompressorToBytes extends AsyncTask<Uri, Integer, byte[]> {
    ContentResolver resolver;
    Bitmap bitmap;
    CompressedImageToBytesCallback imageCallback;

    public BackgroundCompressorToBytes(ContentResolver resolver, CompressedImageToBytesCallback imageCallback) {
      this.resolver = resolver;
      this.imageCallback = imageCallback;
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap, Integer quality) {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
      return stream.toByteArray();
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected byte[] doInBackground(Uri... uris) {
      try {
        bitmap = MediaStore.Images.Media.getBitmap(resolver, uris[0]);
      } catch (Exception e) {
        Log.w("errorOnResizing", e.getMessage());
      }
      return getBytesFromBitmap(bitmap, 60);
    }

    @Override
    protected void onPostExecute(byte[] imageBytes) {
      super.onPostExecute(imageBytes);
      imageCallback.getCompressedImage(imageBytes);
    }
  }

}

