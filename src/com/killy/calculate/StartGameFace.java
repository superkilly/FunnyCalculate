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
 * 逻辑说明
 * 1：当从数字区或者运算符区往表达式区拖拽按钮的时候，当按钮有一半在表达式区的时候，就落子；其他情况，按钮还是回到其原来的地方。
 * 2：当落子的时候，假设该位置上已经有了按钮，则调换该位置的按钮和来源按钮。
 * 3：从表达式区将按钮往数字区或者运算符区拖拽的时候，当按钮有一半在数字区或者运算符区的时候，就落子；当按钮有一半在表达式区的时候，则落子。
 * 4：当落子的时候，假设该位置上已经有了按钮，则调换该位置的按钮和来源按钮。
 * 5：当整个表达式已经占满的时候，自动计算表达式是否正确。如果正确，则计分，清空表达式，重新填充已经空掉的数字区和运算符区；
 *   如果不正确，则提示表达式错误信息，可以进行继续拖拽整个屏幕上的按钮。
 * 类名称：StartGameFace
 * 类描述：
 * @author：superkilly
 * @create：2014-10-23 下午10:05:19
 * @version SMSManager V0.1
 *
 * 修改人：superkilly
 * 修改时间：2014-10-23 下午10:05:19
 * 修改备注：
 */
public class StartGameFace extends Activity
{
    /**
     * 游戏主页面布局
     */
    RelativeLayout relativeLayout = null;

    /**
     * 按钮个数（横向)
     */
    private static final int widNum = 9;

    /**
     * 按钮个数（纵向）[存放数字]
     */
    private static final int numHeiNum = 6;

    /**
     * 按钮个数（纵向）[存放运算符]
     */
    private static final int calHeiNum = 2;

    /**
     * 数字按钮部分与运算符按钮部分的间隔长度
     */
    private static final int splitHeight = 10;
    
    /**
     * 按钮的宽度
     */
    private static int btnWidth = 0;
    
    /**
     * 按钮的高度
     */
    private static int btnHeight = 0;

    /**
     * 存放整个页面的所有数字按钮，采用二维数组 第一维是按钮的横坐标 第二维是按钮的纵坐标
     */
    private static final Button numBtnArr[][] = new Button[numHeiNum][widNum];

    /**
     * 存放整个页面的所有运算符按钮，采用二维数组 第一维是按钮的横坐标 第二维是按钮的纵坐标
     */
    private static final Button calBtnArr[][] = new Button[calHeiNum][widNum];

    /**
     * 存放整个运算表达式的按钮
     */
    private static final Button expressBtnArr[] = new Button[widNum];

    /**
     * 剩余的运算块个数
     */
    private static int leftBtn = widNum;

    /**
     * 运算符列表
     */
    private static final String[] calArr = { "+", "-", "*", "/", "=" };
    
    /**
     * 总成功次数
     */
    private static int succCnt = 0;
    
    /**
     * 总得分
     */
    private static int score = 0;
    
    /**
     * 总得分
     */
    private static TextView totalScore = null;
    
    /**
     * 总成功次数
     */
    private static TextView totalCnt = null;
    
    /**
     * 提示信息 
     */
    private static TextView tip = null;
    
