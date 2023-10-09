//package com.dohyeok.gulpgulp.ui
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import com.dohyeok.gulpgulp.R
//
//@SuppressLint("ViewConstructor")
//class OptionView : LinearLayout {
//    lateinit var layout : LinearLayout
//    lateinit var tvName : TextView
//    lateinit var imgIcon : ImageView
//
//    constructor(context: Context?) : super(context){
//        init(context)
//    }
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
//        init(context)
//        getAttrs(attrs)
//
//    }
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
//        init(context)
//        getAttrs(attrs, defStyleAttr)
//    }
//
//    //아까 만들어둔 커스텀뷰 레이아웃을 inflate 해서 적용해준다.
//    //그리고 layout에서 id를 참조해 위에 lateinit 으로 냅뒀던 변수들을 초기화 해준다.
//    private fun init(context:Context?){
//        val view = LayoutInflater.from(context).inflate(R.layout.customview,this,false)
//        addView(view)
//
//        layout = findViewById(R.id.layout)
//        tvName = findViewById(R.id.tvName)
//        imgIcon = findViewById(R.id.imgIcon)
//    }
//
//    private fun getAttrs(attrs:AttributeSet?){
//        //아까 만들어뒀던 속성 attrs 를 참조함
//        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.LoginButton)
//        setTypeArray(typedArray)
//    }
//
//    private fun getAttrs(attrs:AttributeSet?, defStyle:Int){
//        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.LoginButton,defStyle,0)
//        setTypeArray(typedArray)
//    }
//}