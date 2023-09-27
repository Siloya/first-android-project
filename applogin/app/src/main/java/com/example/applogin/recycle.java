package com.example.applogin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
//import com.google.gson.GsonFactory;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.File;
//import com.google.api.services.drive.model.File;
//import android.util.Pair;
import androidx.core.util.Pair;
import androidx.annotation.NonNull;
//import androidx.core.

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.extensions.android.http.AndroidHttp;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class recycle extends AppCompatActivity implements RunTimePermission.PermissionCallback  {

    ArrayList<String> name;
    DBHelper db;
    Adapter adapter;
    RecyclerView recycle ;
    String mUsername;
    MaterialButton btnadd;
    //jdad backup test
   // GoogleDriveBackupManager backupManager = new GoogleDriveBackupManager(this);
   // GoogleSignInAccount account;
    Button backup, restore;
    //   LinearLayoutManager Layout;
    // List<ModelClass>;
    private RunTimePermission runTimePermission;
    private final int RQ_GOOGLE_SIGN_IN = 210;
    private final String Database_FILE = "login.db";


    private  String BACKUP_FILE = "login.db";

    private boolean isRestore = false;
    private GoogleSignInClient mGoogleApiClient;
    private DriveServiceHelper mDriveServiceHelper;
    private String lastUploadFileId;
    String databasePath;
    GoogleSignInAccount accountT;
    String nameacc ,email;
    GoogleSignInOptions signInOptions;

    private ProgressDialog progressDialog;
    ActivityResultLauncher<Intent> getResult;

    //
  // final String ClientID ="568435305300-atibsdfl4k7k03j351gb436kgdacttt3.apps.googleusercontent.com";
    final String ClientID ="568435305300-kc8jid8c65hqei7mktmp7ncm8bq0rih3.apps.googleusercontent.com";
   //568435305300-atibsdfl4k7k03j351gb436kgdacttt3.apps.googleusercontent.com
    //// 568435305300-atibsdfl4k7k03j351gb436kgdacttt3.apps.googleusercontent.com
       //787574426768-3iguem3q4k89q6tnrv3motm04ee377r1.apps.googleusercontent.com
 // GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(context)
    //568435305300-atibsdfl4k7k03j351gb436kgdacttt3.apps.googleusercontent.com

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent it = getIntent();
        Bundle bdl;
        if (it != null && (bdl = it.getExtras()) != null) {
            //2T2KAD
           mUsername = bdl.getString(MainActivity.EXTRA_USERNAME);
          //  mUsername = bdl.getString(homeactivitiy.mUsername);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rycleview_main);


        db = new DBHelper(this);
        name = new ArrayList<>();
        adapter = new Adapter(this,name);
        recycle = findViewById(R.id.recycleview);
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        btnadd =  (MaterialButton) findViewById(R.id.add);
        backup = (MaterialButton) findViewById(R.id.button);
        restore = (MaterialButton) findViewById(R.id.button2);
        displaydata() ;

      //  runTimePermission = new RunTimePermission();
        runTimePermission = new RunTimePermission(getApplicationContext()); // Pass the activity context to the RunTimePermission constructor
        databasePath = getDatabasePath( Database_FILE).getAbsolutePath();
       ///////

        btnadd.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           recycle.this.finish();
                                           Intent it = new Intent(getApplicationContext(), homeactivitiy.class);
                                           it.putExtra(MainActivity.EXTRA_USERNAME,mUsername.toString());
                                           startActivity(it);
                                       }
                                   }
        );///*
        accountT = GoogleSignIn.getLastSignedInAccount(recycle.this);
       signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(ClientID)
                .requestScopes(new Scope(Scopes.PROFILE), new Scope("https://www.googleapis.com/auth/drive.file"))
                .build();
                mGoogleApiClient = GoogleSignIn.getClient(recycle.this, signInOptions);
