package client.paycial.com.sam4sexample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<T : ViewDataBinding, V : ViewModel, F : ViewModelProvider.Factory> : AppCompatActivity() {
    abstract fun initData()
    abstract fun initView()
    abstract fun initEvent()
    abstract val layoutResource: Int

    lateinit var binding: T
    var viewModel: V? = null
    var viewModelFactory: F? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResource)
        initData()
        initView()
        initEvent()
    }
}