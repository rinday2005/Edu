// Khởi tạo khi trang được tải
document.addEventListener('DOMContentLoaded', function() {
    initializePaymentPage();
});

function initializePaymentPage() {
    // Thêm sự kiện cho nút về trang chủ
    const homeButton = document.querySelector('.home-button');
    if (homeButton) {
        homeButton.addEventListener('click', goToHomePage);
    }
    
    // Thêm sự kiện cho các item trong sidebar
    const sidebarItems = document.querySelectorAll('.sidebar-item');
    sidebarItems.forEach(item => {
        item.addEventListener('click', handleSidebarClick);
    });
    
    // Hiển thị thông báo khi trang thanh toán được tải
    showPaymentInfo();
}

function goToHomePage() {
    // Hiển thị thông báo xác nhận
    if (confirm('Bạn có chắc chắn muốn quay về trang chủ? Thông tin thanh toán chưa hoàn tất sẽ bị mất.')) {
        // Trong thực tế, bạn sẽ chuyển hướng đến trang chủ
        // window.location.href = '/learner/home';
        alert('Đang chuyển hướng về trang chủ...');
        console.log('Chuyển hướng về trang chủ');
    }
}

function handleSidebarClick(event) {
    const clickedItem = event.currentTarget;
    const sidebarItems = document.querySelectorAll('.sidebar-item');
    
    // Xóa class active khỏi tất cả các item
    sidebarItems.forEach(item => {
        item.classList.remove('active');
    });
    
    // Thêm class active cho item được click
    clickedItem.classList.add('active');
    
    // Hiển thị thông báo cho mục được chọn
    const itemText = clickedItem.textContent.trim();
    console.log(`Đã chọn: ${itemText}`);
}

function showPaymentInfo() {
    // Tính tổng số tiền từ các khóa học
    const coursePrices = document.querySelectorAll('.course-price');
    let total = 0;
    
    coursePrices.forEach(priceElement => {
        const priceText = priceElement.textContent;
        const price = parseFloat(priceText.replace(/[^\d]/g, '')) || 0;
        total += price;
    });
    
    // Format số tiền theo định dạng VND
    const formattedTotal = formatCurrency(total);
    
    // Cập nhật tổng số tiền hiển thị
    const totalAmountElement = document.querySelector('.total-amount');
    if (totalAmountElement) {
        totalAmountElement.textContent = formattedTotal;
    }
    
    console.log(`Tổng số tiền cần thanh toán: ${formattedTotal}`);
}

function formatCurrency(amount) {
    // Format số tiền theo định dạng VND
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount);
}

// Hàm để thêm khóa học mới vào danh sách (có thể sử dụng sau này)
function addCourseToPayment(courseName, coursePrice) {
    const courseList = document.querySelector('.course-list');
    
    if (courseList) {
        const newCourseItem = document.createElement('li');
        newCourseItem.className = 'course-item';
        
        newCourseItem.innerHTML = `
            <span class="course-name">${courseName}</span>
            <span class="course-price">${formatCurrency(coursePrice)}</span>
        `;
        
        courseList.appendChild(newCourseItem);
        
        // Cập nhật lại tổng số tiền
        showPaymentInfo();
    }
}

// Hàm để tạo QR code thực (cần tích hợp thư viện QR code)
function generateQRCode(content) {
    // Trong thực tế, bạn có thể sử dụng thư viện như qrcode.js
    // Đây là placeholder cho chức năng tạo QR code
    const qrContainer = document.querySelector('.qr-code');
    if (qrContainer && content) {
        qrContainer.innerHTML = `
            <div class="qr-placeholder">
                <i>QR Code</i>
                <span>Mã QR cho: ${content}</span>
            </div>
        `;
    }
}

// Xuất các hàm để sử dụng ở nơi khác nếu cần
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        initializePaymentPage,
        goToHomePage,
        addCourseToPayment,
        generateQRCode,
        formatCurrency
    };
}