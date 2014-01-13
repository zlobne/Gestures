package ololoev.gestures.lib;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by Anton Prozorov on 13.01.14.
 */
public class SwipeGestureDetector extends BaseGestureDetector{

    private float downX = 0;
    private float downY = 0;
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
        public boolean OnTwoFingerSwipeUp(SwipeGestureDetector detector);
        public boolean OnTwoFingerSwipeDown(SwipeGestureDetector detector);
        public boolean OnTwoFingerSwipeLeft(SwipeGestureDetector detector);
        public boolean OnTwoFingerSwipeRight(SwipeGestureDetector detector);

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
                hasMoved = moved(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                isMultiTouch = (mTouchStart - System.currentTimeMillis()) < MULTITOUCH_DELAY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isMultiTouch = false;
                break;
            case MotionEvent.ACTION_UP:
                if (hasMoved) {
                    float currentX = event.getX(0);
                    float currentY = event.getY(0);
                    long currentTime = System.currentTimeMillis();
                    float diffX = Math.abs(downX - currentX);
                    float diffY = Math.abs(downY - currentY);
                    long time = currentTime - mTouchStart;
                    if (diffX > 100) {
                        if (downX < currentX) {
                            if (event.getPointerCount() == 1) {
                                mListener.OnSwipeRight(this);
                            }
                            if (event.getPointerCount() == 2) {
                                mListener.OnTwoFingerSwipeRight(this);
                            }
                        }
                        if (downX > currentX) {
                            if (event.getPointerCount() == 1) {
                                mListener.OnSwipeLeft(this);
                            }
                            if (event.getPointerCount() == 2) {
                                mListener.OnTwoFingerSwipeLeft(this);
                            }
                        }
                    }
                    if (diffY > 100) {
                        if (downY < currentY) {
                            if (event.getPointerCount() == 1) {
                                mListener.OnSwipeDown(this);
                            }
                            if (event.getPointerCount() == 2) {
                                mListener.OnTwoFingerSwipeDown(this);
                            }
                        }
                        if (downY > currentY) {
                            if (event.getPointerCount() == 1) {
                                mListener.OnSwipeUp(this);
                            }
                            if (event.getPointerCount() == 2) {
                                mListener.OnTwoFingerSwipeUp(this);
                            }
                        }
                    }
                }
                break;
        }
    }

    private boolean moved(MotionEvent e) {
        return (Math.abs(e.getX(0) - downX) > 10);
    }
}
