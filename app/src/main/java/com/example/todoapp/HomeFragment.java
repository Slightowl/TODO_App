package com.example.todoapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Random;


public class HomeFragment extends Fragment {

    private String[] poetryLine;
    private TextView line;
    private ImageView camera;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_page, container, false);

        poetryLine = getResources().getStringArray(R.array.khalil_poem);
        line = view.findViewById(R.id.quote);
        camera = view.findViewById(R.id.camera);

        /*
        randomize string array
        and play small fade in animation
         */
        line.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //line.start();
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation startAnimation = AnimationUtils.loadAnimation(getContext()
                                .getApplicationContext(), R.anim.fade_in);
                        line.startAnimation(startAnimation);

                        //stop the animation
                        //line.stop();
                    }
                }, 2); // ~ small delay

                line = view.findViewById(R.id.quote);
                int randomIndex = new Random().nextInt(poetryLine.length);

                String randomLine = poetryLine[randomIndex];
                line.setText(randomLine);
            }
        });

        /*
        upon clicking camera button app attempts to
        gain permission through a camerea component using
        implicit intent
         */
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        return view;
    }

    /*
    Requests permission from camera
    component
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    /*
    If everything successful retrieve
    the image and set as a bitmap photo
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            camera.setImageBitmap(photo);
        }
    }
}




