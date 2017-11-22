package com.example.hanora.vending;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView zXingScannerView;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        b = (Button) findViewById(R.id.scanBu);
        handlePermissions();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(zXingScannerView);
                zXingScannerView.setResultHandler(MainActivity.this);
                zXingScannerView.startCamera();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    ((ViewGroup)b.getParent()).removeView(b);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void displayAlertMessage(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener){
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("CANCEL", cancelListener)
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        zXingScannerView.startCamera();
        zXingScannerView.resumeCameraPreview(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
//        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_LONG).show();
        zXingScannerView.stopCameraPreview();
//        zXingScannerView.resumeCameraPreview(this);
        zXingScannerView.stopCamera();
        Intent i = new Intent(MainActivity.this, ProductsActivity.class);
        i.putExtra("VENDING_MACHINE_KEY", result.getText());
        startActivity(i);
    }

    private void handlePermissions(){
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                displayAlertMessage("Please Grant the camera permission for this application", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                1);
                    }
                },  new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((ViewGroup)b.getParent()).removeView(b);
                    }
                });

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        1);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

}
