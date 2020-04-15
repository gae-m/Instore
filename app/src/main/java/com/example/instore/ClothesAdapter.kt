package com.example.instore

import android.annotation.SuppressLint
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
import kotlin.text.Typography.euro

class ClothesAdapter(var productList: MutableList<Product>, var context: Context): RecyclerView.Adapter<ClothesAdapter.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
       val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.cell_grid_layout_dress, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var prodotto:Product = productList[position]
        Glide.with(context).load(prodotto.imgUrl).into(holder.tvImage)
        holder.tvNome.text = prodotto.nome
        holder.tvPrezzo.text = prodotto.prezzo.toString() + " " + euro

        holder.tvImage.setOnClickListener {
            Toast.makeText(context, prodotto.nome, Toast.LENGTH_LONG).show()
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var tvImage = itemView.findViewById<ImageView>(R.id.imageTest)
        var tvNome = itemView.findViewById<TextView>(R.id.textNome)
        var tvPrezzo = itemView.findViewById<TextView>(R.id.textPrezzo)

    }

}