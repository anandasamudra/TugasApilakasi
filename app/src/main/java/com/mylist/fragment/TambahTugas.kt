package com.mylist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mylist.R
import com.mylist.model.Tugas

class TambahTugas : Fragment() {

    private lateinit var etJudulTaks: EditText
    private lateinit var etDeskripsiTaks: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var btnTambahTugas: Button
    private lateinit var databaseRef: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tambah_tugas, container, false)
        etJudulTaks = view.findViewById(R.id.judultask)
        etDeskripsiTaks = view.findViewById(R.id.deskripsitaks)
        datePicker = view.findViewById(R.id.tanggal)
        btnTambahTugas = view.findViewById(R.id.buattugas)

        databaseRef = FirebaseDatabase.getInstance().reference.child("tugas")

        btnTambahTugas.setOnClickListener {
            tambahTugas()
        }
        return view
    }

    private fun tambahTugas() {
        val judul = etJudulTaks.text.toString()
        val deskripsi = etDeskripsiTaks.text.toString()
        val tanggal = getDateFromDatePicker(datePicker)

        val tugas = Tugas(judul, deskripsi, tanggal)

        val tugasId = databaseRef.push().key
        if (tugasId != null) {
            databaseRef.child(tugasId).setValue(tugas)
                .addOnSuccessListener {
                    Toast.makeText(activity, "Tugas berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Gagal menambahkan tugas", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun getDateFromDatePicker(datePicker: DatePicker): String {
        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1
        val year = datePicker.year

        return "$day/$month/$year"
    }
}