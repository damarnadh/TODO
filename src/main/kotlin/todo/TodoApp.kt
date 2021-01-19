package todo

import dto.TodoDataClass
import java.util.*
import kotlin.collections.ArrayList

class TodoApp {
    private var todoTaskList = ArrayList<TodoDataClass>()

    private fun customMode():String{
        val read = Scanner(System.`in`)
        println("Enter a number")
        val num = read.nextInt()
        println("Choose one option \n1.days \n" + "2.weeks \n" + "3.months")
        when(read.nextInt()){
            1->return "$num days"
            2->return "$num weeks"
            3->return "$num months"
            else->{
                println("choose valid options")
                customMode()
            }
        }
        return ""
    }

    private fun chooseRepeatMode():String{
        println("Choose Repeat option \n1. No repeat \n2. Once a day \n3. Once a week \n4. Once a month \n5. Once a year \n6. Custom")
        val read = Scanner(System.`in`)
        when(read.nextInt()){
            1->return "No repeat"
            2->return "Once a day"
            3->return "Once a week"
            4->return "Once a month"
            5->return "Once a year"
            6->return customMode()
            else->{
                println("choose valid options")
                chooseRepeatMode()
            }
        }
        return ""
    }

    private fun chooseCategoryList():String{
        println("Choose list to add \n1. Personal \n2. Shopping \n3. Wishlist \n4. Work \n5. Custom")
        val read = Scanner(System.`in`)
        when(read.nextInt()){
            1->return "Personal"
            2->return "Shopping"
            3->return "Wishlist"
            4->return "Work"
            5->{
                println("Enter a reason:")
                return read.nextLine()
            }
        }
        return ""
    }

    private fun addTodoList() {
        val dataObj = TodoDataClass()
        val read = Scanner(System.`in`)

        println("Enter the Task Name")
        dataObj.taskName=read.nextLine()

        println("Enter the due Date")
        dataObj.dueDate = read.nextLine()

        println("Enter the due Time")
        dataObj.dueTime=read.nextLine()

        println("Enter repeatMode")
        dataObj.repeatMode=chooseRepeatMode()

        println("Enter category")
        dataObj.category =chooseCategoryList()

        if(!todoTaskList.contains(dataObj)) {
            todoTaskList.add(dataObj)
        }else{
            println("remainder already exists")
        }
        displayTodoList()
    }

    private fun displayTodoList() {
        println("TODO List\n----------")
        for(task in todoTaskList){
            println("${task.taskName} \n${task.dueDate} ${task.dueTime} \n${task.category} \n${task.repeatMode} \n")
        }
        doTheTask()
    }

    fun doTheTask() {
        println()
        println("---------------------------\n1. Add new TODO list \n2. Display \n3. Exit \n----------------------------")
        println()

        val read = Scanner(System.`in`)
        println("Enter the option")
        when (read.nextInt()) {
            1 -> addTodoList()
            2 -> displayTodoList()
            3 -> return
            else->{
                println("choose valid operation")
                doTheTask()
            }
        }
    }
}
fun main(){
    println()
    println("TODO")
    val todoAppObj = TodoApp()
    todoAppObj.doTheTask()
}