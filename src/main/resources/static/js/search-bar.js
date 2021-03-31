const productsList = document.getElementById('productsList')
const searchBar = document.getElementById('searchInput')

const allProducts = [];

const firmName = document.getElementById("firmDataFirmName").value;
fetch("http://localhost:8080/firms/items/api/" + firmName).
then(response => response.json()).
then(data => {
  for (let item of data) {
    allProducts.push(item);
  }
  displayProducts(allProducts);
})

console.log(allProducts);

const displayProducts = (items) => {
  productsList.innerHTML = items
      .map((i) => {
        return ` <div class="col-md-3" >
                    <div class="card mb-3 box-shadow">
                      <img src="${i.pictureUrl}" class="card-img-top" alt="No image"
                        data-holder-rendered="true"
                        style="height: 150px; width: 100%; display: block;">
                      <div class="card-body">
                        <div class="text-center">
                            <p class="card-text border-bottom " style="text-align:left;">Име: 
                                <span style="float:right;">
                                    ${i.name}
                                </span>
                            </p>
                            <p class="card-text border-bottom " style="text-align:left;">Баркод: 
                                <span style="float:right;">
                                    ${i.barcode}
                                </span>
                            </p>
                            <p class="card-text border-bottom " style="text-align:left;">Група: 
                                <span style="float:right;">
                                    ${i.groupName}
                                </span>
                            </p>
                            <p class="card-text border-bottom " style="text-align:left;">Цена: 
                                <span style="float:right;">
                                    ${Number(i.outgoingPrice).toFixed(2)}
                                </span>
                            </p>
                            <p class="card-text border-bottom " style="text-align:left;">Склад: 
                                <span style="float:right;">
                                    ${i.warehouseName}
                                </span>
                            </p>
                            <p class="card-text border-bottom " style="text-align:left;">Описание: 
                                <span style="float:right;">
                                    ${i.description}
                                </span>
                            </p>
                            <a class="btn-link" href="/transactions/buy/${i.id}">Поръчай</a>
                        </div>
                      </div>
                    </div>
                </div>`
      })
      .join('');

};

searchBar.addEventListener('keyup', (e) => {
  const searchingCharacters = searchBar.value.toLowerCase();
  let filteredProducts = allProducts.filter(item => {
    return item.name.toLowerCase().includes(searchingCharacters);
  });
  displayProducts(filteredProducts);
})