// bshuf iza bhot hol bara bhes lzm ee */
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //backupManager.backupToDrive();

                if (accountT==null)
                {Log.i("accountnull ", "account null  ");
                   // Toast.makeText(recycle.this, "account nullll", Toast.LENGTH_SHORT).show();
                    //mGoogleApiClient = GoogleSignIn.getClient(recycle.this, signInOptions);
                   // accountT = GoogleSignIn.getLastSignedInAccount(recycle.this);
                    //backup.setText("null");
                    backup.setText(accountT.getDisplayName());
                }
                else{
                    backup.setText(accountT.getDisplayName());
                    Log.i("accountvalid ", "account validd ");

                }

                runTimePermission.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new RunTimePermission.PermissionCallback() {// HON
                        @Override
                        public void onPermissionGranted(boolean isGranted) {
                            if (isGranted) {
                                isRestore = false;

                             // LOG LL DRIVE BA2A
                                if (mDriveServiceHelper == null)
                                {// progress bar n3mlu had ahla
                                    showProgressDialog("Authenticating");
                                    googleAuth();
                               }
                       //////

                               else {
                                    Log.i("NON FUT AUTH ", "huwe deja auth ");
//                                    /*progressDialog = new ProgressDialog(recycle.this);
//                                    progressDialog.setMessage("Uploading backup file...");
//                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                                    progressDialog.setCanceledOnTouchOutside(false);
//                                    progressDialog.show();*
                                    Toast.makeText(recycle.this, "uploadinnggggg", Toast.LENGTH_SHORT).show();

                                    ////progressDialog.dismiss();//
                                    uploadBackupFile();

                                    /*try {
                                       // Log.i("restorii", "onComplete: ");

                                      *//*  *//*
                                        //hon shi l progress AW FO2A

                                        Task<String> uploadTask = mDriveServiceHelper.uploadFile(BACKUP_FILE, generateFile());// EXCEPTION


                                        uploadTask.addOnCompleteListener(new OnCompleteListener<String>() {
                                            @Override
                                            public void onComplete(Task<String> task) {
                                                progressDialog.dismiss();
                                                if (task.isSuccessful()) {
                                                    String fileId = task.getResult();
                                                    lastUploadFileId = fileId;
                                                    System.out.println("lastUploadFileId==>" + lastUploadFileId);
                                                    Log.i("backuppp", "onComplete: " + lastUploadFileId);
                                                    //dismiss
                                                    Toast.makeText(recycle.this, "Backup upload successfully", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Exception exception = task.getException();
                                                    Toast.makeText(recycle.this, "EXCEPTIONNN", Toast.LENGTH_SHORT).show();
                                                    Log.i("EXCEPTION: ", "EXCEPTION M MSHI L HAL  ");
                                                }
                                            }
                                        });
                                  //}//
                            } catch (IOException e) {
                                        e.printStackTrace();
                                   // }///
                                }*/
                              }//
                           }
                        }
                    });
                }
            });

        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //backupManager.restoreFromDrive();
                runTimePermission.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new RunTimePermission.PermissionCallback() {
                    @Override
                    public void onPermissionGranted(boolean isGranted) {
                        if (isGranted) {
                            isRestore = true;
                            if (mDriveServiceHelper == null)
                                googleAuth();
                            else {
                                if (lastUploadFileId != null) {
                                    Log.i("restorii", "onComplete: ");
                                    Task<androidx.core.util.Pair<String, String>> downloadTask = mDriveServiceHelper.readFile(lastUploadFileId);
                                    downloadTask.addOnCompleteListener(new OnCompleteListener<Pair<String, String>>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Pair<String, String>> result) {
                                            Pair<String, String> pair = result.getResult();
                                            String name = pair.first;
                                            String content = pair.second;
                                            // Handle the completed task
                                            System.out.println("Name: " + name);
                                            System.out.println("Content: " + content);
                                            Log.i("restorii", "onComplete: "+"Name: " + name +"Content: " + content);
                                            Toast.makeText(recycle.this, "Backup download successfully", Toast.LENGTH_SHORT).show();


                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }
        });
        // NAJAH
       // googleAuth();
    }

     private void displaydata(){

         Cursor cursor = this.db.fetchmycontact(this.mUsername);
        if (cursor.getCount()==0){
            Toast.makeText(recycle.this, "no contact exist", Toast.LENGTH_SHORT).show();
        }
        else { while(cursor.moveToNext()){
            name.add(cursor.getString(0));
        }
        }
     }

    @Override
    public void onPermissionGranted(boolean isGranted) {

    }

// GoogleSignInClient
    private void googleAuth() {
        Log.i("googleAuthhh", "fett googleauth");
        Toast.makeText(recycle.this, "fett googleauthtt", Toast.LENGTH_SHORT).show();
           signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(ClientID)
                .requestScopes(new Scope(Scopes.PROFILE), new Scope("https://www.googleapis.com/auth/drive.file"))
                .build();
        mGoogleApiClient = GoogleSignIn.getClient(this, signInOptions);

        Intent signInIntent = mGoogleApiClient.getSignInIntent();
        startActivityForResult(signInIntent, RQ_GOOGLE_SIGN_IN);
      //  getResult.launch(signInIntent);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getResult.launch(signInIntent);
//            }
//        }, 700); // 2 seconds delay
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("NonActivityResult:", "fett nshallah BL onActivityResult");
        Log.i("onActivityResulttt", "fett onActivityResultt");
        Toast.makeText(recycle.this, "fett onActivityResulttto", Toast.LENGTH_SHORT).show();
        if (requestCode == RQ_GOOGLE_SIGN_IN)
                //&& resultCode == RESULT_OK)
        {
          /*  GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (!result.isSuccess()) {
                // Sign-in was successful, handle the signed-in user.
                Log.i("RREQUESTCODEE", "SignInResult: " + result.getStatus());
                // Perform necessary actions with the signed-in account.
            } else {
            //*/
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Attempt to retrieve the signed-in GoogleSignInAccount object
              accountT = task.getResult(ApiException.class);
             if (accountT !=null)
             {Log.i("accountT:", "accountTResult"+accountT);
                  nameacc = accountT.getDisplayName();
                  email = accountT.getEmail();
                 backup.setText(nameacc);
                 Log.i("name:", "accountTname"+ name );
                 Log.i("email:", "accountTemail"+email );
             }

                Log.i("tryyyonActivityResult", "fett tryyyonActivityResult");
                Toast.makeText(recycle.this, "fett tryyyonActivityResult", Toast.LENGTH_SHORT).show();
            GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener(this, account -> {//task honik wa2ta bs htayna l task w nt2lna 3l safha l tnye bl safha l tnye sahbna l account
                // tab ana shu bdi hot bl safha l tnye


                GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                        recycle.this,
                        Collections.singleton(DriveScopes.DRIVE_FILE)
                );
                credential.setSelectedAccount(account.getAccount());
                Drive googleDriveService = new Drive.Builder(
                        AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                        credential)
                        .setApplicationName(getString(R.string.app_name))
                        .build();
                if (account != null) {
                    String accessToken = account.getIdToken();
                    if (accessToken != null) Log.i("TOKEN RETRIEVAL", accessToken);
                }

                mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                // Dismiss the progress indicator after successful token retrieval
               //progressDialog.dismiss();// t3 l bar ahla w azbat
// backfile()

                if (isRestore) {
                    if (lastUploadFileId != null) {
                        Task<androidx.core.util.Pair<String, String>> downloadTask = mDriveServiceHelper.readFile(lastUploadFileId);
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
                        }).addOnFailureListener(this, exception -> {
                            // Token retrieval failed, handle the exception here
                            Toast.makeText(recycle.this, "Failed to retrieve token", Toast.LENGTH_SHORT).show();
                            Log.i("TOKEN RETRIEVAL ERROR", exception.toString());
                        });
                    }
                      }
                else {
                        Log.i("progressDialog", "fett nshallah");
                    Log.i("fettytba33", "fettytba33");
                    Toast.makeText(recycle.this, "fettytba33", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();// NAJAH
                    uploadBackupFile();
                      /*  try {
                            Log.i("tRYprogRESS", "fett nshallah B TRY ");
                           *//* progressDialog = new ProgressDialog(recycle.this);
                            progressDialog.setMessage("Uploading backup file...");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCanceledOnTouchOutside(false);
*//*
                            // MTL IZA F2S RESTORE W HUWE MNU 3AML UPLOAD YSIR Y3ML UPLOAD AW SHI
                            Task uploadTask = mDriveServiceHelper.uploadFile(BACKUP_FILE, generateFile());

                            uploadTask.addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    lastUploadFileId = task.getResult();
                                    System.out.println("lastUploadFileId==>" + lastUploadFileId);
                                    Toast.makeText(recycle.this, "Backup upload successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
              //  }
            });
        }catch (ApiException e) {
                // Sign-in failed, handle the error.
                int statusCode = e.getStatusCode();
                String statusMessage = e.getMessage();

                // Log the specific details of the error
                Log.i("RREQUESTCODEE", "SignInResult: Failed");
                Log.i("RREQUESTCODEE", "StatusCode: " + statusCode);
                Log.i("RREQUESTCODEE", "StatusMessage: " + statusMessage);

                // Handle the error based on the status code.
                switch (statusCode) {
                    case CommonStatusCodes.SIGN_IN_REQUIRED:
                        // Sign-in is required by the app.
                        break;
                    case CommonStatusCodes.NETWORK_ERROR:
                        // Network error occurred during sign-in.
                        break;
                    // Handle other status codes as needed.
                    default:
                        break;
                }
        }
       // switchtoanotherActivity();
    }
    }
   /*public void switchtoanotherActivity(){
      Intent it= new Intent(this, NewB.class);
       startActivity(it);
    }*/
   private void uploadBackupFile() {
    //   progressDialog.dismiss();
       // Show the progress indicator before starting the backup upload
       showProgressDialog("Uploading backup file...");
       try { Log.i("backuppfett", "fttt " );
           Task<String> uploadTask = mDriveServiceHelper.uploadFile(BACKUP_FILE, generateFile());// HY KNT 3M TSHT8L WA2T L NULL W HL2 LA
           Log.i("backuppfett222", "fttt222 " );
         //  Toast.makeText(recycle.this, "backuppfett222to", Toast.LENGTH_SHORT).show();
           uploadTask.addOnCompleteListener(new OnCompleteListener<String>() {
               @Override
               public void onComplete(@NonNull Task<String> task) {
                   progressDialog.dismiss();
                   if (task.isSuccessful()) {
                       String fileId = task.getResult();
                       lastUploadFileId = fileId;
                       System.out.println("lastUploadFileId==>" + lastUploadFileId);
                       Log.i("completebackupp", "onComplete: " + lastUploadFileId);
                       Toast.makeText(recycle.this, "Backup upload successfully", Toast.LENGTH_SHORT).show();
                   } else {
                       Exception exception = task.getException();
                       Toast.makeText(recycle.this, "EXCEPTIONNNano"+exception, Toast.LENGTH_LONG).show();
                       Log.i("EXCEPTION: ", "EXCEPTION M MSHI L HAL  "+ exception);
                   }
               }
           });
       }
       catch (IOException e) {
           e.printStackTrace();// HON SRNA HL222 // 5AMIS mshi hala slhnha // bdietb3 l exception hon
           Log.e("EXCEPTION: ", "IOException occurred", e);
           Toast.makeText(recycle.this, "EXCEPTIONZUZUUU"+e, Toast.LENGTH_LONG).show();
       }
   }

    private void showProgressDialog(String message) {
       progressDialog = new ProgressDialog(recycle.this);
       progressDialog.setMessage(message);
       progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
       progressDialog.setCanceledOnTouchOutside(false);
       progressDialog.show();
   }
   int i =0;
    private File generateFile() throws IOException {
        Log.i("generateFilee", "generateFileiii" );
        Toast.makeText(recycle.this, "generateFile", Toast.LENGTH_SHORT).show();
        // wen 3m htu ana haliyan ??
      //  File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);// shufa iza null
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        if (storageDir== null || !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))// HONNNN
        { Log.i("storageDir", "storageDir is null ");
            Toast.makeText(recycle.this, "storageDir is null", Toast.LENGTH_SHORT).show();
            return null;}
        else {
        if (!storageDir.exists()) {
            //storageDir.mkdir();// true y false f fiyi shil hlsatr
            if (!storageDir.mkdirs()) { // HONNN HL2
                // Directory creation failed
                Log.i("TAG", "Failed to create directory");
                Toast.makeText(recycle.this, "Failed to create directory", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        i++;
        BACKUP_FILE +="i"; // BHS HY WALLA ZBTTA
        File file = new File(storageDir, BACKUP_FILE);// hon shu , 8yr l esmm +i
        if (!file.exists()) {
            //file.createNewFile(); // ft lhon m3nta kmn fiyi shila hshila b3dn
            try {
                // If the file does not exist, attempt to create it.
                if (!file.createNewFile()) {
                    // File creation failed
                    Log.i("TAG", "Failed to create file");
                    Toast.makeText(recycle.this, "Failed to create file ", Toast.LENGTH_SHORT).show();
                    return null;
                }
            } catch (IOException e) {
                // Handle the IOException if it occurs
                e.printStackTrace();
                Log.i("TAG", "exception offFailed to create file");
                Toast.makeText(recycle.this, "exception offFailed to create file ", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        StringBuilder data = new StringBuilder();
        for (int i = 0; i <= 5; i++) { //HY HON SHU WAD3A
            data.append(i).append(". First string is here to be written. First string is here to be written.");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        fileOutputStream.write(data.toString().getBytes());

            Log.i("nihyt l generate ", "niht l generate");
        // najah ll database
        /*  copyFile(new File(databasePath), file);*/
        return file;}
    }
    private File generateFileFromContent(String content) {
        try {
            File file = new File(getFilesDir(), "restored_file.txt"); // Specify the desired file name and extension
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void copyFile(File sourceFile, File destinationFile) throws IOException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null)
            mGoogleApiClient.signOut();
    }
}
/*getResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            // if (data != null) {
                            //  String value = data.getStringExtra("input");
                            // }
                            Log.i("gettResultt ", "fett nshallah");
                            GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener(recycle.this, account -> {
                                GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                                        recycle.this,
                                        Collections.singleton(DriveScopes.DRIVE_FILE)
                                );
                                credential.setSelectedAccount(account.getAccount());
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
                                            progressDialog = new ProgressDialog(recycle.this);
                                            progressDialog.setMessage("Uploading backup file...");
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                            progressDialog.setCanceledOnTouchOutside(false);
                                            progressDialog.show();

                                            uploadTask.addOnCompleteListener(new OnCompleteListener<String>() {
                                                @Override
                                                public void onComplete(@NonNull Task<String> task) {
                                                    lastUploadFileId = task.getResult();
                                                    System.out.println("lastUploadFileId==>" + lastUploadFileId);
                                                    Toast.makeText(recycle.this, "Backup upload successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });



                        }
                    }
                });*/

             /* GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestServerAuthCode(ClientID)
                                        .requestScopes(new Scope(Scopes.PROFILE), new Scope("https://www.googleapis.com/auth/drive.file"))
                                        .build();
                                    mGoogleApiClient = GoogleSignIn.getClient(recycle.this, signInOptions);

                                    Intent signInIntent = mGoogleApiClient.getSignInIntent();
                                    //startActivityForResult(signInIntent, RQ_GOOGLE_SIGN_IN);
                                    Log.i("FUT AUTH ", "3m y3ml auth ");
                                    */
//  }
/**//*
                                   // GoogleSignInAccount
                                    Task<GoogleSignInAccount> accountTask= GoogleSignIn.getSignedInAccountFromIntent(signInIntent);
                                            //.addOnSuccessListener(this, account -> {
                                    // najah
                                    accountTask.addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            // Account is successfully obtained here
                                             account = task.getResult();
                                        }});// najahhh
                                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                                                recycle.this,
                                                Collections.singleton(DriveScopes.DRIVE_FILE)
                                        );
                                        credential.setSelectedAccount(account.getAccount());
                                        Drive googleDriveService = new Drive.Builder(
                                                AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                                                credential)
                                                .setApplicationName(getString(R.string.app_name))
                                                .build();
                                         mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                                }*/
//);
// }


    // shkli h hot l code h
    // startActivityForResult(mGoogleApiClient.getSignInIntent(), RQ_GOOGLE_SIGN_IN);
    // registerForActivityResult(ActivityResultContract, ActivityResultCallback);


   /* ActivityResultLauncher<Intent> getResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String value = data.getStringExtra("input");
                        }

                        GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener(recycle.this, account -> {
                            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                                    recycle.this,
                                    Collections.singleton(DriveScopes.DRIVE_FILE)
                            );
                            credential.setSelectedAccount(account.getAccount());
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
                                                Toast.makeText(recycle.this, "Backup upload successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });



                    }
                }
            });*/
//    ActivityResultLauncher<Intent> getResult = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            callback);
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //SU2L HAD BUSHT8L B SERVICE AW BLE W BDNA NZID SERVICE SHI
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_GOOGLE_SIGN_IN && resultCode == RESULT_OK) {
            GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener(this, account -> {
                GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                        recycle.this,
                        Collections.singleton(DriveScopes.DRIVE_FILE)
                );
                credential.setSelectedAccount(account.getAccount());
                Drive googleDriveService = new Drive.Builder(
                        AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                        credential)
                        .setApplicationName(getString(R.string.app_name))
                        .build();
                mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                if (isRestore) {
                    if (lastUploadFileId != null) {
                        Task<androidx.core.util.Pair<String, String>> downloadTask = mDriveServiceHelper.readFile(lastUploadFileId);
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
                                    Toast.makeText(recycle.this, "Backup upload successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }*/
    // hon bdi 3adel


// HL2 AM 2T2KD MNL CODE W BDI YH YSHAB FILE L DATABAE BDI 23TI ESMU W YMKN BDI 3YT LL DRECTORY WL PATH T3U
/* W BDI SHI MTL HK L HOT L FILE L BDI NAZLU MHL FILE L DATABASE
*   File backupFile = result.getFiles().get(0);
            service.files().get(backupFile.getId()).executeMediaAndDownloadTo(destinationFile);*/

//InputStream inputStream = context.getDatabasePath(BACKUP_FILE_NAME).getPath();
//OutputStream outputStream = new FileOutputStream(context.getDatabasePath(BACKUP_FILE_NAME));
//private static final String DATABASE_PATH = "/path/to/your/database.db"; // Replace with your actual database path
//    public static void backupToDrive(String backupFilePath) throws IOException {
//        File sourceFile = new File(DATABASE_PATH);
//        File destinationFile = new File(backupFilePath);
//    public static void restoreFromDrive(String backupFilePath) throws IOException {
//        File sourceFile = new File(backupFilePath);
//        File destinationFile = new File(DATABASE_PATH);

// Specify the file ID of the JSON file on Google Drive
//String fileId = "your_file_id";
//
//    // Download the file from Google Drive
//    java.io.File downloadedFile = new java.io.File("/path/to/downloaded_data.json");
//    OutputStream outputStream = new FileOutputStream(downloadedFile);
//service.files().get(fileId).executeMediaAndDownloadTo(outputStream);
// Replace the current database file with the restored file
//File currentDatabaseFile = new File(context.getDatabasePath("your_database_name.db").getPath());
//File chatDbFile = new File("/path/to/chat_database.db");


    // Replace the existing chat database file with the restored file
//    File existingDbFile = new File("/path/to/existing_database.db");
//                    restoreFile.renameTo(existingDbFile);

// Step 1: Set up Google Drive API

// Step 2: Implement authentication

//    // Step 3: Implement file upload to Google Drive
//    java.io.File chatDbFile = new java.io.File("/path/to/chat_database.db");
//    File fileMetadata = new File();
//fileMetadata.setName("chat_database.db");
//        FileContent mediaContent = new FileContent("application/octet-stream", chatDbFile);
//        File uploadedFile = driveService.files().create(fileMetadata, mediaContent).execute();
//        String fileId = uploadedFile.getId();
//
//// Step 4: Implement file download from Google Drive
//        java.io.File downloadedFile = new java.io.File("/path/to/downloaded_database.db");
//        OutputStream outputStream = new FileOutputStream(downloadedFile);
//        driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
//
//// Step 5: Replace the existing chat database file
//        java.io.File existingDbFile = new java.io.File("/path/to/existing_database.db");
//        downloadedFile.renameTo(existingDbFile);
// private static final String TOKEN_DIRECTORY_PATH = "path/to/token/directory";
//    private static final String CREDENTIALS_FILE_PATH = "path/to/credentials/file";

//String databaseName = "your_database_name.db";
//String filePath = getDatabasePath(databaseName).getAbsolutePath();


// badi erj3 23ml l back up ll file l2adim t3 l tjrib



//shu bdi 23ml : bdi hot progress hl2 b3d m y5tar l account ydal ydur lhad m ynzal l file w bs ysnxl ytb3li nzl m bs toast ,
// b3da bl restore zt l haki , b3da bdi hot ede bl % sar , m3a y3ni badi hot service ltsht8l bs etl3 w bdi hot notification 3lya l % , w bdi
// 23ml l auto backup  w esa kmn fi sh8lt zabt l shkl t3un asm2 l accounts l mawjudin hy t2ribn 25r shi ,( w bdi 25ode l size w teri5 l last upload
//N5

//FROM RESTORE
/*  // hon bdi htu mhal l database hl2 5alini shuf l adim b3dn l database lshbta b3dn 2badlon n5
                                            // Replace the current database file with the downloaded content



                                            // Retrieve the backup file from Google Drive
                                            // String query = "name='" + BACKUP_FILE_NAME + "'";
                                            //FileList result = service.files().list().setQ(query).execute();
                                            //if (result.getFiles().isEmpty()) {
                                            // System.out.println("Backup file not found on Google Drive.");
                                             //return;

                                            // Download the backup file hay law shabne k file w 5lsna
                                            /*File backupFile = result.getFiles().get(0);
                                            mDriveServiceHelper.mDriveService.files().get(backupFile.getId()).executeMediaAndDownloadTo(destinationFile);*/// knt hlwe br2yi


   // File restoredFile = generateFileFromContent(content); // Generate a File object from the downloaded content
                                            /* end l badel l database
                                            try {
                                                copyFile(restoredFile, new File(databasePath));
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }*/ // end najah lbadl l database

                                            /*FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                                            fileOutputStream.write(content.getBytes());*/ // hy bda file twawlu l output y3ni law shbna hdk k file w aslan law shbme k file m ftna b hl m3m3a


//SU2L HAD BUSHT8L B SERVICE AW BLE W BDNA NZID SERVICE SHI


// kn bdi shuf l logat shu tb3li b hy     Log.d("RREQUESTCODEE", "SignInResult: " + result.getStatus());

// ntba3 l error bshkl ahsan , nfsol l firebase iza hyne , w n3ml l sh8l 3ala shi jdid mtl viva w nn2ol l sh8l 3l devops : jum3a
// r2yi ent2kd esaa mn km sh8le w ruh tab2 hl sh8l 3l viva , 5susi iza bdi erj3 3id l etap 5ls b3idon honik , blnhye klu honik bdna n3mlu


// 5alasss h2sht8la 3a viva w devops ba2a

// jrb 8yr hydik lshuf w nfz 3 viva , w sh8lt l api 3lya 3lmt estfhm , w fi shi bdon kn y3mlu production aw shi bdl tst

// n3ml hl pourcentage ba2a w hata b viva hata law abl l %
// b3d service backround w notific b3d auto backup b3dn l shkl // urba

// llha2i2a lzm debug l2sa ft 3l generate file m f exception mtl abl , bs b3d m ft m mn3rf shu sar l2n 8alibn m tb3li b3d l task // bs km iza pourcentage mniha wala ktir

//[incl meda m 3ndi fkra 3na esa ktir abdn ]

// devopsss

// shuf lh not create , etba3 esm l account l2t2kd enu  shbu ,deveops, % ll upload , devops
//devopssss// jum3a lyom
// zbt l create , l upload am tal3 exception not succfl , w hl2 am shuf l account iza tmm

// backup file name w account get last , b3dn bhot 3 chat aw hl2 bhot w b3dn bfham shu al
// MAHMOUD DEVOPS L2N SHKLU RAGHEB M FI AMAL MNU
// SSD UPS W WASLE