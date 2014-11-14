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
 * �����ƣ�ContactUs
 * ��������
 * @author��superkilly
 * @create��2014-10-23 ����10:05:19
 * @version SMSManager V0.1
 *
 * �޸��ˣ�superkilly
 * �޸�ʱ�䣺2014-10-23 ����10:05:19
 * �޸ı�ע��
 */
public class ContactUs extends Activity
{
    /**
     * ��Ϸ��ҳ�沼��
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

        // ������
        TextView ownerTxt = new TextView(this);
        ownerTxt.setText(R.string.contact_producer);
        RelativeLayout.LayoutParams ownerParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, 30); // ���ð�ť�Ŀ�Ⱥ͸߶�
        ownerParams.leftMargin = 20;// �����궨λ
        ownerParams.topMargin = 26;// �����궨λ
        relativeLayout.addView(ownerTxt, ownerParams);

        // ������
        TextView emailTxt = new TextView(this);
        emailTxt.setText(R.string.contact_email);
        RelativeLayout.LayoutParams emailParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, 30); // ���ð�ť�Ŀ�Ⱥ͸߶�
        emailParams.leftMargin = 20;// �����궨λ
        emailParams.topMargin = 66;// �����궨λ
        relativeLayout.addView(emailTxt, emailParams);

        // ��ַ
        TextView addressTxt = new TextView(this);
        addressTxt.setText(R.string.contact_address);
        RelativeLayout.LayoutParams addressParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, 30); // ���ð�ť�Ŀ�Ⱥ͸߶�
        addressParams.leftMargin = 20;// �����궨λ
        addressParams.topMargin = 106;// �����궨λ
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
                RelativeLayout.LayoutParams.WRAP_CONTENT, 60); // ���ð�ť�Ŀ�Ⱥ͸߶�
        voteParams.leftMargin = 20;// �����궨λ
        voteParams.topMargin = 146;// �����궨λ
        relativeLayout.addView(voteBtn, voteParams);
        
        setContentView(relativeLayout);
    }
    
    private void addVote() {
        AlertDialog alert = new AlertDialog.Builder(this).setTitle("֪ͨ")
                .setMessage("��ɧ������Ůʿ���Ѿ���������վ��ԭ�ز�Ҫ�����߶������ҽ��ֻ��߸߾���")
                .setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
                {
                    // ����ȷ����ť����¼�
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        return;
                    }
                }).create();
        alert.show();
    }
}