package br.com.fiap.trashit.view.navbar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import br.com.fiap.trashit.R

@Composable
fun BottomNavigation(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(1) }
    val items = listOf(
        BottomNavItem.Coletas,
        BottomNavItem.Lixeira,
        BottomNavItem.Conta
    )

    NavigationBar(
        containerColor = Color.Black,
        contentColor = colorResource(id = R.color.trashIt_green)
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = colorResource(id = R.color.trashIt_green),
                        selectedTextColor = colorResource(id = R.color.trashIt_green),
                        unselectedIconColor = colorResource(id = R.color.disabled_green),
                        unselectedTextColor = colorResource(id = R.color.disabled_green)
                    ),
                    alwaysShowLabel = false,
                    label = { Text(text = item.title)},
                    selected = selectedItem == index,
                    onClick = { selectedItem = index
                                navController.navigate(item.screenRoute)
                              },
                    icon = { Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                    )}
                )
            }
    }
}