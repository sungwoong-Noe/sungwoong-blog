
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

            let parentCategoryList_div = document.getElementById('parentCategoryList');
            parentCategoryList_div.innerHTML += `<div>
                                                    <p style="cursor: pointer; display: inline;" onclick="selectParent(${response.id})">${response.name}</p>
                                                    <button type="button" id="btn_deleteCategroy" onclick="categoryDelete(${response.id}})">삭제</button>
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


const selectParent = (parentId) => {


    let childCategories = '';

    const childList = Category.getChildList(parentId).childCategories;

    childList.forEach(child => {

        childCategories +=
            `<div>
                <p>${child.name}</p>
                <input type="hidden" value="${child.id}"> 
            </div>`;

    });


    document.getElementById('childCategory').innerHTML = childCategories;
}


const deleteCategory = (categoryId, clickTag) => {
    if(confirm('삭제하시겠습니까?')) {

        const xhr = new XMLHttpRequest();
        xhr.open("DELETE", `/category/delete/${categoryId}`, false);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send();


        if(xhr.readyState === xhr.DONE) {
            if (xhr.status === 200) {
                clickTag.parentNode.remove();
            }
        }
    }
}




