# Design Google Docs: The Concurrency Problem
**Asked in:** Atlassian Interview

---

## 🧩 The Problem
How do multiple users edit the same document **at the same time** without overwriting each other's changes?

---

## ❌ Naive Approach (The Fail)

### Scenario:
- **User A** changes:  
  `"Hello!"` → `"Hello, world!"` *(inserts ", world")*

- **User B** changes:  
  `"Hello!"` → `"Hello"` *(deletes "!")*

---

## 💥 Problem with Naive Approach
- Both users started from the **same base version**
- Each makes independent changes
- The system uses **last-write-wins**

### Result:
- Whoever saves last **overwrites the other**
- **User A’s work is lost** ❌

---

## ⚠️ Key Issue
The system lacks:
- Conflict resolution
- Real-time synchronization
- Awareness of concurrent edits

---

## 🎯 Goal
Design a system where:
- Multiple users can edit simultaneously
- Changes are merged correctly
- No data is lost
---


# 🧪 Sample Input & Expected Output
---
## 🔹 Example : Insert vs Delete
Input:- A insert 0 Hello

Output:- Updated Document: Hello

Input:- B insert 0 World

Output:- Updated Document: WorldHello

Input:- A delete 5 5

Output:- Updated Document: World

Input:-  B insert 5 !!!

Output:- Updated Document: World!!!



