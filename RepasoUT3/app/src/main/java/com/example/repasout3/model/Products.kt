package com.example.repasout3.model

data class Products(val prodId: String, val prodName: String, val country: String, val price: Float) {
    override fun toString(): String {
        return "Products( Id: '$prodId', Name: '$prodName', Country: '$country', Price: '$price' )"
    }
}
