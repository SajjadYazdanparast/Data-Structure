package com.yazdanparast.sajjad.chess;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class IntroSliderActivity extends AppCompatActivity {

    ViewPager intro_viewpager ;
    Button next , skip ;
    TextView dots_tv;
    int [] layout_ids = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();

        init();

    }

    private void init() {
        next = (Button) findViewById(R.id.intro_next_btn);
        skip = (Button) findViewById(R.id.intro_btn_skip);
        dots_tv = (TextView) findViewById(R.id.intro_dots_tv);
        intro_viewpager = (ViewPager) findViewById(R.id.intro_viewpager);
        intro_viewpager.setAdapter(new Adapter());
    }

    public class Adapter extends PagerAdapter
    {


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            View view = LayoutInflater.from(IntroSliderActivity.this).inflate(R.layout.developer_slide,container,false);
//            container.addView(view);
//            return view;
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public int getCount() {
            return layout_ids.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
