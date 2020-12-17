package com.example.selfieapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.selfieapp.R
import com.example.selfieapp.models.User
import kotlinx.android.synthetic.main.row_user_adapter.view.*

class AdapterUser(var mContext: Context) : RecyclerView.Adapter<AdapterUser.ViewHolder>(){

    lateinit var mList: ArrayList<User>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUser.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_user_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: AdapterUser.ViewHolder, position: Int) {
        var user = mList[position]
        holder.bind(user)
    }
    fun setData(postList: ArrayList<User>) {
        mList = postList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.text_view_username.text = user.email

        }

    }

}