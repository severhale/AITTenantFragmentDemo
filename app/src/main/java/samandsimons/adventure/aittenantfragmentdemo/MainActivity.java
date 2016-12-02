package samandsimons.adventure.aittenantfragmentdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    void onLogin() {
        Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnRegister)
    void onRegister() {
        Toast.makeText(this, "Registering", Toast.LENGTH_SHORT).show();
    }
}
