(function(){
    'use strict';
    
    var usersKey = 'feBannedUsers';
    var postsKey = 'feBannedPosts';

    function load(key){
        try { return JSON.parse(localStorage.getItem(key) || '[]'); } catch(e){ return []; }
    }
    function save(key, data){ localStorage.setItem(key, JSON.stringify(data || [])); }

    function formatTime(ms){ try{ var d=new Date(ms); return d.toLocaleString(); }catch(e){ return ''; } }

    function renderUsers(){
        var list = load(usersKey);
        var tbody = document.getElementById('feBannedUsersTbody');
        if (!tbody) return;
        if (!list.length){ tbody.innerHTML = '<tr><td colspan="7" style="text-align:center; color:#888;">Chưa có dữ liệu</td></tr>'; return; }
        tbody.innerHTML = list.map(function(u, idx){
            return '<tr>'+
                '<td>'+ (u.id||'') +'</td>'+
                '<td>'+ (u.name||'') +'</td>'+
                '<td>'+ (u.email||'') +'</td>'+
                '<td>'+ (u.reason||'') +'</td>'+
                '<td>'+ (u.duration||'') +'</td>'+
                '<td>'+ formatTime(u.bannedAt) +'</td>'+
                '<td><button type="button" class="btn ghost" data-id="'+(u.id||'')+'">Gỡ ban</button></td>'+
            '</tr>';
        }).join('');
        // Attach handlers after render
        Array.prototype.forEach.call(tbody.querySelectorAll('button[data-id]'), function(btn){
            btn.addEventListener('click', function(){
                var id = btn.getAttribute('data-id');
                var list = load(usersKey).filter(function(x){ return String(x.id) !== String(id); });
                save(usersKey, list);
                renderUsers();
            });
        });
    }

    function renderPosts(){
        var list = load(postsKey);
        var tbody = document.getElementById('feBannedPostsTbody');
        if (!tbody) return;
        if (!list.length){ tbody.innerHTML = '<tr><td colspan="5" style="text-align:center; color:#888;">Chưa có dữ liệu</td></tr>'; return; }
        tbody.innerHTML = list.map(function(p){
            return '<tr>'+
                '<td>'+ (p.post||'') +'</td>'+
                '<td>'+ (p.reason||'') +'</td>'+
                '<td>'+ (p.duration||'') +'</td>'+
                '<td>'+ formatTime(p.bannedAt) +'</td>'+
                '<td><button type="button" class="btn ghost" data-post="'+(p.post||'')+'">Gỡ ban</button></td>'+
            '</tr>';
        }).join('');
        Array.prototype.forEach.call(tbody.querySelectorAll('button[data-post]'), function(btn){
            btn.addEventListener('click', function(){
                var id = btn.getAttribute('data-post');
                var list = load(postsKey).filter(function(x){ return String(x.post) !== String(id); });
                save(postsKey, list);
                renderPosts();
            });
        });
    }

    var clearUserBansBtn = document.getElementById('clearUserBans');
    if (clearUserBansBtn) clearUserBansBtn.addEventListener('click', function(){ save(usersKey, []); renderUsers(); });

    var pbAdd = document.getElementById('pbAdd');
    var pbClear = document.getElementById('pbClear');
    if (pbAdd) pbAdd.addEventListener('click', function(){
        var post = (document.getElementById('pbId')||{}).value || '';
        if (!post) { alert('Nhập Post ID/URL'); return; }
        var duration = (document.getElementById('pbDuration')||{}).value || '7d';
        var reason = (document.getElementById('pbReason')||{}).value || '';
        var list = load(postsKey);
        var idx = list.findIndex(function(x){ return String(x.post) === String(post); });
        var item = { post: post, reason: reason, duration: duration, bannedAt: Date.now() };
        if (idx >= 0) list[idx] = item; else list.push(item);
        save(postsKey, list);
        renderPosts();
    });
    if (pbClear) pbClear.addEventListener('click', function(){ save(postsKey, []); renderPosts(); });

    // Initialize on DOM ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', function(){
            renderUsers();
            renderPosts();
        });
    } else {
        renderUsers();
        renderPosts();
    }
})();





