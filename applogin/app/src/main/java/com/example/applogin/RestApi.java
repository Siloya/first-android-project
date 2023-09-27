/*
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

public class DriveBackupUtils {

    private static final String APPLICATION_NAME = "Your Application Name";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKEN_DIRECTORY_PATH = "path/to/token/directory";
    private static final String CREDENTIALS_FILE_PATH = "path/to/credentials/file";
    private static final String BACKUP_FILE_NAME = "backup.db";

    private static Drive getDriveService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Drive driveService = new Drive.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .setHttpRequestInitializer(DriveBackupUtils.getCredentials(httpTransport))
                .build();
        return driveService;
    }

    private static Credential getCredentials(HttpTransport httpTransport) throws IOException {
        File tokenDirectory = new File(TOKEN_DIRECTORY_PATH);
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(tokenDirectory);
        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, DriveBackupUtils.getClientSecrets(httpTransport), Collections.singleton(DriveScopes.DRIVE_FILE))
                .setDataStoreFactory(fileDataStoreFactory)
                .build()
                .loadCredential("user-id");
    }

    private static GoogleClientSecrets getClientSecrets(HttpTransport httpTransport) throws IOException {
        File credentialsFile = new File(CREDENTIALS_FILE_PATH);
        return GoogleClientSecrets.load(JSON_FACTORY, Files.newReader(credentialsFile, Charsets.UTF_8));
    }

    public static void performBackup(File databaseFile) {
        try {
            Drive service = DriveBackupUtils.getDriveService();

            // Create a new file on Google Drive
            File fileMetadata = new File();
            fileMetadata.setName(BACKUP_FILE_NAME);
            FileContent mediaContent = new FileContent("application/octet-stream", databaseFile);
            File uploadedFile = service.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            System.out.println("Backup file uploaded successfully. File ID: " + uploadedFile.getId());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public static void performRestore(File destinationFile) {
        try {
            Drive service = DriveBackupUtils.getDriveService();

            // Retrieve the backup file from Google Drive
            String query = "name='" + BACKUP_FILE_NAME + "'";
            FileList result = service.files().list().setQ(query).execute();
            if (result.getFiles().isEmpty()) {
                System.out.println("Backup file not found on Google Drive.");
                return;
            }

            // Download the backup file
            File backupFile = result.getFiles().get(0);
            service.files().get(backupFile.getId()).executeMediaAndDownloadTo(destinationFile);

            System.out.println("Backup file downloaded and restored successfully.");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File databaseFile = new File("path/to/your/database/file");
        performBackup(databaseFile);

        // Perform restore
        File destinationFile = new File("path/to/destination/file");
        performRestore(destinationFile);
    }
}
*/
