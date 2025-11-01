// instructorsHome.js - Simple Theme

document.addEventListener('DOMContentLoaded', function() {
    console.log("Instructor Home scripts loaded.");
    
    // 1. Xử lý Active state cho Sidebar
    const navItems = document.querySelectorAll('.sidebar .nav-item');
    const currentPath = window.location.pathname;

    navItems.forEach(item => {
        const itemHref = item.getAttribute('href');
        
        // Đánh dấu active nếu đường dẫn khớp
        if (currentPath.endsWith(itemHref) || currentPath.includes(itemHref) || 
            currentPath.includes(itemHref.split('/').pop())) {
            navItems.forEach(i => i.classList.remove('active'));
            item.classList.add('active');
        }
    });

    // 2. Tìm kiếm đơn giản
    const searchButton = document.querySelector('.search-bar button');
    const searchInput = document.querySelector('.search-bar input');

    if (searchButton && searchInput) {
        searchButton.addEventListener('click', function() {
            const query = searchInput.value.trim();
            if (query) {
                console.log(`Searching for: ${query}`);
                // Thêm logic tìm kiếm thực tế ở đây
            }
        });

        // Tìm kiếm bằng Enter
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                searchButton.click();
            }
        });
    }

    // 3. Hiệu ứng đơn giản cho các card
    const statCards = document.querySelectorAll('.stat-card');
    
    statCards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-2px)';
            this.style.boxShadow = '0 4px 8px rgba(0,0,0,0.15)';
        });

        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
            this.style.boxShadow = '0 2px 4px rgba(0,0,0,0.1)';
        });
    });

    // 4. Hiệu ứng đơn giản cho các button
    const buttons = document.querySelectorAll('.btn');
    
    buttons.forEach(button => {
        button.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-1px)';
        });

        button.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
        });
    });

    // 5. Hiệu ứng đơn giản cho table rows
    const tableRows = document.querySelectorAll('.table tr');
    
    tableRows.forEach(row => {
        row.addEventListener('mouseenter', function() {
            this.style.backgroundColor = 'var(--bg-light)';
        });

        row.addEventListener('mouseleave', function() {
            this.style.backgroundColor = '';
        });
    });

    // 6. Form validation đơn giản
    const forms = document.querySelectorAll('form');
    
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const requiredFields = form.querySelectorAll('[required]');
            let isValid = true;
            
            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    field.style.borderColor = '#dc3545';
                    isValid = false;
                } else {
                    field.style.borderColor = '';
                }
            });
            
            if (!isValid) {
                e.preventDefault();
                alert('Vui lòng điền đầy đủ các trường bắt buộc');
            }
        });
    });

    // 7. Auto-hide alerts
    const alerts = document.querySelectorAll('.alert');
    
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => {
                alert.remove();
            }, 300);
        }, 5000);
    });

    // 8. Simple loading effect
    const loadingElements = document.querySelectorAll('.loading');
    
    loadingElements.forEach(element => {
        element.style.opacity = '0.5';
        element.style.pointerEvents = 'none';
    });

    // 9. Simple tooltip
    const tooltipElements = document.querySelectorAll('[data-tooltip]');
    
    tooltipElements.forEach(element => {
        element.addEventListener('mouseenter', function() {
            const tooltip = document.createElement('div');
            tooltip.className = 'tooltip';
            tooltip.textContent = this.getAttribute('data-tooltip');
            tooltip.style.cssText = `
                position: absolute;
                background: #333;
                color: white;
                padding: 5px 10px;
                border-radius: 4px;
                font-size: 12px;
                z-index: 1000;
                pointer-events: none;
            `;
            document.body.appendChild(tooltip);
            
            const rect = this.getBoundingClientRect();
            tooltip.style.left = rect.left + 'px';
            tooltip.style.top = (rect.top - 30) + 'px';
        });
        
        element.addEventListener('mouseleave', function() {
            const tooltip = document.querySelector('.tooltip');
            if (tooltip) {
                tooltip.remove();
            }
        });
    });

    // 10. Simple confirmation dialogs
    const deleteButtons = document.querySelectorAll('.btn-danger');
    
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            if (!confirm('Bạn có chắc chắn muốn xóa?')) {
                e.preventDefault();
            }
        });
    });
});

// instructorsHome.js - Thêm code vẽ biểu đồ

