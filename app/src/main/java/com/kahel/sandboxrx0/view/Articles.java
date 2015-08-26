package com.kahel.sandboxrx0.view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.kahel.sandboxrx0.R;
import com.kahel.sandboxrx0.adapter.ArticlesAdapter;
import com.kahel.sandboxrx0.model.ArticlesModel;
import com.kahel.sandboxrx0.model.FacebookProfileModel;
import com.kahel.sandboxrx0.parameters.ArticlesParameters;
import com.kahel.sandboxrx0.presenter.ArticlesPresenter;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Articles extends ActionBarActivity {

    @Bind(R.id.btn_getnews) Button get_news;
    @Bind(R.id.list_news) GridView list_news;
    ArticlesAdapter adapter;
    ArrayList<HashMap<String, String>> articles;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                if (result.getPostId() != null) {
                    Toast.makeText(getBaseContext(), "Success message shit", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Nothing happens", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(), "Cancelled this shit", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getBaseContext(), "error message shit", Toast.LENGTH_SHORT).show();
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                requestUserProfile(loginResult);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(),"Login Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getBaseContext(),"Problem connecting to Facebook", Toast.LENGTH_SHORT).show();
            }
        });


        articles = new ArrayList<HashMap<String, String>>();
        adapter = new ArticlesAdapter(getBaseContext(),articles);
        list_news.setAdapter(adapter);
        get_news.setText("Get Articles");

        if(savedInstanceState!=null){
            //Parcelable mListInstanceState = savedInstanceState.getParcelable("saved_articles");
            articles.addAll((Collection<? extends HashMap<String, String>>) savedInstanceState.getSerializable("articles_list"));
            adapter.notifyDataSetChanged();
            //list_news.onRestoreInstanceState(mListInstanceState);
        }else{
            GetArticles();
        }

    }

    public void requestUserProfile(LoginResult loginResult){
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location"); // Parámetros que pedimos a facebook

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {
                        if (response.getError() != null) {
                            // handle error
                        } else {
                            FacebookProfileModel facebookProfileModel;
                            Gson gson = new Gson();
                            facebookProfileModel = gson.fromJson(me.toString(), FacebookProfileModel.class);

                            Log.e("Result",  facebookProfileModel.getEmail());
                        }
                    }
                });
        request.setParameters(parameters);
        request.executeAsync();
    }

    @OnClick(R.id.btn_gethash)
    public void getHash(){
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/1485047148476033",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        Log.e("Response", response.getJSONObject().toString());
                    }
                }
        ).executeAsync();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Note: getValues() is a method in your ArrayAdaptor subclass
        //ArrayList<HashMap<String, String>> values = adapter.getVa
        //outState.putParcelable("saved_articles", list_news.onSaveInstanceState());
        outState.putSerializable("articles_list", articles);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedState){
        super.onRestoreInstanceState(savedState);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


    public void GetArticles(){
        /*ArticlesParameters articlesParameters = new ArticlesParameters();
        ArticlesPresenter presenter = new ArticlesPresenter(this, getBaseContext(),articlesParameters);
        presenter.getArticles();*/


    }

    public void facebookShare(){
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://www.gmanetwork.com/entertainment/gma/articles/2015-08-24/18442/Miguel-Tanfelix-and-Bianca-Umali-to-launch"))
                    .setContentDescription("GMA Network App (TEST)")
                    .setContentTitle("(TEST) Miguel Tanfelix and Bianca Umali to launch")
                    .setImageUrl(Uri.parse("http://socialmediab2b.com/wp-content/uploads/2013/08/b2b-facebook-500x500.png"))
                    .build();

            shareDialog.show(this, content);
        }
    }


    @OnClick(R.id.btn_getnews)
    public void facebookPost(){
        Bundle params = new Bundle();
        params.putString("message", "This is a test message");

        if(AccessToken.getCurrentAccessToken()!=null) {
            if (AccessToken.getCurrentAccessToken().getPermissions().contains("publish_actions")) {
            /* make the API call */
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/feed",
                        params,
                        HttpMethod.POST,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                        /* handle the result */
                            }
                        }
                ).executeAsync();
            } else {
                LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
            }
        }
    }

    @OnClick(R.id.btn_login)
    public void loginFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void displayArticles(ArticlesModel model) {
        //Add to adapters then post invalidate
        articles.addAll(model.getData());
        Log.e("Post", articles.toString());
        adapter.notifyDataSetChanged();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
