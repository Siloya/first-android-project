/*
package com.example.applogin;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
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


public class BACKK {
    private void backupToDrive() {
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            DriveClient driveClient = Drive.getDriveClient(this, signInAccount);
            DriveResourceClient driveResourceClient = Drive.getDriveResourceClient(this, signInAccount);

            // Get the path of the database file
            String databasePath = "/path/to/your/database.db";

            // Create a file metadata object
            MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                    .setTitle("backup.db")
                    .setMimeType("application/vnd.sqlite3")
                    .build();

            // Create a file on Google Drive
            driveResourceClient.createFile(getAppFolder(), metadataChangeSet, null)
                    .continueWithTask(task -> {
                        DriveContents driveContents = DriveUtils.createDriveContents(databasePath);
                        return driveResourceClient.openFile(task.getResult(), DriveFile.MODE_WRITE_ONLY, null)
                                .continueWithTask(writeTask -> {
                                    OutputStream outputStream = driveContents.getOutputStream();
                                    FileInputStream fileInputStream = new FileInputStream(new File(databasePath));

                                    // Copy the database file to the output stream
                                    byte[] buffer = new byte[4096];
                                    int bytesRead;
                                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                                        outputStream.write(buffer, 0, bytesRead);
                                    }

                                    // Close streams
                                    fileInputStream.close();
                                    outputStream.flush();
                                    outputStream.close();

                                    // Commit the changes and close the contents
                                    return driveResourceClient.commitContents(driveContents, null);
                                });
                    })
                    .addOnSuccessListener(task -> {
                        // Backup completed successfully
                        Log.d(TAG, "Backup completed");
                    })
                    .addOnFailureListener(e -> {
                        // Handle backup failure
                        Log.e(TAG, "Error backing up to Google Drive: " + e.getMessage());
                    });
        }
    }
    private void restoreFromDrive() {
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            DriveClient driveClient = Drive.getDriveClient(this, signInAccount);
            DriveResourceClient driveResourceClient = Drive.getDriveResourceClient(this, signInAccount);

            // Get the path where the database file will be restored
            String restorePath = "/path/to/your/restore.db";

            // Query for the backup file
            Query query = new Query.Builder()
                    .addFilter(Filters.eq(SearchableField.TITLE, "backup.db"))
                    .build();

            driveResourceClient.query(query)
                    .addOnSuccessListener(metadataBuffer -> {
                        if (metadataBuffer.getCount() > 0) {
                            DriveFile backupFile = metadataBuffer.get(0).getDriveId().asDriveFile();
                            DriveUtils.openFile(driveResourceClient, backupFile)
                                    .continueWithTask(task -> {
                                        DriveContents driveContents = task.getResult();
                                        InputStream inputStream = driveContents.getInputStream();
                                        FileOutputStream fileOutputStream = new FileOutputStream(new File(restorePath));

                                        // Copy the contents of the backup file to the restore file
                                        byte[] buffer = new byte[4096];
                                        int bytesRead;
                                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                                            fileOutputStream.write(buffer, 0, bytesRead);
                                        }

                                        // Close streams
                                        inputStream.close();
                                        fileOutputStream.flush();
                                        fileOutputStream.close();

                                        // Close the contents
                                        driveContents.discard(driveResourceClient);

                                        return null;
                                    })
                                    .addOnSuccessListener(aVoid -> {
                                        // Restore completed successfully
                                        Log.d(TAG, "Restore completed");
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle restore failure
                                        Log.e(TAG, "Error restoring from Google Drive: " + e.getMessage());
                                    });
                        }

                        metadataBuffer.release();
                    });
        }
    }
    public class DriveUtils {
        public static DriveContents createDriveContents(String filePath) throws IOException {
            DriveContents driveContents = DriveContents.create();
            OutputStream outputStream = driveContents.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));

            // Copy the file to the output stream
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close streams
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();

            return driveContents;
        }

        public static Task<DriveContents> openFile(DriveResourceClient driveResourceClient, DriveFile file) {
            return driveResourceClient.openFile(file, DriveFile.MODE_READ_ONLY);
        }
    }


}
*/
