package client.paycial.com.sam4sexample.util.sam4sPrinter

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sam4s.io.OnConnectListener
import com.sam4s.printer.Sam4sBuilder
import com.sam4s.printer.Sam4sFinder
import com.sam4s.printer.Sam4sPrint
import kotlinx.coroutines.*


class PrintUtil(
    private val context: Context
) : MutableLiveData<Boolean>(), IPrint {

    private lateinit var printer: Sam4sPrint
    private val finder = Sam4sFinder()

    override fun onActive() {
        super.onActive()
        GlobalScope.launch { search() }
        Log.d(TAG, "onActive")
    }

    override fun onInactive() {
        super.onInactive()
        GlobalScope.launch { disConnect() }
        Log.d(TAG, "onInactive")
    }

    //검색
    override suspend fun search() = withContext(Dispatchers.Default) {
        postValue(false)

        var deferred: Deferred<String>? = null

        try {
            deferred = async {
                Log.d(TAG, "searching...")
                finder.startSearch(context, Sam4sFinder.DEVTYPE_ETHERNET)
                delay(500)
                if (finder.result == null) {
                    ""
                } else {
                    finder.result.first()
                }
            }
        } catch (e: Exception) {
            Log.d("error", e.message)
        }

        val address = deferred?.await()
        finder.stopSearch()
        Log.d(TAG, "ip address $address")
        delay(500)
        connect(address)
    }

    private val connectListener: OnConnectListener = object : OnConnectListener {
        override fun OnPrnClose() {
            postValue(false)
            Log.d(TAG, "printer closed")
        }

        override fun OnPrnConnect(isConnected: Boolean) {
            postValue(isConnected)
        }

        override fun OnPrnError() {
            postValue(false)
            Log.d(TAG, "printer error")
        }
    }

    //연결
    override suspend fun connect(address: String?) {
        try {
            Log.d(TAG, "connecting...")
            printer = Sam4sPrint()
            printer.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, address)
            printer.setConnectListener(connectListener)
            delay(500)

            Log.d(
                TAG,
                "connected\n" +
                        "result is ->  " +
                        "\nprinter name : ${printer.printerName}, " +
                        "\nprinter status : ${printer.printerStatus}"
            )

        } catch (e: Exception) {
            Log.d(TAG, "failed connection")
            e.printStackTrace()
        }
    }

    //인쇄
    override fun print(string: String?, model: PrintImageModel) {
        val builder = Sam4sBuilder(GIANT_100, Sam4sBuilder.LANG_KO)
        try {
            builder.apply {
                bSharpenEnable = true
                addTextAlign(Sam4sBuilder.ALIGN_CENTER)
                model.apply { addImage(image, width, height) }
                addCut(Sam4sBuilder.CUT_FEED)
            }
            printer.sendData(builder)
            builder.clearCommandBuffer()
            Log.d(TAG, "print complete")
        } catch (e: Exception) {
            Log.d(TAG, "failed printing")
            e.printStackTrace()
        }
    }

    override suspend fun retryConnection() {
        printer.closePrinter()
        delay(1000)
        search()
    }

    override fun disConnect() = printer.closePrinter()

    companion object {
        const val TAG = "PrintUtil"
        const val GIANT_100 = "GIANT-100"
    }
}