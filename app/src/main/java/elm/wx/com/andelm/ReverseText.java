package elm.wx.com.andelm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import elm.wx.com.andelm.util.SimpleTextWatcher;
import elm.wx.com.core.Elm;
import elm.wx.com.core.Elms;
import elm.wx.com.core.reactive.mutable.Bind;
import elm.wx.com.core.reactive.mutable.ReactiveViewFactory;

/**
 * Created by wangwei on 2018/12/13.
 */
public class ReverseText implements Demo {

    private static final String REVERSE = "reverse";

    public View construct(Context context) {
        final View root = View.inflate(context, R.layout.elm_reverse_text, null);
        Elms.construct(context, initUpdate(), "input something", initView(root));

        return root;
    }

    @NonNull
    private Elm.Update<CharSequence> initUpdate() {
        return (action, param, old) -> ((CharSequence) param);
    }

    @NonNull
    private ReactiveViewFactory<CharSequence> initView(View root) {
        return (context, elm, bind) -> {

            final EditText input = root.findViewById(R.id.input);
            input.addTextChangedListener(getWatcher(elm));

            final TextView reverse = root.findViewById(R.id.text);

            final Bind<CharSequence> $ = new Bind<>(bind);
            $.text(reverse, ReverseText::reverseStr);
        };
    }

    @NonNull
    private TextWatcher getWatcher(Elm<CharSequence> _elm) {
        return new SimpleTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("ReverseText", "afterTextChanged() called with: s = [" + s + "]");
                _elm.send(REVERSE, s);
            }
        };
    }

    private static CharSequence reverseStr(CharSequence str) {
        final StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }

        return sb.toString();
    }
}
