package com.abdul.techm_abdul.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.abdul.techm_abdul.R
import com.abdul.techm_abdul.databinding.FragmentPictureBinding
import com.abdul.techm_abdul.viewmodels.PictureViewModel


class PictureFragment : Fragment() {

    private val pictureViewModel: PictureViewModel by activityViewModels()
    private lateinit var binding: FragmentPictureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_picture, container, false)
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pictureViewModel.getSelectedPicture().observe(viewLifecycleOwner, Observer { picture ->
            binding.picture = picture
            binding.toolbarTitle.text =
                "Album ID: " + picture.albumId + System.getProperty("line.separator") + " Photo ID: " + picture.id
        })
    }

}