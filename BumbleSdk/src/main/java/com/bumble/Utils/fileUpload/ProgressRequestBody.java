package com.bumble.Utils.fileUpload;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by gurmail on 16/01/19.
 *
 * @author gurmail
 */

public class ProgressRequestBody extends RequestBody {
    private File mFile;
    private String mPath;
    private UploadCallbacks mListener;
    private String mMimeType;
    private String mfileUrl;
    private int mMessageIndex;
    private String muid;

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage, int mMessageIndex, String muid);

        void onError(int percentage, int mMessageIndex, String muid);

        void onFinish(int percentage, int mMessageIndex, String muid);
    }

    public ProgressRequestBody(final File file, final UploadCallbacks listener, String mimeType, String fileUrl, int messageIndex, String muid) {
        mFile = file;
        mListener = listener;
        mMimeType = mimeType;
        mfileUrl = fileUrl;
        mMessageIndex = messageIndex;
        this.muid = muid;
        //CommonData.setFileLocalPath(muid, fileUrl);

    }

    @Override
    public MediaType contentType() {
        // i want to upload only images
        try {
            return MediaType.parse(mMimeType);
        } catch (Exception e) {
            return MediaType.parse("jpg");
        }
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;
        Handler handler = new Handler(Looper.getMainLooper());
        if (fileLength > 0) {
            try {
                int read;

                while ((read = in.read(buffer)) != -1) {
                    // update progress on UI thread
//                    handler.post(new ProgressUpdater(uploaded, fileLength, mMessageIndex, muid));
                    if (((int) (100 * uploaded / fileLength)) > 0 && ((int) (100 * uploaded / fileLength) % 10) == 0) {
                        mListener.onProgressUpdate(((int) (100 * uploaded / fileLength)), mMessageIndex, muid);
                    }
                    uploaded += read;
                    sink.write(buffer, 0, read);
                }
                mListener.onFinish((int) uploaded, mMessageIndex, muid);
//                handler.post(new ProgressUpdater(uploaded, fileLength,mMessageIndex,muid));
            } catch (Exception e) {
                handler.post(new ProgressUpdaterFailure(uploaded, mMessageIndex, muid));
            } finally {
                in.close();
            }
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded = 0l;
        private long mTotal = 0l;
        private int messageIndex;
        private String muid;
        int currentProgress = -1;

        public ProgressUpdater(long uploaded, long total, int messageIndex, String muid) {
            mUploaded = uploaded;
            mTotal = total;
            this.messageIndex = messageIndex;
            this.muid = muid;
        }

        @Override
        public void run() {
            if ((int) (100 * mUploaded / mTotal) == 100) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onFinish((int) mUploaded, mMessageIndex, muid);
                    }
                }, 2000);

            }
            if (((int) (100 * mUploaded / mTotal)) > 0 && ((int) (100 * mUploaded / mTotal) % 10) == 0) {
                mListener.onProgressUpdate(((int) (100 * mUploaded / mTotal)), mMessageIndex, muid);
            }
        }
    }

    private class ProgressUpdaterFailure implements Runnable {
        private int messageIndex;
        private String muid;
        private long percentage;

        public ProgressUpdaterFailure(long percentage, int messageIndex, String muid) {
            this.messageIndex = messageIndex;
            this.muid = muid;
            this.percentage = percentage;
        }

        @Override
        public void run() {
            mListener.onError((int) percentage, messageIndex, muid);
        }
    }

}


//public class ProgressRequestBody extends RequestBody {
//    private File mFile;
//    private String mPath;
//    private UploadCallbacks mListener;
//    private String content_type;
//
//    private static final int DEFAULT_BUFFER_SIZE = 2048;
//
//    public interface UploadCallbacks {
//        void onProgressUpdate(int percentage);
//        void onError();
//        void onFinish();
//    }
//
//
//
//    public ProgressRequestBody(final File file, String content_type,  final  UploadCallbacks listener) {
//        this.content_type = content_type;
//        mFile = file;
//        mListener = listener;
//    }
//
//
//
//    @Override
//    public MediaType contentType() {
//        return MediaType.parse(content_type+"/*");
//    }
//
//    @Override
//    public long contentLength() throws IOException {
//        return mFile.length();
//    }
//
//    @Override
//    public void writeTo(BufferedSink sink) throws IOException {
//        long fileLength = mFile.length();
//        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//        FileInputStream in = new FileInputStream(mFile);
//        long uploaded = 0;
//
//        try {
//            int read;
//            Handler handler = new Handler(Looper.getMainLooper());
//            while ((read = in.read(buffer)) != -1) {
//
//                // update progress on UI thread
//                handler.post(new ProgressUpdater(uploaded, fileLength));
//
//                uploaded += read;
//                sink.write(buffer, 0, read);
//            }
//        } finally {
//            in.close();
//        }
//    }
//
//    private class ProgressUpdater implements Runnable {
//        private long mUploaded;
//        private long mTotal;
//        public ProgressUpdater(long uploaded, long total) {
//            mUploaded = uploaded;
//            mTotal = total;
//        }
//
//        @Override
//        public void run() {
//            mListener.onProgressUpdate((int)(100 * mUploaded / mTotal));
//        }
//    }
//}

