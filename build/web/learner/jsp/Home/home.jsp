<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Học Lập Trình Để Đi Làm - EduPlatform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/home.css">
</head>
<body>
    <!-- Header -->
    <jsp:include page="/learner/common/header.jsp" />

    <div class="main-container">
        <!-- Sidebar -->
        <jsp:include page="/learner/common/sidebar.jsp" />

        <!-- Main Content -->
        <main class="main-content">
          

            <!-- Image carousel banner -->
            <section class="banner-carousel">
                <div class="carousel-container">
                    <div class="carousel-slide active">
                        <img src="${pageContext.request.contextPath}/learner/images/banner1.jpeg" alt="Web Development">
                    </div>
                    <div class="carousel-slide">
                        <img src="${pageContext.request.contextPath}/learner/images/banner2.jpg" alt="Coding Skills">
                    </div>
                    <div class="carousel-slide">
                        <img src="${pageContext.request.contextPath}/learner/images/banner3.jpg" alt="Programming">
                    </div>
                    <div class="carousel-slide">
                        <img src="${pageContext.request.contextPath}/learner/images/banner4.jpg" alt="Tech Education">
                    </div>
                </div>
                
                <div class="carousel-dots">
                    <span class="dot active" onclick="currentSlide(0)"></span>
                    <span class="dot" onclick="currentSlide(1)"></span>
                    <span class="dot" onclick="currentSlide(2)"></span>
                    <span class="dot" onclick="currentSlide(3)"></span>
                </div>
            </section>

            <!-- Add intro section with welcome message and 4 feature cards -->
            <section class="intro-section">
                <div class="intro-content">
                    <h2>Chào mừng đến với E-Learning System - Học Lập Trình Để Đi Làm</h2>
                    <p>E-Learning System là nền tảng học lập trình hàng đầu tại Việt Nam, cung cấp các khóa học chất lượng cao từ cơ bản đến nâng cao. Chúng tôi cam kết giúp bạn trở thành một lập trình viên chuyên nghiệp với kỹ năng thực tế và kinh nghiệm làm việc.</p>
                    <div class="intro-features">
                        <div class="feature-item">
                            <div class="feature-icon">👨‍💼</div>
<h4>Giáo viên Chuyên Nghiệp</h4>
                            <p>Học từ những lập trình viên có kinh nghiệm thực tế</p>
                        </div>
                        <div class="feature-item">
                            <div class="feature-icon">📋</div>
                            <h4>Nội Dung Cập Nhật</h4>
                            <p>Các khóa học được cập nhật theo xu hướng công nghệ mới nhất</p>
                        </div>
                        <div class="feature-item">
                            <div class="feature-icon">📚</div>
                            <h4>Hỗ Trợ Việc Làm</h4>
                            <p>Được hỗ trợ tìm việc làm sau khi hoàn thành khóa học</p>
                        </div>
                        <div class="feature-item">
                            <div class="feature-icon">✅</div>
                            <h4>Chứng Chỉ Hoàn Thành</h4>
                            <p>Nhận chứng chỉ được công nhân khi hoàn thành khóa học</p>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Pro Courses Section -->
            <section class="pro-courses-section">
                <div class="section-header">
                    <h2>Khóa học</h2>
                </div>

                <div class="pro-courses-grid" id="coursesGrid">
                    <c:if test="${empty courses}">
                        <p>Chưa có khóa học nào được duyệt.</p>
                    </c:if>

                <c:forEach var="course" items="${courses}" varStatus="status">
                    <div class="pro-course-card" 
                        onclick="window.location.href='${pageContext.request.contextPath}/CourseServletController?action=detail&id=${course.courseID}'">
                    <div class="course-image">
                        <c:choose>
                            <c:when test="${course.imgURL != null && course.imgURL != ''}">
                                <c:choose>
                                    <c:when test="${fn:startsWith(course.imgURL, 'http')}">
                                        <img src="${course.imgURL}" 
                                             alt="${course.name}" 
                                             class="course-image-img"
                                             loading="lazy"
                                             decoding="async"
                                             onerror="this.onerror=null; this.src='data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'400\' height=\'200\'%3E%3Crect fill=\'%23e0e0e0\' width=\'400\' height=\'200\'/%3E%3Ctext x=\'50%25\' y=\'50%25\' text-anchor=\'middle\' dy=\'.3em\' fill=\'%23999\' font-family=\'Arial\' font-size=\'16\'%3ECourse Image%3C/text%3E%3C/svg%3E';" />
                                    </c:when>
                                    <c:when test="${fn:startsWith(course.imgURL, '/')}">
