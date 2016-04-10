package com.wang.xiaoke.fileexplorer.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.xiaoke.fileexplorer.R;

/**
 * Created by Administrator on 2016/4/8.
 */
public class MyTopView extends RelativeLayout {

    //包含topBar上的元素：左按钮、右按钮、标题
    private Button mLeftButton, mRightButton;
    private TextView mTitleView;

    //布局属性，用来控制组件元素在ViewGroup中的位置
    private LayoutParams mLeftParams, mTitleParams, mRightParams;

    //左按钮的属性值，即我们在attrs.xml中定义的属性
    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;
    //标题的属性值，即我们在attrs.xml中定义的属性
    private String mTitle;
    private float mTitleTextSize;
    private int mTitleTextColor;
    //右按钮的属性值，即我们在attrs.xml中定义的属性
    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightText;

    //映射传入的接口对象
    private topBarClickListener mListener;

    public MyTopView(Context context) {
        super(context);
    }

    public MyTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置topBar的背景
        setBackgroundColor(0x00000000);

        //通过这个方法将attrs.xml中定义的所有属性的值存储到TypedArray中
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.MyTopBar);

        //从TypedArray中取出对应的值来为要设置的属性赋值
        mLeftTextColor = typedArray.getColor(R.styleable.MyTopBar_leftTextColor, 0);
        mLeftBackground = typedArray.getDrawable(R.styleable.MyTopBar_leftBackground);
        mLeftText = typedArray.getString(R.styleable.MyTopBar_leftText);

        mTitle = typedArray.getString(R.styleable.MyTopBar_titleText);
        mTitleTextSize = typedArray.getDimension(R.styleable.MyTopBar_titleTextSize, 10);
        mTitleTextColor = typedArray.getColor(R.styleable.MyTopBar_titleColor, 0);

        mRightTextColor = typedArray.getColor(R.styleable.MyTopBar_rightTextColor, 0);
        mRightBackground = typedArray.getDrawable(R.styleable.MyTopBar_rightBackground);
        mRightText = typedArray.getString(R.styleable.MyTopBar_rightText);

        //获取值后，一般要调用recycle方法来避免重新创建的时候的错误
        typedArray.recycle();

        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new Button(context);

        //为创建的组件元素赋值
        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackground(mLeftBackground);
        mLeftButton.setText(mLeftText);

        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightBackground);
        mRightButton.setText(mRightText);

        mTitleView.setText(mTitle);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setGravity(Gravity.CENTER);

        //为组件元素设置相应的布局元素
//        mLeftParams = new LayoutParams(
//                LayoutParams.WRAP_CONTENT,
//                LayoutParams.MATCH_PARENT);
        mLeftParams = new LayoutParams(16,16);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(mLeftButton,mLeftParams); //添加到ViewGroup

        mRightParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(mRightButton, mRightParams);

        mTitleParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(mTitleView,mTitleParams);

        //按钮的点击事件，不需要具体的实现，只需要调用接口的方法，回调的时候再具体的实现
        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //暴露一个方法给调用者来注册接口的回调
    public void setOnTopBarClickListener(topBarClickListener mListener){
        this.mListener = mListener;
    }

    /**
     * 设置按钮的显示与否，通过id区分按钮，flag区分是否显示
     * @param id
     * @param flag
     */
    public void setButtonVisibility(int id,boolean flag){
        if(flag){
            if(id == 0){
                mLeftButton.setVisibility(VISIBLE);
            }else {
                mRightButton.setVisibility(VISIBLE);
            }
        }else {
            if(id == 0){
                mLeftButton.setVisibility(GONE);
            }else {
                mRightButton.setVisibility(GONE);
            }
        }
    }

    //接口对象，实现回调机制，在回调方法中通过映射的接口对象调用接口中的方法
    public interface topBarClickListener{
        //左按钮的点击事件
        void leftClick();
        //右按钮的点击事件
        void rightClick();
    }
}
