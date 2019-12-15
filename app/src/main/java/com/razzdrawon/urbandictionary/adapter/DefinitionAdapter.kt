package com.razzdrawon.urbandictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.razzdrawon.urbandictionary.R

class DefinitionAdapter : RecyclerView.Adapter<DefinitionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.definition_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view) {
    }

}