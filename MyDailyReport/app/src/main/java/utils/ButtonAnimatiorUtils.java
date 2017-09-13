package utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;

/**
 * Created by laizexin on 2017/9/12.
 * FloatingActionButton动画
 */

public class ButtonAnimatiorUtils {

    private static boolean flag = true;

    public static void BindButtonAnimatior(WindowManager manager, final FloatingActionButton button, float x){
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        if (flag) {
            //让隐藏的小球显示
            button.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(button, "translationY", 0, -height / x).setDuration(500);
            //加入动画插值器,增加效果
            animator.setInterpolator(new OvershootInterpolator());
            animator.start();

            //flag的值一定要在动画结束时在改变,否则会使小球在同一次点击时执行的动画不同

            animator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    flag = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

        } else {
            ObjectAnimator animator = ObjectAnimator.ofFloat(button, "translationY", -height / x, 0).setDuration(500);
            animator.start();
            //给动画添加监听器
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    //动画结束时,小球回到原点,让小球隐藏
                    button.setVisibility(View.GONE);
                    flag = true;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }
}
