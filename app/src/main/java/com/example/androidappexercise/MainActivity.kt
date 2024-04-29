package com.example.androidappexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidappexercise.ui.theme.AndroidAppExerciseTheme
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAppExerciseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ApiListApp()
                }
            }
        }
    }
}

data class TodoTask(
    val id: Int,
    var task: String
)

@Composable
fun TodoApp( modifier: Modifier = Modifier) {
    var todoItems by remember { mutableStateOf(listOf<TodoTask>(
        TodoTask(
            id = 0,
            task = "Initial Example task"
        )
    )) }
    var doneItems by remember { mutableStateOf(listOf<TodoTask>(
        TodoTask(
            id = 1,
            task = "Initial Example done task with big text to see the behavior"
        )
    )) }
    val (textInput, setTextInput) = remember { mutableStateOf("") }
    val (isShowDialog, setIsShowDialog) = remember { mutableStateOf(false) }
    val (selectedId, setSelectedId) = remember { mutableStateOf<Int?>(null) }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp),

                ) {

                Text(text = "TODO TASK APP",
                    modifier= Modifier.align(
                        Alignment.CenterHorizontally
                    ),
                    fontWeight = FontWeight(800),
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp)
                OutlinedTextField(value = textInput,
                    placeholder = { Text(text = "Write your task here!") },
                    onValueChange = {
                    setTextInput(it)
                },
                    modifier= Modifier.width(300.dp))
                Button(
                    onClick = {
                        if(textInput.isNotBlank()){
                            val newItem = TodoTask(
                                id = todoItems.size+doneItems.size + 1,
                                task = textInput
                            )
                            todoItems = todoItems + newItem
                        }
                        setTextInput("")
                    },
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    ),
                    shape = RoundedCornerShape(10.dp)

                ) {
                    Text(text = "Add Task")

                }


            }
        }
        Text(text = "To-Do List",
            modifier= Modifier.align(
                Alignment.CenterHorizontally
            ),
            fontWeight = FontWeight(800),
            fontSize = 20.sp)
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
        ) {
            items(todoItems) { item ->
                TodoListItem(
                    item = item,
                    isDoneList = false,
                    onCheckClick ={
                        val checkitem = todoItems.find {
                            it.id == item.id
                        }
                        if(checkitem != null){
                            doneItems = doneItems + checkitem
                            todoItems = todoItems.filter { it.id != checkitem.id }
                        }


                    },
                    onDeleteClick = {})
            }
        }

        Text(text = "Done List",
            modifier= Modifier.align(
                Alignment.CenterHorizontally
            ),
            fontWeight = FontWeight(800),
            fontSize = 20.sp)
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
        ) {
            items(doneItems) { item ->
                TodoListItem(
                    item = item,
                    isDoneList = true,
                    onCheckClick ={
                        val editeditem = doneItems.find {
                            it.id == item.id
                        }
                        if(editeditem != null){
                            todoItems = todoItems + editeditem
                            doneItems = doneItems.filter { it.id != editeditem.id }
                        }

                    },
                    onDeleteClick = {
                        setSelectedId(item.id)
                        setIsShowDialog(true)

                    })
            }
        }
    }

    if (isShowDialog) {
        AlertDialog(onDismissRequest = { setIsShowDialog(false)},
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        if (selectedId != null) {
                            doneItems = doneItems.filter { it.id != selectedId }
                            setIsShowDialog(false)
                            setSelectedId(null)
                        }
                    }) {
                        Text("Delete")
                    }
                    Button(onClick = { setIsShowDialog(false)}) {
                        Text("Cancel")
                    }
                }

            },
            title = { Text("Delete Task") },
            text = {
                Text("Are you sure you want to delete this task?")
            }
        )

    }



}

@Composable
fun TodoListItem(
    item: TodoTask,
    isDoneList: Boolean,
    onCheckClick: () -> Unit,
    onDeleteClick: ()->Unit
) {
    val shapeColor:Color = if (isDoneList) Color.Green else Color.DarkGray
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(
                    2.dp,
                    color = shapeColor
                ),
                shape = RoundedCornerShape(20)
            )
    ) {
        Checkbox(checked = isDoneList,
            onCheckedChange = {
                onCheckClick()
            }
        )

        Text(text = item.task,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.width(240.dp).padding(5.dp))
        if(isDoneList){
            IconButton(
                modifier = Modifier.padding(
                    start = 2.dp
                ),
                onClick = onDeleteClick) {
                Icon(imageVector = Icons.Filled.Delete,
                    tint = Color.Red,
                    contentDescription = null)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidAppExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TodoApp()
        }
    }
}