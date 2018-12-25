package elm.wx.core;

import android.content.Context;
import android.view.Choreographer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import xin.banana.stream.Stream;

import static xin.banana.base.Objects.requireNonNull;

/**
 * Created by wangwei on 2018/12/07.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Elm<M> {

    public interface Tick {
        void runOnNextTick(Choreographer.FrameCallback action);

        void removeAction(Choreographer.FrameCallback action);
    }

    public interface View<M> {
        void render(Context context, M model, Elm<M> elm);

        android.view.View getRealView();
    }

    public interface Update<M> {
        M update(String action, Object payload, M old);
    }

    public interface Plugin<M> {
        void afterSend(String action, Object payload, M newModel, Elm<M> elm);
    }

    private final Tick tick;

    public final Context context;
    public final View<M> view;
    public final Update<M> update;
    private volatile M oldModel;

    private final List<Plugin<M>> plugins = new LinkedList<>();

    @SafeVarargs
    public Elm(Context context,
               Update<M> update,
               M m,
               View<M> view,
               Plugin<M>... plugins) {

        this(ChoreographerTick.getInstance(), context, update, m, view, plugins);
    }

    @SafeVarargs
    public Elm(Tick tick,
               Context context,
               Update<M> update,
               M m,
               View<M> view,
               Plugin<M>... plugins) {

        this.tick = requireNonNull(tick);
        this.context = requireNonNull(context);
        this.view = requireNonNull(view);
        this.oldModel = requireNonNull(m);

        this.update = (action, param, old) -> {
            final M newModel = update.update(action, param, old);
            dirty = true;
            renderOnNextTick();
            return newModel;
        };

        this.plugins.addAll(Arrays.asList(plugins));

        renderOnNextTick();
    }

    public void addPlugin(Plugin<M> plugin) {
        plugins.add(plugin);
    }

    public void delPlugin(Plugin<M> plugin) {
        plugins.remove(plugin);
    }

    public void send(String action, Object payload) {

        final M newModel = this.update.update(requireNonNull(action), payload, oldModel);
        Stream.forEach_(plugins, plugin -> plugin.afterSend(action, payload, newModel, this));

        oldModel = newModel;
    }

    public void send(String action) {
        update.update(requireNonNull(action), null, oldModel);
    }

    public M getOldModel() {
        return oldModel;
    }

    private volatile boolean dirty = true;
    private final Choreographer.FrameCallback renderAction = frameTimeNanos -> render();

    private void renderOnNextTick() {
        tick.removeAction(renderAction);
        tick.runOnNextTick(renderAction);
    }

    private void render() {
        if (!dirty) return;

        view.render(context, oldModel, this);
        dirty = false;
    }

    public void clear() {
        tick.removeAction(renderAction);
    }
}
