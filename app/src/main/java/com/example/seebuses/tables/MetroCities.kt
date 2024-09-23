package com.example.seebuses.tables

import com.example.seebuses.core.data.ControlVars

object MetroCities {

    private val cityTable: MutableList<Array<String>> =
        ArrayList(ControlVars.SUPPORTED_METRO_CITIES)

    fun initTable_ru(): List<Array<String>> {
        cityTable.add(arrayOf("Москва", "moscow"))
        cityTable.add(arrayOf("Санкт-Петербург", "spb"))
        cityTable.add(arrayOf("Самара", "samara"))
        cityTable.add(arrayOf("Екатеринбург", "ekaterinburg"))
        cityTable.add(arrayOf("Новосибирск", "novosibirsk"))
        cityTable.add(arrayOf("Нижний-Новгород", "nizhniy-novgorod"))
        cityTable.add(arrayOf("Казань", "kazan"))
        cityTable.add(arrayOf("Измир", "izmir"))
        cityTable.add(arrayOf("Стамбул", "istanbul"))
        cityTable.add(arrayOf("Тбилиси", "tbilisi"))
        return cityTable
    }

    fun initTable_en(): List<Array<String>> {
        cityTable.add(arrayOf("Moscow", "moscow"))
        cityTable.add(arrayOf("Saint-petersburg", "spb"))
        cityTable.add(arrayOf("Samara", "samara"))
        cityTable.add(arrayOf("Ekaterinburg", "ekaterinburg"))
        cityTable.add(arrayOf("Novosibirsk", "novosibirsk"))
        cityTable.add(arrayOf("Nizhniy-novgorod", "nizhniy-novgorod"))
        cityTable.add(arrayOf("Kazan", "kazan"))
        cityTable.add(arrayOf("Izmir", "izmir"))
        cityTable.add(arrayOf("Istanbul", "istanbul"))
        cityTable.add(arrayOf("Tbilisi", "tbilisi"))
        return cityTable
    }
}