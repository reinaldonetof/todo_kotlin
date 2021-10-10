package com.example.todolist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.datasource.TaskDataSource
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.example.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.tilTitle.text = it.title
                binding.tilDescription.text = it.description
                binding.tilDates.text = it.date
                binding.tilTime.text=it.time
                binding.btnNewTask.text = getString(R.string.label_update_task)
            }
        }

        insertListeners()
    }

    private fun insertListeners() {
        var dateStamp = Date()
        var timeMs = 0

        binding.tilDates.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                dateStamp = Date(it + offset)
                binding.tilDates.text = dateStamp.format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilTime.editText?.setOnClickListener {
            val timePicker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
            timePicker.addOnPositiveButtonClickListener {
                timeMs = timePicker.hour*3600000 + timePicker.minute*60000
                binding.tilTime.text = "${timePicker.hour.toString().padStart(2, '0')}:${
                    timePicker.minute.toString().padStart(2, '0')
                }"
            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancelTask.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            if (binding.tilTitle.text.isNullOrEmpty() || binding.tilDates.text.isNullOrEmpty() || binding.tilTime.text.isNullOrEmpty()) {
                return@setOnClickListener
            }

            val timestamp = dateStamp.time + timeMs

            val task = Task(
                title = binding.tilTitle.text,
                description = binding.tilDescription.text,
                date = binding.tilDates.text,
                time = binding.tilTime.text,
                id = intent.getIntExtra(TASK_ID, 0),
                timestamp = timestamp
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }


}