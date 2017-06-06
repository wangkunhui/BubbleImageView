package com.wang.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wkh.widget.BubbleImageView;

public class MainActivity extends Activity {

    private BubbleImageView bubbleImageView2;

    private String imagePath = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496310728080&di=40ffc5f833c15861653a7ec87b94461e&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2Fimg013%2Fv4%2F96%2Fd%2F45.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bubbleImageView2 = (BubbleImageView) findViewById(R.id.test_bubble_2);
    }

    public void testLoad(View view) {
        bubbleImageView2.setImage(1200, 800, new CustomImageLoader(MainActivity.this, imagePath, bubbleImageView2));
    }
}
