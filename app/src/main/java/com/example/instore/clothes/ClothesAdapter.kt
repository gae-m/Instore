package com.example.instore.clothes

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instore.R
import models.Product
import kotlin.text.Typography.euro

class ClothesAdapter(val productList: MutableList<Product>, val context: Context): RecyclerView.Adapter<ClothesAdapter.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.cell_grid_layout_dress, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val prodotto: Product = productList[position]

        Glide.with(context).load(prodotto.img[0]).into(holder.tvImage)
        holder.tvNome.text = prodotto.nome
        holder.tvPrezzo.text = prodotto.prezzo.toString() + " " + euro

        holder.itemView.setOnClickListener {

            val x = Bundle()
            x.putParcelable("prodotto", prodotto)
            Navigation.findNavController(it).navigate(R.id.action_clothesFragment_to_dressFragment, x)
        }

    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvImage = itemView.findViewById<ImageView>(R.id.imageTest)
        var tvNome = itemView.findViewById<TextView>(R.id.textNome)
        var tvPrezzo = itemView.findViewById<TextView>(R.id.textPrezzo)

    }

}