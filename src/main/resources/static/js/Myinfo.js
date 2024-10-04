// 역할 변경 메서드
function modifyRole(uid, role) {
    fetch('/admin/modifyRole', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ uid: uid, role: role })
    })
        .then(resp => resp.json())
        .then(data => {
            console.log(data);
            if (data) {
                alert('탈퇴가 완료되었습니다');
                window.location.href = '/user/logout';
            } else {
                alert('실패');
            }
        })
        .catch(err => {
            console.error(err);
        });
}


document.querySelector('.btn_quit').addEventListener('click', function() {
    const uid = document.getElementById('modalUserId').value;
    modifyRole(uid, 'LEAVE'); // ADMIN 역할로 변경
});