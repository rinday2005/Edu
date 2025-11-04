/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
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
}