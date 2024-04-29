package com.example.androidappexercise.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidappexercise.recipeService
import com.example.androidappexercise.ui.models.Category
import com.example.androidappexercise.ui.models.Meal
import kotlinx.coroutines.launch

class ReceiptViewModel : ViewModel() {

    private val _categoryState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoryState

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories()
                _categoryState.value = _categoryState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = "Error fetching Categories ${e.message}"
                )
            }
        }
    }

    fun fetchMealsByCategory(category:String) {
        viewModelScope.launch {
            try {
                val response = recipeService.getMealsByCategory(category)
                _categoryState.value = _categoryState.value.copy(
                    mealList = response.meals,
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = "Error fetching Meals ${e.message}"
                )
            }
        }
    }

    fun cleanMeals() {
        viewModelScope.launch {
            try {

                _categoryState.value = _categoryState.value.copy(
                    mealList = emptyList(),
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = "Error fetching Meals ${e.message}"
                )
            }
        }
    }

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val mealList: List<Meal> = emptyList(),
        val error: String? = null
    )

}