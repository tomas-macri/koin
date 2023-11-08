package com.example.koinannotations.ui.main.fragments.detalle_movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.koinannotations.BuildConfig
import com.example.koinannotations.R
import com.example.koinannotations.databinding.ActorViewBinding
import com.example.koinannotations.domain.modelo.Actor

class ActorAdapter : ListAdapter<Actor, ActorAdapter.ActorViewHolder>(
    DiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.actor_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        getItem(position).let {
            holder.render(it)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }
    }

    class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ActorViewBinding.bind(view)

        fun render(
            actor: Actor,
        ) {
            with(binding) {
                txtName.text = actor.name
                idIVCourseImage.load(BuildConfig.IMAGE_URL + actor.profile) {
                    //Mientras carga
                    placeholder(R.drawable.ic_baseline_downloading_24)
                    //Si hubo error al bajarla
                    error(R.drawable.ic_baseline_error_24)
                }
                txtCharacter.text = actor.character
            }
        }
    }
}