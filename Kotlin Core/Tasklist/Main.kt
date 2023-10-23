package tasklist

fun main() {
    val todoist = Todoist()
    todoist.createTaskList("Stage1")
    val taskList = todoist.taskLists.find { it.name == "Stage1" }
    taskList?.showTasks()
}

class Todoist {
    private val _taskLists: MutableList<TaskList> = mutableListOf()
    val taskLists: List<TaskList> get() = _taskLists

    // fun addTaskList(taskList: TaskList) = _taskLists.add(taskList)

    fun createTaskList(name: String = "Stage1") {
        // Create a new task list
        val taskList = TaskList(name)

        // Request tasks
        println("Input the tasks (enter a blank line to end):")

        var inputUser = readln().trim()

        if (inputUser.isEmpty()) {
            println("No tasks have been input")
            return
        } else {
            do {
                if (inputUser.isNotEmpty()) taskList.addTask(name = inputUser)
                inputUser = readln().trim()
            } while (inputUser.isNotEmpty())
        }

        _taskLists.add(taskList)
    }
}

class TaskList(val name: String) {
    private val _tasks: MutableList<Task> = mutableListOf()
    val tasks: List<Task> get() = _tasks

    fun addTask(name: String) {
        val newTask = Task(name)
        _tasks.add(newTask)
    }

    fun showTasks() {
        var taskNumber = 1
        this.tasks.forEach {
            print(taskNumber)
            if (taskNumber < 10) print("  ") else print(" ")
            println(it.name)
            taskNumber++
        }
    }
}

class Task(val name: String)