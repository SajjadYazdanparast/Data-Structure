package com.yazdanparast.sajjad.chess;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IntroSliderActivity extends AppCompatActivity {

    ViewPager intro_viewpager ;
    Button next , skip ;
    LinearLayout dots_layout;
    int [] layout_bgs = {R.color._0 , R.color._1 , R.color._2 ,
            R.color._3 , R.color._4 , R.color._5};
    int [] BeadsImage = {R.drawable.black_king , R.drawable.blackminister , R.drawable.blackelephant ,
                        R.drawable.blacknight , R.drawable.blackcastle , R.drawable.blacksoldier} ;
    String [] BeadsName = new String[6];
    String [] BeadsMove = new String[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();

        init();
        ShowDots(0);
        intro_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ShowDots(position);
                if (position == intro_viewpager.getAdapter().getCount()-1){
                    skip.setVisibility(View.GONE);
                    next.setText(R.string.gotit);
                }
                else{
                    skip.setVisibility(View.VISIBLE);
                    next.setText(R.string.next);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PrefMan(IntroSliderActivity.this).setSituation(true);
                finish();
                startActivity(new Intent(IntroSliderActivity.this,PlayActivity.class));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (next.getText().toString().equals(getString(R.string.next)))
                    intro_viewpager.setCurrentItem(intro_viewpager.getCurrentItem()+1);
                else {
                    new PrefMan(IntroSliderActivity.this).setSituation(false);
                    finish();
                    startActivity(new Intent(IntroSliderActivity.this,PlayActivity.class));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!new PrefMan(this).getSituation())
        {
            finish();
            startActivity(new Intent(IntroSliderActivity.this,PlayActivity.class));
        }
    }

    private void init() {

        next = (Button) findViewById(R.id.intro_next_btn);
        skip = (Button) findViewById(R.id.intro_btn_skip);
        dots_layout = (LinearLayout) findViewById(R.id.intro_dots_layout);
        intro_viewpager = (ViewPager) findViewById(R.id.intro_viewpager);
        intro_viewpager.setAdapter(new Adapter());

        BeadsName = getResources().getStringArray(R.array.BeadsName);
        BeadsMove = getResources().getStringArray(R.array.BeadsMove);
    }

    public class Adapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(IntroSliderActivity.this).inflate(R.layout.slide,container,false);
            ((TextView)view.findViewById(R.id.slide_beadName)).setText(BeadsName[position]);
            ((TextView)view.findViewById(R.id.slide_beadMove)).setText(BeadsMove[position]);
            ((ImageView)view.findViewById(R.id.slide_imgview)).setImageResource(BeadsImage[position]);
            view.setBackgroundResource(layout_bgs[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public int getCount() {
            return layout_bgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void ShowDots(int position) {
        TextView[]dots = new TextView[6];
        dots_layout.removeAllViews();
        for(int i=0 ; i<6 ;i++) {
            dots[i] = new TextView(IntroSliderActivity.this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            if (i == position) {
                dots[i].setTextColor(Color.WHITE);
            }
            else {
                dots[i].setTextColor(Color.BLACK);
            }
            dots[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,35f);
            dots_layout.addView(dots[i]);
        }
    }
}
