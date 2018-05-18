package c.adricals.connectdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder alertLog ;
    TextView signUp ;
    EditText logEmail;
    EditText logPassword;
    Button login;
    String loginUrl= "http://10.0.2.2:9999/dbConnect/login";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = findViewById(R.id.regText);
        logEmail = findViewById(R.id.loginEmail);
        logPassword = findViewById(R.id.loginPass);
        login = findViewById(R.id.logButton);
        alertLog = new AlertDialog.Builder(this);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email, password;

                email =  logEmail.getText().toString();
                password = logPassword.getText().toString();

                if (email.equals("")|| password.equals("")){

                    alertLog.setTitle("Missing fields");
                    alertLog.setMessage("Fill all the fields");
                    alertLog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logEmail.setText("");
                            logPassword.setText("");
                        }
                    });

                    AlertDialog alerts = alertLog.create();
                    alerts.show();



                }else{


                    StringRequest stringUsers = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equals("successful")) {
                                Toast.makeText(MainActivity.this, "Sever - Success" + response, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(MainActivity.this, accessActivity.class));




                            }else if (response.equals("failed")){
                               Toast.makeText(MainActivity.this, "failed" + response, Toast.LENGTH_LONG).show();


                                alertLog.setTitle("");
                                alertLog.setMessage("User not found, try again");
                                alertLog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        logEmail.setText("");
                                        logPassword.setText("");
                                    }
                                });

                                AlertDialog alerts = alertLog.create();
                                alerts.show();

                            }




                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> myParams = new HashMap<String, String>();

                            myParams.put("email", email);
                            myParams.put("pass", password);


                            return myParams;
                        }
                    };


                    MySingleton.getInstance(MainActivity.this).addtoRwquestQueue(stringUsers);


                }







            }
        });



    }
}
