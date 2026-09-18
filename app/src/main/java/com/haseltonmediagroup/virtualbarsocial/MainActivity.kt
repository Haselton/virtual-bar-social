package com.haseltonmediagroup.virtualbarsocial

import android.app.Activity
import android.os.Bundle
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.content.Context
import kotlin.math.hypot

class MainActivity : Activity() {
    private lateinit var barView: BarView
    private lateinit var chatPanel: LinearLayout
    private lateinit var chatTitle: TextView
    private lateinit var messages: TextView
    private lateinit var input: EditText
    private var activeGuest = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val root = FrameLayout(this)
        barView = BarView(this) { guest -> openChat(guest) }
        root.addView(barView)

        val density = resources.displayMetrics.density
        chatPanel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding((16*density).toInt(),(12*density).toInt(),(16*density).toInt(),(12*density).toInt())
            background = GradientDrawable().apply { setColor(0xF2221820.toInt()); cornerRadius=22*density }
            visibility = View.GONE
        }
        chatTitle = TextView(this).apply { textSize=19f; setTextColor(0xFFFFC65A.toInt()) }
        messages = TextView(this).apply { textSize=16f; setTextColor(Color.WHITE); setPadding(0,(8*density).toInt(),0,(8*density).toInt()) }
        val row = LinearLayout(this).apply { orientation=LinearLayout.HORIZONTAL }
        input = EditText(this).apply { hint="Message"; setTextColor(Color.WHITE); setHintTextColor(Color.LTGRAY); isSingleLine=true }
        val send = Button(this).apply { text="SEND"; setOnClickListener { sendMessage() } }
        val close = Button(this).apply { text="LEAVE"; setOnClickListener { closeChat() } }
        row.addView(input, LinearLayout.LayoutParams(0,FrameLayout.LayoutParams.WRAP_CONTENT,1f))
        row.addView(send)
        chatPanel.addView(chatTitle)
        chatPanel.addView(messages)
        chatPanel.addView(row)
        chatPanel.addView(close)
        root.addView(chatPanel, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT).apply {
            gravity=Gravity.BOTTOM; setMargins((12*density).toInt(),0,(12*density).toInt(),(18*density).toInt())
        })
        setContentView(root)
    }

    private fun openChat(index:Int) {
        activeGuest=index
        chatTitle.text="Chat with Guest ${index+1}"
        messages.text=when(index) {
            0 -> "Guest 1: Hey! First time at The Lantern?"
            1 -> "Guest 2: This place is getting busy tonight."
            2 -> "Guest 3: What's up?"
            3 -> "Guest 4: Grab a seat."
            else -> "Guest 5: Hey there."
        }
        chatPanel.visibility=View.VISIBLE
        input.requestFocus()
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(input,InputMethodManager.SHOW_IMPLICIT)
    }

    private fun sendMessage() {
        val msg=input.text.toString().trim()
        if(msg.isEmpty()) return
        messages.append("\nYou: $msg")
        val reply=when(activeGuest) {
            0 -> "Nice. I'm just meeting people."
            1 -> "Yeah, that's cool."
            2 -> "Good to meet you."
            3 -> "Want to hang out here for a bit?"
            else -> "Glad you stopped by."
        }
        messages.append("\nGuest ${activeGuest+1}: $reply")
        input.text.clear()
    }

    private fun closeChat() {
        chatPanel.visibility=View.GONE
        activeGuest=-1
        input.clearFocus()
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(input.windowToken,0)
    }
}

class BarView(context: Context, private val onChat:(Int)->Unit) : View(context) {
    private val p=Paint(Paint.ANTI_ALIAS_FLAG)
    private var playerX=.5f; private var playerY=.78f
    private var joyX=0f; private var joyY=0f; private var dragging=false
    private val people=listOf(.25f to .39f,.48f to .45f,.73f to .38f,.72f to .63f,.29f to .64f)
    private var nearest=-1
    private val names=listOf("Guest 1","Guest 2","Guest 3","Guest 4","Guest 5")

