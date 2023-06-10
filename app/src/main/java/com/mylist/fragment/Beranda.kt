package com.mylist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mylist.R
import com.mylist.adapter.TugasAdapter
import com.mylist.model.Tugas

class Beranda : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TugasAdapter
    private lateinit var databaseRef: DatabaseReference
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_beranda, container, false)

        recyclerView = view.findViewById(R.id.item_list)

        // Menginisialisasi adapter dan layout manager untuk RecyclerView
        adapter = TugasAdapter()
        layoutManager = LinearLayoutManager(activity)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        // Mendapatkan referensi database
        databaseRef = FirebaseDatabase.getInstance().reference.child("tugas")

        // Mendapatkan data tugas dari Firebase dan mengupdate RecyclerView
        getTugasFromFirebase()

        return view
    }

    private fun getTugasFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tugasList = ArrayList<Tugas>()
                for (dataSnapshot in snapshot.children) {
                    val tugas = dataSnapshot.getValue(Tugas::class.java)
                    tugas?.let { tugasList.add(it) }
                }
                adapter.submitList(tugasList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
