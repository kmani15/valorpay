package com.example.valorpay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.valorpay.databinding.AdapterViewCartBinding
import com.example.valorpay.model.Product

class ViewCartAdapter(val context: Context,
                      val list: ArrayList<Product>): RecyclerView.Adapter<ViewCartAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterViewCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = list[position]
        holder.bind(cart)

        try {
            val imgName = list[position].imagesrc
            val resId = context.resources.getIdentifier(imgName,"drawable",context.packageName)
            val drawable = ResourcesCompat.getDrawable(context.resources,resId,context.theme)
            holder.binding.img.setImageDrawable(drawable)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    class ViewHolder(var binding: AdapterViewCartBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(products: Product) {
            binding.cart = products
        }
    }


}