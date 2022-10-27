function getCategory(categoryId) {
    fetch('http://localhost:8006/api/category/' + categoryId)
        .then(response => response.json())
        .then(data => {
                console.log(data);
                document.getElementById('editCategoryId').value = categoryId;
                document.getElementById('editCategoryName').value =data.name;
            }
        )
}
async function updateCategory () {
    let categoryId = document.getElementById('editCategoryId').value;
    let categoryName = document.getElementById('editCategoryName').value;
    try {
        const response = await fetch('http://localhost:8006/api/category',{
            method: "PUT",
            headers:{
                'Content-type':'application/json'
            },
            body : JSON.stringify({
                id:categoryId,
                name:categoryName
            })
        })
        document.getElementById('editCategoryName'+ categoryId).innerText=categoryName;
        document.getElementById('editCategoryCloseBtn').click();
        document.getElementById('editCourseAlertSuccess').style.visibility='visible'
    }
    catch (error){
        document.getElementById('editCourseAlertSuccess').style.visibility='visible'
    }
    finally {

    }
}