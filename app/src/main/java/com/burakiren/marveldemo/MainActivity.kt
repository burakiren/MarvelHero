package com.burakiren.marveldemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host = NavHostFragment.create(R.navigation.main_activity_nav_graph)
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, host)
            .setPrimaryNavigationFragment(host).commit()

    }
}