package lib.view

import android.graphics.Point
import android.view.MotionEvent
import java.util.*

class Gesture(var delegate: GestureDelegate?, private val isVertical: Boolean, private val isHorizontal: Boolean)
{

    var startPosA: ArrayList<Point>
    var changePosA: ArrayList<Point>
    var movePosA: ArrayList<Point>
    private var moveType = -1
    private var isEventStart: Boolean = false
    private var startTime: Long = 0
    private var endTime: Long = 0
    var startRotate: Float = 0.toFloat()
    var startDistance: Float = 0.toFloat()
    private val changeRotate = 30f
    private val longTime: Long = 2
    private val changeMin = 10
    private val changeMax = 50

    interface GestureDelegate
    {
        fun stateChange(g: Gesture, e: String){}
        fun rotateChange(g: Gesture, rotate: Float){}
        fun pinchChange(g: Gesture, dist: Float){}
        fun gestureChange(g: Gesture, e: String){}
        fun gestureComplete(g: Gesture, e: String){}
    }

    init
    {

        moveType = -1
        startPosA = ArrayList()
        changePosA = ArrayList()
        movePosA = ArrayList()
        startTime = 0
        startRotate = 0f
        startDistance = 0f
        endTime = 0

    }

    fun adjustEvent(event: MotionEvent): Boolean
    {

        val action = event.action

        val locations = ArrayList<Point>()
        var location: Point
        var pointerIndex: Int
        var mActivePointerId: Int

        for (i in 0 until event.pointerCount)
        {
            mActivePointerId = event.getPointerId(i)
            pointerIndex = event.findPointerIndex(mActivePointerId)
            location = Point(Math.floor(event.getX(pointerIndex).toDouble()).toInt(), Math.floor(event.getY(pointerIndex).toDouble()).toInt())
            locations.add(location)

        }
        var trigger = true
        when (action)
        {
            MotionEvent.ACTION_DOWN -> startEvent(locations)
            MotionEvent.ACTION_MOVE -> trigger = moveEvent(locations)
            MotionEvent.ACTION_UP -> endEvent(true)
        }
        return trigger

    }

    @Synchronized
    private fun startEvent(locations: ArrayList<Point>)
    {
        isEventStart = true
        moveType = -1
        startPosA = locations
        changePosA = ArrayList()

        for (i in locations.indices) changePosA.add(Point(0, 0))

        val now = Date()
        startTime = now.time
        startDistance = 0f
        startRotate = 0f
        delegate?.stateChange(this, START)

    }

    @Synchronized
    private fun moveEvent(locations: ArrayList<Point>): Boolean
    {
        var trigger = true
        if (isEventStart == false)
        {
            startEvent(locations)
            return trigger
        }
        movePosA = ArrayList()
        val len = locations.size
        var location: Point
        for (i in 0 until len)
        {
            location = locations[i]
            movePosA.add(Point(location.x, location.y))
        }
        var start: Point
        var change: Point

        checkEvent(false)
        if (len == startPosA.size)
        {
            for (i in 0 until len) {
                location = locations[i]
                movePosA.add(Point(location.x, location.y))
                start = startPosA[i]
                change = changePosA[i]
                change.x = location.x - start.x
                change.y = location.y - start.y

            }
            change = changePosA[0]
            // Log.i("t","change.x "+change.x+" -  change.y "+change.y);
            if (Math.abs(change.x) > Math.abs(change.y))
            {
                if (isHorizontal == true) trigger = false
                moveType = 1
                if (isHorizontal == true && len == 1) delegate?.stateChange(this, MOVE_H)

            }
            else if (Math.abs(change.y) > Math.abs(change.x))
            {
                if (isVertical == true) trigger = false
                moveType = 0

                if (isVertical == true && len == 1) delegate?.stateChange(this, MOVE_V)
            }
            delegate?.stateChange(this, MOVE)
        }
        else
        {
            delegate?.stateChange(this, CANCLE)
            endEvent(false)
        }
        return trigger
    }

    @Synchronized
    private fun endEvent(isComplete: Boolean) {
        if (isEventStart == false) return
        val now = Date()
        endTime = now.time
        checkEvent(isComplete)
        delegate?.stateChange(this, END)
        isEventStart = false

    }

