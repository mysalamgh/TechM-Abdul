package com.abdul.techm_abdul.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdul.techm_abdul.R
import com.abdul.techm_abdul.adapters.UserInfoListAdaptor
import com.abdul.techm_abdul.databinding.FragmentUserInfoBinding
import com.abdul.techm_abdul.viewmodels.UserViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UserInfoFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentUserInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getUsers().observe(requireActivity(), Observer { users ->
            binding.recyclerViewUsers.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerViewUsers.adapter =
                UserInfoListAdaptor(users, requireContext(), userViewModel)
        })

        (activity as? AppCompatActivity)?.supportActionBar?.title = "User Info"

    }

}