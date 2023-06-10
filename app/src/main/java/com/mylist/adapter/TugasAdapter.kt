package com.mylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mylist.R
import com.mylist.model.Tugas

class TugasAdapter() : RecyclerView.Adapter<TugasAdapter.TugasViewHolder>() {

    private var tugasList: List<Tugas> = emptyList()

    inner class TugasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Deklarasikan elemen-elemen UI pada item layout
        // Misalnya, TextView untuk judul tugas
        private val judulTextView: TextView = itemView.findViewById(R.id.tv_judul)
        private val deskripsiTextView: TextView = itemView.findViewById(R.id.tv_deskripsi)
        private val tanggalTextView: TextView = itemView.findViewById(R.id.tv_tanggal)

        fun bind(tugas: Tugas) {
            // Set nilai dari elemen-elemen UI berdasarkan data tugas
            judulTextView.text = tugas.judul
            tanggalTextView.text = tugas.tanggal
            deskripsiTextView.text = tugas.deskripsi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TugasViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return TugasViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TugasViewHolder, position: Int) {
        holder.bind(tugasList[position])
    }

    override fun getItemCount(): Int {
        return tugasList.size
    }

    fun submitList(list: List<Tugas>) {
        tugasList = list
        notifyDataSetChanged()
    }
}
