package com.wang.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wang.widget.custom.BubbleImageView;

public class MainActivity extends Activity {

    private BubbleImageView bubbleImageView;

    private String imagePath = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496310728080&di=40ffc5f833c15861653a7ec87b94461e&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2Fimg013%2Fv4%2F96%2Fd%2F45.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bubbleImageView = (BubbleImageView) findViewById(R.id.test_bubble);

    }

    public void testLoad(View view) {
        bubbleImageView.setImage(imagePath, 1200, 800);
    }
}
