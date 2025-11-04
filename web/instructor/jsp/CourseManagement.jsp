<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Kiểm tra nếu chưa có dữ liệu và không phải từ servlet, redirect về servlet để load -->
<c:if test="${empty courselist and empty fromServlet and empty error}">
    <c:redirect url="${pageContext.request.contextPath}/ManageCourse"/>
</c:if>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Khóa học - InstructorsHome</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
        <div class="main-container" data-context-path="${pageContext.request.contextPath}" data-has-course="${not empty course ? 'true' : 'false'}" data-is-edit="${isEdit ? 'true' : 'false'}" data-has-view-course="${not empty viewCourse ? 'true' : 'false'}"<c:if test="${not empty course}"> data-course-id="${course.courseID}" data-course-name="${fn:escapeXml(course.name)}" data-course-description="${fn:escapeXml(course.description)}" data-course-price="${course.price}" data-course-level="${fn:escapeXml(course.level)}" data-course-approved="${course.approved}"</c:if>>

            <!-- ===== HEADER ===== -->
            <jsp:include page="/instructor/common/header.jsp" />

            <!-- ===== SIDEBAR ===== -->
            <jsp:include page="/instructor/common/sidebar.jsp" />

            <!-- ===== MAIN CONTENT ===== -->
            <main class="content-area">
                <section class="section">
                    <div style="display: flex; justify-content: space-between; align-items: center; gap: 10px;">
                        <h2><i class="fas fa-laptop-code"></i> Quản lý Khóa học</h2>
                        <button class="btn btn-primary" onclick="showCourseForm()">
                            <i class="fas fa-plus"></i> Tạo khóa học mới
                        </button>
                    </div>
                </section>

                <section class="section">
                    <h3><i class="fas fa-list"></i> Danh sách Khóa học</h3>
                    
                    <!-- Debug info -->
                    <c:if test="${not empty courselist}">
                        <p style="color: green; margin-bottom: 10px;">
                            <i class="fas fa-check-circle"></i> Tìm thấy ${fn:length(courselist)} khóa học
                        </p>
                    </c:if>
                    <c:if test="${empty courselist}">
                        <p style="color: orange; margin-bottom: 10px;">
                            <i class="fas fa-exclamation-triangle"></i> Chưa có khóa học nào. Hãy tạo khóa học mới!
                        </p>
                    </c:if>
                    
                    <div class="card">
                        <div class="card-header">Tất cả khóa học của bạn</div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${empty courselist}">
                                    <div style="text-align: center; padding: 40px; color: var(--text-muted);">
                                        <i class="fas fa-inbox" style="font-size: 48px; margin-bottom: 15px; display: block;"></i>
                                        <p style="font-size: 16px; margin: 0;">Chưa có khóa học nào. Hãy tạo khóa học mới!</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="stats-grid">
                                        <c:forEach var="course" items="${courselist}">
                                    <div class="stat-card" data-course-name="${course.name}" data-course-level="Beginner" data-course-price="29.99" data-course-status="Published">
                                        <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                            <div>
                                                <h4>${course.name}</h4>
                                                <p style="color: var(--text-muted); margin: 5px 0;">Cấp độ: ${course.level}</p>
                                                <p style="color: var(--text-muted); margin: 5px 0;">Giá: ${course.price}</p>
                                            </div>
                                            <c:if test="${course.approved}">
                                                <span class="btn btn-success" style="padding: 5px 10px; font-size: 12px;">Published</span>
