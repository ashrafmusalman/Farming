package com.shahbaz.farming.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shahbaz.farming.R
import com.shahbaz.farming.databinding.FragmentLoginBinding
import com.shahbaz.farming.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private lateinit var binding:FragmentLoginBinding
    private val authenticationViewmodel by viewModels<AuthenticationViewmodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        authenticationViewmodel.signInWithEmailAndPassword("shahbaz1@gmail.com","HeyThis1234")
        observeLoginProcess()
    }

    private fun observeLoginProcess() {
        lifecycleScope.launch {
            authenticationViewmodel.loginState.collect{
                when(it){
                    is Resources.Error -> {
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                    is Resources.Loading -> {}
                    is Resources.Success -> {
                        Toast.makeText(requireContext(),it.data,Toast.LENGTH_SHORT).show()

                    }
                    is Resources.Unspecified -> {}
                }
            }
        }
    }

}