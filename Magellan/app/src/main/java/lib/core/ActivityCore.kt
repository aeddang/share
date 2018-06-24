package lib.core

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.aeddang.magellan.R
import lib.ViewUtil
import lib.observer.ObserverController
import java.util.ArrayList

open class ActivityCore : AppCompatActivity(), Animation.AnimationListener, View.OnClickListener
{

    open var currentView: ViewCore? = null
    var body: FrameLayout? = null
    var viewer: FrameLayout? = null
    var popupViewer: FrameLayout? = null
    private var viewerDimed: Button? = null
    open var loadingBar: ProgressBar? = null
    open var loadingImage: ImageView? = null
    open var dimed: ImageView? = null

    private var currentAni: Animation? = null
    private var introAni: Animation? = null
    private var loadingAni: Animation? = null
    open var currentPageObj: PageObject? = null
    open var homePageObject: PageObject? = null
    open var historyA: ArrayList<PageObject> = ArrayList<PageObject>()
    open var popupA: ArrayList<ViewCore> = ArrayList<ViewCore>()
    open var intro: FrameLayout? = null
    open var isInit: Boolean? = false

    open var finalOrientation = -1
    open var willChangeOrientation = SCREEN_CHANGED_TYPE_NONE

    private var isChangeAble: Boolean? = null
    private var isDimed: Boolean? = null
    private val removeHandler = Handler()
    private var backKeyCount = 0


    var dpi: Float = 0.toFloat()
        get() = applicationContext.resources.displayMetrics.density

    val screenOrientation: Int
        get() = windowManager.defaultDisplay.rotation

