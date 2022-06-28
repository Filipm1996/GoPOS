package com.example.gopos.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gopos.R
import com.example.gopos.data.db.Item
import com.squareup.picasso.Picasso

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listForDisplay = listOf<Item>()

    class ViewHolderWithPhoto(itemView : View) : RecyclerView.ViewHolder(itemView)  {
        val name : TextView = itemView.findViewById(R.id.nameOfItem)
        val price : TextView = itemView.findViewById(R.id.priceValue)
        val category : TextView = itemView.findViewById(R.id.categoryValue)
        val tax : TextView = itemView.findViewById(R.id.taxValue)
        val image : ImageView = itemView.findViewById(R.id.image)
    }

    class ViewHolderWithoutPhoto(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.nameOfItem)
        val price : TextView = itemView.findViewById(R.id.priceValue)
        val category : TextView = itemView.findViewById(R.id.categoryValue)
        val tax : TextView = itemView.findViewById(R.id.taxValue)
    }

    companion object {
        const val viewHolderWithPhoto = 1
        const val viewHolderWithoutPhoto = 2
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            ViewHolderWithPhoto(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_content,parent,false))
        } else {
            ViewHolderWithoutPhoto(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_content_without_photo,parent,false))
        }

        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listForDisplay[position]
        when (holder) {
            is ViewHolderWithPhoto ->{
                holder.name.text = item.name
                holder.category.text = item.category
                holder.price.text = item.price
                holder.tax.text = item.vat
                Picasso.get().load(item.imageLink).into(holder.image)
            }

            is ViewHolderWithoutPhoto-> {
                holder.name.text = item.name
                holder.category.text = item.category
                holder.price.text = item.price
                holder.tax.text = item.vat
            }
        }
    }

    override fun getItemCount(): Int {
        return listForDisplay.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = listForDisplay[position]
        if (item.imageLink.isNullOrEmpty()){
            return viewHolderWithoutPhoto
        }else{
            return viewHolderWithPhoto
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateListForDisplay(list : List<Item>) {
        this.listForDisplay = list
        notifyDataSetChanged()
    }
}