package com.example.valorpay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.valorpay.databinding.PaymentCardBinding
import com.example.valorpay.model.PaymentCard

class PaymentCardAdapter(val context: Context,var mCardList: ArrayList<PaymentCard>): RecyclerView.Adapter<PaymentCardAdapter.PayCardViewHolder>()  {
    
    var pos = -1
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayCardViewHolder {
        val binding = PaymentCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return PayCardViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return mCardList.size
    }

    override fun onBindViewHolder(holder: PayCardViewHolder, position: Int) {

        val mCard = mCardList[position]
        holder.bind(mCard)

        holder.binding.radioBtn.setOnClickListener {
            pos= holder.adapterPosition
            notifyDataSetChanged()
        }

        holder.binding.radioBtn.isChecked = pos == position
        
    }

    class PayCardViewHolder (var binding: PaymentCardBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(mCard: PaymentCard) {
            binding.card = mCard
        }

    }

}