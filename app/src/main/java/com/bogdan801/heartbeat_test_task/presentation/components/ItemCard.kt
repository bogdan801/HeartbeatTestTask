package com.bogdan801.heartbeat_test_task.presentation.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.bogdan801.heartbeat_test_task.domain.model.Item
import com.bogdan801.heartbeat_test_task.presentation.util.toFormattedString

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    height: Dp = 96.dp,
    elevation: Dp = 2.dp,
    item: Item,
    onDeleteItemClick: (id: Int) -> Unit = {},
    onEditItemClick: (id: Int) -> Unit = {}
) {
    var showDropDown by remember { mutableStateOf(false) }
    var longClickOffset by remember { mutableStateOf(DpOffset.Zero) }
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shadowElevation = elevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = { offset ->
                            showDropDown = true
                            longClickOffset = DpOffset(x = offset.x.toDp(), y = offset.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        }
                    )
                }
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(56.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.systolicPressure.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = item.diastolicPressure.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.secondary)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = item.datetime.toFormattedString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Pulse: ${item.pulse} BPM",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        DropdownMenu(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surface
            ),
            expanded = showDropDown,
            onDismissRequest = { showDropDown = false },
            offset = longClickOffset.copy(y = longClickOffset.y - height)
        ) {
            DropdownMenuItem(
                text = {
                    Text("Delete item")
                },
                colors = MenuDefaults.itemColors(
                    textColor = MaterialTheme.colorScheme.onBackground
                ),
                onClick = {
                    onDeleteItemClick(item.itemID)
                    showDropDown = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text("Edit item")
                },
                colors = MenuDefaults.itemColors(
                    textColor = MaterialTheme.colorScheme.onBackground
                ),
                onClick = {
                    onEditItemClick(item.itemID)
                    showDropDown = false
                }
            )
        }
    }
}