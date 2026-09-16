package com.nawa.cookit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.nawa.cookit.api.api
import com.nawa.cookit.ui.theme.CookItTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookItTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CategoriesRow(modifier = Modifier.padding(innerPadding))
                }
            }
        }

    }
}
@Composable
fun CategoriesRow(
    modifier: Modifier = Modifier
) {

    var categories by remember {
        mutableStateOf<List<CategoryDto>>(emptyList())
    }

    LaunchedEffect(Unit) {
        try {
            val response = api.getCategories()
            categories = response.categories
        } catch (e: Exception) {
            Log.e("API", "Error", e)
        }
    }
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }


    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        items(categories) { category ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    model = category.strCategoryThumb,
                    contentDescription = category.strCategory,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(
                            width = 4.dp,
                            brush = rainbowColorsBrush,
                            shape = CircleShape
                        )
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = category.strCategory,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun MealCategoryPreview() {
    CategoriesRow()
}