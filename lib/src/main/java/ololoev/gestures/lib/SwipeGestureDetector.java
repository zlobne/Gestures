package ololoev.gestures.lib;

import android.content.Context;
import android.util.Log;
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
    private boolean hasMoved = false;
    private boolean hasMoved2 = false;
    private boolean hasMoved3 = false;

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
        public boolean OnThreeFingerSwipeUp(SwipeGestureDetector detector);
        public boolean OnThreeFingerSwipeDown(SwipeGestureDetector detector);
        public boolean OnThreeFingerSwipeLeft(SwipeGestureDetector detector);
        public boolean OnThreeFingerSwipeRight(SwipeGestureDetector detector);

    }

    public static class SimpleOnSwipeGestureListener implements OnSwipeGestureListener {
        public boolean OnSwipeUp(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnSwipeDown(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnSwipeLeft(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnSwipeRight(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnTwoFingerSwipeUp(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnTwoFingerSwipeDown(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnTwoFingerSwipeLeft(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnTwoFingerSwipeRight(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnThreeFingerSwipeUp(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnThreeFingerSwipeDown(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnThreeFingerSwipeLeft(SwipeGestureDetector detector){
            return true;
        };
        public boolean OnThreeFingerSwipeRight(SwipeGestureDetector detector){
            return true;
        };
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
                if (event.getPointerCount() == 1) {
                    hasMoved = moved(event);
                    hasMoved2 = false;
                    hasMoved3 = false;
                }
                if (event.getPointerCount() == 2) {
                    hasMoved2 = moved(event);
                    hasMoved = false;
                    hasMoved3 = false;
                }
                if (event.getPointerCount() == 3) {
                    hasMoved3 = moved(event);
                    hasMoved = false;
                    hasMoved2 = false;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                isMultiTouch = (mTouchStart - System.currentTimeMillis()) < MULTITOUCH_DELAY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isMultiTouch = false;
                break;
            case MotionEvent.ACTION_UP:
                if (hasMoved || hasMoved2 || hasMoved3) {
                    float currentX = event.getX(0);
                    float currentY = event.getY(0);
                    long currentTime = System.currentTimeMillis();
                    float diffX = Math.abs(downX - currentX);
                    float diffY = Math.abs(downY - currentY);
                    long time = currentTime - mTouchStart;
                    if (diffX > 100) {
                        if (downX < currentX) {
                            if (hasMoved3) {
                                mListener.OnThreeFingerSwipeRight(this);
                                break;
                            }
                            if (hasMoved2) {
                                mListener.OnTwoFingerSwipeRight(this);
                                break;
                            }
                            if (hasMoved) {
                                mListener.OnSwipeRight(this);
                                break;
                            }
                        }
                        if (downX > currentX) {
                            if (hasMoved3) {
                                mListener.OnThreeFingerSwipeLeft(this);
                                break;
                            }
                            if (hasMoved2) {
                                mListener.OnTwoFingerSwipeLeft(this);
                                break;
                            }
                            if (hasMoved) {
                                mListener.OnSwipeLeft(this);
                                break;
                            }
                        }
                    }
                    if (diffY > 100) {
                        if (downY < currentY) {
                            if (hasMoved3) {
                                mListener.OnThreeFingerSwipeDown(this);
                                break;
                            }
                            if (hasMoved2) {
                                mListener.OnTwoFingerSwipeDown(this);
                                break;
                            }
                            if (hasMoved) {
                                mListener.OnSwipeDown(this);
                                break;
                            }
                        }
                        if (downY > currentY) {
                            if (hasMoved3) {
                                mListener.OnThreeFingerSwipeUp(this);
                                break;
                            }
                            if (hasMoved2) {
                                mListener.OnTwoFingerSwipeUp(this);
                                break;
                            }
                            if (hasMoved) {
                                mListener.OnSwipeUp(this);
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
