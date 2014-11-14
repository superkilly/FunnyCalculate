package com.killy.calculate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainFace extends Activity {
    /**
     * 整个系统最高分
     */
    private static int HIGH_SCORE = 0;
    
    /**
     * 设置最高分
     * @param highScore
     * 待设置的最高分
     * @return int
     * 返回设置之后的最高分
     */
    public static int setHighScore(int highScore) {
        if (highScore > HIGH_SCORE) {
            HIGH_SCORE = highScore;
        }
        return HIGH_SCORE;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_face);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_face, menu);
        return true;
    }
    
    /**
     * start the game    
     * @param view
     * @Exception 异常对象
     * @return void
     */
    public void startGameFun(View view) {    
        Intent intent = new Intent(MainFace.this, StartGameFace.class);
        startActivity(intent);
    }  
    
    /**
     * set the properties of the game
     * 设置游戏难度、类别等
     * @param view
     * @Exception 异常对象
     * @return void
     */
    public void typeSettingFun(View view) {
        
    }
    
    /**
     * language Setting
     * 设置游戏界面语言
     * @param view
     * @Exception 异常对象
     * @return void
     */
    public void languageSettingFun(View view) {
        setContentView(R.layout.language_setting_face);
    }
    
    /**
     * judge Neighbor
     * 挑战你周边的玩家
     * @param view
     * @Exception 异常对象
     * @return void
     */
    public void judgeNeighborFun(View view) {
        
    }
    
    /**
     * ranking Check
     * 排名情况查询
     * @param view
     * @Exception 异常对象
     * @return void
     */
    public void rankingCheckFun(View view) {
        
    }
    
    /**
     * contact Us
     * 联系信息
     * @param view
     * @Exception 异常对象
     * @return void
     */
    public void contactUsFun(View view) {
        Intent intent = new Intent(MainFace.this, ContactUs.class);
        startActivity(intent);
    }
}