    @Synchronized
    private fun checkEvent(isComplete: Boolean)
    {
        if (startPosA.size != movePosA.size && isComplete == false) return
        val spdMD = 100f
        var moveMD = 0f
        val start: Point
        val move: Point
        val change: Point

        var gestureTime = 0f
        if (isComplete == true) gestureTime = (endTime - startTime) / spdMD

        if (startPosA.size == 1)
        {
            change = changePosA[0]
            if (isComplete == true)
            {
                if (gestureTime >= longTime)
                {
                    if (Math.abs(change.x) < changeMin && Math.abs(change.y) < changeMin) delegate?.gestureComplete(this, LONG_TOUCH)
                }


                if (moveType == 1)
                {
                    moveMD = change.x / gestureTime
                    if (moveMD > changeMax)
                    {
                        delegate?.gestureComplete(this, PAN_RIGHT)

                    }
                    else if (moveMD < -changeMax)
                    {
                        delegate?.gestureComplete(this, PAN_LEFT)
                    }
                }

                if (moveType == 0)
                {
                    moveMD = change.y / gestureTime
                    if (moveMD > changeMax)
                    {
                        delegate?.gestureComplete(this, PAN_DOWN)

                    } else if (moveMD < -changeMax)
                    {
                        delegate?.gestureComplete(this, PAN_UP)
                    }
                }
                if (Math.abs(change.x) < changeMin && Math.abs(change.y) < changeMin) delegate?.gestureComplete(this, TOUCH)

            }
            else
            {
                if (Math.abs(change.x) > changeMin || Math.abs(change.y) > changeMin) delegate?.stateChange(this, PAN)
            }
        }
        else if (startPosA.size == 2)
        {
            val start2: Point
            val move2: Point
            try {
                change = changePosA[0]
                start = startPosA[0]
                move = movePosA[0]
                start2 = startPosA[1]
                move2 = movePosA[1]
            } catch (e: IndexOutOfBoundsException)
            {
                return
            }

            if (startDistance == 0f) startDistance = Math.sqrt(((Math.abs(start.x - start2.x) xor 2) + (Math.abs(start.y - start2.y) xor 2)).toDouble()).toFloat()

            val startDist = startDistance
            val moveDist = Math.sqrt(((Math.abs(move.x - move2.x) xor 2) + (Math.abs(move.y - move2.y) xor 2)).toDouble()).toFloat()
            val dist = moveDist - startDist

            var rotate = 0f
            var w = 0f
            var h = 0f
            if (startRotate == 0f)
            {
                w = (start.x - start2.x).toFloat()
                h = (start.y - start2.y).toFloat()
                startRotate = (Math.atan2(h.toDouble(), w.toDouble()) / Math.PI * 360).toFloat()
            }
            w = (move.x - move2.x).toFloat()
            h = (move.y - move2.y).toFloat()
            rotate = (Math.atan2(h.toDouble(), w.toDouble()) / Math.PI * 360).toFloat()
            delegate?.rotateChange(this, rotate)

            if (isComplete == true && Math.abs(startRotate - rotate) > changeRotate) delegate?.gestureComplete(this, PINCH_ROTATE)

            if (isComplete == true)
            {


                if (Math.abs(dist) > changeMin)
                {
                    if (dist > 0)
                    {
                        delegate?.gestureComplete(this, PINCH_OUT)
                    }
                    else
                    {
                        delegate?.gestureComplete(this, PINCH_IN)
                    }

                }
                else
                {

                    if (moveType == 1)
                    {
                        moveMD = change.x / gestureTime
                        if (moveMD > changeMax)
                        {
                            delegate?.gestureComplete(this, PINCH_RIGHT)
                        }
                        else if (moveMD < -changeMax)
                        {
                            delegate?.gestureComplete(this, PINCH_LEFT)
                        }
                    }
                    if (this.moveType == 0)
                    {
                        moveMD = change.y / gestureTime
                        if (moveMD > changeMax)
                        {
                            delegate?.gestureComplete(this, PINCH_DOWN)
                        }
                        else if (moveMD < -changeMax)
                        {
                            delegate?.gestureComplete(this, PINCH_UP)
                        }
                    }
                }


            }
            else
            {
                if (Math.abs(dist) > changeMin)
                {
                    delegate?.pinchChange(this, dist)
                }
                else
                {
                    delegate?.stateChange(this, PINCH_MOVE)
                }
            }

        }


    }

    companion object
    {
        val NONE = "NONE"
        val START = "START"
        val MOVE = "MOVE"
        val MOVE_V = "MOVE_V"
        val MOVE_H = "MOVE_H"
        val END = "END"
        val CANCLE = "CANCLE"

        val LONG_TOUCH = "LONG_TOUCH"
        val TOUCH = "TOUCH"

        val PAN = "PAN"
        val PAN_RIGHT = "PAN_RIGHT"
        val PAN_LEFT = "PAN_LEFT"
        val PAN_UP = "PAN_UP"
        val PAN_DOWN = "PAN_DOWN"

        val PINCH_MOVE = "PINCH_MOVE"
        val PINCH_RIGHT = "PINCH_RIGHT"
        val PINCH_LEFT = "PINCH_LEFT"
        val PINCH_UP = "PINCH_UP"
        val PINCH_DOWN = "PINCH_DOWN"

        //public static final String PINCH_GESTURE="PINCH_GESTURE";
        val PINCH_IN = "PINCH_IN"
        val PINCH_OUT = "PINCH_OUT"
        val PINCH_ROTATE = "PINCH_ROTATE"
    }
}