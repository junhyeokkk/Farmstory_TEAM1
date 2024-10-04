//유효성 검사에 사용할 상태변수
let isUidOk   = false;
let isPassOk  = false;
let isNameOk  = false;
let isNickOk  = false;
let isEmailOk = false;
let isHpOk    = false;

// 유효성 검사에 사용할 정규표현식
const reUid   = /^[a-z]+[a-z0-9]{4,19}$/g;
const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
const reName  = /^[가-힣]{2,10}$/
const reNick  = /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

window.onload = function (){


    // 이메일 유효성 검사
    const inputEmail = document.getElementsByClassName('inputEmail')[0];
    const resultEmail = document.getElementById('resultEmail');
    const btnCheckEmail = document.getElementById('btnCheckEmail');
    const auth = document.getElementsByClassName('auth')[0];

    btnCheckEmail.onclick = function(){
        console.log(this)
        const type      = this.dataset.type;
        const value     = inputEmail.value;

        // 유효성 검사
        if(!value.match(reEmail)){
            showResultInvalid(resultEmail, '이메일 형식이 맞지 않습니다.');
            isEmailOk = false;
            return;
        }

        // 이메일 인증코드 발급 및 중복체크
        setTimeout(async () => {
            const data = await fetchGet(`/user/${type}/${value}`);
            console.log('data : ' + data.result);

            if(data.result > 0){
                showResultInvalid(resultEmail, '이미 사용중인 이메일 입니다.');
                isEmailOk = false;
            }else{
                showResultValid(resultEmail, '인증코드가 발송 되었습니다.');
                // 인증코드 입력 필드 활성화
                auth.style.display = 'block';

                isEmailOk = false;
            }
        }, 1000);
    }

    // 이메일 인증코드 확인
    const inputEmailCode = document.getElementsByClassName('inputEmailCode')[0];
    const btnCheckEmailCode = document.getElementById('btnCheckEmailCode');

    btnCheckEmailCode.onclick = async function (){

        const jsonData = {"code": inputEmailCode.value};

        const data = await fetchPost(`/email`, jsonData);

        if(!data.result){
            showResultInvalid(resultEmail, '인증코드가 일치하지 않습니다.');
            isEmailOk = false;
        }else{
            showResultValid(resultEmail, '이메일이 인증되었습니다.');
            isEmailOk = true;
        }
    }





    // 최종 유효성 검사 확인
    document.registerForm.onsubmit =  function (){



        if(!isNickOk){
            alertModal('별명이 유효하지 않습니다.');
            return false;
        }

        if(!isEmailOk){
            alertModal('이메일이 유효하지 않습니다.');
            return false;
        }

        if(!isHpOk){
            alertModal('휴대폰이 유효하지 않습니다.');
            return false;
        }

        // 폼 전송
        return true;
    }
}