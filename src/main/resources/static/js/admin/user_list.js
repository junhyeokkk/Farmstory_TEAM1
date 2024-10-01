// 회원 상세 정보 모달처리
// 모달을 열 때 UID 값을 설정하는 기능
document.querySelectorAll('.openModalBtn').forEach(button => {
    button.addEventListener('click', function() {
        // 클릭한 버튼의 부모 tr 요소를 찾음
        const userRow = this.closest('tr');

        // 2번째 td 요소에서 UID 값을 가져옴
        const uid = userRow.querySelector('.user-uid').textContent.trim();

        console.log('uidddddddddddddddddd' + uid);
        // UID를 모달에 표시
        document.getElementById('modalUserId').value = uid;

        fetch('/admin/findUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // JSON 데이터 형식으로 설정
            },
            body: JSON.stringify({ uid: uid })
        })
            .then(resp => resp.json())
            .then(data => {
                console.log(data);
                if (data) { // data가 유효하면 성공으로 간주
                    alert('성공');
                    // 추가적으로 모달에 사용자 정보를 표시할 수 있습니다.
                    document.getElementById('modalUserName').value = data.name;
                    document.getElementById('modalUserRole').value = data.role;
                    document.getElementById('modalUserNick').value  = data.nick;
                    document.getElementById('modalUserHp').value  = data.hp;
                    document.getElementById('modalUserEmail').value  = data.email;
                    document.getElementById('modalUserZip').value  = data.zip;
                    document.getElementById('modalUserAddr1').value  = data.addr1;
                    document.getElementById('modalUserAddr2').value  = data.addr2;
                    document.getElementById('modalUserRegDate').value = data.regDate.split('T')[0];
                } else {
                    alert('실패');
                }
            })
            .catch(err => {
                console.error(err); // 에러를 콘솔에 출력
            });
    });
});