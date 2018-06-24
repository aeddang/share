package lib

import android.os.Handler

class CustomTimer(private var time: Int, private val repeat: Int, private var delegate: TimerDelegate?) {


    private var tHandler: Handler? = null
    private var tRunnable: Runnable? = null
    var count: Int = 0

    fun timerStart()
    {
        timerStop()
        tHandler = Handler()
        val that = this
        tRunnable = Runnable {
            count++
            delegate?.onTime(that)
            if (count == repeat)
            {
                delegate?.onComplete(that)
                timerStop()
            }
            else
            {
                tHandler?.postDelayed(tRunnable, time.toLong())
            }
        }
        tHandler?.postDelayed(tRunnable, time.toLong())
    }

    fun timerStop()
    {
        tHandler?.removeCallbacks(tRunnable)
        tHandler = null
        tRunnable = null
    }

    fun resetTimer(t: Int)
    {
        count = 0
        time = t
        timerStop()
    }

    fun resetTimer()
    {
        count = 0
        timerStop()
    }

    fun removeTimer()
    {
        timerStop()
        delegate = null
    }

    interface TimerDelegate
    {
        fun onTime(timer: CustomTimer){}
        fun onComplete(timer: CustomTimer){}
    }
}