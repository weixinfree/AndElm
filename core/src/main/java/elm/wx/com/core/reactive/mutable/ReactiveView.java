package elm.wx.com.core.reactive.mutable;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import elm.wx.com.core.Elm;
import xin.banana.base.BiConsumer;
import xin.banana.base.Function;
import xin.banana.base.Objects;

/**
 * 允许状态绑定的view
 * Created by wangwei on 2018/12/07.
 */
public abstract class ReactiveView<M> implements Elm.View<M> {

    private boolean hasCreateView = false;

    private View realView;

    @Override
    public final void render(Context context, M model, Elm<M> elm) {
        if (!hasCreateView) {
            realView = onCreateView(context, elm);
            hasCreateView = true;
        }

        refresh(model);
    }

    protected abstract View onCreateView(Context context, Elm<M> elm);

    static class Bind<M> {
        final Object realView;
        final String attrName;
        final Function<M, Object> attrGetter;
        final BiConsumer<Object, Object> attrSetter;

        Bind(Object realView, String attrName, Function<M, Object> attrGetter, BiConsumer<Object, Object> attrSetter) {
            this.realView = realView;
            this.attrName = attrName;
            this.attrGetter = attrGetter;
            this.attrSetter = attrSetter;
        }
    }

    private final List<Bind<M>> bindList = new ArrayList<>();

    @SuppressWarnings("WeakerAccess")
    public <Attr, RV> void bind(RV realView, String attrName, Function<M, Attr> attrGetter, BiConsumer<RV, Attr> attrSetter) {
        //noinspection unchecked
        bindList.add(new Bind<>(realView, attrName, ((Function<M, Object>) attrGetter), ((BiConsumer<Object, Object>) attrSetter)));
    }

    static class State {

        private final Map<Object, Map<String, Object>> viewAttrs = new IdentityHashMap<>();

        void setAttrValue(Object v, String attrName, Object attrValue) {
            Map<String, Object> attrs = viewAttrs.get(v);
            if (attrs == null) {
                attrs = new HashMap<>();
            }

            attrs.put(attrName, attrValue);

            viewAttrs.put(v, attrs);
        }

        Object getAttrValue(Object v, String attrName) {
            final Map<String, Object> attrs = viewAttrs.get(v);
            if (attrs == null) return null;

            return attrs.get(attrName);
        }
    }

    private final State lastState = new State();

    @SuppressWarnings("WeakerAccess")
    public void refresh(M model) {
        for (Bind<M> bind : bindList) {
            final Object realView = bind.realView;
            final String attrName = bind.attrName;

            final Object oldAttrValue = lastState.getAttrValue(realView, attrName);
            final Object newAttrValue = bind.attrGetter.apply(model);

            if (!Objects.equals(oldAttrValue, newAttrValue)) {

                lastState.setAttrValue(realView, attrName, newAttrValue);

                final BiConsumer<Object, Object> attrSetter = bind.attrSetter;
                attrSetter.accept(realView, newAttrValue);
            }
        }
    }

    @Override
    public View getRealView() {
        return realView;
    }
}
