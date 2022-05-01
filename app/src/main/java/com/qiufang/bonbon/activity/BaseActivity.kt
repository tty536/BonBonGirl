package com.qiufang.bonbon.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewbinding.ViewBinding
import com.qiufang.bonbon.R
import com.qiufang.bonbon.databinding.ActivityBaseBinding

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBaseBinding

    //在BaseActivity中实现子Activity
    abstract val bind:ViewBinding?
    protected inline fun <reified T> bindLayout() : Lazy<T> where T:ViewBinding{
        return lazy {
            val clazz :Class<T> = T::class.java
            val method = clazz.getMethod("inflate",LayoutInflater::class.java)
            method.invoke(null,layoutInflater) as  T
        }
    }

    protected fun <T> T.logE() = Log.e("",this.toString())
    /*protected fun <T> T.toast() = Toast.makeText(this@BaseActivity,this.toString())

    protected fun View.click(action:)*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind?.root.apply {
            setContentView(this)
        }
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_base)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_base)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}