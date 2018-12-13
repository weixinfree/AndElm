package elm.wx.com.core.reactive.mutable;

import android.view.View;
import android.widget.TextView;

import xin.banana.base.BiConsumer;
import xin.banana.base.Function;

/**
 * Created by wangwei on 2018/12/13.
 */
@SuppressWarnings("unused")
public interface Binder<M> {

    <Attr, V> void bind(V view, String attrName, Function<M, Attr> attrGetter, BiConsumer<V, Attr> attrSetter);

    ///////////////////////////////////////////////////////////////////////////
    // Text
    ///////////////////////////////////////////////////////////////////////////

    default <V extends TextView> void text(V view, Function<M, ? extends CharSequence> attrGetter) {
        bind(view, "text", attrGetter, (view1, attr) -> view.setText(attr));
    }

    default <V extends TextView> void textSize(V view, Function<M, Float> attrGetter) {
        bind(view, "textSize", attrGetter, (view1, attr) -> view.setTextSize(attr));
    }

    default <V extends TextView> void textColor(V view, Function<M, Integer> attrGetter) {
        bind(view, "textColor", attrGetter, (view1, attr) -> view.setTextColor(attr));
    }

    ///////////////////////////////////////////////////////////////////////////
    // View
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // Image
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // Layout
    ///////////////////////////////////////////////////////////////////////////

    default <V extends View> void paddingLeft(V view, Function<M, ? extends Integer> attrGetter) {
        bind(view, "paddingLeft", attrGetter, (v, o) -> {
            final int top = view.getPaddingTop();
            final int right = view.getPaddingRight();
            final int bottom = view.getPaddingBottom();

            view.setPadding(o, top, right, bottom);
        });
    }

    default <V extends View> void paddingTop(V view, Function<M, ? extends Integer> attrGetter) {
        bind(view, "paddingTop", attrGetter, (v, o) -> {
            final int left = view.getPaddingLeft();
            final int right = view.getPaddingRight();
            final int bottom = view.getPaddingBottom();

            view.setPadding(left, o, right, bottom);
        });
    }

    default <V extends View> void paddingRight(V view, Function<M, ? extends Integer> attrGetter) {
        bind(view, "paddingRight", attrGetter, (v, o) -> {
            final int left = view.getPaddingLeft();
            final int top = view.getPaddingTop();
            final int bottom = view.getPaddingBottom();

            view.setPadding(left, top, o, bottom);
        });
    }

    default <V extends View> void paddingBottom(V view, Function<M, ? extends Integer> attrGetter) {
        bind(view, "paddingBottom", attrGetter, (v, o) -> {
            final int left = view.getPaddingLeft();
            final int top = view.getPaddingTop();
            final int right = view.getPaddingRight();

            view.setPadding(left, top, right, o);
        });
    }

    default <V extends View> void paddingH(V view, Function<M, ? extends Integer> attrGetter) {
        bind(view, "paddingH", attrGetter, (v, o) -> {
            final int top = view.getPaddingTop();
            final int bottom = view.getPaddingBottom();

            view.setPadding(o, top, o, bottom);
        });
    }

    default <V extends View> void paddingV(V view, Function<M, ? extends Integer> attrGetter) {
        bind(view, "paddingV", attrGetter, (v, o) -> {
            final int left = view.getPaddingLeft();
            final int right = view.getPaddingRight();

            view.setPadding(left, o, right, o);
        });
    }

//    default <Attr, View> void paddingTop(View view, String attrName, Function<M, Attr> attrGetter, BiConsumer<View, Attr> attrSetter);
//    default <Attr, View> void paddingBottom(View view, String attrName, Function<M, Attr> attrGetter, BiConsumer<View, Attr> attrSetter);
//    default <Attr, View> void paddingH(View view, String attrName, Function<M, Attr> attrGetter, BiConsumer<View, Attr> attrSetter);
//    default <Attr, View> void paddingV(View view, String attrName, Function<M, Attr> attrGetter, BiConsumer<View, Attr> attrSetter);
//
//    default <Attr, View> void marginLeft(View view, String attrName, Function<M, Attr> attrGetter, BiConsumer<View, Attr> attrSetter);
//    default <Attr, View> void marginTop(View view, String attrName, Function<M, Attr> attrGetter, BiConsumer<View, Attr> attrSetter);
//    default <Attr, View> void marginRight(View view, String attrName, Function<M, Attr> attrGetter, BiConsumer<View, Attr> attrSetter);
//    default <Attr, View> void marginBottom(View view, String attrName, Function<M, Attr> attrGetter, BiConsumer<View, Attr> attrSetter);
//    default <Attr, View> void marginH(View view, String attrName, Function<M, Attr> attrGetter, BiConsumer<View, Attr> attrSetter);
//    default <Attr, View> void marginV(View view, String attrName, Function<M, Attr> attrGetter, BiConsumer<View, Attr> attrSetter);

}
