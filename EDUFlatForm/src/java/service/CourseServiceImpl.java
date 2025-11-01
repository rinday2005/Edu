package service;


import java.util.List;
import java.util.UUID;
import CourseDAO.CourseDAO;
import CourseDAO.ICourseDAO;
import LessionDAO.ILessionDAO;
import LessionDAO.LessionDAO;
import SectionsDAO.SectionDAO;
import SectionsDAO.ISectionDAO;
import model.Courses;
import model.Lession;
import model.Sections;

public class CourseServiceImpl {
    private final ICourseDAO courseDAO;
    private final ISectionDAO sectionDAO;
    private final ILessionDAO lessionDAO;
    public CourseServiceImpl() {
        this.courseDAO = new CourseDAO();
        this.lessionDAO = new LessionDAO();
        this.sectionDAO = new SectionDAO();
    }

    public List<Courses> getAllCourses() {
        return courseDAO.getAllCourses();
    }


    public CourseDetailDTO getCourseDetail(UUID courseId) {
        CourseDetailDTO dto = new CourseDetailDTO();
        try {
            dto.course = courseDAO.getCourseById(courseId);
            if (dto.course == null) {
                throw new IllegalArgumentException("Course not found: " + courseId);
            }
            dto.sections = sectionDAO.getByCourseId(courseId);
            if (dto.sections == null) dto.sections = java.util.Collections.emptyList();
            if (dto.sections.isEmpty()) {
                dto.lessonsMap = java.util.Collections.emptyMap();
            } else {
                java.util.List<UUID> sectionIds = dto.sections.stream()
                        .map(Sections::getSectionID)
                        .toList();
                java.util.List<Lession> lessons = lessionDAO.findBySectionIds(sectionIds);
                if (lessons == null) lessons = java.util.Collections.emptyList();
                dto.lessonsMap = lessons.stream()
                        .collect(java.util.stream.Collectors.groupingBy(Lession::getSectionID));
            }
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            // có thể quăng RuntimeException để Servlet catch và trả 500
            throw new RuntimeException("getCourseDetail failed: " + e.getMessage(), e);
        }
    }

}

