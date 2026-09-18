package com.haseltonmediagroup.virtualbarsocial

import android.app.Activity
import android.os.Bundle
import android.graphics.*
import android.view.*
import android.widget.*
import android.content.Context
import kotlin.math.hypot

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = FrameLayout(this)
        root.addView(BarView(this))
        val chat = EditText(this).apply {
            hint = "Say something nearby..."
            setTextColor(Color.WHITE)
            setHintTextColor(Color.LTGRAY)
            setBackgroundColor(0xBB16131A.toInt())
            isSingleLine = true
        }
        val density = resources.displayMetrics.density
        val lp = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            (56 * density).toInt()
        ).apply {
            gravity = Gravity.BOTTOM
            setMargins((220*density).toInt(),0,(220*density).toInt(),(18*density).toInt())
        }
        root.addView(chat, lp)
        setContentView(root)
    }
}

class BarView(context: Context) : View(context) {
    private val p = Paint(Paint.ANTI_ALIAS_FLAG)
    private var playerX = 0.5f
    private var playerY = 0.72f
    private val people = listOf(0.28f to 0.42f,0.43f to 0.38f,0.68f to 0.45f,0.77f to 0.62f,0.22f to 0.65f)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w=width.toFloat(); val h=height.toFloat()
        p.shader=LinearGradient(0f,0f,0f,h,0xFF120F18.toInt(),0xFF352019.toInt(),Shader.TileMode.CLAMP)
        canvas.drawRect(0f,0f,w,h,p); p.shader=null
        p.color=0xFF4B2B1D.toInt(); canvas.drawRect(0f,h*.18f,w,h*.29f,p)
        p.color=0xFFD99A3A.toInt(); canvas.drawRect(0f,h*.29f,w,h*.305f,p)
        p.color=0xFF21191C.toInt(); canvas.drawRect(0f,h*.76f,w,h,p)
        p.textAlign=Paint.Align.CENTER; p.textSize=32f; p.color=0xFFFFC65A.toInt()
        canvas.drawText("THE LANTERN - SOCIAL BAR",w/2,50f,p)
        people.forEachIndexed { index, q -> drawPerson(canvas,q.first*w,q.second*h,"Guest "+(index+1),false) }
        drawPerson(canvas,playerX*w,playerY*h,"YOU",true)
        p.color=0xCC000000.toInt(); canvas.drawCircle(105f,h-105f,75f,p)
        p.style=Paint.Style.STROKE; p.strokeWidth=4f; p.color=0x88FFFFFF.toInt(); canvas.drawCircle(105f,h-105f,75f,p)
        p.style=Paint.Style.FILL; p.textSize=20f; p.color=Color.WHITE; canvas.drawText("MOVE",105f,h-98f,p)
    }

    private fun drawPerson(canvas:Canvas, px:Float, py:Float, name:String, you:Boolean) {
        p.color=if(you) 0xFFFFB84D.toInt() else 0xFF6E86A6.toInt()
        canvas.drawCircle(px,py-42f,18f,p)
        canvas.drawRoundRect(px-18f,py-25f,px+18f,py+35f,14f,14f,p)
        p.textSize=16f; p.color=Color.WHITE; canvas.drawText(name,px,py-70f,p)
        if(!you && hypot(px-playerX*width,py-playerY*height)<width*.23f) {
            p.textSize=14f; p.color=0xFFFFD88A.toInt(); canvas.drawText("nearby",px,py+58f,p)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.action==MotionEvent.ACTION_DOWN || event.action==MotionEvent.ACTION_MOVE) {
            playerX=(event.x/width).coerceIn(.08f,.92f)
            playerY=(event.y/height).coerceIn(.34f,.82f)
            invalidate()
        }
        return true
    }
}