    val appSize: Point
        get()
        {
            val rec = Point()
            if(body != null) {
                rec.x = body!!.width
                rec.y = body!!.height
            }
            return rec
        }
    val currentPageID: Int
        get() = if (currentPageObj == null)
        {
            -1
        }
        else
        {
            currentPageObj!!.pageID
        }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if (isInit == true) return
        ActivityCore.instence = this
        isChangeAble = true
        isDimed = false
        setContentView(R.layout.activity_main)
        loadingBar = findViewById<View>(R.id._loadingBar) as ProgressBar
        //loadingImage=(ImageView) findViewById(R.id._loadingImage);
        dimed = findViewById<View>(R.id._dimed) as ImageView
        body = findViewById<View>(R.id._body) as FrameLayout
        viewer = findViewById<View>(R.id._viewer) as FrameLayout
        popupViewer = findViewById<View>(R.id._popupViewer) as FrameLayout
        intro = findViewById<View>(R.id._intro) as FrameLayout
        viewerDimed = findViewById<View>(R.id._viewerDimed) as Button
        viewerDimed?.visibility = View.GONE
        viewerDimed?.setOnClickListener(this)
        loadingAni = AnimationUtils.loadAnimation(this, R.anim.motion_rotate)
        loadingImage?.visibility = View.GONE
        actionBar?.hide()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean
    {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if (isChangeAble == false || isDimed == true) return true
                //SoundManeger.getInstence().play();
                changeViewBack()
            }
        }
        return false
    }

    fun update()
    {
        super.onPause()
        currentView?.onUpdate()
        for (pop in popupA) pop.onUpdate()
    }

    override fun onPause()
    {
        super.onPause()
        currentView?.onPause()
        for (pop in popupA) pop.onPause()

    }

    override fun onResume()
    {
        super.onResume()
        currentView?.onResume()
        for (pop in popupA) pop.onResume()

    }

    override fun onClick(arg: View)
    {
        if (viewerDimed === arg) hideKeyBoard()
    }

    open fun resizeScreen(proposedWidth: Int, proposedheight: Int)
    {
        for (pop in popupA) pop.setBodySizeChanged(proposedWidth, proposedheight)

        if (willChangeOrientation >= SCREEN_CHANGED_TYPE_ORIENTATION)
        {
            if (finalOrientation != willChangeOrientation)
            {
                finalOrientation = willChangeOrientation
                ObserverController.shareInstence().notifyPost(NotificationOrientationChanged, finalOrientation, null)
                orientationChanged(finalOrientation)
            }
            willChangeOrientation = SCREEN_CHANGED_TYPE_RESIZED
        }
    }

    fun keyBoardShow(diff: Int)
    {
        viewerDimed?.visibility = View.VISIBLE
        ObserverController.shareInstence().notifyPost(NotificationKeyBoardShow, diff, null)

    }

    fun keyBoardHidden() {
        viewerDimed?.visibility = View.GONE
        ObserverController.shareInstence().notifyPost(NotificationKeyBoardHidden, null, null)

    }

    fun hideKeyBoard() {
        viewerDimed?.visibility = View.GONE
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null)
        {
            if (this.currentFocus != null) imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
    }

    fun changeViewMain()
    {
        val pobj = PageObject(HOME)
        pobj.dr = -1
        changeView(pobj)
    }

    fun changeViewBack()
    {
        if (popupA.size > 0) {
            val pop = popupA[popupA.size - 1]
            if (!pop.directBack()) return
            removePopup(pop)
            return
        }

        if (currentView != null)
        {
            if (currentView!!.directBack() == false) return
        }
        if (currentPageObj == null || currentPageObj!!.isHome)
        {
            backKeyCount++
            if (backKeyCount == 1)
            {
                val msgID = LanguageFactory.shareInstence().getResorceID("msg_app_out")
                Toast.makeText(this, msgID, Toast.LENGTH_LONG).show()
            }
            else
            {
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }
        else
        {
            changeViewBackDirect()
        }

    }

    fun changeViewBackDirect() {

        if(currentPageObj == null) return
        currentPageObj?.isHistory = false
        val pobj: PageObject
        val size = historyA.size - 1
        if (size < 0)
        {
            pobj = PageObject(HOME)
        }
        else
        {
            pobj = historyA[size]
            historyA.removeAt(size)
        }
        if (pobj.pageID == currentPageObj!!.pageID)
        {
            changeViewBack()
            return
        }
        pobj.dr = -1
        //Log.i("", "changeViewBackDirect historyA.size="+historyA.size());
        prevChangeViewBack(pobj)
        changeView(pobj)
    }

    open fun configurationChanged(newConfig: Configuration) {

    }

    open fun orientationChanged(orientation: Int) {

        Log.i("orientationChanged", "orientationChanged  : $orientation")
    }

    override fun onConfigurationChanged(newConfig: Configuration)
    {
        super.onConfigurationChanged(newConfig)
        if (this.screenOrientation != newConfig.orientation) willChangeOrientation = newConfig.orientation
        Log.i("willChangeOrientation", "willChangeOrientation  : $willChangeOrientation")
        if (currentView != null) currentView!!.configurationChanged(newConfig)
        configurationChanged(newConfig)
    }

    open fun prevChangeViewBack(info: PageObject) {

    }

    fun removeIntroStart(offset: Int) {


        if (intro != null)
        {
            introAni = AnimationUtils.loadAnimation(this, R.anim.motion_out)
            introAni!!.startOffset = offset.toLong()
            introAni!!.setAnimationListener(this)
            intro!!.startAnimation(introAni!!)
        }
    }

    private fun addHistory(info: PageObject) {

        if (!info.isHistory) return
        if (info.isHome)
        {
            homePageObject = info
            historyA.clear()
            return
        }
        historyA.add(info)
        //Log.i("", "historyA.size=" + historyA.size)
    }

    fun hasPopup(id: Int): Boolean
    {
        for (pop in popupA) {
            if (pop.pageInfo.pageID == id) return true
        }
        return false
    }

    fun addSinglePopup(info: PageObject): ViewCore?
    {
        return if (this.hasPopup(info.pageID) == true) null else this.addPopup(info)
    }

    fun addPopup(info: PageObject): ViewCore?
    {
        if (info.dr == 2) info.dr = 1
        val ani = getInAni(info)
        val pop = creatPopup(info) ?: return null
        backKeyCount = 0
        popupA.add(pop)
        popupViewer?.addView(pop, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        pop.initCore()
        popupAniStart(pop, ani, true)
        return pop
    }

    fun removePopup()
    {
        if (popupA.size > 0) removePopup(popupA[0])
    }

    fun removePopup(pop: ViewCore)
    {
        if (popupA.indexOf(pop) != -1)
        {
            loadingStop()
            popupA.remove(pop)
            val ani = getOutAni(pop.pageInfo)
            popupAniStart(pop, ani, false)
        }

    }

    fun getInAni(pObj: PageObject): Animation
    {
        val ani: Animation
        when (pObj.dr)
        {
            1 -> ani = AnimationUtils.loadAnimation(this, R.anim.motion_up_in)
            -1 -> ani = AnimationUtils.loadAnimation(this, R.anim.motion_down_in)
            else -> ani = AnimationUtils.loadAnimation(this, R.anim.motion_in)
        }
        return ani

    }

    fun getOutAni(pObj: PageObject): Animation
    {
        val ani: Animation
        when (pObj.dr)
        {
            1 -> ani = AnimationUtils.loadAnimation(this, R.anim.motion_up_out)
            -1 -> ani = AnimationUtils.loadAnimation(this, R.anim.motion_down_out)
            else -> ani = AnimationUtils.loadAnimation(this, R.anim.motion_out)
        }
        return ani

    }

    open fun creatPopup(pinfo: PageObject): ViewCore?
    {
        /*
    	ViewCore pop;
    	switch(pinfo.pageID){
    		case Config.POPUP_SELECT_AREA:
    			pop=new PopupSelectArea(this, pinfo);
    			break;
    	}

    	return pop;
        */
        return null
    }

    open fun doPopupAnimationEnd(pop: ViewCore, isOpen: Boolean?)
    {
        if (isOpen!!) {
            pop.movedInit()
        }
        else
        {
            pop.removeViewCore()
            ViewUtil.remove(pop)
            if (popupA.size == 0) popupViewer?.visibility = View.GONE
        }
    }

    open fun popupAniStart(pop: ViewCore, ani: Animation, isOpen: Boolean)
    {
        if (isOpen == false)
        {
            pop.removeViewInit()
        }
        else
        {
            popupViewer?.visibility = View.VISIBLE
        }

        ani.setAnimationListener(object : Animation.AnimationListener
        {
            override fun onAnimationEnd(arg: Animation)
            {
                val popupHandler = Handler()
                popupHandler.post { doPopupAnimationEnd(pop, isOpen) }

            }
            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationStart(arg0: Animation) {}
        })
        pop.startAnimation(ani)
    }

    /////////////////////////////////////////////////////////////////////////////////////


    fun changeView(info: PageObject)
    {
        if (isChangeAble==false) return

        backKeyCount = 0
        loadingStop()
        isChangeAble = false
        if (info.dr == 2)
        {
            if (currentPageObj != null)
            {
                if (info.pageID > currentPageObj!!.pageID)
                {
                    info.dr = 1

                }
                else if (info.pageID < currentPageObj!!.pageID)
                {
                    info.dr = -1
                }
                else
                {
                    info.dr = 0
                }
            }
            else
            {
                info.dr = 0
            }
        }
        currentPageObj = info
        changeInit()
        creatView()
        if (info.isHome == true) historyA.clear()
        viewer?.addView(currentView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        currentView?.initCore()
        changeStart()

    }

    open fun changeInit()
    {
        val removeAni: Animation
        when (currentPageObj!!.dr)
        {
            1 -> {
                currentAni = AnimationUtils.loadAnimation(this, R.anim.motion_left_in)
                removeAni = AnimationUtils.loadAnimation(this, R.anim.motion_right_out)
            }
            -1 -> {
                currentAni = AnimationUtils.loadAnimation(this, R.anim.motion_right_in)
                removeAni = AnimationUtils.loadAnimation(this, R.anim.motion_left_out)
            }
            else -> {
                currentAni = AnimationUtils.loadAnimation(this, R.anim.motion_in)
                removeAni = AnimationUtils.loadAnimation(this, R.anim.motion_out)
            }
        }
        currentView?.removeViewInit()
        currentView?.startAnimation(removeAni)
    }

    open fun changeStart()
    {
        currentAni?.setAnimationListener(this)
        currentView?.startAnimation(currentAni)
    }

    open fun creatView()
    {
        /*
    	switch(currentPageObj.pageID){
    	case HOME:
    		info.isHome=true;
    		currentView=new Main(this, info);
    		break;
    	}
        */
    }

    open fun changeEnd()
    {
        removeCore()
    }

    private fun removeCore()
    {
        if(viewer == null) return
        val num = viewer!!.childCount
        for (i in 0 until num) {
            val core = viewer!!.getChildAt(i) as? ViewCore
            if (currentView === core)
            {

            }
            else
            {
                if (core != null)
                {
                    addHistory(core!!.pageInfo)
                    core!!.removeViewCore()
                    viewer!!.removeView(core)
                }
            }
        }
        currentView?.movedInit()
        isChangeAble = true
    }

    private fun removeIntro()
    {
        ViewUtil.remove(intro)
        intro = null
    }

    open fun doAnimationEnd(arg: Animation)
    {
        if (currentAni === arg)
        {
            removeHandler.post { removeCore() }
        }
        else if (introAni === arg)
        {
            removeHandler.post { removeIntro() }
        }
    }


    override fun onAnimationEnd(arg: Animation) {
        doAnimationEnd(arg)
    }

    fun loadingStart(_isDimed: Boolean)
    {
        isDimed = _isDimed
        if (isDimed == true) dimed?.visibility = View.VISIBLE else dimed?.visibility = View.GONE
        loadingBar?.visibility = View.VISIBLE
        loadingImage?.animation = loadingAni
        loadingImage?.visibility = View.VISIBLE

    }

    fun loadingStop()
    {
        isDimed = false
        dimed?.visibility = View.GONE
        loadingBar?.visibility = View.GONE
        loadingImage?.animation = null
        loadingImage?.visibility = View.GONE
    }

    fun viewAlertSelect(title: String, msgId: Int, listener: DialogInterface.OnClickListener)
    {
        val msg = getString(msgId)
        viewAlertSelect(title, msg, listener)

    }

    fun viewAlert(title: String, msgId: Int, listener: DialogInterface.OnClickListener)
    {
        val msg = getString(msgId)
        viewAlert(title, msg, listener)
    }

    fun viewAlertSelect(title: String, msg: String, listener: DialogInterface.OnClickListener)
    {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(title)
        alert.setMessage(msg)
        alert.setPositiveButton(R.string.btn_ok, listener)
        alert.setNegativeButton(R.string.btn_no, listener)
        alert.show()
    }

    fun viewAlertSelectWithMenu(title: String, msgId: Int, items: Array<CharSequence>, listener: DialogInterface.OnClickListener)
    {
        val msg = getString(msgId)
        viewAlertSelectWithMenu(title, msg, items, listener)
    }

    fun viewAlertSelectWithMenu(title: String, msg: String, items: Array<CharSequence>, listener: DialogInterface.OnClickListener)
    {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(title)
        alert.setMessage(msg)
        alert.setPositiveButton(items[0], listener)
        alert.setNegativeButton(items[1], listener)
        alert.show()
    }

    fun viewAlert(title: String, msg: String, listener: DialogInterface.OnClickListener)
    {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(title)
        alert.setMessage(msg)
        alert.setPositiveButton(R.string.btn_ok, listener)
        alert.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true
    }


    override fun onAnimationRepeat(arg0: Animation)
    {
        // TODO Auto-generated method stub
    }

    override fun onAnimationStart(arg0: Animation)
    {
        // TODO Auto-generated method stub
    }

    companion object
    {
        val SCREEN_CHANGED_TYPE_ORIENTATION_RESIZED = -3
        val SCREEN_CHANGED_TYPE_ORIENTATION = 0
        val SCREEN_CHANGED_TYPE_RESIZED = -1
        val SCREEN_CHANGED_TYPE_NONE = -2
        val HOME = 0
        val PREFS_NAME="javis";
        val NotificationKeyBoardShow = "NotificationKeyBoardShow"
        val NotificationKeyBoardHidden = "NotificationKeyBoardHidden"
        val NotificationOrientationChanged = "NotificationOrientationChanged"
        private var instence: ActivityCore? = null
        fun  shareInstence(): ActivityCore?
        {
            return instence
        }

    }


}