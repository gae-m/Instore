package com.example.instore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.common.ErrorDialogFragment
import models.Product

class ClothesAdapter(var productList: MutableList<Product>, var context: Context): RecyclerView.Adapter<ClothesAdapter.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
       val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.cell_grid_layout_dress, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var prodotto:Product = productList.get(position)
        Glide.with(context).load(prodotto.imgUrl).into(holder.tvImage)
        holder.tvColore.text = prodotto.colore

        holder.tvImage.setOnClickListener {
            Toast.makeText(context, prodotto.nome, Toast.LENGTH_LONG).show()
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var tvImage = itemView.findViewById<ImageView>(R.id.imageTest)
        var tvColore = itemView.findViewById<TextView>(R.id.textColore)

    }

}