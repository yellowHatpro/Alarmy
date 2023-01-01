package org.yellowhatpro.alarmy

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TextClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import org.yellowhatpro.alarmy.ui.theme.AlarmyTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var alarmService: AlarmService
        setContent {
            alarmService = AlarmService(this)
            AlarmyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    Column (modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                        
                        AndroidView(factory = {TextClock(context).apply {
                            format12Hour?.let { this.format12Hour = "hh:mm:ss a" }
                            // on below line we are setting time zone.
                            timeZone?.let { this.timeZone = it }
                            // on below line we are setting text size.
                            textSize.let { this.textSize = 30f }
                            setTextColor( resources.getColor(R.color.white))
                        }
                        })

                        Button(onClick = {
                            setAlarm{ timeInMillis ->
                                alarmService.setExactAlarm(timeInMillis)
                            }
                        }) {
                            Text(text = "Schedule Alarm")
                        }
                    }
                }
            }
        }
    }

    private fun setAlarm(callback: (Long)-> Unit){
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND,0)
            this.set(Calendar.MILLISECOND,0)
            DatePickerDialog(
                this@MainActivity,
                0,
                 { _, year, month, dayOfMonth ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                     TimePickerDialog(
                         this@MainActivity,
                         0,
                         { _, hourOfDay, minute ->
                             this.set(Calendar.HOUR_OF_DAY, hourOfDay)
                             this.set(Calendar.MINUTE, minute)
                             callback(this.timeInMillis)
                         },
                         this.get(Calendar.HOUR_OF_DAY),
                         this.get(Calendar.MINUTE),
                         false
                     ).show()

                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlarmyTheme {
        Greeting("Android")
    }
}