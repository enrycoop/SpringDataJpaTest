package com.example.SpringDataJpaTest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "Student")
@Table(name = "student_class",
        uniqueConstraints = {@UniqueConstraint(name = "student_email_unique",columnNames = "e_mail")})
@AttributeOverrides({
        @AttributeOverride(
                name = "studentName",
                column = @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
        ),
        @AttributeOverride(
                name = "id",
                column = @Column(name="id",updatable = false)
        ),
        @AttributeOverride(
                name = "email",
                column = @Column(name = "e_mail", nullable = false, columnDefinition = "TEXT")
        )
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @SequenceGenerator(name="student_sequence_generator",
                    sequenceName = "student_sequence",
                    allocationSize = 1,
                    initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "student_sequence_generator")
    //@Column(name="id",updatable = false)
    private Long id;
    //@Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String studentName;
    //@Column(name = "e_mail", nullable = false, columnDefinition = "TEXT") //, unique = true)
    private String email;

    private LocalDate dob;
    @Embedded
    private Address address;

    // Constructors
//    public Student(){}

//    public Student(String studentName, LocalDate dob, String email) {
//        this.studentName = studentName;
//        this.dob = dob;
//        this.email = email;
//    }

    // Getter - Setter
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getStudentName() {
//        return studentName;
//    }
//
//    public void setStudentName(String studentName) {
//        this.studentName = studentName;
//    }
//
//    public Date getDob() {
//        return dob;
//    }
//
//    public void setDob(Date dob) {
//        this.dob = dob;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    @Override
//    public String toString() {
//        return "Student{" +
//                "id=" + id +
//                ", studentName='" + studentName + '\'' +
//                ", dob=" + dob +
//                '}';
//    }
}
