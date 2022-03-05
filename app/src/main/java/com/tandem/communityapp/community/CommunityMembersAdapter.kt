package com.tandem.communityapp.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tandem.communityapp.data.community.CommunityMember
import com.tandem.communityapp.databinding.CommunityMemberItemBinding

class CommunityMembersAdapter : RecyclerView.Adapter<CommunityMembersAdapter.ViewHolder>() {
    private val asyncListDiffer: AsyncListDiffer<CommunityMember> =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<CommunityMember>() {
            override fun areItemsTheSame(oldItem: CommunityMember, newItem: CommunityMember) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CommunityMember, newItem: CommunityMember) =
                oldItem == newItem
        })

    override fun getItemCount(): Int = asyncListDiffer.currentList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(CommunityMemberItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(asyncListDiffer.currentList[position])
    }

    fun setItems(communityMembers: List<CommunityMember>) {
        asyncListDiffer.submitList(communityMembers)
    }

    class ViewHolder(private val binding: CommunityMemberItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(communityMember: CommunityMember) {
            binding.communityMember = communityMember
        }
    }
}