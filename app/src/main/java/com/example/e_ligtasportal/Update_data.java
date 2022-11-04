package com.example.e_ligtasportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Update_data extends AppCompatActivity {

    private EditText etID,etTitle,etContent,etUserid;
    private TextView  textView5 ,textView6, textView7;
    private Button button5;
    private RequestQueue requestQueue;
    private String ids[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_data);
        initView();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Button back = (Button) findViewById(R.id.button7);
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Update_data.this, MainActivity.class));
                Update_data.this.finish();
            }
        });
    }
    private void initView(){

        textView5 = (TextView)findViewById(R.id.textView5);
        textView6 = (TextView)findViewById(R.id.textView6);
        textView7 = (TextView)findViewById(R.id.textView7);
        etID = (EditText) findViewById(R.id.etID);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        etUserid = (EditText) findViewById(R.id.etUserid);
        button5 = (Button) findViewById(R.id.button2);
    }
    public void update(View v){

        String idString = etID.getText().toString().trim();
        if(TextUtils.isEmpty(idString)){
            Toast.makeText(this, "Enter ID", Toast.LENGTH_SHORT).show();
            return;
        }
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


        UpdateRecord(idString,titleString,contentString,useridString);
    }
    public void UpdateRecord(String id,String title,String content,String userid)
    {
        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.2.2/eligtas-api/update.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Update_data.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_data.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String, String> map= new HashMap<String,String>();
                map.put("id",id);
                map.put("title",title);
                map.put("content",content);
                map.put("userid",userid);

                return map;
            }
        };

        requestQueue.add(request);
    }
}

