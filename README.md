# CPSC 210 Project - Peak Progress

## What will the application do?
This application will allow rock climbers to keep track of their climbs, indoor and outdoor, regardless of the location. There will be the ability to see the start and end date of projects. The climbs can be sorted by **climbing grade** and **climbing type** (Any grading system can be used, as long as the user remembers how they have graded their climbs).

Climbing types will include:
- boulders
- top ropes
- lead climbs

## Who will use it?
I hope this wil be used by fellow climbers who want to track their progress and keep up a log of their projects, indoors and outdoors. This application will be especially useful for amateur climbers who may not have the time to climb as regularly. Keeping track of these long term projects will hopefully be helpful for their climbing goals.

## Why is this project of interest to you?
I personally have grown to love rock climbing over the last year and have found a lack of a free application that allows me to track my climbs. I find that remembering what routes I have and haven't done in a gym becomes really difficult, especially with larger gyms. I also want to progress into some outdoor climbs and I think it would be really valuable to be able to put all these projects into one application over time.

## User Stories:
- As a user, I want to be able to add a new climb that I just discovered to my current, ongoing list of projects, as well as include its difficulty and location.
- As a user, I want to be able to remove climbs from my list of projects, without having to specify a specific reason.
- As a user, I want to be able to select a location and see all the climbs that I have stored from that location, regardless of difficulty.
- As a user, I want to be able to select a type of climb and see all the climbs that are that type of climb, regardless of the location and difficulty.
- As a user, I want to be able to select a climbing grade and see all the climbs that I have stored for that level, regardless of location.
- As a user, I want to be able to select a location and a grade and see all the climbs that fit that criteria.
- As a user, I want to be able to save all my climbs to view another time when I quit the application from the console.
- As a user, I want to be able to load climbs that I have previously saved whenever I want.
- As a user, I want to be able to save all my climbs to view another time whenever I want from the menu in the GUI.
- As a user, I want to be able to access the home page at any time from the menu in the GUI.


## Instructions for Grader:
- My visual component is shown on startup of the application. It is a simple design that I created with digital tools. You can leave this view by pressing "Start!"
- You can add multiple X to Y by clicking "Add new climb" and filling in the text fields. after clicking save, the climb (X) will have been added to the list of projects (Y) and you will be redirected to the home page, where you may add as many climbs as you wish.
- You can remove a climb you've previously added by clicking "Remove a climb" and filling in the text field. After clicking "Remove" the climb will be removed from the list and bring you back to the home page. Note: nothing will happen if you try to remove a climb that has not yet been added.
- You can view all the climbs you have added to the list of projects by clicking on "View climbs" and then clicking "View all climbs". Clicking "Done" will bring you back to the home page.
- You can view all the climbs you have added of a specific type by clicking "View climbs" and then clicking "View by type" and filling out the text field. Clicking next will display those climbs (or a blank screen if there's none) and then clicking "Done" will bring you back to the home page.
- By doing the steps from above, you can also sort by location, grade, as well as grade and location simultaneously by clicking "View by location", "View by grade", and "View by grade and location" respectively.
- You can access the home page at any time by clicking "Home" from the menu.
- You can add climbs from the menu by clicking "Climbs" in the menu and clicking "Add climb".
- You can also view climbs from the menu by clicking "Climbs" in the menu and clicking "View climbs".
- You can save your climbs to file at any time by clicking "File" in the menu and selecting "Save". This will override anything you have previously saved to file so be careful.
- You can also load climbs on file at any time by clicking "File" in the menu and selecting "Load". This will remove anything you added that wasn't on file so be careful.

## Phase 4: Task 2 (Sample of event logs)
Tue Aug 06 21:30:05 PDT 2024
Loaded projects from file
Tue Aug 06 21:30:05 PDT 2024
Added climb name: test boulder, type: Boulder, location: gym, grade: v4

Tue Aug 06 21:30:05 PDT 2024
Added climb name: test top rope, type: Top Rope, location: gym, grade: 6a

Tue Aug 06 21:30:05 PDT 2024
Added climb name: test lead climb, type: Lead Climb, location: squamish, grade: 8a

Tue Aug 06 21:30:30 PDT 2024
Added climb name: DOOM, type: Boulder, location: gym, grade: v11

Tue Aug 06 21:30:33 PDT 2024
Displayed all saved climbs
Tue Aug 06 21:30:41 PDT 2024
Sorted climb by difficulty v11
Tue Aug 06 21:30:44 PDT 2024
Unsuccessful in removing a climb
Tue Aug 06 21:30:48 PDT 2024
Removed climb name: DOOM, type: Boulder, location: gym, grade: v11

Tue Aug 06 21:30:50 PDT 2024
Saved projects to file
Tue Aug 06 21:30:54 PDT 2024
Displayed all saved climbs

## Phase 4: Task 3 (Package refactoring)
Quite honestly, from the very beginning, I wanted to implement an abstract class of climb and have Boulder, Top Rope, and Lead Climb be subclasses of it. This would make it easier to build independent methods and help differentiate them since in the real world, there are some things like grading systems that only apply to Boulders or only to Top Ropes, etc. However, doing it this way means that some things, like difficulty/grading, are represented more broadly as Strings which means that the user is free to use whatever grading system they like. Of course, with more time, an automatic grading translator wouldn't be too hard to implement, along with the previously mentioned abstract class, the program would really feel like it was climbers.

Another thing that I think would be cool is abstracting the Projects class itself. Within it, you could have classes like Outdoor Projects and Gym Projects. Being able to create a new list of whatever kind of climbs you want is also really interesting and would round out the user experience. This would also simplify the sorting system since, naturally, users would probably create lists based on type of climb, grades, locations, etc. However, I don't think that the sorting is entirely useless, since a climb can exist independantly in this system, it would be cool to have a large list of everything that you could sort and make into smaller lists like "projects to tackle in 2024" or something of the sorts. Again, all of these are quality of life upgrades that would really improve the program in my opinion.
