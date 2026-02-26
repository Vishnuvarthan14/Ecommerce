// alert("NEW JS LOADED");

const productDiv = document.querySelector(".displaycards")
const filter=document.getElementById("choise")

let allProducts=[]

function loadData()
{
    fetch("/api/").then(response=>response.json())
    .then(data=>{allProducts=data;
                displayProduct(allProducts);
    })
   // console.log('working')
}



function displayProduct(products)
{
    //   console.log("Displaying:", products);
    productDiv.innerHTML="";

    products.forEach(p=>{
        productDiv.innerHTML+=`<div class='card' onclick="openProducts(${p.id})">
                            <img src="/api/product/${p.id}/image" alt="${p.name}">
                            <h5>${p.brand}</h5>
                            <h3>${p.name}</h3>
                            <h4>Category : ${p.category}</h4>
                            <p>${p.description}</p>
                            <p> Price<b> ${p.price}</b></p>
                            </div>`;
    });
}

function openProducts(id)
{
    window.location.href=`product.html?id=${id}`
}

filter.addEventListener('change',()=>{
    const value=filter.value;

    if(value==="")
    {
        displayProduct(allProducts)
    }
    else{
        const datas =allProducts.filter(p=>p.category===value)
        displayProduct(datas) 
    }
});

loadData();

const search = document.getElementById("search")

search.addEventListener("input", function(){
    const keyword = search.value.trim();

    if(keyword.length === 0){
        suggestions.innerHTML = "";
        return;
    }

    fetch(`/api/product/search/${keyword}`)
        .then(response => response.json())
        .then(datas => {
            console.log("Search response:", datas);
            displayInSuggesion(datas, keyword);
        });

});

document.addEventListener("click", function(e) {
    const searchContainer = document.querySelector(".search-container");

    if (!searchContainer.contains(e.target)) {
        suggestions.innerHTML = "";
    }
});

function displayInSuggesion(datas,keyword)
{
    // console.log(datas)
    const suggestions = document.getElementById("suggestions");
    suggestions.innerHTML="";
    if(keyword.length==0||datas.length==0)
        return;

    datas.forEach(data=>{
        suggestions.innerHTML+=`<a onclick="openProducts(${data.id})">${data.name}-- ${data.brand}</a>`
    });
}
