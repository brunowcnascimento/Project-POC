package com.brunowcnascimento.projectpoc.setup_main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brunowcnascimento.projectpoc.setup_main.MainAdapter.MainViewHolder
import com.brunowcnascimento.projectpoc.databinding.RecyclerviewMainItemBinding

class MainAdapter(
    private val itemClick: (nameActivity: String) -> Unit,
) : ListAdapter<MainRecyclerDomain, MainViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = RecyclerviewMainItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position).let { activities ->
            holder.bind(activities.name)
            holder.cardRecyclerMain.setOnClickListener {
                itemClick(activities.className)
            }
        }
    }

    class MainViewHolder(private val binding: RecyclerviewMainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val cardRecyclerMain = binding.cardRecyclerMain

        fun bind(nameActivity: String) {
            binding.textCardRecyclerMain.text = nameActivity
        }

    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MainRecyclerDomain>() {
            override fun areItemsTheSame(
                oldItem: MainRecyclerDomain,
                newItem: MainRecyclerDomain,
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: MainRecyclerDomain,
                newItem: MainRecyclerDomain,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}