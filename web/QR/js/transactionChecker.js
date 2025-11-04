// Biến để kiểm soát việc polling
let checkInterval;
let isChecking = false;
let currentKeyword = '';
let timeCounter = 0;
let ajaxCounter = 0;
let timeInterval;

// Hàm bắt đầu kiểm tra giao dịch
function startTransactionCheck() {
    if (isChecking) {
        console.log('Đang kiểm tra giao dịch...');
        return;
    }
    
    // Sử dụng keyword từ server-side (thống nhất)
    currentKeyword = noiDung;
    
    if (!currentKeyword) {
        console.error('❌ Không có mã giao dịch');
        showError('Lỗi: Không có mã giao dịch');
        return;
    }
    
    isChecking = true;
    timeCounter = 0;
    ajaxCounter = 0;
    console.log('🚀 Bắt đầu kiểm tra giao dịch với mã:', currentKeyword);
    
    // Bắt đầu đếm thời gian
    startCounter();
    
    // Hiển thị thông báo đang kiểm tra
    showCheckingMessage();
    
    // Thực hiện polling mỗi 5 giây
    checkInterval = setInterval(() => {
        checkTransaction();
    }, 5000);
    
    // Kiểm tra ngay lần đầu
    checkTransaction();
}

// Hàm bắt đầu đếm thời gian
function startCounter() {
    if (timeInterval) {
        clearInterval(timeInterval);
    }
    
    timeInterval = setInterval(() => {
        timeCounter++;
        console.log('⏰ Thời gian: ' + timeCounter + ' giây | 🚀 Số lần Ajax: ' + ajaxCounter);
    }, 1000);
}

// Hàm dừng kiểm tra
function stopTransactionCheck() {
    if (timeInterval) {
        clearInterval(timeInterval);
        timeInterval = null;
    }
    
    if (checkInterval) {
        clearInterval(checkInterval);
        checkInterval = null;
    }
    
    isChecking = false;
    console.log('🛑 Đã dừng kiểm tra giao dịch');
    console.log('📊 Tổng kết: ' + timeCounter + ' giây | ' + ajaxCounter + ' lần gọi Ajax');
}

// Hàm gọi Ajax đến Servlet
function checkTransaction() {
    ajaxCounter++;
    console.log('🔔 Gọi Ajax lần thứ: ' + ajaxCounter + ' (sau ' + timeCounter + ' giây)');
    
    const xhr = new XMLHttpRequest();
    const url = `${window.location.origin}${window.location.pathname.replace(/[^/]*$/, '')}checkTransaction`;
    
    xhr.open('GET', url, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    const response = JSON.parse(xhr.responseText);
                    console.log('✅ Ajax lần ' + ajaxCounter + ' thành công: ', response);
                    handleCheckResponse(response);
                } catch (e) {
                    console.error('❌ Lỗi phân tích JSON lần ' + ajaxCounter + ':', e);
                    showError('Lỗi xử lý dữ liệu');
                }
            } else {
                console.error('❌ Lỗi HTTP lần ' + ajaxCounter + ':', xhr.status);
                showError('Lỗi kết nối máy chủ');
            }
        }
    };
    
    xhr.onerror = function() {
        console.error('❌ Lỗi kết nối lần ' + ajaxCounter);
        showError('Lỗi kết nối mạng');
    };
    
    xhr.send();
}

// Hàm xử lý kết quả từ server
function handleCheckResponse(response) {
    const successElement = document.getElementById('success');
    
    switch (response.status) {
        case 'success':
            console.log('🎉 Tìm thấy giao dịch thành công ở lần Ajax thứ ' + ajaxCounter);
            stopTransactionCheck();
            showSuccess('✅ Thanh toán thành công! Giao dịch đã được xác nhận.');
            affterQRPay();
            break;
            
        case 'pending':
            console.log('⏳ Chưa tìm thấy giao dịch lần ' + ajaxCounter + ', tiếp tục kiểm tra...');
            updateCheckingMessage('Đang kiểm tra giao dịch...<br>Mã giao dịch: <strong>' + currentKeyword + '</strong><br>Số tiền: <strong>' + price + ' VND</strong><br>Đã kiểm tra: ' + ajaxCounter + ' lần');
            break;
            
        case 'error':
            console.error('💥 Lỗi từ server lần ' + ajaxCounter + ':', response.message);
            showError('Lỗi: ' + response.message);
            stopTransactionCheck();
            break;
            
        default:
            console.error('❓ Phản hồi không xác định lần ' + ajaxCounter + ':', response);
            showError('Lỗi không xác định');
            stopTransactionCheck();
    }
}

// Hàm hiển thị thông báo thành công
function showSuccess(message) {
    const successElement = document.getElementById('success');
    successElement.innerHTML = message;
    successElement.style.color = 'green';
    successElement.style.fontWeight = 'bold';
    successElement.style.display = 'block';
    

}

// Hàm hiển thị thông báo lỗi
function showError(message) {
    const successElement = document.getElementById('success');
    successElement.innerHTML = message;
    successElement.style.color = 'red';
    successElement.style.fontWeight = 'bold';
    successElement.style.display = 'block';
}

// Hàm hiển thị thông báo đang kiểm tra
function showCheckingMessage() {
    const successElement = document.getElementById('success');
    successElement.innerHTML = 'Đang kiểm tra giao dịch...<br>Vui lòng chờ trong giây lát';
    successElement.style.color = 'blue';
    successElement.style.fontWeight = 'normal';
    successElement.style.display = 'block';
}

// Hàm cập nhật thông báo kiểm tra
function updateCheckingMessage(message) {
    const successElement = document.getElementById('success');
    successElement.innerHTML = message;
}

// Khởi tạo khi trang load xong
document.addEventListener('DOMContentLoaded', function() {
    const successElement = document.getElementById('success');
    successElement.style.display = 'none';
    
    // Kiểm tra nếu đã thanh toán thành công từ trước
    if (initialCheck === true) {
        showSuccess('✅ Thanh toán thành công! Giao dịch đã được xác nhận.');
        affterQRPay();
    } else {
        console.log('🚀 Bắt đầu kiểm tra giao dịch với mã:', currentKeyword);
        startTransactionCheck();
    }
});

// Dọn dẹp khi trang bị đóng
window.addEventListener('beforeunload', function() {
    stopTransactionCheck();
});
function affterQRPay(){
    const xhr = new XMLHttpRequest();
    const url = `${window.location.origin}${window.location.pathname.replace(/[^/]*$/, '')}AfterPayServlet`;
    
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send();
    

}

