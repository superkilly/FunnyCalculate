package com.killy.calculate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainFace extends Activity {

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
     * ����������
     *
     * @param view
     * @Exception �쳣����
     * @return void
     */
    public void typeSettingFun(View view) {
        
    }
    
    /**
     * language Setting
     * ����������
     *
     * @param view
     * @Exception �쳣����
     * @return void
     */
    public void languageSettingFun(View view) {
        setContentView(R.layout.language_setting_face);
    }
    
    /**
     * judge Neighbor
     * ����������
     *
     * @param view
     * @Exception �쳣����
     * @return void
     */
    public void judgeNeighborFun(View view) {
        
    }
    
    /**
     * ranking Check
     * ����������
     *
     * @param view
     * @Exception �쳣����
     * @return void
     */
    public void rankingCheckFun(View view) {
        
    }
    
    /**
     * contact Us
     * ����������
     *
     * @param view
     * @Exception �쳣����
     * @return void
     */
    public void contactUsFun(View view) {
        setContentView(R.layout.contact_us_face);
    }
}
