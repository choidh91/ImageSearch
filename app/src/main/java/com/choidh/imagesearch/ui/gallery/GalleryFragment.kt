package com.choidh.imagesearch.ui.gallery

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.choidh.imagesearch.R
import com.choidh.imagesearch.databinding.FragmentGalleryBinding
import com.choidh.imagesearch.ui.history.HistoryAdapter
import com.choidh.imagesearch.ui.history.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val galleryViewModel by viewModels<GalleryViewModel>()

    private val historyViewModel by viewModels<HistoryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: HistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val naverPhotoAdapter = NaverPhotoAdapter()
        historyAdapter = HistoryAdapter(itemClickListener = {
            searchQuery(it.keyword)
        }, deleteClickListener = {
            historyViewModel.onDeleteClick(it)
        })

        binding.apply {
            recyclerViewPhoto.apply {
                adapter = naverPhotoAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                itemAnimator = null
            }

            recyclerViewHistory.apply {
                adapter = historyAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                itemAnimator = null
            }

            buttonSearch.setOnClickListener {
                searchQuery(editTextQuery.text.toString())
            }

            editTextQuery.setOnTouchListener { view, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    showHistoryView()
                }
                return@setOnTouchListener false
            }

            editTextQuery.addTextChangedListener {
                historyAdapter.filter?.filter(it.toString())
                historyViewModel.searchQuery.value = it.toString()
            }

        }

        galleryViewModel.photos.observe(viewLifecycleOwner) {
            naverPhotoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        historyViewModel.histories.observe(viewLifecycleOwner) {
            historyAdapter.submitList(it)
            historyAdapter.updateList(it)
        }
    }

    private fun searchQuery(query: String) {
        hideHistoryView()

        binding.editTextQuery.setText(query)
        binding.editTextQuery.setSelection(query.length)

        historyViewModel.onDeleteClick(query)
        historyViewModel.insertHistory(query)
        galleryViewModel.searchPhotos(query)
    }

    private fun showHistoryView() {
        binding.recyclerViewHistory.isVisible = true
    }

    private fun hideHistoryView() {
        binding.recyclerViewHistory.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}