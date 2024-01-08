package com.cariochi.objecto.references;

import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.References;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComplexStructureTest {

    private final ObjectoFactory factory = Objecto.create(ObjectoFactory.class);

    @Test
    void test_course() {
        final Course course = factory.createCourse();

        verifyCourse(course);
        verifyProfessor(course.getProfessor());
        course.getEnrollments().stream().map(Enrollment::getStudent).forEach(this::verifyStudent);
        course.getEnrollments().forEach(this::verifyEnrollment);
        course.getProfessor().getAssignments().forEach(this::verifyAssignment);
    }

    @Test
    void test_student() {
        final Student student = factory.createStudent();

        student.getEnrollments().stream().map(Enrollment::getCourse).forEach(this::verifyCourse);
        student.getEnrollments().stream().map(Enrollment::getCourse).map(Course::getProfessor).forEach(this::verifyProfessor);
        verifyStudent(student);
        student.getEnrollments().forEach(this::verifyEnrollment);
        student.getEnrollments().stream().map(Enrollment::getCourse).map(Course::getProfessor).flatMap(professor -> professor.getAssignments().stream()).forEach(this::verifyAssignment);
    }

    @Test
    void test_enrollment() {
        final Enrollment enrollment = factory.createEnrollment();

        verifyCourse(enrollment.getCourse());
        verifyProfessor(enrollment.getCourse().getProfessor());
        verifyStudent(enrollment.getStudent());
        verifyEnrollment(enrollment);
        enrollment.getCourse().getProfessor().getAssignments().forEach(this::verifyAssignment);
    }

    @Test
    void test_professor() {
        final Professor professor = factory.createProfessor();

        professor.getAssignments().stream().map(Assignment::getCourse).forEach(this::verifyCourse);
        verifyProfessor(professor);
        professor.getAssignments().stream().map(Assignment::getCourse).flatMap(course -> course.getEnrollments().stream()).map(Enrollment::getStudent).forEach(this::verifyStudent);
        professor.getAssignments().stream().map(Assignment::getCourse).flatMap(course -> course.getEnrollments().stream()).forEach(this::verifyEnrollment);
        professor.getAssignments().forEach(this::verifyAssignment);
    }

    @Test
    void test_assigment() {
        final Assignment assignment = factory.createAssignment();

        verifyCourse(assignment.getCourse());
        verifyProfessor(assignment.getProfessor());
        assignment.getCourse().getEnrollments().stream().map(Enrollment::getStudent).forEach(this::verifyStudent);
        assignment.getCourse().getEnrollments().forEach(this::verifyEnrollment);
        verifyAssignment(assignment);
    }

    private void verifyStudent(Student student) {
        assertThat(student.getEnrollments())
                .extracting(Enrollment::getStudent)
                .containsOnly(student);
    }

    private void verifyCourse(Course course) {
        assertThat(course.getEnrollments())
                .extracting(Enrollment::getCourse)
                .contains(course);

        assertThat(course.getProfessor().getAssignments())
                .extracting(Assignment::getCourse)
                .contains(course);
    }

    private void verifyEnrollment(Enrollment enrollment) {
        assertThat(enrollment)
                .extracting(Enrollment::getCourse)
                .extracting(Course::getEnrollments).asList()
                .contains(enrollment);

        assertThat(enrollment)
                .extracting(Enrollment::getStudent)
                .extracting(Student::getEnrollments).asList()
                .contains(enrollment);
    }

    private void verifyAssignment(Assignment assignment) {
        assertThat(assignment.getProfessor())
                .extracting(Professor::getAssignments).asList()
                .contains(assignment);
    }

    public interface ObjectoFactory {

        @References({"professor.assignments[*].course", "enrollments[*].course"})
        Course createCourse();

        @References("enrollments[*].student")
        Student createStudent();

        @References({"student.enrollments[*]", "course.enrollments[*]"})
        Enrollment createEnrollment();

        @References({"assignments[*].professor", "assignments[*].course.professor"})
        Professor createProfessor();

        @References({"course.professor.assignments[*]", "professor.assignments[*]"})
        Assignment createAssignment();

    }

    private void verifyProfessor(Professor professor) {
        assertThat(professor.getAssignments())
                .flatMap(Assignment::getProfessor)
                .contains(professor);
    }

    /*
    +----------------+       +-------------------+       +----------------+
    |    Student     |       |     Enrollment    |       |      Course    |
    +----------------+       +-------------------+       +----------------+
    | name           |       | course            |       | name           |
    | enrollments    |-----<>| student           |<>-----| professor      |--------+
    +----------------+       +-------------------+       | enrollments    |        |
                                                         +----------------+        |
                                                                ^                  |
                                                                |                  |
                                                    +----------------------+       |
                                                    |       Assignment     |       |
                                                    +----------------------+       |
                                                    | course               |       |
                                                    | professor            |       |
                                                    +----------------------+       |
                                                               |                   |
                                                               v                   |
                                                    +---------------------+        |
                                                    |      Professor      |<-------+
                                                    +---------------------+
                                                    | name                |
                                                    | assignments         |
                                                    +---------------------+
*/


    @Data
    public static class Student {

        private String name;

        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private List<Enrollment> enrollments;

    }

    @Data
    public static class Enrollment {

        private Course course;
        private Student student;

    }

    @Data
    public static class Course {

        private String name;
        private Professor professor;

        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private List<Enrollment> enrollments;

    }

    @Data
    public static class Professor {

        private String name;

        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private List<Assignment> assignments;

    }

    @Data
    public static class Assignment {

        private Course course;
        private Professor professor;

    }

}
