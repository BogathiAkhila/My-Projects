async function fetchData(){
    let res=await fetch("http://localhost:3000/FoodMenu");
        try{
            if(!res.ok){
                throw new Error("Data not fetching");
            }
            let data=await res.json();
            showdata(data);

        }
        catch(error){

    }
    
}
function showdata(data){
    let container=document.getElementById("container")
    let item=document.createElement("div");
    item.innerHTML=data.map((FoodMenu)=>{
        return `
        <p>Id:${FoodMenu.FOODID}</p>
        <p>Name:${FoodMenu.FoodName}</p>
        <button id="delete${FoodMenu.FOODID}">Delete</button>
        <button id="edit${FoodMenu.FOODID}">Edit</button>
        `
        
    }).join(",");
    container.appendChild(item);
    data.forEach(FoodMenu=>{
        let deletebtn=document.getElementById(`delete${FoodMenu}`)
        let editbtn=document.getElementById(`edit${setDefaultEventParameters.id}`)

        deletebtn.onclick=()=>{
            deleteData(FoodMenu.FOODID)
        }
        editbtn.onclick=()=>{
            editData(FoodMenu.FOODID)
        }
    });

}

//Deleting the Data
async function deleteData(FOODID){
    let res=await fetch(`http://localhost:3000/FoodMenu/${FoodName}`,
    {"method":"DELETE"})
    try{
        if(!res.ok){
            throw new Error("Data is not deleting")
        }

    }
    catch(error){
        console.log("it is not deleting, it is some error at deletion")

    }
}

//Putting the  data
async function editData(FOODID){
    let res=await fetch(`http://localhost:3000/FoodMenu/${FoodName}`,{"method"="PUT",
        "header"="application/json",
        "body"=""
    })
}