/*
package com.example.applogin;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class BACK2 {
    private void backupToDrive(String filePath) {
        // Obtain a valid GoogleSignInAccount for the user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        // Create DriveClient using the signInAccount
        DriveClient driveClient = Drive.getDriveClient(this, signInAccount);

        // Create a DriveContents instance for the file
        DriveContents driveContents = DriveUtils.createDriveContents(filePath);

        // Create metadata for the file
        MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                .setTitle("chat_database.db")
                .setMimeType("application/octet-stream")
                .setStarred(true)
                .build();

        // Create the file on Google Drive
        driveClient.createFile(metadataChangeSet, driveContents)
                .addOnSuccessListener(this,
                        driveFile -> {
                            // File created successfully
                            String fileId = driveFile.getDriveId().encodeToString();
                            // Perform any necessary actions after successful backup
                            // ...
                        })
                .addOnFailureListener(this, e -> {
                    // File upload failed
                    // Handle the error appropriately
                    // ...
                });
    }
    private void restoreFromDrive(String filePath) {
        // Obtain a valid GoogleSignInAccount for the user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        // Create DriveClient using the signInAccount
        DriveClient driveClient = Drive.getDriveClient(this, signInAccount);

        // Retrieve the backup file from Google Drive
        String fileId = "<your_file_id>"; // Replace with the actual file ID
        DriveFile driveFile = DriveId.decodeFromString(fileId).asDriveFile();
        driveClient.openFile(driveFile, DriveFile.MODE_READ_ONLY)
                .addOnSuccessListener(this,
                        driveContents -> {
                            // File retrieved successfully
                            InputStream inputStream = driveContents.getInputStream();

                            // Replace the current chat database file with the retrieved file
                            replaceDatabaseFile(inputStream, filePath);

                            // Perform any necessary actions after successful restore
                            // ...
                        })
                .addOnFailureListener(this, e -> {
                    // File retrieval failed
                    // Handle the error appropriately
                    // ...
                });
    }

    private void replaceDatabaseFile(InputStream inputStream, String filePath) {
        // Replace the current chat database file with the retrieved file
        try {
            OutputStream outputStream = new FileOutputStream(filePath);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            // Handle the file replacement error
            e.printStackTrace();
        }
    }


}
*/// jded
/*
//GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(recycle.this);
                                       // if (signInAccount == null) {
                                            //Log.i("signInAccount", "null is signInAccountnull  ");
                                       // } else{
                                            // GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener(recycle.this, account -> {
                                            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                                                    recycle.this,
                                                    Collections.singleton(DriveScopes.DRIVE_FILE)
                                            );
                                        //credential.setSelectedAccount(account.getAccount());
                                        credential.setSelectedAccount(signInAccount.getAccount());
                                        Drive googleDriveService = new Drive.Builder(
                                                AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                                                credential)
                                                .setApplicationName(getString(R.string.app_name))
                                                .build();
                                        mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                                        if (isRestore) {
                                            if (lastUploadFileId != null) {
                                                Task<Pair<String, String>> downloadTask = mDriveServiceHelper.readFile(lastUploadFileId);
                                                downloadTask.addOnCompleteListener(new OnCompleteListener<Pair<String, String>>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Pair<String, String>> result) {
                                                        if (result.isSuccessful()) {
                                                            Pair<String, String> pair = result.getResult();
                                                            String name = pair.first;
                                                            String content = pair.second;
                                                            // Handle the completed task
                                                            System.out.println("Name: " + name);
                                                            System.out.println("Content: " + content);
                                                        } else {
                                                            // Handle the failed task
                                                            Exception exception = result.getException();
                                                            if (exception != null) {
                                                                // Handle the exception
                                                                exception.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                });

                                            } else {
                                                try {// MTL IZA F2S RESTORE W HUWE MNU 3AML UPLOAD YSIR Y3ML UPLOAD AW SHI
                                                    Task uploadTask = mDriveServiceHelper.uploadFile(BACKUP_FILE, generateFile());
                                                    uploadTask.addOnCompleteListener(new OnCompleteListener<String>() {


                                                        @Override
                                                        public void onComplete(@NonNull Task<String> task) {
                                                            lastUploadFileId = task.getResult();
                                                            System.out.println("lastUploadFileId==>" + lastUploadFileId);
                                                            Toast.makeText(recycle.this, "Backup upload successfully", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        // });//
*/




