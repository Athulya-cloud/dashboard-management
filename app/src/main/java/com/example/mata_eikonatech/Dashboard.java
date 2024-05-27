package com.example.mata_eikonatech;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity implements OnMapReadyCallback {
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private PopupWindow popupWindow;
    private View overlay;
    private View popupView;
    private ImageView capturedImageView;
    private MapView mapView;
    DrawerLayout drawerlayout;
    private String authToken;
    ImageView menu;
    private Bitmap capturedImageBitmap;
    TextView attendance, schedule, logout;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        drawerlayout = findViewById(R.id.drawerlayout);
        menu = findViewById(R.id.menu);
        overlay = new View(this);
        overlay.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        overlay.setBackgroundColor(0x80000000); // Semi-transparent black color
        overlay.setVisibility(View.GONE);
        DrawerLayout rootLayout = findViewById(R.id.drawerlayout);
        rootLayout.addView(overlay);
        authToken = getIntent().getStringExtra("AUTH_TOKEN");
        if (authToken == null) {
            Log.e("Dashboard", "AuthToken is null");
            Toast.makeText(this, "Authentication token is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d("Dashboard", "Retrieved AuthToken: " + authToken);
        TextView logout = findViewById(R.id.logout);
        TextView attendance = findViewById(R.id.attendance);
        TextView schedule = findViewById(R.id.schedule);
        TextView notifications = findViewById(R.id.notifications);
        TextView contact = findViewById(R.id.contact);
        TextView recentpunches = findViewById(R.id.recentpunchestextview);
        TextView request = findViewById(R.id.request);
        TextView announcement = findViewById(R.id.announcement);
        TextView mystatus = findViewById(R.id.mystatus);
        TextView profile = findViewById(R.id.profile);
        ImageButton MarkAttendance = findViewById(R.id.MarkAttendance);
        MarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupPopupWindow();
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerlayout);
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Dashboard.this, Attendance.class);
                Log.d("success", "ATTENDANCE OPEN");
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Dashboard.this, Schedule.class);
                Log.d("success", "SCHEDULE OPEN");
            }
        });
        recentpunches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, RecentPunches.class);
                startActivity(intent);
                Log.d("success", "Recent Punches");
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Notifications.class);
                startActivity(intent);
                Log.d("success", "Notifications");
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Contact.class);
                startActivity(intent);
                Log.d("success", "Contact");
            }
        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Request.class);
                startActivity(intent);
                Log.d("success", "REQUEST");
            }
        });
        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Announcement.class);
                startActivity(intent);
                Log.d("success", "ANNOUNCEMENT");
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Profile.class);
                startActivity(intent);
                Log.d("success", "PROFILE");
            }
        });
        mystatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, MyStatus.class);
                startActivity(intent);
                Log.d("success", "MYSTATUS");
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Logout successfully.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Dashboard.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Log.d("success", "LOGOUT");
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    private void setupPopupWindow() {
        Log.d("Dashboard", "Setting up popup window");
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.punchinpopup, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                overlay.setVisibility(View.GONE);
            }
        });
        overlay.setVisibility(View.VISIBLE);
        Button saveButton = popupView.findViewById(R.id.saveButton);
        ImageView imageButtonMarkAttendance = popupView.findViewById(R.id.imageButtonMarkAttendance);
        capturedImageView = popupView.findViewById(R.id.capturedImageView);
        Spinner workCodeSpinner = popupView.findViewById(R.id.workCodeSpinner);
        Spinner otherDropdownSpinner = popupView.findViewById(R.id.otherDropdownSpinner);
        workCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        otherDropdownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayList<String> work_code_array = new ArrayList<>();
        ArrayList<String> function_key_array = new ArrayList<>();
        work_code_array.add("Manual Log");
        work_code_array.add("Leave");
        work_code_array.add("Training");
        work_code_array.add("Overtime");
        work_code_array.add("Schedule Adjustment");
        function_key_array.add("Check In");
        function_key_array.add("Check Out");
        function_key_array.add("Break In");
        function_key_array.add("Break Out");
        function_key_array.add("Overtime In");
        function_key_array.add("Overtime Out");
        ArrayAdapter<String> workcodeadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, work_code_array);
        ArrayAdapter<String> functionkeyadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, function_key_array);
        workcodeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        functionkeyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workCodeSpinner.setAdapter(workcodeadapter);
        otherDropdownSpinner.setAdapter(functionkeyadapter);
        imageButtonMarkAttendance.setClickable(true);
        imageButtonMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Dashboard", "OnClick is called");
                if (checkPermission()) {
                    openCamera();
                } else {
                    requestPermission();
                }
            }
        });
        saveButton.setOnClickListener(view -> {
            if (capturedImageBitmap != null) {
                markAttendance(capturedImageBitmap);
            } else {
                Toast.makeText(Dashboard.this, "Please capture an image first.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerlayout) {
        drawerlayout.openDrawer(GravityCompat.START);
        Log.d("success", "Drawer Opened");
    }

    public static void closeDrawer(DrawerLayout drawerlayout) {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerlayout);
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null && extras.containsKey("data")) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    String base64Image = encodeImageToBase64(imageBitmap);
                    Log.d("success", "Image captured and encoded");
                    capturedImageView.setImageBitmap(imageBitmap);
                    capturedImageView.setVisibility(View.VISIBLE); // Ensure the ImageView is visible
                    capturedImageBitmap = imageBitmap; // Set the captured image to the field
                }
            }
        }
    }

    private String encodeImageToBase64(Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();
        Log.d("success", "image converted");
        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }

    private void markAttendance(Bitmap imageBitmap) {
        String url = "https://matasecurity.com/apis/V1/mark/attendance";
        String base64Image = encodeImageToBase64(imageBitmap);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("siteId", 1);
            jsonObject.put("latitude", 12.34);
            jsonObject.put("longitude", 14.34);
            jsonObject.put("imageBase64", base64Image); // Corrected this line
            jsonObject.put("department", "");
            jsonObject.put("designation", "");
            jsonObject.put("branch", "");
            jsonObject.put("deviceName", "");
            jsonObject.put("sortField", "");
            jsonObject.put("sortOrder", "");
            jsonObject.put("pageNo", "1");
            jsonObject.put("pageSize", "10");
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            // Handle success
                            Toast.makeText(Dashboard.this, "Attendance marked successfully", Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                        } else {
                            // Handle other responses or errors
                            Toast.makeText(Dashboard.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Unknown error";
                        if (error.networkResponse != null) {
                            String responseBody = new String(error.networkResponse.data);
                            try {
                                JSONObject data = new JSONObject(responseBody);
                                errorMessage = data.optString("message", responseBody);
                            } catch (Exception e) {
                                errorMessage = responseBody; // Fallback to plain text error message
                            }
                        }
                        Toast.makeText(Dashboard.this, "Failed to mark attendance: " + errorMessage, Toast.LENGTH_LONG).show();
                        Log.e("Dashboard", "Attendance marking failed: " + errorMessage, error);
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonObject.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + authToken);
                headers.put("Content-Type", "application/json");

                // Log the Bearer token for verification
                Log.d("Dashboard", "Authorization: Bearer " + authToken);

                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng bengaluru = new LatLng(12.9716, 77.5946);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bengaluru, 12));
    }
}
