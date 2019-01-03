package pooq.com.pooqglobal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import lib.page.PageGestureFragment
import lib.page.PagePresenter
import lib.page.PageGestureView


class PopupTest : PageGestureFragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.popup_test,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        backgroundView = view?.findViewById(R.id.bg) as View?
        gestureView = view?.findViewById(R.id.body) as PageGestureView?
        contentsView = view?.findViewById(R.id.contents) as View?
        var button = view?.findViewById(R.id.button) as Button?
        button?.setOnClickListener {  PagePresenter.getInstence<PageID>()?.closePopup(pageID as PageID)}
        super.onViewCreated(view, savedInstanceState)
    }


}