package wj.app.audio.gestures;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by Anton Prozorov on 13.01.14.
 */
public class SwipeGestureDetector extends BaseGestureDetector{

    private float downX = 0;
    private boolean isMultiTouch = false;
    private static final long MULTITOUCH_DELAY = 500;
    private long mTouchStart;
    boolean hasMoved = false;

    private final OnSwipeGestureListener mListener;

    public SwipeGestureDetector(Context context, OnSwipeGestureListener listener) {
        super(context);
        mListener = listener;
    }

    public interface OnSwipeGestureListener {
        public boolean OnSwipeUp(SwipeGestureDetector detector);
        public boolean OnSwipeDown(SwipeGestureDetector detector);
        public boolean OnSwipeLeft(SwipeGestureDetector detector);
        public boolean OnSwipeRight(SwipeGestureDetector detector);
    }
    @Override
    protected void handleAction(int actionCode, MotionEvent event) {
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX(0);
                mTouchStart = System.currentTimeMillis();
                hasMoved = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    hasMoved = moved(event);
                    break;
                }
            case MotionEvent.ACTION_POINTER_DOWN:
                isMultiTouch = (mTouchStart - System.currentTimeMillis()) < MULTITOUCH_DELAY;
            case MotionEvent.ACTION_POINTER_UP:
                isMultiTouch = false;
            case MotionEvent.ACTION_UP:
                if (event.getPointerCount() == 2 && hasMoved) {
                    float currentX = event.getX(0);
                    long currentTime = System.currentTimeMillis();
                    float diff = Math.abs(downX - currentX);
                    long time = currentTime - mTouchStart;
                    if ((downX < currentX) &&  (diff > 100)) {
                        mListener.OnSwipeRight(this);
                    }
                }
                break;
        }
    }

    private boolean moved(MotionEvent e) {
        return (Math.abs(e.getX(0) - downX) > 10);
    }
}
