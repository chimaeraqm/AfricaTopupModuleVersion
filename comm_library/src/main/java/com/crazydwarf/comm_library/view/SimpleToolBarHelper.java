package com.crazydwarf.comm_library.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

public class SimpleToolBarHelper
{
    private SimpleToolBar mSimpleToolBar;

    private AnimatorSet mAnimatorSet;

    public SimpleToolBarHelper(SimpleToolBar simpleToolBar) {
        mSimpleToolBar = simpleToolBar;
        initAnimation();
    }

    public void start() {
        mAnimatorSet.start();
    }

    private void initAnimation() {
        List<Animator> animators = new ArrayList<>();

        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(mSimpleToolBar,"wave_Angle",0f,360f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(200000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        animators.add(waveShiftAnim);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
    }

    public void cancel() {
        if (mAnimatorSet != null) {
//            mAnimatorSet.cancel();
            mAnimatorSet.end();
        }
    }
}
