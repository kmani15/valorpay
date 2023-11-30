package com.example.valorpay.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.valorpay.R
import com.example.valorpay.databinding.AdapterProductBinding
import com.example.valorpay.model.Product

class ProductAdapter(val context: Context, val pInterface: ProductInterface,
                     val productList: ArrayList<Product>): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = AdapterProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val product = productList[position]
        holder.bind(product)

        if (product.isCart) {
            holder.binding.imgCart.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary),
                PorterDuff.Mode.MULTIPLY)
        }else {
            holder.binding.imgCart.setColorFilter(ContextCompat.getColor(context, R.color.black),
                PorterDuff.Mode.MULTIPLY)
        }

        try {
            val imgName = productList[position].imagesrc
            val resId = context.resources.getIdentifier(imgName,"drawable",context.packageName)
            val drawable = ResourcesCompat.getDrawable(context.resources,resId,context.theme)
            holder.binding.productImg.setImageDrawable(drawable)
        }catch (e:Exception){
            e.printStackTrace()
        }


        holder.binding.imgCart.setOnClickListener {
            productList[position].isCart = !product.isCart

            if (product.isCart) {
                productList[position].itemCount = 1
                productList[position].itemTotalPrice = product.price.toDouble()
            }
            notifyItemChanged(position)
            //notifyDataSetChanged()
            pInterface.selectedItem(productList[position])
        }
    }

    class ProductViewHolder (var binding: AdapterProductBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(products: Product) {
            binding.product = products
        }
    }

    interface ProductInterface {
        fun selectedItem(product: Product)
    }
}