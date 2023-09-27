package com.example.applogin;
import androidx.core.util.Pair;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.*;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A utility for performing read/write operations on Drive files via the REST API and opening a
 * file picker UI via Storage Access Framework.
 */
public class DriveServiceHelper {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
   public final Drive mDriveService;// 3mlta public hl2
    public DriveServiceHelper(Drive driveService) {
        mDriveService = driveService;
    }
    public Task<String> uploadFile(String name, java.io.File fileTest) {
        return Tasks.call(mExecutor, () -> {
            File metadata = new File()
                    .setParents(Collections.singletonList("root"))
                    .setMimeType("text/plain")
                    .setName(name);
            InputStream targetStream = new FileInputStream(fileTest);
            InputStreamContent inputStreamContent = new
                    InputStreamContent("text/plain", targetStream);
            File googleFile = mDriveService.files().create(metadata,
                    inputStreamContent).execute();
            if (googleFile == null) {
                throw new IOException("Null result when requesting file creation.");
            }
            return googleFile.getId();
        });
    }// wn nhat , nhna shi lzm nhadd shi mhl , m shklu hon

    /**
     * Opens the file identified by {@code fileId} and returns a {@link Pair} of its name and * contents. */

    public Task<Pair<String, String>> readFile(String fileId) {
        return Tasks.call(mExecutor, () -> {
            // Retrieve the metadata as a File object.
            File metadata = mDriveService.files().get(fileId).execute();
            String name = metadata.getName();
            // Stream the file contents to a String.
            try (InputStream is =
                         mDriveService.files().get(fileId).executeMediaAsInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                // L FILE BL CONTENT 23D BS K STRING
                String contents = stringBuilder.toString();
                return Pair.create(name, contents);

                // // Download the backup file
                //            File backupFile = result.getFiles().get(0);// HY
                //            service.files().get(backupFile.getId()).executeMediaAndDownloadTo(destinationFile);W HY


                // Retrieve the backup file from Google Drive
//                String query = "name='" + BACKUP_FILE_NAME + "'";
//                FileList result = service.files().list().setQ(query).execute();
//                if (result.getFiles().isEmpty()) {
//                    System.out.println("Backup file not found on Google Drive.");
//                    return;

                // 2EME METHODE :
//                java.io.File fileContent = new java.io.File(filePath);
//                OutputStream outputStream = new FileOutputStream(fileContent);
//
//                driveService.files().get(fileId)
//                        .executeMediaAndDownloadTo(outputStream);W HY
//
//                outputStream.flush();
//                outputStream.close();

                // TLT WEHDE L HAKI LSHABTU B STRING HTU B FILE , Y3NI Y EXECUTDOWLOANDTO Y L BUFFER L BT2RA KLME KLME

                //    /*  FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                //        fileOutputStream.write(data.toString().getBytes());*/
            }
        });
    }

    /**
     * Returns a {@link FileList} containing all the visible files in the user's My Drive.
     *
     * <p>The returned list will only contain files visible to this app, i.e. those which were
     * created by this app. To perform operations on files not created by the app, the project must
     * request Drive Full Scope in the <a href="https://play.google.com/apps/publish">Google
     * Developer's Console</a> and be submitted to Google for verification.</p>
     */

    public Task<FileList> queryFiles() {
        return Tasks.call(mExecutor, () ->
                mDriveService.files().list().setSpaces("drive").execute());
    }
}