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
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistarActivity extends AppCompatActivity {
    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCraeteBtn;
    private FirebaseAuth mAuth;

    private Toolbar mToolbar;

    private DatabaseReference mDatabase;

    //Prograssdialog
    private ProgressDialog mRegProgress;

    //Firbase Auth
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        //Toolbar Set
          mToolbar = (Toolbar) findViewById(R.id.registar_toolbar);
          setSupportActionBar(mToolbar);
          getSupportActionBar().setTitle("Create Account");
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          mRegProgress = new ProgressDialog(this);

        //Firebase Auth

        mAuth = FirebaseAuth.getInstance();

        //Android Fields

        mDisplayName =(TextInputLayout) findViewById(R.id.reg_display_name);
        mEmail = (TextInputLayout) findViewById(R.id.reg_email);
        mPassword = (TextInputLayout) findViewById(R.id.reg_password);
        mCraeteBtn = (Button) findViewById(R.id.reg_create_btn);

        mCraeteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name=mDisplayName.getEditText().getText().toString();
                String email=mEmail.getEditText().getText().toString();
                String password=mPassword.getEditText().getText().toString();

                if (!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please Wait While We Create Your Account!");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    register_user(display_name, email, password);
                }
            }
        });
    }

    private void setSupportActionBar(Toolbar mToolbar) {
    }

    private void register_user(final String display_name, String email, String password){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    HashMap<String,String> UserMap = new HashMap<>();
                    UserMap.put("name",display_name);
                    UserMap.put("status","hi there ,i'm using Chat App");
                    UserMap.put("image","default");
                    UserMap.put("thumb_image","default");

                    mDatabase.setValue(UserMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                                mRegProgress.dismiss();
                                Intent mainIntent=new Intent(RegistarActivity.this,MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();

                            }
                        }
                    });

                    /*

                    */

                }else
                {
                    mRegProgress.hide();
                    Toast.makeText(RegistarActivity.this,"Cannot Sign In.Please Check The Form And Try Again.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
