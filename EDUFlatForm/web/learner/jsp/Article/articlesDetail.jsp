<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String id = request.getParameter("id");
    if (id == null || id.isEmpty()) {
        id = "1";
    }
    
    String title = "";
    String description = "";
    String author = "";
    String authorAvatar = "";
    String tags = "";
    String content = "";
    String thumbnail = "";
    
    switch (id) {
        case "1":
            title = "TRẢI NGHIỆM HỌC THỬ REACT NATIVE, DEVOPS, C++ VỚI CÙNG CHẤT LƯỢNG CÙNG F8";
            description = "Để giúp học viên mới cảm nhận ở nâng chất lượng giảng dạy, tôi đã xây dựng 3 lớp học thử C++, React Native và DevOps với lí trình rõ.";
            author = "Huyền Lê Ngọc";
            authorAvatar = "avatar1.jpg";
            tags = "React Native,2 bảng trước,2 phút đọc";
            thumbnail = "article-featured-1.jpg";
            content = "<h2 class='section-title'>Lợi ích khi tham gia lớp học thứ F8</h2><p>Ngoài lộ trình bài bản, bạn còn nhận được:</p><ul class='benefits-list'><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Học và hỏi đáp trực tiếp với giảng viên</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Trợ giảng hỗ trợ xuyên suốt, fixx bug ngay tại lớp</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Được định hướng lộ trình học phù hợp sau khi trải nghiệm</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#FFB800'><path d='M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z'/></svg><span>Nếu bạn đang tìm một môi trường học lập trình nghiêm túc, thực tế và giàu cảm hứng – hãy thử một buổi học tại F8 nha!</span></li></ul>";
            break;
        case "2":
            title = "Giới thiệu về ngành Công Nghệ Thông Tin và Những Kiến Thức Cơ Bản Bắt Buộc Phải Học";
            description = "Ngành Công Nghệ Thông Tin (CNTT) là một lĩnh vực dạng phát triển mạnh mẽ và có vai trò quan trọng trong thời đại số...";
            author = "Hoàng Tuấn 12A1 40 Vĩ";
            authorAvatar = "avatar2.jpg";
            tags = "học lập trình,3 bảng trước,3 phút đọc";
            thumbnail = "article-featured-2.jpg";
            content = "<h2 class='section-title'>Ngành Công Nghệ Thông Tin là gì?</h2><p>Ngành Công Nghệ Thông Tin (CNTT) là một lĩnh vực dạng phát triển mạnh mẽ và có vai trò quan trọng trong thời đại số. Nó bao gồm các hoạt động liên quan đến máy tính, phần mềm, mạng máy tính và các ứng dụng công nghệ thông tin.</p><h2 class='section-title'>Kiến thức cơ bản cần biết</h2><ul class='benefits-list'><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Hiểu biết về phần cứng máy tính</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Kiến thức về hệ điều hành</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Lập trình cơ bản</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Mạng máy tính và internet</span></li></ul>";
            break;
        case "3":
            title = "SOLID - 5 nguyên lý \"vàng\" giúp viết code sạch và dễ bảo trì";
            description = "Trong hành trình phát triển phần mềm, chắc hẳn bạn đã từng gặp phải những đoạn code dài, khó hiểu và rất khó để rồi hay chữa...";
            author = "Hoàng Tuấn 12A1 40 Vĩ";
            authorAvatar = "avatar2.jpg";
            tags = "OOP,3 bảng trước,3 phút đọc";
            thumbnail = "article-featured-3.jpg";
            content = "<h2 class='section-title'>5 Nguyên lý SOLID</h2><p>SOLID là một tập hợp năm nguyên lý thiết kế phần mềm được đề xuất bởi Robert C. Martin nhằm giúp các nhà phát triển viết code sạch, dễ bảo trì và mở rộng.</p><ul class='benefits-list'><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>S - Single Responsibility Principle</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>O - Open/Closed Principle</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>L - Liskov Substitution Principle</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>I - Interface Segregation Principle</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>D - Dependency Inversion Principle</span></li></ul>";
            break;
        case "4":
            title = "[HTML - CSS - JS tại F8] Một thời mây mở học, lực lại được trang web cũ - chia sẻ cùng anh em";
            description = "[HTML - CSS - JS tại F8] Một thời mây mở học, lực lại được trang web cũ - chia sẻ cùng anh em";
            author = "Hải Đoàn";
            authorAvatar = "avatar3.jpg";
            tags = "HTML - CSS - JS tại F8,5 bảng trước,2 phút đọc";
            thumbnail = "article-featured-4.jpg";
            content = "<h2 class='section-title'>Hành trình học HTML, CSS, JS</h2><p>Bài viết chia sẻ hành trình học lập trình HTML, CSS, JS tại F8 và những bài học quý báu từ quá trình học tập.</p><ul class='benefits-list'><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Kiến thức HTML cơ bản</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>CSS styling và responsive design</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>JavaScript interactivity</span></li></ul>";
            break;
        case "5":
            title = "Hoàng Bảo Trung - Học viên tiêu biểu của F8 tòa sáng với dự án \"AI Powered Learning\"";
            description = "Trong thời đại công nghệ số 4.0, việc học không còn bị buộc trong những cách sách truyền thống. Giờ đây, trí tuệ nhân tạo (AI) đang...";
            author = "Sơn Đặng";
            authorAvatar = "avatar4.jpg";
            tags = "React15,một năm trước,6 phút đọc";
            thumbnail = "article-featured-5.jpg";
            content = "<h2 class='section-title'>Câu chuyện của Hoàng Bảo Trung</h2><p>Hoàng Bảo Trung là một học viên tiêu biểu của F8 đã tạo ra dự án \"AI Powered Learning\" - một nền tảng học tập thông minh sử dụng trí tuệ nhân tạo.</p><ul class='benefits-list'><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Học tập từ F8 và phát triển kỹ năng</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Tạo ra dự án AI Powered Learning</span></li><li><svg viewBox='0 0 24 24' width='20' height='20' fill='#4CAF50'><path d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/></svg><span>Đóng góp cho cộng đồng lập trình</span></li></ul>";
            break;
        default:
            title = "Bài viết không tồn tại";
            description = "Không tìm thấy bài viết này.";
            author = "Admin";
            authorAvatar = "avatar1.jpg";
            tags = "";
            thumbnail = "article-featured-1.jpg";
            content = "<p>Bài viết không tồn tại hoặc đã bị xóa.</p>";
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= title %> - E-Learning System</title>
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
            <article class="article-detail-container">
                <!-- Article Header -->
                <div class="article-detail-header">
                    <div class="article-detail-top">
                        <div class="author-section">
                            <img src="${pageContext.request.contextPath}/learner/images/<%= authorAvatar %>" alt="<%= author %>" class="author-avatar-large" />
                            <span class="author-name-large"><%= author %></span>
                        </div>
                        <div class="article-actions-top">
                            <button class="bookmark-btn-large" aria-label="Bookmark">
                                <svg viewBox="0 0 24 24" width="24" height="24" fill="currentColor">
                                    <path d="M17 3H5c-1.11 0-2 .9-2 2v16l7-3 7 3V5c0-1.1.89-2 2-2z"/>
                                </svg>
                            </button>
                            <button class="menu-btn-large" aria-label="More options">
                                <svg viewBox="0 0 24 24" width="24" height="24" fill="currentColor">
                                    <path d="M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"/>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <h1 class="article-detail-title"><%= title %></h1>
                    
                    <p class="article-detail-description"><%= description %></p>

                    <div class="article-detail-meta">
                        <% String[] tagArray = tags.split(",");
                           for (String tag : tagArray) { %>
                            <span class="tag"><%= tag.trim() %></span>
                        <% } %>
                    </div>

                    <div class="article-detail-image">
                        <img src="${pageContext.request.contextPath}/learner/images/<%= thumbnail %>" alt="<%= title %>" />
                    </div>
                </div>

                <!-- Article Body -->
                <div class="article-detail-body">
                    <div class="article-content-left">
                        <div class="article-engagement">
                            <div class="author-info-sidebar">
                                <img src="${pageContext.request.contextPath}/learner/images/<%= authorAvatar %>" alt="<%= author %>" class="author-avatar-small" />
                                <span class="author-name-small"><%= author %></span>
                            </div>

                            <div class="engagement-buttons">
                                <button class="like-btn" onclick="toggleLike(this)">
                                    <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                        <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
                                    </svg>
                                    <span class="like-count">1</span>
                                </button>
                                <button class="comment-btn" onclick="toggleComments()">
                                    <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                        <path d="M20 2H4c-1.1 0-2 .9-2 2v18l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2z"/>
                                    </svg>
                                    <span class="comment-count">0</span>
                                </button>
                            </div>
                        </div>

                        <!-- Article Content Sections -->
                        <section class="content-section">
                            <%= content %>
                        </section>

                        <!-- Tags Section -->
                        <div class="article-tags-section">
                            <div class="tags-container">
                                <% for (String tag : tagArray) { %>
                                    <span class="tag-item"><%= tag.trim() %></span>
                                <% } %>
                            </div>
                        </div>

                        <!-- Related Articles Section -->
                        <section class="related-articles-section">
                            <h2 class="section-title">Bài đăng cùng tác giả</h2>
                            <p class="section-subtitle">Tác giả chưa có bài đăng nào khác.</p>
                            <div class="divider"></div>
                        </section>

                        <!-- Other Featured Articles Section -->
                        <section class="other-featured-section">
                            <h2 class="section-title">Bài viết nổi bật khác</h2>
                            <p class="section-subtitle">XEM CÁC BÀI VIẾT THEO CHỦ ĐỀ</p>

                            <div class="topic-filter-tabs">
                                <button class="topic-filter-tab active" onclick="filterRelatedArticles(this)">Front-end / Mobile apps</button>
                                <button class="topic-filter-tab" onclick="filterRelatedArticles(this)">Back-end / Devops</button>
                                <button class="topic-filter-tab" onclick="filterRelatedArticles(this)">UI / UX / Design</button>
                                <button class="topic-filter-tab" onclick="filterRelatedArticles(this)">Others</button>
                            </div>

                            <div class="related-articles-list">
                                <article class="related-article-card">
                                    <div class="related-article-header">
                                        <img src="${pageContext.request.contextPath}/learner/images/avatar2.jpg" alt="Hoàng Tuấn" class="related-author-avatar" />
                                        <span class="related-author-name">Hoàng Tuấn 12A1 40 Vĩ</span>
                                    </div>
                                    <h3 class="related-article-title"><a href="${pageContext.request.contextPath}/eduHome/articlesDetail.jsp?id=2">Giới thiệu về ngành Công Nghệ Thông Tin và Những Kiến Thức Cơ Bản Bắt Buộc Phải Học</a></h3>
                                    <p class="related-article-desc">Ngành Công Nghệ Thông Tin (CNTT) là một lĩnh vực dạng phát triển mạnh mẽ và có vai trò quan trọng trong thời đại số...</p>
                                    <div class="related-article-meta">
                                        <span class="tag">học lập trình</span>
                                        <span class="tag">3 bảng trước</span>
                                        <span class="tag">3 phút đọc</span>
                                    </div>
                                </article>

                                <article class="related-article-card">
                                    <div class="related-article-header">
                                        <img src="${pageContext.request.contextPath}/learner/images/avatar2.jpg" alt="Hoàng Tuấn" class="related-author-avatar" />
                                        <span class="related-author-name">Hoàng Tuấn 12A1 40 Vĩ</span>
                                    </div>
                                    <h3 class="related-article-title"><a href="${pageContext.request.contextPath}/eduHome/articlesDetail.jsp?id=3">SOLID - 5 nguyên lý "vàng" giúp viết code sạch và dễ bảo trì</a></h3>
                                    <p class="related-article-desc">Trong hành trình phát triển phần mềm, chắc hẳn bạn đã từng gặp phải những đoạn code dài, khó hiểu và rất khó để rồi hay chữa...</p>
                                    <div class="related-article-meta">
                                        <span class="tag">OOP</span>
                                        <span class="tag">3 bảng trước</span>
                                        <span class="tag">3 phút đọc</span>
                                    </div>
                                </article>
                            </div>
                        </section>
                    </div>
                </div>
            </article>
        </main>
    </div>
    <!-- Footer -->
     <jsp:include page="/learner/common/footer.jsp" />
   
    <!-- Added global theme script before page-specific JS -->
    <script src="${pageContext.request.contextPath}/learner/js/theme.js"></script>
    <script src="${pageContext.request.contextPath}/learner/js/articles.js"></script>
</body>
</html>
