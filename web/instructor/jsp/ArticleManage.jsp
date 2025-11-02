<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Kiểm tra nếu chưa có dữ liệu và không phải từ servlet, redirect về servlet để load -->
<c:if test="${empty articallist and empty fromServlet and empty error}">
    <c:redirect url="${pageContext.request.contextPath}/ManageArtical"/>
</c:if>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Bài viết - InstructorsHome</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/ArticleManagement.css">
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

                   <h3 style="margin-bottom: 30px;"><i class="fas fa-list"></i> Danh sách Bài viết</h3>
                   
                   <!-- Debug info (có thể xóa sau) -->
                   <c:if test="${not empty articallist}">
                       <p style="color: green; margin-bottom: 10px;">
                           <i class="fas fa-check-circle"></i> Tìm thấy ${fn:length(articallist)} bài viết
                       </p>
                   </c:if>
                   <c:if test="${empty articallist}">
                       <p style="color: orange; margin-bottom: 10px;">
                           <i class="fas fa-exclamation-triangle"></i> Không có bài viết nào (Danh sách rỗng)
                       </p>
                   </c:if>

                    <div class="card">
                        <div class="card-header">
                            Tất cả bài viết của bạn
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <!-- Redesigned table with modern styling -->
                                <table class="articles-table">
                                    <thead>
                                        <tr>
                                            <th><i class="fas fa-heading"></i> Tiêu đề</th>
                                            <th><i class="fas fa-align-left"></i> Nội dung</th>
                                            <th><i class="fas fa-calendar"></i> Ngày tạo</th>
                                            <th><i class="fas fa-tag"></i> Trạng thái</th>
                                            <th><i class="fas fa-cogs"></i> Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${empty articallist}">
                                                <tr>
                                                    <td colspan="5" style="text-align: center; padding: 40px; color: var(--text-muted);">
                                                        <i class="fas fa-inbox" style="font-size: 48px; margin-bottom: 15px; display: block;"></i>
                                                        <p style="font-size: 16px; margin: 0;">Chưa có bài viết nào. Hãy tạo bài viết mới!</p>
                                                    </td>
                                                </tr>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="artical" items="${articallist}">
                                                    <tr>
                                                        <td><span class="article-title">${artical.title}</span></td>
                                                        <td><span class="article-content">${artical.content}</span></td>
                                                        <td>
                                                            <span class="article-date">
                                                                <c:choose>
                                                                    <c:when test="${not empty artical.createAt}">
                                                                        <fmt:formatDate value="${artical.createAt}" pattern="dd/MM/yyyy HH:mm" />
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span style="color: var(--text-muted);">N/A</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${artical.status == 'Published'}">
                                                                    <span class="status-badge status-published">Published</span>
                                                                </c:when>
                                                                <c:when test="${artical.status == 'Draft'}">
                                                                    <span class="status-badge status-draft">Draft</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="status-badge status-pending">Pending</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <div class="table-actions">
                                                                <button type="button" 
                                                                        class="action-btn btn-edit"
                                                                        onclick="showEditArticle('${artical.articleID}')"
                                                                        data-article-id="${artical.articleID}"
                                                                        data-article-title="${fn:escapeXml(artical.title)}"
                                                                        data-article-content="${fn:escapeXml(artical.content)}"
                                                                        data-article-status="${artical.status}">
                                                                    <i class="fas fa-edit"></i> Sửa
                                                                </button>

                                                                <a href="<c:url value='/ManageArtical?action=deleteartical&id=${artical.articleID}'/>"
                                                                   class="action-btn btn-delete"
                                                                   onclick="return confirm('Xóa bài viết này?');"><i class="fas fa-trash"></i> Xóa</a>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Comments Management -->
                <section class="section">
                    <h3 style="margin-bottom: 30px;"><i class="fas fa-comments"></i> Quản lý Bình luận</h3>
                    <div class="card">
                        <div class="card-header">
                            Tất cả bình luận
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
                            </div>
                        </div>
                    </div>
