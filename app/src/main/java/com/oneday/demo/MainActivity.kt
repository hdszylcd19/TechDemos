package com.oneday.demo

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.oneday.demo.bean.User
import com.oneday.demo.unsafe.UnsafeProxy
import com.oneday.demo.viewmodel.MyViewModel

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.simpleName
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val obj = UnsafeProxy.allocateInstance(User::class.java)
        val nameField = obj.javaClass.getDeclaredField("userName")
        val ageField = obj.javaClass.getDeclaredField("age")
        val nameOffset = UnsafeProxy.objectFieldOffset(nameField)
        val ageOffset = UnsafeProxy.objectFieldOffset(ageField)
        println("nameOffset = [$nameOffset]; ageOffset = [$ageOffset]")
        println("obj = [${obj}]")
        val nameSuccess = UnsafeProxy.compareAndSwapObject(obj, nameOffset, null, "OneDay")
        val ageSuccess = UnsafeProxy.compareAndSwapInt(obj, ageOffset, 0, 32)
        println("nameSuccess = [$nameSuccess]; ageSuccess = [$ageSuccess]; obj = [${obj}]")

        val model = ViewModelProviders.of(this)[MyViewModel::class.java]
        model.getStrs().observe(this, Observer<List<String>> { strs ->
            for (str in strs) {
                Log.i(TAG, str)
            }
        })

        testLiveData("onCreate()")
    }

    override fun onStart() {
        super.onStart()
        testLiveData("onStart()")
    }

    override fun onRestart() {
        super.onRestart()
        testLiveData("onRestart()")
    }

    override fun onResume() {
        super.onResume()
        testLiveData("onResume()")
    }

    override fun onPause() {
        super.onPause()
        testLiveData("onPause()")
    }

    override fun onStop() {
        super.onStop()
        testLiveData("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        testLiveData("onDestroy()")
    }

    private fun testLiveData(str: String) {
        Log.i(TAG, str)
        val model = ViewModelProviders.of(this)[MyViewModel::class.java]
        val arrayList = ArrayList<String>()
        arrayList.add("LiveData : $str")
        (model.getStrs() as MutableLiveData).value = arrayList
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "action_settings", Toast.LENGTH_LONG).show()
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}


