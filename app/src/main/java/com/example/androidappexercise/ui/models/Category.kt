package com.example.androidappexercise.ui.models

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)
data class CategoriesResponse(val categories: List<Category>)
data class MealsResponse(val meals: List<Meal>)
