package com.example.weatherappxmlapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.weatherappxmlapplication.api.Constants
import com.example.weatherappxmlapplication.databinding.ActivityMainBinding
import com.example.weatherappxmlapplication.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
     //acsses viewmodel
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.search.setOnClickListener {
            getData()
        }
        boilingAlert()


    }
    //get location weather
    private fun getData(){
      //  val t : Double
        var city = binding.etLocation.text.toString()
        viewModel.getCityWeather(city,Constants.APP_ID)
        viewModel.responseWeatherCity.observe(this) { fWeather ->
            binding.apply {
                textTown.text = etLocation.text.toString()
                var tem = fWeather.main.temp
                var t = kToC(tem)
                textTemp.text = "$t°"
                textMain.text = fWeather.weather[0].main.toString()
                textHumi.text = "Humidity  " + fWeather.main.humidity.toString()
            }

        }

    }

    //convert kelvin to celsius
    private fun kToC( tem : Double) : Double{
        var temp = tem
        temp = temp.minus(273)
        var temCelsius = temp.toBigDecimal().setScale(1,RoundingMode.UP).toDouble()
        return temCelsius
    }

    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notification Title"
            val descriptionText = "Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.ChannelID,name,importance).apply {
                description =  descriptionText
            }

            val notificationManager: NotificationManager = getSystemService( NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun boilingAlert(){
        val tem =  binding.textTemp.text
        if (10 < 20.0){
            val builder = NotificationCompat.Builder(this,Constants.ChannelID)
                .setSmallIcon(R.drawable.wb_sunny)
                .setContentTitle("Weather Notification")
                .setContentText("It is boiling..")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManager = NotificationManagerCompat.from(this)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notificationManager.notify(Constants.notificationID,builder)

        }else{
            if (10 <= 0){
                val builder1 = NotificationCompat.Builder(this ,Constants.ChannelID)
                    .setSmallIcon(R.drawable.wb_sunny)
                    .setContentTitle("Weather Notification")
                    .setContentText("It is Freezing..")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()

                val notificationManager1 = NotificationManagerCompat.from(this)

                notificationManager1.notify(Constants.notificationID,builder1)

            }
        }
    }

    fun freezingAlert(){

    }

}