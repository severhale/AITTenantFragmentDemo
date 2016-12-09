package samandsimons.adventure.aittenantfragmentdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import samandsimons.adventure.aittenantfragmentdemo.fragment.ProgressActivity;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

public class MainActivity extends ProgressActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.rbLandlord)
    RadioButton rbLandlord;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        etEmail.setText("severhal@oberlin.edu");
        etPassword.setText("123456");
    }

    @OnClick(R.id.btnLogin)
    void onLogin() {
        if (!isFormValid()) {
            return;
        }

        showProgressDialog();
        firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            FirebaseUser fbUser = task.getResult().getUser();
                            User.getCurrentUser().setFirebaseUser(fbUser);
                            startActivity(new Intent(MainActivity.this, Dashboard.class));
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @OnClick(R.id.btnRegister)
    void onRegister() {
        if (!isFormValid()) {
            return;
        }

        showProgressDialog();
        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            FirebaseUser fbUser = task.getResult().getUser();
                            fbUser.updateProfile(new UserProfileChangeRequest.Builder().
                                    setDisplayName(usernameFromEmail(fbUser.getEmail())).build());

                            User.UserType userType = rbLandlord.isChecked() ? User.UserType.LANDLORD : User.UserType.TENANT;
                            User user = new User(usernameFromEmail(fbUser.getEmail()), fbUser.getEmail(), userType);
                            database.child("users").child(fbUser.getUid()).setValue(user);

                            Toast.makeText(MainActivity.this, "User created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean isFormValid() {
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Required");
            return false;
        }

        return true;
    }
}
