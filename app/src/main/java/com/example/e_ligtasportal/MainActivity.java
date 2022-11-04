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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView list_view;
    private RequestQueue requestQueue;
    private ArrayAdapter <String> arrayAdapter;
    private String ids[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = findViewById(R.id.list_view);
        requestQueue = Volley.newRequestQueue(getBaseContext());
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.brgy_list ,R.id.textView);
        list_view.setAdapter(arrayAdapter);

        list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long l) {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity.this);
                alertdialog.setTitle("Confirmation Message");
                alertdialog.setMessage("Are you sure you want to Delete ?");
                alertdialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.2.2/eligtas-api/delete.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                                        fetchdata();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }

                        }){
                            @Override
                            protected Map<String,String> getParams() throws AuthFailureError
                            {
                                Map<String, String> map= new HashMap<String,String>();
                                map.put("id",ids[position]);
                                return map;
                            }
                        };
                        requestQueue.add(request);
;                    }
                });
                alertdialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertdialog.show();
                return false;
            }
        });


        fetchdata();

        Button addData = (Button) findViewById(R.id.button);
        addData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Add_data.class));
            }
        });
        Button updateData = (Button) findViewById(R.id.button4);
        updateData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Update_data.class));
            }
        });
        Button refresh = (Button) findViewById(R.id.button3);


        refresh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                fetchdata();
            }
        });

        Button resident = (Button) findViewById(R.id.button2);


        resident.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity( new Intent( MainActivity.this, Residents_list.class ) );
            }
        });

    }
        private void fetchdata()
        {
            arrayAdapter.clear();
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2/eligtas-api/view.php"
                    , null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            ids = new String[response.length()];

                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject object = response.getJSONObject(i);
                                    ids[i] = object.getString("id");
                                    arrayAdapter.add( "ID: " +object.getString("id")+" "+
                                            "| Title: " +
                                            object.getString("title")+
                                            "\n"+
                                            "Content: " +
                                            (object.getString("content")+
                                            "\n"+
                                            "User ID: " +
                                            (object.getString("user_id"))));
                                    }
                            } catch (Exception ex) {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(request);
        }

}