package com.abdul.techm_abdul.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdul.techm_abdul.R
import com.abdul.techm_abdul.adapters.PictureListAdaptor
import com.abdul.techm_abdul.databinding.FragmentAlbumBinding
import com.abdul.techm_abdul.models.Picture
import com.abdul.techm_abdul.viewmodels.PictureViewModel
import com.abdul.techm_abdul.viewmodels.UserViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AlbumFragment : Fragment() {

    private val pictureViewModel: PictureViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getSelectedUser().observe(viewLifecycleOwner, Observer { selectedUser ->

            pictureViewModel.getPictures().observe(viewLifecycleOwner, Observer {

                pictureViewModel.getUserPictures(selectedUser.id)
                    .observe(viewLifecycleOwner, Observer { pictures ->
                        binding.recyclerViewPictures.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.recyclerViewPictures.adapter =
                            PictureListAdaptor(pictures, requireContext(), pictureViewModel)

                        (activity as? AppCompatActivity)?.supportActionBar?.title =
                            "Album ID: " + selectedUser.id

                    })

            })

        })

    }
}