</section>

                <!-- Article Form Modal (Create) -->
                <div id="articleFormModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 1000;">
                    <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: var(--bg-card); padding: 30px; border-radius: 8px; width: 90%; max-width: 800px; max-height: 80vh; overflow-y: auto;">
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                            <h3><i class="fas fa-plus"></i> Viết bài mới</h3>
                            <button onclick="hideArticleForm()" style="background: none; border: none; font-size: 24px; cursor: pointer;">&times;</button>
                        </div>

                        <form id="createArticleForm" action="<c:url value='/ManageArtical'/>" method="post">
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
                                <select id="articleStatus" name="statusArtical" class="form-control">
                                    <option value="Draft">Draft</option>
                                    <option value="Published">Published</option>
                                    <option value="Pending Review">Pending Review</option>
                                </select>
                            </div>

                            <div style="display:flex;gap:10px;justify-content:flex-end;margin-top:20px;">
                                <button type="button" class="btn btn-secondary" onclick="hideArticleForm()">Hủy</button>
                                <button type="submit" class="btn btn-primary">
                                    Xuất bản
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Article Edit Modal -->
                <div id="editArticleModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 1000;">
                    <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: var(--bg-card); padding: 30px; border-radius: 8px; width: 90%; max-width: 800px; max-height: 80vh; overflow-y: auto;">
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                            <h3><i class="fas fa-edit"></i> Sửa bài viết</h3>
                            <button onclick="hideEditArticle()" style="background: none; border: none; font-size: 24px; cursor: pointer;">&times;</button>
                        </div>

                        <form id="editArticleForm" action="<c:url value='/ManageArtical'/>" method="post">
                            <input type="hidden" name="action" value="updateartical">
                            <input type="hidden" name="id" id="editArticleId" value="">

                            <div class="form-group">
                                <label for="editArticleTitle">Tiêu đề bài viết *</label>
                                <input name="titleartical" type="text" id="editArticleTitle" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="editArticleContent">Nội dung bài viết *</label>
                                <textarea name="contentArtical" id="editArticleContent" class="form-control" rows="10" required></textarea>
                            </div>

                            <div class="form-group">
                                <label for="editArticleStatus">Trạng thái</label>
                                <select id="editArticleStatus" name="statusArtical" class="form-control">
                                    <option value="Draft">Draft</option>
                                    <option value="Published">Published</option>
                                    <option value="Pending Review">Pending Review</option>
                                </select>
                            </div>

                            <div style="display:flex;gap:10px;justify-content:flex-end;margin-top:20px;">
                                <button type="button" class="btn btn-secondary" onclick="hideEditArticle()">Hủy</button>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Lưu thay đổi
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div>

        <script src="${pageContext.request.contextPath}/assets/js/instructorsHome.js"></script>
        <script src="${pageContext.request.contextPath}/instructor/js/articleManage.js"></script>
        <script>
            function showArticleForm() {
                document.getElementById('articleFormModal').style.display = 'block';
                // Reset form
                document.getElementById('createArticleForm').reset();
            }

            function hideArticleForm() {
                document.getElementById('articleFormModal').style.display = 'none';
            }

            function showEditArticle(articleId) {
                // Tìm button đã click để lấy dữ liệu
                const buttons = document.querySelectorAll('.btn-edit[data-article-id]');
                let articleData = null;
                
                for (let btn of buttons) {
                    if (btn.getAttribute('data-article-id') === articleId) {
                        articleData = {
                            id: btn.getAttribute('data-article-id'),
                            title: btn.getAttribute('data-article-title'),
                            content: btn.getAttribute('data-article-content'),
                            status: btn.getAttribute('data-article-status')
                        };
                        break;
                    }
                }
                
                if (!articleData) {
                    alert('Không tìm thấy dữ liệu bài viết!');
                    return;
                }
                
                // Fill form với dữ liệu bài viết
                document.getElementById('editArticleId').value = articleData.id;
                document.getElementById('editArticleTitle').value = articleData.title || '';
                document.getElementById('editArticleContent').value = articleData.content || '';
                document.getElementById('editArticleStatus').value = articleData.status || 'Draft';
                
                // Hiển thị modal
                document.getElementById('editArticleModal').style.display = 'block';
            }

            function hideEditArticle() {
                document.getElementById('editArticleModal').style.display = 'none';
                // Reset form
                document.getElementById('editArticleForm').reset();
            }

            // Close modal when clicking outside
            window.onclick = function (event) {
                const createModal = document.getElementById('articleFormModal');
                const editModal = document.getElementById('editArticleModal');
                
                if (event.target === createModal) {
                    hideArticleForm();
                }
                if (event.target === editModal) {
                    hideEditArticle();
                }
            }
        </script>
    </body>
</html>