<img src="${pageContext.request.contextPath}${course.imgURL}" 
                                             alt="${course.name}" 
                                             class="course-image-img"
                                             loading="lazy"
                                             decoding="async"
                                             onerror="this.onerror=null; this.src='data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'400\' height=\'200\'%3E%3Crect fill=\'%23e0e0e0\' width=\'400\' height=\'200\'/%3E%3Ctext x=\'50%25\' y=\'50%25\' text-anchor=\'middle\' dy=\'.3em\' fill=\'%23999\' font-family=\'Arial\' font-size=\'16\'%3ECourse Image%3C/text%3E%3C/svg%3E';" />
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/${course.imgURL}" 
                                             alt="${course.name}" 
                                             class="course-image-img"
                                             loading="lazy"
                                             decoding="async"
                                             onerror="this.onerror=null; this.src='data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'400\' height=\'200\'%3E%3Crect fill=\'%23e0e0e0\' width=\'400\' height=\'200\'/%3E%3Ctext x=\'50%25\' y=\'50%25\' text-anchor=\'middle\' dy=\'.3em\' fill=\'%23999\' font-family=\'Arial\' font-size=\'16\'%3ECourse Image%3C/text%3E%3C/svg%3E';" />
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='400' height='200'%3E%3Crect fill='%23e0e0e0' width='400' height='200'/%3E%3Ctext x='50%25' y='50%25' text-anchor='middle' dy='.3em' fill='%23999' font-family='Arial' font-size='16'%3ECourse Image%3C/text%3E%3C/svg%3E" 
                                     alt="${course.name}" 
                                     class="course-image-img"
                                     loading="lazy"
                                     decoding="async" />
                            </c:otherwise>
                        </c:choose>
                    </div>
                        <div class="course-content">
                            <h3>${course.name}</h3>
                            <p class="course-description">${course.description}</p>
                        <div class="course-pricing">
                            <span class="discount-price">Giá tiền: ${course.price}đ</span>
                        </div>
                            <div class="course-stats-row">
                                <span class="stat-item">⭐ <span class="stat-label">${course.rating}/5</span></span>
                                <span class="stat-item">💰 <span class="stat-label">${course.level}</span></span>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                </div>
            </section>


            <!-- Videos Section -->
            <section class="videos-section">
                <div class="section-header">
                    <h2>Videos nổi bật</h2>
                    <a href="#" class="view-all">Xem tất cả ></a>
                </div>
                
                <div class="videos-scroll">
<div class="video-card" onclick="window.open('https://www.youtube.com/watch?v=NVmi45IPG80', '_blank')">
                        <div class="video-thumbnail">
                            <img src="${pageContext.request.contextPath}/learner/images/video1.jpg" alt="HTML CSS">
                            <span class="play-button">▶</span>
                            <span class="duration">07:53</span>
                        </div>
                        <div class="video-info">
                            <h3>Bạn sẽ làm được gì sau khóa học?</h3>
                            <div class="video-stats">
                                <span>1.118.468 lượt xem</span>
                                <span>6.567 lượt thích</span>
                                <span>147 bình luận</span>
                            </div>
                        </div>
                    </div>

                    <div class="video-card" onclick="window.open('https://www.youtube.com/watch?v=YH-E4Y3EaT4', '_blank')">
                        <div class="video-thumbnail">
                            <img src="${pageContext.request.contextPath}/learner/images/video2.jpg" alt="Internship">
                            <span class="play-button">▶</span>
                            <span class="duration">34:51</span>
                        </div>
                        <div class="video-info">
                            <h3>Sinh viên IT đi thực tập tại doanh nghiệp cần biết những gì?</h3>
                            <div class="video-stats">
                                <span>263.572 lượt xem</span>
                                <span>6.434 lượt thích</span>
                                <span>236 bình luận</span>
                            </div>
                        </div>
                    </div>

                    <div class="video-card" onclick="window.open('https://www.youtube.com/watch?v=70j3UJO-_uY', '_blank')">
                        <div class="video-thumbnail">
                            <img src="${pageContext.request.contextPath}/learner/images/video3.jpg" alt="Programming Methods">
                            <span class="play-button">▶</span>
                            <span class="duration">24:06</span>
                        </div>
                        <div class="video-info">
                            <h3>Phương pháp học lập trình</h3>
                            <div class="video-stats">
                                <span>131.590 lượt xem</span>
                                <span>6.203 lượt thích</span>
                                <span>340 bình luận</span>
                            </div>
                        </div>
                    </div>

                    <div class="video-card" onclick="window.open('https://www.youtube.com/watch?v=IjWuRvHyS3Q', '_blank')">
                        <div class="video-thumbnail">
