package com.example.SpringDataJpaTest;

import com.example.SpringDataJpaTest.models.*;
import com.example.SpringDataJpaTest.repos.CourseMaterialRepository;
import com.example.SpringDataJpaTest.repos.CourseRepository;
import com.example.SpringDataJpaTest.repos.StudentRepository;
import com.example.SpringDataJpaTest.repos.TeacherRepository;
import com.example.SpringDataJpaTest.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringDataJpaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaTestApplication.class, args);
	}

	@Bean
	public CommandLineRunner startupDb(StudentRepository stRepo, StudentService stSvc,
									   CourseRepository crsRepo, CourseMaterialRepository crsMatRepo,
									   TeacherRepository tRepo) {
		return args -> {
			Student mary = Student.builder()
					.studentName("Mary")
					.email("mary.test@domain.com")
					.dob(LocalDate.of(1996,02,12))
					.address(Address.builder()
							.street("Via delle Gardenie")
							.num(14)
							.city("Huston")
							.build())
					.build();
			Student ben = Student.builder()
					.studentName("Ben")
					.email("ben.test@domain.com")
					.dob(LocalDate.of(1993,12,7))
					.address(Address.builder()
							.build())
					.build();

			stRepo.saveAll(List.of(mary,ben));

			String testEmail = "ben.test@domain.com";
			String testEmailPattern = "@domain";

			Student test1 = stRepo.findByEmail(testEmail).get();
			List<Student> test2 = stRepo.findByEmailContaining(testEmailPattern);
			List<Student> test3 = stRepo.findByAddressCityNotNull();
			String test4 = stRepo.findNameByEmail(testEmail).get();
			List<String> test5 = stRepo.findNamesByEmailPattern(testEmailPattern);


			System.out.println("\n *------------------------------------------------------------------------------*");
			System.out.println("[*] Checking for email "+ testEmail+ " ...");
			System.out.println("[*] Here what I found");
			System.out.print("[*] ");
			System.out.println(test1.getStudentName());
			System.out.println("\n *------------------------------------------------------------------------------*");
			System.out.println("[*] Checking for for email pattern "+ testEmailPattern+ " ...");
			System.out.println("[*] Here what I found");
			System.out.print("[*] ");
			System.out.println(test2.stream().map(s -> s.getStudentName()).collect(Collectors.toList()));
			System.out.println("\n *------------------------------------------------------------------------------*");
			System.out.println("[*] Checking for for not null city");
			System.out.println("[*] Here what I found");
			System.out.print("[*] ");
			System.out.println(test3.stream().map(s -> s.getStudentName()).collect(Collectors.toList()));
			System.out.println("\n *------------------------------------------------------------------------------*");
			System.out.println("[*] Checking for name given the email : "+ testEmail);
			System.out.println("[*] Here what I found");
			System.out.print("[*] ");
			System.out.println(test4);
			System.out.println("\n *------------------------------------------------------------------------------*");
			System.out.println("[*] Checking for names given the email pattern : "+ testEmailPattern);
			System.out.println("[*] Here what I found");
			System.out.print("[*] ");
			System.out.println(test5);

			String newName= "Edward";
			System.out.println("\n *------------------------------------------------------------------------------*");
			System.out.println("[*] Trying to update Ben's name with : "+ newName);
			stRepo.updateStudentName(newName, testEmail);
			System.out.println("[*] Here what I found");
			System.out.print("[*] "+
					stRepo.findByEmail(testEmail));

			System.out.println("\n *------------------------------------------------------------------------------*");
			System.out.println("[*] Trying to update Edward's name with : "+ newName + " WITH A SERVICE");
			stSvc.updateStudentNameByEmail(testEmail,"PierFrancesco");
			System.out.println("[*] Here what I found");
			System.out.print("[*] "+
					stRepo.findByEmail(testEmail));

			/////////////////////////////////////////////////////////////////////////////////////////////////////////

			Course english = Course.builder()
					.courseName("english")
					.credits(3)
					.build();
			CourseMaterial englishMaterial = CourseMaterial.builder()
					.courseMaterialUrl("https://english.material.mudomain.com")
					.course(english)
					.build();

			//crsRepo.save(english); // --> Il corso vera comunque creato da CASCADE ALL
			crsMatRepo.save(englishMaterial);
			//crsMatRepo.delete(englishMaterial); // CASCADE ALL eliminerà anche il corso

			List<CourseMaterial> retrieved = crsMatRepo.findAll();
			//retrieved.stream().forEach(System.out::println);

			////////////////////////////////////////////////////////////////////////////////
			// FOR ONE TO MANY
//			Teacher jane = Teacher.builder()
//					.teacherName("Jane")
//					.teachingCourses(List.of(english))
//					.build();
//			tRepo.save(jane);

			// FOR MANY TO ONE
			Teacher jane = Teacher.builder()
					.teacherName("Jane")
					.build();
			tRepo.save(jane);
			Course math = Course.builder()
					.courseName("Math")
					.credits(12)
					.teacher(jane)
					.build();
			crsRepo.save(math);

			Course java = Course.builder()
					.courseName("JAVA")
					.credits(1)
					.teacher(jane)
					.build();
			Course spring = Course.builder()
					.courseName("Spring")
					.credits(1)
					.teacher(jane)
					.build();

			crsRepo.saveAll(List.of(java,spring));

			Pageable firstPageWith2Records = PageRequest.of(0,2);
			List<Course> courseList = crsRepo.findAll(firstPageWith2Records).getContent();
			long totPages = crsRepo.findAll(firstPageWith2Records).getTotalPages();

			System.out.println("*-----------------------------------------------*");
			System.out.println("[*] Found these courses : "+ courseList.stream().map(c -> c.getCourseName()).collect(Collectors.toList()));
			System.out.println("[*] Total page : "+ totPages);

			Pageable firstPageWith2RecordsOrdered = PageRequest.of(0,2, Sort.by("courseName").descending());
			List<Course> courseList2 = crsRepo.findAll(firstPageWith2RecordsOrdered).getContent();
			System.out.println("*-----------------------------------------------*");
			System.out.println("[*] Found these courses : "+ courseList2.stream().map(c -> c.getCourseName()).collect(Collectors.toList()));

			Page<Course> p = crsRepo.findByTeacher(jane,firstPageWith2RecordsOrdered);
			System.out.println("*-----------------------------------------------*");
			System.out.println("[*] Found this page : "+ p.stream().map(c -> c.getCourseName()).collect(Collectors.toList()));

			Course relationalDB = Course.builder()
					.courseName("Relational DB")
					.teacher(jane)
					.students(List.of(mary,ben))
					.build();
			crsRepo.save(relationalDB);

		};
	}

}
