package com.garv.pokemon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Drawer(
    items: List<AppEntry.DrawerScreen>,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onItemClick: (AppEntry.DrawerScreen) -> Unit
) {
    ModalDrawerSheet {
        LazyColumn {
            items(items) {
                item ->
                if (item == AppEntry.DrawerScreen.DisplayMode) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = item.dIcon),
                                contentDescription = item.dTitle
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = item.dTitle)
                        }
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { onToggleTheme() }
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(item) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = item.dIcon),
                            contentDescription = item.dTitle
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = item.dTitle)
                    }
                }
            }
        }
    }
}
