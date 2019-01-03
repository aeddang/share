package lib.observer

import android.util.Log
import java.util.ArrayList
import java.util.HashMap

open class ObserverController : Subject
{

    private val observers: MutableMap<String, ArrayList<Observer>>

    init
    {
        Log.i("ObserverController", "ObserverController init")
        this.observers = HashMap()
    }

    override fun registerObserver(o: Observer, key: String)
    {

        var group: ArrayList<Observer>? = this.observers[key]
        if (group == null)
        {
            group = ArrayList()
            this.observers[key] = group
        }
        group.add(o)
    }

    override fun removeObserver(o: Observer)
    {

        val allkeys = this.observers.keys
        for (key in allkeys) this.removeObserver(o, key)

    }

    override fun removeObserver(o: Observer, key: String)
    {
        val group = this.observers[key]
        if (group != null)
            for (observer in group) if (observer === o) group.remove(o)

    }

    override fun notifyPost(key: String)
    {
        notifyPost(key,null,null)
    }

    override fun notifyPost(key: String, value: Any?)
    {
        notifyPost(key,value,null)
    }

    override fun notifyPost(key: String, value: Any?, userData: Map<String, Any>?)
    {

        var arrayList = this.observers[key]
        val group = if (arrayList != null) arrayList else return
        for (observer in group) observer.notification(key, value, userData)
    }

    companion object
    {
        private var instence: ObserverController? = null;

        fun shareInstence(): ObserverController
        {
            if (instence == null) instence = ObserverController()
            return instence!!
        }
    }
}