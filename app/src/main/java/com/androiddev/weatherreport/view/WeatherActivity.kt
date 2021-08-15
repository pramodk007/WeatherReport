package com.androiddev.weatherreport.view
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.androiddev.weatherreport.databinding.ActivityMainBinding
import com.androiddev.weatherreport.utils.ApiState
import com.androiddev.weatherreport.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        lifecycleScope.launchWhenStarted {
            weatherViewModel._postStateFlow.collect {
                when(it){
                    is ApiState.Loading -> {
                        Toast.makeText(this@WeatherActivity,"loading..",Toast.LENGTH_SHORT).show()
                    }
                    is ApiState.Failure -> {
                            Toast.makeText(this@WeatherActivity,"no such city found",Toast.LENGTH_SHORT).show()
                    }
                    is ApiState.Success -> {
                        binding.name.text = it.data.location.name
                        binding.tempInC.text = it.data.current.temp_c.toString()
                        binding.tempInF.text = it.data.current.temp_f.toString()
                        binding.Latitude.text = it.data.location.lat.toString()
                        binding.Longitude.text = it.data.location.lon.toString()
                    }
                }
            }
        }
    }



    private fun initListener()
    {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { weatherViewModel.getCityData(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

}
