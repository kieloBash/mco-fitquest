package com.mobdeve.s13.group.mcofitquest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group.mcofitquest.models.Activity
import com.mobdeve.s13.group.mcofitquest.models.Program

class ProgramsAdapter(private val data: ArrayList<Program>): RecyclerView.Adapter<ProgramsViewHolder>() {
    /*  onCreateViewHolder requires in two parameters:
            parent -> Which is the parent View where this adapter is associated with; this is
                      typically the RecyclerView
                      recall: recyclerView.adapter = MyAdapter(this.characterList)
            viewType -> This parameter refers to the
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramsViewHolder {
        // Create a LayoutInflater using the parent's (i.e. RecyclerView's) context
        val inflater = LayoutInflater.from(parent.context)
        // Inflate a new View given the item_layout.xml item view we created.
        val view = inflater.inflate(R.layout.program_item, parent, false)
        // Return a new instance of our MyViewHolder passing the View object we created
        return ProgramsViewHolder(view)
    }

    /*  Whenever the RecyclerView feels the need to bind data, onBindViewHolder is called. Here, we
        gain access to the specific ViewHolder instance and the position in our data that we should
        be binding to the view.
    * */
    override fun onBindViewHolder(holder: ProgramsViewHolder, position: Int) {
        // Please note that bindData is a function we created to adhere to encapsulation. There are
        // many ways to implement the binding of data.
        holder.bindData(data[position])
        //changed according to suggestion
    }

    override fun getItemCount(): Int {
        // This needs to be modified, so don't forget to add this in.
        return data.size
    }
}