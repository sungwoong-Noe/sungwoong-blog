
function categoryDelete(clickTag){
    console.log(clickTag);
    console.log(clickTag.previousSibling);
    console.log(clickTag.previousSibling.previousSibling);
}


document.getElementById('btn_parentCategory_regist').addEventListener("click", () =>{

    let categoryRegistForm = {};



    if(!parentName_check()){
        alert('이름을 입력해주세요');
        return false;
    }else{
        categoryRegistForm.name = parentName_check();
    }

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/category/regist", false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("dataType", "json");
    xhr.send(JSON.stringify(categoryRegistForm));

    if(xhr.readyState === xhr.DONE){
        if(xhr.status === 200){

            console.log(typeof xhr.response);
            let response = JSON.parse(xhr.response);
            console.log(typeof response);
            console.log(response.name);
            console.log(response.id);

            let parentCategoryList_div = document.getElementById('parentCategoryList');
            parentCategoryList_div.innerHTML += `<div>
                                                    <p>${response.name}</p>
                                                    <input type="hidden" value="${response.id}">
                                                    <button class="btn_delete" type="button" onclick="categoryDelete(this)">-</button>
                                                  </div>`;
        }
    }
});

let parentName_check = () => {
    let parentCategoryName = document.getElementById('parentCategoryName').value;

    if(!parentCategoryName){
        return false;
    }else{
        return parentCategoryName;
    }
}