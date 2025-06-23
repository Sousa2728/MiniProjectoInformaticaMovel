package com.example.yourevent2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.yourevent2.screens.WelcomeScreen
import com.example.yourevent.ui.theme.YourEventTheme
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import com.example.yourevent2.BaseDados.AppDatabase
import com.example.yourevent2.BaseDados.EventoRepository
import com.example.yourevent2.screens.MainScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars(this)

        val dao = AppDatabase.getDatabase(applicationContext).eventoDao()
        val repo = EventoRepository(dao)

        enableEdgeToEdge()
        setContent {
            YourEventTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    myApp(modifier = Modifier.fillMaxSize(),repo=repo)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun myApp(
    modifier: Modifier=Modifier,
    repo: EventoRepository
){
    var botao by rememberSaveable { mutableStateOf(false) }
    Surface(modifier) {
        if(!botao) {
            WelcomeScreen(
                modifier = modifier, onContinueClicked = { botao = true })
        }else{
            MainScreen(repo=repo)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun hideSystemBars(activity: Activity) {
    val window = activity.window
    WindowCompat.setDecorFitsSystemWindows(window, false)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = window.insetsController
        controller?.hide(WindowInsets.Type.navigationBars() or WindowInsets.Type.statusBars())
        controller?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }
}


