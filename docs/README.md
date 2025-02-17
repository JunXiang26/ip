# Dubey User Guide

![Screenshot of Dubey Application GUI](https://JunXiang26.github.io/ip/Ui.png)

### A simple chatbot that does task management
:warning: *Please note that all input commands are case-sensitive*

## Adding todos

Adds todo task to the task list and echoes task if added successfully

Input command: `todo <description>`

Output - Chatbot will reply with following response:

```
Processed: todo <Description>
Got it. I've added this task:
[T][] <description>
Now you have x tasks in the list.
```

:information_source: **Note that x refers to the number of task in the list currently*

## Adding deadlines

Adds deadline task to the task list and echoes task if added successfully

Input command: `deadline <description> /by <date>` 

:warning: **Date should be YYYY-MM-DD format*

Output - Chatbot will reply with following response:

```
Processed: deadline <Description> /by YYYY-MM-DD
Got it. I've added this task:
[D][] <description> (by: <Date formated>)
Now you have x tasks in the list.
```
:information_source: **Note that x refers to the number of task in the list currently*

## Adding events

Adds event task to the task list and echoes task if added successfully

Input command: `event <description> /from <date> /to <date>`

:information_source: **Date is read as a string and need not be in YYYY-MM-DD format*

Output - Chatbot will reply with following response:

```
Processed: event <description> /from <date> /to <date>
Got it. I've added this task:
[E][] <description> (from: <Date String> to: <Date String>)
Now you have x tasks in the list.
```
:information_source: **Note that x refers to the number of task in the list currently*

## Listing tasks

Shows all the tasks that has been recorded previously

Input command: `list`

Output - Chatbot will reply with following response, listing all tasks that has been recorded

Example output:
```
Processed: list
Here are the takss in you list:
1. [T][X] Read book
2. [D][X] Submit assignment (by: Jan 31 2025)
3. [E][] Cycling Event (from: May 05 2025 to: May 07 2025)
4. [T][] Return book
```


## Deleting task

Deletes a task from the recorded task list

Input command: `delete <index>`

:warning: **index should be in the range of current task list length*

Output - Chatbot will reply with following response

```
Processed: delete <index>
Noted. I've removed this task:
[][] <description> 
Now you have x tasks in the list.
```
:information_source: **Note that x refers to the number of task in the list currently*

## Marking / Unmarking tasks

Marks / unmarks the task of the task list using the index of the task list

Input command for mark: `mark <index>`

Input command for unmark: `unmark <index>`

:warning: **index should be in the range of current task list length*

Output - Chatbot will reply with following response, marking / unmarking the task with corresponding index

Example output:
```
Processed: mark 2
Nice! I've marked this task as done:
[D][X] Submit assignment (by: Jan 31 2025)
```

## Finding tasks

Finds task descriptions which contains keyword

Input command: `find <keyword>`

Output - Chatbot will reply with following response, listing all tasks that has been found containing the keyword

Example output:
```
Prcoessd: find boo
Here are the matching tasks in your list:
1. [T][X] Read book
2. [T][] Return book
```