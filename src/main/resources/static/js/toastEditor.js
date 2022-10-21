'ues strict';

let contents = document.getElementById('contents');


toastui.Editor.setLanguage(['ko', 'ko-KR'], {
    Markdown: '마크다운',
    WYSIWYG: '일반',
    Write: '편집하기',
    Preview: '미리보기',
    Headings: '제목크기',
    Paragraph: '본문',
    Bold: '굵게',
    Italic: '기울임꼴',
    Strike: '취소선',
    Code: '인라인 코드',
    Line: '문단나눔',
    Blockquote: '인용구',
    'Unordered list': '글머리 기호',
    'Ordered list': '번호 매기기',
    Task: '체크박스',
    Indent: '들여쓰기',
    Outdent: '내어쓰기',
    'Insert link': '링크 삽입',
    'Insert CodeBlock': '코드블럭 삽입',
    'Insert table': '표 삽입',
    'Insert image': '이미지 삽입',
    Heading: '제목',
    'Image URL': '이미지 주소',
    'Select image file': '이미지 파일을 선택하세요.',
    'Choose a file': '파일 선택',
    'No file': '선택된 파일 없음',
    Description: '설명',
    OK: '확인',
    More: '더 보기',
    Cancel: '취소',
    File: '파일',
    URL: '주소',
    'Link text': '링크 텍스트',
    'Add row to up': '위에 행 추가',
    'Add row to down': '아래에 행 추가',
    'Add column to left': '왼쪽에 열 추가',
    'Add column to right': '오른쪽에 열 추가',
    'Remove row': '행 삭제',
    'Remove column': '열 삭제',
    'Align column to left': '열 왼쪽 정렬',
    'Align column to center': '열 가운데 정렬',
    'Align column to right': '열 오른쪽 정렬',
    'Remove table': '표 삭제',
    'Would you like to paste as table?': '표형태로 붙여 넣겠습니까?',
    'Text color': '글자 색상',
    'Auto scroll enabled': '자동 스크롤 켜짐',
    'Auto scroll disabled': '자동 스크롤 꺼짐',
    'Choose language': '언어 선택',
});


const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    previewStyle: 'vertical',
    language: 'ko',
    height: '800px',
    initialEditType: 'markdown',
    toolbarItems: [
        ['heading', 'bold', 'italic', 'strike'],
        ['hr'],
        ['ul', 'ol'],
        ['code'],
        ['table', 'image', 'link']
    ],
    initialValue: '본문을 입력해주세요.',
    hooks: {
        addImageBlobHook: async (blob, callback) => {
            let imageUrl = await uploadImg(blob);
            callback(imageUrl, "첨부 이미지");
            return false;
        }
    }
});



function uploadImg(blob){

    let formData = new FormData();
    formData.append("img", blob);

    const xhr = new XMLHttpRequest();

    xhr.open("POST", "/post/uploadImg", false);
    xhr.setRequestHeader("contentType", "multipart/form-data");
    xhr.send(formData);

    if(xhr.readyState === xhr.DONE){
        if(xhr.status === 200){
            return xhr.response;
        }
    }
}

document.getElementById('btn_write').addEventListener("click", () => {

    contents.value = editor.getMarkdown();

    if(!checkTitle()) {
        alert('제목을 입력해주세요');
        return false;
    }else if(!checkContent()){
        alert('내용은 1 ~ 65535자 이하여야 합니다.');
        return false;
    }
    let form = document.getElementById("postForm");
    form.action = "/write";
    form.method = "POST";
    form.submit();
});

const checkTitle = () => {
    return document.getElementById('title').value !== "";
}

const checkContent = () => {
    if(contents.value.length >= 65535){
        return false;
    }
    return contents.value !== "";
}