package com.example.project1842.Activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1842.Adapter.CategoryAdapter
import com.example.project1842.Adapter.JobAdapter
import com.example.project1842.Model.JobModel
import com.example.project1842.R
import com.example.project1842.ViewModel.MainViewModel
import com.example.project1842.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initLocation()
        initRecyclerViewCat()
        initRecyclerviewSuggest()
        initRecyclerviewRecent("0")

    }

    private fun initRecyclerviewRecent(cat: String) {
        var data: List<JobModel>
        if (cat == "0") {
            data = mainViewModel.loadData().sortedBy { it.category }
        } else {
            data = mainViewModel.loadData().filter { it.category == cat }
        }

        binding.viewRecent.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.viewRecent.adapter = JobAdapter(data)

    }

    private fun initRecyclerviewSuggest() {
        binding.progressBarSuggest.visibility = View.VISIBLE
        binding.viewSuggested.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.viewSuggested.adapter = JobAdapter(mainViewModel.loadData())
        binding.progressBarSuggest.visibility = View.GONE
    }

    private fun initLocation() {
        val adapter = ArrayAdapter(this, R.layout.spinner_item, mainViewModel.loadLocation())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.locationSpin.adapter = adapter
    }

    private fun initRecyclerViewCat() {
        binding.progressBarCategoty.visibility = View.VISIBLE
        binding.viewCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.viewCategory.adapter =
            CategoryAdapter(mainViewModel.loadCategory(), object : CategoryAdapter.ClickListener {
                override fun onClkick(category: String) {
                    initRecyclerviewRecent(category)
                }

            })
        binding.progressBarCategoty.visibility = View.GONE
    }
}