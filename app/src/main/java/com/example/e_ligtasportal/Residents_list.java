package com.example.e_ligtasportal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Residents_list extends AppCompatActivity {
    private ListView list_view;
    private RequestQueue requestQueue;
    private ArrayAdapter <String> arrayAdapter;
    private String ids[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_residents_list );

        list_view = findViewById(R.id.list_view);
        requestQueue = Volley.newRequestQueue(getBaseContext());
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.brgy_list ,R.id.textView);
        list_view.setAdapter(arrayAdapter);

        fetchdata();

        Button back = (Button) findViewById(R.id.button5);
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Residents_list.this, MainActivity.class));
                Residents_list.this.finish();
            }
        });

    }
    private void fetchdata()
    {
        arrayAdapter.clear();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2/eligtas-api/view_residents.php"
                , null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ids = new String[response.length()];

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                ids[i] = object.getString("id");
                                arrayAdapter.add( "ID: " +object.getString("id")+"\n"+
                                        "Firstname: " +
                                        object.getString("firstname")+
                                        "\n"+
                                        "Lastname: " + (object.getString("lastname")+ "\n"+
                                        "Role: " + (object.getString("role") +"\n"+
                                        "Email: " + (object.getString("email")))));
                            }
                        } catch (Exception ex) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Residents_list.this, "error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }

}