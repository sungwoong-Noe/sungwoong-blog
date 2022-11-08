const thumbnailInput = document.getElementById("thumbnail");
const thumbnailUploadBtn = document.getElementById('btn_thumbnail_upload');


const uploadThumbnail = function(){
    let formData = new FormData();
    formData.append("img", thumbnailInput.files[0]);

    const xhr = new XMLHttpRequest();

    // xhr.onreadystatechange = function () {
    //     if (this.readyState === this.DONE && this.status === 200) {
    //         const response = this.responseText;
    //         document.getElementById('thumbnail_preview').src = response;
    //         document.getElementById('thumbnailUrl').value = response;
    //     }
    // }

    xhr.open("POST", "/post/uploadImg", false);
    xhr.setRequestHeader("contentType", "multipart/form-data");
    xhr.send(formData);

    if(xhr.readyState === xhr.DONE){
        if(xhr.status === 200){
            let thumbnailUrl = xhr.response;
            document.getElementById('thumbnail_preview').src = thumbnailUrl;
            document.getElementById('thumbnailUrl').value = thumbnailUrl;
        }
    }
}


thumbnailUploadBtn.addEventListener("click", () =>{
    let url = uploadThumbnail();
});






