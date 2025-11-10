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
import java.sql.SQLException;
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
        return courseDAO.findAll();
    }
    
    public List<Courses> findAll() {
        return courseDAO.findAll();
    }

    public CourseDetailDTO getCourseDetail(UUID courseId) {
        CourseDetailDTO dto = new CourseDetailDTO();
        try {
            dto.course = courseDAO.findById(courseId);
            if (dto.course == null) {
                throw new IllegalArgumentException("Course not found: " + courseId);
            }
            
            // Fix: Sử dụng findByCourseId() thay vì findById() để lấy TẤT CẢ sections của course
            dto.sections = sectionDAO.findByCourseId(courseId);
            if (dto.sections == null) {
                dto.sections = java.util.Collections.emptyList();
            }
            
            // Lấy lessons cho tất cả sections
            if (!dto.sections.isEmpty()) {
                java.util.List<UUID> sectionIds = dto.sections.stream()
                        .map(Sections::getSectionID)
                        .filter(id -> id != null)
                        .toList();
                java.util.List<Lession> lessons = lessionDAO.findBySectionIds(sectionIds);
                if (lessons == null) {
                    lessons = java.util.Collections.emptyList();
                }
                dto.lessonsMap = lessons.stream()
                        .collect(java.util.stream.Collectors.groupingBy(Lession::getSectionID));
            } else {
                dto.lessonsMap = java.util.Collections.emptyMap();
            }
            
            System.out.println("[CourseServiceImpl] Loaded course: " + dto.course.getName() + 
                    " | Sections: " + dto.sections.size() + 
                    " | Lessons: " + (dto.lessonsMap != null ? dto.lessonsMap.size() : 0));
            
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            // có thể quăng RuntimeException để Servlet catch và trả 500
            throw new RuntimeException("getCourseDetail failed: " + e.getMessage(), e);
        }
    }
    public boolean insert(Courses c){
       return courseDAO.insert(c);
    }
    
    public boolean update(Courses c) {
        return courseDAO.update(c);
    }
    
    public Courses getCourseById(UUID courseId) {
        return courseDAO.findById(courseId);
    }
     public UUID getCourseIdBySectionId(UUID sectionID) throws SQLException {
        return courseDAO.getCourseIdBySectionId(sectionID);
    }
}
