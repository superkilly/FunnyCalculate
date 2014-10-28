package com.killy.calculate;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.killy.calculate.Eval.ExpressionException;

/**
 * �߼�˵��
 * 1����������������������������ʽ����ק��ť��ʱ�򣬵���ť��һ���ڱ��ʽ����ʱ�򣬾����ӣ������������ť���ǻص���ԭ���ĵط���
 * 2�������ӵ�ʱ�򣬼����λ�����Ѿ����˰�ť���������λ�õİ�ť����Դ��ť����Not Finish��
 * 3���ӱ��ʽ������ť���������������������ק��ʱ�򣬵���ť��һ�����������������������ʱ�򣬾����ӣ�����ť��һ���ڱ��ʽ����ʱ�������ӡ�
 * 4�������ӵ�ʱ�򣬼����λ�����Ѿ����˰�ť���������λ�õİ�ť����Դ��ť��
 * 5�����������ʽ�Ѿ�ռ����ʱ���Զ�������ʽ�Ƿ���ȷ�������ȷ����Ʒ֣���ձ��ʽ����������Ѿ��յ��������������������
 *   �������ȷ������ʾ���ʽ������Ϣ�����Խ��м�����ק������Ļ�ϵİ�ť��
 * �����ƣ�StartGameFace
 * ��������
 * @author��superkilly
 * @create��2014-10-23 ����10:05:19
 * @version SMSManager V0.1
 *
 * �޸��ˣ�superkilly
 * �޸�ʱ�䣺2014-10-23 ����10:05:19
 * �޸ı�ע��
 */
public class StartGameFace extends Activity
{
    /**
     * ��Ϸ��ҳ�沼��
     */
    RelativeLayout relativeLayout = null;

    /**
     * ��ť����������)
     */
    private static final int widNum = 9;

    /**
     * ��ť����������[�������]
     */
    private static final int numHeiNum = 6;

    /**
     * ��ť����������[��������]
     */
    private static final int calHeiNum = 2;

    /**
     * ���ְ�ť�������������ť���ֵļ������
     */
    private static final int splitHeight = 10;
    
    /**
     * ��ť�Ŀ��
     */
    private static int btnWidth = 0;
    
    /**
     * ��ť�ĸ߶�
     */
    private static int btnHeight = 0;

    /**
     * �������ҳ����������ְ�ť�����ö�ά���� ��һά�ǰ�ť�ĺ����� �ڶ�ά�ǰ�ť��������
     */
    private static final Button numBtnArr[][] = new Button[numHeiNum][widNum];

    /**
     * �������ҳ��������������ť�����ö�ά���� ��һά�ǰ�ť�ĺ����� �ڶ�ά�ǰ�ť��������
     */
    private static final Button calBtnArr[][] = new Button[calHeiNum][widNum];

    /**
     * �������������ʽ�İ�ť
     */
    private static final Button expressBtnArr[] = new Button[widNum];

    /**
     * ʣ�����������
     */
    private static int leftBtn = widNum;

    /**
     * ������б�
     */
    private static final String[] calArr = { "+", "-", "*", "/", "=" };
    
    /**
     * �ܳɹ�����
     */
    private static int succCnt = 0;
    
    /**
     * �ܵ÷�
     */
    private static int score = 0;
    
    /**
     * �ܵ÷�
     */
    private static TextView totalScore = null;
    
    /**
     * �ܳɹ�����
     */
    private static TextView totalCnt = null;
    
    /**
     * �������������
     */
    private static final Random random = new Random();

