package com.bumble.Utils.filepicker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;


import com.bumble.Utils.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by gurmail on 11/01/19.
 *
 * @author gurmail
 */
class ImageUtil {

    private ImageUtil() {

    }

    static void compressImage(File imageFile, ImageFile actualFile, float reqWidth, float reqHeight, Bitmap.CompressFormat compressFormat,
                              int quality, String destinationPath, CompressorListener compressorListener) throws IOException {
        FileOutputStream fileOutputStream = null;
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1280);
        integers.add(800);

//        String fileExt = Util.getExtension(actualFile.getPath());
//        destinationPath = destinationPath+"."+fileExt;

        File file = new File(destinationPath).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            // write the compressed bitmap at the destination specified by destinationPath.
            Bitmap bitmap = decodeSampledBitmapFromFile(imageFile, reqWidth, reqHeight);

            fileOutputStream = new FileOutputStream(destinationPath);
            bitmap.compress(compressFormat, quality, fileOutputStream);
            if(bitmap.getHeight() > 0)
                integers.add(0, bitmap.getHeight());
            if(bitmap.getWidth() > 0)
                integers.add(1, bitmap.getWidth());
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        if(compressorListener != null) {
            File file1 = new File(destinationPath);
            actualFile.setSize(file1.length());
            actualFile.setPath(destinationPath);
            compressorListener.onImageCompressed(file1, destinationPath, actualFile, integers);
        }

        //return new File(destinationPath);
    }

    static Bitmap decodeSampledBitmapFromFile(File imageFile, float reqWidth, float reqHeight) throws IOException {
        // First decode with inJustDecodeBounds=true to check dimensions

        Bitmap scaledBitmap = null, bmp = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = reqWidth / reqHeight;

        if (actualHeight > reqHeight || actualWidth > reqWidth) {
            //If Height is greater
            if (imgRatio < maxRatio) {
                imgRatio = reqHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) reqHeight;

            }  //If Width is greater
            else if (imgRatio > maxRatio) {
                imgRatio = reqWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) reqWidth;
            } else {
                actualHeight = (int) reqHeight;
                actualWidth = (int) reqWidth;
            }
        }

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2,
                middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        bmp.recycle();
        ExifInterface exif;
        try {
            exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),
                    scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledBitmap;


    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            inSampleSize *= 2;
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
