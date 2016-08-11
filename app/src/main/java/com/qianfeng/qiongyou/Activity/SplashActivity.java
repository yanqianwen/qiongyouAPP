package com.qianfeng.qiongyou.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.qianfeng.qiongyou.MainActivity;
import com.qianfeng.qiongyou.MyApp;
import com.qianfeng.qiongyou.R;


public class SplashActivity extends AppCompatActivity {
 ImageView  imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView= (ImageView) findViewById(R.id.splashimage);
        startAmination();
    }
public void startAmination(){
    AlphaAnimation animation=new AlphaAnimation(1.0f,1.0f);
    animation.setDuration(1500);
    animation.setAnimationListener(new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (MyApp.isFirstlogin()) {
                startActivity(new Intent(SplashActivity.this,GuideActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    });
    imageView.startAnimation(animation);
}
}
