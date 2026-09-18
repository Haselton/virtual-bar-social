package com.haseltonmediagroup.virtualbarsocial
import android.app.Activity
import android.os.Bundle
import android.graphics.*
import android.view.*
import android.widget.*
import android.content.Context
import kotlin.math.*
class MainActivity:Activity(){
 override fun onCreate(b:Bundle?){super.onCreate(b);val root=FrameLayout(this);root.addView(BarView(this));val chat=EditText(this).apply{hint="Say something nearby...";setTextColor(Color.WHITE);setHintTextColor(Color.LTGRAY);setBackgroundColor(0xBB16131A.toInt());isSingleLine=true};val lp=FrameLayout.LayoutParams(-1,56.dp).apply{gravity=Gravity.BOTTOM;setMargins(220.dp,0,220.dp,18.dp)};root.addView(chat,lp);setContentView(root)}
 val Int.dp get()=(this*resources.displayMetrics.density).toInt()
}
class BarView(c:Context):View(c){
 val p=Paint(1);var x=.5f;var y=.72f;val people=listOf(.28f to .42f,.43f to .38f,.68f to .45f,.77f to .62f,.22f to .65f)
 override fun onDraw(v:Canvas){val w=width.toFloat();val h=height.toFloat();p.shader=LinearGradient(0f,0f,0f,h,0xFF120F18.toInt(),0xFF352019.toInt(),Shader.TileMode.CLAMP);v.drawRect(0f,0f,w,h,p);p.shader=null;p.color=0xFF4B2B1D.toInt();v.drawRect(0f,h*.18f,w,h*.29f,p);p.color=0xFFD99A3A.toInt();v.drawRect(0f,h*.29f,w,h*.305f,p);p.color=0xFF21191C.toInt();v.drawRect(0f,h*.76f,w,h,p);p.textAlign=Paint.Align.CENTER;p.textSize=32f;p.color=0xFFFFC65A.toInt();v.drawText("THE LANTERN  •  SOCIAL BAR",w/2,50f,p);people.forEachIndexed{idx,q->person(v,q.first*w,q.second*h,"Guest "+(idx+1),false)};person(v,x*w,y*h,"YOU",true);p.color=0xCC000000.toInt();v.drawCircle(105f,h-105f,75f,p);p.style=Paint.Style.STROKE;p.strokeWidth=4f;p.color=0x88FFFFFF.toInt();v.drawCircle(105f,h-105f,75f,p);p.style=Paint.Style.FILL;p.textSize=20f;p.color=Color.WHITE;v.drawText("MOVE",105f,h-98f,p)}
 fun person(v:Canvas,px:Float,py:Float,name:String,you:Boolean){p.color=if(you)0xFFFFB84D.toInt() else 0xFF6E86A6.toInt();v.drawCircle(px,py-42f,18f,p);v.drawRoundRect(px-18,py-25,px+18,py+35,14f,14f,p);p.textSize=16f;p.color=Color.WHITE;v.drawText(name,px,py-70,p);if(!you&&hypot(px-x*width,py-y*height)<width*.23){p.textSize=14f;p.color=0xFFFFD88A.toInt();v.drawText("nearby",px,py+58,p)}}
 override fun onTouchEvent(e:MotionEvent):Boolean{if(e.action==MotionEvent.ACTION_DOWN||e.action==MotionEvent.ACTION_MOVE){x=(e.x/width).coerceIn(.08f,.92f);y=(e.y/height).coerceIn(.34f,.82f);invalidate()};return true}
}