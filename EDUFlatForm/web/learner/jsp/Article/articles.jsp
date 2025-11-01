<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bài Viết - E-Learning System</title>
    <!-- Updated CSS path to src/css/articles.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/articles.css">
    
</head>
<body>
    <!-- Header -->
    <jsp:include page="/learner/common/header.jsp" />

    <div class="main-container">
        <!-- Sidebar -->
        <jsp:include page="/learner/common/sidebar.jsp" />

        <!-- Main Content -->
        <main class="main-content">
            <!-- Articles Section -->
            <section class="articles-main-section">
                <div class="articles-header">
                    <h1>Bài viết nổi bật</h1>
                    <p>Tổng hợp các bài viết chủ đề về lập trình từ học lập trình online và các kỹ thuật lập trình web.</p>
                </div>

                <div class="articles-container">
                    <!-- Left Column: Articles List -->
                    <div class="articles-list">
                        <!-- Article Item 1 -->
                        <article class="article-item">
                            <div class="article-item-header">
                                <div class="author-info">
                                    <img src="${pageContext.request.contextPath}/learner/images/avatar1.jpg" alt="Huyền Lê Ngọc" class="author-avatar" />
                                    <span class="author-name">Huyền Lê Ngọc</span>
                                </div>
                                <div class="article-actions">
                                    <button class="bookmark-btn" aria-label="Bookmark">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                            <path d="M17 3H5c-1.11 0-2 .9-2 2s-.9 2-2 2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"/>
                                        </svg>
                                    </button>
                                    <button class="menu-btn" aria-label="More options">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                            <path d="M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"/>
                                        </svg>
                                    </button>
                                </div>
                            </div>

                            <h2 class="article-title">
                                <a href="${pageContext.request.contextPath}/learner/jsp/Article/articlesDetail.jsp?id=1">
                                  TRẢI NGHIỆM HỌC THỬ REACT NATIVE, DEVOPS, C++ VỚI CÙNG CHẤT LƯỢNG CÙNG E-LEARNING SYSTEM
                                </a>
                            </h2>
                            <p class="article-description">Để giúp học viên mới cảm nhận ở nâng chất lượng giảng dạy, tôi đã xây dựng 3 lớp học thử C++, React Native và DevOps với lí trình rõ.</p>

                            <div class="article-tags">
                                <span class="tag">React Native</span>
                                <span class="tag">2 bảng trước</span>
                                <span class="tag">2 phút đọc</span>
                            </div>

                            <div class="article-thumbnail">
                                <img src="${pageContext.request.contextPath}/learner/images/art5.jpg" alt="React Native" />
                            </div>
                        </article>

                        <!-- Article Item 2 -->
                        <article class="article-item">
                            <div class="article-item-header">
                                <div class="author-info">
                                    <img src="${pageContext.request.contextPath}/learner/images/avatar2.jpg" alt="Hoàng Tuấn" class="author-avatar" />
                                    <span class="author-name">Hoàng Tuấn 12A1 40 Vĩ</span>
                                </div>
                                <div class="article-actions">
                                    <button class="bookmark-btn" aria-label="Bookmark">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                            <path d="M17 3H5c-1.11 0-2 .9-2 2v16l7-3 7 3V5c0-1.1.89-2 2-2z"/>
                                        </svg>
                                    </button>
                                    <button class="menu-btn" aria-label="More options">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                            <path d="M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"/>
                                        </svg>
                                    </button>
                                </div>
                            </div>

                            <h2 class="article-title">
                                <a href="${pageContext.request.contextPath}/learner/jsp/Article/articlesDetail.jsp?id=2">
                                  Giới thiệu về ngành Công Nghệ Thông Tin và Những Kiến Thức Cơ Bản Bắt Buộc Phải Học
                                </a>
                            </h2>
                            <p class="article-description">Ngành Công Nghệ Thông Tin (CNTT) là một lĩnh vực vực dạng phát triển mạnh mẽ và có vai trò quan trọng trong thời đại số...</p>

                            <div class="article-tags">
                                <span class="tag">học lập trình</span>
                                <span class="tag">3 bảng trước</span>
                                <span class="tag">3 phút đọc</span>
                            </div>

                            <div class="article-thumbnail">
                                <img src="${pageContext.request.contextPath}/learner/images/art6.webp" alt="IT Knowledge" />
                            </div>
                        </article>

                        <!-- Article Item 3 -->
                        <article class="article-item">
                            <div class="article-item-header">
                                <div class="author-info">
                                    <img src="${pageContext.request.contextPath}/learner/images/avatar2.jpg" alt="Hoàng Tuấn" class="author-avatar" />
                                    <span class="author-name">Hoàng Tuấn 12A1 40 Vĩ</span>
                                </div>
                                <div class="article-actions">
                                    <button class="bookmark-btn" aria-label="Bookmark">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                            <path d="M17 3H5c-1.11 0-2 .9-2 2v16l7-3 7 3V5c0-1.1.89-2 2-2z"/>
                                        </svg>
                                    </button>
                                    <button class="menu-btn" aria-label="More options">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                            <path d="M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"/>
                                        </svg>
                                    </button>
                                </div>
                            </div>

                            <h2 class="article-title">
                                <a href="${pageContext.request.contextPath}/learner/jsp/Article/articlesDetail.jsp?id=3">
                                  SOLID - 5 nguyên lý "vàng" giúp viết code sạch và dễ bảo trì
                                </a>
                            </h2>
                            <p class="article-description">Trong hành trình phát triển phần mềm, chắc hẳn bạn đã từng gặp phải những đoạn code dài, khó hiểu và rất khó để rồi hay chữa...</p>

                            <div class="article-tags">
                                <span class="tag">OOP</span>
                                <span class="tag">3 bảng trước</span>
                                <span class="tag">3 phút đọc</span>
                            </div>

                            <div class="article-thumbnail">
                                <img src="${pageContext.request.contextPath}/learner/images/art7.webp" alt="SOLID Principles" />
                            </div>
                        </article>

                        <!-- Article Item 4 -->
                        <article class="article-item">
                            <div class="article-item-header">
                                <div class="author-info">
                                    <img src="${pageContext.request.contextPath}/learner/images/avatar3.jpg" alt="Hải Đoàn" class="author-avatar" />
                                    <span class="author-name">Hải Đoàn</span>
                                </div>
                                <div class="article-actions">
                                    <button class="bookmark-btn" aria-label="Bookmark">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                            <path d="M17 3H5c-1.11 0-2 .9-2 2v16l7-3 7 3V5c0-1.1.89-2 2-2z"/>
                                        </svg>
                                    </button>
                                    <button class="menu-btn" aria-label="More options">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                            <path d="M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"/>
                                        </svg>
                                    </button>
                                </div>
                            </div>

                            <h2 class="article-title">
                                <a href="${pageContext.request.contextPath}/learner/jsp/Article/articlesDetail.jsp?id=4">
                                  [HTML - CSS - JS tại E-Learning System] Một thời mây mở học, lực lại được trang web cũ - chia sẻ cùng anh em
                                </a>
                            </h2>
                            <p class="article-description">[HTML - CSS - JS tại E-Learning System ] Một thời mây mở học, lực lại được trang web cũ - chia sẻ cùng anh em</p>

                            <div class="article-tags">
                                <span class="tag">HTML - CSS - JS tại F8</span>
                                <span class="tag">5 bảng trước</span>
                                <span class="tag">2 phút đọc</span>
                            </div>

                            <div class="article-thumbnail">
                                <img src="${pageContext.request.contextPath}/learner/images/art8.jpg" alt="HTML CSS JS" />
                            </div>
                        </article>

                        
                    </div>

                    <!-- Right Column: Sidebar -->
                    <aside class="articles-sidebar">
                        <!-- Featured Courses Section -->
                        <div class="sidebar-section">
                            <div class="featured-course-card">
                                <div class="course-card-image">
                                    <img src="${pageContext.request.contextPath}/learner/images/pro4.png" alt="HTML CSS Pro" />
                                </div>
                                <div class="course-card-content">
                                    <h4>Khóa học</h4>
                                    <h3>HTML CSS PRO</h3>
                                    <ul class="course-features">
                                        <li>
                                            <svg viewBox="0 0 24 24" width="16" height="16" fill="currentColor">
                                                <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                                            </svg>
                                            <span>Thực hành 8 dự án</span>
                                        </li>
                                        <li>
                                            <svg viewBox="0 0 24 24" width="16" height="16" fill="currentColor">
                                                <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                                            </svg>
                                            <span>Hơn 300 bài tập thực hành</span>
                                        </li>
                                        <li>
                                            <svg viewBox="0 0 24 24" width="16" height="16" fill="currentColor">
                                                <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                                            </svg>
                                            <span>Tăng ứng dụng Flashcards</span>
                                        </li>
                                        <li>
                                            <svg viewBox="0 0 24 24" width="16" height="16" fill="currentColor">
                                                <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                                            </svg>
                                            <span>Tăng 20+ thiết kế trên Figma</span>
                                        </li>
                                    </ul>
                                    <button class="course-btn">Tìm hiểu thêm ></button>
                                </div>
                            </div>

                            

                            <div class="featured-course-card">
                                <div class="course-card-image">
                                    <img src="${pageContext.request.contextPath}/learner/images/pro7.jpg" alt="Advanced Course" />
                                </div>
                                <div class="course-card-content">
                                    <h4>Khóa học</h4>
                                    <h3>Khóa học nâng cao</h3>
                                    <p>Nội dung khóa học nâng cao cho các lập trình viên muốn phát triển kỹ năng.</p>
                                    <button class="course-btn">Tìm hiểu thêm ></button>
                                </div>
                            </div>
                        </div>
                    </aside>
                </div>
            </section>
        </main>
    </div>

    <!-- Authentication Modal -->
    

    <!-- Footer -->
    <jsp:include page="/learner/common/footer.jsp" />

    <!-- Updated JS path to src/js/articles.js -->
    <script src="${pageContext.request.contextPath}/learner/js/articles.js"></script>
</body>
</html>
