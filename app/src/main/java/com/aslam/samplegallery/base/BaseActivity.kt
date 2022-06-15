package com.aslam.samplegallery.base


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.aslam.samplegallery.di.Result
import com.aslam.samplegallery.util.DialogUtils



abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), BaseView {
    abstract fun getViewModel(): BaseViewModel?

    private lateinit var dialog: Dialog
    lateinit var binding: B
    private var originalSoftInputMode: Int? = null
    private lateinit var inputManager: InputMethodManager
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        dialog = DialogUtils.createProgressDialog(this, false)
        inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        getViewModel()?.outcomeLiveData?.observe(this, Observer {
            when (it) {
                is Result.Loading<*> -> {
                    if (it.showLoader)
                        loaderVisibility(true)
                }
                is Result.Success<*> -> loaderVisibility(false)
                is Result.Failure<*> -> loaderVisibility(false)

            }

        })
        getNavHostId()?.let {
            val navHostFragment = supportFragmentManager.findFragmentById(it) as NavHostFragment
            navController = navHostFragment.navController
        }


    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun setSoftInputMode(mode: Int) {
        originalSoftInputMode = window.attributes.softInputMode
        window.setSoftInputMode(mode)
    }

    override fun resetSoftInputMode() {
        originalSoftInputMode?.let {
            window.setSoftInputMode(it)
        }
    }

    override fun showKeyboard(editText: AppCompatEditText) {
        editText.post {
            editText.requestFocus()
            inputManager.showSoftInput(editText.rootView, InputMethodManager.SHOW_FORCED)
        }
    }

    override fun hideKeyboard() {
        this.currentFocus?.let {
            inputManager.hideSoftInputFromWindow(it.applicationWindowToken, 0)
        }
    }


    override fun loaderVisibility(visibility: Boolean) {
        if (::dialog.isInitialized) {
            if (visibility) {
                if (!dialog.isShowing)
                    dialog.show()
            } else {
                dialog.dismiss()
            }
        }
    }

    override fun showToast(message: String?) {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun navigateToDestination(direction: NavDirections) {
        if (::navController.isInitialized) {
            navController.navigate(direction)
        } else {
            throw IllegalAccessException("Nav Controller not set in activity")
        }

    }

    override fun navigateToDestination(id: Int, args: Bundle) {
        if (::navController.isInitialized) {
            navController.navigate(id, args)
        } else {
            throw IllegalAccessException("Nav Controller not set in activity")
        }

    }
}