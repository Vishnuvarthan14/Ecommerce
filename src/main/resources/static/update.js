const prod_name=document.getElementById("name")
const description=document.getElementById("description")
const brand=document.getElementById("brand")
const price=document.getElementById("price")
const category=document.getElementById("category")
const quantity=document.getElementById("quantity")
const image=document.getElementById("preview")
const date=document.getElementById("date")

const update = document.getElementById("update");

const params = new URLSearchParams(window.location.search)
const prod_id = params.get("id")

function loadData()
{
    fetch(`/api/product/${prod_id}`)
    .then(response=>response.json())
    .then(product=>displayProduct(product))
}

function displayProduct(p)
{
    prod_name.value=p.name;
    description.value=p.description;
    brand.value=p.brand;
    price.value=p.price;
    category.value=p.category
    quantity.value=p.quantity
    image.src=`/api/product/${prod_id}/image`
}

loadData();

const form = document.getElementById("productForm")

form.addEventListener('submit',function (e){
    e.preventDefault();
    const formdata= new FormData();

    const product = {
        name:form.name.value,
        description: form.description.value,
        brand: form.brand.value,
        price: parseFloat(form.price.value),
        category: form.category.value,
        releaseDate: form.releaseDate.value,
        quantity: parseInt(form.quantity.value),
        available: parseInt(form.quantity.value) > 0

    };

    formdata.append("product"
        ,new Blob([JSON.stringify(product)],{type:"application/json"}));
    
    formdata.append("imagefile",form.image.files[0]);

    fetch(`/api/product/${prod_id}`,{method:"PUT",
        body:formdata
    })
    .then(res=>{
        if(!res.ok){ throw new Error("Failed to Update product");
            alert("Process failed");
        }
       return res.text();
    })
    .then(data=>{
        console.log(data) 
        alert("Sucessfully Updated")
        form.reset();
    });
});