package com.example.valorpay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.valorpay.databinding.AdapterCartBinding
import com.example.valorpay.model.Product

class CartAdapter(val context: Context,val cInterface: CartInterface,
                  val list: ArrayList<Product>): RecyclerView.Adapter<CartAdapter.CartViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = AdapterCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
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

        holder.binding.addItem.setOnClickListener {
            val count = list[position].itemCount+1
            calculate(count,position)
        }

        holder.binding.minus.setOnClickListener {
            val count = list[position].itemCount-1
            if (count>0) {
                calculate(count,position)
            }else {
                cInterface.removeFromCart(cart,position)
                list.removeAt(position)
                notifyDataSetChanged()
            }
        }

    }

    private fun calculate(count: Int,position:Int) {
        val price: Double = list[position].price.toDouble()
        val total = count.times(price)

        list[position].itemCount = count
        list[position].itemTotalPrice = total
        cInterface.incDecFunction(list[position],position)
        //notifyItemChanged(position)
        notifyDataSetChanged()
    }

    class CartViewHolder(var binding: AdapterCartBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(products: Product) {
            binding.cart = products
        }
    }

    interface CartInterface {
        fun removeFromCart(cart: Product,position: Int)
        fun incDecFunction(list: Product, position: Int)

    }
}