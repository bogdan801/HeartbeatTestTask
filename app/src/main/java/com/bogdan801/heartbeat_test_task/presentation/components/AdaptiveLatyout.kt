package com.bogdan801.heartbeat_test_task.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bogdan801.heartbeat_test_task.presentation.util.DeviceOrientation

@Composable
fun AdaptiveLayout(
    modifier: Modifier = Modifier,
    orientation: DeviceOrientation = DeviceOrientation.Portrait,
    portraitRatio: Float = 1f,
    landscapeRatio: Float = 1f,
    firstHalf: @Composable BoxScope.(isPortrait: Boolean) -> Unit = {},
    secondHalf: @Composable BoxScope.(isPortrait: Boolean) -> Unit = {},
) {
    if(orientation == DeviceOrientation.Portrait){
        Column(
            modifier = modifier
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(portraitRatio)
            ){
                firstHalf(true)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            ){
                secondHalf(true)
            }
        }
    }
    else {
        Row(
            modifier = modifier
        ) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(landscapeRatio)
            ){
                firstHalf(false)
            }
            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
            ){
                secondHalf(false)
            }
        }
    }

}