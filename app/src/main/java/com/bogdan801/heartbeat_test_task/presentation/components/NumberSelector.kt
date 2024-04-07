package com.bogdan801.heartbeat_test_task.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumberSelector(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    elevation: Dp = 2.dp,
    title: String = "Systolic",
    subtitle: String = "(mmHg)",
    currentValue: Int = 100,
    minValue: Int = 50,
    maxValue: Int = 150,
    onValueChanged: (newValue: Int) -> Unit = {},
) {
    val density = LocalDensity.current
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        contentColor = contentColor,
        shadowElevation = elevation
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val height = maxHeight
                val cellHeight = height / 2.9f
                //snapCorrectionOffset is needed to scroll the list so that the middle visible item was in the center
                val snapCorrectionOffset = remember { abs(with(density){((height - (cellHeight * 3)) / 2).toPx()}) }
                val listState = rememberLazyListState()
                val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)

                //list of possible values to select from
                val possibleItems = remember {
                    (minValue..maxValue)
                        .asIterable()
                        .toMutableList()
                        .apply {
                            add(0, -1)
                            add(-1)
                        }
                }

                //updating current value
                LaunchedEffect(key1 = currentValue) {
                    val id = possibleItems.indexOf(currentValue)
                    listState.scrollToItem(id - 1)
                    listState.scrollBy(snapCorrectionOffset)
                }

                //value currently being displayed on the screen
                val displayedItem by remember {
                    derivedStateOf {
                        val visibleID = listState.firstVisibleItemIndex
                        possibleItems[visibleID + 1]
                    }
                }

                //returning the selected value when a scroll stops
                LaunchedEffect(key1 = listState.isScrollInProgress) {
                    if(!listState.isScrollInProgress) {
                        onValueChanged(displayedItem)
                    }
                }

                //selector
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = listState,
                        flingBehavior = snapBehavior,
                    ) {
                        items(possibleItems){ item ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(cellHeight),
                                contentAlignment = Alignment.Center
                            ){
                                if(item >= 0) {
                                    Text(
                                        text = item.toString(),
                                        style = MaterialTheme.typography.displayMedium,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    }
                }

                //fade boxes
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height / 3)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                        .align(Alignment.TopCenter)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height / 3)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                        .align(Alignment.BottomCenter)
                )

                //divider lines
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = height / 3f)
                        .height(1.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.outline)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = height * 2 / 3f)
                        .height(1.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.outline)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}