    /**
     * 产生随机数对象
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
        
        // 添加数字列表
        for (int i = 0; i < numHeiNum; i++)
        {
            for (int j = 0; j < widNum; j++)
            {
                addNumBtn(i, j, random);
            }
        }

        // 添加运算符列表
        for (int i = 0; i < calHeiNum; i++)
        {
            for (int j = 0; j < widNum; j++)
            {
                addCalBtn(i, j, random);
            }
        }

        // 添加待填充的运算格子
        RelativeLayout waitCalLayout = new RelativeLayout(this);
        waitCalLayout.layout(0, btnHeight
                * (numHeiNum + calHeiNum + splitHeight * 2), screenWidth,
                screenHeight);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // 画框
        drawable.setStroke(2, Color.RED); // 边框粗细及颜色
        drawable.setColor(0xFF662200); // 边框内部颜色
        waitCalLayout.setBackground(drawable);
        RelativeLayout.LayoutParams layParams = new RelativeLayout.LayoutParams(
                screenWidth - 10, screenHeight - 10 - btnHeight
                        * (numHeiNum + calHeiNum + splitHeight * 2)); // 设置按钮的宽度和高度
        // i表示的是第i行 k表示的是第k列
        layParams.topMargin = btnHeight
                * (numHeiNum + calHeiNum + splitHeight * 2); // 纵坐标定位
        layParams.leftMargin = 0; // 横坐标定位
        relativeLayout.addView(waitCalLayout, layParams);

        // 添加结果显示
        totalScore = new TextView(this);
        totalScore.setText("总得分：" + score);
        totalScore.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams totalScoreParams = new RelativeLayout.LayoutParams(
                160, 30); // 设置按钮的宽度和高度
        totalScoreParams.leftMargin = 0;// 横坐标定位
        totalScoreParams.topMargin = btnHeight * (numHeiNum + calHeiNum + 1) + splitHeight * 3;// 纵坐标定位
        totalScore.setLayoutParams(totalScoreParams);
        relativeLayout.addView(totalScore);

        totalCnt = new TextView(this);
        totalCnt.setText("总成功次数：" + succCnt);
        RelativeLayout.LayoutParams totalCntParams = new RelativeLayout.LayoutParams(
                160, 30); // 设置按钮的宽度和高度
        totalCntParams.topMargin = btnHeight * (numHeiNum + calHeiNum + 1) + splitHeight * 3; // 纵坐标定位
        totalCntParams.leftMargin = 160 + 10; // 横坐标定位
        relativeLayout.addView(totalCnt, totalCntParams);

        tip = new TextView(this);
        tip.setText("请移动按钮");
        RelativeLayout.LayoutParams tipParams = new RelativeLayout.LayoutParams(
                360, 30); // 设置按钮的宽度和高度
        tipParams.topMargin = btnHeight * (numHeiNum + calHeiNum + 1) + splitHeight * 4 + 30; // 纵坐标定位
        tipParams.leftMargin = 0; // 横坐标定位
        relativeLayout.addView(tip, tipParams);
        
        setContentView(relativeLayout);
    }

    /**
     * 新增运算数按钮
     * @param i
     * 从左上角往下数第i行
     * @param j
     * 从左上角往右数第j列
     * @param random
     * 产生随机数对象
     */
    private void addNumBtn(int i, int j, Random random) {
        numBtnArr[i][j] = new Button(this);
        // 按钮的ID是通过10*横序号+纵序号(考虑到一横最多不会超过10个按钮,一纵也不会超过10个)
        numBtnArr[i][j].setId(getNumId(i, j));
        numBtnArr[i][j].setText("" + Math.abs(random.nextInt(i * splitHeight + j + 1)));
        addMoveLister(numBtnArr[i][j]);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // 画框
        drawable.setStroke(1, Color.argb(0xEE, 0x8E, 0xE5, 0XEE)); // 边框粗细及颜色
        drawable.setColor(0x5000FF00); // 边框内部颜色
        numBtnArr[i][j].setBackground(drawable);
        numBtnArr[i][j].setTextSize(10); // 设置按钮上字体大小
        numBtnArr[i][j].setTextColor(0xFF1022FF);

        RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(btnWidth, btnHeight); // 设置按钮的宽度和高度
        // i表示的是第i行 k表示的是第k列
        btParams.leftMargin = btnHeight * j; // 横坐标定位[距离屏幕最左上方往右]
        btParams.topMargin = btnWidth * i; // 纵坐标定位[距离屏幕最左上方往下]
        relativeLayout.addView(numBtnArr[i][j], btParams);
    }
    
