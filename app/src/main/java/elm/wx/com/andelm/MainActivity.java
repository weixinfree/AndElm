package elm.wx.com.andelm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout root = findViewById(R.id.root);
        final List<Class<? extends Demo>> demos = Arrays.asList(
                Number.class,
                ReverseText.class,
                Forms.class
        );

        for (Class<? extends Demo> demo : demos) {
            final Button button = new Button(this);
            button.setText(demo.getSimpleName());
            button.setOnClickListener(v -> DemoActivity.start(this, demo));
            root.addView(button, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }


}
