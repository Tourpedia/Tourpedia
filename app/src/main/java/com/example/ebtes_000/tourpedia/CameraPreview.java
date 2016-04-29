package com.example.ebtes_000.tourpedia;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by DELL on 12/03/16.
 */
/* Surface on which the camera projects it's capture results.
*/
class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        setCamera(camera);
        Log.e("debug", "12");

        setCameraDisplayOrientation(0, mCamera);//TODO:There is a small problem here fix it!!!!
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);

        Log.e("debug", "13");




    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        Log.e("debug","14");

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            Log.e("debug", "15");

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("debug", "16");

        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
       // mCamera.release();//very important !!!!!!!!!!!!!!!!
        Log.e("debug","17");


        // Surface will be destroyed when we return, so stop the preview.
        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface.
          //  mCamera.stopPreview();
            Log.e("debug","18");

            stopPreviewAndFreeCamera();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        Log.e("debug","19");

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            Log.e("debug", "20");

        } catch (Exception e){
            e.printStackTrace();
        }


    }


    public static void setCameraDisplayOrientation(int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
       // int rotation = Surface.ROTATION_90;
        int degrees = 0;

            degrees = 90;

        Log.e("debug","21");


        int result;
        // back-facing
            result = (info.orientation - degrees + 360) % 360;
        Log.e("debug","22");

        camera.setDisplayOrientation(result);
    }

    public void setCamera(Camera camera) {
        if (mCamera == camera) {
            Log.e("debug","23");
            return; }

        stopPreviewAndFreeCamera();
        Log.e("debug", "24");

        mCamera = camera;

        if (mCamera != null) {
           // List<Camera.Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
           // mSupportedPreviewSizes = localSizes;
            //requestLayout();
            Log.e("debug","25");

            try {
                Log.e("debug","27");

                mCamera.setPreviewDisplay(mHolder);
                Log.e("debug", "26");

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Important: Call startPreview() to start updating the preview
            // surface. Preview must be started before you can take a picture.

            Log.e("debug","28");

            mCamera.startPreview();
        }
    }

    private void stopPreviewAndFreeCamera() {

        Log.e("debug","29");


        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface.

            Log.e("debug","30");

            mCamera.stopPreview();

            // Important: Call release() to release the camera for use by other
            // applications. Applications should release the camera immediately
            // during onPause() and re-open() it during onResume()).
            mCamera.release();

            mCamera = null;
        }
    }
}