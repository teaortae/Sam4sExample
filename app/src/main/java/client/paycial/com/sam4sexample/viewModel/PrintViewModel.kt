package client.paycial.com.sam4sexample.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import client.paycial.com.sam4sexample.model.PrinterModel
import client.paycial.com.sam4sexample.util.sam4sPrinter.PrintImageModel
import client.paycial.com.sam4sexample.util.sam4sPrinter.PrintUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PrintViewModel(context: Context) : ViewModel() {
    private var isConnected = false
    private var model = PrinterModel()

    val printUtil = PrintUtil(context)

    fun setPrintModel(isConnected: Boolean) {
        this.isConnected = isConnected
        model.notifyChange()
    }

    fun getPrintModel(): PrinterModel {
        model = PrinterModel(if (isConnected) "인쇄" else "연결중...", isConnected)
        model.notifyChange()
        return model
    }

    fun print(string: String, model: PrintImageModel) {
        GlobalScope.launch {
            printUtil.print(string, model)
        }
    }

    suspend fun reconnect() = printUtil.retryConnection()
}