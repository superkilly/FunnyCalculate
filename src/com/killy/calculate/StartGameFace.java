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
 * 2�������ӵ�ʱ�򣬼����λ�����Ѿ����˰�ť���������λ�õİ�ť����Դ��ť��
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
     * ��ʾ��Ϣ 
     */
    private static TextView tip = null;
    
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
        relativeLayout.setBackgroundResource(R.drawable.startgame);
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

        tip = new TextView(this);
        tip.setText("���ƶ���ť");
        RelativeLayout.LayoutParams tipParams = new RelativeLayout.LayoutParams(
                360, 30); // ���ð�ť�Ŀ�Ⱥ͸߶�
        tipParams.topMargin = btnHeight * (numHeiNum + calHeiNum + 1) + splitHeight * 4 + 30; // �����궨λ
        tipParams.leftMargin = 0; // �����궨λ
        relativeLayout.addView(tip, tipParams);
        
        setContentView(relativeLayout);
    }

    /**
     * ������������ť
     * @param i
     * �����Ͻ���������i��
     * @param j
     * �����Ͻ���������j��
     * @param random
     * �������������
     */
    private void addNumBtn(int i, int j, Random random) {
        numBtnArr[i][j] = new Button(this);
        // ��ť��ID��ͨ��10*�����+�����(���ǵ�һ����಻�ᳬ��10����ť,һ��Ҳ���ᳬ��10��)
        numBtnArr[i][j].setId(getNumId(i, j));
        numBtnArr[i][j].setText("" + Math.abs(random.nextInt(i * splitHeight + j + 1)));
        addMoveLister(numBtnArr[i][j]);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // ����
        drawable.setStroke(1, Color.argb(0xEE, 0x8E, 0xE5, 0XEE)); // �߿��ϸ����ɫ
        drawable.setColor(0x5000FF00); // �߿��ڲ���ɫ
        numBtnArr[i][j].setBackground(drawable);
        numBtnArr[i][j].setTextSize(10); // ���ð�ť�������С
        numBtnArr[i][j].setTextColor(0xFF1022FF);

        RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(btnWidth, btnHeight); // ���ð�ť�Ŀ�Ⱥ͸߶�
        // i��ʾ���ǵ�i�� k��ʾ���ǵ�k��
        btParams.leftMargin = btnHeight * j; // �����궨λ[������Ļ�����Ϸ�����]
        btParams.topMargin = btnWidth * i; // �����궨λ[������Ļ�����Ϸ�����]
        relativeLayout.addView(numBtnArr[i][j], btParams);
    }
    
    /**
     * �����������ť
     * @param i
     * �����Ͻ���������i��
     * @param j
     * �����Ͻ���������j��
     * @param random
     * �������������
     */
    private void addCalBtn(int i, int j, Random random) {
        calBtnArr[i][j] = new Button(this);
        // ��ť��ID��ͨ��2000+10*�����+�����(���ǵ�һ����಻�ᳬ��10����ť,һ��Ҳ���ᳬ��10��)
        calBtnArr[i][j].setId(getCalId(i, j));
        calBtnArr[i][j].setText(calArr[random.nextInt(calArr.length)]);
        addMoveLister(calBtnArr[i][j]);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // ����
        drawable.setStroke(1, Color.YELLOW); // �߿��ϸ����ɫ
        drawable.setColor(0x44FF2200); // �߿��ڲ���ɫ
        calBtnArr[i][j].setBackground(drawable);
        calBtnArr[i][j].setTextSize(12); // ���ð�ť�������С

        RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(
                btnWidth, btnHeight); // ���ð�ť�Ŀ�Ⱥ͸߶�
        // i��ʾ���ǵ�i�� k��ʾ���ǵ�k��
        btParams.leftMargin = btnHeight * j; // �����궨λ
        btParams.topMargin = btnWidth * (i + numHeiNum) + splitHeight; // �����궨λ
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
                    btnLeft = v.getLeft();
                    btnTop = v.getTop();
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    relativeLayout.bringChildToFront(v);
                    v.postInvalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                	Location location = getLocation(v, event, lastX, lastY);
                    int l = location.getLeft();
                    int b = location.getBotton();
                    int r = location.getRight();
                    int t = location.getTop();
                    v.layout(l, t, r, b);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    v.postInvalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    // ��ťԭ����λ���������������������
                    if (isInNumArea(btnLeft, btnTop) || 
                            isInCalArea(btnLeft, btnTop)) {
                        numCalActionUp(v, event, lastX, lastY);
                    } else if (isInExpressArea(btnLeft, btnTop)){
                        // ��ťԭ����λ����Ŀ��������ʽ��
                        expressActionUp(v, event, lastX, lastY);
                    }
                    validate();
                    relativeLayout.postInvalidate();
                    break;
                }
                return false;
            }
        });
    }
    
    /**
     * ��֤������ʽ��ȷ��
     * @Exception �쳣����
     * @return void
     */
    private void validate() {
        if (leftBtn == 0)
        {
            // �����ڶ��������������=��
            if (!"=".equals(expressBtnArr[widNum - 2].getText()))
            {
                tip.setText("������ʽ��ʽ����������ƶ���ť");
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
                        tip.setText("������ʽ�������ȣ�������ƶ���ť");
                    } else {
                        // ���������
                        score += Float.valueOf(result.toString()).intValue();
                        succCnt += 1;
                        totalScore.setText("�ܵ÷֣�" + score);
                        totalCnt.setText("�ܳɹ�����:" + succCnt);
                        tip.setText("�ɹ���!");
                        
                        /**
                         * 1�������������ʽ�Լ����������ʽ��Ӧ��Button������
                         * 2����������ȡ�ߵĲ����������
                         * 3�����������ȡ�ߵĲ����������
                         */
                        for(int i=0;i<widNum;i++) {
                        	relativeLayout.removeView(expressBtnArr[i]);
                        	expressBtnArr[i] = null;
                        }
                        
                        for (int i = 0; i < numHeiNum; i++)
                        {
                            for (int j = 0; j < widNum; j++)
                            {
                                if (null == numBtnArr[i][j]) {
                                    addNumBtn(i, j, random);
                                }
                            }
                        }
                        
                        for (int i = 0; i < calHeiNum; i++)
                        {
                            for (int j = 0; j < widNum; j++)
                            {
                                if (null == calBtnArr[i][j]) {
                                    addCalBtn(i, j, random);
                                }
                            }
                        }

                        leftBtn = widNum;
                    }
                }
                catch (ExpressionException e)
                {
                	tip.setText("������ʽ��ʽ�Ƿ���������ƶ���ť");
                }
            }
        }
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
     * @Exception �쳣����
     * @return void
     */
    private void numCalActionUp(View v, MotionEvent event, int lastX, int lastY)
    {
    	Location location = getLocation(v, event, lastX, lastY);
        int l = location.getLeft();
        int t = location.getTop();

        // �ŵ�Ŀ��������ʽ��Χ��
        if (isInExpressArea(l, t))
        {
            l = btnHeight * (l / btnHeight);
            t = btnHeight * (numHeiNum + calHeiNum) + 2 * splitHeight;
            // ���Ŀ��������ʽλ����û�а�ť����ŵ�Ŀ��������ʽ��Χ�ڵ���ȷ�����С�
            if (null == expressBtnArr[l / btnHeight])
            {
                moveBtn((Button)v, l, t);
                
                expressBtnArr[l / btnHeight] = (Button)v;
                if (isNumBtn(v.getId())) {
                    numBtnArr[v.getId() / Const.NUM_TEN][v.getId() % Const.NUM_TEN] = null;
                } else if (isCalBtn(v.getId())) {
                    calBtnArr[v.getId() / Const.NUM_TEN - numHeiNum][v.getId() % Const.NUM_TEN] = null;
                }
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
                    int tmpId = v.getId();
                    v.setId(oldBtn.getId());
                    oldBtn.setId(tmpId);
                    moveBtn((Button)v, l, t);
                    expressBtnArr[l / btnHeight] = (Button)v;
                    
                    int top = btnWidth * (oldBtn.getId() / Const.NUM_TEN);
                    int left = btnWidth * (oldBtn.getId() % Const.NUM_TEN);
                    if (isNumBtn(oldBtn.getId()))
                    {
                        moveBtn(oldBtn, left, top);
                        numBtnArr[oldBtn.getId() / Const.NUM_TEN][oldBtn.getId() % Const.NUM_TEN] = oldBtn;
                    }
                    else if (isCalBtn(oldBtn.getId()))
                    {
                        moveBtn(oldBtn, left, top + Const.NUM_TEN);
                        calBtnArr[oldBtn.getId() / Const.NUM_TEN - numHeiNum][oldBtn.getId() % Const.NUM_TEN] = oldBtn;
                    }
                } else {
                    int top = btnWidth * (v.getId() / Const.NUM_TEN);
                    int left = btnWidth * (v.getId() % Const.NUM_TEN);
                    if (isNumBtn(v.getId()))
                    {
                        moveBtn((Button)v, left, top);
                    }
                    else if (isCalBtn(v.getId()))
                    {
                        moveBtn((Button)v, left, top + splitHeight);
                    }
                }
            }
        }
        /**
         * ���û�зŵ�Ŀ��������ʽ��Χ�ڣ��򷵻���ԭ����λ�á�
         */
        else
        {
            int top = btnWidth * (v.getId() / Const.NUM_TEN);
            int left = btnWidth * (v.getId() % Const.NUM_TEN);
            if (isNumBtn(v.getId()))
            {
                moveBtn((Button)v, left, top);
            }
            else if (isCalBtn(v.getId()))
            {
                moveBtn((Button)v, left, top + Const.NUM_TEN);
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
     * @Exception �쳣����
     * @return void
     */
    private void expressActionUp(View v, MotionEvent event, int lastX, int lastY)
    {
    	RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)((Button)v).getLayoutParams();
    	int btnOldLeft = params.leftMargin;
    	int btnOldTop = params.topMargin;
        Location location = getLocation(v, event, lastX, lastY);
        int l = location.getLeft();
        int t = location.getTop();

        // ����ŵ�Ŀ��������ʽ��Χ��
        if (isInExpressArea(l, t))
        {
            l = btnHeight * (l / btnHeight);
            t = btnHeight * (numHeiNum + calHeiNum) + 2 * splitHeight;
            
            // ������ŵĵط��Ѿ�����һ���������������������������ߵ�λ�á�
            if (null != expressBtnArr[l / btnHeight]) {
                Button oldBtn = expressBtnArr[l / btnHeight];
                expressBtnArr[l / btnHeight] = (Button)v;
                expressBtnArr[btnOldLeft / btnHeight] = oldBtn;
                moveBtn(oldBtn, btnOldLeft, btnOldTop);
            } else {
                // ������ŵĵط�û���������������������ֱ���ƶ�һ�¸ð�ť��λ�á�
                expressBtnArr[l / btnHeight] = (Button)v;
                expressBtnArr[btnOldLeft / btnHeight] = null;
            }
            moveBtn((Button)v, l, t);
        }
        // ����ŵ�������
        else if (isInNumArea(l, t)) {
            l = btnHeight * (l / btnHeight);
            t = btnHeight * (t / btnHeight);

            /**
             *  a:����ð�ť�������֣��򷵻�ԭ����λ�á�
             *  a:������ŵĵط��Ѿ���������������������ߵ�λ�á�
             *  b:������ŵĵط�û�������������Ŀ��������ʽ���Ƴ������ҷ��õ���ȷ����������
             */
            BtnType oldBtnType = getBtnType(v.getId());
            // ����ð�ť�������֣��򷵻�ԭ����λ�á�
            if (!BtnType.NUMBER.equals(oldBtnType)) {
                moveBtn((Button)v, btnOldLeft, btnOldTop);
            } else if (null != numBtnArr[t / btnHeight][l / btnHeight]) {
                Button oldBtn = numBtnArr[t / btnHeight][l / btnHeight];
                int oldId = oldBtn.getId();
                numBtnArr[t / btnHeight][l / btnHeight] = (Button)v;
                expressBtnArr[btnOldLeft / btnHeight] = oldBtn;
                moveBtn((Button)v, l, t);
                moveBtn(oldBtn, btnOldLeft, btnOldTop);
                oldBtn.setId(v.getId());
                v.setId(oldId);
            } else if (null == numBtnArr[t / btnHeight][l / btnHeight]) {
                moveBtn((Button)v, l, t);
                expressBtnArr[btnOldLeft / btnHeight] = null;
                numBtnArr[t / btnHeight][l / btnHeight] = (Button)v;
                v.setId(getNumId(t / btnHeight, l / btnHeight));
                leftBtn++;
            }
        }
        // ����ŵ��������
        else if (isInCalArea(l, t)) {
            l = btnHeight * (l / btnHeight);
            t = btnHeight * (t / btnHeight) + splitHeight;
            
            /**
             *  a:����ð�ť������������򷵻�ԭ����λ�á�
             *  a:������ŵĵط��Ѿ��������������������ߵ�λ�á�
             *  b:������ŵĵط�û������������Ŀ��������ʽ���Ƴ������ҷ��õ���ȷ����������
             */
            BtnType oldBtnType = getBtnType(v.getId());
            // ����ð�ť�������֣��򷵻�ԭ����λ�á�
            if (!BtnType.CALCULATE.equals(oldBtnType)) {
                moveBtn((Button)v, btnOldLeft, btnOldTop);
            } else if (null != calBtnArr[t / btnHeight - numHeiNum][l / btnHeight]) {
                Button oldBtn = calBtnArr[t / btnHeight - numHeiNum][l / btnHeight];
                int oldId = oldBtn.getId();
                calBtnArr[t / btnHeight - numHeiNum][l / btnHeight] = (Button)v;
                expressBtnArr[btnOldLeft / btnHeight] = oldBtn;
                
                moveBtn((Button)v, l, t);
                moveBtn(oldBtn, btnOldLeft, btnOldTop);
                
                oldBtn.setId(v.getId());
                v.setId(oldId);
            } else if (null == calBtnArr[t / btnHeight - numHeiNum][l / btnHeight]) {
                moveBtn((Button)v, l, t);
                
                expressBtnArr[btnOldLeft / btnHeight] = null;
                calBtnArr[t / btnHeight - numHeiNum][l / btnHeight] = (Button)v;
                v.setId(Const.NUM_TEN * (t / btnHeight) + l / btnHeight);
                
                leftBtn++;
            }
        }
        // ����ŵ���������������������Լ���������ʽ��Χ�ڣ��򷵻�ԭ����λ��
        else {
            moveBtn((Button)v, btnOldLeft, btnOldTop);
        }
    }
    
    /**
     * ��ȡһ�����ְ�ťID
     * @param leftIndex
     * ������˳��
     * @param topIndex
     * ������˳��
     * @return int
     * ID
     * @Exception �쳣����
     */
    private int getNumId(int leftIndex, int topIndex)
    {
        return Const.NUM_TEN * leftIndex + topIndex;
    }

    /**
     * ��ȡһ���������ťID
     * @param leftIndex
     * ������˳��
     * @param topIndex
     * ������˳��
     * @return int
     * ID
     * @Exception �쳣����
     */
    private int getCalId(int leftIndex, int topIndex)
    {
        return Const.NUM_TEN * (leftIndex + numHeiNum) + topIndex;
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
        if (rawY < numHeiNum * btnHeight) {
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
                (rawY < numHeiNum * btnHeight + splitHeight + calHeiNum * btnHeight)) {
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
        if (rawY >= (numHeiNum + calHeiNum) * btnHeight + 2 * splitHeight &&
                rawY < (numHeiNum + calHeiNum + 1) * btnHeight + 2 * splitHeight) {
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
        if (id / Const.NUM_TEN < numHeiNum)
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
        if (id / Const.NUM_TEN >= numHeiNum && id / Const.NUM_TEN < calHeiNum + numHeiNum)
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
    
    /**
     * �ƶ�һ����ť
     * @param v
     * ���ƶ��İ�ť����
     * @param left
     * ��ť�ƶ�֮��ľ�����Ļ����λ��
     * @param top
     * ��ť�ƶ�֮��ľ�����Ļ�Ϸ���λ��
     */
    private void moveBtn(Button btn, int left, int top) {
    	btn.layout(left, top, left + btnHeight, top + btnHeight);
        RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(btnWidth, btnHeight); // ���ð�ť�Ŀ�Ⱥ͸߶�
        // i��ʾ���ǵ�i�� k��ʾ���ǵ�k��
        btParams.leftMargin = left; // �����궨λ[������Ļ�����Ϸ�����]
        btParams.topMargin = top; // �����궨λ[������Ļ�����Ϸ�����]
        btn.setLayoutParams(btParams);
    }
    
    private Location getLocation(View v, MotionEvent event, int lastX, int lastY) {
    	DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels - 50;
    	Location location = new Location();
    	int dx = (int) event.getRawX() - lastX;
        int dy = (int) event.getRawY() - lastY;
        int left = v.getLeft() + dx;
        int botton = v.getBottom() + dy;
        int right = v.getRight() + dx;
        int top = v.getTop() + dy;
        // �����ж��ƶ��Ƿ񳬳���Ļ
        if (left < 0)
        {
            left = 0;
            right = left + v.getWidth();
        }
        if (top < 0)
        {
            top = 0;
            botton = top + v.getHeight();
        }
        if (right > screenWidth)
        {
            right = screenWidth;
            left = right - v.getWidth();
        }
        if (botton > screenHeight)
        {
            botton = screenHeight;
            top = botton - v.getHeight();
        }
        location.setLeft(left);
        location.setTop(top);
        location.setRight(right);
        location.setBotton(botton);
        return location;
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
    
    class Location {
    	private int left;
    	private int top;
    	private int right;
    	private int botton;
		public int getLeft() {
			return left;
		}
		public void setLeft(int left) {
			this.left = left;
		}
		public int getTop() {
			return top;
		}
		public void setTop(int top) {
			this.top = top;
		}
		public int getRight() {
			return right;
		}
		public void setRight(int right) {
			this.right = right;
		}
		public int getBotton() {
			return botton;
		}
		public void setBotton(int botton) {
			this.botton = botton;
		}
    }
}
