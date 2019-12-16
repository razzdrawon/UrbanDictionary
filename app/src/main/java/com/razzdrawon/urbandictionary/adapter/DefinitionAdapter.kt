package com.razzdrawon.urbandictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.razzdrawon.urbandictionary.R
import com.razzdrawon.urbandictionary.model.Definition
import kotlinx.android.synthetic.main.definition_row.view.*

class DefinitionAdapter(private var definitions: ArrayList<Definition>) :
    ListAdapter<Definition, DefinitionAdapter.ViewHolder>(DefinitionDiffUtil) {

    fun updateDefinitions(newDefinitions: ArrayList<Definition>) {
        definitions.clear()
        definitions.addAll(newDefinitions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.definition_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = definitions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(definitions[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(definition: Definition) {
            itemView.word.text = definition.word
            itemView.definition.text = definition.definition
            itemView.example.text = definition.example
            itemView.author.text = definition.author
            itemView.thumbs_up.text = definition.thumbs_up.toString()
            itemView.thumbs_down.text = definition.thumbs_down.toString()
        }
    }

    object DefinitionDiffUtil : DiffUtil.ItemCallback<Definition>() {
        override fun areItemsTheSame(oldItem: Definition, newItem: Definition) =
            oldItem.defid == newItem.defid

        override fun areContentsTheSame(oldItem: Definition, newItem: Definition) =
            oldItem == newItem
    }

}