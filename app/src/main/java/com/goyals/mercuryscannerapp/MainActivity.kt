package com.goyals.mercuryscannerapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import com.goyals.mercuryscannerapp.ui.EditFormActivity
import com.goyals.mercuryscannerapp.ui.scanner.AadharScanActivity
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.fab
import kotlinx.android.synthetic.main.app_bar_main.fab_other_id
import kotlinx.android.synthetic.main.app_bar_main.fab_scan_id
import kotlinx.android.synthetic.main.app_bar_main.rl_fabs
import kotlinx.android.synthetic.main.app_bar_main.toolbar

class MainActivity : AppCompatActivity() {
  private lateinit var appBarConfiguration: AppBarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initViews()
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment)
    return navController.navigateUp(
      appBarConfiguration) || super.onSupportNavigateUp()
  }

  private fun initViews() {
    setSupportActionBar(toolbar)
    val navController = findNavController(R.id.nav_host_fragment)
    appBarConfiguration = AppBarConfiguration(
      setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawer_layout)
    setupActionBarWithNavController(navController, appBarConfiguration)
    nav_view.setupWithNavController(navController)
    handleFabs()
  }

  private fun handleFabs() {
    fab.setOnClickListener {
      rl_fabs.visibility = View.VISIBLE
    }
    fab_scan_id.setOnClickListener {
      rl_fabs.visibility = View.GONE
      startActivity(Intent(this, AadharScanActivity::class.java))
    }
    fab_other_id.setOnClickListener {
      rl_fabs.visibility = View.GONE
      startActivity(Intent(this, EditFormActivity::class.java))
    }
  }
}