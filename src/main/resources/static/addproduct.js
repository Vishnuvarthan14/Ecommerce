
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

    fetch("/api/product",{method:"POST",
        body:formdata
    })
    .then(res=>{
        if(!res.ok){ throw new Error("Failed to add product");
            alert("Process failed");
        }
       return res.json();
    })
    .then(data=>{
        console.log("successfully added") 
        alert("Sucessfully added")
        form.reset();
    })
    .catch(error=>{
        console.log(error)
        alert("Failed")
    });
});