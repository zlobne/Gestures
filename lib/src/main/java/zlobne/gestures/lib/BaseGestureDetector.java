package wj.app.audio.gestures;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by Anton Prozorov on 13.01.14.
 */
public abstract class BaseGestureDetector {
    protected final Context mContext;
    
    public BaseGestureDetector(Context context) {
        mContext = context;
    }
    
    public boolean onTouchEvent (MotionEvent event) {
        final int actionCode = event.getActionMasked();
        handleAction(actionCode, event);
        return true;
    }

    protected abstract void handleAction(int actionCode, MotionEvent event);
}
