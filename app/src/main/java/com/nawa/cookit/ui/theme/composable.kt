package com.nawa.cookit.ui.theme

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.nawa.cookit.api.api
import com.nawa.cookit.model.CategoryDto
import com.nawa.cookit.model.MealDto
import kotlin.collections.emptyList

@Composable
fun MealsColumn(
    category:String,
    modifier: Modifier = Modifier
) {

    var meals by remember {
        mutableStateOf<List<MealDto>>(emptyList())
    }

    LaunchedEffect(category) {
        try {
            val response = api.getMealsByCategory(category)
            meals = response.meals ?: emptyList()
            Log.d("API", "Meals: $meals")
        } catch (e: Exception) {
            Log.e("API", "Error", e)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(meals) { meal ->
            FoodItemCard(
                title = meal.strMeal,
                imageUrl = meal.strMealThumb
            )

        }
    }
}





@Composable
fun CategoriesRow(
    selectedCategory: String?,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
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
            val isSelected = category.strCategory == selectedCategory

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {

                AsyncImage(
                    model = category.strCategoryThumb,
                    contentDescription = category.strCategory,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .clickable { onClick(category.strCategory) }
                        .border(
                            width = if (isSelected) 4.dp else 2.dp,
                            brush = if (isSelected)  SolidColor(Color.Green)else rainbowColorsBrush,
                            shape = CircleShape
                        )
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = category.strCategory,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}
@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun MealCategoryPreview() {
    CategoriesRow("Beef",onClick = {})
}
@Preview(showSystemUi = true)
@Composable
private fun MealsColumnPreview() {
    MealsColumn("Beef")
}