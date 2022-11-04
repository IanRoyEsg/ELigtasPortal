package com.example.e_ligtasportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Add_data extends AppCompatActivity {

    private EditText etTitle,etContent,etUserid;
    private Button button2;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        initView();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Button back = (Button) findViewById(R.id.button6);
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Add_data.this, MainActivity.class));
            }
        });

    }

    private void initView(){
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        etUserid = (EditText) findViewById(R.id.etUserid);
        button2 = (Button) findViewById(R.id.button2);
    }

    public void submit(View v){

        String titleString = etTitle.getText().toString().trim();
        if(TextUtils.isEmpty(titleString)){
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
            return;
        }
        String contentString = etContent.getText().toString().trim();
        if(TextUtils.isEmpty(contentString)){
            Toast.makeText(this, "Enter Content", Toast.LENGTH_SHORT).show();
            return;
        }
        String useridString = etUserid.getText().toString().trim();
        if(TextUtils.isEmpty(useridString)){
            Toast.makeText(this, "Enter UserID", Toast.LENGTH_SHORT).show();
            return;
        }

        AddRecord(titleString,contentString,useridString);

    }
    public void AddRecord(String title,String content,String userid)
    {
        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.2.2/eligtas-api/insert.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Add_data.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Add_data.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String, String> map= new HashMap<String,String>();
                map.put("title",title);
                map.put("content",content);
                map.put("userid",userid);

                return map;
            }
        };

        requestQueue.add(request);
    }
}