package pooq.com.pooqglobal

import android.os.Bundle
import android.util.Log
import lib.page.PageActivity
import lib.page.PageFragment
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : PageActivity<PageID>()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pagePresenter?.pageChange(PageID.MAIN,true)
        //for (i in 0..100) jjp(60000.0)

        jjp2(60000.0)

        /*&
        val a =  arrayOf(intArrayOf(5,3),intArrayOf(11,5),intArrayOf(13,3),intArrayOf(3,5),intArrayOf(6,1))
        jj(a)
        */
    }



    override fun <T> getPageByID(id:T): PageFragment
    {
        var page: PageFragment? = null
        when(id)
        {
            PageID.MAIN -> {page = PageMain()}
            PageID.SUB -> {page = PageSub()}
        }
        return page?.let {it} ?: super.getPageByID(id)
    }

    override fun <T> getPopupByID(id:T): PageFragment
    {
        var popup: PageFragment? = null
        when(id)
        {
            PageID.TEST -> {popup = PopupTest()}
        }
        return popup?.let {it} ?: super.getPopupByID(id)
    }

    fun jj(nodeinfo: Array<IntArray>)
    {
        var answer = arrayOf<IntArray>()
        val front = IntArray(nodeinfo.size)
        val back = IntArray(nodeinfo.size)
        var idx = 0
        val sortedList = nodeinfo.mapIndexed { index, ints -> Pair(index,ints)}
                .sortedWith (compareBy({it.second.last()},{it.second.first()}))

        var cy = 0
        for(value in sortedList)
        {
            var v = value.second
            var y = v.last()
            var x = v.first()
            var idx = value.first
            if(cy != y)
            {

            }

        }
        Log.i("JJP",sortedList.toString())


    }


    fun jjp(n:Double)
    {
        var len = Math.floor(n/2).toInt()
        var sum:Double = 1.0
        var o = 0
        for (i in 1..len)
        {
            var sn = (n -(i*2)) + i
            var s2 = i
            var k = 1.0
            while(s2>0)
            {
                k *= sn/s2
                s2 --
                sn --
                o++
            }
            sum += k

        }
        Log.i("JJP","sum=$sum n=$n o=$o")
    }

    fun jjp2(n:Double)
    {
        var len = n.toInt()
        var sum:Double = 1.0
        var o = 0
        var stack = Stack<Double>()

        for (i in 1..len)
        {
            var k = 1.0
            var p = 0.0
            if(stack.size <= 2) stack.push(1.0)
            else
            {
                k = 0.0
                p = stack.pop()
                k += p
                k += stack.pop()
                stack.push(p)
                stack.push(k)
            }
            sum = k
        }
        Log.i("JJP2","sum=$sum n=$n o=$o")
    }
}

enum class PageID
{
    MAIN,SUB,TEST
}



