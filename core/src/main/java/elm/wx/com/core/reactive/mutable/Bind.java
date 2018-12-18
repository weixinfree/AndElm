package elm.wx.com.core.reactive.mutable;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import xin.banana.base.BiConsumer;
import xin.banana.base.Function;

/**
 * Created by wangwei on 2018/12/14.
 */
@SuppressWarnings("unused")
public class Bind<M> implements elm.wx.com.core.reactive.mutable.IBinder<M> {

    private final IBinder<M> IBinder;

    public Bind(IBinder<M> IBinder) {
        this.IBinder = IBinder;
    }

    @Override
    public <Attr, V> void bind(V view, String attrName, Function<M, Attr> attrGetter, BiConsumer<V, Attr> attrSetter) {
        IBinder.bind(view, attrName, attrGetter, attrSetter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Text
    ///////////////////////////////////////////////////////////////////////////

    public <V extends TextView> void text(V v, Function<M, ? extends CharSequence> attrGetter) {
        bind(v, "elm-text", attrGetter, (view, attr) -> {
            view.setText(attr);
            if (view instanceof EditText) {
                ((EditText) view).setSelection(view.getText().length());
            }
        });
    }

    public <V extends TextView> void textSize(V v, Function<M, Float> attrGetter) {
        bind(v, "elm-textSize", attrGetter, TextView::setTextSize);
    }

    public <V extends TextView> void textColor(V v, Function<M, Integer> attrGetter) {
        bind(v, "elm-textColor", attrGetter, TextView::setTextColor);
    }

    public <V extends TextView> void textGravity(V v, Function<M, Integer> attrGetter) {
        bind(v, "elm-text-gravity", attrGetter, TextView::setGravity);
    }

    ///////////////////////////////////////////////////////////////////////////
    // View
    ///////////////////////////////////////////////////////////////////////////

    public <V extends View> void enabled(V view, Function<M, Boolean> attrGetter) {
        bind(view, "elm-enabled", attrGetter, View::setEnabled);
    }

    public <V extends View> void visibility(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-visibility", attrGetter, View::setVisibility);
    }

    public <V extends View> void visible(V v, Function<M, Boolean> attrGetter) {
        bind(v, "elm-visible", attrGetter, (view, visible) -> view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE));
    }

    public <V extends View> void gone(V view, Function<M, Boolean> attrGetter) {
        bind(view, "elm-gone", attrGetter, (v, gone) -> v.setVisibility(gone ? View.GONE : View.VISIBLE));
    }

    public <V extends View> void alpha(V view, Function<M, Float> attrGetter) {
        bind(view, "elm-alpha", attrGetter, View::setAlpha);
    }

    public <V extends View> void bgColor(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-bg-color", attrGetter, View::setBackgroundColor);
    }

    public <V extends View> void bgDrawable(V view, Function<M, ? extends Drawable> attrGetter) {
        bind(view, "elm-bg-drawable", attrGetter, View::setBackground);
    }

    public <V extends View> void bgRes(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-bg-res", attrGetter, View::setBackgroundResource);
    }

    public <V extends View> void transX(V view, Function<M, Float> attrGetter) {
        bind(view, "elm-transX", attrGetter, View::setTranslationX);
    }

    public <V extends View> void transY(V view, Function<M, Float> attrGetter) {
        bind(view, "elm-transY", attrGetter, View::setTranslationY);
    }

    public <V extends View> void clipBounds(V view, Function<M, ? extends Rect> attrGetter) {
        bind(view, "elm-clip-bounds", attrGetter, View::setClipBounds);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Image
    ///////////////////////////////////////////////////////////////////////////

    public <V extends ImageView> void imageDrawable(V view, Function<M, ? extends Drawable> attrGetter) {
        bind(view, "elm-image-drawable", attrGetter, ImageView::setImageDrawable);
    }

    public <V extends ImageView> void imageBitmap(V view, Function<M, ? extends Bitmap> attrGetter) {
        bind(view, "elm-image-bitmap", attrGetter, ImageView::setImageBitmap);
    }

    public <V extends ImageView> void imageRes(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-image-res", attrGetter, ImageView::setImageResource);
    }

    public <V extends ImageView> void imageAlpha(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-image-alpha", attrGetter, ImageView::setImageAlpha);
    }

    public <V extends ImageView> void imageScaleType(V view, Function<M, ImageView.ScaleType> attrGetter) {
        bind(view, "elm-image-scaleType", attrGetter, ImageView::setScaleType);
    }

    public <V extends ImageView> void imageMatrix(V view, Function<M, Matrix> attrGetter) {
        bind(view, "elm-image-matrix", attrGetter, ImageView::setImageMatrix);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Layout
    ///////////////////////////////////////////////////////////////////////////

    public <V extends View> void paddingLeft(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-paddingLeft", attrGetter, (v, o) -> {
            final int top = view.getPaddingTop();
            final int right = view.getPaddingRight();
            final int bottom = view.getPaddingBottom();

            view.setPadding(o, top, right, bottom);
        });
    }

    public <V extends View> void paddingTop(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-paddingTop", attrGetter, (v, o) -> {
            final int left = view.getPaddingLeft();
            final int right = view.getPaddingRight();
            final int bottom = view.getPaddingBottom();

            view.setPadding(left, o, right, bottom);
        });
    }

    public <V extends View> void paddingRight(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-paddingRight", attrGetter, (v, o) -> {
            final int left = view.getPaddingLeft();
            final int top = view.getPaddingTop();
            final int bottom = view.getPaddingBottom();

            view.setPadding(left, top, o, bottom);
        });
    }

    public <V extends View> void paddingBottom(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-paddingBottom", attrGetter, (v, o) -> {
            final int left = view.getPaddingLeft();
            final int top = view.getPaddingTop();
            final int right = view.getPaddingRight();

            view.setPadding(left, top, right, o);
        });
    }

    public <V extends View> void paddingH(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-paddingH", attrGetter, (v, o) -> {
            final int top = view.getPaddingTop();
            final int bottom = view.getPaddingBottom();

            view.setPadding(o, top, o, bottom);
        });
    }

    public <V extends View> void paddingV(V view, Function<M, Integer> attrGetter) {
        bind(view, "elm-paddingV", attrGetter, (v, o) -> {
            final int left = view.getPaddingLeft();
            final int right = view.getPaddingRight();

            view.setPadding(left, o, right, o);
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    public <V extends CompoundButton> void checked(V view, Function<M, Boolean> attrGetter) {
        bind(view, "checked", attrGetter, CompoundButton::setChecked);
    }
}
