package lib.page

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator



open class PageFragment:Fragment()
{
    enum class PageType
    {
        DEFAULT,BACK,POPUP
    }
    protected var animationDuration = 200L
    private var animationHandler: Handler = Handler()
    private var viewCreateRunnable: Runnable = Runnable {onCreateAnimation()}
    protected var animationCreateRunnable: Runnable = Runnable {didCreateAnimation()}
    protected var animationDestroyRunnable: Runnable = Runnable {didDestroyAnimation()}

    var pageID:Any? = null
        internal set

    internal var delegate:Delegate? = null
    internal var pageType = PageType.DEFAULT

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(PagePresenter.TAG,"onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        willCreateAnimation()
        animationHandler.post(viewCreateRunnable)
    }

    open fun willCreateAnimation()
    {
        val pageActivity = activity as PageActivity<*>
        pageActivity?.let {
            val size = it.getPageAreaSize()
            var posX = 0f
            var posY = 0f
            when (pageType) {
                PageType.DEFAULT -> posX = -size.first
                PageType.BACK -> posX = size.first
                PageType.POPUP -> posY = size.second
            }
            view?.translationX = posX
            view?.translationY = posY
        }
    }

    open fun onCreateAnimation():Long
    {
        var interpolator:Interpolator? = null
        when (pageType) {
            PageType.DEFAULT -> interpolator = LinearInterpolator()
            PageType.BACK -> interpolator = LinearInterpolator()
            PageType.POPUP -> interpolator = DecelerateInterpolator()

        }
        view?.let {
            it.animate()
                    .translationX(0f)
                    .translationY(0f)
                    .setInterpolator(interpolator)
                    .setDuration(animationDuration)
                    .withEndAction(animationCreateRunnable)
                    .start()
        }
        delegate?.onCreateAnimation(this)
        return animationDuration
    }
    open protected fun didCreateAnimation(){}
    open fun onDestroyAnimation():Long
    {
         view?.let {
            var posX = 0f
            var posY = 0f
            var interpolator:Interpolator? = null
            when (pageType) {
                PageType.DEFAULT ->
                {
                    posX = it.width.toFloat()
                    interpolator = LinearInterpolator()
                }
                PageType.BACK ->
                {
                    posX = -it.width.toFloat()
                    interpolator = LinearInterpolator()
                }
                PageType.POPUP ->
                {
                    posY = it.height.toFloat()
                    interpolator = DecelerateInterpolator()
                }
            }
            it.animate()
                .translationX(posX)
                .translationY(posY)
                .setInterpolator(interpolator)
                .setDuration(animationDuration)
                .withEndAction(animationDestroyRunnable)
                .start()
        }
        return animationDuration
    }

    open protected fun didDestroyAnimation()
    {
        activity?.let { it.supportFragmentManager.beginTransaction().remove(this).commit() }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        view?.let{
            val parent = it.parent as ViewGroup?
            parent?.removeView(it)
        }
        delegate = null
        animationHandler.removeCallbacks(viewCreateRunnable)
        Log.d(PagePresenter.TAG,"onDestroyView")
    }

    open fun onBack():Boolean {return true}

    internal interface Delegate
    {
        fun onCreateAnimation(v:PageFragment)
    }
}