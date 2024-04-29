package com.example.androidappexercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidappexercise.ui.CountViewModel
import com.example.androidappexercise.ui.theme.AndroidAppExerciseTheme

@Composable
fun IncrementApp(modifier: Modifier = Modifier) {

    val viewModel: CountViewModel = viewModel()

    Column {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp),
            ) {

                Text(
                    text = "Count: ${viewModel.count.value}",
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    ),
                    fontWeight = FontWeight(800),
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp
                )
                Row {
                    Button(
                        onClick = {
                            viewModel.decrement()
                        },
                        shape = RoundedCornerShape(10.dp)

                    ) {
                        Text(text = "Decrement")
                    }

                    Button(
                        onClick = {
                            viewModel.increment()
                        },
                        modifier = Modifier.padding(start = 10.dp),
                        shape = RoundedCornerShape(10.dp)

                    ) {
                        Text(text = "Increment")
                    }
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IncrementPreview() {
    AndroidAppExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            IncrementApp()
        }
    }
}

