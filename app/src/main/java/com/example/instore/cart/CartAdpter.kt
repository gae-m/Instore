package com.example.instore.cart

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instore.R
import models.OnceProduct
import kotlin.text.Typography.euro

class CartAdpter(val cartList: MutableList<MutableMap<String, Any?>>, val context: Context) : RecyclerView.Adapter<ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.riga_cart, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var prodottoCart = cartList[position]

        Glide.with(context).load(prodottoCart["imgUrl"]).into(holder.tvImage)
        holder.tvNome.text = prodottoCart["nome"] as String
        holder.tvColore.text = prodottoCart["prezzo"].toString() + " " + euro

        holder.itemView.setOnClickListener {
        }

    }
}


class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var tvImage = itemView.findViewById<ImageView>(R.id.imageCart)
    var tvNome = itemView.findViewById<TextView>(R.id.textNomeCart)
    var tvColore = itemView.findViewById<TextView>(R.id.textColoreCart)


}
