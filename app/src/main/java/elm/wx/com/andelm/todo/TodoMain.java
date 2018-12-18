package elm.wx.com.andelm.todo;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import elm.wx.com.andelm.Demo;
import elm.wx.com.andelm.R;
import elm.wx.com.core.CompoundUpdate;
import elm.wx.com.core.Elm;
import elm.wx.com.core.Elms;
import elm.wx.com.core.reactive.mutable.ReactiveViewFactory;
import elm.wx.com.core.reactive.mutable.list.ListBind;

/**
 * Created by wangwei on 2018/12/18.
 */
public class TodoMain implements Demo, GenericLifecycleObserver {

    static final String KEY_ADD = "ADD";
    static final String KEY_DELETE = "DELETE";

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    private Elm<List<TodoModel>> elm;

    @Override
    public View main(Context context) {
        final View root = View.inflate(context, R.layout.elm_todo, null);
        elm = Elms.construct(context, initUpdate(), TodoModel.getPersistTodoModels(), initView(root));
        return root;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 
    ///////////////////////////////////////////////////////////////////////////

    private Elm.Update<List<TodoModel>> initUpdate() {
        return CompoundUpdate.<List<TodoModel>>create()
                .addMutable(KEY_ADD, (action, param, models) -> {
                    final TodoModel todoModel = new TodoModel();
                    todoModel.createTime = System.currentTimeMillis();
                    todoModel.lastModifyTime = System.currentTimeMillis();
                    models.add(todoModel);
                })
                .add(KEY_DELETE, (action, payload, model) -> {
                    final TodoModel todoModel = (TodoModel) payload;
                    todoModel.delete();

                    return TodoModel.getPersistTodoModels();
                });

    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    @NonNull
    private ReactiveViewFactory<List<TodoModel>> initView(View root) {
        return (context, elm, bind) -> {

            final ListView todoList = root.findViewById(R.id.todo_list);
            final ListBind<List<TodoModel>> $ = new ListBind<>(bind);
            $.listAdapter(todoList, todoModels -> todoModels, () -> new Item(elm));

            // add
            root.findViewById(R.id.add_todo).setOnClickListener(v -> elm.send(KEY_ADD));

            // sort by create time
            // sort by modify time

            return root;
        };
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if (elm == null) return;

        if (event == Lifecycle.Event.ON_STOP) {
            for (TodoModel todoModel : elm.getOldModel()) {
                todoModel.save();
            }
        }
    }
}
