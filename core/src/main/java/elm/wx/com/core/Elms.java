package elm.wx.com.core;

import android.content.Context;

import elm.wx.com.core.reactive.mutable.ReactiveView;
import elm.wx.com.core.reactive.mutable.ReactiveViewFactory;

/**
 * Created by wangwei on 2018/12/13.
 */
public class Elms {

    private Elms() {
        //no instance
    }

    public static <M> void construct(Context context, Elm.Update<M> update, M initModel, ReactiveViewFactory<M> factory) {
        construct(context, update, initModel, new ReactiveView<M>() {
            @Override
            protected void onCreateView(Context context, Elm<M> elm) {
                factory.create(context, elm, this::bind);
            }
        });
    }

    @SuppressWarnings("WeakerAccess")
    public static <M> void construct(Context context, Elm.Update<M> update, M initModel, Elm.View<M> view) {
        new Elm<>(context, update, initModel, view);
    }



}
