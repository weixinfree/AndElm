package elm.wx.com.core;

import android.view.Choreographer;

/**
 * Created by wangwei on 2018/12/13.
 */
public interface Tick {
    void runOnNextTick(Choreographer.FrameCallback action);

    void removeAction(Choreographer.FrameCallback action);
}
