# My CPSC 210 Personal Project

## What will the reading list do?

The reading list will allow users to list books they want to read, view in-progress books, and revisit literature they have read in the past. When a user wants to add a book, they can create a new entry with the book title, author, and genre. Once a user completes a book, they can mark the title as completed, and include a rating and short review of the book.

**There are three main tabs:** 
- Completed books
- In-progress books
- Books for the future.

## Who will use it?

This is a general-use application that requires no extra background, other than in interest in reading. This program will be very useful to those who have a long list of books they would like to read, as well as those who enjoy making notes about books they have completed.

## Why is this project of interest to myself?

Admittedly, I fall into both categories of people mentioned above. I find myself getting lots of book recommendations and have many books that I would like to read, yet I seem to forget many of them. Further, I sometimes forget what I thought of certain books, so an application where I can jot notes and takeaways will be extremely useful.


## User Stories
- As a user, I want to be able to add a book to my reading list.
- As a user, I want to be able to view my unread, in-progress, and completed books. 
- As a user, I want to be able to mark a book as in-progress as well as completed.
- As a user, I want to be able to delete books from my unread and in-progress lists.
- As a user, I want to be able to be able to save my reading list to file.
- As a user, when starting the application, I want the option to load my reading list from file.

## Phase 4: Task 2

Tue Nov 23 17:58:37 PST 2021  
Added new book: Lord of the Rings  
Tue Nov 23 17:58:43 PST 2021  
Added new book: 1984  
Tue Nov 23 17:58:52 PST 2021  
Added new book: Great Gatsby  
Tue Nov 23 17:58:55 PST 2021  
Changed status of: Title: Great Gatsby, By: Fitzgerald. Genre: Tragedy, Status: Compeleted  
Tue Nov 23 17:58:59 PST 2021  
Changed status of: Title: 1984, By: Orwell. Genre: Dystopian, Status: In-progress  
Tue Nov 23 17:59:01 PST 2021  
Lord of the Rings deleted from list 0  

## Phase 4: Task 3
Overall, I am pleased with the design of my UML class diagram. There does not appear to be excessive coupling or overlapping associations that make the project design undesirable. However, if I had more time, here is what I would refactor:
- Create a direct association between BookList and the GUI, instead of using JLists
- Design functionality to only access EventLog through BookList, instead of both Book and BookList