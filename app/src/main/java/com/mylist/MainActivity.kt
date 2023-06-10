package com.mylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.mylist.databinding.ActivityMainBinding
import com.mylist.fragment.Beranda
import com.mylist.fragment.Profile
import com.mylist.fragment.TambahTugas

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(binding.root)
        supportActionBar?.hide()

        replaceFragment(Beranda())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.beranda -> replaceFragment(Beranda())
                R.id.tugas -> replaceFragment(TambahTugas())
                R.id.profil -> replaceFragment(Profile())

                else ->{

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransient = fragmentManager.beginTransaction()
        fragmentTransient.replace(R.id.frameLayout, fragment)
        fragmentTransient.commit()
    }
}