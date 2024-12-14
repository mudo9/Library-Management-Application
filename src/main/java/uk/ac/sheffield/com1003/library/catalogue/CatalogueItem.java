package uk.ac.sheffield.com1003.library.catalogue;

public class CatalogueItem {
    private String title;
    private String isbn;
    private int year;
    private int copies;

    public CatalogueItem() {
        this("Item", 0, 0, "0000000000000");
    }
    
    public CatalogueItem(String title) {
        this(title, 0, 0, "0000000000000");
    }
    
    public CatalogueItem(String title, int year) {
        this(title, year, 0, "0000000000000");
    }
    
    public CatalogueItem(String title, int year, int copies) {
        this(title, year, copies, "0000000000000");
    }

    public CatalogueItem(String title, int year, int copies, String isbn) {
        this.title = title;
        this.year = year;
        this.copies = copies;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }
    

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getIsbn() {
        return isbn;
    }

    /**
     * Adds copies of a catalogue item
     * @param n number of copies of catalogue item to add
     */
    public void addCopies(int n) {
        this.copies += n;
    }
    

    /**
     * Adds a single copy of a catalogue item
     */
    public void addCopy() {
        this.copies++;
    }

    public void decrementCopies() {
        if (copies > 0) {
            copies--;
        } else {
            System.out.println("No more copies available to decrement.");
        }
    }

    public boolean isAvailable() {
        return copies > 0;
    }
}
