package com.example.hw6_youtube_app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.hw6_youtube_app.utils.Resource


typealias inflater<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<
        VB : ViewBinding,
        VM : ViewModel
        >(
    private val inflate: inflater<VB>
) : Fragment() {

    protected abstract val viewModel: VM

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        observe()
    }

    protected fun <T> LiveData<Resource<T>>.resHandler(
        loading: (loading: Boolean) -> Unit,
        success: (data: T) -> Unit
    ) {
        this.observe(viewLifecycleOwner) { resource ->
            loading.invoke(resource is Resource.Loading)
            when (resource) {
                is Resource.Error -> Toast.makeText(
                    requireContext(),
                    resource.message ?: "Unknown error",
                    Toast.LENGTH_LONG
                ).show()
                is Resource.Loading -> {}
                is Resource.Success -> resource.data?.let { success.invoke(it) }
            }
        }
    }

    open fun observe() {

    }

    open fun initialize() {

    }
}