<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tech Instructor Dashboard - Quản lý Khóa học Công nghệ</title>
        
        <%-- CSS và Font Awesome (Dùng EL) --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        
        <%-- THƯ VIỆN CHART.JS CHO BIỂU ĐỒ --%>
        <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.2/dist/chart.umd.min.js"></script>
    </head>
<body>
    <div class="main-container">
         <!-- ===== HEADER ===== -->
    <jsp:include page="/instructor/common/header.jsp" />

    <!-- ===== SIDEBAR ===== -->
   <jsp:include page="/instructor/common/sidebar.jsp" />
   

        <main class="content-area">
            <section class="section">
                <h2><i class="fas fa-tachometer-alt"></i> Bảng điều khiển</h2>
                <div class="welcome-content">
                    <p>Chào mừng bạn đến với hệ thống quản lý khóa học. Dưới đây là tổng quan về hoạt động giảng dạy của bạn.</p>
                </div>
            </section>

            <section class="section">
                <h2><i class="fas fa-chart-bar"></i> Thống kê Tổng quan</h2>
                <div class="stats-grid">
                    <div class="stat-card">
                        <h3><i class="fas fa-graduation-cap"></i> Tổng số Khóa học</h3>
                        <div class="stat-number">12</div>
                        <div class="stat-description">Khóa học đã tạo (Draft: 3, Published: 9)</div>
                    </div>
                    <div class="stat-card">
                        <h3><i class="fas fa-users"></i> Tổng số Học viên</h3>
                        <div class="stat-number">1,247</div>
                        <div class="stat-description">Học viên đang theo dõi các khóa học</div>
                    </div>
                    <div class="stat-card">
                        <h3><i class="fas fa-dollar-sign"></i> Tổng Thu nhập</h3>
                        <div class="stat-number">$15,420</div>
                        <div class="stat-description">Thu nhập từ các khóa học</div>
                    </div>
                    <div class="stat-card">
                        <h3><i class="fas fa-star"></i> Đánh giá Mới</h3>
                        <div class="stat-number">23</div>
                        <div class="stat-description">Đánh giá mới trong tuần này</div>
                    </div>
                </div>
            </section>

            <%-- KHU VỰC BIỂU ĐỒ BÁO CÁO (Đã Thêm Canvas) --%>
            <section class="section">
                <h2><i class="fas fa-chart-line"></i> Biểu đồ Báo cáo</h2>
                <div class="stats-grid">
                    <div class="stat-card">
                        <h3><i class="fas fa-chart-area"></i> Doanh thu theo tháng</h3>
                        <div class="chart-placeholder" style="height: 250px;">
                            <p style="margin-bottom: 15px; color: var(--text-muted);">Biểu đồ doanh thu 6 tháng gần nhất</p>
                            <canvas id="revenueChart" style="max-height: 200px;"></canvas>
                        </div>
                    </div>
                    <div class="stat-card">
                        <h3><i class="fas fa-user-plus"></i> Đăng ký Khóa học</h3>
                        <div class="chart-placeholder" style="height: 250px;">
                            <p style="margin-bottom: 15px; color: var(--text-muted);">Biểu đồ đăng ký theo thời gian</p>
                            <canvas id="enrollmentChart" style="max-height: 200px;"></canvas>
                        </div>
                    </div>
                </div>
            </section>

            <section class="section">
                <h2><i class="fas fa-clock"></i> Hoạt động Gần đây</h2>
                <div class="activity-list">
                    <div class="activity-item">
                        <div class="activity-icon">
                            <i class="fas fa-star"></i>
                        </div>
                        <div class="activity-content">
                            <h4>Đánh giá mới: "Khóa học React rất hay!"</h4>
                            <p>2 giờ trước • Khóa học React.js • Điểm: 5/5</p>
                        </div>
                    </div>
                    <div class="activity-item">
                        <div class="activity-icon">
                            <i class="fas fa-tasks"></i>
                        </div>
                        <div class="activity-content">
                            <h4>Bài nộp mới: Assignment JavaScript</h4>
                            <p>4 giờ trước • Học viên: Nguyễn Văn A</p>
                        </div>
                    </div>
                    <div class="activity-item">
                        <div class="activity-icon">
                            <i class="fas fa-user-plus"></i>
                        </div>
                        <div class="activity-content">
                            <h4>15 học viên mới đăng ký khóa học Python</h4>
                            <p>6 giờ trước • Khóa học Python cơ bản</p>
                        </div>
                    </div>
                    <div class="activity-item">
                        <div class="activity-icon">
                            <i class="fas fa-star"></i>
                        </div>
                        <div class="activity-content">
                            <h4>Đánh giá mới: "Nội dung rất chi tiết"</h4>
                            <p>8 giờ trước • Khóa học HTML/CSS • Điểm: 4/5</p>
                        </div>
                    </div>
                    <div class="activity-item">
                        <div class="activity-icon">
                            <i class="fas fa-tasks"></i>
                        </div>
                        <div class="activity-content">
                            <h4>Bài nộp mới: Assignment HTML</h4>
                            <p>10 giờ trước • Học viên: Trần Thị B</p>
                        </div>
                    </div>
                </div>
            </section>

            <section class="section">
                <h2><i class="fas fa-bell"></i> Thông báo & Cảnh báo</h2>
                <div class="activity-list">
                    <div class="activity-item">
                        <div class="activity-icon" style="background-color: #ffc107;">
                            <i class="fas fa-exclamation-triangle"></i>
                        </div>
                        <div class="activity-content">
                            <h4>Khóa học "Node.js Advanced" cần được phê duyệt</h4>
                            <p>Đang chờ admin phê duyệt để xuất bản</p>
                        </div>
                    </div>
                    <div class="activity-item">
                        <div class="activity-icon" style="background-color: #28a745;">
                            <i class="fas fa-money-bill-wave"></i>
                        </div>
                        <div class="activity-content">
                            <h4>Giao dịch mới: +$250 từ khóa học React</h4>
                            <p>5 học viên mới đăng ký khóa học React.js</p>
                        </div>
                    </div>
                    <div class="activity-item">
                        <div class="activity-icon" style="background-color: #17a2b8;">
                            <i class="fas fa-info-circle"></i>
                        </div>
                        <div class="activity-content">
                            <h4>Nhắc nhở: Cập nhật nội dung khóa học JavaScript</h4>
                            <p>Khóa học đã được tạo 30 ngày, cần cập nhật nội dung</p>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    </div>

    <%-- SCRIPT JS (Dùng EL) --%>
    <script src="${pageContext.request.contextPath}/instructor/js/instructorsHome.js"></script>
</body>
</html>
