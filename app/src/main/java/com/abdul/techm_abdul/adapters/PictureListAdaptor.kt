package com.abdul.techm_abdul.adapters//package com.abdul.techm_abdul.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.abdul.techm_abdul.R
import com.abdul.techm_abdul.databinding.PictureListItemBinding
import com.abdul.techm_abdul.models.Picture
import com.abdul.techm_abdul.viewmodels.PictureViewModel

class PictureListAdaptor(
    private val items: ArrayList<Picture>,
    private val context: Context,
    val pictureViewModel: PictureViewModel
) :
    RecyclerView.Adapter<PictureViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val binding: PictureListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.picture_list_item,
            parent,
            false
        )
        return PictureViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener(View.OnClickListener { view ->
            pictureViewModel.selectPicture(item)
            view.findNavController()
                .navigate(R.id.action_AlbumFragment_to_PictureFragment)
        })
    }
}

class PictureViewHolder(private val binding: PictureListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Picture) {
        binding.apply {
            picture = item
        }
    }

}