    override fun onDraw(c:Canvas) {
        val w=width.toFloat(); val h=height.toFloat()
        p.shader=LinearGradient(0f,0f,0f,h,0xFF100D15.toInt(),0xFF3B241A.toInt(),Shader.TileMode.CLAMP); c.drawRect(0f,0f,w,h,p); p.shader=null
        p.color=0xFF24161B.toInt(); c.drawRect(0f,0f,w,h*.13f,p)
        p.textAlign=Paint.Align.CENTER; p.color=0xFFFFC65A.toInt(); p.textSize=w*.055f; c.drawText("THE LANTERN",w/2,h*.065f,p)
        p.textSize=w*.025f; p.color=0xFFB9A48A.toInt(); c.drawText("SOCIAL BAR • 5 ONLINE",w/2,h*.095f,p)

        // bar and stools
        p.color=0xFF6B3B22.toInt(); c.drawRoundRect(w*.08f,h*.16f,w*.92f,h*.27f,18f,18f,p)
        p.color=0xFFD99A3A.toInt(); c.drawRect(w*.08f,h*.255f,w*.92f,h*.27f,p)
        for(i in 0..3){ val x=w*(.18f+i*.21f); p.color=0xFF31252A.toInt(); c.drawCircle(x,h*.315f,w*.035f,p) }
        // tables
        p.color=0xFF4B3028.toInt(); c.drawCircle(w*.18f,h*.52f,w*.09f,p); c.drawCircle(w*.82f,h*.52f,w*.09f,p)
        p.color=0xFF21191C.toInt(); c.drawRect(0f,h*.86f,w,h,p)

        nearest=-1; var best=Float.MAX_VALUE
        people.forEachIndexed { i,q ->
            val d=hypot(q.first-playerX,q.second-playerY)
            if(d<best){best=d;nearest=i}
            drawPerson(c,q.first*w,q.second*h,names[i],false,d<.17f)
        }
        if(best>=.17f) nearest=-1
        drawPerson(c,playerX*w,playerY*h,"YOU",true,false)

        val jx=w*.18f; val jy=h*.91f; val jr=w*.105f
        p.color=0xBB000000.toInt(); c.drawCircle(jx,jy,jr,p)
        p.style=Paint.Style.STROKE; p.strokeWidth=4f; p.color=0x88FFFFFF.toInt(); c.drawCircle(jx,jy,jr,p); p.style=Paint.Style.FILL
        p.color=0xFFBFAE9C.toInt(); c.drawCircle(jx+joyX*jr*.55f,jy+joyY*jr*.55f,jr*.35f,p)
        if(nearest>=0) {
            val l=w*.48f; val t=h*.875f; val r=w*.94f; val b=h*.95f
            p.color=0xFFFFB84D.toInt(); c.drawRoundRect(l,t,r,b,20f,20f,p)
            p.color=0xFF1A1214.toInt(); p.textSize=w*.04f; c.drawText("CHAT WITH ${names[nearest].uppercase()}",(l+r)/2,(t+b)/2+8f,p)
        } else {
            p.color=0xFFB9A48A.toInt(); p.textSize=w*.029f; c.drawText("Walk near someone to chat",w*.7f,h*.915f,p)
        }
    }

    private fun drawPerson(c:Canvas,x:Float,y:Float,name:String,you:Boolean,near:Boolean) {
        if(near){p.color=0x55FFC65A; c.drawCircle(x,y,width*.07f,p)}
        p.color=if(you) 0xFFFFB84D.toInt() else 0xFF6E86A6.toInt()
        c.drawCircle(x,y-width*.035f,width*.026f,p); c.drawRoundRect(x-width*.026f,y-width*.005f,x+width*.026f,y+width*.065f,14f,14f,p)
        p.textSize=width*.028f; p.color=Color.WHITE; c.drawText(name,x,y-width*.075f,p)
    }

    override fun onTouchEvent(e:MotionEvent):Boolean {
        val w=width.toFloat(); val h=height.toFloat(); val jx=w*.18f; val jy=h*.91f; val jr=w*.13f
        if(e.action==MotionEvent.ACTION_DOWN && nearest>=0 && e.x>w*.45f && e.y>h*.85f){ onChat(nearest); return true }
        when(e.action) {
            MotionEvent.ACTION_DOWN -> dragging=hypot(e.x-jx,e.y-jy)<jr
            MotionEvent.ACTION_MOVE -> if(dragging) {
                joyX=((e.x-jx)/jr).coerceIn(-1f,1f); joyY=((e.y-jy)/jr).coerceIn(-1f,1f)
                val mag=hypot(joyX,joyY); if(mag>1){joyX/=mag;joyY/=mag}
                playerX=(playerX+joyX*.018f).coerceIn(.07f,.93f)
                playerY=(playerY+joyY*.018f).coerceIn(.31f,.82f); invalidate()
            }
            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL -> {dragging=false;joyX=0f;joyY=0f;invalidate()}
        }
        return true
    }
}