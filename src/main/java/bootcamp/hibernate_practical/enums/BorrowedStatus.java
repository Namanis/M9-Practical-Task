package bootcamp.hibernate_practical.enums;

/* Books borrowing logic:
If book is Available it can be -> IN_LIBRARY or BORROWED
If book is Availalbe=false it must be UNAVAILABLE and can't be borrowed or returned
On Creation it is set to IN_LIBRARY
*/
public enum BorrowedStatus {
    IN_LIBRARY,
    BORROWED,
    UNAVAILABLE
}
