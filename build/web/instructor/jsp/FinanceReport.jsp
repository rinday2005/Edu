<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Báo cáo Tài chính - InstructorsHome</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<div class="main-container">

     <!-- ===== HEADER ===== -->
    <jsp:include page="/instructor/common/header.jsp" />

    <!-- ===== SIDEBAR ===== -->
    <jsp:include page="/instructor/common/sidebar.jsp" />
    <!-- ===== MAIN CONTENT ===== -->
    <main class="content-area">
        <section class="section">
            <h2><i class="fas fa-wallet"></i> Báo cáo Tài chính</h2>
        </section>
        
        <c:choose>
            <c:when test="${check == false}">
        <div class="wallet-hero">
            <div class="wallet-card-main">
                <form action="${pageContext.request.contextPath}/instructor/jsp/wallet-form.jsp">
                <button type="submit" class="btn-enroll">CHƯA LIÊN KẾT TK! LIÊN KẾT NGAY</button>
                </form>
            </div>
        </div>    
            </c:when>
            <c:otherwise>
        
        <!-- Redesigned wallet card with improved layout and styling -->
        <div class="wallet-hero">
            <div class="wallet-card-main">
            
                <div class="wallet-header">
                    <div class="wallet-label"><i class="fas fa-piggy-bank"></i> Ví của bạn</div>
                    <div class="wallet-badge">Hoạt động</div>
                </div>
                <div class="wallet-balance-display">
                    <span class="currency-symbol">₫</span>
                    <span class="wallet-balance-amount">
                        <c:out value="${price.balance}" />
                    </span>
                </div>
                <div class="wallet-info-grid">
                    <div class="wallet-info-item">
                        <span class="info-label">Ngân hàng</span>
                            <span class="info-value">
                                <c:out value="${price.bankName}" />
                            </span>
                    </div>
                    <div class="wallet-info-item">
                        <span class="info-label">Số tài khoản</span>
                            <span class="info-value">
                                <c:out value="${price.bankAccount}" />
                            </span>
                    </div>
                    <div class="wallet-info-item">
                        <span class="info-label">Cập nhật</span>
                        <span class="info-value">Hôm nay</span>
                    </div>
                </div>
             </div>
        </div>           


        <!-- Financial statistics with improved visual hierarchy -->
        <section class="section">
            <h3><i class="fas fa-chart-bar"></i> Thống kê Tài chính tháng này</h3>
            <div class="stats-grid-enhanced">
                <div class="stat-box-enhanced income-stat">
                    <div class="stat-icon"><i class="fas fa-arrow-up"></i></div>
                    <div class="stat-content">
                        <h4>Thu nhập</h4>
                        <div class="stat-amount">₫ 15,500,000</div>
                        <p class="stat-change">+12% so với tháng trước</p>
                    </div>
                </div>
                <div class="stat-box-enhanced expense-stat">
                    <div class="stat-icon"><i class="fas fa-arrow-down"></i></div>
                    <div class="stat-content">
                        <h4>Chi phí</h4>
                        <div class="stat-amount">₫ 2,100,000</div>
                        <p class="stat-change">-5% so với tháng trước</p>
                    </div>
                </div>
                <div class="stat-box-enhanced balance-stat">
                    <div class="stat-icon"><i class="fas fa-coins"></i></div>
                    <div class="stat-content">
                        <h4>Tổng lợi nhuận</h4>
                        <div class="stat-amount">₫ 13,400,000</div>
                        <p class="stat-change">Tháng này</p>
                    </div>
                </div>
            </div>
        </section>

        <!-- Transaction history with enhanced table styling -->
        <section class="section">
            <div class="section-header-with-filter">
                <h3><i class="fas fa-history"></i> Lịch sử Giao dịch</h3>
                <div class="filter-actions">
                    <input type="text" class="filter-input" placeholder="Tìm kiếm giao dịch...">
                    <select class="filter-select">
                        <option value="">-- Tất cả --</option>
                        <option value="income">Thu nhập</option>
                        <option value="expense">Chi phí</option>
                        <option value="withdraw">Rút tiền</option>
                    </select>
                </div>
            </div>
            <div class="card">
                <div class="card-body table-responsive">
                    <table class="transaction-table">
                        <thead>
                            <tr>
                                <th><i class="fas fa-calendar"></i> Ngày giao dịch</th>
                                <th><i class="fas fa-info-circle"></i> Mô tả</th>
                                <th><i class="fas fa-money-bill"></i> Số tiền</th>
                                <th><i class="fas fa-tag"></i> Loại</th>
                                <th><i class="fas fa-check-circle"></i> Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="transaction-row">
                                <td>2024-01-15</td>
                                <td>Thanh toán khóa học - React Advanced</td>
                                <td class="transaction-amount credit">+ ₫ 5,000,000</td>
                                <td><span class="badge badge-income">Thu nhập</span></td>
                                <td><span class="transaction-status status-success"><i class="fas fa-check"></i> Thành công</span></td>
                            </tr>
                            <tr class="transaction-row">
                                <td>2024-01-14</td>
                                <td>Phí hệ thống 3%</td>
                                <td class="transaction-amount debit">- ₫ 150,000</td>
                                <td><span class="badge badge-expense">Chi phí</span></td>
                                <td><span class="transaction-status status-success"><i class="fas fa-check"></i> Thành công</span></td>
                            </tr>
                            <tr class="transaction-row">
                                <td>2024-01-12</td>
                                <td>Thanh toán khóa học - Web Design</td>
                                <td class="transaction-amount credit">+ ₫ 3,500,000</td>
                                <td><span class="badge badge-income">Thu nhập</span></td>
                                <td><span class="transaction-status status-success"><i class="fas fa-check"></i> Thành công</span></td>
                            </tr>
                            <tr class="transaction-row">
                                <td>2024-01-10</td>
                                <td>Rút tiền về tài khoản ngân hàng</td>
                                <td class="transaction-amount debit">- ₫ 10,000,000</td>
                                <td><span class="badge badge-withdraw">Rút tiền</span></td>
                                <td><span class="transaction-status status-success"><i class="fas fa-check"></i> Thành công</span></td>
                            </tr>
                            <tr class="transaction-row">
                                <td>2024-01-08</td>
                                <td>Thanh toán khóa học - JavaScript Basics</td>
                                <td class="transaction-amount credit">+ ₫ 2,500,000</td>
                                <td><span class="badge badge-income">Thu nhập</span></td>
                                <td><span class="transaction-status status-success"><i class="fas fa-check"></i> Thành công</span></td>
                            </tr>
                            <tr class="transaction-row">
                                <td>2024-01-05</td>
                                <td>Hoàn tiền - Khôi phục giao dịch</td>
                                <td class="transaction-amount debit">- ₫ 1,500,000</td>
                                <td><span class="badge badge-refund">Hoàn tiền</span></td>
                                <td><span class="transaction-status status-pending"><i class="fas fa-clock"></i> Đang xử lý</span></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>

        <!-- Withdrawal request form with improved layout -->
        <section class="section">
            <h3><i class="fas fa-credit-card"></i> Yêu cầu Rút tiền</h3>
            <div class="card">
                
                
                
