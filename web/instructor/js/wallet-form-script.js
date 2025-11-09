// Client-side validation for Wallet Form

function validateForm() {
    const bankName = document.getElementById('bankName').value.trim();
    const bankAccount = document.getElementById('bankAccount').value.trim();
    
    // Clear previous errors
    document.getElementById('bankNameError').textContent = '';
    document.getElementById('bankAccountError').textContent = '';
    
    let isValid = true;
    
    // Validate bank name
    if (bankName === '') {
        showError('bankName', 'bankNameError', 'Vui lòng chọn ngân hàng');
        isValid = false;
    }
    
    // Validate bank account (9-18 digits)
    if (bankAccount === '') {
        showError('bankAccount', 'bankAccountError', 'Vui lòng nhập số tài khoản');
        isValid = false;
    } else if (!/^[0-9]{9,18}$/.test(bankAccount)) {
        showError('bankAccount', 'bankAccountError', 'Số tài khoản phải từ 9-18 chữ số');
        isValid = false;
    }
    
    return isValid;
}

function showError(inputId, errorId, message) {
    document.getElementById(errorId).textContent = message;
    document.getElementById(inputId).classList.add('wf-invalid');
    document.getElementById(inputId).classList.remove('wf-valid');
}

function clearError(inputId, errorId) {
    document.getElementById(errorId).textContent = '';
    document.getElementById(inputId).classList.remove('wf-invalid');
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    const bankAccountInput = document.getElementById('bankAccount');
    const bankNameSelect = document.getElementById('bankName');
    const updateDateInput = document.getElementById('updateDate');
    
    // Set current date in Vietnamese
    const today = new Date();
    const dateString = today.toLocaleDateString('vi-VN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
    updateDateInput.value = 'Hôm nay - ' + dateString;
    
    // Format bank account - only numbers
    bankAccountInput.addEventListener('input', function(e) {
        this.value = this.value.replace(/[^0-9]/g, '');
        clearError('bankAccount', 'bankAccountError');
    });
    
    // Clear error on bank name change
    bankNameSelect.addEventListener('change', function() {
        clearError('bankName', 'bankNameError');
    });
    
    // Real-time validation on blur
    bankAccountInput.addEventListener('blur', function() {
        const value = this.value.trim();
        if (value !== '' && !/^[0-9]{9,18}$/.test(value)) {
            showError('bankAccount', 'bankAccountError', 'Số tài khoản phải từ 9-18 chữ số');
        }
    });
    
    bankNameSelect.addEventListener('blur', function() {
        if (this.value === '') {
            showError('bankName', 'bankNameError', 'Vui lòng chọn ngân hàng');
        }
    });
});
