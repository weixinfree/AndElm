package elm.wx.com.andelm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import elm.wx.com.core.Elm;
import elm.wx.com.core.Elms;
import elm.wx.com.core.CompoundUpdate;
import elm.wx.com.core.reactive.mutable.Bind;
import elm.wx.com.core.reactive.mutable.ReactiveViewFactory;

/**
 * Created by wangwei on 2018/12/13.
 */
public class Number implements Demo {


    private static final String INC = "inc";
    private static final String DEC = "dec";

    ///////////////////////////////////////////////////////////////////////////
    // main
    ///////////////////////////////////////////////////////////////////////////

    public View main(Context context) {
        final View root = View.inflate(context, R.layout.elm_number, null);
        Elms.construct(context, initUpdate(), 0, initView(root));
        return root;
    }

    ///////////////////////////////////////////////////////////////////////////
    // update
    ///////////////////////////////////////////////////////////////////////////

    @NonNull
    private Elm.Update<Integer> initUpdate() {
        final CompoundUpdate<Integer> update = new CompoundUpdate<>();
        update.add(INC, (action, param, old) -> old + 1);
        update.add(DEC, (action, param, old) -> old - 1);
        return update;
    }

    ///////////////////////////////////////////////////////////////////////////
    // view
    ///////////////////////////////////////////////////////////////////////////

    @NonNull
    private ReactiveViewFactory<Integer> initView(View root) {
        return (context, elm, bind) -> {

            final Bind<Integer> $ = new Bind<>(bind);

            final View inc = root.findViewById(R.id.add_1);
            inc.setOnClickListener(v -> elm.send(INC));

            final View dec = root.findViewById(R.id.minus_1);
            dec.setOnClickListener(v -> elm.send(DEC));

            final TextView number = root.findViewById(R.id.number);
            $.text(number, this::strOfInt);

            return root;
        };
    }

    private String strOfInt(Integer v) {
        return String.valueOf(v);
    }
}
