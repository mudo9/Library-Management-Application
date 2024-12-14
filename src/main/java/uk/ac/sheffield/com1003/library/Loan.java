package uk.ac.sheffield.com1003.library;

import uk.ac.sheffield.com1003.library.catalogue.CatalogueItem;
import uk.ac.sheffield.com1003.library.exceptions.ItemAlreadyReturnedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Loan {
    
    private CatalogueItem item;
    private Person user;
    private LocalDateTime loanDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnedDate;

    /**
     * Constructor that takes as parameters the item being loaned,
     * the user borrowing the item, and the loan length.
     * Sets the loan date and time to
     * the current date using {@link LocalDateTime#now()},
     * the due date to the current date plus loan length
     * and the returned date to null.
     * 
     * @param item CatalogueItem being loaned
     * @param user Person loaning item
     * @param loanLength Number of days the loan is valid for
     */
    public Loan(CatalogueItem item, Person user, int loanLength) {
        this.item = item;
        this.user = user;
        this.loanDate = LocalDateTime.now();
        this.dueDate = loanDate.plusDays(loanLength);
        this.returnedDate = null;
    }

    public CatalogueItem getItem() {
        return item;
    }
    
    public Person getUser() {
        return user;
    }
    

    public LocalDateTime getLoanDate() {
        return loanDate;
    }
    
    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    

    /**
     * Prints loan receipt in the following format:
     * <Loan date in format dd-MM-yyyy HH-mm-ss>
     * <User name> has borrowed <Book title>
     * Date due: <Date item is due to be returned>
     * Returned: <Returned date and time in format dd-MM-yyyy HH-mm-ss if already returned; otherwise "n/a">
     * Thank you!
     */
    public void printReceipt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Loan date: " + loanDate.format(formatter));
        System.out.println(user.getFirstName() + " " +user.getLastName()+ " has borrowed " + item.getTitle());
        System.out.println("Date due: " + dueDate.format(formatter));
        System.out.println("Returned: " + (returnedDate != null ? returnedDate.format(formatter) : "n/a"));
        System.out.println("Thank you!");
    }
    

    /**
     * Updates field {@link Loan#returnedDate} with current date and time
     * @throws ItemAlreadyReturnedException if the item was already returned
     */
    public void returnItem() throws ItemAlreadyReturnedException {
        if (returnedDate != null) {
            throw new ItemAlreadyReturnedException("Item has already been returned.");
        }
        returnedDate = LocalDateTime.now();
        item.addCopy();
    }

    /**
     * Extends the loan term by adding the given amount of days to field {@link Loan#dueDate}
     * @throws ItemAlreadyReturnedException if the item was already returned
     */
    public void extendLoan(int days) throws ItemAlreadyReturnedException {
        if (returnedDate != null) {
            throw new ItemAlreadyReturnedException("Item has already been returned.");
        }
        dueDate = dueDate.plusDays(days);
    }
    

    /**
     * Returns a string representation of a Loan
     *
     * @return A string representation of a loan in
     * the format "Loan: User=<User name>; Item=<Book title>; Type=<"Book" or "Magazine">; Due=<Due date dd-MM-yyyy>"
     */
    @Override
    public String toString() {
        String loanString = "Loan: User=" + user.getFirstName() + " " + user.getLastName() +
            "; Item=" + item.getTitle() +
            "; Due=" + dueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        if (returnedDate != null) {
            loanString += "; Returned=" + returnedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } else {
            loanString += "; Returned=n/a";
        }

        return loanString;
    }
}