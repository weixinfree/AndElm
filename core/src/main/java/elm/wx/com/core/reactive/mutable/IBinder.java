package elm.wx.com.core.reactive.mutable;

import xin.banana.base.BiConsumer;
import xin.banana.base.Function;

/**
 * Created by wangwei on 2018/12/13.
 */
@SuppressWarnings("unused")
public interface IBinder<M> {

    <Attr, V> void bind(V view, String attrName, Function<M, Attr> attrGetter, BiConsumer<V, Attr> attrSetter);

}
