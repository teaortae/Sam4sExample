package client.paycial.com.sam4sexample.main

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import client.paycial.com.sam4sexample.R
import client.paycial.com.sam4sexample.databinding.ActivityMainBinding
import client.paycial.com.sam4sexample.databinding.ReciptOrderedBinding
import client.paycial.com.sam4sexample.util.createViewModelWithFactory
import client.paycial.com.sam4sexample.util.getBitmapFromView
import client.paycial.com.sam4sexample.util.sam4sPrinter.PrintImageModel
import client.paycial.com.sam4sexample.viewModel.PrintViewModel
import client.paycial.com.sam4sexample.viewModelFactory.PrintViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding, PrintViewModel, PrintViewModelFactory>(),
    View.OnClickListener {

    override val layoutResource = R.layout.activity_main

    override fun initData() {
    }

    override fun initView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)// 화면 켜짐 유지

        viewModelFactory = PrintViewModelFactory(this)
        viewModel = createViewModelWithFactory(this, viewModelFactory!!)

        binding.apply {
            vm = viewModel
            listener = this@MainActivity
        }
    }

    override fun initEvent() {
        viewModel?.printUtil?.observe(this, Observer {
            viewModel?.setPrintModel(it)
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.print -> {
                val bitmap = setReceiptImage().root.getBitmapFromView()
                
                viewModel?.print(
                    "",
                    PrintImageModel(bitmap, bitmap.height / 2, bitmap.width / 2)
                )
            }

            else -> GlobalScope.launch { viewModel?.reconnect() }
        }

    }

    private fun setReceiptImage(): ReciptOrderedBinding {
        val textSampleBinding = ReciptOrderedBinding.inflate(LayoutInflater.from(this), null, false)

        textSampleBinding.root.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
        )
        return textSampleBinding
    }
}


