package com.example.limsebatchmanagement.DriveManagement;

import android.app.Activity;
import android.content.*;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.*;

import java.io.IOException;
import java.util.*;
import java.util.function.*;

public class GoogleDrive {
    private static final List<String> DRIVE_SCOPES = Arrays.asList(DriveScopes.DRIVE);

    public static Function<Activity,GoogleSignInClient> signInIntent = activity -> {GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            .requestScopes((new Scope(DriveScopes.DRIVE)))
            .build();
        return GoogleSignIn.getClient(activity, googleSignInOptions);};

    public static Function<Intent,GoogleSignInAccount> getDriveAccount = intent -> GoogleSignIn.getSignedInAccountFromIntent(intent).getResult();

    public static BiFunction<Context,GoogleSignInAccount,GoogleAccountCredential> getDriveCredential = (context,account) -> GoogleAccountCredential
            .usingOAuth2(context,DRIVE_SCOPES)
            .setSelectedAccount(account.getAccount());

    public static BiFunction<String,GoogleAccountCredential,Drive> getDriveService = (appName,driveCredential) -> new Drive
            .Builder(AndroidHttp.newCompatibleTransport(),JacksonFactory.getDefaultInstance(),setHttpTimeout(driveCredential))
            .setApplicationName(appName)
            .build();

    public static Consumer<GoogleSignInClient> logOut = signInClient -> signInClient.signOut();
    private static HttpRequestInitializer setHttpTimeout(final HttpRequestInitializer requestInitializer) {
        return httpRequest -> {
            requestInitializer.initialize(httpRequest);
            httpRequest.setConnectTimeout(3 * 60000);  // 3 minutes connect timeout
            httpRequest.setReadTimeout(3 * 60000);  // 3 minutes read timeout
        };
    }
}