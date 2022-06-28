package com.example.gopos.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gopos.databinding.ActivityMainBinding
import com.example.gopos.ui.GoPOSViewModel
import com.example.gopos.ui.RecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val adapter = RecyclerViewAdapter()
    private lateinit var binding: ActivityMainBinding
    private val viewModel : GoPOSViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setUpViewModel()
        setUpRecyclerView()
        setContentView(binding.root)
    }

    private fun setUpRecyclerView() {
        viewModel.getItemsFromDb().observe(this) {
            adapter.updateListForDisplay(it)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun setUpViewModel() {
        viewModel.getItemsFromAPIandSave()
        viewModel.getError().observe(this){
            if(!it.isNullOrEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                viewModel.clearErrorCollector()
            }
        }
    }


}