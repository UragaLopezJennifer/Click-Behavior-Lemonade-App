package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonade.ui.theme.LemonadeTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp(){
    LemonadeTextAndImage(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun LemonadeTextAndImage(modifier: Modifier = Modifier){
    var result by remember { mutableStateOf(1) }  // Handles image and text being shown
    var clickCount by remember {mutableStateOf(0)} // Handle to record amount of clicks user taps on Lemon image
    var clickSqueezeCount by remember { mutableStateOf((2..4).random())  } // Random number user must tap when shown Lemon

    val imageResource= when (result) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3-> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val textResource = when (result) {
        1 -> R.string.step_one
        2 -> R.string.step_two
        3 -> R.string.step_three
        else -> R.string.step_four
    }

    val descriptionResource = when (result) {
        1 -> R.string.desc_one
        2 -> R.string.desc_two
        3 -> R.string.desc_three
        else -> R.string.desc_four
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .background(Color(255, 228, 76))
                .padding(top = 54.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text (
                text = "Lemonade",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Images on a button
            Button(
                onClick = {
                    //Reset app and return to step one
                    if (result == 4) {
                        result = 1
                        clickCount = 0
                        clickSqueezeCount = (2..4).random()
                    }

                    //Update squeeze count if clicked
                    else if (result == 2){
                        clickCount += 1
                        if (clickCount == clickSqueezeCount) {
                            result += 1
                            clickCount = 0 // Reset click count
                        }
                    }

                    //Move to next step
                    else{
                        result += 1
                    }

                },
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(Color(200, 236, 212)),
                modifier = Modifier
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(imageResource),
                    contentDescription = stringResource(descriptionResource)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Caption of current step
            Text(
                text = stringResource(textResource),
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}