package lib.observer

interface Observer
{
    fun notification(notify: String, value: Any?, userData: Map<String, Any>?)
}