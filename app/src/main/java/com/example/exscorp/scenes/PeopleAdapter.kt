package com.example.exscorp.scenes

import Person
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exscorp.databinding.ItemPersonBinding

class PeopleAdapter(private var items: MutableList<Person> = mutableListOf()) :
    RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.itemBinding) {
            textViewName.text = item.fullName
            textViewId.text = item.id.toString()
        }
    }

    override fun getItemCount() = items.size

    fun addPeople(people: List<Person>) {
        items.addAll(people)
        notifyDataSetChanged()
    }

    class ViewHolder(
        val itemBinding: ItemPersonBinding
    ) : RecyclerView.ViewHolder(itemBinding.root)

}