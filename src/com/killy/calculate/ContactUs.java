package com.killy.calculate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 类名称：ContactUs
 * 类描述：
 * @author：superkilly
 * @create：2014-10-23 下午10:05:19
 * @version SMSManager V0.1
 *
 * 修改人：superkilly
 * 修改时间：2014-10-23 下午10:05:19
 * 修改备注：
 */
public class ContactUs extends Activity
{
    /**
     * 游戏主页面布局
     */
    RelativeLayout relativeLayout = null;
    
    @SuppressLint("UseSparseArrays")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_face);
        relativeLayout = new RelativeLayout(this);
        relativeLayout.setId(121212);
        relativeLayout.setBackgroundResource(R.drawable.contactus);

        // 制作者
        TextView ownerTxt = new TextView(this);
        ownerTxt.setText(R.string.contact_producer);
        RelativeLayout.LayoutParams ownerParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, 30); // 设置按钮的宽度和高度
        ownerParams.leftMargin = 20;// 横坐标定位
        ownerParams.topMargin = 26;// 纵坐标定位
        relativeLayout.addView(ownerTxt, ownerParams);

        // 制作者
        TextView emailTxt = new TextView(this);
        emailTxt.setText(R.string.contact_email);
        RelativeLayout.LayoutParams emailParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, 30); // 设置按钮的宽度和高度
        emailParams.leftMargin = 20;// 横坐标定位
        emailParams.topMargin = 66;// 纵坐标定位
        relativeLayout.addView(emailTxt, emailParams);

        // 地址
        TextView addressTxt = new TextView(this);
        addressTxt.setText(R.string.contact_address);
        RelativeLayout.LayoutParams addressParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, 30); // 设置按钮的宽度和高度
        addressParams.leftMargin = 20;// 横坐标定位
        addressParams.topMargin = 106;// 纵坐标定位
        relativeLayout.addView(addressTxt, addressParams);
        
        Button voteBtn = new  Button(this);
        voteBtn.setBackgroundResource(R.drawable.button1);
        voteBtn.setText(R.string.contact_vote);
        voteBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addVote();
            }
        });
        RelativeLayout.LayoutParams voteParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, 60); // 设置按钮的宽度和高度
        voteParams.leftMargin = 20;// 横坐标定位
        voteParams.topMargin = 146;// 纵坐标定位
        relativeLayout.addView(voteBtn, voteParams);
        
        setContentView(relativeLayout);
    }
    
    private void addVote() {
        AlertDialog alert = new AlertDialog.Builder(this).setTitle("通知")
                .setMessage("您骚扰了王女士，已经报警，请站在原地不要随意走动，并且将手机高高举起")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    // 处理确定按钮点击事件
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        return;
                    }
                }).create();
        alert.show();
    }
}