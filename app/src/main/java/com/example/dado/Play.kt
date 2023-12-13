package com.example.dado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dado.databinding.FragmentPlayBinding

class Play : Fragment() {

    private var binding: FragmentPlayBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(inflater,container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.following = this
    }

    //Para pasar al siguiente fragment
    fun following(){
        findNavController().navigate(R.id.action_jugar_to_launch)
    }

}