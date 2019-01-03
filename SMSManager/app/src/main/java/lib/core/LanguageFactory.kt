package lib.core

import android.content.SharedPreferences

class LanguageFactory
{
    private val LANG_KEY = "LANG_KEY"
    private var type = KOR
    private val shared: SharedPreferences?

    var languageType: String
        get() = shared!!.getString(LANG_KEY, KOR)
        set(_type)
        {
            type = _type
            val editor = shared!!.edit()
            editor.putString(LANG_KEY, type)
            editor.commit()
        }

    init
    {
        instence = this
        shared = ActivityCore.shareInstence()?.getSharedPreferences(ActivityCore.PREFS_NAME, 0)
        type = languageType
    }

    fun getResorceID(value: String): Int
    {
        if(ActivityCore.shareInstence() == null) return -1
        return ActivityCore.shareInstence()!!.getResources().getIdentifier(value + "_" + type, "string",
                ActivityCore.shareInstence()!!.getPackageName())
    }

    fun getResorceString(value: String): String
    {
        if(ActivityCore.shareInstence() == null) return ""
        val rid = getResorceID(value)
        return ActivityCore.shareInstence()!!.getResources().getString(rid)

    }

    companion object
    {

        private var instence: LanguageFactory? = null
        val ENG = "eng"
        val KOR = "kor"

        fun shareInstence(): LanguageFactory
        {
            if (instence == null) instence = LanguageFactory()
            return instence!!
        }
    }


}