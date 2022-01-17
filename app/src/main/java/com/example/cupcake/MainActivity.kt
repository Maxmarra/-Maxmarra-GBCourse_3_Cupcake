package com.example.cupcake

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity(R.layout.activity_main)
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //получаем доступ к содержанию nav_graph.xml
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        //отображаем содержание android:label ... - в заголовке appbar
        //This will do the following: Show a title in the app bar
        // based off of the destination's label,
        // and display the Up button whenever you're not on a top-level destination.
        setupActionBarWithNavController(navController)
    }
}

