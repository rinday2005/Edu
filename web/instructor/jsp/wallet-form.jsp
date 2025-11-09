<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cập nhật Thông tin Ví</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/wallet-form-styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
 <div class="main-container">   
    
     <!-- ===== HEADER ===== -->
    <jsp:include page="/instructor/common/header.jsp" />

    <!-- ===== SIDEBAR ===== -->
    <jsp:include page="/instructor/common/sidebar.jsp" />
    
    
    <div class="wf-container">
        <div class="wf-card">
            <div class="wf-header">
                <i class="fas fa-wallet"></i>
                <h1>Cập nhật Thông tin Ví</h1>
                <p>Vui lòng nhập thông tin ngân hàng của bạn</p>
            </div>

            <!-- Hiển thị thông báo thành công -->
            <c:if test="${not empty successMessage}">
                <div class="wf-success-message">
                    <i class="fas fa-check-circle"></i>
                    <span><c:out value="${successMessage}"/></span>
                </div>
            </c:if>

            <!-- Hiển thị thông báo lỗi -->
            <c:if test="${not empty errorMessage}">
                <div class="wf-error-message">
                    <i class="fas fa-exclamation-circle"></i>
                    <span><c:out value="${errorMessage}"/></span>
                </div>
            </c:if>

            <!-- Form gửi dữ liệu đến Servlet -->
            <form action="${pageContext.request.contextPath}/addWalletServlet" 
                  method="post" 
                  class="wf-form" 
                  onsubmit="return validateForm()">
                
                <div class="wf-info-grid">
                    <!-- Ngân hàng -->
                    <div class="wf-info-item">
                        <label for="bankName" class="wf-label">
                            <i class="fas fa-university"></i>
                            Ngân hàng
                        </label>
                        <select id="bankName" name="bankName" class="wf-input wf-select" required>
                            <option value="">-- Chọn ngân hàng --</option>
                            <option value="Vietcombank" <c:if test="${param.bankName == 'Vietcombank'}">selected</c:if>>Vietcombank (VCB)</option>
                            <option value="Techcombank" <c:if test="${param.bankName == 'Techcombank'}">selected</c:if>>Techcombank (TCB)</option>
                            <option value="BIDV" <c:if test="${param.bankName == 'BIDV'}">selected</c:if>>BIDV</option>
                            <option value="VietinBank" <c:if test="${param.bankName == 'VietinBank'}">selected</c:if>>VietinBank</option>
                            <option value="Agribank" <c:if test="${param.bankName == 'Agribank'}">selected</c:if>>Agribank</option>
                            <option value="ACB" <c:if test="${param.bankName == 'ACB'}">selected</c:if>>ACB</option>
                            <option value="MBBank" <c:if test="${param.bankName == 'MBBank'}">selected</c:if>>MBBank</option>
                            <option value="VPBank" <c:if test="${param.bankName == 'VPBank'}">selected</c:if>>VPBank</option>
                            <option value="TPBank" <c:if test="${param.bankName == 'TPBank'}">selected</c:if>>TPBank</option>
                            <option value="Sacombank" <c:if test="${param.bankName == 'Sacombank'}">selected</c:if>>Sacombank</option>
                            <option value="HDBank" <c:if test="${param.bankName == 'HDBank'}">selected</c:if>>HDBank</option>
                            <option value="SHB" <c:if test="${param.bankName == 'SHB'}">selected</c:if>>SHB</option>
                            <option value="VIB" <c:if test="${param.bankName == 'VIB'}">selected</c:if>>VIB</option>
                            <option value="MSB" <c:if test="${param.bankName == 'MSB'}">selected</c:if>>MSB</option>
                            <option value="OCB" <c:if test="${param.bankName == 'OCB'}">selected</c:if>>OCB</option>
                        </select>
                        <span class="wf-error" id="bankNameError"></span>
                    </div>
                    
                    <!-- Số tài khoản -->
                    <div class="wf-info-item">
                        <label for="bankAccount" class="wf-label">
                            <i class="fas fa-credit-card"></i>
                            Số tài khoản
                        </label>
                        <input 
                            type="text" 
                            id="bankAccount" 
                            name="bankAccount" 
                            class="wf-input" 
                            placeholder="Nhập số tài khoản (9-18 chữ số)"
                            maxlength="18"
                            value="<c:out value='${param.bankAccount}'/>"
                            required
                        >
                        <span class="wf-error" id="bankAccountError"></span>
                    </div>
                    
                    <!-- Cập nhật -->
                    <div class="wf-info-item">
                        <label for="updateDate" class="wf-label">
                            <i class="fas fa-calendar-alt"></i>
                            Cập nhật
                        </label>
                        <input 
                            type="text" 
                            id="updateDate" 
                            name="updateDate" 
                            class="wf-input wf-readonly" 
                            value="Hôm nay" 
                            readonly
                        >
                    </div>
                </div>
                
                <div class="wf-actions">
                    <button type="reset" class="wf-btn wf-btn-secondary">
                        <i class="fas fa-redo"></i>
                        Làm mới
                    </button>
                    <button type="submit" class="wf-btn wf-btn-primary">
                        <i class="fas fa-save"></i>
                        Lưu thông tin
                    </button>
                </div>
            </form>
        </div>
    </div>
 </div>
    
    <script src="${pageContext.request.contextPath}/instructor/js/wallet-form-script.js"></script>
</body>
</html>
