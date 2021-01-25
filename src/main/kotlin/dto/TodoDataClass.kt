package dto

data class TodoDataClass(var id:Int,var taskName :String, var dueDate:String, var dueTime:String,var category:String, var repeatMode :String) {
    constructor():this(0,"","","","","")
}