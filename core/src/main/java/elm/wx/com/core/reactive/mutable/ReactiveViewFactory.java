package elm.wx.com.core.reactive.mutable;

import android.content.Context;

import elm.wx.com.core.Elm;

/**
 * Created by wangwei on 2018/12/13.
 */
public interface ReactiveViewFactory<M> {
    void create(Context context, Elm<M> elm, IBinder<M> bind);
}
