package jlabs.soswagger.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jlabs.soswagger.R;

public class LoginActivity extends FragmentActivity {
    Activity context1;
    RelativeLayout rl;
    LinearLayout ll;
    ImageView hip;
    Context context;
    MaterialIconView fb,gplus,insta;
    public static int splash_time = 5000;
    public static int splash_time1 = 15000;



    private static final String TAG = "android-plus-quickstart";

    private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;
    private static final int RC_SIGN_IN = 786;
    private static final String SAVED_PROGRESS = "sign_in_progress";

    // Client ID for a web server that will receive the auth code and exchange it for a
    // refresh token if offline access is requested.
    private static final String WEB_CLIENT_ID = "WEB_CLIENT_ID";

    // Base URL for your token exchange server, no trailing slash.
    private static final String SERVER_BASE_URL = "SERVER_BASE_URL";

    // URL where the client should GET the scopes that the server would like granted
    // before asking for a serverAuthCode
    private static final String EXCHANGE_TOKEN_URL = SERVER_BASE_URL + "/exchangetoken";

    // URL where the client should POST the serverAuthCode so that the server can exchange
    // it for a refresh token,
    private static final String SELECT_SCOPES_URL = SERVER_BASE_URL + "/selectscopes";


    // GoogleApiClient wraps our service connection to Google Play services and
    // provides access to the users sign in state and Google's APIs.
    private GoogleApiClient mGoogleApiClient;

    private int mSignInProgress;

    private PendingIntent mSignInIntent;

    // Used to store the error code most recently returned by Google Play services
    // until the user clicks 'sign in'.
    private int mSignInError;

    // Used to determine if we should ask for a server auth code when connecting the
    // GoogleApiClient.  False by default so that this sample can be used without configuring
    // a WEB_CLIENT_ID and SERVER_BASE_URL.
    private boolean mRequestServerAuthCode = false;

    // Used to mock the state of a server that would receive an auth code to exchange
    // for a refresh token,  If true, the client will assume that the server has the
    // permissions it wants and will not send an auth code on sign in.  If false,
    // the client will request offline access on sign in and send and new auth code
    // to the server.  True by default because this sample does not implement a server
    // so there would be nowhere to send the code.
    private boolean mServerHasToken = true;


    Boolean loginviafacebook=true;
    private LoginButton loginButton;
    private PendingAction pendingAction = PendingAction.NONE;
    private CallbackManager callbackManager;
    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }

    String name;
    String email;
    String first_name;
    String pos_name;

    JSONArray work;
    Bitmap profilePic;
    String last_name;
    String profilePicUrl;
    Boolean loginviagmail=false;
    ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        context=this;
        context1=this;
        setContentView(R.layout.activity_login);
        rl=(RelativeLayout)findViewById(R.id.social);
        ll=(LinearLayout)findViewById(R.id.login);
        hip=(ImageView)findViewById(R.id.hipster);
        fb=(MaterialIconView)findViewById(R.id.fb_login);
        gplus=(MaterialIconView)findViewById(R.id.gplus_login);
        insta=(MaterialIconView)findViewById(R.id.insta_login);
        loginButton=(LoginButton)findViewById(R.id.loginButton);
        mProgressDialog = new ProgressDialog(this);
        mGoogleApiClient = buildGoogleApiClient();
        callbackManager = CallbackManager.Factory.create();







        final Animation hippo = AnimationUtils.loadAnimation(this, R.anim.fade_in);
       // final Intent myIntent = new Intent(this, MainPage.class);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                hip.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
                ll.startAnimation(hippo);
                rl.setVisibility(View.VISIBLE);

                rl.startAnimation(hippo);


            }
        }, splash_time);



//        final Intent myIntent = new Intent(this, MainPage.class);
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                startActivity(myIntent);
//                context1.finish();
//            }
//        }, splash_time1);


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();

            }
        });

