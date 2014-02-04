package ololoev.gestures.lib;

import android.content.Context;
import android.view.MotionEvent;

import zlobne.gestures.lib.R;

/**
 * Created by Anton Prozorov on 13.01.14.
 */
public class SwipeGestureDetector extends BaseGestureDetector{

    private float downX = 0;
    private float downY = 0;
    private float downX1 = 0;
    private float downY1 = 0;
    private boolean isMultiTouch = false;
    private static final long MULTITOUCH_DELAY = 500;
    private long mTouchStart;
    private boolean hasMoved = false;
    private static int SWIPE_MINIMAL_THRESHOLD = 0;
    private int pointerCount;
    private float currentX = 0;
    private float currentY = 0;
    private float currentX1 = 0;
    private float currentY1 = 0;
    private boolean isSwipe = true;

    private final OnSwipeGestureListener mListener;

    public SwipeGestureDetector(Context context, OnSwipeGestureListener listener) {
        super(context);
        mListener = listener;
        if (SWIPE_MINIMAL_THRESHOLD == 0) {
            SWIPE_MINIMAL_THRESHOLD = context.getResources().getDimensionPixelSize(R.dimen.swipe_threshold);
        }
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
        public boolean Unknown(SwipeGestureDetector detector);

    }

    public static class SimpleOnSwipeGestureListener implements OnSwipeGestureListener {
        public boolean OnSwipeUp(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnSwipeDown(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnSwipeLeft(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnSwipeRight(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnTwoFingerSwipeUp(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnTwoFingerSwipeDown(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnTwoFingerSwipeLeft(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnTwoFingerSwipeRight(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnThreeFingerSwipeUp(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnThreeFingerSwipeDown(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnThreeFingerSwipeLeft(SwipeGestureDetector detector){
            return false;
        };
        public boolean OnThreeFingerSwipeRight(SwipeGestureDetector detector){
            return false;
        };
        public boolean Unknown(SwipeGestureDetector detector){
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
                if (event.getPointerCount() > pointerCount) {
                    pointerCount = event.getPointerCount();
                }
                hasMoved = moved(event);
                currentX = event.getX(0);
                currentY = event.getY(0);
                if (event.getPointerCount() > 1) {
                    currentX1 = event.getX(1);
                    currentY1 = event.getY(1);
                }
                if ((downX - currentX > 0) && (downX1 - currentX1 < 0) || ((downX - currentX < 0) &&(downX1 - currentX1 > 0))) {
                    isSwipe = false;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                downX1 = event.getX(1);
                isMultiTouch = (mTouchStart - System.currentTimeMillis()) < MULTITOUCH_DELAY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isMultiTouch = false;
                break;
            case MotionEvent.ACTION_UP:
                currentX = event.getX(0);
                currentY = event.getY(0);
                long currentTime = System.currentTimeMillis();
                float diffX = Math.abs(currentX - downX);
                float diffY = Math.abs(currentY - downY);
                long time = currentTime - mTouchStart;
                if (hasMoved && (diffX > SWIPE_MINIMAL_THRESHOLD) && isSwipe) {
                    if (downX < currentX) {
                        switch (pointerCount) {
                            case 3:
                                mListener.OnThreeFingerSwipeRight(this);
                                break;
                            case 2:
                                if (downX1 < currentX1) {
                                    mListener.OnTwoFingerSwipeRight(this);
                                }
                                else {
                                    mListener.Unknown(this);
                                }
                                break;
                            case 1:
                                mListener.OnSwipeRight(this);
                                break;
                        }
                    }
                    if (downX > currentX) {
                        switch (pointerCount) {
                            case 3:
                                mListener.OnThreeFingerSwipeLeft(this);
                                break;
                            case 2:
                                if (downX1 > currentX1) {
                                    mListener.OnTwoFingerSwipeLeft(this);
                                }
                                else {
                                    mListener.Unknown(this);
                                }
                                break;
                            case 1:
                                mListener.OnSwipeLeft(this);
                                break;
                        }
                    }
                }
                if (diffY > SWIPE_MINIMAL_THRESHOLD) {
                    if (downY < currentY) {
                        switch (pointerCount) {
                            case 3:
                                mListener.OnThreeFingerSwipeDown(this);
                                break;
                            case 2:
                                mListener.OnTwoFingerSwipeDown(this);
                                break;
                            case 1:
                                mListener.OnSwipeDown(this);
                                break;
                        }
                    }
                    if (downY > currentY) {
                        switch (pointerCount) {
                            case 3:
                                mListener.OnThreeFingerSwipeUp(this);
                                break;
                            case 2:
                                mListener.OnTwoFingerSwipeUp(this);
                                break;
                            case 1:
                                mListener.OnSwipeUp(this);
                                break;
                        }
                    }
                }
                pointerCount = 0;
                currentX1 = 0;
                downX1 = 0;
                currentX = 0;
                downX = 0;
        }
    }

    private boolean moved(MotionEvent e) {
        return (Math.abs(e.getX(0) - downX) > 50);
    }
}
