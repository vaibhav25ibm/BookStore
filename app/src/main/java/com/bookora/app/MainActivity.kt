package com.bookora.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.bookora.app.navigation.BookoraNavGraph
import com.bookora.app.ui.theme.BookoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookoraTheme {
                val navController = rememberNavController()
                BookoraNavGraph(navController = navController)
            }
        }
    }
}
