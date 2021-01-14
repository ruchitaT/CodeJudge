package org.codejudge.application.adapter

import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_rest_list.view.*
import org.codejudge.application.R
import org.codejudge.application.model.BySearchResult

class AdapterRestaurants(
        var context: Activity?,
        var list: ArrayList<BySearchResult>
) : RecyclerView.Adapter<AdapterRestaurants.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) =
            AdapterRestaurants.ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.adapter_rest_list, parent, false)
            )
    override fun onBindViewHolder(holder: AdapterRestaurants.ViewHolder, position: Int) {
        Log.e("data",list[position].toString())
        holder.tv_label.text = list[position].name


        var category = ""
        list[position].types?.forEach {
            category = "$category* $it "
        }
        holder.tv_category.text = category
        holder.tv_user_reviews.text = (list[position].user_ratings_total?.toInt()).toString()+" user reviews"

                if(list[position].rating!=null || list[position].rating.toString()!=""){
                    holder.tv_rating.text = list[position].rating.toString()
                }else{
                    holder.tv_rating.text = "0"
                }
        holder.tv_delivery.text = list[position].reference.toString()
        if(list[position].opening_hours!=null && list[position].opening_hours?.open_now!=null && list[position].opening_hours?.open_now!!){
            holder.tv_status.text = "Open"
            holder.tv_status.setTextColor(Color.parseColor("#00B81F"))
        }else{
            holder.tv_status.text = "Closed"
            holder.tv_status.setTextColor(Color.parseColor("#CA1919"))
        }
                    // holder.iv_imgview.setImageURI(Uri.parse(list[position].icon))
        if(list[position].icon!=null){
            Glide.with(context)
                    .load(list[position].icon)
                    .into(holder.iv_imgview)
        }else{
            holder.iv_imgview.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
//        return if(list.size >3){
//            3
//        }else
//            list.size
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_label = view.tv_label
        var tv_category = view.tv_category
        var tv_delivery = view.tv_delivery
        var tv_status = view.tv_status
        var tv_user_reviews = view.tv_user_reviews
        var tv_rating = view.tv_rating
        var iv_imgview = view.iv_imgview

    }

}