package com.killy.calculate;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ImageTextButton extends Activity
{
    LinearLayout m_ll;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_face);
        m_ll = (LinearLayout) findViewById(R.id.contactUs);
        m_ll.setClickable(true);
        m_ll.setOnClickListener(ocl);
        m_ll.setOnTouchListener(otl);
    }

    public OnClickListener ocl = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT)
                    .show();
        }
    };

    public OnTouchListener otl = new OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            // TODO Auto-generated method stub
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                m_ll.setBackgroundColor(Color.rgb(127, 127, 127));
            }
            else if (event.getAction() == MotionEvent.ACTION_UP)
            {
                m_ll.setBackgroundColor(Color.TRANSPARENT);
            }
            return false;
        }
    };
}