package com.gamis214.datastoreexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.gamis214.datastoreexample.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView(){
        with(binding){
            btnSave.setOnClickListener {
                viewModel.saveUserInDataStore(edtAge.editText?.text.toString().toInt(),edtName.editText?.text.toString())
                Toast.makeText(this@MainActivity,"Usuario Guardado",Toast.LENGTH_SHORT).show()
            }
            btnSearch.setOnClickListener {
                viewModel.getDataUser().observe(this@MainActivity){ userData ->
                    txtName.text = userData.name
                    txtAge.text = userData.age.toString()
                }
            }
        }
    }

}