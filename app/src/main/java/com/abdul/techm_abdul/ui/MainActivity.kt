package com.abdul.techm_abdul.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.abdul.techm_abdul.R
import com.abdul.techm_abdul.viewmodels.PictureViewModel
import com.abdul.techm_abdul.viewmodels.UserViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val userViewModel: UserViewModel by viewModels()
        val pictureViewModel: PictureViewModel by viewModels()

    }
}