package lib.observer

interface Subject
{
    fun registerObserver(o: Observer, key: String)
    fun removeObserver(o: Observer)
    fun removeObserver(o: Observer, key: String)
    fun notifyPost(key: String, value: Any?, userData: Map<String, Any>?)

}