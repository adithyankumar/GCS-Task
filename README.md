# GCS-Task
GCS Task app will fetch latest Github open issues and display based on update time.

- Used MVVM architecture , data binding and viewmodel
- List latest open issue based on updated time
- Displayed issue titles and first 140 characters of the issue body  
- When the user tap on an issue move to issue detail screen with all comments for that issue
- If the comments are not available for a particular issue , shown a popup with message (No Comments available)
- Implemented persistent storage in the application for caching data so that the issues are only fetched once in 24 hours

