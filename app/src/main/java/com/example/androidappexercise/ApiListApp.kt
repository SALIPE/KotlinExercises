package com.example.androidappexercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.androidappexercise.ui.ReceiptViewModel
import com.example.androidappexercise.ui.models.Category
import com.example.androidappexercise.ui.models.Meal
import com.example.androidappexercise.ui.theme.AndroidAppExerciseTheme

@Composable
fun ApiListApp(modifier: Modifier = Modifier) {
    val recipeViewModel: ReceiptViewModel = viewModel()
    val viewstate by recipeViewModel.categoriesState

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewstate.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            viewstate.error != null -> {
                Text("ERROR OCCURRED")
            }

            else -> {
                if(viewstate.mealList.isEmpty()){
                    CategoryScreen(categories = viewstate.list,
                        onCategorySelect = {
                        recipeViewModel.fetchMealsByCategory(it)
                    })
                }else{
                    MealsScreen(meals = viewstate.mealList,
                        cleanMeals = {
                            recipeViewModel.cleanMeals()
                        })
                }

            }
        }
    }


}

@Composable
fun CategoryScreen(categories: List<Category>,
                   onCategorySelect: (c:String)->Unit) {

    Column {
        Text(
            text = "Meals Categories",
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            ),
            fontWeight = FontWeight(800),
            color = Color.Magenta,
            fontSize = 24.sp
        )
        Text(
            text = "Select a category to see the meals",
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            ),
            fontWeight = FontWeight(800),
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp
        )
        LazyVerticalGrid(GridCells.Fixed(2)) {
            items(categories) { category ->
                CategoryItem(category = category,
                    onCategorySelect=onCategorySelect)
            }
        }
    }

}

@Composable
fun MealsScreen(meals: List<Meal>,
                cleanMeals:()->Unit) {
    Column{
        Button(onClick = { cleanMeals() },
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            ),) {
            Text(text = "Back to Categories")
        }
        Text(
            text = "Meals",
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            ),
            fontWeight = FontWeight(800),
            fontStyle = FontStyle.Italic,
            fontSize = 20.sp
        )
        LazyVerticalGrid(GridCells.Fixed(2),
            modifier = Modifier) {
            items(meals) { meal ->
                MealItem(meal = meal)
            }
        }
    }

}

// How each Items looks like
@Composable
fun CategoryItem(category: Category,
                 onCategorySelect: (c:String)->Unit) {

    Column(
        modifier = Modifier
            .clickable { onCategorySelect(category.strCategory) }
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )


        Text(
            text = category.strCategory,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
@Composable
fun MealItem(meal:  Meal) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = rememberAsyncImagePainter(meal.strMealThumb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )


        Text(
            text = meal.strMeal,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ApiListAppPreview() {
    AndroidAppExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ApiListApp()
        }
    }
}