<img src="${pageContext.request.contextPath}/learner/images/video4.jpg" alt="Code Battle">
                            <span class="play-button">▶</span>
                            <span class="duration">25:10</span>
                        </div>
                        <div class="video-info">
                            <h3>"Code Thiếu Nhi Battle" Trạng Giải Trả Sửa Size L</h3>
                            <div class="video-stats">
                                <span>282.432 lượt xem</span>
                                <span>5.683 lượt thích</span>
                                <span>182 bình luận</span>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Articles Section -->
            <section id="articles" class="articles-section">
                <div class="section-header">
                    <h2>Bài viết nổi bật</h2>
                    <a href="articles.jsp" class="view-all">Xem tất cả ></a>
                </div>
                
                <div class="articles-scroll">
                    <article class="article-card" onclick="navigateTo('articlesDetail.jsp')">
                        <div class="article-image">
                            <img src="${pageContext.request.contextPath}/learner/images/art1.jpg" alt="Student Collection">
                        </div>
                        <div class="article-info">
                            <h3>Tổng hợp các sản phẩm của học viên tại E-Learning System</h3>
                            <div class="article-meta">
                                <span class="author">Sơn Đặng</span>
                                <span class="date">6 phút đọc</span>
                            </div>
                        </div>
                    </article>

                    <article class="article-card" onclick="navigateTo('articlesDetail.jsp')">
                        <div class="article-image">
                            <img src="${pageContext.request.contextPath}/learner/images/art2.jpg" alt="Webpack React">
                        </div>
                        <div class="article-info">
                            <h3>[Phần 1] Tạo dự án ReactJS với Webpack và Babel</h3>
                            <div class="article-meta">
                                <span class="author">Sơn Đặng</span>
                                <span class="date">12 phút đọc</span>
                            </div>
                        </div>
                    </article>

                    <article class="article-card" onclick="navigateTo('articlesDetail.jsp')">
                        <div class="article-image">
                            <img src="${pageContext.request.contextPath}/learner/images/art3.png" alt="GitHub Pages">
                        </div>
                        <div class="article-info">
<h3>Cách đưa code lên GitHub và tạo GitHub Pages</h3>
                            <div class="article-meta">
                                <span class="author">Vo Minh Kha</span>
                                <span class="date">4 phút đọc</span>
                            </div>
                        </div>
                    </article>

                    <article class="article-card" onclick="navigateTo('articlesDetail.jsp')">
                        <div class="article-image">
                            <img src="${pageContext.request.contextPath}/learner/images/art4.jpg" alt="F8 Experience">
                        </div>
                        <div class="article-info">
                            <h3>Kỳ sự ngày thứ 25 học ở E-Learning System</h3>
                            <div class="article-meta">
                                <span class="author">Sơn Sơn</span>
                                <span class="date">1 phút đọc</span>
                            </div>
                        </div>
                    </article>
                </div>
            </section>
        </main>
    </div>

    <!-- Footer -->
    <jsp:include page="/learner/common/footer.jsp" />

    <!-- Added global theme script before page-specific JS -->
    <script src="${pageContext.request.contextPath}/learner/js/theme.js"></script>
    <script src="${pageContext.request.contextPath}/learner/js/home.js"></script>
</body>
</html>