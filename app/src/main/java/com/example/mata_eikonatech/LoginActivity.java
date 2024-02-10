package com.example.mata_eikonatech;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        // Call the login method when the activity is created
        login("exampleUsername", "examplePassword");
    }

    private void login(String username, String password) {
        new LoginTask(username, password, new LoginTask.LoginListener() {
            @Override
            public void onLoginSuccess(String authToken) {
                // Handle successful login
            }

            @Override
            public void onLoginError(String errorMessage) {
                // Handle login error
            }
        }).execute();
    }

    private static class LoginTask extends AsyncTask<Void, Void, String> {

        private static final String BASE_URL = "http://103.74.54.133:8080/apis/V1/authenticate";
        private String username;
        private String password;
        private LoginListener listener;

        public LoginTask(String username, String password, LoginListener listener) {
            this.username = username;
            this.password = password;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(BASE_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonParams = new JSONObject();
                jsonParams.put("username", username);
                jsonParams.put("password", password);

                OutputStream os = urlConnection.getOutputStream();
                os.write(jsonParams.toString().getBytes());
                os.flush();
                os.close();

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();
                    return response.toString();
                } else {
                    return null; // Handle error response
                }
            } catch (IOException | JSONException e) {
                Log.e("LoginTask", "Error: " + e.getMessage());
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String authToken = jsonResponse.getString("authorize_token");
                    listener.onLoginSuccess(authToken);
                } catch (JSONException e) {
                    Log.e("LoginTask", "Error parsing JSON: " + e.getMessage());
                    listener.onLoginError("Error parsing JSON response");
                }
            } else {
                listener.onLoginError("Login failed. Please try again later.");
            }
        }

        public interface LoginListener {
            void onLoginSuccess(String authToken);
            void onLoginError(String errorMessage);
        }
    }
}
