package royalstacks.app.model;

import javax.persistence.Entity;

@Entity
public class Employee extends User {

    private String position;

     // CONSTRUCTORS
    public Employee(int userid, String username, String password, String firstName, String lastName, String position) {
        super(userid, username, password, firstName, lastName);
        this.position = position;
    }

    public Employee(String username, String password, String firstName, String lastName, String position) {
        super(username, password, firstName, lastName);
        this.position = position;
    }

    public Employee() { }

    // GETTERS EN SETTERS
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
