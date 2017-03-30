package fr.arnaudguyon.toolbox;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aguyon on 13.01.17.
 */

public class BitmapTools {

    private static final String TAG = "BitmapTools";

    public static Bitmap loadFromPrivateFile(Context context, String path, BitmapFactory.Options resizeOptions) {
        try {
            FileInputStream fileInputStream = context.openFileInput(path);
            Bitmap bitmap = loadFromInputStream(fileInputStream, resizeOptions);
            fileInputStream.close();
            return bitmap;
        } catch (IOException | IllegalArgumentException e) {
            Log.w(TAG, "Couldn't load bitmap " + path);
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveJPGtoPrivateFile(Context context, Bitmap sourceBmp, String pictureName, Bitmap.CompressFormat format, int quality) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(pictureName, Context.MODE_PRIVATE);
            return sourceBmp.compress(format, quality, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Bitmap loadFromInputStream(InputStream inputStream, BitmapFactory.Options resizeOptions) {
        try {
            if (inputStream != null) {
                return BitmapFactory.decodeStream(inputStream, null, resizeOptions);
            }
            return null;
        } catch (OutOfMemoryError e) {
            Log.e(TAG, TAG + ".loadFromInputStream(): " + e.getMessage());
            return null;
        }

    }

    public static Bitmap loadAndTurnAndResize(String path, int approxWidth) {
        try {
            final ExifInterface exif = new ExifInterface(path);
            final int srcWidth = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, approxWidth);
            final int subSample = (srcWidth / approxWidth);
            BitmapFactory.Options resizeOptions = new BitmapFactory.Options();
            resizeOptions.inSampleSize = subSample;

            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeFile(path, resizeOptions);
            } catch (OutOfMemoryError e) {
                return null;
            }

            if (bitmap != null) {
                final int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                if (orientation != ExifInterface.ORIENTATION_NORMAL) {
                    int angle = 0;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                        angle = 90;
                    } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                        angle = 180;
                    } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                        angle = 270;
                    }
                    if (angle != 0f) {
                        Matrix matrix = new Matrix();
                        matrix.preRotate(angle);
                        try {
                            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                            bitmap.recycle();
                            return rotatedBitmap;
                        } catch (OutOfMemoryError e) {
                            return bitmap;
                        }
                    }
                }
            }
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO: documentation: for Android >= 7, must ask for android.permission.READ_EXTERNAL_STORAGE to read the file after
    public static String getFilePathFromMediaUri(@NonNull Context context, @NonNull Uri mediaUri) {
        String scheme = mediaUri.getScheme();
        if ("file".equals(scheme)) {
            return mediaUri.getPath();
        } else if ("content".equals(scheme)) {
            Cursor cursor = null;
            try {
                String[] projection = {MediaStore.Images.Media.DATA};
                cursor = context.getContentResolver().query(mediaUri, projection, null, null, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    if (columnIndex >= 0) {
                        cursor.moveToFirst();
                        return cursor.getString(columnIndex);
                    }
                }
            } catch (SecurityException exception) {
                exception.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return null;
    }

    public static File createTemporaryImageFile(Context context, String prefix) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = prefix + timeStamp;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return file;
    }

    public static void addImageToGallery(Context context, File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
