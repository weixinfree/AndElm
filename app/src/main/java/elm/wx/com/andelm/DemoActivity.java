package elm.wx.com.andelm;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DemoActivity extends AppCompatActivity {


    public static void start(Context context, Class<? extends Demo> clazz) {
        Intent starter = new Intent(context, DemoActivity.class);
        starter.putExtra("key", clazz.getCanonicalName());
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Demo demoImpl = getDemoImpl();
        if (demoImpl == null) {
            finish();
            return;
        }

        if (demoImpl instanceof LifecycleObserver) {
            getLifecycle().addObserver(((LifecycleObserver) demoImpl));
        }

        setContentView(demoImpl.main(this));
    }

    private Demo getDemoImpl() {
        final String demo = getIntent().getStringExtra("key");
        try {
            return ((Demo) Class.forName(demo).newInstance());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
