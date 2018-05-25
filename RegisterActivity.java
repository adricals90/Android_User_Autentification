package c.adricals.connectdb;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    AlertDialog.Builder alert ;
    EditText regName;
    EditText regEmail;
    EditText regPassword;
    EditText regConfPassword;
    Button register;
   // String regUrl = "http://10.0.2.2:9999/dbConnect/register";
   String regUrl= "http://userauthentication-env.tsgxdqnqhw.us-east-2.elasticbeanstalk.com/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPass);
        regConfPassword = findViewById(R.id.regConfirmPass);
        register = findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String  name,email, pass, confirmP;

                name = regName.getText().toString();
                email= regEmail.getText().toString();
                pass= regPassword.getText().toString();
                confirmP = regConfPassword.getText().toString();
                 alert = new AlertDialog.Builder(RegisterActivity.this);
                // pass= "paswordTest";

                if(!pass.equals(confirmP)|| name.equals("")|| email.equals("")|| pass.equals("")|| confirmP.equals("")) {

                    alert.setTitle("Missing fields");
                    alert.setMessage("Fill all the fields");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            regPassword.setText("");
                            regConfPassword.setText("");
                        }
                    });

                    AlertDialog alerts= alert.create();
                    alerts.show();

                }else {

                    StringRequest stringR = new StringRequest(Request.Method.POST, regUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(RegisterActivity.this, "Sever - Success" + response, Toast.LENGTH_LONG).show();

                            regName.setText("");
                            regEmail.setText("");
                            regPassword.setText("");
                            regConfPassword.setText("");


                            alert.setTitle("");
                            alert.setMessage(response);
                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    regPassword.setText("");
                                    regConfPassword.setText("");
                                }
                            });

                            AlertDialog alerts= alert.create();
                            alerts.show();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(RegisterActivity.this, "Error .. ", Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {


                            Map<String, String> params = new HashMap<String, String>();

                            params.put("name", name);
                            params.put("email", email);
                            params.put("pass", pass);

                            return params;
                        }
                    };

                    MySingleton.getInstance(RegisterActivity.this).addtoRwquestQueue(stringR);


                }




            }
        });



    }
}
