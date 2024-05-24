package com.example.mata_eikonatech;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.util.Log;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences("authPrefs", Context.MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                login(username, password);
            }
        });
    }

    private void login(String username, String password) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("username", username);
            jsonParams.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://matasecurity.com/apis/V1/authenticate", jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String authToken = response.getString("authorize_token");
                            storeAuthToken(authToken); // Store the authToken securely
                            onLoginSuccess();
                            Log.d("success","login successful");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onLoginError("Error parsing JSON response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LoginActivity", "Login Error: " + error.getMessage());
                        onLoginError("Login failed. Please try again later.");
                    }
                });

        requestQueue.add(request);
    }

    private void storeAuthToken(String authToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("authToken", authToken);
        Log.d("success","authtokenstored");
        editor.apply();
    }

    private void onLoginSuccess() {
        // Navigate to the next screen or perform any other action on successful login
        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this,Dashboard.class);
        startActivity(intent);
        finish();
    }

    private void onLoginError(String errorMessage) {
        // Display error message to the user
        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
