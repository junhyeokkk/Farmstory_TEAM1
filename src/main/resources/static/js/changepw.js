// 비밀번호 유효성 검사
const newPassword = document.getElementById('newPassword'); // 비밀번호 입력 필드들
const confirmPassword = document.getElementById('confirmPassword'); // 비밀번호 입력 필드들
let isPassOk = false; // 비밀번호 검증 결과를 저장하는 플래그
console.log(confirmPassword)

// 비밀번호 형식 확인 정규식 (8자 이상, 영문, 숫자, 특수문자 포함)
const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

// 비밀번호 확인 필드에 대한 이벤트 리스너 추가
confirmPassword.addEventListener('focusout', () => {
    // 비밀번호와 비밀번호 확인 필드가 일치하는지 확인
    if (newPassword.value === confirmPassword.value) {
        // 비밀번호 형식 확인
        if (!passwordPattern.test(confirmPassword.value)) {
            showResultInvalid(resultPass, '비밀번호 형식에 맞지 않습니다. 영문, 숫자, 특수문자를 포함하여 8자 이상 입력해 주세요.');
            isPassOk = false;
        } else {
            showResultValid(resultPass, '사용 가능한 비밀번호입니다.');
            isPassOk = true;
        }
    } else {
        showResultInvalid(resultPass, '비밀번호가 일치하지 않습니다.');
        isPassOk = false;
    }
});

// 폼 제출 시 검증
document.getElementById('passwordResetForm').onsubmit = function () {
    if (!isPassOk) {
        alert('비밀번호를 다시 확인해 주세요.');
        return false; // 검증 실패 시 폼 제출을 막음
    }
    return true; // 검증 성공 시 폼 제출 허용
}
