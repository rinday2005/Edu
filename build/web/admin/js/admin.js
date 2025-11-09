(function () {
    'use strict';
    
    window.EDUAdmin = {
        init: function () {
          
            var menu = document.getElementById('avatarMenu');
            if (menu) {
                var img = menu.querySelector('.avatar');
                if (img) {
                    img.addEventListener('click', function (e) {
                        e.stopPropagation();
                        menu.classList.toggle('open');
                    });
                }
                document.addEventListener('click', function () {
                    menu.classList.remove('open');
                });
            }
            
            // FE-only safe navigation for cross-role links to avoid server 500
            var quickLinks = document.querySelectorAll('.quick-link');
            if (quickLinks && quickLinks.length) {
                var base = (function(){
                    var path = location.pathname;
                    var idx = path.indexOf('/admin/');
                    return idx >= 0 ? path.substring(0, idx) : '';
                })();
                Array.prototype.forEach.call(quickLinks, function(a){
                    var label = (a.textContent || a.innerText || '').toLowerCase();
                    if (label.indexOf('back to instructor') !== -1 || label.indexOf('back to learner') !== -1) {
                        a.addEventListener('click', function(e){
                            e.preventDefault();
                            // Redirect to a safe admin landing instead of hitting server pages that may 500
                            window.location.href = base + '/admin/index.jsp';
                        });
                    }
                });
            }
            
            // Load avatar from localStorage if available (FE persistence)
            try {
                var savedAvatarUrl = localStorage.getItem('adminAvatarUrl');
                if (savedAvatarUrl) {
                    var headerAvatar = document.querySelector('.admin-header .avatar');
                    if (headerAvatar) {
                        var separator = savedAvatarUrl.indexOf('?') >= 0 ? '&' : '?';
                        headerAvatar.src = savedAvatarUrl + separator + 't=' + Date.now();
                    }
                }
            } catch(e) {}
        }
    };
    document.addEventListener('DOMContentLoaded', function () {
        window.EDUAdmin.init();
    });
})();

