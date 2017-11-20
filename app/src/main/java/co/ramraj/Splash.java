package co.ramraj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

public class Splash extends Activity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    //    Button btnstart;
    public static String device_id;
    SliderLayout sliderLayout;
    HashMap<String, Integer> Hash_file_maps;
    PagerIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        btnstart = (Button) findViewById(R.id.btnstart);
        device_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Internet Connection Error");
            alertDialog.setMessage("Please connect to working Internet connection");
            alertDialog.setIcon(R.drawable.ic_launcher);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Splash.this.finish();
                }
            });
            alertDialog.show();
            return;
        }

//        btnstart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in = new Intent(Splash.this, MainActivity.class);
//                startActivity(in);
//                Splash.this.finish();
//            }
//        });
        Hash_file_maps = new HashMap<String, Integer>();

        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        indicator = (PagerIndicator) findViewById(R.id.custom_indicator);

        Hash_file_maps.put("A1", R.drawable.background);
        Hash_file_maps.put("A2", R.drawable.background1);
        Hash_file_maps.put("A3", R.drawable.background2);
        Hash_file_maps.put("A4", R.drawable.background3);

        for (String name : Hash_file_maps.keySet()) {

            DefaultSliderView textSliderView = new DefaultSliderView(Splash.this);
            textSliderView
//                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.setBackgroundColor(Color.TRANSPARENT);
            sliderLayout.addSlider(textSliderView);
        }
//        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.setCustomIndicator(indicator);
        sliderLayout.addOnPageChangeListener(this);
    }

    public void start(View view) {
        Intent in = new Intent(Splash.this, MainActivity.class);
        startActivity(in);
        Splash.this.finish();
    }

    @Override
    protected void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

//        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderLayout.startAutoCycle();
    }
}
