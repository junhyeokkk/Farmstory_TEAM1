
window.onload = function () {

    const btnComplete = document.getElementById('btnComplete');
    const commentForm = document.commentForm;
    const commentList = document.getElementsByClassName('commentList')[0];

    btnComplete.onclick = async function (e) {
        e.preventDefault();

        const jsonData = {
            "writer": commentForm.writer.value,
            "parent": commentForm.parent.value,
            "content": commentForm.content.value,
            "user" : {
                "uid": commentForm.writer.value,
                "nick": commentForm.nick.value
            }
        }
        const data = await fetchPost('/comment/write', jsonData)

        if(data.no){
            alert('댓글이 등록되었습니다.')
            console.log(data.user.uid);
            console.log(data.no);
            console.log(commentForm.commentSize.value);

            if(commentForm.commentSize.value <= 0){
                location.href = "/article/" + commentForm.cateGroup.value + "/" + commentForm.cateName.value + "/" + commentForm.parent.value + "?content=view&pg=" + commentForm.pg.value;
            }else {
                // 동적 태그 생성
                const commentArticle = `<article>
                                                <span class="nick">${data.user.nick}</span>
                                                <span class="date">${data.date}</span>
                                                <p class="content">${data.content}</p>
                                                <div>
                                                    <a href="/comment/delete?no=${data.no}&pg=${commentForm.pg.value}&writer=${data.user.uid}}" class="remove">삭제</a>
                                                    <a href="/comment/modify?no=${data.no}&pg=${commentForm.pg.value}&writer=${data.user.uid}}" class="modify">수정</a>
                                                </div>
                                       </article>`;
                commentList.insertAdjacentHTML('beforeend', commentArticle);
                commentForm.reset();

            }
        }else{
            alert('댓글 등록 실패');
        }


    }



}