</c:if>
                                            <c:if test="${!course.approved}">
                                                <span class="btn btn-success" style="padding: 5px 10px; font-size: 12px;">Pending Review</span>
                                            </c:if> 


                                        </div>
                                        <div style="display: flex; gap: 10px; margin-top: 15px;">
                                            <button class="btn btn-primary" style="padding: 8px 15px; font-size: 12px;" onclick="editCourseFromCard('${course.courseID}')"><i class="fas fa-edit"></i> Chỉnh sửa</button>
                                            <button class="btn btn-secondary" style="padding: 8px 15px; font-size: 12px;" onclick="viewCourseContent('${course.courseID}')"><i class="fas fa-eye"></i> Xem nội dung</button>
                                            <a href="<c:url value='/ManageSection?course=${course.courseID}'/>" class="btn btn-info" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-layer-group"></i> Quản lý chương</a>
                                        </div>
                                    </div>
                                        </c:forEach>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            </div>
                        </div>
                    </div>
                </section>

                <!-- ===== MODAL TẠO KHÓA HỌC (có tích hợp Thêm chương) ===== -->
                <div id="courseFormModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 1000;">
                    <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: var(--bg-card); padding: 30px; border-radius: 8px; width: 90%; max-width: 700px; max-height: 85vh; overflow-y: auto;">
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                            <h3 id="modalTitle"><i class="fas fa-plus"></i> Tạo khóa học mới</h3>
<button onclick="hideCourseForm()" style="background: none; border: none; font-size: 24px; cursor: pointer; color: var(--text-light);">&times;</button>
                        </div>
                        <form id="createCourseForm"action="<c:url value='/ManageCourse'/>"  method="post"  enctype="multipart/form-data">
                            <input type="hidden" id="formAction" name="action" value="createcourse">
                            <input type="hidden" id="courseIdInput" name="courseId" value="">
                            <div class="form-group">
                                <label for="courseName">Tên khóa học *</label>
                                <input name="namecourse" type="text" id="courseName" class="form-control" placeholder="Nhập tên khóa học" required>
                            </div>
                            <div class="form-group">
                                <label for="courseDescription">Mô tả khóa học *</label>
                                <textarea name="descriptioncourse" id="courseDescription" class="form-control" rows="4" placeholder="Mô tả chi tiết về khóa học" required></textarea>
                            </div>
                            <div class="form-group">
                                <label for="coursePrice">Giá khóa học ($) *</label>
                                <input type="number" name = "pricecourse" id="coursePrice" class="form-control" placeholder="0.00" step="0.01" min="0" required>
                            </div>
                            <div class="form-group">
                                <label for="courseLevel">Cấp độ *</label>

                                <select id="courseLevel" name="levelcourse" class="form-control" required>
                                    <option value="">Chọn cấp độ</option>
                                    <option value="Beginner">Beginner</option>
                                    <option value="Intermediate">Intermediate</option>
                                    <option value="Advanced">Advanced</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="courseImage">Ảnh bìa khóa học</label>
                                <input type="file" name="picturecourse" id="courseImage" class="form-control" accept="image/*">
                            </div>
                            <div class="form-group">
                                <label for="courseStatus">Trạng thái</label>
                                <select id="courseStatus" name="status" class="form-control">
                                    <option value="true">Published</option>
                                    <option value="false">Pending Review</option>
                                </select>
                            </div>
                            <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 20px;">
<button type="button" class="btn btn-secondary" onclick="hideCourseForm()">Hủy</button>
                                <button type="submit" id="submitBtn" class="btn btn-primary">Tạo khóa học</button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- ===== MODAL XEM KHÓA HỌC ===== -->
                <div id="viewCourseModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 1000;">
                    <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: var(--bg-card); padding: 30px; border-radius: 8px; width: 90%; max-width: 700px; max-height: 85vh; overflow-y: auto;">
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                            <h3><i class="fas fa-eye"></i> Chi tiết khóa học</h3>
                            <button onclick="hideViewCourse()" style="background: none; border: none; font-size: 24px; cursor: pointer; color: var(--text-light);">&times;</button>
                        </div>
                        <div id="viewCourseContent">
                            <c:if test="${not empty viewCourse}">
                                <div class="form-group">
                                    <label><strong>Tên khóa học:</strong></label>
                                    <p>${fn:escapeXml(viewCourse.name)}</p>
                                </div>
                                <div class="form-group">
                                    <label><strong>Mô tả:</strong></label>
                                    <p>${fn:escapeXml(viewCourse.description)}</p>
                                </div>
                                <div class="form-group">
                                    <label><strong>Giá:</strong></label>
                                    <p>$${viewCourse.price}</p>
                                </div>
                                <div class="form-group">
                                    <label><strong>Cấp độ:</strong></label>
                                    <p>${fn:escapeXml(viewCourse.level)}</p>
                                </div>
                                <div class="form-group">
                                    <label><strong>Trạng thái:</strong></label>
                                    <p>
                                        <c:if test="${viewCourse.approved}">
                                            <span class="btn btn-success" style="padding: 5px 10px; font-size: 12px;">Published</span>
                                        </c:if>
                                        <c:if test="${!viewCourse.approved}">
                                            <span class="btn btn-warning" style="padding: 5px 10px; font-size: 12px;">Pending Review</span>
                                        </c:if>
                                    </p>
                                </div>
                                <c:if test="${not empty viewCourse.imgURL}">
                                    <div class="form-group">
                                        <label><strong>Ảnh bìa:</strong></label>
                                        <img src="${pageContext.request.contextPath}/${viewCourse.imgURL}" alt="Course Image" style="max-width: 100%; height: auto; border-radius: 8px; margin-top: 10px;">
                                    </div>
                                </c:if>
                            </c:if>
                        </div>
                        <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 20px;">
                            <button type="button" class="btn btn-secondary" onclick="hideViewCourse()">Đóng</button>
                        </div>
                    </div>
                </div>

            </main>
        </div>

        <script src="${pageContext.request.contextPath}/assets/js/instructorsHome.js"></script>
        <script src="${pageContext.request.contextPath}/instructor/js/CourseManagement.js"></script>
    </body>
</html>
