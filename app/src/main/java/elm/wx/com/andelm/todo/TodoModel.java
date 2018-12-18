package elm.wx.com.andelm.todo;

import com.orm.SugarRecord;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangwei on 2018/12/18.
 */
public class TodoModel extends SugarRecord<TodoModel> {

    public long createTime = -1;
    public long lastModifyTime = -1;
    public String content = "";
    public boolean isFinished = false;

    public TodoModel(long createTime, long lastModifyTime, String content, boolean isFinished) {
        this.createTime = createTime;
        this.lastModifyTime = lastModifyTime;
        this.content = content;
        this.isFinished = isFinished;
    }

    public TodoModel() {
    }

    public static final TodoModel EMPTY = new TodoModel();

    public static List<TodoModel> getPersistTodoModels() {
        final List<TodoModel> todoModels = TodoModel.listAll(TodoModel.class);
        return new LinkedList<>(todoModels);
    }
}
