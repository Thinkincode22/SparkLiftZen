package com.example.spark_uplift_zen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spark_uplift_zen.ui.theme.SparkupliftzenTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SparkupliftzenTheme {
                QuoteScreen()
            }
        }
    }
}

@Composable
fun BoxScope.FloatingBubbles() {
    val infiniteTransition = rememberInfiniteTransition()

    val offsetY1 by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val offsetX1 by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val offsetY2 by infiniteTransition.animateFloat(
        initialValue = 50f,
        targetValue = -50f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val offsetX2 by infiniteTransition.animateFloat(
        initialValue = 50f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .offset(x = offsetX1.dp, y = offsetY1.dp)
            .size(250.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.05f))
    )
    Box(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .offset(x = offsetX2.dp, y = offsetY2.dp)
            .size(300.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.05f))
    )
}

@Composable
fun QuoteScreen() {
    var quotes by remember { mutableStateOf<List<Quote>>(emptyList()) }
    var currentQuote by remember { mutableStateOf<Quote?>(null) }
    var visible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        quotes = getQuotes()
        if (quotes.isNotEmpty()) {
            currentQuote = quotes.random()
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "quoteFade"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFF1A1A3A), Color(0xFF2C2C54))))
            .clickable {
                if (quotes.isNotEmpty()) {
                    scope.launch {
                        visible = false
                        delay(400) // Wait for fade out to complete
                        currentQuote = quotes.random()
                        visible = true
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        FloatingBubbles()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(32.dp)
                .graphicsLayer {
                    this.alpha = alpha
                }
        ) {
            currentQuote?.let {
                Text(
                    text = "\"${it.text}\"",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "- ${it.author ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SparkupliftzenTheme {
        QuoteScreen()
    }
}
