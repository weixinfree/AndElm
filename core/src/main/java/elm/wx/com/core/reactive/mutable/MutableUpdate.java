package elm.wx.com.core.reactive.mutable;

import elm.wx.com.core.Elm;

/**
 * Created by wangwei on 2018/12/13.
 */
public interface MutableUpdate<M> extends Elm.Update<M>  {

    @Override
    default M update(String action, Object param, M old) {
        mutableUpdate(action, param, old);
        return old;
    }

    void mutableUpdate(String action, Object param, M model);
}
