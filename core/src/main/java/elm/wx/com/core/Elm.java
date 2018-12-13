package elm.wx.com.core;

import android.content.Context;
import android.view.Choreographer;

import static xin.banana.base.Objects.requireNonNull;

/**
 * Created by wangwei on 2018/12/07.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Elm<M> {

    public interface View<M> {
        void render(Context context, M model, Elm<M> elm);
    }

    public interface Update<M> {
        M update(String action, Object param, M old);
    }

    private final Tick tick;

    private final Context context;
    private final View<M> view;
    private final Update<M> update;
    private volatile M lastModel;

    public Elm(Context context,
               Update<M> update,
               M m,
               View<M> view) {

        this(ChoreographerTick.getInstance(), context, update, m, view);
    }

    public Elm(Tick tick,
               Context context,
               Update<M> update,
               M m,
               View<M> view) {



        this.tick = requireNonNull(tick);
        this.context = requireNonNull(context);
        this.view = requireNonNull(view);
        this.lastModel = requireNonNull(m);

        this.update = (action, param, old) -> {
            final M newModel = update.update(action, param, old);
            dirty = true;
            renderOnNextTick();
            lastModel = newModel;
            return newModel;
        };

        renderOnNextTick();
    }

    public void send(String action, Object value) {
        update.update(requireNonNull(action), value, lastModel);
    }

    public void send(String action) {
        update.update(requireNonNull(action), null, lastModel);
    }

    private volatile boolean dirty = true;
    private final Choreographer.FrameCallback renderAction = frameTimeNanos -> render();

    private void renderOnNextTick() {
        tick.removeAction(renderAction);
        tick.runOnNextTick(renderAction);
    }

    private void render() {
        if (!dirty) return;

        view.render(context, lastModel, this);
        dirty = false;
    }

    public void clear() {
        tick.removeAction(renderAction);
    }
}
