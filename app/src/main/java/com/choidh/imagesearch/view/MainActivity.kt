package com.choidh.imagesearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.view.*
import android.widget.*
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.choidh.imagesearch.R
import com.choidh.imagesearch.adapter.HistoryAdapter
import com.choidh.imagesearch.adapter.SearchImageAdapter
import com.choidh.imagesearch.data.History
import com.choidh.imagesearch.databinding.ActivityMainBinding
import com.choidh.imagesearch.viewmodel.HistoryViewModel
import com.choidh.imagesearch.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.*
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchAdapter: SearchImageAdapter
    private lateinit var historyAdapter: HistoryAdapter

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        searchAdapter = SearchImageAdapter()

        binding.apply {
            imageRecyclerView.setHasFixedSize(true)
            imageRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            imageRecyclerView.adapter = searchAdapter
        }

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.photos.observe(this) {
            searchAdapter.submitData(this.lifecycle, it)
        }

        viewModel.currentQuery.observe(this) {
            deleteSearchKeyword(it)
            binding.searchEditText.setText("")
            hideHistoryView()
            saveSearchKeyword(it)
        }

        bindViews()
        initHistoryRecyclerView()
        initViewModel()
    }

    private fun bindViews() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                historyAdapter.filter?.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                showHistoryView()
            }
            return@setOnTouchListener false
        }

        binding.searchButton.setOnClickListener{

        }
    }

    private fun initHistoryRecyclerView() {
        historyAdapter = HistoryAdapter(clickListener = {
            search(it.keyword.toString())
        }, deleteClickListener = {
            deleteSearchKeyword(it)
            showHistoryView()
        })
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyAdapter
    }

    private fun initViewModel() {
        historyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(HistoryViewModel::class.java)
        historyViewModel.getAll().observe(this, androidx.lifecycle.Observer {
            historyAdapter.submitList(it)
            historyAdapter.updateList(it)
        })
    }

    private fun showHistoryView() {
        binding.historyRecyclerView.isVisible = true
    }

    private fun hideHistoryView() {
        binding.historyRecyclerView.isVisible = false
    }

    private fun search(keyword: String) {
        deleteSearchKeyword(keyword)
        binding.searchEditText.setText("")
        hideHistoryView()
        saveSearchKeyword(keyword)

        viewModel.searchImages(keyword)
    }

    private fun saveSearchKeyword(keyword: String) {
        historyViewModel.insert(History(null, keyword))
    }

    private fun deleteSearchKeyword(keyword: String) {
        historyViewModel.delete(keyword)
    }


}