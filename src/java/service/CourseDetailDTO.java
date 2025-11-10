/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import model.Assignment;
import model.Courses;
import model.Lession;
import model.Sections;

/**
 *
 * @author trank
 */
public class CourseDetailDTO {
    public Courses course;
    public List<Sections> sections;
    public Map<UUID, List<Lession>> lessonsMap; // key = sectionID (UUID)
    public Map<UUID, List<Assignment>> assignmentsMap;
    
    public Courses getCourse() {
        return course;
    }
    public void setCourse(Courses course) {
        this.course = course;
    }

    public List<Sections> getSections() {
        return sections;
    }
    public void setSections(List<Sections> sections) {
        this.sections = sections;
    }

    public Map<UUID, List<Lession>> getLessonsMap() {
        return lessonsMap;
    }
    public void setLessonsMap(Map<UUID, List<Lession>> lessonsMap) {
        this.lessonsMap = lessonsMap;
    }

    public Map<UUID, List<Assignment>> getAssignmentsMap() {
        return assignmentsMap;
    }
    public void setAssignmentsMap(Map<UUID, List<Assignment>> assignmentsMap) {
        this.assignmentsMap = assignmentsMap;
    }
}