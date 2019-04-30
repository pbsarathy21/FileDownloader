package com.example.filedownloader;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DownloadListener{

    ProgressDialog mProgressDialog;
    List<DownloadModel> downloadModelList = new ArrayList<>();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
            (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        )
        {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WAKE_LOCK,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },555);
        }

        DownloadModel downloadModel = new DownloadModel();
        downloadModel.setFileUrl("http://beginnersparadise.com/storage_app/public/backup-uplods/files/doc-2019043071049.mp3");
        downloadModel.setFilePath("/data/hw_init/version/region_comm/oversea/media/Pre-loaded/Music/Magic_Mullet.mp3");
        downloadModelList.add(downloadModel);

        DownloadModel downloadModel1 = new DownloadModel();
        downloadModel1.setFileUrl("http://beginnersparadise.com/storage_app/public/backup-uplods/files/doc-2019043095032.jpeg");
        downloadModel1.setFilePath("/storage/emulated/0/Download/images.jpeg");
        downloadModelList.add(downloadModel1);

        DownloadModel downloadModel2 = new DownloadModel();
        downloadModel2.setFileUrl("http://beginnersparadise.com/storage_app/public/backup-uplods/files/doc-2019043095110.pdf");
        downloadModel2.setFilePath("/storage/emulated/0/Download/sample.pdf");
        downloadModelList.add(downloadModel2);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("A message");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMax(downloadModelList.size());
        mProgressDialog.show();

        fileDownloaded();
    }

    @Override
    public void fileDownloaded() {

        if (i < downloadModelList.size())
        {
            mProgressDialog.setProgress(i+1);
            final DownloadTask downloadTask = new DownloadTask(this, this);
            downloadTask.execute(downloadModelList.get(i).getFileUrl(), downloadModelList.get(i).getFilePath());
            i++;
        } else
        {
            Toast.makeText(this, "All Files Downloaded", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
        }
    }
}