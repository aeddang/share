package lib.core

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import lib.CustomTimer

open class ViewCore(context: Context, pageInfo: PageObject = PageObject(0)) : FrameLayout(context), CustomTimer.TimerDelegate
{
    open var btnBack: Button? = null
    open var mainActivity: ActivityCore? = null
    private var isMoveInit: Boolean = false
    private var resizeTimer: CustomTimer? = null
    open var isDirectBack: Boolean = true
    var pageInfo: PageObject = pageInfo

    init
    {
        mainActivity = ActivityCore.shareInstence()
        this.isClickable = true
        // View.inflate(context, R.layout.view_main,this);

    }

    open fun setResizeHendler()
    {
        removeResizeTimer()
        resizeTimer = CustomTimer(100, 1, this)
        resizeTimer!!.resetTimer()
    }

    override fun onComplete(timer: CustomTimer)
    {
        resize()
    }

    open fun removeResizeTimer()
    {
        resizeTimer?.removeTimer()
        resizeTimer = null
    }

    fun initCore()
    {
        //btnBack=(Button) findViewById(R.id._btnBack);
        doInit()
    }

    fun movedInit()
    {
        if (isMoveInit == true) return
        isMoveInit = true
        btnBack?.setOnClickListener { ActivityCore.shareInstence()?.changeViewBack() }
        doMovedInit()
    }


    fun directBack(): Boolean
    {
        doDirectBack()
        return isDirectBack
    }

    fun activityResult(requestCode: Int, data: Intent) {

        doActivityResult(requestCode, data)
    }

    fun configurationChanged(newConfig: Configuration) {

        doConfigurationChanged(newConfig)
        resizeTimer?.timerStop()
        resizeTimer?.resetTimer()
        resizeTimer?.timerStart()
    }

    open fun setBodySizeChanged(proposedWidth: Int, proposedheight: Int) {
        doSetBodySizeChanged(proposedWidth, proposedheight)
    }

    fun removeViewInit()
    {
        doRemoveInit()
    }

    fun removeViewCore()
    {
        removeResizeTimer()
        doRemove()
        mainActivity = null
    }

    fun resize()
    {
        //val w = this.width
        //val h = this.height
        // Log.i("", "ViewCore Resize w : " + w + " h : " + h);
        doResize()
    }

    fun onUpdate()
    {
        doUpdate()
    }

    fun onPause()
    {
        doPause()
    }

    fun onResume()
    {
        doResume()
    }

    open fun doUpdate() {

        // Log.i("","ViewCore Resize!!");
    }

    open fun doResize() {

        // Log.i("","ViewCore Resize!!");
    }

    open fun doSetBodySizeChanged(proposedWidth: Int, proposedheight: Int) {

    }

    open fun doActivityResult(requestCode: Int, data: Intent) {

    }

    open fun doConfigurationChanged(newConfig: Configuration) {

    }

    open fun doInit() {

    }

    open fun doMovedInit() {

    }

    open fun doDirectBack() {

    }

    open fun doRemoveInit() {

    }

    open fun doRemove() {

    }

    open fun doPause() {

    }

    open fun doResume() {

    }
}