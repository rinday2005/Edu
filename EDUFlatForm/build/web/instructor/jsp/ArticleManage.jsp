<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Bài viết - InstructorsHome</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
        <div class="main-container">
            <!-- ===== HEADER ===== -->
            <jsp:include page="/instructor/common/header.jsp" />

            <!-- ===== SIDEBAR ===== -->
            <jsp:include page="/instructor/common/sidebar.jsp" />

            <main class="content-area">
                <!-- Header Section -->
                <section class="section">
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <h2><i class="fas fa-blog"></i> Quản lý Bài viết</h2>
                        <button class="btn btn-primary" onclick="showArticleForm()">
                            <i class="fas fa-plus"></i> Viết bài mới
                        </button>
                    </div>
                </section>

                <!-- Articles List -->
                <section class="section">
                    <h3><i class="fas fa-list"></i> Danh sách Bài viết</h3>
                    <div class="card">
                        <div class="card-header">
                            Tất cả bài viết của bạn
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Tiêu đề</th>
                                            <th>Ngày tạo</th>
                                            <th>Trạng thái</th>
                                            <th>Lượt xem</th>
                                            <th>Bình luận</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div>
                                                    <div style="font-weight: 600;">10 Tips để học React hiệu quả</div>
                                                    <div style="font-size: 12px; color: var(--text-muted);">Hướng dẫn học React từ cơ bản đến nâng cao</div>
                                                </div>
                                            </td>
                                            <td>15/12/2024</td>
                                            <td><span class="btn btn-success" style="padding: 3px 8px; font-size: 11px;">Published</span></td>
                                            <td><strong>1,245</strong></td>
                                            <td><strong>23</strong></td>
                                            <td>
                                                <div style="display: flex; gap: 5px;">
                                                    <button class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                                        <i class="fas fa-edit"></i>
                                                    </button>
                                                    <button class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;">
                                                        <i class="fas fa-trash"></i>
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                        <!--                                    <tr>
                                                                                <td>
                                                                                    <div>
                                                                                        <div style="font-weight: 600;">JavaScript ES6+ Features mới nhất</div>
                                                                                        <div style="font-size: 12px; color: var(--text-muted);">Tổng hợp các tính năng mới trong JavaScript</div>
                                                                                    </div>
                                                                                </td>
                                                                                <td>12/12/2024</td>
                                                                                <td><span class="btn btn-success" style="padding: 3px 8px; font-size: 11px;">Published</span></td>
                                                                                <td><strong>892</strong></td>
                                                                                <td><strong>15</strong></td>
                                                                                <td>
                                                                                    <div style="display: flex; gap: 5px;">
                                                                                        <button class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                                                                            <i class="fas fa-edit"></i>
                                                                                        </button>
                                                                                        <button class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;">
                                                                                            <i class="fas fa-trash"></i>
                                                                                        </button>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <div>
                                                                                        <div style="font-weight: 600;">Python vs JavaScript: So sánh chi tiết</div>
                                                                                        <div style="font-size: 12px; color: var(--text-muted);">Phân tích ưu nhược điểm của hai ngôn ngữ</div>
                                                                                    </div>
                                                                                </td>
                                                                                <td>10/12/2024</td>
                                                                                <td><span class="btn btn-warning" style="padding: 3px 8px; font-size: 11px; background-color: #ffc107; color: #000;">Draft</span></td>
                                                                                <td><strong>0</strong></td>
                                                                                <td><strong>0</strong></td>
                                                                                <td>
                                                                                    <div style="display: flex; gap: 5px;">
                                                                                        <button class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                                                                            <i class="fas fa-edit"></i>
                                                                                        </button>
                                                                                        <button class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;">
                                                                                            <i class="fas fa-trash"></i>
                                                                                        </button>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <div>
                                                                                        <div style="font-weight: 600;">Hướng dẫn setup môi trường Node.js</div>
                                                                                        <div style="font-size: 12px; color: var(--text-muted);">Cài đặt và cấu hình Node.js từ A-Z</div>
                                                                                    </div>
                                                                                </td>
                                                                                <td>08/12/2024</td>
                                                                                <td><span class="btn btn-info" style="padding: 3px 8px; font-size: 11px; background-color: #17a2b8;">Pending Review</span></td>
                                                                                <td><strong>567</strong></td>
                                                                                <td><strong>8</strong></td>
                                                                                <td>
                                                                                    <div style="display: flex; gap: 5px;">
                                                                                        <button class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                                                                            <i class="fas fa-edit"></i>
                                                                                        </button>
                                                                                        <button class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;">
                                                                                            <i class="fas fa-trash"></i>
                                                                                        </button>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>-->
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Comments Management -->
                <section class="section">
                    <h3><i class="fas fa-comments"></i> Quản lý Bình luận</h3>
                    <div class="card">
                        <div class="card-header">
                            Bình luận mới nhất
                        </div>
                        <div class="card-body">
                            <div class="activity-list">
                                <div class="activity-item">
                                    <div class="activity-icon">
                                        <i class="fas fa-user"></i>
                                    </div>
                                    <div class="activity-content">
                                        <h4>Nguyễn Văn A</h4>
                                        <p>"Bài viết rất hay và hữu ích! Cảm ơn tác giả đã chia sẻ."</p>
                                        <p style="font-size: 12px; color: var(--text-muted);">Bài viết: 10 Tips để học React hiệu quả • 2 giờ trước</p>
                                    </div>
                                    <div style="display: flex; gap: 5px;">
                                        <button class="btn btn-success" style="padding: 5px 10px; font-size: 12px;">
                                            <i class="fas fa-check"></i> Duyệt
                                        </button>
                                        <button class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;">
                                            <i class="fas fa-times"></i> Xóa
                                        </button>
                                    </div>
                                </div>
                                <!--                            <div class="activity-item">
                                                                <div class="activity-icon">
                                                                    <i class="fas fa-user"></i>
                                                                </div>
                                                                <div class="activity-content">
                                                                    <h4>Trần Thị B</h4>
                                                                    <p>"Có thể bạn có thể giải thích thêm về phần hooks không?"</p>
                                                                    <p style="font-size: 12px; color: var(--text-muted);">Bài viết: JavaScript ES6+ Features mới nhất • 4 giờ trước</p>
                                                                </div>
                                                                <div style="display: flex; gap: 5px;">
                                                                    <button class="btn btn-success" style="padding: 5px 10px; font-size: 12px;">
                                                                        <i class="fas fa-check"></i> Duyệt
                                                                    </button>
                                                                    <button class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;">
                                                                        <i class="fas fa-times"></i> Xóa
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <div class="activity-item">
                                                                <div class="activity-icon">
                                                                    <i class="fas fa-user"></i>
                                                                </div>
                                                                <div class="activity-content">
                                                                    <h4>Lê Văn C</h4>
                                                                    <p>"Tuyệt vời! Đây chính là những gì tôi đang tìm kiếm."</p>
                                                                    <p style="font-size: 12px; color: var(--text-muted);">Bài viết: Hướng dẫn setup môi trường Node.js • 6 giờ trước</p>
                                                                </div>
                                                                <div style="display: flex; gap: 5px;">
                                                                    <button class="btn btn-success" style="padding: 5px 10px; font-size: 12px;">
                                                                        <i class="fas fa-check"></i> Duyệt
                                                                    </button>
                                                                    <button class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;">
                                                                        <i class="fas fa-times"></i> Xóa
                                                                    </button>
                                                                </div>
                                                            </div>-->
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Article Form Modal -->
                <div id="articleFormModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 1000;">
                    <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: var(--bg-card); padding: 30px; border-radius: 8px; width: 90%; max-width: 800px; max-height: 80vh; overflow-y: auto;">
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                            <h3><i class="fas fa-plus"></i> Viết bài mới</h3>
                            <button onclick="hideArticleForm()" style="background: none; border: none; font-size: 24px; cursor: pointer;">&times;</button>
                        </div>

                        <form action="<c:url value='/ManageArtical'/>" method="post">
                            <input type="hidden" name="action" value="createArtical">

                            <div class="form-group">
                                <label for="articleTitle">Tiêu đề bài viết *</label>
                                <input name="titleartical" type="text" id="articleTitle" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="articleContent">Nội dung bài viết *</label>
                                <textarea name="contentArtical" id="articleContent" class="form-control" rows="10" required></textarea>
                            </div>

                            <div class="form-group">
                                <label for="articleStatus">Trạng thái</label>
                                <!-- PHẢI có name để submit -->
                                <select id="articleStatus" name="statusArtical" class="form-control">
                                    <option value="Draft">Draft</option>
                                    <option value="Published">Published</option>
                                    <option value="Pending Review">Pending Review</option>
                                </select>
                            </div>

                            <div style="display:flex;gap:10px;justify-content:flex-end;margin-top:20px;">
                                <button type="button" class="btn btn-secondary" onclick="hideArticleForm()">Hủy</button>
                                <!-- 2 nút này đổi value của select nếu bạn muốn -->
                                <button type="submit" class="btn btn-warning" onclick="document.getElementById('articleStatus').value = 'Draft'">
                                    Lưu nháp
                                </button>
                                <button type="submit" class="btn btn-primary"  onclick="document.getElementById('articleStatus').value = 'Published'">
                                    Xuất bản
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div>

        <script src="${pageContext.request.contextPath}/assets/js/instructorsHome.js"></script>
        <script>
            function showArticleForm() {
                document.getElementById('articleFormModal').style.display = 'block';
            }

            function hideArticleForm() {
                document.getElementById('articleFormModal').style.display = 'none';
            }

            // Close modal when clicking outside
            window.onclick = function (event) {
                const modal = document.getElementById('articleFormModal');
                if (event.target === modal) {
                    hideArticleForm();
                }
            }
        </script>
    </body>
</html>