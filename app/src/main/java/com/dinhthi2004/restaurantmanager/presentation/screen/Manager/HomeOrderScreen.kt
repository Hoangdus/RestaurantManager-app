package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.bill.BillData
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.IngreCT

import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.OrderItem
import com.dinhthi2004.restaurantmanager.viewmodel.BillViewModel
import com.dinhthi2004.restaurantmanager.viewmodel.DishViewModel
import com.dinhthi2004.restaurantmanager.viewmodel.OrderViewModel


@Composable
fun HomeOrderScreen(navigationController: NavHostController) {
    val billViewModel: BillViewModel = viewModel()
    val orderViewModel: OrderViewModel = viewModel()
    val token = TokenManager.token
    Log.d("tokeen", "OrderViewModel: " + token)

    LaunchedEffect(Unit) {
        if (token != null) {
            billViewModel.getBills(token)
        }
    }

    val order by billViewModel.bills.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var selectedOrder by remember { mutableStateOf<BillData?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(380.dp)
                .height(200.dp)
                .padding(top = 5.dp)
        )

        Text(
            text = "Danh Sách Đơn Hàng",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 10.dp, top = 5.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(order) { currentOrder ->
                OrderItem(order = currentOrder) { selected ->
                    selectedOrder = selected
                    showDialog = true
                }
            }
        }
    }

    if (showDialog && selectedOrder != null) {
        IngreCT(navigationController, billData = selectedOrder,onDismiss = {
            showDialog = false
            selectedOrder = null
        })
    }
}

