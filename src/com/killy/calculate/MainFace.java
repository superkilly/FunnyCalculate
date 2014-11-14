package com.killy.calculate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainFace extends Activity {
    /**
     * ����ϵͳ��߷�
     */
    private static int HIGH_SCORE = 0;
    
    /**
     * ������߷�
     * @param highScore
     * �����õ���߷�
     * @return int
     * ��������֮�����߷�
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
     * @Exception �쳣����
     * @return void
     */
    public void startGameFun(View view) {    
        Intent intent = new Intent(MainFace.this, StartGameFace.class);
        startActivity(intent);
    }  
    
    /**
     * set the properties of the game
     * ������Ϸ�Ѷȡ�����
     * @param view
     * @Exception �쳣����
     * @return void
     */
    public void typeSettingFun(View view) {
        
    }
    
    /**
     * language Setting
     * ������Ϸ��������
     * @param view
     * @Exception �쳣����
     * @return void
     */
    public void languageSettingFun(View view) {
        setContentView(R.layout.language_setting_face);
    }
    
    /**
     * judge Neighbor
     * ��ս���ܱߵ����
     * @param view
     * @Exception �쳣����
     * @return void
     */
    public void judgeNeighborFun(View view) {
        
    }
    
    /**
     * ranking Check
     * ���������ѯ
     * @param view
     * @Exception �쳣����
     * @return void
     */
    public void rankingCheckFun(View view) {
        
    }
    
    /**
     * contact Us
     * ��ϵ��Ϣ
     * @param view
     * @Exception �쳣����
     * @return void
     */
    public void contactUsFun(View view) {
        Intent intent = new Intent(MainFace.this, ContactUs.class);
        startActivity(intent);
    }
}
