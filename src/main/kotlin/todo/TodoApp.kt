package todo

import dto.TodoDataClass
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TodoApp {
    //To maintain todo task list
    private var mTodoTaskList = ArrayList<TodoDataClass>()
    //To maintain id for todo task list
    private var mId= 0

    private val mDateFormat2: DateFormat = SimpleDateFormat("dd-MM-yyyy") //To support date format 1
    private val mDateFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy") //To support date format 2

    private fun customMode():String{ // To choose custom repeat mode
        val lRead = Scanner(System.`in`)
        println("Enter a number")
        var lNum = 0
        if(lRead.hasNextInt()){
            lNum = lRead.nextInt()
        }else{
            println("please enter a number")
            customMode()
        }
        println("Choose one option \n1.days \n" + "2.weeks \n" + "3.months")
        var lOption = 0
        if(lRead.hasNextInt()){
            lOption = lRead.nextInt()
        }
        when(lOption){
            1->return "$lNum days"
            2->return "$lNum weeks"
            3->return "$lNum months"
            else->{
                println("choose valid options $lOption")
                customMode()
            }
        }
        return ""
    }

    private fun chooseRepeatMode():String{ //To choose repeat mode
        println("Choose Repeat option \n1. No repeat \n2. Once a day \n3. Once a week \n4. Once a month \n5. Once a year \n6. Custom")
        val lRead = Scanner(System.`in`)
        var lOption=0
        if(lRead.hasNextInt()){
            lOption = lRead.nextInt()
        }
        when(lOption){
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

    private fun chooseCategoryList():String{ //To choose category list
        println("Choose list to add \n1. Personal \n2. Shopping \n3. Wishlist \n4. Work \n5. Custom")
        val lRead = Scanner(System.`in`)
        var lReason =""
        var lOption=0
        if(lRead.hasNextInt()){
            lOption = lRead.nextInt()
        }
        when(lOption){
            1->return "Personal"
            2->return "Shopping"
            3->return "Wishlist"
            4->return "Work"
            5->{//To choose custom reason
                val lReadReason = Scanner(System.`in`)
                println("Enter a reason:")
                lReason= lReadReason.nextLine()
                if(lReason != ""){
                    return lReason
                }
                println("Enter a valid reason:")
                chooseCategoryList()

            }
            else->{
                println("Choose valid option")
                chooseCategoryList()
            }
        }
        return lReason
    }

    private fun getValidTaskName():String{ //To get valid task name(i.e., valid string)
        val lRead = Scanner(System.`in`)
        val lTaskName = lRead.nextLine()
        if(lTaskName.filter {it in '0'..'9'  }.length==lTaskName.length){ //condition to check string contains only numbers
            println("enter a valid name")
            getValidTaskName()
        }
        return lTaskName
    }
    private fun getValidDate():String{ // Function for date validation

        val lRead = Scanner(System.`in`)
        val lDate = lRead.nextLine()

        if(inDateFormat1(lDate)){ //To check whether date is in date format1
            return lDate
        }
        if(inDateFormat2(lDate)){//To check whether date is in date format2
            return lDate
        }

        return getValidDate()
    }

    private fun inDateFormat2(pDate: String):Boolean {//To check whether date is in date format2
        mDateFormat2.isLenient = false
        try {
            mDateFormat2.parse(pDate)
            if(System.currentTimeMillis()- 1000*60*60*24>mDateFormat2.parse(pDate).time){
                println("Invalid date or The date you entered is already passed away")
                return false
            }
            return true
        } catch (e: ParseException) {
            return false
        }

    }

    private fun inDateFormat1(pDate: String):Boolean {//To check whether date is in date format1
        mDateFormat.isLenient = false
        try {
            mDateFormat.parse(pDate)
            if(System.currentTimeMillis()- 1000*60*60*24>mDateFormat.parse(pDate).time){ //date is past
                println("Invalid date or The date you entered is already passed away")
                return false
            }
            return true
        } catch (e: ParseException) {
            return false
        }
    }

    private fun getValidTime(pDate:String):String{//To check whether time is in time format
        val lRead = Scanner(System.`in`)
        val lTime = lRead.nextLine()
        val timeFormat: DateFormat = SimpleDateFormat("h:mm a")
        timeFormat.isLenient = false
        try {
            timeFormat.parse(lTime)
        } catch (e: ParseException){
            println("Enter valid Time")
            return getValidTime(pDate)
        }
        if(checkIfToday(pDate)){
            return checkTimeIsValid(pDate,lTime)
        }

        return lTime
    }

    private fun checkTimeIsValid(pDate: String, pTime: String): String { //Function to check time is valid
        //println("checkTimeIsValid")
        val timeFormat: DateFormat = SimpleDateFormat("h:mm a")
        val lTime = timeFormat.format(Date())

        if (timeFormat.parse(lTime).time < timeFormat.parse(pTime).time) {
            //println("time valid")
            return pTime
        }
        println("please enter valid time *The time should be after current time*")
        return getValidTime(pDate)
    }

    private fun checkIfToday(pDate: String): Boolean { //Function to check whether given date is today or not
        return try {//To check whether given date is today or not for date format2
            System.currentTimeMillis()- 1000*60*60*24<mDateFormat2.parse(pDate).time && System.currentTimeMillis()>mDateFormat2.parse(pDate).time
        }catch (e:ParseException) {
            return try {//To check whether given date is today or not for date format1
                System.currentTimeMillis() - 1000 * 60 * 60 * 24 < mDateFormat.parse(pDate).time && System.currentTimeMillis() > mDateFormat.parse(
                    pDate
                ).time
            }catch (e:ParseException){
                println("invalid date")
                false
            }
        }

    }

    private fun addTodoList() { //Function to add records to todo list
        val lDataObj = TodoDataClass()//object for data class

        lDataObj.id = mId +1
        mId++

        println("\nEnter the Task Name")
        lDataObj.taskName=getValidTaskName()

        println("\nEnter the due date Eg : (19-Jan-2021 or 19-01-2021)")
        lDataObj.dueDate = getValidDate()

        println("\nEnter the due time Eg : (05:30 pm/PM)")
        lDataObj.dueTime=getValidTime(lDataObj.dueDate)

        println("\nEnter repeatMode")
        lDataObj.repeatMode=chooseRepeatMode()

        println("\nEnter category")
        lDataObj.category =chooseCategoryList()

        if(!mTodoTaskList.contains(lDataObj)) {
            mTodoTaskList.add(lDataObj)
        }else{
            println("remainder already exists")
        }
        displayTodoList()
    }

    private fun displayTodoList() { //Function to display todo list
        println("\nTODO List\n----------")
        if(mTodoTaskList.isNotEmpty()){
            viewTodoList()
        }else{
            println("No records found")
        }
        doTheTask()
    }
    private fun viewTodoList(){ //Function to iterate over each object of list.
        for(iTask in mTodoTaskList){
            println("id : ${iTask.id} \n${iTask.taskName} \n${iTask.dueDate} ${iTask.dueTime} \n${iTask.category} \n${iTask.repeatMode} \n")
        }
    }

    fun doTheTask() { //Function to display options to be performed on application
        println()
        println("---------------------------\n1. Add new TODO list \n2. Display \n3. Edit \n4. Delete \n5. Exit\n----------------------------")
        println()

        val lRead = Scanner(System.`in`)
        println("Enter the option")
        var lOption =0
        if(lRead.hasNextInt()){
            lOption = lRead.nextInt()
        }else{
            println("please enter a number")
            doTheTask()
        }

        when (lOption) {
            1 -> addTodoList()
            2 -> displayTodoList()
            3 -> editList()
            4 -> deleteRecord()
            5 -> exitProgram()
            else->{
                println("choose valid operation")
                doTheTask()
            }
        }
    }

    private fun exitProgram(){
        println("Do you want to exit ? \n1.Yes \n2.No")
        val lRead = Scanner(System.`in`)
        var lOption = 0
        if(lRead.hasNextInt()){
            lOption = lRead.nextInt()
        }
        when(lOption){
            1->return
            2->doTheTask()
            else->{
                println("Enter valid option")
                exitProgram()
            }
        }
    }

    private fun deleteRecord() { //Function to delete record from list
        viewTodoList()
        if(mTodoTaskList.isEmpty()){
            println("There are no records to delete")
        }else{
            val lRecord = getRecord()
            mTodoTaskList.remove(lRecord)
        }
        doTheTask()
    }

    private fun checkEditList(){ //Function to check whether list to edit is empty or not
        if(mTodoTaskList.isNotEmpty()){
            viewTodoList()
        }else{
            println("“There is no TODO List to Edit”")
        }
    }

    private fun getRecord():TodoDataClass{ //Function to return record of given id..
        val lRead = Scanner(System.`in`)
        println("Enter the ID you want Update :")
        var lId = 0
        if(lRead.hasNextInt()){
            lId = lRead.nextInt()
        }else{
            println("please enter a number")
            getRecord()
        }
        if(lId==0||lId>mTodoTaskList.size){
            println("Invalid record.. please choose valid record Id")
            getRecord()
        }
        var lRecord = TodoDataClass()
        for(iItem in mTodoTaskList){
            if(iItem.id ==lId){
                lRecord = iItem
            }
        }
        return lRecord
    }

    private fun editList(){ //Function to give access to edit list
       checkEditList()
       if(mTodoTaskList.isEmpty()){
           doTheTask()
           return
       }
       val lRecord = getRecord()
       var lOption = getOptionCancel()
       while(lOption<6){
           when(lOption){
               1->{
                   println("Enter the task name")
                   val readTask = Scanner(System.`in`)
                   lRecord.taskName= readTask.nextLine()
                   lOption= getOption()
               }
               2->{
                   println("Enter the Due Date")
                   lRecord.dueDate= getValidDate()
                   lOption= getOption()
               }
               3->{
                   println("Enter the Due Time")
                   lRecord.dueTime= getValidTime(lRecord.dueDate)
                   lOption= getOption()
               }
               4->{
                   println("Enter repeat option")
                   lRecord.repeatMode=chooseRepeatMode()
                   lOption= getOption()
               }
               5->{
                   println("Enter reason")
                   lRecord.category=chooseCategoryList()
                   lOption= getOption()
               }
               else->{
                   println("option invalid")
               }
           }
           if(lOption==6){
               println("id : ${lRecord.id} \n${lRecord.taskName} \n${lRecord.dueDate} ${lRecord.dueTime} \n${lRecord.category} \n${lRecord.repeatMode} \n")
           }
       }
        doTheTask()
    }

    private fun getOptionCancel(): Int {//Function to Choose option you want to Edit with cancel returns option
        val lRead =Scanner(System.`in`)
        var lOption =0
        println("------------------ Choose option you want to Edit -------------------\n" +
                "1. Task Name\n" +
                "2. Due Date\n" +
                "3. Due Time\n" +
                "4. Repeat option\n" +
                "5. Reason\n" +
                "6. cancel ")
        if(lRead.hasNextInt()){
            lOption = lRead.nextInt()
        }else{
            println("please enter a number")
            getOptionCancel()
        }
        if(lOption==0 || lOption>6){
            println("Please choose number in given options")
            getOptionCancel()
        }
        return lOption
    }

    private fun getOption():Int{//Function to Choose option you want to Edit with save returns option
        val lRead =Scanner(System.`in`)
        var lOption =0
        println("------------------ Choose option you want to Edit -------------------\n" +
                "1. Task Name\n" +
                "2. Due Date\n" +
                "3. Due Time\n" +
                "4. Repeat option\n" +
                "5. Reason\n" +
                "6. Save ")
        if(lRead.hasNextInt()){
            lOption = lRead.nextInt()
        }else{
            println("please enter a number")
            getOption()
        }
        return lOption
    }
}
fun main(){//Application driver function
    println()
    println("TODO")
    val todoAppObj = TodoApp()
    todoAppObj.doTheTask()
}