    /**
     * 新增运算符按钮
     * @param i
     * 从左上角往下数第i行
     * @param j
     * 从左上角往右数第j列
     * @param random
     * 产生随机数对象
     */
    private void addCalBtn(int i, int j, Random random) {
        calBtnArr[i][j] = new Button(this);
        // 按钮的ID是通过2000+10*横序号+纵序号(考虑到一横最多不会超过10个按钮,一纵也不会超过10个)
        calBtnArr[i][j].setId(getCalId(i, j));
        calBtnArr[i][j].setText(calArr[random.nextInt(calArr.length)]);
        addMoveLister(calBtnArr[i][j]);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // 画框
        drawable.setStroke(1, Color.YELLOW); // 边框粗细及颜色
        drawable.setColor(0x44FF2200); // 边框内部颜色
        calBtnArr[i][j].setBackground(drawable);
        calBtnArr[i][j].setTextSize(12); // 设置按钮上字体大小

        RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(
                btnWidth, btnHeight); // 设置按钮的宽度和高度
        // i表示的是第i行 k表示的是第k列
        btParams.leftMargin = btnHeight * j; // 纵坐标定位
        btParams.topMargin = btnWidth * (i + numHeiNum) + splitHeight; // 横坐标定位
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
                    // 获取触摸事件触摸位置的原始X坐标
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
                    // 按钮原来的位置在数字区或者运算符区
                    if (isInNumArea(btnLeft, btnTop) || 
                            isInCalArea(btnLeft, btnTop)) {
                        numCalActionUp(v, event, lastX, lastY);
                    } else if (isInExpressArea(btnLeft, btnTop)){
                        // 按钮原来的位置在目的运算表达式区
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
     * 验证运算表达式正确性
     * @Exception 异常对象
     * @return void
     */
    private void validate() {
        if (leftBtn == 0)
        {
            // 倒数第二个运算符必须是=号
            if (!"=".equals(expressBtnArr[widNum - 2].getText()))
            {
                tip.setText("运算表达式格式错误，请继续移动按钮");
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
                        // 计算的结果不相等  
                        tip.setText("运算表达式结果不相等，请继续移动按钮");
                    } else {
                        // 计算结果相等
                        score += Float.valueOf(result.toString()).intValue();
                        succCnt += 1;
                        totalScore.setText("总得分：" + score);
                        totalCnt.setText("总成功次数:" + succCnt);
                        tip.setText("成功啦!");
                        
                        /**
                         * 1：清除掉运算表达式以及存放运算表达式对应的Button的数组
                         * 2：给数字区取走的部分重新填充
                         * 3：给运算符区取走的部分重新填充
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
                	tip.setText("运算表达式格式非法，请继续移动按钮");
                }
            }
        }
    }
    
    /**
     * 数字区或者运算符区按钮移动处理方法
     * 1：如果放到目的运算表达式范围内
     *   a:如果目的运算表达式位置上没有按钮，则放到目的运算表达式范围内的正确格子中。
     *   b:如果目的运算表达式位置上已有按钮，假设两者的按钮类型一致，则调换两者的位置；如果两者的按钮类型不一致，则返回原来的位置。
     * 2：如果没有放到目的运算表达式范围内，则返回其原来的位置。
     * @param v
     * @param event
     * @param lastX
     * @param lastY
     * @Exception 异常对象
     * @return void
     */
    private void numCalActionUp(View v, MotionEvent event, int lastX, int lastY)
    {
    	Location location = getLocation(v, event, lastX, lastY);
        int l = location.getLeft();
        int t = location.getTop();

        // 放到目的运算表达式范围内
        if (isInExpressArea(l, t))
        {
            l = btnHeight * (l / btnHeight);
            t = btnHeight * (numHeiNum + calHeiNum) + 2 * splitHeight;
            // 如果目的运算表达式位置上没有按钮，则放到目的运算表达式范围内的正确格子中。
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
                 * 如果目的运算表达式位置上已有按钮。
                 * 1：如果目的运算符表达式位置上的按钮类型和移动过来的按钮类型一致，则调换两者的位置，并且调换两者的ID
                 * 2：如果目的运算符表达式位置上的按钮类型和移动过来的按钮类型不一致，则移动过来的按钮归原处
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
         * 如果没有放到目的运算表达式范围内，则返回其原来的位置。
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
     * 运算符区域按钮移动处理方法
     * 1：如果放到目的运算表达式范围内
     *  a:如果待放的地方已经有了一个运算数或者运算符，则调换两者的位置。
     *  b:如果待放的地方没有运算数或者运算符，则直接移动一下该按钮的位置。
     * 2：如果放到数字区
     *  a:如果该按钮不是数字，则返回原来的位置。
     *  a:如果待放的地方已经有了运算数，则调换两者的位置。
     *  b:如果待放的地方没有运算数，则从目的运算表达式中移除，并且放置到正确的数字区。
     * 3：如果放到运算符区
     *  a:如果该按钮不是运算符，则返回原来的位置。
     *  a:如果待放的地方已经有了运算符，则调换两者的位置。
     *  b:如果待放的地方没有运算符，则从目的运算表达式中移除，并且放置到正确的运算符区。
     * 4: 如果放到非数字区、非运算符区以及非运算表达式范围内，则返回原来的位置
     * @param v
     * @param event
     * @param lastX
     * @param lastY
     * @Exception 异常对象
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

        // 如果放到目的运算表达式范围内
        if (isInExpressArea(l, t))
        {
            l = btnHeight * (l / btnHeight);
            t = btnHeight * (numHeiNum + calHeiNum) + 2 * splitHeight;
            
            // 如果待放的地方已经有了一个运算数或者运算符，则调换两者的位置。
            if (null != expressBtnArr[l / btnHeight]) {
                Button oldBtn = expressBtnArr[l / btnHeight];
                expressBtnArr[l / btnHeight] = (Button)v;
                expressBtnArr[btnOldLeft / btnHeight] = oldBtn;
                moveBtn(oldBtn, btnOldLeft, btnOldTop);
            } else {
                // 如果待放的地方没有运算数或者运算符，则直接移动一下该按钮的位置。
                expressBtnArr[l / btnHeight] = (Button)v;
                expressBtnArr[btnOldLeft / btnHeight] = null;
            }
            moveBtn((Button)v, l, t);
        }
        // 如果放到数字区
        else if (isInNumArea(l, t)) {
            l = btnHeight * (l / btnHeight);
            t = btnHeight * (t / btnHeight);

            /**
             *  a:如果该按钮不是数字，则返回原来的位置。
             *  a:如果待放的地方已经有了运算数，则调换两者的位置。
             *  b:如果待放的地方没有运算数，则从目的运算表达式中移除，并且放置到正确的数字区。
             */
            BtnType oldBtnType = getBtnType(v.getId());
            // 如果该按钮不是数字，则返回原来的位置。
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
        // 如果放到运算符区
        else if (isInCalArea(l, t)) {
            l = btnHeight * (l / btnHeight);
            t = btnHeight * (t / btnHeight) + splitHeight;
            
            /**
             *  a:如果该按钮不是运算符，则返回原来的位置。
             *  a:如果待放的地方已经有了运算符，则调换两者的位置。
             *  b:如果待放的地方没有运算符，则从目的运算表达式中移除，并且放置到正确的运算区。
             */
            BtnType oldBtnType = getBtnType(v.getId());
            // 如果该按钮不是数字，则返回原来的位置。
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
        // 如果放到非数字区、非运算符区以及非运算表达式范围内，则返回原来的位置
        else {
            moveBtn((Button)v, btnOldLeft, btnOldTop);
        }
    }
    
    /**
     * 获取一个数字按钮ID
     * @param leftIndex
     * 横坐标顺序
     * @param topIndex
     * 纵坐标顺序
     * @return int
     * ID
     * @Exception 异常对象
     */
    private int getNumId(int leftIndex, int topIndex)
    {
        return Const.NUM_TEN * leftIndex + topIndex;
    }

    /**
     * 获取一个运算符按钮ID
     * @param leftIndex
     * 横坐标顺序
     * @param topIndex
     * 纵坐标顺序
     * @return int
     * ID
     * @Exception 异常对象
     */
    private int getCalId(int leftIndex, int topIndex)
    {
        return Const.NUM_TEN * (leftIndex + numHeiNum) + topIndex;
    }
    
    /**
     * 按钮原来在数字区
     * 方法描述：
     * 按钮的位置原来在数字区
     * @param rawX
     * 横坐标
     * @param rawY
     * 纵坐标
     * @return boolean
     * 在数字区，返回true   否则，返回false
     * @Exception 异常对象
     */
    private static boolean isInNumArea(float rawX, float rawY) {
        if (rawY < numHeiNum * btnHeight) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
    
    /**
     * 按钮原来在运算符区
     * 方法描述：
     * 按钮的位置原来在运算符区
     * @param rawX
     * 横坐标
     * @param rawY
     * 纵坐标
     * @return boolean
     * 在运算符区，返回true   否则，返回false
     * @Exception 异常对象
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
     * 按钮原来在表达式区
     * 方法描述：
     * 按钮的位置原来在表达式区
     * @param rawX
     * 横坐标
     * @param rawY
     * 纵坐标
     * @return boolean
     * 在表达式区，返回true   否则，返回false
     * @Exception 异常对象
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
     * 是数字按钮
     * @param id
     * 待判断的按钮ID
     * @return boolean
     * 如果是数字按钮，则返回true   否则，返回false
     * @Exception 异常对象
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
     * 是运算符按钮
     * @param id
     * 待判断的按钮ID
     * @return boolean
     * 如果是运算符按钮，则返回true   否则，返回false
     * @Exception 异常对象
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
     * 获取按钮的类型
     * @param id
     * @return
     * @Exception 异常对象
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
     * 移动一个按钮
     * @param v
     * 待移动的按钮对象
     * @param left
     * 按钮移动之后的距离屏幕左侧的位置
     * @param top
     * 按钮移动之后的距离屏幕上方的位置
     */
    private void moveBtn(Button btn, int left, int top) {
    	btn.layout(left, top, left + btnHeight, top + btnHeight);
        RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(btnWidth, btnHeight); // 设置按钮的宽度和高度
        // i表示的是第i行 k表示的是第k列
        btParams.leftMargin = left; // 横坐标定位[距离屏幕最左上方往右]
        btParams.topMargin = top; // 纵坐标定位[距离屏幕最左上方往下]
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
        // 下面判断移动是否超出屏幕
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
         * 运算数
         */
        NUMBER,
        /**
         * 运算符
         */
        CALCULATE,
        /**
         * 表达式
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
