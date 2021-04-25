package com.vovan.pokeapp.pokelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentPokeListBinding

class PokeListFragment : Fragment() {

    lateinit var viewModel: PokeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Binding
        val binding: FragmentPokeListBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_poke_list,
                container,
                false
            )

        viewModel = ViewModelProvider(this).get(PokeListViewModel::class.java)

        return binding.root
    }


}