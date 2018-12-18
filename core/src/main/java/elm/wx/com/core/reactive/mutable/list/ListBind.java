package elm.wx.com.core.reactive.mutable.list;

import android.widget.ListView;

import java.util.List;

import elm.wx.com.core.Elm;
import elm.wx.com.core.reactive.mutable.IBinder;
import xin.banana.base.BiConsumer;
import xin.banana.base.Function;
import xin.banana.base.Supplier;

/**
 * Created by wangwei on 2018/12/18.
 */
public class ListBind<M> implements elm.wx.com.core.reactive.mutable.IBinder<M> {

    private final IBinder<M> IBinder;

    public ListBind(elm.wx.com.core.reactive.mutable.IBinder<M> IBinder) {
        this.IBinder = IBinder;
    }

    @Override
    public <Attr, V> void bind(V view, String attrName, Function<M, Attr> attrGetter, BiConsumer<V, Attr> attrSetter) {
        IBinder.bind(view, attrName, attrGetter, attrSetter);
    }

    public interface Adapter<E> {
        void notifyDataSetChanged(ListView listView, List<E> data);
    }

    static class Var<T> {
        final T value;

        Var(T value) {
            this.value = value;
        }
    }

    public <V extends ListView, E> void listAdapter(V view,
                                                    Function<M, ? extends List<E>> attrGetter,
                                                    Adapter<E> adapter) {
        bind(view,
                "elm-list-adapter",
                m -> new Var<>(attrGetter.apply(m)),
                (v, var) -> adapter.notifyDataSetChanged(v, var.value));
    }

    public <V extends ListView, E> void listAdapter(V view,
                                                    Function<M, ? extends List<E>> attrGetter,
                                                    Supplier<ElmAdapter.IHolder<E>> holderFactory) {
        listAdapter(view,
                attrGetter,
                new ElmAdapter<>(holderFactory));
    }
}
