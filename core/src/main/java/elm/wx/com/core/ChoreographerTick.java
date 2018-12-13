package elm.wx.com.core;

import android.view.Choreographer;

/**
 *
 * Created by wangwei on 2018/12/07.
 */
public class ChoreographerTick implements Tick {

    private static final ChoreographerTick outInstance = new ChoreographerTick();

    private ChoreographerTick() {}

    public static ChoreographerTick getInstance() {
        return outInstance;
    }

    @Override
    public void runOnNextTick(Choreographer.FrameCallback frameCallback) {
        getChoreographer().postFrameCallback(frameCallback);
    }

    private Choreographer getChoreographer() {
        return Choreographer.getInstance();
    }

    @Override
    public void removeAction(Choreographer.FrameCallback frameCallback) {
        getChoreographer().removeFrameCallback(frameCallback);
    }
}