    @SuppressLint("UseSparseArrays")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game_face);
        relativeLayout = new RelativeLayout(this);
        relativeLayout.setId(121212);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        btnWidth = screenWidth / widNum;
        btnHeight = btnWidth;
        
        // ��������б�
        for (int i = 0; i < numHeiNum; i++)
        {
            for (int j = 0; j < widNum; j++)
            {
                addNumBtn(i, j, random);
            }
        }

        // ���������б�
        for (int i = 0; i < calHeiNum; i++)
        {
            for (int j = 0; j < widNum; j++)
            {
                addCalBtn(i, j, random);
            }
        }

        // ��Ӵ������������
        RelativeLayout waitCalLayout = new RelativeLayout(this);
        waitCalLayout.layout(0, btnHeight
                * (numHeiNum + calHeiNum + splitHeight * 2), screenWidth,
                screenHeight);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // ����
        drawable.setStroke(2, Color.RED); // �߿��ϸ����ɫ
        drawable.setColor(0xFF662200); // �߿��ڲ���ɫ
        waitCalLayout.setBackground(drawable);
        RelativeLayout.LayoutParams layParams = new RelativeLayout.LayoutParams(
                screenWidth - 10, screenHeight - 10 - btnHeight
                        * (numHeiNum + calHeiNum + splitHeight * 2)); // ���ð�ť�Ŀ�Ⱥ͸߶�
        // i��ʾ���ǵ�i�� k��ʾ���ǵ�k��
        layParams.topMargin = btnHeight
                * (numHeiNum + calHeiNum + splitHeight * 2); // �����궨λ
        layParams.leftMargin = 0; // �����궨λ
        relativeLayout.addView(waitCalLayout, layParams);

        // ��ӽ����ʾ
        totalScore = new TextView(this);
        totalScore.setText("�ܵ÷֣�" + score);
        totalScore.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams totalScoreParams = new RelativeLayout.LayoutParams(
                160, 30); // ���ð�ť�Ŀ�Ⱥ͸߶�
        totalScoreParams.leftMargin = 0;// �����궨λ
        totalScoreParams.topMargin = btnHeight * (numHeiNum + calHeiNum + 1) + splitHeight * 3;// �����궨λ
        totalScore.setLayoutParams(totalScoreParams);
        relativeLayout.addView(totalScore);

        totalCnt = new TextView(this);
        totalCnt.setText("�ܳɹ�������" + succCnt);
        RelativeLayout.LayoutParams totalCntParams = new RelativeLayout.LayoutParams(
                160, 30); // ���ð�ť�Ŀ�Ⱥ͸߶�
        totalCntParams.topMargin = btnHeight * (numHeiNum + calHeiNum + 1) + splitHeight * 3; // �����궨λ
        totalCntParams.leftMargin = 160 + 10; // �����궨λ
        relativeLayout.addView(totalCnt, totalCntParams);

        setContentView(relativeLayout);
    }

    private void addNumBtn(int i, int j, Random random) {
        numBtnArr[i][j] = new Button(this);
        // ��ť��ID��ͨ��10*�����+�����(���ǵ�һ����಻�ᳬ��10����ť,һ��Ҳ���ᳬ��10��)
        numBtnArr[i][j].setId(getNumId(j, i));
        numBtnArr[i][j].setText("" + Math.abs(random.nextInt(i * splitHeight + j + 1)));
        addMoveLister(numBtnArr[i][j]);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // ����
        drawable.setStroke(1, Color.BLUE); // �߿��ϸ����ɫ
        drawable.setColor(0x22FFFF00); // �߿��ڲ���ɫ
        numBtnArr[i][j].setBackground(drawable);
        
        numBtnArr[i][j].setTextSize(10); // ���ð�ť�������С

        RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(btnWidth, btnHeight); // ���ð�ť�Ŀ�Ⱥ͸߶�
        // i��ʾ���ǵ�i�� k��ʾ���ǵ�k��
        btParams.topMargin = btnHeight * i; // �����궨λ
        btParams.leftMargin = btnWidth * j; // �����궨λ
        relativeLayout.addView(numBtnArr[i][j], btParams);
    }
    
    private void addCalBtn(int i, int j, Random random) {
        calBtnArr[i][j] = new Button(this);
        // ��ť��ID��ͨ��2000+10*�����+�����(���ǵ�һ����಻�ᳬ��10����ť,һ��Ҳ���ᳬ��10��)
        calBtnArr[i][j].setId(getCalId(j, i));
        calBtnArr[i][j].setText(calArr[random.nextInt(calArr.length)]);
        addMoveLister(calBtnArr[i][j]);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // ����
        drawable.setStroke(1, Color.YELLOW); // �߿��ϸ����ɫ
        drawable.setColor(0x44FF2200); // �߿��ڲ���ɫ
        calBtnArr[i][j].setBackground(drawable);

        RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(
                btnWidth, btnHeight); // ���ð�ť�Ŀ�Ⱥ͸߶�
        // i��ʾ���ǵ�i�� k��ʾ���ǵ�k��
        btParams.topMargin = btnHeight * (i + numHeiNum) + splitHeight; // �����궨λ
        btParams.leftMargin = btnWidth * j; // �����궨λ
        relativeLayout.addView(calBtnArr[i][j], btParams);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main_face, menu);
        return true;
    }

    private void addMoveLister(final Button btn)
    {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        final int screenWidth = dm.widthPixels;
        final int screenHeight = dm.heightPixels - 50;
        btn.setOnTouchListener(new OnTouchListener()
        {
            int lastX, lastY;
            int btnLeft = 0;
            int btnTop = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int ea = event.getAction();
                switch (ea)
                {
                case MotionEvent.ACTION_DOWN:
                    // ��ȡ�����¼�����λ�õ�ԭʼX����
                    System.out.println(v.getLeft() + "[:]" + v.getTop());
                    btnLeft = v.getLeft();
                    btnTop = v.getTop();
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    relativeLayout.bringChildToFront(v);
                    v.postInvalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    int l = v.getLeft() + dx;
                    int b = v.getBottom() + dy;
                    int r = v.getRight() + dx;
                    int t = v.getTop() + dy;
                    // �����ж��ƶ��Ƿ񳬳���Ļ
                    if (l < 0)
                    {
                        l = 0;
                        r = l + v.getWidth();
                    }
                    if (t < 0)
                    {
                        t = 0;
                        b = t + v.getHeight();
                    }
                    if (r > screenWidth)
                    {
                        r = screenWidth;
                        l = r - v.getWidth();
                    }
                    if (b > screenHeight)
                    {
                        b = screenHeight;
                        t = b - v.getHeight();
                    }
                    v.layout(l, t, r, b);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    v.postInvalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    // ��ťԭ����λ���������������������
                    if (isInNumArea(btnLeft, btnTop) || 
                            isInCalArea(btnLeft, btnTop)) {
                        numCalActionUp(v, event, lastX, lastY, screenWidth, screenHeight);
                    } else if (isInExpressArea(btnLeft, btnTop)){
                        // ��ťԭ����λ����Ŀ��������ʽ��
                        System.out.println("cal");
                        expressActionUp(v, event, lastX, lastY, screenWidth, screenHeight, btnLeft, btnTop);
                    }
                    break;
                }
                return false;
            }
        });
    }
    
    /**
     * �������������������ť�ƶ�������
     * 1������ŵ�Ŀ��������ʽ��Χ��
     *   a:���Ŀ��������ʽλ����û�а�ť����ŵ�Ŀ��������ʽ��Χ�ڵ���ȷ�����С�
     *   b:���Ŀ��������ʽλ�������а�ť���������ߵİ�ť����һ�£���������ߵ�λ�ã�������ߵİ�ť���Ͳ�һ�£��򷵻�ԭ����λ�á�
     * 2�����û�зŵ�Ŀ��������ʽ��Χ�ڣ��򷵻���ԭ����λ�á�
     * @param v
     * @param event
     * @param lastX
     * @param lastY
     * @param screenWidth
     * @param screenHeight
     * @Exception �쳣����
     * @return void
     */
    private void numCalActionUp(View v, MotionEvent event, int lastX, int lastY,
            int screenWidth, int screenHeight)
    {
        int dx = (int) event.getRawX() - lastX;
        int dy = (int) event.getRawY() - lastY;
        int l = v.getLeft() + dx;
        int b = v.getBottom() + dy;
        int r = v.getRight() + dx;
        int t = v.getTop() + dy;
        // �����ж��ƶ��Ƿ񳬳���Ļ
        if (l < 0)
        {
            l = 0;
            r = l + v.getWidth();
        }
        if (t < 0)
        {
            t = 0;
            b = t + v.getHeight();
        }
        if (r > screenWidth)
        {
            r = screenWidth;
            l = r - v.getWidth();
        }
        if (b > screenHeight)
        {
            b = screenHeight;
            t = b - v.getHeight();
        }

        // �ŵ�Ŀ��������ʽ��Χ��
        if ((t > btnHeight * (numHeiNum + calHeiNum) - btnHeight / 2 + 2
                * splitHeight) && (t < btnHeight * (numHeiNum + calHeiNum + 1) + btnHeight / 2 + 2 * splitHeight))
        {
            if (l % btnHeight * 2 > btnHeight)
            {
                l = btnHeight * (l / btnHeight) + btnHeight;
            }
            else
            {
                l = btnHeight * (l / btnHeight);
            }
            t = btnHeight * (numHeiNum + calHeiNum) + 2 * splitHeight;
            // ���Ŀ��������ʽλ����û�а�ť����ŵ�Ŀ��������ʽ��Χ�ڵ���ȷ�����С�
            if (null == expressBtnArr[l / btnHeight])
            {
                v.layout(l, t, l + btnHeight, t + btnHeight);
                expressBtnArr[l / btnHeight] = (Button)v;
                System.out.println(v.getId());
                leftBtn--;
            } else {
                /**
                 * ���Ŀ��������ʽλ�������а�ť��
                 * 1�����Ŀ����������ʽλ���ϵİ�ť���ͺ��ƶ������İ�ť����һ�£���������ߵ�λ�ã����ҵ������ߵ�ID
                 * 2�����Ŀ����������ʽλ���ϵİ�ť���ͺ��ƶ������İ�ť���Ͳ�һ�£����ƶ������İ�ť��ԭ��
                 */
                BtnType oldBtnType = getBtnType(v.getId());
                BtnType newBtnType = getBtnType(expressBtnArr[l / btnHeight].getId());
                if (oldBtnType.equals(newBtnType)) {
                    Button oldBtn = expressBtnArr[l / btnHeight];
                    v.layout(l, t, l + btnHeight, t + btnHeight);
                    expressBtnArr[l / btnHeight] = (Button)v;
                    
                    int top = btnWidth * (v.getId() / 10);
                    int left = btnWidth * (v.getId() % 10);
                    
                    
                    if (isNumBtn(v.getId()))
                    {
                        oldBtn.layout(left, top, left + btnWidth, top + btnWidth);
                    }
                    else if (isCalBtn(v.getId()))
                    {
                        oldBtn.layout(left, top + 10, left + btnWidth, top + btnWidth
                                + splitHeight);
                    }
                    
                    int tmpId = v.getId();
                    v.setId(oldBtn.getId());
                    oldBtn.setId(tmpId);
                } else {
                    int top = btnWidth * (v.getId() / 10);
                    int left = btnWidth * (v.getId() % 10);
                    if (isNumBtn(v.getId()))
                    {
                        v.layout(left, top, left + btnWidth, top + btnWidth);
                    }
                    else if (isCalBtn(v.getId()))
                    {
                        v.layout(left, top + splitHeight, left + btnWidth, top + btnWidth
                                + splitHeight);
                    }
                }
            }
            if (leftBtn == 0)
            {
                // �����ڶ��������������=��
                if (!"=".equals(expressBtnArr[widNum - 2].getText()))
                {
                    // TODO �����ڶ������������=�ţ���˵
                }
                else
                {
                    StringBuilder express = new StringBuilder();
                    for (int i = 0; i < widNum - 2; i++)
                    {
                        express.append(expressBtnArr[i].getText());
                    }
                    Eval eval = new Eval();
                    Object result;
                    try
                    {
                        result = eval.calculate(express.toString());
                        if (Integer.parseInt(expressBtnArr[widNum - 1]
                                .getText().toString()) != Float.valueOf(result.toString()).intValue()) {
                            // ����Ľ�������  
                            
                        } else {
                            // ���������
                            score += Float.valueOf(result.toString()).intValue();
                            succCnt += 1;
                            totalScore.setText("�ܵ÷֣�" + score);
                            totalCnt.setText("�ܳɹ�����:" + succCnt);
                            /**
                             * 1����������ȡ�ߵĲ����������
                             * 2�����������ȡ�ߵĲ����������
                             * 3�������������ʽ�Լ����������ʽ��Ӧ��Button������
                             */
                            int btnId = 0;
                            for(int i=0;i<widNum;i++) {
                                btnId = expressBtnArr[i].getId();
                                relativeLayout.removeView(findViewById(expressBtnArr[i].getId()));
                                expressBtnArr[i] = null;
                                if (isNumBtn(btnId)) {
                                    addNumBtn(btnId / 10, btnId % 10, random);
                                } else {
                                    addCalBtn(btnId / 10 - numHeiNum, btnId % 10, random);
                                }
                                leftBtn ++;
                            }
                            relativeLayout.postInvalidate();
                        }
                    }
                    catch (ExpressionException e)
                    {
                    }
                }
            }
        }
        /**
         * ���û�зŵ�Ŀ��������ʽ��Χ�ڣ��򷵻���ԭ����λ�á�
         */
        else
        {
            int top = btnWidth * (v.getId() / 10);
            int left = btnWidth * (v.getId() % 10);
            if (isNumBtn(v.getId()))
            {
                v.layout(left, top, left + btnWidth, top + btnWidth);
            }
            else if (isCalBtn(v.getId()))
            {
                v.layout(left, top + 10, left + btnWidth, top + btnWidth
                        + splitHeight);
            }
        }
    }

    /**
     * ���������ť�ƶ�������
     * 1������ŵ�Ŀ��������ʽ��Χ��
     *  a:������ŵĵط��Ѿ�����һ���������������������������ߵ�λ�á�
     *  b:������ŵĵط�û���������������������ֱ���ƶ�һ�¸ð�ť��λ�á�
     * 2������ŵ�������
     *  a:����ð�ť�������֣��򷵻�ԭ����λ�á�
     *  a:������ŵĵط��Ѿ���������������������ߵ�λ�á�
     *  b:������ŵĵط�û�������������Ŀ��������ʽ���Ƴ������ҷ��õ���ȷ����������
     * 3������ŵ��������
     *  a:����ð�ť������������򷵻�ԭ����λ�á�
     *  a:������ŵĵط��Ѿ��������������������ߵ�λ�á�
     *  b:������ŵĵط�û������������Ŀ��������ʽ���Ƴ������ҷ��õ���ȷ�����������
     * 4: ����ŵ���������������������Լ���������ʽ��Χ�ڣ��򷵻�ԭ����λ��
     * @param v
     * @param event
     * @param lastX
     * @param lastY
     * @param screenWidth
     * @param screenHeight
     * @Exception �쳣����
     * @return void
     */
    private void expressActionUp(View v, MotionEvent event, int lastX, int lastY,
            int screenWidth, int screenHeight, int btnLeft, int btnTop)
    {
        int dx = (int) event.getRawX() - lastX;
        int dy = (int) event.getRawY() - lastY;
        int l = v.getLeft() + dx;
        int b = v.getBottom() + dy;
        int r = v.getRight() + dx;
        int t = v.getTop() + dy;
        // �����ж��ƶ��Ƿ񳬳���Ļ
        if (l < 0)
        {
            l = 0;
            r = l + v.getWidth();
        }
        if (t < 0)
        {
            t = 0;
            b = t + v.getHeight();
        }
        if (r > screenWidth)
        {
            r = screenWidth;
            l = r - v.getWidth();
        }
        if (b > screenHeight)
        {
            b = screenHeight;
            t = b - v.getHeight();
        }

        // ����ŵ�Ŀ��������ʽ��Χ��
        if (t >= btnHeight * (numHeiNum + calHeiNum) - btnHeight / 2 + 2
                * splitHeight)
        {
            if (l % btnHeight * 2 > btnHeight)
            {
                l = btnHeight * (l / btnHeight) + btnHeight;
            }
            else
            {
                l = btnHeight * (l / btnHeight);
            }
            t = btnHeight * (numHeiNum + calHeiNum) + 2 * splitHeight;
            
            // ������ŵĵط��Ѿ�����һ���������������������������ߵ�λ�á�
            if (null != expressBtnArr[l / btnHeight]) {
                Button oldBtn = expressBtnArr[l / btnHeight];
                expressBtnArr[l / btnHeight] = (Button)v;
                expressBtnArr[(int)event.getRawX() / btnHeight] = oldBtn;
                oldBtn.layout(btnLeft, btnTop, btnLeft + btnHeight, btnTop + btnHeight);
            } else {
                // ������ŵĵط�û���������������������ֱ���ƶ�һ�¸ð�ť��λ�á�
                expressBtnArr[l / btnHeight] = (Button)v;
                expressBtnArr[(int)event.getRawX() / btnHeight] = null;
            }
            v.layout(l, t, l + btnHeight, t + btnHeight);
            relativeLayout.postInvalidate();
            return;
        }
        
        // ����ŵ�������
        if (t <= btnHeight * numHeiNum + btnHeight / 2) {
            /**
             * a:
     *  a:������ŵĵط��Ѿ���������������������ߵ�λ�á�
     *  b:������ŵĵط�û�������������Ŀ��������ʽ���Ƴ������ҷ��õ���ȷ����������
             */
            BtnType oldBtnType = getBtnType(v.getId());
            // ����ð�ť�������֣��򷵻�ԭ����λ�á�
            if (!BtnType.NUMBER.equals(oldBtnType)) {
                v.layout(btnLeft, btnTop, btnLeft + btnHeight, btnTop + btnHeight);
                return;
            }
            
        }
        
        // ����ŵ��������
        if ((t >= btnHeight * numHeiNum + splitHeight - btnHeight / 2) &&
                (t <= btnHeight * (numHeiNum + calHeiNum) + splitHeight + btnHeight / 2)) {
            
        }
        
        // ����ŵ���������������������Լ���������ʽ��Χ�ڣ��򷵻�ԭ����λ��
            
    }
    
    /**
     * ��ȡһ�����ְ�ťID
     * @param widthIndex
     * ������˳��
     * @param heightIndex
     * ������˳��
     * @return int
     * ID
     * @Exception �쳣����
     */
    private int getNumId(int widthIndex, int heightIndex)
    {
        return 10 * heightIndex + widthIndex;
    }

    /**
     * ��ȡһ���������ťID
     * @param widthIndex
     * ������˳��
     * @param heightIndex
     * ������˳��
     * @return int
     * ID
     * @Exception �쳣����
     */
    private int getCalId(int widthIndex, int heightIndex)
    {
        return 10 * (heightIndex + numHeiNum) + widthIndex;
    }
    
    /**
     * ��ťԭ����������
     * ����������
     * ��ť��λ��ԭ����������
     * @param rawX
     * ������
     * @param rawY
     * ������
     * @return boolean
     * ��������������true   ���򣬷���false
     * @Exception �쳣����
     */
    private static boolean isInNumArea(float rawX, float rawY) {
        if (rawY <= numHeiNum * btnHeight) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
    
    /**
     * ��ťԭ�����������
     * ����������
     * ��ť��λ��ԭ�����������
     * @param rawX
     * ������
     * @param rawY
     * ������
     * @return boolean
     * ���������������true   ���򣬷���false
     * @Exception �쳣����
     */
    private static boolean isInCalArea(float rawX, float rawY) {
        if ((rawY >= numHeiNum * btnHeight + splitHeight) &&
                (rawY <= numHeiNum * btnHeight + splitHeight + calHeiNum * btnHeight)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
    
    /**
     * ��ťԭ���ڱ��ʽ��
     * ����������
     * ��ť��λ��ԭ���ڱ��ʽ��
     * @param rawX
     * ������
     * @param rawY
     * ������
     * @return boolean
     * �ڱ��ʽ��������true   ���򣬷���false
     * @Exception �쳣����
     */
    private static boolean isInExpressArea(float rawX, float rawY) {
        if (rawY >= numHeiNum * btnHeight + splitHeight + calHeiNum * btnHeight + splitHeight &&
                rawY <= numHeiNum * btnHeight + splitHeight + calHeiNum * btnHeight + splitHeight + btnHeight) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * �����ְ�ť
     * @param id
     * ���жϵİ�ťID
     * @return boolean
     * ��������ְ�ť���򷵻�true   ���򣬷���false
     * @Exception �쳣����
     */
    private boolean isNumBtn(int id)
    {
        if (id / 10 < numHeiNum)
        {
            return Boolean.TRUE;
        }
        else
        {
            return Boolean.FALSE;
        }
    }

    /**
     * ���������ť
     * @param id
     * ���жϵİ�ťID
     * @return boolean
     * ������������ť���򷵻�true   ���򣬷���false
     * @Exception �쳣����
     */
    private boolean isCalBtn(int id)
    {
        if (id / 10 >= numHeiNum && id / 10 < calHeiNum + numHeiNum)
        {
            return Boolean.TRUE;
        }
        else
        {
            return Boolean.FALSE;
        }
    }
    
    /**
     * ��ȡ��ť������
     * @param id
     * @return
     * @Exception �쳣����
     * @return BtnType
     */
    private BtnType getBtnType(int id) {
        if (isCalBtn(id)) {
            return BtnType.CALCULATE;
        } else if (isNumBtn(id)) {
            return BtnType.NUMBER;
        } else {
            return BtnType.EXPRESS;
        }
    }
    
    enum BtnType {
        /**
         * ������
         */
        NUMBER,
        /**
         * �����
         */
        CALCULATE,
        /**
         * ���ʽ
         */
        EXPRESS;
    }
}
