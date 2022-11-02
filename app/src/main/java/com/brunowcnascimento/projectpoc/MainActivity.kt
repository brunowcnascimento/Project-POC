package com.brunowcnascimento.projectpoc

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.brunowcnascimento.projectpoc.utils.common.CommonGenericActivity
import com.brunowcnascimento.projectpoc.databinding.ActivityMainBinding
import com.brunowcnascimento.projectpoc.feature.font_size.FontSizeFontSizeActivity
import com.brunowcnascimento.projectpoc.setup_main.MainAdapter
import com.brunowcnascimento.projectpoc.setup_main.MainRecyclerDomain

class MainActivity : CommonGenericActivity() {

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
            else -> getToast("Activity não cadastrada").show()
        }
    }
}