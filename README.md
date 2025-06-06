# Kotlin To-Do List App

> A simple practice project for learning core concepts with Kotlin and Android Studio for App Development!
> 
> A lightweight to-do list application. It’s designed to be modular and easy to extend. The core idea is that users can organize their tasks into groups, then tap, swipe, or long-press to perform common actions like editing and deleting.

![AppDemo](https://github.com/user-attachments/assets/2590c1fd-e1b9-4364-9244-2d1ddad49424)

---

## 📘 Overview

This app lets users create named “groups” (folders) for their tasks. You can:
- **Create new groups** via an AlertDialog (with inline validation).
- **Rename** a group by long-pressing its row.
- **Swipe** left or right on any group to delete it instantly.
- (Future) Dive into each group to manage individual tasks.

Validation rules ensure group names are:
- Non-empty
- ≤ 20 characters
- Only letters, numbers, and spaces
- Unique (case-insensitive)

---

## 🛠️ How to Use

### Create a Group
1. Tap the **“Add New Group”** button (floating action button or toolbar button).  
2. Enter a name (up to 20 alphanumeric & space characters).  
3. Tap **Add**.  
4. If the name is invalid (empty, too long, duplicate, invalid characters), a red warning message appears.

### Edit a Group Name
1. Long-press any group row in the list.  
2. An AlertDialog appears with an EditText pre-filled with the current name.  
3. Type a new name (same validation rules).  
4. Tap **Save**.

### Swipe to Delete
1. Swipe left or right on any group row.  
2. The group is removed immediately from the list.  
3. A Toast message confirms deletion.  

---

## 🧠 What I've Learned:

- **Kotlin & Android Basics**  
  Writing Activities, working with XML layouts, and wiring up buttons/Views.

- **RecyclerView & Adapters**  
  Creating a custom `GroupAdapter` with `ViewHolder`, handling clicks and long-presses.

- **Input Validation**  
  Using `TextWatcher` to show real-time warnings, plus regex checks in AlertDialogs.

- **Intent Calling Through Delegation**
  With use of `Interfaces`, we can switch to different layouts/ activities with interfaces being called somewhere else to make it work at places other than the main activity.

- **AlertDialog Customization**  
  Building dialogs with embedded `EditText` and validation messages to create or rename groups.

- **ItemTouchHelper for Gestures**  
  Adding swipe-to-delete functionality with `SimpleCallback` and keeping the list in sync.

- **Managing Data In-Memory**  
  Storing groups in an `AppData` object for quick prototyping (future steps could swap in a database).

- **Lifecycle & Edge Cases**  
  Safely using `adapterPosition` to avoid stale indices after deletions, and guarding against `NO_POSITION`.
  
---


*This is an exercise in leaning into Android, Kotlin and mobile application development fundamentals!*  
