package com.nawa.cookit.model

// Categories Response
data class CategoriesResponse(
    val categories: List<CategoryDto>
)

data class CategoryDto(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
)

// Meals Filter Response
data class MealsResponse(
    val meals: List<MealDto>?
)

data class MealDto(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)