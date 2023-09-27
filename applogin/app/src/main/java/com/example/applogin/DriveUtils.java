/*
package com.example.applogin;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
public class DriveUtils {
    private static final String APPLICATION_NAME = "Your App Name";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static void backupFile(String filePath) throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        DriveService driveService = new Drive.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .setHttpRequestInitializer(DriveBackupUtils.getCredentials(httpTransport))
                .build();

        File fileMetadata = new File();
        fileMetadata.setName("backup.db");
        java.io.File fileContent = new java.io.File(filePath);
        FileContent mediaContent = new FileContent("application/octet-stream", fileContent);

        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        System.out.println("File ID: " + uploadedFile.getId());
    }

    public static void restoreFile(String filePath, String fileId) throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        DriveService driveService = new Drive.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .setHttpRequestInitializer(DriveBackupUtils.getCredentials(httpTransport))
                .build();

        java.io.File fileContent = new java.io.File(filePath);
        OutputStream outputStream = new FileOutputStream(fileContent);

        driveService.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);

        outputStream.flush();
        outputStream.close();
    }
}

*/