document.addEventListener('DOMContentLoaded', function() {

    // 1. Dữ liệu giả định cho Biểu đồ Doanh thu (Bar Chart)
    const revenueData = {
        labels: ['Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10'],
        datasets: [{
            label: 'Doanh thu (USD)',
            data: [4500, 3200, 5800, 4100, 6900, 7500],
            backgroundColor: 'rgba(0, 123, 255, 0.7)', // Màu xanh dương
            borderColor: 'rgba(0, 123, 255, 1)',
            borderWidth: 1,
            borderRadius: 5,
        }]
    };

    // Cấu hình Biểu đồ Doanh thu
    const revenueConfig = {
        type: 'bar',
        data: revenueData,
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Doanh thu (USD)'
                    }
                }
            },
            plugins: {
                legend: { display: false },
                title: { display: false }
            }
        },
    };

    // Vẽ Biểu đồ Doanh thu
    const revenueCtx = document.getElementById('revenueChart');
    if (revenueCtx) {
        new Chart(revenueCtx, revenueConfig);
    }
    
    // -------------------------------------------------------------

    // 2. Dữ liệu giả định cho Biểu đồ Đăng ký (Line Chart)
    const enrollmentData = {
        labels: ['Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10'],
        datasets: [{
            label: 'Số lượt đăng ký',
            data: [150, 120, 210, 180, 250, 290],
            backgroundColor: 'rgba(40, 167, 69, 0.7)', // Màu xanh lá
            borderColor: 'rgba(40, 167, 69, 1)',
            borderWidth: 2,
            tension: 0.3, // Đường cong nhẹ
            fill: true, // Tô màu bên dưới đường
        }]
    };

    // Cấu hình Biểu đồ Đăng ký
    const enrollmentConfig = {
        type: 'line', 
        data: enrollmentData,
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Số lượt đăng ký'
                    }
                }
            },
            plugins: {
                legend: { display: false },
                title: { display: false }
            }
        },
    };

    // Vẽ Biểu đồ Đăng ký
    const enrollmentCtx = document.getElementById('enrollmentChart');
    if (enrollmentCtx) {
        new Chart(enrollmentCtx, enrollmentConfig);
    }

});

// instructorsHome.js - Logic Dropdown và Biểu đồ

document.addEventListener('DOMContentLoaded', function() {
    
    // ===================================
    // 1. LOGIC ẨN/HIỆN DROPDOWN MENU
    // ===================================
    const profileToggle = document.getElementById('userProfileToggle');
    const profileDropdown = document.getElementById('profileDropdown');

    if (profileToggle && profileDropdown) {
        // Chức năng bật/tắt menu khi click vào Profile
        profileToggle.addEventListener('click', function(event) {
            profileDropdown.classList.toggle('show');
            event.stopPropagation(); // Ngăn sự kiện click lan truyền lên document
        });

        // Chức năng đóng menu khi click ra ngoài
        document.addEventListener('click', function(event) {
            const isClickInside = profileToggle.contains(event.target) || profileDropdown.contains(event.target);
            
            if (!isClickInside && profileDropdown.classList.contains('show')) {
                profileDropdown.classList.remove('show');
            }
        });
    }


    // ===================================
    // 2. LOGIC VẼ BIỂU ĐỒ (CHART.JS)
    // ===================================

    // 2.1. Biểu đồ Doanh thu (Bar Chart)
    const revenueData = {
        labels: ['Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10'],
        datasets: [{
            label: 'Doanh thu (USD)',
            data: [4500, 3200, 5800, 4100, 6900, 7500],
            backgroundColor: 'rgba(0, 123, 255, 0.7)', 
            borderColor: 'rgba(0, 123, 255, 1)',
            borderWidth: 1,
            borderRadius: 5,
        }]
    };

    const revenueConfig = {
        type: 'bar',
        data: revenueData,
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Doanh thu (USD)'
                    }
                }
            },
            plugins: {
                legend: { display: false },
                title: { display: false }
            }
        },
    };

    const revenueCtx = document.getElementById('revenueChart');
    if (revenueCtx) {
        new Chart(revenueCtx, revenueConfig);
    }
    
    // 2.2. Biểu đồ Đăng ký (Line Chart)
    const enrollmentData = {
        labels: ['Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10'],
        datasets: [{
            label: 'Số lượt đăng ký',
            data: [150, 120, 210, 180, 250, 290],
            backgroundColor: 'rgba(40, 167, 69, 0.7)', 
            borderColor: 'rgba(40, 167, 69, 1)',
            borderWidth: 2,
            tension: 0.3, 
            fill: true, 
        }]
    };

    const enrollmentConfig = {
        type: 'line', 
        data: enrollmentData,
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Số lượt đăng ký'
                    }
                }
            },
            plugins: {
                legend: { display: false },
                title: { display: false }
            }
        },
    };

    const enrollmentCtx = document.getElementById('enrollmentChart');
    if (enrollmentCtx) {
        new Chart(enrollmentCtx, enrollmentConfig);
    }

});

