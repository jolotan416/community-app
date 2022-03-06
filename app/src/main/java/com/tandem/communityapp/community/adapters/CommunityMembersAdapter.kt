package com.tandem.communityapp.community.adapters

import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tandem.communityapp.R
import com.tandem.communityapp.data.community.CommunityMember
import com.tandem.communityapp.databinding.CommunityMemberItemBinding
import com.tandem.communityapp.databinding.RetryViewBinding

class CommunityMembersAdapter(private val callback: Callback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val ITEM_PADDING_DP = 16f
    }

    private val asyncListDiffer: AsyncListDiffer<CommunityMembersAdapterViewType> =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<CommunityMembersAdapterViewType>() {
            override fun areItemsTheSame(
                oldItem: CommunityMembersAdapterViewType,
                newItem: CommunityMembersAdapterViewType
            ) = if (oldItem is CommunityMembersAdapterViewType.ItemViewType) {
                oldItem.communityMember.id ==
                        (newItem as? CommunityMembersAdapterViewType.ItemViewType)?.communityMember?.id
            } else oldItem.viewType == newItem.viewType

            override fun areContentsTheSame(
                oldItem: CommunityMembersAdapterViewType,
                newItem: CommunityMembersAdapterViewType
            ) = if (oldItem is CommunityMembersAdapterViewType.ItemViewType) {
                oldItem.communityMember ==
                        (newItem as? CommunityMembersAdapterViewType.ItemViewType)?.communityMember
            } else oldItem == newItem
        })

    override fun getItemCount(): Int = asyncListDiffer.currentList.count()

    override fun getItemViewType(position: Int): Int =
        asyncListDiffer.currentList[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            CommunityMembersAdapterViewType.LoadingViewType.viewType -> {
                val progressBar = ProgressBar(context).apply {
                    layoutParams = RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT
                    )
                    setPadding(
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            ITEM_PADDING_DP,
                            parent.resources.displayMetrics
                        ).toInt()
                    )
                }
                LoadingViewHolder(progressBar)
            }
            CommunityMembersAdapterViewType.ErrorViewType.viewType -> ErrorViewHolder(
                RetryViewBinding.inflate(inflater, parent, false)
            )
            else -> {
                ViewHolder(CommunityMemberItemBinding.inflate(inflater, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val communityMemberItem = asyncListDiffer.currentList[position] as
                        CommunityMembersAdapterViewType.ItemViewType
                holder.bindData(communityMemberItem.communityMember)
            }
            is LoadingViewHolder -> holder.bind()
            is ErrorViewHolder -> holder.bind()
        }
    }

    fun setItems(communityMembers: List<CommunityMember>, willShowPageLoader: Boolean) {
        val addedCommunityMembers: MutableList<CommunityMembersAdapterViewType> =
            communityMembers.map { CommunityMembersAdapterViewType.ItemViewType(it) }
                .toMutableList()
        if (willShowPageLoader) {
            addedCommunityMembers.add(CommunityMembersAdapterViewType.LoadingViewType)
        }
        asyncListDiffer.submitList(addedCommunityMembers)
    }

    fun addRetryView() {
        asyncListDiffer.submitList(
            asyncListDiffer.currentList.filterIsInstance<CommunityMembersAdapterViewType.ItemViewType>() +
                    listOf(CommunityMembersAdapterViewType.ErrorViewType)
        )
    }

    inner class ViewHolder(private val binding: CommunityMemberItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(communityMember: CommunityMember) {
            binding.apply {
                this.communityMember = communityMember
                likeButton.setOnClickListener {
                    callback.onClickCommunityMemberLike(communityMember)
                }
            }
        }
    }

    inner class LoadingViewHolder(private val progressBar: ProgressBar) :
        RecyclerView.ViewHolder(progressBar) {
        fun bind() {
            progressBar.apply {
                isIndeterminate = true
                indeterminateTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.highlightBlue)
                )
            }
            callback.loadPage()
        }
    }

    inner class ErrorViewHolder(private val binding: RetryViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.retryButton.setOnClickListener {
                callback.onClickReloadButton()
            }
        }
    }

    interface Callback {
        fun loadPage()
        fun onClickCommunityMemberLike(communityMember: CommunityMember)
        fun onClickReloadButton()
    }
}