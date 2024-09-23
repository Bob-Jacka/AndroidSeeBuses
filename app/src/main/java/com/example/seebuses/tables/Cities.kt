package com.example.seebuses.tables

import com.example.seebuses.core.data.ControlVars

object Cities {

    private val cityTable: MutableList<Array<String>> =
        ArrayList(ControlVars.SUPPORTED_TRANSPORT_CITIES)

    fun initTable_ru(): List<Array<String>> {
        cityTable.add(arrayOf("Ижевск", "izh"))
        cityTable.add(arrayOf("Пермь", "perm"))
        cityTable.add(arrayOf("Ростов", "rostov"))
        cityTable.add(arrayOf("Воронеж", "voronezh"))
        cityTable.add(arrayOf("Санкт-петербург", "spb"))
        cityTable.add(arrayOf("Екатеринбург", "ekaterinburg"))
        cityTable.add(arrayOf("Ярославль", "yaroslavl"))
        cityTable.add(arrayOf("Уфа", "ufa"))
        cityTable.add(arrayOf("Калининград", "kaliningrad"))
        cityTable.add(arrayOf("Казань", "kazan"))
        return cityTable
    }

    fun initTable_en(): List<Array<String>> {
        cityTable.add(arrayOf("Izhevsk", "izh"))
        cityTable.add(arrayOf("Perm", "perm"))
        cityTable.add(arrayOf("Rostov", "rostov"))
        cityTable.add(arrayOf("Voronezh", "voronezh"))
        cityTable.add(arrayOf("Saint-petersburg", "spb"))
        cityTable.add(arrayOf("Ekaterinburg", "ekaterinburg"))
        cityTable.add(arrayOf("Yaroslavl", "yaroslavl"))
        cityTable.add(arrayOf("Ufa", "ufa"))
        cityTable.add(arrayOf("Kaliningrad", "kaliningrad"))
        cityTable.add(arrayOf("Kazan", "kazan"))
        return cityTable
    }
}