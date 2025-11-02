<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<%
    // Mock data - Replace with actual database queries
//    java.util.List<java.util.Map<String, Object>> cartItems = new java.util.ArrayList<>();
//    
//    // Sample cart items
//    java.util.Map<String, Object> item1 = new java.util.HashMap<>();
//    item1.put("cartID", "1");
//    item1.put("courseID", "1");
//    item1.put("courseName", "Kiến Thức Nhập Môn IT");
//    item1.put("courseDescription", "Khóa học cơ bản về IT, từ những khái niệm cơ bản đến các ứng dụng thực tế trong công việc.");
//    item1.put("courseImage", "${pageContext.request.contextPath}/assets/images/course-thumb.jpg");
//    item1.put("coursePrice", 299000);
//    item1.put("courseLevel", "Beginner");
//    item1.put("courseRating", 4.5);
//    item1.put("instructorName", "Hoàng Lộc");
//    item1.put("instructorAvatar", "${pageContext.request.contextPath}/assets/images/avatar1.jpg");
//    cartItems.add(item1);
//    
//    java.util.Map<String, Object> item2 = new java.util.HashMap<>();
//    item2.put("cartID", "2");
//    item2.put("courseID", "2");
//    item2.put("courseName", "Lập Trình Web Frontend");
//    item2.put("courseDescription", "Học HTML, CSS, JavaScript và các framework hiện đại để xây dựng giao diện web đẹp mắt.");
//    item2.put("courseImage", "${pageContext.request.contextPath}/assets/images/course-thumb2.jpg");
//    item2.put("coursePrice", 499000);
//    item2.put("courseLevel", "Intermediate");
//    item2.put("courseRating", 4.8);
//    item2.put("instructorName", "Nguyễn Minh");
//    item2.put("instructorAvatar", "${pageContext.request.contextPath}/assets/images/avatar2.jpg");
//    cartItems.add(item2);
//    
//    java.util.Map<String, Object> item3 = new java.util.HashMap<>();
//    item3.put("cartID", "3");
//    item3.put("courseID", "3");
//    item3.put("courseName", "Python Cho Người Mới Bắt Đầu");
//    item3.put("courseDescription", "Khóa học Python từ cơ bản đến nâng cao, phù hợp cho người mới học lập trình.");
//    item3.put("courseImage", "${pageContext.request.contextPath}/assets/images/course-thumb3.jpg");
//    item3.put("coursePrice", 0);
//    item3.put("courseLevel", "Beginner");
//    item3.put("courseRating", 4.2);
//    item3.put("instructorName", "Trần Thị Lan");
//    item3.put("instructorAvatar", "${pageContext.request.contextPath}/assets/images/avatar3.jpg");
//    cartItems.add(item3);
//    
//    // Calculate totals
//    int totalItems = cartItems.size();
//    long totalAmount = 0;
//   for (java.util.Map<String, Object> item : cartItems) {
//    totalAmount += ((Number) item.get("coursePrice")).longValue();
//}
//    
//    request.setAttribute("cartItems", cartItems);
//    request.setAttribute("totalItems", totalItems);
//    request.setAttribute("totalAmount", totalAmount);
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🛒 Giỏ hàng của tôi - E-Learning</title>
    <!-- Updated CSS path to src/css/cart.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/cart.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <!-- Header -->
    <jsp:include page="/learner/common/header.jsp" />

    <div class="main-container">
        <!-- Sidebar -->
        <jsp:include page="/learner/common/sidebar.jsp" />

        <!-- Main Content -->
        <main class="main-content">
        <div class="cart-container">
            <!-- Cart Header -->
            <div class="cart-header">
                <div class="cart-title">
                    <h1><i class="fas fa-shopping-cart"></i> Giỏ hàng của tôi</h1>
                    <p class="cart-summary">
                        <c:choose>
                            <c:when test="${NumberInCart > 0}">
                                Bạn có <strong>${NumberInCart}</strong> khóa học trong giỏ hàng.
                            </c:when>
                            <c:otherwise>
                                Giỏ hàng của bạn đang trống.
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>

            <div class="cart-content">
                <c:choose>
                    <c:when test="${NumberInCart > 0}">
                        <!-- Cart Items -->
                        <div class="cart-items">
                            <c:forEach var="item" items="${CourseInCart}" varStatus="status">
                                <div class="cart-item" >
                                    <div class="item-image">
                                        <img src="${item.imgURL}" alt="${Citem.name}" >
                                    </div>
                                    
                                    <div class="item-details">
                                        <h3 class="item-title">${item.name}</h3>
                                        <p class="item-description">
                                            <c:choose>
                                                <c:when test="${fn:length(item.description) > 100}">
                                                    ${fn:substring(item.description, 0, 100)}...
                                                </c:when>
                                                <c:otherwise>
                                                    ${item.description}
                                                </c:otherwise>
                                            </c:choose>
                                        </p>
                                        
                                        <div class="item-meta">
                                            <div class="instructor-info">
                                                <span class="instructor-name">👨‍🏫 </span>
                                            </div>
                                            
                                            <div class="course-info">
                                                <span class="course-level level-${fn:toLowerCase(item.level)}">📊 ${item.level}</span>
                                                <span class="course-rating">⭐ ${item.rating}</span>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="item-price">
                                        <c:choose>
                                            <c:when test="${item.price == 0}">
                                                <span class="price-free">Miễn phí</span>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatNumber value="${item.price}" type="currency" currencyCode="VND" var="formattedPrice"/>
                                                <span class="price-amount">${formattedPrice}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    
                                    <div class="item-actions">
                                        <button class="btn-detail" onclick="viewCourseDetail('${item.courseID}')">
                                            <i class="fas fa-eye"></i> Xem chi tiết
                                        </button>
                                            
                                        <form action="${pageContext.request.contextPath}/DeleteCartServlet" method="post"> 
                                        <input type="hidden" name="courseID" value="${item.courseID}">
                                        <button type="submit" class="btn-remove" >
                                            <i class="fas fa-trash"></i> Xóa
                                        </button>
                                        </form>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <!-- Cart Summary -->
                        <div class="cart-summary-section">
                            <div class="summary-card">
                                <h3><i class="fas fa-receipt"></i> Tóm tắt đơn hàng</h3>
                                
                                <div class="summary-details">
                                    <div class="summary-row">
                                        <span>Tổng số khóa học:</span>
                                        <span class="summary-value">${NumberInCart}</span>
                                    </div>
                                    
                                    <div class="summary-row">
                                        <span>Tổng cộng:</span>
                                        <span class="summary-value total-amount">
                                            <fmt:formatNumber value="${cartPrice}" type="currency" currencyCode="VND" var="formattedPrice"/>
                                            <span class="price-amount">${formattedPrice}</span>
                                        </span>
                                    </div>
                                    
                                    <div class="summary-row discount-row">
                                        <label for="discount-code">🎟️ Mã giảm giá:</label>
                                        <div class="discount-input-group">
                                            <input type="text" id="discount-code" placeholder="Nhập mã giảm giá">
                                            <button class="btn-apply-discount" onclick="applyDiscount()">
                                                <i class="fas fa-check"></i> Áp dụng
                                            </button>
                                        </div>
                                    </div>
                                    
                                    <div class="summary-row final-total">
                                        <span><strong>Thành tiền:</strong></span>
                                        <span class="summary-value final-amount">
                                            <fmt:formatNumber value="${cartPrice}" type="currency" currencyCode="VND" var="formattedPrice"/>
                                            <span class="price-amount">${formattedPrice}</span>
                                        </span>
                                    </div>
                                </div>
                                
                                <button class="btn-checkout" onclick="proceedToCheckout()">
                                    <i class="fas fa-credit-card"></i> Thanh toán
                                </button>
                            </div>
                        </div>
                    </c:when>
                    
                    <c:otherwise>
                        <!-- Empty Cart -->
                        <div class="empty-cart">
                            <div class="empty-cart-icon">
                                <i class="fas fa-shopping-cart"></i>
                            </div>
                            <h2>Giỏ hàng của bạn đang trống</h2>
                            <p>Hãy khám phá các khóa học thú vị và thêm vào giỏ hàng!</p>
                            <button class="btn-continue-shopping" onclick="continueShopping()">
                                <i class="fas fa-arrow-left"></i> Tiếp tục học
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        </main>
    </div>

    <!-- Footer -->
    <jsp:include page="/learner/common/footer.jsp" />

        <script src="${pageContext.request.contextPath}/learner/js/theme.js"></script>
        <script src="${pageContext.request.contextPath}/learner/js/cart.js"></script>
</body>
</html>