//
//        gplus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginviafacebook=false;
//                if(isNetworkAvailable()) {
//                    if (!mGoogleApiClient.isConnecting()) {
//                        showprogressbar();
//                        loginviagmail=true;
//                        switch (v.getId()) {
//                            case R.id.google:
//                                Log.i("here","here");
//                                mSignInProgress = STATE_SIGN_IN;
//                                mGoogleApiClient.connect();
//                                break;
//                        }
//                    }
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Please Check internet connectivity", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        if (savedInstanceState != null) {
            mSignInProgress = savedInstanceState
                    .getInt(SAVED_PROGRESS, STATE_DEFAULT);
        }


        //FB Login
        loginButton.setReadPermissions(Arrays.asList("public_profile, email,user_work_history"));
        Log.d("user2q", "hello1");
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        loginviagmail = false;
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        email = object.optString("email");
                                        last_name = object.optString("last_name");
                                        first_name = object.optString("first_name");
                                        try {
                                            work=object.getJSONArray("work");
                                            JSONObject position=work.getJSONObject(0);
                                            pos_name=position.getString("name");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            pos_name="Swagger";
                                        }

                                        if (object.has("picture")) {

                                            try {
                                                profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                            // set profilePic bitmap to imageview
                                        }
                                        SendFbData();

                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "email,name,first_name,last_name,birthday,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }


                    @Override
                    public void onCancel() {
                        Log.d("user2", "hello2");

                    }

                    @Override
                    public void onError(FacebookException exception) {

                        if (pendingAction != PendingAction.NONE
                                && exception instanceof FacebookAuthorizationException) {
                            showAlert();
                            pendingAction = PendingAction.NONE;
                        }

                    }

                    private void showAlert() {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle(R.string.cancelled)
                                .setMessage(R.string.permission_not_granted)
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });




    }


    @Override
    public void onPause() {
        super.onPause();

        // Call the 'deactivateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onPause methods of the primary Activities that an app may be
        // launched into.
        AppEventsLogger.deactivateApp(this);
    }





    private GoogleApiClient buildGoogleApiClient() {
        // When we build the GoogleApiClient we specify where connected and
        // connection failed callbacks should be returned, which Google APIs our
        // app uses and which OAuth 2.0 scopes our app requests.
        Log.i("here","here1");
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        // Reaching onConnected means we consider the user signed in.
                        Log.i(TAG, "onConnected");
                        Log.i("here","here2");
                        // Update the user interface to reflect that the user is signed in.
                        fb.setEnabled(false);
                        Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                       Log.i(" currentUser"," currentUser"+ currentUser);
                        Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
                                .setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
                                    @Override
                                    public void onResult(People.LoadPeopleResult loadPeopleResult) {
                                        if (loadPeopleResult.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS)
                                        {

                                        } else {
                                            Log.e(TAG, "Error requesting visible circles: " + loadPeopleResult.getStatus());
                                        }
                                    }
                                });

                        // Indicate that the sign in process is complete.
                        mSignInProgress = STATE_DEFAULT;
                        onSignedOut();
                        if(isNetworkAvailable()&&(!loginviafacebook)&&loginviagmail) {

                            if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.GET_ACCOUNTS);
                                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[] {Manifest.permission.GET_ACCOUNTS},
                                            10);
                                    return;
                                }
                            }
                            else{
                                email=Plus.AccountApi.getAccountName(mGoogleApiClient).toString();
                                Log.i("email",""+email);
                            }
                            Log.i("email", "" + email);
                            Intent intent = new Intent(getApplication(), MainPage.class);
                            intent.putExtra("email", email);
                            intent.putExtra("fname", currentUser.getName().getGivenName());
                            intent.putExtra("lname", currentUser.getName().getFamilyName());
                            Log.e("longa",""+currentUser.getUrl());
                            String b=currentUser.getImage().getUrl();
                            intent.putExtra("images",b.replace(b.substring(b.length()-2), "200") );
                            Log.i("Goole Image",":  "+b.replace(b.substring(b.length()-2), "200"));
                            startActivity(intent);
                            finish();
                        }


                        else
                        {
                            Toast.makeText(getApplicationContext(), "No Internet Connectivity available", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        // The connection to Google Play services was lost for some reason.
                        // We call connect() to attempt to re-establish the connection or get a
                        // ConnectionResult that we can attempt to resolve.
                        mGoogleApiClient.connect();

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
// Refer to the javadoc for ConnectionResult to see what error codes might
                        // be returned in onConnectionFailed.
                        Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                                + result.getErrorCode());

                        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
                            // An API requested for GoogleApiClient is not available. The device's current
                            // configuration might not be supported with the requested API or a required component
                            // may not be installed, such as the Android Wear application. You may need to use a
                            // second GoogleApiClient to manage the application's optional APIs.
                            Log.w(TAG, "API Unavailable.");
                        } else if (mSignInProgress != STATE_IN_PROGRESS) {
                            // We do not have an intent in progress so we should store the latest
                            // error resolution intent for use when the sign in button is clicked.
                            mSignInIntent = result.getResolution();
                            mSignInError = result.getErrorCode();

                            if (mSignInProgress == STATE_SIGN_IN) {
                                // STATE_SIGN_IN indicates the user already clicked the sign in button
                                // so we should continue processing errors until the user is signed in
                                // or they click cancel.
                                resolveSignInError();
                            }
                        }

                        // In this sample we consider the user signed out whenever they do not have
                        // a connection to Google Play services.
                        onSignedOut();
                    }
                })
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN);

        if (mRequestServerAuthCode) {
            checkServerAuthConfiguration();
            builder = builder.requestServerAuthCode(WEB_CLIENT_ID, new GoogleApiClient.ServerAuthCodeCallbacks() {

                @Override
                public CheckResult onCheckServerAuthorization(String idToken, Set<Scope> scopeSet) {
                    Log.i(TAG, "Checking if server is authorized.");
                    Log.i(TAG, "Mocking server has refresh token: " + String.valueOf(mServerHasToken));

                    if (!mServerHasToken) {
                        // Server does not have a valid refresh token, so request a new
                        // auth code which can be exchanged for one.  This will cause the user to see the
                        // consent dialog and be prompted to grant offline access. This callback occurs on a
                        // background thread so it is OK to do synchronous network access.

                        // Ask the server which scopes it would like to have for offline access.  This
                        // can be distinct from the scopes granted to the client.  By getting these values
                        // from the server, you can change your server's permissions without needing to
                        // recompile the client application.
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpGet httpGet = new HttpGet(SELECT_SCOPES_URL);
                        HashSet<Scope> serverScopeSet = new HashSet<Scope>();

                        try {
                            HttpResponse httpResponse = httpClient.execute(httpGet);
                            int responseCode = httpResponse.getStatusLine().getStatusCode();
                            String responseBody = EntityUtils.toString(httpResponse.getEntity());

                            if (responseCode == 200) {
                                String[] scopeStrings = responseBody.split(" ");
                                for (String scope : scopeStrings) {
                                    Log.i(TAG, "Server Scope: " + scope);
                                    serverScopeSet.add(new Scope(scope));
                                }
                            } else {
                                Log.e(TAG, "Error in getting server scopes: " + responseCode);
                            }

                        } catch (ClientProtocolException e) {
                            Log.e(TAG, "Error in getting server scopes.", e);
                        } catch (IOException e) {
                            Log.e(TAG, "Error in getting server scopes.", e);
                        }

                        // This tells GoogleApiClient that the server needs a new serverAuthCode with
                        // access to the scopes in serverScopeSet.  Note that we are not asking the server
                        // if it already has such a token because this is a sample application.  In reality,
                        // you should only do this on the first user sign-in or if the server loses or deletes
                        // the refresh token.
                        return CheckResult.newAuthRequiredResult(serverScopeSet);
                    } else {
                        // Server already has a valid refresh token with the correct scopes, no need to
                        // ask the user for offline access again.
                        return CheckResult.newAuthNotRequiredResult();
                    }
                }

                @Override
                public boolean onUploadServerAuthCode(String idToken, String serverAuthCode) {
                    // Upload the serverAuthCode to the server, which will attempt to exchange it for
                    // a refresh token.  This callback occurs on a background thread, so it is OK
                    // to perform synchronous network access.  Returning 'false' will fail the
                    // GoogleApiClient.connect() call so if you would like the client to ignore
                    // server failures, always return true.
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(EXCHANGE_TOKEN_URL);

                    try {
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                        nameValuePairs.add(new BasicNameValuePair("serverAuthCode", serverAuthCode));
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        HttpResponse response = httpClient.execute(httpPost);
                        int statusCode = response.getStatusLine().getStatusCode();
                        final String responseBody = EntityUtils.toString(response.getEntity());
                        Log.i(TAG, "Code: " + statusCode);
                        Log.i(TAG, "Resp: " + responseBody);

                        // Show Toast on UI Thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, responseBody, Toast.LENGTH_LONG).show();
                            }
                        });
                        return (statusCode == 200);
                    } catch (ClientProtocolException e) {
                        Log.e(TAG, "Error in auth code exchange.", e);
                        return false;
                    } catch (IOException e) {
                        Log.e(TAG, "Error in auth code exchange.", e);
                        return false;
                    }
                }
            });
        }

        return builder.build();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                } else {
                    // Permission Denied
                    Log.i("Hi","deep");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    private void onSignedOut() {

        fb.setEnabled(true);

    }


    public boolean isNetworkAvailable() {
        ConnectivityManager cm=(ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo()!=null&&cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_PROGRESS, mSignInProgress);
    }
    private void resolveSignInError() {
        if (mSignInIntent != null) {
            // We have an intent which will allow our user to sign in or
            // resolve an error.  For example if the user needs to
            // select an account to sign in with, or if they need to consent
            // to the permissions your app is requesting.

            try {
                // Send the pending intent that we stored on the most recent
                // OnConnectionFailed callback.  This will allow the user to
                // resolve the error currently preventing our connection to
                // Google Play services.
                mSignInProgress = STATE_IN_PROGRESS;
                startIntentSenderForResult(mSignInIntent.getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.i(TAG, "Sign in intent could not be sent: "
                        + e.getLocalizedMessage());
                // The intent was canceled before it was sent.  Attempt to connect to
                // get an updated ConnectionResult.
                mSignInProgress = STATE_SIGN_IN;
                mGoogleApiClient.connect();
            }
        } else {
            // Google Play services wasn't able to provide an intent for some
            // error types, so we show the default Google Play services error
            // dialog which may still start an intent on our behalf if the
            // user can resolve the issue.
            createErrorDialog().show();
        }
    }
    private Dialog createErrorDialog() {
        if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
            return GooglePlayServicesUtil.getErrorDialog(
                    mSignInError,
                    this,
                    RC_SIGN_IN,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            Log.e(TAG, "Google Play services resolution cancelled");
                            mSignInProgress = STATE_DEFAULT;

                        }
                    });
        } else {
            return new AlertDialog.Builder(this)
                    .setMessage(R.string.play_services_error)
                    .setPositiveButton(R.string.close,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e(TAG, "Google Play services error could not be "
                                            + "resolved: " + mSignInError);
                                    mSignInProgress = STATE_DEFAULT;

                                }
                            }).create();
        }
    }
    private void checkServerAuthConfiguration() {
        // Check that the server URL is configured before allowing this box to
        // be unchecked
        if ("WEB_CLIENT_ID".equals(WEB_CLIENT_ID) ||
                "SERVER_BASE_URL".equals(SERVER_BASE_URL)) {
            Log.w(TAG, "WEB_CLIENT_ID or SERVER_BASE_URL configured incorrectly.");
            Dialog dialog = new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.configuration_error))
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();

            dialog.show();
        }
    }
    public void showprogressbar()
    {
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("we are taking you there");
        mProgressDialog.setCancelable(true);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    private void SendFbData() {
        Intent intent = new Intent(getApplication(), MainPage.class);
        intent.putExtra("email", email);
        intent.putExtra("fname", first_name);
        intent.putExtra("lname", last_name);
        intent.putExtra("posi", pos_name);
        intent.putExtra("images",profilePicUrl);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onResume methods of the primary Activities that an app may be
        // launched into.
        AppEventsLogger.activateApp(this);


    }








    @Override
    protected void onDestroy() {
        if(mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {

        if (loginviafacebook) {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (!loginviafacebook) {

            switch (requestCode) {
                case RC_SIGN_IN:
                    if (resultCode == RESULT_OK) {
                        // If the error resolution was successful we should continue
                        // processing errors.
                        mSignInProgress = STATE_SIGN_IN;
                    } else {
                        // If the error resolution was not successful or the user canceled,
                        // we should stop processing errors.
                        mSignInProgress = STATE_DEFAULT;
                    }

                    if (!mGoogleApiClient.isConnecting()) {
                        // If Google Play services resolved the issue with a dialog then
                        // onStart is not called so we need to re-attempt connection here.
                        mGoogleApiClient.connect();
                    }
                    break;
            }
        }







    }



}
