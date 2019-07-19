package com.damilola.searchapp;

public class BookModel {
    /*
     * Declarations of the variables for the Recycler views
     * */

    private String title;
    private String authors;
    private String year;
    private String category;


    // the method below handles the JSON for 'This Week', 'This Payment Period' and 'Since Year Started'
    public BookModel(String title, String authors, String year, String category) {

        this.title = title;
        this.authors = authors;
        this.year = year;
        this.category = category;


    }

    //The Method below consists of the getters for all the variables declared in this class
    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }
}
