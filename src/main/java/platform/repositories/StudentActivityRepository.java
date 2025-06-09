package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Course;
import platform.entities.StudentActivity;
import platform.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentActivityRepository  extends CrudRepository<StudentActivity, Long> {
    Optional<StudentActivity> findByStudentAndCourse(User user, Course course);

    List<StudentActivity> findAllByTeacher(User teacher);
    List<StudentActivity> findAllByStudent(User student);
}
