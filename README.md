# Tandem Community App

## Features

1. Retrieves paginated community member list from API alongside the following information:
    1. Name
    2. Topic Description
    3. Native Language
    4. Learned Language
    5. Reference Count
2. Allows users to like or unlike a community member and saving it locally
3. Prioritizes information from API over locally stored data
4. Shows loading and error states for paginated community member list

## Tech Stack Used

### Architecture

1. Model-View-ViewModel
2. Repository Pattern
3. LiveData
4. Coroutines
5. Hilt (for dependency injection)

### Data Sources

1. Retrofit
2. GSON
3. Room

### Views

1. Glide (for image loading)
2. AsyncListDiffer (for RecyclerView)
3. Data Binding

## Modules

### community

Contains Views, ViewModels, and Repositories involving community

1. adapters - contains adapters used for RecyclerView for community
2. repositories - contains repositories that interact with data sources for community
3. viewmodels - contains ViewModels that contain the presenter logic for community

### data

Contains all information about various data sources

1. community - contains data source information involving community
2. database - contains general dependencies for app database

### utilities

Contains general dependencies used by various modules of the app

## Icons Used

1. Inactive Like Icon - https://www.flaticon.com/free-icon/like_126473
2. Active Like Icon - https://www.flaticon.com/free-icon/like_456115