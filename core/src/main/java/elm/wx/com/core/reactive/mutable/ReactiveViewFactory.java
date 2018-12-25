package elm.wx.com.core.reactive.mutable;

import android.content.Context;
import android.view.View;

import elm.wx.core.Elm;

/**
 * Created by wangwei on 2018/12/13.
 */
public interface ReactiveViewFactory<M> {
    View create(Context context, Elm<M> elm, IBinder<M> bind);
}
