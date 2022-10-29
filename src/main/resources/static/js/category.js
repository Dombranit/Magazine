function getCategory(categoryId) {
    fetch('http://localhost:8006/api/category/' + categoryId)
        .then(response => response.json())
        .then(data => {
                console.log(data);
                document.getElementById('editCategoryId').value = categoryId;
                document.getElementById('editCategoryName').value = data.name;
            }
        )
}

async function updateCategory() {
    let categoryId = document.getElementById('editCategoryId').value;
    let categoryName = document.getElementById('editCategoryName').value;
    try {
        const response = await fetch('http://localhost:8006/api/category', {
            method: "PUT",
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                id: categoryId,
                name: categoryName
            })
        })
        document.getElementById('editCategoryName' + categoryId).innerText = categoryName;
        document.getElementById('editCategoryCloseBtn').click();
        document.getElementById('editCategoryAlertSuccess').style.visibility = 'visible'
    } catch (error) {
        document.getElementById('editCategoryAlert').style.visibility = 'visible'
    } finally {

    }
}

    function getCountry(countryId) {
        fetch('http://localhost:8006/api/country/' + countryId)
            .then(response => response.json())
            .then(data => {
                    console.log(data);
                    document.getElementById('editCountryId').value = countryId;
                    document.getElementById('editCountryName').value = data.countryName;
                }
            )
    }

    async function updateCountry() {
        let countryId = document.getElementById('editCountryId').value;
        let countryName = document.getElementById('editCountryName').value;
        try {
            const response = await fetch('http://localhost:8006/api/country', {
                method: "PUT",
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify({
                    id: countryId,
                    countryName: countryName
                })
            })
            document.getElementById('editCountryName' + countryId).innerText = countryName;
            document.getElementById('editCountryCloseBtn').click();
            document.getElementById('editCountryAlertSuccess').style.visibility = 'visible'
        } catch (error) {
            document.getElementById('editCountryAlert').style.visibility = 'visible'
        } finally {

        }
    }

        async function createCountry() {
            let countryName = document.getElementById('addCountryName').value;
            try {
                const response = await fetch('http://localhost:8006/api/country', {
                    method: "POST",
                    headers: {
                        'Content-type': 'application/json'
                    },
                    body: JSON.stringify({
                        countryName: countryName
                    })
                })
                document.getElementById('editCountryName').innerText = countryName;
                document.getElementById('editCountryCloseBtn').click();
                document.getElementById('editCountryAlertSuccess').style.visibility = 'visible'
            } catch (error) {
                document.getElementById('editCountryAlert').style.visibility = 'visible'
            } finally {

            }
}