<div class="card-body">
    <form action="${pageContext.request.contextPath}/tranferServlet" method="post" class="withdrawal-form-enhanced">
        <div class="form-section">
            <p class="form-note"><i class="fas fa-info-circle"></i> Điền thông tin tài khoản ngân hàng để yêu cầu rút tiền</p>
            
            <div class="form-grid-2">
                <div class="form-group">
                    <label for="withdrawAmount"><i class="fas fa-dollar-sign"></i> Số tiền cần rút (₫) *</label>
                    <input type="number" name="withdrawAmount" id="withdrawAmount" class="form-control" placeholder="1,000,000" min="100000" required>
                    <small class="form-hint">Tối thiểu: 100,000 ₫ | Tối đa: 5,250,000 ₫ </small>
                    <small class="form-hint">Nếu điền quá số tiền có trong ví thì giao dịch sẽ không thành công</small>
                </div>
                <div class="form-group">
                    <label for="bankName"><i class="fas fa-building"></i> Tên ngân hàng *</label>
                    <select name="bankName" id="bankName" class="form-control" required>
                        <option value="">-- Chọn ngân hàng --</option>
                        <option value="Vietcombank">Vietcombank</option>
                        <option value="BIDV">BIDV</option>
                        <option value="Techcombank">Techcombank</option>
                        <option value="ACB">ACB</option>
                        <option value="MBBank">MBBank</option>
                        <option value="VietinBank">VietinBank</option>
                        <option value="Agribank">Agribank</option>
                        <option value="VPBank">VPBank</option>
                        <option value="TPBank">TPBank</option>
                        <option value="Sacombank">Sacombank</option>
                        <option value="HDBank">HDBank</option>
                        <option value="MB Bank">SHB</option>
                        <option value="SHB">VIB</option>
                        <option value="MSB">MSB</option>
                        <option value="OCB">OCB</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="bankAccount"><i class="fas fa-credit-card"></i> Số tài khoản ngân hàng *</label>
                <input type="text" name="bankAccount" id="bankAccount" class="form-control" placeholder="1234567890" required>
                <small class="form-hint">Nhập số tài khoản đầy đủ 9-18 chữ số</small>
            </div>

            <div class="form-group">
                <label for="accountHolder"><i class="fas fa-user"></i> Tên chủ tài khoản *</label>
                <input type="text" name="accountHolder" id="accountHolder" class="form-control" placeholder="Nguyễn Văn A" required>
            </div>

            <div class="form-note-warning">
                <i class="fas fa-warning"></i>
                <span>Vui lòng kiểm tra kỹ thông tin trước khi gửi yêu cầu. Không thể thay đổi sau khi xác nhận.</span>
            </div>
        </div>

        <div class="form-actions">
            <button type="button" id="cancelWithdrawBtn" class="btn btn-secondary">
                <i class="fas fa-times"></i> Hủy
            </button>
            <button type="submit" class="btn btn-primary"><i class="fas fa-paper-plane"></i> Gửi yêu cầu rút tiền</button>
        </div>
    </form>
</div>

                
                
            </div>
        </section>
        
        </c:otherwise>
        </c:choose>
    </main>
</div>

<script src="${pageContext.request.contextPath}/instructor/js/instructorsHome.js"></script>
</body>
</html>
