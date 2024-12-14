package uk.ac.sheffield.com1003.library.catalogue;

public class Magazine extends CatalogueItem {
    private int number;

    public Magazine(String title, int year, int number) {
        super(title, year);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Magazine: title=" + getTitle() + "; year=" + getYear() + "; number=" + number + "; copies=" + getCopies();
    }
}
