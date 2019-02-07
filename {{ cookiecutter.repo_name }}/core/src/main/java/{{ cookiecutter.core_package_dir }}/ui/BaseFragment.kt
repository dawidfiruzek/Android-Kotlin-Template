package {{ cookiecutter.core_package_name }}.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import android.arch.lifecycle.ViewModel
import {{ cookiecutter.core_package_name }}.utils.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel, DB: ViewDataBinding> : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory<VM>
    protected lateinit var viewModel: VM
    protected abstract val viewModelClassToken: Class<VM>
    private lateinit var dataBinding: DB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClassToken)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = getBinding(inflater, container)
        dataBinding.setLifecycleOwner(this)
        return dataBinding.root
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): DB

    @CallSuper
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
