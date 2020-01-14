package com.hsw.moonlight

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis

class KotlinActivity {
    data class Product(var id: String, var title: String)
    data class Stock(var pid: String, var stock: Int)
    data class Pms(var pid: String, var pmsTips: String)

    suspend fun getProductsByIds(pids: List<String>): List<Product> {
        delay(1000)
        return listOf(Product("1", "a"), Product("2", "b"))
    }

    suspend fun getProductStocksByIds(pids: List<String>): List<Stock> {
        delay(2000)
        return listOf(Stock("1", 2), Stock("2", 4))
    }

    suspend fun getProductPMSByIds(pids: List<String>): List<Pms> {
        delay(3000)
        return listOf(Pms("1", "100减99"), Pms("2", "100减99"))
    }

    fun combine(products: List<Product>?, productStocks: List<Stock>?, productPMS: List<Pms>?) {
        println(products)
        println(productStocks)
        println(productPMS)
    }

    fun main(args: Array<String>) = runBlocking<Unit> {
        val pids = listOf<String>("1", "2")
        val products = async {
            withTimeoutOrNull(1500) {
                getProductsByIds(pids)
            }
        }
        val productStocks = async {
            withTimeoutOrNull(2500) {
                getProductStocksByIds(pids)
            }
        }
        val productPMS = async {
            withTimeoutOrNull(2500) {
                getProductPMSByIds(pids)
            }
        }

        val measureTimeMillis = measureTimeMillis {
            combine(products.await(), productStocks.await(), productPMS.await())
        }
        println(measureTimeMillis)
    }
}