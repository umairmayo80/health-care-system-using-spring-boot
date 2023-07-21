package server.domain;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userId;

    private String name;
    private String role;

    @Column(name = "username", unique = true)
    private String username;

    private String password;

    @Column(name = "accountLocked")
    private boolean accountLocked;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Slot> slots = new HashSet<>();


    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointmentV1List = new ArrayList<>();



    public User() {
    }


    public User(String name, String role, String username, String password, boolean accountLocked) {
        this.userId = 0;
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
        this.accountLocked = accountLocked;
    }


    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Set<Slot> getSlots() {
        return slots;
    }



    public List<Appointment> getAppointmentV1List(){
        return appointmentV1List;
    }


    public void addSlot(Slot slot) {
        slots.add(slot);
        slot.setDoctor(this);
    }

    public void removeSlot(Slot slot) {
        slots.remove(slot);
        slot.setDoctor(null);
    }

    public void addAppointmentV1(Appointment appointmentV1) {
        appointmentV1List.add(appointmentV1);
        appointmentV1.setPatient(this);
    }

    public void removeAppointment(Appointment appointmentV1){
        appointmentV1List.remove(appointmentV1);
        appointmentV1.setPatient(null);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountLocked='" + accountLocked + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                accountLocked == user.accountLocked &&
                Objects.equals(name, user.name) &&
                Objects.equals(role, user.role) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, role, username, password, accountLocked);
    }
}
