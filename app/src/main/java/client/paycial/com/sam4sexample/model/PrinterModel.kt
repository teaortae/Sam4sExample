package client.paycial.com.sam4sexample.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class PrinterModel(
    @Bindable var printerText: String = "connecting...",
    @Bindable var isConnected: Boolean = false
) : BaseObservable()