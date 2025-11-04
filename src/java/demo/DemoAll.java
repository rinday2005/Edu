import CourseDAO.*;
import model.Courses;
import java.util.UUID;

public class DemoAll {
    public static void main(String[] args) {
        ICourseDAO dao = new CourseDAO();

        UUID courseId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID(); // hoặc lấy từ session

        Courses c = new Courses(
            courseId, instructorId,
            "Java Core", "Khóa nhập môn Java", null,
            0, 199, "Beginner", false
        );

        System.out.println("Insert: " + dao.insert(c));

        Courses got = dao.findById(courseId);
        System.out.println("Got: " + (got != null ? got.getName() : "null"));

        System.out.println("Approve: " + dao.updateIsApproved(courseId, true));

        c.setPrice(249);
        c.setDescription("Cập nhật mô tả");
        c.setApproved(true);
        System.out.println("Update full: " + dao.update(c));

        System.out.println("Delete: " + dao.delete(courseId));
    }
}
