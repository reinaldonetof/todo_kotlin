# Android project with Kotlin language

This is a project created during a Kotlin's Bootcamp by [Digital Innovation One](https://web.digitalinnovation.one/home)

The project was to create a native app for Android, with Kotlin. It's a TO-DO List, that we could create, edit and cancel tasks.
During the course, many features were created, but there were bugs when we try to edit a task:
- First, the button to `edit` the task, wasn't change the text to show the user that he will edit the task, instead of this behavior, the buttons continues with the text as `create a new task`
- Also, when we edited any task and back to the RecyclerView, the text didn't change correctly, because of the DiffCallback inside the TaskListAdapter.

So, both bad behaviors were fixed. Add to this, I implemented a new variable to model Task, called timestamp, to save the date and time in milliseconds and sort the array by the time in RecyclerView.


## Video
https://user-images.githubusercontent.com/47038980/136712597-6b7279ba-7e1e-4aee-b400-6374ff229bdf.mov
