package client.paycial.com.sam4sexample.util.sam4sPrinter

interface IPrint {
    suspend fun search()
    suspend fun connect(address: String?)
    suspend fun retryConnection()
    fun print(string: String?, model: PrintImageModel)
    fun disConnect()
}