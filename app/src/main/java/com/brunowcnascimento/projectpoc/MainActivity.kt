package com.brunowcnascimento.projectpoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brunowcnascimento.projectpoc.databinding.ActivityMainBinding
import com.brunowcnascimento.projectpoc.feature.font_size.FontSizeFontSizeActivity
import com.brunowcnascimento.projectpoc.setup_main.MainAdapter
import com.brunowcnascimento.projectpoc.setup_main.MainRecyclerDomain

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var mainAdapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupAdapter()
        setupRecyclerView()

        mainAdapter?.submitList(listActivities())

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

    private fun listActivities(): List<MainRecyclerDomain> {
        return listOf(
            MainRecyclerDomain(
                name = "Font Size",
                FontSizeFontSizeActivity.newInstance().javaClass.name
            )
        )
    }

    private fun verificationActivity(name: String) {

        when(name) {
            FontSizeFontSizeActivity.newInstance().javaClass.name -> {
                val intent = FontSizeFontSizeActivity.getIntent(this)
                startActivity(intent)
            }
            else -> Toast.makeText(this, "Activity não cadastrada", Toast.LENGTH_LONG).show()
        }
    }
}