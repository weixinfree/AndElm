package elm.wx.com.andelm.todo;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import elm.wx.com.andelm.R;
import elm.wx.com.andelm.util.SimpleTextWatcher;
import elm.wx.com.core.CompoundUpdate;
import elm.wx.com.core.Elm;
import elm.wx.com.core.Elms;
import elm.wx.com.core.reactive.mutable.Bind;
import elm.wx.com.core.reactive.mutable.ReactiveViewFactory;
import elm.wx.com.core.reactive.mutable.list.ElmAdapter;

/**
 * Created by wangwei on 2018/12/18.
 */
public class Item implements ElmAdapter.IHolder<TodoModel> {

    private static final String KEY_UPDATE = "UPDATE";
    private static final String KEY_FINISH = "FINISH";
    private static final String KEY_REPLACE = "REPLACE";

    private final Elm<List<TodoModel>> mainElm;

    public Item(Elm<List<TodoModel>> mainElm) {
        this.mainElm = mainElm;
    }

    private Elm<TodoModel> elm;

    @Override
    public View onCreateView(ViewGroup parent) {
        final Context context = parent.getContext();
        final View root = LayoutInflater.from(context).inflate(R.layout.elm_todo_item, parent, false);
        elm = Elms.construct(context, initUpdate(), TodoModel.EMPTY, initView(root));
        return root;
    }

    private Elm.Update<TodoModel> initUpdate() {
        return CompoundUpdate.<TodoModel>create()
                .add(KEY_REPLACE, (action, payload, old) -> ((TodoModel) payload))
                .addMutable(KEY_FINISH, (action, payload, model) -> {

                    model.isFinished = (Boolean) payload;
                    model.lastModifyTime = System.currentTimeMillis();
                })
                .addMutable(KEY_UPDATE, (action, payload, model) -> {

                    model.content = ((String) payload);
                });
    }

    private ReactiveViewFactory<TodoModel> initView(View root) {
        return (context, elm, bind) -> {

            final Bind<TodoModel> $ = new Bind<>(bind);

            // check
            CheckBox checkBox = root.findViewById(R.id.check);
            $.checked(checkBox, todoModel -> todoModel.isFinished);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> elm.send(KEY_FINISH, isChecked));

            // input
            EditText input = root.findViewById(R.id.input);
            $.text(input, todoModel -> {
                if (todoModel.isFinished) {
                    final SpannableString str = new SpannableString(todoModel.content);
                    str.setSpan(new StrikethroughSpan(), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return str;
                }

                return todoModel.content;
            });
            $.enabled(input, todoModel -> !todoModel.isFinished);

            input.addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    elm.send(KEY_UPDATE, s.toString());
                }
            });

            // time
            TextView time = root.findViewById(R.id.time);
            $.text(time, todoModel -> formatTime(todoModel.createTime));

            root.findViewById(R.id.delete).setOnClickListener(v -> mainElm.send(TodoMain.KEY_DELETE, elm.getOldModel()));

            return root;
        };
    }

    @Override
    public void onBind(int position, TodoModel data) {
        elm.send(KEY_REPLACE, data);
    }

    private String formatTime(long createTime) {
        final long now = System.currentTimeMillis();
        final long span = now - createTime;

        if (span < TimeUnit.MINUTES.toMillis(1)) {
            return "刚刚";
        } else if (span < TimeUnit.HOURS.toMillis(1)) {
            return Periods.formatPeriod(span, "m分钟前");
        } else if (span < TimeUnit.DAYS.toMillis(1)) {
            return Periods.formatPeriod(span, "h小时m分钟前");
        } else if (span < TimeUnit.DAYS.toMillis(30)) {
            return Periods.formatPeriod(span, "d天前");
        } else {
            return DateFormat.getDateInstance().format(new Date(createTime));
        }
    }
}
