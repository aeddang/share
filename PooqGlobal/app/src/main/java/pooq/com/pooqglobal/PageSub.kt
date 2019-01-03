package pooq.com.pooqglobal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import lib.page.PageFragment
import lib.page.PagePresenter

class PageSub : PageFragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.page_sub,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        var button = view?.findViewById<Button>(R.id.button) as Button?
        button?.setOnClickListener {  PagePresenter.getInstence<PageID>()?.openPopup(PageID.TEST)}

    }
}