package comn.example.apurva.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;
    private Button mLoginBtn;

    private Toolbar mToolbar;
    private ProgressDialog mLoginProgress;

    //FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ToolbarSet

        mToolbar=(Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoginProgress = new ProgressDialog(this);

        //FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        mLoginEmail=(TextInputLayout) findViewById(R.id.login_email);
        mLoginPassword=(TextInputLayout) findViewById(R.id.login_password);
        mLoginBtn=(Button) findViewById(R.id.login_create_btn);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=mLoginEmail.getEditText().getText().toString();
                String password=mLoginPassword.getEditText().getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

                    mLoginProgress.setTitle("Logging In");
                    mLoginProgress.setMessage("Please Wait While We Check Your Credentials.");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    login_user(email, password);
                }
            }

            private void login_user(String email, String password) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            mLoginProgress.dismiss();

                            Intent logIntent = new Intent(LoginActivity.this,MainActivity.class);
                            logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(logIntent);
                            finish();
                        }else {
                            mLoginProgress.hide();
                            Toast.makeText(LoginActivity.this,"you are not created an account",Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });
    }

    private void setSupportActionBar(Toolbar mToolbar) {
    }
}
