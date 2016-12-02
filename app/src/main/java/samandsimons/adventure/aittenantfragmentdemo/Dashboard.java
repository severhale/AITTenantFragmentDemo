package samandsimons.adventure.aittenantfragmentdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;

public class Dashboard extends BaseActivity {

    @BindView(R.id.tvStatus)
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvStatus.setText("Logged in as: " + getUserEmail());
    }
}
