<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán QR Code</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/QR/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/QR/css/QR.css">
</head>
<body class="light-mode">
    <!-- Header -->
    <jsp:include page="/QR/common/header.jsp" />

    <div class="qr-main-container">
        <!-- Sidebar -->
        <jsp:include page="/QR/common/sidebar.jsp" />

        <main class="qr-main-content">
            <!-- Main Content -->
            <div class="content">
                <h1 class="page-title">Thanh toán qua QR Code</h1>
                
                <div class="qrpay-container">
                    <!-- Course List Section -->
                    <section class="qrpay-course-section">
                        <h2 class="qrpay-section-title">Các khóa học đã mua</h2>
                        
                        <ul class="qrpay-course-list">
                            <c:forEach var="item" items="${CourseInCart}" varStatus="status">
                                <li class="qrpay-course-item">
                                    <span class="qrpay-course-name">${item.name}</span>
                                    <span class="qrpay-course-price">${item.price} ₫</span>
                                </li>
                            </c:forEach>
                        </ul>

                        
                        <div class="qrpay-total-section">
                            <span>Tổng số tiền:</span>
                                <span class="qrpay-total-amount">
                                    <fmt:formatNumber value="${price}" type="currency" currencyCode="VND" var="formattedPrice"/>
                                <span class="price-amount">${formattedPrice}</span>
                            </span>
                        </div>
                    </section>
                    
                    <!-- QR Code Section -->
                    <section class="qrpay-payment-section">
                        <div class="qrpay-code-container">
                            <div class="qrpay-code-placeholder">
                                <img id="qr" 
                                    src="https://qr.sepay.vn/img?acc=0911245428&bank=MBBANK&amount=${price}&des=${noiDung}" 
                                    alt="QR Sepay">
                            </div>
                        </div>
                        <div id="success"></div>
                        <button class="qrpay-home-button"
                            onclick="window.location.href='${pageContext.request.contextPath}/CourseServletController'">
                            Về trang chủ
                        </button>

                    </section>
                </div>
            </div>
        </main>
    </div>
    <script>
        // Sử dụng keyword từ server-side (thống nhất)
        const price = "${price}";
        const noiDung = "${noiDung}";
        const initialCheck = ${check};
        const userID = "${userID}";
        
        console.log('💰 Số tiền:', price);
        console.log('🔑 Mã giao dịch:', noiDung);
        console.log('🔍 Trạng thái ban đầu:', initialCheck);
    </script>
    <script src="${pageContext.request.contextPath}/QR/js/transactionChecker.js"></script>
    <script src="${pageContext.request.contextPath}/QR/js/QR.js"></script>
</body>
</html>