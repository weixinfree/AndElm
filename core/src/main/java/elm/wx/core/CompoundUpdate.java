package elm.wx.core;

import android.util.ArrayMap;

import java.util.Map;

/**
 * Update组合
 * Created by wangwei on 2018/12/13.
 */
public class CompoundUpdate<M> implements Elm.Update<M> {

    private final Map<String, Elm.Update<M>> actionUpdateMap = new ArrayMap<>();

    public static <M> CompoundUpdate<M> create() {
        return new CompoundUpdate<>();
    }

    public synchronized CompoundUpdate<M> add(String action, Elm.Update<M> update) {
        actionUpdateMap.put(action, update);
        return this;
    }

    public CompoundUpdate<M> addMutable(String action, MutableUpdate<M> update) {
        return this.add(action, update);
    }

    @SuppressWarnings("WeakerAccess")
    public synchronized Elm.Update<M> get(String action) {
        return actionUpdateMap.get(action);
    }

    @Override
    public M update(String action, Object payload, M old) {
        final Elm.Update<M> update = get(action);
        if (update == null) {
            throw new ElmException("unsupported action: " + action);
        }
        return update.update(action, payload, old);
    }
}
