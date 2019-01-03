package lib.core

class PageObject(var pageID: Int)
{
    var info: Map<String, Any>? = null
    var dr: Int = 2
    var isHistory: Boolean = true
    var isHome: Boolean = false
    var isGestureUsed: Boolean = true

    init
    {

    }

}