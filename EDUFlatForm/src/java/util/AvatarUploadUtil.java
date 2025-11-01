/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;
import java.io.File;

/**
 *
 * @author trank
 */
public class AvatarUploadUtil {

    /**
     * Upload file và trả về đường dẫn URL truy cập.
     * @param filePart phần file từ form (request.getPart)
     * @param servletContext context servlet
     * @param contextPath contextPath (request.getContextPath())
     * @param relativeFolder thư mục tương đối (ví dụ "uploads/avatar" hoặc "uploads/course/banner")
     * @return URL có thể lưu vào DB hoặc hiển thị
     * @throws Exception nếu có lỗi upload
     */
    public static String uploadFile(Part filePart, ServletContext servletContext, String contextPath, String relativeFolder) throws Exception {
        if (filePart == null || filePart.getSize() <= 0) {
            return null; // không có file upload
        }

        // Làm sạch tên file
        String originalName = new File(filePart.getSubmittedFileName()).getName();
        String safeName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String fileName = System.currentTimeMillis() + "_" + safeName;

        // Chuẩn hóa thư mục upload
        String safeFolder = relativeFolder.replaceAll("[/\\\\]+", "/").replaceAll("^/+", "").replaceAll("/+$", "");
        String uploadPath = servletContext.getRealPath("/" + safeFolder);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Lưu file
        File savedFile = new File(uploadDir, fileName);
        filePart.write(savedFile.getAbsolutePath());

        // Trả URL
        return contextPath + "/" + safeFolder + "/" + fileName + "?t=" + System.currentTimeMillis();
    }
}
