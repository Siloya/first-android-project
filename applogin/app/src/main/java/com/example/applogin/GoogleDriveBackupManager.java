/*package com.example.applogin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class GoogleDriveBackupManager {
    private static final String TAG = "GoogleDriveBackup";
    private static final String BACKUP_FILE_NAME = "chatlog.db";

    private Context context;

    public GoogleDriveBackupManager(Context context) {
        this.context = context;
    }

    public void backupToDrive() {
        new BackupTask().execute();
    }

    public void restoreFromDrive() {
        new RestoreTask().execute();
    }

    private class BackupTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(context);
                if (signInAccount != null) {
                    DriveFolder appFolder = Drive.getDriveClient(context, signInAccount).getAppFolder();
                    DriveFile backupFile = createBackupFile();
                    DriveContents driveContents = appFolder.createFile(backupFile.getName(), backupFile.getMimeType()).await();
                    FileInputStream fileInputStream = new FileInputStream(backupFile);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                        driveContents.write(buffer, 0, bytesRead);
                    }
                    driveContents.commit();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error backing up to Google Drive: " + e.getMessage());
            }
            return null;
        }

        private File createBackupFile() throws IOException {
            // Retrieve the chat messages from your application's data source or database
            // Format the chat messages into a suitable format for backup, such as JSON or CSV
            // Create a backup file and write the formatted chat messages into it
            // Return the created backup file
            File f= new File();
            return f;
        }
    }

    private class RestoreTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(context);
                if (signInAccount != null) {
                    DriveFolder appFolder = Drive.getDriveClient(context, signInAccount).getAppFolder();
                    DriveFile backupFile = appFolder.getFileById("BACKUP_FILE_ID").await();
                    DriveContents driveContents = backupFile.open(DriveFile.MODE_READ_ONLY).await();
                    FileInputStream fileInputStream = new FileInputStream(driveContents.getInputStream());
                    File restoreFile = createRestoreFile();
                    FileOutputStream outputStream = new FileOutputStream(restoreFile);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                    outputStream.close();
                    fileInputStream.close();
                    Log.d

                }*/