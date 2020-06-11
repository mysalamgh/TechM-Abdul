package com.abdul.techm_abdul.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.abdul.techm_abdul.R
import com.abdul.techm_abdul.databinding.UserListItemBinding
import com.abdul.techm_abdul.models.User
import com.abdul.techm_abdul.viewmodels.UserViewModel

class UserInfoListAdaptor(
    private val items: ArrayList<User>,
    private val context: Context,
    private val userViewModel: UserViewModel
) :
    RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: UserListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.user_list_item,
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener(View.OnClickListener {
            userViewModel.selectUser(item)
            holder.itemView.findNavController()
                .navigate(R.id.action_UserInfoFragment_to_AlbumFragment)
        })
    }

}

class UserViewHolder(val binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: User) {
        binding.apply {
            userInfo = item
        }
    }
}