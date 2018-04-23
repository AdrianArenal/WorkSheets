package com.dsdm.miw.uniovi.worksheets.server

import com.dsdm.miw.uniovi.worksheets.client.APIClient
import com.dsdm.miw.uniovi.worksheets.model.Customer
import com.dsdm.miw.uniovi.worksheets.model.WorkSheet
import com.dsdm.miw.uniovi.worksheets.model.Worker
import com.google.common.net.UrlEscapers
import com.google.gson.Gson
import java.net.URL


class WorkSheetServer {

    companion object {
        private const val API_URL = "http://156.35.98.102:1339"
        val api: APIService
            get() = APIClient.getClient(API_URL)!!.create(APIService::class.java)
    }

    fun getWorkers(): Array<Worker>? {
        val workersJsonStr = URL("$API_URL/worker").readText()
        return Gson().fromJson(workersJsonStr, Array<Worker>::class.java)
    }

    fun getCustomers(): Array<Customer>? {
        val customersJsonStr = URL("$API_URL/customer").readText()
        return Gson().fromJson(customersJsonStr, Array<Customer>::class.java)
    }

    fun getCustomerByName(name: String): Customer? {
        val encodedString = UrlEscapers.urlFragmentEscaper().escape(name)
        val customerJsonStr = URL("$API_URL/customer/findByName/$encodedString").readText()
        return Gson().fromJson(customerJsonStr, Customer::class.java)
    }

    fun getWorkSheets(): Array<WorkSheet>? {
        val customersJsonStr = URL("$API_URL/worksheet").readText()
        return Gson().fromJson(customersJsonStr, Array<WorkSheet>::class.java)
    }
}