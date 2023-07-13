## Current back-end server URL:  

https://cf8c-14-186-21-80.ngrok-free.app 

## Test User ID:

#### Username: 
taile

#### Password: 
123456

## How to change into new back-end URL

base → Common → Constant → change value of BASE_URL

## API instruction:

Can be found in API Contracts (Confluence Page)

## Known bugs
Navigate between screen fragment leads to viewmodel reset
Not validate the user input in log in page (not empty, minimum lenght...)

## Complete
Multi-module + Clean MVVM architecture set up
#### Log In Feature:
- UI 
- Request Log in With back-end
- Handle Request on Loading, Error and Success with Modal Dialog

#### Dashboard feature:
- UI (Still need to improve)
- Handle Request on Loading, Error and Success for User, Advertisement API
- Cards design
- Scoring System

#### Transaction feature 
- Handle Request on Loading, Error and Success for User, Advertisement API
- Click a slice on pie chart to send request filterig the transcations by Category

## What to do
- Change other fragments except Dashboard to activity as Dashboard is the navigation view to direct to other screens
- Update UI
- Transaction History page on completion (change pie chart, add button for sorting/filtering)
- Notification (Duong Do)
- Chart/Graph(s) for dashboard (Long)
- Back-end update (add data for advertisement API, make api for cards and notifications)


## Contributing



## License
