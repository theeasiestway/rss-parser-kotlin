package com.theeasiestway.rssparserkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.util.Log
import com.theeasiestway.parser.RssParser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "RssParserActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.movementMethod = ScrollingMovementMethod.getInstance()


        Thread {
            try {
                var rssParser = RssParser()
                //  var rssChannel = rssParser.parse("https://www.techrepublic.com/rssfeeds/downloads/")
                //  var rssChannel = rssParser.parse("https://www.techrepublic.com/rssfeeds/downloads/", "<li>|</li>|<ul>|</ul>|<p>|</p>|<br>", "")
                  var rssChannel = rssParser.parse("http://www.aif.ru/rss/all.php")
                //  var rssChannel = rssParser.parse("http://www.aif.ru/rss/all.php", "<li>|</li>|<ul>|</ul>|<p>|</p>|<br>", "")
                //  var rssChannel = rssParser.parse("https://www.vesti.ru/section.rss?cid=8")
                //  var rssChannel = rssParser.parse("https://www.vesti.ru/section.rss?cid=8", "<li>|</li>|<ul>|</ul>|<p>|</p>|<br>", "")
                //  var rssChannel = rssParser.parse("")
                var rssString: String? = rssChannel?.toString()
                if (rssString != null) runOnUiThread { textView.text = rssString }
                else runOnUiThread { textView.text = "rssString is null" }
                printString(rssString)
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread { textView.text = "Error while parsing rss: $e" }
            }
        }.start()
    }

    private fun printString(string: String?) {
        if (string == null) Log.d(TAG, "rssString is null")
        else if (string.trim().length <= 999) Log.d(TAG, string)
        else {
            var strLength = string.length
            var loopCount = strLength / 1000
            var pos = 0
            for (i in 0 until (loopCount + 1)) {
                var subString = if (i + 1 == loopCount + 1) string.substring(pos, strLength) else string.substring(pos, pos + 1000)
                Log.d(TAG, subString)
                pos += 1000
            }
        }
    }
}
