package com.abc.app.presentation.adapters

import android.graphics.Color
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.abc.app.R
import com.abc.app.data.model.Stats

class BottomSheetAdapter(private val statistics: Stats) :
    RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bottom_sheet_item, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val charWithCount = statistics.top3CharsWithCounts[position]
        val top3Char= charWithCount.key.toString() +"  ->  "+ charWithCount.value.toString()
        val text = SpannableString(top3Char)
        text.setSpan(BulletSpan(15, Color.BLACK, 6 ), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.tvName.text = text


    }

    override fun getItemCount(): Int = statistics.top3CharsWithCounts.size
}