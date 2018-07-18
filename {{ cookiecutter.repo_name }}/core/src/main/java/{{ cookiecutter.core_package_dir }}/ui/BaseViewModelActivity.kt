package {{ cookiecutter.core_package_name }}.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import pl.dawidfiruzek.core.utils.ViewModelFactory
import javax.inject.Inject

abstract class BaseViewModelActivity<VM : ViewModel, DB: ViewDataBinding> : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory<VM>
    protected lateinit var viewModel: VM
    protected lateinit var dataBinding: DB
    protected abstract val viewModelClassToken: Class<VM>
    protected abstract val layoutId: Int

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClassToken)
        dataBinding = DataBindingUtil.setContentView(this, layoutId)
        dataBinding.setLifecycleOwner(this)
    }
}