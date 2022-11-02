package com.brunowcnascimento.projectpoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import com.brunowcnascimento.projectpoc.databinding.ActivityMainBinding
import com.brunowcnascimento.projectpoc.feature.font_size.FontSizeActivity
import com.brunowcnascimento.projectpoc.setup_main.MainAdapter

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var mainAdapter: MainAdapter? = null
    private val listActivities = listOf<String>(
        FontSizeActivity.newInstance().javaClass.name
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupAdapter()
        setupRecyclerView()

        mainAdapter?.submitList(listActivities)
    }


    private fun setupRecyclerView() {
        binding?.recyclerMain?.apply {
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }

    private fun setupAdapter() {
        mainAdapter = MainAdapter { nameActivity ->
            verificationActivity(nameActivity)
        }
    }

    private fun verificationActivity(name: String) {
        when(name) {
            FontSizeActivity.newInstance().javaClass.name -> {
                Toast.makeText(this, "Name: $name", Toast.LENGTH_LONG).show()
            }
            else -> Toast.makeText(this, "Activity não cadastrada", Toast.LENGTH_LONG).show()
        }
    }
}