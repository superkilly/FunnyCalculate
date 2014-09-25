package com.killy.calculate;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class StartGameFace extends Activity
{
    /**
     * 按钮个数（横向)
     */
    private static final int widNum = 8;
    
    /**
     * 按钮个数（纵向）
     */
    private static final int heiNum = 6;
    
    /**
     * 存放整个页面的所有按钮，采用二维数组
     * 第一维是按钮的横坐标
     * 第二维是按钮的纵坐标
     */
    private static final ImageButton BtnArr[][] = new ImageButton[widNum][heiNum];

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game_face);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setId(121212);
          for (int i = 0; i < 4; i++)
          {
              for (int j = 0; j < 6; j++)
              {
                  BtnArr[i][j] = new ImageButton(this);
                  // 按钮的ID是通过2000+10*横序号+纵序号(考虑到一横最多不会超过10个按钮,一纵也不会超过10个)
                  BtnArr[i][j].setId(1212 + i + j);
                  addMoveLister(BtnArr[i][j]);
                  BtnArr[i][j].setBackgroundResource(R.drawable.color);
                  RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(
                          96, 96); // 设置按钮的宽度和高度
                  // i表示的是第i行 k表示的是第k列
                  btParams.topMargin = 96 * i; // 纵坐标定位
                  btParams.leftMargin = 96 * j; // 横坐标定位
                  relativeLayout.addView(BtnArr[i][j], btParams);
              }
          }
          setContentView(relativeLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main_face, menu);
        return true;
    }
    
    private void addMoveLister(final ImageButton btn) {
        DisplayMetrics dm = getResources().getDisplayMetrics();   
        final int screenWidth = dm.widthPixels;   
        final int screenHeight = dm.heightPixels - 50;   
        btn.setOnTouchListener(new OnTouchListener() {   
            int lastX, lastY;   
            @Override  
            public boolean onTouch(View v, MotionEvent event) {     
                int ea = event.getAction();  
                Log.i("TAG", "Touch:" + ea);  
                switch (ea) {   
                case MotionEvent.ACTION_DOWN:   
                    // 获取触摸事件触摸位置的原始X坐标
                    lastX = (int) event.getRawX();   
                    lastY = (int) event.getRawY();   
                    break;   
                case MotionEvent.ACTION_MOVE: 
                    int dx = (int) event.getRawX() - lastX;   
                    int dy = (int) event.getRawY() - lastY;   
                    int l = v.getLeft() + dx;   
                    int b = v.getBottom() + dy;   
                    int r = v.getRight() + dx;   
                    int t = v.getTop() + dy;   
                    // 下面判断移动是否超出屏幕   
                    if (l < 0) {   
                        l = 0;   
                        r = l + v.getWidth();   
                    }   
                    if (t < 0) {   
                        t = 0;   
                        b = t + v.getHeight();   
                    }   
                    if (r > screenWidth) {   
                        r = screenWidth;   
                        l = r - v.getWidth();   
                    } 
                    if (b > screenHeight) {   
                        b = screenHeight;   
                        t = b - v.getHeight();   
                    }   
                    v.layout(l, t, r, b);   
                    lastX = (int) event.getRawX();   
                    lastY = (int) event.getRawY();   
                    Toast.makeText(StartGameFace.this,   
                            "当前位置：(" + l + "," + t + "," + r + "," + b + ")",   
                            Toast.LENGTH_SHORT).show();   
                    v.postInvalidate();   
                    break;   
                case MotionEvent.ACTION_UP:   
                    break;   
                }   
                return false;   
            }   
        });
      }
}
